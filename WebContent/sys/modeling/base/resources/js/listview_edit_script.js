var _xform_main_data_insystem_split = "|";

//用于设置radio的name后缀，避免单选的name不能唯一
var _xform_main_data_insystem_radioNameNum = 0;

//默认值
var _xform_main_data_insystem_fieldFixed = xform_main_data_getEnumType("inputValue")[0].value;

var xform_main_data_insystem_whereSelectFieldInputName = "fdWhereSelectFieldInput";
seajs.use([ 'sys/ui/js/dialog' ,'lui/topic'],function(dialog,topic) {
//属性列表值改变
//changeEvent true|false ，是否需要改变其他dom
	window.xform_main_data_trrigleFieldAttr = function(dom , changeEvent, nonSubField){
		var selectDom = dom;
		var type = $(selectDom).find("option:selected").attr('data-type');
		var field = $(selectDom).find("option:selected").val();
		var typeChange = changeEvent;
		//刷新预览
		topic.publish("preview.refresh");
		// 如果属性是对象类型
		if(type.indexOf("com.landray.kmss") > -1 && !nonSubField){
			//删除当前元素后面的同级元素
			$(selectDom).nextAll().remove();
			var params = {'modelName' : type,'isListProperty':'false'};
			var isListProperty = $(selectDom).find("option:selected").attr('data-isListProperty');
			if(isListProperty && isListProperty == 'true'){
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
						arr.push(pro);
					}
					var selectHtml = xform_main_data_getFieldOptionHtml(arr,"fdAttrField");
					$(selectDom).after(selectHtml);	
					typeChange = false;
					//$(selectHtml).change(); 
					//不能用上面这种方式触发，因为此时的$(selectHtml)并不存在于dom结构里面，会导致下面的获取不到父元素
					$(selectDom).next().change();					
				}
			});
		}else{
			//删除当前元素后面的同级元素
			$(selectDom).nextAll().remove();
		}
		if(typeChange){
			//当前选的是ID
			var isIdProperty = $(selectDom).find("option:selected").attr('data-isIdProperty');
			if(isIdProperty){
				//这个ID属于组织架构 人员=ORG_TYPE_PERSON 部门=ORG_TYPE_ORG|ORG_TYPE_DEPT
				var orgType = getFieldOrgType(selectDom);
				if(orgType) {
					//部门类型
					if (orgType === "ORG_TYPE_ORG|ORG_TYPE_DEPT") {
						//运算符位置显示'包含子部门'选项
						$(selectDom).closest("tr").find(".xform_main_data_operatorWrap").html('<label><input type="checkbox" name="isIncludeSub" value="true"/>包含子部门</label>');
					} else {
						//其它组织架构类型
						//运算符只显示'等于'
						var operatorArray = xform_main_data_getOperatorByType(type.toLowerCase());
						operatorArray = [operatorArray[0]];
						$(selectDom).closest("tr").find(".xform_main_data_operatorWrap").html(xform_main_data_buildOperatorSelectHtml(operatorArray));
					}
					xform_main_data_changeFieldValue(selectDom);
					return;
				}
			}
			//改变运算符
			var operatorArray = xform_main_data_getOperatorByType(type.toLowerCase());
			$(selectDom).closest("tr").find(".xform_main_data_operatorWrap").html(xform_main_data_buildOperatorSelectHtml(operatorArray));
			//运算符title
			$(selectDom).closest("tr").find(".xform_main_data_operatorWrap").parent().prev().html('运算符');
			//改变值展示方式
			xform_main_data_changeFieldValue(selectDom);
		}
		//更新标题
		changeUlTitle($(selectDom).closest('li').find('select[name="fdAttrField"]'));
	}
});

function getFieldOrgType(selectDom){
	return getSelectField(selectDom).attr('data-orgType');
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

//获取属性列表，返回下拉菜单
function xform_main_data_getFieldOptionHtml(dataArray,fdName,noOnchangeEvent,value,fieldJSON,nonSubField){
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
		var currentModelName = $("input[name='fdModelName']").val();
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
					html += xform_main_data_getFieldOptionHtml(arr,fdName,null,selectArray[i]);
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

//通过modelname查找数据字典
function xform_main_data_getDictAttrByModelName(param,callback){
	if(param.modelName == listviewOption.modelingAppListviewForm.fdModelName){
		callback(listviewOption.modelingAppListviewForm.modelDict);
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

//预定义查询类型
function getWhereTypeHtml(value) {
	html = "<li class='model-edit-view-oper-content-item first-item' style='display:none;' name='whereTypeLi'><div class='item-title'>类型</div>";
	html += "<div class='item-content'>";
	html += "<input type='hidden' name='fdWhereType' value='" + value + "'/>" +
			"<div class='view_flag_radio' style='display: inline-block;'>" +
			"<div class='view_flag_radio_no' style='display: inline-block;cursor: pointer;'  onclick='changeFdWhereType(this,\"fdWhereType\",0)'><i class='view_flag_no " + (value === '1' ? '' : 'view_flag_yes') + "'></i>自定义查询项</div>" +
			"<div class='view_flag_radio_yes view_flag_last' style='display:inline-block;cursor: pointer;'  onclick='changeFdWhereType(this,\"fdWhereType\",1)'><i class='view_flag_no " + (value === '1' ? 'view_flag_yes' : '') + "'></i>内置查询项</div>" +
			"</div>";
	html += "</div></li>";
	return html;
}

//更新标题
function changeUlTitle(selectDom) {
	$(selectDom).parents(".model-edit-view-oper").eq(0).find(".model-edit-view-oper-head-title span").text($(selectDom).find("option:selected").text());
}

//内置查询下拉框变化事件
function changePredefined(selectDom){
	changeUlTitle(selectDom);
}

function changeFdWhereType(dom, name, value) {
	//切换radio
	var fdWhereType = $(dom).closest("li").find("[name='"+name+"']");
	if(fdWhereType.val() === value)
		return;
	var radioObj = $(dom).parents(".view_flag_radio")[0];
	if(value === 0){
		$(radioObj).find(".view_flag_radio_no i").addClass("view_flag_yes");
		$(radioObj).find(".view_flag_radio_yes i").removeClass("view_flag_yes");
		changeUlTitle($(dom).closest('ul').find('select[name="fdAttrField"]'));
	}else{
		$(radioObj).find(".view_flag_radio_no i").removeClass("view_flag_yes");
		$(radioObj).find(".view_flag_radio_yes i").addClass("view_flag_yes");
		changePredefined($(dom).closest('ul').find('select[name="fdPredefinedWhereType"]'));
	}
	fdWhereType.val(value);

	//控制 内置查询项 与 预定义查询项 的显示
	$(dom).closest('tr').find('.model-edit-view-oper-content-item').each(function () {
		var name = $(this).attr('name');
		if(name === 'whereTypeLi')
			return;
		if ((name === 'predefinedLi' && value === 1)
			|| (name !== 'predefinedLi' && value === 0))
			$(this).removeClass("liHidden");
		else
			$(this).addClass("liHidden");
	});
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
			operatorArray = [operatorArray[0]];
			html += xform_main_data_buildOperatorSelectHtml(operatorArray,value);
		}
	} else {
		var operatorArray = xform_main_data_getOperatorByType(type);
		html += xform_main_data_buildOperatorSelectHtml(operatorArray,value);
	}
	html += "</div>";
	return html;
}

//构建运算符的HTML
function xform_main_data_buildOperatorSelectHtml(operatorArray,value){
	var html = "";
	html += "<select name='fdWhereSelectFieldOperator' class='inputsgl' style='margin:0 4px;width:100%' onchange=\"xform_main_data_changeFieldValue(this);\">";
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
	html += "<div class='xform_main_data_fieldDomWrap input_radio height28' style='bottom: 1px; display: inline-block;width:64%;'>";
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
	var fieldType = pfield.attr("data-type");
	updateOperatorTdByFieldValue($tr,valueType);
	//运算符
	var operator = $tr.find("select[name='fdWhereSelectFieldOperator'] option:selected").val();
	$tr.find(".xform_main_data_fieldDomWrap").html(xform_main_data_getFieldValueDomByType(type.toLowerCase(),valueType,field,orgType,fullField,operator,fieldType));
	$tr.find(".validation-advice").hide();
}
//更新运算符
function updateOperatorTdByFieldValue($tr,valueType) {
	var $whereRuleFieldOperatorSelect = $tr.find("select[name='fdWhereSelectFieldOperator']");
	if(valueType === '!{empty}'){
		//当为空值时，只有运算符等于和不等于，其他隐藏
		$whereRuleFieldOperatorSelect.find("option").each(function () {
			if($(this).val() != "="
				&& $(this).val() != "!="
				&& $(this).val() != "!{equal}"
				&& $(this).val() != "!{notequal}"
				&& $(this).val() != "eq" ){
				$(this).css("display","none");
			}
		})
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
		})
	}

}

//输入值后面的dom type:属性的类型；	valueType:值的类型	field:属性名（在枚举类型的时候需要根据属性名取相应的选项）  orgType:组织架构类型（当前选的是ID才会有orgType） rootField:根源字段
function xform_main_data_getFieldValueDomByType(type,value,field,orgType,fullField,operator,fieldType){
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
				var booleanShowValueArray = ['是','否'];
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
					if(operator == "!{contain}"){
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
                    var multiFlag = "false";
                    if(fieldType){
                    	//以[]结尾的是多选地址本控件
                    	if(/\[\]$/.test(fieldType)){
                    		multiFlag = "true";
                    	}
                    }
					html += "<div class='inputselectsgl' onclick='Dialog_Address("+multiFlag+", \"" + selectIds + "\",\"" + selectNames + "\", \";\", " + orgSelectType + ");'><input name='" + selectIds + "' value='" + inputValue + "' type='hidden'><div class='input'><input placeholder='请选择' data-lui-position='" + selectIds + "' name='" + selectNames + "' value='" + inputValueText + "' type='text' readonly=''></div><div class='selectitem'></div></div>";
				} else {
					html += "<input type='text' style='width:100%' name='" + xform_main_data_insystem_whereSelectFieldInputName + "' class='inputsgl' value='"+ inputValue +"'/>";
				}
			}
		} else if(valueType == xform_main_data_getEnumType("inputValue")[2].value) {
			//入参
			var fieldOperator = operator ? operator : "!{equal}";
			var urlParamKey = fullField.replace("|", "_") + "_" + fieldOperator.replace("!{", "").replace("}", "");;
			var checked = value.isAllowUrlParam === 'true' ? "checked='checked'" : "";
			html += '<label title="注意：在业务标签及业务操作中时无效"><input type="checkbox" name="isAllowUrlParam" value="true" ' + checked + ' />是否接收URL传参</label><br><label style="font-size: 10px; color: #AAA">' + urlParamKey + '</label>';
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

//删除一行
function xform_main_data_delTrItem(aDom,callback){
	$(aDom).closest("tr").remove();
	if(callback){
		callback();
	}
}

//清空所有行
function xform_main_data_emptyAllTr(tableId){
	if(tableId && typeof(tableId) == 'string'){
		$('#' + tableId + ' tr:not(:first)').remove();
	}else{
		$('#xform_main_data_whereTable tr:not(:first)').remove();
		$('#xform_main_data_orderbyTable tr:not(:first)').remove();
	}
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

//提交时处理返回值和搜索值
function xform_main_data_detailAttrWhere(tr,dealEnum){
	var valueJSON = {};
	//处理查询属性
	var attrOptions = $(tr).find("select[name='fdAttrField']");
	var fieldVal = "";
	var fieldType = "";
	for(var i = 0;i < attrOptions.length;i++){
		var fieldOption = $(attrOptions[i]).find("option:selected");
		var ty = fieldOption.attr('data-type');
		// 设置枚举值
		if(dealEnum && dealEnum == 'true' && ty == 'enum'){
			// 最多只有一个枚举类型
			valueJSON.enumType = fieldOption.attr('data-enumtype');
			valueJSON.enumShowType = $(tr).find("[name^='fdEnumShow']:checked").val();
		}
		// 设置列表对象
		var isListProperty = fieldOption.attr('data-isListProperty');
		if(isListProperty && isListProperty == 'true'){
			valueJSON.isListProperty = 'true';
		}
		// 设置ID标识
		if(fieldOption.val() == insystemContext.idProperty.field){
			valueJSON.isIdProperty = 'true';
		}
		// 设置主题标识
		if(insystemContext.nameProperty && fieldOption.val() == insystemContext.nameProperty.field){
			valueJSON.isSubjectProperty = 'true';
		}
		fieldVal += fieldOption.val() + _xform_main_data_insystem_split;
		fieldType += ty + _xform_main_data_insystem_split;
	}
	valueJSON.field = fieldVal.substring(0,fieldVal.length - 1);
	valueJSON.fieldType = fieldType.substring(0,fieldType.length - 1);
	// 处理显示值
	var show = $(tr).find("input[name='fdSelectShowFlag']");
	if(show.length > 0){
		valueJSON.show = show[0].checked + '';
	}
	return valueJSON;
}

//查询预览的时候调用,通过js获取翻译
function xform_main_data_getFieldsText(tableId,field,selectName){
	var $selectsTr = $("#" + tableId).find("tr:not(.xform_main_data_tableTitle)");
	var fieldText = "";	
	var fieldArray = field.split(_xform_main_data_insystem_split);//对象类型
	for(var i = 0;i < $selectsTr.length;i++){
		var selectTr = $selectsTr[i];
		var selectOptions = $(selectTr).find("select[name='"+ selectName +"']");			
		//先判断长度是否相等
		if(selectOptions.length == fieldArray.length){
			for(var j = 0;j < selectOptions.length;j++){
				var fieldOption = $(selectOptions[j]).find("option:selected");
				if(fieldOption.val() == fieldArray[j]){
					fieldText += fieldOption.text() + ".";
				}else{
					//只要有一个不相同，就跳出
					fieldText = "";
					break;
				}
				//只要遍历完field，即可退出
				if(j == (fieldArray.length - 1)){
					if(fieldText.length > 0){
						fieldText = fieldText.substring(0,fieldText.length - 1);
					}
					return fieldText;
				}
			}
		}
	}
}

function xform_main_data_custom_enumChangeEvent(tableId){
	$("#" + tableId).delegate("select[name='fdAttrField']","change",function(event){
		var option = $(this).find("option:selected");
		if(option.attr('data-type') == 'enum'){
			//构建选项
			var suffix = tableId.substring(tableId.lastIndexOf('_') + 1) + "_" + ++_xform_main_data_insystem_radioNameNum;
			$(this).after(xform_main_data_custom_buildEnumHtml(suffix));
		}
		xform_main_data_custome_stopBubble(event);
	});
}

// 设置枚举类型初始值
function xform_main_data_custom_initEnum($tr,selectedItem){
	if(selectedItem && selectedItem.fieldType){
		// 处理对象类型的枚举属性
		var fieldArray = selectedItem.fieldType.split(_xform_main_data_insystem_split);
		if(fieldArray.length > 0 && fieldArray[fieldArray.length - 1] == 'enum'){
			var tableId = $tr.closest('table').attr('id');
			var suffix = tableId.substring(tableId.lastIndexOf('_') + 1) + "_" + ++_xform_main_data_insystem_radioNameNum;
			$tr.find("select[name='fdAttrField']:last").after(xform_main_data_custom_buildEnumHtml(suffix));
			$tr.find("input[name^='fdEnumShow'][value='"+ selectedItem.enumShowType +"']").attr("checked",true);	
		}
	}
}

function xform_main_data_custom_buildEnumHtml(suffix){
	var html = [];
	if(!suffix){
		suffix = "";
	}
	var name = "fdEnumShow_" + suffix;
	html.push("<div class='custom_enumChoose' style='display:inline-block;margin-left:15px;'><label>");
	html.push("<input type='radio' value='text' name='"+ name +"' checked/>显示值");
	html.push("</label>");
	html.push("<label>");
	html.push("<input type='radio' value='value' name='"+ name +"'/>实际值");
	html.push("</label></div>");
	return html.join("");
}

/*
 * 停止冒泡
 * */
function xform_main_data_custome_stopBubble(e) {
    // 如果提供了事件对象，则这是一个非IE浏览器
    if ( e && e.stopPropagation ) {
        // 因此它支持W3C的stopPropagation()方法 
        e.stopPropagation();
    } else { 
        // 否则，我们需要使用IE的方式来取消事件冒泡
        window.event.cancelBubble = true;
    }
}

//切换状态
function changeFlag(obj, name, value, relatedShowName){
	var curVal = $("[name='"+name+"']").val();
	if(curVal == value){
		return;
	}
	var radioObj = $(obj).parents(".view_flag_radio")[0];
	if(value == 0){
		if(relatedShowName) {
			$("select[name='" + relatedShowName + "']").hide();
			$("select[name='" + relatedShowName + "']").val("");
		}
		$(radioObj).find(".view_flag_radio_no i").addClass("view_flag_yes");
		$(radioObj).find(".view_flag_radio_yes i").removeClass("view_flag_yes");
	}else{
		if(relatedShowName)
			$("select[name='" + relatedShowName + "']").show();
		$(radioObj).find(".view_flag_radio_no i").removeClass("view_flag_yes");
		$(radioObj).find(".view_flag_radio_yes i").addClass("view_flag_yes");
	}
	$("[name='"+name+"']").val(value);
}

//分页器设置：1|默认，2|简版，3|可切换
function changePageSetting(obj, name, value){
	var curVal = $("[name='"+name+"']").val();
	if(curVal == value){
		return;
	}
	var radioObj = $(obj).parents(".view_flag_radio")[0];
	if(value == 1){
		$(radioObj).find(".view_flag_radio_no i").removeClass("view_flag_yes");
		$(radioObj).find(".view_flag_radio_last i").removeClass("view_flag_yes");
		$(radioObj).find(".view_flag_radio_yes i").addClass("view_flag_yes");
	}else if(value == 2){
		$(radioObj).find(".view_flag_radio_no i").addClass("view_flag_yes");
		$(radioObj).find(".view_flag_radio_yes i").removeClass("view_flag_yes");
		$(radioObj).find(".view_flag_radio_last i").removeClass("view_flag_yes");
	}else{
		$(radioObj).find(".view_flag_radio_no i").removeClass("view_flag_yes");
		$(radioObj).find(".view_flag_radio_last i").addClass("view_flag_yes");
		$(radioObj).find(".view_flag_radio_yes i").removeClass("view_flag_yes");
	}
	$("[name='"+name+"']").val(value);
}