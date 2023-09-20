<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">

	
	function generateTree() {
		var para = new Array;
		var href = location.href;
		para[0] = Com_GetUrlParameter(href, "url");
		LKSTree = new TreeView("LKSTree", "<bean:message bundle="sys-category" key="sysCategoryMain.modelName"/>", document.getElementById("treeDiv"));
		var n1 = LKSTree.treeRoot;
		n1.AppendSimpleCategoryData(Com_GetUrlParameter(href, "modelName"), para);
		LKSTree.Show();
		
		// 获取回调URL，如果有设置此数据，则会在右则打开回调页面
		var callback_url = Com_GetUrlParameter(location.href, "callback_url");
		if(callback_url)
			Com_OpenWindow(callback_url);
	}

 </template:replace>
</template:include>