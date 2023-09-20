<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.landray.kmss.sys.mobile.util.MobileConfigPlugin" %>
<%@page import="com.landray.kmss.third.pda.util.LicenseUtil"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="third.wx.menu.tree" bundle="third-weixin"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;

	
	<kmss:authShow roles="ROLE_THIRDWXWORK_ADMIN">
			<%
			String wxEnabled= com.landray.kmss.util.ResourceUtil.getKmssConfigString("kmss.third.wx.enabled");
			if("true".equals(wxEnabled)){
			%>
	
	n2 = n1.AppendURLChild(
		"<bean:message key="third.wx.menu.title" bundle="third-weixin"/>",
		"<c:url value="/third/wxwork/menu/wxworkMenuDefine.do" />?method=list" 
	);
		<%}%>

	</kmss:authShow>
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}
<%@ include file="/resource/jsp/tree_down.jsp" %>
