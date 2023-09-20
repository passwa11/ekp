﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
<%@ page import="com.landray.kmss.tic.core.mapping.plugins.TicCoreMappingIntegrationPlugins"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%
// 组件解耦分离
List<Map<String, String>> listMap = TicCoreMappingIntegrationPlugins.getConfigs();
for (Map<String, String> map : listMap) {
	if (map.get("integrationKey").equals("SAP")) {
		request.setAttribute("sapFlag", "1");
	} else if (map.get("integrationKey").equals("SOAP")) {
		request.setAttribute("soapFlag", "1");
	} else if (map.get("integrationKey").equals("JDBC")) {
		request.setAttribute("jdbcFlag", "1");
	}
}
%>
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.tic.core" bundle="tic-core"/>",
		document.getElementById("treeDiv")
	);
	var n1_common, n2_common, n1_log,n3, n4, n5;
	n1_common = LKSTree.treeRoot;
	
	<kmss:authShow roles="ROLE_TIC_COMMON_ADMIN">
	n_mapping=n1_common.AppendURLChild("<bean:message key="module.tic.core.mapping" bundle="tic-core-mapping" />","");
	
	n1_log=n1_common.AppendURLChild("<bean:message key="tic.core.log.manager" bundle="tic-core-log" />","");
	
	
	
	<%-- 应用模块注册 --%>
	n1_mapping = n_mapping.AppendURLChild(
		"<bean:message key="table.ticCoreMappingModule" bundle="tic-core-mapping" />",
		"<c:url value="/tic/core/mapping/tic_core_mapping_module/ticCoreMappingModule.do?method=list" />"
	);
	<%-- 表单流程映射  --%>
	n2_mapping = n_mapping.AppendURLChild(
		"<bean:message key="tree.form.flow.mapping" bundle="tic-core-mapping" />",
		""
	);
	<%--加载模块树--%>
	n3_common = n2_mapping.AppendBeanData("ticCoreMappingModuleTreeService&id=!{value}"
	);
	
	<%-- 日志配置ticCoreManage --%>
	n2_log = n1_log.AppendURLChild(
		"<bean:message key="table.ticCoreLogManage" bundle="tic-core-log" />",
		"<c:url value="/tic/core/log/tic_core_log_manage/ticCoreLogManage.do?method=edit" />"
	);
	<%-- 操作日志 --%>
	n2_log = n1_log.AppendURLChild(
		"<bean:message key="table.ticCoreLogOpt" bundle="tic-core-log" />",
		"<c:url value="/tic/core/log/tic_core_log_opt/ticCoreLogOpt.do?method=list" />"
	);
	<%-- 日志管理 --%>
	n2_log = n1_log.AppendURLChild(
		"<bean:message key="tic.core.log.manager" bundle="tic-core-log" />"
	);
	
	n2_log_type = n2_log.AppendBeanData("ticCoreLogTypeTreeService&id=!{value}");
	
	<%-- 导入导出 --%>
	var n1_imExport= n1_common.AppendURLChild(
		"<bean:message key="module.tic.core.inoutdata" bundle="tic-core-inoutdata" />",
		""
	);
	
	<%-- 导出 --%>
	n2_export = n1_imExport.AppendURLChild(
		"<bean:message key="imExport.dataExport" bundle="tic-core-inoutdata" />",
		"<c:url value="/tic/core/inoutdata/ticCoreInoutdata_export.jsp" />"
	);
	
	<%-- 导入 --%>
	n2_import = n1_imExport.AppendURLChild(
		"<bean:message key="imExport.dataImport" bundle="tic-core-inoutdata" />",
		"<c:url value="/tic/core/inoutdata/ticCoreInoutdata_upload.jsp" />"
	);
	
	 <c:if test="${jdbcFlag == '1' }">
		   <%--缓存配置--%>
		n1_common.AppendURLChild(
		"<bean:message key="ticCore.config.cache" bundle="tic-core" />",
		"<c:url value="/tic/core/config/tic_core_config/ticCoreConfig.do?method=edit" />"
		);
	</c:if>

	<%-- 数据初始化 --%>
	var n1_init = n1_common.AppendURLChild(
		"<bean:message key="module.init.data" bundle="tic-core-init" />",
		"<c:url value="/tic/core/init/ticCoreInit.do?method=showInit" />"
	);
	</kmss:authShow>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ExpandNode(n1_log);
	LKSTree.ExpandNode(n1_imExport);
	LKSTree.ClickNode(n1_mapping);
}
<%@ include file="/resource/jsp/tree_down.jsp" %>
