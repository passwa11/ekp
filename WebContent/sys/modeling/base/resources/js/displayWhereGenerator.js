/**
 * 显示项查询条件生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var relationDiagram = require("sys/modeling/base/relation/trigger/behavior/js/relationDiagram");
    var modelingLang = require("lang!sys-modeling-base");
    var DisplayWhereGenerator = base.Component.extend({

        initProps: function ($super, cfg) {
            $super(cfg);
            this.modelDict = this.filterSubTableField(cfg.parent.modelDict);
            if (cfg.storeData){
                this.field = cfg.storeData.name.value;
                this.modelInfo = this.getModelFieldInfo();
                this.text = cfg.storeData.name.text;
                this.type = this.modelInfo.fieldType;
                this.fieldOperator =cfg.storeData.match
            }else {
                this.field = cfg.parent.field;
                this.modelInfo = this.getModelFieldInfo();
                this.text = cfg.parent.text;
                this.type = this.modelInfo.fieldType;
                this.fieldOperator = "=";

            }
            this.valueName = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
        },

        fieldTypes: {
            "1": {
                "name": modelingLang['modelingAppListview.enum.fix'],
                "valueDrawFun": "fixValDraw"
            },
            //#162951 屏蔽入参
            /*"2": {
                "name": modelingLang['modelingAppListview.enum.dynamic'],
                "valueDrawFun": "nullValDraw"
            },*/
            "3": {
                "name": modelingLang['modelingAppListview.enum.empty'],
                "valueDrawFun": "nullValDraw"
            },
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
        //163369
        operators : [
            {"name": modelingLang['modelingAppListview.enum.equal'], "value": "eq"},
            {"name": modelingLang['modelingAppListview.enum.notequal'], "value": "!{notequal}"}
        ],
      //163369
        shieldNullType: [
            "Number",
            "Double",
            "BigDecimal",
            "Date",
            "DateTime",
            "Time"
        ],
        operatorType : [
         "eq",
         "!{notequal}"
        ],
       draw: function (container) {
           var self = this;
           self.element = $("<tr class='where_tr'></tr>");
           //值
           self.$field = $("<td width='15%' ></td>");
           var $whereRuleField = self.buildWhereRuleField();
           self.$field.append($whereRuleField);

           // 运算符td
           self.$operatorTd = $("<td width='15.3%'></td>");
           // 值类型td
           self.$fieldTypeTd = $("<td width='62.8%'></td>");
           self.updateoperatorTd();
            var $fieldTypeSelect = $("<select class='where_style'></selct>");
            for (var value in this.fieldTypes) {
                if(this.parent.displayType == "calendar" && value == "2"){
                    continue;
                }
                $fieldTypeSelect.append("<option value='" + value + "'>" + this.fieldTypes[value].name + "</option>");
            }
            $fieldTypeSelect.on("change", function () {
                self.updateValueTd(this);
                self.updateoperatorTd();
            });
            self.$fieldTypeTd.append($fieldTypeSelect);

            // 值
            self.$valueTd = $("<span style='margin-left:20px !important;display: inline;'></span>");
            self.$fieldTypeTd.append(self.$valueTd);
            // 操作td
            var $delTd = $("<td></td>");
            var $delSpan = $("<span class='table_opera'>"+modelingLang['modeling.page.delete']+"</span>");
            $delSpan.on("click", function () {
                self.destroy();
            });
            $delTd.append($delSpan);
            self.element.append(self.$field);
            self.element.append(self.$operatorTd);
            self.element.append(self.$fieldTypeTd);
            self.element.append($delTd);
            container.append(self.element);
            $fieldTypeSelect.trigger($.Event("change"));
            
            self.parent.addWgt(this);
        },

        updateoperatorTd: function () {
            // 更新运算符
            var self = this;
            this.$operatorTd.html("");
            var fieldType = this.$fieldTypeTd.find(".where_style").val();
            var optionInfo = relationDiagram.getDiagram("where",this.type);
            if (fieldType ==="3"){
                if(self.shieldNullType.indexOf(self.type) !=-1){
                    optionInfo = self.operators;
                }
            }
            if (optionInfo) {
                var $selectHtml = $("<select class='where_operator'>");
                for (var i = 0; i < optionInfo.length; i++) {
                    var option =  optionInfo[i];
                    var $whereRuleFieldOperatorOption = $("<option value="+option.value+">" + option.name + "</option>");
                    $selectHtml.append($whereRuleFieldOperatorOption);
                }
                this.$operatorTd.append($selectHtml);
                $selectHtml.on("change",function(){
                    var $whereRule = $(this).parents('.where_tr');

                        self.fieldOperator = $(this).val();

                        var $whereRuleFieldValueType = $whereRule.find('.where_style');
                        if('1' == $whereRuleFieldValueType.val()){
                            if(self.type === "enum") {
                                self.updateValueTd({"value":1});
                            }
                        }
                        if(self.shieldNullType.indexOf(self.type) !=-1){
                            if (self.operatorType.indexOf(self.fieldOperator) ==-1 ){
                                self.updatefieldType(false,$whereRuleFieldValueType);
                                return;
                            }
                        }
                    self.updatefieldType(true,$whereRuleFieldValueType);
                });
            }
        },

        updatefieldType:function (isDisplay,$whereRuleFieldValueType){
            if (isDisplay){
                $whereRuleFieldValueType.find("option[value = 3]").css("display","block");
            }else {
                var $sd =  $whereRuleFieldValueType.find("option[value = 3]");
                $sd.css("display","none");
            }

        },

        updateValueTd: function (dom) {
            this.$valueTd.html("");
            var fun = this.fieldTypes[dom.value]["valueDrawFun"];
            this.$valueTd.append(this[fun]($(dom).closest("tr")));
            $(".modeling_formula").css("display","inline-block");
        },

        fixValDraw: function ($tr) {
            var type = this.type;
            var html = "";
            if (this.fixValDrawValueFormula.hasOwnProperty(type)) {
                var fun = this.fixValDrawValueFormula[type].fun;
                html = formulaBuilder[fun](false, this.valueName, this.fixValDrawValueFormula[type].type);
            }else {
            	var bsType = this.modelInfo.businessType;
        		var enums = this.modelInfo.enumValues;
	            if(this.field === "docStatus"){
	            	//文档状态
	            	if(typeof(enums) != "undefined"){
	        			html += '<div style="display:inline-block;" class="whereFieldValueItem">'
	        			html += '<select name="whereFieldValue" class="whereFieldValue">';
	        			html += '<option value>'+modelingLang["behavior.null"]+'</option>';
	    				for(var i = 0;i < enums.length; i++){
	        				html += '<option value="'+enums[i].fieldEnumValue+'">'+enums[i].fieldEnumLabel+'</option>';
	        			}
	        			html += '</select></div>';
	            	}
            	}else if(type === "enum"){
                    html += this.getEnumHtml(enums, this.valueName + "_fieldInputValue",this.fieldOperator,bsType);
            	}else{
            		html = $("<input type='text' name='" + this.valueName + "' class='inputsgl where_value' style='width:150px;'/>");
            	}
	            
            }
            return html;
        },

        nullValDraw: function () {
            return "";
        },

        formulaValDraw: function ($tr) {
            var type = this.type;
            var name = this.field;
            if (name.indexOf(".") > 0) {
                type = type + "[]";
            }
	        if(!this.parent.xformIsInit){
	        	//屏蔽公式定义器的“变量”
	        	//formulaBuilder.initFieldList(window.top.xformId);
            	this.parent.xformIsInit = true;
            }
            return formulaBuilder.get(this.valueName, type);
        },
        getEnumHtml : function(options, name, operator,businessType){
            var html = "";
            var type = "radio";
            if(operator == 'like' || operator == "!{notContain}"
                || businessType == "inputCheckbox" || businessType == "fSelect"){
                type = "checkbox";
            }
            for(var i = 0; i < options.length;i++){
                html += '<input type="'+type+'" name="'+name+'"  class="whereFieldValue" value="'+options[i].fieldEnumValue+'" data-text="'+options[i].fieldEnumLabel+'" style="margin-left: 10px"  />'+options[i].fieldEnumLabel;
            }
            return html;
        },

        destroy: function ($super, cfg) {
            this.parent.deleteWgt(this);
            $super(cfg);
        },
        
        getKeyData: function () {
            var keyData = {};
            keyData.name = {};
            keyData.match = "";
            keyData.type = {};
            keyData.expression = {};
            if(this.field.indexOf(".fdName") > -1){
                this.field = this.field.substring(0,this.field.indexOf(".fdName"));
            }
            keyData.name.type = this.type;
            keyData.name.value = this.field;
            keyData.name.text = this.text;

            //运算符
            keyData.match = this.element.find(".where_operator option:selected").val();

            //值
            $option = this.element.find(".where_style option:selected");
            keyData.type.text = $option.text();
            keyData.type.value = $option.val();

            var bsType = this.parent.selected.businessType;
    		var enums = this.parent.selected.enumValues;
            var $input = this.element.find(".whereFieldValue");
            if(this.field === "docStatus"){
                //文档状态
                var $value = this.element.find(".whereFieldValue option:selected");
                keyData.expression.text = $value.text();
                keyData.expression.value = $value.val();
            }else if("radio" == $input.attr("type")){
                $input.each(function () {
                    if(true == $(this).prop("checked")){
                        keyData.expression.text = $(this).attr("data-text");
                        keyData.expression.value = $(this).val();
                    }
                })
            }else if("checkbox" == $input.attr("type")){
                var val = '';
                var text = '';
                $input.each(function () {
                    if(true == $(this).prop("checked")){
                        val = val + $(this).val() + ";";
                        text = text + $(this).attr("data-text") + ";";
                    }
                })
                val = val.slice(0,-1);
                text = text.slice(0,-1);
                keyData.expression.value = val;
                keyData.expression.text = text;
            }else{
            	var $value = this.element.find("[name='" + this.valueName + "']");
            	keyData.expression.text = $value.val();
                keyData.expression.value = $value.val();
            }
            var $valueText = this.element.find("[name='" + this.valueName + "_name']");
            if ($valueText.length > 0) {
                keyData.expression.text = $valueText.val();
            }
            //#162959 空值或者枚举类型没选值的情况下，获取不到数据存数据库存的是undefined，回显会异常，前台逻辑处理也会出现问题
            if (!keyData.expression.text || !keyData.expression.value){
                keyData.expression.text ="";
                keyData.expression.value ="";
            }
            keyData.expression.text = encodeURI(keyData.expression.text)
            keyData.expression.value = encodeURI(keyData.expression.value)
            return keyData;
        },

        initByStoreData: function (storeData) {
        	if (storeData) {
                storeData.expression.text = decodeURI(storeData.expression.text)
                storeData.expression.value = decodeURI(storeData.expression.value)
                  this.element.find(".display_item").text(this.text);
	              this.element.find(".where_style").val(storeData.type.value).trigger($.Event("change"));
                  this.element.find(".where_operator").val(storeData.match);
                  this.element.find(".where_operator").val(storeData.match).trigger($.Event("change"));
	              var bsType = storeData.name.type;
	      		  var enums = this.parent.selected.enumValues;
                var $input = this.element.find(".whereFieldValue");
                    if(this.field === "docStatus"){
                    //文档状态
	                  var $value = this.element.find(".whereFieldValue").find("option:contains('"+storeData.expression.text+"')");
	                  if ($value.length > 0) {
	                	  $value.attr("selected",true);
		              }
	              }else if('radio' == $input.attr('type')){
                    $input.each(function () {
                        if(storeData.expression.value == $(this).val()){
                            $(this).prop("checked",true);
                        }
                    })
                }else if('checkbox' == $input.attr('type')){
                    var fieldValueArr=storeData.expression.value.split(';');
                    $input.each(function () {
                        for(var i=0;i<fieldValueArr.length;i++) {
                            if(fieldValueArr[i] == $(this).val()){
                            /*    $(this).prop("checked",true);*/
                                $(this).attr("checked",true);
                                console.log("$(this)",$(this).val());
                                console.log("$(this)a",$(this).html());
                            }
                        }
                    })
                }else{
	            	  var $value = this.element.find("[name='" + this.valueName + "']");
	            	  if ($value.length > 0) {
		                  $value.val(storeData.expression.value);
		                  var $valueText = this.element.find("[name='" + this.valueName + "_name']");
		                  if ($valueText.length > 0) {
		                      $valueText.val(storeData.expression.text);
		                  }
		            }
	             }
	            
        	}
        },
        buildWhereRuleField : function () {
            var self =  this;
            //字段
            var $whereRuleField = $("<div class='open_rule_setting_where_rule_field' style='margin-bottom: 0'></div>");
            var $whereRuleFieldSelect = $("<select class='inputsgl'/>");
            for (var i = 0; i < self.modelDict.length; i++) {
                var option =  self.modelDict[i];
                var fieldType = option.fieldType;
                var showText = option.fieldText;
                if(self.getStrLen(showText)>16){
                        showText = showText.substring(0,self.getSubStrLenIndex(showText))+'...';
                    }
                    $whereRuleFieldSelect.append($("<option value="+option.field+" title='"+option.text+"'>" + showText + "</option>"));
            }
            $whereRuleFieldSelect.find("option[value = '"+self.field+"']").attr("selected","selected");
            $whereRuleFieldSelect.on("change",function(){
                var field = $(this).val();

                for(var i=0;i < self.modelDict.length;i++){
                    if(field == self.modelDict[i].field){
                        var fieldType = self.modelDict[i].fieldType;
                        self.field = field;
                        self.type = fieldType;
                        self.text = self.modelDict[i].fieldText;
                        self.modelInfo = self.modelDict[i];
                        self.element.find(".where_style").val("1");
                        self.updateoperatorTd();
                        self.updatefieldType(true,self.$operatorTd.parents('.where_tr').find('.where_style'));
                        self.updateValueTd({"value":1});
                        break;
                    }
                }

            });

            $whereRuleField.append($whereRuleFieldSelect);
            return $whereRuleField;
        },
        /*过滤明细表的字段*/
        filterSubTableField : function(fields){
            var allField = fields || [];
            if(typeof allField === 'string'){
                allField = $.parseJSON(fields);
            }
            var newAllField = [];
            for(var i=0; i<allField.length; i++){
                var field = allField[i];
                if(field.isSubTableField){
                    continue;
                }
                newAllField.push(field);
            }
            return newAllField;
        },
        getStrLen : function (str) {
            //中文字符计为2，其他字符计为1
            if (str == null) {
                return 0;
            }
            var len = 0;
            for (var i=0; i<str.length; i++) {
                if (str.charCodeAt(i)>127 || str.charCodeAt(i)==94) {
                    len += 2;
                } else {
                    len ++;
                }
            }
            return len;
        },
        getSubStrLenIndex : function (str) {
            var len = 0;
            for (var i=0; i<str.length; i++) {
                if (str.charCodeAt(i)>127 || str.charCodeAt(i)==94) {
                    len += 2;
                } else {
                    len ++;
                }
                if(len >= 16){
                    return i;
                }
            }
            return 8;
        },
        getModelFieldInfo:function (){
            //修复有时创建者字段是docCreator.fdName时导致的数据不匹配
            if(this.field.indexOf("docCreator") > -1){
                this.field = "docCreator";
            }
            for(var i=0;i < this.modelDict.length;i++){
                if(this.field == this.modelDict[i].field){
                   return  this.modelDict[i];
                }
            }
        }
    });

    exports.DisplayWhereGenerator = DisplayWhereGenerator;
});
