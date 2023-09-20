<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	//设置为以钉钉浏览器模式打开
	request.setAttribute("dingPcForce", "true");
%>
<template:include file="/sys/profile/resource/template/tree.jsp" bodyClass="luiReviewTemplateTreeBody">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/km/review/km_review_ui/dingSuit/css/tree.css?s_cache=${LUI_Cache }"/>
	</template:replace>
    <template:replace name="content">

	function generateTree() {
		var para = new Array;
		var href = location.href;
		para[0] = Com_GetUrlParameter(href, "url");
		para[1] = Com_GetUrlParameter(href, "target");
		para[2] = Com_GetUrlParameter(href, "winStyle");
		LKSTree = new TreeView("LKSTree", "<bean:message bundle="sys-category" key="sysCategoryMain.modelName"/>", document.getElementById("treeDiv"));
		var n1 = LKSTree.treeRoot;
		n1.AppendCategoryData(Com_GetUrlParameter(href, "modelName"), para, 0,0,Com_GetUrlParameter(href, "showTemplate"),null, Com_GetUrlParameter(href, "startWith"));
		LKSTree.Show();

		// 获取回调URL，如果有设置此数据，则会在右则打开回调页面
		var callback_url = Com_GetUrlParameter(location.href, "callback_url");
		if(callback_url)
			Com_OpenWindow(callback_url);
	}

 </template:replace>
</template:include>