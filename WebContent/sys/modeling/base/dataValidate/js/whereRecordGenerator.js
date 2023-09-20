/**
 * 查询条件生成器---自定义校验规则
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var relationDiagram = require("sys/modeling/base/relation/trigger/behavior/js/relationDiagram");
    var modelingLang = require("lang!sys-modeling-base");
    var WhereRecordGenerator = base.Component.extend({
        //=|等于，单选，like|包含 多选
        operatorMatch: 0,
        enumValues: {},
        initEnum: function () {
            var sourceData = {};
            if(this.isPreWhere){
                if(this.currentData){
                    sourceData = this.currentData;
                }
            }else{
                sourceData = this.parent.getTargetData();
            }
            if (sourceData && sourceData.data) {
                for (var controlId in sourceData.data) {
                    if(controlId == "fdId"){
                        continue;
                    }
                    var info = sourceData.data[controlId];
                    if (info.enumValues) {
                        this.enumValues[controlId] = info.enumValues;
                    }
                }
            }
        },
        initProps: function ($super, cfg) {
            $super(cfg);
            this.currentData = cfg.currentData;
            this.isPreWhere = cfg.isPreWhere;
            this.valueName = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.initEnum();
        },

        fieldTypes: {
            "1": {
                "name": modelingLang['modelingAppListview.enum.fix'],
                "valueDrawFun": "fixValDraw"
            },
           /* "2": {
                "name": modelingLang['modelingAppListview.enum.dynamic'],
                "valueDrawFun": "nullValDraw"
            },
            "3": {
                "name": modelingLang['modelingAppListview.enum.empty'],
                "valueDrawFun": "nullValDraw"
            },*/
            "4": {
                "name": modelingLang['modelingAppListview.enum.formula'],
                "valueDrawFun": "formulaValDraw"
            }
        },
        fixValDrawValueFormula: {
            "com.landray.kmss.sys.organization.model.SysOrgPerson": {
                "fun": "getOrgAddress",
                "type": ORG_TYPE_PERSON
            },
            "com.landray.kmss.sys.organization.model.SysOrgElement": {
                "fun": "getOrgAddress",
                "type": ORG_TYPE_ALLORG
            },
            "Date": {
                "fun": "getDate",
                "type": "Date"
            },
            "DateTime": {
                "fun": "getDate",
                "type": "DateTime"
            },
            "Time": {
                "fun": "getDate",
                "type": "Time"
            },
        },

        draw: function (container, isDetail) {
            var self = this;
            self.element = $("<tr class='view_field_tr'></tr>");
            // 字段td
            var $fieldTd = $("<td></td>");
            var $fieldSelect = $("<select class='where_field'></select>");
            $fieldSelect.append("<option value=''>"+modelingLang['relation.please.choose']+"</option>");
            var sourceData = {};
            if(self.isPreWhere){
            	if(self.currentData){
            		sourceData = self.currentData;
            	}
            }else{
            	sourceData = this.parent.getTargetData();
            }
            if (sourceData && sourceData.data) {
                for (var controlId in sourceData.data) {
                    if(controlId == "fdId"){
                        continue;
                    }
                    var info = sourceData.data[controlId];
                    var fullLabel = info.fullLabel||info.label;
                    if (info.type === "Attachment") {
                        //附件不处理
                        continue;
                    }
                    if (info.isClob){
                        //Clob不处理
                        continue;
                    }
                    //过滤明细表
                    if (isDetail) {
                        $fieldSelect.append("<option title='" + fullLabel + "' value='" + info.name + "' data-property-type='" + info.type +  "' data-property-business-type='" + info.businessType + "'>" + info.label + "</option>");
                    } else {
                        if (!self.isPreWhere && info.name.indexOf(".") > 0)
                            continue;
                        $fieldSelect.append("<option title='" + fullLabel + "' value='" + info.name + "' data-property-type='" + info.type +   "' data-property-business-type='" + info.businessType +"'>" + info.label + "</option>");
                    }

                }
            }
            $fieldSelect.on("change", function () {
                self.updateoperatorTd(this);
                self.element.find(".where_style").val("1");
                self.element.find(".where_style").trigger($.Event("change"));
            });
            $fieldTd.append($fieldSelect);

            // 运算符td
            self.$operatorTd = $("<td></td>");
            self.$operatorTd.on("change", function () {
                self.operatorMatch = $(this).find(".where_operator option:selected").val();
                self.operatorTdClick(this);
            });
            // 值类型td
            var $fieldTypeTd = $("<td></td>");
            var $fieldTypeSelect = $("<select class='where_style'></selct>");
            for (var value in this.fieldTypes) {
                $fieldTypeSelect.append("<option value='" + value + "'>" + this.fieldTypes[value].name + "</option>");
            }
            $fieldTypeSelect.on("change", function () {
                self.updateValueTd(this);
                self.updateOperatorTdByFieldType(this,self.$operatorTd);
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
            self.element.append(self.$operatorTd);
            self.element.append($fieldTypeTd);
            self.element.append(self.$valueTd);
            self.element.append($delTd);
            container.append(self.element);

            $fieldTypeSelect.trigger($.Event("change"));

            if(self.isPreWhere){
            	self.parent.addWgt(this, "preWhere");
            }else{
            	self.parent.addWgt(this, "where");
            }
        },

        updateoperatorTd: function (dom) {
            // 更新运算符
            this.$operatorTd.html("");
            var $selectedOption = $(dom).find(":selected");
            var propertyType = $selectedOption.attr("data-property-type");
            if (this.enumValues[$selectedOption.val()])
                propertyType = "Enum";
            var optionInfo = relationDiagram.getDiagram("where", propertyType);
            if (optionInfo) {
                var selectHtml = "<select class='where_operator'>";
                for (var i = 0; i < optionInfo.length; i++) {
                    selectHtml += "<option value='" + optionInfo[i].value + "'>" + optionInfo[i].name + "</option>";
                }
                selectHtml += "</select>";
                this.$operatorTd.append(selectHtml);
            }
        },

        operatorTdClick: function (dom) {
            var self = this;
            var $tr = $(dom).closest("tr");
            var $option = $tr.find(".where_style option:selected");
            if ($option.val() === "1") {
                //固定值
                this.$valueTd.html("");
                var html = self.fixValDraw($tr);
                this.$valueTd.append(html);
            }
        },

        updateValueTd: function (dom) {
            var $tr = $(dom).closest("tr");
            var name = $tr.find(".where_field option:selected").val();
            this.$valueTd.html("");
            if (name){
            	$(".mainModelWhereTip").remove();
                var fun = this.fieldTypes[dom.value]["valueDrawFun"];
                this.$valueTd.append(this[fun]($tr));
            }else{
                this.$valueTd.html("<span style='color:red'>"+modelingLang['behavior.select.field.name.first']+"</span>");
            }

        },

        updateOperatorTdByFieldType: function (fieldTypeDom,operatorTdDom) {
           //当为空值时，只有运算符等于和不等于
            var $tr = $(fieldTypeDom).closest("tr");
            var type = $tr.find(".where_style option:selected").val();
            if("3" == type){
                $tr.find(".where_operator").find("option").each(function () {
                    if($(this).val() != "="
                        && $(this).val() != "!="
                        && $(this).val() != "!{equal}"
                        && $(this).val() != "!{notequal}"
                        && $(this).val() != "eq" ){
                        $(this).css("display","none");
                    }
                })
                var operator = $tr.find(".where_operator option:selected").val();
                if(operator != "="
                    && operator != "!="
                    && operator != "!{equal}"
                    && operator != "!{notequal}"
                    && operator != "eq" ){
                    $tr.find(".where_operator").prop("selectedIndex",0);
                }
            }else{
                $tr.find(".where_operator").find("option").each(function () {
                    if($(this).css("display") == "none"){
                        $(this).css("display","");
                    }
                })
            }
        },

        fixValDraw: function ($tr) {
            var type = $tr.find(".where_field option:selected").attr("data-property-type");
            var businessType = $tr.find(".where_field option:selected").attr("data-property-business-type");
            var field_id = $tr.find(".where_field option:selected").val();
            this.operatorMatch = $tr.find(".where_operator option:selected").val();
            var html = "";
            if (this.fixValDrawValueFormula.hasOwnProperty(type)) {
                var fun = this.fixValDrawValueFormula[type].fun;
                html = formulaBuilder[fun](false, this.valueName, this.fixValDrawValueFormula[type].type);
            } else {
                if (this.enumValues[field_id]) {
                    html = this.enumValDraw(field_id, this.valueName,businessType);
                } else {
                    html = $("<input type='text' name='" + this.valueName + "' class='inputsgl where_value' style='width:150px'/>");
                }
            }
            return html;
        },

        enumValDraw: function (field_id, valueName,businessType) {
            var self = this;
            var html = "";
            var evs = this.enumValues[field_id].split(";");
            html = $("<div class='where_enum_check' />");
            if((self.operatorMatch === "=" || self.operatorMatch === "!{notequal}")
                && (businessType === "inputRadio" || businessType === "select")){
                var inputType = "radio";
            }else {
                var inputType = "checkbox";
            }
            // var inputType = self.operatorMatch === "like" ? "checkbox" : "radio";
            for (var i = 0; i < evs.length; i++) {
                var ei = evs[i].split("|");
                var checkId = "where_check_" + valueName + ei[1] + new Date().getTime();
                var $check = $("<input type=\"" + inputType + "\" value='" + ei[1] + "'  name='" + valueName + "'  />");
                //直接使用value会被替换，具体原因未知，暂用这个代替
                $check.attr("enumValue", ei[1]);
                $check.attr("enumTxt", ei[0]);
                $check.attr("id", checkId);
                $check.appendTo(html);
                $("<labe for='" + checkId + "'>" + ei[0] + " &nbsp;</labe>").appendTo(html);
            }
            return html;
        },

        nullValDraw: function () {
            return "";
        },

        formulaValDraw: function ($tr) {
            var type = $tr.find(".where_field option:selected").attr("data-property-type");
            var name = $tr.find(".where_field option:selected").val();
            if (name.indexOf(".") > 0) {
                type = type + "[]";
            }
            var prefix = this.parent.prefix;
            if(!prefix && this.parent.parent){
                prefix = this.parent.parent.prefix;
            }
            if(!this.parent.isCustomValidate && (this.isPreWhere || prefix !== "create" || typeof this.parent.getPreModelData !== "function")){
                return formulaBuilder.get(this.valueName, type);
            }else{
                var url = Com_Parameter.ContextPath + "sys/modeling/base/relation/trigger/behavior/js/formulaDialog.jsp";
                var preModelData = this.parent.getPreModelData();
                return formulaBuilder.get(this.valueName, type,{
                    "modelMainName": this.parent.modelMainName,
                    "preModelModelName": preModelData?preModelData.modelName:null,
                    supportType:["preModel"],
                    prefix:"customValidate",
                    supportDetailAtt : false
                },url);
            }
        },

        destroy: function ($super, cfg) {
        	if(this.isPreWhere){
        		 this.parent.deleteWgt(this, "preWhere");
        	}else{
        		 this.parent.deleteWgt(this, "where");
        	}
            $super(cfg);
        },

        getKeyData: function () {
            var keyData = {};
            keyData.name = {};
            keyData.match = "";
            keyData.type = {};
            keyData.expression = {};

            var $option = this.element.find(".where_field option:selected");
            if ($option.val() === "") {
                return null;
            }
            keyData.name.type = $option.attr("data-property-type");
            var field_id = $option.val();
            keyData.name.value = field_id;
            keyData.name.text = $option.text();
            keyData.name.fullLabel = $option.attr("title");

            keyData.match = this.element.find(".where_operator option:selected").val();

            $option = this.element.find(".where_style option:selected");
            keyData.type.text = $option.text();
            keyData.type.value = $option.val();

            var $value = this.element.find("[name='" + this.valueName + "']");
            if (this.enumValues[field_id] && $option.val() === "1") {
                var exValue = "";
                var exText = "";
                $value.each(function (idx, ele) {
                    if ($(ele).prop('checked')) {
                        exValue += $(ele).attr("enumValue") + ";";
                        exText += $(ele).attr("enumTxt") + ";";
                    }
                })
                if (exText.lastIndexOf(";") == exText.length - 1)
                    exText = exText.substring(0, exText.length - 1)
                if (exValue.lastIndexOf(";") == exValue.length - 1)
                    exValue = exValue.substring(0, exValue.length - 1)
                keyData.expression.text = exText;
                keyData.expression.value = exValue;
            } else {
                keyData.expression.text = $value.val();
                keyData.expression.value = $value.val();
                var $valueText = this.element.find("[name='" + this.valueName + "_name']");
                if ($valueText.length > 0) {
                    keyData.expression.text = $valueText.val();
                }
            }
            return keyData;
        },

        initByStoreData: function (storeData) {

            var $selectField = this.element.find(".where_field");
            var hasChanged = true;
            $selectField.find("option").each(function (idx, dom) {
                if (storeData.name.value == $(dom).val()) {
                    hasChanged = false;
                }
            });
            if (hasChanged) {
                console.warn("初始化失败[whereRecord] ，msg:a field has been changed ,storeData is ", storeData);
                this.destroy();
                return;
            }
            $selectField.val(storeData.name.value);
            $selectField.trigger($.Event("change"));

            this.element.find(".where_operator").val(storeData.match);

            this.element.find(".where_style").val(storeData.type.value).trigger($.Event("change"));
            var $value = this.element.find("[name='" + this.valueName + "']");
            if (this.enumValues[storeData.name.value] && storeData.type.value === "1") {
                var exValue = storeData.expression.value.split(";");
                $value.each(function (idx, ele) {
                    for (var j = 0; j < exValue.length; j++) {
                        if ($(ele).attr("enumValue") === exValue[j])
                            $(ele).prop("checked", true)
                    }
                })
            } else {
                if ($value.length > 0) {
                    $value.val(storeData.expression.value);
                    var $valueText = this.element.find("[name='" + this.valueName + "_name']");
                    if ($valueText.length > 0) {
                        $valueText.val(storeData.expression.text)
                    }
                }
            }
        }
    });

    exports.WhereRecordGenerator = WhereRecordGenerator;
});
