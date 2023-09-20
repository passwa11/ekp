<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmSimulationExample" list="${queryPage.list }" varIndex="status">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--序号--%>
		<list:data-column col="index">${status+1 }</list:data-column>
		<%--模板名称--%>
		<list:data-column property="fdTemplateName" title="${ lfn:message('sys-lbpmservice-support:lbpmSimulationExample.fdTemplateName') }" style="text-align:left">
		</list:data-column>
		<!-- 仿真实例标题 -->
		<list:data-column  property="fdTitle" title="${ lfn:message('sys-lbpmservice-support:lbpmSimulationExample.fdTitle') }" style="text-align:left;min-width:120px">
		</list:data-column>
		<%--创建人--%>
		<list:data-column headerStyle="width:120px;"  col="docCreator" title="${ lfn:message('sys-lbpmservice-support:lbpmSimulationExample.docCreator') }" escape="false">
			<ui:person personId="${lbpmSimulationExample.docCreator.fdId}" personName="${lbpmSimulationExample.docCreator.fdName}"></ui:person>
		</list:data-column>
		<%--创建时间--%>
		<list:data-column  headerStyle="width:120px;" property="fdCreateTime" title="${ lfn:message('sys-lbpmservice-support:lbpmSimulationExample.fdCreateTime') }">
			<kmss:showDate value="${lbpmSimulationExample.fdCreateTime}" type="date" />
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>