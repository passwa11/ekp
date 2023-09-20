<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"${lfn:message('tic-core-common:ticCoreCommon.businessIntegrate')}",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5,n_sync;
	n1 = LKSTree.treeRoot;
	<%-- 概览 
	n2 = n1.AppendURLChild(
		"概览",
		""
	);
	--%>
	<%-- 业务分类 --%>
		n2 = n1.AppendURLChild(
		"${lfn:message('tic-core-common:table.ticCoreBusiCate')}",
		"<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.tic.business.connector.model.TicBusinessCategory&actionUrl=/tic/business/connector/tic_business_category/ticBusinessCategory.do&formName=ticBusinessCategoryForm&rightFlag=1&docFkName=fdCategory" />"
	);
	
	<%-- 函数管理 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="FunctionSetting" bundle="tic-business-connector" />",
		""
	);
	
	<%--BAPI 
	n3 = n2.AppendURLChild(
		"BAPI",
		"<c:url value="/tic/sap/bapi_setting_index.jsp?fdAppType=5"/>"
	);
	--%>
	
	<%-- SOAP --%>
	<kmss:ifModuleExist path="/tic/soap/">
	n3 = n2.AppendURLChild(
		"SOAP",
		"<c:url value="/tic/soap/soap_setting_index.jsp?modelName=com.landray.kmss.tic.business.connector.model.TicBusinessCategory&fdAppType=5"/>"
	);
	</kmss:ifModuleExist>
	
	<%--JDBC--%>
	<kmss:ifModuleExist path="/tic/jdbc/">
	n3 = n2.AppendURLChild(
		"JDBC",
		"<c:url value="/tic/jdbc/jdbc_setting_index.jsp?modelName=com.landray.kmss.tic.business.connector.model.TicBusinessCategory&fdAppType=5"/>"
	);
	</kmss:ifModuleExist>
	
	<kmss:ifModuleExist path="/tic/rest/">
	<%--REST--%>
	n3 = n2.AppendURLChild(
		"REST",
		"<c:url value="/tic/rest/rest_setting_index.jsp?modelName=com.landray.kmss.tic.business.connector.model.TicBusinessCategory&fdAppType=5"/>"
	);
	</kmss:ifModuleExist>

	<%-- 转换适配 --%>
	n2 = n1.AppendURLChild(
		"${lfn:message('tic-core-common:ticCoreTree.transAdapter')}",
		"<c:url value="/tic/core/common/tic_core_trans_sett/index.jsp?modelName=com.landray.kmss.tic.business.connector.model.TicBusinessCategory&fdAppType=5"/>"
	);
		<%--计划任务 --%>
	n2 = n1.AppendURLChild(
		"${lfn:message('tic-core-common:ticCoreTree.planJob')}",
		"<c:url value="/tic/core/sync/tic_core_sync_job/ticCoreSyncJob_ui_include.jsp?modelName=com.landray.kmss.tic.business.connector.model.TicBusinessCategory&fdAppType=5"/>"
		
	);
	
		<%-- 日志信息 --%>
	n2 = n1.AppendURLChild(
		"${lfn:message('tic-core-common:ticCoreTree.logInfo')}",
		""
	);
	<%-- 集成日志 --%>
	n3 = n2.AppendURLChild(
		"${lfn:message('tic-core-common:ticCoreTree.integrationLog')}",
		"<c:url value="/tic/core/log/tic_core_log_main/ticCoreLogMain_ui_include_iframe.jsp?isError=0&displayName=&subDisplayName=&logResultType=Normal&fdAppType=5"/>"
	);
	<%-- 操作日志 --%>
	n3 = n2.AppendURLChild(
		"${lfn:message('tic-core-common:ticCoreTree.operateLog')}",
		"<c:url value="/tic/core/log/tic_core_log_opt/ticCoreLogOpt_ui_include.jsp?fdAppType=5"/>"
	);
		<%-- 操作日志配置 --%>
	n3 = n2.AppendURLChild(
		"${lfn:message('tic-core-common:ticCoreTree.operateLogSetting')}",
		"<c:url value="/tic/core/log/tic_core_log_manage/ticCoreLogManage.do?method=edit&fdAppType=5"/>"
	);
		
	<%-- 其他配置 --%>
	n2 = n1.AppendURLChild(
		"${lfn:message('tic-core-common:ticCoreTree.otherSetting')}",
		""
	);
	<%--导入导出 --%>
	n3 = n2.AppendURLChild(
		"${lfn:message('tic-core-common:ticCoreTree.importExport')}",
		"<c:url value="/tic/core/inoutdata/ticCoreInoutdata_iframe.jsp?fdAppType=5"/>"
		
	);
		
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
<%@ include file="/resource/jsp/tree_down.jsp" %>
