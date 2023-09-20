<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.sys.number" bundle="sys-number"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;
	
	<%-- 规则模板映射
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysNumberMainMapp" bundle="sys-number" />",
		"<c:url value="/sys/number/sys_number_main_mapp/sysNumberMainMapp.do?method=list" />"
	); --%>
	
	<%-- 编号规则 --%>
	<kmss:authShow roles="ROLE_SYSNUMBER_ADMIN">
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysNumberMain" bundle="sys-number" />",
		"");
	n3 = n2.AppendBeanData("sysNumberMainFlowTreeService",
	new Array("<c:url value="/sys/number/sys_number_main/index.jsp?isSysnumberAdmin=yes&modelName=!{value}"/>",0));
	</kmss:authShow>
	<%--流水号 --%>
	<kmss:authShow roles="ROLE_SYSNUMBER_ADMIN">
	n2 = n1.AppendURLChild(
		"<bean:message key="sysNumberMainFlow.fdFlowNum.manage" bundle="sys-number" />",
		"");
	n3 = n2.AppendBeanData("sysNumberMainFlowTreeService",
	new Array("<c:url value="/sys/number/sys_number_main_flow/index.jsp?modelName=!{value}"/>",0));
	</kmss:authShow>
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
   </template:replace>
</template:include>