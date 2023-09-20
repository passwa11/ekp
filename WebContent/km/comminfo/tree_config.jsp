<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView("LKSTree", "<bean:message  bundle="km-comminfo" key="table.kmComminfoMain"/>", document.getElementById("treeDiv"));
	var n1, n2;
	n1 = LKSTree.treeRoot;	
	<%-- 类别 --%>	
	n2 = n1.AppendURLChild(
		"<bean:message  bundle="km-comminfo" key="kmComminfoCategory.fdId"/>",
		"<c:url value="/km/comminfo/km_comminfo_category/index.jsp?modelName=com.landray.kmss.km.comminfo.model.KmComminfoCategory" />"
	);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(n2);
}
 </template:replace>
</template:include>
