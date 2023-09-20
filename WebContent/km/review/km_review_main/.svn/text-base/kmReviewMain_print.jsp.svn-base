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
	<c:out value="${kmReviewMainForm.docSubject }"></c:out>
</template:replace>
<template:replace name="toolbar">
	<ui:toolbar  id="toolbar" layout="sys.ui.toolbar.float" count="9">
			<ui:button  id="zoomIn" text="${ lfn:message('km-review:button.zoom.in') }"   onclick="ZoomFontSize(2);">
		    </ui:button>
		     <ui:button id="zoomOut" text="${ lfn:message('km-review:button.zoom.out') }"   onclick="ZoomFontSize(-2);">
	 	   	</ui:button>
	 	   	 <ui:button text="${ lfn:message('km-review:button.pageBreak') }" id="pageBreakButton">
	  		 </ui:button>
	  		 <ui:button text="${ lfn:message('button.print') }"   onclick="printorder();">
		    </ui:button>
		     <ui:button style="display:none;"  text="${ lfn:message('km-review:button.printPreview') }"   onclick="printView();">
		    </ui:button>
		    <c:if test="${isShowSwitchBtn=='true'}">
		      <ui:button text="${ lfn:message('km-review:button.print.new') }"   onclick="switchPrintPage();">
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
		//表单文本框转PDF时，转不成功？
		var $xforms = $("#_xform_detail input[type='text']:visible").each(function(index,dom){
			$(this).after("<span>"+$(this).val()+"</span>").hide();
		});
		exp.exportPdf($(".lui_form_content_td")[0],{callback:function() {
			$(".qrcodeArea").show();
			$("#toolBarDiv").show();
			$xforms.each(function(index,dom){
				$(this).next().remove();
				$(this).show();
			});
		}});
	});
}
</script>
<script>
Com_IncludeFile("jquery.js|dialog.js|doclist.js");
Com_IncludeFile("previewdesign.js",Com_Parameter.ContextPath+"sys/print/import/","js",true);
Com_IncludeFile("print.js",Com_Parameter.ContextPath+"km/review/resource/js/","js",true);
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
		if("${subject}" != 'subject' ){
			$("#subjectDiv").hide();
		}
		/*#139365-打印时能够打印出来文档的状态标记-开始*/
		if("${statusFlag}" != 'statusFlag' ){
			$("#statusFlagDiv").hide();
		}
		if("${statusFlagShow}" != 'block' ){
			$("#statusFlagDiv").hide();
		}
		/*#139365-打印时能够打印出来文档的状态标记-结束*/
		if("${base}" != 'base' ){
			$("#baseDiv").hide();
		}
		if("${info}" != 'info' ){
			$("#infoDiv").hide();
		}
		if("${note}" != 'note' ){
			$("#noteDiv").hide();
		}
		if("${qrcode}" != 'qrcode' ){
			$("#qrcodexDiv").hide();
		}
	});
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
		alert("<bean:message key="button.printPreview.error" bundle="km-review"/>");
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
				tabs[i].deleteRow(0);
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
	sysPreviewDesign.resetBoxWidth($('.sysDefineXform')[0]);
    resetTableSize();
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
		alert("<bean:message key="button.printPreview.error" bundle="km-review"/>");
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
		padding: 15px 0px 10px 0px;
	    border-left: 3px solid #46b1fc;
	    height: 6px;
	    line-height: 0;
	    margin: 15px 0;
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
	#printTable{width:980px;margin-bottom:20px;margin-top:20px;}

	/*#139365-打印时能够打印出来文档的状态标记-开始*/
	#statusFlagDiv {
		position: absolute;
		z-index: 9;
		width: 100px;
		height: 100px;
		top: 110px;
		right: 200px;
	}
	.lbpm_flowInfoW{
		width: 100px;
		height: 100px;
		position: absolute !important;
		left: 0px !important;
		top: 0px !important;
	}
	/*.print_title_header {
		padding-top: 20px;
		padding-bottom: 10px;
		border-bottom: 1px solid #dbdbdb;
		position: relative !important;*/
		/* width: auto; */
		/* display: inline-block; */
		/* width: 100%; */
		/*width: 980px;*/
	/*}*/
	/*#139365-打印时能够打印出来文档的状态标记-结束*/

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
	.lui_upload_img_box{
		min-height: 80px
	}
	.lui_upload_img_box .imgbox > img {
		max-width: 95% !important;
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
	#_xform_detail .lui_upload_img_box{
		display: block;
		margin-top:24px !important;
	}
	/*145360 打印错位*/
	#printTable table .tb_normal{
		margin-bottom: 10px !important;
	}
}
</style>

<form name="kmReviewMainForm" method="post" action="<c:url value="/km/review/km_review_main/kmReviewMain.do"/>">
<center>
<div id="toolBarDiv" style="padding-top: 12px;max-width:1000px;" data-remove="false">
	 <table class="tb_normal" width=100%>
		<tr>
			<td>
				<label>
				<input  id="subject" type="checkbox" onclick="changeDisplay(this);" <c:if test="${subject eq 'subject'}">checked="checked"</c:if>/>
				 <bean:message bundle="km-review" key="kmReview.config.subject" />
				</label>
			</td>
			<%--#139365-打印时能够打印出来文档的状态标记-开始--%>
			<c:if test="${statusFlagShow eq 'block'}">
				<td>
					<label>
						<input  id="statusFlag" type="checkbox" onclick="changeDisplay(this);"<c:if test="${statusFlag eq 'statusFlag'}">checked="checked"</c:if>/>
						<bean:message bundle="km-review" key="kmReview.config.statusFlag" />
					</label>
				</td>
			</c:if>
			<%--#139365-打印时能够打印出来文档的状态标记-结束--%>
			<td>
				<label>
				<input  id="base" type="checkbox" onclick="changeDisplay(this);" <c:if test="${base eq 'base'}">checked="checked"</c:if>/>
				 <bean:message bundle="km-review" key="kmReview.config.base" />
				</label>
			</td>
			<td>
				<label>
				<input id="info" type="checkbox" onclick="changeDisplay(this);" <c:if test="${info eq 'info'}">checked="checked"</c:if>/>
				<bean:message bundle="km-review" key="kmReview.config.info" />
				</label>
			</td>
			<td>
				<label>
				<input id="note" type="checkbox" onclick="changeDisplay(this);" <c:if test="${note eq 'note'}">checked="checked"</c:if>/>
				<bean:message bundle="km-review" key="kmReview.config.note" />
				</label>
			</td>
			<td>
				<label>
				<input id="qrcodex" type="checkbox" onclick="changeDisplay(this);" <c:if test="${qrcode eq 'qrcode'}">checked="checked"</c:if>/>
				<bean:message bundle="km-review" key="kmReview.config.qrcode" />
				</label>
			</td>
		</tr>
	</table>
</div>
<div class="print_title_header" id="subjectDiv">
	<p id="title" class="print_txttitle"><bean:write name="kmReviewMainForm" property="docSubject" /></p>
	<div class="printDate">
	  <bean:message bundle="km-review" key="kmReviewMain.fdPrintDate" />:<% out.print(DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATE, request.getLocale()));%>
	</div>
</div>
<%--#139365-打印时能够打印出来文档的状态标记-开始--%>
<div id="statusFlagDiv" onmousemove = "divMove()">
	<c:import url="/km/review/km_review_ui/kmReviewMain_banner.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="kmReviewMainForm" />
		<c:param name="approveType" value="${param.approveModel}" />
	</c:import>
</div>
<%--#139365-打印时能够打印出来文档的状态标记-结束--%>
<div id="printTable" style="border: none;">
<%--#165902-打印页面导出HTML的标记 start--%>
<c:if test="${param.isGetHtml == 'true'}">
	<div id="isGetHtml" style="border: none;display: none"></div>
</c:if>
<%--#165902-打印页面导出HTML的标记 end--%>
<div printTr="true" style="border: none;">

<%-- 基本信息 width="650px" --%>
<div id="baseDiv">
    <div class="tr_label_title">
       <div class="title">
      	 <bean:message bundle="km-review" key="kmReviewDocumentLableName.baseInfo" />
       </div>
    </div>
	<table class="tb_normal" width=100%>
		<!--主题-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="km-review" key="kmReviewMain.docSubject" /></td>
			<td colspan=3><bean:write name="kmReviewMainForm"
				property="docSubject" /></td>
		</tr>
		<!--模板名称-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="km-review" key="kmReviewTemplate.fdName" /></td>
			<td colspan=3><bean:write name="kmReviewMainForm"
				property="fdTemplateName" /></td>
		</tr>
		<!--申请人-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="km-review" key="kmReviewMain.docCreatorName" /></td>
			<td width=35%><html:hidden name="kmReviewMainForm"
				property="docCreatorId" /> <bean:write name="kmReviewMainForm"
				property="docCreatorName" /></td>
			<td class="td_normal_title" width=15%><bean:message
				bundle="km-review" key="kmReviewMain.fdNumber" /></td>
			<td width=35%><bean:write name="kmReviewMainForm"
				property="fdNumber" /></td>
		</tr>
		<!--部门-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="km-review" key="kmReviewMain.department" /></td>
			<td><bean:write name="kmReviewMainForm" property="fdDepartmentName"/></td>
			<td class="td_normal_title" width=15%><bean:message
				bundle="km-review" key="kmReviewMain.docCreateTime" /></td>
			<td width=35%><bean:write name="kmReviewMainForm"
				property="docCreateTime" /></td>
		</tr>
		<!--实施反馈人-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="km-review" key="table.kmReviewFeedback" /></td>
			<td colspan=3><bean:write name="kmReviewMainForm"
				property="fdFeedbackNames" /></td>

		</tr>
	</table>
</div>

<%-- 审批内容 --%>

<div id="infoDiv">
    <div class="tr_label_title">
	    <div class="title">
	       <bean:message bundle="km-review" key="kmReviewDocumentLableName.reviewContent" />
	    </div>
    </div>

	<c:if test="${kmReviewMainForm.fdUseForm == 'false'}">
		<table id="info_content" class="tb_normal" width=100% >
			<tr>
				<td colspan="4">
					${kmReviewMainForm.docContent}
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="km-review" key="kmReviewMain.attachment" />
				</td>
				<td colspan=3>
					<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
						<c:param name="fdMulti" value="true" />
						<c:param name="formBeanName" value="kmReviewMainForm" />
						<c:param name="fdKey" value="fdAttachment" />
					</c:import>
				</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${kmReviewMainForm.fdUseForm == 'true' || empty kmReviewMainForm.fdUseForm}">
		<table id="info_content" width=100% >
			<tr>
				<td id="_xform_detail">
					<%-- 表单 --%>
					<c:import url="/sys/xform/include/sysForm_view.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="kmReviewMainForm" />
						<c:param name="fdKey" value="reviewMainDoc" />
						<c:param name="messageKey" value="km-review:kmReviewDocumentLableName.reviewContent" />
						<c:param name="useTab" value="false" />
						<c:param name="isPrint" value="true" />
					</c:import>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
          Com_IncludeFile("printDLMix.js",Com_Parameter.ContextPath+"km/review/resource/js/","js",true);
        </script>
	 </c:if>
</div>
<%-- 审批记录 --%>
<div id="noteDiv">
    <div class="tr_label_title">
	    <div class="title">
	       <bean:message bundle="km-review" key="kmReviewMain.flow.trail" />
	    </div>
    </div>
	<table width=100%>
		<!-- 审批记录 -->
		<tr>
			<td colspan=4>
				<c:import url="/sys/workflow/include/sysWfProcess_log.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmReviewMainForm" />
				</c:import>
			</td>
		</tr>
	</table>
</div>
<%
	String agent = request.getHeader("User-Agent").toLowerCase();
	if(agent.indexOf("msie 8") < 0){
%>
<div id="qrcodexDiv">
	<%@ include file="/km/review/km_review_main/kmReviewMain_printQrCode.jsp"%>
</div>
<%
	}
%>
</div></div>

</center>
</form>
	<%--#139365-打印时能够打印出来文档的状态标记-开始--%>
	<script>
		dragElement(document.getElementById(("statusFlagDiv")));

		function dragElement(elmnt) {
			var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
			elmnt.onmousedown = dragMouseDown;

			function dragMouseDown(e) {
				e = e || window.event;
				pos3 = e.clientX;
				pos4 = e.clientY;
				document.onmouseup = closeDragElement;
				document.onmousemove = elementDrag;
			}

			function elementDrag(e) {
				e = e || window.event;
				pos1 = pos3 - e.clientX;
				pos2 = pos4 - e.clientY;
				pos3 = e.clientX;
				pos4 = e.clientY;
				elmnt.style.top = (elmnt.offsetTop - pos2) + "px";
				elmnt.style.left = (elmnt.offsetLeft - pos1) + "px";
			}

			function closeDragElement() {
				document.onmouseup = null;
				document.onmousemove = null;
			}
		}
	</script>
	<script>
		function divMove(){
			var divLeft  = $("#statusFlagDiv").offset().left - $("#printTable").offset().left;
			var divTop  = $("#statusFlagDiv").offset().top - 100;
			var style = document.createElement('style');
			style.innerHTML = "@media print { #statusFlagDiv {" + "left:" + divLeft + "px !important;" + "top:" + divTop + "px !important;" +"}}";
			window.document.head.appendChild(style);
		}
	</script>
	<script>
		//调整打印图标一开始显示与表格右侧对齐
		$(document).ready(function() {
			var divRight = $(window).width()- $("#printTable").offset().left-$("#printTable").width();
			$("#statusFlagDiv").css("right",divRight + "px");

			//修复一开始没有拖动图标时候打印预览显示图标的定位与页面默认显示不一样的问题
			//即使没有拖动图标也获取图标的默认位置距离设置到打印样式
			var divLeft  = $("#statusFlagDiv").offset().left - $("#printTable").offset().left;
			var divTop  = $("#statusFlagDiv").offset().top - 100;
			var style = document.createElement('style');
			style.innerHTML = "@media print { #statusFlagDiv {" + "left:" + divLeft + "px !important;" + "top:" + divTop + "px !important;" +"}}";
			window.document.head.appendChild(style);
			//#170218
			$("td div.xform_textArea").css("word-break","break-word");
		});
	</script>
	<%--#139365-打印时能够打印出来文档的状态标记-结束--%>
</template:replace>

</template:include>

