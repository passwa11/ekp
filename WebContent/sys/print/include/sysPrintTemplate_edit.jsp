<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.print.forms.SysPrintTemplateForm"%>
<%@page import="com.landray.kmss.sys.print.interfaces.ISysPrintTemplateForm,com.landray.kmss.sys.xform.interfaces.ISysFormTemplateForm"%>
<%@page import="com.landray.kmss.sys.xform.XFormConstant"%>
<c:set var="templateForm" value="${requestScope[param.formName]}" />
<c:set var="sysPrintTemplateForm" value="${templateForm.sysPrintTemplateForm}" />
<c:set var="sysPrintPrefix" value="sysPrintTemplateForm.${param.fdKey}." />
<link href="${KMSS_Parameter_ContextPath}sys/print/designer/style/designer.css" type="text/css" rel="stylesheet" />
<link href="${KMSS_Parameter_ContextPath}resource/style/default/doc/document.css" type="text/css" rel="stylesheet" />
<link href="${KMSS_Parameter_ContextPath}sys/attachment/view/img/dnd.css" type="text/css" rel="stylesheet" />
<!--[if IE 6]>
	<link href="style/designer_ie6.css" type="text/css" rel="stylesheet" />
<![endif]-->
<script>
Com_IncludeFile("jquery.js|dialog.js|formula.js|doclist.js");
<%@ include file="lang.jsp" %>
</script>

<%
	//是否部署表单机制
	Object templateForm = pageContext.getAttribute("templateForm");
	String _isXForm = templateForm instanceof ISysFormTemplateForm ? "true":"false";
	pageContext.setAttribute("_isXForm", _isXForm);
%>

<!-- 设计器区域 -->
<c:if test="${param.useLabel != 'false'}">
	<tr LKS_LabelName="<kmss:message key="${(not empty param.messageKey) ? (param.messageKey) : 'sys-print:table.sysPrint.title'}" />" style="display:none" id="sysPrint_tab" LKS_LabelEnable="${JsParam.enable eq 'false' ? 'false' : 'true'}">
		<td>
</c:if>
<%@include file="/sys/print/include/sysPrintTemplate_edit_content.jsp"%>
<c:if test="${param.useLabel != 'false'}">
	</td>
		</tr>
</c:if>
<%@ include file="/sys/print/include/sysPrintTemplate_edit_script.jsp"%>