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
		"<bean:message key="module.sys.webservice2" bundle="sys-webservice2"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5,defaultNode;
	n1 = LKSTree.treeRoot;
	
	<%-- 所有服务 --%>
	defaultNode = n1.AppendURLChild(
		"<bean:message key="table.sysWebserviceMain" bundle="sys-webservice2" />",
		"<c:url value="/sys/webservice2/sys_webservice_main/index.jsp" />"
	);
	<% 
	String pattern = ResourceUtil.getKmssConfigString("kmss.oper.log.store.pattern");
	if (StringUtil.isNull(pattern) || LogConstant.LogStorePattern.DB.getVal().equals(pattern)) { %>
	<%-- 运行记录 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysWebserviceLog" bundle="sys-webservice2" />",
		"<c:url value="/sys/webservice2/sys_webservice_log/index.jsp?methodName=list" />"
	);
	<% } %>
	
	<%-- 超时预警 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="sysWebserviceMain.timeout" bundle="sys-webservice2" />",
		"<c:url value="/sys/webservice2/sys_webservice_log/index.jsp?methodName=timeout" />"
	);	
	
	<%-- 模块设置   --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="sys.webservice2.tree.moduleSet" bundle="sys-webservice2" />"
	);
	<%-- 用户帐号设置   --%>
	n2.AppendURLChild(
		"<bean:message key="table.sysWebserviceUser" bundle="sys-webservice2" />",
		"<c:url value="/sys/webservice2/sys_webservice_user/index.jsp" />"
	);
	<!-- 日志记录信息配置 -->
	n2.AppendURLChild(
		"<bean:message key="sys.webservice2.config" bundle="sys-webservice2" />",
		"<c:url value="/sys/webservice2/sys_webservice_log_config/sysWebserviceLogConfig.do?method=edit&modelName=com.landray.kmss.sys.webservice2.model.SysWebserviceLogConfig" />"
	);	
	<%-- 日志备份设置   --%>	
	n2.AppendURLChild(
		"<bean:message key="module.sys.webservice2.base" bundle="sys-webservice2"/>",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.webservice2.model.SysWebServiceBaseInfo"/>"
	);
	<%-- REST服务配置 --%>
	<%-- 
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysWebserviceRestConfig" bundle="sys-webservice2" />",
		"<c:url value="/sys/webservice2/sys_webservice_rest_config/sysWebserviceRestConfig.do?method=list&orderby=docCreateTime&ordertype=down" />"
	);	
	--%>
	<%-- 导入注册的服务  
	n2.AppendURLChild(
		"<bean:message bundle="sys-webservice2" key="sysWebservice.init"/>",
		"<c:url value="/sys/webservice2/sys_webservice_init/sysWebserviceInit.do?method=init"/>"
	);	
	 --%>	
	LKSTree.ExpandNode(n2);

	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
	
}
	</template:replace>
</template:include>