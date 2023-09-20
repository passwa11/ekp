/**
 * 资源面板类
 */

define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var dialog = require('lui/dialog');
    var topic = require("lui/topic");
    //公式定义器
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var resPanelBase = require("sys/modeling/base/views/business/res/resPanelBase");
    var colorRecordGenerator = require("sys/modeling/base/views/business/res/colorRecordGenerator");
    var ResPanelSource = resPanelBase.ResPanelBase.extend({

        /**
         * 初始化
         * cfg应包含参考引用方
         */
        initProps: function ($super, cfg) {
            this.cst = {};
            this.cst.rpClickFun = {
                "modelTarget": "__modelTargetDialogClick",
                "sourceWhere": "__whereItemAddClickSource",
                "sourceColor": "__colorItemAddClickSource",
            };
            $super(cfg);
            var self = this;
            topic.channel("modelingResourcePanel").subscribe("category.change", function (data) {
                console.log("topic.subscribe._categoryChange");
                self._categoryChange(self, data)
            });

        },
        /**
         * 实际绘制显示设置区域
         *
         */
        build: function () {
            this.$sd = $("#resPanelSourceDom");

            if (!this.sdHtml) {
                this.sdHtml = this.$sd.html();
            }
            this.$sd.html("");

        },
        _categoryChange: function (self, data) {
            if (self.element && self.cst.cfg && self.cst.cfg.targetWidgets) {
                var rowType, colType;
                try {
                    rowType = self.parent.resPanelTable.__getRowAndColType("row");
                    colType = self.parent.resPanelTable.__getRowAndColType("col");
                } catch (e) {
                    console.warn("未获取到面板行列字段属性", e)
                }
                self.drawFiledSelect(self.element.find("#_resPanel_source_matchRow"),
                    self.cst.cfg.targetWidgets, rowType);
                self.drawFiledSelect(self.element.find("#_resPanel_source_matchCol"),
                    self.cst.cfg.targetWidgets, colType);
                return;
            } else {
                console.warn("面板内容未初始化，等待")
                // setTimeout(function(){
                //     self._categoryChange(self, data) },1500);

            }
        },
        reBuild: function (data) {
            var self = this;
            self.whereWgtCollection = [];
            self.colorWgtCollection = [];
            self.$sd.html("");
            var $sdTemp = $(self.sdHtml);
            //数据初始化
            self.cst.cfg = {
                modelTargetId: data.modelId,
                targetWidgets: data.data.data,
                targetXformId: data.data.xformId
            };
            self.fdEnableFlow = undefined;
            if (data.data.flowInfo) {
                self.fdEnableFlow = data.data.flowInfo.fdEnableFlow;
            }

            //匹配字段渲染
            var rowType, colType;
            try {
                rowType = this.parent.resPanelTable.__getRowAndColType("row");
                colType = this.parent.resPanelTable.__getRowAndColType("col");
            } catch (e) {
                console.warn("未获取到面板行列字段属性", e)
            }
            this.drawFiledSelect($sdTemp.find("#_resPanel_source_matchRow"),
                self.cst.cfg.targetWidgets, rowType);
            this.drawFiledSelect($sdTemp.find("#_resPanel_source_matchCol"),
                self.cst.cfg.targetWidgets, colType);

            //显示行
            var targetFormulaField = self._getTargetFormulaField(self.cst.cfg.targetXformId);
            var sourceShow = formulaBuilder.get_style2("sourceShow", "String", null, null, function () {
                return targetFormulaField;
            });
            $sdTemp.find("#_resPanel_source_show").append(sourceShow);
            //详情展示
            this.drawSourceDialogTable($sdTemp.find("#source_dialog_table"), targetFormulaField);
            //视图
            this.drawSourceThrough($sdTemp.find("#_resPanel_source_through"), data.modelId);
            //颜色

            self.$sd.append($sdTemp);
            this.element = self.$sd;
            if (this.initByStoreData_data) {
                this.initByStoreData(this.initByStoreData_data, "true");
            }
            this.parent.loading.hide();
        },
        //------穿透
        drawSourceThrough: function ($ele, modelId) {
            var self = this;
            var $view = $ele.find("#source_through_view");
            this._buildViewFlagRadio($ele, function (t, v, ov) {
                $view.html("");
                if (v === "1") {
                    self.drawSourceThroughViewSelect(modelId, $view);
                }
            })
        },
        drawSourceThroughViewSelect: function (modelId, $view) {
            var self = this;
            if (!self.viewList) {
                self.viewList = {}
            }

            if (!self.viewList[modelId]) {
                var self = this;
                var url = Com_Parameter.ContextPath + "sys/modeling/base/pcAndMobileView.do?method=findAllByAjax&fdModelId=" + modelId;
                $.ajax({
                    url: url,
                    type: "get",
                    async: false,
                    success: function (data, status) {
                        if (data) {
                            self.viewList[modelId] = JSON.parse(data);
                            self.drawSourceThroughViewSelect(modelId, $view);
                        }
                    }
                });
            } else {
                var $select = $("<select  class=\"inputsgl\" name='sourceView' value='def'  style=\"margin:0 4px;width:45%\"></selct>");
                var list = self.viewList[modelId].list;
                console.log("viewlist", list)
                $select.append("<option value='def' >默认视图</option>")
                if (list) {
                    for (var i = 0; i < list.length; i++) {
                        var o = "<option value='" + list[i].id + "' mark-pam-type='" + list[i].type + "'>" + list[i].name + "</option>";
                        $select.append(o);
                    }
                }
                //initByStoreData-初始化视图配置
                if (self.initByStoreData_data_view) {
                    $select.find("option[value='" + self.initByStoreData_data_view.id + "']").attr("selected", "selected")
                    self.initByStoreData_data_view = undefined;
                } else {
                    $select.find("option[value='def']").attr("selected", "selected")
                }
                $view.append($select);
            }
        },
        //------穿透 结束

        drawSourceDialogTable: function ($ele, targetFormulaField) {
            $ele.find(".source_dialog_table_item").each(function (i, item) {
                var pTitle = "source_dialog_" + $(item).attr("source_dialog_table");
                var pFormulaInput = formulaBuilder.get_style2(pTitle, "String", null, null, function () {
                    return targetFormulaField;
                });
                $(item).html("");
                $(item).append(pFormulaInput);
            })
        },
        drawFiledSelect: function ($ele, ws, fieldType) {
            if (!fieldType) {
                fieldType = "String";
            }
            var dateString = ["Date", "DateTime", "Time", "dateTime"]
            if (dateString.indexOf(fieldType) > -1) {
                fieldType = "dateTime"
            }
            $ele.html("");
            var $fieldSelect = $("<select  class=\"inputsgl fieldSelect\"  style=\"margin:0 4px;width:27%\"></selct>");
            if (ws) {
                for (var controlId in ws) {
                    var info = ws[controlId];
                    if (info.type === "Attachment" || info.name.indexOf(".") > 0)
                        continue;
                    if (info.name === "fdId")
                        info.label = "ID";
                    if (fieldType) {
                        if ("dateTime" === fieldType) {
                            if (dateString.indexOf(info.type) > -1 ) {
                                var option = "<option value='" + info.name + "' data-property-type='" + info.type + "'>" + info.label + "</option>";
                                $fieldSelect.append(option);
                            }
                        } else {
                            if (info.type != fieldType) {
                                continue;
                            }
                            var option = "<option value='" + info.name + "' data-property-type='" + info.type + "'>" + info.label + "</option>";
                            $fieldSelect.append(option);
                        }
                    }
                }
            }
            $ele.append($fieldSelect);
            if ("dateTime" === fieldType) {
                var $fieldSelect_sub = $("<select  class=\"inputsgl fieldSelectSub\"  style=\"margin:0 4px;width:27%\"></selct>");
                if (ws) {
                    for (var controlId in ws) {
                    	var info = ws[controlId];
                    	if (info.name.indexOf(".") > 0){
                    		continue;
                    	}
                    	  if (info.name === "fdId")
                              info.label = "ID";
                    	  
                        if (dateString.indexOf(info.type) > -1) {
                            var option = "<option value='" + info.name + "' data-property-type='" + info.type + "'>" + info.label + "</option>";
                            $fieldSelect_sub.append(option);
                        }
                    }
                }
                $ele.append($fieldSelect_sub);
            }

        },


        _getTargetFormulaField: function (xformId) {
            var fl = formulaBuilder.XForm_getXFormDesignerObj(xformId);
            var ff = [];
            for (var i = 0; i < fl.length; i++) {
                var item = fl[i];
                if (item["controlType"] && item["controlType"] === "detailsTable")
                    continue
                if (item["name"] && item["name"].indexOf(".") > 0)
                    continue;
                if (item["type"]) {
                    if ("RTF" == item["type"] || "Attachment" == item["type"] || "Attachment[]" == item["type"]) {
                        continue
                    }
                }
                ff.push(item);
            }
            return ff;
        },
        __modelTargetDialogClick: function (thisObj) {
            var self = this;
            var appId = "";
            if (this.parent.flowInfo && this.parent.flowInfo.appId) {
                appId = this.parent.flowInfo.appId;
            }
            dialog.iframe("/sys/modeling/base/relation/import/model_select.jsp?appId=" + appId, "选择表单",
                function (value) {
                    if (value) {
                        self.parent.loading = dialog.loading();
                        var span = value.fdName;
                        $(thisObj).find("[data-lui-mark='dialogText']").val($(span).text());
                        $(thisObj).find("input[type='hidden']").val(value.fdId);
                        self.targetModel = {
                            id: value.fdId,
                            name: $(span).text()
                        };
                        self._modelTargetInit(value.fdId)
                    }
                }, {
                    width: 1010,
                    height: 600
                });

        },
        _modelTargetInit: function (modelId) {
            var self = this;
            var url = Com_Parameter.ContextPath + "sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + modelId;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                success: function (data, status) {
                    if (data) {
                        self.reBuild({
                            "data": JSON.parse(data),
                            "modelId": modelId
                        })
                    }
                }
            });
        },
        __whereItemAddClickSource: function (thisObj) {
            var self = this;
            var whereCfg = {
                parent: self,
                widgets: self.cst.cfg.targetWidgets,
                formulaWidgets: self.widgets,
                fdEnableFlow: self.fdEnableFlow
            };
            this._whereItemAddClick(thisObj, whereCfg);
        },
        __colorItemAddClickSource: function (thisObj) {
            var self = this;
            var colorCfg = {
                parent: self,
                widgets: self.cst.cfg.targetWidgets,
                formulaWidgets: self.widgets
            };
            var tabId = $(thisObj).attr("data-lui-mark");
            var $colorTable = $("#" + tabId);
            var colorWgt = new colorRecordGenerator.ColorRecordGenerator(colorCfg);
            colorWgt.draw($colorTable);
        },
        getKeyData: function () {
            var self = this;
            var source = {};
            source.model = self.targetModel;
            source.where = self.getWhereKeyData();

            source.matchRow = {
                field: self.element.find("#_resPanel_source_matchRow").find(".fieldSelect").find("option:selected").val(),
                fieldSub: self.element.find("#_resPanel_source_matchRow").find(".fieldSelectSub").find("option:selected").val()
            };
            source.matchCol = {
                field: self.element.find("#_resPanel_source_matchCol").find(".fieldSelect").find("option:selected").val(),
                fieldSub: self.element.find("#_resPanel_source_matchCol").find(".fieldSelectSub").find("option:selected").val()
            };
            source.show = {
                text: self.element.find("[name='sourceShow_name']").val(),
                value: self.element.find("[name='sourceShow']").val()
            };
            //
            source.through = {};
            source.through.isThrough = self.element.find("#_resPanel_source_through").find(".view_flag_radio").attr("view-flag-radio-value");
            if (source.through.isThrough === "1") {
                source.through.view = {};
                var $th =  self.element.find("#source_through_view").find("option:selected")
                source.through.view.id = $th.val();
                source.through.view.name = $th.text();
                source.through.view.type = $th.attr("mark-pam-type");
                source.through.view.default = "0";
                if (source.through.view.id === "def") {
                    source.through.view.default = "1";
                }
            } else {
                source.through.view = {}
            }

            //
            var dialogTitle = ["title", "time", "person", "content"];
            source.dialog = {};
            var dialogData = {};
            for (var i = 0; i < dialogTitle.length; i++) {
                var item = dialogTitle[i];
                dialogData[item] = {
                    text: self.element.find("[name='source_dialog_" + item + "_name']").val(),
                    value: self.element.find("[name='source_dialog_" + item + "']").val()
                }
            }
            source.dialog.data = dialogData;
            //
            source.color = self.getColorKeyData();
            console.debug("getKeyData - source::", source);
            return source;
        },
        initByStoreData: function (data, initModelTarget) {
            console.debug("initByStoreData - source::", data, initModelTarget);
            var self = this;
            if (data) {
                if (initModelTarget) {
                    var whereCfg = {
                        parent: self,
                        widgets: self.cst.cfg.targetWidgets,
                        formulaWidgets: self.widgets
                    };
                    self._initWgtCollection(data.where, "where", whereCfg, $("#source_whereTable"));
                    self.element.find("#_resPanel_source_matchRow").find(".fieldSelect").find("option[value='" + data.matchRow.field + "']").attr("selected", "selected");
                    if (data.matchRow.fieldSub) {
                        self.element.find("#_resPanel_source_matchRow").find(".fieldSelectSub").find("option[value='" + data.matchRow.fieldSub + "']").attr("selected", "selected");
                    }

                    self.element.find("#_resPanel_source_matchCol").find(".fieldSelect").find("option[value='" + data.matchCol.field + "']").attr("selected", "selected");
                    if (data.matchCol.fieldSub) {
                        self.element.find("#_resPanel_source_matchCol").find(".fieldSelectSub").find("option[value='" + data.matchCol.fieldSub + "']").attr("selected", "selected");
                    }

                    self.element.find("[name='sourceShow_name']").val(data.show.text);
                    self.element.find("[name='sourceShow']").val(data.show.value);
                    //特殊处理视图穿透参数
                    this.initByStoreData_data_view = data.through.view;
                    self.element.find("#through_isThrough").find(".view_flag_radio_item[view-flag-radio-value='" + data.through.isThrough + "']").trigger($.Event("click"));
                    var dialogTitle = ["title", "time", "person", "content"];
                    for (var i = 0; i < dialogTitle.length; i++) {
                        var item = dialogTitle[i];
                        if (data.dialog.data[item]) {
                            self.element.find("[name='source_dialog_" + item + "_name']").val(data.dialog.data[item].text);
                            self.element.find("[name='source_dialog_" + item + "']").val(data.dialog.data[item].value);
                        }
                    }
                    var colorCfg = {
                        parent: self,
                        widgets: self.cst.cfg.targetWidgets,
                        formulaWidgets: self.widgets
                    };
                    self._initWgtCollection(data.color, "color", colorCfg, $("#source_colorTable"));
                    this.initByStoreData_data = undefined;
                } else {
                    self.targetModel = data.model;
                    $("#resPanelSourceDom_model").find("[data-lui-mark='dialogText']").val(data.model.name);
                    $("#resPanelSourceDom_model").find("input[type='hidden']").val(data.model.id);
                    this.initByStoreData_data = data;
                    this._modelTargetInit(data.model.id)
                }
            }
        }
    });

    exports.ResPanelSource = ResPanelSource;
});
