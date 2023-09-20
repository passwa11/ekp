<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message key="hr.organization.sync.rule" bundle="hr-organization"/></template:replace>
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
			<!-- 编制统计规则 -->
				<span class="profile_config_title"><bean:message key="hr.organization.Compilation.rule" bundle="hr-organization"/></span>
			</h2>
			<center>
				<table class="tb_normal" width=90%>
				<!-- 正式员工纳入编制统计 -->
					<tr>
						<td class="td_normal_title" width=25%>${lfn:message('hr-organization:hr.organization.Compilation.rule.official')}</td>
						<td  class="td_normal_title"  colspan=3>
						<div  style="display: inline-block;">
							   	<ui:switch id="hrcompilationofficial" property="value(compilationOfficial)"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
							   	 checked="true" showType="show">
							   	 </ui:switch>
						</div>
						   	 <span  style="color:red" >(默认开启，不允许关闭)</span>
						</td>
					</tr>
					<!-- 试用期员工纳入编制统计 -->
					<tr>
						<td class="td_normal_title" width=25%>${lfn:message('hr-organization:hr.organization.Compilation.rule.Trial')}</td>
						<td  colspan=3>
						   	<ui:switch id="hrcompilationtrial" property="value(compilationTrial)"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
						   	disabledText="${lfn:message('sys-ui:ui.switch.disabled')}" checked="true"></ui:switch>
						</td>
					</tr>
						<!-- 实习员工纳入编制统计 -->
					<tr>
						<td class="td_normal_title" width=25%>${lfn:message('hr-organization:hr.organization.Compilation.rule.practice')}</td>
						<td  colspan=3>
						   	<ui:switch id="hrcompilationpractice" property="value(compilationPractice)"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
						   	disabledText="${lfn:message('sys-ui:ui.switch.disabled')}" checked="true"></ui:switch>
						</td>
					</tr>
						<!-- 临时员工纳入编制统计 -->
					<tr>
						<td class="td_normal_title" width=25%>${lfn:message('hr-organization:hr.organization.Compilation.rule.temporary')}</td>
						<td  colspan=3>
						   	<ui:switch id="hrcompilationtemporary" property="value(compilationTemporary)"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
						   	disabledText="${lfn:message('sys-ui:ui.switch.disabled')}" checked="true" ></ui:switch>
						</td>
					</tr>
						<!-- 试用期延期员工纳入编制统计 -->
					<tr>
						<td class="td_normal_title" width=25%>${lfn:message('hr-organization:hr.organization.Compilation.rule.trialDelay')}</td>
						<td  colspan=3>
						   	<ui:switch id="hrcompilationtrialDelay" property="value(compilationTrialDelay)"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
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
		<input type="hidden" name="modelName" value="com.landray.kmss.hr.organization.model.HrOrganizationCompilingSum" />
		</html:form>
		</template:replace>
</template:include>
