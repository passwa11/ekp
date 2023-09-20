<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">

//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.sys.lbpmext.businessauth" bundle="sys-lbpmext-businessauth" />",
		document.getElementById("treeDiv")
	);
	var n1,n2,defaultNode;
	n1 = LKSTree.treeRoot;
	<%if(new LbpmSetting().getBusinessauth().equals("true")){ %>
	defaultNode=n1.AppendURLChild("<bean:message key='table.lbpmext.businessAuthCate' bundle='sys-lbpmext-businessauth' />",
		'<c:url value="/sys/lbpmext/businessauth/lbpmext_businessauth_cate/index.jsp"/>');
		
	n1.AppendURLChild("<bean:message key='table.lbpmext.businessAuthInfoCate' bundle='sys-lbpmext-businessauth' />",
		'<c:url value="/sys/lbpmext/businessauth/lbpmext_businessauth_info_cate/index.jsp"/>');
	
	n2 = n1.AppendChild("<bean:message key='table.lbpmext.businessAuth' bundle='sys-lbpmext-businessauth' />");
		
	n2.AppendBeanData(
		"lbpmExtBusinessAuthCateService&parentId=!{value}",
		"<c:url value="/sys/lbpmext/businessauth/lbpmext_businessauth/index.jsp?category=!{value}"/>"
	);
	
	n2 = n1.AppendURLChild("<bean:message key='table.lbpmext.businessAuthInfo' bundle='sys-lbpmext-businessauth' />",
	'<c:url value="/sys/lbpmext/businessauth/lbpmext_businessauth_info/index.jsp"/>');
		
	n2.AppendBeanData(
		"lbpmExtBusinessAuthInfoCateService&parentId=!{value}",
		"<c:url value="/sys/lbpmext/businessauth/lbpmext_businessauth_info/index.jsp?category=!{value}"/>"
	);

	n1.AppendURLChild("<bean:message key='lbpmext.businessauth.simulator' bundle='sys-lbpmext-businessauth' />",
		'<c:url value="/sys/lbpmext/businessauth/lbpmext_businessauth_simulator/lbpmextBusinessAuth_simulator.jsp"/>');
	
	<%} %>
	
	LKSTree.ExpandNode(n1);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>