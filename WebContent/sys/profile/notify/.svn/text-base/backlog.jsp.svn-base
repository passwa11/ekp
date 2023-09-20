<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.notify.actions.SysNotifyTodoAction"%>
<%@ page import="com.landray.kmss.sys.notify.mk.MKNotifyUtils"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sys.profile.notify.notify" bundle="sys-profile" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:auth requestURL="/sys/notify/sys_notify_todo/index.jsp">
	//========== 待办查询 ==========
	n2 = n1.AppendURLChild(
        "<bean:message key="sys.profile.notify.query" bundle="sys-profile" />"
	);
	</kmss:auth>
	
	//========== 消息自定义 ==========
	<kmss:auth requestURL="/sys/notify/sys_notify_lang/selfTitleIndex.jsp">	
	n3 = n1.AppendURLChild(
		"<bean:message key="sysNotifySelfTitleSetting.self.notify" bundle="sys-notify" />",
		"<c:url  value="/sys/notify/sys_notify_lang/selfTitleIndex.jsp" />"
	);	
	</kmss:auth>

	<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifyConfig.do?method=edit&modelName=com.landray.kmss.sys.notify.model.SysNotifyConfig">
	//========== 参数配置 ==========
	n4 = n1.AppendURLChild(
		"<bean:message key="sysNotifyTodo.param.config" bundle="sys-notify" />",
		"<c:url  value="/sys/notify/sys_notify_todo/sysNotifyConfig.do?method=edit&modelName=com.landray.kmss.sys.notify.model.SysNotifyConfig" />"
	);
	</kmss:auth>
	
	<kmss:auth requestURL="/sys/notify/sys_notify_category/index.jsp">
	//========== 聚合成业务分类 ==========
	n5 = n1.AppendURLChild(
		"<bean:message key="table.sysNotifyCategory" bundle="sys-notify" />",
		"<c:url  value="/sys/notify/sys_notify_category/index.jsp" />"
	);
	</kmss:auth>
	
	//========== 发送到MK待办列表  ==========
	<% 
		if(MKNotifyUtils.getMKNotifyTodoAble()){
	%>
	<kmss:auth requestURL="/sys/notify/sys_notify_todo/index.jsp">
	n5 = n1.AppendURLChild(
		"<bean:message key="table.sysNotifyMKRequest" bundle="sys-notify" />",
		"<c:url  value="/sys/notify/sys_notify_mkrequest/index.jsp" />"
	);
	</kmss:auth>
	<%
		}
	%>
	
	
	//================================== 二级目录 =====================================//	
	
	
	<%-- 待办查询    二级目录 --%>
	<kmss:auth requestURL="/sys/notify/sys_notify_todo/index.jsp">
	//========== 待办事项 ==========
	defaultNode = n3 = n2.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.doing.item"/>",
		"<c:url value="/sys/notify/sys_notify_todo/index.jsp?oprType=doing&owner=false" />"
	);
	<%-- 
	n4 = n3.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.cate"/>",
		"<c:url value="/sys/notify/sys_notify_todo/index.jsp?oprType=doing&owner=false" />"
	);
	n4.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.cate.audit"/>",
		"<c:url value="/sys/notify/sys_notify_todo/index.jsp?oprType=doing&fdType=13&owner=false" />"
	);
	n4.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.cate.copyto"/>",
		"<c:url value="/sys/notify/sys_notify_todo/index.jsp?oprType=doing&fdType=2&owner=false" />"
	);

	n4 = n3.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.app"/>",
		"<c:url value="/sys/notify/sys_notify_todo/index.jsp?oprType=doing&owner=false" />"
	);
	n4.AppendBeanData('sysNotifyModuleCateTreeService&oprType=doing&isAdmin=true&fdAppName=!{value}','<c:url value="/sys/notify/sys_notify_todo/index.jsp?owner=false&oprType=doing&_fdAppName=!{value}&_fdModelName=!{value}" />');
	
	//=========待办按部门========
	n4 = n3.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.org"/>"
	);
	
	n4.AppendOrgData(
		ORG_TYPE_PERSON,
		"<c:url value="/sys/notify/sys_notify_todo/index.jsp?oprType=doing&owner=false&userId=!{value}" />"
	);
	 --%>
	
	//========== 已办事项 ==========
	n3 = n2.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.done.item"/>",
		"<c:url value="/sys/notify/sys_notify_todo/index.jsp?oprType=done&owner=false" />"
	);
	<%-- 
	n4 = n3.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.cate"/>",
		"<c:url value="/sys/notify/sys_notify_todo/index.jsp?oprType=done&owner=false" />"
	);
	n4.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.cate.audit"/>",
		"<c:url value="/sys/notify/sys_notify_todo/index.jsp?oprType=done&fdType=13&owner=false" />"
	);
	n4.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.cate.copyto"/>",
		"<c:url value="/sys/notify/sys_notify_todo/index.jsp?oprType=done&fdType=2&owner=false" />"
	);
	n4 = n3.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.app"/>",
		"<c:url value="/sys/notify/sys_notify_todo/index.jsp?oprType=done&owner=false" />"
	);
	n4.AppendBeanData('sysNotifyModuleCateTreeService&oprType=done&isAdmin=true&fdAppName=!{value}','<c:url value="/sys/notify/sys_notify_todo/index.jsp?oprType=done&owner=false&_fdAppName=!{value}&_fdModelName=!{value}" />');
	
	//=========已办按部门========
	n4 = n3.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.org"/>"
	);
	
	n4.AppendOrgData(
		ORG_TYPE_PERSON,
		"<c:url value="/sys/notify/sys_notify_todo/index.jsp?oprType=done&owner=false&userId=!{value}" />"
	);
	 --%>
	</kmss:auth>
	
	LKSTree.ExpandNode(n2);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>