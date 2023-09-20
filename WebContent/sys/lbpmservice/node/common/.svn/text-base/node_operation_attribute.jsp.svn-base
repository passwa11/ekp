<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<%@page import="com.landray.kmss.sys.lbpm.engine.task.NodeInstanceUtils"%>
<%
	pageContext.setAttribute("isLangSuportEnabled", MultiLangTextGroupTag.isLangSuportEnabled());
%>
<%
String textWidth="100%";
if(MultiLangTextGroupTag.isLangSuportEnabled()){
	textWidth="55%";
}
request.setAttribute("textWidth",textWidth);
%>
<%@ page import="com.landray.kmss.sys.lbpm.engine.manager.operation.OperationType,
	com.landray.kmss.sys.lbpm.engine.manager.operation.OperationTypeManager,
	com.landray.kmss.util.ResourceUtil,
	java.util.*" %>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
Com_IncludeFile("jquery.js|docutil.js|doclist.js|dialog.js");
</script>

<script>
var _oprDefText=<%=NodeInstanceUtils.getAllOprNameSupportLangText()%> ;

DocList_Info.push("drafterOptList", "handlerOptList", "historyhandlerOptList");

function operationDataCheck(data) {
	data.operations = new Array();
	var optTypeField = document.getElementsByName("operationType")[0];
	if(optTypeField.selectedIndex==-1 || optTypeField.options[optTypeField.selectedIndex].value==""){
		switch(nodeDataCheckOperation(data, "drafter")){
			case 1:
				alert('<kmss:message key="FlowChartObject.Lang.Operation.checkCreatorOptNameEmpty" bundle="sys-lbpmservice" />');
				return false;
			case 2:
				alert('<kmss:message key="FlowChartObject.Lang.Operation.checkCreatorOptTypeEmpty" bundle="sys-lbpmservice" />');
				return false;
		}
		switch(nodeDataCheckOperation(data, "handler", true)){
			case 1:
				alert('<kmss:message key="FlowChartObject.Lang.Operation.checkHandlerOptNameEmpty" bundle="sys-lbpmservice" />');
				return false;
			case 2:
				alert('<kmss:message key="FlowChartObject.Lang.Operation.checkHandlerOptTypeEmpty" bundle="sys-lbpmservice" />');
				return false;
			case 3:
				alert('<kmss:message key="FlowChartObject.Lang.Operation.checkHandlerOptTypePass" bundle="sys-lbpmservice" arg0="${param.passOperationType}" />');
				return false;
		}
		switch(nodeDataCheckOperation(data, "historyhandler")){
			case 1:
				alert('<kmss:message key="FlowChartObject.Lang.Operation.checkHistoryhandlerOptNameEmpty" bundle="sys-lbpmservice" />');
				return false;
			case 2:
				alert('<kmss:message key="FlowChartObject.Lang.Operation.checkHistoryhandlerOptTypeEmpty" bundle="sys-lbpmservice" />');
				return false;
		}
	}else{		
		data.operations.refId =  optTypeField.options[optTypeField.selectedIndex].value;
	}

	if(nodeDataCheckOperationSample(data,"drafter")){
		return false;
	}
	if(nodeDataCheckOperationSample(data,"handler")){
		return false;
	}
	if(nodeDataCheckOperationSample(data,"historyhandler")){
		return false;
	}

	return true;
}
AttributeObject.CheckDataFuns.push(operationDataCheck);

function initOperationData(data) {
	var NodeData = data || AttributeObject.NodeData;
	// 1 ajax填充选择项 2 JSP加载可选择操作 3 ajax加载选中的数据、显示操作编辑列表
	var data = new KMSSData(), i, selectedIndex,defaultIndex;
	if(NodeData.operations && NodeData.operations.refId != null){
		data.AddBeanData("getOperTypesByNodeService&nodeType=${JsParam.nodeType}&refId="+NodeData.operations.refId);
	}
	else{
		data.AddBeanData("getOperTypesByNodeService&nodeType=${JsParam.nodeType}");
	}
	data = data.GetHashMapArray();
	var field = document.getElementsByName("operationType")[0];
	field.options.length = 0;
	field.options[field.options.length] = new Option('<kmss:message key="FlowChartObject.Lang.Operation.operationCustom" bundle="sys-lbpmservice" />', "");
	for(i=0; i<data.length; i++){
		//field.options[field.options.length] = new Option(data[i].label, data[i].value);
		if(AttributeObject.isNodeCanBeEdit){
			field.options[field.options.length] = new Option(_getLangLabel(data[i].label
					,data[i].langs, langJson["official"]["value"]),  data[i].value);
		}else{
			field.options[field.options.length] = new Option(_getLangLabel(data[i].label
					,data[i].langs, userLang),  data[i].value);
		}
		if (selectedIndex == null) {
			if(NodeData.operations && data[i].value == NodeData.operations.refId){
				selectedIndex = field.options.length - 1;
			}
			else if(data[i].isDefault=="true"){
				defaultIndex = field.options.length - 1;
			}
		}
	}
	if(selectedIndex == null && defaultIndex){
		selectedIndex = defaultIndex;
	}
	if(NodeData.operations && NodeData.operations.refId==null){
		selectedIndex = 0;
	}
	if(selectedIndex > -1){
		field.selectedIndex = selectedIndex;
	}
	refreshOperationList(NodeData, true);
}
AttributeObject.Init.AllModeFuns.push(initOperationData);


//修改操作方式后，刷新界面显示
function refreshOperationList(data, isInit){
	var drafterCount=0;
	var NodeData = data || AttributeObject.NodeData;
	var drafterOptList = document.getElementById("drafterOptList"),
		handlerOptList = document.getElementById("handlerOptList"),
		historyhandlerOptList = document.getElementById("historyhandlerOptList");
	var field = document.getElementsByName("operationType")[0];
	if(field.options[field.selectedIndex].value==""){
		if(isInit && NodeData.operations!=null){
			for(var i=0; i<NodeData.operations.length; i++){
				addOperationRow(NodeData.operations[i], NodeData.operations[i].XMLNODENAME,true);

				if (NodeData.operations[i].XMLNODENAME.indexOf("drafter")!=-1
						|| NodeData.operations[i].XMLNODENAME.indexOf("creator")!=-1) {
					drafterCount++;
				}

			}
		}
		refreshOperationDisabled(drafterOptList, !AttributeObject.isNodeCanBeEdit,"drafter");
		refreshOperationDisabled(handlerOptList, !AttributeObject.isNodeCanBeEdit,"handler");
		refreshOperationDisabled(historyhandlerOptList, !AttributeObject.isNodeCanBeEdit,"historyhandler");

		if(AttributeObject.isNodeCanBeEdit){
			document.getElementById("drafterTRId").style.display="";
		}else{
			document.getElementById("drafterTRId").style.display=(drafterCount==0?"none":"");
		}
		
		//刷新后修改样式，如果选择自定义，则恢复显示
		$(".multiLang.drafterOperation_name").children().find(".multi_lang_icon").css("display","");
		$(".multiLang.drafterOperation_name").children().find(".lang_item").css("display","none");
		$(".multiLang.drafterOperation_name").children().find(".multi_lang_input").css("width","100%");
		$(".multiLang.drafterOperation_name").attr("onclick","changeLang(event, this)");
		//checkLbpmLangStatus1($(".multiLang.drafterOperation_name"));
		
		$(".multiLang.handlerOperation_name").children().find(".multi_lang_icon").css("display","");
		$(".multiLang.handlerOperation_name").children().find(".lang_item").css("display","none");
		$(".multiLang.handlerOperation_name").children().find(".multi_lang_input").css("width","100%");
		$(".multiLang.handlerOperation_name").attr("onclick","changeLang(event, this)");
		//checkLbpmLangStatus1($(".multiLang.handlerOperation_name"));
		
		$(".multiLang.historyhandlerOperation_name").children().find(".multi_lang_icon").css("display","");
		$(".multiLang.historyhandlerOperation_name").children().find(".lang_item").css("display","none");
		$(".multiLang.historyhandlerOperation_name").children().find(".multi_lang_input").css("width","100%");
		$(".multiLang.historyhandlerOperation_name").attr("onclick","changeLang(event, this)");
		//checkLbpmLangStatus1($(".multiLang.historyhandlerOperation_name"));
	}else{
		deleteAllOperationRow(drafterOptList);
		deleteAllOperationRow(handlerOptList);
		deleteAllOperationRow(historyhandlerOptList);
		var data = new KMSSData();
		data.AddBeanData("getOperationsByDefinitionService&fdId="+field.options[field.selectedIndex].value);
		data = data.GetHashMapArray();
		if (data.length > 0) {
			var operations = data[0].operation;
			operations = (new Function("return ("+operations+");"))();
			for(var i=0; i<operations.length; i++){
				addOperationRow({name:operations[i].name, type:operations[i].type,langs:operations[i].langs}, operations[i].handlerType,false);

				if(operations[i].handlerType=="drafter"){
					drafterCount++;
				}

			}
		}
		refreshOperationDisabled(drafterOptList, true,"drafter");
		refreshOperationDisabled(handlerOptList, true,"handler");
		refreshOperationDisabled(historyhandlerOptList, true,"historyhandler");
		document.getElementById("drafterTRId").style.display=(drafterCount==0?"none":"");
		//刷新后修改样式，如果选择非自定义，则要按原有的流程多语言显示
		$(".multiLang.drafterOperation_name").children().find(".multi_lang_icon").css("display","none");
		$(".multiLang.drafterOperation_name").children().find(".lang_item").css("display","");
		$(".multiLang.drafterOperation_name").children().find(".multi_lang_input").css("width","100%");
		$(".multiLang.drafterOperation_name").attr("onclick",null);
		
		$(".multiLang.handlerOperation_name").children().find(".multi_lang_icon").css("display","none");
		$(".multiLang.handlerOperation_name").children().find(".lang_item").css("display","");
		$(".multiLang.handlerOperation_name").children().find(".multi_lang_input").css("width","100%");
		$(".multiLang.handlerOperation_name").attr("onclick",null);
		
		$(".multiLang.historyhandlerOperation_name").children().find(".multi_lang_icon").css("display","none");
		$(".multiLang.historyhandlerOperation_name").children().find(".lang_item").css("display","");
		$(".multiLang.historyhandlerOperation_name").children().find(".multi_lang_input").css("width","100%");
		$(".multiLang.historyhandlerOperation_name").attr("onclick",null);
		
	}
}

//设置操作的disabled属性
function refreshOperationDisabled(obj, disabled,type){
	var i, fields;
	for(i=0; i<obj.rows.length; i++)
		obj.rows[i].cells[2].style.display = disabled?"none":"";
	fields = obj.getElementsByTagName("INPUT");
	for(i=0; i<fields.length; i++)
		fields[i].disabled = disabled;
	fields = obj.getElementsByTagName("SELECT");
	for(i=0; i<fields.length; i++)
		fields[i].disabled = disabled;
	
	if(!AttributeObject.isNodeCanBeEdit){
		for(i=0; i<obj.rows.length; i++){
			var spanName = type+"Operation_name_span";
			var span = $("span.otherLangs[name='" + spanName + "']",obj.rows[i])[0];
			if(span){
				span.style.display="none";
			}
		}
	}

}

//添加操作行
function addOperationRow(obj, person,isDefined){
	var index = person.indexOf("Operation");
	if (index > 0) {
		person = person.substring(0, index);
	}
	if (person == 'creator') {
		person = 'drafter';
	}
	var fieldValues = new Object();
	//fieldValues[person+"Operation_name"] = obj.name;
	fieldValues[person+"Operation_type"] = obj.type;
	//多语言 
	if(!isDefined){
		refOprLang(person,fieldValues,obj);
	}else{
		definedOprLang(person,fieldValues,obj);
	}
	
	DocList_AddRow(person+"OptList", null, fieldValues);
}

function refOprLang(person,fieldValues,obj){
	fieldValues[person+"Operation_name"] = _getLangLabel(obj.name, obj.langs, langJson["official"]["value"]);
	if(!AttributeObject.isNodeCanBeEdit){
		defValue = _getLangLabel(obj.name,obj.langs,userLang);
		fieldValues[person+"Operation_name"] =defValue;
	}
	if(AttributeObject.isNodeCanBeEdit){
		for(var i=0;i<langJson["support"].length;i++){
			var lang = langJson["support"][i]["value"];
			fieldValues[person+"Operation_name_"+lang] = _getLangLabel("", obj.langs, lang);
		}
	}
}
function definedOprLang(person,fieldValues,obj){
	var nodeOprLangs = nodeLangs.nodeOpr;
	var label=obj.name;
	if(!(typeof nodeOprLangs == "undefined" || nodeOprLangs==null)){
		var oprLang = nodeOprLangs[obj.type+"-"+label];
		var defValue = _getLangLabelByJson(obj.name,oprLang, langJson["official"]["value"]);
		if(!AttributeObject.isNodeCanBeEdit){
			defValue = _getLangLabelByJson(obj.name,oprLang,userLang);
		}
		fieldValues[person+"Operation_name"] =defValue;
		if(AttributeObject.isNodeCanBeEdit){
			for(var i=0;i<langJson["support"].length;i++){
				var lang = langJson["support"][i]["value"];
				fieldValues[person+"Operation_name_"+lang]= _getLangLabelByJson("",oprLang, lang);
			}				
		}
	}else{
		fieldValues[person+"Operation_name"] =obj.name;
	}
}

//删除操作行
function deleteAllOperationRow(handlerOptList){
	for(var i=handlerOptList.rows.length-1; i>0; i--)
		DocList_DeleteRow(handlerOptList.rows[i]);
}

//选择操作的时候更新操作名
function changeOperationType(obj){
	var fieldName = obj.name;
	fieldName = fieldName.substring(0, fieldName.length-4)+"name";
	var fields = document.getElementsByName(fieldName);
	var index = Com_ArrayGetIndex(document.getElementsByName(obj.name), obj);
	if(obj.selectedIndex>0){
		fields[index].value = obj.options[obj.selectedIndex].text;
	
		if(isLangSuportEnabled && typeof _oprDefText != "undefined"){
			var type = obj.options[obj.selectedIndex].value;
			var defValue = obj.options[obj.selectedIndex].text;
			var oprLang = _oprDefText[type];
			defValue = _getLangLabelByJson(defValue,oprLang, langJson["official"]["value"]);
			fields[index].value =defValue;
			for(var i=0;i<langJson["support"].length;i++){
				var lang = langJson["support"][i]["value"];
				document.getElementsByName(fieldName+"_"+lang)[index].value= _getLangLabelByJson("",oprLang, lang);
			}				
		}

	}
}

//操作数据校验
function nodeDataCheckOperation(data, person, required){
	var nameFields = document.getElementsByName(person+"Operation_name");
	var typeFields = document.getElementsByName(person+"Operation_type");
	var typeFound = false;
	if(required && nameFields.length == 0){
		data.operations.length = 0;
		return 3;
	}
	for(var i=0; i<nameFields.length; i++){
		if(nameFields[i].value == "")
			return 1;
		var selectedOpt = typeFields[i].options[typeFields[i].selectedIndex];
		var optType = selectedOpt.value;
		if(optType=="")
			return 2;
		if(selectedOpt.getAttribute('isPass') == 'true')
			typeFound = true;
		data.operations[data.operations.length] = {
				XMLNODENAME:person+"Operation",
				name:nameFields[i].value,
				type:optType};
	}
	return typeFound?0:3;
}

function nodeDataCheckOperationSample(data, person){
	function _nmIsExist(arr,val) {
		for (var i = 0; i < arr.length; i++) {
			if (arr[i] == val) {
				return true;
			}
		}
		return false;
	}
	var nameFields = document.getElementsByName(person+"Operation_name");
	var ns = [];
	for(var i=0; i<nameFields.length; i++){
		if(!_nmIsExist(ns,nameFields[i].value)){
			ns.push(nameFields[i].value);
		}else{
			alert("<kmss:message key="lbpmOperMain.lbpmOperations.name.repeat" bundle="sys-lbpmservice-support" />'"+nameFields[i].value+"'");
			return true;
		}
	}
	return false;
}

/*
	"nodeOpr":{//操作，只针对自定操作
			"drafter_press":[{"lang":"zh-CN","value":"催办"},{"lang":"en-US","value":"Press"}],
			"drafter_return":[{"lang":"zh-CN","value":"撤回"},{"lang":"en-US","value":"Return"}],
			"handler_pass":[{"lang":"zh-CN","value":"通过"},{"lang":"en-US","value":"Pass"}],
			"handler_refuse":[{"lang":"zh-CN","value":"驳回"},{"lang":"en-US","value":"Refuse"}],
			"history_handler_back":[{"lang":"zh-CN","value":"撤回审批"},{"lang":"en-US","value":"Cancel Back"}],
			"history_handler_press":[{"lang":"zh-CN","value":"催办"},{"lang":"en-US","value":"Press"}]
	}
*/
AttributeObject.AppendDataFuns.push(function(data){
	var optTypeField = document.getElementsByName("operationType")[0];
	var nodeOpr={};
	if(optTypeField.selectedIndex==-1 || optTypeField.options[optTypeField.selectedIndex].value==""){
		//只有自定义才保存多语言
		var typeKeys=["drafter","handler","historyhandler"];
		for(var n=0;n<typeKeys.length;n++){
			var typeFields = document.getElementsByName(typeKeys[n]+"Operation_type");
			for(var i=0;i<typeFields.length;i++){
				var selectedOpt = typeFields[i].options[typeFields[i].selectedIndex];
				var optType = selectedOpt.value;
				var optLangs = _getLangByElName(typeKeys[n]+"Operation_name",i);
				//nodeOpr[optType] = optLangs;
				var label = document.getElementsByName(typeKeys[n]+"Operation_name")[i].value;
				nodeOpr[optType+"-"+label] = optLangs;
			}
		}
	}
	nodeLangs["nodeOpr"]=nodeOpr;
	data.langs=JSON.stringify(nodeLangs);
});

</script>

<table class="tb_normal" width="100%" id="TB_Operation">
	<tr>
		<td width="100px"><kmss:message key="FlowChartObject.Lang.Operation.operationRefer" bundle="sys-lbpmservice" /></td>
		<td>
			<select name="operationType" onChange="refreshOperationList();">
				<option value=""><kmss:message key="FlowChartObject.Lang.Operation.operationCustom" bundle="sys-lbpmservice" /></option>
			</select>
		</td>
	</tr>
	<tr id="drafterTRId">
		<td width="100px">
			<kmss:message key="FlowChartObject.Lang.Node.creator" bundle="sys-lbpm-engine" /><br>
			<kmss:message key="FlowChartObject.Lang.Operation.creatorOperationHelp" bundle="sys-lbpmservice" />
		</td>
		<td style="vertical-align:top" id="drafterTdId">
			<table id="drafterOptList" class="tb_normal" width="100%">
				<tr>
					<td width="85px"><kmss:message key="FlowChartObject.Lang.Operation.operationType" bundle="sys-lbpmservice" /></td>
					<td width="310px"><kmss:message key="FlowChartObject.Lang.Operation.operationName" bundle="sys-lbpmservice" /></td>
					<td width="62px" align="center">
						<a href="#" onclick="DocList_AddRow();"><img src="${KMSS_Parameter_StylePath}icons/add.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationAdd" bundle="sys-lbpmservice" />"></a>
					<!--
						<a href="#" onclick="DocList_AddRow();"><kmss:message key="FlowChartObject.Lang.Operation.operationAdd" bundle="sys-lbpmservice" /></a>
					-->
				</tr>
				<tr KMSS_IsReferRow="1" style="display:none">
					<td>
 						<select name="drafterOperation_type" onchange="changeOperationType(this);">
 							<option value=""><kmss:message key="FlowChartObject.Lang.pleaseSelect" bundle="sys-lbpm-engine" /></option>
 						<%
 						List operList = OperationTypeManager.getInstance().getOperationsByNode(
 					    	request.getParameter("nodeType"), request.getParameter("modelName"), OperationType.HANDLERTYPE_DRAFTER);
 						for (int i = 0; i < operList.size(); i ++) {
 							OperationType operType = (OperationType) operList.get(i);
 							if(operType.getDisplay()) {
 						%>
							<option value="<%=operType.getType() %>">
							<c:out value="<%=ResourceUtil.getString(operType.getMessageKey()) %>" />
							</option>
						<%  }
 						}%>
						</select>
					</td>
					<td><%-- <input name="drafterOperation_name" class="inputsgl" style="width:<%=textWidth%>">
							<xlang:lbpmlang property="drafterOperation_name" style="width:55%" langs=""/> --%>
						<c:if test="${!isLangSuportEnabled }">
							<input name="drafterOperation_name" class="inputsgl" style="width:<%=textWidth%>">
						</c:if>
						<c:if test="${isLangSuportEnabled }">
							<xlang:lbpmlangNew property="drafterOperation_name" style="width:70%" className="inputsgl" langs=""/>
						</c:if>
					</td>
					<td>
						<a href="#" onclick="DocList_DeleteRow();"><img src="${KMSS_Parameter_StylePath}icons/delete.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationDelete" bundle="sys-lbpmservice" />"></a>
						<a href="#" onclick="DocList_MoveRow(-1);"><img src="${KMSS_Parameter_StylePath}icons/up.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationMoveUp" bundle="sys-lbpmservice" />"></a>
						<a href="#" onclick="DocList_MoveRow(1);"><img src="${KMSS_Parameter_StylePath}icons/down.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationMoveDown" bundle="sys-lbpmservice" />"></a>
					<!--
						<a href="#" onclick="DocList_DeleteRow();"><kmss:message key="FlowChartObject.Lang.Operation.operationDelete" bundle="sys-lbpmservice" /></a>
						<a href="#" onclick="DocList_MoveRow(-1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveUp" bundle="sys-lbpmservice" /></a>
						<a href="#" onclick="DocList_MoveRow(1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveDown" bundle="sys-lbpmservice" /></a>
					-->
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td width="100px">
			<kmss:message key="FlowChartObject.Lang.Node.handler" bundle="sys-lbpm-engine" /><br>
			<kmss:message key="FlowChartObject.Lang.Operation.handlerOperationHelp" bundle="sys-lbpmservice" />
		</td>
		<td style="vertical-align:top">
			<table id="handlerOptList" class="tb_normal" width="100%">
				<tr>
					<td width="70px"><kmss:message key="FlowChartObject.Lang.Operation.operationType" bundle="sys-lbpmservice" /></td>
					<td width="310px"><kmss:message key="FlowChartObject.Lang.Operation.operationName" bundle="sys-lbpmservice" /></td>
					<td width="62px" align="center">
						<a href="#" onclick="DocList_AddRow();"><img src="${KMSS_Parameter_StylePath}icons/add.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationAdd" bundle="sys-lbpmservice" />"></a>
					<!--
						<a href="#" onclick="DocList_AddRow();"><kmss:message key="FlowChartObject.Lang.Operation.operationAdd" bundle="sys-lbpmservice" /></a>
					-->
					</td>
				</tr>
				<tr KMSS_IsReferRow="1" style="display:none">
					<td>
						<select name="handlerOperation_type" onchange="changeOperationType(this);">
							<option value=""><kmss:message key="FlowChartObject.Lang.pleaseSelect" bundle="sys-lbpm-engine" /></option>
							<%
							String nodeType = request.getParameter("nodeType");
 							operList = OperationTypeManager.getInstance().getOperationsByNode(
 									nodeType, request.getParameter("modelName"), OperationType.HANDLERTYPE_HANDLER);
 							
							for (int i = 0; i < operList.size(); i ++) {
 								OperationType operType = (OperationType) operList.get(i);
 								if ("handler_jump".equals(operType.getType())
 												&& !nodeType.equals("checkNode") && !nodeType.equals("reviewNode")) {
 									continue;
 								}
 								if(operType.getDisplay()) {
 							%>
								<option value="<%=operType.getType() %>" isPass="<%=operType.isPassType() %>">
								<c:out value="<%=ResourceUtil.getString(operType.getMessageKey()) %>" />
								</option>
							<%  }
 							}%>
						</select>
					</td>
					<td><%-- <input name="handlerOperation_name" class="inputsgl" style="width:<%=textWidth%>">
							<xlang:lbpmlang property="handlerOperation_name" style="width:55%" langs=""/> --%>
						<c:if test="${!isLangSuportEnabled }">
							<input name="handlerOperation_name" class="inputsgl" style="width:<%=textWidth%>">
						</c:if>
						<c:if test="${isLangSuportEnabled }">
							<xlang:lbpmlangNew property="handlerOperation_name" style="width:70%" langs="" className="inputsgl"/>					
						</c:if>
					</td>
					<td align="center">
						<a href="#" onclick="DocList_DeleteRow();"><img src="${KMSS_Parameter_StylePath}icons/delete.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationDelete" bundle="sys-lbpmservice" />"></a>
						<a href="#" onclick="DocList_MoveRow(-1);"><img src="${KMSS_Parameter_StylePath}icons/up.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationMoveUp" bundle="sys-lbpmservice" />"></a>
						<a href="#" onclick="DocList_MoveRow(1);"><img src="${KMSS_Parameter_StylePath}icons/down.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationMoveDown" bundle="sys-lbpmservice" />"></a>
						<!--
						<a href="#" onclick="DocList_DeleteRow();"><kmss:message key="FlowChartObject.Lang.Operation.operationDelete" bundle="sys-lbpmservice" /></a>
						<a href="#" onclick="DocList_MoveRow(-1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveUp" bundle="sys-lbpmservice" /></a>
						<a href="#" onclick="DocList_MoveRow(1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveDown" bundle="sys-lbpmservice" /></a>
						-->
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td width="100px">
			<kmss:message key="FlowChartObject.Lang.Node.historyhandler" bundle="sys-lbpm-engine" /><br>
			<kmss:message key="FlowChartObject.Lang.Operation.historyhandlerOperationHelp" bundle="sys-lbpmservice" />
		</td>
		<td style="vertical-align:top">
			<table id="historyhandlerOptList" class="tb_normal" width="100%">
				<tr>
					<td width="70px"><kmss:message key="FlowChartObject.Lang.Operation.operationType" bundle="sys-lbpmservice" /></td>
					<td width="310px"><kmss:message key="FlowChartObject.Lang.Operation.operationName" bundle="sys-lbpmservice" /></td>
					<td width="62px" align="center">
						<a href="#" onclick="DocList_AddRow();"><img src="${KMSS_Parameter_StylePath}icons/add.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationAdd" bundle="sys-lbpmservice" />"></a>
					<!--
						<a href="#" onclick="DocList_AddRow();"><kmss:message key="FlowChartObject.Lang.Operation.operationAdd" bundle="sys-lbpmservice" /></a>
					-->
				</tr>
				<tr KMSS_IsReferRow="1" style="display:none">
					<td>
 						<select name="historyhandlerOperation_type" onchange="changeOperationType(this);">
 							<option value=""><kmss:message key="FlowChartObject.Lang.pleaseSelect" bundle="sys-lbpm-engine" /></option>
 						<%
 						operList = OperationTypeManager.getInstance().getOperationsByNode(
 					    	request.getParameter("nodeType"), request.getParameter("modelName"), OperationType.HANDLERTYPE_HISTORY_HANDLER);
 						for (int i = 0; i < operList.size(); i ++) {
 							OperationType operType = (OperationType) operList.get(i);
 							if(operType.getDisplay()) {
 						%>
							<option value="<%=operType.getType() %>">
							<c:out value="<%=ResourceUtil.getString(operType.getMessageKey()) %>" />
							</option>
						<%  }
 						}%>
						</select>
					</td>
					<td><%-- <input name="historyhandlerOperation_name" class="inputsgl" style="width:<%=textWidth%>">
							<xlang:lbpmlang property="historyhandlerOperation_name" style="width:55%" langs=""/> --%>
						<c:if test="${!isLangSuportEnabled }">
							<input name="historyhandlerOperation_name" class="inputsgl" style="width:<%=textWidth%>">
						</c:if>
						<c:if test="${isLangSuportEnabled }">
							<xlang:lbpmlangNew property="historyhandlerOperation_name" style="width:70%" langs="" className="inputsgl"/>										
						</c:if>
					</td>
					<td>
						<a href="#" onclick="DocList_DeleteRow();"><img src="${KMSS_Parameter_StylePath}icons/delete.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationDelete" bundle="sys-lbpmservice" />"></a>
						<a href="#" onclick="DocList_MoveRow(-1);"><img src="${KMSS_Parameter_StylePath}icons/up.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationMoveUp" bundle="sys-lbpmservice" />"></a>
						<a href="#" onclick="DocList_MoveRow(1);"><img src="${KMSS_Parameter_StylePath}icons/down.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationMoveDown" bundle="sys-lbpmservice" />"></a>
						<!--
						<a href="#" onclick="DocList_DeleteRow();"><kmss:message key="FlowChartObject.Lang.Operation.operationDelete" bundle="sys-lbpmservice" /></a>
						<a href="#" onclick="DocList_MoveRow(-1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveUp" bundle="sys-lbpmservice" /></a>
						<a href="#" onclick="DocList_MoveRow(1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveDown" bundle="sys-lbpmservice" /></a>
						-->
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>