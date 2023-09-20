<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<c:set var="sysPrintTemplateForm" value="${sysPrintTemplateForm }" />
<c:set var="sysPrintPrefix" value="sysPrintTemplateForm.${param.fdKey}." />
<c:set var="fdPrintOperType" value="templateHistory"></c:set>
<c:set var="fdPrintSubTemplateSize" value="0"></c:set>
<link href="${KMSS_Parameter_ContextPath}sys/print/designer/style/designer.css" type="text/css" rel="stylesheet" />
<link href="${KMSS_Parameter_ContextPath}resource/style/default/doc/document.css" type="text/css" rel="stylesheet" />
<!--[if IE 6]>
	<link href="style/designer_ie6.css" type="text/css" rel="stylesheet" />
<![endif]-->

<script>

Com_IncludeFile("jquery.js|dialog.js|formula.js|doclist.js|security.js");
<%@ include file="../include/lang.jsp" %>
<%
pageContext.setAttribute("_isXForm","true");
%>
</script>
<kmss:windowTitle moduleKey="sys-xform:xform.title"
	subjectKey="sys-xform:tree.xform.def"
	subject="历史模板" />
	
<div id="optBarDiv">
	<c:if test="${sysPrintTemplateHistoryForm.method_GET=='edit'}">
		<input type="button" value="<bean:message key="button.submit"/>"
		onclick="onClickSubmit('update');" />
	</c:if>
	<c:if test="${sysPrintTemplateHistoryForm.method_GET=='add'}">
		<input type="button" value="<bean:message key="button.save"/>"
		onclick="onClickSubmit('save');" />
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();" />
</div>
<html:form action="/sys/print/sys_print_template_history/sysPrintTemplateHistory.do" method="post">
	<input type="hidden" name="fdPrintOperType" value="templateHistory">
	<input type="hidden" name="fdPrintSubTemplateSize" value="${fdPrintSubTemplateSize }">
	<input type="hidden" name="fdFormFileNames" value="${fdFormFileNames }">
	<input type="hidden" name="fdTemplateId" value="${sysPrintTemplateHistoryForm.fdTemplateId }">
	<input type="hidden" name="fdTemplateEdition" value="${sysPrintTemplateForm.fdTemplateEdition }">
	<input type="hidden" name="fdFormMode" value="${sysPrintTemplateForm.fdFormMode }">
	<input type="hidden" name="fdModelName" value="${HtmlParam.templateModelName }">
	<input type="hidden" name="fdModelId" value="${HtmlParam.fdModelId }">

	<div id="_tmp_history_xform_html" style="display: none;">${formDesginerHtml}</div>
	
<p class="txttitle">打印历史模板_V${sysPrintTemplateForm.fdTemplateEdition }</p>
<center>
	<table id="Label_Tabel" width=95%>
		
<!-- 设计器区域 -->
<c:if test="${param.useLabel != 'false'}">
	<tr LKS_LabelName="<kmss:message key="${(not empty param.messageKey) ? (param.messageKey) : 'sys-print:table.sysPrint.title'}" />" style="display:none" id="sysPrint_tab">
		<td>
</c:if>
<%@include file="/sys/print/include/sysPrintTemplate_edit_content.jsp"%>
<c:if test="${param.useLabel != 'false'}">
	</td>
		</tr>
</c:if>
<%@ include file="/sys/print/include/sysPrintTemplate_edit_script.jsp"%>
	</table>
</center>
</html:form>
<script type="text/javascript">
function onClickSubmit(method){
	Com_Submit(document.sysPrintTemplateHistoryForm, method);
}
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>