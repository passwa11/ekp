<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"webservice注册",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, n6;
	n1 = LKSTree.treeRoot;
	
	//========== 系统注册服务 ==========
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysWebserviceMain" bundle="sys-webservice2" />",
		"<c:url value="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=list" />"
	);
	
	//========== 服务运行日志 ==========
	n3 = n1.AppendURLChild(
		"<bean:message key="table.sysWebserviceLog" bundle="sys-webservice2" />",
		"<c:url value="/sys/webservice2/sys_webservice_log/sysWebserviceLog.do?method=list" />"
	);
	
	//========== 超时预警 ==========
	n4 = n1.AppendURLChild(
		"<bean:message key="sysWebserviceMain.timeout" bundle="sys-webservice2" />",
		"<c:url value="/sys/webservice2/sys_webservice_log/sysWebserviceLog.do?method=timeout" />"
	);	
	
	//========== 基础设置 ==========
	n5 = n1.AppendURLChild(
		"基础设置"
	);
	
	
	//================================== 二级目录 =====================================//	
	
	
	<%-- 系统注册服务    二级目录 --%>
	<!-- 服务状态 -->
	n6 = n2.AppendURLChild("<bean:message key="sysWebserviceMain.fdServiceStatus" bundle="sys-webservice2" />");
	n6.AppendURLChild(
		"<bean:message key="sysWebserviceMain.fdServiceStatus.start" bundle="sys-webservice2" />",
		"<c:url value="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=list&status=1" />"
	);	
	n6.AppendURLChild(
		"<bean:message  key="sysWebserviceMain.fdServiceStatus.stop" bundle="sys-webservice2" />",
		"<c:url value="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=list&status=0" />"
	);	
	<!-- 启动类型 -->
	n6 = n2.AppendURLChild("<bean:message key="sysWebserviceMain.fdStartupType" bundle="sys-webservice2" />");
	n6.AppendURLChild(
		"<bean:message key="sysWebserviceMain.fdStartupType.auto" bundle="sys-webservice2" />",
		"<c:url value="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=list&type=0" />"
	);	
	n6.AppendURLChild(
		"<bean:message  key="sysWebserviceMain.fdStartupType.manual" bundle="sys-webservice2" />",
		"<c:url value="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=list&type=1" />"
	);	
	n6.AppendURLChild(
		"<bean:message  key="sysWebserviceMain.fdStartupType.disable" bundle="sys-webservice2" />",
		"<c:url value="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=list&type=2" />"
	);		
	
	<%-- 基础设置    二级目录 --%>
	<!-- 用户帐号设置 -->
	n5.AppendURLChild(
		"<bean:message key="table.sysWebserviceUser" bundle="sys-webservice2" />",
		"<c:url value="/sys/webservice2/sys_webservice_user/sysWebserviceUser.do?method=list" />"
	);	
	<!-- 日志备份设置 -->
	n5.AppendURLChild(
		"<bean:message key="module.sys.webservice2.base" bundle="sys-webservice2"/>",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.webservice2.model.SysWebServiceBaseInfo"/>"
	);
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
	</template:replace>
</template:include>