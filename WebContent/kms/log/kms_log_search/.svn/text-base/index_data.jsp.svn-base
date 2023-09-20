<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="Object" list="${queryPage.list}" varIndex="status">
  		<list:data-column col="fdId">
  			<c:out value="${Object[0] }"></c:out>
		</list:data-column>  
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column col="fdCreateTime" title="${ lfn:message('kms-log:kmsLogSearch.fdCreateTime') }">
			<kmss:showDate value="${Object[1] }" ></kmss:showDate>
		</list:data-column>
		<list:data-column col="fdOperatorName" title="${ lfn:message('kms-log:kmsLogSearch.fdOperator') }">
			<c:out value="${Object[2] }"></c:out>
		</list:data-column>
		<list:data-column col="fdSearchKey" title="${ lfn:message('kms-log:kmsLogSearch.fdSearchKey') }">
			<c:out value="${Object[3] }"></c:out>
		</list:data-column>
		<list:data-column col="openDoc" title="${ lfn:message('kms-log:kmsLogSearch.openDoc') }">
			<c:out value="${Object[4] }"></c:out>
		</list:data-column>
		<list:data-column col="fdIp" title="${ lfn:message('kms-log:kmsLogSearch.fdIp') }">
			<c:out value="${Object[5] }"></c:out>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>
