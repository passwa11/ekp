<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmsCommonRecycleLog" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<%-- 操作动作 --%>
		<list:data-column col="fdOperateName" title="${lfn:message('kms-common:kmsCommonRecycleLog.fdOperateName')}">
			<c:if test="${kmsCommonRecycleLog.fdOperateName eq 'recycle'}">
				<bean:message bundle="kms-common" key="kms.common.recycleLog.recycle"/>
			</c:if>
			<c:if test="${kmsCommonRecycleLog.fdOperateName eq 'recover'}">
				<bean:message bundle="kms-common" key="kms.common.recycleLog.recover"/>
			</c:if>
			<c:if test="${kmsCommonRecycleLog.fdOperateName eq 'delete'}">
				<bean:message bundle="kms-common" key="kms.common.recycleLog.delete"/>
			</c:if>
		</list:data-column>
		<%-- 文档标题 --%>
		<list:data-column style="width:35%;text-align:center" property="operateDocSubject" title="${ lfn:message('kms-common:kmsCommonRecycleLog.operateDocSubject') }">
		</list:data-column>
		<%-- 操作时间 --%>
		<list:data-column col="fdOperateTime" title="${lfn:message('kms-common:kmsCommonRecycleLog.fdOperateTime')}">
			<kmss:showDate value="${kmsCommonRecycleLog.fdOperateTime }" type="date"></kmss:showDate>
		</list:data-column>
		<%-- 操作者 --%>
		<list:data-column property="fdOperator.fdName" title="${ lfn:message('kms-common:kmsCommonRecycleLog.fdOperatorName') }">
		</list:data-column>
	</list:data-columns>
	<list:data-paging page="${queryPage}" />
</list:data>