<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<ui:button text="提交" 
				onclick="submit();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<div class="lui_form_content" style="margin-top: 20px;">
		<html:form action="/kms/log/kms_log_app/kmsLogApp.do">
		<table class="tb_normal" style="width: 100%">
			<tr>
				<td width="25%">
					上次运行时间
				</td>
				<td id = "lastDate">
				</td>
				
			</tr>
			<tr>
				<td width="25%">
					运行间隔时间
				</td>
				<td>
					<xform:datetime style="width:20%" showStatus="edit" property="updateTime" validators="required" required="true" />
				</td>
				
			</tr>
			<tr>
				<td colspan="2">
					此任务是为了优化系统任务名称为【KMS日志库】日志定时任务执行时间过长导致内存溢出
					<span style="color: red;" >
					(注意：此任务运行截止时间尽量上次执行时间间隔尽量设置间隔小，例如设置间隔时间为一个月，时间间隔越小执行完成越快，请不要在重复执行,运行后可在后台 日志管理->后台日志 查看是否已经结束)
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
						Com_Submit(document.kmsLogAppForm, 'updateLastDateAppConfig');
					}
				})
			})
		}
		$KMSSValidation(document.forms['kmsLogAppForm']);
		
		function getLastDateAppConfig(){
			var url = Com_Parameter.ContextPath+"kms/log/kms_log_app/kmsLogApp.do?method=getLastDateAppConfig";
			jQuery.ajax({
				url: url,
				type: 'get',
				dataType: 'html',
				success: function(data, textStatus, xhr) {
					$(document.getElementById("lastDate")).text(xhr.responseText);
					
				},
				error: function(xhr, textStatus, errorThrown) {
					alert(errorThrown);
				}
			});
		}
		
		getLastDateAppConfig();
		</script>
	</template:replace>
</template:include>
