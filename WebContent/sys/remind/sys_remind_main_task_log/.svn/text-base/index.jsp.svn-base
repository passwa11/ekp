<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.list">
	<template:replace name="title">${lfn:message('sys-remind:table.sysRemindMainTaskLog')}</template:replace>
	<template:replace name="content">
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/remind/sys_remind_main_task_log/sysRemindMainTaskLog.do?method=list&remindId=${JsParam.remindId}&taskId=${JsParam.taskId}'}
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
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
		 	});
	 	</script>
	</template:replace>
</template:include>
