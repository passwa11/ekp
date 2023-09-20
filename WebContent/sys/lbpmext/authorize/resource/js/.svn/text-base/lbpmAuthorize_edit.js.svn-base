/*
 * 授权方式选择
 * */
function authorizeCatagoryChanged(radioObj) {
	if(radioObj.value == 1){
		if(!(SettingInfoObj.businessauth!="true"||SettingInfoObj.personBusinessauth!="true") && $("[name='method_GET']").val() == "add"){//存在业务授权操作
			$("input[name='fdAuthorizeType'][value=4]").parent().hide();
		}
		//若之前选中的是业务授权，需要先点击授权处理
		if($("[name='fdAuthorizeType']:checked").val() == '4'){
			$("[name='fdAuthorizeType'][value='0']").click();
			$(".lbpm_authorize_row").show();
		}
		$("#fdAuthorizedPersonTitle").hide();
		$("#fdAuthorizedPerson").hide();
		$("#lbpmAuthorizeItem").show();
		$("#lbpmAuthorizeItem")[0].width="85%";
		$("#lbpmAuthorizeItem")[0].colSpan = 3;
		$('#lbpmAuthorizeRow').hide();
		$("#lbpmAuthorizeProcess").show();
		$("#lbpmAuthorizeSetting").show();

		//清除被授权人信息
		var address = Address_GetAddressObj("fdAuthorizedPersonName",0);
		address.emptyAddress(false);

		$("input[data-propertyname='fdAuthorizedPersonName']").attr("validate","");
		$("input[data-propertyname='fdAuthorizedReaderNames']").attr("validate","");
		//恢复流程信息模式的校验
		$("input[name='processNameVal']").attr("validate","required");
		//授权设置里边的校验
		$("#lbpmAuthorizeSetting").find("input[name*='fdDisFormula']").attr("validate","required");
		$("#lbpmAuthorizeSetting").find("input[name*='fdAuthorizedPersonName']").attr("validate","required");
		$("#lbpmAuthorizeSetting").find("input[name*='fdDisValue']").attr("validate","required");
	} else {
		if(!(SettingInfoObj.businessauth!="true"||SettingInfoObj.personBusinessauth!="true") && $("[name='method_GET']").val() == "add"){//错在业务授权操作
			$("input[name='fdAuthorizeType'][value=4]").parent().show();
		}
		$("#fdAuthorizedPersonTitle").show();
		$("#fdAuthorizedPerson").show();
		$("#lbpmAuthorizeItem").show();
		$("#lbpmAuthorizeItem")[0].width="35%";
		$("#lbpmAuthorizeItem").removeAttr('colspan');
		$('#lbpmAuthorizeRow').show();
		$("#lbpmAuthorizeProcess").hide();
		$("#lbpmAuthorizeSetting").hide();

		var fdAuthorizeType = $('input[name="fdAuthorizeType"]:checked').val();
		if(fdAuthorizeType==1){
			$("input[data-propertyname='fdAuthorizedReaderNames']").attr("validate","required");
		}else{
		$("input[data-propertyname='fdAuthorizedPersonName']").attr("validate","required");
		}
		//去除流程信息模式的校验
		$("input[name='processNameVal']").attr("validate","");
		//授权设置里边的校验
		$("#lbpmAuthorizeSetting").find("input[name*='fdDisFormula']").attr("validate","");
		$("#lbpmAuthorizeSetting").find("input[name*='fdAuthorizedPersonName']").attr("validate","");
		$("#lbpmAuthorizeSetting").find("input[name*='fdDisValue']").attr("validate","");
	}
}

/*选择授权流程（单选）*/
function showDocSgl(){
	Dialog_Tree(false, 'processIdVal', 'processNameVal', ';', 'lbpmAuthorizeModelDataService&top=true',
		Data_GetResourceString("sys-lbpmext-authorize:lbpmAuthorize.lbpmAuthorizeScope"),
		null, afterClickedModelDataDialogAction,null, null, null,
		Data_GetResourceString("sys-lbpmext-authorize:lbpmAuthorize.lbpmAuthorizeScope"));
}
/*回调 */
function afterClickedModelDataDialogAction(obj){
	if(obj == null){//取消
		return null;
	}
	if(obj.data && obj.data.length == 0){//取消选定
		delAllSettingInfoRow();
		window.currentFields = null;
		return null;
	}
	var isChange = afterClickedScopeDialogAction(obj);
	if(isChange){
		delAllSettingInfoRow();
		window.currentFields = null;

		//新建一行
		addSettingRow("change");
		var len = $("#TABLE_Setting_Info>tbody>tr:not('.tr_normal_title')").length;
		if(len > 0){
			var index = len-1;
			var obj = $("[name='authContents["+index+"].fdId']")[0];
			if(obj){
				//新建一个条件
				addCondition(obj);
			}
		}
	}
	var scopeTempValuesObj = document.getElementsByName("scopeTempValues")[0];
	var processIdObj = document.getElementsByName("processIdVal")[0];
	scopeTempValuesObj.value = processIdObj.value;
	var processNameObj = document.getElementsByName("processNameVal")[0];
	var fdScopeFormAuthorizeCateShowtextsObj = document.getElementsByName("fdScopeFormAuthorizeCateShowtexts")[0];
	processNameObj.value = fdScopeFormAuthorizeCateShowtextsObj.value.substring(0,fdScopeFormAuthorizeCateShowtextsObj.value.length-1);
}

/*
 * 删除所有设置行
*/
function delAllSettingInfoRow(){
	//清空条件设置行
	var trObjs = $("#TABLE_Setting_Info>tbody>tr:not('.tr_normal_title')");
	for(var i=0; i<trObjs.length; i++){
		DocList_DeleteRow(trObjs[i]);
	}
}

/*添加授权设置行*/
function addSettingRow(type){
	var processId = $("[name='processIdVal']").val();
	if(!processId){
		/*#152443-流程授权多语言处理一下-开始*/
		seajs.use(['lui/dialog', "lang!sys-lbpmext-authorize"],
			function (dialog, addRow) {
				alert(addRow['lbpmAuthorize.addSettingRow']);
			})
		/*#152443-流程授权多语言处理一下-结束*/
		return false;
	}
	var trObj = DocList_AddRow('TABLE_Setting_Info');
	var index = $(trObj).index() - 1;
	$("[name='authContents["+index+"].fdId']").val(getUnid());
	if(type != "change"){
		//新增一个条件
		var obj = $("[name='authContents["+index+"].fdId']")[0];
		if(obj){
			addCondition(obj);
		}
	}
	//移除公式的校验
	$(trObj).find("[name^='authContents'][name$='fdDisFormula']").attr("validate","");
	var fdAuthorizeType = $('input[name="fdAuthorizeType"]:checked').val();
}

//授权设置类型切换
function switchSettingType(obj){
	var tbObj = DocListFunc_GetParentByTagName("TD", obj);
	if(obj.value == 'formula'){
		$(tbObj).find(".formula_content").css("display","block");
		$(tbObj).find(".condition_content").css("display","none");
		var fdFormula = $(tbObj).find(".formula_content").find("[name$='fdFormula']").val();
		var fdDisFormula = $(tbObj).find(".formula_content").find("[name$='fdDisFormula']").val();
		if(fdFormula && !fdDisFormula){
			$(tbObj).find(".formula_content").find("[name$='fdFormula']").val("");//避免条件模式的公式被带到高级模式
		}
		//去掉所有条件的值校验
		var $fdDisValueObjs = $("[name^='fdDisValue']");
		for(var i=0; i<$fdDisValueObjs.length; i++){
			var fdDisValueObj = $fdDisValueObjs[i];
			var advice = $KMSSValidation_GetAdvice(fdDisValueObj);
			if(advice){
				$(advice).remove();
			}
		}

		$(tbObj).find(".add_condition_btn").css("display","none");

		//移除条件的校验
		$(tbObj).find("[name^='fdDisValue']").attr("validate","");
		$(tbObj).find("[name^='authContents'][name$='fdDisValue']").removeAttr("__validate_serial");
		//恢复公式的校验
		$(tbObj).find("[name^='authContents'][name$='fdDisFormula']").attr("validate","required");
	}else{
		$(tbObj).find(".formula_content").css("display","none");
		var advice = $KMSSValidation_GetAdvice($(tbObj).find(".formula_content").find("[name*='fdDisFormula']")[0]);
		if(advice){
			$(advice).remove();
		}

		$(tbObj).find(".condition_content").css("display","block");
		$(tbObj).find(".add_condition_btn").css("display","block");
		//移除公式的校验
		$(tbObj).find("[name^='authContents'][name$='fdDisFormula']").attr("validate","");
		$(tbObj).find("[name^='authContents'][name$='fdDisFormula']").removeAttr("__validate_serial");
		//恢复条件的校验
		$(tbObj).find("[name^='fdDisValue']").attr("validate","required");
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
		alert("没有可以选择的字段！");
		return false;
	}
	var curField = fdAuthCondition ? fdAuthCondition.field : window.currentFields[0];
	var html = "<div class='auth_condition_row'>";
	//字段部分
	html += "<label><select class='auth_condition_field auth_condition_select' onchange='switchConditionField(this)'>";
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
	html += "<label><select class='auth_condition_operator'>";
	for(var i=0; i<operators.length; i++){
		html += "<option value='"+operators[i].value+"'>"+operators[i].name+"</option>";
	}
	html += "</select></label>";
	//值部分
	html += "<label class='value_label auth_condition_value'>";
	html += "</label>";
	//删除按钮部分
	html += "<label class='auth_condition_btn'>";
	html += '<img src="'+Com_Parameter.ContextPath+'resource/style/default/icons/delete.gif" alt="del" onclick="delCondition(this)" style="cursor:pointer">';
	html += "</label>";
	html+="</div>";
	$(tbObj).find(".condition_content").append(html);

	var conditionRows = $(tbObj).find(".condition_content").find(".auth_condition_row");
	var conditionRow = conditionRows[conditionRows.length-1];
	var index = conditionRows.length-1;

	$(conditionRow).attr("data-index",index);

	var srcObj = {
		rtnType:curField.type,
		orgType:curField.orgType,
		isMulti:curField.type.indexOf("[]") != -1 ? true : false,
		idField:"fdValue["+index+"]",
		nameField:"fdDisValue["+index+"]"
	};
	createControl(srcObj,$(conditionRow).find(".value_label")[0],function(c){
		if(c && c.FieldObject && _$validation){
			_$validation.validateElement(c.FieldObject);
		}
	});
}

/**
 * 删除条件
 */
function delCondition(obj){
	var tdObj = DocListFunc_GetParentByTagName("TD", obj);;
	var conditionRow = $(obj).closest(".auth_condition_row");
	var delIndex = $(conditionRow).attr("data-index");
	var len= $(tdObj).find(".auth_condition_row").length;
	var advice = $KMSSValidation_GetAdvice($(conditionRow).find("[name^='fdDisValue']")[0]);
	if(advice){
		advice.remove();//移除校验提示信息
	}
	$(conditionRow).remove();//删除行
	for(var i=parseInt(delIndex)+1; i<len; i++){//后边的行角标依次减一
		var fieldId = $(tdObj).find("[data-index='"+i+"']").find(".auth_condition_field").val();
		var field = getFieldById(fieldId);
		var valLabel = $(tdObj).find("[data-index='"+i+"']").find(".value_label")[0];
		var value = $(valLabel).find("[name^=fdValue]").val();
		var disValue = $(valLabel).find("[name^=fdDisValue]").val();
		var advice = $KMSSValidation_GetAdvice($(valLabel).find("[name^=fdDisValue]")[0]);
		var isValidate = false;
		if(advice){
			advice.remove();//移除校验提示信息
			isValidate = true;
		}
		$(valLabel).empty();
		var index = i-1;
		$(tdObj).find("[data-index='"+i+"']").attr("data-index",index);
		var idField = "fdValue["+index+"]";
		var nameField = "fdDisValue["+index+"]";
		var valObj = {};
		valObj[idField] = value;
		valObj[nameField] = disValue;
		var srcObj = {
			rtnType:field.type,
			orgType:field.orgType,
			isMulti:field.type.indexOf("[]") != -1 ? true : false,
			idField:idField,
			nameField:nameField,
			value:valObj
		};
		createControl(srcObj,valLabel,function(c){
			if(c && c.FieldObject && _$validation){
				_$validation.validateElement(c.FieldObject);
			}
		});
		//重新校验
		if(isValidate){
			_$validation.validateElement($(valLabel).find("[name^=fdDisValue]")[0]);
		}
	}
}

/*切换条件的字段*/
function switchConditionField(obj){
	var tdObj = DocListFunc_GetParentByTagName("TD", obj);
	var conditionRow = $(obj).closest(".auth_condition_row")[0];
	var value = obj.value;
	var optionObj = $(obj).find("option[value='"+value+"']")[0];
	if(optionObj){
		var fieldType = $(optionObj).attr("field-type");
		//运算符
		var operators = getOperatorsByType(fieldType);
		var operatorSelect = $(conditionRow).find("select.auth_condition_operator")[0];
		var html = "";
		for(var i=0; i<operators.length; i++){
			html += "<option value='"+operators[i].value+"'>"+operators[i].name+"</option>";
		}
		$(operatorSelect).html(html);
		//控件部分
		var conditionRows = $(tdObj).find(".condition_content").find(".auth_condition_row");
		var index;
		for(var i=0; i<conditionRows.length; i++){
			if(conditionRows[i] == conditionRow){
				index = i;
				break;
			}
		}
		var field = getFieldById(value);
		var srcObj = {
			rtnType:fieldType,
			orgType:field.orgType,
			isMulti:fieldType.indexOf("[]") != -1 ? true : false,
			idField:"fdValue["+index+"]",
			nameField:"fdDisValue["+index+"]"
		};
		createControl(srcObj,$(conditionRow).find(".value_label")[0],function(c){
			if(c && c.FieldObject && _$validation){
				_$validation.validateElement(c.FieldObject);
			}
		});
	}
}

/*获取对应类型的运算符*/
function getOperatorsByType(type){
	for(var types in operators){
		var arr = types.split("|");
		for(var i=0; i<arr.length; i++){
			if(arr[i] == type){
				return operators[types];
			}
		}
	}
	//取不到时获取默认的
	return operators['default'];
}

/*高级公式*/
function openFormulaDialog(idField, nameField){
	var fdScopeFormModelNames = $("[name='fdScopeFormModelNames']").val();
	var modelName = fdScopeFormModelNames.split(";")[0];
	var tempId = getTempId();
	Formula_Dialog(idField,nameField,GetFieldByTempId(tempId), "Boolean");
}

/*获取行角标*/
function getTrIndex(obj){
	var row = DocListFunc_GetParentByTagName("TR", obj);
	var table = DocListFunc_GetParentByTagName("TABLE", row);

	return DocList_GetRowIndex(table, row);
}

/*提交前格式化数据:授权设置数据*/
function formatDataBeforeSubmit(){
	var fdAuthorizeCategory = $("input[name='fdAuthorizeCategory']:checked").val();
	if(fdAuthorizeCategory == "0"){
		return true;
	}

	if(!validateSetting()){
		return false;
	}

	var $trObjs = $("#TABLE_Setting_Info").find(">tbody>tr:not(.tr_normal_title)");
	var fdAuthContents = [];
	for(var i=0; i<$trObjs.length; i++){
		var fdAuthContent = {};
		var trObj = $trObjs[i];
		var index = $(trObj).index()-1;

		var fdOrder = $(trObj).find(".rowIndex").text();
		fdAuthContent['fdOrder'] = fdOrder;
		var fdId = $("[name='authContents["+index+"].fdId']").val();
		fdAuthContent['fdId'] = fdId;
		var fdType = $("[name='authContents["+index+"].fdType']:checked").val();
		fdAuthContent['fdType'] = fdType;
		if(fdType == 'condition'){
			var conditionContent = getConditionContent(trObj);
			fdAuthContent['fdAuthConditions'] = conditionContent.fdAuthConditions;
			fdAuthContent['fdFormula'] = conditionContent.fdFormula;
		}else{
			var fdFormula = $("[name='authContents["+index+"].fdFormula']").val();
			var fdDisFormula = $("[name='authContents["+index+"].fdDisFormula']").val();
			fdAuthContent['fdFormula'] = fdFormula;
			fdAuthContent['fdDisFormula'] = fdDisFormula;
		}
		//被授权人
		var fdAuthorizedPersonId = $("[name='authContents["+index+"].fdAuthorizedPersonId']").val();
		var fdAuthorizedPersonName = $("[name='authContents["+index+"].fdAuthorizedPersonName']").val();
		fdAuthContent['fdAuthorizedPersonId'] = fdAuthorizedPersonId;
		fdAuthContent['fdAuthorizedPersonName'] = fdAuthorizedPersonName;

		fdAuthContents.push(fdAuthContent);
	}
	$("[name='fdAuthContents']").val(JSON.stringify(fdAuthContents));
	return true;
}

/*提交前进行校验*/
function validateSetting(){
	var $trObjs = $("#TABLE_Setting_Info").find(">tbody>tr:not(.tr_normal_title)");
	if($trObjs.length <= 0){
		alert("授权设置不能为空!");
		return false;
	}

	for(var i=0; i<$trObjs.length; i++){
		var trObj = $trObjs[i];
		var index = $(trObj).index()-1;
		var fdType = $("[name='authContents["+index+"].fdType']:checked").val();
		//移除之前校验的提示信息
		$(trObj).find(".validation-advice").remove();
		if(fdType == 'condition'){
			var $conditionRowDivs = $(trObj).find(".condition_content").find(".auth_condition_row");
			if($conditionRowDivs.length <= 0){
				alert("条件设置不能为空，请检查！");
				return false;
			}
			//移除高级公式的校验
			$(trObj).find("[name^='authContents'][name$='fdDisFormula']").attr("validate","");
			$(trObj).find("[name^='authContents'][name$='fdDisFormula']").removeAttr("__validate_serial");
		}else{
			//
			//移除条件的校验
			$(trObj).find("[name^='fdDisValue']").attr("validate","");
			$(trObj).find("[name^='authContents'][name$='fdDisValue']").removeAttr("__validate_serial");
		}
	}

	if(!_$validation.validate()){
		return false;
	}

	return true;
}

/*获取条件设置的数据*/
function getConditionContent(trObj){
	var $conditionContentDiv = $(trObj).find(".condition_content");
	var $conditionRowDivs = $conditionContentDiv.find(".auth_condition_row");
	var conditionContent = {};
	var fdAuthConditions = [];
	var fdFormula = "";
	$conditionRowDivs.each(function(){
		var fdAuthCondition = {};
		//字段
		var fieldId = $(this).find("select.auth_condition_field").val();
		var field = getFieldById(fieldId);
		fdAuthCondition['field'] = {"name":field.name,"label":field.label,"type":field.type,"orgType":field.orgType};
		//操作
		var operator = $(this).find("select.auth_condition_operator").val();
		fdAuthCondition['operator'] = operator || "";
		//值
		var value = $(this).find("[name^='fdValue']").val();
		var disValue = $(this).find("[name^='fdDisValue']").val();
		fdAuthCondition['value'] = value || "";
		fdAuthCondition['disValue'] = disValue || "";
		fdAuthConditions.push(fdAuthCondition);
		//构建一个公式
		var formula = buildFormulaByCondition(fdAuthCondition);
		if(formula){
			fdFormula ? (fdFormula += " && " + formula) : (fdFormula = formula);
		}
	});
	conditionContent['fdAuthConditions'] = fdAuthConditions;
	conditionContent['fdFormula'] = fdFormula;

	return conditionContent;
}

/*构建公式*/
function buildFormulaByCondition(fdAuthCondition){
	if(!fdAuthCondition){
		return null;
	}
	var formula = "$"+fdAuthCondition.field.name+"$";
	//操作转换
	var type = fdAuthCondition.field.type;
	var value = fdAuthCondition.disValue;
	if(type.indexOf("com.landray.kmss.sys.organization.model") != -1){
		if(type.indexOf("[]") == -1){
			//单值的组织架构类型，要获取id进行比较
			formula += ".fdId";
			value = fdAuthCondition.value;
		}else{
			value = fdAuthCondition.value;
		}
	}else if(type == "Date" || type == "DateTime" || type == "Time"){
		formula += '.getTime()';
		//转换时间为毫秒数
		value =  'Long.valueOf("'+Date.parse(dateParse(value))+'")';
	}else if(type == 'Date[]' || type == "DateTime[]" || type == "Time[]"){
		value = '"' + Date.parse(dateParse(value)) + '"';
	}
	if("contain" == fdAuthCondition.operator){//包含的公式不需要带头部
		formula = "";
	}
	var operators = getOperatorsByType(type);
	for(var i=0; i<operators.length; i++){
		if(operators[i].value == fdAuthCondition.operator){
			var formulaVal = operators[i].formulaVal;
			formulaVal = formulaVal.replace(/\{value\}/g, value);
			formulaVal = formulaVal.replace(/\{field\}/g, "$"+fdAuthCondition.field.name+"$");
			formula += formulaVal;
			if(!operators[i].isFun){
				formula += value;
			}
			break;
		}
	}
	return formula;
}

/**
 * 日期解析，字符串转日期
 * @param dateString 可以为2017-02-16，2017/02/16，2017.02.16
 * @returns {Date} 返回对应的日期对象
 */
function dateParse(dateString){
	var SEPARATOR_BAR = "-";
	var SEPARATOR_SLASH = "/";
	var SEPARATOR_DOT = ".";
	var dateArray;
	if(dateString.indexOf(SEPARATOR_BAR) > -1){
		dateArray = dateString.split(SEPARATOR_BAR);
	}else if(dateString.indexOf(SEPARATOR_DOT) > -1){
		dateArray = dateString.split(SEPARATOR_DOT);
	}
	return dateArray[0]+SEPARATOR_SLASH+dateArray[1]+SEPARATOR_SLASH+dateArray[2];
}

/*根据字段id获取对应的字段*/
function getFieldById(fieldId){
	if(!window.currentFields){
		var tempId = getTempId();
		window.currentFields = GetFieldByTempId(tempId);
	}
	for(var i=0; i<window.currentFields.length; i++){
		var field = window.currentFields[i];
		if(field.name == fieldId){
			return field;
		}
	}
	return null;
}

/*获取唯一id*/
function getUnid(){
	if(!window.unids || window.unids.length <= 1){
		//重新加载
		window.unids = Data_GetRadomId(200);
	}
	var unid = window.unids[0];
	window.unids.splice(0,1);
	while(!unid){
		unid = window.unids[0];
		window.unids.splice(0,1);//避免unid为空导致空id数据在数据库，列表加载不了
	}
	return unid;
}

/*初始化授权设置的内容*/
function initFdAuthContent(){
	if($("[name='method_GET']").val() == "add"){
		//去除流程信息模式的校验
		$("input[name='processNameVal']").attr("validate","");
		//授权设置里边的校验
		$("#lbpmAuthorizeSetting").find("input[name$='fdDisFormula']").attr("validate","");
		$("#lbpmAuthorizeSetting").find("input[name$='fdAuthorizedPersonName']").attr("validate","");
		$("#lbpmAuthorizeSetting").find("input[name$='fdDisValue']").attr("validate","");
		return;//新建不需要初始化
	}
	var fdAuthorizeCategory = $("[name='fdAuthorizeCategory']:checked").val();
	if(fdAuthorizeCategory == "0"){
		//去除流程信息模式的校验
		$("input[name='processNameVal']").attr("validate","");
		//授权设置里边的校验
		$("#lbpmAuthorizeSetting").find("input[name$='fdDisFormula']").attr("validate","");
		$("#lbpmAuthorizeSetting").find("input[name$='fdAuthorizedPersonName']").attr("validate","");
		$("#lbpmAuthorizeSetting").find("input[name$='fdDisValue']").attr("validate","");

		//若选择的是业务授权，则不显示切换的选择
		if($("input[name='fdAuthorizeType']:checked").val() == '4'){
			$("[name='fdAuthorizeCategory'][value='1']").closest("label").hide();
		}
		return;
	}else{
		$("input[data-propertyname='fdAuthorizedPersonName']").attr("validate","");
		$("input[data-propertyname='fdAuthorizedReaderNames']").attr("validate","");
		var fdAuthorizeType = $('input[name="fdAuthorizeType"]:checked').val();
	}

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
		//var trObj = DocList_AddRow('TABLE_Setting_Info');
		/*var index = $(trObj).index()-1;
		//唯一id
		$("[name='authContents["+index+"].fdId']").val(fdAuthContent.fdId);
		//设置方式
		$("[name='authContents["+index+"].fdType']").val(fdAuthContent.fdType);
		//被授权人
		$("[name='authContents["+index+"].fdAuthorizedPersonId']").val(fdAuthContent.fdAuthorizedPersonId);
		$("[name='authContents["+index+"].fdAuthorizedPersonName']").val(fdAuthContent.fdAuthorizedPersonName);*/
		//条件内容
		var trObj = $("#TABLE_Setting_Info").find(">tbody>tr").eq(i+1);
		if(fdAuthContent.fdType == "condition"){
			initConditionContent(trObj,fdAuthContent);
			//移除公式的校验
			$(trObj).find("[name^='authContents'][name$='fdDisFormula']").attr("validate","");
		}else{
			var tdObj = DocListFunc_GetParentByTagName("TD", $(trObj).find(".condition_content")[0]);
			$(tdObj).find(".add_condition_btn").css("display","none");
			//移除条件的校验
			$(trObj).find("[name^='fdDisValue']").attr("validate","");
		}
		/*else{
			$("[name='authContents["+index+"].fdFormula']").val(fdAuthContent.fdFormula);
			$("[name='authContents["+index+"].fdDisFormula']").val(fdAuthContent.fdDisFormula);
		}*/
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
		$(tdObj).find("[name='fdValue["+i+"]']").val(fdAuthCondition.value);
		$(tdObj).find("[name='fdDisValue["+i+"]']").val(fdAuthCondition.disValue);
	}
}

Com_AddEventListener(window, "load", initFdAuthContent);