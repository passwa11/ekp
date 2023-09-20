<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<%@page import="com.landray.kmss.sys.lbpm.engine.task.NodeInstanceUtils"%>
<%
	pageContext.setAttribute("isLangSuportEnabled", MultiLangTextGroupTag.isLangSuportEnabled());
%>
<style>
.btn_txt {
	margin: 0px 2px;
	color: #2574ad;
	border-bottom: 1px solid transparent;
}

.btn_txt:hover {
	text-decoration: underline;
}
</style>
<script type="text/javascript">
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("jquery.js|docutil.js|dialog.js|formula.js|doclist.js");
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("json2.js");

</script>
<script>
var isLangSuportEnabled = <%=MultiLangTextGroupTag.isLangSuportEnabled()%>;
var langJson = <%=MultiLangTextGroupTag.getLangsJsonStr()%>;
var allNodeName = <%=NodeInstanceUtils.getAllNodeNameSupportLangText()%> ;
var nodeLangs={};

DocList_Info.push("nodeList");

var dialogRtnValue = null;
var dialogObject = null;
var isOpenWindow = true;//弹出形式:弹窗or弹层
if(window.showModalDialog && window.dialogArguments){
	dialogObject = window.dialogArguments;
}else if(opener && opener.Com_Parameter.Dialog){
	dialogObject = opener.Com_Parameter.Dialog;
}else{
	dialogObject = top.Com_Parameter.Dialog;
	isOpenWindow = false;
}

FlowChartObject = dialogObject.Window.FlowChartObject;

function initDocument(){
	document.getElementsByName("btnOK")[0].value = FlowChartObject.Lang.OK;
	document.getElementsByName("btnCancel")[0].value = FlowChartObject.Lang.Cancel;
	document.getElementById("canDrawNum").innerText = dialogObject.canDrawNum;
}

function disabledOperation(){
	var i;
	var fields = document.getElementsByTagName("A");
	for(i=0; i<fields.length; i++)
		fields[i].style.display = "none";
	fields = document.getElementsByTagName("INPUT");
	for(i=0; i<fields.length; i++){
		if(fields[i].type!="button")
			fields[i].disabled = true;
	}
	fields = document.getElementsByTagName("SELECT");
	for(i=0; i<fields.length; i++)
		fields[i].disabled = true;
	fields = document.getElementsByTagName("TEXTAREA");
	for(i=0; i<fields.length; i++)
		fields[i].disabled = true;
}

function checkNodeData(){
	for(var i=0; i<document.getElementsByName("wf_nodeName").length; i++){
		var name = document.getElementsByName("wf_nodeName")[i];
		if(name.value==""){
			alert('<kmss:message key="FlowChartObject.Lang.Node.checkNameEmpty" bundle="sys-lbpm-engine" />');
			return false;
		}
		if(name.value.indexOf(";")>-1){
			alert('<kmss:message key="FlowChartObject.Lang.Node.checkNameSymbol" bundle="sys-lbpm-engine" />');
			return false;
		}
	}
	return true;
}

function dialogReturn(){
	if(!checkNodeData()){
		return;
	}

	var data = new KMSSData();
	data.AddBeanData("getOperTypesByNodeService&nodeType=reviewNode");
	data = data.GetHashMapArray();
	var refId = null;
	for(var j=0;j<data.length;j++){
		if(data[j].isDefault=="true"){
			refId = data[j].value;
			break;
		}
	}
	if(refId==null){
		alert(FlowChartObject.Lang.configDefaultOperationType_ApprovalNode);
		return;
	}
	
	var nodes = new Array();
	for(var i=0; i<document.getElementsByName("wf_nodeName").length; i++){
		var node;
		
		node = {"nodeName":document.getElementsByName("wf_nodeName")[i].value,"handlerSelectType":document.getElementsByName("wf_handlerSelectType")[i].value,
				"handlerIds":document.getElementsByName("wf_handlerIds")[i].value,"handlerNames":document.getElementsByName("wf_handlerNames")[i].value,
				"ignoreOnHandlerEmpty":(document.getElementsByName("wf_ignoreOnHandlerEmpty")[i].checked==true)?"true":"false","processType":document.getElementsByName("wf_processType")[i].value};
		
		var langsValue = _getLangByElName(i);
		nodeLangs["nodeName"]=langsValue;
		node.langs=JSON.stringify(nodeLangs);

		node.operations = new Array();
		node.operations.refId = refId;

		nodes.push(node);
	}

	dialogObject.rtnData = nodes;
	if(isOpenWindow){
		window.close();
	}else if(window.$dialog!=null){
		window.close();
		$dialog.hide();
	}
}

//审批人选择方式
var handlerSelectType = "org";

//切换审批人选择方式
function switchHandlerSelectType(thisObj){
	var handlerSelectType = thisObj.value;
	while (thisObj.tagName != 'TR') {
		thisObj = thisObj.parentNode;
	}
	var inputs = thisObj.getElementsByTagName("input");
	var handlerIds = getElementsByName(inputs, "wf_handlerIds");
	var handlerNames = getElementsByName(inputs, "wf_handlerNames");
	var spans = thisObj.getElementsByTagName("span");
	var SPAN_SelectType1 = getElementsById(spans, "SPAN_SelectType1");
	var SPAN_SelectType2 = getElementsById(spans, "SPAN_SelectType2");
	SPAN_SelectType1.style.display=handlerSelectType!="formula"?"":"none";
	SPAN_SelectType2.style.display=handlerSelectType=="formula"?"":"none";
	
	handlerIds.value = "";
	handlerNames.value = "";
}

function getElementsByName(list, name) {
	for (var i = 0; i < list.length; i ++) {
		if (list[i].name == name) {
			return list[i];
		}
	}
	return null;
}

function getElementsById(list, id) {
	for (var i = 0; i < list.length; i ++) {
		if (list[i].id == id) {
			return list[i];
		}
	}
	return null;
}

//选人添加节点
function addNodeBySelectOrg(){
	action = function(rtnVal) {
		if(rtnVal == null) {
			return;
		}
		for (var i = 0; i < rtnVal.data.length; i ++) {
			DocList_AddRow("nodeList");
			var wf_handlerIds = document.getElementsByName("wf_handlerIds");
			var wf_handlerNames = document.getElementsByName("wf_handlerNames");
			wf_handlerIds[wf_handlerIds.length-1].value = rtnVal.data[i].id;
			wf_handlerNames[wf_handlerNames.length-1].value = rtnVal.data[i].name;	
			nodeNameLang(wf_handlerIds.length-1);
		}
	};
	Dialog_Address(true, null, null, null, ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE, action);
}

function initNodeNameLang(){
	var wf_handlerIds = document.getElementsByName("wf_handlerIds");
	nodeNameLang(wf_handlerIds.length-1);
}

function nodeNameLang(index){
	if(!isLangSuportEnabled){
		return;
	}
	var def = "<kmss:message key="lbpm.nodeType.reviewNode" bundle="sys-lbpmservice" />";
	if(!(typeof allNodeName == "undefined" || allNodeName==null)){
		var reviewNodeLang = allNodeName["reviewNode"];
		var defValue = _getLangLabelByJson(def,reviewNodeLang, langJson["official"]["value"]);
		document.getElementsByName("wf_nodeName")[index].value = defValue||"";
		for(var i=0;i<langJson["support"].length;i++){
			var lang = langJson["support"][i]["value"];
			var elName = "_wf_name";
			if (document.getElementsByName(elName+"_"+lang)[index]){
				document.getElementsByName(elName+"_"+lang)[index].value= _getLangLabelByJson("",reviewNodeLang, lang);
			}
		}
	}

}
function _getLangLabelByJson(defLabel,langsArr,lang){
	if(langsArr==null){
		return defLabel;
	}
	for(var i=0;i<langsArr.length;i++){
		if(lang==langsArr[i]["lang"]){
			return _formatValues(langsArr[i]["value"])||defLabel;
		}
	}
	return _formatValues(defLabel);
}
function _formatValues(value){
	value=value||"";
	value=value.replace(/(<pre>)|(<\/pre>)/ig,"").replace(/<br\/>/ig,"\r\n");
	return value;
}

function _getLangByElName(index){
	//[{lang:"zh-CN","value":""},{lang:"en-US","value":""}]
	var elLang=[];
	if(!isLangSuportEnabled){
		return elLang;
	}
	var eles = document.getElementsByName("wf_nodeName");
	var val= eles[index].value;
	var eleName="_wf_name";
	var officialElName=eleName+"_"+langJson["official"]["value"];
	var offeles = document.getElementsByName(officialElName);
	offeles[index].value=val;
	var lang={};
	lang["lang"]=langJson["official"]["value"];
	lang["value"]=val;
	elLang.push(lang);
	for(var j=0;j<langJson["support"].length;j++){
		var elName = eleName+"_"+langJson["support"][j]["value"];
		if(elName==officialElName){
			continue;
		}
		lang={};
		lang["lang"]=langJson["support"][j]["value"];
		lang["value"]=document.getElementsByName(elName)[index].value;
		elLang.push(lang);
	}
	return elLang;
}

function selectByOrg(tr){
	while (tr.tagName != 'TR') {
		tr = tr.parentNode;
	}
	var inputs = tr.getElementsByTagName("input");
	var handlerIds = getElementsByName(inputs, "wf_handlerIds");
	var handlerNames = getElementsByName(inputs, "wf_handlerNames");
	Dialog_Address(true, handlerIds, handlerNames, null, ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE);
}

function selectByFormula(tr){
	while (tr.tagName != 'TR') {
		tr = tr.parentNode;
	}
	var inputs = tr.getElementsByTagName("input");
	var handlerIds = getElementsByName(inputs, "wf_handlerIds");
	var handlerNames = getElementsByName(inputs, "wf_handlerNames");
	Formula_Dialog(handlerIds,
			handlerNames,
			FlowChartObject.FormFieldList, 
			"com.landray.kmss.sys.organization.model.SysOrgElement[]",
			null,
			"com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction",
			FlowChartObject.ModelName);
}
</script>
<title><bean:message key="FlowChartObject.Lang.Node.quickAddReviewNode" bundle="sys-lbpmservice"/></title>
</head>
<body onload="initDocument();">
<br><br><div>
<p class="txttitle"><span onclick="addNodeBySelectOrg();" style="cursor: pointer;">
<bean:message key="FlowChartObject.Lang.Node.quickAddReviewNode.selectOrg" bundle="sys-lbpmservice"/>:&nbsp;
<img src="../images/orgelement.png" style="vertical-align:middle;"></img></span>
</p><br>
<center><span><kmss:message key="FlowChartObject.Lang.theNodeNumCanAdd" bundle="sys-lbpm-engine" /><label id="canDrawNum" style="color: #ff0000;"></label></span></center>
</div>
<center>
<br><br>
<table id="nodeList" class="tb_normal" width="92%">
	<tr>
		<td class="td_normal_title" width="4%"></td>
		<td class="td_normal_title" width="35%"><kmss:message key="FlowChartObject.Lang.Node.name" bundle="sys-lbpm-engine" /></td>
		<td class="td_normal_title" width="35%"><kmss:message key="FlowChartObject.Lang.Node.handlerNames" bundle="sys-lbpmservice" /></td>
		<td class="td_normal_title" width="10%"><kmss:message key="FlowChartObject.Lang.Node.processType" bundle="sys-lbpmservice" /></td>
		<td class="td_normal_title" width="16%">
			<a class="btn_txt" href="javascript:void(0);" onclick="DocList_AddRow();initNodeNameLang();"><kmss:message key="FlowChartObject.Lang.Operation.operationAdd" bundle="sys-lbpmservice" /></a>
		</td>
	</tr>
	<tr KMSS_IsReferRow="1" style="display:none">
		<td KMSS_IsRowIndex="1"></td>
		<td>
			<%-- <input name="wf_nodeName" class="inputsgl" style="width:65%" value='<kmss:message key="lbpm.nodeType.reviewNode" bundle="sys-lbpmservice" />'>
			<xlang:lbpmlang property="_wf_name" style="width:65%" langs=""/> --%>
			<c:if test="${!isLangSuportEnabled }">
				<input name="wf_nodeName" class="inputsgl" style="width:65%" value='<kmss:message key="lbpm.nodeType.reviewNode" bundle="sys-lbpmservice" />'>
			</c:if>
			<c:if test="${isLangSuportEnabled }">
				<xlang:lbpmlangNew property="_wf_name" alias="wf_nodeName" className="inputsgl" style="width:65%" value="${ lfn:message('sys-lbpmservice:lbpm.nodeType.reviewNode') }"/>
			</c:if>
			<input type="hidden" name="wf_langs" value="">
			<span class="txtstrong">*</span>
		</td>
		<td>
		<select name="wf_handlerSelectType" onchange="switchHandlerSelectType(this);">
			<option value="org" selected="selected">
				<kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></option>
			<option value="formula">
				<kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></option>
		</select>
			<input name="wf_handlerNames" class="inputsgl" style="width:80%" readonly>
			<input name="wf_handlerIds" type="hidden" orgattr="handlerIds:handlerNames">
			<span id="SPAN_SelectType1" style="display:'';">
			<a href="#" onclick="selectByOrg(this);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
			</span>
			<span id="SPAN_SelectType2" style="display:none;">
			<a href="#" onclick="selectByFormula(this);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
			</span>
			<br>
			<label>
				<input type="checkbox" name="wf_ignoreOnHandlerEmpty" value="true">
				<kmss:message key="FlowChartObject.Lang.Node.ignoreOnHandlerEmpty" bundle="sys-lbpmservice" />
			</label>
		</td>
		<td>
		
		<select name="wf_processType">
			<option value="0" selected="selected">
				<kmss:message key="FlowChartObject.Lang.Node.processType_0" bundle="sys-lbpmservice" /></option>
			<option value="1">
				<kmss:message key="FlowChartObject.Lang.Node.processType_1" bundle="sys-lbpmservice" /></option>
			<option value="2">
				<kmss:message key="FlowChartObject.Lang.Node.processType_20" bundle="sys-lbpmservice" /></option>
		</select>
		</td>
		<td>
			<a href="javascript:void(0);" onclick="DocList_DeleteRow();"><kmss:message key="FlowChartObject.Lang.Operation.operationDelete" bundle="sys-lbpmservice" /></a>
			<a href="javascript:void(0);" onclick="DocList_MoveRow(-1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveUp" bundle="sys-lbpmservice" /></a>
			<a href="javascript:void(0);" onclick="DocList_MoveRow(1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveDown" bundle="sys-lbpmservice" /></a>
		</td>
	</tr>
</table>
<br><br>
<div id="DIV_EditButtons" style="padding-bottom:20px;">
	<input name="btnOK" type="button" class="btnopt" onclick="dialogReturn();">
	&nbsp;&nbsp;&nbsp;&nbsp;
	<input name="btnCancel" type="button" class="btnopt" onclick="window.close();">
</div>
</center>
</body>
</html>