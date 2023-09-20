var _xform_main_data_show_split = "|";

//用于设置radio的name后缀，避免单选的name不能唯一
var _xform_main_data_show_radioNameNum = 0;

//默认值
var _xform_main_data_show_fieldFixed = xform_main_data_getEnumType("inputValue")[0].value;

var xform_main_data_show_whereSelectFieldInputName = "fdWhereSelectFieldInput";

//获取属性列表，返回下拉菜单
function xform_main_data_getFieldOptionHtml(dataArray,fdName,noOnchangeEvent,value,fieldJSON, pleaseSelect, modelName){
	var html = "";
	var selectValue = value;
	//属性可能是对象类型
	if(selectValue != null && selectValue.indexOf(_xform_main_data_show_split)  > -1){
		var currentModelName = $("input[name='fdModelName']").val();
		if(modelName){
			currentModelName = modelName;
			//debugger;
		}
		var fieldType = currentModelName + _xform_main_data_show_split + fieldJSON.fieldType;
		var selectArray = selectValue.split(_xform_main_data_show_split);
		var fieldTypeArray = fieldType.split(_xform_main_data_show_split);
		for(var i = 0;i < selectArray.length;i++){
			//一个个属性遍历，通过ajax查询返回对象的数据字典
			xform_main_data_getDictAttrByModelName(fieldTypeArray[i],function(data){
				if(data){
					var dataJSON = $.parseJSON(data);
					if(dataJSON.fieldArray){
						html += xform_main_data_getFieldOptionHtml(dataJSON.fieldArray,fdName,null,selectArray[i]);
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
		if(pleaseSelect){
			html += "<option value='' data-type='String'>请选择</option>";
		}
		for(var i = 0;i < dataArray.length;i++){
			var data = dataArray[i];
			html += "<option value='" + data.field + "' data-type='" + data.fieldType + "'";
			// 添加枚举值
			if(data.fieldType == 'enum'){
				html += " data-enumtype='" + data.enumType + "'";
			}
			if(selectValue != null && selectValue == data.field){
				html += " selected='selected'";
			}
			html += ">" + data.fieldText + "</option>";
		}
		html += "</select>"
	}
	return html;		
}

//属性列表值改变
//changeEvent true|false ，是否需要改变其他dom
function xform_main_data_trrigleFieldAttr(dom , changeEvent){
	//debugger;
	var selectDom = dom;
	var type = $(selectDom).find("option:selected").attr('data-type');
	var typeChange = changeEvent;
	// 如果属性是对象类型
	if(type.indexOf("com.landray.kmss") > -1){
		//删除当前元素后面的同级元素
		$(selectDom).nextAll().remove();
		xform_main_data_getDictAttrByModelName(type,function(data){
			if(data){
				var dataJSON = $.parseJSON(data);
				if(dataJSON.fieldArray){
					var selectHtml = xform_main_data_getFieldOptionHtml(dataJSON.fieldArray,"fdAttrField");
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
function xform_main_data_getDictAttrByModelName(modelName,callback){
	$.ajax({
		url : Com_Parameter.ContextPath+"sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=getDictAttrByModelName",
		type : 'post',
		async : false,//是否异步
		data : {'modelName' : modelName},
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
	if(type && type.indexOf(_xform_main_data_show_split) > -1){
		var lastIndex = type.lastIndexOf(_xform_main_data_show_split);
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
		}else if(type == "long" || type == "integer" || type == "double"){
			operatorArray = xform_main_data_getEnumType("operatorNum");
		}else if(type == "datetime"){
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

//获取enum type根据MainDataShowEnumUtil getAllEnum（）里面的key匹配
function xform_main_data_getEnumType(type){
	if(_main_data_show_enumCollection.hasOwnProperty(type)){
		return _main_data_show_enumCollection[type];
	}
	return [];
}

//构建输入值HTML
function xform_main_data_getFieldvalueOptionHtml(data,value,fieldDatas){
	var html = "";
	var optionValueArray = xform_main_data_getEnumType("inputValue");
	
	html += "<div class='xform_main_data_fieldWrap' style='position:relative;'>";
	html += "<select name='fdWhereSelectValue' onchange='xform_main_data_changeFieldValue(this);'>";
	for(var i = 0;i < optionValueArray.length; i++){
		if(optionValueArray[i].value=='!{dynamic}'){
			continue;
		}
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
	var selectType = _xform_main_data_show_fieldFixed;
	if(value != null){
		selectType = value;
	}
	//根据值来决定后面的domHTML
	html += xform_main_data_getFieldValueDomByType(type,selectType,field,fieldDatas);
	html += "</div>";
	html += "</div>";
	return html;
}

//触发改变输入值后面的dom
function xform_main_data_changeFieldValue(selectDom){
	//debugger;
	var $tr = $(selectDom).closest("tr");
	//查找最后一个同名的fdWhereSelectField
	var type = $tr.find("select[name='fdAttrField']:last option:selected").attr('data-type');
	//alert(type);
	// 枚举型需要根据field来查找选项
	var field = $tr.find("select[name='fdAttrField']:last option:selected").val();
	var valueType = $tr.find("select[name='fdWhereSelectValue'] option:selected").val();
	$tr.find(".xform_main_data_fieldDomWrap").html(xform_main_data_getFieldValueDomByType(type.toLowerCase(),valueType,field));
}

//输入值后面的dom type:属性的类型；	valueType:值的类型	field:属性名（在枚举类型的时候需要根据属性名取相应的选项）
function xform_main_data_getFieldValueDomByType(type,value,field,fieldDatas){
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
		if(valueType == _xform_main_data_show_fieldFixed){
			//如果是日期类型，则显示日期选择；如果是枚举类型，则显示单选（是否）；如果是地址本，则显示地址本
			if(type == "datetime"){
				//构建时间控件
				html += "<div class='inputselectsgl' style='width:120px;' onclick=\"xform_main_data_triggleSelectDate(event,this);\">";
				html += "<div class='input'><input name='" + xform_main_data_show_whereSelectFieldInputName + "' type='text' validate='__datetime' value='"+ inputValue +"'></div>";
				html += "<div class='inputdatetime'></div>";
				html += "</div>";
			}else if(type == "boolean"){
				var booleanShowValueArray = ['是','否'];
				var booleanRealValueArray = ['1','0'];
				for(var i = 0; i < booleanShowValueArray.length;i++){
					html += "<label><input type='radio' name='"+ xform_main_data_show_whereSelectFieldInputName + "'";
					if(inputValue != '' && inputValue == booleanRealValueArray[i]){
						html += " checked";
					}
					html += " value='" + booleanRealValueArray[i] + "'>"+ booleanShowValueArray[i] +"</label>";
				}
			}else if(type == 'enum'){
				//枚举型
				var items = xform_main_data_findItemByDict(field,fieldDatas);
				if(items){
					var valuesArray = items.enumValues;
					var values;
					// 默认隔开空格个数
					var appendSpaceHtml = '&nbsp;&nbsp;';
					// 例如inputValue为： m;f
					if(inputValue != ''){
						values = inputValue.split(";");
					}
					var enumNameNum = ++_xform_main_data_show_radioNameNum;
					// 枚举的修改为单选
					for(var i = 0; i < valuesArray.length;i++){
						/*html += "<label><input type='checkbox' name='_"+ xform_main_data_show_whereSelectFieldInputName + "' onclick='xform_main_data_setCheckBoxValue(this);'";
						// 只要存在于该数组里面
						if(values && $.inArray(valuesArray[i].fieldEnumValue,values) > -1){
							html += " checked";
						}*/
						html += "<label><input type='radio' name='"+ xform_main_data_show_whereSelectFieldInputName + "_" + enumNameNum + "' ";
						// 只要存在于该数组里面
						if(values && $.inArray(valuesArray[i].fieldEnumValue,values) > -1){
							html += " checked";
						}
						html += " value='" + valuesArray[i].fieldEnumValue + "'>"+ valuesArray[i].fieldEnumLabel +"</label>" + appendSpaceHtml;
					}	
					// 构造真正的存储复选框置的input
					//html += "<input type='hidden' name='"+ xform_main_data_show_whereSelectFieldInputName +"' />"
				}
				
			}else{
				html += "<input type='text' name='" + xform_main_data_show_whereSelectFieldInputName + "' class='inputsgl' value='"+ inputValue +"'/>";
			}
		}
		return html;
	}
}

// 设置实际的复选框的值 暂无用
function xform_main_data_setCheckBoxValue(dom){
	var checkboxs = $(dom).closest('div').find("input[name='_"+ xform_main_data_show_whereSelectFieldInputName +"']");
	var hidden = $(dom).closest('div').find("input[name='"+ xform_main_data_show_whereSelectFieldInputName +"']");
	var val = "";
	for(var i = 0;i < checkboxs.length ;i++){
		var checkbox = checkboxs[i];
		if(checkbox && checkbox.checked){
			val += ";" + checkbox.value;
		}
	}
	if(val != ''){
		hidden[0].value = val.substring(1);
	}else{
		hidden[0].value = val;
	}
}

// 根据属性值去数据字典变量里面查找对应的选项
function xform_main_data_findItemByDict(field,fieldDatas){
	var dictData = null;
	if(fieldDatas){
		dictData = fieldDatas;
	}else{
	if(currentIndex!=null){
		var dd = $("input[name='sysFormMainDataShowRelateForms["+currentIndex+"].fieldArray']").val();
		//alert(dd);
		if(dd){
			dictData = $.parseJSON(dd.replace(/&quot;/g,"\""));
		}
	}else{
		dictData = xform_main_data_show_dictData;
	}
	}
	if(dictData && field){
		for(var i = 0;i <  dictData.length ;i++){
			if(dictData[i].field == field){
				return dictData[i];
			}
		}
	}
	return null;
}

//日期控件触发
function xform_main_data_triggleSelectDate(event,dom){
	var input = $(dom).find("input[name='"+ xform_main_data_show_whereSelectFieldInputName +"']");
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
		fieldVal += fieldOption.val() + _xform_main_data_show_split;
		fieldType += fieldOption.attr('data-type') + _xform_main_data_show_split;
	}
	selectJSON.field = fieldVal.substring(0,fieldVal.length - 1);
	selectJSON.fieldType = fieldType.substring(0,fieldType.length - 1);
	//处理运算符
	var operatorOption = $(tr).find("select[name='fdWhereSelectFieldOperator'] option:selected");
	selectJSON.fieldOperator = operatorOption.val();
	//处理输入值
	var valueOption = $(tr).find("select[name='fdWhereSelectValue'] option:selected");
	selectJSON.fieldValue = valueOption.val();
	
	var inputValue = $(tr).find("input[name^='" + xform_main_data_show_whereSelectFieldInputName + "']");
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

//提交时处理返回值
function xform_main_data_detailAttrWhere(tr,dealEnum){
	var valueJSON = {};
	//处理查询属性
	var attrOptions = $(tr).find("select[name='fdAttrField']");
	var fieldVal = "";
	var fieldType = "";
	for(var i = 0;i < attrOptions.length;i++){
		var fieldOption = $(attrOptions[i]).find("option:selected");
		var ty = fieldOption.attr('data-type');
		if(dealEnum && dealEnum == 'true' && ty == 'enum'){
			// 最多只有一个枚举类型
			valueJSON.enumType = fieldOption.attr('data-enumtype');
			valueJSON.enumShowType = $(tr).find("[name^='fdEnumShow']:checked").val();
		}
		fieldVal += fieldOption.val() + _xform_main_data_show_split;
		fieldType += ty + _xform_main_data_show_split;
	}
	valueJSON.field = fieldVal.substring(0,fieldVal.length - 1);
	valueJSON.fieldType = fieldType.substring(0,fieldType.length - 1);
	
	var alias = $(tr).find("input[name='alias']");
	if(alias && alias.length>0){
		valueJSON.alias = $(alias[0]).val();
	}
	var cards = $(tr).find("input[name='card']");
	if(cards && cards.length>0){
		//alert(cards[0].checked);
		valueJSON.card = cards[0].checked+"";
	}
	
	return valueJSON;
}

//查询预览的时候调用,通过js获取翻译
function xform_main_data_getFieldsText(tableId,field,selectName){
	var $selectsTr = $("#" + tableId).find("tr:not(.xform_main_data_tableTitle)");
	var fieldText = "";	
	var fieldArray = field.split(_xform_main_data_show_split);//对象类型
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
			var suffix = tableId.substring(tableId.lastIndexOf('_') + 1) + "_" + ++_xform_main_data_show_radioNameNum;
			$(this).after(xform_main_data_custom_buildEnumHtml(suffix));
		}
		xform_main_data_custome_stopBubble(event);
	});
}

// 设置枚举类型初始值
function xform_main_data_custom_initEnum($tr,selectedItem){
	if(selectedItem && selectedItem.fieldType){
		// 处理对象类型的枚举属性
		var fieldArray = selectedItem.fieldType.split(_xform_main_data_show_split);
		if(fieldArray.length > 0 && fieldArray[fieldArray.length - 1] == 'enum'){
			var tableId = $tr.closest('table').attr('id');
			var suffix = tableId.substring(tableId.lastIndexOf('_') + 1) + "_" + ++_xform_main_data_show_radioNameNum;
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
