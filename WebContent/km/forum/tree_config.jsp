<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">

<%@ page import="com.landray.kmss.util.UserUtil" %>
<%
	request.setAttribute("userId",UserUtil.getUser(request).getFdId());
%>
    
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView("LKSTree", "<bean:message key="home.nav.kmForum" bundle="km-forum"/>", document.getElementById("treeDiv"));
	var n1, n2, n3,n4;
	n1 = LKSTree.treeRoot;
	//========== 论坛设置 ==========
	<kmss:authShow roles="ROLE_KMFORUMCONFIG_ADMIN,ROLE_KMFORUMCATE_ADMIN">
	n2 = n1.AppendURLChild("<bean:message bundle="km-forum" key="menu.kmForum.global"/>");
	</kmss:authShow>
	n2.isExpanded = true;
	<kmss:authShow roles="ROLE_KMFORUMCONFIG_ADMIN">
	defaultNode = n2.AppendURLChild("<bean:message bundle="km-forum" key="menu.kmForum.config"/>",
		 "<c:url value="/km/forum/km_forum_config/kmForumConfig.do?method=edit" />");
	</kmss:authShow>
	<kmss:authShow roles="ROLE_KMFORUMCONFIG_ADMIN,ROLE_KMFORUMCATE_ADMIN">
	n3 = n2.AppendURLChild("<bean:message bundle="km-forum" key="menu.kmForum.directory"/>",
		 "<c:url value="/km/forum/km_forum_cate/index.jsp?type=directory" />");
	</kmss:authShow> 
	<kmss:authShow roles="ROLE_KMFORUMCATE_ADMIN">
	n3 = n2.AppendURLChild("<bean:message bundle="km-forum" key="menu.kmForum.category"/>",
		 "<c:url value="/km/forum/km_forum_cate/index.jsp?type=forum" />");
	</kmss:authShow>
	n2 = n1.AppendURLChild("<bean:message bundle="km-forum" key="menu.kmForum.manage"/>");
	n2.isExpanded = true;
	n2.AppendBeanData("kmForumCategoryTeeService&categoryId=!{value}&isManage=true");
	LKSTree.EnableRightMenu();	
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
 </template:replace>
</template:include>