<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<list:data-columns var="sysRecycleLog" list="${queryPage.list}">
		<list:data-column property="fdId"></list:data-column>
		<list:data-column property="modelName"></list:data-column>
		<list:data-column property="docSubject" title="${ lfn:message('sys-recycle:sysRecycleLog.docSubject') }"></list:data-column>
		<list:data-column property="docDeleteTime" headerStyle="width:150px" title="${ lfn:message('sys-recycle:sysRecycleLog.fdOptDate') }"></list:data-column>
		<list:data-column property="docDeleteBy" headerStyle="width:100px" title="${ lfn:message('sys-recycle:sysRecycleLog.fdOperator') }"></list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>