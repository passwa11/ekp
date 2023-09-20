<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	<kmss:authShow roles="ROLE_LBPM_WORKFLOW_AUTHORITY_MANAGER">
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorize.moduleName"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, defaultNode;
	n1 = LKSTree.treeRoot;
	//========== 参数配置 ==========
	defaultNode = n2 = n1.AppendURLChild(
		"<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorize.tree.part1"/>",
		"<c:url value="/sys/lbpmext/authorize/lbpm_authorize/index.jsp"/>"
	);
	</kmss:authShow>	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
<%@ include file="/resource/jsp/tree_down.jsp" %>