<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sysEvaluationMain.manager" bundle="sys-evaluation"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<%-- 点评总览 --%>
	<kmss:authShow roles="ROLE_SYSEVALUATION_MANAGER">
	defaultNode = n2 = n1.AppendURLChild(
		"<bean:message key="sysEvaluationMain.overView" bundle="sys-evaluation" />",
		"<c:url value="/sys/evaluation/import/overView_index.jsp?fdModelName=com.landray.kmss.sys.evaluation.model.SysEvaluationMain"/>"
	);
	</kmss:authShow>
	<%-- 段落点评 --%>
	<kmss:authShow roles="ROLE_SYSEVALUATION_MANAGER">
	n3 = n1.AppendURLChild(
		"<bean:message bundle="sys-evaluation" key="table.sysEvaluationNotes"/>",
		"<c:url value="/sys/evaluation/import/notesView_index.jsp?fdModelName=com.landray.kmss.sys.evaluation.model.SysEvaluationMain"/>"); 
	</kmss:authShow>
	<%-- 点评基础设置 --%>
	<kmss:authShow roles="ROLE_SYSEVALUATION_MANAGER">
		n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-evaluation" key="sysEvaluationWords.word.setting"/>",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.evaluation.model.SysEvaluationNotesConfig" />"); 
	</kmss:authShow>
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
  </template:replace>
</template:include>