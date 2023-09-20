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
	var n1, n2, n3, n4, n5,n6,n6_1;
	n1 = LKSTree.treeRoot;
	
	<%-- 域名校验 --%>
	<%-- n2 = n1.AppendURLChild(
		"<bean:message key="domain.check" bundle="third-weixin"/>",
		"<c:url value="/third/weixin/upload.jsp" />"
	);  --%>
	
	<%-- 组织架构检查 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="third.wx.oms.check" bundle="third-weixin"/>",
		"<c:url value="/third/wxwork/weixinSynchroOrgCheck.do?method=check" />"
	); 
		
	<%-- 部门映射关系表 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="other.dept.relation" bundle="third-weixin" />",
		"<c:url value="/third/weixin/work/spi/wxwork_oms_relation/wxworkOmsRelation.do?method=list&type=dept" />"
	);
	<%-- 微信组织初始化 --%>
	n3 = n2.AppendURLChild(
		"<bean:message key="other.init" bundle="third-weixin" />",
		"<c:url value="/third/weixin/work/third_wxwork_oms_init/thirdWxworkOmsInit.do?method=list&fdIsOrg=1" />"
	);
	<%-- 人员映射关系表 --%>
	n4 = n1.AppendURLChild(
		"<bean:message key="other.person.relation" bundle="third-weixin" />",
		"<c:url value="/third/weixin/work/spi/wxwork_oms_relation/wxworkOmsRelation.do?method=list&type=person" />"
	);
	<%-- 微信人员初始化 --%>
	n5 = n4.AppendURLChild(
		"<bean:message key="other.init" bundle="third-weixin" />",
		"<c:url value="/third/weixin/work/third_wxwork_oms_init/thirdWxworkOmsInit.do?method=list&fdIsOrg=0" />"
	);

	<%-- 人员映射关系表 --%>
	n4 = n1.AppendURLChild(
		"<bean:message key="table.thirdWeixinContactMapp" bundle="third-weixin" />",
		"<c:url value="/third/weixin/third_weixin_contact_mapp/list.jsp" />"
	);
	n6 = n1.AppendURLChild(
		"<bean:message key="config.syn.log" bundle="third-weixin-work" />",
		""
	);
	n6_1 = n6.AppendURLChild(
		"<bean:message key="config.callback.log" bundle="third-weixin-work" />",
		"<c:url value="/third/weixin/work/third_weixin_work_callback/list.jsp" />"
	);

	n1.AppendURLChild(
		"<bean:message key="table.thirdWeixinWorkGroup" bundle="third-weixin-work" />",
		"<c:url value="/third/weixin/work/third_weixin_work_group/list.jsp" />"
		);

	var n8 = n1.AppendChild("上下游组织映射");
		n8.AppendURLChild(
		"部门映射",
		"<c:url value="/third/weixin/work/third_weixin_cg_dept_mapp/list.jsp" />"
		);

		n8.AppendURLChild(
		"人员映射",
		"<c:url value="/third/weixin/work/third_weixin_cg_user_mapp/list.jsp" />"
		);
	LKSTree.EnableRightMenu();
	LKSTree.ExpandNode(n2);
	LKSTree.ExpandNode(n4);
	LKSTree.Show();
}
	</template:replace>
</template:include>