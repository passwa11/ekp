<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="ticJdbcMappManage" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--标题--%>
		<list:data-column col="docSubject" title="${ lfn:message('tic-jdbc:ticJdbcMappManage.docSubject') }" style="text-align:center" escape="false">
			<span class="com_subject"><c:out value="${ticJdbcMappManage.docSubject}" /></span>
		</list:data-column>
		<list:data-column col="fdDataSourceSql" title="${ lfn:message('tic-jdbc:ticJdbcMappManage.fdDataSourceSql') }" escape="false" style="text-align:center;">
			<c:out value="${ticJdbcMappManage.fdDataSourceSql}" />
		</list:data-column>
		<list:data-column col="fdDataSource" title="${ lfn:message('tic-jdbc:ticJdbcMappManage.fdDataSource') }" escape="false" style="text-align:center;">
			<c:out value="${dataSoure[ticJdbcMappManage.fdDataSource]}" />
		</list:data-column>
		<list:data-column col="fdTargetSource" title="${ lfn:message('tic-jdbc:ticJdbcMappManage.fdTargetSource') }" escape="false" style="text-align:center;">
			<c:out value="${dataSoure[ticJdbcMappManage.fdTargetSource]}" />
		</list:data-column>
		<list:data-column col="fdTargetSourceSelectedTable" title="${ lfn:message('tic-jdbc:ticJdbcMappManage.fdTargetSourceSelectedTable') }" escape="false" style="text-align:center;">
			<c:out value="${ticJdbcMappManage.fdTargetSourceSelectedTable}" />
		</list:data-column>
		<list:data-column col="fdIsEnabled" title="${ lfn:message('tic-jdbc:ticJdbcMappManage.fdIsEnabled') }" escape="false" style="text-align:center;">
			<sunbor:enumsShow value="${ticJdbcMappManage.fdIsEnabled}" enumsType="common_yesno"/>
		</list:data-column>
		<list:data-column col="docCategory.fdName" title="${ lfn:message('tic-jdbc:ticJdbcMappManage.docCategory') }" escape="false" style="text-align:center;">
			<c:out value="${ticJdbcMappManage.docCategory.fdName}" />
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
