<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sysSmsMain.sms" bundle="sys-sms"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, n6, n7, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:authShow roles="ROLE_SYSSMS_DEFAULT">
	//========== 短信查询 ==========
	n2 = n1.AppendURLChild(
        "<bean:message key="sysSmsMain.sms.query" bundle="sys-sms"/>"
	);
	</kmss:authShow>
	
	<kmss:auth requestURL="/sys/sms/sys_sms_config/sysSmsConfig.do?method=edit">
	//========== 参数配置 ==========
	n4 = n1.AppendURLChild(
		"<bean:message key="sysSmsMain.number.set" bundle="sys-sms"/>",
		"<c:url value="/sys/sms/sys_sms_config/sysSmsConfig.do?method=edit"/>"
	);
	</kmss:auth>
	
	
	//================================== 二级目录 =====================================//	
	
	<kmss:authShow roles="ROLE_SYSSMS_DEFAULT">
	<%-- 短信查询    二级目录 --%>
	<!-- 统一查询 -->
	defaultNode = n2.AppendURLChild(
		"<bean:message key="sysSmsMain.global.query" bundle="sys-sms"/>",
		"<c:url value="/sys/sms/sys_sms_main/index.jsp" />"
	);
	<!-- 搜索 -->
	n2.AppendURLChild(
		"<bean:message bundle="sys-sms" key="sysSmsMain.sms.search"/>",
		"<c:url value="/sys/sms/sys_sms_main/sysSmsMain.do?method=condition&fdModelName=com.landray.kmss.sys.notify.model.SysNotifyShortMessageSend&canClose=false" />"
	);
	</kmss:authShow>
	
	LKSTree.ExpandNode(n2);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>