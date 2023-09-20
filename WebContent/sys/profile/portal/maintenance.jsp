<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message bundle="sys-portal" key="portal.gateway.maintenance" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, defaultNode;
	n1 = LKSTree.treeRoot;
	
	//========== 登录页 ==========
	<kmss:authShow roles = "ROLE_SYSPORTAL_BASE_SETTING">
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-portal" key="portal.login.page" />",
		"<c:url value="/sys/profile/sys_login_template/sysLoginTemplate.do?method=loginConfig" />"
	);
	
	</kmss:authShow>
	
	//门户管理_门户和页面配置
	<!-- 针对门户的可维护者可以维护自己的门户、页面部件等，取消角色限制，在是否可编辑处做控制 -->
	//========== 门户配置 ==========
	defaultNode = n3 = n1.AppendURLChild(
		"<bean:message bundle="sys-portal" key="table.sysPortalMain" />",
		"<c:url value="/sys/portal/sys_portal_main/sysPortalMain.do?method=list" />"
	);
	
	//========== 页面配置 ==========
	n4 = n1.AppendURLChild(
		"<bean:message bundle="sys-portal" key="table.sysPortalPage" />",
		"<c:url value="/sys/portal/sys_portal_page/index.jsp?config=true" />"
	);
	
	<kmss:authShow roles = "ROLE_SYSPORTAL_BASE_SETTING">
	//========== 参数设置 ==========
	n5 = n1.AppendURLChild(
		"<bean:message bundle="sys-portal" key="sys.portal.ui.config" />",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.ui.model.SysUiConfig" />"
	);
	
	//==========门户公告 ==========
	n6 = n1.AppendURLChild(
		"<bean:message bundle="sys-portal" key="sys.portal.ui.config.notify" />",
		"<c:url value="/sys/portal/sys_portal_notice/sysPortalNotice.do?method=edit" />"
	);
	</kmss:authShow>
	
	<kmss:authShow roles="ROLE_SYSPORTAL_BASE_SETTING">
	//==========首页弹窗 ==========
	
	n7 = n1.AppendURLChild(
		"<bean:message bundle="sys-portal" key="sysPortalPopMain.homePage.pop" />"
	);
	
	n8 = n7.AppendURLChild(
		"<bean:message bundle="sys-portal" key="sysPortalPopMain.matterNotice.pop" />",
		"<c:url value="/sys/portal/pop/sys_portal_pop_main/sysPortalPopMain_list.jsp?customCategory=notice"/>"
	);
	
	n9 = n7.AppendURLChild(
		"<bean:message bundle="sys-portal" key="sys_portal_pop_main_custom_category_birthday" />",
		"<c:url value="/sys/portal/pop/sys_portal_pop_main/sysPortalPopMain_list.jsp?customCategory=birthday"/>"
	);
	
	n10 = n7.AppendURLChild(
		"<bean:message bundle="sys-portal" key="sys_portal_pop_main_custom_category_care" />",
		"<c:url value="/sys/portal/pop/sys_portal_pop_main/sysPortalPopMain_list.jsp?customCategory=care"/>"
	);
	
	/*
	n8 = n7.AppendURLChild(
		"弹窗模板分类",
		"<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.sys.portal.model.SysPortalPopTplCategory&actionUrl=/sys/portal/pop/sys_portal_pop_tpl_category/sysPortalPopTplCategory.do&formName=sysPortalPopTplCategoryForm&mainModelName=com.landray.kmss.sys.portal.model.SysPortalPopTpl&docFkName=fdCategory" />"
	);
	
	n9 = n7.AppendURLChild("弹窗模板");
	n9.authType="01";
	n9.AppendSimpleCategoryDataWithAdmin(
		"com.landray.kmss.sys.portal.model.SysPortalPopTplCategory",
		"<c:url value="/sys/portal/pop/sys_portal_pop_tpl/sysPortalPopTpl_list.jsp?categoryId=!{value}"/>",
		"<c:url value="/sys/portal/pop/sys_portal_pop_tpl/sysPortalPopTpl.do?method=data&categoryId=!{value}"/>"
	);
	
	n10 = n7.AppendURLChild(
		"弹窗分类",
		"<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.sys.portal.model.SysPortalPopCategory&actionUrl=/sys/portal/pop/sys_portal_pop_category/sysPortalPopCategory.do&formName=sysPortalPopCategoryForm&mainModelName=com.landray.kmss.sys.portal.model.SysPortalPopMain&docFkName=fdCategory" />"
	);

	n11 = n7.AppendURLChild("弹窗文档");
	n11.authType="01";
	n11.AppendSimpleCategoryDataWithAdmin(
		"com.landray.kmss.sys.portal.model.SysPortalPopCategory",
		"<c:url value="/sys/portal/pop/sys_portal_pop_main/sysPortalPopMain_list.jsp?categoryId=!{value}"/>",
		"<c:url value="/sys/portal/pop/sys_portal_pop_main/sysPortalPopMain.do?method=data&categoryId=!{value}"/>"
	);
	*/
	</kmss:authShow>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>