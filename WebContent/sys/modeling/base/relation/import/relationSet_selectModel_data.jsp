<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="modelingAppModel" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdName" title="名称" style="text-align:left;min-width:180px" escape="false">
			<span class="com_subject"><c:out value="${modelingAppModel.fdName}" /></span>
		</list:data-column>
		<list:data-column col="fdAppName" title="名称" style="text-align:left;min-width:180px" escape="false">
			<span class="com_subject"><c:out value="${modelingAppModel.fdApplication.fdAppName}" /></span>
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>