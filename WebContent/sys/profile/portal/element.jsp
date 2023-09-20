<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message bundle="sys-profile" key="sys.profile.portal.element" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, defaultNode;
	n1 = LKSTree.treeRoot;
	
	//========== 公共门户部件 ==========
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-portal" key="nav.sys.portal.portlet" />"
	);
	<%-- 公共门户部件    二级目录 --%>
	<!-- 快捷方式 -->
	n2.AppendURLChild(
		"<bean:message bundle="sys-portal" key="table.sysPortalLink" />",
		"<c:url value="/sys/portal/sys_portal_link/index.jsp?fdType=2&config=true" />"
	);
	<!-- 常用链接 -->
	n2.AppendURLChild(
		"<bean:message bundle="sys-portal" key="sys_portal_link_type_1" />",
		"<c:url value="/sys/portal/sys_portal_link/index.jsp?fdType=1&config=true" />"
	);
	<!-- 多级树菜单 -->
	n2.AppendURLChild(
		"<bean:message bundle="sys-portal" key="table.sysPortalTree" />",
		"<c:url value="/sys/portal/sys_portal_tree/index.jsp?config=true" />"
	);
	<!-- 系统导航 -->
	n2.AppendURLChild(
		"<bean:message bundle="sys-portal" key="table.sysPortalNav" />",
		"<c:url value="/sys/portal/sys_portal_nav/index.jsp?config=true" />"
	);
	<!-- 地图模板 -->
	n2.AppendURLChild(
		"<bean:message bundle="sys-portal" key="table.sysPortalMapTpl" />",
		"<c:url value="/sys/portal/sys_portal_map_tpl/index.jsp?config=true" />"
	);
	<!-- 自定义页面 -->
	n2.AppendURLChild(
		"<bean:message bundle="sys-portal" key="table.sysPortalHtml" />",
		"<c:url value="/sys/portal/sys_portal_html/index.jsp?config=true" />"
	);
	<!-- 自定义引导页 -->
	n2.AppendURLChild(
		"<bean:message bundle="sys-portal" key="table.sysPortalGuide" />",
		"<c:url value="/sys/portal/sys_portal_guide/index.jsp" />"
	);
	n2.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-portal:table.sysPortalTopic'))}",
		"<c:url value="/sys/portal/sys_portal_topic/index.jsp" />"
	);	
	
	//========== 个人门户部件 ==========
	n3 = n1.AppendURLChild(
		"<bean:message bundle="sys-portal" key="portal.personal.components" />"
	);
	<%-- 个人门户部件    二级目录 --%>
	<!-- 导航设置 -->
	n3.AppendURLChild(
		"<bean:message bundle="sys-portal" key="portal.navigation.setting" />",
		"<c:url value="/sys/person/sys_person_sysnav_category/index.jsp" />"
	);
	<!-- 窗口设置 -->
	n3.AppendURLChild(
		"<bean:message bundle="sys-portal" key="portal.window.setting" />",
		"<c:url value="/sys/person/sys_person_systab_category/index.jsp?type=page" />"
	);
	
	<kmss:authShow roles="ROLE_SYSPORTAL_BASE_SETTING">
	//========== 系统portlet部件 ==========
	defaultNode = n4 = n1.AppendURLChild(
		"<bean:message bundle="sys-portal" key="portal.system.components" />",
		"<c:url value="/sys/portal/sys_portal_portlet/index.jsp" />"
	);
	//========== 部件管理 ==========
	defaultNode = n5 = n1.AppendURLChild(
		"<bean:message bundle="sys-portal" key="portal.system.components.manage" />",
		"<c:url value="/sys/profile/sys_componenet_Temp/sysComponentTemp.do?method=getComponent" />"
	);
	</kmss:authShow>
	
	LKSTree.ExpandNode(n2);
	LKSTree.ExpandNode(n3);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>