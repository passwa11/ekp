<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<link rel=stylesheet href="../css/panel.css">
<c:choose>
	<c:when test="${JsParam.mobile eq 'true'}">
		<%@ include file="/resource/jsp/htmlhead.jsp" %>
	</c:when>
	<c:otherwise>
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	</c:otherwise>
</c:choose>	
<script type="text/javascript">
Com_IncludeFile('jquery.js|dialog.js|formula.js|plugin.js');
</script>
</head>
<body style="margin:0px">
<table id="mainTable" style="height:99.9%; width:99.9%" border=0 cellpadding=0 cellspacing=0>
	<tr class="buttonbar_main" style="display:none">
		<td></td>
	</tr>
	<tr class="processTool" id="processTool" style="display:none">
		<td></td>
	</tr>
	<tr>
		<td style="height: 100%">
			<iframe id="iframe_chart" width=100% height=99.5% frameborder=0></iframe>
		</td>
	</tr>
	<tr class="buttonbar_main" style="display:none">
		<td align="center">
			<a style="display:none" herf="#" id="btnModify" class="href_opt" onclick="FlowChart_Com_PopupWindow('process_popedom_modify_content.html',640,480)"></a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="button_opt" id="btnOk" onclick="returnDialogValue();">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="button_opt" id="btnCancel" onclick="window.close();">
		</td>
	</tr>
	<tr class="buttonbar_main" style="display:none">
		<td align="center"></td>
	</tr>
	<c:if test="${JsParam.mobile ne 'true'}">
		<tr class="buttonbar_main" style="height:46px;display:none">
			<td align="center">
				<ui:button text="${lfn:message('button.save') }" order="1" onclick="save();"  style="width:77px;">
				</ui:button>
				<ui:button text="${lfn:message('button.cancel') }" order="2" onclick="closeDialog();" style="width:77px;padding-left:10px">
				</ui:button>
			</td>
		</tr>
	</c:if>
</table>
<script type="text/javascript" src="../js/buttonbar.js"></script>
<script>
var dialogObject = null;
if(window.showModalDialog) {
	dialogObject = window.dialogArguments;
	var popup = Com_GetUrlParameter(location.href,"popup");
	if (dialogObject == null && popup == "true") {
		//#154468-IE浏览器，修改其它节点处理人弹出框没有内容
		dialogObject = top.Com_Parameter.Dialog;
	}
} else if(opener) {
	dialogObject = opener.Com_Parameter.Dialog;
} else {
	var popup = Com_GetUrlParameter(location.href,"popup");
	if (dialogObject == null && popup == "true") {
		dialogObject = top.Com_Parameter.Dialog;
	}
}
</script>
<script>
var qdomain = Com_GetUrlParameter("domain");
if (qdomain != null && qdomain != '') {
	document.domain = qdomain;
} else {
	qdomain = null;
}
var FlowChartObject = null;
var FULLSCREEN_STATUS = false;
var FULLSCREEN_IFRAME = null;
var PanelHeight = 0;
//全屏操作
function FlowChart_ChangeFullScreen(){
	FULLSCREEN_STATUS = !FULLSCREEN_STATUS;
	var iStyle = FULLSCREEN_IFRAME.style;
	var pBody = parent.document.body;
	if(FULLSCREEN_STATUS){
		PanelHeight = FULLSCREEN_IFRAME.clientHeight;
		pBody.style.overflowX = "hidden";
		pBody.style.overflowY = "hidden";
		iStyle.position = "absolute";
		iStyle.top = pBody.scrollTop;
		iStyle.left = pBody.scrollLeft;
		iStyle.width = pBody.clientWidth;
		iStyle.height = pBody.clientHeight;
	}else{
		pBody.style.overflowX = "auto";
		pBody.style.overflowY = "auto";
		iStyle.position = "relative";
		iStyle.top = 0;
		iStyle.left = 0;
		iStyle.width = "100%";
		iStyle.height = PanelHeight;
	}
	if(parent.addEventListener){
		parent.addEventListener("resize", FlowChart_FullScreenOnParentResize, false);
	} else {
		parent.attachEvent("onresize", FlowChart_FullScreenOnParentResize);
	}
}

//功能：全屏时父窗口大小改变事件
function FlowChart_FullScreenOnParentResize(){
	FULLSCREEN_IFRAME.style.width = parent.document.body.clientWidth;
	FULLSCREEN_IFRAME.style.height = parent.document.body.clientHeight;
}

//功能：浏览器是否支持svg
function FlowChart_SVG_Supported() {
	return !!document.createElementNS
			&& !!document.createElementNS('http://www.w3.org/2000/svg', 'svg').createSVGRect;
}

//功能：初始化
function FlowChart_Bar_Initialize(){
	document.getElementById("iframe_chart").onload = FlowChart_ShowStatusInfo;

	var srcurl = "chart_vml.html";
	//if(!(typeof window.ActiveXObject!="undefined")) { //IE
		//srcurl = "chart_svg.html";
	//}
	if(FlowChart_SVG_Supported()) {
		srcurl = "chart_svg.html";
	}
	if(qdomain != null) {
		srcurl += "?domain=" + qdomain;
	}
	
	var mainTable = document.getElementById("mainTable");
	if(dialogObject==null || dialogObject.Window==null){
		var parentWindow = parent;
		//读取取父窗口中，当前窗口对应的IFrame对象
		var iframes = parent.document.getElementsByTagName("IFRAME");
		for(var i=0; i<iframes.length; i++){
			if(iframes[i].contentWindow == window){
				FULLSCREEN_IFRAME = iframes[i];
				FULLSCREEN_IFRAME.style.zIndex = 9999;
				break;
			}
		}
		if((Com_GetUrlParameter("isNotShowBar") == null || Com_GetUrlParameter("isNotShowBar") == 'false')&&Com_GetUrlParameter("edit")!="true" && Com_GetUrlParameter("template")!="true"){
			mainTable.rows[5].style.display = "";
		}
	}else{
		var parentWindow = dialogObject.Window;
		if (dialogObject.ShowOptButton != false && Com_GetUrlParameter("edit")=="true")
			mainTable.rows[5].style.display = "";
		if (dialogObject.FormFieldList != null)
			setTimeout(WorkFlow_SetFormList, 100);
		window.resizeTo(screen.availWidth||screen.width, screen.availHeight||screen.height);
	}
	if (Com_GetUrlParameter("showBar") == 'true') {
		mainTable.rows[0].style.display = "";
	}
	Com_Parameter = {
		ContextPath:parentWindow.Com_Parameter.ContextPath,
		ResPath:parentWindow.Com_Parameter.ResPath,
		Style:parentWindow.Com_Parameter.Style,
		StylePath:parentWindow.Com_Parameter.StylePath,
		Cache:parentWindow.Com_Parameter.Cache
	};
	document.getElementById("iframe_chart").src = srcurl;
	
	Data_XMLCatche = parentWindow.Data_XMLCatche;
	if(Data_XMLCatche==null) {
		Data_XMLCatche = new Array();
	}
	Data_MyAddedNodes = new Array();
	Data_MyAddedNodes_Init = new Array();
	if (dialogObject != null && dialogObject.MyAddedNodes) {
		Data_MyAddedNodes = dialogObject.MyAddedNodes;
		Data_MyAddedNodes_Init = Data_MyAddedNodes.slice(0);
	}else{
		if (parent && parent.lbpm && parent.lbpm.myAddedNodes) {
			Data_MyAddedNodes = parent.lbpm.myAddedNodes;
			Data_MyAddedNodes_Init = Data_MyAddedNodes.slice(0);
		}
	}
	if(parent.lbpm && parent.lbpm.freeFlow && parent.lbpm.freeFlow.nextNodes && parent.lbpm.freeFlow.nextNodes.length>0){
		if(parent.lbpm.constant && parent.lbpm.constant.ROLETYPE==parent.lbpm.constant.AUTHORITYROLETYPE){
			return;
		}
		Data_NextNodes = parent.lbpm.freeFlow.nextNodes;
	}
}

function FlowChart_ShowStatusInfo(){
	try{
		FlowChartObject.Lang.Node;
	}catch(e){
		setTimeout("FlowChart_ShowStatusInfo()",100);
		return;
	}
	var langObj = FlowChartObject.Lang;
	var htmlCode = FlowChart_GetColorBlock(FlowChartObject.STATUS_NORMAL, langObj.Node.STATUS_NORMAL);
	htmlCode += FlowChart_GetColorBlock(FlowChartObject.STATUS_PASSED, langObj.Node.STATUS_PASSED);
	htmlCode += FlowChart_GetColorBlock(FlowChartObject.STATUS_RUNNING, langObj.Node.STATUS_RUNNING);
	var mainTable = document.getElementById("mainTable");
	mainTable.rows[4].cells[0].innerHTML = htmlCode;
	
	var zoom = Com_GetUrlParameter("zoom");
	if (zoom != null && zoom != '') {
		zoom = parseInt(zoom);
		var iframe = document.getElementById('iframe_chart').contentWindow;
		if (iframe.FlowChart_Operation_ChangeZoom) {
			iframe.FlowChart_Operation_ChangeZoom(zoom);
		}
	}
}

function FlowChart_GetColorBlock(colorIndex, text){
	return "<span class='buttonbar_label' style='background-color:"+FlowChartObject.NODESTYLE_STATUSCOLOR[colorIndex]+";'></span>&nbsp;"+text+"&nbsp;&nbsp;&nbsp;";
}
function WorkFlow_SetFormList() {
	try {
		FlowChartObject.FormFieldList = dialogObject.FormFieldList;
	} catch(e) {
		setTimeout(WorkFlow_SetFormList, 100);
	}
}
function WorkFlow_GetProcessData(){
	
	if(Com_GetUrlParameter("freeflowPanelImg")!=null&&Com_GetUrlParameter("freeflowPanelImg")=="true"){
		var GetFormField = function() {
			var fieldName = Com_GetUrlParameter("contentField");
			if(fieldName!=null){
				return parent.document.getElementsByName(fieldName)[0].value;
			}
			return null;
		};
		
		if(dialogObject==null){
			return GetFormField();
		}else{
			return dialogObject.processData;
		}
		
	}else{
		var IsEdit = Com_GetUrlParameter("edit")=="true";
		var IsTemplate = Com_GetUrlParameter("template")=="true";
		var GetFormField = function() {
			var fieldName = Com_GetUrlParameter("contentField");
			if(fieldName!=null){
				return parent.document.getElementsByName(fieldName)[0].value;
			}
			return null;
		};
		if (IsTemplate) {
			if(dialogObject==null){
				return GetFormField();
			}else{
				return dialogObject.processData;
			}
		}
		else {
			if (IsEdit) {
				if(dialogObject==null){
					return GetFormField();
				}else{
					return dialogObject.processData;
				}
			} else {
				if (window.parent && window.parent.lbpm) {
					var lbpm = window.parent.lbpm;
					var nodes = [], lines = [];
					for (var o in lbpm.nodes) {
						nodes.push(lbpm.nodes[o]);
					}
					for (var o in lbpm.lines) {
						lines.push(lbpm.lines[o]);
					}
					var process = {nodes: nodes, lines: lines};
					for (var o in lbpm.flowcharts) {
						process[o] = lbpm.flowcharts[o];
					}
					return process;
				}
				return GetFormField();
			}
		}
	}
}

function WorkFlow_GetStatusData(){
	if(dialogObject==null){
		var fieldName = Com_GetUrlParameter("statusField");
		if(fieldName!=null){
			return parent.document.getElementsByName(fieldName)[0].value;
		}
		return null;
	}else{
		return dialogObject.statusData;
	}
}

function Com_GetUrlParameter(param){
	var url = location.href;
	var re = new RegExp();
	re.compile("[\\?&]"+param+"=([^&]*)", "i");
	var arr = re.exec(url);
	if(arr==null)
		return null;
	else
		return decodeURIComponent(arr[1]);
}

function FlowChart_Com_PopupWindow(url,width,height) {
	var dialogObject = [];
	dialogObject.FlowChartObject=FlowChartObject;
	dialogObject.Window=window;
	var left = (screen.width-width)/2;
	var top = (screen.height-height)/2;
	if(window.showModalDialog){
		var winStyle = "resizable:1;scroll:1;dialogwidth:"+width+"px;dialogheight:"+height+"px;dialogleft:"+left+";dialogtop:"+top;
		window.showModalDialog(url, dialogObject, winStyle);
	}else{
		var winStyle = "resizable=1,scrollbars=1,width="+width+",height="+height+",left="+left+",top="+top+",dependent=yes,alwaysRaised=1";
		Com_Parameter.Dialog = dialogObject;
		window.open(url, "_blank", winStyle);
	}
}

function returnDialogValue(){
	if(!FlowChartObject.CheckFlow(true))
		return;
	returnValue = FlowChartObject.BuildFlowXML();
	window.close();
}

function save(){
	if (typeof(window.$dialog) != 'undefined') {
		if(!FlowChartObject.CheckFlow(true))
			return;
		if (parent && parent.lbpm) {
			parent.lbpm.myAddedNodes = Data_MyAddedNodes;
		}
		returnValue = FlowChartObject.BuildFlowXML();
		$dialog.hide(returnValue);
	} else {
		returnDialogValue();
	}
}

function closeDialog(){
	if(!confirm('<bean:message key="message.closeWindow"/>')){
		return;
	}
	if (parent && parent.lbpm) {
		parent.lbpm.myAddedNodes = Data_MyAddedNodes_Init;
	}
	Com_CloseWindow();
}

FlowChart_Bar_Initialize();
</script>
</body>
</html>