var _xform_main_data_insystem_split = "|";

//用于设置radio的name后缀，避免单选的name不能唯一
var _xform_main_data_insystem_radioNameNum = 0;

//默认值
var _xform_main_data_insystem_fieldFixed = xform_main_data_getEnumType("inputValue")[0].value;

var xform_main_data_insystem_whereSelectFieldInputName = "fdWhereSelectFieldInput";

//获取属性列表，返回下拉菜单
function xform_main_data_getFieldOptionHtml(dataArray,fdName,noOnchangeEvent,value,fieldJSON){
	var html = "";
	var selectValue = value;
	//属性可能是对象类型，数据含有|即为对象类型
	if(selectValue != null && selectValue.indexOf(_xform_main_data_insystem_split)  > -1){
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
					var dataJSON = $.parseJSON(data);
					if(dataJSON.fieldArray){
						var datas = dataJSON.fieldArray;
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
						childFieldAttrs = arr;
						html += xform_main_data_getFieldOptionHtml(arr,fdName,null,selectArray[i]);
						return html;
					}
				}
			});
		}
	}else{
		if(noOnchangeEvent != null && noOnchangeEvent == 'true'){
			html += "<select name='" + fdName + "' onchange='xform_main_data_trrigleFieldAttr(this,false);'>";
		}else{
			html += "<select name='" + fdName + "' onchange='xform_main_data_trrigleFieldAttr(this,true);'>";	
		}
		
		for(var i = 0;i < dataArray.length;i++){
			var data = dataArray[i];
			html += "<option value='" + data.field + "' data-type='" + data.fieldType + "'";
			html += "' data-dict-type='" + data.dictType + "'";
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
			/*************** end ***************/
			html += ">" + data.message + "</option>";
		}
		html += "</select>"
	}
	return html;		
}

var childFieldAttrs = [];
//属性列表值改变
//changeEvent true|false ，是否需要改变其他dom
function xform_main_data_trrigleFieldAttr(dom , changeEvent){
	var selectDom = dom;
	var type = $(selectDom).find("option:selected").attr('data-type');
	var field = $(selectDom).find("option:selected").val();
	var typeChange = changeEvent;
	// 如果属性是对象类型
	if(type.indexOf("com.landray.kmss") > -1){
		//删除当前元素后面的同级元素
		$(selectDom).nextAll().remove();
		var params = {'modelName' : type,'isListProperty':'false'};
		var isListProperty = $(selectDom).find("option:selected").attr('data-isListProperty');
		if(isListProperty && isListProperty == 'true'){
			params.isListProperty = 'true';
		}
		xform_main_data_getDictAttrByModelName(params,function(data){
			if(data){
				var dataJSON = $.parseJSON(data);
				if(dataJSON.fieldArray){
					var datas = dataJSON.fieldArray;
					var arr = [];
					// 封装数组
					for(var j = 0;j < datas.length;j++){
						var pro = new Insystem_Property();
						var data = datas[j];
						pro.initialize(data);
						arr.push(pro);
					}
					childFieldAttrs = arr;
					var selectHtml = xform_main_data_getFieldOptionHtml(arr,"fdAttrField");
					$(selectDom).after(selectHtml);	
					typeChange = false;
					//$(selectHtml).change(); 
					//不能用上面这种方式触发，因为此时的$(selectHtml)并不存在于dom结构里面，会导致下面的获取不到父元素
					$(selectDom).next().change();					
				}
			}
		});
	}else{
		//删除当前元素后面的同级元素
		$(selectDom).nextAll().remove();
		//childFieldAttrs = [];
	}
	if(typeChange){
		//改变运算符
		var operatorArray = xform_main_data_getOperatorByType(type.toLowerCase());
		$(selectDom).closest("tr").find(".xform_main_data_operatorWrap").html(xform_main_data_buildOperatorSelectHtml(operatorArray));
		//改变值展示方式
		xform_main_data_changeFieldValue(selectDom);
	}
}

//通过modelname查找数据字典
function xform_main_data_getDictAttrByModelName(param,callback){
	$.ajax({
		url : Com_Parameter.ContextPath+"sys/xform/maindata/main_data_insystem/sysFormMainDataInsystem.do?method=getDictAttrByModelName",
		type : 'post',
		async : false,//是否异步
		data : param,
		success : callback
	})
}

//运算符
function xform_main_data_getOperatorOptionHtml(data,value){
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
	var operatorArray = xform_main_data_getOperatorByType(type);
	html += xform_main_data_buildOperatorSelectHtml(operatorArray,value);
	html += "</div>";
	return html;
}

//构建运算符的HTML
function xform_main_data_buildOperatorSelectHtml(operatorArray,value){
	var html = "";
	html += "<select name='fdWhereSelectFieldOperator'>";
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
		}else if(type == "datetime" || type =="date"){
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

//获取enum type根据MainDataInsystemEnumUtil getAllEnum（）里面的key匹配
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
	html += "<select name='fdWhereSelectValue' onchange='xform_main_data_changeFieldValue(this);'>";
	for(var i = 0;i < optionValueArray.length; i++){
		html += "<option value='" + optionValueArray[i].value + "'";
		if(value && optionValueArray[i].value == value.fieldValue){
			html += " selected='selected'";
		}
		html += ">"+ optionValueArray[i].text +"</option>";
	}
	html += "</select>";
	html += "<div class='xform_main_data_fieldDomWrap' style='bottom: 1px; margin-left: 6px; display: inline-block;'>";
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
	if(type.indexOf(_xform_main_data_insystem_split)  > -1){
		type = type.split(_xform_main_data_insystem_split)[1];
		field = field.split(_xform_main_data_insystem_split)[1];
	}
	//根据值来决定后面的domHTML
	html += xform_main_data_getFieldValueDomByType(type,selectType,field);
	html += "</div>";
	html += "</div>";
	return html;
}

//触发改变输入值后面的dom
function xform_main_data_changeFieldValue(selectDom){
	var $tr = $(selectDom).closest("tr");
	//获取不到tr说明已被删除
	if ($tr.length === 0) {
		return;
	}
	//查找最后一个同名的fdWhereSelectField
	var type = $tr.find("select[name='fdAttrField']:last option:selected").attr('data-type');
	// 枚举型需要根据field来查找选项
	var field = $tr.find("select[name='fdAttrField']:last option:selected").val();
	var valueType = $tr.find("select[name='fdWhereSelectValue'] option:selected").val();
	$tr.find(".xform_main_data_fieldDomWrap").html(xform_main_data_getFieldValueDomByType(type.toLowerCase(),valueType,field));
}

//输入值后面的dom type:属性的类型；	valueType:值的类型	field:属性名（在枚举类型的时候需要根据属性名取相应的选项）
function xform_main_data_getFieldValueDomByType(type,value,field){
	if(type != null && value != null){
		var html = "";
		var valueType = value;
		var inputValue = "";
		if(typeof(value) == "object"){
			valueType = value.fieldValue;
			if(value.fieldInputValue){
				inputValue = value.fieldInputValue;
			}
		}
		//valueType 为固定值的时候
		if(valueType == _xform_main_data_insystem_fieldFixed){
			//如果是日期类型，则显示日期选择；如果是枚举类型，则显示单选（是否）；如果是地址本，则显示地址本
			if(type == "datetime"){
				//构建时间控件
				html += "<div class='inputselectsgl' style='width:120px;' onclick=\"xform_main_data_triggleSelectDateTime(event,this);\">";
				html += "<div class='input'><input name='" + xform_main_data_insystem_whereSelectFieldInputName + "' type='text' validate='__datetime' value='"+ inputValue +"'></div>";
				html += "<div class='inputdatetime'></div>";
				html += "</div>";
			}else if(type == "date"){
				//构建时间控件
				html += "<div class='inputselectsgl' style='width:120px;' onclick=\"xform_main_data_triggleSelectDate(event,this);\">";
				html += "<div class='input'><input name='" + xform_main_data_insystem_whereSelectFieldInputName + "' type='text' validate='__date' value='"+ inputValue +"'></div>";
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
					for(var i = 0; i < valuesArray.length;i++){
						html += "<label><input type='radio' name='"+ xform_main_data_insystem_whereSelectFieldInputName + "_" + enumNameNum + "' ";
						// 只要存在于该数组里面
						if(values && $.inArray(valuesArray[i].fieldEnumValue,values) > -1){
							html += " checked";
						}
						html += " value='" + valuesArray[i].fieldEnumValue + "'>"+ valuesArray[i].fieldEnumLabel +"</label>" + appendSpaceHtml;
					}	
				}
				
			}else{
				html += "<input type='text' name='" + xform_main_data_insystem_whereSelectFieldInputName + "' class='inputsgl' value='"+ inputValue +"'/>";
			}
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
	if(childFieldAttrs.length>0 && field){
		for(var i = 0;i <  childFieldAttrs.length ;i++){
			if(childFieldAttrs[i].field == field){
				return childFieldAttrs[i];
			}
		}
	}
	return null;
}

//日期控件触发
function xform_main_data_triggleSelectDate(event,dom){
	var input = $(dom).find("input[name='"+ xform_main_data_insystem_whereSelectFieldInputName +"']");
	selectDate(event,input);
}
//日期控件触发
function xform_main_data_triggleSelectDateTime(event,dom){
	var input = $(dom).find("input[name='"+ xform_main_data_insystem_whereSelectFieldInputName +"']");
	selectDateTime(event,input);
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
		$('#xform_main_data_returnValueTable tr:not(:first)').remove();
		$('#xform_main_data_searchTable tr:not(:first)').remove();
		$("input[name='fdAuthRead']")[0].checked = false;
		$("input[name='fdAuthEdit']")[0].checked = false;	
	}
}

//提交时处理查询条件
function xform_main_data_detailSelectWhere(tr){
	var selectJSON = {};
	//处理查询属性
	var attrOptions = $(tr).find("select[name='fdAttrField']");
	var fieldVal = "";
	var fieldType = "";
	for(var i = 0;i < attrOptions.length;i++){
		var fieldOption = $(attrOptions[i]).find("option:selected");
		fieldVal += fieldOption.val() + _xform_main_data_insystem_split;
		fieldType += fieldOption.attr('data-type') + _xform_main_data_insystem_split;
	}
	selectJSON.field = fieldVal.substring(0,fieldVal.length - 1);
	selectJSON.fieldType = fieldType.substring(0,fieldType.length - 1);
	//处理运算符
	var operatorOption = $(tr).find("select[name='fdWhereSelectFieldOperator'] option:selected");
	selectJSON.fieldOperator = operatorOption.val();
	//处理输入值
	var valueOption = $(tr).find("select[name='fdWhereSelectValue'] option:selected");
	selectJSON.fieldValue = valueOption.val();
	
	var inputValue = $(tr).find("input[name^='" + xform_main_data_insystem_whereSelectFieldInputName + "']");
	var inputVal = '';
	if(inputValue.length > 1){
		// 如果长度大于1，即表明是单选
		for(var i = 0;i < inputValue.length ;i++){
			if($(inputValue[i]).is(':checked')){
				inputVal = $(inputValue[i]).val();
				break;
			}
		}
	}else{
		inputVal = inputValue.val();
	}
	selectJSON.fieldInputValue = inputVal; 
	
	return selectJSON;
}

//提交时处理返回值和搜索值
function xform_main_data_detailAttrWhere(tr,dealEnum){
	var valueJSON = {};
	//处理查询属性
	var attrOptions = $(tr).find("select[name='fdAttrField']");
	var fieldVal = "";
	var fieldType = "";
	var dictType = "";
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
		dictType += fieldOption.attr('data-dict-type') + _xform_main_data_insystem_split;
		fieldVal += fieldOption.val() + _xform_main_data_insystem_split;
		fieldType += ty + _xform_main_data_insystem_split;
	}
	valueJSON.field = fieldVal.substring(0,fieldVal.length - 1);
	valueJSON.fieldType = fieldType.substring(0,fieldType.length - 1);
	valueJSON.dictType = dictType.substring(0,dictType.length - 1);
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
