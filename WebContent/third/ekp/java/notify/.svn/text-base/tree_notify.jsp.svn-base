﻿<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="ekpj.notify" bundle="third-ekp-java-notify"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;
	
	n2 = n1.AppendURLChild(
		"<bean:message key="table.thirdEkpJavaNotifyLog" bundle="third-ekp-java-notify" />",
		"<c:url value="/third/ekp/java/notify/third_ekp_java_notify_log/list.jsp" />"
	);
	<%-- 
	n3 = n1.AppendURLChild(
		"<bean:message key="table.thirdEkpJavaNotifyMapp" bundle="third-ekp-java-notify" />",
		"<c:url value="/third/ekp/java/notify/third_ekp_java_notify_mapp/list.jsp" />"
	);
	--%>
	n4 = n1.AppendURLChild(
		"<bean:message key="table.thirdEkpJavaNotifyQueErr" bundle="third-ekp-java-notify" />",
		"<c:url value="/third/ekp/java/notify/third_ekp_java_notify_que_err/list.jsp" />"
	);
	
	
	n1.AppendURLChild(
        "待办补偿工具",
        '<c:url value="/third/ekp/java/notify/cleaning_tool.jsp"/>');
	
	LKSTree.EnableRightMenu();
	//LKSTree.ExpandNode(n2);
	//LKSTree.ExpandNode(n4);
	LKSTree.Show();
}
	</template:replace>
</template:include>