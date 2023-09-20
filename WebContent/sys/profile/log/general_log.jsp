<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
var personImport, deptImport, orgImport, defaultNode;
function generateTree() {
	// 普通用户日志
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message bundle="sys-log" key="sysLogUserOper.type.general"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:auth requestURL="/sys/log/sys_log_user_oper/sysLogUserOper.do?method=listUser" requestMethod="GET">
        //========== 登录日志 ==========
       	n1.AppendURLChild(
           "<bean:message bundle="sys-log" key="table.sysLogLogin"/>",
            "<c:url value="/sys/log/sys_log_user_oper/index.jsp?method=listUser&eventType=login"/>"
        );
        //========== 登出日志 ==========
       	n1.AppendURLChild(
           "<bean:message bundle="sys-log" key="table.sysLogLogout"/>",
            "<c:url value="/sys/log/sys_log_user_oper/index.jsp?method=listUser&eventType=logout"/>"
        );
        //========== 操作日志 ==========
       	n1.AppendURLChild(
           "<bean:message bundle="sys-log" key="table.sysLogUserOper"/>",
            "<c:url value="/sys/log/sys_log_user_oper/index.jsp?method=listUser&eventType=oper"/>"
        );
    </kmss:auth>
	
	LKSTree.Show();
}
	</template:replace>
</template:include>