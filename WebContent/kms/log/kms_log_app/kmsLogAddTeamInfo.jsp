<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<ui:button text="${lfn:message('button.submit') }" 
				onclick="submit();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<div class="lui_form_content" style="margin-top: 20px;">
		<html:form action="/kms/log/kms_log_app/kmsLogApp.do">
		<table class="tb_normal" style="width: 100%">
			<tr>
				<td width="25%">新版团队积分计算起始时间</td>
				<td width="75%">${fdCalcCfgTime }</td>
			</tr>
			<tr>
				<td width="25%">日志同步时间</td>
				<td width="75%">${oldDateStr }</td>
			</tr>
			<tr>
				<td width="25%">上次运行时间</td>
				<td width="75%">${fdAddTeamTime }</td>
			</tr>
			<tr>
				<td width="25%">选择运行时间</td>
				<td>
					<xform:datetime style="width:20%" showStatus="edit" property="fdAddTeamTime" validators="required" required="true" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					此任务是为了补充统计新版团队积分数据中的团队信息而设计的
					<span style="color: red;" >
					(注意：此任务运行时间段尽量设置间隔小，例如设置间隔时间为5天，时间段间隔越小执行完成越快，请不要在重复执行,运行后可在后台 日志管理->后台日志 查看是否已经结束)
					</span>
				</td>
			</tr>
		</table>
		</html:form>
		</div>
		<script>
		function submit() {
			seajs.use("lui/dialog", function(d){
				d.confirm("确定提交吗？请重复确认！", function(flag) {
					if(flag) {
						Com_Submit(document.kmsLogAppForm, 'addTeamInfo');
					}
				})
			})
		}
		$KMSSValidation(document.forms['kmsLogAppForm']);
		
		</script>
	</template:replace>
</template:include>
