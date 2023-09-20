<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
Com_Parameter.XMLDebug = top.dialogObject.XMLDebug;
var Data_XMLCatche = top.dialogObject.XMLCatche;
top.document.title = '<bean:message bundle="sys-formula" key="tree.root"/>';
function generateTree() {
	LKSTree = new TreeView("LKSTree", "<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.messageDefiner"/>", document.getElementById("treeDiv"));
	var n1, n2;
	n1 = LKSTree.treeRoot;
	n2 = n1.AppendChild(
		"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.formVars"/>"
	);
	n2.FetchChildrenNode = getVars;
	n2 = n1.AppendChild(
		"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.lbpmVars"/>"
	);
	n2.FetchChildrenNode = getLbpmVars;
	n2 = n1.AppendChild(
		"<bean:message bundle="sys-formula" key="tree.func"/>"
	);
	n2.FetchChildrenNode = getFunctions;
	
	n1.isExpanded = true;
	LKSTree.Show();
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

function opFuncFormula(){
	top.setCaret();
	var area = top.focusTextArea||top.document.getElementById("expression");
	top.insertText(area, this);
	top.loadFuncFormulaDetail(this);
}

function getVars(){
	var varInfo = top.dialogObject.formulaParameter.varInfo;
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

function getLbpmVars(){
	var lbpmVarInfo = setLbpmVars();
	for(var i=0; i<lbpmVarInfo.length; i++){
		var textArr = lbpmVarInfo[i].text.split(".");
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
		node.value = "%"+lbpmVarInfo[i].text+"%";
		node.title = lbpmVarInfo[i].value;
	}
}

function setLbpmVars(){
	var lbpmVars = [{'text':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.handler"/>",'value':'%handler%','title':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.handler"/>"},
					{'text':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.creator"/>",'value':'%creator%','title':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.creator"/>"},
					{'text':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.creatorDept"/>",'value':'%creatorDept%','title':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.creatorDept"/>"},
					{'text':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.creatorPost"/>",'value':'%creatorPost%','title':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.creatorPost"/>"},
					{'text':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.identity"/>",'value':'%identity%','title':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.identity"/>"},
					{'text':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.identityDept"/>",'value':'%identityDept%','title':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.identityDept"/>"},
					{'text':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.docCreator"/>",'value':'%docCreator%','title':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.docCreator"/>"},
					{'text':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.nodeName"/>",'value':'%nodeName%','title':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.nodeName"/>"},
					{'text':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.oprName"/>",'value':'%oprName%','title':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.oprName"/>"},
					{'text':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.docSubject"/>",'value':'%docSubject%','title':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.docSubject"/>"},
					{'text':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.auditNote"/>",'value':'%auditNote%','title':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.auditNote"/>"},
					{'text':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.oprHandler"/>",'value':'%oprHandler%','title':"<bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.oprHandler"/>"}
		        ];
	return lbpmVars;
}

function opFormula(){
	top.setCaret();
	var area = top.focusTextArea||top.document.getElementById("expression");
	top.insertText(area, this);
}
<%@ include file="/resource/jsp/tree_down.jsp" %>