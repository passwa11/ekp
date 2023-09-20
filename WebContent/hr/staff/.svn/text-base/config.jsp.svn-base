<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="sys-organization" key="sysOrgConfig" /></template:replace>
	<template:replace name="head">
		<script>
			Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|dialog.js", null, "js");
		</script>
		<script>$KMSSValidation();</script>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do" >
			<div style="margin-top:25px">
			<p class="configtitle">
				<bean:message bundle="hr-staff" key="py.MoRenQuanXianKaiGuan"/>
			</p>
			<center>
			<table class="tb_normal" width=90%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="hr-staff" key="HrStaffConfig.openSelect"/>
					</td><td colspan=3>
						<ui:switch property="value(fdSelfView)" 
							enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
							disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						<br/>
						<bean:message bundle="hr-staff" key="HrStaffConfig.view.owner"/><span style="color:red;">(<bean:message bundle="hr-staff" key="HrStaffConfig.view.owner.tip"/>)</span>
					</td>
				</tr>
			</table>
			<div style="margin-bottom: 10px;margin-top:25px">
				   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');" order="1"></ui:button>
			</div>
			</center>
			</div>
			<html:hidden property="method_GET"/>
			<html:hidden property="modelName" value="com.landray.kmss.hr.staff.model.HrStaffConfig"/>
		</html:form>
	</template:replace>
</template:include>