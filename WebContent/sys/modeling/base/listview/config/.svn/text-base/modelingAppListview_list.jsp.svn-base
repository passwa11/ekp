<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="modelingAppListview" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<%-- <list:data-column headerClass="width50" property="fdOrder" title="${ lfn:message('sys-modeling-base:modelingAppListview.fdOrder') }">
		</list:data-column> --%>
		<!-- 名称 -->
		<list:data-column property="fdName" title="${ lfn:message('sys-modeling-base:modelingAppListview.fdName') }" style="min-width:180px">
		</list:data-column>
		<!-- 创建人 -->
		<list:data-column col="docCreator" title="${ lfn:message('sys-modeling-base:modelingAppListview.docCreator') }" escape="false">
		 	<ui:person personId="${modelingAppListview.docCreator.fdId}" personName="${modelingAppListview.docCreator.fdName}"></ui:person>
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column col="docCreateTime" title="${ lfn:message('sys-modeling-base:modelingAppListview.docCreateTime') }">
		    <kmss:showDate value="${modelingAppListview.docCreateTime}" type="date"/>
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>