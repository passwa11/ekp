<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.notify.actions.SysNotifyTodoAction"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sysFtsearch.ftsearch.logs" bundle="sys-ftsearch-expand" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:authShow roles="ROLE_SYSFTSEARCHEXPAND_MAINTAINER,ROLE_SYSFTSEARCHEXPAND_USELESS">
    <%-- 用户搜索日志表 --%>
    defaultNode = n2 = n1.AppendURLChild(
        "<bean:message key="table.sysFtsearchWord" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sys_ftsearch_word/sysFtsearchWord.do?method=list" />"
    );
    </kmss:authShow>
	
	LKSTree.ExpandNode(n2);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>