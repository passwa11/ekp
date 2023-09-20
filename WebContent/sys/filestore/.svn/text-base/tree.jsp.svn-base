<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	<kmss:authShow roles="SYSROLE_ADMIN;SYSROLE_SYSADMIN">
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="attconvert.desc" bundle="sys-filestore"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2
	n1 = LKSTree.treeRoot;
	n2 = n1.AppendURLChild(
		"<bean:message key="filestore.queueConfig" bundle="sys-filestore" />",
		"<c:url value="/sys/filestore/sys_filestore/sysFileConvertGlobalConfig.do?method=config" />"
	);
	
	n1.AppendURLChild(
		"<bean:message key="filestore.converterList" bundle="sys-filestore" />",
		"<c:url value="/sys/filestore/convertclient/index.jsp" />"
	);
	
	n1.AppendURLChild(
		"<bean:message key="filestore.queueList" bundle="sys-filestore" />",
		"<c:url value="/sys/filestore/convertqueue/index.jsp" />"
	);
	n1.AppendURLChild(
		'<bean:message key="sysFilestore.tree.convertUrl.displayConfig" bundle="sys-filestore" />',
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.filestore.model.SysFileConvertUrlConfig" />"
	);
	n1.AppendURLChild(
		'<bean:message key="sysFilestore.tree.convertClear.displayConfig" bundle="sys-filestore" />',
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.filestore.model.SysFileConvertClearConfig" />"
	);			
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(n2);
	</kmss:authShow>
}
	</template:replace>
</template:include>