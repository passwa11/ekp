<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="sysTagTemplate_Key" value="${param.fdKey}" />
<c:set var="sysTagTemplateFormPrefix"
	   value="sysTagTemplateForms.${param.fdKey}." />
<c:set var="sysTagTemplateForm"
	   value="${requestScope[param.formName].sysTagTemplateForms[param.fdKey]}" />
<c:set var="useTab" value="true"></c:set>
<c:if test="${param.useTab!=null && param.useTab==false}">
	<c:set var="useTab" value="false"/>
</c:if>
<c:if test="${ useTab=='true'}">
<tr>
	<td class="td_normal_title" width=15% nowrap>
		<bean:message bundle="sys-tag" key="table.sysTagTags"/>
	</td>
	<td colspan="3">
</c:if>
		${sysTagTemplateForm.fdTagNames}
<c:if test="${ useTab=='true'}">
	</td>
</tr>
</c:if>