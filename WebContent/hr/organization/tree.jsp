<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
	<template:replace name="content">
	function generateTree()
	{
	    window.LKSTree = new TreeView( 
	        'LKSTree',
	        '<bean:message bundle="hr-organization" key="module.hr.organization"/>',
	        document.getElementById('treeDiv')
	    );
	    var node = LKSTree.treeRoot; 
	    <!-- var n1, n2, n3, n4, n11, n12, defaultNode; -->
		<!-- 暂时在此声明,后续使用组件扩充 -->
		var HR_TYPE_HRORDEPT = 0x3,HR_FLAG_BUSINESSALL = 0xc00;
		<!-- 组织架构授权 -->
		var n1 = node.AppendURLChild(
			"<bean:message key="hr.organization.hrStaffFileAuthor" bundle="hr-organization"/>"
		);
		n1.AppendBeanData(
			"hrOrganizationTree&parent=!{value}&orgType="+(HR_TYPE_HRORDEPT|HR_FLAG_BUSINESSALL)+"&sys_page=true"+"&isTree=true",
			"<c:url value="/hr/organization/hr_org_file_author/hrOrgFileAuthor.do?method=config"/>&parentId=!{value}", 
			null, true);
	    
	    <!-- 同步规则 -->
		var n2 = node.AppendURLChild(
			"<bean:message key="hr.organization.sync.rule" bundle="hr-organization"/>",
			"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting" />"
		);
			<!-- 人事组织架构开关 -->
		var n3 = node.AppendURLChild(
		"<bean:message key="hr.organization.personinfo.setting" bundle="hr-organization"/>",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.hr.organization.model.HrOrganizationPersoninfoSetting" />"
		)
		<!-- 编制统计规则 -->
		var n4 = node.AppendURLChild(
		"<bean:message key="hr.organization.Compilation.rule" bundle="hr-organization"/>",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.hr.organization.model.HrOrganizationCompilingSum" />"
		)
	     
	    LKSTree.Show();
	}
	</template:replace>
</template:include>