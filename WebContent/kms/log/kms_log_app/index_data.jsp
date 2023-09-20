<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmsLogApp" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column col="fdCreateTime" title="${ lfn:message('kms-log:kmsLogApp.fdCreateTime') }">
			<kmss:showDate value="${kmsLogApp.fdCreateTime }" ></kmss:showDate>
		</list:data-column>
		<list:data-column col="fdOperatorName" title="${ lfn:message('kms-log:kmsLogApp.fdOperatorName') }">
			<c:out value="${kmsLogApp.fdOperatorName}"></c:out> 
		</list:data-column>
		<list:data-column col="operateText" title="${ lfn:message('kms-log:kmsLogApp.operateText') }">
			<c:out value="${kmsLogApp.operateText}"></c:out>
		</list:data-column>
		<list:data-column col="moduleName" title="${ lfn:message('kms-log:kmsLogApp.moduleName') }">
			<c:out value="${kmsLogApp.moduleName}"></c:out>
		</list:data-column>
		<list:data-column col="fdIp" title="${ lfn:message('kms-log:kmsLogApp.fdIp') }">
			<c:out value="${kmsLogApp.fdIp}"></c:out>
		</list:data-column>
		<list:data-column col="fdSubject" title="${ lfn:message('kms-log:kmsLogApp.fdSubject') }">
			<c:out value="${kmsLogApp.fdSubject}"></c:out>
		</list:data-column>
		<list:data-column col="fdTargetId" title="${ lfn:message('kms-log:kmsLogApp.fdTargetId') }">
			<c:out value="${kmsLogApp.fdTargetId}"></c:out>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>
