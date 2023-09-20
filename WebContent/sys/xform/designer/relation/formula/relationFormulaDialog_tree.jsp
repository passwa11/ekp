<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
//Com_Parameter.XMLDebug = dialogArguments.XMLDebug;
//var Data_XMLCatche = dialogArguments.XMLCatche;
//var dialogObject = opener.Com_Parameter.Dialog;
function generateTree() {
	LKSTree = new TreeView("LKSTree", '<bean:message bundle="sys-xform-base" key="Designer_Lang.relation_attrFieldVar"/>', document.getElementById("treeDiv"));
	var n1, n2;
	n1 = LKSTree.treeRoot;
	n1.FetchChildrenNode = getVars;
	n1.isExpanded = true;
	LKSTree.Show();
}

function getVars(){
	var varInfo = top.dialogObject.formulaParameter.varInfo;
	for(var i=0; i<varInfo.length; i++){
		var textArr = varInfo[i].label.split(".");
		var pNode = this;
		var node;
		for(var j=0; j<textArr.length; j++){
			node = Tree_GetChildByText(pNode, textArr[j]);
			if(node==null){
				node = pNode.AppendChild(textArr[j]);
			}
			pNode = node;
		}
		node.action = opFormula;
		node.value = "$"+varInfo[i].label+"$";
	}
}

function opFormula(){
	top.setCaret();
	var area = top.document.getElementById("expression");
	top.insertText(area, {value:this.value});
}
<%@ include file="/resource/jsp/tree_down.jsp" %>