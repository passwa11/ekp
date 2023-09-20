/**********************************************************
功能：JSP控件
使用：
	
作者：傅游翔
创建时间：2009-06-26
**********************************************************/

Designer_Config.controls.jsp = {
	dragRedraw : false,
	type : "jsp",
	storeType : 'none',
	inherit    : 'base',
	onDraw : _Designer_Control_JSP_OnDraw,
	drawMobile : _Designer_Control_JSP_DrawMobile,
	fillMobileValue : Designer_Control_JSP_FillMobileValue,
	onDrawEnd : null,
	onDrag : _Designer_JspControl_DoDrag,
	onInitialize : _Designer_JspControl_OnInitialize,
	initDefaultValueAfterPaste :_Designer_Control_JSP_initDefaultValueAfterPaste,
	implementDetailsTable : true,
	// 在新建流程文档的时候，是否显示
	hideInMainModel : true,
	info : {
		name: Designer_Lang.controlJspInfoName
	},
	resizeMode : 'no',
	blockDom: null,
	showDom: null,
	valueDom: null,
	contentDiv : null
};
//兼容历史版本
function _Designer_JspControl_OnInitialize(){
	var label = $(this.options.domElement).find("label");
	if (label.length > 0){
		$(label[0]).css("display","inline-block");
		$(label[0]).css("position","relative");
		label[0].setAttribute("onmouseover","_Designer_Control_JSP_Onmouseover(event,this);");
		label[0].setAttribute("onmouseout","_Designer_Control_JSP_Onmouseout(event,this);");
	}
	var span = $(this.options.domElement).find("span");
	if (span.length > 0){
		if($(span[0]).css("position")!="relative"){
			$(span[0]).css("position","relative");
			span[0].setAttribute("onmouseover","_Designer_Control_JSP_Onmouseover(event,this);");
			span[0].setAttribute("onmouseout","_Designer_Control_JSP_Onmouseout(event,this);");
		}
	}
}

function _Designer_Control_JSP_initDefaultValueAfterPaste(values){
	values.copyid = values.id;
	return values;
}

function  _Designer_JspControl_DoDrag(event){
	var el = Designer_Config.controls.jsp.contentDiv;
	if(el){
		el.style.display='none';
	}
	_Designer_Control_Base_DoDrag.call(this,event);
}

function Get_Designer_Control_JSP_BlockDom() {
	var dom = document.createElement('div');
	with(document.body.appendChild(dom).style) {
		position = 'fixed'; filter = 'alpha(opacity=80)'; opacity = '0.8';
		border = '1px'; background = '#EAEFF3'; display = 'none';
		width = '100%';
		height = '100%';
		top = '0'; left = '0';
		zIndex = '999';
	}
	return dom;
}

//滚轮事件
function Designer_Control_JSP_mainPanelScroll(event,scroller){
	var k = event.wheelDelta? event.wheelDelta:-event.detail*10;
    scroller.scrollTop = scroller.scrollTop - k;
    return false;
}

function Get_Designer_Control_JSP_ShowDom() {
	var domElement = document.createElement('div');
	document.body.appendChild(domElement);
	domElement.className= 'panel_jsp_panel';
	domElement.id = "Designer_Control_JSP_Div";
	domElement.style.width = '80%';
	var divtreewidth = domElement.offsetWidth*0.15;
	var textareaH = Designer.getDocumentAttr("clientHeight")*300/418;
	var html = [];
	//功能按钮
	var panelButtonHtml = '<div style="float:right;">'; 
	panelButtonHtml += '<a id="Designer_Control_JSP_A" href="javascript:void(0)" onclick="_Designer_Control_JSP_MaxImize();" title="'+ Designer_Lang.jspControlMaximize + '" style="display:inline-block;width:12px;height:12px;background: url(style/img/jsp_control_max.png) no-repeat -3px ;margin-top: 6px;"></a>';
	panelButtonHtml += '<a id="Designer_Control_JSP_A2" href="javascript:void(0)" onclick="Designer_Control_JSP_CloseEditFrame();" title="'+ Designer_Lang.jspControlClose + '" style="display:inline-block;width:12px;height:12px;background: url(style/img/jsp_control_close.png) no-repeat 0px ;margin-top: 6px;"></a>';
	panelButtonHtml += '</div>';
	html.push('<div class="resize_panel">', 
			'<div class="resize_panel_top"><table class="resize_panel_top_panel"><tr>',
			'<td class="resize_panel_top_left"></td>', 
			'<td class="resize_panel_top_center">','<span style="float:left;">'+ Designer_Lang.controlJspInfoName +'</span>',panelButtonHtml, '</td>',
			'<td class="resize_panel_top_right"></td></tr></table></div>');
	html.push('<div class="resize_panel_main">',
			'<table class="resize_panel_main_panel"><tr>',
			'<td class="resize_panel_main_left" style="width:'+divtreewidth+'px;vertical-align:top;"><div id="Designer_Control_JSP_TreeDiv" onmousewheel="return Designer_Control_JSP_mainPanelScroll(event,this);" style="width:'+divtreewidth+'px;height:'+(textareaH+32)+'px;overflow:auto;margin-left:8px" class="treediv"></div></td>',
			'<td class="resize_panel_main_center">',
			'<textarea id="Designer_Control_JSP_Value" style="width:100%;height:'+textareaH+'px;word-break:break-all;" wrap="off"></textarea>',
			'<div><label>' + Designer_Lang.jspControlName + '</label><input type="text" name="Designer_Control_JSP_Name" style="width:60%;" ></div>',
			'</td><td class="resize_panel_main_right"></td></tr></table></div>');
	html.push('<div class="resize_panel_bottom">', 
			'<table class="resize_panel_bottom_panel">',
			'<tr><td class="resize_panel_bottom_panel_left"></td>',
			'<td class="resize_panel_bottom_panel_center"><div>',
			'<button class="panel_success" title="',Designer_Lang.controlJspSuccess,'" ',
			' onclick="Designer_Control_JSP_SuccessEditFrame();">&nbsp;</button>',
			'&nbsp;&nbsp;',
			'<button class="panel_cancel" title="',Designer_Lang.controlJspCancel,'" ',
			' onclick="Designer_Control_JSP_CloseEditFrame();">&nbsp;</button>',
			'&nbsp;&nbsp;',
			'<button class="panel_help" title="',Designer_Lang.controlJspHelp,'" ',
			' onclick="Com_OpenWindow(\'jspcontrol_help.jsp\', \'_blank\');">&nbsp;</button>',
			'</div></td><td class="resize_panel_bottom_panel_right"></td>',
			'</tr></table>',
			'<table class="resize_panel_bottom_border">',
			'<tr><td class="resize_panel_bottom_border_left"></td>', 
			'<td class="resize_panel_bottom_border_center">&nbsp;</td>',
			'<td class="resize_panel_bottom_border_right"></td></tr></table>',
			'</div></div>');
	domElement.innerHTML = html.join('');
	with(domElement.style) {
		position = 'fixed'; zIndex = '1000'; display = 'none';
	}
	Designer.addEvent(window , 'scroll' , function() {
		if (domElement.style.display != '') return;
		Set_Designer_Control_JSP_ShowDomNeedPostion(domElement);
	});
	return domElement;
}

function Set_Designer_Control_JSP_ShowDomNeedPostion(domElement) {
	var p = Get_Designer_Control_JSP_ShowDomNeedPostion(domElement);
	domElement.style.top = p.y + 'px';
	domElement.style.left = p.x + 'px';
}

function Get_Designer_Control_JSP_ShowDomNeedPostion(domElement) {
	return ({
		x : document.body.offsetWidth / 2 + document.body.scrollLeft - domElement.offsetWidth / 2,
		y : window.innerHeight?(window.innerHeight / 2 + document.documentElement.scrollTop - domElement.offsetHeight / 2):(document.body.offsetHeight / 2 + document.body.scrollTop - domElement.offsetHeight / 2)
	});
}

function Designer_Control_JSP_ShowEditFrame(event, dom) {
	//防止view模式下，点击jsp片段出现异常 作者 曹映辉 #日期 2015年3月31日
	if(!Designer.instance.shortcuts){
		return;
	}
	event = event || window.event;
	event.cancelBubble = true;
	if (event.stopPropagation) {event.stopPropagation();}
	dom = dom ? dom : this;
	Designer_Config.controls.jsp.valueDom = dom.firstChild;
	var valueDom = Designer_Config.controls.jsp.valueDom;
	var value = valueDom.value ? valueDom.value : "<%\n%>";
	if (Designer_Config.controls.jsp.blockDom == null)  {
		Designer_Config.controls.jsp.blockDom = Get_Designer_Control_JSP_BlockDom();
	}else{
		//非首次打开的时候，重新设置遮罩的宽高 by zhugr 2017-06-30
		with(Designer_Config.controls.jsp.blockDom.style){
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
	if (Designer_Config.controls.jsp.showDom == null) {
		Designer_Config.controls.jsp.showDom = Get_Designer_Control_JSP_ShowDom();
		Designer.instance.shortcuts.add('tab', function() {
			var obj = document.getElementById("Designer_Control_JSP_Value");
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
		}, {'target':Designer_Config.controls.jsp.showDom});
	}else{
		//非首次打开的时候，重新设置treediv和textarea高度
		var jsp_d = document.getElementById("Designer_Control_JSP_TreeDiv");
		var jsp_t = document.getElementById("Designer_Control_JSP_Value");
		var textareaH = Designer.getDocumentAttr("clientHeight")*300/418;
		$(jsp_t).css("height",textareaH);
		$(jsp_d).css("height",textareaH+32);
	}
	Designer.instance.jspoldscrtop = Designer.getDocumentAttr("scrollTop");
	document.documentElement.scrollTop = document.body.scrollTop = 0;
	document.body.style.overflow = 'hidden';
	_Designer_Control_JSP_GenerateTree(Designer.instance.getObj(false));
	var valueDom = document.getElementById("Designer_Control_JSP_Value");
	valueDom.value = value;
	document.getElementsByName("Designer_Control_JSP_Name")[0].value = dom.parentNode.title;
	Designer_Config.controls.jsp.blockDom.style.display = '';
	Designer_Config.controls.jsp.showDom.style.display = '';
	Set_Designer_Control_JSP_ShowDomNeedPostion(Designer_Config.controls.jsp.showDom);
}

function Designer_Control_JSP_CloseEditFrame() {
	Designer_Config.controls.jsp.blockDom.style.display = 'none';
	Designer_Config.controls.jsp.showDom.style.display = 'none';
	Designer_Config.controls.jsp.valueDom = null;
	_Designer_Control_JSP_Minrestore=1;
	_Designer_Control_JSP_MaxImize();
	document.body.style.overflow = 'auto';
	document.documentElement.scrollTop = document.body.scrollTop  = Designer.instance.jspoldscrtop;
}

function Designer_Control_JSP_SuccessEditFrame() {
	var valueDom = Designer_Config.controls.jsp.valueDom;
	valueDom.value = document.getElementById("Designer_Control_JSP_Value").value;
	valueDom.parentNode.parentNode.title = 
		document.getElementsByName("Designer_Control_JSP_Name")[0].value || Designer_Lang.controlJspInfoName;
	Designer_Control_JSP_CloseEditFrame();
	
	//同步移动端控件值
	if (Designer.instance.mobileDesigner) {
		var __mobileDesigner = Designer.instance.mobileDesigner;
		var __designer = __mobileDesigner.getMobileDesigner();
		var jspControl = Designer.instance.builder.getControlByDomElement(valueDom);
		var pcControlId = jspControl.options.values.id;
		var mobileControls = __mobileDesigner.getMobileControlById(pcControlId);
		for (var i = 0; i < mobileControls.length; i++) {
			mobileControls[i].__pcOptions = jspControl.options;
			mobileControls[i].fillMobileValue();
		}
	}
	
	//增加对撤销功能的支持,--add by zhouzf
	if(typeof (DesignerUndoSupport)  != 'undefined'){
		DesignerUndoSupport.saveOperation();
	}
	_Designer_Control_JSP_Minrestore=1;
	_Designer_Control_JSP_MaxImize();
	document.body.style.overflow = 'auto';
	document.documentElement.scrollTop = document.body.scrollTop  = Designer.instance.jspoldscrtop;
}

function _Designer_Control_JSP_OnDraw(parentNode, childNode) {
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	domElement.id = this.options.values.id;
	domElement.style.width='25px';
	domElement.style.display="inline";
	domElement.style.margin = "0 7px";
	var div = document.createElement("label");
	var label = document.createElement("span");
	var jspcontent = document.createElement("input");
	jspcontent.type = 'hidden';
	label.appendChild(jspcontent);
	label.style.background = "url(style/img/jsp.png) no-repeat";
	label.style.margin = '0px 0px 0px 0px';
	label.style.width='24px';
	label.style.height='24px';
	label.style.display="inline-block";
	label.style.position = 'relative';
	//label.ondblclick = "Designer_Control_JSP_ShowEditFrame(event, this);";
	label.setAttribute("ondblclick","Designer_Control_JSP_ShowEditFrame(event, this);");
	
	//label.onmouseover = "_Designer_Control_JSP_Onmouseover(event,this);";
	//label.onmouseout = "_Designer_Control_JSP_Onmouseout(event,this);";
	label.setAttribute("onmouseover","_Designer_Control_JSP_Onmouseover(event,this);");
	label.setAttribute("onmouseout","_Designer_Control_JSP_Onmouseout(event,this);");
	
	div.appendChild(label);
	domElement.innerHTML = div.innerHTML;
	domElement.title = Designer_Lang.controlJspInfoName;
	//兼容复制粘贴
	if(this.options.values.copyid && !jspcontent.value && !domElement.getAttribute("mobileView")){
		//获取复制对象的数据
		var copyid = this.options.values.copyid;
		var copyDom = $("#"+copyid)[0];
		var copyValueDom = $("#"+copyid).find("input")[0];
		if(copyDom && copyValueDom){
			$(domElement).find("input").val(copyValueDom.value);
			domElement.setAttribute("mobileView",copyDom.getAttribute("mobileView"));
		}
	}
}

function _Designer_Control_JSP_GenerateTree(varInfo) {
	LKSTree = new TreeView("LKSTree", Designer_Lang.jspControlCode, document.getElementById("Designer_Control_JSP_TreeDiv"));
	var n1,n2,n3,n4,n5,n6,n7;
	n1 = LKSTree.treeRoot;
	n2 = n1.AppendChild(
		Designer_Lang.jspControlFormField
	);
	n2.FetchChildrenNode = _Designer_Control_JSP_GetVars(varInfo,n2);
	n3 = n1.AppendChild(
		Designer_Lang.jspControlFun
	);
	n3.FetchChildrenNode = _Designer_Control_JSP_GetFunctions;
	n4 = n1.AppendChild(
		Designer_Lang.jspControlJs
	);
	_Designer_Control_JSP_SetNode(n4,'<script type="text/javascript">\r\n//此处添加js代码\r\n\r\n</script>\r\n');
	n5 = n1.AppendChild(
		Designer_Lang.jspControlJava
	);
	_Designer_Control_JSP_SetNode(n5,"<%\r\n//此处添加java代码\r\n\r\n%>\r\n");
	n6 = n1.AppendChild(
		Designer_Lang.jspControlCss
	);
	_Designer_Control_JSP_SetNode(n6,"<style>\r\n/* 此处添加css代码  */\r\n\r\n</style>\r\n");
	n7 = n1.AppendChild(
		Designer_Lang.jspControlImport
	);
	_Designer_Control_JSP_SetNode(n7,'<c:import url="例如：/sys/right/import/right_view.jsp" charEncoding="UTF-8"></c:import>\r\n');
	
	LKSTree.Show();
}

function _Designer_Control_JSP_SetNode(node,value){
	node.action = _Designer_Control_JSP_SetSelect;
	node.value =value;
}

function _Designer_Control_JSP_GetVars(info,pNode){
	var varInfo = info;
	//存储模板节点
	var tempNode = pNode;
	for(var i=0; i < varInfo.length; i++){
		var textArr = varInfo[i].label.split(".");
		//重置模板节点
		pNode = tempNode;
		var node;
		for(var j=0; j < textArr.length; j++){
			node = Tree_GetChildByText(pNode, textArr[j]);
			if(node == null){
				node = pNode.AppendChild(textArr[j]);
			}
			pNode = node;
		}
		node.action = _Designer_Control_JSP_SetSelect;
		node.value =varInfo[i].name;
		node.title = varInfo[i].label;
		//node.summary = varInfo[i].summary;
	}
}

function _Designer_Control_JSP_GetFunctions(){
	var funs = _Designer_Control_JSP_SetFunctions();
	for(var i=0; i < funs.length; i++){
		var pNode = this;
		var node;
		node = Tree_GetChildByText(pNode, funs[i].text);
		if(node==null){
			node = pNode.AppendChild(funs[i].text);
		}	
		node.action = _Designer_Control_JSP_SetSelect;
		node.value =funs[i].value;
		node.title = funs[i].title;
	}
}

function _Designer_Control_JSP_SetFunctions(){
	var funs = [{'text':Designer_Lang.jsp_AttachXFormValueChangeEventById,'value':'AttachXFormValueChangeEventById("表单控件ID",function(){\r\n//function为绑定函数\r\n\r\n});\r\n','title':Designer_Lang.jsp_AttachXFormValueChangeEventById_Title},
	            {'text':Designer_Lang.jsp_GetXFormFieldById,'value':'GetXFormFieldById("表单控件ID");','title':Designer_Lang.jsp_GetXFormFieldById},
	            {'text':Designer_Lang.jsp_GetXFormFieldValueById,'value':'GetXFormFieldValueById("表单控件ID");','title':Designer_Lang.jsp_GetXFormFieldValueById},
	            {'text':Designer_Lang.jsp_SetXFormFieldValueById,'value':'SetXFormFieldValueById("表单控件ID","值");','title':Designer_Lang.jsp_SetXFormFieldValueById}];
	return funs;
}

function _Designer_Control_JSP_SetSelect(){
	var area = document.getElementById("Designer_Control_JSP_Value");
	_Designer_Control_JSP_InsertText(area, this);
}

function _Designer_Control_JSP_InsertText(obj, node) {
	obj.focus();
	if (document.selection) {
		var sel = document.selection.createRange();
		sel.text = node.value;
	} else if (typeof obj.selectionStart === 'number' && typeof obj.selectionEnd ==='number') {
		var startPos = obj.selectionStart, endPos = obj.selectionEnd, cursorPos = startPos, tmpStr = obj.value;   
		obj.value = tmpStr.substring(0, startPos) + node.value + tmpStr.substring(endPos, tmpStr.length);
		cursorPos += node.value.length;
		obj.selectionStart = obj.selectionEnd = cursorPos;
	} else {
		obj.value += node.value;
	}
}

var _Designer_Control_JSP_Minrestore = 0;var _Designer_Control_JSP_parentIframeWidth;var _Designer_Control_JSP_parentIframeHeight;
var _Designer_Control_JSP_OldWidth;
var _Designer_Control_JSP_OldHeight; var _Designer_Control_JSP_OldTop; var _Designer_Control_JSP_OldLeft;
function _Designer_Control_JSP_MaxImize(){
	
	var jsp_d = document.getElementById("Designer_Control_JSP_Div");
	var jsp_t = document.getElementById("Designer_Control_JSP_Value");
	var jsp_a = document.getElementById("Designer_Control_JSP_A");
	var jsp_treediv = document.getElementById("Designer_Control_JSP_TreeDiv");
	
	if (_Designer_Control_JSP_Minrestore==0){
		_Designer_Control_JSP_Minrestore=1;
		_Designer_Control_JSP_parentIframeWidth = parentIframe.width;
		_Designer_Control_JSP_parentIframeHeight = parentIframe.height;
		parentIframe.width = Designer.getDocumentAttr("clientWidth",parent);
		parentIframe.height = Designer.getDocumentAttr("clientHeight",parent);
		parentIframe.style.position = "absolute";
		parentIframe.style.zIndex = "9999";
		parentIframe.style.top = '0px';
		parentIframe.style.left = '0px';
		
		_Designer_Control_JSP_OldWidth = jsp_d.style.width;
		_Designer_Control_JSP_OldHeight = jsp_t.style.height;
		_Designer_Control_JSP_OldTop = jsp_d.style.top;
		_Designer_Control_JSP_OldLeft = jsp_d.style.left;
		Designer_Config.controls.jsp.TreeDivH = jsp_d.style.height;;
		var bodyHeight = Designer.getDocumentAttr("clientHeight");
		$(jsp_t).css('height',bodyHeight*0.814);
		$(jsp_treediv).css('height',bodyHeight*0.814+32);
		jsp_d.style.top = '0px';
		jsp_d.style.left = '0px';
		$(jsp_d).css('width',parentIframe.width);
		jsp_a.title = Designer_Lang.jspControlRestore;
		jsp_a.style.background = "url(style/img/jsp_control_min.png) no-repeat -3px";
	}else{
		_Designer_Control_JSP_Minrestore=0;	
		if(!Designer.instance.isFullScreen){
			parentIframe.style.position = "";
			parentIframe.style.zIndex = "1";
			parentIframe.width = _Designer_Control_JSP_parentIframeWidth ? _Designer_Control_JSP_parentIframeWidth:parentIframe.width;
			parentIframe.height = _Designer_Control_JSP_parentIframeHeight ? _Designer_Control_JSP_parentIframeHeight:parentIframe.height;
		}
		$(jsp_d).css('width',_Designer_Control_JSP_OldWidth ? _Designer_Control_JSP_OldWidth:jsp_d.style.width);
		$(jsp_t).css('height',_Designer_Control_JSP_OldHeight ? _Designer_Control_JSP_OldHeight:jsp_t.style.height);
		$(jsp_treediv).css('height',Designer_Config.controls.jsp.TreeDivH);
		jsp_d.style.top = _Designer_Control_JSP_OldTop ? _Designer_Control_JSP_OldTop:jsp_d.style.top;
		jsp_d.style.left = _Designer_Control_JSP_OldLeft ? _Designer_Control_JSP_OldLeft:jsp_d.style.left;
		jsp_a.title = Designer_Lang.jspControlMaximize;
		jsp_a.style.background = "url(style/img/jsp_control_max.png) no-repeat -3px";
	}
	//放大缩小后重新设置遮罩层宽高
	with(Designer_Config.controls.jsp.blockDom.style){
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

function Get_Designer_Control_JSP_contentDiv(){
	var jspHiddenDiv = document.createElement('div');
	jspHiddenDiv.style.position = 'absolute'; 
	jspHiddenDiv.style.zIndex = '2000'; 
	jspHiddenDiv.style.width = '400px'; 
	jspHiddenDiv.style.height = '300px';
	jspHiddenDiv.style.overflow = 'auto';
	jspHiddenDiv.style.display = 'none';
	jspHiddenDiv.style.borderStyle = 'solid';
	jspHiddenDiv.style.borderWidth = '1px';
	jspHiddenDiv.style.borderColor = '#d3e1ea';
	jspHiddenDiv.style.backgroundColor = '#FFFFE1';
	jspHiddenDiv.onclick = function(){
		_Designer_Control_JSP_HiddenDiv_Click(event,this);
	};
	document.body.appendChild(jspHiddenDiv);
	return jspHiddenDiv;
}

function _Designer_Control_JSP_Onmouseover(event,dom){
	if(!Designer.instance.shortcuts){
		return;
	}
	event = event || window.event;
	dom = dom ? dom : this;
	
	if (Designer_Config.controls.jsp.contentDiv == null) {
		Designer_Config.controls.jsp.contentDiv = Get_Designer_Control_JSP_contentDiv();
	}
	
	Designer_Config.controls.jsp.valueDom = dom.firstChild;
	var valueDom = Designer_Config.controls.jsp.valueDom;
	var value = valueDom.value ? valueDom.value : "<%\n%>";
	var x=dom.offsetLeft;
	var y=dom.offsetTop;
	var scrollWidth = Designer.getDocumentAttr("scrollWidth");
	var scrollHeight = Designer.getDocumentAttr("scrollHeight");
	var d = Designer_Config.controls.jsp.contentDiv;
	//兼容旧版jsp控件的历史数据，旧版的jsp控件图标大小和新版不同，故悬浮框定位需修改
	var isLabel = dom.tagName.toLowerCase()=='label'?true:false;
	if((x+(isLabel?16:24)+402)-scrollWidth<0){
		d.style.left = (x+(isLabel?16:24))+'px';
	}else{
		d.style.left = (x-402)+'px';
	}
	if((151+(isLabel?7:12)+y)-scrollHeight<0){
		d.style.top = (y-(isLabel?143:138))+'px';
	}else{
		d.style.top = (scrollHeight-302)+'px';
	}
	d.style.display = '';
	d.style.color = 'black';
	document.body.onclick = _Designer_Control_JSP_bodyClick;
	d.innerText = value;
}

function _Designer_Control_JSP_Onmouseout(event,dom){
	if(!Designer.instance.shortcuts){
		return;
	}
	event = event || window.event;
	var el = Designer_Config.controls.jsp.contentDiv;
	if(event.toElement==el){
		el.onmouseout = _Designer_Control_JSP_Onmouseout;
		return;
	}
	el.style.display = 'none';
	document.body.onclick = '';
}

function _Designer_Control_JSP_bodyClick(){
	var d = Designer_Config.controls.jsp.contentDiv;
	d.onmouseout = _Designer_Control_JSP_Onmouseout;
	d.style.display = 'none';
}

function _Designer_Control_JSP_HiddenDiv_Click(event,dom){
	event = event || window.event;
	event.cancelBubble = true;
	if (event.stopPropagation) {event.stopPropagation();}
	dom.onmouseout = '';
	dom.style.display = '';
}