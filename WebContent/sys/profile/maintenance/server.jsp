<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.cluster.interfaces.ClusterDiscover"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sys.profile.maintenance.server" bundle="sys-profile" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:authShow roles="SYSROLE_ADMIN;SYSROLE_SYSADMIN">
	//========== 子系统信息 ==========
	<% if(ClusterDiscover.getInstance().isMultiSystemEnabled()){ %>
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-cluster" key="table.sysClusterGroup"/>",
		"<c:url value="/sys/cluster/sys_cluster_group/index.jsp"/>"
	);
	<% } %>
	//========== 群集节点信息 ==========
	defaultNode = n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-cluster" key="table.sysClusterServer"/>",
		"<c:url value="/sys/cluster/sys_cluster_server/index.jsp"/>"
	);
	</kmss:authShow>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>