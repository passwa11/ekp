<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
  
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"图表设置",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5,defaultNode;
	n1 = LKSTree.treeRoot;

	<kmss:ifModuleExist path="/dbcenter/echarts/">
		<!-- 统计图表 -->
		defaultNode = n1.AppendURLChild(
		"<bean:message key="table.dbEchartsChart" bundle="dbcenter-echarts"/>",
		"<c:url
			value="/dbcenter/echarts/db_echarts_chart/index.jsp?fdKey=${param.fdId}&fdModelName=com.landray.kmss.sys.modeling.base.model.ModelingAppModel"/>"
		);

		<!-- 统计列表 -->
		n2 = n1.AppendURLChild(
		"<bean:message key="table.dbEchartsTable" bundle="dbcenter-echarts"/>",
		"<c:url
			value="/dbcenter/echarts/db_echarts_table/index_db_echarts_table.jsp?fdKey=${param.fdId}&fdModelName=com.landray.kmss.sys.modeling.base.model.ModelingAppModel"/>"
		);
		<!-- 统计图表集 -->
		n2 = n1.AppendURLChild(
		"<bean:message key="table.dbEchartsChartSet" bundle="dbcenter-echarts"/>",
		"<c:url
			value="/dbcenter/echarts/db_echarts_chart_set/index.jsp?fdKey=${param.fdId}&fdModelName=com.landray.kmss.sys.modeling.base.model.ModelingAppModel"/>"
		);
	</kmss:ifModuleExist>
	

	LKSTree.ExpandNode(n2);

	LKSTree.EnableRightMenu();
	LKSTree.Show();
	setTimeout(function(){
			LKSTree.ClickNode(defaultNode);
		},100);	
}
    </template:replace>
</template:include>