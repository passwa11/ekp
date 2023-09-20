<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="component" list="${queryPage.list }"
		varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="path"></list:data-column>
		<list:data-column property="fdThumb"></list:data-column>
		<list:data-column property="fdName"></list:data-column>
		<list:data-column property="thumbPath"></list:data-column>
		<list:data-column property="uiType"></list:data-column>
		<list:data-column col="rowSize">${queryPage.rowsize }</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>