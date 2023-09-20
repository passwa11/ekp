<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="ticCoreLogMain" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<list:data-column col="funcName" title="${ lfn:message('tic-core-log:ticCoreLogMain.funcName') }" style="text-align:center" escape="false">
			<span class="com_subject"><c:out value="${ticCoreLogMain.funcName}" /></span>
		</list:data-column>
		<list:data-column col="fdUrl" title="${ lfn:message('tic-core-log:ticCoreLogMain.fdUrl') }" escape="false" style="text-align:center;">
			<c:out value="${ticCoreLogMain.fdUrl}" />
		</list:data-column>
		<%--时间--%>
		<list:data-column col="fdStartTime" title="${ lfn:message('tic-core-log:ticCoreLogMain.fdStartTime') }" escape="false" style="text-align:center;">
			<kmss:showDate value="${ticCoreLogMain.fdStartTime}" />
		</list:data-column>
		<list:data-column col="fdEndTime" title="${ lfn:message('tic-core-log:ticCoreLogMain.fdEndTime') }" escape="false" style="text-align:center;">
			<kmss:showDate value="${ticCoreLogMain.fdEndTime}" />
		</list:data-column>
		 <list:data-column col="fdTimeConsuming" title="${lfn:message('tic-core-log:ticCoreLogMain.fdTimeConsuming')}(单位:ms)">
            <c:out value="${ticCoreLogMain.fdTimeConsuming}" />
        </list:data-column>
         <list:data-column col="fdTimeConsumingOrg" title="${lfn:message('tic-core-log:ticCoreLogMain.fdTimeConsumingOrg')}(单位:ms)">
            <c:out value="${ticCoreLogMain.fdTimeConsumingOrg}" />
        </list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
