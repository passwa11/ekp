<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
var dialogObject = top.dialogObject;
Com_Parameter.XMLDebug = dialogObject.XMLDebug;
var Data_XMLCatche = dialogObject.XMLCatche;
var cateUnitParam = dialogObject.tree.treeRoot.parameter;

function generateTree(){
	
	LKSTree = new TreeView("LKSTree", "<bean:message bundle="sys-unit" key="sysUnitDialog.group" />", document.getElementById("treeDiv"));
	LKSTree.ClickNode = Dialog_ClickNode;
	var n1 = LKSTree.treeRoot;
	var param = "sysUnitGroupTreeService"
	n1.isExpanded = true;
	
	var getUnitType = Com_GetUrlParameter(cateUnitParam, "type");
	if (!getUnitType) {
		getUnitType = "allUnit";
	}
	var groupTreeBean  = "sysUnitGroupTreeService", unitDataBean = "sysUnitListWithAuthByGroupService&parentId=!{value}&type=" + getUnitType;
	var showUnitGroup = Com_GetUrlParameter(cateUnitParam, "showUnitGroup");
	if(showUnitGroup != "" && showUnitGroup == "true"){
		groupTreeBean += "&showUnitGroup=true";
	}

n1.AppendBeanData(groupTreeBean, unitDataBean, null, false, null);
	
	LKSTree.ClickNode = Dialog_ClickNode;
	LKSTree.DrawNode = TreeFunc_DrawNode;
	LKSTree.ExpandNode = TreeFunc_ExpandNode;
	LKSTree.DrawNodeIndentHTML = TreeFunc_DrawNodeIndentHTML;
	LKSTree.DrawNodeOuterHTML = TreeFunc_DrawNodeOuterHTML;
	LKSTree.DrawNodeInnerHTML = TreeFunc_DrawNodeInnerHTML;
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

	window.top.sysUnitSelectType = 1;
	if(typeof(node)=="number")
		node = Tree_GetNodeByID(this.treeRoot, node);
	if(node == null) {
		return;
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