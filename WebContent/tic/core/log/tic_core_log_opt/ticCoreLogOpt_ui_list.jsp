<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="ticCoreLogOpt" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<list:data-column col="fdPerson" title="${ lfn:message('tic-core-log:ticCoreLogOpt.fdPerson') }" style="text-align:center" escape="false">
			<c:out value="${ticCoreLogOpt.fdPerson}" />
		</list:data-column>
		<%--时间--%>
		<list:data-column col="fdAlertTime" title="${ lfn:message('tic-core-log:ticCoreLogOpt.fdAlertTime') }" escape="false" style="text-align:center;">
			<kmss:showDate value="${ticCoreLogOpt.fdAlertTime}" />
		</list:data-column>
		<list:data-column col="fdUrl" title="${ lfn:message('tic-core-log:ticCoreLogOpt.fdUrl') }" escape="false" style="text-align:center;">
			<c:out value="${ticCoreLogOpt.fdUrl}" />
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
