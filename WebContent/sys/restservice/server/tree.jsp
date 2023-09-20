<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.log.util.LogConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.sys.restservice.server" bundle="sys-restservice-server"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5,defaultNode;
	n1 = LKSTree.treeRoot;
	
	<%-- 所有服务 --%>
	defaultNode = n1.AppendURLChild(
		"<bean:message key="table.sysRestserviceServerMain" bundle="sys-restservice-server" />",
		"<c:url value="/sys/restservice/server/sys_restservice_server_main/index.jsp" />"
	);
	<% 
	String pattern = ResourceUtil.getKmssConfigString("kmss.oper.log.store.pattern");
	if (StringUtil.isNull(pattern) || LogConstant.LogStorePattern.DB.getVal().equals(pattern)) { %>
	<%-- 访问日志 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysRestserviceServerLog" bundle="sys-restservice-server" />",
		"<c:url value="/sys/restservice/server/sys_restservice_server_log/index.jsp?methodName=list" />"
	);
	<% } %>
	
	<%-- 超时预警 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="sysRestserviceServerMain.timeout" bundle="sys-restservice-server" />",
		"<c:url value="/sys/restservice/server/sys_restservice_server_log/index.jsp?methodName=timeout" />"
	);	
	
	<%-- 模块设置   --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="sys.restservice.tree.moduleSet" bundle="sys-restservice-server" />"
	);
	<%-- 访问策略设置   --%>
	n2.AppendURLChild(
		"<bean:message key="table.sysRestserviceServerPolicy" bundle="sys-restservice-server" />",
		"<c:url value="/sys/restservice/server/sys_restservice_server_policy/index.jsp" />"
	);
	<!-- 日志记录信息配置 -->
	n2.AppendURLChild(
		"<bean:message key="sys.restservice.config" bundle="sys-restservice-server" />",
		"<c:url value="/sys/restservice/server/sys_restservice_server_log_config/sysRestserviceServerLogConfig.do?method=edit&modelName=com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLogConfig" />"
	);	
	<%-- 日志备份设置   --%>	
	n2.AppendURLChild(
		"<bean:message key="module.sys.restservice.base" bundle="sys-restservice-server"/>",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.restservice.server.model.SysRestServiceBaseInfo"/>"
	);
	<%-- 导入注册的服务  
	n2.AppendURLChild(
		"<bean:message bundle="sys-restservice-server" key="sysRestserviceServer.init"/>",
		"<c:url value="/sys/restservice/server/sys_restservice_server_init/sysRestserviceServerInit.do?method=init"/>"
	);	
	 --%>	
	LKSTree.ExpandNode(n2);

	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
	
}
	</template:replace>
</template:include>
