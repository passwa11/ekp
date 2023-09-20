<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
var personImport, deptImport, orgImport, defaultNode;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="org.basic.setting" bundle="sys-organization" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:authShow roles="ROLE_SYSORG_DEFAULT">
	//========== 职务设置 ==========
	<kmss:authShow roles="ROLE_SYSORG_STAFFING_LEVEL_ADMIN">
	n2 = n1.AppendURLChild(
		"<bean:message key="sysOrganizationStaffingLevel.config" bundle="sys-organization" />",
		"<c:url value="/sys/organization/sys_organization_staffing_level/index.jsp" />"
	);
	</kmss:authShow>
	
	<kmss:authShow roles="ROLE_SYSORG_TRANSPORT">
	//========== 基础数据导入导出 ==========
	defaultNode = n3 = n1.AppendURLChild(
		"<bean:message key="sys.profile.org.base.io" bundle="sys-profile" />",
		"<c:url value="/sys/profile/org/io/org_io.jsp" />"
	);
	
	//========== 高级数据查询 ==========
	n5 = n1.AppendURLChild(
		"<bean:message key="org.advanced.data.query" bundle="sys-organization" />"
	);
	
	//========== 高级数据导入 ==========
	n6 = n1.AppendURLChild(
		"<bean:message key="org.advanced.data.import" bundle="sys-organization" />"
	);
	</kmss:authShow>
	
	//========== 参数设置 ==========
	<kmss:authShow roles="ROLE_SYSORG_CONFIG">
	n7 = n1.AppendURLChild(
		"<bean:message key="org.parameter.configuration" bundle="sys-organization" />"
	);
	
	//========== 地址本隔离 ==========
	n8 = n1.AppendURLChild(
		"<bean:message key="org.address.isolation" bundle="sys-organization" />"
	);
	</kmss:authShow>

	//================================== 二级目录 =====================================//	
	<kmss:authShow roles="ROLE_SYSORG_TRANSPORT">
	<%-- 高级数据查询    二级目录 --%>
	<kmss:auth requestURL="/sys/search/sys_search_main/sysSearchMain.do?method=list&fdModelName=com.landray.kmss.sys.organization.model.SysOrgOrg">
	<!-- 搜索设置 -->
	n9 = n5.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrg.tree.search.config"/>"
	);
	<!-- 搜索设置（机构） -->
	n9.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.org"/>",
		"<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgOrg"/>"
	);
	<!-- 搜索设置（部门） -->
	n9.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.dept"/>",
		"<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgDept"/>"
	);
	<!-- 搜索设置（岗位） -->
	n9.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.post"/>",
		"<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgPost"/>"
	);
	<!-- 搜索设置（员工） -->
	n9.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.person"/>",
		"<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgPerson"/>"
	);
	<!-- 搜索设置（常用分组） -->
	n9.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.group"/>",
		"<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgGroup"/>"
	);
	</kmss:auth>
	
	<!-- 数据查询 -->
	n10 = n5.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrg.tree.search.query"/>"
	);
	<!-- 数据查询（机构） -->
	n10.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.org"/>",
		"<c:url value="/sys/search/tree.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgOrg&canClose=false"/>",
		2
	);
	<!-- 数据查询（部门 ）-->
	n10.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.dept"/>",
		"<c:url value="/sys/search/tree.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgDept&canClose=false"/>",
		2
	);
	<!-- 数据查询（岗位） -->
	n10.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.post"/>",
		"<c:url value="/sys/search/tree.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgPost&canClose=false"/>",
		2
	);
	<!-- 数据查询（员工） -->
	n10.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.person"/>",
		"<c:url value="/sys/search/tree.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgPerson&canClose=false"/>",
		2
	);
	<!-- 数据查询（常用分组） -->
	n10.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.group"/>",
		"<c:url value="/sys/search/tree.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgGroup&canClose=false"/>",
		2
	);
	</kmss:authShow>
	
	<%-- 高级数据导入    二级目录 --%>
	<kmss:auth requestURL="/sys/transport/sys_transport_import/SysTransportImport.do?method=list&fdModelName=com.landray.kmss.sys.organization.model.SysOrgOrg">
	<!-- 机构 -->
	orgImport = n6.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.org"/>",
		"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgOrg"/>"
	);
	<!-- 部门 -->
	deptImport = n6.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.dept"/>",
		"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgDept"/>"
	);
	<!-- 岗位 -->
	n6.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.post"/>",
		"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgPost"/>"
	);
	<!-- 员工 -->
	personImport = n6.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.person"/>",
		"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgPerson"/>"
	);
	<!-- 常用分组 -->
	n6.AppendURLChild(
		"<bean:message bundle="sys-organization" key="sysOrgElement.group"/>",
		"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.sys.organization.model.SysOrgGroup"/>"
	);
	</kmss:auth>
	
	<%-- 参数设置    二级目录 --%>
	<kmss:authShow roles="ROLE_SYSORG_CONFIG">
	<!-- 参数配置 -->
	n7.AppendURLChild(
		"<bean:message key="sysOrgPerson.config.title" bundle="sys-organization" />",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.organization.model.SysOrganizationConfig" />"
	);
	n7.AppendURLChild(
		"<bean:message key="sysOrgElement.config.default" bundle="sys-organization" />",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.organization.model.SysOrgDefaultConfig" />"
	);	
	<kmss:auth requestURL="/sys/oms/orgsynchro_notify_template_empty/orgSynchroNotifyTemplateEmpty.do?method=edit&fdId=0">
	//========== 通知配置 ==========
	n7.AppendURLChild(
		"<bean:message bundle="sys-oms-notify" key="orgSynchroNotify"/>",
		"<c:url value="/sys/oms/orgsynchro_notify_template_empty/orgSynchroNotifyTemplateEmpty.do?method=edit&fdId=0"/>"
	);
	</kmss:auth>
	
	<%-- 地址本隔离    二级目录 --%>
	<!-- 员工 -->
	<kmss:auth requestURL="/sys/organization/sys_organization_visible/sysOrganizationVisibleList.do?method=edit">
	n8.AppendURLChild(
		"<bean:message key="sysOrganizationVisible.config" bundle="sys-organization" />",
		"<c:url value="/sys/organization/sys_organization_visible/sysOrganizationVisibleList.do?method=edit" />"
	);
	</kmss:auth>
	<!-- 职级过滤配置 -->
	<kmss:auth requestURL="/sys/organization/sys_organization_staffing_level_filter/sysOrganizationStaffingLevelFilter.do?method=edit">
	n8.AppendURLChild(
		"<bean:message key="sysOrganizationStaffingLevelFilter.setting" bundle="sys-organization" />",
		"<c:url value="/sys/organization/sys_organization_staffing_level_filter/sysOrganizationStaffingLevelFilter.do?method=edit" />"
	);
	</kmss:auth>
	</kmss:authShow>
	</kmss:authShow>
	
	<kmss:authShow roles="SYSROLE_ADMIN;SYSROLE_SYSADMIN;ROLE_SYSORG_CONFIG">
	<!-- 自定义字段功能只有超级管理员可用 -->
	//========== 自定义设置 ==========
	n11 = n1.AppendURLChild(
		"<bean:message key="custom.field.settings" bundle="sys-property" />"
	);
	
	//========== 人员卡片自定义 ==========
	n11.AppendURLChild(
		"<bean:message key="custom.field.settings.person" bundle="sys-organization" />",
		"<c:url value="/sys/property/custom_field/index.jsp?modelName=com.landray.kmss.sys.organization.model.SysOrgPerson"/>"
	);
	//========== 岗位卡片自定义 ==========
	n11.AppendURLChild(
		"<bean:message key="custom.field.settings.post" bundle="sys-organization" />",
		"<c:url value="/sys/property/custom_field/index.jsp?modelName=com.landray.kmss.sys.organization.model.SysOrgPost"/>"
	);
	//========== 部门卡片自定义 ==========
	n11.AppendURLChild(
		"<bean:message key="custom.field.settings.dept" bundle="sys-organization" />",
		"<c:url value="/sys/property/custom_field/index.jsp?modelName=com.landray.kmss.sys.organization.model.SysOrgDept"/>"
	);
	//========== 机构卡片自定义 ==========
	n11.AppendURLChild(
		"<bean:message key="custom.field.settings.org" bundle="sys-organization" />",
		"<c:url value="/sys/property/custom_field/index.jsp?modelName=com.landray.kmss.sys.organization.model.SysOrgOrg"/>"
	);			
	</kmss:authShow>
	
	LKSTree.ExpandNode(n2);
	LKSTree.ExpandNode(n3);
	LKSTree.ExpandNode(n4);
	LKSTree.ExpandNode(n5);
	LKSTree.ExpandNode(n6);
	LKSTree.ExpandNode(n7);
	LKSTree.ExpandNode(n8);
	LKSTree.ExpandNode(n11);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
	//console.log("LKSTree:", LKSTree);
}

seajs.use(['lui/topic'], function(topic) {
	topic.subscribe('sys.profile.moduleMain.loaded',function(args){
		switch(args.key){
			case 'personImport' : {
				if(personImport) LKSTree.ClickNode(personImport);
				break;
			}
			case 'deptImport' : {
				if(deptImport) LKSTree.ClickNode(deptImport);
				break;
			}
			case 'orgImport' : {
				if(orgImport) LKSTree.ClickNode(orgImport);
				break;
			}
			default : {
				LKSTree.ClickNode(defaultNode);
			}
		}
	});
});
	</template:replace>
</template:include>