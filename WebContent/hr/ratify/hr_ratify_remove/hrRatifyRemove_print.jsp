<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="java.util.Date"%>
<template:include ref="default.print" sidebar="no">
<template:replace name="head">
</template:replace>
<template:replace name="title">
	<c:out value="${hrRatifyRemoveForm.docSubject }"></c:out>
</template:replace>
<template:replace name="toolbar">
	<ui:toolbar  id="toolbar" layout="sys.ui.toolbar.float" count="9"> 
			<ui:button  id="zoomIn" text="${ lfn:message('hr-ratify:button.zoom.in') }"   onclick="ZoomFontSize(2);">
		    </ui:button>
		     <ui:button id="zoomOut" text="${ lfn:message('hr-ratify:button.zoom.out') }"   onclick="ZoomFontSize(-2);">
	 	   	</ui:button>
	 	   	 <ui:button text="${ lfn:message('hr-ratify:button.pageBreak') }" id="pageBreakButton">
	  		 </ui:button>
	  		 <ui:button text="${ lfn:message('button.print') }"   onclick="printorder();">
		    </ui:button>
		     <ui:button style="display:none;"  text="${ lfn:message('hr-ratify:button.printPreview') }"   onclick="printView();">
		    </ui:button>
		    <c:if test="${isShowSwitchBtn=='true'}">
		      <ui:button text="${ lfn:message('hr-ratify:button.print.new') }"   onclick="switchPrintPage();">
		   	 </ui:button>
		    </c:if>
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
		$("#toolBarDiv").hide();
		exp.exportPdf($(".lui_form_content_td")[0],{callback:function() {
			$(".qrcodeArea").show();
			$("#toolBarDiv").show();
		}});
	});
}
</script>
<script>
Com_IncludeFile("jquery.js|dialog.js|doclist.js");
Com_IncludeFile("previewdesign.js",Com_Parameter.ContextPath+"sys/print/import/","js",true);
</script>
<script>

function changeDisplay(obj){
	if(obj.checked){
      $("#"+obj.id+"Div").show();
	}else{
		$("#"+obj.id+"Div").hide();
	}
}
seajs.use(['lui/jquery'], function($) {
	$(document).ready(function(){
		$("#subjectDiv").hide();
		$("#baseDiv").hide();
		$("#infoDiv").hide();
		$("#noteDiv").hide();
	});
});

var toPageBreak = false;
$("#pageBreakButton").click(function(event){
	if (event.stopPropagation()) {
		event.stopPropagation();
	} else {
		event.cancelBubble = true;
	}
	toPageBreak = true;
	document.body.style.cursor = 'pointer';
});

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
	var root = document.getElementById("printTable");
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
}
function SetZoomFontSize(dom, size) {
	if (dom.nodeType == 1 && dom.tagName != 'OBJECT' && dom.tagName != 'SCRIPT' && dom.tagName != 'STYLE' && dom.tagName != 'HTML') {
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
		alert("<bean:message key="button.printPreview.error" bundle="hr-ratify"/>");
	}
}
function expandXformTab(){
	var xformArea = $("#_xform_detail");
	if(xformArea.length>0){
		var tabs = $("#_xform_detail table.tb_label");
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
function resetTableSize(){
	var tables = $(".sysDefineXform table[fd_type='standardTable']");
	for(var i = 0 ;i < tables.length;i++){
		var table = tables[i];
		//表格宽度调整
		$(table).css('width','100%');
		var tbWidth = $(table).width();
		//计算原始宽度
		for (var j = 0; j < table.rows.length; j++) {
			var cells = table.rows[j].cells;
			var cellsCount = cells.length;
			for(var k = 0;k < cellsCount;k++){
				var w = $(cells[k]).width();
				$(cells[k]).attr('cfg-width',w * 980/tbWidth);
			}
		}
		//重置宽度
		for (var j = 0; j < table.rows.length; j++) {
			var cells = table.rows[j].cells;
			var cellsCount = cells.length;
			for(var k = 0;k < cellsCount;k++){
				$(cells[k]).css('width',$(cells[k]).attr('cfg-width'));
			}
		}
	}
}
Com_AddEventListener(window, "load", function() {
	ClearDomWidth(document.getElementById("info_content"));
	expandXformTab();
	//清除链接样式
	$('#_xform_detail a').css('text-decoration','none');
	$('a[id^=thirdCtripXformPlane_]').removeAttr('onclick');
	$('a[id^=thirdCtripXformHotel_]').removeAttr('onclick');
	resetTableSize();
	sysPreviewDesign.resetBoxWidth($('.sysDefineXform')[0]);
});

//打印页面点击手写审批意见需要给window.previewImage传值
if(window.seajs){
	seajs.use(['lui/imageP/preview'], function(preview) {
		window.previewImage = preview;
	});
};
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
	try {
        //PageSetup_temp();//取得默认值  
        //PageSetup_Null();//设置页面  
        //WebBrowser.execwb(6,6);//打印页面  
        window.print();
        //PageSetup_Default();//还原页面设置  
	} catch (e) {
		alert("<bean:message key="button.printPreview.error" bundle="hr-ratify"/>");
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
	#printTable{width:980px;margin-bottom:20px;}
	
	/*--- 打印页面带二维码的标题 ---*/
	.print_title_header{padding-top:20px; padding-bottom: 10px;border-bottom: 1px solid #dbdbdb;}
	.print_txttitle,.print_txttitle#title{ font-size: 18px; color:#333; padding:8px 0; text-align: center;}
	.printDate{ text-align: right; color:#808080;}
	td div.xform_inputText{display:inline-block !important;}
	td div.xform_Calculation{display:inline-block !important;word-break : break-all;word-wrap : break-word;}
	.upload_list_tr .upload_list_operation {display: none;}
	.lui_upload_img_box .upload_opt_td { display: none;}
	.upload_list_tr .upload_list_download_count {display: none;}
@media print {
	#toolBarDiv{
		display: none !important;
	}
	.new_page {
		page-break-before : always;
	}
	.page_line {
		display: none;
	}
	
	#printTable .tb_noborder,
	#printTable table .tb_noborder,
	#printTable .tb_noborder td {
		border: none;
	}
	#printTable .tr_label_title {
		/*font-weight: 900;*/
	}
	#printTable{width:100%;margin-bottom:0px;}
	
	/*- 打印头部 标题 -*/
	.print_title_header{border-bottom: 1px solid #000}
	.print_txttitle,.print_txttitle#title{ font-size: 20px; font-weight: normal; color:#000;}
	.printDate{color:#000;}
}
</style>

<form name="hrRatifyRemoveForm" method="post" action="<c:url value="/hr/ratify/hr_ratify_remove/hrRatifyRemove.do"/>">
<center>
<div id="toolBarDiv" style="padding-top: 12px;max-width:1000px;" data-remove="false">
	 <table class="tb_normal" width=100%>
		<tr>
			<td>
				<label>
				<input  id="subject" type="checkbox" onclick="changeDisplay(this);" <c:if test="${subject eq 'subject'}">checked="checked"</c:if>/>
				 <bean:message bundle="hr-ratify" key="hrRatify.subject" />
				</label>
			</td>
			<td>
				<label>
				<input  id="base" type="checkbox" onclick="changeDisplay(this);" <c:if test="${base eq 'base'}">checked="checked"</c:if>/>
				 <bean:message bundle="hr-ratify" key="py.JiBenXinXi" />
				</label>
			</td>
			<td>
				<label>
				<input id="info" type="checkbox" onclick="changeDisplay(this);" <c:if test="${info eq 'info'}">checked="checked"</c:if>/>
				<bean:message bundle="hr-ratify" key="py.BiaoDanNeiRong" />
				</label>
			</td>
			<td>
				<label>
				<input id="note" type="checkbox" onclick="changeDisplay(this);" <c:if test="${note eq 'note'}">checked="checked"</c:if>/>
				<bean:message bundle="hr-ratify" key="hrRatify.note" />
				</label>
			</td>
		</tr>
	</table>
</div>
<div class="print_title_header" id="subjectDiv">
	<p id="title" class="print_txttitle"><bean:write name="hrRatifyRemoveForm" property="docSubject" /></p>
	<div class="printDate">
	  <bean:message bundle="hr-ratify" key="hrRatify.fdPrintDate" />:<% out.print(DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATE, request.getLocale()));%>
	</div>
</div>
<div id="printTable" style="border: none;">
<div printTr="true" style="border: none;">

<%-- 基本信息 width="650px" --%>
<div id="baseDiv">
    <div class="tr_label_title"> 
       <div class="title">
      	 <bean:message bundle="hr-ratify" key="py.JiBenXinXi" />
       </div>
    </div>
	<table class="tb_normal" width=100%>
		<!--主题-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="hr-ratify" key="hrRatifyMain.docSubject" /></td>
			<td colspan=3><bean:write name="hrRatifyRemoveForm"
				property="docSubject" /></td>
		</tr>
		<!--模板名称-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="hr-ratify" key="hrRatifyMain.docTemplate" /></td>
			<td colspan=3><bean:write name="hrRatifyRemoveForm"
				property="docTemplateName" /></td>
		</tr>
		<!--申请人-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="hr-ratify" key="hrRatifyMain.docCreator" /></td>
			<td width=35%><html:hidden name="hrRatifyRemoveForm"
				property="docCreatorId" /> <bean:write name="hrRatifyRemoveForm"
				property="docCreatorName" /></td>
			<td class="td_normal_title" width=15%><bean:message
				bundle="hr-ratify" key="hrRatifyMain.docNumber" /></td>
			<td width=35%><bean:write name="hrRatifyRemoveForm"
				property="docNumber" /></td>
		</tr>
		<!--部门-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="hr-ratify" key="hrRatifyMain.fdDepartment" /></td>
			<td><bean:write name="hrRatifyRemoveForm" property="fdDepartmentName"/></td>
			<td class="td_normal_title" width=15%><bean:message
				bundle="hr-ratify" key="hrRatifyMain.docCreateTime" /></td>
			<td width=35%><bean:write name="hrRatifyRemoveForm"
				property="docCreateTime" /></td>
		</tr>
		<!--实施反馈人-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="hr-ratify" key="hrRatifyMain.fdFeedback" /></td>
			<td colspan=3><bean:write name="hrRatifyRemoveForm"
				property="fdFeedbackNames" /></td>
	
		</tr>
	</table>
</div>

<%-- 审批内容 --%>

<div id="infoDiv">
    <div class="tr_label_title">
	    <div class="title">
	       <bean:message bundle="hr-ratify" key="py.BiaoDanNeiRong" />
	    </div>
    </div>
	
	<c:if test="${hrRatifyRemoveForm.docUseXform == 'false'}">
		<table id="info_content" class="tb_normal" width=100% >
			<tr>
				<td colspan="4">
					${hrRatifyRemoveForm.docContent}
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="hr-ratify" key="hrRatify.attachment" />
				</td>
				<td colspan=3>
					<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
						<c:param name="fdMulti" value="true" />
						<c:param name="formBeanName" value="hrRatifyRemoveForm" />
						<c:param name="fdKey" value="fdAttachment" />
					</c:import>
				</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${hrRatifyRemoveForm.docUseXform == 'true' || empty hrRatifyRemoveForm.docUseXform}">
		<table id="info_content" width=100% >
			<tr>
				<td id="_xform_detail">
					<%-- 表单 --%>
					<c:import url="/sys/xform/include/sysForm_view.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="hrRatifyRemoveForm" />
						<c:param name="fdKey" value="hrRatifyMain" />
						<c:param name="messageKey" value="hr-ratify:py.BiaoDanNeiRong" />
						<c:param name="useTab" value="false" />
						<c:param name="isPrint" value="true" />
					</c:import>
				</td>
			</tr>
		</table>
	 </c:if>
</div>
<%-- 审批记录 --%>
<div id="noteDiv">
    <div class="tr_label_title">
	    <div class="title">
	       <bean:message bundle="hr-ratify" key="hrRatify.note" />
	    </div>
    </div>
	<table width=100%>
		<!-- 审批记录 -->
		<tr>
			<td colspan=4>
				<c:import url="/sys/workflow/include/sysWfProcess_log.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="hrRatifyRemoveForm" />
				</c:import>
			</td>
		</tr>
	</table>
</div>
<%
	String agent = request.getHeader("User-Agent").toLowerCase();
	if(agent.indexOf("msie 8") < 0){
%>

<%
	}
%>
</div></div>

</center>
</form>
</template:replace>
		
</template:include>

