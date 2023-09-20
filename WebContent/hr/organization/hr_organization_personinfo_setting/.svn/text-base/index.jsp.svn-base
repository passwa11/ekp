<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message key="hr.organization.personinfo.setting" bundle="hr-organization"/></template:replace>
	<template:replace name="head">
		<script>
			Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|dialog.js", null, "js");
		</script>
		<script>$KMSSValidation();</script>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
		<div style="margin-top:25px">
			<h2 align="center" style="margin:10px 0">
			<!-- 人事组织架构开关配置 -->
				<span class="profile_config_title"><bean:message key="hr.organization.personinfo.setting" bundle="hr-organization"/></span>
			</h2>
			<center>
				<table class="tb_normal" width=90%>
				<!-- 是否开启岗位名称关联部门名称 -->
					<tr>
						<td class="td_normal_title" width=50%>${lfn:message('hr-organization:hr.organization.personinfo.setting.tips')}
						<br>
						 <font color="red"><bean:message bundle="hr-organization" key="hr.organization.personinfo.setting.open.tips"/></font>
						</td>
						<td  colspan=3>
						   	<ui:switch id="postRelationdept" property="value(ispostRelationdept)"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
						   	disabledText="${lfn:message('sys-ui:ui.switch.disabled')}" checked="false" ></ui:switch>
						</td>
					</tr>
					<!-- 是否保持组织架构和群组名唯一 -->
					<tr>
						<td class="td_normal_title" width=50%>${lfn:message('hr-organization:hr.organization.personinfo.setting.tips2')}
						<br>
						   <font color="red"><bean:message bundle="hr-organization" key="hr.organization.personinfo.setting.open.tips2"/></font>
						</td>
						</td>
						<td  colspan=3>
						   	<ui:switch id="UniqueGroupName" property="value(isUniqueGroupName)"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
						   	disabledText="${lfn:message('sys-ui:ui.switch.disabled')}" checked="true" ></ui:switch>
						</td>
					</tr>
			</table>
				<div style="margin-bottom: 10px;margin-top:25px">
				   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
				</div>
			</center>
		</div>
		<html:hidden property="method_GET" />
		<input type="hidden" name="modelName" value="com.landray.kmss.hr.organization.model.HrOrganizationPersoninfoSetting" />
		</html:form>
		</template:replace>
</template:include>
