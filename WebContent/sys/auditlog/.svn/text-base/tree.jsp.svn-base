<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="table.auditlog" bundle="sys-auditlog"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2,n3,n4;
	n1 = LKSTree.treeRoot;
	
	<kmss:auth
		requestURL="/sys/auditlog/sys_audit_log/sysAuditlog.do?method=list"
		requestMethod="GET">
		<%-- 按模块--%>
		n2 = n1.AppendURLChild(
			"<bean:message key="sysAuditlog.query" bundle="sys-auditlog" />",
			"<c:url value="/sys/auditlog/sys_audit_log/index.jsp" />"
		);
	</kmss:auth>
	
	 n4=n1.AppendURLChild("<bean:message key="sysAuditlog.setting" bundle="sys-auditlog"/>","<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.auditlog.model.SysAuditlogConfig"/>");
	
	LKSTree.EnableRightMenu();	
	LKSTree.Show();
}
 </template:replace>
</template:include>
