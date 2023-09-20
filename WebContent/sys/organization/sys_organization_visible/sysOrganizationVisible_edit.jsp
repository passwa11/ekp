<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/sys/organization/sys_organization_visible/sysOrganizationVisible.do">
<div id="optBarDiv">
	<c:if test="${sysOrganizationVisibleForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysOrganizationVisibleForm, 'update');">
	</c:if>
	<c:if test="${sysOrganizationVisibleForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.sysOrganizationVisibleForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.sysOrganizationVisibleForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-organization" key="sysOrganizationVisible.config"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-organization" key="sysOrganizationVisible.visiblePrincipals"/>
		</td><td width="35%">
			<xform:address propertyId="visiblePrincipalIds" propertyName="visiblePrincipalNames" mulSelect="true" orgType="ORG_TYPE_ALLORG" textarea="true" style="width:85%" />
			<span class="txtstrong">*</span><br>
			<bean:message bundle="sys-organization" key="sysOrganizationVisible.visiblePrincipals.describe"/>
		</td>
		
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-organization" key="sysOrganizationVisible.visibleSubordinates"/>
		</td><td width="35%">
			<xform:address propertyId="visibleSubordinateIds" propertyName="visibleSubordinateNames" mulSelect="true" orgType="ORG_TYPE_ORGORDEPT" textarea="true" style="width:85%" />
			<span class="txtstrong">*</span><br>
			<bean:message bundle="sys-organization" key="sysOrganizationVisible.visibleSubordinates.describe"/>
		</td>
		
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-organization" key="sysOrganizationVisible.fdDescription"/>
		</td><td width="35%">
			<xform:textarea property="fdDescription" style="width:85%" />
		</td>
	</tr>
	<tr>
	<td colspan="2">
		<bean:message bundle="sys-organization" key="sysOrganizationVisible.notice.describe"/>
	</td>
	</tr>
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	Com_IncludeFile("dialog.js");
	$KMSSValidation();
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>