<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.sys.time" bundle="sys-time" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<%-- 排班安排 --%>
	defaultNode = n1.AppendURLChild(
		"<bean:message key="sysTime.resule" bundle="sys-time" />",
		"<c:url value="/sys/time/sys_time_area/index.jsp" />"
	);
	
	<%-- 通用班次设置 --%>
	<kmss:authShow roles="ROLE_SYS_TIME_TIMECOMMON_EDIT">
	n5 = n1.AppendURLChild(
		"<bean:message key="sysTimeCommonTime.commonTime" bundle="sys-time" />",
		"<c:url value="/sys/time/sys_time_common_time/sysTimeCommonTime_list.jsp" />"
	); 
	</kmss:authShow>
	
	<%-- 节假日设置 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysTimeHoliday" bundle="sys-time" />",
		"<c:url value="/sys/time/sys_time_holiday/index.jsp" />"
	); 
	
	<%-- 假期管理 --%>
	n3 = n1.AppendURLChild(
		"<bean:message key="sysTimeLeaveRule.tree.leaveManage" bundle="sys-time" />"
	);
	<%-- 假期类型 --%>
	<kmss:authShow roles="ROLE_SYS_TIME_LEAVERULE_ADMIN">
	n4 = n3.AppendURLChild(
		"<bean:message key="sysTimeLeaveRule.tree.leaveTypeManage" bundle="sys-time" />",
		"<c:url value="/sys/time/sys_time_leave_rule/index.jsp" />"
	); 
	</kmss:authShow>
	<%-- 假期额度--%>
	n4 = n3.AppendChild(
		"<bean:message key="sysTimeLeaveRule.tree.leaveAmountManage" bundle="sys-time" />", null,
		function(){
			top.open("<c:url value="/sys/time/#j_path=%2FleaveAmount" />");
		}
	);
	<%-- 假期明细--%>
	n4 = n3.AppendChild(
		"<bean:message key="sysTimeLeaveRule.tree.leaveAmountDetail" bundle="sys-time" />", null,
		function(){
			top.open("<c:url value="/sys/time/#j_path=%2FleaveDetail" />");
		}
	); 
	<%-- 假期通用参数--%>
	<kmss:authShow roles="ROLE_SYS_TIME_LEAVERULE_ADMIN">
	n3.AppendURLChild(
		"<bean:message key="sysTimeLeaveConfig.appconfig" bundle="sys-time" />",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.time.model.SysTimeLeaveConfig" />"
	); 
	</kmss:authShow>

	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	var type = Com_GetUrlParameter(window.top.location.href,'type');
	if(type == 'attend'){
		LKSTree.ClickNode(n2);
	}else if(type == 'leaveRule'){
		LKSTree.ClickNode(n4);
	}else {
		LKSTree.ClickNode(defaultNode);
	}
}
	</template:replace>
</template:include>