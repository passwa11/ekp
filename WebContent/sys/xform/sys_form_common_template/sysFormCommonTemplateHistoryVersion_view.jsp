<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<c:set var="sysFormTemplateFormPrefix" value="" />
<c:set var="xFormTemplateForm" value="${sysFormCommonTempHistoryForm}" />
<script>

</script>
<kmss:windowTitle moduleKey="sys-xform:xform.title"
	subjectKey="sys-xform:tree.xform.def"
	subject="${sysFormCommonTempHistoryForm.fdName}" />


<div id="optBarDiv">
	<c:if test="${param.versionType eq null || param.versionType ne 'new'}">
		<kmss:auth
			requestURL="/sys/xform/sys_form_common_template/sysFormCommonTemplate.do?method=edit&fdId=${param.fdId}"
			requestMethod="GET">
			<input type="button" value="<bean:message key="button.edit"/>"
				onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}/sys/xform/sys_form_common_template_history/sysFormCommonTemplateHistory.do?method=editHistory&fdId=${param.fdId}&fdMainModelName=${param.fdMainModelName}','_self');">
		</kmss:auth>
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<p class="txttitle">通用表单历史模板_V${xFormTemplateForm.fdTemplateEdition }</p>
<center>
	<html:hidden name="sysFormCommonTempHistoryForm" property="fdId" />
	<table id="Label_Tabel" width=95%>
		<tr
			LKS_LabelName="<bean:message bundle='sys-xform' key='sysFormCommonTemplate.templateInfo'/>">
			<td>
				<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width=15%><bean:message
								bundle="sys-xform" key="sysFormCommonTemplate.fdName" /></td>
						<td><bean:write name="sysFormCommonTempHistoryForm" property="fdName" /></td>
					</tr>
					<%@ include file="/sys/xform/base/sysFormTemplateDisplay_view.jsp"%>
				</table>
			</td>
		</tr>
		<%
			 if(LangUtil.isEnableMultiLang(request.getParameter("fdMainModelName"), "model") && LangUtil.isEnableAdminDoMultiLang()) {
		%>
		<%--多语言 --%>
		<c:import url="/sys/xform/lang/include/sysFormHistoryMultiLang_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="sysFormCommonTempHistoryForm" />
			<c:param name="sysFormTemplateFormPrefix" value="${sysFormTemplateFormPrefix }" />
		</c:import>
		<%
			}
		%>
		<%--被引用的主文档 --%>
		<c:import url="/sys/xform/base/sys_form_template_history/sysFormTemplateHistoryRefMain_view.jsp"	charEncoding="UTF-8">
				<c:param name="formName" value="sysFormCommonTempHistoryForm" />
				<c:param name="fdMainModelName" value="${param.fdMainModelName}" />
		</c:import>
	</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>