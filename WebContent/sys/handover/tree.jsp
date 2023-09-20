﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView("LKSTree", "<bean:message bundle="sys-handover" key="module.sys.handover"/>", document.getElementById("treeDiv"));
	var n1, n2, defaultNode;
	n1 = LKSTree.treeRoot;
	
	//========== 交接类型  ==========
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-handover" key="sysHandoverConfigMain.handoverType" />"
	);
	
	//========== 在途的流程  ==========
	defaultNode = n2.AppendURLChild(
		"<bean:message bundle="sys-handover" key="sysHandoverConfigMain.handoverType.doc" />",
		"<c:url value="/sys/handover/config.jsp?type=doc" />"
	);
	//========== 分类与模板 ==========
	n2.AppendURLChild(
		"<bean:message bundle="sys-handover" key="sysHandoverConfigMain.handoverType.config" />",
		"<c:url value="/sys/handover/config.jsp?type=config" />"
	);
	//========== 文档中权限 ==========
	n2.AppendURLChild(
		"<bean:message bundle="sys-handover" key="sysHandoverConfigMain.handoverType.auth" />",
		"<c:url value="/sys/handover/config.jsp?type=auth" />"
	);
	//========== 事项交接 ==========
	n2.AppendURLChild(
		"<bean:message bundle="sys-handover" key="sysHandoverConfigMain.handoverType.item" />",
		"<c:url value="/sys/handover/config.jsp?type=item" />"
	);
	//========== 权限交接查询 ==========
	n2.AppendURLChild(
		"<bean:message bundle="sys-handover" key="sysHandoverConfigMain.handoverType.auth.query" />",
		"<c:url value="/sys/handover/sys_handover_auth/sysHandoverAuthLogDetail_index.jsp" />"
	);
	
	<kmss:auth requestURL="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.handover.model.SysHandoverTaskSetting">
	//========== 交接任务设置 ==========
	n1.AppendURLChild(
		"<bean:message bundle="sys-handover" key="sysHandoverConfigMain.task.setting" />",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.handover.model.SysHandoverTaskSetting" />"
	);
	</kmss:auth>
	
	LKSTree.ExpandNode(n2);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>