<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">

//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.sys.rss" bundle="sys-rss"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2;
	n1 = LKSTree.treeRoot;
	//====分类设置===
	<kmss:authShow roles="ROLE_SYSRSS_CATEGORY;">
	n1.AppendURLChild(
		"<bean:message key="menu.rss.cateory" bundle="sys-rss" />",
		"<c:url value="/sys/rss/sys_rss_category/sysRssCategory_tree_edit.jsp" />"
	);
	</kmss:authShow>
	//====频道设置===
	n2 = n1.AppendURLChild(
		"<bean:message key="menu.rss.channel" bundle="sys-rss" />",
		"<c:url value="/sys/rss/sys_rss_main/index.jsp" />"
	);
	n2.AppendBeanData("sysRssCategoryTreeService&href=true&selectdId=!{value}");
	
	LKSTree.EnableRightMenu();	
	LKSTree.Show();
	LKSTree.ClickNode(n2);
	
}
  </template:replace>
</template:include>
