<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="modelingAppMobileListview" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 名称 -->
		<list:data-column property="fdName" title="${ lfn:message('sys-modeling-base:modelingAppListview.fdName') }" style="min-width:180px">
		</list:data-column>
		<!-- 创建人 -->
		<list:data-column col="docCreator" title="${ lfn:message('model.fdCreator') }" escape="false">
		 	<ui:person personId="${modelingAppMobileListview.docCreator.fdId}" personName="${modelingAppMobileListview.docCreator.fdName}"></ui:person>
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column col="docCreateTime" title="${ lfn:message('model.fdCreateTime') }">
		    <kmss:showDate value="${modelingAppMobileListview.docCreateTime}" type="date"/>
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>