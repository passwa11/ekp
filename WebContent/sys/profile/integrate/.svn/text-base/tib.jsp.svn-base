<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"TIB集成",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, n6, n7;
	n1 = LKSTree.treeRoot;
	
	//========== tib公共组件 ==========
	n2 = n1.AppendURLChild(
		"tib公共组件"
	);
	
	//表单映射模块
	n3 = n2.AppendURLChild(
		"表单映射模块"
	);
	//日志管理
	n4 = n2.AppendURLChild(
		"日志管理"
	);
	//导入导出
	n5 = n2.AppendURLChild(
		"导入导出"
	);
	<kmss:authShow roles="ROLE_TIB_COMMON_ADMIN">
	n2.AppendURLChild(
		"数据初始化",
		"<c:url value="/tib/common/init/tibCommonInit.do?method=showInit" />"
	);
	</kmss:authShow>
	
	
	//================================== 二级目录 =====================================//	
	
	
	<%-- 表单映射模块    二级目录 --%>
	<!-- 应用模块注册 -->
	n3.AppendURLChild(
		"<bean:message key="table.tibCommonMappingModule" bundle="tib-common-mapping" />",
		"<c:url value="/tib/common/mapping/tib_common_mapping_module/tibCommonMappingModule.do?method=list" />"
	);
	<!-- 表单流程映射 -->
	n6 = n3.AppendURLChild(
		"<bean:message key="tree.form.flow.mapping" bundle="tib-common-mapping" />",
		""
	);
	n6.AppendBeanData("tibCommonMappingModuleTreeService&id=!{value}");
	
	<%-- 日志管理    二级目录 --%>
	<!-- 日志配置 -->
	n4.AppendURLChild(
		"<bean:message key="table.tibCommonLogManage" bundle="tib-common-log" />",
		"<c:url value="/tib/common/log/tib_common_log_manage/tibCommonLogManage.do?method=edit" />"
	);
	<!-- 操作日志 -->
	n4.AppendURLChild(
		"<bean:message key="table.tibCommonLogOpt" bundle="tib-common-log" />",
		"<c:url value="/tib/common/log/tib_common_log_opt/tibCommonLogOpt.do?method=list" />"
	);
	<!-- 日志管理 -->
	n7 = n4.AppendURLChild(
		"<bean:message key="tib.common.log.manager" bundle="tib-common-log" />"
	);
	n7.AppendBeanData("tibCommonLogTypeTreeService&id=!{value}");
	
	<%-- 导入导出    二级目录 --%>
	<kmss:authShow roles="ROLE_TIB_COMMON_ADMIN">
	<!-- 导出 -->
	n5.AppendURLChild(
		"<bean:message key="imExport.dataExport" bundle="tib-common-inoutdata" />",
		"<c:url value="/tib/common/inoutdata/tibCommonInoutdata_export.jsp" />"
	);
	<!-- 导入 -->
	n5.AppendURLChild(
		"<bean:message key="imExport.dataImport" bundle="tib-common-inoutdata" />",
		"<c:url value="/tib/common/inoutdata/tibCommonInoutdata_upload.jsp" />"
	);
	</kmss:authShow>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
	</template:replace>
</template:include>