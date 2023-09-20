<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>

//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.sys.mportal" bundle="sys-mportal"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5; 
	n1 = LKSTree.treeRoot;
	
	
	<kmss:auth requestURL="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.mportal.model.SysMportalLogoInfo" requestMethod="GET">
	n1.AppendURLChild("logo配置",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.mportal.model.SysMportalLogoInfo" />");
	</kmss:auth>
	
	
	<kmss:auth requestURL="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.mportal.model.SysMportalBgInfo" requestMethod="GET">
	n1.AppendURLChild("背景图配置",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.mportal.model.SysMportalBgInfo" />");
	</kmss:auth>
	
	

	<kmss:auth requestURL="/sys/mportal/sys_mportal_card/sysMportalCard.do?method=list" requestMethod="GET">
	n1.AppendURLChild("卡片配置",
		"<c:url value="/sys/mportal/sys_mportal_card/sysMportalCard.do?method=list" />");
	</kmss:auth>
	
	<kmss:auth requestURL="/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=list" requestMethod="GET">	
	n1.AppendURLChild("快捷方式","<c:url value="/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=list" />");
	</kmss:auth>
	
	
	<kmss:auth requestURL="/sys/mportal/sys_mportal_html/sysMportalHtml.do?method=list" requestMethod="GET">	
	n1.AppendURLChild("自定义页面",
		"<c:url value="/sys/mportal/sys_mportal_html/sysMportalHtml.do?method=list" />");
	</kmss:auth>
	
	n2 = n1.AppendURLChild(
		"个人门户",
		""
	);
	
	<kmss:auth requestURL="/sys/mportal/sys_mportal_topmenu/sysMportalTopmenu.do?method=edit" requestMethod="GET">
	n2.AppendURLChild("顶部菜单配置",
		"<c:url value="/sys/mportal/sys_mportal_topmenu/sysMportalTopmenu.do?method=edit" />");
	</kmss:auth>
	

	n2 = n1.AppendURLChild(
		"公共门户",
		""
	);
	
	<kmss:auth requestURL="/sys/mportal/sys_mportal_page/sysMportalPage.do?method=list&orderby=docCreateTime&ordertype=down" requestMethod="GET">
	n2.AppendURLChild(
		"页面配置",
		"<c:url value="/sys/mportal/sys_mportal_page/sysMportalPage.do?method=list&orderby=docCreateTime&ordertype=down" />");
	</kmss:auth>
	
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}

<%@ include file="/resource/jsp/tree_down.jsp" %> 