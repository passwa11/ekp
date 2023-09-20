/**
 *  汇总控件移动端
 */
define([
    "dojo/_base/array",
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-prop",
    "mui/util",
    "dijit/registry",
    "mui/form/_FormBase",
    "dojo/query", "dojo/dom",
    "dojo/dom-class",
    "dojo/dom-attr",
    "dojo/dom-style",
    "dojo/on",
    "dojo/_base/lang",
    "dojo/topic",
    "mui/i18n/i18n!sys-xform-base",
    "mui/dialog/Dialog",
    "sys/xform/mobile/controls/xformUtil"
], function (array, declare, domConstruct, domProp, util, registry,
             _FormBase, query, dom, domClass, domAttr, domStyle, on, lang, topic, msg, Dialog, xformUtil) {

    var claz = declare("sys.xform.mobile.controls.MobileDetailSummaryControl", [_FormBase], {
        buildRendering: function () {
            this.inherited(arguments);
        },
        startup: function () {
            this.inherited(arguments);
            if (this.showStatus === "edit") {
                this.buildCalcParam();
                this.buildEdit();
                if ("false"===this.calcParam.autoCalc)
                    return;
                this.subscribe("/mui/form/valueChanged", "listenDetailChange");
            } else {
                this.buildView()
            }
        },
        buildView: function () {
            var val = xformUtil.getXformWidgetBlur(this.domNode, xformUtil.parseXformName(this)).value;
            if (!val)
                val = 0;
            var exampleHtml = '<div style=float:right;line-height: 3rem;">' + val + '</div>'
            var element = domConstruct.toDom(exampleHtml);
            domConstruct.place(element, this.domNode, "last");
        },
        buildEdit: function () {
            var val = xformUtil.getXformWidgetBlur(this.domNode, xformUtil.parseXformName(this)).value;
            if (!val)
                val = 0;
            this.textContent = domConstruct.create("span", {
                className: "mui-detail-summary-value",
                style: {
                    "line-height": "1.8rem",
                    "padding-right": "24px"
                },
                innerHTML: val
            }, this.valueNode);
            domAttr.set( query("[name='"+this.name+"']")[0],"value",val);
           this.calcNode = domConstruct.create("span", {
                className: "mui-detail-summary-button detailTableNormal",
                innerHTML: "计算"
            }, this.valueNode);
            this.connect(this.calcNode, "click", this.doCalcDetailSummary);
        },
        /**
         * 计算
         * @param eventType all、null|全量计算，none|不计算，
         * detailsTable-delRow|判断clacContext.detail是否需要关联，并进行计算
         * change|判断 field 并进行计算
         * @param clacContext
         */
        doCalcDetailSummary: function (eventType, clacContext) {
            var cp = this.calcParam;
            if (eventType === "none"
                || (eventType === "change" && cp.calcField.indexOf(clacContext.field) > -1)
                || (eventType === "detailsTable-delRow" && cp.detailPointer.indexOf(clacContext.detail) > -1)) {
                return;
            }
            var cf = this.calcFunc;
            var selfVal = cf.selfVal();
            var calcFieldValues = cf.calcFieldValues(selfVal);
            var calcVal = cf.calc(calcFieldValues);
            calcVal = cf.setValText(calcVal);
            query(this.textContent).html(calcVal);
            domAttr.set( query("[name='"+this.name+"']")[0],"value",calcVal);
        },
        buildCalcParam: function () {
            var self = this;
            var other = JSON.parse(this.other.replace(/'/g, "\""));
            var fieldCalc = JSON.parse(this.fieldCalc.replace(/'/g, "\""));
            var relation = JSON.parse(this.relation.replace(/'/g, "\""));
            this.calcParam = {
                autoCalc: other.autoCalc,
                scale: other.scale,
                thousandShow: other.thousandShow,
                detailPointer: self.detailPointer,
                calcField: fieldCalc.field,
                pointerField: relation.pointer
            };
            this.calcFunc = {
                calc: function (array, scale) {
                    scale = scale ? scale : other.scale ? other.scale : 0;
                    return self.DetailSummaryCalcFun[fieldCalc.calc](array, scale);
                },
                setValText: function (val, ts,scale) {
                    if (!ts)
                        ts = other.thousandShow;
                    scale = scale ? scale : other.scale ? other.scale : 0;
                    val = parseFloat(val).toFixed(scale)
                    if ("true" === ts) {
                        val = val.replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
                        //上面的正则表达式会给小数位后面也加上千分位,所以还要把小数点后面的,给替换掉
                        var index = val.indexOf(".");
                        if (index > 0) {
                            var substring = val.substring(index);
                            substring = substring.replace(/,/g, "");
                            val = val.substring(0, index) + substring;
                        }
                    }
                    return val;
                },
                selfVal: function () {
                    //获取控件所在行
                    var nodes = query(self.domNode).parents("tr[kmss_iscontentrow='1']");
                    if (nodes.length > 0) {
                        var areaDom = nodes[0];
                        if(relation.self){
                        	return xformUtil.getXformWidgetBlur(areaDom, relation.self).value;
                        }
                        return null;
                    }
                    return null;
                },
                calcFieldValues: function (selfVal) {
                    if (!selfVal)
                        return [0];
                    var tableId = "#TABLE_DL_" + self.detailPointer;
                    var calcFieldVals = [];
                    query(tableId + " tr[kmss_iscontentrow='1'] ").forEach(function (node, index, nodelist) {
                        var pointerVal = xformUtil.getXformWidgetBlur(node, relation.pointer).value;
                        if (pointerVal === selfVal) {
                            var calcVal = xformUtil.getXformWidgetBlur(node, fieldCalc.field).value;
                            calcVal = parseFloat(calcVal);
                            if (!isNaN(calcVal)) {
                                calcFieldVals.push(calcVal);
                            }
                        }
                    });
                    return calcFieldVals;
                }
            }
        },
        //监听
        listenDetailChange: function (srcObj, arguContext) {
            var eventType = "all";
            var clacContext = {
                "field": "",
                "detail": ""
            };
            if (srcObj) {
                //监听表单事件变更
                eventType = "change";
                clacContext.field = srcObj.name;
            } else if (arguContext.eventType) {
                if (arguContext.eventType === "detailsTable-addRow") {
                    //新增行不进行计算
                    eventType = "none";
                } else {
                    //删除行进行计算
                    eventType = arguContext.eventType;
                    clacContext.detail = arguContext.tableId;
                }
            }
            this.doCalcDetailSummary(eventType, clacContext);
            if(srcObj != this && arguContext && typeof arguContext.oldValue != "undefined" && typeof arguContext.value != "undefined" && arguContext.oldValue != arguContext.value){
                topic.publish("/mui/form/rowValueChanged", this, arguContext);
            }
        },
        DetailSummaryCalcFun: {
            "sum": function (arr, scale) {
                var r = 0;
                scale = scale > 0 ? scale : 0;
                var pow = Math.pow(10, scale);
                for (var i = 0; i < arr.length; i++) {
                    r += arr[i] * pow;
                }
                return r / pow;
            },
            "max": function (arr, scale) {
                var r = arr[0];
                for (var i = 0; i < arr.length; i++) {
                    r = arr[i] > r ? arr[i] : r
                }
                return r;
            },
            "min": function (arr, scale) {
                var r = arr[0];
                for (var i = 0; i < arr.length; i++) {
                    r = arr[i] < r ? arr[i] : r
                }
                return r;
            },
            "avg": function (arr, scale) {
                var r = 0;
                var scaleResult = scale > 0 ? scale : 0;
                scale = scale > 0 ? scale : 1;
                for (var i = 0; i < arr.length; i++) {
                    r += arr[i]*scale;
                }
                if(self.otherCfg){
                    scale = self.otherCfg.scale > 0 ? self.otherCfg.scale : 1;
                    scaleResult =  self.otherCfg.scale > 0 ? self.otherCfg.scale : 0;
                }
                //被除数不能为零
                if((arr.length*scale) == 0){
                    r = r / 1 ;
                }else{
                    r =  r / (arr.length*scale);
                }
                return parseFloat(r).toFixed(scaleResult);
            }
        }
    });
    
    return claz;
});