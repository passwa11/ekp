<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
<template:replace name="head">
<template:super/>
<script type="text/javascript">
seajs.use(['theme!form']);
var FlowChartObject = parent.FlowChartObject;
Com_IncludeFile("jquery.js|dialog.js|formula.js|doclist.js|json2.js");
</script>
</template:replace>
<template:replace name="body">
<body>
<script type="text/javascript">
DocList_Info.push("paramTable");
/**
 * 功能：页面初始化（标准接口）
 *
 * @param config 当前修改的监听器参数配置信息，从节点属性窗口传递。
 * @param eventType 当前修改的事件完整参数配置信息。
 *    格式为：{id: 唯一标识, name: 事件配置显示名, type: 事件类型, typeName: 事件显示名, eventConfig: 事件参数配置,
 *	             listenerId: 监听器类型, listenerName: 监听器显示名, listenerConfig: 监听器参数配置}
 * @param nodeObject 当前配置的节点在流程图中的JS对象
 * @param context 参数配置需要的相关上下文信息。
 *    格式为：{selectedId: 当前选中的事件类型, nodeEvents: 当前节点能选择的事件类型集, modelName: 当前模块的modelName}
 */
function initValue(config, eventType, nodeObject, context){
	// 监听器配置参数
	var listenerConfig = (config == null) ? null : $.parseJSON(config);
	if (listenerConfig == null) {
		SetStartCountTypeState('1');
		return;
	}
	
	initModelName(listenerConfig);
	initParams(listenerConfig);
};

function initModelName(json) {
	document.getElementsByName('modelName')[0].value = json.seriesProcess.modelName;
	document.getElementsByName('dictBean')[0].value = json.seriesProcess.dictBean;
	document.getElementsByName('templateId')[0].value = json.seriesProcess.templateId;
	document.getElementsByName('templateName')[0].value = json.seriesProcess.templateName;
	document.getElementsByName('createParam')[0].value = json.seriesProcess.createParam;

	setRadio(document.getElementsByName('startIdentityType'), json.startIdentity.type);
	setRadio(document.getElementsByName('startCountType'), json.startCountType);
	setRadio(document.getElementsByName('skipDraftNode'), json.skipDraftNode);

	if (json.startIdentity.type == 3) {
		document.getElementsByName('addressValueId')[0].value = json.startIdentity.values;
		document.getElementsByName('addressValueName')[0].value = json.startIdentity.names;
	} else if (json.startIdentity.type == 4) {
		document.getElementsByName('formulaValueId')[0].value = json.startIdentity.values;
		document.getElementsByName('formulaValueName')[0].value = json.startIdentity.names;
	}
	var div = document.getElementById("IdentityType_" + json.startIdentity.type);
	if (div != null) {
		div.style.display = "";
	}
	SetStartCountTypeState(json.startIdentity.type);
}
function initParams(json) {
	var params = json.startParamenters;
	for (var i = 0; i < params.length; i ++) {
		var row = params[i];
		var p = [];//(row.name.notNull == 'true' || row.name.notNull == true) ? [null, null, null, ""] : [];
		DocList_AddRow("paramTable", p,
				{
					"startParamenters.name.type" : row.name.type, 
					"startParamenters.name.value" : row.name.value,
					"startParamenters.name.text" : row.name.text,
					"startParamenters.name.notNull" : row.name.notNull,
					"startParamenters.expression.value" : row.expression.value,
					"startParamenters.expression.text" : row.expression.text
				});
	}
}

/**
 * 功能：页面提交时校验方法（标准接口）
 */
function checkValue() {
	if (document.getElementsByName('modelName')[0].value == '') {
		alert('<kmss:message key="FlowChartObject.Lang.Node.seriesprocessAlertModelNameNotNull" bundle="sys-lbpmservice-event-seriesprocess" />');
		return false;
	}
	var startIdentityType = getRadioValue(document.getElementsByName('startIdentityType'));
	if (startIdentityType == '3' && (document.getElementsByName('addressValueId')[0].value == '')) {
		alert('<kmss:message key="FlowChartObject.Lang.Node.seriesprocessAlertAddressValueIdNotNull" bundle="sys-lbpmservice-event-seriesprocess" />');
		return false;
	}
	else if (startIdentityType == '4' && (document.getElementsByName('formulaValueId')[0].value == '')) {
		alert('<kmss:message key="FlowChartObject.Lang.Node.seriesprocessAlertFormulaValueIdNotNull" bundle="sys-lbpmservice-event-seriesprocess" />');
		return false;
	}
	var expressionValues= document.getElementsByName("startParamenters.expression.value");
	for (var i = 0; i < expressionValues.length; i ++) {
		var value = expressionValues[i].value;
		if (value == '') {
			alert('<kmss:message key="FlowChartObject.Lang.Node.seriesprocessAlertExpressionValueNotNull" bundle="sys-lbpmservice-event-seriesprocess" />');
			return false;
		}
	}
	return true;
};


function getRadioValue(radios) {
	for (var i = 0; i < radios.length; i ++) {
		var radio = radios[i];
		if (radio.checked) {
			return radio.value;
		}
	}
}
function getCheckboxValue(checkboxs) {
	var rtn = [];
	for (var i = 0; i < checkboxs.length; i ++) {
		if (checkboxs[i].checked) {
			rtn.push(checkboxs[i].value);
		}
	}
	return rtn;
}

function setRadio(radios, value) {
	value = value.toString();
	for (var i = 0; i < radios.length; i ++) {
		var radio = radios[i];
		if (radio.value == value) {
			radio.checked = true;
			break;
		}
	}
}
function setCheckbox(checkboxs, values) {
	for (var i = 0; i < values.length; i ++) {
		var value = values[i];
		setRadio(checkboxs, value);
	}
}

/**
 * 功能：页面提交时返回值（标准接口）
 */
function returnValue() {
	var getNameValue = function(name) {
		return (document.getElementsByName(name)[0].value);
	}
	var json = {};
	json.seriesProcess = {};
	json.seriesProcess.modelName = getNameValue('modelName');
	json.seriesProcess.dictBean = getNameValue('dictBean');
	json.seriesProcess.templateId = getNameValue('templateId');
	json.seriesProcess.templateName = getNameValue('templateName');
	json.seriesProcess.createParam = getNameValue('createParam');
	
	var startIdentityType = getRadioValue(document.getElementsByName('startIdentityType'));
	var startIdentityNames = ["", ""];
	if (startIdentityType == "3") {
		startIdentityNames = [getNameValue("addressValueId"), getNameValue("addressValueName")];
	} else if (startIdentityType == "4") {
		startIdentityNames = [getNameValue("formulaValueId"), getNameValue("formulaValueName")];
	}
	
	json.startIdentity = {};
	json.startIdentity.type = startIdentityType;
	json.startIdentity.names = startIdentityNames[1];
	json.startIdentity.values = startIdentityNames[0];
	
	json.startCountType = getRadioValue(document.getElementsByName('startCountType'));
	
	json.skipDraftNode = document.getElementsByName('skipDraftNode')[0].checked + "";
	
	json.startParamenters = getParamentersJson();
	
	return JSON.stringify(json);
};

function getParamentersJson() {
	var rtn = [];
	var values = document.getElementsByName("startParamenters.name.value");
	var texts = document.getElementsByName("startParamenters.name.text");
	var types = document.getElementsByName("startParamenters.name.type");
	var notNulls = document.getElementsByName("startParamenters.name.notNull");
	var expressionValues= document.getElementsByName("startParamenters.expression.value");
	var expressionTexts= document.getElementsByName("startParamenters.expression.text");
	for (var i = 0; i < values.length; i ++) {
		var obj = {};
		obj.name = {};
		obj.name.text = texts[i].value;
		obj.name.value = values[i].value;
		obj.name.type = types[i].value;
		obj.name.notNull = notNulls[i].value;
		
		obj.expression = {};
		obj.expression.text = expressionTexts[i].value;
		obj.expression.value = expressionValues[i].value;
		
		rtn.push(obj);
	}
	return rtn;
}

function initView() {
	// 查看模式隐藏操作列
	var obj = document.getElementById("paramTable");
	for(var i=0; i<obj.rows.length; i++){
		obj.rows[i].cells[3].style.display = "none";
	}
}
</script>
<center>
<table id="List_ViewTable" class="tb_normal" width="100%">
	<tr>
		<td width="15%"><kmss:message key="FlowChartObject.Lang.Node.seriesprocess" bundle="sys-lbpmservice-event-seriesprocess" /></td>
		<td>
			<input type="hidden" name="templateId">
			<input type="text" name="templateName" readOnly style="width:90%" class="inputSgl">
			<a href="javascript:void(0);" onclick="selectSubFlow();"><kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
			<span class="txtstrong">*</span>
			<input type="hidden" name="modelName">
			<input type="hidden" name="createParam">
			<input type="hidden" name="dictBean">
		</td>
	</tr>
	<tr>
		<td width="15%"><kmss:message key="FlowChartObject.Lang.Node.seriesprocessStartIdentityType" bundle="sys-lbpmservice-event-seriesprocess" /></td>
		<td>
			<label><input type="radio" name="startIdentityType" onclick="changeIdentityType(this);" value="1" checked><kmss:message key="FlowChartObject.Lang.Node.seriesprocessStartIdentityTypeDraftsman" bundle="sys-lbpmservice-event-seriesprocess" /></label>
			<label><input type="radio" name="startIdentityType" onclick="changeIdentityType(this);" value="3"><kmss:message key="FlowChartObject.Lang.Node.seriesprocessStartIdentityTypeAddress" bundle="sys-lbpmservice-event-seriesprocess" /></label>
			<label><input type="radio" name="startIdentityType" onclick="changeIdentityType(this);" value="4"><kmss:message key="FlowChartObject.Lang.Node.seriesprocessStartIdentityTypeFormula" bundle="sys-lbpmservice-event-seriesprocess" /></label>
			<div id="IdentityType_3" style="display:none;">
				<input type="hidden" name="addressValueId">
				<input type="text" name="addressValueName" style="width:85%" class="inputSgl">
				<a href="javascript:void(0);" 
					onclick="Dialog_Address(true, 'addressValueId','addressValueName', ';', ORG_TYPE_PERSON, null, null, null, true);">
					<kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
				<span class="txtstrong">*</span>
			</div>
			<div id="IdentityType_4" style="display:none;">
				<input type="hidden" name="formulaValueId">
				<input type="text" name="formulaValueName" style="width:85%" class="inputSgl">
				<a href="javascript:void(0);" 
					onclick="Formula_Dialog('formulaValueId','formulaValueName', 
						FlowChartObject.FormFieldList, 'com.landray.kmss.sys.organization.model.SysOrgElement[]'
						, null, 'com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction;com.landray.kmss.sys.lbpmservice.formula.LbpmSubProcessFunction', FlowChartObject.ModelName);">
						<kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
				<span class="txtstrong">*</span>
				<p style="margin-top: 3px;padding-top: 0px;"><kmss:message key="FlowChartObject.Lang.Node.seriesprocessStartIdentityTypeFormulaInfo" bundle="sys-lbpmservice-event-seriesprocess" /></p>
			</div>
		</td>
	</tr>
	<tr>
		<td width="15%"><kmss:message key="FlowChartObject.Lang.Node.seriesprocessStartCountType" bundle="sys-lbpmservice-event-seriesprocess" /></td>
		<td>
			<label><input type="radio" name="startCountType" value="1" checked><kmss:message key="FlowChartObject.Lang.Node.seriesprocessStartCountTypeOne" bundle="sys-lbpmservice-event-seriesprocess" /></label>
			<label><input type="radio" name="startCountType" value="2"><kmss:message key="FlowChartObject.Lang.Node.seriesprocessStartCountTypeMulti" bundle="sys-lbpmservice-event-seriesprocess" /></label>
		</td>
	</tr>
	<tr>
		<td width="15%"><kmss:message key="FlowChartObject.Lang.Node.seriesprocessStartRule" bundle="sys-lbpmservice-event-seriesprocess" /></td>
		<td>
			<label><input type="checkbox" name="skipDraftNode" value="true"><kmss:message key="FlowChartObject.Lang.Node.seriesprocessStartRuleSkipDraftNode" bundle="sys-lbpmservice-event-seriesprocess" /></label>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<div class="txtstrong">
			<kmss:message key="FlowChartObject.Lang.Node.seriesprocessParameterIntroduce" bundle="sys-lbpmservice-event-seriesprocess" />
			</div>
			<table class="tb_normal" id="paramTable" style="width:100%">
				<tr>
					<td style="width:8%"><kmss:message key="FlowChartObject.Lang.Node.seriesprocessStartParamNo" bundle="sys-lbpmservice-event-seriesprocess" /></td>
					<td style="width:30%"><kmss:message key="FlowChartObject.Lang.Node.seriesprocessStartParamName" bundle="sys-lbpmservice-event-seriesprocess" /></td>
					<td style="width:44%"><kmss:message key="FlowChartObject.Lang.Node.seriesprocessStartParamExpression" bundle="sys-lbpmservice-event-seriesprocess" /></td>
					<td style="width:18%"><a href="javascript:void(0);" onclick="addSubProcessParamenter(this);"><kmss:message key="FlowChartObject.Lang.Node.seriesprocessStartParamAdd" bundle="sys-lbpmservice-event-seriesprocess" /></a></td>
				</tr>
				<tr KMSS_IsReferRow="1" style="display:none">
					<td KMSS_IsRowIndex="1"></td>
					<td>
						<input type="hidden" name="startParamenters.name.notNull">
						<input type="hidden" name="startParamenters.name.value">
						<input type="hidden" name="startParamenters.name.type">
						<input type="text" name="startParamenters.name.text" readonly="readonly" style="width:90%" class="inputSgl">
					</td>
					<td>
						<input type="hidden" name="startParamenters.expression.value">
						<input type="text" name="startParamenters.expression.text" readonly="readonly" style="width:80%" class="inputSgl">
						<nobr><a href="javascript:void(0);"  onclick="showParamenterFormulaDialog(this);"><kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
						<span class="txtstrong">*</span></nobr>
					</td>
					<td>
						<a href="javascript:void(0);" onclick="DocList_DeleteRow();"><kmss:message key="FlowChartObject.Lang.Node.seriesprocessStartParamDel" bundle="sys-lbpmservice-event-seriesprocess" /></a>
						<a href="javascript:void(0);" onclick="DocList_MoveRow(-1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveUp" bundle="sys-lbpmservice" /></a>
						<a href="javascript:void(0);" onclick="DocList_MoveRow(1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveDown" bundle="sys-lbpmservice" /></a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</center>
<script>
//---- 串联流程选择对话框
function addSubProcessParamenter(table) {
	while (table.tagName != 'TABLE') {
		table = table.parentNode;
	}
	var modelName = document.getElementsByName('modelName')[0];
	var dictBean = document.getElementsByName('dictBean')[0];
	var templateId = document.getElementsByName('templateId')[0];
	var url = "lbpmSeriesProcessDictService&modelName=" + modelName.value + "&templateId=" + templateId.value;
	if (dictBean != null && dictBean.value != '') {
		url = dictBean.value;
	}
	
	Dialog_List(false, null, null, ";", url, function(data) {
		if (data != null) {
			var tr = DocList_AddRow(table);
			var inputs = tr.getElementsByTagName("input");
			var value = getElementsByName(inputs, "startParamenters.name.value");
			var text = getElementsByName(inputs, "startParamenters.name.text");
			var type = getElementsByName(inputs, "startParamenters.name.type");
			var notNull = getElementsByName(inputs, "startParamenters.name.notNull");
			if (value != null && text != null) {
				var rows = data.GetHashMapArray();
				value.value = rows[0].id;
				text.value = rows[0].name;
				type.value = rows[0].type;
				notNull.value = rows[0].notNull;
			}
		}
	});
}

//参数公式对话框
function showParamenterFormulaDialog(tr) {
	while (tr.tagName != 'TR') {
		tr = tr.parentNode;
	}
	var inputs = tr.getElementsByTagName("input");
	var value = getElementsByName(inputs, "startParamenters.expression.value");
	var text = getElementsByName(inputs, "startParamenters.expression.text");
	var type = getElementsByName(inputs, "startParamenters.name.type");
	if(type.value == "Attachment" || type.value == "Attachment[]") {
		// 表单附件
		var data = new KMSSData();
		for(var i=0; i<FlowChartObject.FormFieldList.length; i++){
			var field = FlowChartObject.FormFieldList[i];
			if(!(field.type == "Attachment"  || field.type == "Attachment[]"))
				continue;
			var pp={name:field.name,label:field.label};
			if(field.type == "Attachment[]"){
				pp.name="$"+field.name+"$";
				pp.label="$"+field.label+"$";
			}
			data.AddHashMap({id:pp.name, name:pp.label});
		}
		var dialog = new KMSSDialog(false, true);
		dialog.winTitle = FlowChartObject.Lang.dialogTitle;
		dialog.AddDefaultOption(data);
		dialog.BindingField(value, text, ";");
		dialog.Show();
	} else {
		Formula_Dialog(value, text,
				FlowChartObject.FormFieldList, type.value, function(data) {
			
		});
	}
}

function loadNotNullProperties(modelName, templateId, dictBean) {
	var url = "lbpmSeriesProcessDictService&modelName=" + modelName + "&templateId=" + templateId;
	if (dictBean != null && dictBean != "") {
		url = dictBean;
	}
	var kmssData = new KMSSData();
	kmssData.SendToBean(url , function(data) {
		var rows = data.GetHashMapArray();
		for (var i = 0; i < rows.length; i ++) {
			var row = rows[i];
			if (row.id != "docSubject") {
				continue;
			}
			DocList_AddRow("paramTable",
				[null, null, null, ""],
				{
					"startParamenters.name.type" : row.type, 
					"startParamenters.name.value" : row.id,
					"startParamenters.name.text" : row.name,
					"startParamenters.name.notNull" : row.notNull
				});
		}
	});
}
//串联流程选择框
function selectSubFlow() {
	var dialog = new KMSSDialog(false, false);
	var treeTitle = '<kmss:message key="FlowChartObject.Lang.Node.seriesprocess" bundle="sys-lbpmservice-event-seriesprocess" />';
	var node = dialog.CreateTree(treeTitle);
	dialog.winTitle = treeTitle;
	var fdId = null;
	try {
		if (FlowChartObject.IsTemplate) { // just for template
			var dialogObject=window.dialogArguments?window.dialogArguments:(window.parent.dialogArguments?window.parent.dialogArguments:null);
			if(!dialogObject){
				dialogObject=opener?opener.Com_Parameter.Dialog:(parent.opener?parent.opener.Com_Parameter.Dialog:null);
			}
			var url = dialogObject.Window.parent.parent.location.href;
			fdId = Com_GetUrlParameter(url, 'fdId');
		}
	} catch (e) {}
	node.AppendBeanData("lbpmSeriesProcessDialogService", null, null, false, fdId);
	dialog.notNull = true;
	dialog.BindingField('templateId', 'templateName');
	dialog.SetAfterShow(function(rtnData){
		if(rtnData!=null){
			var node = Tree_GetNodeByID(this.tree.treeRoot, this.rtnData.GetHashMapArray()[0].nodeId);
			var pNode = node;
			for(;  pNode.value.indexOf("&") == -1; pNode = pNode.parent){
				
			}
			// 回写全名
			var path = Tree_GetNodePath(node,"/",node.treeView.treeRoot);
			document.getElementsByName('templateName')[0].value = path;
			
			var modelName = document.getElementsByName('modelName')[0];
			var dictBean = document.getElementsByName('dictBean')[0];
			var createParam = document.getElementsByName('createParam')[0];

			
			modelName.value = findValueByName(pNode.value, 'MODEL_NAME');
			createParam.value = replaceParam(pNode.value, node.value);

			var dictBeanValue = findValueByName(pNode.value, 'DICT_BEAN');

			dictBeanValue = decodeURIComponent(dictBeanValue);
			dictBeanValue = replaceModelName(dictBeanValue, modelName.value);
			dictBeanValue = replaceCateid(dictBeanValue, node.value);
			dictBean.value = dictBeanValue;

			clearParamTable();
			loadNotNullProperties(modelName.value, node.value, dictBean.value);
		}
	});
	dialog.Show();
}

function changeIdentityType(dom) {
	var td = dom;
	while (td.tagName != 'TD') {
		td = td.parentNode;
	}
	var value = dom.value;
	var divs = td.getElementsByTagName('DIV');
	for (var i = 0; i < divs.length; i ++) {
		var div = divs[i];
		if ("IdentityType_" + value == div.id) {
			div.style.display = "";
		} else {
			div.style.display = "none";
		}
	}
	SetStartCountTypeState(value);
}

function SetStartCountTypeState(identityType) {
	var startCountTypes = document.getElementsByName('startCountType');
	if (identityType == '1') {
		for (var i = 0; i < startCountTypes.length; i ++) {
			var startCountType = startCountTypes[i];
			if (startCountType.value == '1') {
				if (!startCountType.checked)
					startCountType.checked = true;
			}
			else if (!startCountType.disabled) {
				startCountType.disabled = true;
			}
		}
	}
	else {
		for (var i = 0; i < startCountTypes.length; i ++) {
			var startCountType = startCountTypes[i];
			if (startCountType.disabled) {
				startCountType.disabled = false;
			}
		}
	}
}

function clearParamTable() {
	var table = document.getElementById("paramTable");
	var rows = table.rows;
	var l = rows.length - 1;
	for (var i = l; i > 0; i --) {
		//table.deleteRow(i);
		DocList_DeleteRow(rows[i]);
	}
}

function findValueByName(value, name) {
	var vs = value.split("&"), i, v;
	for (i = 0; i < vs.length; i ++) {
		v = vs[i].split('=');
		if (name == v[0]) {
			return v[1];
		}
	}
	return '';
}
function replaceParam(url, cateid) {
	var re = /!\{cateid\}/gi;
	url = url.replace(/!\{cateid\}/gi, cateid);
	return url.substring(url.indexOf('&') + 1, url.length);
}
function replaceModelName(url, modelName) {
	var re = /!\{modelName\}/gi;
	url = url.replace(/!\{modelName\}/gi, modelName);
	return url;
}
function replaceCateid(url, cateid) {

	if(cateid.indexOf("@")){
		//兼容业务建模的新建参数
		var appIdAndFlowId = cateid.split("@");
		if(appIdAndFlowId.length==2){
			var app = appIdAndFlowId[0].split("=");
			if(app.length==2){
				cateid = app[1];
				var flow = appIdAndFlowId[1].split("=");
				if(flow.length==2){
					cateid +="&flowId=" + flow[1];
				}
			}
		}
	}

	var re = /!\{cateid\}/gi;
	url = url.replace(/!\{cateid\}/gi, cateid);
	return url;
}
function getElementsByName(list, name) {
	for (var i = 0; i < list.length; i ++) {
		if (list[i].name == name) {
			return list[i];
		}
	}
	return null;
}

function initialPage() {
	try {
		var arguObj = document.getElementById("List_ViewTable");
		if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
			var height = arguObj.offsetHeight + 0;
			if(height>0)
				window.frameElement.style.height = height + "px";
		}
		setTimeout(initialPage, 200);
	} catch(e) {
	}
}
Com_AddEventListener(window, "load", initialPage);
</script>
</template:replace>
</template:include>