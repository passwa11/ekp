<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="sys-notify" key="sysNotifyTodo.param.config" /></template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-notify" key="sysNotifyTodo.param.config" /></span>
			<% if(!new com.landray.kmss.sys.notify.transfer.SysNotifyMailConfigChecker().isRuned()) { %>
			<span style="color: red;"><bean:message bundle="sys-notify" key="sysNotifyTodo.config.transfer" /></span>
			<% } %>
		</h2>
		
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do" onsubmit="return validateAppConfigForm(this);">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width="20%">
							<bean:message bundle="sys-notify" key="sysNotifyTodo.tip.config" />
						</td>
						<td>
							<xform:text property="value(fdTip)" style="width:500px;"/>
						</td>
					</tr>
				</table>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.notify.model.SysNotifyMailConfig" />
			
			<kmss:authShow roles="SYSROLE_ADMIN;SYSROLE_SYSADMIN">
				<center style="margin-top: 10px;">
				<!-- 提交 -->
				<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');" order="1" ></ui:button>
				</center>
			</kmss:authShow>
		</html:form>
		
		<div style="margin:20px 0px; border-top: 1px #C0C0C0 dashed; text-align: center; padding-top:10px;">
			<div style="display:inline;vertical-align: top;"><bean:message bundle="sys-notify" key="sysNotifyTodo.mail.tip.config.example" /></div>
			<div style="display:inline; margin-left:20px;">
				<img src="<c:url value="/sys/notify/sys_notify_todo/mail_tip.png" />" style="border:1px #C0C0C0 solid;">
			</div>
		</div>
		
	 	<script type="text/javascript">
		 	function validateAppConfigForm(thisObj){
		 		return true;
		 	}
	 	</script>
	</template:replace>
</template:include>
