<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message bundle="sys-quartz" key="table.sysQuartzJob"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:authShow roles="ROLE_SYSQUARTZJOB_ADMIN;SYSROLE_SYSADMIN">
	//========== 系统任务 ==========
	defaultNode = n2 = n1.AppendURLChild(
		'<bean:message bundle="sys-quartz" key="sysQuartzJob.sysJob"/>',
		'<c:url value="/sys/quartz/sys_quartz_job/index.jsp?sysJob=1"/>');
	
	//========== 普通任务 ==========
	n3 = n1.AppendURLChild(
		'<bean:message bundle="sys-quartz" key="sysQuartzJob.normalJob"/>',
		'<c:url value="/sys/quartz/sys_quartz_job/index.jsp?sysJob=0"/>');
	
	//========== 参数设置 ==========
	n4 = n1.AppendURLChild(
		'<bean:message bundle="sys-quartz" key="sysQuartzJob.config"/>',
		'<c:url value="/sys/quartz/sys_quartz_job/sysQuartzConfig.do?method=edit&modelName=com.landray.kmss.sys.quartz.model.SysQuartzConfig"/>'
	);
	</kmss:authShow>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>