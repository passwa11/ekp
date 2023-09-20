<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/viewList.css" rel="stylesheet">
<html>
<head>
<script type="text/javascript">
	var lang = {
		enterNodeName: '${lfn:message("sys-modeling-base:modeling.enter.node.name")}',
		edit: '${lfn:message("sys-modeling-base:modeling.page.edit")}',
		down: '${lfn:message("sys-modeling-base:modelingTransport.button.down")}',
		up: '${lfn:message("sys-modeling-base:modelingTransport.button.up")}',
		delete: '${lfn:message("sys-modeling-base:modeling.page.delete")}',
	}
Com_IncludeFile("treeview.js|jquery.js");
Com_IncludeFile("custom_treeview.js",Com_Parameter.ContextPath + "sys/modeling/base/profile/nav/v0/container/","js",true);
var dialogObject = parent.dialogObject;
</script>
<script type="text/javascript">
var LKSTree;
Tree_IncludeCSSFile();
function generateTree(){
	var type = "${JsParam.type}";
	var rptType = "${JsParam.rptType}";
	var fdAppName = $("[name='fdAppName']",parent.document).val();
	if ((type && /^other/.test(type)) ||
			(rptType && /^other/.test(rptType))) {
		LKSTree = new TreeView("LKSTree",'${lfn:message("sys-modeling-base:modeling.page.other")}');
	} else {
		LKSTree = new TreeView("LKSTree",fdAppName);
	}
	var treeRoot = new TreeNode("${lfn:message("sys-modeling-base:table.modelingCollectionView")}", null, null, null, null, null);
	LKSTree.treeRoot.AddChild(treeRoot);
	<c:if test="${JsParam.type eq 'rpt'}">
		var url = "modelingAppRptDataBean&type=!{value}&fdAppId=${JsParam.fdAppId}&fdModelName=com.landray.kmss.sys.modeling.base.model.ModelingAppModel";
		if (rptType === "otherRptview") {
			url += "&rptType=otherRptview"
		}
		treeRoot.AppendBeanData(url, null, null, null, null);
	</c:if>
	<c:if test="${JsParam.type ne 'rpt'}">
		var url = "modelingAppModelDataBean&parentNodeId=!{value}&fdAppId=${JsParam.fdAppId}";
		if (type === "otherListview") {
			url += "&type=otherListview"
		}
		treeRoot.AppendBeanData(url, null, null, null, null);
		//业务场景
        var nodeBusiness = new TreeNode("${lfn:message("sys-modeling-base:table.modelingResourcePanel")}", null, null, null, null, null);
        LKSTree.treeRoot.AddChild(nodeBusiness);
        var url2 = "modelingAppModelBusinessDataBean&parentNodeId=!{value}&bType=resPanel&fdAppId=${JsParam.fdAppId}";
        if (type === "otherListview") {
            url2 += "&type=otherListview"
        }
		nodeBusiness.AppendBeanData(url2, null, null, null, null);
		var nodeGantt = new TreeNode("${lfn:message("sys-modeling-base:table.modelingGantt")}", null, null, null, null, null);
		LKSTree.treeRoot.AddChild(nodeGantt);
		var url3 = "modelingAppModelBusinessDataBean&parentNodeId=!{value}&bType=gantt&fdAppId=${JsParam.fdAppId}";
		if (type === "otherListview") {
			url3 += "&type=otherListview"
		}
	    nodeGantt.AppendBeanData(url3, null, null, null, null);
	 </c:if>


	LKSTree.DOMElement = document.getElementById("treeDiv");
	LKSTree.ClickNode = Dialog_ClickNode;
	LKSTree.DrawNode = TreeFunc_DrawNode;
	LKSTree.DrawNodeIndentHTML = TreeFunc_DrawNodeIndentHTML;
	LKSTree.DrawNodeOuterHTML = TreeFunc_DrawNodeOuterHTML;
	//LKSTree.DrawNodeInnerHTML = TreeFunc_DrawNodeInnerHTML;
	LKSTree.DrawNodeInnerHTML = Modeling_Nav_DrawNodeInnerHTML_LEFT;
	LKSTree.DrawNodeIndentHTML = Modeling_Nav_DrawNodeIndentHTML_LEFT;
	LKSTree.ExpandNode = TreeFunc_ExpandNode;
	LKSTree.GetCheckedNode = TreeFunc_GetCheckedNode;
	LKSTree.SelectNode = TreeFunc_SelectNode;
	LKSTree.SetCurrentNode = TreeFunc_SetCurrentNode;
	LKSTree.SetNodeChecked = TreeFunc_SetNodeChecked;
	LKSTree.SetTreeRoot = TreeFunc_SetTreeRoot;
	LKSTree.Show = TreeFunc_Show;
	LKSTree.isMultSel = true;
	LKSTree.isShowCheckBox = true;
	LKSTree.isAutoSelectChildren = true;
	//LKSTree.OnNodeCheckedQueryChange = Modeling_OnNodeCheckedQueryChange;
	LKSTree.Show();
}

function Modeling_findChildNodesByParentNode(node){
	var childNodes = [];
	for(var child=node.firstChild; child!=null; child=child.nextSibling){
		childNodes.push(child);
	}
	return childNodes;
}

function Modeling_OnNodeCheckedQueryChange(node,optType){
	//获取node的所有子节点
	var childNodes = Modeling_findChildNodesByParentNode(node);
	for(var i=0; i<childNodes.length; i++){
		var nodeId = childNodes[i].id;
		$("#CHK_" + nodeId)[0].click();
	}
}

function Dialog_ClickNode(node){
	if(typeof(node)=="number")
		node = Tree_GetNodeByID(this.treeRoot, node);
	if(node == null)
		return;
	if(node.parameter==null){
		this.SetCurrentNode(node);
		this.ExpandNode(node);
		return;
	}
	if(node.parameter.IsKMSSData!=true)
		node.parameter = new KMSSData().AddBeanData(Com_ReplaceParameter(node.parameter, node));
	try{
		//parent.optFrame.setOptData(node.parameter);
		// 设置隐藏域，当前ID
		$("#currentId").val(node.value);
	}catch(e){
		return;
	}
	this.SetCurrentNode(node);
}

function getCategoryId() {
	var val = $("#currentId").val();
	return val;
}
</script>
<style type="text/css">
	body{
		overflow-y:auto!important;
	}
</style>
</head>

<body style="margin:0px;">
	<div id=treeDiv class="treediv lui_modeling_nav_tree_left" style="overflow: hidden;height: auto"></div>
	<input type="hidden" id="currentId" value=""/>
	<script>generateTree();</script>
</body>
</html>