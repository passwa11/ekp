
// 获取所有数据源
// var relationSource = new relationSource();
Designer_Config.operations['relationEvent'] = {
	lab : "5",
	imgIndex : 37,
	title :Designer_Lang.relation_event_name,
	run : function(designer) {
		designer.toolBar.selectButton('relationEvent');
	},
	type : 'cmd',
	order: 2,
	line_splits_font:true,
	shortcut : '',
	isAdvanced: true,
	select : true,
	cursorImg : 'style/cursor/releationEvent.cur'
};
Designer_Config.controls.relationEvent = {
	type : "relationEvent",
	storeType : 'none',
	inherit : 'base',
	container : false,
	onDraw : _Designer_Control_RelationEvent_OnDraw,
	drawMobile : _Designer_Control_RelationEvent_DrawMobile,
	drawXML : _Designer_Control_RelationEvent_DrawXML,
	implementDetailsTable : true,
	// 在新建流程文档的时候，是否显示
	hideInMainModel : true,
	//onAttrSuccess : _Designer_Control_Attr_RelationEvent_SuccessAttr,
	onAttrLoad : function(){
//		var w252="352px";
//		var w240="340px";
//		$(".panel_title").css("width",w252);
//		
//		$(".panel_main").css("width",w252);
//		$(".panel_main").css("background","");
//		
//		$(".panel_table").css("width",w240);
//		
//		$(".panel_bottom").css("width",w252);
		
	},
	attrs : {
		source : {
			//展示样式
			text: Designer_Lang.relation_select_source,
			value : '',
			type : 'select',
			opts:relationSource.GetOptionsArray(),
			onchange:'_Designer_Control_Event_Attr_Source_Change(this)',
			show: true,
			getVal:relationSource_getVal
		},
		
		funName : {
			text : Designer_Lang.relation_select_busiName,//'业务名称',
			value : '',
			type : 'comText',
			readOnly : true,
			required: true,
			operate:"_Designer_Control_Attr_RelationEvent_Self_FunName_Draw",
			show : true,
			validator: [Designer_Control_Attr_Required_Validator],
			checkout: [Designer_Control_Attr_Required_Checkout]
		},
		funKey : {
			text : '函数key',
			value : '',
			type : 'text',
			show : false,
			skipLogChange:true
		},
		bindDom : {
			text : Designer_Lang.relation_event_trigger_dom,
			value : '',
			required: true,
			width:"75%",
			type : 'comText',
			operate:'_TrigetEventControl',
			show : true,
			validator: [Designer_Control_Attr_Required_Validator],
			checkout: [Designer_Control_Attr_Required_Checkout]
		},
		bindEvent : {
			text : Designer_Lang.relation_event_trigger_event,
			value : '',
			type : 'select',
			opts:[{"text":Designer_Lang.relation_event_onmouseClick,'value':'click'},
			      {"text":Designer_Lang.relation_event_onchange,'value':'change'},
			      {"text":Designer_Lang.relation_event_onextendReturn,'value':'relation_event_setvalue'}],
			translator:opts_common_translator,
			show : true
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
		outerSearchParams:{
			text : '搜索参数',
			value : '',
			type : 'text',
			show : false,
			skipLogChange:true
		},
		outputParams : {
			text : "<span style='cursor:pointer' >"+Designer_Lang.relation_event_params_out1+"&nbsp;&nbsp;<img src='relation_event/style/icons/addshow.gif' onclick='_addOutputParams(this,0);' title='"+Designer_Lang.relation_event_add_show_out+"' style='margin-top:5px;'></img>" +
					Designer_Lang.relation_event_params_out2+"&nbsp;&nbsp;<img src='relation_event/style/icons/addhidden.gif' onclick='_addOutputParams(this,1);' title='"+Designer_Lang.relation_event_add_hidden_out+"'></img></span>",//传出参数
			value : '',
			type : 'self',
			draw : _Designer_Control_Attr_RelationEvent_Self_Output_Draw,
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
			checkout: Designer_RelationEvent_Control_InputParams_Required_Checkout,
			type : 'self',
			draw : _Designer_Control_Attr_RelationEvent_Self_Draw,
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
		name : Designer_Lang.relation_event_name
		//preview : "mutiTab.png"
	},
	resizeMode : 'no'
};
Designer_Config.buttons.tool.push("relationEvent");
Designer_Menus.tool.menu['relationEvent'] = Designer_Config.operations['relationEvent'];

function _Designer_Attr_AddAll_RelationEvent_Controls(controls, obj,expectId) {
	for (var i = 0, l = controls.length; i < l; i ++) {
		
		if("relationEvent"==controls[i].type&&expectId != controls[i].options.values.id){
			var temp={};
			temp.name=controls[i].options.values.id;
			temp.label='填充控件('+controls[i].options.values.id+')';
			temp.type='String';
			obj.push(temp);
		}
		_Designer_Attr_AddAll_RelationEvent_Controls(controls[i].children, obj,expectId);
	}
}
function _TrigetEventControl(a,name){
	var c;
	if(window.parent && typeof window.parent.XForm_SupportFieldForRelationEvent != "undefined" && window.parent.XForm_SupportFieldForRelationEvent == "true"){
		c = Designer.instance.getObj(false,null,null,true);
	}else{
		c = Designer.instance.getObj(false);
	}
	_Designer_Attr_AddAll_RelationEvent_Controls(Designer.instance.builder.controls,c,Designer.instance.attrPanel.panel.control.options.values.id);
	//属性列表
	var fieldLayout = Designer_Control_Relation_Rule_getCanApplyFieldLayout();
	c = fieldLayout.concat(c);
	RelatoinFormFieldChoose(document.getElementById(name),document.getElementById(name),function(rtn){
		if(rtn&&rtn.data&&rtn.data[0].id){
			document.getElementById(name).value=rtn.data[0].id;
			RelationEventClick(rtn.data[0].id);
		}
	},c);
}
//根据属性id获取控件类型
function RelationEventGetInputControlTypeById(fieldId) {
	if (!fieldId) {
		return;
	}
	var fields = Designer.instance.getObj(true);
	var controlType;
	for (var i = 0; i < fields.length; i++) {
		if (fields[i].name == fieldId) {
			controlType = fields[i].controlType;
			break;
		}
	}
	return controlType;
}
//业务控件click事件处理
function RelationEventClick(fieldId){
	var controlType = RelationEventGetInputControlTypeById(fieldId);
	var bindEvent =document.getElementsByName("bindEvent");
	var flag = false;
	for (var i = 0; i < bindEvent[0].options.length; i++) {
		if(bindEvent[0].options[i].value == 'click') {//如果是业务关联控件则移除click事件，否则要显示click事件
			flag = true;
			if(controlType =='placeholder') {
				//移除鼠标单击事件
				bindEvent[0].options[i].remove();
				break;
			}
		}
	}
	//除业务关联控件外如果不包含click事件,则添加鼠标单击事件
	if(!flag && controlType !='placeholder'){
		$(bindEvent[0]).prepend(new Option(Designer_Lang.relation_event_onmouseClick, 'click'));
		bindEvent[0].options[0].selected = true;
	}
}

function Designer_RelationEvent_Control_Source_Required_Checkout(msg, name, attr, value, values, control){
	if(values.source){
		return true;
	}
	msg.push(values.label,","+Designer_Lang.relation_select_sourceNotNull);
	return false;
}
function Designer_RelationEvent_Control_InputParams_Required_Checkout(msg, name, attr, value, values, control){
	var val=value?value:"{}";
	var inputParamsMapping = JSON.parse(val.replace(/quot;/g,"\""));
	if(values.inputParamsRequired){
		var inputRequireds=values.inputParamsRequired.substring(0,values.inputParamsRequired.length-1).split(",");
		for(var i=0;i<inputRequireds.length;i++){
			var hasIn=false;
			for(var field in inputParamsMapping){
				if(field==inputRequireds[i]){
					if(!inputParamsMapping[field].fieldIdForm||!inputParamsMapping[field].fieldNameForm){
						msg.push(Designer_Lang.relation_event_event+control.options.values.id,Designer_Lang.relation_event_inputParamsNotNull);
						return false;
					}
					hasIn=true;
				}
			}
			if(!hasIn){
				msg.push(Designer_Lang.relation_event_event+control.options.values.id,Designer_Lang.relation_event_inputParamsNotNull);
				return false; 
			}
		}
	}
	return true;
}
function Designer_RelationEvent_Control_OutputParams_Required_Checkout(msg, name, attr, value, values, control){
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

// 属性面板成功关闭时设置值
function _Designer_Control_Attr_RelationEvent_SuccessAttr() {

	//this.options.values.inputParams=JSON.stringify(_GetInsParamsMapping()).replace(/"/g,"'");
	// this.options.values.inputParams=JSON.stringify(_GetInsParamsMapping()).replace(/"/g,"'");
	// $(this.options.domElement).find("select").attr("inputParams",this.options.values.inputParams);
}

function _Designer_Control_RelationEvent_OnDraw(parentNode, childNode) {
	if (this.options.values.id == null){
		this.options.values.id = "fd_" + Designer.generateID();
		
	}
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	//domElement.label=_Get_Designer_Control_Label(this.options.values, this);
	domElement.style.width='25px';
	//domElement.style.height='30px';
	var values = this.options.values;
	var inputDom = document.createElement('input');
	inputDom.type='hidden';
	domElement.appendChild(inputDom);
	//domElement.style.display="inline";
	
	// selectDom.style.width = values.width;
	inputDom.id = this.options.values.id;
	
	if (values.outputParams) {
		$(inputDom).attr("outputParams", values.outputParams);
	}
	if (values.inputParams) {
		values.inputParams = RelationEventSetInputParamType(values.inputParams);
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
	var label = document.createElement("label");
	//label.appendChild(document.createTextNode("JSP"));
	label.style.background = "url(style/img/event.png) no-repeat";
	label.style.margin = '0px 0px 0px 0px';
	label.style.display = 'inline-block';
	label.style.width='24px';
	label.style.height='24px';
	domElement.appendChild(label);
	//$(domElement).html("<span style='font-style:italic;font-weight:bold;background-color:#FFC125;'>EVENT</span>");
	//$(domElement).html("<img src='relation_event/style/icons/event.png' width='16px' height='16px'></img>");
	

	//alert(domElement.innerHTML);
}
//做个兼容历史，提交时给输入参数内置字段类型
function RelationEventSetInputParamType(inputParams){
	var inputParamsJSONArr = JSON.parse(inputParams.replace(/quot;/g,"\""));
	if(inputParamsJSONArr){
		for(var key in inputParamsJSONArr){
			var inputParamsJSON = inputParamsJSONArr[key];
			var fieldId = inputParamsJSON.fieldIdForm;
			var fieldTypeForm = RelationEventGetInputParamTypeById(fieldId) || "";
			inputParamsJSON['fieldTypeForm'] = fieldTypeForm;
			inputParamsJSONArr[key] = inputParamsJSON;
		}
	}
	return JSON.stringify(inputParamsJSONArr).replace(/"/g,"quot;");
}
function _Designer_Control_Attr_RelationEvent_Self_FunName_Draw(){
	_Designer_Control_Event_Attr_Source_Change(document.getElementsByName("source")[0],true);
}
function _Designer_Control_Event_Attr_Source_Change(obj,choose) {
	var control = Designer.instance.attrPanel.panel.control;
	// 选择“--请选择---” 后无需处理业务
	if (!obj.value) {
		_Clear_Relation_Event_Attrs(control);
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
				$("#relation_event_source").val(control.options.values.source_oldValue);
				
				return ;
			}
			_Clear_Relation_Event_Attrs(control);
			
			

			control.options.values.source_oldValue=obj.value;
			// 设置业务名称
			document.getElementsByName("funName")[0].value = rtnVal._keyName;
			control.options.values.funName = rtnVal._keyName;
			control.options.values.funKey = rtnVal._key;
			control.options.values.source=obj.value;
			
			LoadEventInputParamsTemplate(control, obj.value, rtnVal._key );
	    	
	    }).show();
	}
	//直接选择业务 
	else{
		
		if(choose){
			alert('该业务没有更多可选项');
			return;
		}
		_Clear_Relation_Event_Attrs(control);
		

		control.options.values.source_oldValue=obj.value;
		// 设置业务名称
		document.getElementsByName("funName")[0].value = rtnVal._keyName;
		control.options.values.funName = rtnVal._keyName;
		control.options.values.funKey = rtnVal._key;
		control.options.values.source=obj.value;
		
		LoadEventInputParamsTemplate(control, obj.value, rtnVal._key );
	}
	if(source){
		//设置待选列表分页
		_Designer_Control_Event_Attr_updataListRule(source);	
	}
}

//更新分页的选项
function _Designer_Control_Event_Attr_updataListRule(source){
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

function _Clear_Relation_Event_Attrs(control){

	$('#relation_event_inputs').html(Designer_Lang.relation_select_chooseSource);
	$('#relation_event_outputs').html(Designer_Lang.relation_event_noOutputParams);
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
function LoadEventInputParamsTemplate(control, source, key) {

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
			$("#relation_event_inputs").html(insStr);
		
	 },true);
}

//构建每个参数项
function _CreateEventInputParams(field) {
	
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
	if (field.showSelect){
		html.push(" <a href='javascript:void(0)' onclick='RelatoinEventOpenExpressionEditor(this,\""
				+ field.uuId + "\");'>"+Designer_Lang.relation_select_choose+"</a>");
	}
	return html.join("");
}
function _addOutputParams(obj,hiddenFlag){
	
	if(!Designer.instance.attrPanel.panel.control.options.values.funKey){
		alert('请先选择数据源');
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
	if($("#relation_event_outputs").html()==Designer_Lang.relation_event_noOutputParams){
		$("#relation_event_outputs").html("");
		isFirst=true;
	}
	$("#relation_event_outputs").append(_CreateEventOutParams(field,isFirst,hiddenFlag));                
//}
}
function _delEventOutputParams(obj){
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
	if(!$("#relation_event_outputs").html()){
		$("#relation_event_outputs").html(Designer_Lang.relation_event_noOutputParams);
	}
}
function _CreateEventOutParams(field,isFirst,hiddenFlag) {
	var html = [];

	var fid=field.fid;
	html.push("<span id='"+fid+"'>");
	//第一个元素不需要分隔符
	if(!isFirst){
		html.push("<hr />");
	}
	
	
	html.push(" <img src='relation_event/style/icons/delete.gif' onclick='_delEventOutputParams(this);' style='cursor:pointer;vertical-align:middle;'></img>");
	
	html.push("<input type='hidden' id='" + fid + "_hiddenFlag' value='" + hiddenFlag + "' />");
	html.push("<input type='hidden' id='" + fid + "_fieldIdForm' value='" + field.fieldIdForm + "' />");
	html.push("<input id='" + fid + "_fieldNameForm' value='"+ field.fieldNameForm + "' readOnly=true  style='width:58px;vertical-align:middle;color:"+(hiddenFlag=='1'?'gray':'')+"'/>");
	
	
	html.push(" <img src='relation_event/style/icons/edit.gif' onclick='RelatoinEventOpenOutExpressEditor(this,\""+fid+"\");' style='cursor:pointer;vertical-align:middle;'></img>");
	
	// html.push("<input type='hidden' name='insParams' value='"+field.uuId+"'
	// />");
	var uuid = field.uuId ? field.uuId : field.fieldId;
	html.push("<input type='hidden' id='" + fid
			+ "_fieldId' value='" + uuid + "' />");
	html.push("<input id='" + fid + "_fieldName' value='"
			+ field.fieldName + "' readOnly=true  style='width:58px;vertical-align:middle;color:"+(hiddenFlag=='1'?'gray':'')+"'/>");
	html.push(" <img src='relation_event/style/icons/edit.gif' onclick='Open_Relation_Event_Tree_Dialog(this,\""
			+ fid + "\");' style='cursor:pointer;vertical-align:middle;'></img>");
	
	if(field._required){
		//html.push("<span class='txtstrong'>*</span>");
	}
	html.push("</span>");
	return html.join("");
}
function _Designer_Control_Attr_RelationEvent_Self_Draw(name, attr, value,
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
					insStr += _CreateEventInputParams(field) + "<br/>";
				}
			}
			html += "<div id='relation_event_inputs'> " + insStr + "</div>";
			if(data&&data.searchs){
				values.outerSearchParams=JSON.stringify(data.outerSearchs).replace(/"/g,"quot;");
			}
		 });
		// _LoadTemplate("","","",mapping);
	} else {
		html += "<div id='relation_event_inputs'>"+Designer_Lang.relation_select_chooseSource+"</div>";
	}
	if(values.source){
		//设置待选列表分页
		setTimeout(function(){
			_Designer_Control_Event_Attr_updataListRule(relationSource.GetSourceByUUID(values.source));
		},0);	
	}
	
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}
function _Designer_Control_Attr_RelationEvent_Self_Output_Draw(name, attr,
		value, form, attrs, values, control) {
	if (!control.options.values.funKey) {
		var html = "<div id='relation_event_outputs'>"+Designer_Lang.relation_event_noOutputParams+"</div>";
		html+="<div>";
		html+="<span title='"+Designer_Lang.relation_event_outputParamTip+"' style='color:red; text-overflow:-o-ellipsis-lastline;overflow:hidden;text-overflow:ellipsis;display:-webkit-box;-webkit-line-clamp:2;-webkit-box-orient:vertical;'>"+Designer_Lang.relation_event_outputParamTip+"</span>"
		html+="</div>";
		return Designer_AttrPanel.wrapTitle(name, attr, value, html);
	}
	var html = "<div id='relation_event_outputs'>";

	var val = value ? value : "{}";
	var outputParamsMapping = JSON.parse(val.replace(/quot;/g,"\""));
	var i=0;
	for(var fid in outputParamsMapping){
		var param = outputParamsMapping[fid];
		param.fid=fid;
		html += _CreateEventOutParams(param,i==0,param.hiddenFlag?param.hiddenFlag:"0");
		i++;
	}
	html+="</div>";
	html+="<div>";
	html+="<span title='"+Designer_Lang.relation_event_outputParamTip+"' style='color:red; text-overflow:-o-ellipsis-lastline;overflow:hidden;text-overflow:ellipsis;display:-webkit-box;-webkit-line-clamp:2;-webkit-box-orient:vertical;'>"+Designer_Lang.relation_event_outputParamTip+"</span>"
	html+="</div>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}
function _Designer_Control_RelationEvent_DrawXML() {

}

function Open_Relation_Event_Tree_Dialog(a, fid) {

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
		 /******
		 Relation_Formula_Dialog(
					document.getElementById(uuid + "_fieldId"),
					document.getElementById(uuid + "_fieldName"),
					varInfo,
					"String",
					function(rtn){
						if(!rtn){
							return;
						}
						Designer.instance.attrPanel.panel._changed = true;
						Designer.instance.attrPanel.panel.showBottom();
						
						var control = Designer.instance.attrPanel.panel.control;
						control.options.values.outputParams = control.options.values.outputParams ? control.options.values.outputParams
								: "{}";
						var outputParamsJSON = JSON
								.parse(control.options.values.outputParams.replace(/quot;/g,"\""));
						if(!outputParamsJSON[uuid]){
							outputParamsJSON[uuid]={};
						}
						outputParamsJSON[uuid].fieldId=rtn.data[0].id;
						outputParamsJSON[uuid].fieldName=rtn.data[0].name;
				
						control.options.values.outputParams = JSON.stringify(
								outputParamsJSON).replace(/"/g,"quot;");
						
					});
			**************/
	 });
}
//设定输出参数
function RelatoinEventOpenOutExpressEditor(obj, uuid, action){
	var idField, nameField;
	idField = document.getElementById(uuid + "_fieldIdForm");
	nameField = document.getElementById(uuid + "_fieldNameForm");
	var hiddenFlag = document.getElementById(uuid + "_hiddenFlag");
	var c;
	if(window.parent && typeof window.parent.XForm_SupportFieldForRelationEvent != "undefined" && window.parent.XForm_SupportFieldForRelationEvent == "true"){
		c = Designer.instance.getObj(false,null,null,true);
	}else{
		c = Designer.instance.getObj(false);
	}
	
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
	},c);
	/****************
	var dialog = new KMSSDialog();
	dialog.BindingField(idField, nameField);
	dialog.Parameters = {
		varInfo : Designer.instance.getObj(true)
	};

	dialog
			.SetAfterShow( function(rtn) {
				Designer.instance.attrPanel.panel._changed = true;
				Designer.instance.attrPanel.panel.showBottom();
				
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
					control.options.values.outputParams = JSON.stringify(
							outputParamsJSON).replace(/"/g,"quot;");
				}
			});

	dialog.URL = Com_Parameter.ContextPath
			+ "sys/xform/designer/relation/relation_formfields_tree.jsp?t="+(new Date());
	dialog.Show(380, 480);
	*****************/
}
function RelationEventGetInputParamTypeById(fieldId){
	if(!fieldId){
		return;
	}
	var fields = Designer.instance.getObj(true);
	var fieldType;
	for(var i=0; i<fields.length; i++){
		if(fields[i].name == fieldId){
			fieldType = fields[i].type;
			break;
		}
	}
	return fieldType;
}
function RelatoinEventOpenExpressionEditor(obj, uuid, action) {
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
			t.fieldTypeForm = RelationEventGetInputParamTypeById(rtn.data[0].id) || "";
			inputParamsJSON[uuid] = t;
			control.options.values.inputParams = JSON.stringify(
					inputParamsJSON).replace(/"/g,"quot;");
		}
		else{
			//清空选择时，需要取消映射
			delete inputParamsJSON[uuid];
			
			/**
			//清空选择时，需要取消映射
			var temp={};
			for(var param in inputParamsJSON){
				if(param==uuid){
					continue;
				}
				temp[param]=inputParamsJSON[param];
				
			}
			**/
			control.options.values.inputParams = JSON.stringify(
					inputParamsJSON).replace(/"/g,"quot;");
		}
		 
		 
		 
	 });
	/**************
	var dialog = new KMSSDialog();
	dialog.BindingField(idField, nameField);
	dialog.Parameters = {
		varInfo : Designer.instance.getObj(true)
	};

	// dialog.SetAfterShow(function(){Designer_AttrPanel.showButtons(obj);});

	dialog
			.SetAfterShow( function(rtn) {
				
				
				
				Designer.instance.attrPanel.panel._changed = true;
				Designer.instance.attrPanel.panel.showBottom();
				
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
					var temp={};
					for(var param in inputParamsJSON){
						if(param==uuid){
							continue;
						}
						temp[param]=inputParamsJSON[param];
						
					}
					control.options.values.inputParams = JSON.stringify(
							temp).replace(/"/g,"quot;");
				}
			});

	dialog.URL = Com_Parameter.ContextPath
			+ "sys/xform/designer/relation/relation_formfields_tree.jsp?t="+(new Date());
	dialog.Show(380, 480);
*******************/
};