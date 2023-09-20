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
    var ResPanelTable = resPanelBase.ResPanelBase.extend({

        /**
         * 初始化
         * cfg应包含参考引用方
         */
        initProps: function ($super, cfg) {
            this.cst = {}
            this.cst.categoryFun = {
                "dateTime": "categoryDateTime",
                "field": "categoryField"
            };
            this.cst.rpClickFun = {
                "tableCondition": "_conditionDialogClick",
                "tableWhere": "_whereItemAddClickTable"
            };
            if (cfg.flowInfo) {
                this.fdEnableFlow = cfg.flowInfo.fdEnableFlow;
            }
            $super(cfg);
        },
        /**
         * 实际绘制显示设置区域
         *
         */
        build: function () {
            var self = this;
            //#1  图表标题
            //去除变量
            var tableTitle = formulaBuilder.get_style2("cfgTableTitle", "String", null, null, function () {
                return [];
            });
            self.$c.find("#_resPanel_table_title").append(tableTitle);
            //# 行列改变事件
            self.$c.find("[rp-table-mark=\"category\" ]").on("change", function () {
                self.categoryChange($(this))
            }).trigger($.Event("change"));
            self.element = self.$c;
        },
        /********************
         * 以下为显示设置中标题行/列的一些方法，
         * 可支持自由选择行列匹配类型，
         * 当前需求先固定选择************/
        categoryChange: function ($dom) {
            $dom.next(".category_sub").remove();
            var drawContentId = $dom.attr("rp-table-mark-content");
            var $dc = $("#" + drawContentId);
            $dc.html("");
            var fun = this.cst.categoryFun[$dom.val()];
            topic.channel("modelingResourcePanel").publish("category.change", {
                "$dom": $dom,
                "type": "categoryChange"
            });
            if (fun) {
                $dom.after(this[fun]($dom));
            }

        },
        categoryDateTime: function ($dom) {
            //绑定渲染时间
            var drawContentId = $dom.attr("rp-table-mark-content");
            var $dc = $("#" + drawContentId);
            var tempHtml = this.$tempHtml.find("#categoryTime").html();
            $dc.append(tempHtml);
            this._buildViewFlagRadio($dc)
            return "";
        },
        categoryField: function ($dom) {
            var drawContentId = $dom.attr("rp-table-mark-content");
            var $dc = $("#" + drawContentId);
            var tempHtml = this.$tempHtml.find("#categoryField").html();
            $dc.append(tempHtml);
            //显示值
            $dc.find("[rp-table-mark=\"categoryFieldText\"]").find(".onlineContext").append(formulaBuilder.get_style2(drawContentId + "_text", "String"));
           $dc.find(".modeling_formula").css({"width": "100%"});

            //实际值inputsgl width35Pe
            var $fieldSelect = $("<select  class=\"inputsgl categoryFieldValue\"  style=\"width:100%\"></selct>");
            if (this.widgets) {
                for (var controlId in this.widgets) {
                    var info = this.widgets[controlId];
                    if (info.type === "Attachment" || info.name.indexOf(".") > 0 || info.type.indexOf("[]") > 0) {
                        //明细表,数组，附件不处理
                        continue;
                    }
                    if (info.type.indexOf("SysOrg") > -1) {
                        //组织架构特殊处理
                        var option1 = "<option value='" + info.name + "' data-property-type='" + info.type + "'>" + info.label + ".ID</option>";
                        var option2 = "<option value='" + info.name + "_text' data-property-type='String'>" + info.label + ".名称</option>";
                        $fieldSelect.append(option1);
                        $fieldSelect.append(option2);
                        continue;
                    }
                    if (info.name === "fdId")
                        info.label = "ID";
                    var option = "<option value='" + info.name + "' data-property-type='" + info.type + "'>" + info.label + "</option>";
                    $fieldSelect.append(option);
                }
                $fieldSelect.on("change", function () {
                    console.debug("$fieldSelect change :: modelingResourcePanel.publish")
                    topic.channel("modelingResourcePanel .item-content").publish("category.change", {
                        "$dom": $(this),
                        "type": "fieldChange"
                    });
                });
            }
            $dc.find("[rp-table-mark=\"categoryFieldValue\"]").children(".onlineContext").append($fieldSelect);

            return "";
        },
        getCategoryKeyData: function ($dom) {
            var data = {};
            data.category = $dom.find("[rp-table-mark=\"category\"]").val();
            var drawContentId = $dom.find("[rp-table-mark=\"category\"]").attr("rp-table-mark-content");
            var $dc = $("#" + drawContentId);
            if ("dateTime" === data.category) {
                data.type = data.category;
                data[data.category] = {
                    default: $dc.find("[rp-table-mark=\"default\"]").find(".view_flag_radio").attr("view-flag-radio-value"),
                    section: {
                        start: $dc.find("[rp-table-mark=\"sectionStart\"]").find("option:selected").val(),
                        end: $dc.find("[rp-table-mark=\"sectionEnd\"]").find("option:selected").val()
                    },
                    split: {
                        number: $dc.find("[rp-table-mark=\"splitNumber\"]").val(),
                        unit: $dc.find("[rp-table-mark=\"splitUnit\"]").find("option:selected").val(),
                    }
                };
            } else {
                var $text = $dc.find("[rp-table-mark=\"categoryFieldText\"]");
                var $value = $dc.find("[rp-table-mark=\"categoryFieldValue\"]").find(".categoryFieldValue");
                data[data.category] = {
                    "text": {
                        "text": $text.find("[name='" + drawContentId + "_text_name']").val(),
                        "value": $text.find("[name='" + drawContentId + "_text']").val()
                    },
                    "value": {
                        "text": $value.find("option:selected").html(),
                        "value": $value.find("option:selected").val()
                    }
                };
                data.type = $value.find("option:selected").attr("data-property-type");
            }
            return data;
        },
        initCategoryStoreData: function ($dom, data) {
            $dom.find("[rp-table-mark=\"category\"]").find("option[value='" + data.category + "']").attr("selected", "selected");
            $dom.find("[rp-table-mark=\"category\"]").trigger($.Event("change"));
            var drawContentId = $dom.find("[rp-table-mark=\"category\"]").attr("rp-table-mark-content");
            var $dc = $("#" + drawContentId);
            var dataCfg = data[data.category];
            if ("dateTime" === data.category) {
                $dc.find("[rp-table-mark=\"default\"]").find("[view-flag-radio-value=\"" + dataCfg.default + "\"]").trigger($.Event("click"));
                $dc.find("[rp-table-mark=\"sectionStart\"]").find("option[value='" + dataCfg.section.start + "']").attr("selected", "selected");
                $dc.find("[rp-table-mark=\"sectionEnd\"]").find("option[value='" + dataCfg.section.end + "']").attr("selected", "selected");
                $dc.find("[rp-table-mark=\"splitNumber\"]").val(dataCfg.split.number);
                $dc.find("[rp-table-mark=\"splitUnit\"]").find("option[value='" + dataCfg.split.unit + "']").attr("selected", "selected");
            } else {
                var $text = $dc.find("[rp-table-mark=\"categoryFieldText\"]");
                $text.find("[name='" + drawContentId + "_text_name']").val(dataCfg.text.text);
                $text.find("[name='" + drawContentId + "_text']").val(dataCfg.text.value);
                var $value = $dc.find("[rp-table-mark=\"categoryFieldValue\"]").find(".categoryFieldValue");
                $value.find("option[value='" + dataCfg.value.value + "']").attr("selected", "selected");
                $value.trigger($.Event("change"));
            }
            return data;
        },
        //
        _conditionDialogClick: function (thisObj) {
            var allField = this.__getWidgetsOfDialogJsp();
            var selected = $(thisObj).find("input[type='hidden']").val();
            var url = '/sys/modeling/base/views/business/res/conditionDialog.jsp?type=condition';
            dialog.iframe(url, "筛选项", function (data) {
                if (!data) {
                    return;
                }
                data = $.parseJSON(data);
                $(thisObj).find("[data-lui-mark='dialogText']").val(data.text.join(";"));
                $(thisObj).find("input[type='hidden']").val(JSON.stringify(data.selected));
            }, {
                width: 720,
                height: 530,
                params: {
                    selected: selected,
                    allField: JSON.stringify(allField)
                }
            });
        },
        _whereItemAddClickTable: function (thisObj) {
            var self = this;
            //这里相当于自己查询自己，没有当前主文档的概念，需要屏蔽掉公式定义器
            var whereCfg = {
                parent: self,
                widgets: self.widgets,
                formulaWidgets: self.widgets,
                fdEnableFlow: self.fdEnableFlow
            };
            this._whereItemAddClick(thisObj, whereCfg);
        },
        getKeyData: function () {
            var self = this;
            var table = {};
            table.title = {
                text: self.element.find("[name='cfgTableTitle_name']").val(),
                value: self.element.find("[name='cfgTableTitle']").val()
            };
            table.row = self.getCategoryKeyData(self.element.find("#_resPanel_table_rowTitle"));
            table.col = self.getCategoryKeyData(self.element.find("#_resPanel_table_colTitle"));
            var condition = self.element.find("[name=tableCondition]").val();
            table.condition = [];
            if (condition || condition.length > 0) {
                table.condition = JSON.parse(condition);
                table.conditionText = self.element.find("[name= tableConditionText]").val();
            }
            table.where = self.getWhereKeyData();
            return table;
        },
        initByStoreData: function (table) {
            console.debug("initByStoreData - table::", table);
            var self = this;
            if (table) {
                try {
                    if (table.title) {
                        self.element.find("[name='cfgTableTitle_name']").val(table.title.text)
                        self.element.find("[name='cfgTableTitle']").val(table.title.value);
                    }
                    //
                    if (table.condition) {
                        self.element.find("[name=tableCondition]").val()
                    }
                    var condition = table.condition;
                    if (condition || condition.length > 0) {
                        self.element.find("[name=tableCondition]").val(JSON.stringify(condition));
                        if (table.conditionText) {
                            self.element.find("[name= tableConditionText]").val(table.conditionText);
                        }
                    }
                    //where
                    //这里相当于自己查询自己，没有当前主文档的概念，需要屏蔽掉公式定义器
                    var whereCfg = {
                        parent: self,
                        widgets: self.widgets,
                        formulaWidgets: self.widgets
                    };
                    self._initWgtCollection(table.where, "where", whereCfg, $("#table_whereTable"));
                    //row
                    self.initCategoryStoreData(self.element.find("#_resPanel_table_rowTitle"), table.row);
                    self.initCategoryStoreData(self.element.find("#_resPanel_table_colTitle"), table.col);
                } catch (e) {
                    console.log("初始化失败", e)
                }


            }
        },
        __getRowAndColType: function (type) {
            try {
                var sourceKeyData = this.getKeyData();
                var data = sourceKeyData[type];
                return data.type;
            } catch (e) {
                console.warn("未获取到面板行列字段属性", e)
            }
        }

    });

    exports.ResPanelTable = ResPanelTable;
});
