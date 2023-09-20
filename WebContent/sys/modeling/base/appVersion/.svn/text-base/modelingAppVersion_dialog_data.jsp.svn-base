<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="modelingAppVersion" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 状态val -->
		<list:data-column property="fdStatus"></list:data-column>
		<!-- 应用ID -->
		<list:data-column col="fdApplicationId">
			<c:out value="${modelingAppVersion.fdApplication.fdId}"/>
		</list:data-column>
		<!-- 版本 -->
		<list:data-column col="fdVersion" title="${ lfn:message('sys-modeling-base:modelingAppVersion.fdVersion') }" escape="false">
			<c:out value="V${modelingAppVersion.fdVersion}.0"/>
		</list:data-column>
		<!-- 创建人 -->
		<list:data-column col="fdCreator" title="${ lfn:message('sys-modeling-base:modelingAppVersion.fdCreator') }" escape="false">
		 	<c:out value="${modelingAppVersion.fdCreator.fdName}"/>
		</list:data-column>
		<!-- 发布时间 -->
		<list:data-column col="fdPublishTime" title="${ lfn:message('sys-modeling-base:modelingAppVersion.fdPublishTime') }">
			<kmss:showDate value="${modelingAppVersion.fdPublishTime}" type="datetime"/>
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>