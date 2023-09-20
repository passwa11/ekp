<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.list">
	<template:replace name="title">${lfn:message('sys-remind:table.sysRemindMainTask')}</template:replace>
	<template:replace name="content">
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/remind/sys_remind_main_task/sysRemindMainTask.do?method=list&remindId=${JsParam.remindId}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable">
				<list:col-auto props=""/>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
				// 查看日志
				window.showLog = function(fdId) {
					dialog.iframe("/sys/remind/sys_remind_main_task_log/index.jsp?taskId=" + fdId, "${lfn:message('sys-remind:table.sysRemindMainTaskLog')}", null, 
							{width:900, height:500, topWin:window, close:true});
				}
				// 执行任务
				window.runTask = function(fdId) {
					dialog.confirm("${lfn:message('sys-remind:sysRemindMainTask.confirm.runTask')}", function(value) {
						if(value == true) {
							$.post(Com_Parameter.ContextPath + "sys/remind/sys_remind_main_task/sysRemindMainTask.do?method=runTask&fdId=" + fdId, function(res) {
								dialog.result(res);
								topic.publish("list.refresh");
							}, "json");
						}
					});
				}
		 	});
	 	</script>
	</template:replace>
</template:include>
