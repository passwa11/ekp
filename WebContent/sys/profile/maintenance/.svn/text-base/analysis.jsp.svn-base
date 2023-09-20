<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sys.profile.maintenance.action" bundle="sys-profile" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:auth requestURL="/sys/log/sys_log_online/sysLogOnline.do?method=list" requestMethod="GET">
	//========== 在线人数分析 ==========
	defaultNode = n2 = n1.AppendURLChild(
        "<bean:message bundle="sys-profile" key="sys.profile.maintenance.online"/>",
		"<c:url value="/sys/log/sys_log_online/index.jsp" />"
	);
	</kmss:auth>
	
	<kmss:authShow roles="SYSROLE_USER">
	n1.AppendURLChild(
        "<bean:message bundle="sys-profile-behavior" key="module.sys.profile.behavior"/>",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.profile.behavior.model.BehaviorSettingConfig" />"
	);
	</kmss:authShow>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>