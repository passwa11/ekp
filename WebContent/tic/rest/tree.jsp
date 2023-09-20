<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"REST集成",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5,n_sync;
	n1 = LKSTree.treeRoot;
	<%-- 概览 --%>
	n2 = n1.AppendURLChild(
		"概览",
		""
	);

	<%-- 配置分类 --%>
		n2 = n1.AppendURLChild(
		"注册分类",
		"<c:url value="/tic/rest/connector/tic_rest_sett_category/ticRestSettCategory_tree.jsp?fdAppType=5" />"
	);

	<%-- 业务分类 --%>
		n2 = n1.AppendURLChild(
		"业务分类",
		"<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.tic.rest.connector.model.TicRestCategory&actionUrl=/tic/rest/connector/tic_rest_category/ticRestCategory.do&formName=ticRestCategoryForm&rightFlag=1&docFkName=fdCategory" />"
	
	);

	<%-- REST函数管理 --%>
		n2 = n1.AppendURLChild(
		"函数管理",
		"<c:url value="/tic/rest/rest_setting_index.jsp?fdAppType=5" />"
	);

		<%-- 转换适配 --%>
	n2 = n1.AppendURLChild(
		"转换适配",
		"<c:url value="/tic/core/common/tic_core_trans_sett/index.jsp?modelName=com.landray.kmss.tic.rest.connector.model.TicRestCategory&fdAppType=5"/>"
	);
		<%--计划任务 --%>
	n2 = n1.AppendURLChild(
		"计划任务",
		"<c:url value="/tic/core/sync/tic_core_sync_job/ticCoreSyncJob_ui_include.jsp?fdAppType=5"/>"
	);
	
		<%-- 日志信息 --%>
	n2 = n1.AppendURLChild(
		"日志信息",
		""
	);
	<%-- 集成日志 --%>
	n3 = n2.AppendURLChild(
		"集成日志",
		"<c:url value="/tic/core/log/tic_core_log_main/ticCoreLogMain_ui_include.jsp?isError=1&displayName=&subDisplayName=&logResultType=Normal&fdAppType=5"/>"
	);
	<%-- 操作日志 --%>
	n3 = n2.AppendURLChild(
		" 操作日志",
		"<c:url value="/tic/core/log/tic_core_log_opt/ticCoreLogOpt_ui_include.jsp?fdAppType=5"/>"
	);
		<%-- 操作日志配置 --%>
	n3 = n2.AppendURLChild(
		"操作日志配置",
		"<c:url value="/tic/core/log/tic_core_log_manage/ticCoreLogManage.do?method=edit&fdAppType=5"/>"
	);
	
	<%-- 其他配置 --%>
	n2 = n1.AppendURLChild(
		"其他配置",
		""
	);
	<%--导入导出 --%>
	n3 = n2.AppendURLChild(
		"导入导出",
		"<c:url value="/tic/core/inoutdata/ticCoreInoutdata_index.jsp?fdAppType=5"/>"
	);

	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
<%@ include file="/resource/jsp/tree_down.jsp" %>
