<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sys.profile.notify.mail" bundle="sys-profile" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:authShow roles="ROLE_NOTIFYTODO_MNG;SYSROLE_SYSADMIN">
	//========== 邮箱设置 ==========
	defaultNode = n2 = n1.AppendURLChild(
        "<bean:message key="sysNotifyTodo.mail.config" bundle="sys-notify" />",
		"<c:url  value="/sys/notify/sysNotifyMailSetting.do?method=edit&modelName=com.landray.kmss.sys.notify.model.SysNotifyMailSetting" />"
	);
	</kmss:authShow>
	<kmss:authShow roles="ROLE_NOTIFYTODO_MNG;SYSROLE_SYSADMIN">
		//========== 邮箱列表 ==========
		defaultNode = n2 = n1.AppendURLChild(
		"<bean:message key="sysNotifyTodo.mail.config.list" bundle="sys-profile" />",
		"<c:url  value="/sys/profile/email_info/index.jsp" />"
		);
	</kmss:authShow>

	
	<kmss:auth requestURL="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.notify.model.SysNotifyMailConfig" requestMethod="GET">
	//========== 温馨提示内容 ==========
	n3 = n1.AppendURLChild(
		"<bean:message key="sysNotifyTodo.tip.config" bundle="sys-notify" />",
		"<c:url  value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.notify.model.SysNotifyMailConfig" />"
	);	
	</kmss:auth>
	
	<kmss:auth requestURL="/sys/notify/tools/mailTest_commontools.jsp" requestMethod="GET">
	//========== 邮件调试工具 ==========
	n4 = n1.AppendURLChild(
		"<bean:message key="sys.profile.notify.mail.test" bundle="sys-profile" />",
		"<c:url  value="/sys/notify/tools/mailTest_commontools.jsp" />"
	);	
	</kmss:auth>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>