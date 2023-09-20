<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"${lfn:escapeJs(lfn:message('sys-zone:module.name')) }",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-zone" key="table.sysZonePerson"/>",
		"<c:url value="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=listPersons&parentId=!{value}"/>"
	);
	n3 = n2.AppendOrgData("organizationTree&fdId=!{value}",
		"<c:url value="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=listPersons&parentId=!{value}"/>"
	);
	n2 = n1.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-zone:path.nav.setting')) }",
		"<c:url value="/sys/zone/sys_zone_navigation/sysZoneNavigation_list.jsp" />"
	);
	LKSTree.ExpandNode(n2);
	n2 = n1.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-zone:path.page.setting')) }",
		"<c:url value="/sys/zone/sys_zone_page_template/sysZonePageTemplate.do?method=edit" />"
	);
	
	n2 = n1.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-zone:table.sysZonePerDataTempl')) }",
		"<c:url value="/sys/zone/sys_zone_person_data_cate/sysZonePersonDataCate.do?method=list" />"
	);
	
	n2 = n1.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-zone:sysZonePrivate.privacy.setting')) }",
		"<c:url value="/sys/zone/sys_zone_private_config/sysZonePrivateConfig.do?method=edit&modelName=com.landray.kmss.sys.zone.model.SysZonePrivateConfig" />"
	);
	LKSTree.ExpandNode(n2);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
	</template:replace>
</template:include>