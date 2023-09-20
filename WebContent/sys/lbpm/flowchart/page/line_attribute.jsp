<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ page import="com.landray.kmss.sys.lbpm.engine.manager.node.*" %>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<%
	pageContext.setAttribute("isLangSuportEnabled", MultiLangTextGroupTag.isLangSuportEnabled());
%>
<%
String textWidth="100%";
if(MultiLangTextGroupTag.isLangSuportEnabled()){
	textWidth="70%";
}
request.setAttribute("textWidth",textWidth);
%>

<script>
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("jquery.js|docutil.js|dialog.js|formula.js");
</script>
<script src="../js/workflow.js"></script>
<script src="../js/attribute.js"></script>
<script type="text/javascript">
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
</script>
<script>
var dialogObject = window.dialogArguments?window.dialogArguments:opener.Com_Parameter.Dialog;
FlowChartObject = dialogObject.Window.FlowChartObject;
var LineObject = dialogObject.Line;
var LineData = dialogObject.Line.Data;

function initDocument(){
	//初始化规则
	initRuleInfo();
	
	WorkFlow_PutDataToField(LineData, function(propertyName){
		return "wf_"+propertyName;
	});
	
	if(LineObject.StartNode.TypeCode == FlowChartObject.NODETYPE_CONDITION){
		document.getElementById("conditionId").style.display="";
	}
	
	if(FlowChartObject.IsEdit){
		document.getElementsByName("btnOK")[0].value = FlowChartObject.Lang.OK;
		document.getElementsByName("btnCancel")[0].value = FlowChartObject.Lang.Cancel;
		document.getElementsByName("wf_name")[0].focus();
		DIV_EditButtons.style.display = "";
	}else{
		disabledOperation();
		document.getElementsByName("btnClose")[0].value = FlowChartObject.Lang.Close;
		DIV_ReadButtons.style.display = "";
	}
	dialogHeight = "250px";

	var isEdit = AttributeObject.isEdit ? AttributeObject.isEdit() : FlowChartObject.IsEdit;
	if(isEdit){
		//多语言
		_initPropLang4Edit("name","_");
	}else{
		_initPropLang4View("name","_");
	}
}

function initRuleInfo(){
	//初始化规则
	var isRule = false;
	try{
		var condition = eval('('+LineData.condition+')');
		if(condition.type && condition.type == 'rule'){
			isRule = true;
		}
	}catch(e){
	}
	var isShow = false;
	if(FlowChartObject.SysRuleTemplate || isRule){
		isShow = true;
	}else{
		if(FlowChartObject && FlowChartObject.ModelId){
			var modelId = FlowChartObject.ModelId;
			//请求后台来确认是否显示（主要解决流程文档页面节点看不到选项的问题）
			$.ajax({
				  url: Com_Parameter.ContextPath+"sys/lbpm/engine/jsonp.jsp?s_bean=lbpmRuleHandlerService",
				  type:'GET',
				  async:false,//同步请求
				  data:{modelId: modelId},
				  success: function(json){
					  var data = eval('('+json+')');
					  if(data.isShow){
						 isShow = true;
					  }	
				  }
			});
		}
	}
	if(isShow){//初始化规则机制对象
		if(!window.sysRuleQuote){
			window.sysRuleQuote = window.SysRuleQuote("wf_condition","wf_disCondition","wf_condition",FlowChartObject.SysRuleTemplate,FlowChartObject.LbpmTemplateKey);
		}
		$(".originalMode").remove();
		if(isRule){
			var index;
			if(FlowChartObject.IsEdit){
				index = window.sysRuleQuote.initRuleQuote(LineData.condition,0,'wf_condition','wf_disCondition','edit');
			}else{
				index = window.sysRuleQuote.initRuleQuote(LineData.condition,0,'wf_condition','wf_disCondition','view');
			}
			if(index != -1){
				$(".formula").eq(0).hide();
				$(".condition").eq(0).hide();
				$(".rule.wf_condition").eq(0).show();
				$("input[name='lineMode'][value='rule']").attr("checked",true);
			}
		}
	}else{
		//不存在机制内容，隐藏入口
		$(".newMode").remove();
	}
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

function writeMessage(key){
	document.write(FlowChartObject.Lang.Line[key]);
}

function writeLineData(){
	var data = new Object();
	WorkFlow_GetDataFromField(data, function(fieldName){
		if(fieldName.substring(0,3)=="wf_")
			return fieldName.substring(3);
		return null;
	});
	if(!lineDataCheck(data))
		return;
	for(var o in data)
		LineData[o] = data[o];

	_propLang4AppendData("name","_");

	returnValue = true;
	//写入规则的数据
	writeRuleMapData();
	
	window.close();
}
function lineDataCheck(data){
	if(LineObject.StartNode.TypeCode == FlowChartObject.NODETYPE_CONDITION){
		if(data["condition"] == null || Com_Trim(data["condition"]) == ''){
			alert('<kmss:message key="FlowChartObject.Lang.Line.checkConditionAllEmpty" bundle="sys-lbpm-engine" />');
			return false;
		}
		if(!checkRuleData()){
			return false;
		}
	}
	return true;
}

function openExpressionEditor() {
	Formula_Dialog("wf_condition", "wf_disCondition",FlowChartObject.FormFieldList,"Boolean",null,"com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction",FlowChartObject.ModelName);
}
</script>
</head>
<body onload="initDocument();">
<br><br>
<center>
<kmss:message key="FlowChartObject.Lang.Line.id" bundle="sys-lbpm-engine" />: <input name="wf_id" class="inputread" size="4" readonly>
<br><br>
<table width="590px" class="tb_normal">
	<tr>
		<td width="100px"><script>writeMessage("name")</script></td>
		<td>
			<%-- <input name="wf_name" class="inputsgl" style="width:<%=textWidth%>" onkeydown="if(event.keyCode==13){document.getElementsByName('btnOK')[0].click();}">
			<xlang:lbpmlang property="_wf_name" style="width:70%" langs=""/> --%>
			<c:if test="${!isLangSuportEnabled }">
				<input name="wf_name" class="inputsgl" style="width:<%=textWidth%>" onkeydown="if(event.keyCode==13){document.getElementsByName('btnOK')[0].click();}">
			</c:if>
			<c:if test="${isLangSuportEnabled }">
				<xlang:lbpmlangNew property="_wf_name" alias="wf_name" className="inputsgl" style="width:${requestScope.textWidth }" onkeydown="if(event.keyCode==13){document.getElementsByName('btnOK')[0].click();"/>
			</c:if>
			<input type="hidden" name="wf_langs" value="">
		</td>
	</tr>
	<tr id="conditionId" style="display:none">
		<td width="100px"><script>writeMessage("condition")</script></td>
		<td>
			<div class="originalMode">
				<input type="hidden" name="wf_condition">
				<input style="width:100%" class="inputsgl" readonly name="wf_disCondition">
				<br>
				<a href="#" onclick="openExpressionEditor();"><script>writeMessage("formula")</script></a>
			</div>
			<div class="newMode">
				<label><input type="radio" value="rule" name="lineMode" onclick="selectMode(this,'rule')">引用规则</label>
				<label><input type="radio" value="formula" name="lineMode" checked="true" onclick="selectMode(this,'formula')">公式定义器</label>
				<input type="hidden" name="wf_condition">
				<input style="width:100%" class="inputsgl condition" readonly name="wf_disCondition">
				<a class="formula" href="#" onclick="openExpressionEditor();">选择</a>
				<c:import url="/sys/rule/sys_ruleset_quote/sysRuleQuote.jsp" charEncoding="UTF-8">
					<c:param name="type" value="line"></c:param>
					<c:param name="returnType" value="Boolean"></c:param>
					<c:param name="mode" value="rule"></c:param>
					<c:param name="key" value="wf_condition"></c:param>
				</c:import>
			</div>
		</td>
	</tr>
</table>
<br><br>
<div id="DIV_EditButtons" style="display: none">
	<input name="btnOK" type="button" class="btnopt" onclick="writeLineData();">
	&nbsp;&nbsp;&nbsp;&nbsp;
	<input name="btnCancel" type="button" class="btnopt" onclick="window.close();">
</div>
<div id="DIV_ReadButtons" style="display: none">
	<input name="btnClose" type="button" class="btnopt" onclick="window.close();">
</div>
</center>

<script>
Com_IncludeFile("json2.js");

var isLangSuportEnabled = <%=MultiLangTextGroupTag.isLangSuportEnabled()%>;
var langJson = <%=MultiLangTextGroupTag.getLangsJsonStr()%>;
var userLang = "<%=MultiLangTextGroupTag.getUserLangKey()%>";
var lineLangs=[];
var lineLangsStr = LineData.langs;
if(!(typeof lineLangsStr == "undefined" || lineLangsStr=="")){
	lineLangs = $.parseJSON(lineLangsStr);
}

function _getLangLabel(defLabel,langs,lang){
	var langArr=[];
	if(langs!=null && langs!=""){
		langArr = $.parseJSON(langs);
	}
	for(var i=0;i<langArr.length;i++){
		if(lang==langArr[i]["lang"]){
			return langArr[i]["value"]||defLabel;
		}
	}
	return defLabel;
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
	propName:流程定义属性，如name
	prefix:是否在流程定义属性加_,区分流程属性
*/
function _initPropLang4Edit(propName,prefix){
	//多语言
	if(!(typeof lineLangs == "undefined" || lineLangs==null)){
		var defValue = _getLangLabelByJson(LineData[propName],lineLangs, langJson["official"]["value"]);
		document.getElementsByName("wf_"+propName)[0].value = defValue||"";
		for(var i=0;i<langJson["support"].length;i++){
			var lang = langJson["support"][i]["value"];
			var elName = "wf_"+propName;
			if(!(typeof prefix=="undefined" || prefix==null)){
				elName=prefix+elName;
			}
			document.getElementsByName(elName+"_"+lang)[0].value= _getLangLabelByJson("",lineLangs, lang);
		}
	}
}

/*
	nodeData:流程节点数据
	propName:流程定义属性，如name
	prefix:是否在流程定义属性加_,区分流程属性
*/
function _initPropLang4View(propName,prefix){
	//多语言
	var elName = "wf_"+propName;
	if(!(typeof prefix=="undefined" || prefix==null)){
		elName=prefix+elName;
	}
	if(!(typeof lineLangs == "undefined" || lineLangs==null)){
		var defValue = _getLangLabelByJson(LineData[propName],lineLangs, userLang);
		document.getElementsByName("wf_"+propName)[0].value = defValue||"";
	}
	$("#"+elName+"_span").hide();
}

function _propLang4AppendData(propName,prefix){
	//多语言
	var langsValue = _getLangByElName("wf_"+propName ,0 ,prefix);
	LineData.langs=JSON.stringify(langsValue);
}

</script>
<script type="text/javascript" src='<c:url value="/sys/rule/resources/js/common.js"/>'></script>
<script type="text/javascript" src='<c:url value="/sys/rule/resources/js/rule_quote.js"/>'></script>
<script type="text/javascript">
/*规则引擎*/
//选择模式
function selectMode(obj,label){
	if(label == 'formula'){
		$(".formula").eq(0).show();
		$(".condition").eq(0).show();
		$(".rule.wf_condition").eq(0).hide();
		//记录需要该引用id
		window.sysRuleQuote.recordDelMapContentIds(0);
		//清空规则信息
		$(".rule.wf_condition").eq(0).find("[name='ruleId']").eq(0).val("");
		$(".rule.wf_condition").eq(0).find("[name='ruleName']").eq(0).val("");
		
		$(".rule.wf_condition").eq(0).find("[name='mapContent']").eq(0).val("");
		$(".rule.wf_condition").eq(0).find("[name='alreadyMapId']").eq(0).val("");
		$(".rule.wf_condition").eq(0).find("[name='alreadyMapName']").eq(0).val("");
	}else{
		$(".formula").eq(0).hide();
		$(".condition").eq(0).hide();
		$(".rule.wf_condition").eq(0).show();
		
		$(".rule.wf_condition").eq(0).find(".alreadyMapType").eq(0).hide();
		$(".rule.wf_condition").eq(0).find(".mapArea").eq(0).hide();
		
	}
	$("[name='wf_condition']").eq(0).val("");
	$("[name='wf_disCondition']").eq(0).val("");
}
//校验
function checkRuleData(){
	var condition = $("[name='wf_condition']").eq(0).val();
	var mode = $("[name='lineMode']").val();
	if(mode == 'rule'){
		return window.sysRuleQuote.checkData();
	}
	return true;
}

function writeRuleMapData(){
	if(window.sysRuleQuote){
		window.sysRuleQuote.writeData("lineMode");
	}
}
//选择
function selectRule(returnType,mode,key){
	if(key == 'wf_condition'){
		window.sysRuleQuote.selectRule(returnType,mode);
	}
}
//更新映射
function updateMapContent(value, obj,key){
	if(key == 'wf_condition'){
		window.sysRuleQuote.updateMapContent(value, obj);
	}
}
/*规则引擎*/
</script>
</body>
</html>