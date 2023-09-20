<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmsLogSysConfig" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId" >
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column col="fdCreateTime"   title="${ lfn:message('kms-log:kmsLogSysConfig.fdCreateTime') }" >
			<kmss:showDate value="${kmsLogSysConfig.fdCreateTime }" ></kmss:showDate>
		</list:data-column>
		<list:data-column col="fdCreatorName"    title="${ lfn:message('kms-log:kmsLogSysConfig.fdCreatorName') }">
			<c:out value="${kmsLogSysConfig.fdCreator.fdName}"></c:out> 
		</list:data-column>
		<list:data-column col="fdOprateMethod"    title="${ lfn:message('kms-log:kmsLogSysConfig.fdOprateMethod') }">
			<c:out value="${kmsLogSysConfig.fdOprateMethod}"></c:out>
		</list:data-column>
		<list:data-column col="fdModuleName"   title="${ lfn:message('kms-log:kmsLogSysConfig.fdModuleName') }">
			<c:out value="${kmsLogSysConfig.fdModuleName}"></c:out>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>
