<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.util.SysAuthAreaHelper"%>
<%@ page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ page import="com.landray.kmss.sys.authorization.util.LicenseUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
	<c:set var="fdType" value="0"/>
	<% if(ISysAuthConstant.IS_AREA_ENABLED) { %>
		<c:set var="fdType" value="1"/>
	<%} %>
//Com_Parameter.XMLDebug = true;
function generateTree()
{
<% if(ISysAuthConstant.IS_AREA_ENABLED) { %>
	LKSTree = new TreeView("LKSTree", "<bean:message bundle="sys-authorization" key="title.sysAuthArea"/>", document.getElementById("treeDiv"));
<% } else { %>
	LKSTree = new TreeView("LKSTree", "<bean:message bundle="sys-authorization" key="authorization.moduleName"/>", document.getElementById("treeDiv"));
<% } %>
	var n1, n2, n3, n4, n5, n6, n9, n10, n11, defaultNode;
	n1 = LKSTree.treeRoot;
<kmss:auth requestURL="/sys/authorization/sys_auth_sys_role/sysAuthSysRole.do?method=list" requestMethod="GET">
	/**
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="sysAuthRole.tree.init"/>",
		"<c:url value="/sys/authorization/sys_auth_sys_role/sysAuthSysRole.do?method=systemInit"/>"
	);
	*/
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="sysAuthRole.allSysRole"/>"
	);
	n2.AppendBeanData(
		"sysAuthModuleTree",
		"<c:url value="/sys/authorization/sys_auth_sys_role/index.jsp"/>?modulePath=!{value}",
		null,
		false
	);
	
</kmss:auth>
<% if(ISysAuthConstant.IS_AREA_ENABLED) { %>
<kmss:auth requestURL="/sys/authorization/sys_auth_role/sysAuthRole.do?method=list&type=0" requestMethod="GET">
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="sysAuthRole.tree.common" />"
	);
	n3 = n2.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="sysAuthRole.fdType.common" />",
		"<c:url value="/sys/authorization/sys_auth_role/index.jsp?type=0" />"
	);		
	n3.AppendBeanData("sysAuthCategoryTreeService&categoryId=!{value}",
		"<c:url value="/sys/authorization/sys_auth_role/index.jsp?type=0&categoryId=!{value}" />"
	);
	<% if (UserUtil.checkRole(ISysAuthConstant.ROLE_SYSAUTHROLE_ADMIN)) { %>
	n4 = n2.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="sysAuthRole.fdType.area" />",
		"<c:url value="/sys/authorization/sys_auth_role/index.jsp?type=2" />"
	);
	n4.AppendBeanData("sysAuthCategoryTreeService&categoryId=!{value}",
		"<c:url value="/sys/authorization/sys_auth_role/index.jsp?type=2&categoryId=!{value}" />"		
	);
	<% } %>

	n1.AppendURLChild("<bean:message bundle="sys-authorization" key="sysAuthTemplate.name"/>",
		"<c:url value="/sys/authorization/sys_auth_template/index.jsp?type=${fdType}&fdTemplate=1" />"
	);
</kmss:auth>
<%-- 
<kmss:auth requestURL="/sys/authorization/sys_auth_area/sysAuthArea.do?method=list" requestMethod="GET">
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="table.sysAuthArea" />",
		"<c:url value="/sys/authorization/sys_auth_area/sysAuthArea.do?method=list" />"
	);
</kmss:auth>
--%>
<kmss:auth requestURL="/sys/authorization/sys_auth_area/sysAuthArea.do?method=list" requestMethod="GET">
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="sysAuthArea.Manage" />"
	);
	n3 = n2.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="table.sysAuthArea" />",
		"<c:url value="/sys/authorization/sys_auth_area/sysAuthArea_tree.jsp?modelName=com.landray.kmss.sys.authorization.model.SysAuthArea" />"
	);
	n2.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="invalid.sysAuthArea" />",
		"<c:url value="/sys/authorization/sys_auth_area/index.jsp?isAvailable=false" />"
	);	
</kmss:auth>

<kmss:authShow roles="ROLE_SYSAUTHROLE_ADMIN">
	<c:set var="ADMIN_FLAG" value="1" />
</kmss:authShow>
<%if(SysAuthAreaHelper.isAdmin()) {%>
	n1.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="sysAuthArea.authAreaVisitor.setting" />",
		"<c:url value="/sys/authorization/sys_auth_area/manageVisitor.jsp"/>"
	);
	n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="sysAuthRole.tree.areaMng" />"
	);
	n10 = n1.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="sysAuthRole.search"/>"
	);
<kmss:auth requestURL="/sys/authorization/sys_auth_role/sysAuthRole.do?method=list&type=1" requestMethod="GET">
	defaultNode = n3 = n2.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="sysAuthRole.tree.area" />",
		"<c:url value="/sys/authorization/sys_auth_role/index.jsp?type=1" />"
	);
	n3.AppendBeanData("sysAuthCategoryTreeService&categoryId=!{value}",
		"<c:url value="/sys/authorization/sys_auth_role/index.jsp?type=1&categoryId=!{value}" />"
	);
</kmss:auth>
<kmss:auth requestURL="/sys/authorization/sys_auth_research/sysAuthResearch" requestMethod="GET">
	<c:if test="${ADMIN_FLAG != '1'}">
	n2.AppendURLChild(
		'<bean:message bundle="sys-authorization" key="sysAuthResearch.tree.person"/>',
		'<c:url value="/sys/authorization/sys_auth_research/sysAuthResearch_orgtree.jsp?orgType=" />'+(ORG_TYPE_PERSON|ORG_FLAG_BUSINESSALL),
		2
	);
	n2.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="sysAuthResearch.tree.role"/>",
		"<c:url value="/sys/authorization/sys_auth_research/sysAuthResearch_roletree.jsp"/>",
		2
	);
	</c:if>
</kmss:auth>
<%}%>
<kmss:auth requestURL="/sys/authorization/sys_auth_research/sysAuthResearch" requestMethod="GET">
	<c:if test="${ADMIN_FLAG == '1'}">
	n10.AppendLV2Child(
		"<bean:message bundle="sys-authorization" key="sysAuthResearch.tree.person"/>",
		"<c:url value="/sys/authorization/sys_auth_research/sysAuthResearch.do?method=researchPerson"/>&personId=!{value}&type=0",
		ORG_TYPE_PERSON | ORG_FLAG_BUSINESSALL
	);
	n10.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="sysAuthResearch.tree.role"/>",
		"<c:url value="/sys/authorization/sys_auth_research/sysAuthResearch_roletree.jsp"/>",
		2
	);
	</c:if>
</kmss:auth>
<kmss:auth requestURL="/sys/authorization/sys_auth_category/sysAuthCategory.do?method=list" requestMethod="GET">
	n2.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="sysAuthCategory.setting"/>",
		"<c:url value="/sys/authorization/sys_auth_category/index.jsp"/>"
	);
</kmss:auth>
<% } else { %>
<kmss:auth requestURL="/sys/authorization/sys_auth_role/sysAuthRole.do?method=list&type=0" requestMethod="GET">
	n1.AppendURLChild("<bean:message bundle="sys-authorization" key="sysAuthTemplate.name"/>",
		"<c:url value="/sys/authorization/sys_auth_template/index.jsp?type=${fdType}&fdTemplate=1" />"
	);	
</kmss:auth>
	n9 = n1.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="table.sysAuthRole"/>"
	);
<kmss:auth requestURL="/sys/authorization/sys_auth_category/sysAuthCategory.do?method=list" requestMethod="GET">
	n9.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="sysAuthCategory.setting"/>",
		"<c:url value="/sys/authorization/sys_auth_category/index.jsp"/>"
	);
</kmss:auth>
<kmss:auth requestURL="/sys/authorization/sys_auth_role/sysAuthRole.do?method=list&type=0" requestMethod="GET">
	defaultNode = n11 = n9.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="table.sysAuthRole" />",
		"<c:url value="/sys/authorization/sys_auth_role/index.jsp?type=0" />"
	);
	n11.AppendBeanData("sysAuthCategoryTreeService&categoryId=!{value}",
		"<c:url value="/sys/authorization/sys_auth_role/index.jsp?type=0&categoryId=!{value}" />"
	);
</kmss:auth>
<kmss:auth requestURL="/sys/authorization/sys_auth_research/sysAuthResearch" requestMethod="GET">
	n10 = n1.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="sysAuthRole.search"/>"
	);
	n10.AppendLV2Child(
		"<bean:message bundle="sys-authorization" key="sysAuthResearch.tree.person"/>",
		"<c:url value="/sys/authorization/sys_auth_research/sysAuthResearch.do?method=researchPerson"/>&personId=!{value}&type=0",
		ORG_TYPE_PERSON | ORG_FLAG_BUSINESSALL
	);
	n10.AppendURLChild(
		"<bean:message bundle="sys-authorization" key="sysAuthResearch.tree.role"/>",
		"<c:url value="/sys/authorization/sys_auth_research/sysAuthResearch_roletree.jsp"/>",
		2
	);
</kmss:auth>
<% } %>

<% if (UserUtil.getKMSSUser().isAdmin() && (LicenseUtil.getAreaLicence() == -1 || LicenseUtil.getAreaLicence() > 0)) { %>
	//==========集团分级数据迁移======
	n1.AppendURLChild("<bean:message bundle="sys-authorization" key="sys.sysAuthAreaTransfer"/>",
		"<c:url value="/sys/authorization/sysAuthAreaTransfer.do?method=guide"/>"
	);	
<% } %>
	LKSTree.ExpandNode(n9);
	LKSTree.ExpandNode(n10);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>