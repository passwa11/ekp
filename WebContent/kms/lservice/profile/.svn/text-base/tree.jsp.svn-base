<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
	<template:replace name="content">
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.kms.lservice" bundle="kms-lservice" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;
	
	n1.isExpanded = true;
	
	<kmss:auth
			requestURL="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.kms.lservice.config.LserviceConfig"
			requestMethod="GET">
			
		n2 = n1.AppendURLChild("<bean:message bundle="kms-lservice"
				key="module.kms.lservice.config" />",
			"<c:url
				value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.kms.lservice.config.LserviceConfig" />");
	</kmss:auth>
	

	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
	</template:replace>
</template:include>
