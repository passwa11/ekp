<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmEventsLog" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="fdCreateTime" title="${ lfn:message('sys-lbpmservice-support:lbpmEventsLog.fdCreateTime') }">
		</list:data-column>
		<list:data-column property="fdCreatorName" title="${ lfn:message('sys-lbpmservice-support:lbpmEventsLog.fdCreatorName') }">
		</list:data-column>
		<list:data-column property="fdEventType" title="${ lfn:message('sys-lbpmservice-support:lbpmEventsLog.fdEventType') }">
	 	</list:data-column>
		<list:data-column property="fdNodesName" title="${ lfn:message('sys-lbpmservice-support:lbpmEventsLog.fdNodesName') }">
		</list:data-column>
		<list:data-column property="fdEventName" title="${ lfn:message('sys-lbpmservice-support:lbpmEventsLog.fdEventName') }">
		</list:data-column>
		<list:data-column property="fdEventListener" title="${ lfn:message('sys-lbpmservice-support:lbpmEventsLog.fdEventListener') }">
		</list:data-column>
		<list:data-column property="fdOperationContent" title="${ lfn:message('sys-lbpmservice-support:lbpmEventsLog.fdOperationContent') }">
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>














































