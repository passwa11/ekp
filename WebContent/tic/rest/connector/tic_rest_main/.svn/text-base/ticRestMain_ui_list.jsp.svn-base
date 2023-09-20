<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="ticRestMain" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--标题--%>
		<list:data-column col="fdName" title="${ lfn:message('tic-rest-connector:ticRestMain.docSubject') }" style="text-align:center" escape="false">
			<span class="com_subject"><c:out value="${ticRestMain.fdName}" /></span>
		</list:data-column>
		<list:data-column col="ticRestSetting.fdName" title="${ lfn:message('tic-rest-connector:ticRestMain.fdServerSetting') }" escape="false" style="text-align:center;">
			<c:out value="${ticRestMain.ticRestSetting.docSubject}" />
		</list:data-column>
		<list:data-column col="fdCategory.fdName" title="${ lfn:message('tic-rest-connector:ticRestMain.docCategory') }" escape="false" style="text-align:center;">
			<c:out value="${ticRestMain.fdCategory.fdName}" />
		</list:data-column>
		<list:data-column col="fdIsAvailable" title="${ lfn:message('tic-rest-connector:ticRestMain.fdIsAvailable') }" escape="false" style="text-align:center;">
			<sunbor:enumsShow value="${ticRestMain.fdIsAvailable}" enumsType="common_yesno"/>
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
