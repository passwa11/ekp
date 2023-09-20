<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()+"/"+SysUiPluginUtil.getThemesFileByName(request,"common")%>"></link>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()+"/"+SysUiPluginUtil.getThemesFileByName(request,"module")%>"></link>
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
<script type="text/javascript" src="../../../resource/js/common.js"></script>
<script type="text/javascript">
Com_IncludeFile("treeview.js",Com_Parameter.ContextPath	+ "resource/js/","js",true);
Com_IncludeFile("data.js",Com_Parameter.ContextPath	+ "resource/js/","js",true);
Com_IncludeFile("xml.js",Com_Parameter.ContextPath	+ "resource/js/","js",true);
</script>
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
//获取数据的bean
var _dataBean;
function Dialog_ClickNode(node){
	if(typeof(node)=="number")
		node = Tree_GetNodeByID(this.treeRoot, node);
	if(node == null)
		return;
	 //判断点击的节点是否是公文交换单位
	 var addOuterUnitElem=parent.optFrame.document.getElementById('addOuterUnit');
	   if(addOuterUnitElem!=null ){
			 if(node.value =='outerUnit'){
				addOuterUnitElem.style.display = "none";
			 }else{
				 addOuterUnitElem.style.display = "block";
			 }
	   }
	 
	if(node.parameter==null){
		this.ExpandNode(node);
		return;
	}
	_dataBean = node.parameter;
	var kmssData = new KMSSData();
	kmssData.UseCache = false;
	dataaa = kmssData.AddBeanData(Com_ReplaceParameter(node.parameter, node));
	try{
		parent.optFrame.setOptData(dataaa);
		if(_dataBean!=""){
			//截取数据bean的参数部分
			var param = Com_ReplaceParameter(_dataBean, node)
			if(param.indexOf("&")-1){
				//设置父窗口的参数
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