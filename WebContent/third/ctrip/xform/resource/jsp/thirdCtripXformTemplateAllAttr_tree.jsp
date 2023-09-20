<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
//双击关闭窗口
function closeWindow(){
	window.close();
}

function generateTree() {
	LKSTree = new TreeView("LKSTree", "${lfn:message('third-ctrip:bookTicket.chooseRelationControl')}", document.getElementById("treeDiv"));
	LKSTree.DblClickNode = closeWindow;
	var n1, n2;
	n1 = LKSTree.treeRoot;
	n2 = n1.AppendChild(
		"<kmss:message bundle="sys-xform-base" key="Designer_Lang.controlCalculation_edit_var"/>"
	);
	n2.FetchChildrenNode = getVars;
	n2.isExpanded = true;
	n1.isExpanded = true;
	LKSTree.Show();
}

function getVars(){
	var varInfo = top.dialogObject.varInfo.obj;
	var showType = top.dialogObject.varInfo.type;
	for(var i=0; i < varInfo.length; i++){
		var dataType = varInfo[i].controlType;
		if(isType(showType,dataType)){
			if(varInfo[i].label && varInfo[i].label != ''){
				var textArr = varInfo[i].label.split(".");
				var pNode = this;
				var node;
				for(var j=0; j < textArr.length; j++){
					node = Tree_GetChildByText(pNode, textArr[j]);
					if(node == null){
						node = pNode.AppendChild(textArr[j]);
					}
					pNode = node;
				}
				node.action = selectNode;
				node.value = varInfo[i].label;
				node.attrId = varInfo[i].name;
			}	
		}	
	}
}

function isType(showType,dataType){
	var flag = false;
	if(showType.indexOf("|") > -1){
		var types = showType.split("|");
		for(var i = 0;i < types.length;i++){
			//当showType == ''的时候表示地点框，目前没有地点控件，所有的控件都可以应用于地点，暂不做过滤
			if(dataType && dataType == types[i]){
				flag = true;
				break;
			}
		}
	}else{
		if(dataType && dataType == showType || showType == ''){
			flag = true;
		}
	}
	return flag;
}

function selectNode(){
	var data = {};
	data.value = this.attrId;
	data.text = this.value;
	top.dialogObject.rtnData = data;;
}

<%@ include file="/resource/jsp/tree_down.jsp" %>