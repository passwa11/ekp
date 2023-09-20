<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        '<bean:message key="module.sys.help" bundle="sys-help" />',//根节点的名称
        document.getElementById('treeDiv')
    );
    var n1, n2, n3, n4;
    var n1 = LKSTree.treeRoot; 
	<kmss:authShow roles="ROLE_SYSHELP_SETTING">
	
	<!-- 开启帮助中心 -->
	n2 = n1.AppendURLChild(
		"<bean:message key="sysHelpConfig.open" bundle="sys-help" />",
		"<c:url value="/sys/help/sys_help_module_main/sysHelpModuleMain.do?method=config" />"
	);
	
    <!-- 模块功能页面路径设置 -->
	n3 = n1.AppendURLChild(
		"<bean:message key="sysHelpConfig.url" bundle="sys-help" />",
		"<c:url value="/sys/help/sys_help_config/index.jsp" />"
	);
	
	<!-- 帮助文档管理 -->
	n4 = n1.AppendURLChild(
		"<bean:message key="tree.sys.help.main.management" bundle="sys-help" />"
	);
	
	<!-- 所有文档 -->
	n5 = n4.AppendURLChild(
		"<bean:message key="tree.sys.help.main.all" bundle="sys-help" />",
		"<c:url value="/sys/help/sys_help_main/index.jsp" />"
	);
	
	<!-- 草稿箱 -->
	<%-- n6 = n4.AppendURLChild(
		"<bean:message key="tree.sys.help.main.draft" bundle="sys-help" />",
		""
	); --%>
	</kmss:authShow>
    
    LKSTree.Show();
}
</template:replace>
</template:include>