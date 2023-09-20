<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-handover" key="sysHandoverConfigMain.task.setting" /></span>
		</h2>
		
		<html:form action="/sys/handover/sysHandoverTaskSetting.do">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
					  <td class="td_normal_title" width=15%>
						 <bean:message bundle="sys-handover" key="sysHandoverConfigMain.task.setting.migrationTime" />
					  </td>
					  <td>
							<bean:message bundle="sys-handover" key="sysHandoverConfigMain.task.setting.migrationTime.text1" />
							<xform:datetime property="value(startTime)" dateTimeType="time" style="width:100px;" showStatus="edit" required="true"></xform:datetime>
							<bean:message bundle="sys-handover" key="sysHandoverConfigMain.task.setting.migrationTime.text2" />
							<xform:text property="value(runtimes)" style="width:50px;" showStatus="edit" required="true" validators="digits range(1,24)"></xform:text>
							<bean:message bundle="sys-handover" key="sysHandoverConfigMain.task.setting.migrationTime.text3" />
							<br>
							<font color="red"><bean:message bundle="sys-handover" key="sysHandoverConfigMain.task.setting.migrationTime.desc" /></font>
					  </td>
					</tr>
					<tr>
					  <td class="td_normal_title">
						 <bean:message bundle="sys-handover" key="sysHandoverConfigMain.task.setting.batchUpdateCount" />
					  </td>
					  <td>
					  		<xform:text property="value(batchUpdateCount)" style="width:50px;" showStatus="edit" required="true" validators="digits range(10,500)"></xform:text>
							<br>
							<font color="red"><bean:message bundle="sys-handover" key="sysHandoverConfigMain.task.setting.batchUpdateCount.desc" /></font>
					  </td>
					</tr>
				</table>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.handover.model.SysHandoverTaskSetting" />
			
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			</center>
		</html:form>
		
	 	<script type="text/javascript">
	 		$KMSSValidation();
	 	</script>
	</template:replace>
</template:include>
