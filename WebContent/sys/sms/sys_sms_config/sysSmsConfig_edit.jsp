<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="sys-organization" key="sysOrgConfig" /></template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message key="sysSmsMain.number.set" bundle="sys-sms"/></span>
		</h2>
		<html:form action="/sys/sms/sys_sms_config/sysSmsConfig.do">
			<center>
			<table class="tb_normal" width=95%>
				<tr>
					<td colspan=4>
						<bean:message  bundle="sys-sms" key="sysSmsConfig.fdContent"/>
					</td>
				</tr>
				<tr>
					<td colspan=4>
						<html:textarea property="fdContent" style="width:100%" rows="10"></html:textarea>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-sms" key="sysSmsConfig.fdDeleteTime"/>
					</td>
					<td colspan=3>
						<xform:text property="fdDeleteTime" validators="digits min(0)"/>
						<bean:message bundle="sys-sms" key="sysSmsConfig.fdDeleteTime.hint"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-sms" key="sysSmsConfig.fdDeleteFailureTime"/>
					</td>
					<td colspan=3>
						<xform:text property="fdDeleteFailureTime" validators="digits min(0)"/>
						<bean:message bundle="sys-sms" key="sysSmsConfig.fdDeleteFailureTime.hint"/>
					</td>
				</tr>
			</table>
			</center>
			<html:hidden property="method_GET"/>
			
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysSmsConfigForm, 'update');"></ui:button>
			</center>
			
			<script type="text/javascript">
		 	$KMSSValidation();
			</script>
		</html:form>
		<html:javascript formName="sysSmsMainForm" cdata="false" dynamicJavascript="true" staticJavascript="false" />
	</template:replace>
</template:include>	
