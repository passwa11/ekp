/*初始化授权设置的内容*/
Com_IncludeFile("doclist.js");
function initFdAuthContent(){
	var fdAuthorizeCategory = $("[name='fdAuthorizeCategory']").val();
	if(fdAuthorizeCategory == "0"){
		return;
	}
	var SettingInfoObj = new KMSSData().AddBeanData("lbpmSettingInfoService").GetHashMapArray()[0];
	if(!(SettingInfoObj.businessauth!="true"||SettingInfoObj.personBusinessauth!="true")){//存在业务授权操作
		$("input[name='fdAuthorizeType'][value=4]").parent().hide();
	}
	$("#fdAuthorizedPersonTitle").hide();
	$("#fdAuthorizedPerson").hide();
	$("#lbpmAuthorizeItem")[0].width="85%";
	$("#lbpmAuthorizeItem")[0].colSpan = 3;
	$('#lbpmAuthorizeRow').hide();
	$("#lbpmAuthorizeProcess").show();
	$("#lbpmAuthorizeSetting").show();
	
	//流程信息条件授权 ：需要初始化授权设置
	var fdAuthContents = $("[name='fdAuthContents']").val();
	if(!fdAuthContents){
		return;
	}
	fdAuthContents = eval('(' + fdAuthContents + ')');
	for(var i=0; i<fdAuthContents.length; i++){
		var fdAuthContent = fdAuthContents[i];
		//条件内容
		if(fdAuthContent.fdType == "condition"){
			var trObj = $("#TABLE_Setting_Info").find("tr").eq(i+1);
			initConditionContent(trObj,fdAuthContent);
		}
	}
}

/*初始化条件数据*/
function initConditionContent(trObj,fdAuthContent){
	var fdAuthConditions = fdAuthContent.fdAuthConditions;
	for(var i=0; i<fdAuthConditions.length; i++){
		var fdAuthCondition = fdAuthConditions[i];
		var tdObj = DocListFunc_GetParentByTagName("TD", $(trObj).find(".condition_content")[0]);
		addCondition(null, tdObj, fdAuthCondition);
		$(tdObj).find("select.auth_condition_field:eq("+i+")").val(fdAuthCondition.field.name);
		$(tdObj).find("select.auth_condition_operator:eq("+i+")").val(fdAuthCondition.operator);
		$(tdObj).find("input.fdDisValue:eq("+i+")").val(fdAuthCondition.disValue);
	}
}

/*添加条件*/
function addCondition(obj,tbObj,fdAuthCondition){
	var tbObj = tbObj || DocListFunc_GetParentByTagName("TD", obj);
	var fdScopeFormModelNames = $("[name='fdScopeFormModelNames']").val();
	var modelName = fdScopeFormModelNames.split(";")[0];
	if(!window.currentFields){
		var tempId = getTempId();
		window.currentFields = GetFieldByTempId(tempId);
	}
	if(!window.currentFields || window.currentFields.length <= 0){
		return false;
	}
	var curField = fdAuthCondition ? fdAuthCondition.field : window.currentFields[0];
	var html = "<div class='auth_condition_row'>";
	//字段部分
	html += "<label><select style='color: inherit;' readonly disabled class='auth_condition_field auth_condition_select' onchange='switchConditionField(this)'>";
	for(var index in window.currentFields){
		var field = window.currentFields[index];
		if(field.type.indexOf("[][]") != -1){//不加载二维数组的类型
			continue;
		}
		html += "<option field-type='"+field.type+"' value='"+field.name+"'>"+field.label+"</option>";
	}
	html += "</select></label>";
	//运算符部分
	var operators = getOperatorsByType(curField.type);
	html += "<label><select style='color: inherit;' readonly disabled class='auth_condition_operator'>";
	for(var i=0; i<operators.length; i++){
		html += "<option value='"+operators[i].value+"'>"+operators[i].name+"</option>";
	}
	html += "</select></label>";
	//值部分
	html += "<label class='value_label auth_condition_value'>";
	html += "<input style='color: inherit;' class='inputsgl auth_condition_input fdDisValue' readonly/>";
	html += "</label>";
	html+="</div>";
	$(tbObj).find(".condition_content").append(html);
}

Com_AddEventListener(window, "load", initFdAuthContent);