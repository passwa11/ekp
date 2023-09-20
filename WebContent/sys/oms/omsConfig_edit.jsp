<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title">${lfn:message('sys-oms:oms.setting')}</template:replace>
		<template:replace name="head">
		<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/common.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
		Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js", null, "js");
		</script>
		<style type="text/css"> 
			.tb_normal td {
    //padding: 5px;
    //border: 1px #d2d2d2 solid;
    //word-break: break-all;
}
		</style> 
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title">${lfn:message('sys-oms:oms.setting')}</span>
		</h2>
		
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
			<center>
				
							<table class="tb_normal" width=95%  cellpadding="20" cellspacing="20" style="width: 95%;">
								
	<tr>
		<td class="td_normal_title" width="15%">${lfn:message('sys-oms:oms.batch.size')}</td>
		<td>
			<xform:text property="value(kmss.oms.in.batch.size)" showStatus="edit" validators="number"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">${lfn:message('sys-oms:oms.in.delete.size')}</td>
		<td>
			<xform:text property="value(kmss.oms.in.delete.size)" showStatus="edit" validators="number"/>
			<span class="message">${lfn:message('sys-oms:oms.in.delete.size.tip')}</span>
		</td>
	</tr>
	<tr>
	<td class="td_normal_title" width="15%">${lfn:message('sys-oms:oms.in.organization.backup')}</td>
		<td>
			<label> 
				<ui:switch property="value(kmss.oms.in.organization.backup)"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
			</label>
			<br><span class="txtstrong">
			${lfn:message('sys-oms:oms.in.organization.backup.tip')}）</span>
		</td>
	</tr>
	<tr>
	<td colspan="2">
	<span class="txtstrong">${lfn:message('sys-oms:oms.notify.config')}<a target="_blank" href='<c:url value="/sys/oms/orgsynchro_notify_template_empty/orgSynchroNotifyTemplateEmpty.do?method=edit&fdId=1"/>'>/sys/oms/orgsynchro_notify_template_empty/orgSynchroNotifyTemplateEmpty.do?method=edit&fdId=1</a></span><br>
	</td>
	</tr>
					
				</table>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.com.OMSConfig" />
			<input type="hidden" name="autoclose" value="false" />
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			<kmss:auth requestURL="/sys/quartz/sys_quartz_job/sysQuartzJob.do?method=view">
				<ui:button text="${lfn:message('sys-oms:sys.oms.db.task.url')}" height="35" width="160" onclick="Com_OpenWindow('${LUI_ContextPath}/sys/oms/jobEdit.jsp');"></ui:button>
				<ui:button text="${lfn:message('sys-oms:sys.oms.db.enable.run')}" height="35" width="160" onclick="Com_OpenWindow('${LUI_ContextPath}/sys/quartz/sys_quartz_job/sysQuartzJob.do?method=run&fdJobService=synchroInService&fdJobMethod=synchro');"></ui:button>
			</kmss:auth>
			</center>
		</html:form>
		
	 	<script type="text/javascript">
			$KMSSValidation();
			function validateAppConfigForm(thisObj) {
				return true;
			}

		</script>
	</template:replace>
</template:include>
