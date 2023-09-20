<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<list:data-columns var="sysIassisterItem" list="${queryPage.list}"
		varIndex="status" custom="false">
		<list:data-column property="fdId" />
		<list:data-column col="index">
            ${status+1}
        </list:data-column>
		<list:data-column property="fdOrder"
			title="${lfn:message('sys-iassister:sysIassisterItem.fdOrder')}" />
		<list:data-column property="fdName"
			title="${lfn:message('sys-iassister:sysIassisterItem.fdName')}" />
		<list:data-column col="categoryName"
			title="${lfn:message('sys-iassister:sysIassisterItem.docCategory')}">
			<c:out value="${sysIassisterItem.docCategory.fdName}" />
		</list:data-column>
		<list:data-column col="ruleSetName"
			title="${lfn:message('sys-iassister:sysIassisterItem.rule')}">
			<c:out value="${sysIassisterItem.rule.fdName}" />
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>