<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
	<template:replace name="content">
	function generateTree()
		{
		LKSTree = new TreeView(
			"LKSTree",
			"<bean:message key="module.sys.praiseInfo.manager" bundle="sys-praise"/>",
			document.getElementById("treeDiv")
		);
		var n1, n2, n3, n4, n5;
		n1 = LKSTree.treeRoot;
		<kmss:authShow roles="ROLE_SYSPRAISEINFO_MAINTAINER">
		n2 = n1.AppendURLChild(
		"<bean:message key="main.baseSetting" bundle="sys-praise" />"
		);
		n3 = n2.AppendURLChild(
		"<bean:message key="sysPraiseInfoCategory.config" bundle="sys-praise" />",
		"<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.sys.praise.model.SysPraiseInfoCategory&actionUrl=/sys/praise/sys_praise_info_category/sysPraiseInfoCategory.do&formName=sysPraiseInfoCategoryForm&mainModelName=com.landray.kmss.sys.praise.model.SysPraiseInfo&docFkName=docCategory" />"
		);
		n3 = n2.AppendURLChild(
		"<bean:message key="module.openSetting" bundle="sys-praise" />",
		"<c:url value="/sys/praise/sys_praise_info_config_main/sysPraiseInfoConfigMain.do?method=add" />"
		);
		n3 = n2.AppendURLChild(
		"<bean:message key="main.replySetting" bundle="sys-praise" />",
		"<c:url value="/sys/praise/sys_praise_reply_config/sysPraiseReplyConfig.do?method=viewConfig" />"
		);
		
		n2 = n1.AppendURLChild(
		"<bean:message key="sysPraiseInfo.allInfo" bundle="sys-praise" />",
		"<c:url value="/sys/praise/sys_praise_info/sysPraiseInfo_mainList.jsp?&categoryId=" />"
		);
		 
		n2.authRole="optAll";
		n2.AppendSimpleCategoryDataWithAdmin("com.landray.kmss.sys.praise.model.SysPraiseInfoCategory",
		"<c:url value="/sys/praise/sys_praise_info/sysPraiseInfo_mainList.jsp?&categoryId=!{value}" />",
		"<c:url value="/sys/praise/sys_praise_info/sysPraiseInfo_mainList.jsp?&categoryId=!{value}" />");		
		
		
		n2 = n1.AppendURLChild(
		"<bean:message key="sysPraiseInfo.calculate" bundle="sys-praise" />",
		"<c:url value="/sys/praise/sys_praise_info/sysPraiseInfo_calculate.jsp" />"
		);
		</kmss:authShow>
		
		LKSTree.EnableRightMenu();
		LKSTree.Show();
		}
	</template:replace>
</template:include>