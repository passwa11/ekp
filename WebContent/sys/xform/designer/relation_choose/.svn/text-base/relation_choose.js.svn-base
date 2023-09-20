
// 获取所有数据源
// var relationSource = new relationSource();
Designer_Config.operations['relationChoose'] = {
	lab : "5",
	imgIndex : 58,
	title : Designer_Lang.relation_choose_name,
	run : function(designer) {
		designer.toolBar.selectButton('relationChoose');
	},
	type : 'cmd',
	order: 1.5,
	shortcut : '',
	select : true,
	cursorImg : 'style/cursor/relationChoose.cur'
};
Designer_Config.controls.relationChoose = {
	type : "relationChoose",
	storeType : 'field',
	inherit    : 'base',
	container : false,
	onDraw : _Designer_Control_RelationChoose_OnDraw,
	drawMobile : _Designer_Control_RelationChoose_DrawMobile,
	drawXML : _Designer_Control_RelationChoose_DrawXML,
	onInitialize : _Designer_Control_RelationChoose_OnInitialize,
	onInitializeDict : _Designer_Control_DisplayText_OnInitializeDict,
	initDefaultValueAfterPaste :_Designer_Control_RelationChoose_initDefaultValueAfterPaste,
	implementDetailsTable : true,
	attrs : {
		label : Designer_Config.attrs.label,
		required: {
			text: Designer_Lang.controlAttrRequired,
			value: "true",
			type: 'checkbox',
			checked: false,
			show: true
		},
		width : {
			text: Designer_Lang.controlAttrWidth,
			value: "120",
			type: 'text',
			show: true,
			validator: Designer_Control_Attr_Width_Validator,
			checkout: Designer_Control_Attr_Width_Checkout
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
			text: Designer_Lang.relation_select_source,
			value : '',
			type : 'select',
			opts:relationSource.GetOptionsArray(),
			onchange:'_Designer_Control_Choose_Attr_Source_Change(this)',
			show: true,
			getVal:relationSource_getVal
		},
		
		funName : {
			text : Designer_Lang.relation_select_busiName,//'业务名称',
			value : '',
			type : 'comText',
			readOnly : true,
			required: true,
			operate:"_Designer_Control_Attr_RelationChoose_Self_FunName_Draw",
			show : true
		},
		funKey : {
			text : '函数key',
			value : '',
			type : 'text',
			show : false,
			skipLogChange:true
		},

		listRule:{
			//分页

			text: Designer_Lang.relation_event_chooseList,
			value : '11',
			type : 'select',
			opts:[{"text":Designer_Lang.relation_event_singleNoPage,'value':'00'},
			      {"text":Designer_Lang.relation_event_singlePage,'value':'01'},
			      {"text":Designer_Lang.relation_event_mutiNoPage,'value':'10'},
			      {"text":Designer_Lang.relation_event_mutiPage,'value':'11'},
			      {"text":Designer_Lang.relation_event_mutiReturn,'value':'99'}],
			show : true
		},
		fillType:{
			//明细表传出参数填充方式	
			text: Designer_Lang.relation_event_fillType,
			value : '01',
			type : 'select',
			opts:[{"text":Designer_Lang.relation_event_fillType_detailadd,'value':'01'},
			      {"text":Designer_Lang.relation_event_fillType_detailrepace,'value':'11'}],
			show : true
		},
		fillTypeOne:{
			//单值传出参数填充方式	
			text: Designer_Lang.relation_event_fillTypeOne,
			value : '11',
			type : 'select',
			opts:[{"text":Designer_Lang.relation_event_fillTypeOne_replace,'value':'11'}],
			show : true
		},
		outerSearchParams:{
			text : '搜索参数',
			value : '',
			type : 'text',
			show : false,
			skipLogChange:true
		},
		outShowValue:{
			text : Designer_Lang.relation_choose_showvalue,
			value : '',
			type : 'self',
			required: true,
			skipLogChange:true,
			draw:_Designer_Control_Attr_RelationChoose_Self_outShowValue_Draw,
			show : true,
			checkout: function(msg, name, attr, value, values, control){
				if(!values.outShowValue){
				    msg.push(values.label+" "+Designer_Lang.relation_choose_showvalue,Designer_Lang.relation_choose_notNul);
					return false;
				}
				return true;
			}
		},
		outShowValueName:{
			text : Designer_Lang.relation_common_base_showText,
			show : false
		},
		outHiddenValue:{
			text : Designer_Lang.relation_choose_hidevalue,
			value : '',
			type : 'self',
			readOnly : true,
			required: true,
			skipLogChange:true,
			draw:_Designer_Control_Attr_RelationChoose_Self_outHiddenValue_Draw,
			show : true,
			checkout: function(msg, name, attr, value, values, control){
				if(!values.outHiddenValue){
				    msg.push(values.label+" "+Designer_Lang.relation_choose_hidevalue,Designer_Lang.relation_choose_notNul);
					return false;
				}
				return true;
			}
		},
		outHiddenValueName:{
	        text : Designer_Lang.relation_common_base_hiddeValue,
			show : false
		},
		//弹窗宽度
		dialogWidth:{
			text: Designer_Lang.relation_event_dialogWidth,
			value:'',
			type:'text',
			show:true,
			validator : [Designer_Control_Attr_Number_Validator],
			checkout: [Designer_Control_Attr_Number_Checkout]
		},
		//弹窗高度
		dialogHeight:{
			text: Designer_Lang.relation_event_dialogHeight,
			value:'',
			type:'text',
			show:true,
			validator: [Designer_Control_Attr_Number_Validator],
			checkout: [Designer_Control_Attr_Number_Checkout]
		},
		//一行显示搜索数
		oneRowSearchNum:{
			text: Designer_Lang.relation_event_oneRowSearchNum,
			value:'',
			type:'text',
			show:true,
			validator : [Designer_Control_Attr_Int_Validator],
			checkout: [Designer_Control_Attr_Int_Checkout]
		},
		appendSearchResult : {
			text: Designer_Lang.relation_event_appendSearchResult,
			value: "true",
			type: 'checkbox',
			checked: false,
			show: true
		},
		outputParams : {
			text : "<span style='cursor:pointer' >"+Designer_Lang.relation_event_params_out1+"&nbsp;&nbsp;<img src='relation_choose/style/icons/addshow.gif' onclick='_addChooseOutputParams(this,0);' title='"+Designer_Lang.relation_event_add_show_out+"' style='margin-top:5px;'></img><br/>" +
					Designer_Lang.relation_event_params_out2+"&nbsp;&nbsp;<img src='relation_choose/style/icons/addhidden.gif' onclick='_addChooseOutputParams(this,1);' title='"+Designer_Lang.relation_event_add_hidden_out+"'></img></span>",//传出参数
			value : '',
			type : 'self',
			draw : _Designer_Control_Attr_RelationChoose_Self_Output_Draw,
			show : true,
			getVal : relationOutParams_getVal,
			compareChange: relationOutParams_compareChange,
			translator: relationOutParams_translator,
			displayText: Designer_Lang.relation_event_params_out1 + Designer_Lang.relation_event_params_out2
		},
		inputParams : {
			//传入参数
			text : Designer_Lang.relation_select_inputParams,//'传入参数',
			value : '',
			checkout: Designer_RelationChoose_Control_InputParams_Required_Checkout,
			type : 'self',
			draw : _Designer_Control_Attr_RelationChoose_Self_Draw,
			show : true,
			getVal : relationCommonOutputParams_getVal,
			compareChange:relationCommonInputParams_compareChange,
			translator: relationCommonInputParams_translator
		},
		inputParamsRequired : {
			text : '传入参数必填字段',
			value : '',
			type : 'text',
			show : false
		},
		template : {
			text : '模板',
			value : '',
			varInfo : '',
			isLock:false,
			type : 'text',
			show : false
		},
		source_oldValue : {
			text:"",
			show:false,
			skipLogChange:true
		},
	},
	info : {
		name : Designer_Lang.relation_choose_name
		//preview : "mutiTab.png"
	},
	resizeMode : 'onlyWidth'
};
Designer_Config.buttons.tool.push("relationChoose");
Designer_Menus.tool.menu['relationChoose'] = Designer_Config.operations['relationChoose'];

function _Designer_Control_RelationChoose_OnInitialize(){
	this.onInitializeDict();
}

//粘贴后初始化默认值，有需要改动的值可以在这里更新
function _Designer_Control_RelationChoose_initDefaultValueAfterPaste(values){
	values.outputParams = "";
	values.outHiddenValueName = "";
	values.outHiddenValue = "";
	values.outShowValueName = "";
	values.outShowValue = "";
	return values;
}

function _TrigetChooseControl(a,name){
	var c=Designer.instance.getObj(false);
	RelatoinFormFieldChoose(document.getElementById(name),document.getElementById(name),function(rtn){
		if(rtn&&rtn.data&&rtn.data[0].id){
			document.getElementById(name).value=rtn.data[0].id;
		}
	},c);
}
function Designer_RelationChoose_Control_Source_Required_Checkout(msg, name, attr, value, values, control){
	if(values.source){
		return true;
	}
	msg.push(values.label,","+Designer_Lang.relation_select_sourceNotNull);
	return false;
}
function Designer_RelationChoose_Control_InputParams_Required_Checkout(msg, name, attr, value, values, control){
	var val=value?value:"{}";
	var inputParamsMapping = JSON.parse(val.replace(/quot;/g,"\""));
	if(values.inputParamsRequired){
		var inputRequireds=values.inputParamsRequired.substring(0,values.inputParamsRequired.length-1).split(",");
		for(var i=0;i<inputRequireds.length;i++){
			var hasIn=false;
			for(var field in inputParamsMapping){
				if(field==inputRequireds[i]){
					if(!inputParamsMapping[field].fieldIdForm||!inputParamsMapping[field].fieldNameForm){
						msg.push(control.options.values.label+control.options.values.id,Designer_Lang.relation_event_inputParamsNotNull);
						return false;
					}
					hasIn=true;
				}
			}
			if(!hasIn){
				msg.push(control.options.values.label+control.options.values.id,Designer_Lang.relation_event_inputParamsNotNull);
				return false; 
			}
		}
	}
	return true;
}
function Designer_RelationChoose_Control_OutputParams_Required_Checkout(msg, name, attr, value, values, control){
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
				 msg.push(values.label,","+Designer_Lang.relation_select_outputParamsNotNull);
				 return false;
			 }
		}
		else{
			msg.push(values.label,","+Designer_Lang.relation_select_outputParamsNotNull);
			return false;
		}

		
	}
	return true;
	
	
	
}

function _Designer_Control_RelationChoose_OnDraw(parentNode, childNode) {
	var values=this.options.values;
	if (this.options.values.id == null){
		this.options.values.id = "fd_" + Designer.generateID();
		
	}
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	//domElement.label=_Get_Designer_Control_Label(this.options.values, this);
	domElement.className="xform_relation_choose";
	domElement.style.display="inline";
	//domElement.style.height='30px';
	var values = this.options.values;
	var inputDom = document.createElement('input');
	inputDom.className="inputsgl";
	inputDom.label = _Get_Designer_Control_Label(this.options.values, this);
	if (this.options.values.width ) {
		if( this.options.values.width.toString().indexOf('%') > -1){
			inputDom.style.width = this.options.values.width;
		}
		else{
			inputDom.style.width = this.options.values.width+"px";
		}
	}
	else{
		values.width = "120";
		inputDom.style.width=values.width+"px";
	}
	
	domElement.appendChild(inputDom);
	if(values.required == 'true') {
		$(domElement).append('<span class=txtstrong>*</span>');
	}
	$(domElement).append('&nbsp;');
	//domElement.style.display="inline";
	
	// selectDom.style.width = values.width;
	inputDom.id = this.options.values.id;
	
	if (values.required == "true") {
		$(inputDom).attr("required", "true");
		$(inputDom).attr("_required", "true");
	} else {
		$(inputDom).attr("required", "false");
		$(inputDom).attr("_required", "false");
	}
	if (values.width) {
		$(inputDom).attr("width", values.width);
	}
	if (values.outputParams) {
		$(inputDom).attr("outputParams", values.outputParams);
	}
	if (values.inputParams) {
		$(inputDom).attr("inputParams",values.inputParams);
	}
	if (values.outerSearchParams) {
		$(inputDom).attr("outerSearchParams",values.outerSearchParams);
	}
	if (values.source) {
		$(inputDom).attr("source", values.source);
	}
	if (values.funKey) {
		$(inputDom).attr("funKey" , values.funKey);
	}
	if (values.bindDom) {
		$(inputDom).attr("bindDom",values.bindDom);
	}
	if (values.bindEvent) {
		$(inputDom).attr("bindEvent",values.bindEvent);
	}
	if(values.listRule){
		$(inputDom).attr("listRule", values.listRule);
	}
	if(values.fillType){
		$(inputDom).attr("fillType", values.fillType);
	}
	if(values.fillTypeOne){
		$(inputDom).attr("fillTypeOne", values.fillTypeOne);
	}
	//是否摘要
	if(values.summary == "true"){
		$(inputDom).attr("summary", "true");
	}else{
		$(inputDom).attr("summary", "false");
	}
	if (!values.dialogWidth){
		var dialogWidth = window.screen.width*600/1366;
		values.dialogWidth = parseInt(dialogWidth);
	}
	$(inputDom).attr("dialogWidth", values.dialogWidth);
	if (!values.dialogHeight){
		var dialogHeight = window.screen.height*400/768;
		values.dialogHeight = parseInt(dialogHeight);
		
	}
	$(inputDom).attr("dialogHeight", values.dialogHeight);
	if (!values.oneRowSearchNum){
		values.oneRowSearchNum = 2;
	}
	$(inputDom).attr("oneRowSearchNum", values.oneRowSearchNum);
	
	$(inputDom).attr("appendSearchResult", values.appendSearchResult);
	
	var a = document.createElement("a");
	a.innerText=Designer_Lang.relation_choose_button;
	//label.appendChild(document.createTextNode("JSP"));
	domElement.appendChild(a);
	//$(domElement).html("<span style='font-style:italic;font-weight:bold;background-color:#FFC125;'>EVENT</span>");
	//$(domElement).html("<img src='relation_event/style/icons/event.png' width='16px' height='16px'></img>");
	

	//alert(domElement.innerHTML);
}
function _Designer_Control_Attr_RelationChoose_Self_FunName_Draw(){
	_Designer_Control_Choose_Attr_Source_Change(document.getElementsByName("source")[0],true);
}
function _Designer_Control_Choose_Attr_Source_Change(obj,choose) {
	var control = Designer.instance.attrPanel.panel.control;
	// 选择“--请选择---” 后无需处理业务
	if (!obj.value) {
		_Clear_Relation_Choose_Attrs(control);
		control.options.values.source_oldValue='';
		control.options.values.source='';
		alert(Designer_Lang.relation_choose_sourceNotNul);
		return;
	}

	var source = relationSource.GetSourceByUUID(obj.value);
	
	control.options.values.funName=source.sourceName;
	// 如果扩展点中 paramsURL为空，数据源即业务
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
				$("#relation_choose_source").val(control.options.values.source_oldValue);
				
				return ;
			}
			_Clear_Relation_Choose_Attrs(control);
			
			

			control.options.values.source_oldValue=obj.value;
			// 设置业务名称
			document.getElementsByName("funName")[0].value = rtnVal._keyName;
			control.options.values.funName = rtnVal._keyName;
			control.options.values.funKey = rtnVal._key;
			control.options.values.source=obj.value;
			
			_Designer_Control_Choose_LoadEventInputParamsTemplate(control, obj.value, rtnVal._key );
	    	
	    }).show();
	}
	//直接选择业务 
	else{
		
		if(choose){
			alert('该业务没有更多可选项');
			return;
		}
		_Clear_Relation_Choose_Attrs(control);
		

		control.options.values.source_oldValue=obj.value;
		// 设置业务名称
		document.getElementsByName("funName")[0].value = rtnVal._keyName;
		control.options.values.funName = rtnVal._keyName;
		control.options.values.funKey = rtnVal._key;
		control.options.values.source=obj.value;
		
		_Designer_Control_Choose_LoadEventInputParamsTemplate(control, obj.value, rtnVal._key );
	}
	if(source){
		//设置待选列表分页
		_Designer_Control_Choose_Attr_updataListRule(source);	
	}
	
}

//更新分页的选项
function _Designer_Control_Choose_Attr_updataListRule(source){
	var notSupportPage = source.isSupportPage && source.isSupportPage == 'false' ? true:false;
	var $select = $("select[name='listRule']"); 
	// 01 11 为分页	
	$select.find("option").each(function(){
		var option = $(this);
		if(/[\d][1]/g.test(option.val())){
			if(notSupportPage && $select.val() == option.val()){
				if(option.attr("selected") && option.attr("selected") == "selected"){
					$select.val("00");
				}	
			}
			option.attr("disabled",notSupportPage);
		}
	});
}

function _Designer_Control_Choose_LoadEventInputParamsTemplate(control, source, key) {

	 loadTemplate(key,source,function(data){
			var insStr = "";
			if(data&&data.ins){
				control.options.values.inputParamsRequired="";
				for ( var i = 0; i < data.ins.length; i++) {
					var field = data.ins[i];
					insStr += _CreateEventInputParams(field) + "<br/>";
					if("1"==field._required){
						control.options.values.inputParamsRequired+=(field.uuId?field.uuId:field.fieldId)+",";
					}
				}
			}
			if(data&&data.searchs){
				control.options.values.outerSearchParams=JSON.stringify(data.outerSearchs).replace(/"/g,"quot;");
			}
			$("#relation_choose_inputs").html(insStr);
		
	 },true);
}

function _Clear_Relation_Choose_Attrs(control){

	$('#relation_choose_inputs').html(Designer_Lang.relation_select_chooseSource);
	$('#relation_choose_outputs').html(Designer_Lang.relation_event_noOutputParams);
	// 清空输出
	control.options.values.outputParams = "";
	
	control.options.values.inputParams = "";

	// 清空业务
	document.getElementsByName("funName")[0].value = "";
	control.options.values.funName = "";
	control.options.values.funKey = "";
	
	document.getElementsByName("outShowValue")[0].value = "";
	document.getElementsByName("outShowValueName")[0].value = "";
	control.options.values.outShowValue = "";
	control.options.values.outShowValueName = "";
	
	document.getElementsByName("outHiddenValue")[0].value = "";
	document.getElementsByName("outHiddenValueName")[0].value = "";
	control.options.values.outHiddenValue = "";
	control.options.values.outHiddenValueName = "";
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
function LoadChooseInputParamsTemplate(control, source, key) {

	 loadTemplate(key,source,function(data){
			var insStr = "";
			if(data&&data.ins){
				control.options.values.inputParamsRequired="";
				for ( var i = 0; i < data.ins.length; i++) {
					var field = data.ins[i];
					insStr += _CreateChooseInputParams(field) + "<br/>";
					if("1"==field._required){
						control.options.values.inputParamsRequired+=(field.uuId?field.uuId:field.fieldId)+",";
					}
				}
			}
			if(data&&data.searchs){
				control.options.values.outerSearchParams=JSON.stringify(data.outerSearchs).replace(/"/g,"quot;");
			}
			$("#relation_event_inputs").html(insStr);
		
	 },true);
}

//构建每个参数项
function _CreateChooseInputParams(field) {
	
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
			+ field._required + "' />");
	// html.push("<input type='hidden' name='insParams' value='"+field.uuId+"'
	// />");
	html.push("<input type='hidden' id='" + field.uuId + "_formId' value='"
			+ field.fieldIdForm + "' />");
	html.push("<input id='" + field.uuId + "_formName' value='"
			+ field.fieldNameForm + "' onchange='Relation_Fiexed_InputParam_Change(this,\""
			+ field.uuId + "\");' style='width:80%;'/>");
	html.push(" <a href='javascript:void(0)' onclick='RelatoinChooseOpenExpressionEditor(this,\""
			+ field.uuId + "\");'>"+Designer_Lang.relation_select_choose+"</a>");
	return html.join("");
}
function _addChooseOutputParams(obj,hiddenFlag){
	
	if(!Designer.instance.attrPanel.panel.control.options.values.funKey){
		alert(Designer_Lang.relation_choose_sourceNotNul);
		return ;
	}
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
	if($("#relation_choose_outputs").html()==Designer_Lang.relation_event_noOutputParams){
		$("#relation_choose_outputs").html("");
		isFirst=true;
	}
	$("#relation_choose_outputs").append(_CreateChooseOutParams(field,isFirst,hiddenFlag));                
//}
}
function _delChooseOutputParams(obj){
	//显示按钮面板
	Relation_ShowButton();
	var control = Designer.instance.attrPanel.panel.control;
	
	var fid=$(obj).parent().attr("id");
	
	control.options.values.outputParams = control.options.values.outputParams ? control.options.values.outputParams
			: "{}";
	var outputParamsJSON = JSON
			.parse(control.options.values.outputParams.replace(/quot;/g,"\""));
	if(outputParamsJSON[fid]){
		//删除掉 这个对象的映射值 
		delete outputParamsJSON[fid];
	}
	control.options.values.outputParams=JSON.stringify(
			outputParamsJSON).replace(/"/g,"quot;");
	$(obj).parent().remove();
	//清空到最后一条的时候需要 加上初始值
	if(!$("#relation_choose_outputs").html()){
		$("#relation_choose_outputs").html(Designer_Lang.relation_event_noOutputParams);
	}
}
function _CreateChooseOutParams(field,isFirst,hiddenFlag) {
	var html = [];

	var fid=field.fid;
	html.push("<span id='"+fid+"'>");
	//第一个元素不需要分隔符
	if(!isFirst){
		html.push("<hr />");
	}
	
	
	html.push(" <img src='relation_choose/style/icons/delete.gif' onclick='_delChooseOutputParams(this);' style='cursor:pointer;vertical-align:middle;'></img>");
	
	html.push("<input type='hidden' id='" + fid + "_hiddenFlag' value='" + hiddenFlag + "' />");
	html.push("<input type='hidden' id='" + fid + "_fieldIdForm' value='" + field.fieldIdForm + "' />");
	html.push("<input id='" + fid + "_fieldNameForm' value='"+ field.fieldNameForm + "' readOnly=true  style='width:58px;vertical-align:middle;color:"+(hiddenFlag=='1'?'gray':'')+"'/>");
	
	
	html.push(" <img src='relation_choose/style/icons/edit.gif' onclick='RelatoinChooseOpenOutExpressEditor(this,\""+fid+"\");' style='cursor:pointer;vertical-align:middle;'></img>");
	
	// html.push("<input type='hidden' name='insParams' value='"+field.uuId+"'
	// />");
	var uuid = field.uuId ? field.uuId : field.fieldId;
	html.push("<input type='hidden' id='" + fid
			+ "_fieldId' value='" + uuid + "' />");
	html.push("<input id='" + fid + "_fieldName' value='"
			+ field.fieldName + "' readOnly=true  style='width:58px;vertical-align:middle;color:"+(hiddenFlag=='1'?'gray':'')+"'/>");
	html.push(" <img src='relation_choose/style/icons/edit.gif' onclick='Open_Relation_Choose_Tree_Dialog(this,\""
			+ fid + "\");' style='cursor:pointer;vertical-align:middle;'></img>");
	
	if(field._required){
		//html.push("<span class='txtstrong'>*</span>");
	}
	html.push("</span>");
	return html.join("");
}
function _Designer_Control_Attr_RelationChoose_Self_Draw(name, attr, value,
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
			if(data&&data.ins){
				values.inputParamsRequired="";
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
					insStr += _CreateChooseInputParams(field) + "<br/>";
				}
			}
			html += "<div id='relation_choose_inputs'> " + insStr + "</div>";
			if(data&&data.searchs){
				values.outerSearchParams=JSON.stringify(data.outerSearchs).replace(/"/g,"quot;");
			}
		 });
		// _LoadTemplate("","","",mapping);
	} else {
		html += "<div id='relation_choose_inputs'>"+Designer_Lang.relation_select_chooseSource+"</div>";
	}
	if(values.source){
		//设置待选列表分页
		setTimeout(function(){
			_Designer_Control_Choose_Attr_updataListRule(relationSource.GetSourceByUUID(values.source));
		},0);	
	}
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}
function _Designer_Control_Attr_RelationChoose_Self_outShowValue_Draw(name, attr,
		value, form, attrs, values, control) {
	var html=[];
	var textValue=values.outShowValueName?values.outShowValueName:"";
	var idValue=values.outShowValue?values.outShowValue:"";
	html.push("<input name='outShowValueName' value='"+textValue+"' style='width:80%;' readonly></input>" +
			"<input type='hidden' name='outShowValue' value='"+idValue+"'></input>" +
					"<a href='javascript:void(0)' onclick=\"Open_Relation_Choose_Tree_Dialog_Inner('outShowValue','outShowValueName',false,true);\">"+Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_RelationChoose_Self_outHiddenValue_Draw(name, attr,
		value, form, attrs, values, control) {
	var html=[];
	var textValue=values.outHiddenValueName?values.outHiddenValueName:"";
	var idValue=values.outHiddenValue?values.outHiddenValue:"";
	html.push("<input name='outHiddenValueName' value='"+textValue+"' style='width:80%;' readonly></input>" +
			"<input type='hidden' name='outHiddenValue' value='"+idValue+"'></input>" +
					"<a href='javascript:void(0)' onclick=\"Open_Relation_Choose_Tree_Dialog_Inner('outHiddenValue','outHiddenValueName',true,false);\">"+Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_RelationChoose_Self_Output_Draw(name, attr,
		value, form, attrs, values, control) {
	if (!control.options.values.funKey) {
		var html = "<div id='relation_choose_outputs'>"+Designer_Lang.relation_event_noOutputParams+"</div>";
		html+="<div>";
		html+="<span title='"+Designer_Lang.relation_event_outputParamTip+"' style='color:red; text-overflow:-o-ellipsis-lastline;overflow:hidden;text-overflow:ellipsis;display:-webkit-box;-webkit-line-clamp:2;-webkit-box-orient:vertical;'>"+Designer_Lang.relation_event_outputParamTip+"</span>"
		html+="</div>";
		return Designer_AttrPanel.wrapTitle(name, attr, value, html);
	}
	var html = "<div id='relation_choose_outputs'>";

	var val = value ? value : "{}";
	var outputParamsMapping = JSON.parse(val.replace(/quot;/g,"\""));
	var i=0;
	for(var fid in outputParamsMapping){
		if(fid&&fid.indexOf("fixed_")==0){
			continue;
		}
		var param = outputParamsMapping[fid];
		param.fid=fid;
		html += _CreateChooseOutParams(param,i==0,param.hiddenFlag?param.hiddenFlag:"0");
		i++;
	}
	html+="</div>";
	html+="<div>";
	html+="<span title='"+Designer_Lang.relation_event_outputParamTip+"' style='color:red; text-overflow:-o-ellipsis-lastline;overflow:hidden;text-overflow:ellipsis;display:-webkit-box;-webkit-line-clamp:2;-webkit-box-orient:vertical;'>"+Designer_Lang.relation_event_outputParamTip+"</span>"
	html+="</div>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}
function _Designer_Control_RelationChoose_DrawXML() {

	var values = this.options.values;

	// 如果一个控件对应多个值，需要补充这两个属性，用于流程文档提交业务处理
	var customElementProperties = {};
	customElementProperties.isMultiVal = "true";
	customElementProperties.controlId = values.id;
	if (values.source) {
		customElementProperties.source = values.source;
	}
	if (values.funKey) {
		customElementProperties.funKey = values.funKey;
	}
	buf=[];
	
	buf.push( '<extendSimpleProperty ');
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	buf.push('type="', values.dataType ? values.dataType : 'String', '" ');
	// 控件类型
	buf.push('businessType="', this.type, '" ');
	
	if (values.formula != '' && values.formula != null) {
		buf.push('formula="true" ');
		buf.push('defaultValue="', values.formula, '" ');
		if (values.reCalculate == 'true') {
			buf.push('recalculateOnSave="true" ');
		}
	} else if (values.defaultValue != '' && values.defaultValue != null) {
		buf.push('defaultValue="', values.defaultValue, '" ');
	}
	
	buf.push('customElementProperties="', Designer.HtmlEscape(JSON.stringify(customElementProperties)), '" ');
	
	// 字段是否需要加密
	if (values.encrypt == 'true') {
		buf.push('encrypt="true" ');
		buf.push('encryptionMethod="AES" ');
	}
	buf.push('/>\r\n');

	buf.push(_Designer_Control_RelationChoose_DrawXML_HiddenPropertyTemp(values.id+"_dataFdId",values.label + '(不需映射)',this.type,customElementProperties,values,'false'));
	buf.push(_Designer_Control_RelationChoose_DrawXML_HiddenPropertyTemp(values.id+"_dataModelName",values.label + '(不需映射)',this.type,customElementProperties,values,'false'));
	buf.push(_Designer_Control_RelationChoose_DrawXML_HiddenPropertyTemp(values.id+"_dataSourceId",values.label + '(不需映射)',this.type,customElementProperties,values,'false'));
	buf.push(_Designer_Control_RelationChoose_DrawXML_HiddenPropertyTemp(values.id+"_text",values.label + Designer_Lang.controlDisplayValueMessage,this.type,customElementProperties,values));
	buf.push(_Designer_Control_RelationChoose_DrawXML_HiddenPropertyTemp(values.id+"_selectedDatas",values.label + '(不需映射)',this.type,customElementProperties,values,'false'));
	return buf.join('');
}

function _Designer_Control_RelationChoose_DrawXML_HiddenPropertyTemp(name,label,businessType,customElementProperties,values,isStore){
	var buf = [];
	customElementProperties.isShow = 'false';
	buf.push( '<extendSimpleProperty ');
	buf.push('name="', name, '" ');
	buf.push('label="',label , '" ');
	buf.push('type="', values.dataType ? values.dataType : 'String', '" ');
	buf.push('businessType="', businessType, '" ');
	if(isStore){
		buf.push('store="',isStore,'" ');
	}
	buf.push('customElementProperties="', Designer.HtmlEscape(JSON.stringify(customElementProperties)), '" ');
	// 字段是否需要加密
	if (values.encrypt == 'true') {
		buf.push('encrypt="true" ');
		buf.push('encryptionMethod="AES" ');
	}
	if(name.indexOf('_text') > 0 && values.summary == 'true'){
		//摘要汇总
		buf.push('summary="true" ');
	}
	buf.push('/>\r\n');
	return buf.join('');
}

function Open_Relation_Choose_Tree_Dialog_Inner(id,name,hiddenFlag,isText){
	var control = Designer.instance.attrPanel.panel.control;
    var values=control.options.values;
    if (!control.options.values.label ) {
    	if(document.getElementsByName("label")&&document.getElementsByName("label")[0].value){
    		values.label=document.getElementsByName("label")[0].value;
    	}
    	else
    	{
    		alert(Designer_Lang.relation_choose_labelNotNul);
    		return;
    	}	
		
	}
	if (!control.options.values.funKey) {
		alert(Designer_Lang.relation_select_chooseSource);
		return;
	}	
	
	
	loadTemplate(values.funKey,values.source,function(data,varInfo){
		
		Open_Relation_Fields_Tree(document.getElementsByName(id)[0],
				document.getElementsByName(name)[0],
				varInfo,function(rtn){
			if(!rtn){
				return;
			}
			Relation_ShowButton();
			var control = Designer.instance.attrPanel.panel.control;
			values[id]=rtn.data[0].id;
			values[name].fieldName=rtn.data[0].name;
			
			var fid="fixed_"+id;
			
			control.options.values.outputParams = control.options.values.outputParams ? control.options.values.outputParams
					: "{}";
			var outputParamsJSON = JSON
					.parse(control.options.values.outputParams.replace(/quot;/g,"\""));
			if(!outputParamsJSON[fid]){
				outputParamsJSON[fid]={};
			}
			outputParamsJSON[fid].fieldId=rtn.data[0].id;
			outputParamsJSON[fid].fieldName=rtn.data[0].name;
			if(isText){
				outputParamsJSON[fid].fieldIdForm=values.id+"_text";
			}
			else{
				outputParamsJSON[fid].fieldIdForm=values.id;
			}
			//如果当前控件是在明细表里面，则需要在前面加上明细表ID
			var detailsTable = _Designer_Control_RelationChoose_getDetailstableParent(control);
			if(detailsTable && detailsTable.options.values.id){
				outputParamsJSON[fid].fieldIdForm = detailsTable.options.values.id + '.' + outputParamsJSON[fid].fieldIdForm;
			}
			outputParamsJSON[fid].fieldNameForm=values.label;
			outputParamsJSON[fid].hiddenFlag=hiddenFlag;
			
			if(data&&data.outs){
				for ( var i = 0; i < data.outs.length; i++) {
					var field = data.outs[i];
					var uuid = field.uuId ? field.uuId : field.fieldId;
					if(uuid==rtn.data[0].id){
						outputParamsJSON[fid].canSearch=field.canSearch;
						break;
					}
				}
			}
			
			control.options.values.outputParams = JSON.stringify(
					outputParamsJSON).replace(/"/g,"quot;");
		});
	 });
}

// 获取当前明细表，如果控件不在明细表内，返回null
function _Designer_Control_RelationChoose_getDetailstableParent(control){
    var closestDetailsTable = Designer.getClosestDetailsTable(control);
    if (closestDetailsTable && $(control.options.domElement).closest("tr").attr("type") === "templateRow") {
        return closestDetailsTable;
    } else {
        return;
    }
}

function Open_Relation_Choose_Tree_Dialog(a, fid) {

	var control = Designer.instance.attrPanel.panel.control;
    var values=control.options.values;
	if (!control.options.values.funKey) {
		alert(Designer_Lang.relation_select_chooseSource);
		return;
	}	
	loadTemplate(values.funKey,values.source,function(data,varInfo){
		
		Open_Relation_Fields_Tree(document.getElementById(fid + "_fieldId"),
				document.getElementById(fid + "_fieldName"),
				varInfo,function(rtn){
			if(!rtn){
				return;
			}
			Relation_ShowButton();
			var control = Designer.instance.attrPanel.panel.control;
			control.options.values.outputParams = control.options.values.outputParams ? control.options.values.outputParams
					: "{}";
			var outputParamsJSON = JSON
					.parse(control.options.values.outputParams.replace(/quot;/g,"\""));
			if(!outputParamsJSON[fid]){
				outputParamsJSON[fid]={};
			}
			outputParamsJSON[fid].fieldId=rtn.data[0].id;
			outputParamsJSON[fid].fieldName=rtn.data[0].name;
			
			if(data&&data.outs){
				for ( var i = 0; i < data.outs.length; i++) {
					var field = data.outs[i];
					var uuid = field.uuId ? field.uuId : field.fieldId;
					if(uuid==rtn.data[0].id){
						outputParamsJSON[fid].canSearch=field.canSearch;
						break;
					}
				}
			}
			control.options.values.outputParams = JSON.stringify(
					outputParamsJSON).replace(/"/g,"quot;");
		});
	 });
}
//设定输出参数
function RelatoinChooseOpenOutExpressEditor(obj, uuid, action){
	var idField, nameField;
	idField = document.getElementById(uuid + "_fieldIdForm");
	nameField = document.getElementById(uuid + "_fieldNameForm");
	var hiddenFlag = document.getElementById(uuid + "_hiddenFlag");
	
	RelatoinFormFieldChoose(idField,nameField,function(rtn){
			if(!rtn){
				return;
			}
			Relation_ShowButton();
			
			var control = Designer.instance.attrPanel.panel.control;
	
			control.options.values.outputParams = control.options.values.outputParams ? control.options.values.outputParams
					: "{}";
			var outputParamsJSON = JSON
					.parse(control.options.values.outputParams.replace(/quot;/g,"\""));
	
			if(rtn&&rtn.data&&rtn.data[0].id){
				if(!outputParamsJSON[uuid]){
					outputParamsJSON[uuid]={};
				}
				outputParamsJSON[uuid].fieldIdForm=rtn.data[0].id;
				outputParamsJSON[uuid].fieldNameForm=rtn.data[0].name;
				outputParamsJSON[uuid].hiddenFlag=hiddenFlag.value;
				
				control.options.values.outputParams = JSON.stringify(
						outputParamsJSON).replace(/"/g,"quot;");
			}
	},Designer.instance.getObj(false));
}
function RelatoinChooseOpenExpressionEditor(obj, uuid, action) {
	var idField, nameField;
	idField = document.getElementById(uuid + "_formId");
	nameField = document.getElementById(uuid + "_formName");
	var _requiredValue="";
	if(document.getElementById(uuid + "_required"))
		_requiredValue=document.getElementById(uuid + "_required").value;
	
	RelatoinFormFieldChoose(idField,nameField, function(rtn){
		
		Relation_ShowButton();
		
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