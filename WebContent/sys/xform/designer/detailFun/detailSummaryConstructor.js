/**
 * 构造器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var strutil = require('lui/util/str');
    var env = require('lui/util/env');
    var DetailSummaryConstructor = base.Container.extend({

        initialize: function ($super, cfg) {
            $super(cfg);
            this.config.controlId = XForm_FormatControlIdWithNoIndex(this.config.controlId);
            var controlIdWithNoPrefix = XForm_FormatControlIdWithNoPrefix(this.config.controlId);
            var xformFlagNode = this.element.parent("xformflag");
            var valueNode = xformFlagNode.find("input[name$='" + controlIdWithNoPrefix + ")']");
            var textNode = xformFlagNode.find("input[name$='" + controlIdWithNoPrefix + "_text)']");
            var selfTr = xformFlagNode.parent().parent();
            this.config.node = {
                value: valueNode,
                text: textNode,
                xform: xformFlagNode,
                tr: selfTr,
                cfg: this.element
            };
            this.config.relation = JSON.parse(this.config.relation.replace(/'/g, "\""));
            this.config.fieldCalc = JSON.parse(this.config.fieldCalc.replace(/'/g, "\""));
            this.otherCfg = JSON.parse(this.config.other.replace(/'/g, "\""));
            this.build();
        },
        DetailSummaryCalcFun: {
            "sum": function (arr, self) {
                var r = 0;
                var scale = self.otherCfg.scale > 0 ? self.otherCfg.scale : 0;
                var pow = Math.pow(10, scale);
                for (var i = 0; i < arr.length; i++) {
                    r += arr[i] * pow;
                }
                return parseFloat(r / pow).toFixed(scale);
            },
            "max": function (arr, self) {
                var r = arr[0];
                for (var i = 0; i < arr.length; i++) {
                    r = arr[i] > r ? arr[i] : r
                }
                return r;
            },
            "min": function (arr, self) {
                var r = arr[0];
                for (var i = 0; i < arr.length; i++) {
                    r = arr[i] < r ? arr[i] : r
                }
                return r;
            },
            "avg": function (arr, self) {
                var r = 0;
                var scale = self.otherCfg.scale > 0 ? self.otherCfg.scale : 1;
                var scaleResult = self.otherCfg.scale  > 0 ? self.otherCfg.scale  : 0;
                for (var i = 0; i < arr.length; i++) {
                    r += arr[i]*scale;
                }
                //被除数不能为零
                if((arr.length*scale) == 0){
                    r = r / 1 ;
                }else{
                    r =  r / (arr.length*scale);
                }
                return parseFloat(r).toFixed(scaleResult);
            }
        },
        startup: function ($super, cfg) {
            $super(cfg);
        },
        build: function () {
            var cfg = this.config;
            var valNode = this.config.node.value;
            var textNode = this.config.node.text;
            var self = this;

            //显示值处理
            //valNode.attr("type", "text");
            textNode.attr("readonly", "readonly");
            valNode.on("change", function () {
                textNode.val(valNode.val());
            });
            valNode.trigger($.Event("change"));
            // 根据配置项设置宽度
            if (cfg.width) {
                if (cfg.width.indexOf("%") > -1) {
                    textNode.css("width", cfg.width);
                } else {
                    textNode.css("width", cfg.width + 'px');
                }
            }
            textNode.css("cursor","default");
            //按钮处理
            var $clacBtn = $("<span class=\"lui-detail-summary-button\" style='cursor: pointer' ></span>");
            $clacBtn.html(cfg.lan.buttonName)
            $clacBtn.on("click", function () {
                self.doCalcDetailSummary();
            });
            textNode.after($clacBtn);
            this.config.node.clac = $clacBtn;
            if (cfg.status === "edit") {
                $clacBtn.show();
                this.listenDetailChange();
            } else {
                this.setValShow(valNode,textNode);
                $clacBtn.hide();
            }
        },
        setValShow:function(valNode,textNode){
            var num =valNode.val();
            if ((typeof num) != 'number' || window.isNaN(num))
               return;
            //小数位显示
             var scale = this.otherCfg.scale;
            num= parseFloat(num).toFixed(scale)
            if('true' ==   this.otherCfg.thousandShow){//千分位显示
                /*num = num.replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');*/
                num = num.replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
                //上面的正则表达式会给小数位后面也加上千分位,所以还要把小数点后面的,给替换掉
                var index = num.indexOf(".");
                if (index > 0){
                    var substring = num.substring(index);
                    substring = substring.replace(/,/g,"");
                    num = num.substring(0,index) + substring;
                }
            }
            valNode.val(num);
            textNode.val(num);
        },
        //获取dom元素的值，需要判断区别获取input\select\radio\checkbox\textarea
        getDomVal: function (fieldId, parentTr) {
            //获取自己用于匹配的字段值
            var xformFieldId = XForm_FormatControlIdWithNoPrefix(fieldId)
            var ele = parentTr.find("[name$='" + xformFieldId + ")']");
            var tagName = ele.length > 0 ? ele[0].tagName : ele.tagName;
            if (tagName === "INPUT") {
                //radio
                var inputTagType = ele.attr("type");
                if (inputTagType === "radio") {
                    return  parentTr.find("input[name$='" + xformFieldId + ")']:checked").val();
                } else {
                    return ele.val();
                }
            }else if (tagName === "SELECT"){
                return parentTr.find("select[name$='" + xformFieldId + ")']  option:selected").val();
            }
        },
        doCalcDetailSummary: function () {
            var self = this;
            //获取匹配对象
            var cfg = self.config;
            if(cfg==null){
                return;
            }
            //获取自己用于匹配的字段值
            var selfRelationFieldVal = self.getDomVal(cfg.relation.self, cfg.node.tr);
            if (!selfRelationFieldVal){
                return;
            }

            //获取需要计算的字段
            var calcField = cfg.fieldCalc.field;
            var calcFieldVals = [];
            //获取对方用于匹配的字段dom
            $("#TABLE_DL_" + cfg.detailPointer).find("tr").each(function (idx, tr) {
                var pointerVal = self.getDomVal(cfg.relation.pointer, $(tr));
                if (pointerVal === selfRelationFieldVal) {
                    // console.log(selfRelationFieldVal, pointerVal);
                    var calcVal = self.getDomVal(calcField, $(tr));
                    calcVal = parseFloat(calcVal);
                    if (!isNaN(calcVal)) {
                        calcFieldVals.push(calcVal);
                    }
                }
            });

            //计算
            var result = self.DetailSummaryCalcFun[cfg.fieldCalc.calc](calcFieldVals,self);
            result = result?result:0;
            this.config.node.value.val(result).trigger($.Event("change"));
            // 触发值改变事件
            __xformDispatch(result, this.config.node.value[0]);
        },
        listenDetailChange: function () {
            var self = this;
            if (!self.otherCfg.autoCalc || self.otherCfg.autoCalc === "1") {
                return;
            }
            //监听指定明细表的行删除/新增事件,
            $(document).on('table-add-new', 'table[showStatisticRow]', function (e, argus) {
                self.doCalcDetailSummary()
                var row = argus.row;
            });
            $(document).on('table-delete', 'table[showStatisticRow]', function (e, argus) {
                self.doCalcDetailSummary()
            });
            //监听自己与指定明细表的匹配字段改变事件,
            var relation = self.config.relation;
            var xformSelfFieldId = XForm_FormatControlIdWithNoPrefix(relation.self)
            $(document).on("change", "[name$='" + xformSelfFieldId + ")']", function (d) {
                self.doCalcDetailSummary()
            });
            var xformPointerFieldId = XForm_FormatControlIdWithNoPrefix(relation.pointer)
            $(document).on("change", "[name$='" + xformPointerFieldId + ")']", function (d) {
                self.doCalcDetailSummary()
            });
            //监听指定明细表的计算字段改变事件,
            var xformCalcFieldId = XForm_FormatControlIdWithNoPrefix(self.config.fieldCalc.field)
            $(document).on("change", "[name$='" + xformCalcFieldId + ")']", function (d) {
                self.doCalcDetailSummary()
            });
        },
        doRender: function (html) {
            this.element.append(html);
            if (this.config.status === "edit" && this.placeholderWgt) {
                this.placeholderWgt.emit("render_finish");
            }
        },

        updateTextView: function () {
            this.placeholderWgt.updateTextView();
        }
    });

    exports.DetailSummaryConstructor = DetailSummaryConstructor;
})