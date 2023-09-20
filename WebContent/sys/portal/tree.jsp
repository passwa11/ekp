<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"${lfn:escapeJs(lfn:message('sys-portal:module.sys.portal')) }",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;
	<%-- 主文档 --%>
	n2 = n1.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-portal:table.sysPortalMain'))}",
		"<c:url value="/sys/portal/sys_portal_main/sysPortalMain.do?method=list" />"
	);
	n2 = n1.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-portal:table.sysPortalPage'))}",
		"<c:url value="/sys/portal/sys_portal_page/sysPortalPage.do?method=list&config=true" />"
	);
	
	<%-- 部件 --%>
	n2 = n1.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-portal:nav.sys.portal.portlet'))}"
	);
	n3 = n2.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-portal:table.sysPortalLink'))}",
		"<c:url value="/sys/portal/sys_portal_link/sysPortalLink.do?method=list&fdType=2&config=true" />"
	);	
	n3 = n2.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-portal:sys_portal_link_type_1'))}",
		"<c:url value="/sys/portal/sys_portal_link/sysPortalLink.do?method=list&fdType=1&config=true" />"
	);	
	n3 = n2.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-portal:table.sysPortalTree'))}",
		"<c:url value="/sys/portal/sys_portal_tree/sysPortalTree.do?method=list&config=true" />"
	);	
	n3 = n2.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-portal:table.sysPortalHtml'))}",
		"<c:url value="/sys/portal/sys_portal_html/sysPortalHtml.do?method=list&config=true" />"
	);	
	n3 = n2.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-portal:table.sysPortalGuide'))}",
		"<c:url value="/sys/portal/sys_portal_guide/sysPortalGuide.do?method=list&config=true" />"
	);	
	LKSTree.ExpandNode(n2);	
	
	<%@include file="/sys/person/portal_tree.jsp" %>
	<kmss:authShow roles="ROLE_SYSPORTAL_DATA_EDIT">
	<%-- 系统资源 --%>
	n2 = n1.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-portal:sys.portal.sysresource'))}"
	);
	
	<%-- 部件 --%>
	n3 = n2.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-portal:sys.portal.syswidget'))}",
		"<c:url value="/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=list" />"
	);
	<%-- 部件 --%>
	n3 = n2.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-portal:sys.portal.extresource'))}","<c:url value="/sys/ui/help/lui-ext/index.jsp" />"
	);
	n3 = n2.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-portal:sys.portal.ui.config')) }",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.ui.model.SysUiConfig" />"
	);
	LKSTree.ExpandNode(n2); 
	</kmss:authShow>
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
<%@ include file="/resource/jsp/tree_down.jsp" %>