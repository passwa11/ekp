/**
 * 目标行生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require("lui/topic");
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var relationDiagram = require("sys/modeling/base/relation/trigger/behavior/js/relationDiagram");
    var validatorGenerator = require("sys/modeling/base/relation/trigger/behavior/js/validatorGenerator")
    var modelingLang = require("lang!sys-modeling-base");
    var TargetRecordGenerator = base.Component.extend({

        initProps: function ($super, cfg) {
            $super(cfg);
            this.sourceData = cfg.sourceData;
            this.isDetail = cfg.isDetail;
            this.valueName = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.targetData = {};
        },

        startup: function ($super, cfg) {
            $super(cfg);
        },

        tmpOption: {
            "0": {
                "name": modelingLang['behavior.null'],
                "valueDrawFun": "nullDraw"
            },
            "1": {
                "name": modelingLang['modelingAppListview.enum.fix'],
                "valueDrawFun": "fixDraw"
            },
            "2": {
                "name": modelingLang['modelingAppListview.enum.dynamic'],
                "valueDrawFun": "nullDraw"
            },
            "3": {
                "name": modelingLang['modelingAppListview.enum.empty'],
                "valueDrawFun": "nullDraw"
            },
            "4": {
                "name": modelingLang['modelingAppListview.enum.formula'],
                "valueDrawFun": "formulaDraw"
            },
            "5": {
                "name": modelingLang['behavior.user.input'],
                "valueDrawFun": "nullDraw"
            }
        },
        attachmentsOption: {
            "0": {
                "name": modelingLang['behavior.null'],
                "valueDrawFun": "nullDraw"
            },
            "2": {
                "name": modelingLang['modelingAppListview.enum.dynamic'],
                "valueDrawFun": "nullDraw"
            },
            "4": {
                "name": modelingLang['modelingAppListview.enum.formula'],
                "valueDrawFun": "formulaDraw"
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
        draw: function (targetData) {
            var self = this;
            var $tr = $("<tr />");
            //#125212
            var fullLabel = targetData.fullLabel || targetData.label;
            $tr.append("<td style='cursor: default' target-filed-td-id='" + targetData.name + "' title='" + fullLabel + "'><span>" + targetData.label + "</span></td>");

            var $select = $("<select class='behavior_style'></select>");
            if (targetData.businessType === "attachments") {
                for (var key in this.attachmentsOption) {
                    $select.append("<option value='" + key + "'>" + self.attachmentsOption[key]["name"] + "</option>");
                }
            } else {
                for (var key in this.tmpOption) {
                    $select.append("<option value='" + key + "'>" + self.tmpOption[key]["name"] + "</option>");
                }
            }


            // 变更值类型
            $select.on("change", function () {
                var valueDom = self[self.tmpOption[this.value]["valueDrawFun"]](targetData);
                $(this).closest("tr").find(".behavior_value").html(valueDom);
            });

            $tr.append($("<td/>").append($select));

            $tr.append("<td><div class='behavior_value'></div></td>");

            $select.trigger($.Event("change"));

            self.parent.addWgt(this, "target");

            self.targetData = targetData;
            self.element = $tr;
            return $tr;
        },

        fixDraw: function (targetData) {
            var type = targetData.type;
            if (type.indexOf("[]")>0){
                type = type.replaceAll("[]","");
            }
            var html = "";
            if (this.fixValDrawValueFormula.hasOwnProperty(type)) {
                var fun = this.fixValDrawValueFormula[type].fun;
                html = formulaBuilder[fun](false, this.valueName, this.fixValDrawValueFormula[type].type);
            } else {
                html = $("<input type='text' name='" + this.valueName + "' class='inputsgl where_value' style='width:150px'/>");
            }
            return html;
        },

        nullDraw: function () {
            return "";
        },

        formulaDraw: function (targetData) {
            var url = Com_Parameter.ContextPath + "sys/modeling/base/relation/trigger/behavior/js/formulaDialog.jsp";
            var targetModelData = this.parent.getTargetData();
            var params={};
            var prefix = this.parent.prefix;
            if(!prefix && this.parent.parent){
                prefix = this.parent.parent.prefix;
            }
            var supportType = [];
            if(prefix == "create"){
                supportType =["preModel","preQuery"];
            }else if (prefix == "update"){
                supportType =["preQuery"];
            }
            if(prefix == "create"){
                var preModelData = this.parent.getPreModelData();
                params = {
                    "targetFormList": targetModelData.data,
                    "modelMainName": this.parent.modelMainName,
                    "otherVarInfo": preModelData?preModelData.data:null,
                    "preModelModelName": preModelData?preModelData.modelName:null,
                    "sourceFormList": this.sourceData,
                    isForceSave: true,
                    supportDetailAtt: true,
                    supportType:supportType,
                    prefix:prefix
                };
            }else{
                params = {
                    "targetFormList": targetModelData.data,
                    "modelMainName": this.parent.modelMainName,
                    "sourceFormList": this.sourceData,
                    isForceSave: true,
                    supportDetailAtt: true,
                    supportType:supportType,
                    prefix:prefix
                }
            }
            if (targetData.name.indexOf(".") > 0) {
                return formulaBuilder.get(this.valueName, targetData.type + "[]", params, url);
            } else {
                return formulaBuilder.get(this.valueName, targetData.type, params, url);
            }
        },
        destroy: function ($super, cfg) {
            this.parent.deleteWgt(this, "target");
            $super(cfg);
        },

        getKeyData: function () {
            var keyData = {};
            keyData.name = {};
            keyData.type = {};
            keyData.expression = {};

            keyData.name.text = this.targetData.label;
            keyData.name.type = this.targetData.type;
            keyData.name.value = this.targetData.name;
            keyData.name.fullLabel = this.targetData.fullLabel||this.targetData.label;

            var $styleOption = this.element.find(".behavior_style option:selected");
            keyData.type.text = $styleOption.text();
            keyData.type.value = $styleOption.val();

            var $value = this.element.find("[name='" + this.valueName + "']");
            keyData.expression.text = $value.val();
            keyData.expression.value = $value.val();
            var $valueText = this.element.find("[name='" + this.valueName + "_name']");
            if ($valueText.length > 0) {
                keyData.expression.text = $valueText.val();
            }

            return keyData;
        },

        initByStoreData: function (storeData) {

            this.element.find(".behavior_style").val(storeData.type.value).trigger($.Event("change"));

            var $value = this.element.find("[name='" + this.valueName + "']");
            if ($value.length > 0) {
                $value.val(storeData.expression.value);
                var $valueText = this.element.find("[name='" + this.valueName + "_name']");
                if ($valueText.length > 0) {
                    $valueText.val(storeData.expression.text)
                }
            }
        },

        validators:function() {

            var $styleOption = this.element.find(".behavior_style option:selected");
            var type = $styleOption.val();
            var validator = true;
            if (type !== "4"){
                return validator;
            }
            //存在非配置的前置查询字段
            var existsIsNotPreQuery = true;
            var $value = this.element.find("[name='" + this.valueName + "']");
            var key = this.getPreQueryKeys($value.val());
            if(!key){
                return validator;
            }
            var preQueryCollection = this.parent.preQueryCollection;
            if(!preQueryCollection && this.parent.parent){
                preQueryCollection = this.parent.parent.preQueryCollection;
            }
            if(preQueryCollection && preQueryCollection.length > 0){
                for (var i = 0; i < preQueryCollection.length; i++) {
                    var preQueryWgt = preQueryCollection[i];
                    if(key === preQueryWgt.getNickName()){
                        existsIsNotPreQuery = false;
                        break;
                    }
                }
            }

            //目标表单为空校验
            if(!this.valueWgt && existsIsNotPreQuery){
                this.valueWgt = new validatorGenerator.ValidatorGenerator({
                    title:"值",
                    content:"选择了非配置的前置查询字段",
                    container:this.element.find(".behavior_value").closest("td")
                })
            }
            this.buildValidator(existsIsNotPreQuery,this.valueWgt);
            if(this.valueWgt && this.valueWgt.isShow()){
                validator = false;
            }
            return validator;
        },
        buildValidator: function (expr,weiget) {
            if (expr) {
                if(weiget && !weiget.isShow()){
                    weiget.show();
                }
            } else {
                if (weiget) {
                    weiget.hide();
                }
            }
        },
        //获取公式中前置查询别名
        getPreQueryKeys:function(expr) {
            var startIdx = -1;
            if(!expr){
                return null;
            }
            for (var i = 0; i < expr.length; i++) {
                var c = expr[i];
                if (startIdx != -1) {
                    if (c == '$') {
                        var fieldName = expr.substring(startIdx + 1, i);
                        if (fieldName.indexOf('*') > -1) {
                            var index = fieldName.indexOf("*");
                            var preKey = fieldName.substring(0, index);
                            return preKey;
                        } else {
                            startIdx = -1;
                        }
                    }
                } else {
                    if (c == '$') {
                        startIdx = i;
                    }
                }
            }
            return null;
        }
    })
    exports.TargetRecordGenerator = TargetRecordGenerator;

})
