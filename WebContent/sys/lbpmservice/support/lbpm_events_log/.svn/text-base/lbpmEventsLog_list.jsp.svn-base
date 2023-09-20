<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<list:listview id="listview">
	<ui:source type="AjaxJson">
		{url:'/sys/lbpmservice/support/lbpm_events_log/lbpmEventsLog.do?method=list&fdProcessTemplateId=${JsParam.fdProcessTemplateId}'}
	</ui:source>

	<!-- 列表视图 -->
	<list:colTable isDefault="true" layout="sys.ui.listview.columntable">
		<list:col-serial></list:col-serial>
		<list:col-auto props="fdCreateTime;fdCreatorName;fdEventType;fdNodesName;fdEventName;fdEventListener;fdOperationContent"></list:col-auto>
	</list:colTable>
</list:listview>
<list:paging></list:paging>

