<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.tic.core.provider" bundle="tic-core-provider"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;
	
	<%-- 导入初始化数据 --%>
	n2_init = n1.AppendURLChild(
		"<bean:message key="ticCoreIface.importInit" bundle="tic-core-provider" />",
		"<c:url value="/tic/core/provider/tic_core_iface/ticCoreIface.do?method=importInit" />"
	);
	<%-- provider接口定义 --%>
	n2_iface = n1.AppendURLChild(
		"<bean:message key="table.ticCoreIface" bundle="tic-core-provider" />",
		"<c:url value="/tic/core/provider/tic_core_iface/ticCoreIface.do?method=list" />"
	);
	<%-- provider接口实现 --%>
	n2_impl = n1.AppendURLChild(
		"<bean:message key="table.ticCoreIfaceImpl" bundle="tic-core-provider" />",
		"<c:url value="/tic/core/provider/tic_core_iface_impl/ticCoreIfaceImpl.do?method=list" />"
	);
	<%-- 节点信息
	n2 = n1.AppendURLChild(
		"<bean:message key="table.ticCoreNode" bundle="tic-core-provider" />",
		"<c:url value="/tic/core/provider/tic_core_node/ticCoreNode.do?method=list" />"
	); --%>
	<%-- 标签信息
	n2 = n1.AppendURLChild(
		"<bean:message key="table.ticCoreTag" bundle="tic-core-provider" />",
		"<c:url value="/tic/core/provider/tic_core_tag/ticCoreTag.do?method=list" />"
	); --%>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
<%@ include file="/resource/jsp/tree_down.jsp" %>