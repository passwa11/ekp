<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/search/dingSuit/resource/css/dingSearch.css"/>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/km/review/km_review_ui/dingSuit/css/tree.css?s_cache=${LUI_Cache }"/>
<script type="text/javascript">
Com_IncludeFile("treeview.js|jquery.js");
Com_IncludeFile("custom_treeview.js",Com_Parameter.ContextPath + "sys/modeling/base/manage/nav/container/","js",true);
</script>
<script type="text/javascript">
Tree_IncludeCSSFile();
var LKSTree;
var __interval = setInterval(__Interval,"50");

function __Interval(){
	if (!window.$dialog) {
		return;
	}
	generateTree();
	clearInterval(__interval);
}
	
function generateTree(){
	var params = $dialog.___params;
	var exceptVal = params.exceptValue;
	var title = params.title || "";
	LKSTree = new TreeView("LKSTree",title);
	LKSTree.isShowCheckBox = true;
	LKSTree.isMultSel = false;
	var treeRoot = LKSTree.treeRoot;
	if (params.authNodeValue && params.authNodeValue.length > 0) {
		LKSTree.authNodeValue = [].concat(params.authNodeValue);
	}
	var url = params.action;
	treeRoot.AppendBeanData(url, null, null, null, exceptVal);
	LKSTree.DOMElement = document.getElementById("treeDiv");
	LKSTree.Show();
}

function Com_DialogReturnValue(){
	var rtnVal = new Array;
	var selectNodes = LKSTree.GetCheckedNode();
	if(selectNodes!=null){
		selectNodes = new Array(selectNodes);
		for(var i = 0; i < selectNodes.length; i++){
			rtnVal[i] = new Array;
			rtnVal[i]["id"] = selectNodes[i].value;
			rtnVal[i]["name"] = (selectNodes[i].text!=null && selectNodes[i].text!="")?selectNodes[i].text:selectNodes[i].title;
			rtnVal[i]["nodeId"] = selectNodes[i].id;
			rtnVal[i]["node"] = selectNodes[i];
		}
	}
	if(rtnVal.length==0)
		alert('<bean:message key="dialog.requiredSelect"/>');
	else
		Com_DialogReturn(rtnVal);
}

function Com_DialogReturnEmpty(){
	Com_DialogCleanValue(new Array());
}

function Com_DialogReturn(value){
	$dialog.hide(value);
}

function Com_DialogCleanValue(value){
	$dialog.callback(value);
}
</script>
</head>
<body	>
	<div id="treeDiv" class="treediv lui_ding_search_cate_tree"></div>
	<div class="lui-ding-search-category-btnWrap">
		<ui:button text="${ lfn:message('button.ok') }" onclick="Com_DialogReturnValue();" />
		<ui:button text="${ lfn:message('button.cancel') }" styleClass="lui_toolbar_btn_gray" onclick="Com_DialogReturn();" />
		<ui:button text="${ lfn:message('dialog.selectNone') }" styleClass="lui_toolbar_btn_gray" onclick="Com_DialogReturnEmpty();" />
	</div>
</body>
</html>