<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message bundle="sys-search" key="table.sysSearchMain"/>",
		document.getElementById("treeDiv")
	);
	var n1;
	n1 = LKSTree.treeRoot;
	var modelName = Com_GetUrlParameter(location.href, "fdModelName");
	// V13新增后台管理模块，可以选择性的移除页面“关闭”按钮，当canClose为false时，不显示关闭按钮
	var canClose = Com_GetUrlParameter(location.href, "canClose");
	if(canClose != "false") {
		canClose = "true";
	}
	var url = "<c:url value="/sys/search/search.do?method=condition" />&searchId=!{value}&fdModelName="+modelName+"&canClose="+canClose;
	n1.AppendBeanData("sysSearchConfigTree&fdModelName="+modelName, url, null, false);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
	</template:replace>
</template:include>