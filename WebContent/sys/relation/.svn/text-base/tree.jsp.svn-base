<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		'<bean:message bundle="sys-relation" key="title.sysRelationMain.manager"/>',
		document.getElementById("treeDiv")
	);
	var n1 = LKSTree.treeRoot;
	
	<kmss:auth requestURL="/sys/relation/sys_relation_main/sysRelationMain.do?method=add" requestMethod="GET">
		n1.AppendURLChild(
		'<bean:message bundle="sys-relation" key="title.sysRelationMain.Template.Setting"/>',
		'<c:url value="/sys/relation/sys_relation_main/sysRelationMain.do?method=add"/>');
	</kmss:auth>
	<kmss:authShow roles="ROLE_SYSRELATION_MANAGER">
		n1.AppendURLChild(
			'<bean:message bundle="sys-relation" key="title.sysRelationForeignModule.Setting"/>',
			'<c:url value="/sys/relation/sys_relation_foreign_module/sysRelationForeignModule.do?method=list"/>');
		n1.AppendURLChild(
			'<bean:message bundle="sys-relation" key="title.sysRelationMain.overView"/>',
			'<c:url value="/sys/relation/import/sysRelationMain_over_index.jsp"/>');
		n1.AppendURLChild(
			'<bean:message bundle="sys-relation" key="sysRelationDocLog.tree"/>',
			'<c:url value="/sys/relation/sys_relation_doc_log/index.jsp"/>');
	</kmss:authShow>
	LKSTree.Show();
	// 默认选中第一个菜单项
	var defaultNode = n1.firstChild;
	if(defaultNode){
		setTimeout(function(){
			LKSTree.ClickNode(defaultNode);
		},100);	
	}
}
</template:replace>
</template:include>