<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
var personImport, deptImport, orgImport, defaultNode;
function generateTree() {
	// 安全保密员日志
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message bundle="sys-log" key="sysLogUserOper.type.secrecy"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:auth requestURL="/sys/log/sys_log_user_oper/sysLogUserOper.do?method=listSecadmin" requestMethod="GET">
        //========== 登录日志 ==========
       	n1.AppendURLChild(
           "<bean:message bundle="sys-log" key="table.sysLogLogin"/>",
            "<c:url value="/sys/log/sys_log_user_oper/index.jsp?method=listSecadmin&eventType=login"/>"
        );
        //========== 登出日志 ==========
       	n1.AppendURLChild(
           "<bean:message bundle="sys-log" key="table.sysLogLogout"/>",
            "<c:url value="/sys/log/sys_log_user_oper/index.jsp?method=listSecadmin&eventType=logout"/>"
        );
        //========== 操作日志 ==========
      	n1.AppendURLChild(
           "<bean:message bundle="sys-log" key="table.sysLogUserOper"/>",
            "<c:url value="/sys/log/sys_log_user_oper/index.jsp?method=listSecadmin&eventType=oper"/>"
        );
    </kmss:auth>
	<kmss:auth requestURL="/sys/log/sys_log_user_oper/sysLogUserOper.do?method=listOrg" requestMethod="GET">
    //========== 组织架构操作日志 ==========
    n1.AppendURLChild(
        "<bean:message bundle="sys-log" key="sysLogOper.method.organization"/>",
        "<c:url value="/sys/log/sys_log_user_oper/index.jsp?method=listOrg"/>"
    );
    </kmss:auth>
    <kmss:auth requestURL="/sys/log/sys_log_user_oper/sysLogUserOper.do?method=listAuth" requestMethod="GET">
    //========== 权限变更日志 ==========
    n1.AppendURLChild(
        "<bean:message bundle="sys-log" key="sysLogOper.method.sysAuthRole"/>",
        "<c:url value="/sys/log/sys_log_user_oper/index.jsp?method=listAuth"/>"
    );
    </kmss:auth>
	
	LKSTree.Show();
}

	</template:replace>
</template:include>