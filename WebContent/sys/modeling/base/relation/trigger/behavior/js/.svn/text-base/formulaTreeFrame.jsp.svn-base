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
	var modelMainName = "<bean:message bundle="sys-formula" key="tree.var"/>";
	if(top.dialogObject.formulaParameter.modelMainName){
		modelMainName = top.dialogObject.formulaParameter.modelMainName + "(<bean:message bundle="sys-modeling-base" key="sys.profile.modeling.homePage.modelChecked"/>)";
	}
	n2 = n1.AppendChild(modelMainName);
	n2.FetchChildrenNode = getVars;

	// 前置表单字段变量
	var prefix = top.dialogObject.formulaParameter.prefix;
    if(prefix != "customValidate"){
		var otherFieldList = top.dialogObject.formulaParameter.otherVarInfo;
		if(otherFieldList && otherFieldList.length>0){
			for(var i=0;i< otherFieldList.length;i++){
				var varsInfo = otherFieldList[i];
				n2 = n1.AppendChild(varsInfo.varName);
				n2.FetchChildrenNode = getOtherVars(varsInfo.data,n2);
			}
		}
	}


	n2 = n1.AppendChild(
		"<bean:message bundle="sys-formula" key="tree.func"/>"
	);
	n2.FetchChildrenNode = getFunctions;

		// 目标表单字段变量
	if(top.dialogObject.formulaParameter.targetFormList){
		n2 = n1.AppendChild("<bean:message bundle="sys-modeling-base" key="modelingFormula.target.form.field.constant"/>");
		n2.FetchChildrenNode = getTargetVars;
	}

	//关联表单字段常量
	if(prefix == "customValidate"){
		var otherTargetFormList = top.dialogObject.formulaParameter.otherVarInfo;
		if(otherTargetFormList && otherTargetFormList.length>0){
			for(var i=0;i< otherTargetFormList.length;i++){
			var varsInfo = otherTargetFormList[i];
			n2 = n1.AppendChild(varsInfo.varName);
			n2.FetchChildrenNode = getOtherTargetVars(varsInfo.data,n2);
			}
		}
	}
	n1.isExpanded = true;
	LKSTree.Show();
}

function getVars(){
	var varInfo = top.dialogObject.formulaParameter.varInfo;
	for(var i=0; i< varInfo.length; i++){
        var label = varInfo[i].fullLabel||varInfo[i].label;
		if(!label){
			continue
		}
		var textArr =label.split(".");
		var pNode = this;
		var node;
		for(var j=0; j< textArr.length; j++){
			node = Tree_GetChildByText(pNode, textArr[j]);
			if(node==null){
				node = pNode.AppendChild(textArr[j]);
			}
			pNode = node;
		}
		node.name = varInfo[i].name;
		node.action = opFormula;

		node.title =label+ "(" + varInfo[i].type + ")"; //在提示信息里带上变量的类型
		node.value = "$"+label+"$";
		node.summary = varInfo[i].summary;
	}
}

function getTargetVars(){
	var targetFormList = top.dialogObject.formulaParameter.targetFormList;
	for(var controlId in targetFormList){
        var label = targetFormList[controlId].fullLabel||targetFormList[controlId].label;
		if(!label){
			continue
		}
		var textArr = label.split(".");
		var pNode = this;
		var node;
		for(var j=0; j< textArr.length; j++){
			node = Tree_GetChildByText(pNode, textArr[j]);
			if(node==null){
				node = pNode.AppendChild(textArr[j]);
			}
			pNode = node;
		}
		node.name = targetFormList[controlId].name;
		node.action = opFormula;
		node.title = label + "(" + targetFormList[controlId].type + ")"; //在提示信息里带上变量的类型
		node.value = label;
	}
}

function getOtherTargetVars(data,cnode){
	var targetFormList = data;
	for(var controlId in targetFormList){
		var label = targetFormList[controlId].fullLabel||targetFormList[controlId].label;
		if(!label){
			continue
		}
		var textArr = label.split(".");
		var pNode = cnode;
		var node;
		for(var j=0; j< textArr.length; j++){
			node = Tree_GetChildByText(pNode, textArr[j]);
			if(node==null){
				node = pNode.AppendChild(textArr[j]);
			}
			pNode = node;
		}
		node.name = targetFormList[controlId].name;
		node.action = opFormula;
		node.title = label + "(" + targetFormList[controlId].type + ")"; //在提示信息里带上变量的类型
		node.value = label;
	}
}

function getOtherVars(data,cnode){
	if(data && data.length>0){
		for(var i=0; i< data.length; i++){
			var label = data[i].fullLabel||data[i].label;
			if(!label){
				continue
			}
			var textArr = label.split(".");
			var pNode = cnode;
			var node;
			for(var j=0; j< textArr.length; j++){
				node = Tree_GetChildByText(pNode, textArr[j]);
				if(node==null){
					node = pNode.AppendChild(textArr[j]);
				}
				pNode = node;
			}
			node.name = data[i].name;
			node.action = opFormula;
			node.title = label + "(" + data[i].type + ")"; //在提示信息里带上变量的类型
			label = data[i].useLabel || label;
			node.value = "$" + label + "$";
		}
	}
}

function getFunctions(){
	var funcInfo = top.dialogObject.formulaParameter.funcInfo;
	funcInfo.sort(function(o1, o2){return o1.text==o2.text?0:(o1.text>o2.text?1:-1);});
	for(var i=0; i< funcInfo.length; i++){
		var textArr = funcInfo[i].text.split(".");
		var pNode = this;
		var node;
		for(var j=0; j< textArr.length; j++){
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