<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
<script>
	Com_IncludeFile("config.css", "${LUI_ContextPath}/sys/time/sys_time_leave_rule/", "css", true);
</script>
<center>
<br>
<table class="tb_normal" width=95%> 
	<tr class="tr_normal_title">
		<td colspan="4" class="config_title">
			<bean:message bundle="sys-time" key="sysTimeLeaveRuleList.fdInputHelpTitle1"/>
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<%@ include file="/sys/time/sys_time_leave_rule/input_help.jsp"%>
		</td>
	</tr>
</table>
<br>
</center>
	</template:replace>
</template:include>