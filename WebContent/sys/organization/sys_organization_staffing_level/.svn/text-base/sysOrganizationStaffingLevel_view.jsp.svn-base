<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('sysOrganizationStaffingLevel.do?method=edit&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('sysOrganizationStaffingLevel.do?method=delete&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-organization" key="table.sysOrganizationStaffingLevel"/></p>

<center>
<table class="tb_normal" width=95%>
	
	<tr>
		
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevel.fdName"/>
		</td><td width="35%">
			<xform:text property="fdName" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevel.fdLevel"/>
		</td><td width="35%">
			<xform:text property="fdLevel" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevel.fdIsDefault"/>
		</td><td width="85%" colspan="3">
			<c:choose>
				<c:when test="${sysOrganizationStaffingLevelForm.fdIsDefault=='true'}">
					<bean:message key="message.yes"/>
				</c:when>
				<c:otherwise>
					<bean:message key="message.no"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevel.fdPersons"/>
		</td><td colspan=3 style="word-wrap:break-word;word-break:break-all;">
			<bean:write name="sysOrganizationStaffingLevelForm" property="fdPersonNames"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevel.fdDescription"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdDescription" style="width:85%" />
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>