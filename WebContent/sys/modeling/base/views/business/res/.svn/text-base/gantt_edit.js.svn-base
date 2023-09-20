//默认值
//用于设置radio的name后缀，避免单选的name不能唯一
var _xform_main_data_insystem_radioNameNum = 0;
var _xform_main_data_insystem_fieldFixed = xform_main_data_getEnumType("inputValue")[0].value;
var xform_main_data_insystem_whereSelectFieldInputName = "fdWhereSelectFieldInput";
// 全局变量，存储当前model的信息
var insystemContext = new Insystem_Context();
//增加预定义查询
window.xform_main_data_addWhereItem = function(selectedItem,currentDom, type){
	var $selectTable = $(currentDom).closest(".model-query-cont").find(".model-edit-view-oper-content-table");
	var rowIndex = $selectTable.find("tr.whereTr").length+1;
	var trObj = $("<tr class='whereTr'>");
	var html = "";
	html += "<td><div class='model-edit-view-oper'>";
	//头部
	html += "<div class='model-edit-view-oper-head'>";
	html += "<div class='model-edit-view-oper-head-title'><div onclick='changeToOpenOrClose(this)'><i class='open'></i></div><span>"+ganttOption.lang.sortItem+"<span class='title-index'>"+(rowIndex-1)+"</span></span></div>";
	html += "<div class='model-edit-view-oper-head-item'><div class='del' onclick='updateRowAttr(0,\"no-position\",this);xform_main_data_delTrItem(this);'><i></i></div><div class='down' onclick='xform_main_data_moveTr_new(1,this);updateRowAttr(1,\"no-position\",this)'><i></i></div><div class='up' onclick='xform_main_data_moveTr_new(-1,this);updateRowAttr(-1,\"no-position\",this)'><i></i></div></div>";
	html += "</div>";
	//内容
	html += "<div class='model-edit-view-oper-content'>";
	html += "<ul>";
	//查询类型
	var showPredefined = type === '1';
	html += getWhereTypeHtml(type);
	html += getCustomWhereTypeHtml(selectedItem, !showPredefined);
	html += getPredefinedWhereTypeHtml(selectedItem, showPredefined);
	html += "</ul>";
	html += "</div></div></td>";
	trObj.append(html);
	$selectTable.append(trObj);
	//控制 内置查询项 与 预定义查询项 的显示
	$selectTable.find('.model-edit-view-oper-content-item').each(function () {
		var name = $(this).attr('name');
		if(name === 'whereTypeLi')
			return;
		if ((name === 'predefinedLi' && type === '1')
			|| (name !== 'predefinedLi' && type === '0'))
			$(this).removeClass("liHidden");
		else
			$(this).addClass("liHidden");
	});
	//更新角标
	var index = $selectTable.find("> tbody > tr").last().find(".title-index").text();
	$selectTable.find("> tbody > tr").last().find(".title-index").text(parseInt(index)+1);
	//更新向下的图标
	$selectTable.find("> tbody > tr").last().prev("tr").find("div.down").show();
	$(trObj).find("div.down").hide();
	//修改默认标题
	if(showPredefined)
		changeUlTitle($(trObj).find('select[name="fdPredefinedWhereType"]'));
	else
		changeUlTitle($(trObj).find('select[name="fdAttrField"]'));
}

/**
 * 绘制预定义查询-自定义类型的html
 */
function getCustomWhereTypeHtml(selectedItem, show) {
	var clazz = show ? "" : "liHidden";
	var html = "";
	//过滤明细表的属性
	var dictData = window.filterSubTableField(insystemContext.filterDictData);
	if(!dictData||dictData.length == 0){
		//初始化数据字典变量
		var modelDict = ganttOption.modelingGanttForm.modelDict;
		modelDict = modelDict.replace(/&quot;/g,"\"");
		if(modelDict) {
			var dictJSON = $.parseJSON(getValidJSONArray(modelDict));
			insystemContext.clear();
			insystemContext.initialize(dictJSON);
			dictData = window.filterSubTableField(insystemContext.filterDictData);
		}
	}
	//字段
	html += "<li class='model-edit-view-oper-content-item first-item "+clazz+"'><div class='item-title'>"+ganttOption.lang.field+"</div>";
	html += "<div class='item-content'>";
	html += xform_main_data_getFieldOptionEditHtml(dictData, 'fdAttrField', null, selectedItem == null ? null : selectedItem.field, selectedItem == null ? null : selectedItem);
	html += "</div></li>";
	//运算符
	html += "<li class='model-edit-view-oper-content-item "+clazz+"'><div class='item-title'>"+ganttOption.lang.fdOperator+"</div>";
	html += "<div class='item-content'>";
	if (selectedItem) {
		html += xform_main_data_getOperatorOptionHtml(selectedItem.fieldType, selectedItem.fieldOperator, selectedItem);
	} else {
		html += xform_main_data_getOperatorOptionHtml(insystemContext.dictData);
	}
	html += "</div></li>";
	//值
	html += "<li class='model-edit-view-oper-content-item last-item "+clazz+"'><div class='item-title'>"+ganttOption.lang.fdValue+"</div>";
	html += "<div class='item-content'>";
	if (selectedItem) {
		html += xform_main_data_getFieldvalueOptionHtml(selectedItem.fieldType, selectedItem);
	} else {
		html += xform_main_data_getFieldvalueOptionHtml(insystemContext.dictData);
	}
	html += "</div></li>";
	return html;
}

/**
 * 绘制预定义查询-内置类型的html
 */
function getPredefinedWhereTypeHtml(selectedItem, show) {
	var value = '';
	if(selectedItem)
		value = selectedItem.predefined;
	var clazz = show ? '' : 'liHidden';
	var html = "";
	html += "<li class='model-edit-view-oper-content-item first-item last-item "+clazz+"' name='predefinedLi'><div class='item-title'>"+ganttOption.lang.builtInQuery+"</div>";
	html += "<div class='item-content'>";
	//下拉框
	html += "<select name='fdPredefinedWhereType' class='inputsgl' onchange='changePredefined(this)' style='margin:0 4px;width:100%'>";

	var selectArray = xform_main_data_getEnumType("predefindedWhereType");
	for(var i = 0;i < selectArray.length; i++){
		var json = selectArray[i];
		if(json.value){
			//无流程文档只可选择'我创建的'
			if(ganttOption.modelingGanttForm.fdEnableFlow !== 'true' && json.value !== "create") {
				continue;
			}
			html += "<option value='" + json.value + "'";
			if(value && value === json.value){
				html += " selected='selected'";
			}
			html += ">" + json.text + "</option>";
		}
	}
	html += "</select>";
	html += "</div></li>";
	return html;
}

//预定义查询类型
function getWhereTypeHtml(value) {
	html = "<li class='model-edit-view-oper-content-item first-item' style='display:none;' name='whereTypeLi'><div class='item-title'>"+ganttOption.lang.type+"</div>";
	html += "<div class='item-content'>";
	html += "<input type='hidden' name='fdWhereType' value='" + value + "'/>" +
		"<div class='view_flag_radio' style='display: inline-block;'>" +
		"<div class='view_flag_radio_no' style='display: inline-block;cursor: pointer;'  onclick='changeFdWhereType(this,\"fdWhereType\",0)'><i class='view_flag_no " + (value === '1' ? '' : 'view_flag_yes') + "'></i>"+ganttOption.lang.customQueryItem+"</div>" +
		"<div class='view_flag_radio_yes view_flag_last' style='display:inline-block;cursor: pointer;'  onclick='changeFdWhereType(this,\"fdWhereType\",1)'><i class='view_flag_no " + (value === '1' ? 'view_flag_yes' : '') + "'></i>"+ganttOption.lang.builtInQueryItem+"</div>" +
		"</div>";
	html += "</div></li>";
	return html;
}

//运算符
function xform_main_data_getOperatorOptionHtml(data,value,selectedItem){
	var type;
	if(typeof(data) == "string" && data.constructor == String){
		type = data.toLowerCase();
	}else{
		// 每次新增必定是第一个被选中
		type = data[0].fieldType.toLowerCase();
	}
	var html = "";
	//对象的情况
	if(type && type.indexOf(_xform_main_data_insystem_split) > -1){
		var lastIndex = type.lastIndexOf(_xform_main_data_insystem_split);
		type = type.substring(lastIndex + 1 , type.length);
	}
	html += "<div class='xform_main_data_operatorWrap'>";

	if(selectedItem && selectedItem.field && selectedItem.field.indexOf('|fdId') !== -1 && selectedItem.orgType) {
		if (selectedItem.orgType === "ORG_TYPE_ORG|ORG_TYPE_DEPT") {
			//部门类型 运算符位置显示'包含子部门'选项
			var checked = selectedItem.isIncludeSub === 'true' ? "checked='checked'" : "";
			html += '<label><input type="checkbox" name="isIncludeSub" value="true" ' + checked + '/>包含子部门</label>';
		}else{
			//其它组织架构运算符限定为'等于'
			var operatorArray = xform_main_data_getOperatorByType(type);
			operatorArray = [operatorArray[0],operatorArray[1]];		//#150803 地址本类型增加了不等于：operatorArray[1]
			html += xform_main_data_buildOperatorSelectHtml(operatorArray,value,type);
		}
	} else {
		var operatorArray = xform_main_data_getOperatorByType(type);
		html += xform_main_data_buildOperatorSelectHtml(operatorArray,value,type);
	}
	html += "</div>";
	return html;
}

//根据类型返回对应的运算符
function xform_main_data_getOperatorByType(type){
	if(type != null){
		type = type.toLowerCase();
		var operatorArray = [];
		if(type == "string" || type == "rtf"){
			operatorArray = xform_main_data_getEnumType("operatorString");
		}else if(type == "long" || type == "integer" || type == "double" || type == "bigdecimal"){
			operatorArray = xform_main_data_getEnumType("operatorNum");
		}else if(type == "datetime" || type == "date" || type == "time"){
			operatorArray = xform_main_data_getEnumType("operatorTime");
		}else if(type == "boolean"){
			operatorArray = xform_main_data_getEnumType("operatorEnum");
		}else if(type.indexOf("com.landray.kmss") > -1){

		}else{
			// 无法匹配的，统一按字符串处理
			operatorArray = xform_main_data_getEnumType("operatorString");
		}
		return operatorArray;
	}
}

//获取enum type根据ListviewEnumUtil getAllEnum（）里面的key匹配
function xform_main_data_getEnumType(type){
	if(_main_data_insystem_enumCollection.hasOwnProperty(type)){
		return _main_data_insystem_enumCollection[type];
	}
	return [];
}

//构建运算符的HTML
function xform_main_data_buildOperatorSelectHtml(operatorArray,value,type){
	var html = "";
	html += "<select name='fdWhereSelectFieldOperator' class='inputsgl' style='margin:0 4px;width:100%' onchange=\"xform_main_data_changeFieldValue(this);\">";
	if((value == "enum" || type == "enum" )&& operatorArray.length < 4){
		operatorArray.push({
			"value":"!{notContain}",
			"text":ganttOption.lang.notContain
		})
	}else if(value == "string" && operatorArray.length >= 4){
		for(var i = 0; i < operatorArray.length;i++){
			if(operatorArray[i].value == "!{notContain}"){
				operatorArray.splice(i,1);
			}
		}
	}
	for(var i = 0;i < operatorArray.length; i++){
		var operatorJson = operatorArray[i];
		if(operatorJson.value){
			html += "<option value='" + operatorJson.value + "'";
			if(value != null && value == operatorJson.value){
				html += " selected='selected'";
			}
			html += ">" + operatorJson.text + "</option>";
		}
	}
	html += "</select>";
	return html;
}

//构建输入值HTML
function xform_main_data_getFieldvalueOptionHtml(data,value){
	var html = "";
	var optionValueArray = xform_main_data_getEnumType("inputValue");
	html += "<div class='xform_main_data_fieldWrap' style='position:relative;'>";
	html += "<select name='fdWhereSelectValue' onchange='xform_main_data_changeFieldValue(this);' class='inputsgl' style='margin:0 4px;width:30%'>";
	for(var i = 0;i < optionValueArray.length; i++){
		html += "<option value='" + optionValueArray[i].value + "'";
		if(value && optionValueArray[i].value == value.fieldValue){
			html += " selected='selected'";
		}
		html += ">"+ optionValueArray[i].text +"</option>";
	}
	html += "</select>";
	html += "<div class='xform_main_data_fieldDomWrap input_radio height28' style='bottom: 1px; display: inline-block;width:64%;word-break: break-all;white-space: normal;'>";
	var type,field;
	if(typeof(data) == "string" && data.constructor == String){
		type = data.toLowerCase();
		field = value.field;
	}else{
		// 每次新增必定是第一个被选中
		type = data[0].fieldType.toLowerCase();
		field = data[0].field;
	}
	var selectType = _xform_main_data_insystem_fieldFixed;
	if(value != null){
		selectType = value;
	}
	//根据值来决定后面的domHTML
	var orgType = null;
	if(value && value.orgType)
		orgType = value.orgType;
	html += xform_main_data_getFieldValueDomByType(type,selectType,field,orgType);
	html += "</div>";
	html += "</div>";
	return html;
}

//输入值后面的dom type:属性的类型；	valueType:值的类型	field:属性名（在枚举类型的时候需要根据属性名取相应的选项）  orgType:组织架构类型（当前选的是ID才会有orgType） rootField:根源字段
function xform_main_data_getFieldValueDomByType(type,value,field,orgType,fullField,operator){
	if(type != null && value != null){
		var html = "";
		var valueType = value;
		var inputValue = "";
		if(typeof(value) == "object") {
			valueType = value.fieldValue;
			if(value.fieldInputValue) {
				inputValue = value.fieldInputValue;
			}
			if(value.fieldOperator) {
				operator = value.fieldOperator;
			}
			if(value.field) {
				fullField = value.field;
			}
		}
		//固定值
		if(valueType == _xform_main_data_insystem_fieldFixed){
			if(type == "datetime" || type == "date" || type == "time"){
				//时间控件
				var functionName = "xform_main_data_triggleSelect" + type + "(event,this);";
				var validateName = "__" + type;
				html += "<div class='inputselectsgl ' style='width:100%' onclick=\"" + functionName + "\">";
				html += "<div class='input'><input name='" + xform_main_data_insystem_whereSelectFieldInputName + "' type='text' validate='" + validateName + "' value='"+ inputValue +"'></div>";
				html += "<div class='inputdatetime'></div>";
				html += "</div>";
			}else if(type == "boolean"){
				var booleanShowValueArray = [ganttOption.lang.yes,ganttOption.lang.no];
				var booleanRealValueArray = ['1','0'];
				for(var i = 0; i < booleanShowValueArray.length;i++){
					html += "<label><input type='radio' name='"+ xform_main_data_insystem_whereSelectFieldInputName + "'";
					if(inputValue != '' && inputValue == booleanRealValueArray[i]){
						html += " checked";
					}
					html += " value='" + booleanRealValueArray[i] + "'>"+ booleanShowValueArray[i] +"</label>";
				}
			}else if(type == 'enum'){
				//枚举型
				var items = xform_main_data_findItemByDict(field);
				//补全枚举的businessType信息
				var businessType = businessTypeForEnum(field);
				if(items){
					var valuesArray = items.enumValues;
					var values;
					// 默认隔开空格个数
					var appendSpaceHtml = '&nbsp;&nbsp;';
					// 例如inputValue为： m;f
					if(inputValue != ''){
						values = inputValue.split(";");
					}
					var enumNameNum = ++_xform_main_data_insystem_radioNameNum;
					// 枚举的修改为单选
					var type = "radio"
					if(operator == "!{contain}" || operator == "!{notContain}"
						|| businessType == "inputCheckbox" || businessType == "fSelect"){
						type = "checkbox";
					}
					for(var i = 0; i < valuesArray.length;i++){
						html += "<label><input type='"+type+"' name='"+ xform_main_data_insystem_whereSelectFieldInputName + "_" + enumNameNum + "' ";
						// 只要存在于该数组里面
						if(values && $.inArray(valuesArray[i].fieldEnumValue,values) > -1){
							html += " checked";
						}
						html += " value='" + valuesArray[i].fieldEnumValue + "'>"+ valuesArray[i].fieldEnumLabel +"</label>" + appendSpaceHtml;
					}
				}

			}else if(type == 'double' || type == 'float' || type == 'long' || type == 'int' || type == 'bigdecimal'){
				//数字
				html += "<input type='text' style='width:100%' name='" + xform_main_data_insystem_whereSelectFieldInputName + "' class='inputsgl' validate='number' value='"+ inputValue +"'/>";
			}else{
				if((field === 'fdId' || field.indexOf('|fdId') !== -1) && orgType) {
					//当前选中的是组织架构类型的ID，显示组织架构选择框
					var randomSerial = Math.random().toString(36).substr(2,6).toUpperCase();
					var selectIds = xform_main_data_insystem_whereSelectFieldInputName + "_" + randomSerial;
					var selectNames = "__" + xform_main_data_insystem_whereSelectFieldInputName + "_" + randomSerial + "_Names";
					var orgSelectType = "ORG_TYPE_ALL";
					if(orgType === "ORG_TYPE_PERSON"){
						orgSelectType = "ORG_TYPE_PERSON";
					} else if(orgType === "ORG_TYPE_ORG|ORG_TYPE_DEPT"){
						orgSelectType = "ORG_TYPE_DEPT";
					}
					//显示值
					var inputValueText = "";
					if(value.fieldInputValueText)
						inputValueText = value.fieldInputValueText;
					html += "<div class='inputselectsgl' onclick='Dialog_Address(true, \"" + selectIds + "\",\"" + selectNames + "\", \";\", " + orgSelectType + ");'><input name='" + selectIds + "' value='" + inputValue + "' type='hidden'><div class='input'><input placeholder='"+ganttOption.lang.choose+"' data-lui-position='" + selectIds + "' name='" + selectNames + "' value='" + inputValueText + "' type='text' readonly=''></div><div class='selectitem'></div></div>";
				} else {
					html += "<input type='text' style='width:100%' name='" + xform_main_data_insystem_whereSelectFieldInputName + "' class='inputsgl' value='"+ inputValue +"'/>";
				}
			}
		} else if(valueType == xform_main_data_getEnumType("inputValue")[2].value) {
			//入参
			var fieldOperator = operator ? operator : "!{equal}";
			var urlParamKey = fullField.replace("|", "_") + "_" + fieldOperator.replace("!{", "").replace("}", "");;
			var checked = value.isAllowUrlParam === 'true' ? "checked='checked'" : "";
			html += '<label title="'+ganttOption.lang.invalidOper+'"><input type="checkbox" name="isAllowUrlParam" value="true" ' + checked + ' />'+ganttOption.lang.URLparameters+'</label><br><label style="font-size: 10px; color: #AAA">' + urlParamKey + '</label>';
		} else if(valueType == xform_main_data_getEnumType("inputValue")[3].value) {
			//公式定义
			var inputName = xform_main_data_insystem_whereSelectFieldInputName + "_formula_" + Math.random().toString(36).slice(-8);
			html += "<div class='inputselectsgl' style='width:100%;' onclick=\"onClick_Formula_Dialog('" + inputName + "','" + inputName + "', '', 'String');\">";
			html += "<div class='input'><input name='" + inputName + "' type='text' value='" + inputValue + "'></div>";
			html += "<div class='selectitem'></div>";
			html += "</div>";
		} else {
			//空值
			html = "";
		}
		return html;
	}
}

function businessTypeForEnum(field) {
	var businessType = "";
	var dictData = insystemContext.dictData;
	for(var i = 0;i < dictData.length;i++){
		if(dictData[i].fieldType.toLowerCase() == "enum" && field == dictData[i].field){
			businessType = dictData[i].businessType;
		}
	}
	return businessType;
}

//提交时处理查询条件
function xform_main_data_detailSelectWhere(tr){
	var selectJSON = {};
	var fdWhereTypeVal = $(tr).find("input[name='fdWhereType']").val();
	if (fdWhereTypeVal) {
		selectJSON.whereType = fdWhereTypeVal;
	} else {
		selectJSON.whereType = '0';
	}
	if(fdWhereTypeVal === '1'){
		//************内置查询项*************
		var fdPredefinedWhereType = $(tr).find("select[name='fdPredefinedWhereType'] option:selected");
		selectJSON.predefined = fdPredefinedWhereType.val();
		//初始化值 防止页面逻辑报错
		selectJSON.field = "fdId";
		selectJSON.fieldType = "String";

	} else {
		//***********自定义查询项************
		//处理查询属性
		var attrOptions = $(tr).find("select[name='fdAttrField']");
		var fieldVal = "";
		var fieldType = "";
		for (var i = 0; i < attrOptions.length; i++) {
			var fieldOption = $(attrOptions[i]).find("option:selected");
			fieldVal += fieldOption.val() + _xform_main_data_insystem_split;
			fieldType += fieldOption.attr('data-type') + _xform_main_data_insystem_split;
		}
		selectJSON.field = fieldVal.substring(0, fieldVal.length - 1);
		selectJSON.fieldType = fieldType.substring(0, fieldType.length - 1);

		//组织架构类型
		var orgType = $(attrOptions).find("option:selected").attr('data-orgType');
		if (orgType) selectJSON.orgType = orgType;

		//处理运算符
		var operatorOption = $(tr).find("select[name='fdWhereSelectFieldOperator'] option:selected");
		if (operatorOption.length > 0)
			selectJSON.fieldOperator = operatorOption.val();
		else
			selectJSON.fieldOperator = '!{equal}';	//组织架构选择ID时，没有运算符

		//处理输入值
		var valueOption = $(tr).find("select[name='fdWhereSelectValue'] option:selected");
		selectJSON.fieldValue = valueOption.val();

		//值
		var inputValue = $(tr).find("input[name^='" + xform_main_data_insystem_whereSelectFieldInputName + "']");
		var inputVal = '';
		if (inputValue.length > 1) {
			// 如果长度大于1，即表明是枚举（包括单选和多选）
			var inputValArr = [];
			for (var i = 0; i < inputValue.length; i++) {
				if ($(inputValue[i]).is(':checked')) {
					//inputVal += $(inputValue[i]).val() + ";";
					inputValArr.push($(inputValue[i]).val());
				}
			}
			inputVal = inputValArr.join(";");
		} else {
			inputVal = inputValue.val();
		}
		selectJSON.fieldInputValue = inputVal;

		//关联对象（如:组织架构）显示值
		var inputValueText = $(tr).find("input[name^='__" + xform_main_data_insystem_whereSelectFieldInputName + "']");
		if (inputValueText.length > 0)
			selectJSON.fieldInputValueText = inputValueText.val();

		//是否包含子部门
		var isIncludeSub = $(tr).find("input[name='isIncludeSub']:checked").val();
		if (isIncludeSub) {
			selectJSON.isIncludeSub = isIncludeSub;
		}
		//是否允许URL传参
		var isAllowUrlParam = $(tr).find("input[name='isAllowUrlParam']:checked").val();
		if (isAllowUrlParam) {
			selectJSON.isAllowUrlParam = isAllowUrlParam;
		}
	}
	return selectJSON;
}

//触发改变输入值后面的dom
function xform_main_data_changeFieldValue(selectDom){
	var $tr = $(selectDom).closest("tr");
	//查找最后一个同名的fdWhereSelectField
	var type = $tr.find("select[name='fdAttrField']:last option:selected").attr('data-type');
	// 枚举型需要根据field来查找选项
	var field = $tr.find("select[name='fdAttrField']:last option:selected").val();
	var valueType = $tr.find("select[name='fdWhereSelectValue'] option:selected").val();
	// 组织架构类型，且当前子选项选中的是ID
	var orgType = null;
	if(getFieldSubOptionIsID(selectDom)) {
		orgType = getFieldOrgType(selectDom);
	}
	// 全字段名（可能当前选择的是字段子选项）
	var fullField = field;
	var pfield = getSelectField(selectDom);
	if(pfield.val() !== field) {
		fullField = pfield.val() + "|" + field
	}
	//运算符
	var operator = $tr.find("select[name='fdWhereSelectFieldOperator'] option:selected").val();
	$tr.find(".xform_main_data_fieldDomWrap").html(xform_main_data_getFieldValueDomByType(type.toLowerCase(),valueType,field,orgType,fullField,operator));
	$tr.find(".validation-advice").hide();
}


function getFieldSubOptionIsID(selectDom){
	return getSelectFieldSubOption(selectDom).attr('data-isIdProperty');
}

function getSelectField(selectDom){
	return $($(selectDom).closest("tr").find('select[name="fdAttrField"]')[0]).find('option:selected');
}

function getSelectFieldSubOption(selectDom){
	return $($(selectDom).closest("tr").find('select[name="fdAttrField"]')[1]).find('option:selected');
}

//datetime控件触发
function xform_main_data_triggleSelectdatetime(event,dom){
	var input = $(dom).find("input[name='"+ xform_main_data_insystem_whereSelectFieldInputName +"']");
	selectDateTime(event,input);
}
//date控件触发
function xform_main_data_triggleSelectdate(event,dom){
	var input = $(dom).find("input[name='"+ xform_main_data_insystem_whereSelectFieldInputName +"']");
	selectDate(event,input);
}
//date控件触发
function xform_main_data_triggleSelecttime(event,dom){
	var input = $(dom).find("input[name='"+ xform_main_data_insystem_whereSelectFieldInputName +"']");
	selectTime(event,input);
}

//通过modelname查找数据字典
function xform_main_data_getDictAttrByModelName(param,callback){
	if(param.modelName == ganttOption.modelingGanttForm.fdModelName){
		callback(ganttOption.modelingGanttForm.modelDict);
	}else{
		$.ajax({
			url : Com_Parameter.ContextPath+"sys/modeling/base/modelingAppListview.do?method=getDictAttrByModelName",
			type : 'post',
			async : false,//是否异步
			data : param,
			success : callback
		})
	}
}

function getFieldOrgType(selectDom){
	return getSelectField(selectDom).attr('data-orgType');
}

// 根据属性值去数据字典变量里面查找对应的选项
function xform_main_data_findItemByDict(field){
	var dictData = insystemContext.dictData;
	if(dictData && field){
		for(var i = 0;i <  dictData.length ;i++){
			if(dictData[i].field == field){
				return dictData[i];
			}
		}
	}
	return null;
}

//获取属性列表，返回下拉菜单
function xform_main_data_getFieldOptionEditHtml(dataArray,fdName,noOnchangeEvent,value,fieldJSON,nonSubField){
	dataArray = window.filterSubTableField(dataArray);
	var html = "";
	var selectValue = value;
	//属性可能是对象类型，数据含有|即为对象类型
	if(selectValue != null && selectValue.indexOf(_xform_main_data_insystem_split) > -1){
		// 重新编辑
		// 属性对象，用于存储上下文，主要用于列表对象
		function MainDataFields(){
			this.childrent = [];
		}
		// var currentModelName = $("input[name='fdModelName']").val();
		var currentModelName = ganttOption.modelingGanttForm.fdModelName;
		var fieldType = currentModelName + _xform_main_data_insystem_split + fieldJSON.fieldType;// 加上当前modelname
		var selectArray = selectValue.split(_xform_main_data_insystem_split);
		var fieldTypeArray = fieldType.split(_xform_main_data_insystem_split);
		var mainDataFields = new MainDataFields();
		for(var i = 0;i < selectArray.length;i++){
			/********************* 构建临时对象 start *********************/
			var mainDataField = new Insystem_Property();
			mainDataField.fieldName = selectArray[i];
			mainDataField.fieldType = fieldTypeArray[i];
			mainDataFields.childrent.push(mainDataField);
			if(i != 0){
				mainDataField.parent = mainDataFields.childrent[i-1];
			}
			/********************* 构建临时对象 end *********************/
				//一个个属性遍历，通过ajax查询返回对象的数据字典
			var params = {'modelName' : fieldTypeArray[i],'isListProperty':'false'};
			// 上一个属性是否是列表属性对象，如果是则做一些业务处理
			if(mainDataField.parent && mainDataField.parent.isListProperty == true){
				params.isListProperty = 'true';
			}
			xform_main_data_getDictAttrByModelName(params,function(data){
				if(data){
					var datas = $.parseJSON(data);
					var arr = [];
					// 封装数组
					for(var j = 0;j < datas.length;j++){
						var pro = new Insystem_Property();
						var data = datas[j];
						pro.initialize(data);
						/********************* 构建临时对象 start *********************/
						if(data.field == selectArray[i] && pro.isListProperty){
							mainDataField.isListProperty = true;
						}
						/********************* 构建临时对象 end *********************/
						arr.push(pro);
					}
					html += xform_main_data_getFieldOptionEditHtml(arr,fdName,null,selectArray[i]);
					return html;
				}
			});
		}
	}else{
		if(!nonSubField) nonSubField=false;
		if(noOnchangeEvent != null && noOnchangeEvent == 'true'){
			html += "<select name='" + fdName + "' onchange='xform_main_data_trrigleFieldAttr(this,false," + nonSubField + ");' class='inputsgl floatLeft' style='margin:0 4px;width:45%;'>";
		}else{
			html += "<select name='" + fdName + "' onchange='xform_main_data_trrigleFieldAttr(this,true," + nonSubField + ");' class='inputsgl floatLeft' style='margin:0 4px; width:45%'>";
		}
		for(var i = 0;i < dataArray.length;i++){
			var data = dataArray[i];
			html += "<option value='" + data.field + "' data-type='" + data.fieldType + "'";
			// 选中已选有的值
			if(selectValue != null && selectValue == data.field){
				html += " selected='selected'";
			}
			/************** 业务参数 start****************/
			// 添加枚举值
			if(data.fieldType == 'enum'){
				html += " data-enumtype='" + data.enumType + "'";
			}
			// 是否是列表对象属性
			if(data.isListProperty){
				html += " data-isListProperty='true'";
			}
			// 是否是ID属性
			if(data.isIdProperty){
				html += " data-isIdProperty='true'";
			}
			// 组织架构的类型
			if(data.orgType){
				html += " data-orgType='" + data.orgType + "'";
			}else{
				if(data.fieldType == "com.landray.kmss.sys.organization.model.SysOrgElement"){
					html += " data-orgType='ORG_TYPE_ORG|ORG_TYPE_DEPT|ORG_TYPE_POST'";
				}
			}
			/*************** end ***************/
			html += ">" + data.message + "</option>";
		}
		html += "</select>"
	}
	return html;
}

seajs.use([ 'sys/ui/js/dialog' ,'lui/topic'],function(dialog,topic) {
	//增加业务操作
	window.xform_main_data_addOperation = function(optTb){
		DocList_AddRow(optTb);
		//更新角标
		var index = $('#'+optTb).find("> tbody > tr").last().find(".title-index").text();
		$('#'+optTb).find("> tbody > tr").last().find(".title-index").text(parseInt(index)+1);
		//更新向下的图标
		$('#'+optTb).find("> tbody > tr").last().prev("tr").find("div.down").show();
		//刷新预览
		topic.publish("preview.refresh");
		//渲染之后出发点击
		$('#'+optTb).find("> tbody > tr").last().find(".model-edit-view-oper").click();
	}
});

function getElemIndex(){
	var row = DocListFunc_GetParentByTagName("TR");
	var table = DocListFunc_GetParentByTagName("TABLE",row);
	for(var i=0; i<table.rows.length; i++){
		if(table.rows[i]==row){
			return i;
		}
	}
}

/**
 * 业务操作对话框
 *
 * exceptValue 需要排除的值，格式为字符串id;id
 */
function dialogSelect(mul, key, idField, nameField, action, exceptValue){
	var rowIndex;
	if(idField.indexOf('*')>-1 && window.DocListFunc_GetParentByTagName){
		var tr=DocListFunc_GetParentByTagName('TR');
		var tb= DocListFunc_GetParentByTagName("TABLE");
		var tbInfo = DocList_TableInfo[tb.id];
		rowIndex=tr.rowIndex-tbInfo.firstIndex;
	}
	var dialogCfg = ganttOption.dialogs[key];
	if(dialogCfg){
		var params='';
		var inputs=getDialogInputs(idField);
		if(inputs){
			for(var i=0;i<inputs.length;i++){
				var argu = inputs[i];
				var modelVal;
				if(argu["value"].indexOf('*')>-1){
					//入参来自明细表
					modelVal=$form(argu["value"],idField.replace("*",rowIndex)).val();
					if(modelVal==null||modelVal==''){
						if(argu["required"]=="true"){
							var errorInfo=ganttOption.lang.curDialogMiss+argu["label"]+ganttOption.lang.checkFormConf;
							alert(errorInfo);
							return;
						}
					}else{
						params+='&'+argu["key"]+'='+modelVal;
					}
				}else{
					//入参来自主表
					modelVal=$form(argu["value"]).val();
					if(modelVal==null||modelVal==''){
						if(argu["required"]=="true"){
							var errorInfo=ganttOption.lang.curDialogMiss+argu["label"]+ganttOption.lang.checkFormConf;
							alert(errorInfo);
							return;
						}
						params+='&'+argu["key"]+'='+formInitData[argu["value"]];
					}else{
						params+='&'+argu["key"]+'='+modelVal;
					}
				}
			}
		}
		params=encodeURI(params);
		var tempUrl = 'sys/modeling/base/resources/jsp/dialog_select_template.jsp?dialogType=opener&modelName=' + dialogCfg.modelName + '&_key=dialog_' + idField + '&exceptValue='+exceptValue;
		if(mul==true){
			tempUrl += '&mulSelect=true';
		}else{
			tempUrl += '&mulSelect=false';
		}
		var dialog = new KMSSDialog(mul,true);
		dialog.URL = Com_Parameter.ContextPath + tempUrl;
		var source = dialogCfg.sourceUrl;
		var propKey = '__dialog_' + idField + '_dataSource';
		dialog[propKey] = function(){
			if(idField.indexOf('*')>-1){
				var initField=idField.replace('*',rowIndex);
				return {url:source+params,init:document.getElementsByName(initField)[0].value};
			}else{
				return {url:source+params,init:document.getElementsByName(idField)[0].value};
			}
		};
		window[propKey] = dialog[propKey];
		propKey =  'dialog_' + idField;
		dialog[propKey] = function(rtnInfo){
			if(rtnInfo==null) return;
			var datas = rtnInfo.data;
			var rtnDatas=[],ids=[],names=[];
			for(var i=0;i<datas.length;i++){
				var rowData = domain.toJSON(datas[i]);
				rtnDatas.push(rowData);
				ids.push($.trim(rowData[rtnInfo.idField]));
				names.push($.trim(rowData[rtnInfo.nameField]));
			}
			if(idField.indexOf('*')>-1){
				//明细表
				$form(idField,idField.replace("*",rowIndex)).val(ids.join(";"));
				$form(nameField,idField.replace("*",rowIndex)).val(names.join(";"));
			}else{
				//主表
				$form(idField).val(ids.join(";"));
				$form(nameField).val(names.join(";"));
			}
			if(action){
				action(rtnDatas);
			}
			//出参处理
			var outputs=getDialogOutputs(idField);
			if(outputs){
				if(rtnDatas.length==1){
					for(var i=0;i<outputs.length;i++){
						var output=outputs[i];
						if(output["value"].indexOf('*')>-1){
							$form(output["value"],output["value"].replace("*",rowIndex)).val(rtnDatas[0][output["key"]]);
						}else{
							$form(output["value"]).val(rtnDatas[0][output["key"]]);
						}
					}
				}
			}
		};
		domain.register(propKey,dialog[propKey]);
		dialog.Show(800,440);
	}
}

function getDialogInputs(idField){
	var dialogLinks=ganttOption.dialogLinks;
	if(dialogLinks==null||dialogLinks.length==0){
		return null;
	}
	for(var i=0;i<dialogLinks.length;i++){
		var dialogLink=dialogLinks[i];
		if(idField==dialogLink.idField){
			return dialogLink.inputs;
		}
	}
	return null;
}
function getDialogOutputs(idField){
	var dialogLinks=ganttOption.dialogLinks;
	if(dialogLinks==null||dialogLinks.length==0){
		return null;
	}
	for(var i=0;i<dialogLinks.length;i++){
		var dialogLink=dialogLinks[i];
		if(idField==dialogLink.idField){
			return dialogLink.outputs;
		}
	}
	return null;
}

//业务操作行删除
function xform_main_data_beforeDelOperationRow(){
	var index = getElemIndex();
	//获取当前行id和name
	var fdId = $("[name='listOperationIdArr["+index+"]']").val();
	var fdName = $("[name='listOperationNameArr["+index+"]']").val();

	var listOperationIds = $("[name='listOperationIds']").val();
	var listOperationNames = $("[name='listOperationNames']").val();
	//删除数据，进行两次替换，兼容第一个和非第一个
	listOperationIds = listOperationIds.replace(fdId+";","");
	listOperationNames = listOperationNames.replace(fdName+";","");
	listOperationIds = listOperationIds.replace(fdId,"");
	listOperationNames = listOperationNames.replace(fdName,"");
	$("[name='listOperationIds']").val(listOperationIds);
	$("[name='listOperationNames']").val(listOperationNames);
	$("[name='listOperationIds_last']").val(listOperationIds);
	$("[name='listOperationNames_last']").val(listOperationNames);
}
