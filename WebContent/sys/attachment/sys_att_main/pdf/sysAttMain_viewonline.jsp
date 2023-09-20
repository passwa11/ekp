<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<c:set var="fdAttMainId" value="${sysAttMainForm.fdId}" scope="request"></c:set>
<%@ include file="/sys/attachment/sys_att_main/pdf/sysAttMain_CheckSupport.jsp"%>
<script>
	Com_IncludeFile("jquery.js");
</script>
<script>
	if(typeof Com_Parameter.__sysAttMainlocale__ == "undefined")
		Com_Parameter.__sysAttMainlocale__= "<%= UserUtil.getKMSSUser(request).getLocale().toString().toLowerCase().replace('_', '-') %>";
</script>
<script>Com_IncludeFile("jg_pdfviewer.js","${KMSS_Parameter_ContextPath}sys/attachment/js/","js",true);</script>
<c:if test="${empty HtmlParam.inner }">
	<div id="optBarDiv">
		<input type="button" value="<bean:message key="button.close"/>"
			onclick="Com_Parameter.CloseInfo=null;Com_CloseWindow();">
	</div>
<%-- 全屏显示按钮 --%>
<div id="fullSizeBtn" style="display:none;">
	<input type="button"
			value="<bean:message bundle="sys-attachment" key="JG.tools.fullsize"/>"
			onclick="JG_PDFObject.fullSize();">
</div>
</c:if>
<c:set var="canPrint" value="" />
<kmss:auth
    requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=${sysAttMainForm.fdId}"
    requestMethod="GET">
    <c:set var="canPrint" value="1" />
</kmss:auth>
<c:if test="${canPrint!='1'}">
  <style>
    @media print { 
      #sysAttMain_pdf_table { display:none; } 
    } 
</style>
</c:if>
<c:set var="attHeight" value="100%"></c:set>
<c:if test="${not empty HtmlParam.attHeight}">
<c:set var="attHeight" value="${HtmlParam.attHeight}"></c:set>
</c:if>

<table class="tb_normal" width=100% height="100%" style="margin-top: -10px;" id="sysAttMain_pdf_table">
		<tr>
		<td>
		<c:import url="/sys/attachment/sys_att_main/pdf/sysAttMain_OCX.jsp" charEncoding="UTF-8">
			<c:param name="fdKey" value="${sysAttMainForm.fdKey}" />
			<c:param name="fdAttType" value="pdf" />
			<c:param name="fdModelId" value="${sysAttMainForm.fdModelId}" />
			<c:param name="fdModelName" value="${sysAttMainForm.fdModelName}" />
			<c:param name="editMode" value="3" />		
			<c:param name="attachmentId" value="${sysAttMainForm.fdId}" />
			<c:param name="fdFileName" value="${sysAttMainForm.fdFileName}" />
			<c:param name="canPrint" value="${canPrint}" />
			<c:param name="canCopy" value="${canCopy}" />
			<c:param name="attHeight" value="${attHeight}" />
		</c:import>		
		</td>
		</tr>
	</table>
<script>
/*******************************************
* 初始化
*******************************************/
$(document).ready(function(){
	var JG_PDF = JG_PDFObject.create("${sysAttMainForm.fdId}", "${sysAttMainForm.fdKey}", "${sysAttMainForm.fdModelName}", "${sysAttMainForm.fdModelId}","pdf",null,"${sysAttMainForm.fdFileName}");
	//打印权限控制
	<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=${sysAttMainForm.fdId}" requestMethod="GET">
	JG_PDFObject.canPrint = true;
	</kmss:auth>
	//复制打印控制
	<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=copy&fdId=${sysAttMainForm.fdId}" requestMethod="GET">
	JG_PDFObject.canCopy = true;
	</kmss:auth>
	//打开文档
	JG_PDFObject.pdfLoad();
	<c:if test="${isPrint}">
	setTimeout(function(){
	JG_PDFObject.pdfObject.WebPrint(-1, "", 0, 0, true);
	},1000);
	</c:if>
	//添加全屏按钮
	OptBar_AddOptBar("fullSizeBtn");
	
	var winHeight = 550;
	if (window.innerHeight)
		winHeight = window.innerHeight;
	else if ((document.body) && (document.body.clientHeight))
		winHeight = document.body.clientHeight;
	var obj1 = document.getElementById("JGWebPdf_${sysAttMainForm.fdKey}");
	if(obj1){
		obj1.setAttribute("height", (winHeight-80)+"px");
	}
	
	//关闭文档
	Com_AddEventListener(window, "unload",JG_PDFObject.pdfUnLoad);
});
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>
