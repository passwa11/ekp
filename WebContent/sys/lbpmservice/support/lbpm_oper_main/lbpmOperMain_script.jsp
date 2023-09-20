<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<%@page import="com.landray.kmss.sys.lbpm.engine.task.NodeInstanceUtils"%>

<script>
var _oprDefText=<%=NodeInstanceUtils.getAllOprNameSupportLangText()%> ;

Com_IncludeFile("json2.js");

var drafterOpers=[];
var handlerOpers=[];
var historyhandlerOpers=[];

//当前操作
var currOper={
		currNodeType:"${lbpmOperMainForm.fdNodeType}"//当前节点类型
};
// 根据节点类型返回操作集合
function getOperationsByNode(nodeType) {
	var data = new KMSSData();
	data.AddBeanData('getOperationsByNodeService&nodeType='+nodeType);
	var operation = eval("("+data.GetHashMapArray()[0]['operation']+")");
	drafterOpers = operation.drafter;
	handlerOpers = operation.handler;
	historyhandlerOpers = operation.historyhandler;
}

// 设置操作组
function selectOperationsGroup(nodeType){	
	//#47307 重复选择当前节点类型时，不清空操作
	if(currOper.currNodeType==nodeType){
		return;
	}
	else{
		currOper.currNodeType=nodeType;
	}
	
	getOperationsByNode(nodeType);
	deleteRows("TABLE_DocList_Draft");
	deleteRows("TABLE_DocList_Processor");
	deleteRows("TABLE_DocList_Historyhandler");
	if (drafterOpers.length > 0) {
		document.getElementById("drafterGroup").style.display = "";
	} else {
		document.getElementById("drafterGroup").style.display = "none";
	}
	if(nodeType=="draftNode"){
		document.getElementById("drafterGroup").style.display = "none";
	}
	if (handlerOpers.length > 0) {
		document.getElementById("processorGroup").style.display = "";
	} else {
		document.getElementById("processorGroup").style.display = "none";
	}
	if (historyhandlerOpers.length > 0) {
		document.getElementById("historyhandlerGroup").style.display = "";
	} else {
		document.getElementById("historyhandlerGroup").style.display = "none";
	}
}

// 删除动态表格内容行
function deleteRows(tableId){
    var optTB = document.getElementById(tableId);
    var rowLen = optTB.rows.length;
    for(var i = rowLen; i > 1; i--){
	    DocList_DeleteRow(optTB.rows[i-1]);
    }
}

// 添加起草人动态行时初始化操作下拉框
function AddRow_InitDrafterSelect() {
	DocList_AddRow();
	var tb = document.getElementById("TABLE_DocList_Draft");
	var index = tb.rows.length-2;
	var fdOperType = "drafterOperations["+index+"].fdOperType";
	var selectObj = document.getElementsByName(fdOperType)[0];
	for (var i = 0; i < drafterOpers.length; i++) {
		selectObj.options.add(new Option(drafterOpers[i].name, drafterOpers[i].type));
	}
}

// 添加处理人动态行时初始化操作下拉框
function AddRow_InitHandlerSelect() {
	DocList_AddRow();
	var tb = document.getElementById("TABLE_DocList_Processor");
	var index = tb.rows.length-2;
	var fdOperType = "handlerOperations["+index+"].fdOperType";
	var selectObj = document.getElementsByName(fdOperType)[0];
	for (var i = 0; i < handlerOpers.length; i++) {
		selectObj.options.add(new Option(handlerOpers[i].name, handlerOpers[i].type));
	}
}

//添加已处理人动态行时初始化操作下拉框
function AddRow_InitHistoryhandlerSelect() {
	DocList_AddRow();
	var tb = document.getElementById("TABLE_DocList_Historyhandler");
	var index = tb.rows.length-2;
	var fdOperType = "historyhandlerOperations["+index+"].fdOperType";
	var selectObj = document.getElementsByName(fdOperType)[0];
	for (var i = 0; i < historyhandlerOpers.length; i++) {
		selectObj.options.add(new Option(historyhandlerOpers[i].name, historyhandlerOpers[i].type));
	}
}

function _getLangLabelByJson(defLabel,langsArr,lang){
	if(langsArr==null){
		return defLabel;
	}
	for(var i=0;i<langsArr.length;i++){
		if(lang==langsArr[i]["lang"]){
			return langsArr[i]["value"]||defLabel;
		}
	}
	return defLabel;
}

// 设置同一行操作名称
function selectOperType(operType, selectObj){
	var selectText = selectObj.options[selectObj.selectedIndex].innerText||selectObj.options[selectObj.selectedIndex].textContent;
	var operNameObj = selectObj.parentNode.parentNode.childNodes[1].getElementsByTagName("input")[0];
	var value=selectText;
	var prefix = selectObj.name.substring(0,selectObj.name.indexOf("."));
	
	if(selectObj.value==""){
		value = "";
	}
	if(isLangSuportEnabled && typeof _oprDefText != "undefined"){
		var type = operType;
		var oprLang = _oprDefText[type];
		value = _getLangLabelByJson(value,oprLang, langJson["official"]["value"]);
		document.getElementsByName(prefix+".fdOperName")[0].value =value;
		for(var i=0;i<langJson["support"].length;i++){
			var lang = langJson["support"][i]["value"];
			document.getElementsByName(prefix+".fdOperName_"+lang)[0].value= _getLangLabelByJson(value,oprLang, lang);
		}				
	}else{
		document.getElementsByName(prefix+".fdOperName")[0].value =value;
	}
}

// 提交前校验操作名称与必须配置项
function validateForm() {
	// 操作名唯一性校验
	if(!(checkOprNameUnique("drafter") && checkOprNameUnique("handler") && checkOprNameUnique("historyhandler"))){
		return false;
	}

	// 校验是否至少选择了一个通过操作
	var tb = document.getElementById("TABLE_DocList_Processor");
	var index = tb.rows.length-1;
	for (var i = 0; i < index; i++) {
		var fdOperType = "handlerOperations["+i+"].fdOperType";
		var selectObj = document.getElementsByName(fdOperType)[0];
		for (var j = 0, size = handlerOpers.length; j < size; j++) {
			if (handlerOpers[j].isPass && handlerOpers[j].type == selectObj.value) {
				return true;
			}
		}
	}
	var message = null;
	var message1 = '<bean:message key="lbpmOperations.processor.fdOperType.mustSelect.1" bundle="sys-lbpmservice-support" />';
	var message2 = '<bean:message key="lbpmOperations.processor.fdOperType.mustSelect.2" bundle="sys-lbpmservice-support" />';
	for (var i = 0, size = handlerOpers.length; i < size; i++) {
		if (handlerOpers[i].isPass) {
			message = message1 + handlerOpers[i].name + message2;
		}
	}
	if(message) {
		alert(message);
		return false;
	}
	return true;
}

// 页面加载时判断起草人、处理人操作是否显示, 编辑页面时获取节点操作
function initialContext(){
	<c:if test="${lbpmOperMainForm.method_GET=='edit'}">
	    getOperationsByNode("${lbpmOperMainForm.fdNodeType}");
	</c:if>
	if (drafterOpers.length > 0) {
	    document.getElementById("drafterGroup").style.display = "";
	}
	var nodeType = "${lbpmOperMainForm.fdNodeType}";
	if(nodeType=="draftNode"){
		document.getElementById("drafterGroup").style.display = "none";
	}

	if (handlerOpers.length > 0) {
	    document.getElementById("processorGroup").style.display = "";
	}
	if (historyhandlerOpers.length > 0) {
	    document.getElementById("historyhandlerGroup").style.display = "";
	}
}
Com_AddEventListener(window, "load", initialContext);

//==============多语言增加==================//
var isLangSuportEnabled = <%=MultiLangTextGroupTag.isLangSuportEnabled()%>;
var langJson = <%=MultiLangTextGroupTag.getLangsJsonStr()%>;

Com_Parameter.event["submit"].push(function(){
	 handleLangByElName("fdName","fdLangJson");
	 handleLangByOpr();
	 return true;
});

function handleLangByOpr(){
	var tb = document.getElementById("TABLE_DocList_Draft");
	var index = tb.rows.length-1;
	for (var i = 0; i < index; i++) {
		var fdOperName = "drafterOperations["+i+"].fdOperName";
		var fdLangJson = "drafterOperations["+i+"].fdLangJson";
		 handleLangByElName(fdOperName,fdLangJson);
	}

	tb = document.getElementById("TABLE_DocList_Processor");
	index = tb.rows.length-1;
	for (var i = 0; i < index; i++) {
		var fdOperName = "handlerOperations["+i+"].fdOperName";
		var fdLangJson = "handlerOperations["+i+"].fdLangJson";
		 handleLangByElName(fdOperName,fdLangJson);
	}

	tb = document.getElementById("TABLE_DocList_Historyhandler");
	index = tb.rows.length-1;
	for (var i = 0; i < index; i++) {
		var fdOperName = "historyhandlerOperations["+i+"].fdOperName";
		var fdLangJson = "historyhandlerOperations["+i+"].fdLangJson";
		 handleLangByElName(fdOperName,fdLangJson);
	}

}

function handleLangByElName(eleName,elJsonName){
	//[{lang:"zh-CN","value":""},{lang:"en-US","value":""}]
	var elLang=[];
	if(!isLangSuportEnabled){
		return elLang;
	}
	var fdValue = document.getElementsByName(eleName)[0].value;
	var officialElName=eleName+"_"+langJson["official"]["value"];
	document.getElementsByName(officialElName)[0].value=fdValue;
	var lang={};
	lang["lang"]=langJson["official"]["value"];
	lang["value"]=fdValue;
	elLang.push(lang);
	for(var i=0;i<langJson["support"].length;i++){
		var elName = eleName+"_"+langJson["support"][i]["value"];
		if(elName==officialElName){
			continue;
		}
		lang={};
		lang["lang"]=langJson["support"][i]["value"];
		lang["value"]=document.getElementsByName(elName)[0].value;
		elLang.push(lang);
	}
	document.getElementsByName(elJsonName)[0].value=JSON.stringify(elLang);
}

// 校验不同身份操作的操作名称唯一性
function checkOprNameUnique(handlerType) {
	var tb = null;
	if (handlerType == "drafter") {
		tb = document.getElementById("TABLE_DocList_Draft");
	} else if (handlerType == "handler") {
		tb = document.getElementById("TABLE_DocList_Processor");
	} else if (handlerType == "historyhandler") {
		tb = document.getElementById("TABLE_DocList_Historyhandler");
	} else {
		return true;
	}
	var oprNameHash={};
	for (var i = 0; i < tb.rows.length-1; i++) {
		var fdOperName = handlerType + "Operations["+i+"].fdOperName";
		var fdValue = document.getElementsByName(fdOperName)[0].value;
		if(isLangSuportEnabled){
			var officialLang = langJson["official"]["value"];
			for(var j=0;j<langJson["support"].length;j++){
				var lang = langJson["support"][j]["value"];
				var elName = fdOperName+"_"+lang;
				var langValue = fdValue;
				if (lang != officialLang) {
					langValue = document.getElementsByName(elName)[0].value;
				}
				if (langValue) {
					if(oprNameHash[lang + "-" + langValue]===undefined){
						oprNameHash[lang + "-" + langValue]=1;
					} else {
						alert('<kmss:message key="lbpmOperMain.lbpmOperations.name.repeat" bundle="sys-lbpmservice-support" />"'+ langValue +'"');
						document.getElementsByName(elName)[0].focus();
						return false;
					}
				}
			}
		} else {
			if(oprNameHash[fdValue]===undefined){
				oprNameHash[fdValue]=1;
			} else {
				alert('<kmss:message key="lbpmOperMain.lbpmOperations.name.repeat" bundle="sys-lbpmservice-support" />"'+ fdValue +'"');
				document.getElementsByName(fdOperName)[0].focus();
				return false;
			}
		}
	}
	return true;
}
</script>
