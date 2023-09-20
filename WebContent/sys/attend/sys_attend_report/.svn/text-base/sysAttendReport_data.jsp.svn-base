<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysAttendReport" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column property="fdMonth" title="${ lfn:message('sys-attend:sysAttendReport.fdMonth') }">
		</list:data-column>
		<list:data-column property="docCreateTime" title="${ lfn:message('sys-attend:sysAttendReport.docCreateTime') }">
		</list:data-column>
		<list:data-column property="fdName" title="${ lfn:message('sys-attend:sysAttendReport.fdName') }">
		</list:data-column>
		<list:data-column col="docCreator.fdName" title="${ lfn:message('sys-attend:sysAttendReport.docCreator') }">
			<c:out value="${sysAttendReport.docCreator.fdName}" />
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>