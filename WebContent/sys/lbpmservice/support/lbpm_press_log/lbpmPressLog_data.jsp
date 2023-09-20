<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmPressLog" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column property="docCreateTime" title="${ lfn:message('sys-lbpmservice-support:lbpmPressLog.docCreateTime') }">
		</list:data-column>
		<list:data-column col="docCreator.fdName" title="${ lfn:message('sys-lbpmservice-support:lbpmPressLog.docCreator') }">
			<c:out value="${lbpmPressLog.docCreator.fdName}" />
		</list:data-column>
		<list:data-column col="fdAction" title="${ lfn:message('sys-lbpmservice-support:lbpmPressLog.fdActionKey') }">
			<c:if test="${lbpmPressLog.fdActionKey=='draftPress'}">
				${lfn:message('sys-lbpmservice-support:lbpmPressLog.fdActionKey.draftTypeName')}
			</c:if>
			<c:if test="${lbpmPressLog.fdActionKey=='reviewPress'}">
				${lfn:message('sys-lbpmservice-support:lbpmPressLog.fdActionKey.reviewTypeName')}
			</c:if>
		</list:data-column>
		<list:data-column col="fdPressed.fdName" title="${ lfn:message('sys-lbpmservice-support:lbpmPressLog.pressed') }">
			<c:out value="${lbpmPressLog.fdPressed.fdName}" />
		</list:data-column>
		
		<list:data-column style="text-align:center;vertical-align:middle;" headerClass="width60" col="operations" title="${ lfn:message('sys-lbpmservice-support:lbpmFlowSimulation.delete') }" escape="false">
			<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_press_log/lbpmPressLog.do?method=delete&fdId=${lbpmPressLog.fdId}">
				      <a class="delete_lbpmPress" href="javascript:deleteLbpmPress('${lbpmPressLog.fdId}')">${ lfn:message('sys-lbpmservice-support:lbpmFlowSimulation.delete') }</a>
			</kmss:auth>
		</list:data-column>
		
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>