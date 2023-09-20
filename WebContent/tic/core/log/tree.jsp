<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.tic.core.log" bundle="tic-core-log"/>",
		document.getElementById("treeDiv")
	);
	var n1_log, n2_log, n3_log, n4, n5;
	n1_log = LKSTree.treeRoot;
	
	<%-- 日志配置ticCoreLogManage --%>
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
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
<%@ include file="/resource/jsp/tree_down.jsp" %>