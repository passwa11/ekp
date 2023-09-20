<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message key="hr.staff.tree.info.setting" bundle="hr-staff"/></template:replace>
	<template:replace name="content">
	
		<h2 align="center" style="margin:10px 0">
			<span class="profile_config_title"><bean:message key="hr.staff.idcard.staff.reminder" bundle="hr-staff"/></span>
		</h2>
			<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do" >
			<center>
				<table class="tb_normal" width=95% >
					<tr>
						<td class="td_normal_title" width="15%">${lfn:message('hr-staff:hr.staff.idcard.enable')}</td>
						<td >
						   	<ui:switch property="value(isIdcardValidate)"  enabledText="${lfn:message('hr-staff:hr.staff.enable.open1')}" disabledText="${lfn:message('hr-staff:hr.staff.enable.close1')}"  checked="true" ></ui:switch>
						   <bean:message bundle="hr-staff" key="hr.staff.idcard.enable.tip"/>
						</td>
					</tr>
		
				</table>
			</center>
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			</center>	
			</html:form>
			
		<script>

		</script>
	</template:replace>
</template:include>
