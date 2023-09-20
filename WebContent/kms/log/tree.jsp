﻿﻿<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.kms.log" bundle="kms-log"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;
	
	<%-- 日志基础配置 --%>
	<kmss:auth requestURL="/kms/log/kms_log_config/kmsLogConfig.do?method=edit" requestMethod="GET">
	n2 = n1.AppendURLChild(
		"<bean:message key="table.kmsLogConfig" bundle="kms-log" />",
		"<c:url value="/kms/log/kms_log_config/kmsLogConfig.do?method=edit" />"
	);
	</kmss:auth>
    <kmss:authShow roles="ROLE_KMSLOG_ADMIN">
    n2 = n1.AppendURLChild(
        "<bean:message key="kmsLogConfig.admintool" bundle="kms-log" />",
        "<c:url value="/kms/log/kms_log_config/kmsLogConfig.do?method=listAdminTool" />"
    );
    </kmss:authShow>
	<%-- 应用日志表 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.kmsLogApp" bundle="kms-log" />"
	);
	
	<kmss:auth requestURL="/kms/log/kms_log_app/kmsLogApp.do?method=list" requestMethod="GET">
	n3 = n2.AppendURLChild(
		"<bean:message key="kmsLog.recent.info" bundle="kms-log" />",
		<!-- "<c:url value="/kms/log/kms_log_app/kmsLogApp.do?method=list" />" -->
		 "<c:url value="/kms/log/kms_log_app/kmsLogApp_list_index.jsp?fdModelName=com.landray.kmss.kms.log.model.KmsLogApp" />" 
	);
	</kmss:auth>
	
	<kmss:auth requestURL="/kms/log/kms_log_app/kmsLogApp.do?method=listMonth" requestMethod="GET">
	<%-- 按月查询日志（分表查询）--%>
	n3 = n2.AppendURLChild(
		"<bean:message key="kmsLogApp.logMonth.info" bundle="kms-log" />",
		<%--  "<c:url value="/kms/log/kms_log_app/kmsLogApp.do?method=listMonth" />"  --%>
	"<c:url value="/kms/log/kms_log_app/kmsLogApp_listMonth.jsp?fdModelName=com.landray.kmss.kms.log.model.KmsLogAPP" />" 
	);
	</kmss:auth>
	
	<%-- 历史应用日志表 --%>
	<% if(Boolean.valueOf(ResourceUtil.getKmssConfigString("kmss.log.detachment"))){
	%>
	
	<kmss:auth requestURL="/kms/log/kms_log_app/kmsLogApp.do?method=list&history=true" requestMethod="GET">
		n3 = n2.AppendURLChild(
			"<bean:message key="kmsLogApp.kmsLogAppHistory" bundle="kms-log" />",
			"<c:url value="/kms/log/kms_log_app/kmsLogApp.do?method=list&history=true" />"
		);
	</kmss:auth>
		
	<%} %>
	
	<%-- 搜索日志表 --%>
	<kmss:auth requestURL="/kms/log/kms_log_search/kmsLogSearch.do?method=list" requestMethod="GET">
	n2 = n1.AppendURLChild(
		"<bean:message key="table.kmsLogSearch" bundle="kms-log" />",
		<!-- "<c:url value="/kms/log/kms_log_search/kmsLogSearch.do?method=list" />" -->
		"<c:url value="/kms/log/kms_log_search/kmsLogSearch_list_index.jsp?fdModelName=com.landray.kmss.kms.log.model.KmsLogSearch" />" 
	);
	</kmss:auth>
	
	<%-- 后台配置日志表 czk2019--%>
	<kmss:auth requestURL="/kms/log/kms_log_sys_config/kmsLogSysConfig.do?method=list" requestMethod="GET">
	n2 = n1.AppendURLChild(
		"<bean:message key="table.kmsLogSysConfig" bundle="kms-log" />",
		"<c:url value="/kms/log/kms_log_sys_config/kmsLogSysConfig_list_index.jsp?fdModelName=com.landray.kmss.kms.log.model.KmsLogSysConfig" />" 
	);
	</kmss:auth>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
	</template:replace>
</template:include>
