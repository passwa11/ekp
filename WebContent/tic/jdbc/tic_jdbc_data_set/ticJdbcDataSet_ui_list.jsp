<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="ticJdbcDataSet" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--标题--%>
		<list:data-column col="fdName" title="${ lfn:message('tic-jdbc:ticJdbcDataSet.docSubject') }" style="text-align:center" escape="false">
			<span class="com_subject"><c:out value="${ticJdbcDataSet.fdName}" /></span>
		</list:data-column>
		<list:data-column col="fdDataSource" title="${ lfn:message('tic-jdbc:ticJdbcDataSet.fdDataSource') }" escape="false" style="text-align:center;">
			<xform:select property="fdDataSource" style="float: left;" showStatus="view" value="${ticJdbcDataSet.fdDataSource}">
			 	<xform:beanDataSource serviceBean="compDbcpService"
					selectBlock="fdId,fdName" orderBy="" />
			</xform:select>
		</list:data-column>
		<list:data-column col="fdCategory" title="${ lfn:message('tic-jdbc:ticJdbcDataSet.docCategory') }" style="text-align:center" escape="false">
			<c:out value="${ticJdbcDataSet.fdCategory.fdName}" />
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
