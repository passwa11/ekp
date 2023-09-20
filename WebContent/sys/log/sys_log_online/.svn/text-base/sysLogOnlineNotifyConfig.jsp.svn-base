<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="sys-log" key="sysLogOnline.NotifyConfig.set"/></template:replace>
	<template:replace name="head">
		<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/common.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
		Com_IncludeFile("dialog.js");
		Com_IncludeFile("icon.css", "../sys/ui/extend/theme/default/style/");
		</script>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin:10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-log" key="sysLogOnline.NotifyConfig.set"/></span>
		</h2>
		
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do?autoclose=false">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="sys-log" key="sysLogOnline.NotifyConfig.NotifyType"/>
						</td><td>
							<kmss:editNotifyType property="value(notifyType)"/>
						</td>
					</tr>	
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="sys-log" key="sysLogOnline.NotifyConfig.NotifyTargets"/>
						</td>
						<td>
							<html:hidden property="value(notifyTargetIds)" />
							<html:textarea	property="value(notifyTargetNames)" style="width:90%;height:90px" styleClass="inputmul" readonly="true" /> 
							<a href="#"	onclick="Dialog_Address(true, 'value(notifyTargetIds)','value(notifyTargetNames)', ';', ORG_TYPE_ALL | ORG_FLAG_BUSINESSALL);">
							<bean:message key="dialog.selectOrg" /> </a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<bean:message  bundle="sys-log" key="sysLogOnline.NotifyConfig.describe"/> <div class="lui_icon_s lui_icon_s_icon_help"></div>
						</td>
					</tr>
				</table>
			</center>
			<html:hidden property="method_GET" />
			
			<center style="margin:10px 0;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			</center>
		</html:form>
		
	 	<script type="text/javascript">
			 // 加载数据库保存的数据
			$.ajax({
				url : '<c:url value="/sys/log/sys_log_online/sysLogOnline.do?method=getNotifyConfig" />',
				type : 'post',
				dataType : 'text',
				success : function(data) {
					if (data && data.length > 0) {
						$("form :checkbox").attr("checked", false);
						var values = data.split(";");
						$.each(data.split(";"), function(i, val) {
							$("form input[value=" + val + "]").trigger("click");
						});
					}
				}
			});
		</script>
	</template:replace>
</template:include>
