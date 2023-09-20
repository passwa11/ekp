<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.notify.actions.SysNotifyTodoAction"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message bundle="sys-notify" key="table.sysNotifyTodo"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2;
	n1 = LKSTree.treeRoot;
	<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&owner=false">
	/*
		n2 = n1.AppendURLChild(
			"<bean:message bundle="sys-notify" key="sysNotifyTodo.param.config"/>",
			"<c:url value="/sys/notify/sys_notify_todo/sysNotifyConfig.do?method=edit&modelName=com.landray.kmss.sys.notify.model.SysNotifyConfig" />"
		);
	*/

	//========== 待办事项 ==========
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.doing.item"/>",
		"<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&oprType=doing&owner=false" />"
	);
	n3 = n2.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.cate"/>",
		"<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&oprType=doing&owner=false" />"
	);
	n3.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.cate.audit"/>",
		"<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&oprType=doing&fdType=13&owner=false" />"
	);
	n3.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.cate.copyto"/>",
		"<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&oprType=doing&fdType=2&owner=false" />"
	);

	n3 = n2.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.app"/>",
		"<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&oprType=doing&owner=false" />"
	);
	n3.AppendBeanData('sysNotifyModuleCateTreeService&oprType=doing&isAdmin=true&fdAppName=!{value}','<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&owner=false&oprType=doing&_fdAppName=!{value}&_fdModelName=!{value}" />');
		
	
	//=========代办按部门========
	n3 = n2.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.org"/>"
	);
	
	n3.AppendOrgData(
		ORG_TYPE_PERSON,
		"../notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&oprType=doing&owner=false&userId=!{value}"
	);
	
	//========== 已办事项 ==========
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.done.item"/>",
		"<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&oprType=done&owner=false" />"
	);
	n3 = n2.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.cate"/>",
		"<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&oprType=done&owner=false" />"
	);
	n3.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.cate.audit"/>",
		"<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&oprType=done&fdType=13&owner=false" />"
	);
	n3.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.cate.copyto"/>",
		"<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&oprType=done&fdType=2&owner=false" />"
	);
	n3 = n2.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.app"/>",
		"<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&oprType=done&owner=false" />"
	);
	n3.AppendBeanData('sysNotifyModuleCateTreeService&oprType=done&isAdmin=true&fdAppName=!{value}','<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&oprType=done&owner=false&_fdAppName=!{value}&_fdModelName=!{value}" />');
	
	//=========已办按部门========
	n3 = n2.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.org"/>"
	);
	
	n3.AppendOrgData(
		ORG_TYPE_PERSON,
		"../notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&oprType=done&owner=false&userId=!{value}"
	);
	
	// ========= 参数配置 ==========
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-notify" key="sysNotifyTodo.param.config"/>",
		"<c:url value="/sys/notify/sys_notify_todo/sysNotifyConfig.do?method=edit&modelName=com.landray.kmss.sys.notify.model.SysNotifyConfig" />"
	);

	// ========= 聚合成业务分类 ==========
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-notify" key="table.sysNotifyCategory"/>",
		"<c:url value="/sys/notify/sys_notify_category/sysNotifyCategory.do?method=list" />"
	);

	// =========消息队列出错信息 ==========
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-notify" key="table.sysNotifyQueueError"/>",
		"<c:url value="/sys/notify/queue/sysNotifyQueueError.do?method=list" />"
	);

	//========== 数据升级 ==========
	/*
	n2 = n1.AppendURLChild("<bean:message bundle="sys-notify" key="sysNotifyTodo.dataUpdate.title"/>",
		"<c:url value="/sys/notify/sysNotifyTodoData_update.jsp" />"
	);
	*/
	</kmss:auth>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	
}
<%@ include file="/resource/jsp/tree_down.jsp" %>