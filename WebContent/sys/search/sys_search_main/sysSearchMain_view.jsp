<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.search.forms.SysSearchMainForm"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%
	SysSearchMainForm sysSearchMainForm = (SysSearchMainForm)request.getAttribute("sysSearchMainForm");
	sysSearchMainForm.setLocale(request.getLocale());
	sysSearchMainForm.setFdModelName(request.getParameter("fdModelName"));
	sysSearchMainForm.setFdTemplateModelName(request.getParameter("fdTemplateModelName"));
%>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/search/sys_search_main/sysSearchMain.do?method=edit&fdId=${param.fdId}&fdModelName=${param.fdModelName}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('sysSearchMain.do?method=edit&fdId=${JsParam.fdId}&fdModelName=${JsParam.fdModelName}&fdTemplateModelName=${JsParam.fdTemplateModelName}&showCate=${JsParam['showCate'] }','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/sys/search/sys_search_main/sysSearchMain.do?method=delete&fdId=${param.fdId}&fdModelName=${param.fdModelName}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('sysSearchMain.do?method=delete&fdId=${JsParam.fdId}&fdModelName=${JsParam.fdModelName}&fdTemplateModelName=${JsParam.fdTemplateModelName}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="sys-search" key="table.sysSearchMain"/></p>
<center>

<table class="tb_normal" width="600px">
	<tr>
		<td class="td_normal_title" width="25%">
			<bean:message bundle="sys-search" key="sysSearchMain.fdName"/>
		</td><td width="75%">
			<bean:write name="sysSearchMainForm" property="fdName"/>
		</td>
	</tr>
	<c:if test="${JsParam.showCate eq 'true' }">
		<tr>
			<td width=25% class="td_normal_title">
			    <bean:message bundle="sys-search" key="sysSearchMain.fdCategory"/>
			</td><td width=75%>
				<bean:write name="sysSearchMainForm" property="fdCategoryName"/>
			</td>
		</tr>
	</c:if>
	<tr>
		<td class="td_normal_title" width="25%">
			<bean:message bundle="sys-search" key="sysSearchMain.fdOrder"/>
		</td><td width="75%">
			<bean:write name="sysSearchMainForm" property="fdOrder"/>
		</td>
	</tr>
	<c:if test="${not empty sysSearchMainForm.fdTemplateId}">
	<tr>
		<td class="td_normal_title" width="25%">
			<bean:message bundle="sys-search" key="sysSearchMain.fdTemplateId"/>
		</td><td width="75%">
			<c:choose>
				<c:when test="${not empty sysSearchMainForm.fdTemplateFullName}">
					<c:out value="${sysSearchMainForm.fdTemplateFullName}"/>
				</c:when>
				<c:otherwise>
					<c:out value="${sysSearchMainForm.fdTemplateName}"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	</c:if>
	<tr>
		<td class="td_normal_title" width="25%">
			<bean:message bundle="sys-search" key="sysSearchMain.fdCondition"/>
		</td><td width="75%">
			<c:out value="${sysSearchMainForm.conditionText}"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="25%">
			<bean:message bundle="sys-search" key="sysSearchMain.fdDisplay"/>
		</td><td width="75%">
			<c:out value="${sysSearchMainForm.displayText}"/>
		</td>
	</tr>
	<c:if test="${not empty sysSearchMainForm.fdResultUrl}">
	<tr>
		<td class="td_normal_title" width="25%">
			<bean:message bundle="sys-search" key="sysSearchMain.fdResultUrl"/>
		</td><td width="75%">
			${sysSearchMainForm.fdResultUrl}
		</td>
	</tr>
	</c:if>
	<c:if test="${not empty sysSearchMainForm.fdParemNames}">
	<tr>
		<td class="td_normal_title" width="25%">
			<bean:message bundle="sys-search" key="sysSearchMain.fdParemNames"/>
		</td><td width="75%">
			${sysSearchMainForm.paremNamesText}
		</td>
	</tr>
	</c:if>
	<tr>
		<td class="td_normal_title" width="25%">
			<bean:message bundle="sys-search" key="sysSearchMain.fdOrderBy"/>
		</td><td width="75%">
			${sysSearchMainForm.orderByText}
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="25%">
			<bean:message bundle="sys-search" key="sysSearchMain.fdOrderType"/>
		</td><td width="75%">
			<c:if test="${empty sysSearchMainForm.fdOrderType or sysSearchMainForm.fdOrderType eq 'asc'}">
			<bean:message bundle="sys-search" key="sysSearchMain.fdOrderType.asc"/>
			</c:if>
			<c:if test="${sysSearchMainForm.fdOrderType eq 'desc'}">
			<bean:message bundle="sys-search" key="sysSearchMain.fdOrderType.desc"/>
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="25%">
			<kmss:message key="model.tempReaderName"/>
		</td><td width="75%">
			<bean:write name="sysSearchMainForm" property="authSearchReaderNames"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="25%">
			<bean:message bundle="sys-search" key="sysSearchMain.fdAuthEnabled"/>
		</td><td width="75%">
			<sunbor:enumsShow value="${sysSearchMainForm.fdAuthEnabled}" enumsType="common_yesno" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="25%">
			<bean:message bundle="sys-search" key="sysSearchMain.fdCreator"/>
		</td><td width="75%">
			<bean:write name="sysSearchMainForm" property="fdCreatorName"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="25%">
			<bean:message bundle="sys-search" key="sysSearchMain.fdCreateTime"/>
		</td><td width="75%">
			<bean:write name="sysSearchMainForm" property="fdCreateTime"/>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>