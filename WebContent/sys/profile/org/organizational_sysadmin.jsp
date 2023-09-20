<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">

function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message bundle="sys-organization" key="sysOrg.address.tree.new"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2;
	n1 = LKSTree.treeRoot;
	
	<kmss:authShow roles="SYSROLE_SYSADMIN">
	// 导入人员
	n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="org.personnel.import"/>",
		"<c:url value="/sys/profile/org/io/import_data.jsp"/>"
	);
	// 创建人员
	n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="org.personnel.create"/>",
		"<c:url value="/sys/organization/sys_org_person/sysOrgPerson.do?method=add&hideClose=false"/>"
	);
	<!-- 待激活人员 -->
	n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="org.personnel.activation.no"/>",
		"<c:url value="/sys/organization/sys_org_person/activation_index.jsp?available=0"/>"
	);
	
	<!-- 已激活人员 -->
	n1.AppendURLChild(
		"<bean:message bundle="sys-organization" key="org.personnel.activation.yes"/>",
		"<c:url value="/sys/organization/sys_org_person/activation_index.jsp?available=1"/>"
	);
	</kmss:authShow>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}

	</template:replace>
</template:include>