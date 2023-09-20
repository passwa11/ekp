﻿<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.third.ding" bundle="third-ding"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;
	
	<%-- 组织架构检查 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="third.ding.oms.check" bundle="third-ding"/>",
		"<c:url value="/third/ding/oms2DingCheck.do?method=check" />"
	);
	
	<%-- 同步异常数据 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.thirdDingOmsError.org" bundle="third-ding"/>",
		"<c:url value="/third/ding/third_ding_oms_error/index.jsp" />"
	);
	
	<%-- 钉钉部门中间映射表 --%>	
	n2 = n1.AppendURLChild(
		"<bean:message key="other.dept.relation" bundle="third-ding" />",
		"<c:url value="/third/ding/oms_relation_model/omsRelationModel.do?method=list&type=dept" />"
	);
	<%-- 组织初始化 --%>
	n3 = n2.AppendURLChild(
		"<bean:message key="other.init" bundle="third-ding" />",
		"<c:url value="/third/ding/third_ding_oms_init/thirdDingOmsInit.do?method=list&fdIsOrg=1" />"
	);
	<%-- 钉钉人员中间映射表 --%>	
	n4 = n1.AppendURLChild(
		"<bean:message key="other.person.relation" bundle="third-ding" />",
		"<c:url value="/third/ding/oms_relation_model/omsRelationModel.do?method=list&type=person" />"
	);
	<%-- 人员初始化 --%>
	n5 = n4.AppendURLChild(
		"<bean:message key="other.init" bundle="third-ding" />",
		"<c:url value="/third/ding/third_ding_oms_init/thirdDingOmsInit.do?method=list&fdIsOrg=0" />"
	);
	<%-- 同步日志 --%>
	n6 = n1.AppendURLChild(
		"<bean:message key="dingsync.log" bundle="third-ding" />",
		"<c:url value="" />"
	);
	<%-- 钉钉回调日志 --%>
	n7 = n6.AppendURLChild(
		"<bean:message key="ding.callback.log" bundle="third-ding" />",
		"<c:url value="/third/ding/third_ding_callback_log/index.jsp" />"
	);
	

	
	LKSTree.EnableRightMenu();
	LKSTree.ExpandNode(n2);
	LKSTree.ExpandNode(n4);
	LKSTree.ExpandNode(n7);
	LKSTree.Show();
}
	</template:replace>
</template:include>