<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="news.tree.title" bundle="sys-news"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5,defaultNode;
	n1 = LKSTree.treeRoot;
	
	//类别设置
	defaultNode = n1.AppendURLChild(
		"<bean:message key="news.category.set" bundle="sys-news" />",
		"<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.sys.news.model.SysNewsTemplate&actionUrl=/sys/news/sys_news_template/sysNewsTemplate.do&formName=sysNewsTemplateForm&mainModelName=com.landray.kmss.sys.news.model.SysNewsMain&docFkName=fdTemplate" />"
	);
	//=========模块设置========
	n2 = n1.AppendURLChild(
		"<bean:message key="news.tree.moduleSet" bundle="sys-news" />"
	);
	n2.isExpanded = true;
	<kmss:authShow roles="ROLE_SYSNEWS_SETTING">
		n2.AppendURLChild(
		"<bean:message bundle="sys-news" key="sysNewsMain.param.config"/>",
		"<c:url value="/sys/news/sys_news_main/sysNewsConfig.do?method=edit&modelName=com.landray.kmss.sys.news.model.SysNewsConfig" />"
	);
	</kmss:authShow>
	<kmss:authShow roles="ROLE_SYSNEWS_COMMONWORKFLOW">
		n2.AppendURLChild(
			"<bean:message key="Template.tree.fdIsDefault" bundle="sys-news" />",
			"<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.sys.news.model.SysNewsTemplate&fdKey=newsMainDoc" />"
		);
	</kmss:authShow>
	<%-- 列表显示设置--%>	
	<kmss:authShow roles="ROLE_SYSNEWS_SETTING">
	n2.AppendURLChild(
		"<bean:message key="sys.profile.list.display.config" bundle="sys-profile" />",
		"<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.sys.news.model.SysNewsMain"/>"
	);
	</kmss:authShow>
	n2 = n1.AppendURLChild("<bean:message key="tree.sysCategory.maintains" bundle="sys-category" />")
	n2.authType="01";
	<kmss:authShow roles="ROLE_SYSNEWS_OPTALL">
	n2.authRole="optAll";
	</kmss:authShow>
	n2.AppendSimpleCategoryDataWithAdmin ("com.landray.kmss.sys.news.model.SysNewsTemplate",
	"<c:url value="/sys/news/sys_news_main/sysNewsMain_manageList.jsp?categoryId=!{value}&status=all&showDocStatus=true" />",
	"<c:url value="/sys/news/sys_news_main/sysNewsMain_manageList.jsp?type=category&categoryId=!{value}"/>");
	//=========回收站========
	<% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.sys.news.model.SysNewsMain")) { %>
	n1.AppendURLChild("<bean:message key="module.sys.recycle" bundle="sys-recycle" />","<c:url value="/sys/news/sys_news_ui/sysRecycleBox.jsp" />");	
	<% } %>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
    </template:replace>
</template:include>