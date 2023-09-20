﻿<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.third.welink" bundle="third-welink"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;
	
	<%-- 组织架构检查 
	n2 = n1.AppendURLChild(
		"<bean:message key="third.welink.oms.check" bundle="third-welink"/>",
		"<c:url value="/third/ding/oms2DingCheck.do?method=check" />"
	);
	--%>

	
	<%-- 钉钉部门中间映射表 --%>	
	n2 = n1.AppendURLChild(
		"<bean:message key="table.thirdWelinkDeptMapping" bundle="third-welink" />",
		"<c:url value="/third/welink/third_welink_dept_mapping/list.jsp" />"
	);
	<%-- 组织初始化 
	n3 = n2.AppendURLChild(
		"<bean:message key="table.thirdWelinkDeptNoMapping" bundle="third-welink" />",
		"<c:url value="/third/welink/third_welink_dept_no_mapping/list.jsp" />"
	);
	--%>
	<%-- 钉钉人员中间映射表 --%>	
	n4 = n1.AppendURLChild(
		"<bean:message key="table.thirdWelinkPersonMapping" bundle="third-welink" />",
		"<c:url value="/third/welink/third_welink_person_mapping/list.jsp" />"
	);
	<%-- 人员初始化
	n5 = n4.AppendURLChild(
		"<bean:message key="table.thirdWelinkPersonNoMapp" bundle="third-welink" />",
		"<c:url value="/third/welink/third_welink_person_no_mapp/list.jsp" />"
	);
	 --%>


	
	LKSTree.EnableRightMenu();
	LKSTree.ExpandNode(n2);
	LKSTree.ExpandNode(n4);
	LKSTree.Show();
}
	</template:replace>
</template:include>