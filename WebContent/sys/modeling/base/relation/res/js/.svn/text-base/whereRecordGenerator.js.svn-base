/**
 * 关系设置-查询条件生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var relationDiagram = require("sys/modeling/base/relation/trigger/behavior/js/relationDiagram");
    var modelingLang = require("lang!sys-modeling-base");
    var WhereRecordGenerator = base.Component.extend({
        fieldTypes: {
            "1": {
                "name": modelingLang['modelingAppListview.enum.fix'],
                "valueDrawFun": "fixValDraw"
            },
            "2": {
                "name": modelingLang['modelingAppListview.enum.dynamic'],
                "valueDrawFun": "dialogValDraw"
            },
            "3": {
                "name": modelingLang['modelingAppListview.enum.empty'],
                "valueDrawFun": "nullValDraw"
            }
            // ,
            // "4":{
            // 	"name" : "公式定义",
            // 	"valueDrawFun" : "formulaValDraw"
            // }
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
            }
        },
        //=|等于，单选，like|包含 多选
        operatorMatch: 0,
        enumValues: {},
        initProps: function ($super, cfg) {
            $super(cfg);
            //console.log("WhereRecordGenerator", cfg);
            $super(cfg);
            this.fieldName = cfg.fieldName;
            this.pcfg = cfg.pcfg;
            this.filterWhereByStyleEq2 = cfg.filterWhereByStyleEq2 || false;
            this.isDetail = cfg.isDetail || false;
            this.detailId = cfg.detailId;
            this.initEnum();
            this.build();
            this.lines = [];
        },
        initEnum: function () {
            var sourceData = this.pcfg.widgets.passive;
            if (sourceData && sourceData) {
                for (var controlId in sourceData) {
                    var info = sourceData[controlId];
                    if (info.enumValues) {
                        this.enumValues[controlId] = info.enumValues;
                    }
                }
            }
            //补充
            var widgets_docStatus = this.config.widgets_docStatus;
            if (widgets_docStatus) {
                for (var controlId in widgets_docStatus) {
                    var info = widgets_docStatus[controlId];
                    if (info.enumValues) {
                        this.enumValues[controlId] = info.enumValues;
                    }
                }
            }
            console.debug("enumValues")
            console.debug(this.enumValues)
        },
        build: function () {
            var self = this;
            var $pele = self.pcfg.container;
            var $ele = $pele.find("[mdlng-rltn-property=\"" + self.fieldName + "\"]");
            $ele.find("[prprty-click=\"create\"]").each(function (idx, dom) {
                $(dom).off("click").on("click", function () {
                    if(!self.isDetail){
                        $(".mainModelWhereTip").remove();
                    }
                    self.createNewLine();
                })
            });
            self.element = $ele

        },
        createNewLine: function () {
            var self = this;
            var $valueTable = self.element.find("[mdlng-rltn-prprty-value=\"" + self.fieldName + "\"]").find("tbody");
            var lineId = parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            var $nline = $("<tr></tr>");
            $nline.attr("where-tr-id", lineId);
            $nline.attr("mdlng-prprty-mark", "fdInWhere");
            // 字段td
            var $fieldTd = $("<td where-td='field'></td>");
            var $fieldSelect = $("<select class='where_field'></selct>");
            $fieldSelect.append("<option value=''>"+modelingLang['relation.please.choose']+"</option>");
            var sourceData = self.pcfg.widgets.passive;
            if (sourceData && sourceData) {
                for (var controlId in sourceData) {
                    var info = sourceData[controlId];
                    var fullLabel = info.fullLabel||info.label;
                    if (info.type === "Attachment" || info.type === "RTF") {
                        //附件不处理
                        continue;
                    }
                    if (info.type.indexOf("[]")>0) {
                        //多选不处理
                        continue;
                    }
                    if (info.isClob) {
                        //Clob不处理
                        continue;
                    }
                    if(this.isDetail){
                        if (info.name.indexOf(".") < 0 || info.name.indexOf(this.detailId) < 0){
                            continue;
                        }
                        $fieldSelect.append("<option title='" + fullLabel + "'  value='" + info.name + "' data-property-type='" + info.type + "' data-property-business-type='" + info.businessType + "'>" +
                            info.label + "</option>");
                    }else {
                        //过滤明细表
                        if (info.name.indexOf(".") > 0)
                            continue;
                        $fieldSelect.append("<option title='" + fullLabel + "'  value='" + info.name + "' data-property-type='" + info.type + "' data-property-business-type='" + info.businessType + "'>" +
                            info.label + "</option>");
                    }
                }
            }
            var widgets_docStatus = self.config.widgets_docStatus;
            if (widgets_docStatus) {
                for (var controlId in widgets_docStatus) {
                    var info = widgets_docStatus[controlId];
                    if (info.type === "Attachment" || info.type === "RTF") {
                        //附件不处理
                        continue;
                    }
                    if (info.type.indexOf("[]")>0) {
                        //多选不处理
                        continue;
                    }
                    var fullLabel = info.fullLabel||info.label;
                    $fieldSelect.append("<option title='" + fullLabel + "'  value='" + info.name + "' data-property-type='" + info.type + "' data-property-business-type='" + info.businessType + "'>" +
                        info.label + "</option>");
                }
            }
            $fieldSelect.on("change", function () {
                self.updateoperatorTd(lineId);
                $nline.find(".where_style").val("1");
                $nline.find(".where_style").trigger($.Event("change"));
            });
            $fieldTd.append($fieldSelect);
            $nline.append($fieldTd);
            // 运算符td
            var $operatorTd = $("<td where-td='operation'></td>");
            $operatorTd.on("change", function () {
                self.operatorMatch = $(this).find(".where_operator option:selected").val();
                self.operatorTdClick(lineId);
            });
            $nline.append($operatorTd);

            // 值类型td
            var $fieldTypeTd = $("<td where-td='type'></td>");
            var $fieldTypeSelect = $("<select class='where_style'></selct>");
            for (var value in this.fieldTypes) {
                if ( this.filterWhereByStyleEq2){
                    //#119425 屏蔽入参
                    if (value==2){
                        $fieldTypeSelect.append("<option style='display: none' value='" + value + "'>" + this.fieldTypes[value].name + "</option>");
                        continue
                    }
                }
                $fieldTypeSelect.append("<option value='" + value + "'>" + this.fieldTypes[value].name + "</option>");
            }
            $fieldTypeSelect.on("change", function () {
                self.updateValueTd(lineId, this);
            });
            $fieldTypeTd.append($fieldTypeSelect);
            $nline.append($fieldTypeTd);
            // 值td
            var $valueTd = $("<td where-td='value'></td>");
            $nline.append($valueTd);
            // 操作td
            var $delTd = $("<td class=\"model-mask-panel-table-opt\"></td>");
            var $delSpan = $(" <p>"+modelingLang['modeling.page.delete']+"</p>");
            $delSpan.on("click", function () {
                self.destroyLine(lineId);
            });
            $delTd.append($delSpan);
            $nline.append($delTd);
            $valueTable.append($nline);
            return $nline;
        },
        destroyLine: function (id) {
            var self = this;
            var $valueTable = self.element.find("[mdlng-rltn-prprty-value=\"" + self.fieldName + "\"]").find("tbody");
            $valueTable.find("[where-tr-id='" + id + "']").remove();
        },
        //更新运算符，字段改变时
        updateoperatorTd: function (id) {
            var self = this;
            var $valueTable = self.element.find("[mdlng-rltn-prprty-value=\"" + self.fieldName + "\"]").find("tbody");
            var $line = $valueTable.find("[where-tr-id='" + id + "']");
            // 更新运算符
            var $operatorTd = $line.find("[where-td='operation']");
            $operatorTd.html("");
            var $fieldTd = $line.find("[where-td='field']");
            var $selectedOption = $fieldTd.find(":selected");
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
                $operatorTd.append(selectHtml);
            }
        },

        operatorTdClick: function (id) {
            var self = this;
            var $valueTable = self.element.find("[mdlng-rltn-prprty-value=\"" + self.fieldName + "\"]").find("tbody");
            var $line = $valueTable.find("[where-tr-id='" + id + "']");
            var $option = $line.find(".where_style option:selected");
            if ($option.val() === "1") {
                //固定值
                var $valueTd = $line.find("[where-td='value']");
                $valueTd.html("");
                var html = self.fixValDraw($line);
                $valueTd.append(html);
            }
        },
        //更新值，直接触发-值类型改变时,简介 字段-值类型
        updateValueTd: function (id, dom) {
            var self = this;
            var $valueTable = self.element.find("[mdlng-rltn-prprty-value=\"" + self.fieldName + "\"]").find("tbody");
            var $line = $valueTable.find("[where-tr-id='" + id + "']");
            var $valueTd = $line.find("[where-td='value']");
            $valueTd.html("");
            try {
                var fun = this.fieldTypes[dom.value]["valueDrawFun"];
                $valueTd.append(this[fun]($line));
            } catch (e) {
                console.error("updateValueTd error", e, dom);
            }

        },
        fixValDraw: function ($tr) {
            var type = $tr.find(".where_field option:selected").attr("data-property-type");
            var businessType = $tr.find(".where_field option:selected").attr("data-property-business-type");
            var valueName = $tr.attr("where-tr-id");
            var field_id = $tr.find(".where_field option:selected").val();
            this.operatorMatch = $tr.find(".where_operator option:selected").val();
            var html = "";
            if (type.indexOf("[]") > -1) {
                type = type.replace("[]", "");
            }
            if (this.fixValDrawValueFormula.hasOwnProperty(type)) {
                var fun = this.fixValDrawValueFormula[type].fun;
                html = formulaBuilder[fun](false, valueName, this.fixValDrawValueFormula[type].type);
            } else {
                if (this.enumValues[field_id]) {
                    html = this.enumValDraw(field_id, valueName,businessType);
                } else {

                    html = $("<input type='text' name='" + valueName + "' placeholder='请输入' class='inputsgl where_value' style='width:150px'/>");
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
        dialogValDraw: function ($tr) {
            var self = this;
            var type = $tr.find(".where_field option:selected").attr("data-property-type");
            var name = $tr.attr("where-tr-id")
            var html = $("<div class='modeling_formula'/>");
            html.append("<input type='hidden' name='" + name + "' />");
            html.append("<input type='text' name='" + name + "_name' class='inputsgl' style='width:150px' readonly/>");
            var $formula = $("<span class='highLight'><a href='javascrip:void(0);'>"+modelingLang['button.select']+"</a></span>");
            $formula.on("click", function (e) {
                self.Formul_Dialog(name, name + "_name", self.pcfg.widgets.main || "", type);
            });
            html.append($formula);
            return html;

        },
        Formul_Dialog: function (idField, nameField, fieldList, type) {
            var data = new KMSSData();
            for (var i in fieldList) {
                var field = fieldList[i];
                //附件过滤
                if (field.type == "Attachment" || field.type == "Attachment[]" || field.type != type) {
                    continue;
                }
                //明细表过滤
                if (field.name.indexOf(".") > -1) {
                    continue;
                }
                var pp = {name: field.name, label: field.label};
                data.AddHashMap({id: pp.name, name: pp.label});
            }
            var dialog = new KMSSDialog(false, true);
            dialog.winTitle = modelingLang['relation.select.parameters'];
            dialog.AddDefaultOption(data);
            dialog.BindingField(idField, nameField, ";");
            dialog.Show();
        },
        nullValDraw: function () {
            return "";
        },

        formulaValDraw: function ($tr) {
            var id = $tr.attr("where-tr-id");
            return formulaBuilder.get(id, $tr.find(".where_field option:selected").attr("data-property-type"));
        },

        destroy: function ($super, cfg) {
            this.parent.deleteWgt(this, "where");
            $super(cfg);
        },
        refreshWhereByStyleEq2: function () {
            //#119425 屏蔽入参
            var self = this;
            var $valueTable = self.element.find("[mdlng-rltn-prprty-value=\"" + self.fieldName + "\"]").find("tbody");
            var isAlert = false;
            $valueTable.find("[mdlng-prprty-mark='fdInWhere']").each(function (idx, nl) {
                var $option = $(nl).find(".where_style option:selected");
                var oval = $option.val();
                //若是原本选择了入参 需要重置并提醒
                if (oval == "2") {
                    if (self.filterWhereByStyleEq2 ){
                        isAlert = true;
                        $(nl).find(".where_style").val("1").trigger($.Event("change"));
                    }
                }
                //入参显示切换
                if (self.filterWhereByStyleEq2 ){
                    $(nl).find(".where_style option[value='2']").hide();
                }else {
                    $(nl).find(".where_style option[value='2']").show();
                }
            });
            if (isAlert) {
                dialog.alert(modelingLang['relation.non-pop-up.box.reset'])
            }

        },
        getKeyData: function () {
            var self = this;
            var $valueTable = self.element.find("[mdlng-rltn-prprty-value=\"" + self.fieldName + "\"]").find("tbody");
            var keyData = []
            $valueTable.find("[mdlng-prprty-mark='fdInWhere']").each(function (idx, nl) {
                var lineData = self.getLineKeyData($(nl));
                if (lineData) {
                    keyData.push(lineData);
                }
            });
            return keyData;
        },
        getLineKeyData: function ($nline) {
            var keyData = {};
            keyData.name = {};
            keyData.match = "";
            keyData.type = {};
            keyData.expression = {};

            var $option = $nline.find(".where_field option:selected");
            if ($option.val() === "") {
                return null;
            }
            keyData.name.type = $option.attr("data-property-type");
            var field_id = $option.val();
            keyData.name.value = field_id;
            keyData.name.text = $option.text();

            keyData.match = $nline.find(".where_operator option:selected").val();

            $option = $nline.find(".where_style option:selected");
            keyData.type.text = $option.text();

            keyData.type.value = $option.val();
            var valueName = $nline.attr("where-tr-id")
            if (this.enumValues[field_id] && $option.val() === "1") {
                var $value = $nline.find("[name='" + valueName + "']");
                var exValue = "";
                var exText = "";
                $value.each(function (idx, ele) {
                    if ($(ele).prop('checked')) {
                        exValue += $(ele).attr("enumValue") + "|";
                        exText += $(ele).attr("enumTxt") + "|";
                    }
                })
                if (exText.lastIndexOf("|") == exText.length - 1)
                    exText = exText.substring(0, exText.length - 1)
                if (exValue.lastIndexOf("|") == exValue.length - 1)
                    exValue = exValue.substring(0, exValue.length - 1)
                keyData.expression.text = exText;
                keyData.expression.value = exValue;

            } else {

                var $value = $nline.find("[name='" + valueName + "']");
                keyData.expression.text = $value.val();
                keyData.expression.value = $value.val();
                var $valueText = $nline.find("[name='" + valueName + "_name']");
                if ($valueText.length > 0) {
                    keyData.expression.text = $valueText.val();
                }
            }


            return keyData;
        },

        initByStoreData: function (storeData) {
            if (!storeData) {
                return
            }
            var self = this;
            var sourceData = self.pcfg.widgets.passive;
            var widgets_docStatus = self.config.widgets_docStatus;
            for (var i in storeData) {
            	var $nl = self.createNewLine();
                var data = storeData[i];

                if ((sourceData && sourceData[data.name.value])
                    || (widgets_docStatus && widgets_docStatus[data.name.value])) {
                        var $selectField = $nl.find(".where_field");
                        $selectField.val(data.name.value);
                        $selectField.trigger($.Event("change"));
                }

                $nl.find(".where_operator").val(data.match).trigger($.Event("change"));

                $nl.find(".where_style").val(data.type.value).trigger($.Event("change"));
                var valueName = $nl.attr("where-tr-id")
                if (this.enumValues[data.name.value] && data.type.value === "1") {
                    var $value = $nl.find("[name='" + valueName + "']");
                    var exValue = data.expression.value.split("|");
                    $value.each(function (idx, ele) {
                        for (var j = 0; j < exValue.length; j++) {
                            if ($(ele).attr("enumValue") === exValue[j])
                                $(ele).prop("checked", true)
                        }
                    })

                } else {
                    var $value = $nl.find("[name='" + valueName + "']");
                    if ($value.length > 0) {
                        $value.val(data.expression.value);
                        var $valueText = $nl.find("[name='" + valueName + "_name']");
                        if ($valueText.length > 0) {
                            $valueText.val(data.expression.text)
                        }
                    }
                }

            }

        }
    });

    exports.WhereRecordGenerator = WhereRecordGenerator;
});
