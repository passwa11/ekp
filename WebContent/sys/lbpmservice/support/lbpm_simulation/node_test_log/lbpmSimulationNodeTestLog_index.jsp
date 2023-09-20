<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 仿真日志详情首页 -->
<template:include file="/sys/profile/resource/template/list.jsp">
	<p class="txttitle"><bean:message  bundle="sys-lbpmservice-support" key="table.lbpmSimulationNodeTestLog"/></p>
	<template:replace name="content">
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/lbpmservice/support/lbpm_simulation_node_test_log/lbpmSimulationNodeTestLog.do?method=findPageByLogId&logId=${param.fdId}'}
			</ui:source>
			<!-- 默认点击列表请求查看页面 -->
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable"  rowHref="">
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdApprovalStatus,fdNodeName,fdHandlerName,fdHandlerNames,fdLogMessage,fdCreateTime"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
	</template:replace>
</template:include>
