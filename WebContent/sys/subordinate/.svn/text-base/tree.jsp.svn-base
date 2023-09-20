<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.tree">
    <template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message bundle="sys-subordinate" key="module.sys.subordinate"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, defaultNode;
	n1 =  LKSTree.treeRoot;
	
	//========== 模块参数设置 ==========
	<kmss:authShow roles="ROLE_SYS_SUBORDINATE_BACKSTAGE_MANAGER">
	defaultNode = n1.AppendURLChild(
		"<bean:message key="subordinate.config" bundle="sys-subordinate" />",
		"<c:url value="/sys/subordinate/sysSubordinateMappingForm.do?method=edit" />"
	);
	</kmss:authShow>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
 </template:replace>
</template:include>