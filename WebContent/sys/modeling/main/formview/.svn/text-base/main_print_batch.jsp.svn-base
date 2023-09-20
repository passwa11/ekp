<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="java.util.Date"%>
<template:include ref="default.print" sidebar="no">
<template:replace name="head">
	<style>
		.printTableCtrl{
			width:980px!important;
		}
	</style>
</template:replace>
<template:replace name="title">
	${ lfn:message('sys-modeling-main:main.print.batch.title') }
</template:replace>
<template:replace name="toolbar">
	<ui:toolbar  id="toolbar" layout="sys.ui.toolbar.float" count="9"> 
			<ui:button  id="zoomIn" text="${ lfn:message('sys-modeling-main:button.zoom.in') }"   onclick="ZoomFontSize(2);">
		    </ui:button>
		     <ui:button id="zoomOut" text="${ lfn:message('sys-modeling-main:button.zoom.out') }"   onclick="ZoomFontSize(-2);">
	 	   	</ui:button>
	 	   	 <ui:button text="${ lfn:message('sys-modeling-main:button.pageBreak') }"  onclick="ShowToPageBreak(event);">
	  		 </ui:button>
	  		 <ui:button text="${ lfn:message('button.print') }"   onclick="printorder();">
		    </ui:button>
		     <ui:button style="display:none;"  text="${ lfn:message('sys-modeling-main:button.printPreview') }"   onclick="printView();">
		    </ui:button>
		    <c:if test="${fn:length(mainFormList) >0 }">
		     <ui:button text="${ lfn:message('sys-modeling-main:button.printConfig') }"   onclick="ShowPrintList();">
		    </ui:button>
		    </c:if>
		    <%--
		    <c:if test="${isShowSwitchBtn=='true'}">
		      <ui:button text="${ lfn:message('sys-modeling-main:button.print.new') }"   onclick="switchPrintPage();">
		   	 </ui:button>
		    </c:if>
		     --%>
		    <c:import url="/sys/common/exportButton.jsp" charEncoding="UTF-8"></c:import>
		      <ui:button text="${ lfn:message('button.close') }"   onclick="window.close();">
		    </ui:button>
	</ui:toolbar>
</template:replace>
<template:replace name="content">
<script language="JavaScript">
seajs.use(['theme!form']);
function outputPDF() {
	seajs.use(['lui/jquery','lui/export/export'],function($,exp) {
		$(".qrcodeArea").hide();
		exp.exportPdf($("#print_content")[0],{callback:function() {
			$(".qrcodeArea").show();
		}});
	});
}
</script>
<c:set var="p_defconfig" value="${p_defconfig}" scope="request"/>
<%
String subject = (String)request.getAttribute("subject");
String info = (String)request.getAttribute("info");
String note = (String)request.getAttribute("note");
String qrcode = (String)request.getAttribute("qrcode");
String p_config=request.getParameter("p_config");
java.util.ArrayList showConfig = new java.util.ArrayList(4);
java.util.ArrayList leftConfig = new java.util.ArrayList(4);
	if(!"false".equals(subject))
		leftConfig.add("subject");
	if(!"false".equals(info))
		leftConfig.add("info");
	if(!"false".equals(note))
		leftConfig.add("note");
	if(!"false".equals(qrcode))
		leftConfig.add("qrcode");

if (p_config == null || p_config.length() == 0 ) {
	if(!"false".equals(subject))
		showConfig.add("subject");
	if(!"false".equals(info))
		showConfig.add("info");
	if(!"false".equals(note))
		showConfig.add("note");
	if(!"false".equals(qrcode))
		showConfig.add("qrcode");
} else {
	String[] configs = p_config.split(";");
	for (int i = 0; i < configs.length; i ++) {
		String cfg = configs[i];
		if (cfg != null && cfg.length() != 0 ) {
			showConfig.add(cfg);
		}
	}
}
String defValue = "";
for (int i = 0; i < showConfig.size(); i ++) {
	defValue += ",'" + showConfig.get(i) + "'";
}
defValue = defValue.substring(1);
%>
<script>
Com_IncludeFile("jquery.js|dialog.js|doclist.js");
</script>
<script>
var defValue = [<%=defValue%>];
var defOptions = [];
<% 
for (int i = 0; i < leftConfig.size(); i ++) {
	String cfg = (String) leftConfig.get(i);
	if ("subject".equals(cfg)) { %>
	defOptions.push({id:'subject', name:'<bean:message bundle="sys-modeling-main" key="mainDocumentLableName.subject" />'});
<%
	}else if("info".equals(cfg)){
%>
	defOptions.push({id:'info', name:'<bean:message bundle="sys-modeling-main" key="mainDocumentLableName.reviewContent" />'});
<%
	}else if("note".equals(cfg) && "true".equals(request.getAttribute("hasFlow"))){
%>
	defOptions.push({id:'note', name:'<bean:message bundle="sys-modeling-main" key="main.flow.trail" />'});
<%
	}else if("qrcode".equals(cfg)){
%>
	defOptions.push({id:'qrcode', name:'<bean:message bundle="sys-modeling-main" key="print.config.qrcode" />'});
<%
	}
}
%>
/* <c:if test="${hasFlow eq 'true'}">
var defOptions = [{id:'subject', name:'<bean:message bundle="sys-modeling-main" key="mainDocumentLableName.subject" />'}, 
    {id:'info', name:'<bean:message bundle="sys-modeling-main" key="mainDocumentLableName.reviewContent" />'}, 
    {id:'note', name:'<bean:message bundle="sys-modeling-main" key="main.flow.trail" />'},
    {id:'qrcode', name:'<bean:message bundle="sys-modeling-main" key="print.config.qrcode" />'}];
</c:if>
<c:if test="${hasFlow ne 'true'}">
var defOptions = [{id:'subject', name:'<bean:message bundle="sys-modeling-main" key="mainDocumentLableName.subject" />'}, 
    {id:'info', name:'<bean:message bundle="sys-modeling-main" key="mainDocumentLableName.reviewContent" />'},
    {id:'qrcode', name:'<bean:message bundle="sys-modeling-main" key="print.config.qrcode" />'}];
</c:if> */
var flag = false;
function ShowPrintList() {
	var optionData = new KMSSData();
	optionData.AddHashMapArray(defOptions);
	var valueData = new KMSSData();
	for (var i = 0; i < defValue.length; i ++) {
		var defV = defValue[i];
		for (var j = 0; j < defOptions.length; j ++) {
			var opt = defOptions[j];
			if (opt.id == defV) {
				valueData.AddHashMap(opt);
			}
		}
	}

	var dialog = new KMSSDialog(true, true);
	dialog.AddDefaultOption(optionData);
	dialog.AddDefaultValue(valueData);
	dialog.SetAfterShow(function(rtn) {
		if (rtn == null || rtn.IsEmpty()) {
			return ;
		}
		var value = '';
		var values = rtn.GetHashMapArray();
		
		for (var i = 0; i < values.length; i ++) {
			value += ';' + values[i].id;
		}
		var url = Com_SetUrlParameter(window.location.href, 'p_config', value);
		setTimeout(function(){window.location.href = url;},500);
	});
	dialog.Show();
}
var toPageBreak = false;
function ShowToPageBreak(event) {
	window.clearInterval(timerH);
	event.cancelBubble = true;
	toPageBreak = true;
	document.body.style.cursor = 'pointer';
}

function AbsPosition(node, stopNode) {
	var x = y = 0;
	for (var pNode = node; pNode != null && pNode !== stopNode; pNode = pNode.offsetParent) {
		x += pNode.offsetLeft - pNode.scrollLeft; y += pNode.offsetTop - pNode.scrollTop;
	}
	x = x + document.body.scrollLeft;
	y = y + document.body.scrollTop;
	return {'x':x, 'y':y};
};
function MousePosition(event) {
	if(event.pageX || event.pageY) return {x:event.pageX, y:event.pageY};
	return {
		x:event.clientX + document.body.scrollLeft - document.body.clientLeft,
		y:event.clientY + document.body.scrollTop  - document.body.clientTop
	};
};
function SetPageBreakLine(tr) {
	var pos = AbsPosition(tr);
	var line = document.createElement('div');
	line.className = 'page_line';
	line.style.top = pos.y + 'px';
	line.style.left = '0px';
	line.id = 'line_' + tr.uniqueID;
	document.body.appendChild(line);
}
function RemovePageBreakLine(tr) {
	var line = document.getElementById('line_' + tr.uniqueID);
	if (line != null)
		document.body.removeChild(line);
	line = null;
}

function createPageBreakLine(){
	var printHeaders = $("div.print_title_header");
	if(printHeaders.length > 1){
		printHeaders.each(function(index,element){
			if(index > 0){
				SetPageBreakLine(element);
				$(element).addClass('new_page');
			}
			if(index == printHeaders.length - 1){
				flag = true;
			}
		});
	}else{
		flag = true;
	}
}

Com_AddEventListener(document, "click", function(event) {
	if (toPageBreak) {
		event = event || window.event;
		toPageBreak = false;
		document.body.style.cursor = 'default';
		var target = event.target || event.srcElement;
		while(target) {
			if (target.tagName != null && (target.tagName == 'TR' ||target.tagName=="DIV")) {
				if (target.printTr == 'true') {
					var pos = MousePosition(event);
					var tables = target.getElementsByTagName('table');
					var mtable = null, msize = 0, m = 0;
					for (var n = 0; n < tables.length; n ++) {
						var tb = tables[n];
						var tbp = AbsPosition(tb);
						if (mtable == null) {
							mtable = tb;
							msize = Math.abs(pos.y - tbp.y);
							continue;
						}
						m = Math.abs(pos.y - tbp.y);
						if (m < msize) {
							msize = m;
							mtable = tb;
						}
					}
					if (mtable == null)
						break;
					target = mtable.rows[0];
				}
				if (target.tagName=='TR' && target.rowIndex == 0) {
					target = target.parentNode.parentNode;
				}
				if (target.className.indexOf('new_page') > -1) {
					RemovePageBreakLine(target);
					target.className = target.className.replace(/new_page/g, '');
				} else if(target.className.indexOf("page_line")==-1) {
					SetPageBreakLine(target);
					target.className = 'new_page ' + target.className;
				}
				break;
			} else {
				target = target.parentNode;
			}
		}
	}
});
var flag = 0;
function ZoomFontSize(size) {
	//当字体缩小到一定程度时，缩小字体按钮变灰不可点击
	if(flag>=0||flag==-2){
		flag = flag+size;
	}
	if(flag<0){
		$("#zoomOut").prop("disabled",true);
		$("#zoomOut").addClass("status_disabled");
	}else{
		$("#zoomOut").prop("disabled",false);
		$("#zoomOut").removeClass("status_disabled");
	} 
	
	$(".printTableCtrl").each(function(index,element){
		var root = element;
		var iframe = $(root).children().find("iframe")[0];
		if (iframe) {
			var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
			if (iframeWin.document.body) {
				root = iframeWin.document.body;
			}
		}
		var i = 0;
		for (i = 0; i < root.childNodes.length; i++) {
			SetZoomFontSize(root.childNodes[i], size);
		}
		var tag_fontsize;
		if(root.currentStyle){
		    tag_fontsize = root.currentStyle.fontSize;  
		}  
		else{  
		    tag_fontsize = getComputedStyle(root, null).fontSize;  
		} 
		root.style.fontSize = parseInt(tag_fontsize) + size + 'px';
		//重新调整iframe高度
		if (iframe) {
			var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
			if (iframeWin.document.body) {
				iframe.height = iframeWin.document.documentElement.offsetHeight || iframeWin.document.body.offsetHeight;
			}
		}
	});
	setTimeout(function (){
		// 先移除分页线
		$("div.page_line").remove();
		// 重新绘制分页线
		createPageBreakLine();
	}, 1000);
	
	_ZoomFontSize(size);
}

function _ZoomFontSize(size) {
	$(".printTableCtrl").each(function(index,element){
		var root = element;
		var i = 0;
		for (i = 0; i < root.childNodes.length; i++) {
			SetZoomFontSize(root.childNodes[i], size);
		}
		var tag_fontsize;
		if(root.currentStyle){
		    tag_fontsize = root.currentStyle.fontSize;  
		}  
		else{  
		    tag_fontsize = getComputedStyle(root, null).fontSize;  
		} 
		root.style.fontSize = parseInt(tag_fontsize) + size + 'px';
	});
}

function SetZoomFontSize(dom, size) {
	if (dom.nodeType == 1 && dom.tagName != 'LINK' && dom.tagName != 'OBJECT' && dom.tagName != 'SCRIPT' && dom.tagName != 'STYLE' && dom.tagName != 'HTML') {
		for (var i = 0; i < dom.childNodes.length; i ++) {
			SetZoomFontSize(dom.childNodes[i], size);
		}
		var tag_fontsize;
		if(dom.currentStyle){  
		    tag_fontsize = dom.currentStyle.fontSize;  
		}  
		else{  
		    tag_fontsize = getComputedStyle(dom, null).fontSize;  
		} 
		dom.style.fontSize = parseInt(tag_fontsize) + size + 'px';
	}
}
function ClearDomWidth(dom) {
	if (dom != null && dom.nodeType == 1 && dom.tagName != 'OBJECT' && dom.tagName != 'SCRIPT' && dom.tagName != 'STYLE' && dom.tagName != 'HTML') {
		//修改打印布局为 百分比布局 #曹映辉 2014.8.7
			/****
			var w = dom.getAttribute("width");
			if (w != '100%')
				dom.removeAttribute("width");
			w = dom.style.width;
			if (w != '100%')
				dom.style.removeAttribute("width");
			****/
			if (dom.style.whiteSpace == 'nowrap') {
				dom.style.whiteSpace = 'normal';
			}
			if (dom.style.display == 'inline') {
				dom.style.wordBreak  = 'break-all';
				dom.style.wordWrap  = 'break-word';
			}
		ClearDomsWidth(dom);
	}
}
function ClearDomsWidth(root) {
	for (var i = 0; i < root.childNodes.length; i ++) {
		ClearDomWidth(root.childNodes[i]);
	}
}

function printView() {
	try {
		//PageSetup_temp();
		//PageSetup_Null();
		document.getElementById('WebBrowser').ExecWB(7,1);
	    //PageSetup_Default();
	} catch (e) {
		alert("<bean:message key="button.printPreview.error" bundle="sys-modeling-main"/>");
	}
}

function expandXformTab(){
	var xformArea = $("td[name='_xform_detail']");
	if(xformArea.length>0){
		var tabs = $("td[name='_xform_detail'] table.tb_label");
		if(tabs.length>0){
			for(var i=0; i<tabs.length; i++){
				var id = $(tabs[i]).prop("id");
				if(id==null || id=='') continue;
				$(tabs[i]).toggleClass("tb_normal");
				tabs[i].deleteRow(0);
				var tmpFun = function(idx,trObj){
					var trObj = $(trObj);
					//trObj.children("td").css({"padding":"0px","margin":"0px"});
					var tmpTitleTr = $("<tr class='tr_normal_title'>");
					var tempTd = $('<td align="left">');
					tempTd.html(trObj.attr("LKS_LabelName"));
					tempTd.appendTo(tmpTitleTr);
					trObj.before(tmpTitleTr);
				};
				var trArr = $("#"+id+" >tbody > tr[LKS_LabelName]");
				if(trArr.length<1){
					trArr = $("#"+id+" > tr[LKS_LabelName]");
				}
				trArr.each(tmpFun).show();
			}
		}
	}
}
Com_AddEventListener(window, "load", function() {
	$("table[name='info_content']").each(function(index,element){
		ClearDomWidth(element);
	});
	expandXformTab();
	//清除链接样式
	$("td[name='_xform_detail'] a").css('text-decoration','none');
	$('a[id^=thirdCtripXformPlane_]').removeAttr('onclick');
	$('a[id^=thirdCtripXformHotel_]').removeAttr('onclick');
	//添加分页符
	doPageBreakLineLayout();
});

function doPageBreakLineLayout(){
	//等工具栏加载完成后再绘制分页线
	var marginTop = $(".lui_print_body").css("margin-top");
	if (marginTop != "0px") {
		setTimeout('createPageBreakLine()',1500);
		return;
	}
	setTimeout(function() {
		doPageBreakLineLayout();
	}, 20);
}
</script>
<SCRIPT language=javascript>  
var HKEY_Root,HKEY_Path,HKEY_Key;   
HKEY_Root="HKEY_CURRENT_USER";   
HKEY_Path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";   
var head,foot,top,bottom,left,right;  
  
//取得页面打印设置的原参数数据  
function PageSetup_temp() {    
  var Wsh=new ActiveXObject("WScript.Shell");   
  HKEY_Key="header";   
//取得页眉默认值  
  head = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
  HKEY_Key="footer";   
//取得页脚默认值
  foot = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
  HKEY_Key="margin_bottom";   
//取得下页边距  
  bottom = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
  HKEY_Key="margin_left";   
//取得左页边距  
  left = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
  HKEY_Key="margin_right";   
//取得右页边距  
  right = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
  HKEY_Key="margin_top";   
//取得上页边距  
  top = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
  
}  
  
//设置网页打印的页眉页脚和页边距  
function PageSetup_Null()   
{     
  var Wsh=new ActiveXObject("WScript.Shell");   
  HKEY_Key="header";   
//设置页眉（为空）  
  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");   
  HKEY_Key="footer";   
//设置页脚（为空）  
  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");   
  HKEY_Key="margin_bottom";   
//设置下页边距（0）  
  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0");   
  HKEY_Key="margin_left";   
//设置左页边距（0）  
  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0");   
  HKEY_Key="margin_right";   
//设置右页边距（0）  
  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0");   
  HKEY_Key="margin_top";   
//设置上页边距（8）  
  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0");   

}   
//设置网页打印的页眉页脚和页边距为默认值   
function  PageSetup_Default()   
{     

  var Wsh=new ActiveXObject("WScript.Shell");   
  HKEY_Key="header";   
  HKEY_Key="header";   
//还原页眉  
  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,head);   
  HKEY_Key="footer";   
//还原页脚  
  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,foot);   
  HKEY_Key="margin_bottom";   
//还原下页边距  
  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,bottom);   
  HKEY_Key="margin_left";   
//还原左页边距  
  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,left);   
  HKEY_Key="margin_right";   
//还原右页边距  
  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,right);   
  HKEY_Key="margin_top";   
//还原上页边距  
  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,top);    
}

function printorder()
{
	if (flag){
		try {
			$(".printTableCtrl").find("iframe").each(function (index, element) {
				var iframe = element;
				//重新调整iframe高度
				if (iframe) {
					var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
					if (iframeWin.document.body) {
						iframe.height = iframeWin.document.documentElement.offsetHeight || iframeWin.document.body.offsetHeight;
					}
				}
			});
			//PageSetup_temp();//取得默认值
			//PageSetup_Null();//设置页面
			//WebBrowser.execwb(6,6);//打印页面
			window.print();
			//PageSetup_Default();//还原页面设置
		}
		catch
				(e)
		{
			alert("<bean:message key="button.printPreview.error" bundle="sys-modeling-main"/>");
		}
	}else {
		alert("${lfn:message('sys-modeling-main:modeling.page.not.loaded.completely') }")
	}
	//factory.execwb(6,6);
	// window.close();
}
function switchPrintPage(){
	var href = window.location.href;
	if(href.indexOf('&_ptype=') > -1){
		href = href.replace('&_ptype=old','').replace('&_ptype=new','');
	}
	if(href.indexOf('?') > -1){
		href = href+"&_ptype=new";
	}else{
		href=href+'?_ptype=new';
	}
	window.location.href=href;
}

//调整iframe高度
var timerH;
function setIframeHeight(iframe) {
	timerH = self.setInterval(function (){
		if (iframe) {
			var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
			if (iframeWin.document.body) {
				// 先移除分页线
				$("div.page_line").remove();
				var height = iframeWin.document.documentElement.offsetHeight || iframeWin.document.body.offsetHeight;
				iframe.height = height + 120;
				// 重新绘制分页线
				createPageBreakLine();
			}
		} 
	}, 100);
}

</script>
<style type="text/css">
	#title {
		font-size: 22px;
		color: #000;
	}
	.tr_label_title{
		margin: 28px 0px 10px 0px;
		border-left: 3px solid #46b1fc
	}

	.tr_label_title .title{
		font-weight: 900;
		font-size: 16px;
		color: #000;
		text-align:left;
		margin-left: 8px;
	}
	.page_line {
		background-color: red;
		height: 1px;
		border: none;
		width: 100%;
		position: absolute;
		overflow: hidden;
	}
	a:hover{color:#333;text-decoration: none;}
	.printTableCtrl{width:720px;margin-bottom:20px;}

	/*--- 打印页面带二维码的标题 ---*/
	.print_title_header{ padding-bottom: 10px;border-bottom: 1px solid #dbdbdb;}
	.print_txttitle,.print_txttitle#title{ font-size: 18px; color:#333; padding:8px 0; text-align: center;}
	.printDate{ text-align: right; color:#808080;}


@media print {
	.new_page {
		page-break-before : always;
	}
	.page_line {
		display: none;
	}

	.printTableCtrl .tb_noborder,
	.printTableCtrl table .tb_noborder,
	.printTableCtrl .tb_noborder td {
		border: none;
	}
	.printTableCtrl .tr_label_title {
		/*font-weight: 900;*/
	}
	.printTableCtrl{width:100%;margin-bottom:0px;}

	/*- 打印头部 标题 -*/
	.print_title_header{border-bottom: 1px solid #000}
	.print_txttitle,.print_txttitle#title{ font-size: 20px; font-weight: normal; color:#000;}
	.printDate{color:#000;}
	.batchPrintTip{display:none;}
}
</style>

<%-- ie10引起崩溃
	<OBJECT ID='WebBrowser' NAME="WebBrowser" WIDTH=0 HEIGHT=0 CLASSID='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2'></OBJECT>
 --%>
<form name="${formName }Temp" method="post" action="<c:url value="/${actionUrl }"/>">
<center id="print_content">

<c:forEach items="${mainFormList }" var="modelMainFormTemp" varStatus="vstatus">
<kmss:auth requestURL="/${actionUrl }?method=view&fdId=${modelMainFormTemp.fdId}" requestMethod="GET">
<c:set var="modelMainForm" value="${modelMainFormTemp }" scope="request" />
<div class="print_title_header">
<%
for (int i = 0; i < showConfig.size(); i ++) {
	String cfg = (String) showConfig.get(i);
	if("subject".equals(cfg)){
%>
<p id="title" class="print_txttitle"><bean:write name="modelMainForm" property="docSubject" /></p>
<%}} %>
<div class="printDate">
  <bean:message bundle="sys-modeling-main" key="main.fdPrintDate" />:<% out.print(DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATE, request.getLocale()));%>
</div>
</div>
<div class="printTableCtrl" style="border: none;">
<div printTr="true" style="border: none;">

<%-- 基本信息 width="650px" --%>
<% 
for (int i = 0; i < showConfig.size(); i ++) {
	String cfg = (String) showConfig.get(i);
	if ("info".equals(cfg)) { %>

<div>
    <div class="tr_label_title">
	    <div class="title">
	       <bean:message bundle="sys-modeling-main" key="mainDocumentLableName.reviewContent" />
	    </div>
    </div>
	<table name="info_content" width=100% >
		<tr>
			<td name="_xform_detail">
				<ui:iframe cfg-dyniframe="false" src="${KMSS_Parameter_ContextPath}${actionUrl }?method=printBatch&fdId=${modelMainFormTemp.fdId}&getXForm=1"></ui:iframe>
			</td>
		</tr>
	</table>
</div>
<%-- 审批记录 --%>
<% } else if ("note".equals(cfg)) { %>
<c:if test="${hasFlow eq 'true' }">
	<div>
	    <div class="tr_label_title">
		    <div class="title">
		       <bean:message bundle="sys-modeling-main" key="main.flow.trail" />
		    </div>
	    </div>
		<table width=100%>
			<!-- 审批记录 -->
			<tr>
				<td colspan=4>
					<c:import url="/sys/workflow/include/sysWfProcess_log.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="modelMainForm" />
					</c:import>
				</td>
			</tr>
		</table>
	</div>
</c:if>
<%
} else if("qrcode".equals(cfg)){
%>
<%
	String agent = request.getHeader("User-Agent").toLowerCase();
	if(agent.indexOf("msie 8") < 0){
%>
<div id="">
	<c:import url="/sys/modeling/main/formview/model_printQrCode_batch.jsp">
		<c:param name="mainId" value="${modelMainFormTemp.fdId }"></c:param>
		<c:param name="modelViewUrl" value="sys/modeling/main/modelingAppModelMain.do?method=view&fdId=${modelMainFormTemp.fdId}"></c:param>
	</c:import>
</div>
<%
	}
%>
<%
}//end if
} // end for
%>
</div></div>
<% 
//清除自定义表单内容
request.removeAttribute("com.landray.kmss.web.taglib.FormBean");
//清除自定义表单标签
request.removeAttribute("com.landray.kmss.sys.xform.MulitiLang");
//清除表单信息
request.removeAttribute("modelMainForm"); 
%> 
</kmss:auth>
</c:forEach>

<!-- HTML自定义模板批量打印 -->
<c:forEach items="${mainFormListCus }" var="modelMainFormTemp" varStatus="vstatus">
<kmss:auth requestURL="/${actionUrl }?method=view&fdId=${modelMainFormTemp.fdId}" requestMethod="GET">
<c:set var="modelMainForm" value="${modelMainFormTemp }" scope="request" />
<div class="print_title_header">
	<p id="title" class="print_txttitle"><bean:write name="modelMainForm" property="docSubject" /></p>
	<div class="printDate">
	  <bean:message bundle="sys-modeling-main" key="main.fdPrintDate" />:<% out.print(DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATE, request.getLocale()));%>
	</div>
</div>
<div class="printTableCtrl" style="border: none;">
	<div printTr="true"  style="border: none;">
		<iframe 
			id="iframe${vstatus.index }"
			frameborder="no" 
			border="0" 
			scrolling="no"
			onload="setIframeHeight(this)"
			class="lui_widget_iframe"
			src="${KMSS_Parameter_ContextPath}${actionUrl }?method=cusprint&fdId=${modelMainFormTemp.fdId}&s_css=default">
		</iframe>
	</div>
</div>
<% 
//清除自定义表单内容
request.removeAttribute("com.landray.kmss.web.taglib.FormBean");
//清除自定义表单标签
request.removeAttribute("com.landray.kmss.sys.xform.MulitiLang");
//清除表单信息
request.removeAttribute("mainFormListCus"); 
%> 
</kmss:auth>
</c:forEach>

<!-- Word自定义模板批量打印 -->



</center>


<!-- 不支持批量打印的模板 -->
<c:if test="${fn:length(printSubList) >0 }">
	<div class="batchPrintTip">
		<div><span style="font-weight: 900;font-size: 16px; color: #FF6000;text-align: left;"><bean:message bundle="sys-modeling-main" key="main.printBatch.subject.notSurport" /></span></div>
		<span>
			<c:forEach var="item" items="${printSubList }" varStatus="vstatus">
				${item }</br>
			</c:forEach>
		</span>
	</div>
</c:if>
</form>
</template:replace>
		
</template:include>

