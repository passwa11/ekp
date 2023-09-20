<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.landray.kmss.sys.mobile.util.MobileConfigPlugin" %>
<%@ page import="com.landray.kmss.third.pda.util.LicenseUtil"%>

<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.third.tree.application.management" bundle="third-pda"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, n6, n7,defaultNode;
	n1 = LKSTree.treeRoot;
	
	//========== 模块分类 ==========
	<kmss:authShow roles="ROLE_THIRDPDA_ADMIN">
	n2 = n1.AppendURLChild(
		"<bean:message key="module.third.tree.moduleCategory" bundle="third-pda"/>",
		"<c:url value="/third/pda/pda_module_cate/index.jsp" />"
	);
	
	//========== 移动应用配置 ==========
	defaultNode = n3 = n1.AppendURLChild(
		"<bean:message key="module.third.tree.application.configuration" bundle="third-pda"/>",
		"<c:url value="/third/pda/pda_module_config_main/index.jsp" />"
	);
	</kmss:authShow>
	//========== 应用页签配置 ==========
	<kmss:authShow roles="ROLE_SYSMOBILE_ADMIN">
	n4 = n1.AppendURLChild(
		"<bean:message key="module.third.tree.tabs.configuratio" bundle="third-pda"/>"
	);
	LKSTree.ExpandNode(n4);
	</kmss:authShow>
	
	
	//========== 参数配置 ==========
	<kmss:authShow roles="ROLE_THIRDPDA_ADMIN">
	n5 = n1.AppendURLChild(
		"<bean:message key="module.third.tree.setting.other" bundle="third-pda"/>",
		"<c:url value="/third/pda/pda_rows_per_page_config/pdaRowsPerPageConfig.do?method=edit" />"
	);
	
	//========== 压缩合并设置 ==========
	n6 = n1.AppendURLChild(
		"<bean:message key="module.third.tree.zipSetting" bundle="third-pda"/>",
		"<c:url value="/sys/mobile/sys_mobile_compress/index.jsp" />"
	);
	</kmss:authShow>
	
	
	<kmss:authShow roles="ROLE_SYSMOBILE_ADMIN">
	n8 = n1.AppendURLChild(
		"<bean:message key="module.third.tree.offlineApp" bundle="third-pda"/>",
		"<c:url value="/sys/mobile/sys_mobile_offline/index.jsp" />"
	);
	</kmss:authShow>
	//================================== 二级目录 =====================================//	
	
	
	<%-- 应用页签配置    二级目录 --%>
	<kmss:authShow roles="ROLE_SYSMOBILE_ADMIN">
	<%
	List<MobileConfigPlugin.MobileConfigInfo> modules = MobileConfigPlugin.findModuleConfigs();
	for (int i = 0; i < modules.size(); i ++) {
		MobileConfigPlugin.MobileConfigInfo cfgPage = (MobileConfigPlugin.MobileConfigInfo) modules.get(i);
	%>
		n4.AppendURLChild(
			"<c:out value="<%=cfgPage.getName() %>" escapeXml="false"/>",
			"<c:url value="/sys/mobile/sys_mobile_module_category_config/sysMobileModuleCategoryConfig.do">
				<c:param name="fdModelName" value="<%=cfgPage.getModelName() %>" />
				<c:param name="method" value="edit" />
			</c:url>"
		);
	<%
	}
	%>
	</kmss:authShow>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	if(defaultNode){
		LKSTree.ClickNode(defaultNode);
	}
}
	</template:replace>
</template:include>