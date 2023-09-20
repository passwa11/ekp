<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sys.profile.lbpm.monitor" bundle="sys-profile" />",
		document.getElementById("treeDiv")
	);
	var n1, defaultNode, n2, n3;
	n1 = LKSTree.treeRoot;
	
	//========== 流程概况 ==========
	defaultNode = n1.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.processOverview" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_over/index.jsp" />"
	);
	
	<%-- //========== 流程监控 ==========
	n1.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.processMoniter" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=listChildren" />"
	); --%>
	
	 //========== 流程监控 ==========
	n1.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.processMoniter" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=getMonitorInfo" />"
	); 
	
	//========== 流程查询 ==========
	n2 = n1.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.processQuery" bundle="sys-lbpmmonitor" />"
	);
	
	//========== 所有流程 ==========
	n2.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.wholeFlow" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=listChildren&fdStatus=all" />"
	);
	
	//========== 运行时流程 ==========
	n2.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.allFlow" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=listChildren&fdStatus=20;40&fdType=running" />"
	);
	
	//========== 状态暂停的流程 ==========
	n2.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.pauseFlow" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=getPause" />"
	);
	
	//========== 我重启过的流程 ==========
	n2.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.processRestart" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=getProcessRestart" />"
	);
	
	//========== 待审超时的流程 ==========
	<%-- n2.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.expiredFlow" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=getExpired" />"
	); --%>
	n2.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.expiredFlow" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=getLimitExpired" />"
	);
	
	//========== 已结束的流程 ==========
	n2.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.finishedFlow" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=listChildren&fdStatus=00;30&fdType=finish" />"
	);
	
	//========== 流程处理 ==========
	n3 = n1.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.processHandling" bundle="sys-lbpmmonitor" />"
	);
	
	//========== 异常的流程 ==========
	n3.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.errorFlow" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=listChildren&fdStatus=21&fdType=error" />"
	);
	
	//========== 处理人无效的流程 ==========
	n3.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.notValidFlow" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=getInvalidHandler" />"
	);	
	
	//========== 状态异常的流程 ==========
	n3.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.statusErrorFlow" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=getStatusError&extend=true" />"
	);
	
	//========== 接口调用的流程 ==========
	n3.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.interfacelog.infolog" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sys_interface_log/sysLbpmMonitor_interfaceLog.jsp" />"
	);
	
	//========== 流程队列  ==========
	n3.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.queue" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_queue/index.jsp" />"
	);
	
	//========== 我刚处理过的流程 ==========
	n3.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.recentHandleFlow" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=getRecentHandle" />"
	);
	
	//========== 流程监控配置==========
	n1.AppendURLChild(
		"<bean:message key="sysLbpmMonitor.tree.processConfig" bundle="sys-lbpmmonitor" />",
		"<c:url value="/sys/lbpmmonitor/sys_lbpmmonitor_flow/LbpmAnalysisConfigAction.do?method=lbpmAnalysisConfigPage" />"
	); 
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>