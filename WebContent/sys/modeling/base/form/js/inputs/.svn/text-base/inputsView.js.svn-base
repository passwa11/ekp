/**
 * 入参
 *
 */

define(function (require, exports, module) {
    require("sys/modeling/base/form/js/inputs/inputs.css");
    require("resource/js/formula.js");
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var env = require("lui/util/env");
    var topic = require("lui/topic");
    var dialog = require('lui/dialog');

    var InputsView = base.DataView.extend({

        setSourceParams: function (key, value) {
            this.source.vars[key] = value;
        },

        doRefresh: function () {
            this.element.append(this.loading);
            this.source.url = this.source._url = this.config.requestUrl;
            this.source.resolveUrl();
            this.load();
        },
        // #125221 流程事件配置触发支持明细表附件
        //缓存
        _modelingFormFieldList: {},
        // 获取
        _modelingGetFormFieldList: function (flowChartObject) {
            var self = this;
            var fieldList = flowChartObject.FormFieldList;
            if (flowChartObject && flowChartObject.FdAppModelId) {
                if (self._modelingFormFieldList[flowChartObject.FdAppModelId]) {
                    return
                }
                $.ajax({
                    url: Com_Parameter.ContextPath + "sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + flowChartObject.FdAppModelId,
                    type: "GET",
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        if (result.success) {
                            var sourceFormList = result.data;
                            for (var key in sourceFormList) {
                                if (sourceFormList[key].type == "Attachment" && key.indexOf(".") > -1) {
                                    var item = [];
                                    item["name"] = sourceFormList[key].name
                                    item["label"] = sourceFormList[key].label
                                    item["businessType"] = sourceFormList[key].businessType
                                    item["type"] = sourceFormList[key].type;
                                    fieldList.push(item)
                                }
                            }
                            self._modelingFormFieldList[flowChartObject.FdAppModelId] = fieldList;
                        }
                    }
                });
            }

        },
        doRender: function ($super, cfg) {
            $super(cfg);
            var self = this;

            // 根据配置隐藏标题
            if (self.config.isRenderArray === "false") {
                self.element.find(".input_title").hide();
            }

            // 初始化style的值（input_style）
            /**
             *    {
					"记录id" : {
						"where" : {
							"fd_37d6f2c3aa0b7e":{
								"style":"fix|",
								"value":{
									"val":"实际值",
									"name":"公式定义器的名称",
									"type":"公式定义器的类型"
								}
							}
						},
						"target" : {
						
						}						
					}
				}
             *
             *
             * */
            if (!$.isEmptyObject(self.config.values)) {
                for (var recordId in self.config.values) {
                    var record = self.config.values[recordId];
                    for (var type in record) {
                        var controls = record[type];
                        for (var controlId in controls) {
                            var $tr = $(self.element).find("tr[data-record-id='" + recordId + "'][data-record-type='" + type + "'][data-record-wgtid='" + controlId + "']");
                            $tr.find(".input_style").val(controls[controlId]["style"]);
                        }
                    }
                }
            }

            // 给下拉框添加事件
            var $selectArr = self.element.find(".input_style");
            $selectArr.on("change", function (e) {
                var $tr = $(this).closest("tr");
                var valueName = self.getValueName($tr.attr("data-record-id"), $tr.attr("data-record-type"), $tr.attr("data-record-wgtid"));
                var valueType = self.getValueName($tr.attr("data-record-id"), $tr.attr("data-record-type"), $tr.attr("data-record-wgttype"));
                var $value = $tr.find(".input_value");
                if (this.value === "formula") {
                    $value.html(self.buildFormula(valueName, valueType, $tr));
                } else if (this.value === "fix") {
                    $value.html("<input type='text' name='" + valueName + "' class='inputsgl' style='width:150px'/>");
                }
            });

            // 脚本触发下拉框以更新值dom结构
            $selectArr.trigger($.Event("change"));

            // 初始化value的值（input_value）
            if (!$.isEmptyObject(self.config.values)) {
                for (var recordId in self.config.values) {
                    var record = self.config.values[recordId];
                    for (var type in record) {
                        var controls = record[type];
                        for (var controlId in controls) {
                            var $tr = $(self.element).find("tr[data-record-id='" + recordId + "'][data-record-type='" + type + "'][data-record-wgtid='" + controlId + "']");
                            var value = controls[controlId]["value"];
                            var valueName = this.getValueName(recordId, type, controlId);
                            $tr.find("input[name='" + valueName + "']").val(value.val);
                            var $formulaName = $tr.find("input[name='" + valueName + "_name']");
                            if ($formulaName.length > 0) {
                                $formulaName.val(value.name);
                            }
                        }
                    }
                }
            }
        },

        load: function () {
            this.source.get();
        },

        // 构建公式定义器
        buildFormula: function (name, valueType, $tr) {
            var html = $("<div />");
            var self = this;
            html.append("<input type='hidden' name='" + name + "' />");
            html.append("<input type='text' name='" + name + "_name' class='inputsgl' style='width:150px' readonly/>");
            var $formula = $("<span><a href='javascrip:void(0);'>选择</a></span>");
            var type = $tr.find("input[name='valueType']").val();
            this._modelingGetFormFieldList(parent.FlowChartObject);
            $formula.on("click", function (e) {
                if (valueType.indexOf("Attachment") > -1) {
                    //附件
                    self.buildFormulaAttachmentDialog(name, name + "_name", parent.FlowChartObject)
                } else {
                    //兼容代码模式入参
                    if("sql" == type){
                        type = "String";
                    }
                    Formula_Dialog(name, name + "_name", parent.FlowChartObject.FormFieldList, type, self.formulaReturn,
                        "com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction", parent.FlowChartObject.ModelName);

                }
            });

            html.append($formula);
            return html;
        },

        formulaReturn: function () {

        },
        //构建附件选择框，中转一下方便后续修改
        buildFormulaAttachmentDialog: function (idField, nameField, flowChartObject) {
            var modelId;
            if (flowChartObject && flowChartObject.FdAppModelId) {
                modelId = flowChartObject.FdAppModelId
            }
            if (modelId) {
                var fieldList = this._modelingFormFieldList[modelId];
                if (fieldList) {
                    this.Formula_Attachment_Dialog(idField, nameField, fieldList);
                }
            }
            this.Formula_Attachment_Dialog(idField, nameField, flowChartObject.FormFieldList);
        },
        Formula_Attachment_Dialog: function (idField, nameField, fieldList) {
            var data = new KMSSData();
            for (var i = 0; i < fieldList.length; i++) {
                var field = fieldList[i];
                if (!(field.type == "Attachment" || field.type == "Attachment[]")) {
                    continue;
                }
                var pp = {name: field.name, label: field.label};
                if (field.type == "Attachment[]") {
                    pp.name = "$" + field.name + "$";
                    pp.label = "$" + field.label + "$";
                }
                data.AddHashMap({id: pp.name, name: pp.label});
            }
            var dialog = new KMSSDialog(false, true);
            dialog.winTitle = "title";
            dialog.AddDefaultOption(data);
            dialog.BindingField(idField, nameField, ";");
            dialog.Show();
        },
        getKeyData: function () {
            var self = this;
            var sourceData = self.sourceData;
            var keyData = {};
            for (var key in sourceData) {
                keyData[key] = {};
                this.element.find("tr[data-record-id='" + key + "']").each(function (i, dom) {
                    var id = $(dom).attr("data-var-key");
                    var type = $(dom).attr("data-record-type"); //target|where
                    if (!keyData[key].hasOwnProperty(type)) {
                        keyData[key][type] = {};
                    }
                    var wgtId = $(dom).attr("data-record-wgtid");
                    keyData[key][type][wgtId] = {};
                    keyData[key][type][wgtId]["style"] = $(dom).find("select.input_style option:selected").val().trim() || "";
                    keyData[key][type][wgtId]["value"] = {};
                    var valueName = self.getValueName(key, type, wgtId);
                    keyData[key][type][wgtId]["value"].val = $(dom).find("input[name='" + valueName + "']").val() || "";
                    keyData[key][type][wgtId]["value"].type = $(dom).find("input[name='valueType']").val() || "";
                    keyData[key][type][wgtId]["value"].varkey = id;
                    var $formulaName = $(dom).find("input[name='" + valueName + "_name']");
                    if ($formulaName.length > 0) {
                        keyData[key][type][wgtId]["value"].name = $formulaName.val() || "";
                    }
                });
            }
            return keyData;
        },

        getValueName: function (key, type, wgtId) {
            return key + "_" + type + "_" + wgtId;
        },

        // 对数据进行分类，主要区分为where和target
        // records ==> {"记录id":{"name":"xxx","data":[{"jtype":"target|where","name":{"value":"xxxx"...}...}...]}}
        classifyType: function (records) {
            if (records) {
                for (var recordId in records) {
                    var record = records[recordId];
                    if (!record.hasOwnProperty("group")) {
                        record["group"] = {};
                        record["group"]["where"] = [];
                        record["group"]["target"] = [];
                        record["group"]["sqlWhereParams"] = [];
                        record["group"]["preModelWhere"] = [];
                        record["group"]["preQueryWhere"] = [];
                        record["group"]["detailQueryWhere"] = [];
                        record["group"]["other"] = [];
                    }
                    var data = record["data"];
                    if (data) {
                        for (var i = 0; i < data.length; i++) {
                            if (data[i]["type"] === "target") {
                                record["group"]["target"].push(data[i]);
                            } else if (data[i]["type"] === "where") {
                                record["group"]["where"].push(data[i]);
                            } else if (data[i]["type"] === "sqlWhereParams") {
                                record["group"]["sqlWhereParams"].push(data[i]);
                            } else if (data[i]["type"] === "preModelWhere") {
                                record["group"]["preModelWhere"].push(data[i]);
                            }else if (data[i]["type"] === "preQueryWhere") {
                                record["group"]["preQueryWhere"].push(data[i]);
                            }else if (data[i]["type"] === "detailQueryWhere") {
                                record["group"]["detailQueryWhere"].push(data[i]);
                            } else {
                                record["group"]["other"].push(data[i]);
                            }
                        }
                    }
                }
            }
            return records;
        }
    });

    exports.InputsView = InputsView;

})