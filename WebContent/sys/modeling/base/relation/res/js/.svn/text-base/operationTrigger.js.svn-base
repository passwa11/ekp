/**
 * 排序生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var modelingLang = require("lang!sys-modeling-base");
    var OperationTrigger = base.Component.extend({
            _incType: {
                "1": {
                    "name": "固定值"
                },
                "2": {
                    "name": "入参"
                },
                "4": {
                    "name": "公式定义"
                },
                "5": {
                    "name": "用户输入"
                }
            },
            //lines,
            initProps: function ($super, cfg) {
                $super(cfg);
                this.pcfg = cfg.pcfg;
                //初始化容器
                this.key = "fd_trigger_inc";
                this.element = cfg.container;
                this.valElement = cfg.container.find("[mdlng-prtn-data-group=\"fdTrigger\"]");
                this.showElement = cfg.container.find("[mdlng-prtn-prprty-value=\"fdTrigger\"]");
                //初始化参数
                this.prop = {
                    "fdScenesId": this.valElement.find("[name='fdScenesId']").val(),
                    "fdBehaviorId": this.valElement.find("[name='fdBehaviorId']").val(),
                    "fdScenesName": this.valElement.find("[name='fdScenesName']").val(),
                    "fdBehaviorName": this.valElement.find("[name='fdBehaviorName']").val(),
                    "fdTriggerType": this.valElement.find("[name='fdTriggerType']").val(),
                    "fdInParam": this.valElement.find("[name='triggerInc']").val()
                };
                this.incParamMap = {};
                //其他
                this.bindEvent();
            },
            bindEvent: function () {
                var self = this;
                var $ele = this.showElement;
                $ele.find(".model-mask-panel-table-option div").on("click", function () {
                    var val = $(this).attr("option-value");
                    if (self.prop.fdTriggerType === val) {
                        return;
                    }
                    self.prop.fdTriggerType = val;
                    self.valElement.find("[name='fdTriggerType']").val(val)
                    var $dialog = $ele.find("[mdlng-prtn-prprty-type=\"dialog\"]");
                    $dialog.empty();
                    if (val) {
                        //非空显示
                        $dialog.show();
                    } else {
                        $dialog.hide();
                        var $inc = self.showElement.find("[mdlng-prtn-prprty-value=\"fdTriggerInc\"]");
                        var $incTb = $inc.find("tbody");
                        $incTb.empty();
                        $inc.hide();
                    }
                });
                $ele.find("[mdlng-prtn-prprty-type=\"dialog\"]").each(function (idx, dom) {
                    $(dom).on("click", function () {
                        var $e = $(this);
                        self.onDialog($e)
                    })
                });
            },
            onDialog: function () {
                var self = this;
                var type = self.prop.fdTriggerType;
                if (type === "0" || type === "1") {
                    dialog.iframe("/sys/modeling/base/relation/import/operation_trigger_select.jsp?type=" + type + "&modelId=" + self.pcfg.modelMainId
                        , modelingLang['operation.trigger.selection'],
                        function (value) {
                            if (value) {
                                self.setTrigger(value);
                            }
                        }, {
                            width: 1010,
                            height: 600
                        });
                }
            },
            getTrigger: function () {
                var type = this.prop.fdTriggerType;
                if (type) {
                    if (type === "0") {
                        return {
                            fdName: this.prop.fdScenesName,
                            fdId: this.prop.fdScenesId
                        }
                    } else {
                        return {
                            fdName: this.prop.fdBehaviorName,
                            fdId: this.prop.fdBehaviorId
                        }
                    }
                }
            },
            setTrigger: function (val) {
                var type = this.prop.fdTriggerType;
                if (type) {
                    var $valEle = this.valElement;
                    if (type === "0") {
                        this.prop.fdScenesName = val.fdName;
                        this.prop.fdScenesId = val.fdId;
                        $valEle.find("[name='fdScenesId']").val(val.fdId);
                        $valEle.find("[name='fdBehaviorId']").val("");
                    } else {
                        this.prop.fdBehaviorName = val.fdName;
                        this.prop.fdBehaviorId = val.fdId;
                        $valEle.find("[name='fdBehaviorId']").val(val.fdId);
                        $valEle.find("[name='fdScenesId']").val("");
                    }
                    var $ele = this.showElement.find("[mdlng-prtn-prprty-type=\"dialog\"]");
                    var $p = $("<p></p>");
                    $p.append(val.fdName);
                    $ele.html($p);
                    this.getIncParam(type, val.fdId);
                }
            },
            getIncParam: function (type, id, oldData) {
                var self = this;
                var action = type === "0" ? "sysModelingScenes.do" : "sysModelingBehavior.do"
                var url = Com_Parameter.ContextPath + "sys/modeling/base/" + action
                    + "?method=getInput&fdId=" + id;
                $.ajax({
                    url: url,
                    method: 'GET',
                    async: false
                }).success(function (resultStr) {
                    var result = JSON.parse(resultStr);
                    var data = result.data;
                    self.incParamMap[id] = data;
                    self.drawInc(data, oldData);
                });


            },
            getIncId: function (triggerId, data) {
                return triggerId + data.type + data.detailId + data.widgetId;
            },
            drawInc: function (store, oldData) {
                var self = this;
                var $ele = self.showElement.find("[mdlng-prtn-prprty-value=\"fdTriggerInc\"]");
                var $tb = $ele.find("tbody");
                $tb.empty();
                $ele.hide();

                $.each(store, function (triggerId, item) {
                    for (var i in item.data) {
                        $ele.show();
                        //绘制列表
                        var data = item.data[i];
                        var td1 = data.triggerName;
                        if (data.detailName) {
                            td1 += "[" + data.detailName + "]";
                        }
                        var td2 = "触发前提";
                        if(data.type === "target"){
                            td2 = "目标";
                        }else if (data.type === "where" ){
                            td2 = "查询";
                        }else if (data.type === "preModelWhere" ){
                            td2 = "前置表单查询";
                        }else if (data.type === "preQueryWhere"){
                            td2 = "前置查询";
                        }else if (data.type === "detailQueryWhere"){
                            td2 = "明细表查询";
                        }
                        var td3 = "[" + self._incType[data.triggerInputType].name + "]" + data.widgetName;
                        var $eleTr = $("<tr class='inputParam'/>");

                        $eleTr.append("<td>" + td1 + "</td>");
                        $eleTr.append("<td>" + td2 + "</td>");
                        $eleTr.append("<td>" + td3 + "</td>");
                        //触发id+类型+明细表Id+控件Id
                        var incId = self.getIncId(triggerId, data);
                        // 值td
                        var $formulaTd = $("<td />");
                        var dataType = data.widgetType;
                        //明细表类型变数组
                        if (data.widgetId.indexOf(".") > 0) {
                            dataType += "[]";
                        }
                        //兼容代码模式的入参
                        if("sql" == dataType){
                            dataType = 'String';
                        }
                        $formulaTd.append(formulaBuilder.get(incId, dataType, {
                            "sourceFormList": self.pcfg.sourceData,
                            supportDetailAtt: true
                        }));
                        $eleTr.append($formulaTd);
                        $eleTr.attr("id", incId);
                        $tb.append($eleTr);
                    }
                });
                //init
                if (oldData) {
                    $.each(oldData, function (idx, item) {
                        var ele = item.ele;
                        //组件id+触发id+控件Id
                        var incId = self.getIncId(ele.triggerId, ele);
                        var exp = item.expression;
                        if (exp) {
                            self.showElement.find("[name='" + incId + "']").val(exp.value);
                            self.showElement.find("[name='" + incId + "_name']").val(exp.text);
                        }
                    });
                }

            },
            getTriggerInc: function () {
                var self = this;
                var trigger = self.getTrigger();
                var store = null;
                if (trigger && trigger.fdId) {
                    store = self.incParamMap[trigger.fdId];
                }
                if (!store) {
                    return [];
                }
                var triggerInc = [];
                var idx = 0;
                $.each(store, function (triggerId, item) {
                    for (var i in item.data) {
                        var data = item.data[i];
                        var ele = data;
                        ele.triggerId = triggerId;
                        //组件id+触发id+控件Id
                        var incId = self.getIncId(triggerId, data);
                        var expression = {};
                        expression.value = self.element.find("[name='" + incId + "']").val();
                        expression.text = self.element.find("[name='" + incId + "_name']").val();
                        var triggerIncItem = {
                            idx: idx,
                            ele: ele,
                            expression: expression
                        };
                        triggerInc.push(triggerIncItem);
                        idx++
                    }
                });
                return triggerInc;
            },
            destroy: function ($super, cfg) {

            },
            getKeyData: function () {

            },

            initByStoreData: function () {
                var $ele = this.showElement
                var p = this.prop;
                //type
                var $typeOperation = $ele.find("[option-value=\"" + p.fdTriggerType + "\"]");
                var $select = $typeOperation.parent().parent();
                var $p = $select.find(".model-mask-panel-table-select-val");
                var val = $typeOperation.attr("option-value");
                if (val) {
                    $p.attr("table-select-val", val);
                    $p.html($typeOperation.html());
                    var $dialog = $ele.find("[mdlng-prtn-prprty-type=\"dialog\"]");
                    $dialog.show();
                    var trigger = this.getTrigger();
                    var $p = $("<p></p>");
                    $p.append(trigger.fdName);
                    $dialog.html($p);
                    var triggerInc = null;
                    try {
                        var triggerInParam = JSON.parse(this.prop.fdInParam);
                        triggerInc = triggerInParam.trigger;
                    } catch (e) {
                        console.warn("触发格式解析出错")
                    }
                    this.getIncParam(p.fdTriggerType, trigger.fdId, triggerInc);
                }
            },

        })
    ;

    exports.OperationTrigger = OperationTrigger;
})
;
