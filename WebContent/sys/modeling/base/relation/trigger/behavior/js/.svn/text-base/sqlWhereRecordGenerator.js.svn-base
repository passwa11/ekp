/**
 * 查询条件生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var modelingLang = require("lang!sys-modeling-base");
    var WhereRecordGenerator = base.Component.extend({

        initProps: function ($super, cfg) {
            $super(cfg);
            this.currentData = cfg.currentData;
            this.isPreWhere = cfg.isPreWhere;
            //	console.log("WhereRecordGenerator cfg",cfg)
            this.valueName = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
        },

        fieldTypes: {
            "2": {
                "name": modelingLang['sysModelingOperation.fdInParam'],
                "valueDrawFun": "nullValDraw"
            },
            "4": {
                "name": modelingLang['modelingAppListview.enum.formula'],
                "valueDrawFun": "formulaValDraw"
            }
        },
        fixValDrawValueFormula: {
        },
        draw: function (container, isDetail) {
            var self = this;
            self.element = $("<tr class='view_field_tr'></tr>");
            // 字段td
            var $fieldTd = $("<td></td>");
            var $fieldInput = $("<input type='text' class='inputsgl sql_where_field'/>");

            $fieldTd.append($fieldInput);

            // 运算符td
            //self.$operatorTd = $("<td></td>");

            // 值类型td
            var $fieldTypeTd = $("<td></td>");
            var $fieldTypeSelect = $("<select class='sql_where_style'></selct>");
            for (var value in this.fieldTypes) {
                $fieldTypeSelect.append("<option value='" + value + "'>" + this.fieldTypes[value].name + "</option>");
            }
            $fieldTypeSelect.on("change", function () {
                self.updateValueTd(this);

            });
            $fieldTypeTd.append($fieldTypeSelect);

            // 值td
            self.$valueTd = $("<td></td>");

            // 操作td
            var $delTd = $("<td></td>");
            var $delSpan = $("<span class='table_opera'>"+modelingLang['modeling.page.delete']+"</span>");
            $delSpan.on("click", function () {
                self.destroy();
            });
            $delTd.append($delSpan);

            self.element.append($fieldTd);
            //self.element.append(self.$operatorTd);
            self.element.append($fieldTypeTd);
            self.element.append(self.$valueTd);
            self.element.append($delTd);
            container.append(self.element);

            $fieldTypeSelect.trigger($.Event("change"));

            self.parent.addWgt(this, "sqlWhere");
        },


        updateValueTd: function (dom) {
            var $tr = $(dom).closest("tr");
            this.$valueTd.html("");
            $(".mainModelWhereTip").remove();
            var fun = this.fieldTypes[dom.value]["valueDrawFun"];
            this.$valueTd.append(this[fun]($tr));
        },

        nullValDraw: function () {
            return "";
        },

        formulaValDraw: function ($tr) {
            if(typeof this.parent.getPreModelData !== "function" || this.parent.prefix != "create"){
                return formulaBuilder.get(this.valueName, "");
            }else{
                var url = Com_Parameter.ContextPath + "sys/modeling/base/relation/trigger/behavior/js/formulaDialog.jsp";
                var preModelData = this.parent.getPreModelData();
                return formulaBuilder.get(this.valueName, "",{
                    "modelMainName": this.parent.modelMainName,
                    "otherVarInfo": preModelData?preModelData.data:null,
                    "preModelModelName": preModelData?preModelData.modelName:null,
                    supportPreModel:true
                },url);
            }
            //return formulaBuilder.get(this.valueName, "");
        },

        destroy: function ($super, cfg) {
            this.parent.deleteWgt(this, "sqlWhere");
            $super(cfg);
        },

        getKeyData: function () {
            var keyData = {};
            keyData.name = {};
            keyData.match = "";
            keyData.type = {};
            keyData.expression = {};

            var $option = this.element.find(".sql_where_field");
            if ($option.val() === "") {
                return null;
            }
            keyData.name.type = "sql";
            keyData.name.value = $option.val().trim();
            keyData.name.text = $option.val().trim();
            keyData.name.fullLabel = $option.val().trim();

            //keyData.match = this.element.find(".where_operator option:selected").val();

            $option = this.element.find(".sql_where_style option:selected");
            keyData.type.text = $option.text();
            keyData.type.value = $option.val();

            var $value = this.element.find("[name='" + this.valueName + "']");
            keyData.expression.text = $value.val();
            keyData.expression.value = $value.val();
            var $valueText = this.element.find("[name='" + this.valueName + "_name']");
            if ($valueText.length > 0) {
                keyData.expression.text = $valueText.val();
            }

            return keyData;
        },

        initByStoreData: function (storeData,sql) {
            var $selectField = this.element.find(".sql_where_field");

            $selectField.val(storeData.name.value);

            this.element.find(".sql_where_style").val(storeData.type.value).trigger($.Event("change"));

            var $value = this.element.find("[name='" + this.valueName + "']");
            if ($value.length > 0) {
                $value.val(storeData.expression.value);
                var $valueText = this.element.find("[name='" + this.valueName + "_name']");
                if ($valueText.length > 0) {
                    $valueText.val(storeData.expression.text)
                }
            }
        }
    });

    exports.WhereRecordGenerator = WhereRecordGenerator;
});
