<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.kms.medal" bundle="kms-medal"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;
	
	<%-- 勋章分类 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.kmsMedalCategory" bundle="kms-medal" />",
		"<c:url value="/kms/medal/kms_medal_category/kmsMedalCategory_list.jsp" />"
	);
	<%-- 勋章维护 --%>
	n2 = n1.AppendURLChild("<bean:message key="kmsMedalCategory.medal" bundle="kms-medal" />")
	n2.authType="01";
	n2.AppendSimpleCategoryData("com.landray.kmss.kms.medal.model.KmsMedalCategory","<c:url value="/kms/medal/kms_medal_main/kmsMedalMain_list.jsp?categoryId=!{value}" />");

	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
	</template:replace>
</template:include>
