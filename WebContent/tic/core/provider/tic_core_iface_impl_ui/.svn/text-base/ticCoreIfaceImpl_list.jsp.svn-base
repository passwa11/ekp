<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="ticCoreIfaceImpl" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--接口实现名称--%>
		<list:data-column col="fdName" title="${ lfn:message('tic-core-provider:ticCoreIfaceImpl.fdName') }" style="text-align:center" escape="false">
			<span class="com_subject"><c:out value="${ticCoreIfaceImpl.fdName}" /></span>
		</list:data-column>
		<%--实现函数名称--%>
		<list:data-column col="fdImplRefName" title="${ lfn:message('tic-core-provider:ticCoreIfaceImpl.fdImplRefName') }" escape="false" style="text-align:center;">
			<c:out value="${ticCoreIfaceImpl.fdImplRefName}" />
		</list:data-column>
		<%--实现函数名称--%>
		<list:data-column col="ticCoreIface.fdId" title="${ lfn:message('tic-core-provider:ticCoreIfaceImpl.ticCoreIface') }" escape="false" style="text-align:center;">
			<c:out value="${ticCoreIfaceImpl.ticCoreIface.fdIfaceName}" />
		</list:data-column>
		
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
