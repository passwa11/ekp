<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmSimulationPlan" list="${queryPage.list }" varIndex="status">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--序号--%>
		<list:data-column col="index">${status+1 }</list:data-column>
		<%--计划名称--%>
		<list:data-column property="fdPlanName" title="${ lfn:message('sys-lbpmservice-support:lbpmSimulationPlan.fdPlanName') }" style="text-align:left">
		</list:data-column>
		<%--创建人--%>
		<list:data-column headerStyle="width:120px;"  col="docCreator" title="${ lfn:message('sys-lbpmservice-support:lbpmSimulationPlan.docCreator') }" escape="false">
			<ui:person personId="${lbpmSimulationPlan.docCreator.fdId}" personName="${lbpmSimulationPlan.docCreator.fdName}"></ui:person>
		</list:data-column>
		<%--创建时间--%>
		<list:data-column  headerStyle="width:120px;" property="fdCreateTime" title="${ lfn:message('sys-lbpmservice-support:lbpmSimulationPlan.fdCreateTime') }">
			<kmss:showDate value="${lbpmSimulationPlan.fdCreateTime}" type="date" />
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>