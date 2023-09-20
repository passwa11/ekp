<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sysSmsMain.service" bundle="sys-sms"/>",
		document.getElementById("treeDiv")
	);
	var n1,n2;
	n1 = LKSTree.treeRoot;
	<kmss:authShow roles="ROLE_SYSSMS_BASE_SET">
	//模块设置
	n2 = n1.AppendURLChild("<bean:message key="tree.sysSmsConfig" bundle="sys-sms"/>", "<c:url value="/sys/sms/sys_sms_config/sysSmsConfig.do?method=view"/>");
	</kmss:authShow>
	
	LKSTree.EnableRightMenu();	
	LKSTree.Show();
}
<%@ include file="/resource/jsp/tree_down.jsp" %>
