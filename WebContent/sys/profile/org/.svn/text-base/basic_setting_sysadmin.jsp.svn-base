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
	
	<kmss:authShow roles="SYSROLE_ADMIN;SYSROLE_SYSADMIN">
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