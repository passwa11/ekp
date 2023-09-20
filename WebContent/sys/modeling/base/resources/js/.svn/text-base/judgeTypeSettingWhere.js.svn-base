/**
 * 条件判断视图的输入控件
 */

define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        topic = require('lui/topic'),
        formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder"),
        relationDiagram = require('sys/modeling/base/mobile/resources/js/relationDiagram'),
        base = require('lui/base');
    var modelingLang = require("lang!sys-modeling-base");
    var fieldOperators = [
        {"name": modelingLang['modelingAppListview.enum.equal'], "value": "!{equal}"},
        {"name": modelingLang['modelingAppListview.enum.notequal'], "value": "!{notequal}"},
        {"name": modelingLang['modelingAppListview.enum.contain'], "value": "!{contain}"}
    ];

    var fieldValueTypes = [
        {"name":modelingLang['modelingAppListview.enum.fix'],  "value":"!{fix}" },
        {"name":modelingLang['modelingAppListview.enum.empty'],  "value":"!{empty}" },
        {"name":modelingLang['modelingAppListview.enum.formula'],  "value":"!{formula}" }
    ];

    var JudgeTypeSettingWhere = base.Component.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.randomName = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
            this.container = cfg.container || null;
            this.storeData = cfg.storeData || "";
            this.spaceType = cfg.spaceType;
            this.field =  this.storeData.field;
            this.fieldType = 'String';
            this.fieldOperator = this.storeData.fieldOperator;
            this.fieldValueType = this.storeData.fieldValueType;
            this.fieldValue = this.storeData.fieldValue;
            this.fieldValueText = this.storeData.fieldValueText || "";
            if(!cfg.modelDict){
                cfg.modelDict = listviewOption.baseInfo.modelDict;
            }
            this.modelDict = this.filterSubTableField(cfg.modelDict);
            this.modelInfo = this.updateModelInfo(this.field);
            this.parent = cfg.parent;
            if(cfg.parent){
                this.channel = cfg.parent.channel;
            }
            this.xformId = cfg.xformId;
        },

        draw : function($super, cfg){
            $super(cfg);
            this.buildWhereContentRule();
            //赋值
            this.initStoreData();

        },

        buildWhereContentRule : function (){
            var self =  this;

            var $whereRule = self.container;

            var $whereRuleField = self.buildWhereRuleField();
            $whereRule.append($whereRuleField);

            var $whereRuleFieldOperator = self.buildWhereRuleFieldOperator();
            $whereRule.append($whereRuleFieldOperator);

            var $whereRuleFieldValueType = self.buildWhereRuleFieldValueType();
            $whereRule.append($whereRuleFieldValueType);

            var $whereRuleFieldValue = self.buildWhereRuleFieldValue();
            $whereRule.append($whereRuleFieldValue);

            var $whereRuleDel = self.buildWhereRuleDel();
            $whereRule.append($whereRuleDel);

            return $whereRule;
        },


        initStoreData : function(){
            var self = this;
            var $whereRule = self.container;
            if(!self.field){
                //初始化
                var $ruleFieldSelect = $whereRule.find(".open_rule_setting_where_rule_field").find("select");
                $ruleFieldSelect.prop("selectedIndex",0);
                $ruleFieldSelect.change();
                return;
            }
            var field = self.field;
            var fieldText = '';
            var fieldOperator = self.fieldOperator;
            var isShowDialogAddress = 'false';
            if(field.indexOf(".fdId") > -1){
                field = field.substring(0,field.indexOf(".fdId"));
                isShowDialogAddress = 'true';
            }
            if(field.indexOf(".fdName") > -1){
                field = field.substring(0,field.indexOf(".fdName"));
            }
            var $whereRuleFieldValue = $whereRule.find('.open_rule_setting_where_rule_field_value');
            for(var i=0;i < self.modelDict.length;i++){
                if(field == self.modelDict[i].field){
                    var fieldType = self.modelDict[i].fieldType;
                    fieldText = self.modelDict[i].fieldText;
                    // 更新运算符
                    var $whereRuleFieldOperatorSelect = $whereRule.find('.open_rule_setting_where_rule_field_operator').find('select');
                    $whereRuleFieldOperatorSelect.empty();
                    var fileOperatorItem = [];
                    if('false' == isShowDialogAddress && ('com.landray.kmss.sys.organization.model.SysOrgPerson' == fieldType
                        || 'com.landray.kmss.sys.organization.model.SysOrgElement' == fieldType)){
                        fileOperatorItem = relationDiagram.getDiagram("where",'String').operator;
                    }else{
                        fileOperatorItem = relationDiagram.getDiagram("where",fieldType).operator;
                    }
                    for(var j = 0;j < fileOperatorItem.length;j++){
                        $whereRuleFieldOperatorSelect.append("<option value='"+ fileOperatorItem[j].value +"'>"+ fileOperatorItem[j].name +"</option>");
                    }
                    $whereRuleFieldOperatorSelect.val(fieldOperator);

                    $whereRuleFieldValue.empty();
                    $whereRuleFieldValue.append(self.fixDraw(isShowDialogAddress,self.modelDict[i],fieldOperator));
                    break;
                }
            }

            var $whereRuleField = $whereRule.find('.open_rule_setting_where_rule_field');
            $whereRuleField.find('select').val(self.field);
            //显示值过长
            $whereRuleField.find('select').attr("title",fieldText);

            var fieldValueType = self.fieldValueType;
            var $whereRuleFieldValueType = $whereRule.find('.open_rule_setting_where_rule_field_value_type');
            $whereRuleFieldValueType.find('select').val(fieldValueType);
            if('!{empty}' == fieldValueType){
                $whereRuleFieldValue.empty();
                $whereRuleFieldValue.append(self.nullDraw());
            }else if('!{formula}' == fieldValueType){
                $whereRuleFieldValue.empty();
                $whereRuleFieldValue.append(self.formulaDraw(fieldType));
            }

            var fieldValue = self.fieldValue;
            var fieldValueText = self.fieldValueText;
            if('!{empty}' != fieldValueType){
                var $input = $whereRule.find('.open_rule_setting_where_rule_field_value').find('input');
                if('radio' == $input.attr('type')){
                    $input.each(function () {
                        if(fieldValue == $(this).val()){
                            $(this).prop("checked",true);
                        }
                    })
                }else if('checkbox' == $input.attr('type')){
                    var fieldValueArr=fieldValue.split(';');
                    $input.each(function () {
                        for(var i=0;i<fieldValueArr.length;i++) {
                            if(fieldValueArr[i] == $(this).val()){
                                $(this).prop("checked",true);
                            }
                        }
                    })
                }else if('hidden' == $input.attr('type')){
                    $input.eq(0).val(fieldValue);
                    $input.eq(1).val(fieldValueText);
                }else{
                    $input.val(fieldValue);
                }
            }else{
                //空值，更新绘制运算符
                self.updateOperatorTdByFieldValue($whereRule, '!{empty}');
            }

        },


        buildWhereRuleField : function () {
            var self =  this;
            //字段
            var $whereRuleField = $("<div class='open_rule_setting_where_rule_field'></div>");
            var $whereRuleFieldSelect = $("<select class='inputsgl'/>");
            for (var i = 0; i < self.modelDict.length; i++) {
                var option =  self.modelDict[i];
                var fieldType = option.fieldType;
                var showText = option.fieldText;
                if('com.landray.kmss.sys.organization.model.SysOrgPerson' == fieldType
                    || 'com.landray.kmss.sys.organization.model.SysOrgElement' == fieldType){
                    var showTextId = showText + '.ID';
                    var showTextName = showText + '.名称';
                    if(self.getStrLen(showTextName)>16){
                        showTextId = showTextId.substring(0,self.getSubStrLenIndex(showTextId))+'...';
                        showTextName = showTextName.substring(0,self.getSubStrLenIndex(showTextName))+'...';
                    }
                    $whereRuleFieldSelect.append($("<option value='"+option.field+".fdId' title='"+showText+".ID'>" + showTextId + "</option>"));
                    $whereRuleFieldSelect.append($("<option value='"+option.field+".fdName' title='"+showText+".名称'>" + showTextName + "</option>"));
                }else{
                    if(self.getStrLen(showText)>16){
                        showText = showText.substring(0,self.getSubStrLenIndex(showText))+'...';
                    }
                    $whereRuleFieldSelect.append($("<option value="+option.field+" title='"+option.fieldText+"'>" + showText + "</option>"));
                }
            }

            $whereRuleFieldSelect.on("change",function(){
                //显示值过长
                $(this).attr("title",$(this).find("option:selected").attr('title'));

                var $whereRule = $(this).parents('.open_rule_setting_where_rule');
                var $whereRuleFieldOperatorSelect = $whereRule.find('.open_rule_setting_where_rule_field_operator').find('select');
                $whereRuleFieldOperatorSelect.empty();
                self.fieldOperator = '!{equal}';
                var $fileValueType = $whereRule.find('.open_rule_setting_where_rule_field_value_type').find('select');
                $fileValueType.val('!{fix}');
                var $whereRuleFieldValue = $whereRule.find('.open_rule_setting_where_rule_field_value');
                $whereRuleFieldValue.empty();
                var isShowDialogAddress = 'false';
                var field = $(this).val();
                for(var i=0;i < self.modelDict.length;i++){
                    if(field.indexOf(".fdId") > -1){
                        field = field.substring(0,field.indexOf(".fdId"));
                        isShowDialogAddress = 'true';
                    }
                    if(field.indexOf(".fdName") > -1){
                        field = field.substring(0,field.indexOf(".fdName"));
                    }
                    if(field == self.modelDict[i].field){
                        var fieldType = self.modelDict[i].fieldType;
                        self.field = field;
                        self.fieldType = fieldType;
                        self.modelInfo = self.modelDict[i];
                        // 更新运算符
                        var fileOperatorItem = [];
                        if('false' == isShowDialogAddress && ('com.landray.kmss.sys.organization.model.SysOrgPerson' == fieldType
                            || 'com.landray.kmss.sys.organization.model.SysOrgElement' == fieldType)){
                            fileOperatorItem = relationDiagram.getDiagram("where",'String').operator;
                        }else{
                            fileOperatorItem = relationDiagram.getDiagram("where",fieldType).operator;
                        }
                        for(var j = 0;j < fileOperatorItem.length;j++){
                            $whereRuleFieldOperatorSelect.append("<option value='"+ fileOperatorItem[j].value +"'>"+ fileOperatorItem[j].name +"</option>");
                        }
                        //更新输入值
                        $whereRuleFieldValue.append(self.fixDraw(isShowDialogAddress,self.modelInfo,self.fieldOperator));
                        break;
                    }
                }
            });

            $whereRuleField.append($whereRuleFieldSelect);
           return $whereRuleField;
        },

        buildWhereRuleFieldOperator : function () {
            var self = this;
            //比较类型
            var $whereRuleFieldOperator = $("<div class='open_rule_setting_where_rule_field_operator'></div>");
            var $whereRuleFieldOperatorSelect = $("<select class='inputsgl' />");
            for (var i = 0; i < fieldOperators.length; i++) {
                var option =  fieldOperators[i];
                var $whereRuleFieldOperatorOption = $("<option value="+option.value+">" + option.name + "</option>");
                $whereRuleFieldOperatorSelect.append($whereRuleFieldOperatorOption);
            }
            $whereRuleFieldOperator.append($whereRuleFieldOperatorSelect);
            $whereRuleFieldOperatorSelect.on("change",function(){
                var $whereRule = $(this).parents('.open_rule_setting_where_rule');
                var $whereRuleFieldSelect = $whereRule.find('.open_rule_setting_where_rule_field').find('select');
                var isShowDialogAddress = 'false';
                var field = $whereRuleFieldSelect.val();
                if(field){
                    if(field.indexOf(".fdId") > -1){
                        field = field.substring(0,field.indexOf(".fdId"));
                        isShowDialogAddress = 'true';
                    }
                    if(field.indexOf(".fdName") > -1){
                        field = field.substring(0,field.indexOf(".fdName"));
                    }
                    var obj;
                    for(var i=0;i < self.modelDict.length;i++) {
                        if (field == self.modelDict[i].field) {
                            obj = self.modelDict[i];
                        }
                    }

                    self.fieldOperator = $(this).val();

                    var $whereRuleFieldValueType = $whereRule.find('.open_rule_setting_where_rule_field_value_type').find('select');
                    if('!{fix}' == $whereRuleFieldValueType.val()){
                        if('!{contain}' == $(this).val() && obj.fieldType === "enum") {
                            var $whereRuleFieldValue = $whereRule.find('.open_rule_setting_where_rule_field_value');
                            $whereRuleFieldValue.empty();
                            $whereRuleFieldValue.append(self.fixDraw(isShowDialogAddress,obj,'!{contain}'));
                        }else if(obj.fieldType === "enum"){
                            var $whereRuleFieldValue = $whereRule.find('.open_rule_setting_where_rule_field_value');
                            $whereRuleFieldValue.empty();
                            $whereRuleFieldValue.append(self.fixDraw(isShowDialogAddress,obj,self.fieldOperator));
                        }
                    } else if('!{empty}' == $whereRuleFieldValueType.val()){
                        if('!{contain}' == $(this).val() || '!{notContain}' == $(this).val()
                            || '!{less2}' == $(this).val() || '!{more2}' == $(this).val()
                            || '!{lessEqual2}' == $(this).val() || '!{moreEqual2}' == $(this).val()) {
                            self.updateOperatorTdByFieldValue($whereRule, '!{empty}');
                        }
                    }

                }
            });
            return $whereRuleFieldOperator;
        },

        buildWhereRuleFieldValueType : function () {
            var self =  this;
            //值类型
            var $whereRuleFieldValueType = $("<div class='open_rule_setting_where_rule_field_value_type'></div>");
            var $whereRuleFieldValueTypeSelect = $("<select class='inputsgl'  />");
            for (var i = 0; i < fieldValueTypes.length; i++) {
                var option =  fieldValueTypes[i];
                var $whereRuleFieldValueTypeOption = $("<option value="+option.value+">" + option.name + "</option>");
                $whereRuleFieldValueTypeSelect.append($whereRuleFieldValueTypeOption);
            }
            $whereRuleFieldValueTypeSelect.on("change",function(){
                var $whereRule = $(this).parents('.open_rule_setting_where_rule');
                var $whereRuleFieldSelect = $whereRule.find('.open_rule_setting_where_rule_field').find('select');
                var isShowDialogAddress = 'false';
                var field = $whereRuleFieldSelect.val();
                if(field) {
                    if (field.indexOf(".fdId") > -1) {
                        field = field.substring(0, field.indexOf(".fdId"));
                        isShowDialogAddress = 'true';
                    }
                    if (field.indexOf(".fdName") > -1) {
                        field = field.substring(0, field.indexOf(".fdName"));
                    }
                    var obj;
                    for (var i = 0; i < self.modelDict.length; i++) {
                        if (field == self.modelDict[i].field) {
                            obj = self.modelDict[i];
                        }
                    }

                    var $whereRuleFieldValue = $whereRule.find('.open_rule_setting_where_rule_field_value');
                    $whereRuleFieldValue.empty();

                    if('!{fix}'  == $(this).val()){
                        var $whereRuleFieldOperator = $whereRule.find('.open_rule_setting_where_rule_field_operator');
                        var  $whereRuleFieldOperatorSelect = $whereRuleFieldOperator.find("select");
                        if('!{contain}' == $whereRuleFieldOperatorSelect.val() && obj.fieldType === "enum") {
                            $whereRuleFieldValue.append(self.fixDraw(isShowDialogAddress,obj,'!{contain}'));
                        }else if('!{notContain}' == $whereRuleFieldOperatorSelect.val() && obj.fieldType === "enum"){
                            $whereRuleFieldValue.append(self.fixDraw(isShowDialogAddress,obj,"!{notContain}"));
                        }else{
                            $whereRuleFieldValue.append(self.fixDraw(isShowDialogAddress,obj,''));
                        }
                    }else if('!{empty}'  == $(this).val()){
                        $whereRuleFieldValue.append(self.nullDraw());
                    }else if('!{formula}'  == $(this).val()){
                        $whereRuleFieldValue.append(self.formulaDraw(self.fieldType));
                    }
                    self.updateOperatorTdByFieldValue($whereRule,$(this).val());
                }

            });
            $whereRuleFieldValueType.append($whereRuleFieldValueTypeSelect);

            return $whereRuleFieldValueType;
        },

        buildWhereRuleFieldValue : function () {
            //值
            var $whereRuleFieldValue = $("<div class='open_rule_setting_where_rule_field_value'></div>");
            var $input = $("<input type='text' class='inputsgl' style='width: 100%;'/>");
            $whereRuleFieldValue.append($input);

            return $whereRuleFieldValue;
        },

        buildWhereRuleDel : function () {
            var self =  this;
            var $whereRuleDel = $("<div class='open_rule_setting_where_rule_del'><i></i></div>");
            $whereRuleDel.on("click",function () {
                if(self.spaceType){
                    //业务空间新加
                    self.delItem4Space(this);
                }else{
                    self.delItem(this);
                }
            });
           return $whereRuleDel;
        },
        delItem4Space : function(dom){
            var $item = $(dom).parent(".open_rule_setting_where_rule");
            var curIndex = $item.attr("index");
            var luiId = $item.parents("[data-lui-cid]").eq(0).attr("data-lui-cid");
            var kClassParent = LUI(luiId);
            var statisticsRuleSettingWhereWgts = kClassParent.statisticsRuleSettingWhereWgts;
            var wgt = statisticsRuleSettingWhereWgts[curIndex];
            $item.remove();
            wgt.destroy();
            statisticsRuleSettingWhereWgts.splice(curIndex,1);
            return;
        },

        //更新运算符
        updateOperatorTdByFieldValue :function($whereRule,fieldValue) {
            var $whereRuleFieldOperatorSelect = $whereRule.find('.open_rule_setting_where_rule_field_operator').find('select');
            if(fieldValue === '!{empty}'){
                this.fieldOperator = '!{equal}';
                //当为空值时，只有运算符等于和不等于，其他隐藏
                $whereRuleFieldOperatorSelect.find("option").each(function () {
                    if($(this).val() != "="
                        && $(this).val() != "!="
                        && $(this).val() != "!{equal}"
                        && $(this).val() != "!{notequal}"
                        && $(this).val() != "eq" ){
                        $(this).css("display","none");
                    }
                });
                //为空值时，需要重新设置运算符下拉框选中等于
                var operator = $whereRuleFieldOperatorSelect.find("option:selected").val();
                if(operator != "="
                    && operator != "!="
                    && operator != "!{equal}"
                    && operator != "!{notequal}"
                    && operator != "eq" ){
                    $whereRuleFieldOperatorSelect.prop("selectedIndex",0);
                }
            }else{
                //为其他类型时，将隐藏的运算符重新显示出来
                $whereRuleFieldOperatorSelect.find("option").each(function () {
                    if($(this).css("display") == "none"){
                        $(this).css("display","");
                    }
                });
            }

        },

        fixDraw : function(isShowDialogAddress,info,operator){
            var html = "";
            var fieldType = info.fieldType;
            // 约定：如果是字段为地址本的ID，则用地址本控件显示
            if('true' == isShowDialogAddress){
                var orgType = info.orgType;
                var orgSelectType = "ORG_TYPE_ALL";
                if(orgType === "ORG_TYPE_PERSON"){
                    orgSelectType = "ORG_TYPE_PERSON";
                } else if(orgType === "ORG_TYPE_ORG|ORG_TYPE_DEPT"){
                    orgSelectType = "ORG_TYPE_DEPT";
                }
                html += "<div class='inputselectsgl' style='height: 28px !important;margin: 8px 0px 8px 0px;' onclick='Dialog_Address(true, \"" + this.randomName + "_fieldInputValue\",\"" + this.randomName + "_fieldInputValue_name\", \";\", " + orgSelectType + ");'>" +
                    "<input name='" + this.randomName + "_fieldInputValue' type='hidden'>" +
                    "<div class='input'><input style='height: 28px !important;' placeholder='"+modelingLang['modeling.page.choose']+"' name='" + this.randomName + "_fieldInputValue_name' type='text' readonly></div>" +
                    "<div class='selectitem'></div>" +
                    "</div>";
            }else{
                if(fieldType === "String"){
                    html += "<input type='text' name='"+ this.randomName +"_fieldInputValue' class='inputsgl' style='width: 100%;' />";
                }else if(fieldType === 'com.landray.kmss.sys.organization.model.SysOrgPerson'
                    || fieldType === 'com.landray.kmss.sys.organization.model.SysOrgElement'){
                    html += "<input type='text' name='"+ this.randomName +"_fieldInputValue' class='inputsgl' style='width: 100%;' />";
                }else if(fieldType === "enum"){
                    html += this.getEnumHtml(info.enumValues, this.randomName + "_fieldInputValue",operator,info.businessType);
                }else if(fieldType === "Date" || fieldType === "DateTime" || fieldType === "Time"){
                    //时间控件
                    var functionName = "triggleSelectdatetime(event,this,'"+fieldType+"','"+this.randomName + "_fieldInputValue');";
                    var validateName = "__" + fieldType.toLowerCase();
                    html += "<div class='inputselectsgl ' style='width:100%;height: 28px !important;margin: 8px 0px 8px 0px;' onclick=\"" + functionName + "\">";
                    html += "<div class='input'><input style='height: 28px !important;' name='" + this.randomName + "_fieldInputValue' type='text' validate='" + validateName + "'></div>";
                    html += "<div class='inputdatetime'></div>";
                    html += "</div>";
                }else if(fieldType === "BigDecimal" || fieldType === "Double"){
                    var functionName = "statisticsValidateNum(this);";
                    html += "<input type='text' name='"+ this.randomName +"_fieldInputValue' onblur='"+functionName+"'  class='inputsgl' style='width: 100%;' />";
                }
            }
            return html;
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

        updateModelInfo : function(field){
            var self = this;
            for(var i=0;i < self.modelDict.length;i++){
                if(field == self.modelDict[i].field){
                    self.modelInfo = self.modelDict[i];
                    break;
                }
            }
        },

        nullDraw : function(){
            return "<div style='width: 150px;height: 30px;\n" +
                "    margin: 8px 0 8px 8px;'><div>";
        },

        formulaDraw : function(fieldType){
            formulaBuilder.initFieldList(this.xformId,true);
            var $formula = formulaBuilder.get(this.randomName +"_fieldInputValue", fieldType);
            $formula.css( {
                    "margin":"8px 0 8px 0",
                }
            );
            $formula.find("input").addClass("inputCover positionCover");
            $formula.find("input").css(
                {
                    "width":"128px"
                }
            );

            return $formula;
        },

        getEnumHtml : function(options, name, operator,businessType){
            var html = "";
            var type = "radio";
            if(operator == '!{contain}' || operator == "!{notContain}"
                || businessType == "inputCheckbox" || businessType == "fSelect"){
                type = "checkbox";
            }
            for(var i = 0; i < options.length;i++){
                html += "<div class='enum_div'><input type='"+type+"' name='"+ name +"'";
                html += " value='" + options[i].fieldEnumValue + "'>"+ options[i].fieldEnumLabel +"</div>";
            }
            return html;
        },

        delItem : function (dom) {
            var $item = $(dom).parent(".open_rule_setting_where_rule");
            var curIndex = $item.attr("index");
            var luiId = $item.parents("[data-lui-cid]").eq(0).attr("data-lui-cid");
            var kClassParent = LUI(luiId);
            var judgeTypeSetting = kClassParent.judgeTypeSetting;
            var judgeTypeSettingWhereWgts = judgeTypeSetting.judgeTypeSettingWhereWgts;
            var wgt = judgeTypeSettingWhereWgts[curIndex];
            $item.remove();
            wgt.destroy();
            judgeTypeSettingWhereWgts.splice(curIndex,1);
            return;
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


        getKeyData : function (){
            var keyData = {};
            var $whereRule  = $(this.container);
            var field = $whereRule.find('.open_rule_setting_where_rule_field').find('select').val();
            keyData.field = field;
            var fieldOperator = $whereRule.find('.open_rule_setting_where_rule_field_operator').find('select').val();
            keyData.fieldOperator = fieldOperator;
            var fieldValueType = $whereRule.find('.open_rule_setting_where_rule_field_value_type').find('select').val();
            keyData.fieldValueType = fieldValueType;
            if('!{empty}' == fieldValueType){
                keyData.fieldValue = null;
                keyData.fieldValueText = null;
            }else{
                var $input = $whereRule.find('.open_rule_setting_where_rule_field_value').find('input');
                if("radio" == $input.attr("type")){
                    $input.each(function () {
                       if(true == $(this).prop("checked")){
                           keyData.fieldValue = $(this).val();
                           keyData.fieldValueText = $(this).val();
                       }
                    });
                }else if("checkbox" == $input.attr("type")){
                    var val = '';
                    var text = '';
                    $input.each(function () {
                        if(true == $(this).prop("checked")){
                            val = val + $(this).val() + ";";
                            text = text + $(this).val() + ";";
                        }
                    });
                    keyData.fieldValue = val;
                    keyData.fieldValueText = text;
                }else if("hidden" == $input.attr("type")){
                    keyData.fieldValue = $input.eq(0).val();
                    keyData.fieldValueText = $input.eq(1).val();
                }else{
                    keyData.fieldValue = $input.val();
                    keyData.fieldValueText = $input.val();
                }
            }
            return keyData;
        }
    })

    module.exports = JudgeTypeSettingWhere;
})