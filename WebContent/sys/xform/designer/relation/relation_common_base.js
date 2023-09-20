Com_IncludeFile('json2.js');
Com_IncludeFile("relationFormula.js",Com_Parameter.ContextPath + "sys/xform/designer/relation/formula/","js",true);

Designer_Config.controls.relationCommonBase = {
	type : "",
	storeType : 'field',
	inherit : 'base',
	container : false,
	onDraw : null,
	drawXML : null,
	implementDetailsTable : true,
	attrs : {
		label : Designer_Config.attrs.label,
		required : {
			text : Designer_Lang.controlAttrRequired,
			value : "true",
			type : 'checkbox',
			checked : false,
			show : true
		},
		mobileRenderType :{
			//预占位置，子类直接覆盖
		},
		alignment : {
			//预占位置，子类直接覆盖
		},
		width : {
			//预占位置，子类直接覆盖
		},
		summary: {
			text: Designer_Lang.controlAttrSummary,
			value: "true",
			type: 'checkbox',
			checked: false,
			show: true
		},
		encrypt : Designer_Config.attrs.encrypt,
		source : {
			//展示样式
			text: Designer_Lang.relation_common_base_source,
			value : '',
			type : 'select',
			opts:relationSource.GetOptionsArray(),
			getVal:relationSource_getVal,
			onchange:'_Designer_Control_Attr_CommonBaseSource_Change(this)',
			show: true
		},
		source_oldValue : {
			text:"",
			show:false,
			skipLogChange:true
		},
		help:{
			text: Designer_Lang.relation_common_base_dataSourceConfigurationTips1 + '<br/>' + Designer_Lang.relation_common_base_dataSourceConfigurationTips2,
			type: 'help',
			align:'left',
			show: true
		},
		
		funName : {
			text : Designer_Lang.relation_common_base_busiName,//'业务名称',
			value : '',
			type : 'comText',
			readOnly : true,
			required: true,
			operate:"_Designer_Control_Attr_RelationCommonBase_FunName_Draw",
			show : true
		},
		funKey : {
			text : '函数key',
			value : '',
			type : 'text',
			show : false,
			skipLogChange:true
		},
		outputParams : {
			text : Designer_Lang.relation_common_base_outputParams,//传出参数
			value : '',
			type : 'self',
			init : [ {
				"fieldIdForm" : "textValue",
				"fieldNameForm" : Designer_Lang.relation_common_base_showText,//"显示值",
				"fieldName" : "",
				"fieldId" : "",
				"_required":true
			}, {
				"fieldIdForm" : "hiddenValue",
				"fieldNameForm" : Designer_Lang.relation_common_base_hiddeValue,//"隐藏值",
				"fieldName" : "",
				"fieldId" : "",
				"_required":true
			} ],
			checkout: Designer_RelationCommonBase_OutputParams_Required_Checkout,
			draw : _Designer_Control_Attr_RelationCommonBase_Output_Draw,
			show : true,
			getVal : relationCommonOutputParams_getVal,
			compareChange:relationCommonOutputParams_compareChange,
			translator: relationCommonOutputParams_translator
		},
		inputParams : {
			//传入参数
			text : Designer_Lang.relation_common_base_inputParams,//'传入参数',
			value : '',
			checkout: Designer_RelationCommonBase_InputParams_Required_Checkout,
			type : 'self',
			draw : _Designer_Control_Attr_RelationCommonBase_Inputout_Draw,
			show : true,
			getVal : relationCommonOutputParams_getVal,
			compareChange:relationCommonInputParams_compareChange,
			translator: relationCommonInputParams_translator
		},
		resetValue :{
			// 预占位置
		},
		inputParamsRequired : {
			text : '传入参数必填字段',
			value : '',
			type : 'text',
			show : false
		},
		defValue : {
			text: Designer_Lang.relation_common_base_defalutValue,
			value: "",
			type: 'text',
			show: true,
			convertor: Designer_Control_Attr_HtmlQuotEscapeConvertor
		},
		template : {
			text : '模板',
			value : '',
			varInfo : '',
			isLock:false,
			type : 'text',
			show : false
		}
	},
	resizeMode : 'onlyWidth'
};

function Designer_RelationCommonBase_Control_Source_Required_Checkout(msg, name, attr, value, values, control){
	if(values.source){
		return true;
	}
	msg.push(values.label,","+Designer_Lang.relation_select_sourceNotNull);
	return false;
}

function Designer_RelationCommonBase_InputParams_Required_Checkout(msg, name, attr, value, values, control){
	var val=value?value:"{}";
	var inputParamsMapping = JSON.parse(val.replace(/quot;/g,"\""));
	if(values.inputParamsRequired){
		var inputRequireds=values.inputParamsRequired.substring(0,values.inputParamsRequired.length-1).split(",");
		for(var i=0;i<inputRequireds.length;i++){
			var hasIn=false;
			for(var field in inputParamsMapping){
				if(field==inputRequireds[i]){
					if(!inputParamsMapping[field].fieldIdForm||!inputParamsMapping[field].fieldNameForm){
						msg.push(control.options.values.label,Designer_Lang.relation_common_base_inputParamsNotNull);
						return false;
					}
					hasIn=true;
				}
			}
			if(!hasIn){
				msg.push(control.options.values.label,Designer_Lang.relation_common_base_inputParamsNotNull);
				return false; 
			}
		}
	}
	return true;
}
function Designer_RelationCommonBase_OutputParams_Required_Checkout(msg, name, attr, value, values, control){
	var val=value?value:"{}";
	
	var fieldsTemplate = attr.init;

	var outputParamsMapping = JSON.parse(val.replace(/quot;/g,"\""));
	for ( var i = 0; i < fieldsTemplate.length; i++) {
		var field = fieldsTemplate[i];
		if(!field._required){
			continue;
		}
		if (outputParamsMapping && outputParamsMapping[field.fieldIdForm]) {
			 var uuid=outputParamsMapping[field.fieldIdForm].uuId;
			 if(!uuid){
				 msg.push(values.label,","+Designer_Lang.relation_common_base_outputParamsNotNull);
				 return false;
			 }
		}
		else{
			msg.push(values.label,","+Designer_Lang.relation_common_base_outputParamsNotNull);
			return false;
		}
	}
	return true;
}

function _Designer_Control_Attr_RelationCommonBase_FunName_Draw(){
	_Designer_Control_Attr_CommonBaseSource_Change(document.getElementsByName("source")[0],true);
}

function _Designer_Control_Attr_CommonBaseSource_Change(obj,choose) {
	var control = Designer.instance.attrPanel.panel.control;
	
	// 选择“--请选择---” 后无需处理业务
	if (!obj.value) {
		
		_Clear_Relation_Select_Attrs(control);
		
		control.options.values.source_oldValue='';
		control.options.values.source='';
		alert(Designer_Lang.relation_choose_sourceNotNul);
		return;
	}

	var source = relationSource.GetSourceByUUID(obj.value);
	control.options.values.funName=source.sourceName;
	// 如果扩展点钟 paramsURL为空，数据源即业务
	var rtnVal = {
		"_source" : obj.value,
		"_key" : obj.value,
		"_keyName" : control.options.values.funName
	};
	
	if (source.paramsURL) {
		
		 new ModelDialog_Show(Com_Parameter.ContextPath+source.paramsURL,rtnVal,function(rtnVal){
				// 没有选择函数
				if(!rtnVal||!rtnVal._key||rtnVal._key=='undefined'){
					//新数据源没有选择业务函数时 回退选择源数据源
					$("#relation_select_source").val(control.options.values.source_oldValue);
					
					return ;
				}
				_Clear_Relation_Select_Attrs(control);
				control.options.values.source_oldValue=obj.value;
				// 设置业务名称
				document.getElementsByName("funName")[0].value = rtnVal._keyName;
				control.options.values.funName = rtnVal._keyName;
				control.options.values.funKey = rtnVal._key;
				control.options.values.source=obj.value;
				_LoadInputParamsTemplate(control, obj.value, rtnVal._key );
				_LoadOutPutParamsTemplate(control, obj.value, rtnVal._key);
				
		 }).show();
	
	}
	//直接选择业务 
	else{
		
		if(choose){
			alert(Designer_Lang.relationBase_noMoreOptions);
			return;
		}
		_Clear_Relation_Select_Attrs(control);
		
		control.options.values.source_oldValue=obj.value;
		// 设置业务名称
		document.getElementsByName("funName")[0].value = rtnVal._keyName;
		control.options.values.funName = rtnVal._keyName;
		control.options.values.funKey = rtnVal._key;
		control.options.values.source=obj.value;
		_LoadInputParamsTemplate(control, obj.value, rtnVal._key );
		_LoadOutPutParamsTemplate(control, obj.value, rtnVal._key);
	}
}
function _Clear_Relation_Select_Attrs(control){

	$('#relation_select_inputs').html(Designer_Lang.relation_select_chooseSource);
	$('#relation_select_outputs').html(Designer_Lang.relation_select_chooseSource);
	// 清空输出
	control.options.values.outputParams = "";
	
	control.options.values.inputParams = "";

	// 清空业务
	document.getElementsByName("funName")[0].value = "";
	control.options.values.funName = "";
	control.options.values.funKey = "";
}
/**
 * 
 * @param control
 * @param source 选择的数据源，如TIB 或是其他
 * @param key    函数key
 * @param beanName 业务bean的id
 * @param mapping 
 * @return
 */
function _LoadOutPutParamsTemplate(control, source, key, beanName, outputParamsMapping) {
	var html = "";
	var fieldsTemplate = control.attrs.outputParams.init;

	for ( var i = 0; i < fieldsTemplate.length; i++) {
		var field = fieldsTemplate[i];
		
		html += _CreateOutParams(field) + "<br/>";
	}
	
	$("#relation_select_outputs").html(html);
}

function _LoadInputParamsTemplate(control, source, key) {

	 loadTemplate(key,source,function(data){
			var insStr = "";
			if(data&&data.ins){
				control.options.values.inputParamsRequired="";
				for ( var i = 0; i < data.ins.length; i++) {
					var field = data.ins[i];
					insStr += _CreateSelectInputParams(field) + "<br/>";
					if("1"==field._required){
						control.options.values.inputParamsRequired+=(field.uuId?field.uuId:field.fieldId)+",";
					}
				}
			}
			// 当有传入参数的时候，不能设置默认实际值；如果没有传入参数，可以设置默认实际值
			if(insStr){
				var defV=Designer_Control_Attr_AcquireParentTr("defValue");
				control.attrs.defValue.value="";
				if(control.options.values.defValue)control.options.values.defValue="";
				defV==null ? '' : defV.hide();
				//设置“清空值”
				var $resetValue = Designer_Control_Attr_AcquireParentTr("resetValue");
				if($resetValue != null){
					$resetValue.show();
				}
			}
			else{
				var defV=Designer_Control_Attr_AcquireParentTr("defValue");
				defV==null ? '' : defV.show();
				//设置“清空值”
				var $resetValue = Designer_Control_Attr_AcquireParentTr("resetValue");
				if($resetValue != null){
					$resetValue.hide();
				}
			}
			$("#relation_select_inputs").html(insStr);
		
	 },true);
}

//构建每个参数项
function _CreateSelectInputParams(field) {
	
	var html = [];
	var paramName = field.fieldName ? field.fieldName : field.fieldId;
		paramName=paramName?paramName:field.uuId;
	html.push("<label>" + paramName + "</lable>");
	// 必填
	if(field._required=="1"){
		html.push("<span class='txtstrong'>*</span>");
	}
	html.push("<br/>");
	
	html.push("<input type='hidden' id='" + field.uuId + "_required' value='"
			+ field._required + "'/>");
	// html.push("<input type='hidden' name='insParams' value='"+field.uuId+"'
	// />");
	html.push("<input type='hidden' id='" + field.uuId + "_formId' value='"
			+ field.fieldIdForm + "' />");
	html.push("<input id='" + field.uuId + "_formName' value='"
			+ field.fieldNameForm + "' onchange='Relation_Fiexed_InputParam_Change(this,\""
			+ field.uuId + "\");' style='width:80%;'/>");
	html.push(" <a href='javascript:void(0)' onclick='RelatoinOpenExpressionEditor(this,\""
			+ field.uuId + "\");'>"+Designer_Lang.relation_select_choose+"</a>");
	return html.join("");
}

function _CreateOutParams(field) {
	
	var html = [];
	var paramName = field.fieldNameForm ? field.fieldNameForm
			: field.fieldIdForm;
	var uuid = field.uuId ? field.uuId : field.fieldId;
	html.push("<label>" + paramName + "</lable><br/>");
	// html.push("<input type='hidden' name='insParams' value='"+field.uuId+"'
	// />");
	html.push("<input type='hidden' id='" + field.fieldIdForm
			+ "_fieldId' value='" + uuid + "' />");
	html.push("<input id='" + field.fieldIdForm + "_fieldName' value='"
			+ field.fieldName + "' readOnly=true style='width:80%;'/>");
	html.push(" <a href='javascript:void(0)' onclick='Open_Relation_Formula_Dialog(this,\""
			+ field.fieldIdForm + "\");'>"+Designer_Lang.relation_select_choose+"</a>");
	if(field._required){
		html.push("<span class='txtstrong'>*</span>");
	}
	return html.join("");
}
function Open_Relation_Formula_Dialog(a, fieldIdForm) {

	var control = Designer.instance.attrPanel.panel.control;
	var domElement = control.options.domElement;
    var values=control.options.values;
	if (!control.options.values.funKey) {
		alert(Designer_Lang.relation_select_chooseSource);
		return;
	}	
	loadTemplate(values.funKey,values.source,function(data,varInfo){
		
		 
		 Relation_Formula_Dialog(
					document.getElementById(fieldIdForm + "_fieldId"),
					document.getElementById(fieldIdForm + "_fieldName"),
					varInfo,
					"String",
					function(rtn){
						if(!rtn){
							return;
						}
						var control = Designer.instance.attrPanel.panel.control;

						control.options.values.outputParams = control.options.values.outputParams ? control.options.values.outputParams
								: "{}";
						var outputParamsJSON = JSON
								.parse(control.options.values.outputParams.replace(/quot;/g,"\""));

						var t = {};
						t.uuId = rtn.data[0].id;
						t.fieldName = rtn.data[0].name;
						outputParamsJSON[fieldIdForm] = t;

						control.options.values.outputParams = JSON.stringify(
								outputParamsJSON).replace(/"/g,"quot;");
						
						
					});
	 });
}
function _Designer_Control_Attr_RelationCommonBase_Inputout_Draw(name, attr, value,
		form, attrs, values, control) {
	var val = value;

	html = "";

	if (values.funKey) {
		if (!val) {
			val = "{}";
		}
		var mapping = JSON.parse(val.replace(/quot;/g,"\""));
		 
		 loadTemplate(values.funKey,values.source,function(data){
			 
			 
			// var template = JSON.parse(JSON.stringify(data).replace(/"/g,"quot;"));
			 
			 var insStr = "";
			if(data && data.ins && data.ins.length > 0){
				values.inputParamsRequired="";
				// 当有传入参数的时候，不能设置默认实际值；如果没有传入参数，可以设置默认实际值
				setTimeout(function(){
					var defV = Designer_Control_Attr_AcquireParentTr("defValue");
					if(defV){
						control.attrs.defValue.value="";
						if(control.options.values.defValue){
							control.options.values.defValue="";
						}
						defV.hide();	
					}
				},0);
				
				for ( var i = 0; i < data.ins.length; i++) {
					var fieldTemp = data.ins[i];
					var field={};
					// 克隆一个对象，防止模板被修改
					$.extend(true,field, fieldTemp);
					if (mapping && mapping[field.uuId]) {
						field.fieldIdForm = mapping[field.uuId].fieldIdForm;
						field.fieldNameForm = mapping[field.uuId].fieldNameForm;
					}
					if("1"==field._required){
						values.inputParamsRequired+=(field.uuId?field.uuId:field.fieldId)+",";
					}
					insStr += _CreateSelectInputParams(field) + "<br/>";
				}
			}else{
				//当没有传入参数时，应该去掉清空值选项
				setTimeout(function(){
					var resetValue = Designer_Control_Attr_AcquireParentTr("resetValue");
					if(resetValue){
						control.attrs.resetValue.value="";
						if(control.options.values.resetValue){
							control.options.values.resetValue="";
						}
						resetValue.hide();	
					}
				},0);
			}
			html += "<div id='relation_select_inputs'> " + insStr + "</div>";
			 
		 });
		// _LoadTemplate("","","",mapping);
	} else {
		html += "<div id='relation_select_inputs'>"+Designer_Lang.relation_common_base_chooseSource+"</div>";
	}
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}
function _Designer_Control_Attr_RelationCommonBase_Output_Draw(name, attr,
		value, form, attrs, values, control) {
	if (!control.options.values.funKey) {
		var html = "<div id='relation_select_outputs'>"+Designer_Lang.relation_common_base_chooseSource+"</div>";
		return Designer_AttrPanel.wrapTitle(name, attr, value, html);
	}
	var html = "<div id='relation_select_outputs'>";
	var fieldsTemplate = attr.init;
	
	var val = value ? value : "{}";
	var outputParamsMapping = JSON.parse(val.replace(/quot;/g,"\""));
	for ( var i = 0; i < fieldsTemplate.length; i++) {
		var fieldTemp = fieldsTemplate[i];
		var field={};
		// 克隆一个对象，防止模板被修改
		$.extend(true,field, fieldTemp);
		if (outputParamsMapping && outputParamsMapping[field.fieldIdForm]) {
			field.uuId = outputParamsMapping[field.fieldIdForm].uuId;
			field.fieldName = outputParamsMapping[field.fieldIdForm].fieldName;
		}

		html += _CreateOutParams(field) + "<br/>";
	}
	html+="</div>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

function RelatoinOpenExpressionEditor(obj, uuid, action) {

	var idField, nameField;
	idField = document.getElementById(uuid + "_formId");
	nameField = document.getElementById(uuid + "_formName");
	_requiredValue=document.getElementById(uuid + "_required").value;
	
	RelatoinFormFieldChoose(idField,nameField, function(rtn){
		var control = Designer.instance.attrPanel.panel.control;

		control.options.values.inputParams = control.options.values.inputParams ? control.options.values.inputParams
				: "{}";
		var inputParamsJSON = JSON
				.parse(control.options.values.inputParams.replace(/quot;/g,"\""));
		if(rtn&&rtn.data&&rtn.data[0].id){
			var t = {};
			t.fieldIdForm = rtn.data[0].id;
			t.fieldNameForm = rtn.data[0].name;
			t._required=_requiredValue;
			inputParamsJSON[uuid] = t;
			control.options.values.inputParams = JSON.stringify(
					inputParamsJSON).replace(/"/g,"quot;");
		}
		else{
			//清空选择时，需要取消映射
			delete inputParamsJSON[uuid];
			control.options.values.inputParams = JSON.stringify(
					inputParamsJSON).replace(/"/g,"quot;");
		}
		
		
	});
};

function relationSource_getVal(name,attr,value,controlValue) {
	var opts = attr["opts"];
	if(undefined==opts){
    		return "";
    	}
	for (var i = 0; i < opts.length; i++) {
		var opt = opts[i];
		if (opt.value === value) {
			controlValue[name] = opt.text;
			return opt.text;
		}
	}
	return "";
}

/** 动态下拉,动态单选,动态多选 变更日志相关start */
function relationCommonOutputParams_getVal(name,attr,value,controlValue){
	var val = value || "";
	val = val.replace(/quot;/g,"\"");
	if (val === "") {
		return;
	}
	val = JSON.parse(val);
	controlValue[name] = val;
}

function relationCommonOutputParams_compareChange(name,attr,oldValue,newValue) {
	var oldTextValue = oldValue["textValue"] && oldValue["textValue"].fieldName;
	var oldHiddenValue = oldValue["hiddenValue"] && oldValue["hiddenValue"].fieldName;
	var newTextValue = newValue["textValue"] && newValue["textValue"].fieldName;
	var newHiddenValue = newValue["hiddenValue"] && newValue["hiddenValue"].fieldName;
	if (oldTextValue != newTextValue || oldHiddenValue != newHiddenValue) {
		var textValChange = {};
		textValChange.oldVal = oldTextValue + "#" + oldHiddenValue;
		textValChange.newVal  = newTextValue + "#" + newHiddenValue;
		return JSON.stringify(textValChange);
	}
}

function relationCommonInputParams_compareChange(name,attr,oldValue,newValue) {
	var oldValueArr = [];
	var newValueArr = [];
	if (oldValue != ""){
		for (var key in oldValue) {
			var oldText = oldValue[key].fieldNameForm;
			oldValueArr.push(oldText);
		}
	}
	if (newValue != "") {
		for (var key in newValue) {
			var newText = newValue[key].fieldNameForm;
			newValueArr.push(newText);
		}
	}
	var oldValText = oldValueArr.join(";");
	var newValText = newValueArr.join(";")
	if (oldValText != newValText) {
		var textValChange = {};
		textValChange.oldVal = oldValText;
		textValChange.newVal  = newValText;
		textValChange.name = "textValue";
		
		return JSON.stringify(textValChange); 
	}
}

function relationCommonInputParams_translator(change) {
	if (!change) {
		return "";
	}
	var change = JSON.parse(change);
	var html = "<span> 由 (" + change.oldVal + ") 变更为 (" + change.newVal + ")</span>";
	return html; 
}

function relationCommonOutputParams_translator(change) {
	if (!change) {
		return "";
	}
	var change = JSON.parse(change);
	return "<span> 由 " + change.oldVal + "	变为	" + change.newVal + " </span>";
}

/** 动态下拉,动态单选,动态多选 变更日志相关end */

//========================================================================

/** 选择框,数据填充 变更日志start */
function relationInputParams_getVal(name,attr,value,controlValue) {
	var val = value || "";
	val = val.replace(/quot;/g,"\"");
	if (val === "") {
		return;
	}
	val = JSON.parse(val);
	controlValue[name] = val;
}

function relationOutParams_getVal(name,attr,value,controlValue) {
	var val = value || "";
	val = val.replace(/quot;/g,"\"");
	if (val === "") {
		return;
	}
	val = JSON.parse(val);
	var itemObjs = {};
	for (var key in val) {
		if (/^fm/.test(key)) {
			itemObjs[key] = val[key];
		}
	}
	controlValue[name] = itemObjs;
}

function relationOutParams_translator(change) {
	if (!change) {
		return "";
	}
	var change = JSON.parse(change);
	return "<span> 由 " + change.oldVal + "	变为	" + change.newVal + " </span>";
}

function relationOutParams_compareChange(name,attr,oldValue,newValue) {
	var newValArr = [];
	var oldValArr = [];
	for (var key in newValue) {
		var newParam = newValue[key];
		var newItemVal = newParam.fieldNameForm + "#" + newParam.fieldName;
		newValArr.push(newItemVal);
	}
	for (var key in oldValue) {
		var oldParam = oldValue[key];
		var oldItemVal = oldParam.fieldNameForm + "#" + oldParam.fieldName;
		oldValArr.push(oldItemVal);
	}
	if (newValArr.join("~") != oldValArr.join("~")) {
		var changeResult = {}
		changeResult.oldVal = JSON.stringify(oldValArr);
		changeResult.newVal = JSON.stringify(newValArr);
		return JSON.stringify(changeResult);
	}
}
/** 选择框,数据填充 变更日志end */
