<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
	function generateTree() {
		LKSTree = new TreeView(
			"LKSTree",
			"<bean:message key="module.sys.rule" bundle="sys-rule" />",
			document.getElementById("treeDiv")
		);
		var n1, n2, defaultNode;
		<!-- 规则中心 -->
		n1 = LKSTree.treeRoot;
		
		<!-- 规则类别 -->
		defaultNode = n2 = n1.AppendURLChild(
			"<bean:message key="tree.node.title.sysRuleSetCate" bundle="sys-rule" />",
			"<c:url value="/sys/rule/sys_ruleset_cate/index.jsp" />"
		);
		
		<!-- 规则设置-->
		n3 = n1.AppendURLChild(
			"<bean:message key="tree.node.title.sysRuleSetDoc" bundle="sys-rule" />",
			"<c:url value="/sys/rule/sys_ruleset_doc/index.jsp?category=!{value}"/>"
		);
		<%-- n3.AppendBeanData(
			Tree_GetBeanNameFromService('sysRuleSetCateService', 'fdParent', 'fdName:fdId'),
			"<c:url value="/sys/rule/sys_ruleset_doc/index.jsp?category=!{value}"/>"
		); --%>
		n3.AppendBeanData(
			"sysRuleSetCateService&parentId=!{value}&item=fdName:fdId",
			"<c:url value="/sys/rule/sys_ruleset_doc/index.jsp?category=!{value}"/>"
		);
		
		<!-- 规则模拟器 -->
		n4 = n1.AppendURLChild(
			"<bean:message key="tree.node.title.sysRuleSetRule.simulator" bundle="sys-rule" />",
			"<c:url value="/sys/rule/sys_ruleset_doc/simulator.jsp" />"
		);
		
		LKSTree.EnableRightMenu();
		LKSTree.Show();
		LKSTree.ClickNode(defaultNode);
	}
	</template:replace>
</template:include>