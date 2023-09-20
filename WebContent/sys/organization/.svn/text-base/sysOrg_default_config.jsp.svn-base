<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="sys-organization" key="sysOrgConfig" /></template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-organization" key="sysOrgElement.config.default" /></span>
		</h2>
		
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do" onsubmit="return validateAppConfigForm(this);">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
					  <td class="td_normal_title" width=35%  colspan="2">
						 <bean:message  bundle="sys-organization" key="sysOrgElement.config.default.password" />
					  </td><td colspan="3">
							<xform:text property="value(orgDefaultPassword)" value="${SysOrgDefaultConfig.orgDefaultPassword}" style="width:200px;" showStatus="edit"/>
					  </td>
					</tr>				
					<tr>
					  <td class="td_normal_title" rowspan="5"><bean:message  bundle="sys-organization" key="sysOrgElement.config.default.order" /></td>	
					  <td class="td_normal_title" width=15%>
						 <bean:message  bundle="sys-organization" key="sysOrgElement.org" />
					  </td><td colspan="3">
							<xform:text property="value(orgOrgDefaultOrder)" value="${SysOrgDefaultConfig.orgOrgDefaultOrder}" style="width:80px;" showStatus="edit" validators="digits"/>
					  </td>
					</tr>
					<tr>
					  <td class="td_normal_title" width=15%>
						 <bean:message  bundle="sys-organization" key="sysOrgElement.dept" />
					  </td><td colspan="3">
							<xform:text property="value(orgDeptDefaultOrder)" value="${SysOrgDefaultConfig.orgDeptDefaultOrder}" style="width:80px;" showStatus="edit" validators="digits"/>
					  </td>
					</tr>										
					<tr>
					  <td class="td_normal_title" width=15%>
						 <bean:message  bundle="sys-organization" key="sysOrgElement.post" />
					  </td><td colspan="3">
							<xform:text property="value(orgPostDefaultOrder)" value="${SysOrgDefaultConfig.orgPostDefaultOrder}" style="width:80px;" showStatus="edit" validators="digits"/>
					  </td>
					</tr>
					<tr>  	
					  <td class="td_normal_title" width=15%>
						 <bean:message  bundle="sys-organization" key="sysOrgElement.person" />
					  </td><td colspan="3">
							<xform:text property="value(orgPersonDefaultOrder)" value="${SysOrgDefaultConfig.orgPersonDefaultOrder}" style="width:80px;" showStatus="edit" validators="digits"/>
					  </td>
					</tr>
					<tr>
					  <td class="td_normal_title" width=15%>
						 <bean:message  bundle="sys-organization" key="sysOrgElement.group" />
					  </td><td colspan="3">
							<xform:text property="value(orgGroupDefaultOrder)" value="${SysOrgDefaultConfig.orgGroupDefaultOrder}" style="width:80px;" showStatus="edit" validators="digits"/>
					  </td>
					</tr>																				

					<tr><td colspan="4"><bean:message  bundle="sys-organization" key="sysOrgElement.config.default.description" /></td></tr>										
				</table>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.organization.model.SysOrgDefaultConfig" />
			
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			</center>
		</html:form>
		
	 	<script type="text/javascript">
	 		$KMSSValidation();
		 	function validateAppConfigForm(thisObj){
		 		return true;
		 	}
	 	</script>
	</template:replace>
</template:include>
