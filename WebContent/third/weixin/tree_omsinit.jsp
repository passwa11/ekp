<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="thirdWxOmsInit.deploy.guide" bundle="third-weixin"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;
	
	<%-- 域名校验 --%>
	<%-- n2 = n1.AppendURLChild(
		"<bean:message key="domain.check" bundle="third-weixin"/>",
		"<c:url value="/third/weixin/upload.jsp" />"
	);  --%>
	
	<%-- 组织架构检查 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="third.wx.oms.check" bundle="third-weixin"/>",
		"<c:url value="/third/wx/weixinSynchroOrgCheck.do?method=check" />"
	); 
		
	<%-- 部门映射关系表 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="other.dept.relation" bundle="third-weixin" />",
		"<c:url value="/third/weixin/spi/wx_oms_relation/wxOmsRelation.do?method=list&type=dept" />"
	);
	<%-- 微信组织初始化 --%>
	n3 = n2.AppendURLChild(
		"<bean:message key="other.init" bundle="third-weixin" />",
		"<c:url value="/third/weixin/third_wx_oms_init/thirdWxOmsInit.do?method=list&fdIsOrg=1" />"
	);
	<%-- 人员映射关系表 --%>
	n4 = n1.AppendURLChild(
		"<bean:message key="other.person.relation" bundle="third-weixin" />",
		"<c:url value="/third/weixin/spi/wx_oms_relation/wxOmsRelation.do?method=list&type=person" />"
	);
	<%-- 微信人员初始化 --%>
	n5 = n4.AppendURLChild(
		"<bean:message key="other.init" bundle="third-weixin" />",
		"<c:url value="/third/weixin/third_wx_oms_init/thirdWxOmsInit.do?method=list&fdIsOrg=0" />"
	);
	
	LKSTree.EnableRightMenu();
	LKSTree.ExpandNode(n2);
	LKSTree.ExpandNode(n4);
	LKSTree.Show();
}
	</template:replace>
</template:include>