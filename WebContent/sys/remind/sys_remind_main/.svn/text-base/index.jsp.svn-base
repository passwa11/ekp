<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.list">
	<template:replace name="title">${lfn:message('sys-remind:table.sysRemindMain')}</template:replace>
	<template:replace name="content">
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/remind/sys_remind_main/sysRemindMain.do?method=list&tmplId=${JsParam.tmplId}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable">
				<list:col-auto props=""/>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
		 		// 查看任务
				window.showTask = function(fdId) {
					dialog.iframe("/sys/remind/sys_remind_main_task/index.jsp?remindId=" + fdId, "${lfn:message('sys-remind:table.sysRemindMainTaskLog')}", null, 
							{width:900, height:500, topWin:window, close:true});
				}
				
				// 查看日志
				window.showLog = function(fdId) {
					dialog.iframe("/sys/remind/sys_remind_main_task_log/index.jsp?remindId=" + fdId, "${lfn:message('sys-remind:table.sysRemindMainTaskLog')}", null, 
							{width:900, height:500, topWin:window, close:true});
				}
				
				// 查看提醒设置
				window.showRemind = function(fdId) {
					dialog.iframe("/sys/remind/sys_remind_main/sysRemindMain.do?method=view&fdId=" + fdId, "${lfn:message('sys-remind:table.sysRemindMainTaskLog')}", null, 
							{width:900, height:500, topWin:window, close:true});
				}
		 	});
	 	</script>
	</template:replace>
</template:include>
