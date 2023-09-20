<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<list:data-columns var="ticCoreMappingModule" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--标题--%>
		<list:data-column col="fdModuleName" title="${ lfn:message('tic-core-mapping:ticCoreMappingModule.fdModuleName') }" style="text-align:center" escape="false">
			<span class="com_subject"><c:out value="${ticCoreMappingModule.fdModuleName}" /></span>
		</list:data-column>
		<%--模版名称--%>
		<list:data-column col="fdTemplateName" title="${ lfn:message('tic-core-mapping:ticCoreMappingModule.fdTemplateName') }" escape="false" style="text-align:center;">
			<c:out value="${ticCoreMappingModule.fdTemplateName}" />
		</list:data-column>
		<list:data-column col="fdMainModelName" title="${ lfn:message('tic-core-mapping:ticCoreMappingModule.fdMainModelName') }" escape="false" style="text-align:center;">
			<c:out value="${ticCoreMappingModule.fdMainModelName}" />
		</list:data-column>
		<list:data-column col="fdCate" title="${ lfn:message('tic-core-mapping:ticCoreMappingModule.cate.type') }">
			<sunbor:enumsShow value="${ticCoreMappingModule.fdCate}" enumsType="ticCoreMappingModule_cate"  />
		</list:data-column>
		<list:data-column col="fdUse" title="${ lfn:message('tic-core-mapping:ticCoreMappingModule.fdUse') }">
			<sunbor:enumsShow value="${ticCoreMappingModule.fdUse}" enumsType="common_yesno"  />
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
