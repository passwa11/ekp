<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="modelingAppVersion" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 状态text -->
		<list:data-column col="fdStatusText" title="${ lfn:message('sys-modeling-base:modelingAppVersion.fdStatus') }">
			<sunbor:enumsShow value="${modelingAppVersion.fdStatus}" enumsType="modeling_app_version_status" bundle="sys-modeling-base" />
		</list:data-column>
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
		 	<ui:person personId="${modelingAppVersion.fdCreator.fdId}" personName="${modelingAppVersion.fdCreator.fdName}"/>
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column col="fdCreateTime" title="${ lfn:message('sys-modeling-base:modelingAppVersion.fdCreateTime') }">
		    <kmss:showDate value="${modelingAppVersion.fdCreateTime}" type="datetime"/>
		</list:data-column>
		<!-- 修改人 -->
		<list:data-column col="fdModifier" title="${ lfn:message('sys-modeling-base:modelingAppVersion.fdModifier') }" escape="false">
			<ui:person personId="${modelingAppVersion.fdModifier.fdId}" personName="${modelingAppVersion.fdModifier.fdName}"/>
		</list:data-column>
		<!-- 修改时间 -->
		<list:data-column col="fdUpdateTime" title="${ lfn:message('sys-modeling-base:modelingAppVersion.fdUpdateTime') }">
			<kmss:showDate value="${modelingAppVersion.fdUpdateTime}" type="datetime"/>
		</list:data-column>
		<!-- 发布人 -->
		<list:data-column col="fdPublisher" title="${ lfn:message('sys-modeling-base:modelingAppVersion.fdPublisher') }" escape="false">
			<ui:person personId="${modelingAppVersion.fdPublisher.fdId}" personName="${modelingAppVersion.fdPublisher.fdName}"/>
		</list:data-column>
		<!-- 发布时间 -->
		<list:data-column col="fdPublishTime" title="${ lfn:message('sys-modeling-base:modelingAppVersion.fdPublishTime') }">
			<kmss:showDate value="${modelingAppVersion.fdPublishTime}" type="datetime"/>
		</list:data-column>
		<!-- 源版本 -->
		<list:data-column col="fdOriginVersion" title="${ lfn:message('sys-modeling-base:modelingAppVersion.fdOriginVersion') }">
		</list:data-column>
		<!-- 操作 -->
		<list:data-column col="operation" escape="false">
			<a href="${LUI_ContextPath}/sys/modeling/base/modelingApplication.do?method=appIndex&fdId=${modelingAppVersion.fdApplication.fdId}" target="_blank">${ lfn:message('sys-modeling-base:modeling.page.enter') }</a>
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>