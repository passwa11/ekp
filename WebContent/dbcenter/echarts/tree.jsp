<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
﻿<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
<!-- 构建图表中心菜单树 -->
function generateTree()
{
	LKSTree = new TreeView("LKSTree","<bean:message key="module.dbcenter.piccenter" bundle="dbcenter-echarts"/>",document.getElementById("treeDiv"));
	var n1, n2, n3, n4, n5,defaultNode;
	n1 = LKSTree.treeRoot; // 根节点
	n1.isExpanded = true;  // 展开根节点
	
	<!-- 图表中心概览  -->
	defaultNode = n1.AppendURLChild(
		"<bean:message key="module.echarts.configuration.wizard" bundle="dbcenter-echarts" />",
		"<c:url value="/dbcenter/echarts/db_echars_ui/statistics_overview.jsp" />"
	);

	<%-- <!-- 分类设置  -->
	n1.AppendURLChild(
		"<bean:message key="table.dbEchartsTemplate" bundle="dbcenter-echarts" />",
		"<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.dbcenter.echarts.model.DbEchartsTemplate&actionUrl=/dbcenter/echarts/db_echarts_template/dbEchartsTemplate.do&formName=dbEchartsTemplateForm&mainModelName=com.landray.kmss.dbcenter.echarts.model.DbEchartsChart&docFkName=dbEchartsTemplate" />"
	); --%>
			//类别设置
	n1.AppendURLChild(
		"<bean:message key="table.dbEchartsTemplate" bundle="dbcenter-echarts" />",
		"<c:url value="/dbcenter/echarts/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.dbcenter.echarts.model.DbEchartsTemplate&actionUrl=/dbcenter/echarts/db_echarts_template/dbEchartsTemplate.do&formName=dbEchartsTemplateForm&mainModelName=com.landray.kmss.dbcenter.echarts.model.DbEchartsChart&docFkName=dbEchartsTemplate" />"
	);
	
	<!-- 主题配置  -->
	n1.AppendURLChild(
		"<bean:message key="tree.theme" bundle="dbcenter-echarts" />",
		"<c:url value="/dbcenter/echarts/dbEchartsConfig.do?method=edit" />"
	);
	
	<!-- 数据源（未开启三员管理模式的场景下才显示此配置菜单） -->
	<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
		n2 = n1.AppendURLChild(
			"<bean:message key="tree.compDbcp.label" bundle="component-dbop" />",
			"<c:url value="/component/dbop/cp" />"
		);
	<% } %>
	
	<!-- 图表编辑  -->
	var chartEdit = n1.AppendChild("<bean:message key="tree.chart" bundle="dbcenter-echarts" />");
	chartEdit.isExpanded = true;
	<!-- 自定义数据  -->
	chartEdit.AppendURLChild(
		"<bean:message key="tree.chart.custom" bundle="dbcenter-echarts" />",
		"<c:url value="/dbcenter/echarts/db_echarts_custom/index.jsp" />"
	);
	<!-- 统计图表  -->
	chartEdit.AppendURLChild(
		"<bean:message key="table.dbEchartsChart" bundle="dbcenter-echarts" />",
		"<c:url value="/dbcenter/echarts/db_echarts_chart/index.jsp" />"
	);
	<!-- 统计列表  -->
	chartEdit.AppendURLChild(
		"<bean:message key="table.dbEchartsTable" bundle="dbcenter-echarts" />",
		"<c:url value="/dbcenter/echarts/db_echarts_table/index_db_echarts_table.jsp" />"
	);
	<!-- 统计图表集  -->
	chartEdit.AppendURLChild(
		"<bean:message key="table.dbEchartsChartSet" bundle="dbcenter-echarts" />",
		"<c:url value="/dbcenter/echarts/db_echarts_chart_set/index.jsp" />"
	);
	
	
	<!-- 定时任务（未开启三员管理模式的场景下才显示此配置菜单）  -->
	<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN){ %>
		var timedTask = n1.AppendChild(
			"<bean:message key="tree.timingTask" bundle="dbcenter-echarts" />"
		);
		timedTask.isExpanded = true;
		<!-- 统计操作  -->
		timedTask.AppendURLChild(
			"<bean:message key="table.dbEchartsOperation" bundle="dbcenter-echarts" />",
			"<c:url value="/dbcenter/echarts/db_echarts_operation/index.jsp" />"
		);
		<!-- 统计任务  -->
		timedTask.AppendURLChild(
			"<bean:message key="table.dbEchartsJob" bundle="dbcenter-echarts" />",
			"<c:url value="/dbcenter/echarts/db_echarts_job/index.jsp" />"
		);
	<% } %>
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	setTimeout(function(){
		LKSTree.ClickNode(defaultNode);
	},100);
}
	</template:replace>
</template:include>