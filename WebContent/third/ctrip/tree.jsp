<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.third.ctrip" bundle="third-ctrip"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;
	
	<%-- 携程配置 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="py.XieChengPeiZhi" bundle="third-ctrip"/>",
		"<c:url value="/third/ctrip/third_ctrip_config/index.jsp" />"
	);
	<%-- 机票城市数据 --%>
	n3 = n1.AppendURLChild(
		"<bean:message key="table.thirdCtripFlight.base" bundle="third-ctrip"/>",
		"<c:url value="/third/ctrip/third_ctrip_flight/index.jsp" />"
	);
	<%-- 酒店城市数据 --%>
	n4 = n1.AppendURLChild(
		"<bean:message key="table.thirdCtripHotel.base" bundle="third-ctrip"/>",
		"<c:url value="/third/ctrip/third_ctrip_hotel/index.jsp" />"
	);
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(n2);
}
</template:replace>
</template:include>