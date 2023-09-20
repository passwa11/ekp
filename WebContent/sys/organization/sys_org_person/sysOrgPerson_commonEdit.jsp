<%@page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.organization.forms.SysOrgPersonForm"%>
<tr>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdName" />
	</td>
	<td width=35%>
		<xform:text property="fdName" style="width:80%" value="${sysOrgPersonForm.fdNameOri}"></xform:text>
	</td>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdNickName" />
	</td>
	<td width=35%>
	  <xform:text property="fdNickName" style="width:90%"></xform:text>
	</td>
</tr>
<tr>
<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdNo" />
	</td>
	<td width=85% colspan="3">
	  	<%-- 引用通用的编号属性 --%>
	  	<input type="hidden" name="fdOrgType" value="8">
		<%@ include file="/sys/organization/org_common_fdNo_edit.jsp"%>
	</td>
</tr>
<% if (TripartiteAdminUtil.isSysadmin()) { %>
<tr>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdEmail" />
	</td>
	<td width=85% colspan="3">
		<xform:text property="fdEmail" validators="email" style="width:90%"></xform:text>
	</td>
</tr>
<% } else { %>
<tr>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdParent" />
	</td>
	<td width=35%>
		<html:hidden property="fdParentId" />
		<html:text style="width:90%" property="fdParentName" readonly="true" styleClass="inputsgl" />
		<a href="#" onclick="Dialog_Address(false, 'fdParentId', 'fdParentName', null, ORG_TYPE_ORGORDEPT|ORG_FLAG_BUSINESSALL, null, null, null, null, null, null, null, null, false);">
			<bean:message key="dialog.selectOrg" />
		</a>
	</td>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdEmail" />
	</td>
	<td width=35%>
	<xform:text property="fdEmail" validators="email" style="width:90%"></xform:text>
	
	</td>
</tr>
<% } %>
<tr>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdMobileNo" />
	</td>
	<td width=85% colspan="3">
	<xform:text property="fdMobileNo" validators="phone uniqueMobileNo" style="width:90%" htmlElementProperties="placeholder='${ lfn:message('sys-organization:sysOrgPerson.moblieNo.tips') }'"></xform:text>
	
	</td>
</tr>
<tr>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdWorkPhone" />
	</td>
	<td width=85% colspan="3">
	<xform:text property="fdWorkPhone" style="width:90%"></xform:text>
	
	</td>
</tr>
<tr>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdLoginName" />
	</td>
	<td width=35%>
	  <xform:text property="fdLoginName" validators="uniqueLoginName invalidLoginName normalLoginName" style="width:90%"></xform:text>
		
	</td>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdDefaultLang" />
	</td>
	<td width=35%>
	<%
		SysOrgPersonForm sysOrgPersonForm = (SysOrgPersonForm) request
				.getAttribute("sysOrgPersonForm");
		out.write(sysOrgPersonForm.getLangSelectHtml(request,
				"fdDefaultLang", sysOrgPersonForm.getFdDefaultLang()));
	%>
	</td>
</tr>
<c:if test="${sysOrgPersonForm.method_GET=='add'}">
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPerson.fdPassword" />
		</td>
		<td width=85% colspan="3">
			<xform:text style="width:90%" property="fdNewPassword" subject="${ lfn:message('sys-organization:sysOrgPerson.fdPassword') }"></xform:text>
		</td>
		
	</tr>
</c:if>
<tr>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdKeyword" />
	</td>
	<td width=35%>
	<xform:text property="fdKeyword" style="width:90%"></xform:text>
	
	</td>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdOrder" />
	</td>
	<td width=35%>
		<xform:text property="fdOrder" style="width:90%"></xform:text>
	</td>
</tr>
<% if (!TripartiteAdminUtil.isSysadmin()) { %>
<tr>
	<td width=15% class="td_normal_title"><bean:message
		bundle="sys-organization" key="sysOrgPerson.fdStaffingLevel" /></td>
	<td width="35%">
			<xform:staffingLevel propertyName="fdStaffingLevelName" propertyId="fdStaffingLevelId"></xform:staffingLevel>
	</td>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdPosts" />
	</td>
	<td width=35%>
        <html:hidden property="fdPostIds" />
		<html:text property="fdPostNames" readonly="true" styleClass="inputsgl" style="width:90%" />
		<a href="#" onclick="Dialog_Address(true,'fdPostIds', 'fdPostNames', ';', ORG_TYPE_POST | ORG_FLAG_BUSINESSALL, null, null, null, null, null, null, null, null, false);">
			<bean:message key="dialog.selectOrg" />
		</a>		
	</td>
</tr>
<% } %>
<tr>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdRtxNo" />
	</td>
	<td width=35%>
	<xform:text property="fdRtxNo" style="width:90%"></xform:text>
	
	</td>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdCardNo" />
	</td>
	<td width=35%><html:text style="width:90%" property="fdCardNo" />
	</td>
</tr>
<tr>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdSex" />
	</td>
	<td width="35%">
	    <sunbor:enums property="fdSex" enumsType="sys_org_person_sex" elementType="radio" />
	</td>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdWechatNo" />
	</td>
	<td width=35%>
		<xform:text property="fdWechatNo" style="width:90%"></xform:text>
	</td>
</tr>
<tr>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdShortNo" />
	</td>
	<td width="35%">
	    <xform:text property="fdShortNo" style="width:90%"></xform:text>
	</td>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdDoubleValidation" />
	</td>
	<td width="35%">
		<%
			if("true".equals(com.landray.kmss.sys.profile.model.PasswordSecurityConfig.newInstance().getDoubleAuthEnable())) {
				// 后台总开关开启时，可以按状态显示
		%>
			<sunbor:enums property="fdDoubleValidation" enumsType="sys_org_double_validation" elementType="select"/>
		<%
			} else {
				// 后台总开关关闭时，一律显示"禁用"
		%>
			<bean:message bundle="sys-organization" key="sysOrgPerson.fdDoubleValidation.disable" />
		<%	
			}
		%>
	</td>
</tr>

<% if (!TripartiteAdminUtil.isSysadmin()) { %>
<tr>
	<c:set var="_colspan" value="3" />
	<c:if test="${sysOrgPersonForm.method_GET=='edit'}">
		<c:set var="_colspan" value="1" />
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPerson.fdIsAvailable" />
		</td>
		<td width="35%">
		    <sunbor:enums property="fdIsAvailable" enumsType="sys_org_available" elementType="radio" />
		</td>
	</c:if>	
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdIsBusiness" />
	</td>
	<td width=35% colspan="${_colspan}">
		<sunbor:enums property="fdIsBusiness" enumsType="common_yesno" elementType="radio" />
	</td>
</tr>
<tr>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdCanLogin" />
	</td>
	<td width=85% colspan="3">
		<sunbor:enums property="fdCanLogin" value="${sysOrgPersonForm.fdCanLogin}" enumsType="common_yesno" elementType="radio" />
	</td>
</tr>
<% } %>

<c:if test="${personImportType=='inner'}">
	<c:import url="${personExtendFormUrl}" charEncoding="UTF-8" />
</c:if>
<tr>
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdMemo" />
	</td>
	<td colspan=3>
		<xform:textarea property="fdMemo" style="width:100%"></xform:textarea>
	</td>
</tr>

<%-- 引入动态属性 --%>
<c:import url="/sys/property/custom_field/custom_fieldEdit.jsp" charEncoding="UTF-8" />
