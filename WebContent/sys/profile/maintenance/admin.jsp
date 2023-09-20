<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sys.profile.maintenance.admin" bundle="sys-profile" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, n6, n7, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:authShow roles="SYSROLE_ADMIN;SYSROLE_SYSADMIN">
	//========== 版本升级向导 ==========
	n2 = n1.AppendURLChild(
        "<bean:message key="sys.admin.sysupgradeWizard" bundle="sys-admin" />",
        "<c:url value="/sys/admin/sysAdminUpgrade_wizard.jsp"/>"
	);
	
	//========== 数据初始化 ==========
	n3 = n1.AppendURLChild(
		"<bean:message key="sys.profile.maintenance.init" bundle="sys-profile" />"
	);
	
	//========== 常用工具集 ==========
	defaultNode = n4 = n1.AppendURLChild(
		"<bean:message key="sys.sysAdminCommonTools" bundle="sys-admin" />",
		"<c:url value="/sys/admin/commontools.do"/>"
	);
	
	//========== 请求监控 ==========
	n5 = n1.AppendURLChild(
		"<bean:message key="sys.sysAdminThreadMonitor" bundle="sys-admin" />",
		"<c:url value="/sys/admin/threadmonitor/index.jsp"/>"
	);
	
	//#136126 最终决定删除ekp/sys/common/debug.jsp
<%--	n1.AppendURLChild(--%>
<%--		"<bean:message bundle="sys-admin" key="sys.sysAdminConfig"/>",--%>
<%--		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.admin.model.SysAdminConfig"/>"--%>
<%--	);--%>
	//================================== 二级目录 =====================================//	
	
	
	<%-- 版本升级向导   二级目录 --%>
	<!-- 系统初始化 -->
	n2.AppendURLChild(
		"<bean:message key="global.init.system"/>",
		"<c:url value="/sys/common/config.do?method=systemInitPage"/>"
	);
	<!-- 数据库检测 -->
	n2.AppendURLChild(
		"<bean:message bundle="sys-admin" key="sysAdminDbchecker.dbchecker"/>",
		"<c:url value="/sys/admin/sys_admin_dbchecker/sysAdminDbchecker.do?method=select"/>"
	);
	<!-- 兼容性检测 -->
	n2.AppendURLChild(
		"<bean:message bundle="sys-admin-transfer" key="module.sys.admin.transfer"/>",
		"<c:url value="/sys/admin/transfer/sys_admin_transfer_task/index.jsp"/>"
	);
	<!-- 多语言检测 -->
	n2.AppendURLChild(
		"<bean:message bundle="sys-profile" key="sys.profile.i18n.checker"/>",
		"<c:url value="/sys/profile/i18n/resetI18n_upgrade.jsp"/>"
	);
	
	<%-- 数据初始化   二级目录 --%>
	
	<!-- 三员管理员不能导出数据 -->
	<kmss:auth requestURL="/sys/datainit/sys_datainit_main/sysDatainit_select.jsp">
	<!-- 导入系统基础数据 -->
	n3.AppendURLChild(
	"<bean:message key="global.init.import.baseData"/>",
		"<c:url value="/sys/datainit/sys_datainit_main/sysDatainitMain.do?method=showStatus&type=baseImport"/>"
	);
	<!-- 导出设置 -->
	n7 = n3.AppendURLChild(
		"<bean:message key="global.init.data.download"/>"
	);
	<!-- 选择导出数据 -->
	n7.AppendURLChild(
		"<bean:message key="global.init.data.download.select"/>",
		"<c:url value="/sys/datainit/sys_datainit_main/sysDatainit_select.jsp"/>"
	);
	<!-- 打包下载 -->
	n7.AppendURLChild(
		"<bean:message key="global.init.data.download.export"/>",
		"<c:url value="/sys/datainit/sys_datainit_main/sysDatainit_export.jsp"/>"
	);
	</kmss:auth>
	<!-- 导入设置 -->
	n4 = n3.AppendURLChild(
		"<bean:message key="global.init.export"/>"
	);
	<!-- 上传数据 -->
	n4.AppendURLChild(
	"<bean:message key="global.init.data.upload"/>",
		"<c:url value="/sys/datainit/sys_datainit_main/sysDatainit_upload.jsp" />"
	);
	<!-- 导入数据 -->
	n4.AppendURLChild(
	"<bean:message key="global.init.export.data"/>",
		"<c:url value="/sys/datainit/sys_datainit_main/sysDatainitMain.do?method=showStatus"/>"
	);
	
	LKSTree.ExpandNode(n2);
	LKSTree.ExpandNode(n3);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
	</kmss:authShow>
}
	</template:replace>
</template:include>