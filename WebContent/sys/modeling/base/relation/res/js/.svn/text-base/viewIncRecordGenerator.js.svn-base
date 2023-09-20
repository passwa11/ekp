/**
 * 视图入参生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var modelingLang = require("lang!sys-modeling-base");
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");

    /*
        列表视图，有入参。需要自动生成
     */
    var ListViewIncRecordGenerator = base.Component.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.viewId = cfg.viewId;
            this.pamType = cfg.pamType;
            this.container = cfg.container;
            this.key = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.draw();
        },
        draw: function () {
            var self = this;
            var $container = self.container
            if (self.viewId && self.viewId != "_def") {
                var u = Com_Parameter.ContextPath + "sys/modeling/base/sysModelingOperation.do?method=getIncParams&fdId=" + self.viewId+"&pamType="+self.pamType;
                $.ajax({
                    url: u,
                    method: 'GET',
                    async: false
                }).success(function (resultStr) {
                    var result = JSON.parse(resultStr);
                    var arr = JSON.parse(result.data);
                    if (!arr || arr.length <= 0) {
                        return
                    }
                    $.each(arr, function (idx, v) {
                        var $tr = $("<tr class='listViewIncTr'/>");
                        var typeTip = "";
                        if(v.type){
                            typeTip = '(' + v.type + ')';
                        }
                        var formulaBuilderKey = v.paramName;
                        if(v.type){
                            formulaBuilderKey = formulaBuilderKey + "_" + v.type;
                        }
                        $tr.attr("lui_mark_viewset_inv", v.paramName);
                        $tr.attr("type", v.type?v.type:"");
                        $tr.append("<td>" + v.paramText + typeTip +"</td>");
                        $tr.append("<td>" + v.operator + "</td>");
                        var $formulaTd = $("<td />");
                        $formulaTd.append(formulaBuilder.get(self.key + "_" + formulaBuilderKey , v.fieldType));
                        $tr.append($formulaTd);
                        $container.append($tr);
                    });
                    self.element = $container;
                });
            }
        },
        destroy: function ($super, cfg) {
            $super(cfg);
        },
        // "view": [{"idx": 0,"ele": "fdId", "expression": {"text": "","value": "$ID$"}}],
        getKeyData: function () {
            var self = this;
            var keyData = [];
            var trs = self.element.find(".listViewIncTr");
            $.each(trs, function (idx, tr) {
                var $tr = $(tr);
                var name = $tr.attr("lui_mark_viewset_inv");
                var type = $tr.attr("type");
                var typeSuffix = "";
                if(type && type != ""){
                    typeSuffix =  "_" + type;
                }
                var exValue = $tr.find("[name=" + self.key + "_" + name + typeSuffix + "]").val()
                var exText = $tr.find("[name=" + self.key + "_" + name + typeSuffix +"_name]").val()
                var item = {
                    "idx": idx,
                    "ele": name,
                    "type": type,
                    "expression": {
                        "text": exText,
                        "value": exValue
                    }
                };
                keyData.push(item);
            })
            return keyData;
        },
        initByStoreData: function (storeData) {

            var self = this;
            if (storeData) {
                var views = storeData.view;
                if (views) {
                    $.each(views, function (idx, v) {
                        var name = v.ele;
                        var exp = v.expression;
                        var type = v.type;
                        var typeSuffix = "";
                        if(type && type != ""){
                            typeSuffix =  "_" + type;
                        }
                        if (name && exp) {
                            self.element.find("[name=" + self.key + "_" + name + typeSuffix + "]").val(exp.value);
                            self.element.find("[name=" + self.key + "_" + name + typeSuffix + "_name]").val(exp.text);
                        }
                    })
                }
            }
        }
    });
    /*
        查看视图，一定会带fdId入参
     */
    var ViewIncRecordGenerator = base.Component.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.viewId = cfg.viewId;
            this.container = cfg.container;
            this.key = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.draw()
        },
        draw: function () {
            var self = this;
            self.drawDef( );
        },
        drawDef: function () {
            var self = this;

            var $fdIdTr = $("<tr class='viewIncTr'/>");
            $fdIdTr.append("<td>fdId</td>");
            $fdIdTr.append("<td>"+ modelingLang['modelingAppListview.enum.equal']+"</td>");
            var $formulaTd = $("<td />");
            $formulaTd.append(formulaBuilder.get(self.key + "_fdId", "String"));
            $fdIdTr.append($formulaTd);
            self.container.append($fdIdTr)
            self.element = self.container;
        },
        destroy: function ($super, cfg) {
            this.parent.deleteWgt(this, "where");
            $super(cfg);
        }, getKeyData: function () {
            var self = this;
            var keyData = [];
            var name = self.key + "_fdId";
            var exValue = self.element.find("[name=" + name + "]").val();
            var exText = self.element.find("[name=" + name + "_name]").val();
            var item = {
                "idx": 0,
                "ele": "fdId",
                "expression": {
                    "text": exText,
                    "value": exValue
                }
            };
            keyData.push(item);
            return keyData;
        },
        initByStoreData: function (storeData) {
            var self = this;
            if (storeData) {
                var views = storeData.view;
                if (views) {
                    $.each(views, function (idx, v) {
                        var name = v.ele;
                        var exp = v.expression;
                        if (name && exp) {
                            self.element.find("[name=" + self.key + "_" + name + "]").val(exp.value);
                            self.element.find("[name=" + self.key + "_" + name + "_name]").val(exp.text);
                        }
                    })
                }
            }

        }
    });
    /*
        新建视图，有入参，可选择+
     */
    var NewViewIncRecordGenerator = base.Component.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.childLines = [];
            this.viewId = cfg.viewId;
            this.widgets = cfg.widgets;
            this.container = cfg.container;
            this.createBtn = cfg.createBtn;
            this.key = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.draw();
        },
        draw: function () {
            var self = this;
            self.element = self.container;
            //#129255
            self.createBtn.unbind();
            self.createBtn.on("click", function () {
                var incP = new NewViewIncParam({parent: self})
                incP.draw(self.element);
            });
        },
        addWgt: function (wgt) {
            this.childLines.push(wgt);
        },
        deleteWgt: function (wgt) {
            var collect = this.childLines;
            for (var i = 0; i < collect.length; i++) {
                if (collect[i] === wgt) {
                    collect.splice(i, 1);
                    break;
                }
            }
        }, getKeyData: function () {
            var self = this;
            var keyData = [];
            $.each(self.childLines, function (idx, ele) {
                var item = ele.getKeyData();
                item.idx = idx;
                keyData.push(item);
            });
            return keyData;
        },
        initByStoreData: function (storeData) {
            var self = this;
            if (storeData) {
                var views = storeData.viewNew;
                if (views) {
                    for (var i in views) {
                        if (!views[i])
                            break;
                        var incP = new NewViewIncParam({parent: self})
                        incP.draw(self.element);
                        incP.initByStoreData(views[i]);
                    }
                }
            }

        }
    });

    var NewViewIncParam = base.Component.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.key = "fd_newView_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
        },
        draw: function (container) {
            var self = this;
            self.element = $("<tr class='newViewInc_tr'></tr>");
            // 字段td
            var $widgetTd = $("<td class='newViewInc_td_widget'></td>");
            var $widgetSelect = $("<select class='newViewInc_widget'></selct>");
            $widgetSelect.append("<option value=''>"+modelingLang['relation.please.choose']+"</option>");
            var sourceData = this.parent.widgets;
            if (sourceData && sourceData) {
                for (var controlId in sourceData) {
                    var info = sourceData[controlId];
                    // if(info.name.indexOf(".")>-1&&info.businessType == 'placeholder'){
                    //     //暂时过滤业务关联控件
                    //     continue;
                    // }
                    var title = info.fullLabel;
                    if(!title){
                        title =  info.label;
                    }
                    $widgetSelect.append("<option value='" + info.name + "' data-property-type='" + info.type + "' title='" + title + "'>" + info.label + "</option>");
                }
            }
            $widgetSelect.on("change", function () {
                self.updateValueTd(this);
            });
            $widgetTd.append($widgetSelect);
            // 输入值td公式定义器
            self.$valueTd = $("<td></td>");
            // 操作td
            var $delTd = $("<td class=\"model-mask-panel-table-opt\"></td>");
            var $delSpan = $("<p>"+modelingLang['enums.behavior_type.2'] +"</p>");
            $delSpan.on("click", function () {
                self.destroy();
            });
            $delTd.append($delSpan);
            self.element.append($widgetTd);
            self.element.append(self.$valueTd);
            self.element.append($delTd);
            container.append(self.element);
            self.parent.addWgt(this);
        },
        updateValueTd: function (dom) {
            this.$valueTd.html("");
            var $tr = $(dom).closest("tr");
            var type =$tr.find(".newViewInc_widget option:selected").attr("data-property-type");
            try{
                var name=$tr.find(".newViewInc_widget option:selected").val();
                if (name.indexOf(".") > 0) {
                    type = type+"[]";
                }
            }catch (e) {
                console.debug("updateValueTd",name)
            }
            var sourceData ;
            if(this.parent && this.parent.config && this.parent.config.pcfg && this.parent.config.pcfg.pcfg){
                sourceData = this.parent.config.pcfg.pcfg.sourceData;
            }
            var formula = formulaBuilder.get(this.key,
                type,undefined,undefined, function (fieldList) {
                    if (sourceData) {
                        f1:for (var controlId in sourceData) {
                            var info = sourceData[controlId];
                            if(type == 'Attachment'||type == 'Attachment[]'){
                                //添加附件
                                if( info.type == 'Attachment'){
                                    for(var i=0;i<fieldList.length;i++){
                                        if(fieldList[i].name == info.name){
                                            continue f1;
                                        }
                                    }
                                    //添加
                                    fieldList.push(info)
                                }
                            }else{
                                //过滤所有附件
                                if( info.type == 'Attachment'){
                                    for(var i=fieldList.length-1;i>=0;i--){
                                        if(fieldList[i].name == info.name){
                                            //移除
                                            fieldList.splice(i,1);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return fieldList;
            });
            this.$valueTd.append(formula);
        },
        destroy: function ($super, cfg) {
            this.parent.deleteWgt(this);
            $super(cfg);
        },
        getKeyData: function () {
            /*
                "ele": "fdId",
                "name": {"text": "文本1","type": "String", "value": "fd_37d857edb1a520" },
                "expression": { "text": exText,"value": exValue}
            */
            var keyData = {};
            var $option = this.element.find(".newViewInc_widget option:selected");
            if ($option.val() === "") {
                return null;
            }
            keyData.ele = $option.val();
            keyData.name = {};
            keyData.name.type = $option.attr("data-property-type");
            keyData.name.value = $option.val();
            keyData.name.text = $option.text();
            var $value = this.element.find("[name='" + this.key + "']");
            keyData.expression = {};
            keyData.expression.text = $value.val();
            keyData.expression.value = $value.val();
            var $valueText = this.element.find("[name='" + this.key + "_name']");
            if ($valueText.length > 0) {
                keyData.expression.text = $valueText.val();
            }
            return keyData;
        },
        initByStoreData: function (storeData) {
            var self = this
            if (!storeData) {
                return
            }
            var $widgetSelect = self.element.find(".newViewInc_widget");
            $widgetSelect.val(storeData.ele);
            $widgetSelect.trigger($.Event("change"));

            var exp = storeData.expression;
            if (exp) {
                self.element.find("[name='" + this.key + "']").val(exp.value);
                self.element.find("[name='" + this.key + "_name']").val(exp.text);
            }
        }
    });

    exports.ListViewIncRecordGenerator = ListViewIncRecordGenerator;
    exports.ViewIncRecordGenerator = ViewIncRecordGenerator;
    exports.NewViewIncRecordGenerator = NewViewIncRecordGenerator;
});
