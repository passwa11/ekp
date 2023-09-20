<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()+"/"+SysUiPluginUtil.getThemesFileByName(request,"common")%>"></link>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()+"/"+SysUiPluginUtil.getThemesFileByName(request,"module")%>"></link>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
var LKSTree;
Tree_IncludeCSSFile();
function generateTree(){
	LKSTree = dialogObject.tree;
	LKSTree.DOMElement = document.getElementById("treeDiv");
	LKSTree.ClickNode = Dialog_ClickNode;
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
	LKSTree.Show = TreeFunc_Show;
	LKSTree.Show();
}
//è·åæ°æ®çbean
var _dataBean;
function Dialog_ClickNode(node){
	if(typeof(node)=="number")
		node = Tree_GetNodeByID(this.treeRoot, node);
	if(node == null)
		return;
	if(node.parameter==null){
		this.ExpandNode(node);
		return;
	}
	if(node.parameter.IsKMSSData!=true){
		_dataBean = node.parameter;
		var _parameter = Com_ReplaceParameter(node.parameter, node);
		var canRequest = true;
        if(!_parameter){
            canRequest = false;
        }else if(Array.isArray(_parameter) && !_parameter[0]){
            canRequest = false;
        }
        if(canRequest){
            node.parameter = new KMSSData().AddBeanData(_parameter);
        }
	}
	try{
		parent.optFrame.setOptData(node.parameter);
		if(_dataBean!=""){
			//æªåæ°æ®beançåæ°é¨å
			var param = Com_ReplaceParameter(_dataBean, node)
			if(param.indexOf("&")-1){
				//è®¾ç½®ç¶çªå£çåæ°
			  parent.optFrame.treeParamVal = param.substring(param.indexOf("&"),param.length);
			}
		}
	}catch(e){
		return;
	}
	this.SetCurrentNode(node);
}
</script>
</head>
<body>
<div id=treeDiv class="treediv"></div>
<script>generateTree();</script>
</body>
</html>