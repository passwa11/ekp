<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">


//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.sys.lbpmext.authorize" bundle="sys-lbpmext-authorize" />",
		document.getElementById("treeDiv")
	);
	var n1,  defaultNode;
	n1 = LKSTree.treeRoot;
	
	//========== 所有授权 ==========
	<kmss:authShow roles="ROLE_LBPM_WORKFLOW_AUTHORITY_MANAGER">
		defaultNode=n1.AppendURLChild("<bean:message key="lbpmAuthorize.all.info" bundle="sys-lbpmext-authorize" />",
			'<c:url value="/sys/lbpmext/authorize/lbpm_authorize/index.jsp"/>');
		
		
			
	</kmss:authShow>
	
	<kmss:authShow roles="ROLE_LBPM_WORKFLOW_AUTHORITY_MANAGER">
	
		n1.AppendURLChild("<bean:message key="module.sys.lbpmext.authorize.set" bundle="sys-lbpmext-authorize" />",
			'<c:url value="/sys/lbpmext/authorize/lbpm_authorize/userAuthorizeScope.do?method=edit"/>');
			
	</kmss:authShow>
	
	
	LKSTree.ExpandNode(n1);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>