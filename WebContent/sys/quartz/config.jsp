<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="sys-quartz" key="sysQuartzJob.config.params"/></template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin:10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-quartz" key="sysQuartzJob.config.params"/></span>
		</h2>
		
		<html:form action="/sys/quartz/sys_quartz_job/sysQuartzConfig.do">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
					  <td class="td_normal_title" width=25%>
						 <bean:message bundle="sys-quartz" key="sysQuartzJob.config.params.threadPoolQueueSize"/>
					  </td>
					  <td colspan="3">
						<xform:text property="value(threadPoolQueueSize)" validators="required digits min(1) max(500)" showStatus="edit" />
						<span class="message"><bean:message bundle="sys-quartz" key="sysQuartzJob.config.params.threadPoolQueueSize.tip"/></span>
					  </td>
					</tr>
				</table>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.quartz.model.SysQuartzConfig" />
			
			<center style="margin:10px 0;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			</center>
		</html:form>
		
	 	<script type="text/javascript">
	 	$KMSSValidation();
		</script>
	</template:replace>
</template:include>
