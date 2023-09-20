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
			<center>
					<table class="tb_normal" width=90%>
						<tr>
							<td class="td_normal_title" width=20%>加班白名单人员</td>
							<td colspan="3">
							<xform:address mulSelect="true"
									orgType="ORG_TYPE_PERSON"
									propertyName="value(fdOvertimeWhiteName)"
									propertyId="value(fdOvertimeWhiteId)"></xform:address></td>
						</tr>
					</table>
					<div style="margin-bottom: 10px;margin-top:25px">
				   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');" order="1"></ui:button>
			</div>
			</center>
			</div>
		</html:form>
	</template:replace>
</template:include>