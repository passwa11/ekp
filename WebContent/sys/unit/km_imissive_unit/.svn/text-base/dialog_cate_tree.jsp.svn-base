<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
var dialogObject = top.dialogObject;
Com_Parameter.XMLDebug = dialogObject.XMLDebug;
var Data_XMLCatche = dialogObject.XMLCatche;
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

	window.top.sysUnitSelectType = 0;
	if(typeof(node)=="number")
		node = Tree_GetNodeByID(this.treeRoot, node);
	if(node == null)
		return;
	 //判断点击的节点是否是公文交换单位
	 var addOuterUnitElem=top.optFrame.document.getElementById('addOuterUnit');
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
		top.optFrame.setOptData(dataaa);
		if(_dataBean!=""){
			//截取数据bean的参数部分
			var param = Com_ReplaceParameter(_dataBean, node)
			if(param.indexOf("&")-1){
				//设置父窗口的参数
			  top.optFrame.treeParamVal = param.substring(param.indexOf("&"),param.length);
			}
		}
	}catch(e){
		return;
	}
	this.SetCurrentNode(node);
}
<%@ include file="/resource/jsp/tree_down.jsp" %>

<%@ include file="/resource/jsp/watermarkPcDialog.jsp" %>