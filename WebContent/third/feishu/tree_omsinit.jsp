﻿<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.third.feishu" bundle="third-feishu"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;
	
	n2 = n1.AppendURLChild(
		"<bean:message key="dept.relation" bundle="third-feishu" />",
		"<c:url value="/third/feishu/third_feishu_dept_mapping/list.jsp" />"
	);
	n3 = n2.AppendURLChild(
		"<bean:message key="dept.init.no.mapping" bundle="third-feishu" />",
		"<c:url value="/third/feishu/third_feishu_dept_no_mapping/list.jsp" />"
	);
	n4 = n1.AppendURLChild(
		"<bean:message key="person.relation" bundle="third-feishu" />",
		"<c:url value="/third/feishu/third_feishu_person_mapping/list.jsp" />"
	);
	n5 = n4.AppendURLChild(
		"<bean:message key="person.init.no.mapping" bundle="third-feishu" />",
		"<c:url value="/third/feishu/third_feishu_person_no_mapp/list.jsp" />"
	);
	

	
	LKSTree.EnableRightMenu();
	LKSTree.ExpandNode(n2);
	LKSTree.ExpandNode(n4);
	LKSTree.Show();
}
	</template:replace>
</template:include>