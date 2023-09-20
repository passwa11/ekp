/**********************************************************
功能：校验器控件
使用：
	
作者：李文昌
创建时间：2018-02-08
**********************************************************/
Com_IncludeFile("validatorControl.css",Com_Parameter.ContextPath + "sys/xform/designer/style/","css",true);
Designer_Config.controls['validatorControl'] = {
		dragRedraw : false,
		type : "validatorControl",
		storeType : 'none',
		inherit    : 'base',
		onDrag        : _Designer_Control_Validator_DoDrag,         //开始拖拽
		onDragStop    : _Designer_Control_Validator_DoDragStop, 	//拖拽结束
		onDraw : _Designer_Control_Validator_OnDraw,
		drawMobile : _Designer_Control_Validator_DrawMobile,
		onDrawEnd : null,
		fillMobileValue : Designer_Control_Validator_FillMobileValue,
		// 在新建流程文档的时候，是否显示
		hideInMainModel : false,
		implementDetailsTable : true,
		info : {
			name: Designer_Lang.controlValidatorControl_info_name
		},
		resizeMode : 'no',
		ondrawDom : null,			//ondraw绘制的dom元素
		blockDom: null,				//遮罩层
		showDom: null,				//主面板
		contentDiv : null,			//表达式预览dom元素
		expression_mode : "",		//标识校验器控件是否在明细表内
		scrollTop:null,
		getFdValues:getFdValues_get,
		translator: validatorControl_translator,
		text:Designer_Lang.controlValidatorRule
};
function validatorControl_translator(change) {
	if (!change) {
		return "";
	}
	var before = change.before;
	var after = change.after;
      before=before.replace(/expressionName/g,Designer_Lang.validatorExpression);
      before=before.replace(/message/g,Designer_Lang.failMessage);
      before=before.replace(/expressionShow/g,Designer_Lang.validatorRule);
      before=before.replace(/notPassTip/g,Designer_Lang.notPassTip);
      before=before.replace(/passTip/g,Designer_Lang.passTip);
      after=after.replace(/expressionName/g,Designer_Lang.validatorExpression);
      after=after.replace(/message/g,Designer_Lang.failMessage);
      after=after.replace(/expressionShow/g,Designer_Lang.validatorRule);
      after=after.replace(/notPassTip/g,Designer_Lang.notPassTip);
      after=after.replace(/passTip/g,Designer_Lang.passTip);
	var html =[];
	html.push("<span> "+Designer_Lang.from);
	var blank="&emsp;&emsp;";
	html.push(before+blank+Designer_Lang.to+blank+after+"</span>");
	return html.join("");
}
function getFdValues_get(name,attr,value,controlValue){
	var idValueqq=this.options.values.id;
	var param =this.options.values.param;
	var result="[]";
	if(param!=undefined && param.length>3){
		
      result=param;
	}
	
	
    var controlValue = {};
	var text="";
	controlValue.id=idValueqq;
	controlValue.controlValidatorRule=result;
   
    return controlValue;
  
       
}

function get(str,result){
	var start=str.indexOf("{");
	var end=str.indexOf("}");
	if(start==-1){
		return "";
	}else{
		if(result.length>0){
			result=result+",";
		}
	}
	
	var temp=str.substring(start,end+1);
	temp=temp.replace("{", "{\"");
	temp=temp.replace("}", "\"}");
	temp=temp.replace(/:/g, "\":\"");
	temp=temp.replace(/,/g, "\",\"");
	
	var tempObj=JSON.parse(temp);
	delete tempObj.expressionId;
	if(tempObj.expressionShow=="0"){
		tempObj.expressionShow="notPassTip";
	}else{
		tempObj.expressionShow="passTip";
	}
	
	result=result+JSON.stringify(tempObj);
	var newStr=str.substring(end+1);
	var newstart=newStr.indexOf("{");
	if(newstart!=-1){
		var a=get(newStr,result);
		return a;
	}
	return result;
	
}

Designer_Config.operations['validatorControl'] = {
		lab : "2",
		imgIndex : 64,
		title : Designer_Lang.controlValidatorControl_title,
		run : function (designer) {
			designer.toolBar.selectButton('validatorControl');
		},
		type : 'cmd',
		order: 80,
		select: true,
		cursorImg: 'style/cursor/validatorControl.cur'
	};

Designer_Config.buttons.form.push("validatorControl");
Designer_Menus.form.menu['validatorControl'] = Designer_Config.operations['validatorControl'];


/**
 * 拖拽开始事件,明细表内的控件拖拽到明细表外时,要清空明细表内的表达式
 * @returns
 */
var _Designer_Validator_flag ;
function _Designer_Control_Validator_DoDrag(event){
	var _event = event || window.event, srcElement = _event.srcElement || _event.target;
	srcElement = $(srcElement).closest("div[fd_type='validatorControl']")[0];
	_Designer_Validator_flag = Designer_Control_Validator_isIntemplateRow(srcElement);
	_Designer_Control_Base_DoDrag.call(this,event);
}

/**
 * 拖拽结束事件
 * @returns
 */
function _Designer_Control_Validator_DoDragStop(event){
	
	var _event = event || window.event, srcElement = _event.srcElement || _event.target;
	_Designer_Control_Base_DoDragStop.call(this,event);
	var isInDetailTable = Designer_Control_Validator_isIntemplateRow(this.options.domElement);
	if (_Designer_Validator_flag && !isInDetailTable){//从明细表内拖到明细表外
		$(this.options.domElement).attr("param","");
	}
}

/**
 * 绘制校验器图标
 * @param parentNode
 * @param childNode
 * @returns
 */
function _Designer_Control_Validator_OnDraw(parentNode, childNode) {
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	domElement.id = this.options.values.id;
	$(domElement).attr("param",this.options.values.param || "[]");
	$(domElement).attr("varIds",this.options.values.varIds || "");
	domElement.style.width='25px';
	domElement.style.display="inline";
	domElement.style.margin = "0 7px";
	var span = document.createElement("span");
	//背景样式
	span.style.background = "url(style/img/validatorControl.png) no-repeat";
	span.style.margin = '0px 0px 0px 0px';
	span.style.width='24px';
	span.style.height='24px';
	span.style.display="inline-block";
	span.style.position = 'relative';
	span.style.verticalAlign = "middle";
	//绑定鼠标双击事件
	span.setAttribute("ondblclick","Designer_Control_Validator_ShowEditFrame(event, this);");
	
	//鼠标悬停预览表达式
	span.setAttribute("onmouseover","_Designer_Control_Validator_Onmouseover(event,this);");
	span.setAttribute("onmouseout","_Designer_Control_Validator_Onmouseout(event,this);");
	
	domElement.appendChild(span);
	domElement.title = Designer_Lang.controlValidatorInfoName;
	
	//设置是否在明细表内属性
	Designer_Control_Validator_isIntemplateRow(domElement);
	
	Designer_Config.controls.validatorControl.ondrawDom = domElement;
}

//处理旧数据
function _Designer_Control_Validator_processOldData(valueDom,dom){
	var expressionText,failMessageTip,values;
	values = valueDom.value ? valueDom.value : "";
	expressionText = dom.value ? dom.value : "";
	failMessageTip = $(dom).attr("failmessagetip") ? $(dom).attr("failmessagetip") : "";
	
	//解义
	expressionText = _Designer_Control_Validator_htmlUnEscape(expressionText);
	failMessageTip = _Designer_Control_Validator_htmlUnEscape(failMessageTip);
	values =  _Designer_Control_Validator_htmlUnEscape(values);
	
	var textArr = expressionText.split(";") || [];
	var failMessageTipArr = failMessageTip ? failMessageTip.split(";") : [];
	var valueArr = values ? failMessageTip.split(";") : [];
	
	var param = [];
	for (var i = 0; i < valueArr.length; i++){
		var obj = {};
		obj.expressionId = valueArr[i].split("|")[0] || "";
		obj.expressionName = textArr[i];
		obj.message = failMessageTipArr[i].split("|")[1] || "";
		obj.expressionShow = 0;
		param.push(obj);
	}
	return param;
}

/**
 * 校验器控件图标双击事件,每次重新加载变量,因此多个校验器控件使用同一个面板
 * @param event
 * @param dom
 * @returns
 */
function Designer_Control_Validator_ShowEditFrame(event, dom) {
	//防止view模式下，点击控件出现异常
	if(!Designer.instance.shortcuts){
		return;
	}
	
	//阻止冒泡
	event = event || window.event;
	event.cancelBubble = true;
	if (event.stopPropagation) {event.stopPropagation();}
	
	var $div,fdId;
	$div = $(dom).closest("div[fd_type='validatorControl']");//获取控件id
	fdId = $div.attr("id");

	dom = dom || this;	
	Designer_Config.controls.validatorControl.ondrawDom = $div[0];
	Designer_Config.controls.validatorControl.expression_mode = $div.attr("expression_mode");
	
	var param = [];
	if($div.attr("param")){ //优先使用json格式
		param = $div.attr("param");
		if (param){
			param = _Designer_Control_Validator_htmlUnEscape(param);
			param = JSON.parse(param);
			for(var p=0;p<param.length;p++){
				if(param[p].message){
					param[p].message = param[p].message.replace(/\"/g,"&quot;");
				}
					
			}
			
		}
	}else{	
		//文本隐藏域和提示语隐藏域
		var valueDom = $(dom).find("input[name='validatorRule_id']")[0];
		var textDom = $(dom).find("input[name='validatorRule_name']")[0];
		if (valueDom && textDom){
			param = _Designer_Control_Validator_processOldData(valueDom,textDom);
		}
	}
	//遮罩层
	if (Designer_Config.controls.validatorControl.blockDom == null)  {
		Designer_Config.controls.validatorControl.blockDom = Get_Designer_Control_Validator_BlockDom();
	}else{
		//非首次打开的时候，重新设置遮罩的宽高
		with(Designer_Config.controls.validatorControl.blockDom.style){
			if(window.innerWidth){
				width = window.innerWidth + 'px';
				height = window.innerHeight + 'px';
			}else{
				var scrollbarWidth = document.body.offsetWidth - document.body.clientWidth;
				width = document.body.scrollWidth + scrollbarWidth + 'px';
				height = document.body.scrollHeight + 'px';
			}
		}
	}
	if (Designer_Config.controls.validatorControl.showDom == null) {
		//重新绘画面板
		Designer_Config.controls.validatorControl.showDom = Get_Designer_Control_Validator_ShowDom(fdId);
	
		Designer.instance.shortcuts.add('tab', function() {
			var obj = document.getElementById("Designer_Control_Validator_Value");
			if (document.selection) {
				var sel = document.selection.createRange();
				sel.text = '\t';
			} else if (typeof obj.selectionStart === 'number' && typeof obj.selectionEnd ==='number') {
				var startPos = obj.selectionStart, endPos = obj.selectionEnd, cursorPos = startPos, tmpStr = obj.value;   
				obj.value = tmpStr.substring(0, startPos) + '\t' + tmpStr.substring(endPos, tmpStr.length);
				cursorPos += '\t'.length;
				obj.selectionStart = obj.selectionEnd = cursorPos;
			} else {
				obj.value += '\t';
			}
		}, {'target':Designer_Config.controls.validatorControl.showDom});
	}
	Designer_Config.controls.validatorControl.scrollTop = Designer.getDocumentAttr("scrollTop");
	document.documentElement.scrollTop = document.body.scrollTop = 0;
	document.body.style.overflow = 'hidden';
	$("#ruleList").find("tr[name!='titleRow'][name!='descRow']").remove();
	
	for (var i = 0; i < param.length; i++){
		var info = param[i];
		_Designer_Control_Validator_addExpression(info,Designer_Config.controls.validatorControl.showDom);
	}
	
	//每次打开面板重新绘制变量下拉列表
	var drawDom = Designer_Config.controls.validatorControl.ondrawDom;
	var isRow = $(drawDom).attr("expression_mode") === "isRow" ? true : false;
	var tables = Designer_Control_Validator_getTable(isRow,drawDom);
	var tableSelectConfig = {
			onchange:"Designer_Control_Validator_selectChange(this.value)",
			opts : tables
	}
	
	//控件下拉列表配置
	var id = tables[0] ? tables[0].name : "";
	var control = Designer_control_validator_getChildrenById(id);
	var pleaseSelect = {label:Designer_Lang.controlValidatorPleaseSelect,name:""};
	control.unshift(pleaseSelect);
	var controlSelectConfig = {
			onchange : "Designer_Control_Validator_varSelectChange(this)",
			opts : control
	}
	$("#varis").empty();
	$("#varis").append(Designer_Control_Validator_SelectDraw("tableSelect",tableSelectConfig));
	$("#varis").append(Designer_Control_Validator_SelectDraw("controlSelect",controlSelectConfig));
	
	document.body.style.overflow = 'hidden';

	Designer_Config.controls.validatorControl.blockDom.style.display = '';
	Designer_Config.controls.validatorControl.showDom.style.display = '';
	Set_Designer_Control_JSP_ShowDomNeedPostion(Designer_Config.controls.validatorControl.showDom);
}

/**
 * 遮罩层
 * @returns
 */
function Get_Designer_Control_Validator_BlockDom() {
	var dom = document.createElement('div');
	with(document.body.appendChild(dom).style) {
		position = 'absolute'; filter = 'alpha(opacity=80)'; opacity = '0.8';
		border = '1px'; background = '#EAEFF3'; display = 'none';
		width = '100%';
		height = '100%';
		top = '0'; left = '0';
		zIndex = '999';
	}
	return dom;
}

function Set_Designer_Control_Validator_ShowDomNeedPostion(domElement) {
	var p = Get_Designer_Control_Validator_ShowDomNeedPostion(domElement);
	domElement.style.top = p.y + 'px';
	domElement.style.left = p.x + 'px';
}

function Get_Designer_Control_Validator_ShowDomNeedPostion(domElement){
	return ({
		x : document.body.offsetWidth / 2 + document.body.scrollLeft - domElement.offsetWidth / 2,
		y : window.innerHeight ? 
				(window.innerHeight / 2 + document.documentElement.scrollTop - domElement.offsetHeight / 2)
			    :(document.body.offsetHeight / 2 + document.body.scrollTop - domElement.offsetHeight / 2)
	});
}

/**
 *校验器主面板dom元素
 * @returns
 */
function Get_Designer_Control_Validator_ShowDom(fdId) {
	var domElement = document.createElement('div');
	document.body.appendChild(domElement);
	domElement.className= 'panel_jsp_panel';
	domElement.id = "Designer_Control_Validator_Div_" + fdId ;
	domElement.style.width = '80%';
	var textareaH = Designer.getDocumentAttr("clientHeight")*166/514;
	var html = [];
	//最大化和关闭按钮
	var panelButtonHtml = '<div style="float:right;">';
	///右上角最大化图标
	panelButtonHtml += '<a id="Designer_Control_Validator_MaxImizeIcon" href="javascript:void(0)" onclick="_Designer_Control_Validator_MaxImize();" title="'+ Designer_Lang.validatorControlMaximize + '" style="display:none;width:12px;height:12px;background: url(style/img/jsp_control_max.png) no-repeat -3px ;margin-top: 6px;"></a>';
	//右上角关闭图标
	panelButtonHtml += '<a id="Designer_Control_Validator_CloseEditFrameIcon" href="javascript:void(0)" onclick="Designer_Control_Validator_CloseEditFrame();" title="'+ Designer_Lang.validatorControlClose + '" style="display:inline-block;width:12px;height:12px;background: url(style/img/jsp_control_close.png) no-repeat 0px ;margin-top: 6px;"></a>';
	panelButtonHtml += '</div>';
		
	//顶部工具栏
	html.push('<div class="resize_panel">', 
			'<div class="resize_panel_top"><table class="resize_panel_top_panel"><tr>',
			'<td class="resize_panel_top_left"></td>', 
			'<td class="resize_panel_top_center">','<span style="float:left;">'+ Designer_Lang.controlValidatorInfoName +'</span>',panelButtonHtml, '</td>',
			'<td class="resize_panel_top_right"></td></tr></table></div>');
	
	//主面板 Designer_Control_Validator_SelectDraw("tableSelect",tableSelectConfig) +  Designer_Control_Validator_SelectDraw('controlSelect',controlSelectConfig) 
	html.push('<div class="resize_panel_main" style="margin-left: 6px;border-right:1px solid #d2d2d2;width:99%;">',
			'<table class="resize_panel_main_panel">',
			//变量区域
			'<tr style="border-left: 1px solid #d2d2d2;"><td id="varis" colspan="3" style="background-color:#f5f5f5">' +  '</td></tr>', 
			
			'<tr><td class="resize_panel_main_center" width="65%">',
			
			'<textarea id="Designer_Control_Validator_Value" style="width:99%;height:' + textareaH +'px;word-break:break-all;" wrap="off"></textarea>',
			
			'</td>',
			
			//运算符区域
			Designer_control_validator_operationDom(),
			
			'<td class="resize_panel_main_right" style="background:no-repeat;width:0px;"></td></tr></table>',
			//规则列表区域
			Designer_control_validator_validatorRuleList(),
			
			'</div>');
	
	//底部
	html.push('<div class="resize_panel_bottom" style="margin-left:1px;">', 
			'<table class="resize_panel_bottom_panel">',
			'<tr>',
			'<td class="resize_panel_bottom_panel_left"></td>',
			'<td class="resize_panel_bottom_panel_center">',
			'<div  style="background-color: #f5f5f5;">',
			'<button class="panel_success" title="',Designer_Lang.controlValidatorSuccess,'" ',
			' onclick="Designer_Control_Validator_SuccessEditFrame();">&nbsp;</button>',
			'&nbsp;&nbsp;',
			'<button class="panel_cancel" title="',Designer_Lang.controlValidatorCancel,'" ',
			' onclick="Designer_Control_Validator_CloseEditFrame();">&nbsp;</button>',
			'&nbsp;&nbsp;',
			'<button class="panel_help" style="display:none;" title="',Designer_Lang.controlValidatorHelp,'" ',
			' onclick="Com_OpenWindow(\'jspcontrol_help.jsp\', \'_blank\');">&nbsp;</button>',
			'</div></td><td class="resize_panel_bottom_panel_right"></td>',
			'</tr></table>',
			'<table class="resize_panel_bottom_border">',
			'<tr><td class="resize_panel_bottom_border_left"></td>', 
			'<td class="resize_panel_bottom_border_center">&nbsp;</td>',
			'<td class="resize_panel_bottom_border_right"></td></tr></table>',
			'</div>');
	
	html.push("</div>");
	
	domElement.innerHTML = html.join('');
	with(domElement.style) {
		position = 'absolute'; zIndex = '1000'; display = 'none';
	}
	return domElement;
}

//标识面板开启还是关闭,0:开启 1:关闭
var _Designer_Control_Validator_Minrestore = 0;

/**
 * 关闭主面板
 * @returns
 */
function Designer_Control_Validator_CloseEditFrame() {
	Designer_Config.controls.validatorControl.blockDom.style.display = 'none';
	Designer_Config.controls.validatorControl.showDom.style.display = 'none';
	Designer_Config.controls.validatorControl.valueDom = null;
	_Designer_Control_Validator_Minrestore=1;
	document.body.style.overflow = 'auto';
	document.documentElement.scrollTop = document.body.scrollTop  = Designer_Config.controls.validatorControl.scrollTop;
}

/**
 * 确定按钮事件
 * @returns
 */
function Designer_Control_Validator_SuccessEditFrame() {
	var onDrawDom = Designer_Config.controls.validatorControl.ondrawDom;
	Designer_Control_Validator_RemoveOldAttribute(onDrawDom);
	
	var input = $("#ruleList").find("input[type='checkbox'][name!='selectAll']");
	var arr = [];
	var controlIdArr = [];
	var validateResult = true;
	input.each(function(index,ele){
			var obj = {};
			var $tr,expression,text,mess;
			$tr = $(ele).closest("tr");
			expression = $tr.find("input[type='hidden']").val();
			text = $tr.find("span[name='expressionText']").text();
			var parseResult = _Desiger_Control_Validator_parseExpression(text);
			validateResult = _Desiger_Control_ValidateExpression(parseResult);
			
			if (!validateResult){
				return false;
			}
			var showVal = $tr.find("select[name='show']").val();
			mess = $tr.find("input[name='failMessageTip']").val();
			if (parseResult) {
				var varis = parseResult.varis;
				for (var i = 0; i < varis.length; i++) {
					var varId = varis[i].varId;
					if ($.inArray(varId, controlIdArr) < 0) {
						controlIdArr.push(varId);
					}
				}
			}
			obj.expressionId = expression;
			obj.expressionName = text;
			obj.message = mess;
			obj.expressionShow = showVal;
			arr.push(obj);
	});
	if (!validateResult){
		return;
	}
	//json格式
	var param = _Designer_Control_Validator_HtmlEscape(JSON.stringify(arr));
	var varIds = controlIdArr.join(";");
	$(onDrawDom).attr("param",param);
	$(onDrawDom).attr("varIds",varIds);
	var fdValues = Designer.instance.control.options.values;
	fdValues.param = param;
	fdValues.varIds = varIds;
	
	//同步移动端控件值
	if (Designer.instance.mobileDesigner) {
		var __mobileDesigner = Designer.instance.mobileDesigner;
		var __designer = __mobileDesigner.getMobileDesigner();
		var __control = Designer.instance.builder.getControlByDomElement(onDrawDom);
		var pcControlId = __control.options.values.id;
		var mobileControls = __mobileDesigner.getMobileControlById(pcControlId);
		for (var i = 0; i < mobileControls.length; i++) {
			mobileControls[i].__pcOptions = __control.options;
			mobileControls[i].fillMobileValue();
		}
	}
	
	Designer_Control_Validator_CloseEditFrame();
	
	//增加对撤销功能的支持,--add by zhouzf
	if(typeof (DesignerUndoSupport)  != 'undefined'){
		DesignerUndoSupport.saveOperation();
	}
	_Designer_Control_Validator_Minrestore=1;

	document.body.style.overflow = 'auto';
	document.documentElement.scrollTop = document.body.scrollTop  = Designer_Config.controls.validatorControl.scrollTop;
	
}

//移除旧的属性
function Designer_Control_Validator_RemoveOldAttribute(dom){
	$(dom).removeAttr("expression_id");
	$(dom).removeAttr("expression_name");
	$(dom).removeAttr("failmessagetip");
	$(dom).removeAttr("textmessarr");
	$("input[name='validatorRule_id']",dom).remove();
	$("input[name='validatorRule_name']",dom).remove();
}

/**
 * 下拉框dom元素创建
 * @param name
 * @param config
 * @returns
 */
function Designer_Control_Validator_SelectDraw (name,config) {
	var html = "<select name='" + name +"'";
	
	//绑定值改变事件
	if (config.onchange) {
		html += " onchange=\"" + config.onchange + "\"";
	}
	
	html += " class='attr_td_select' style='width:10%;margin:5px;'>";
	
	if (config.opts) {
		for (var i = 0; i < config.opts.length; i ++) {
			var opt = config.opts[i];
			html += "<option value='" + opt.name;
			if (opt.style) {
				html += "' style='" + opt.style;
			}
			html += "'>" + opt.label + "</option>";
		}
	}
	html += "</select>";
	return html;
}

/**
 * 根据指定id获取子元素并创建option
 * @param id
 * @returns
 */
function Designer_Control_Validator_selectChange(id){
	var children = [],opts = [];
	children = Designer_control_validator_getChildrenById(id);
	//请选择
	opts.push('<option value="">' + Designer_Lang.controlValidatorPleaseSelect + '</option>');
	for (var i = 0; i < children.length; i++){
		opts.push('<option value="' + children[i].name + '">' + children[i].label + '</option>');
	}
	$("#varis").find("select[name='controlSelect']").empty();
	$("#varis").find("select[name='controlSelect']").append(opts.join(""));
}



//根据控件类型，判断后传回持久化对话框要用的数据类型
function _Designer_Validator_GetObj_GetType(control) {
	var values = control.options.values;
	if (control.type == 'rtf') {
		return 'RTF';
	}
	if (control.type == 'address' || control.type == 'new_address') {
		var orgType = 'com.landray.kmss.sys.organization.model.SysOrgElement';
		if(values._orgType=="ORG_TYPE_PERSON"){
			orgType = 'com.landray.kmss.sys.organization.model.SysOrgPerson';
		}
		return values.multiSelect == 'true' ? orgType + '[]' : orgType;
	}
	if (control.type == 'datetime') {
		if (values.businessType == 'timeDialog') {
			return 'Time';
		}
		return 'Date';
	}
	if(control.type=='attachment' || control.type=='uploadimg' || control.type=='docimg'){
		return 'Attachment';
	}
	return (values.dataType ? values.dataType : 'String');
}

/**
 * 创建运算符区域
 * @returns
 */
function Designer_control_validator_operationDom(){
	var operator = '';
	var textareaH = Designer.getDocumentAttr("clientHeight")*166/514;
	operator += '<td width="35%" id="operator">';
	operator += '<table width="" height=' + textareaH +  'px class="tb_normal" border="0">';
	
	operator += '<tr align="center">';
	operator += '<td width="20%" class="tdNumber"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'7\');" value="7"></td>';
	operator += '<td width="20%" class="tdNumber"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'8\');" value="8"></td>';
	operator += '<td width="20%" class="tdNumber"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'9\');" value="9"></td>';
	operator += '<td width="20%" class="tdOpr"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'+\', \' \');" value="+"></td>';
	operator +=	'<td width="20%" class="tdOpr"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'-\', \' \');" value="-"></td>';
	operator +=	'<td width="20%" class="tdOpr"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'<\', \' \');" value="<"></td>';
	operator += '</tr>';
	
	operator += '<tr align="center">';
	operator += '<td width="20%" class="tdNumber"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'4\');" value="4"></td>';
	operator += '<td width="20%" class="tdNumber"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'5\');" value="5"></td>';
	operator += '<td width="20%" class="tdNumber"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'6\');" value="6"></td>';
	operator += '<td width="20%" class="tdOpr"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'*\', \' \');" value="*"></td>';
	operator +=	'<td width="20%" class="tdOpr"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'/\', \' \');" value="/"></td>';
	operator +=	'<td width="20%" class="tdOpr"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'>\', \' \');" value=">"></td>';
	operator += '</tr>';
	
	operator += '<tr align="center">';
	operator += '<td width="20%" class="tdNumber"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'1\');" value="1"></td>';
	operator += '<td width="20%" class="tdNumber"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'2\');" value="2"></td>';
	operator += '<td width="20%" class="tdNumber"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'3\');" value="3"></td>';
	operator += '<td width="20%" class="tdOpr"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'(\', \' \');" value="("></td>';
	operator +=	'<td width="20%" class="tdOpr"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\')\', \' \');" value=")"></td>';
	operator +=	'<td width="20%" class="tdOpr"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'<=\', \' \');" value="<="></td>';
	operator += '</tr>';
	
	operator += '<tr align="center">';
	operator += '<td width="20%" class="tdNumber"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'0\');" value="0"></td>';
	operator += '<td width="20%" class="tdNumber"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'.\');" value="."></td>';
	operator += '<td width="20%" class="tdOpr"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'%\');" value="%"></td>';
	operator += '<td width="20%" class="tdOpr"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'==\', \' \');" value="=="></td>';
	operator += '<td width="20%" class="tdOpr"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'!=\', \' \');" value="!="></td>';
	operator +=	'<td width="20%" class="tdOpr"><input type="button" class="bigButton" onclick="Designer_control_validator_opFormula(\'>=\', \' \');" value=">="></td>';
	operator += '</tr>'; 
      
	operator +=  '</table>';
	operator += '</td>';
	
	return operator;
}

//运算符点击事件
function Designer_control_validator_opFormula(param, space){
	var area = document.getElementById("Designer_Control_Validator_Value");
	area.focus();
	if (space == null)
		space = '';
	Designer_control_validator_insertText(area, {value:space + param + space});
}

function Designer_control_validator_insertText(obj, node) {
	obj.focus();
	if (document.selection) {
		var sel = document.selection.createRange();
		sel.text = node.value;
	} else if (typeof obj.selectionStart === 'number' && typeof obj.selectionEnd === 'number') {
		var startPos = obj.selectionStart, endPos = obj.selectionEnd, cursorPos = startPos, tmpStr = obj.value;   
		obj.value = tmpStr.substring(0, startPos) + node.value + tmpStr.substring(endPos, tmpStr.length);
		cursorPos += node.value.length;
		obj.selectionStart = obj.selectionEnd = cursorPos;
	} else {
		obj.value += node.value;
	}
}

function Designer_Control_Validator_varSelectChange(opt){
	Designer_Control_Validator_setCaret();
	var area = document.getElementById("Designer_Control_Validator_Value");
	Designer_control_validator_insertText(area, {value:opt.value});
}

function Designer_Control_Validator_setCaret() {
	var txb = document.getElementById("Designer_Control_Validator_Value");
	var focusIndex = (txb.value || "").length;
	if (document.selection) {
		var r = txb.createTextRange();
		r.collapse(true);
		r.moveStart('character', focusIndex);
		r.select();
	} else {
		focusIndex = txb.value.length;
	}
}

/**
 * 显示帮助
 * @returns
 */
function Designer_control_showSkipRuleDetailDialog(){
	var height = screen.height * 0.6;
	var width = screen.width * 0.6;
	var url = "/sys/xform/designer/validatorControlTipRule/validator_control_tipRule.jsp";
	if (typeof(seajs) != 'undefined' && typeof( top['seajs'] ) != 'undefined' ) {
		seajs.use(['lui/dialog'], function(dialog) {
			dialog.iframe(url,Designer_Lang.controlValidator_tipRule,null,{width:width,height : height});
		});
	}else{
		Dialog_PopupWindow(url, width, height, null);
	}
}

/**
 * 创建校验器列表
 * @returns
 */
function Designer_control_validator_validatorRuleList(){
	var arr = new Array();
	var areaH = Designer.getDocumentAttr("clientHeight")*120/514;
	arr.push('<table class="tb_normal" width="100%">');
	arr.push('<tr name="titleRow">');
	
	arr.push('<td width="84%;" style="border:0px;">' + Designer_Lang.controlValidatorRule);
	arr.push('<span style="vertical-align:middle;display:inline-block;margin-bottom:-1px;">&nbsp;&nbsp;<label onclick="Designer_control_showSkipRuleDetailDialog();">'+
			'<img src="'+Com_Parameter.ContextPath+'sys/lbpmservice/resource/images/icon_help.png" style="margin-top:-2px;"></img>'+
			'</label>'+
			'</span></td>')
	
	//添加和删除按钮
	arr.push('<td width="16%;" style="border:0px;text-align:right;">');
	arr.push('<a href="javascript:;" class="com_btn_link" onclick="Com_EventPreventDefault();_Designer_Control_Validator_addExpression();">');
	arr.push('<img alt="" src="'+ Com_Parameter.ContextPath +'resource/style/default/icons/add.gif" title="' + Designer_Lang.controlValidator_addExpression + '">');
	arr.push('</a>&nbsp;&nbsp;');
	arr.push('<a href="javascript:;" class="com_btn_link" onclick="Com_EventPreventDefault();_Designer_Control_Validator_removeSelectedExpression();">');
	arr.push('<img alt="" src="'+ Com_Parameter.ContextPath +'resource/style/default/icons/delete.gif" title="' + Designer_Lang.controlValidator_deleteExpression + '">');
	arr.push('</a>');
	arr.push('</td>');
	
	arr.push('</tr>');
	arr.push("</table>");
	
	arr.push('<div style="height:auto;overflow-y:scroll;max-height:'+areaH+'px;">');
	
	arr.push('<table id="ruleList" width="100%" class="tb_normal" border="0">');
	
	arr.push('<tr name="descRow">');
	//checkcbox 按钮
	arr.push('<td width="15%" align="center">');
	arr.push('<input type="checkbox" name="selectAll" onclick="Designer_control_validator_selectAll(this);" />');
	arr.push('</td>')
	//字段表达式
	arr.push('<td width="30%" align="center">');
	arr.push(Designer_Lang.validatorExpression);
	arr.push('</td>');
	//校验规则
	arr.push('<td width="20%" align="center">');
	arr.push(Designer_Lang.validatorRule);
	arr.push('</td>');
	//提示语
	arr.push('<td width="30%" align="center">');
	arr.push(Designer_Lang.failMessage);
	arr.push('</td>');
	
	arr.push('<td width="10%">');
	arr.push('</td>');
	
	arr.push('</tr>');
	
	arr.push('</table>');
	
	arr.push('</div>');
	
	return arr.join("");
}

/**
 * 添加一条校验规则,点击添加按钮的时候执行校验，打开面板和预览都不校验,
 * @returns
 */
function _Designer_Control_Validator_addExpression(obj,dom){
	var scriptIn = "",tip = "",isNeedValidate=true;
	var showValue = 0;
	if (obj){//打开面板
		scriptIn = obj["expressionName"];
		tip = obj["message"] ? obj["message"] : "";
		isNeedValidate = false;
		showValue = obj["expressionShow"];
	}else{//添加按钮事件
		scriptIn = document.getElementById("Designer_Control_Validator_Value").value;
		var selectObj = $("select[name='controlSelect']",Designer_Config.controls.validatorControl.showDom)[0];//添加了表达式重置控件下拉列表
		if (selectObj.options[0]){
			selectObj.options[0].selected = true;
		}
	}
	  
	//前端校验表达式是否合法
	var result = "";
	var parseResult = _Desiger_Control_Validator_parseExpression(scriptIn);
	var scriptOut = parseResult["scriptOut"] || {};
	parseResult["scriptIn"] = scriptIn;
	var validateResult = true ;
	if (isNeedValidate){
		validateResult = _Desiger_Control_ValidateExpression(parseResult);
	}
	
	if (!validateResult){
		return ;
	}else{
		//如果已存在表达式，则提示请勿 重复添加
		result =_Designer_Control_Validator_HtmlEscape(scriptOut);
		var isRepeat = _Designer_Control_Validator_scriptOutRepeatValidate(result);
		if (isRepeat){
			alert(Designer_Lang.pleaseNotAddRepeatValidator);
			return;
		}
		
		var arr = [];
		
		arr.push('<tr>');
		
		arr.push('<td width="15%" align="center">');
		arr.push('<input type="checkbox" onclick="_Designer_Control_Validator_ChangeSelectAll(this);"/>');
		arr.push('</td>');
		
		arr.push('<td width="30%" align="center">');
		arr.push('<span name="expressionText">' + scriptIn + '</span>');
		arr.push('<input type="hidden" value="' + result + '"/>');
		arr.push('</td>');
		
		arr.push('<td width="20%" align="center">');
		arr.push('<select class="inputsgl" name="show">');
		if (showValue == 1){
			arr.push('<option value=0>'+Designer_Lang.notPassTip+'</option><option selected value=1>'+Designer_Lang.passTip+'</option>');
		}else{
			arr.push('<option selected value=0>'+Designer_Lang.notPassTip+'</option><option value=1>'+Designer_Lang.passTip+'</option>');
		}
		arr.push('</select>');
		arr.push('</td>');
		
		arr.push('<td width="30%" align="center">');
		arr.push('<input type="text" name="failMessageTip" style="width:98%;" class="inputsgl" value="' + tip + '"/>');
		arr.push('</td>');
		
		arr.push('<td width="5%">');
		arr.push('<a href="javascript:;" onclick="Com_EventPreventDefault();removeValidator(this)">' + Designer_Lang.DelValidator + '</a>');
		arr.push('</td>');
		
		arr.push('</tr>');
		
		$("#ruleList",dom).append(arr.join(""));
		$("#Designer_Control_Validator_Value").val("");
	}
}

/**
 * 校验表达式是否重复
 * @param scriptOut
 * @returns
 */
function _Designer_Control_Validator_scriptOutRepeatValidate(scriptOut){
	var inputArr = $("#ruleList").find("input[type='checkbox'][name != 'selectAll']");
	var isRepeat = false;
	inputArr.each(function(index,ele){
		var validator = $(ele).closest("tr").find("input[type='hidden']").val();
		validator = _Designer_Control_Validator_HtmlEscape(validator).replace(/\s/g,""); 
		scriptOut = scriptOut ? scriptOut.replace(/\s/g,"") : ""; //去除表达式中的空格和进行比较
		if (validator === scriptOut){
			isRepeat = true;
		}
	});
	return isRepeat;
}

//删除选中的多个校验表达式
function _Designer_Control_Validator_removeSelectedExpression(){
	var inputArr = new Array();
	inputArr = $("input:checked",$("#ruleList"));
	//校验是否有选中表达式
	if (inputArr.length <= 0){
		alert(Designer_Lang.pleaseSelectRemoveValidators);
	}else{
		inputArr = $("#ruleList").find("input[type='checkbox'][name != 'selectAll']");
		inputArr.each(function(index,ele){
			if (ele.checked){
				//删除此dom元素
				$(ele).closest("tr").remove();
			}
		});
	}
}

//校验器复选框点击事件
function _Designer_Control_Validator_ChangeSelectAll(self){
	//取消选中,则设置全选按钮为不选中
	if (!self.checked){
		$("#ruleList").find("input[type='checkbox'][name = 'selectAll']").prop("checked","");
	}
	//选中，则判断其它选项是否选中，若都选中则设置全选按钮选中
	if (self.checked){
		var input = $("#ruleList").find("input[type='checkbox'][name!= 'selectAll']");
		var isChecked = true;
		input.each(function(index,ele){
			if (!ele.checked){
				isChecked = false;
			}
		});
		if (isChecked){
			$("#ruleList").find("input[type='checkbox'][name = 'selectAll']").prop("checked","true");
		}
	}
}

//全选按钮事件
function Designer_control_validator_selectAll(self){
	var input = $("#ruleList").find("input[type='checkbox'][name != 'selectAll']");
	if (self.checked){
		input.each(function(index,ele){
			if (!ele.checked){
				$(ele).prop("checked","true");
			}
		});
	}else{
		input.each(function(index,ele){
			$(ele).prop("checked","");
		});
	}
}

/**
 * 校验表达式
 * @param obj 解析后的表达式结果{scriptOut:scriptOut,varis:vairs}
 * @returns  校验通过返回true,否则返回false
 */
function _Desiger_Control_ValidateExpression(obj){
	if($.isEmptyObject(obj)){//表达式为空
		alert(Designer_Lang.pleaseWritevalidatorExpression);
		return false;
	}
	var varis = obj["varis"] || [],scriptOut = obj["scriptOut"] || {},obj;
	if (varis.length === 0){
		alert(Designer_Lang.validator_message_unknowexperssion + ":" + obj["scriptIn"]);//判断校验表达式中是否不含变量,若不包含变量则为非法表达式
		return false;
	}
	
	if (!(/(\S+)(\s*)[<|>|<=|>=|==](\s*)(\S+)/.test(scriptOut))){//判断表达式是否为非boolean类型表达式
		alert(Designer_Lang.validator_message_unknowexperssion + ":" + obj["scriptIn"]);
		return false;
	}
	
	if (scriptOut.indexOf("$$")>-1){//校验两个变量并列的错误
		alert(Designer_Lang.varEval);
		return false;
	}
	for (var i = 0;i < varis.length; i++){
		obj = varis[i] || {};
		if(obj["varId"]==null || obj["varId"] == "" ){//先写了表达式,然后将表达式相关的控件删掉,就获取不到此dom元素
			alert(Designer_Lang.validator_message_unknowvar + ":" + obj["varName"]);
			return false;
		}
		//明细表外的校验控件表达式不能包含明细表内的变量
		if (obj["varId"].indexOf(".") > 0 && Designer_Config.controls.validatorControl.expression_mode === "notRow"){
			alert(Designer_Lang.validator_notContainIsRowControl);
			return false;
		}
	}
	return true;
	
}

/**
 * 解析表达式
 * @param scriptIn
 * @returns {scriptOut:scriptOut,varis:vairs} 解析后的表达式,表达式中的公式变量
 */
function _Desiger_Control_Validator_parseExpression(scriptIn){
	var rtnVal = new Object(), varis = new Array();
	if (Com_Trim(scriptIn) === ''){//表达式不能为空
		return rtnVal;
	}
	var preInfo = {rightIndex:-1};
	var scriptOut = "",exit = true,isInVaris = false;
	for (var nxtInfo = _Desiger_Control_Validator_GetNextInfo(scriptIn, preInfo); nxtInfo!=null; 
							nxtInfo = _Desiger_Control_Validator_GetNextInfo(scriptIn, nxtInfo)) {
		var varId = _Desiger_Control_Validator_GetVarIdByName(nxtInfo.varName, nxtInfo.isFunc);
		if (varId==null || Com_Trim(varId)){//判断此变量是否存在
			exit = false;
		}
		for (var i = 0; i < varis.length; i++){//判断此变量是否已经存在变量数组中
			var obj = varis[i];
			if (obj && obj["varId"] === varId && obj["varName"] === nxtInfo.varName){//变量数组去重
				isInVaris = true;
				break;
			}
		}
		if (!isInVaris){
			varis.push({varId:varId,varName:nxtInfo.varName,exit:exit});
		}
		scriptOut += scriptIn.substring(preInfo.rightIndex+1, nxtInfo.leftIndex) + "$" + varId + "$";
		preInfo = nxtInfo;
	}
	scriptOut += scriptIn.substring(preInfo.rightIndex+1);
	rtnVal.scriptOut = scriptOut,rtnVal.varis = varis;
	return rtnVal
}

//获取指定位置获取变量的信息
function _Desiger_Control_Validator_GetNextInfo(script, preInfo){
	var rtnVal = {};
	rtnVal.leftIndex = script.indexOf("$", preInfo==null?0:preInfo.rightIndex+1);
	if(rtnVal.leftIndex==-1)
		return null;
	rtnVal.rightIndex = script.indexOf("$", rtnVal.leftIndex+1);
	if(rtnVal.rightIndex==-1)
		return null;
	rtnVal.varName = script.substring(rtnVal.leftIndex + 1, rtnVal.rightIndex);
	rtnVal.isFunc = script.charAt(rtnVal.rightIndex+1)=="(";
	return rtnVal;
}

//根据变量名取ID
function _Desiger_Control_Validator_GetVarIdByName(varName, isFunc){
	var varInfo = Designer.instance.getObj();
	for(var i=0; i<varInfo.length; i++){
		var label = _Designer_Control_Validator_htmlUnEscape(varInfo[i].label);
		if(label == varName)
			return varInfo[i].name;
	}
}

//删除校验器
function removeValidator(self){
	var tr = $(self).closest('tr');
	tr.remove();
}

/**
 * 判断校验器控件是否在明细表内
 * @returns
 */
function Designer_Control_Validator_isIntemplateRow(domElement){
	var isRow = false;
	for (var parent = domElement.parentNode; parent != null; parent = parent.parentNode) {
		if (parent.tagName == 'TR'||parent.tagName == 'tr') {
			if ($(parent).attr("type") == 'templateRow') {
				$(domElement).attr("expression_mode",'isRow');
				isRow = true;
				break;
			}
		}
	}
	if (!isRow) {
		$(domElement).attr("expression_mode",'notRow');
	}
	return isRow;
}

/**
 * 根据主表或明细表id获取控件
 */
function Designer_control_Validator_getControlById(id){
	//获取所有控件
	var controls = [],control;
	controls = Designer.instance.getObj();
	for (var i = 0; i < controls.length; i++){
		control = controls[i];
		if (id === control["name"]){
			return control;
		}
	}
	return {};
}

/**
 * @param isRow 
				true:校验器控件在明细表模板行内
				false:校验器控件在明细表模板行外
 * 获取主表和模板行存在控件的明细表
 */
function Designer_Control_Validator_getTable(isRow,validatorDom){
	var tableArr = [],controls,control,
	isContainNotTemplateRowControl = false,isContainStandTable;

	controls = Designer.instance.getObj();//获取所有控件
	
	//判断是否存在明细表外的控件，有则添加一个主表
	isContainStandTable = Designer_Control_Validator_isContainStandTable(controls);
	if (isContainStandTable){
		tableArr.push({name:"",label:Designer_Lang.validator_standTable});
	}else{
		tableArr.push({name:"",label:Designer_Lang.validator_standTable});
	}
	
	if(isRow){//明细表模板行内，显示该明细表和主表
		var detailTableId = Designer_Control_Validator_getDetaliTableId(validatorDom);
		for (var i = 0; i < controls.length; i++){
			control = controls[i];
			if (control["name"] === detailTableId){
				tableArr.push(control);
			}
		}
	}
	return tableArr;
}

/**
 * [Designer_Control_Validator_getDetaliTableId 获取校验器控件所在的明细表id]
 * @param {[type]} validatorDom [description]
 */
function Designer_Control_Validator_getDetaliTableId(validatorDom){
	for (var parent = validatorDom.parentNode; parent != null; parent = parent.parentNode) {
		if (parent.tagName == 'TABLE'|| parent.tagName == 'table') {
			if (Designer_Control_Validator_isDetailsTable(parent)) {
				return $(parent).attr("id");
			}
		}
	}
	return "";
}

function Designer_Control_Validator_isDetailsTable(context) {
    if (!context) {
        return false;
    }
    var fdType = $(context).attr("fd_type");
    var controlType = context["controlType"];
    var applyType = fdType || controlType;
    return applyType == 'detailsTable' || applyType == "seniorDetailsTable";
}

/**
 * [Designer_Control_Validator_isContainNotTemplateRowControl 判断control所在的明细表是否有非模板行控件]
 * @param {[type]} control [description]
 */
function Designer_Control_Validator_isContainNotTemplateRowControl(control){
	//非明细表返回false
	if (!Designer_Control_Validator_isDetailsTable(control)){
		return false;
	}
	var detailTableId = control["name"],
	controls = Designer.instance.getObj(),
	obj,id;

	for (var i = 0; i < controls.length; i++){
		obj = controls[i];
		id = obj["name"];
		//如果控件的id包含明细表的id,并且该控件的isTemplateRow属性为false,则表明该控件
		//在明细表的非模板行
		if (id && id.indexOf(".") < 0 && id != detailTableId){
			var dom = $("#" + id)[0];
			for (var parent = dom.parentNode; parent != null; parent = parent.parentNode) {
				if (parent.tagName == 'TABLE'|| parent.tagName == 'table') {
					if (Designer_Control_Validator_isDetailsTable(parent)) {
						return true;
					}
				}
			}
		}
	}
	return false;
	
}

function Designer_Control_Validator_isContainStandTable(objs){
	var obj = {};
	objs = objs || [];
	for (var i = 0; i < objs.length; i++){
		obj = objs[i];
		if (obj["isTemplateRow"] === false && !Designer_Control_Validator_isDetailsTable(obj)){//非明细表
			return true;
		}
	}
	return false;
}

function Designer_control_validator_getChildrenById(id){
	var objs = new Array(),control;
	control = Designer_control_Validator_getControlById(id);
	objs = Designer_control_validator_getChildren(control);
	return objs;
}

/**
 * @param parent
 * @returns
 */
function Designer_control_validator_getChildren(parent){
	var controls = [],objs = [],control;
	objs = Designer.instance.getObj();
	for (var i = 0;i < objs.length; i++){
		control = objs[i];
		//非明细表
		if ($.isEmptyObject(parent) && control.name.indexOf(".") < 0 && !Designer_Control_Validator_isDetailsTable(control)
			&& control["label"]){
				control["name"] = "$" + control["label"] + "$";
				controls.push(control);
				continue;
		}
		//明细表
		if (parent && parent.name && control.name.indexOf(".") > -1 && control.name.indexOf(parent["name"]) > -1 
				&& parent.name != control.name &&  control["label"]){
			control["name"] = "$" + control["label"] + "$";
			control["label"] = control["label"].split(".")[1];
			controls.push(control);
			continue;
		}
	}
	return controls;
}

function _Designer_Control_Validator_HtmlEscape(s){
	if (s == null || s ==' ') return '';
	s = s.replace(/&/g, "&amp;");
	s = s.replace(/\"/g, "&quot;");
	s = s.replace(/</g, "&lt;");
	s = s.replace(/\'/g,"&#39;");
	return s.replace(/>/g, "&gt;");
};

function _Designer_Control_Validator_htmlUnEscape (s){
	if (s == null || s ==' ') return '';
	s = s.replace(/&amp;/g,"\&");
	s = s.replace(/&quot;/g,"\"");
	s = s.replace(/&lt;/g,"\<");
	s = s.replace(/&#39;/g,"\'");
	return s.replace(/&gt;/g,"\>");
};

/**
 * 鼠标悬停预览表达式事件
 * @param event
 * @param dom
 * @returns
 */
function _Designer_Control_Validator_Onmouseover(event,dom){
	if(!Designer.instance.shortcuts){
		return;
	}
	event = event || window.event;
	dom = dom ? dom : this;
	
	if (Designer_Config.controls.validatorControl.contentDiv == null) {
		Designer_Config.controls.validatorControl.contentDiv = Get_Designer_Control_Validator_contentDiv();
	}
	
	$div = $(dom).closest("div[fd_type='validatorControl']");//获取控件id
	Designer_Config.controls.validatorControl.expression_mode = $div.attr("expression_mode");//重新设置是否在明细内属性，不然会提示请勿添加明细表内数据
	
	//文本隐藏域和提示语隐藏域
	var $textDom = $(dom).find("input[name='validatorRule_name']");
	var $valueDom = $(dom).find("input[name='validatorRule_id']");
	var param = [];
	if ($div.attr("param")){ //优先使用json格式
		param = $div.attr("param");
		if (param){
			param = _Designer_Control_Validator_htmlUnEscape(param);
			param = JSON.parse(param);
			for(var p=0;p<param.length;p++){
				if(param[p].message){
					param[p].message = param[p].message.replace(/\"/g,"&quot;");
				}
					
			}
			
		}else{
			param = [];
		}
		
	}else if ($textDom.size() > 0){
		param = _Designer_Control_Validator_processOldData($valueDom[0],$textDom[0]);
	}
	
	var x=dom.offsetLeft+document.body.scrollLeft;
	var y=dom.offsetTop;
	var scrollWidth = document.body.scrollWidth;
	var scrollHeight = document.body.scrollHeight;
	var d = Designer_Config.controls.validatorControl.contentDiv;
	if(x + 426 - scrollWidth<0){
		d.style.left = (x+24)+'px';
	}else{
		d.style.left = (x-402)+'px';
	}
	if(163 + y - scrollHeight < 0){
		d.style.top = (y-138)+'px';
	}else{
		d.style.top = (scrollHeight-302)+'px';
	}
	d.style.display = '';
	d.style.color = 'black';
	document.body.onclick = _Designer_Control_Validator_bodyClick;
	
	//绘制表达式表格
	$(d).empty();//先清空之前的预览表格
	var tip, arr = [];
	arr.push('<table name="previewExpression" width="100%">');
	arr.push('<tr>');
	arr.push('<td width="35%" align="center">');
	arr.push(Designer_Lang.validatorExpression);
	arr.push('</td>');
	
	arr.push('<td width="30%" align="center">');
	arr.push(Designer_Lang.validatorRule);
	arr.push('</td>');
	
	arr.push('<td width="35%" align="center">');
	arr.push(Designer_Lang.failMessage);
	arr.push('</td>');
	arr.push('</tr>');
	arr.push('</table>');
	$(d).append(arr.join(""));
	
	$(param).each(function(index,info){
		_Designer_Control_Validator_previewExpression(info,d);
	});
}

/**
 * 绘制预览表达式表格
 * @param obj
 * @param dom
 * @returns
 */
function _Designer_Control_Validator_previewExpression(obj,dom){
	var arr = new Array();
	var scriptIn,tip;
	scriptIn = obj["expressionName"] || "";
	tip = obj["message"] || "";
	
	arr.push('<tr>');
		
	arr.push('<td width="35%" align="center">');
	arr.push('<span>' + scriptIn + '</span>');
	arr.push('</td>');
	
	arr.push('<td width="30%" align="center">');
	if (obj.expressionShow == 1){
		arr.push('<span>' + Designer_Lang.passTip+ '</span>');
	}else{
		arr.push('<span>' + Designer_Lang.notPassTip + '</span>');
	}
	arr.push('</td>');
	
	arr.push('<td width="35%" align="center">');
	arr.push('<span>' + tip + '</span>');
	arr.push('</td>');
	
	arr.push('</tr>');
	
	$("table[name='previewExpression']",dom).append(arr.join(""));
	
}

/**
 * 鼠标离开事件
 * @param event
 * @param dom
 * @returns
 */
function _Designer_Control_Validator_Onmouseout(event,dom){
	if(!Designer.instance.shortcuts){
		return;
	}
	event = event || window.event;
	var el = Designer_Config.controls.validatorControl.contentDiv;
	if(event.toElement==el){
		el.onmouseout = _Designer_Control_Validator_Onmouseout;
		return;
	}
	el.style.display = 'none';
	document.body.onclick = '';
}

function _Designer_Control_Validator_bodyClick(){
	var d = Designer_Config.controls.validatorControl.contentDiv;
	d.onmouseout = _Designer_Control_Validator_Onmouseout;
	d.style.display = 'none';
}

function _Designer_Control_Validator_HiddenDiv_Click(event,dom){
	event = event || window.event;
	event.cancelBubble = true;
	if (event.stopPropagation) {event.stopPropagation();}
	dom.onmouseout = '';
	dom.style.display = '';
}

/**
 * 预览dom元素
 * @returns
 */
function Get_Designer_Control_Validator_contentDiv(){
	var validatorHiddenDiv = document.createElement('div');
	with(validatorHiddenDiv.style){
		position = 'absolute',zIndex = '2000',
		width = '400px',height = '300px',
		overflow = 'auto',display = 'none',
		borderStyle = 'solid',borderWidth = '1px',
		borderColor = '#d3e1ea',backgroundColor = '#FFFFE1';	
	};
	validatorHiddenDiv.onclick = function(){
		_Designer_Control_Validator_HiddenDiv_Click(event,this);
	};
	document.body.appendChild(validatorHiddenDiv);
	return validatorHiddenDiv;
}



