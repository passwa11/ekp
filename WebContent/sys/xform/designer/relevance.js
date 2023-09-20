/**********************************************************
功能：关联控件
使用：
	
作者：朱国荣
**********************************************************/

Designer_Config.controls['relevance'] = {
		type : "relevance",
		//storeType : 'none',
		inherit    : 'base',
		needInsertValidate : false, //虽然不是容器，但确实需要插入校验
		onDraw : _Designer_Control_Relevance_OnDraw,
		drawMobile : _Designer_Control_Relevance_DrawMobile,
		onDrawEnd : _Designer_Control_Relevance_OnDrawEnd,
		drawXML : _Designer_Control_Relevance_DrawXML,
		destroyMessage: Designer_Lang.relevance_sureDeleteRelevance,
		_destroy : Designer_Control_Destroy,
		implementDetailsTable : true,
		insertValidate: _Designer_Control_Relevance_InsertValidate, //插入校验
		onAttrLoad : _Designer_Control_Relevance_OnAttrLoad,
		info : {
			name: Designer_Lang.relevance
		},
		resizeMode : 'all',
		attrs : {
			label : Designer_Config.attrs.label,
			required: {
				text: Designer_Lang.controlAttrRequired,
				value: "true",
				type: 'checkbox',
				checked: false,
				show: true
			},
			encrypt : Designer_Config.attrs.encrypt,
			btnName: {
				text: Designer_Lang.relevance_btnName,
				value: "",
				type: 'text',
				show: true,
				lang: true
			},
			modelRange : {
				text: Designer_Lang.relevance_range,
				value: '',
				type: 'self',
				draw: _Designer_Control_Attr_ModelRange_Self_Draw,
				show: true
			},
			modelRangeValue : {
				text: Designer_Lang.relevance_rangeValue,
				value: '',
				type: 'hidden',
				show: true,
				validator: Designer_Relevance_modelRangeValue_Validator,
				skipLogChange:true
			},
			isUseNew : {
				text: Designer_Lang.relevance_isUseNew,
				value: '',
				type: 'hidden',
				show: true,
				skipLogChange:true
			},
			isMul : {
				text : Designer_Lang.relevance_option,
				value: 'multi',
				type: 'radio',
				opts: [
					{text:Designer_Lang.relevance_single,value:'single'},
					{text:Designer_Lang.relevance_multi,value:'multi'}
				],
				translator:opts_common_translator,
				show: true
			},
			outputParams : {
				text : "<span>"+Designer_Lang.relation_event_params_out1+Designer_Lang.relation_event_params_out2+"</span>" +
						"<div align='center'><img onclick='_Designer_Control_Add_Relevance_Self_Output(this,0);' title='"+Designer_Lang.controlNew_AddressOutputParamsMsg+"' src='relation_event/style/icons/add.gif' style='cursor:pointer'></img></div>",//传出参数
				value : '',
				type : 'self',
				draw : _Designer_Control_Relevance_Self_Output_Draw,
				show : true,
				validator: Designer_Relevance_outputParams_Validator,
				checkout: Designer_Relevance_outputParams_Checkout,
				getVal : relationCommonOutputParams_getVal,
				compareChange: Relevance_OutParams_compareChange,
				translator: Relevance_OutputParams_translator,
				displayText: Designer_Lang.relation_event_params_out1 + Designer_Lang.relation_event_params_out2
			},
			isOutout : {
				text: Designer_Lang.controlNew_AddressIsShowOutputParams,
				value: 'false',
				type: 'hidden',
				skipLogChange:true
			},
			inputParams : {
				translatorText:Designer_Lang.relation_common_base_inputParams,
				text : "<span>"+Designer_Lang.relation_common_base_inputParams+"</span>" +
					   "<div align='center'><img onclick='_Designer_Control_Relevance_Self_Input(this,0);' src='relation_event/style/icons/add.gif' style='cursor:pointer'></img></div>",//传入参数
				value : '',
				type : 'self',
				draw : _Designer_Control_Relevance_Self_Input_Draw,
				show : true,
				validator: Designer_Relevance_inputParams_Validator,
				checkout: Designer_Relevance_inputParams_Checkout,
				getVal : relationCommonOutputParams_getVal,
			    compareChange:relevance_compareChange,
			    translator: relationCommonInputParams_translator
			},
			isInput : {
				text: Designer_Lang.controlNew_AddressIsShowOutputParams,
				value: 'false',
				type: 'hidden',
				skipLogChange:true
			},
			docStatus:{
				text: Designer_Lang.relevance_docStatus,
				value: '',
				type: 'self',
				draw: _Designer_Control_Relevance_DocStatus_Self_Draw,
				opts: [{text:Designer_Lang.relevance_append,value:"20", name: '_status_append'},
				       {text:Designer_Lang.relevance_publish,value:"30", name: '_status_publish'},
				       {text:Designer_Lang.relevance_discard,value:"00", name: '_status_discard'}],
				show: true
			},
			diffusionAuth : {
				text: Designer_Lang.relevance_diffusionAuth,
				value: 'true',
				type: 'self',
				draw: _Designer_Control_Attr_DiffusionAuth_Self_Draw,
				show: true
			}
		}
};
function relationCommonOutputParams_getVal(name,attr,value,controlValue){
	    var val = value || "";
	    val = val.replace(/quot;/g,"\"");
	    if (val === "") {
	       	return;
	    }
	    val = JSON.parse(val);
	    controlValue[name] = val;
}

function relevance_compareChange(name,attr,oldValue,newValue) {
	var oldValueArr = [];
	var newValueArr = [];
	if (oldValue != ""){
		for (var key in oldValue) {
			var oldText = oldValue[key].fieldNameForm+"#"+oldValue[key].fieldName;
			oldValueArr.push(oldText);
		}
	}
	if (newValue != "") {
		for (var key in newValue) {
			var newText = newValue[key].fieldNameForm+"#"+newValue[key].fieldName;
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
function Relevance_OutputParams_translator(change) {
	if (!change) {
		return "";
	}
	var change = JSON.parse(change);
	return "<span> 由 " + change.oldVal + "	变为	" + change.newVal + " </span>";
}
function Relevance_OutParams_compareChange(name,attr,oldValue,newValue) {
	var newValArr = [];
	var oldValArr = [];
	for (var key in newValue) {
		var newParam = newValue[key];
		if(newParam.fieldNameForm == undefined && newParam.fieldName == undefined ){
			continue;
		}
		var newItemVal = newParam.fieldNameForm + "#" + newParam.fieldName;
		newValArr.push(newItemVal);
	}
	for (var key in oldValue) {
		var oldParam = oldValue[key];
		if(oldParam.fieldNameForm == undefined && oldParam.fieldName == undefined ){
			continue;
		}
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
Designer_Config.operations['relevance'] = {
		lab : "2",
		imgIndex : 50,
		title : Designer_Lang.relevance,
		order: 9,
		run : function (designer) {
			designer.toolBar.selectButton('relevance');
		},
		type : 'cmd',
		select: true,
		cursorImg: 'style/cursor/relevance.cur'
	};

Designer_Config.buttons.tool.push("relevance");

Designer_Menus.tool.menu['relevance'] = Designer_Config.operations['relevance'];

function _Designer_Control_Relevance_OnDraw(parentNode,childNode){
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	domElement.id = this.options.values.id;		
	domElement.style.width='27px';
	//设置默认与左边文字域绑定
	domElement.label = _Get_Designer_Control_Label(this.options.values, this);
	var html = document.createElement("label");
	html.style.background = "url(style/img/relevance.png) no-repeat";
	html.style.width='24px';
	html.style.height='24px';
	html.style.display="inline-block";
	if (this.options.values.required == "true") {
		$(html).attr("required","true");
		$(html).attr("_required","true");
		$(domElement).attr("required","true");
		$(domElement).attr("_required","true");
	} else {
		$(html).attr("required","false");
		$(html).attr("_required","false");
		$(domElement).attr("required","false");
		$(domElement).attr("_required","false");
	}
	$(domElement).append(html);
	if(this.options.values.required == "true"){
		$(domElement).append('<span class=txtstrong>*</span>');
	}
	if(this.options.values.btnName){
		$(domElement).attr("btnName","true");
	}
	//判断关联范围值中是否含有“|”,若含有则认为新数据,不含则为历史数据,为空按历史数据处理
	if(this.options.values.modelRangeValue){
		if(/\|/.test(this.options.values.modelRangeValue)){
			$(domElement).attr("isUseNew","true");
			this.options.values.isUseNew = "true";
		}
	}
	if(this.options.values.isMul == 'multi' || this.options.values.isMul == null){
		$(domElement).attr("isMul","true");
	}else{
		$(domElement).attr("isMul","false");
	}
	if (this.attrs.docStatus) {
		var docStatus = [];
		var opts = this.attrs.docStatus.opts;
		var values = this.options.values;
		for (var i = 0; i < opts.length; i ++) {
			var opt = opts[i];
			if (values[opt.name] == null && this.attrs.docStatus.value != null && opts[i].value == this.attrs.docStatus.value) {
				values[opt.name] = "true";
			}
			if (values[opt.name] == "true") {
				docStatus.push(opt.value);
			}
		}
		values._docStatus = docStatus.join(';');
	}
	if (values.inputParams) {
		$(domElement).attr("inputParams",values.inputParams);
	}
	if (values.outputParams) {
		$(domElement).attr("outputParams",values.outputParams);
	}
}

function _Designer_Control_Relevance_OnDrawEnd(){
	
}

//cell指被插入的单元，control只即将插入的控件
function _Designer_Control_Relevance_InsertValidate(cell, control){
	//权限控件支持插入
	/*if(control && control.container){
		alert(Designer_Lang.relevance_notSupportPutInConainer);
		return false;
	}*/
	return true;
}

function _Designer_Control_Relevance_DrawXML(){
	var values = this.options.values;
	//配置的模块范围
	var customElementProperties = {};
	customElementProperties.modelRange = values.modelRangeValue;
	customElementProperties.diffusionAuth = values.diffusionAuth;
	customElementProperties.isMultiVal = "true";
	customElementProperties.controlId = values.id;
	if(values.isUseNew && values.isUseNew == "true"){
		customElementProperties.isUseNew = "true";
	}
	customElementProperties.docStatus = values._docStatus;
	customElementProperties.inputParams = values.inputParams;
	//配置后台存储的字段
	var buf = ['<extendSimpleProperty '];
	buf.push('name="', values.id+ "_config", '" ');
	buf.push('label="', values.label, '" ');
	buf.push('type="', 'String', '" ');
	// 控件类型
	buf.push('businessType="', this.type, '" ');
	buf.push('customElementProperties="',Designer.HtmlEscape(JSON.stringify(customElementProperties)),'" ');
	buf.push('canDisplay="','false','" ');
	buf.push('store="','false','" ');
	buf.push('/>');
	//配置前端需要存储的字段
	buf.push('\r\n<extendSimpleProperty ');
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	buf.push('type="', 'String', '" ');
	buf.push('length="','4000','" ');
	// 控件类型
	buf.push('businessType="', this.type, '" ');
	buf.push('customElementProperties="',Designer.HtmlEscape(JSON.stringify(customElementProperties)),'" ');
	// 字段是否需要加密
	if (values.encrypt == 'true') {
		buf.push('encrypt="true" ');
		buf.push('encryptionMethod="AES" ');
	}
	
	buf.push('/>');
	return buf.join('');
}

//关联范围属性绘制
function _Designer_Control_Attr_ModelRange_Self_Draw(name, attr, value, form, attrs, values,control){
	var html = [];
	value = typeof(value) == 'undefined' ? '' : value;
	html.push('<textarea readonly name='+name+'>'+value+'</textarea>');
	html.push('<a href="javascript:void(0)" onclick="Designer_Control_Attr_SelectModelRange();" style="vertical-align:bottom;">'+Designer_Lang.attrpanelSelect+'</a>');
	html.push('</br><span style="color:#9e9e9e;">'+Designer_Lang.relevance_associatedAllModulesWhenNull+'</span>');
	setTimeout(function(){
		if(document.getElementsByName("modelRangeValue")[0]){
			document.getElementsByName("modelRangeValue")[0].value = values.modelRangeValue || '';
		}
	},0);
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(''));
}

//文档状态属性绘制
function _Designer_Control_Relevance_DocStatus_Self_Draw(name, attr, value, form, attrs, values,control){
	var html = [];
	html.push('<input type="hidden" value="" name="' + name + '">');
	for (var i = 0, l = attr.opts.length; i < l; i ++) {
		var opt = attr.opts[i];
		html.push('<label isfor="true"><input type="checkbox" value="true" name="'+opt.name+'"');
		if((values[opt.name] == null && opt.checked) || values[opt.name] == "true"){
			html.push(' checked="checked"');
		}
		html.push('>'+opt.text + '</label><br>');
	}
	html.push('<span style="color:#9e9e9e;">'+Designer_Lang.relevance_ifNull+'</span>');
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(''));
}

// 权限传递
function _Designer_Control_Attr_DiffusionAuth_Self_Draw(name, attr, value, form, attrs, values,control){
	var isOpenInSys = _Designer_Control_Attr_DiffusionAuth_CheckRelevanceEnable();
	// 只有全局开关开启才显示属性
	if(isOpenInSys){
		var html = [];
		value = typeof(value) == 'undefined' ? '' : value;
		html.push("<label isfor='true'>");
		html.push("<input type='checkbox' name='diffusionAuth' ");
		var diffusionAuth = control.options.values.diffusionAuth || 'true';
		if(diffusionAuth == 'true'){
			html.push(" checked='checked'");		
		}
		html.push(" onclick='_Designer_Control_Attr_DiffusionAuth_SetValue(this);'/>");
		html.push(Designer_Lang.relevance_diffusionAuth_note);
		html.push("</label>");
		setTimeout(function(){
			// 只有开启了，才可以进行单个调整
			if(document.getElementsByName("diffusionAuth")[0]){
				// #168826 关联文档从多选改成单选，但是表单修改日志显示传阅文档阅读权限也变更了，实际上没有变更 修复：初始值改为true
				document.getElementsByName("diffusionAuth")[0].value = values.diffusionAuth || 'true';
			}
		},0);
		return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(''));
	}
	return '';
}

// 查询总开关是否开启，默认关闭
function _Designer_Control_Attr_DiffusionAuth_CheckRelevanceEnable(){
	var flag = false;
	var url = Com_Parameter.ContextPath + 'sys/xform/controls/relevance.do?method=isEnabled';
	$.ajax({ url: url, async: false, dataType: "json", cache: false, success: function(rtn){
		if("1"==rtn.status)
			flag = true;
	}});
	return flag;
}

function _Designer_Control_Attr_DiffusionAuth_SetValue(dom){
	if(dom){
		if(document.getElementsByName("diffusionAuth")[0]){
	    	document.getElementsByName("diffusionAuth")[0].value = dom.checked ? 'true':'false';
	    }
	}
}

function Designer_Control_Attr_SelectModelRange(){
	Dialog_Tree(true, 'modelRangeValue','modelRange', ',','sysFormRelevanceService&fdValue=!{value}', Designer_Lang.relevance_range,false,function(rtn){
		if(!rtn || !rtn.data){
			return;
		}
		var tr= Designer_Control_Attr_AcquireParentTr('inputParams');
		var tr2= Designer_Control_Attr_AcquireParentTr('outputParams');
		if(rtn.data.length!=1){
			$(tr).hide();
			$(tr2).hide();
		}else{
			$(tr).show();
			$(tr2).show();
		}
		$("input[name='inputParams']").val("");
		$("input[name='outputParams']").val("");
		$("input[name='isInput']").val('false');
		$("input[name='isOutout']").val('false');
		$("#relevance_inputs").html(Designer_Lang.relevance_noInputParams);
		$("#relevance_outputs").html(Designer_Lang.relation_event_noOutputParams);
	});
}

function _Designer_Control_Relevance_OnAttrLoad(form, control){
	var values = control.options.values;
	var tr= Designer_Control_Attr_AcquireParentTr('inputParams');
	var tr2= Designer_Control_Attr_AcquireParentTr('outputParams');
	if(values && values.modelRangeValue && values.modelRangeValue.indexOf(",")<0){
		$(tr).show();
		$(tr2).show();
	}else{
		$(tr).hide();
		$(tr2).hide();
		$("input[name='inputParams']").val("");
		$("input[name='outputParams']").val("");
		$("input[name='isInput']").val('false');
		$("input[name='isOutout']").val('false');
		$("#relevance_inputs").html(Designer_Lang.relevance_noInputParams);
		$("#relevance_outputs").html(Designer_Lang.relation_event_noOutputParams);
	}
}

function Designer_Relevance_modelRangeValue_Validator(elem, name, attr, value, values){
	if(value){
		var vals = value.split(",");
		var have = 0;
		var nohave = 0;
		for(var i=0;i<vals.length;i++){
			if(/\|/.test(vals[i])){
				have++;
			}else{
				nohave++;
			}
		}
		if(have>0 && nohave>0){
			alert(Designer_Lang.relevance_notMix);
			return false;
		}
	}
	return true;
}

//关联控件传出参数draw
function _Designer_Control_Relevance_Self_Output_Draw(name, attr,
		value, form, attrs, values, control) {
	var html = "<input name='outputParams' value='"+(values.outputParams?values.outputParams:"{}")+"' type='hidden'/>"
	html += "<input name='isOutout' value='"+(values.isOutout?values.isOutout:"false")+"' type='hidden'/>";
	if ((values.isOutout && values.isOutout=='false')||!values.isOutout) {
		html += "<div id='relevance_outputs'>"+Designer_Lang.relation_event_noOutputParams+"</div>";
		return Designer_AttrPanel.wrapTitle(name, attr, value, html);
	}
	
	html += "<div id='relevance_outputs'>";

	var val = value ? value : "{}";
	var outputParamsMapping = JSON.parse(val.replace(/quot;/g,"\""));
	var i=0;
	for(var fid in outputParamsMapping){
		var param = outputParamsMapping[fid];
		param.fid=fid;
		html += _Designer_Control_Relevance_CreateOutParams(param,i==0);
		i++;
	}
	html+="</div>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

//输出参数中+号触发的点击事件
function _Designer_Control_Add_Relevance_Self_Output(obj,hiddenFlag){
	//显示按钮面板
	Relation_ShowButton();

	var field={
			"fieldIdForm" : "",
			"fieldNameForm" : Designer_Lang.relation_event_formField,
			"fieldName" : Designer_Lang.relation_event_templateField,
			"fieldId" : "",
			"_required":false
		}; 
	//随机产生一个唯一标识
	field.fid="fm_"+Designer.generateID();
	var isFirst=false;
	//清空初始值                          
	if($("#relevance_outputs").html()==Designer_Lang.relation_event_noOutputParams){
		$("#relevance_outputs").html("");
		isFirst=true;
	}
	$("#relevance_outputs").append(_Designer_Control_Relevance_CreateOutParams(field,isFirst));         
}

function _Designer_Control_Relevance_delOutputParams(obj){
	//显示按钮面板
	Relation_ShowButton();
	
	var fid=$(obj).parent().attr("id");
	
	var outputParams = $("input[name='outputParams']").val();
	
	outputParams = outputParams ? outputParams : "{}";
	var outputParamsJSON = JSON.parse(outputParams.replace(/quot;/g,"\""));
	if(outputParamsJSON[fid]){
		//删除掉 这个对象的映射值 
		delete outputParamsJSON[fid];
	}
	$("input[name='outputParams']").val(JSON.stringify(
			outputParamsJSON).replace(/"/g,"quot;"));
	$(obj).parent().remove();
	//清空到最后一条的时候需要 加上初始值
	if(!$("#relevance_outputs").html()){
		$("#relevance_outputs").html(Designer_Lang.relation_event_noOutputParams);
		$("input[name='isOutout']").val('false');
	}
}

function _Designer_Control_Relevance_CreateOutParams(field,isFirst) {
	
	var html = [];

	var fid=field.fid;
	var fieldNameForm = field.fieldNameForm||'';
	html.push("<span id='"+fid+"'>");
	//第一个元素不需要分隔符
	if(!isFirst){
		html.push("<hr />");
	}
	
	html.push(" <img src='relation_event/style/icons/delete.gif' onclick='_Designer_Control_Relevance_delOutputParams(this);' style='cursor:pointer;vertical-align:middle;'></img>");
	
	html.push("<input type='hidden' id='" + fid + "_fieldIdForm' value='" + field.fieldIdForm + "' />");
	html.push("<input id='" + fid + "_fieldNameForm' value='"+ fieldNameForm + "' readOnly=true  style='width:58px;vertical-align:middle;'/>");
	
	html.push(" <img src='relation_event/style/icons/edit.gif' onclick='_Designer_Control_Relevance_OpenOutDialog(this,\""+fid+"\");' style='cursor:pointer;vertical-align:middle;'></img>");
	
	var uuid = field.uuId ? field.uuId : field.fieldId;
	var fieldName = field.fieldName||'';
	html.push("<input type='hidden' id='" + fid
			+ "_fieldId' value='" + uuid + "' />");
	html.push("<input id='" + fid + "_fieldName' value='"
			+ fieldName + "' readOnly=true  style='width:58px;vertical-align:middle;'/>");
	html.push(" <img src='relation_event/style/icons/edit.gif' onclick='_Designer_Control_Relevance_OpenOutMappingDialog(this,\""
			+ fid + "\");' style='cursor:pointer;vertical-align:middle;'></img>");
	
	html.push("</span>");
	return html.join("");
}

function _Designer_Control_Relevance_OpenOutDialog(obj, uuid) {
	idField = document.getElementById(uuid + "_fieldIdForm");
	nameField = document.getElementById(uuid + "_fieldNameForm");
	var varInfo = Designer.instance.getObj(false);
	var control = Designer.instance.attrPanel.panel.control;
	var controlId = control.options.values.id;
	for(var i=0;i<varInfo.length;i++){
		if(varInfo[i].controlType=="relevance"){
			varInfo.splice(i,1);
			i--;
		}
	}
	_Designer_Control_Relevance_FieldChoose(idField,nameField,function(rtn){
			if(!rtn){
				return;
			}
			
			Relation_ShowButton();
			
			$("input[name='isOutout']").val('true');
			
			var outputParams = $("input[name='outputParams']").val();
			outputParams = outputParams ? outputParams : "{}";
			var outputParamsJSON = JSON
			    .parse(outputParams.replace(/quot;/g,"\""));
	
			if(rtn&&rtn.data&&rtn.data[0].id){
				if(!outputParamsJSON[uuid]){
					outputParamsJSON[uuid]={};
				}
				outputParamsJSON[uuid].fieldIdForm=rtn.data[0].id;
				outputParamsJSON[uuid].fieldNameForm=rtn.data[0].name;
				outputParamsJSON[uuid].fieldDataTypeForm=rtn.data[0].dataType;
				
				$("input[name='outputParams']").val(JSON.stringify(
						outputParamsJSON).replace(/"/g,"quot;"));
			}
	},varInfo);
}

function _Designer_Control_Relevance_OpenOutMappingDialog(obj, uuid){
	idField = document.getElementById(uuid + "_fieldId");
	nameField = document.getElementById(uuid + "_fieldName");

	var varInfo = _Designer_Control_Relevance_SetInfo(true);
	//varInfo = [];
	_Designer_Control_Relevance_FieldFormChoose(idField,nameField,function(rtn){
			if(!rtn){
				return;
			}
			Relation_ShowButton();
			
			$("input[name='isOutout']").val('true');
			var outputParams = $("input[name='outputParams']").val();
			outputParams = outputParams ? outputParams : "{}";
			var outputParamsJSON = JSON
					.parse(outputParams.replace(/quot;/g,"\""));
			if(rtn&&rtn.data&&rtn.data[0].id){
				if(!outputParamsJSON[uuid]){
					outputParamsJSON[uuid]={};
				}
				outputParamsJSON[uuid].fieldId=rtn.data[0].id;
				outputParamsJSON[uuid].fieldName=rtn.data[0].name;
				outputParamsJSON[uuid].fieldDataType=rtn.data[0].dataType;
				outputParamsJSON[uuid].fieldDictType=rtn.data[0].dictType;
				
				$("input[name='outputParams']").val(JSON.stringify(
						outputParamsJSON).replace(/"/g,"quot;"));
			}
	},varInfo);
}

//关联控件传入参数draw
function _Designer_Control_Relevance_Self_Input_Draw(name, attr,
		value, form, attrs, values, control){
	var html = "<input name='inputParams' value='"+(values.inputParams?values.inputParams:"{}")+"' type='hidden'/>"
	html += "<input name='isInput' value='"+(values.isInput?values.isInput:"false")+"' type='hidden'/>";
	if ((values.isInput && values.isInput=='false')||!values.isInput) {
		html += "<div id='relevance_inputs'>"+Designer_Lang.relevance_noInputParams+"</div>";
		return Designer_AttrPanel.wrapTitle(name, attr, value, html);
	}
	
	html += "<div id='relevance_inputs'>";

	var val = value ? value : "{}";
	var inputParamsMapping = JSON.parse(val.replace(/quot;/g,"\""));
	var i=0;
	for(var fid in inputParamsMapping){
		var param = inputParamsMapping[fid];
		param.fid=fid;
		html += _Designer_Control_Relevance_CreateInParams(param,i==0);
		i++;
	}
	html+="</div>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

function _Designer_Control_Relevance_Self_Input(obj,hiddenFlag){
	//显示按钮面板
	Relation_ShowButton();

	var field={
			"fieldIdForm" : "",
			"fieldNameForm" : Designer_Lang.relevance_mappingFiled,
			"fieldName" : Designer_Lang.relevance_inputFiled,
			"fieldId" : "",
			"_required":false
		}; 
	//随机产生一个唯一标识
	field.fid="fm_"+Designer.generateID();
	var isFirst=false;
	//清空初始值                          
	if($("#relevance_inputs").html()==Designer_Lang.relevance_noInputParams){
		$("#relevance_inputs").html("");
		isFirst=true;
	}
	$("#relevance_inputs").append(_Designer_Control_Relevance_CreateInParams(field,isFirst));
}

function _Designer_Control_Relevance_CreateInParams(field,isFirst) {
	var html = [];
	var fid=field.fid;
	var fieldNameForm = field.fieldNameForm||'';
	html.push("<span id='"+fid+"'>");
	//第一个元素不需要分隔符
	if(!isFirst){
		html.push("<hr />");
	}
	html.push(" <img src='relation_event/style/icons/delete.gif' onclick='_Designer_Control_Relevance_delIntputParams(this);' style='cursor:pointer;vertical-align:middle;'></img>");
	html.push("<input type='hidden' id='" + fid + "_fieldIdForm' value='" + field.fieldIdForm + "' />");
	html.push("<input id='" + fid + "_fieldNameForm' value='"+ fieldNameForm + "' readOnly=true  style='width:58px;vertical-align:middle;'/>");
	html.push(" <img src='relation_event/style/icons/edit.gif' onclick='_Designer_Control_Relevance_OpenMappingDialog(this,\""+fid+"\");' style='cursor:pointer;vertical-align:middle;'></img>");
	var uuid = field.uuId ? field.uuId : field.fieldId;
	var fieldName = field.fieldName||'';
	html.push("<input type='hidden' id='" + fid
			+ "_fieldId' value='" + uuid + "' />");
	html.push("<input id='" + fid + "_fieldName' value='"
			+ fieldName + "' readOnly=true  style='width:58px;vertical-align:middle;'/>");
	html.push(" <img src='relation_event/style/icons/edit.gif' onclick='_Designer_Control_Relevance_OpenInputDialog(this,\""
			+ fid + "\");' style='cursor:pointer;vertical-align:middle;'></img>");
	html.push("</span>");
	return html.join("");
}

function _Designer_Control_Relevance_delIntputParams(obj){
	//显示按钮面板
	Relation_ShowButton();
	
	var fid=$(obj).parent().attr("id");
	
	var inputParams = $("input[name='inputParams']").val();
	
	inputParams = inputParams ? inputParams : "{}";
	var inputParamsJSON = JSON.parse(inputParams.replace(/quot;/g,"\""));
	if(inputParamsJSON[fid]){
		//删除掉 这个对象的映射值 
		delete inputParamsJSON[fid];
	}
	$("input[name='inputParams']").val(JSON.stringify(
			inputParamsJSON).replace(/"/g,"quot;"));
	$(obj).parent().remove();
	//清空到最后一条的时候需要 加上初始值
	if(!$("#relevance_inputs").html()){
		$("#relevance_inputs").html(Designer_Lang.relevance_noInputParams);
		$("input[name='isInput']").val('false');
	}
}

//映射字段dialog
function _Designer_Control_Relevance_OpenMappingDialog(obj, uuid){
	idField = document.getElementById(uuid + "_fieldIdForm");
	nameField = document.getElementById(uuid + "_fieldNameForm");

	var varInfo = _Designer_Control_Relevance_SetInfo();
	//varInfo = [];
	_Designer_Control_Relevance_FieldFormChoose(idField,nameField,function(rtn){
			if(!rtn){
				return;
			}
			Relation_ShowButton();
			
			$("input[name='isInput']").val('true');
			var inputParams = $("input[name='inputParams']").val();
			inputParams = inputParams ? inputParams : "{}";
			var inputParamsJSON = JSON
					.parse(inputParams.replace(/quot;/g,"\""));
			if(rtn&&rtn.data&&rtn.data[0].id){
				if(!inputParamsJSON[uuid]){
					inputParamsJSON[uuid]={};
				}
				inputParamsJSON[uuid].fieldIdForm=rtn.data[0].id;
				inputParamsJSON[uuid].fieldNameForm=rtn.data[0].name;
				inputParamsJSON[uuid].fieldDataTypeForm=rtn.data[0].dataType;
				inputParamsJSON[uuid].fieldDictTypeForm=rtn.data[0].dictType;
				
				$("input[name='inputParams']").val(JSON.stringify(
						inputParamsJSON).replace(/"/g,"quot;"));
			}
	},varInfo);
}

//入参字段dialog
function _Designer_Control_Relevance_OpenInputDialog(obj, uuid) {
	idField = document.getElementById(uuid + "_fieldId");
	nameField = document.getElementById(uuid + "_fieldName");
	
	_Designer_Control_Relevance_FieldChoose(idField,nameField,function(rtn){
			if(!rtn){
				return;
			}
			
			Relation_ShowButton();
			
			$("input[name='isInput']").val('true');
			
			var inputParams = $("input[name='inputParams']").val();
			inputParams = inputParams ? inputParams : "{}";
			var inputParamsJSON = JSON
					.parse(inputParams.replace(/quot;/g,"\""));
	
			if(rtn&&rtn.data&&rtn.data[0].id){
				if(!inputParamsJSON[uuid]){
					inputParamsJSON[uuid]={};
				}
				inputParamsJSON[uuid].fieldId=rtn.data[0].id;
				inputParamsJSON[uuid].fieldName=rtn.data[0].name;
				inputParamsJSON[uuid].fieldDataType=rtn.data[0].dataType;
				
				$("input[name='inputParams']").val(JSON.stringify(
						inputParamsJSON).replace(/"/g,"quot;"));
			}
	},Designer.instance.getObj(false));
}

//获得映射字段内容
function _Designer_Control_Relevance_SetInfo(isOut){
	var info = [];
	var url = Com_Parameter.ContextPath + "sys/xform/controls/relevance.do?method=getMappingFileds&modelRangeValue="+encodeURIComponent($("input[name='modelRangeValue']").val());
	if(isOut){
		url += "&isOut=true";
	}
	$.ajax({
		  url: url,
		  type:'GET',
		  async:false,//同步请求
		  success: function(json){
			  info=json;
		  },
		  dataType: 'json'
		});
	return info;
}

//入参字段数据请求
function _Designer_Control_Relevance_FieldChoose(idField,nameField, action,varInfo) {
	var dialog = new KMSSDialog();
	dialog.BindingField(idField, nameField);
	if(!varInfo){
		varInfo=Designer.instance.getObj(true);
	}
	dialog.Parameters = {
		varInfo : varInfo
	};
	dialog
			.SetAfterShow( function(rtn) {
				action(rtn);
			});
	dialog.URL = Com_Parameter.ContextPath
			+ "sys/xform/designer/relevance/relevance_formfields_tree.jsp?t="+encodeURIComponent(new Date());
	dialog.Show(window.screen.width*380/1366,window.screen.height*480/768);

}

//入参字段数据请求
function _Designer_Control_Relevance_FieldFormChoose(idField,nameField, action,varInfo) {
	var dialog = new KMSSDialog();
	dialog.BindingField(idField, nameField);
	if(!varInfo){
		varInfo=Designer.instance.getObj(true);
	}
	dialog.Parameters = {
		varInfo : varInfo
	};
	dialog
			.SetAfterShow( function(rtn) {
				action(rtn);
			});
	dialog.URL = Com_Parameter.ContextPath
			+ "sys/xform/designer/relevance/relevance_inputfields_tree.jsp?t="+encodeURIComponent(new Date());
	dialog.Show(window.screen.width*380/1366,window.screen.height*480/768);

}

function Designer_Relevance_inputParams_Validator(elem, name, attr, value, values){
	if(value){
		var inputParamsMapping = JSON.parse(value.replace(/quot;/g,"\""));
		for(var fid in inputParamsMapping){
			var param = inputParamsMapping[fid];
			if(param.fieldDataType && param.fieldDataTypeForm){
				if(param.fieldDataType != param.fieldDataTypeForm){
					alert(Designer_Lang.relevance_mappingFiled + param.fieldNameForm + Designer_Lang.relevance_and + Designer_Lang.relevance_inputFiled + param.fieldName + Designer_Lang.relevance_noAgree);
					return false;
				}
			}else{
				alert(Designer_Lang.relevance_mappingFiled + param.fieldNameForm + Designer_Lang.relevance_or + Designer_Lang.relevance_inputFiled + param.fieldName + Designer_Lang.relevance_noExist);
				return false;
			}
		}
	}
	return true;
}

function Designer_Relevance_inputParams_Checkout(msg, name, attr, value, values){
	if(value){
		var infos = Designer.instance.getObj(false);
		var inputParamsMapping = JSON.parse(value.replace(/quot;/g,"\""));
		for(var fid in inputParamsMapping){
			var param = inputParamsMapping[fid];
			for(var i = 0; i<infos.length; i++){
				if(param.fieldId == infos[i].name || param.fieldId == infos[i].name+'.id' || param.fieldId == infos[i].name+'.name'){
					var controlType = infos[i].controlType;
					if (controlType == 'address' || controlType == 'new_address') {
						if(param.fieldDataTypeForm){
							if(param.fieldDataTypeForm!="String"){
								msg.push(values.label+Designer_Lang.relevance_mappingFiled + param.fieldNameForm + Designer_Lang.relevance_and + Designer_Lang.relevance_inputFiled + param.fieldName + Designer_Lang.relevance_noAgree);
								return false;
							}
						}else{
							msg.push(values.label+Designer_Lang.relevance_mappingFiled + param.fieldNameForm + Designer_Lang.relevance_or + Designer_Lang.relevance_inputFiled + param.fieldName + Designer_Lang.relevance_noExist);
							return false;
						}
					}else{
						if(infos[i].type && param.fieldDataTypeForm){
							if(infos[i].type != param.fieldDataTypeForm){
								msg.push(values.label+Designer_Lang.relevance_mappingFiled + param.fieldNameForm + Designer_Lang.relevance_and + Designer_Lang.relevance_inputFiled + param.fieldName + Designer_Lang.relevance_noAgree);
								return false;
							}
						}else{
							msg.push(values.label+Designer_Lang.relevance_mappingFiled + param.fieldNameForm + Designer_Lang.relevance_or + Designer_Lang.relevance_inputFiled + param.fieldName + Designer_Lang.relevance_noExist);
							return false;
						}
					}
				}
			}
		}
	}
	return true;
}
function Designer_Relevance_outputParams_Validator(elem, name, attr, value, values){
	if(value){
		var outputParamsMapping = JSON.parse(value.replace(/quot;/g,"\""));
		for(var fid in outputParamsMapping){
			var param = outputParamsMapping[fid];
			if(param.fieldDataType && param.fieldDataTypeForm){
				var formType = param.fieldDataTypeForm.indexOf('BigDecimal') > -1 ? 'BigDecimal' : param.fieldDataTypeForm;
				if(param.fieldDataType != formType){
					alert(Designer_Lang.relevance_formFiled + param.fieldNameForm + Designer_Lang.relevance_and + Designer_Lang.relevance_tempFiled + param.fieldName + Designer_Lang.relevance_noAgree);
					return false;
				}
			}else{
				//#164717if中已经做判断，else这里提示词为undefined，去掉错误的提示词
				alert(Designer_Lang.relevance_formFiled + Designer_Lang.relevance_or + Designer_Lang.relevance_tempFiled + Designer_Lang.relevance_noExist);
				//alert(Designer_Lang.relevance_formFiled + param.fieldNameForm + Designer_Lang.relevance_or + Designer_Lang.relevance_tempFiled + param.fieldName + Designer_Lang.relevance_noExist);
				return false;
			}
		}
	}
	return true;
}

function Designer_Relevance_outputParams_Checkout(msg, name, attr, value, values){
	if(value){
		var infos = Designer.instance.getObj(false);
		var outputParamsMapping = JSON.parse(value.replace(/quot;/g,"\""));
		for(var fid in outputParamsMapping){
			var param = outputParamsMapping[fid];
			for(var i = 0; i<infos.length; i++){
				if(param.fieldId == infos[i].name || param.fieldId == infos[i].name+'.id' || param.fieldId == infos[i].name+'.name'){
					var controlType = infos[i].controlType;
					if (controlType == 'address' || controlType == 'new_address') {
						if(param.fieldDataTypeForm){
							if(param.fieldDataTypeForm!="String"){
								msg.push(values.label+Designer_Lang.relevance_formFiled + param.fieldNameForm + Designer_Lang.relevance_and + Designer_Lang.relevance_tempFiled + param.fieldName + Designer_Lang.relevance_noAgree);
								return false;
							}
						}else{
							msg.push(values.label+Designer_Lang.relevance_formFiled + param.fieldNameForm + Designer_Lang.relevance_or + Designer_Lang.relevance_tempFiled + param.fieldName + Designer_Lang.relevance_noExist);
							return false;
						}
					}else{
						if(infos[i].type && param.fieldDataTypeForm){
							if(infos[i].type != param.fieldDataTypeForm){
								msg.push(values.label+Designer_Lang.relevance_formFiled + param.fieldNameForm + Designer_Lang.relevance_and + Designer_Lang.relevance_tempFiled + param.fieldName + Designer_Lang.relevance_noAgree);
								return false;
							}
						}else{
							msg.push(values.label+Designer_Lang.relevance_formFiled + param.fieldNameForm + Designer_Lang.relevance_or + Designer_Lang.relevance_tempFiled + param.fieldName + Designer_Lang.relevance_noExist);
							return false;
						}
					}
				}
			}
		}
	}
	return true;
}