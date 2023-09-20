<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
Com_Parameter.XMLDebug = top.dialogObject.XMLDebug;
var Data_XMLCatche = top.dialogObject.XMLCatche;
top.document.title = '<bean:message bundle="sys-formula" key="tree.root"/>';
function generateTree() {
	LKSTree = new TreeView("LKSTree", "<bean:message bundle="sys-formula" key="formula.title"/>", document.getElementById("treeDiv"));
	var n1, n2;
	n1 = LKSTree.treeRoot;
	n2 = n1.AppendChild(
		"<bean:message bundle="sys-formula" key="tree.var"/>"
	);
	n2.FetchChildrenNode = getVars;
	n1.isExpanded = true;
	LKSTree.Show();
}

function getVars(){
	var varInfo = top.dialogObject.formulaParameter.varInfo;
	debugger;
	for(var i=0; i<varInfo.length; i++){
		if(!varInfo[i].label){
			continue
		}
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
		node.name = varInfo[i].name;
		node.action = opFormula;
		node.title = varInfo[i].label + "(" + varInfo[i].type + ")"; //在提示信息里带上变量的类型
		node.value = "$"+varInfo[i].label+"$";
		node.summary = varInfo[i].summary;
	}
}

function getFunctions(){
	var funcInfo = top.dialogObject.formulaParameter.funcInfo;
	funcInfo.sort(function(o1, o2){return o1.text==o2.text?0:(o1.text>o2.text?1:-1);});
	for(var i=0; i<funcInfo.length; i++){
		var textArr = funcInfo[i].text.split(".");
		var pNode = this;
		var node;
		for(var j=0; j<textArr.length; j++){
			node = Tree_GetChildByText(pNode, textArr[j]);
			if(node==null){
				node = pNode.AppendChild(textArr[j]);
			}
			pNode = node;
		}
		node.action = opFuncFormula;
		node.value = funcInfo[i].value;
		node.title = funcInfo[i].title;
		node.summary = funcInfo[i].summary;
		for (var n=1; n<10; n++) {
			if(funcInfo[i]["example"+n]){
				node["example" + n] = funcInfo[i]["example"+n];
				node["exampleFormula" + n] = funcInfo[i]["exampleFormula"+n];
			} else {
				break;
			}
		}
	}
}

function opFormula(){
	top.setCaret();
	var area = top.document.getElementById("expression");
	top.insertText(area, this);
}

function opFuncFormula(){
	top.setCaret();
	var area = top.document.getElementById("expression");
	top.insertText(area, this);
	top.loadFuncFormulaDetail(this);
}
<%@ include file="/resource/jsp/tree_down.jsp" %>