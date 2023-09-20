<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<%
	request.setAttribute("isLangSuportEnabled", MultiLangTextGroupTag.isLangSuportEnabled());
%>
<script>
Com_IncludeFile("json2.js");

/**
nodeLangs = {
	"nodeName":[//名字
		{"lang":"zh-CN","value":"主管审批"},{"lang":"en-US","value":"Manager Auditing"}
	],
	"nodeDesc":[//描述
		{"lang":"zh-CN","value":"主管审批意见"},{"lang":"en-US","value":"Manager Auditing Note"}
	],
	"nodeOpr":{//操作，只针对自定操作
			"drafter_press":[{"lang":"zh-CN","value":"催办"},{"lang":"en-US","value":"Press"}],
			"drafter_return":[{"lang":"zh-CN","value":"撤回"},{"lang":"en-US","value":"Return"}],
			"handler_pass":[{"lang":"zh-CN","value":"通过"},{"lang":"en-US","value":"Pass"}],
			"handler_refuse":[{"lang":"zh-CN","value":"驳回"},{"lang":"en-US","value":"Refuse"}],
			"history_handler_back":[{"lang":"zh-CN","value":"撤回审批"},{"lang":"en-US","value":"Cancel Back"}],
			"history_handler_press":[{"lang":"zh-CN","value":"催办"},{"lang":"en-US","value":"Press"}]
	}
};
**/

var isLangSuportEnabled = <%=MultiLangTextGroupTag.isLangSuportEnabled()%>;
var langJson = <%=MultiLangTextGroupTag.getLangsJsonStr()%>;
var userLang = "<%=MultiLangTextGroupTag.getUserLangKey()%>";
var nodeLangs={};
var nodeLangsStr = AttributeObject.NodeData.langs;
if(!(typeof nodeLangsStr == "undefined" || nodeLangsStr=="")){
	nodeLangs = $.parseJSON(nodeLangsStr);
}

function _getLangLabel(defLabel,langs,lang){
	var langArr=[];
	if(langs!=null && langs!=""){
		langArr = $.parseJSON(langs);
	}
	for(var i=0;i<langArr.length;i++){
		if(lang==langArr[i]["lang"]){
			return _formatValues(langArr[i]["value"])||defLabel;
		}
	}
	return _formatValues(defLabel);
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

function _getLangByElName(eleName,index,prefix){
	//[{lang:"zh-CN","value":""},{lang:"en-US","value":""}]
	var elLang=[];
	if(!isLangSuportEnabled){
		return elLang;
	}
	var eles = document.getElementsByName(eleName);
	var val= eles[index].value;
	if(!(typeof prefix=="undefined" || prefix==null)){
		eleName=prefix+eleName;
	}
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

/*
	langKey:语言对象KEY
	nodeData:流程节点数据
	propName:流程定义属性，如name
	prefix:是否在流程定义属性加_,区分流程属性
*/
function _initPropLang4Edit(langKey,nodeData,propName,prefix){
	if(!isLangSuportEnabled){
		return ;
	}
	//多语言
	var langsValue = nodeLangs[langKey];
	var NodeData = nodeData || AttributeObject.NodeData;
	if(!(typeof langsValue == "undefined" || langsValue==null)){
		var defValue = _getLangLabelByJson(NodeData[propName],langsValue, langJson["official"]["value"]);
		document.getElementsByName("wf_"+propName)[0].value = defValue||"";
		for(var i=0;i<langJson["support"].length;i++){
			var lang = langJson["support"][i]["value"];
			var elName = "wf_"+propName;
			if(!(typeof prefix=="undefined" || prefix==null)){
				elName=prefix+elName;
			}
			if (document.getElementsByName(elName+"_"+lang)[0]){
				document.getElementsByName(elName+"_"+lang)[0].value= _getLangLabelByJson("",langsValue, lang);
			}
		}
	}
}

/*
	langKey:语言对象KEY
	nodeData:流程节点数据
	propName:流程定义属性，如name
	prefix:是否在流程定义属性加_,区分流程属性
*/
function _initPropLang4View(langKey,nodeData,propName,prefix){
	if(!isLangSuportEnabled){
		return ;
	}
	//多语言
	var langsValue = nodeLangs[langKey];
	var NodeData = nodeData || AttributeObject.NodeData;
	var elName = "wf_"+propName;
	if(!(typeof prefix=="undefined" || prefix==null)){
		elName=prefix+elName;
	}
	if(!(typeof langsValue == "undefined" || langsValue==null)){
		var defValue = _getLangLabelByJson(NodeData[propName],langsValue, userLang);
		if(document.getElementsByName("wf_"+propName)[0]){
			document.getElementsByName("wf_"+propName)[0].value = defValue||"";
		}
	}
	$("#"+elName+"_span").hide();
}

function _propLang4AppendData(langKey,nodeData,propName,prefix){
	if(!isLangSuportEnabled){
		return ;
	}
	//多语言
	var langsValue = _getLangByElName("wf_"+propName ,0 ,prefix);
	var NodeData = nodeData || AttributeObject.NodeData;
	nodeLangs[langKey]=langsValue;
	NodeData.langs=JSON.stringify(nodeLangs);
}

</script>
<tr>
	<c:if test="${param.flowType == '1'}">
	<td class="tdTitle"><kmss:message key="FlowChartObject.Lang.Node.name" bundle="sys-lbpm-engine" /></td>
	</c:if>
	<c:if test="${param.flowType != '1'}">
	<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.name" bundle="sys-lbpm-engine" /></td>
	</c:if>
	<td>
	<c:choose>
		<c:when test="${param.flowType == '1'}">
			<%-- <input name="wf_name" class="inputsgl" style="width:150px" onkeydown="document.getElementsByName('nodeNameList')[0].selectedIndex=0;">
			<xlang:lbpmlang property="_wf_name" style="width:150px" langs=""/> --%>
			<c:if test="${!isLangSuportEnabled }">
				<input name="wf_name" class="inputsgl" style="width:150px" onkeydown="document.getElementsByName('nodeNameList')[0].selectedIndex=0;">
			</c:if>
			<c:if test="${isLangSuportEnabled }">
				<xlang:lbpmlangNew property="_wf_name" alias="wf_name" style="width:200px" langs="" onkeydown="document.getElementsByName('nodeNameList')[0].selectedIndex=0;"/>
			</c:if>
			<input type="hidden" name="wf_langs" value="">
			<span id="SPAN_NodeNameList">
				<span class="txtstrong">*</span>
				<select style="display:none" name="nodeNameList" onChange="if(selectedIndex>0) document.getElementsByName('wf_name')[0].value=options[selectedIndex].text;"></select>
			</span>
		</c:when>
		<c:otherwise>
			<%-- <input name="wf_name" class="inputsgl" style="width:200px" onkeydown="document.getElementsByName('nodeNameList')[0].selectedIndex=0;">
			<xlang:lbpmlang property="_wf_name" style="width:200px" langs=""/> --%>
			<c:if test="${!isLangSuportEnabled }">
				<input name="wf_name" class="inputsgl" style="width:200px" onkeydown="document.getElementsByName('nodeNameList')[0].selectedIndex=0;">
			</c:if>
			<c:if test="${isLangSuportEnabled }">
				<xlang:lbpmlangNew property="_wf_name" alias="wf_name" style="width:250px" langs="" onkeydown="document.getElementsByName('nodeNameList')[0].selectedIndex=0;"/>
			</c:if>
			<input type="hidden" name="wf_langs" value="">
			<span id="SPAN_NodeNameList">
				<span class="txtstrong">*</span>
				<select name="nodeNameList" onChange="if(selectedIndex>0) document.getElementsByName('wf_name')[0].value=options[selectedIndex].text;"></select>
			</span>
		</c:otherwise>
	</c:choose>

		<script>
		AttributeObject.CheckDataFuns.push(function(data) {
			if(data.name==""){
				alert('<kmss:message key="FlowChartObject.Lang.Node.checkNameEmpty" bundle="sys-lbpm-engine" />');
				return false;
			}
			if(data.name.indexOf(";")>-1){
				alert('<kmss:message key="FlowChartObject.Lang.Node.checkNameSymbol" bundle="sys-lbpm-engine" />');
				return false;
			}
			return true;
		});
		AttributeObject.Init.EditModeFuns.push(function(nodeData) {
			//加载节点名列表
			var data = new KMSSData();
			//edge浏览器设置缓存报RTC服务器不可用?
			data.UseCache = false;
			var field;
			data.AddBeanData("lbpmBaseInfoService");
			data = data.GetHashMapArray();
			field = document.getElementsByName("nodeNameList")[0];
			field.options[0] = new Option(FlowChartObject.Lang.pleaseSelect, "");
			if(data.length>0 && data[0].nodeNameSelectItem!=null && data[0].nodeNameSelectItem!=""){
				var nodeNameList = data[0].nodeNameSelectItem.split(";");
				for(var i=0; i<nodeNameList.length; i++)
					field.options[i+1] = new Option(nodeNameList[i]);
			}

			//多语言
			_initPropLang4Edit("nodeName",nodeData,"name","_");
		});
		AttributeObject.Init.ViewModeFuns.push(function(nodeData) {
			document.getElementsByName("nodeNameList")[0].style.display = 'none';
			
			//多语言
			_initPropLang4View("nodeName",nodeData,"name","_");
		});

		AttributeObject.AppendDataFuns.push(function(nodeData){
			//var langs=[{"lang":"zh-CN","value":"经理审批"},{"lang":"en-US","value":"Manager Auditing"}];
			_propLang4AppendData("nodeName",nodeData,"name","_");
		});

		</script>
	</td>
</tr>