<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="third.ding.buss.data.title" bundle="third-ding"/>",
		document.getElementById("treeDiv")
	);

	var n1, n2, n3, n4, n5, n6, n7, n8, n9,n41,n51,n61,n91,n11,n111,n112,n113,n114,n115,nCspace,nCspaceUpload,nCalendar,nCalendarLog,nCspaceDownlown;

	n1 = LKSTree.treeRoot;
	
	<%-- 考勤 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="third.ding.buss.data.schdule" bundle="third-ding"/>",
		""
	);
	<%-- 钉盘--%>
	nCspace = n1.AppendURLChild(
		"钉盘文件",
		""
	);
	
	<%-- 上传日志 --%>	
	nCspaceUpload = nCspace.AppendURLChild(
		"上传日志",
		"<c:url value="/third/ding/third_ding_cspace/index.jsp?fdType=0"/>"
	);
	<%-- 下载日志 --%>
	nCspaceDownlown = nCspace.AppendURLChild(
	"下载日志",
	"<c:url value="/third/ding/third_ding_cspace/index.jsp?fdType=1"/>"
	);
	
	<%-- 流程管理" --%>
	n11 = n1.AppendURLChild(
		"<bean:message key="third.ding.buss.lbpm" bundle="third-ding"/>",
		""
	);
	
	n111 = n11.AppendURLChild(
		"<bean:message key="third.ding.buss.syn.template" bundle="third-ding"/>",
		"<c:url value="/third/ding/third_ding_dtemplate_xform/index.jsp" />"
	);
	n112 = n11.AppendURLChild(
		"<bean:message key="third.ding.buss.syn.instance" bundle="third-ding"/>",
		"<c:url value="/third/ding/third_ding_dinstance_xform/index.jsp" />"
	);
	n113 = n11.AppendURLChild(
		"<bean:message key="third.ding.buss.syn.task" bundle="third-ding"/>",
		"<c:url value="/third/ding/third_ding_dtask_xform/index.jsp" />"
	);
	n114 = n11.AppendURLChild(
		"<bean:message key="third.ding.buss.syn.task.log" bundle="third-ding"/>",
		"<c:url value="/third/ding/third_ding_xform_notify_log/index.jsp" />"
	);
	n115 = n11.AppendURLChild(
		"<bean:message key="third.ding.buss.syn.category.log" bundle="third-ding"/>",
		"<c:url value="/third/ding/third_ding_category_log/index.jsp" />"
	);
	
	<%-- 日程 
	n3 = n1.AppendURLChild(
		"<bean:message key="third.ding.buss.data.time" bundle="third-ding"/>",
		""
	);
	--%>
	
	<%-- 请假 --%>	
	n4 = n2.AppendURLChild(
		"<bean:message key="third.ding.buss.data.leave" bundle="third-ding" />",
		"<c:url value="/third/ding/third_ding_leavelog/thirdDingLeavelog.do?method=list" />"
	);
	
	n41 = n4.AppendURLChild(
		"<bean:message key="thirdDingLeavelog.cancel" bundle="third-ding" />",
		"<c:url value="/third/ding/third_ding_leavelog/thirdDingLeavelog.do?method=cancelList&cancelType=leave" />"
	);
	
	<%-- 外出 --%>	
	n5 = n2.AppendURLChild(
		"<bean:message key="third.ding.buss.data.business" bundle="third-ding" />",
		"<c:url value="/third/ding/third_ding_leavelog/thirdDingLeavelog.do?method=bussList" />"
	);
	n51 = n5.AppendURLChild(
		"<bean:message key="thirdDingLeavelog.cancel.business" bundle="third-ding" />",
		"<c:url value="/third/ding/third_ding_leavelog/thirdDingLeavelog.do?method=cancelList&cancelType=business" />"
	);
	
	<%-- 出差 --%>	
	n6 = n2.AppendURLChild(
		"<bean:message key="thirdDingLeavelog.trip" bundle="third-ding" />",
		"<c:url value="/third/ding/third_ding_leavelog/thirdDingLeavelog.do?method=tripList" />"
	);
	n61 = n6.AppendURLChild(
		"<bean:message key="thirdDingLeavelog.cancel.trip" bundle="third-ding" />",
		"<c:url value="/third/ding/third_ding_leavelog/thirdDingLeavelog.do?method=cancelList&cancelType=trip" />"
	);
	
	<%-- 补卡 --%>	
	n7 = n2.AppendURLChild(
		"<bean:message key="third.ding.buss.data.check" bundle="third-ding" />",
		"<c:url value="/third/ding/third_ding_leavelog/thirdDingLeavelog.do?method=checkList" />"
	);
	
	<%-- 换班 --%>	
	n8 = n2.AppendURLChild(
		"<bean:message key="third.ding.buss.data.switch" bundle="third-ding" />",
		"<c:url value="/third/ding/third_ding_leavelog/thirdDingLeavelog.do?method=switchList" />"
	);
	
	<%-- 加班 --%>	
	n9 = n2.AppendURLChild(
		"<bean:message key="third.ding.buss.data.overtime" bundle="third-ding" />",
		"<c:url value="/third/ding/third_ding_leavelog/thirdDingLeavelog.do?method=overtimeList" />"
	);
	n91 = n9.AppendURLChild(
		"<bean:message key="thirdDingLeavelog.cancel.overtime" bundle="third-ding" />",
		"<c:url value="/third/ding/third_ding_leavelog/thirdDingLeavelog.do?method=cancelList&cancelType=overtime" />"
	);
	<%-- 日程 --%>
	nCalendar = n1.AppendURLChild(
	"日程",
	""
	);
	nCalendarLog = nCalendar.AppendURLChild(
	"<bean:message key="table.thirdDingCalendarLog" bundle="third-ding"/>",
	"<c:url value="/third/ding/third_ding_calendar_log/index.jsp"/>"
	);
	
	<%-- 销假	
	n10 = n2.AppendURLChild(
		"<bean:message key="third.ding.buss.data.cancel" bundle="third-ding" />",
		"<c:url value="/third/ding/third_ding_leavelog/thirdDingLeavelog.do?method=cancelList" />"
	);
	 --%>
	
	<%-- 日程 
	n9 = n3.AppendURLChild(
		"<bean:message key="third.ding.buss.data.timesync" bundle="third-ding" />",
		"<c:url value="" />"
	);
	--%>	
	
	LKSTree.EnableRightMenu();
	LKSTree.ExpandNode(n2);
	LKSTree.ExpandNode(n3);
	LKSTree.Show();
}
	</template:replace>
</template:include>