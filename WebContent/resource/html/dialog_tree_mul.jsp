<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()+"/"+SysUiPluginUtil.getThemesFileByName(request,"common")%>"></link>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()+"/"+SysUiPluginUtil.getThemesFileByName(request,"module")%>"></link>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
</head>
<script type="text/javascript">
var Com_Parameter = {
	ContextPath:parent.Com_Parameter.ContextPath,
	ResPath:parent.Com_Parameter.ResPath,
	Style:parent.Com_Parameter.Style,
	StylePath:parent.Com_Parameter.StylePath,
	JsFileList:new Array,
	DialogLang:parent.Com_Parameter.DialogLang,
	Lang:parent.Com_Parameter.Lang
};
var dialogObject = parent.dialogObject;
Com_Parameter.XMLDebug = dialogObject.XMLDebug;
var Data_XMLCatche = dialogObject.XMLCatche;
</script>
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
<script type="text/javascript">
var progressDialog = null;
var Selected_Data = new KMSSData(dialogObject.valueData).UniqueTrimByKey("id");
var LKSTree;
Tree_IncludeCSSFile();
function generateTree(){
	LKSTree = dialogObject.tree;
	LKSTree.DOMElement = document.getElementById("treeDiv");
	LKSTree.ClickNode = TreeFunc_ClickNode;
	LKSTree.DrawNode = TreeFunc_DrawNode;
	LKSTree.DrawNodeIndentHTML = TreeFunc_DrawNodeIndentHTML;
	LKSTree.DrawNodeOuterHTML = TreeFunc_DrawNodeOuterHTML;
	LKSTree.DrawNodeInnerHTML = TreeFunc_DrawNodeInnerHTML;
	LKSTree.ExpandNode = TreeFunc_ExpandNode;
	LKSTree.GetCheckedNode = TreeFunc_GetCheckedNode;
	LKSTree.SelectNode = TreeFunc_SelectNode;
	LKSTree.SetCurrentNode = TreeFunc_SetCurrentNode;
	LKSTree.SetNodeChecked = TreeFunc_SetNodeChecked;
	LKSTree.SetTreeRoot = TreeFunc_SetTreeRoot;
	LKSTree.OnNodeQueryClick = Dialog_OnNodeQueryClick;
	LKSTree.OnNodeCheckedPostChange = Dialog_OnNodeCheckedPostChange;
	LKSTree.Show = TreeFunc_Show;
	LKSTree.isShowCheckBox = true;
	LKSTree.isMultSel = true;
	modifyNodeInfo(LKSTree.treeRoot, true);
	LKSTree.Show();
	if(progressDialog){
		progressDialog.hide();
	}
}

function modifyNodeInfo(root, refreshRoot){
	if(refreshRoot){
		if(root.value!=null && Selected_Data.IndexOf("id", root.value)>-1)
			root.isChecked = true;
		if(root.FetchChildrenNode!=null){
			if(root.FetchChildrenNode==Dialog_FetchChildrenNode){
				root.Dialog_FetchChildrenNode = root.parent.Dialog_FetchChildrenNode;
			}else{
				root.Dialog_FetchChildrenNode = root.FetchChildrenNode;
				root.FetchChildrenNode = Dialog_FetchChildrenNode;
			}
		}
	}
	for(var node = root.firstChild; node!=null; node=node.nextSibling)
		modifyNodeInfo(node, true);
}
function Dialog_FetchChildrenNode(){
	this.Dialog_FetchChildrenNode();
	modifyNodeInfo(this, false);
}
function Com_DialogReturnValue(){
	var rtnVal = Selected_Data.GetHashMapArray();
	if(rtnVal.length==0 && dialogObject.notNull)
		alert(Com_Parameter.DialogLang.requiredSelect);
	else
		parent.Com_DialogReturn(rtnVal);
}
function Dialog_OnNodeQueryClick(node){
	node.action = null;
	node.parameter = null;
} 
function Dialog_OnNodeCheckedPostChange(node){
	var i = Selected_Data.IndexOf("id", node.value);
	if(node.isChecked){
		if(i==-1){
			Selected_Data.AddHashMap({id:node.value, name:node.text});
			refreshSelectedList();
		}
	}else{
		if(i>-1){
			Selected_Data.Delete(i);
			refreshSelectedList();
		}
	}
}
function refreshSelectedList(){
	try{
		parent.optFrame.refreshSelectedList();
	}catch(e){
	}
}
function onSelectedDataDelete(){
	var nodes = LKSTree.GetCheckedNode();
	for(var i=0; i<nodes.length; i++){
		if(Selected_Data.IndexOf("id", nodes[i].value)==-1){
			LKSTree.SetNodeChecked(nodes[i], false);
		}
	}
}
</script>
</head>
<body>
<div id=treeDiv class="treediv"></div>
<script>
  // Edge浏览器下加载treeview树形结构性能比较差，暂通过加提醒层的方式来避免加载的过程中等待空白页时间太长
  if(window.navigator.userAgent.indexOf("Edge")>-1){
	    seajs.use("lui/dialog" , function(dialog) {
			progressDialog = dialog.progress(false);
			setTimeout(function(){ 
				// 请稍等！数据正在加载...
				progressDialog.setProgressText('<bean:message key="page.loadingHint"/>');
				setTimeout(function(){
				   generateTree(); 
				}, 50);
		    }, 50);
		});	  
  }else{
	  generateTree();
  }

</script>
<%@ include file="/resource/jsp/watermarkPcDialog.jsp" %>
</body>
</html>