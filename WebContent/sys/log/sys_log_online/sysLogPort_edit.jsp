<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="sys-log" key="sysLogOnline.Port.Switch"/></template:replace>
	<template:replace name="head">
		<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/common.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
		Com_IncludeFile("dialog.js");
		Com_IncludeFile("icon.css", "../sys/ui/extend/theme/default/style/");
		</script>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin:10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-log" key="sysLogOnline.Port.Switch"/></span>
		</h2>
		
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="sys-log" key="sysLogOnline.Port.Switch"/>
						</td>
						<td>
						<label>
							<xform:checkbox property="value(portSwitch)" showStatus="edit" >
								<xform:simpleDataSource value="true"><bean:message  bundle="sys-log" key="sysLogOnline.Port.Switch.describe"/></xform:simpleDataSource>
							</xform:checkbox>
						</label>
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
	</template:replace>
</template:include>
