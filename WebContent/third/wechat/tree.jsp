<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.third.wechat" bundle="third-wechat"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;

	
	<kmss:authShow roles="ROLE_THIRDWECHAT_ADMIN">
	<%-- 微信组件配置 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="module.third.wechat.tree.setting" bundle="third-wechat"/>",
		"<c:url value="/third/wechat/wechatMainConfig.do?method=edit" />"
	);
	n3 = n1.AppendURLChild(
		"<bean:message key="module.third.wechat.tree.setting.notify" bundle="third-wechat"/>",
		"<c:url value="/third/wechat/wechatNotify.do?method=toNotify" />"
	);
	</kmss:authShow>
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
<%@ include file="/resource/jsp/tree_down.jsp" %>