<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sys.profile.lbpm.workflow" bundle="sys-profile" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, n6, n7, n8,n9, defaultNode;
	n1 = LKSTree.treeRoot;

	<kmss:authShow roles="ROLE_SYS_LBPM_BASE_MODULE_ADMIN;ROLE_SYS_LBPM_BASE_MODULE_SYSADMIN">
	//========== 基础设置 ==========
	defaultNode = n2 = n1.AppendURLChild(
        "<bean:message bundle="sys-lbpmservice" key="module.node.paramsSetup.base" />",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmBaseInfo" />"
	);
	
	//========== 默认操作方式 ==========
	n3 = n1.AppendURLChild(
		"<bean:message bundle="sys-lbpmservice" key="module.node.paramsSetup.operations" />",
		"<c:url value="/sys/lbpmservice/support/lbpm_oper_main/index.jsp" />"
	);
	
	//========== 功能开关 ==========
	n4 = n1.AppendURLChild(
		"<bean:message bundle="sys-lbpmservice" key="module.node.paramsSetup.functionSwitch" />",
		"<c:url value="/sys/lbpmservice/support/lbpmConfigAction.do?method=edit&modelName=com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting"/>"
	);
	
	//========== 默认值设置  ==========
	n5 = n1.AppendURLChild(
		"<bean:message bundle="sys-lbpmservice" key="module.node.paramsSetup.defaultSetting" />",
		"<c:url value="/sys/lbpmservice/support/lbpmConfigAction.do?method=edit&modelName=com.landray.kmss.sys.lbpmservice.support.model.LbpmSettingDefault" />"
	);
	
	//========== 默认审批语 ==========
	n6 = n1.AppendURLChild(
		"<bean:message bundle="sys-lbpmservice" key="module.node.paramsSetup.defaultUsage" />",
		"<c:url value="/sys/lbpmservice/support/lbpm_usage/lbpmUsage.do?method=edit&sys=true" />"
	);
	
	//========== 默认审批通过意见 ==========
	n7 = n1.AppendURLChild(
		"<bean:message key="module.node.paramsSetup.defaultUsageContent" bundle="sys-lbpmservice"/>",
		"<c:url value="/sys/lbpmservice/support/lbpmConfigAction.do?method=edit&modelName=com.landray.kmss.sys.lbpmservice.support.model.LbpmUsageContent"/>"
	);
	
	//========== 流程小工具 ==========
	n8 = n1.AppendURLChild(
		"<bean:message key="module.node.paramsSetup.lbpmTools" bundle="sys-lbpmservice"/>",
		"<c:url value="/sys/lbpmservice/support/lbpm_tools/index.jsp?s_default=true"/>"
	);
	
	<%-- //========== 审批要点 ==========
	n5 = n1.AppendURLChild(
		"<bean:message key="table.lbpmExtAuditPoint" bundle="sys-lbpmext-auditpoint" />",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.lbpmext.auditpoint.model.LbpmExtAuditPointConfig"/>"
	); --%>

	<%-- 未实现功能暂时隐藏 
	//========== 流程模板总数 ==========
	n6 = n1.AppendURLChild(
		"流程模板总数",
		"<c:url value="/sys/profile/building.jsp" />"
	);
	
	//========== 流程模板范本 ==========
	n7 = n1.AppendURLChild(
		"流程模板范本",
		"<c:url value="/sys/profile/building.jsp" />"
	);
	--%>
	</kmss:authShow>
	
	<kmss:authShow roles="ROLE_LBPMPRESSLOG_DEFAULT">
	//========== 催办日志 ==========
	n9 = n1.AppendURLChild(
		"<bean:message key="module.sys.lbpmservice.operationLog" bundle="sys-lbpmservice-support"/>");
	
	var lbpmPressLogMenu=n9.AppendURLChild(
		'<bean:message key="module.sys.lbpmservice.lbpmPressLog.support" bundle="sys-lbpmservice-support" />',
		'<c:url value="/sys/lbpmservice/support/lbpm_press_log/lbpmPressLog_list.jsp?s_default=true"/>'
	);
	
	 //==========特权人操作日志 ==========
	
	var lbpmPrivilegeLogMenu=n9.AppendURLChild(
		'<bean:message key="module.sys.lbpmservice.lbpmPrivilegeLog.support" bundle="sys-lbpmservice-support" />',
		'<c:url value="/sys/lbpmservice/support/lbpm_privilege_log/lbpmPrivilegeLog_list.jsp?"/>'
	);
	
	</kmss:authShow>	
	<kmss:authShow roles="ROLE_SYS_LBPM_BASE_MODULE_ADMIN;ROLE_SYS_LBPM_EMBEDDEDSUBFLOW">
	<!-- 嵌入子流程 -->
	var embeddedSubFlow = n1.AppendURLChild(
        "<bean:message bundle='sys-lbpmservice-support' key='table.lbpmEmbeddedSubFlow' />"
	);
	<!-- 类别设置 -->
	embeddedSubFlow.AppendURLChild(
		"<bean:message bundle='sys-lbpmservice-support' key='lbpmEmbeddedSubFlowCategory.set' />",
		"<c:url value='/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.sys.lbpmservice.support.model.LbpmEmbeddedSubFlowCategory&actionUrl=/sys/lbpmservice/support/lbpmEmbeddedSubFlowCategory.do&formName=lbpmEmbeddedSubFlowCategoryForm&rightFlag=1' />"
	);
	<!-- 嵌入子流程 -->					
	var embeddedSubFlowTree = embeddedSubFlow.AppendURLChild("<bean:message bundle='sys-lbpmservice-support' key='lbpmEmbeddedSubFlow.templateSet' />");
	embeddedSubFlowTree.authType="02";
	embeddedSubFlowTree.AppendSimpleCategoryDataWithAdmin ("com.landray.kmss.sys.lbpmservice.support.model.LbpmEmbeddedSubFlowCategory",
							'<c:url value="/sys/lbpmservice/support/lbpmEmbeddedSubFlow.do?method=manageList&categoryId=!{value}&status=all&showDocStatus=true" />',
							'<c:url value="/sys/lbpmservice/support/lbpmEmbeddedSubFlow.do?method=listChildren&type=category&categoryId=!{value}" />');
	<!-- 片段组设置 -->
	var embeddedSubFlowTree = embeddedSubFlow.AppendURLChild("<bean:message bundle='sys-lbpmservice-support' key='lbpmDynamicSubFlow.partgroupSet' />");
	embeddedSubFlowTree.authType="02";
	embeddedSubFlowTree.AppendSimpleCategoryDataWithAdmin ("com.landray.kmss.sys.lbpmservice.support.model.LbpmEmbeddedSubFlowCategory",
		'<c:url value="/sys/lbpmservice/support/lbpmDynamicSubFlow.do?method=manageList&categoryId=!{value}&status=all&showDocStatus=true" />',
		'<c:url value="/sys/lbpmservice/support/lbpmDynamicSubFlow.do?method=listChildren&type=category&categoryId=!{value}" />');
	</kmss:authShow>
	<kmss:authShow roles="ROLE_SYS_LBPM_SUMMARY_CREATE">
	<!-- 汇总审批设置 -->
	n10 = n1.AppendURLChild(
		"<bean:message key="module.sys.lbpmservice.summaryApproval" bundle="sys-lbpmservice-support"/>",
		"<c:url value="/sys/lbpmservice/support/lbpm_summary_approval/config/index.jsp"/>"
	);
	</kmss:authShow>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>