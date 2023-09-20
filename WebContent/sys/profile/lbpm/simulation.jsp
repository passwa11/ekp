<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
	//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="lbpmFlowSimulation.title" bundle="sys-lbpmservice-support" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, n6, n7, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:authShow roles="ROLE_SYS_LBPM_SIMULATION_EXAMPLE">
	
	//========== 仿真实例 ==========
	defaultNode = n2 = n1.AppendURLChild(
        "<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.example" />",
		"<c:url value="/sys/lbpmservice/support/lbpm_simulation/example/index.jsp" />"
	);
	
	//========== 仿真计划 ==========
	n3 = n1.AppendURLChild(
		"<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.plan" />",
		"<c:url value="/sys/lbpmservice/support/lbpm_simulation/plan/index.jsp" />"
	);
	
	</kmss:authShow>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>