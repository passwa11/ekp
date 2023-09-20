<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextareaGroupTagNew"%>
<script>Com_IncludeFile("formula.js");</script>

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

<table width="590px" id="Label_Tabel">
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Base" bundle="sys-lbpm-engine" />">
		<td>
		<table width="100%" class="tb_normal">
			<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8" />
			<tr>
				<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.subprocessTitle" bundle="sys-lbpmservice-node-subprocess" /></td>
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
				<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartIdentityType" bundle="sys-lbpmservice-node-subprocess" /></td>
				<td>
					<label><input type="radio" name="startIdentityType" onclick="changeIdentityType(this);" value="1" checked><kmss:message key="FlowChartObject.Lang.Node.subprocessStartIdentityTypeDraftsman" bundle="sys-lbpmservice-node-subprocess" /></label>
					<!--label><input type="radio" name="startIdentityType" onclick="changeIdentityType(this);" value="2">前一节点审批人</label-->
					<label><input type="radio" name="startIdentityType" onclick="changeIdentityType(this);" value="3"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartIdentityTypeAddress" bundle="sys-lbpmservice-node-subprocess" /></label>
					<label><input type="radio" name="startIdentityType" onclick="changeIdentityType(this);" value="4"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartIdentityTypeFormula" bundle="sys-lbpmservice-node-subprocess" /></label>
					<label><input type="radio" name="startIdentityType" onclick="changeIdentityType(this);" value="5"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartIdentityTypeDetails" bundle="sys-lbpmservice-node-subprocess" /></label>
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
						<p style="margin-top: 3px;padding-top: 0px;"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartIdentityTypeFormulaInfo" bundle="sys-lbpmservice-node-subprocess" /></p>
					</div>
					<div id="IdentityType_5" style="display:none;">
						<kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice-node-subprocess" />
						<select id="selectAddress">
							<option value=""><kmss:message key="FlowChartObject.Lang.Node.pleaseSelect" bundle="sys-lbpmservice-node-subprocess" /></option>
						</select> <span class="txtstrong">*</span>
						<span class="txtstrong">
					<kmss:message key="FlowChartObject.Lang.Node.selectAddressInfo" bundle="sys-lbpmservice-node-subprocess" />
					</span>
					</div>
				</td>
			</tr>
			<tr>
				<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartCountType" bundle="sys-lbpmservice-node-subprocess" /></td>
				<td>
					<label><input type="radio" name="startCountType" value="1" onclick="startCountTypeChange('1');"  checked><kmss:message key="FlowChartObject.Lang.Node.subprocessStartCountTypeOne" bundle="sys-lbpmservice-node-subprocess" /></label>
					<label><input type="radio" name="startCountType" value="2" onclick="startCountTypeChange('2');"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartCountTypeMulti" bundle="sys-lbpmservice-node-subprocess" /></label>
					<label><input type="radio" name="startCountType" value="3" onclick="startCountTypeChange('3');"><kmss:message key="FlowChartObject.Lang.Node.startByDetailedRow" bundle="sys-lbpmservice-node-subprocess" /></label>
					<br>
					<div id="div_startCountType_selectDetails" style="display: none;">
						<kmss:message key="FlowChartObject.Lang.Node.selectDetailsId" bundle="sys-lbpmservice-node-subprocess" />
						<select id="selectDetailsId" onchange="clearRowDetailParamTable();loadDetailSelectAddress();">
							<option value=""><kmss:message key="FlowChartObject.Lang.Node.pleaseSelect" bundle="sys-lbpmservice-node-subprocess" /></option>
						</select>
						<span class="txtstrong">*</span>
						<table class="tb_normal" id="rowParamTable" style="width:100%">
						<tr>
							<td style="width:10%"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartParamNo" bundle="sys-lbpmservice-node-subprocess" /></td>
							<td style="width:30%"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartParamName" bundle="sys-lbpmservice-node-subprocess" /></td>
							<td style="width:38%"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartParamExpression" bundle="sys-lbpmservice-node-subprocess" /></td>
							<td style="width:22%"><a class="btn_txt" href="javascript:void(0);" onclick="addSubProcessParamenterCheck(this);"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartParamAdd" bundle="sys-lbpmservice-node-subprocess" /></a></td>
						</tr>
						<tr KMSS_IsReferRow="1" style="display:none">
							<td KMSS_IsRowIndex="1"></td>
							<td>
								<input type="hidden" name="rowParamTableParamenters.name.notNull">
								<input type="hidden" name="rowParamTableParamenters.name.value">
								<input type="hidden" name="rowParamTableParamenters.name.type">
								<input type="text" name="rowParamTableParamenters.name.text" readonly="readonly" style="width:90%" class="inputSgl">
							</td>
							<td>
								<input type="hidden" name="rowParamTableParamenters.expression.value">
								<input type="text" name="rowParamTableParamenters.expression.text" readonly="readonly" style="width:70%" class="inputSgl">
								<a href="javascript:void(0);"  onclick="showParamenterFormulaDialog(this,true);"><kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
								<span class="txtstrong">*</span>
							</td>
							<td>
								<a href="javascript:void(0);" onclick="DocList_DeleteRow();"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartParamDel" bundle="sys-lbpmservice-node-subprocess" /></a>
								<a href="javascript:void(0);" onclick="DocList_MoveRow(-1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveUp" bundle="sys-lbpmservice" /></a>
								<a href="javascript:void(0);" onclick="DocList_MoveRow(1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveDown" bundle="sys-lbpmservice" /></a>
							</td>
						</tr>
					</table>
						
					</div>
					
				</td>
			</tr>
			<tr>
				<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartRule" bundle="sys-lbpmservice-node-subprocess" /></td>
				<td>
					<label><input type="checkbox" name="skipDraftNode" value="true"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartRuleSkipDraftNode" bundle="sys-lbpmservice-node-subprocess" /></label>
					<label><input type="checkbox" name="wf_synchronizeRight" value="true"><kmss:message key="FlowChartObject.Lang.Node.subprocessSynchronizeRight" bundle="sys-lbpmservice-node-subprocess" /></label>
					<label><input type="checkbox" name="wf_distributeParentNote" value="true" onclick="showDistributeParent(this)"><kmss:message key="FlowChartObject.Lang.Node.subprocessDistributeParentNote" bundle="sys-lbpmservice-node-subprocess" /></label>
				</td>
			</tr>
			<tr id="distributeParentNote" style="display: none">
				<td width="100px">
					<kmss:message key="FlowChartObject.Lang.Node.subprocessDistributeNoteNode" bundle="sys-lbpmservice-node-subprocess" />
					<span class="txtstrong">*</span>
				</td>
				<td>
					<input name="wf_distributeParentDisplayName" class="inputsgl" style="width:400px" placeholder="<kmss:message key="FlowChartObject.Lang.Node.subprocessDistributeNodeName" bundle="sys-lbpmservice-node-subprocess" />">
					<input type="hidden" name="wf_distributeParentDisplayId">
					<span id="SPAN_OptSelectType1">
						<a href="#" onclick="Formula_Dialog('wf_distributeParentDisplayId','wf_distributeParentDisplayName',
								FlowChartObject.FormFieldList, 'com.landray.kmss.sys.organization.model.SysOrgElement[]'
								, distributeCallback, 'com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction;com.landray.kmss.sys.lbpmservice.formula.LbpmSubProcessFunction', FlowChartObject.ModelName);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
					</span> <br> <br>
					<input type="hidden" name="wf_distributeNoteNodeIds">
					<textarea name="wf_distributeNoteNodeNames" style="width:100%;height:50px" readonly></textarea>
					<a href="#" onclick="selectNodes('wf_distributeNoteNodeIds', 'wf_distributeNoteNodeNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="txtstrong">
					<kmss:message key="FlowChartObject.Lang.Node.subprocessParameterIntroduce" bundle="sys-lbpmservice-node-subprocess" />
					</div>
					<table class="tb_normal" id="paramTable" style="width:100%">
						<tr>
							<td style="width:10%"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartParamNo" bundle="sys-lbpmservice-node-subprocess" /></td>
							<td style="width:30%"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartParamName" bundle="sys-lbpmservice-node-subprocess" /></td>
							<td style="width:40%"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartParamExpression" bundle="sys-lbpmservice-node-subprocess" /></td>
							<td style="width:30%"><a class="btn_txt" href="javascript:void(0);" onclick="addSubProcessParamenter(this);"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartParamAdd" bundle="sys-lbpmservice-node-subprocess" /></a></td>
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
								<a href="javascript:void(0);"  onclick="showParamenterFormulaDialog(this);"><kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
								<span class="txtstrong">*</span>
							</td>
							<td>
								<a href="javascript:void(0);" onclick="DocList_DeleteRow();"><kmss:message key="FlowChartObject.Lang.Node.subprocessStartParamDel" bundle="sys-lbpmservice-node-subprocess" /></a>
								<a href="javascript:void(0);" onclick="DocList_MoveRow(-1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveUp" bundle="sys-lbpmservice" /></a>
								<a href="javascript:void(0);" onclick="DocList_MoveRow(1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveDown" bundle="sys-lbpmservice" /></a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.subprocessExceptionConfig" bundle="sys-lbpmservice-node-subprocess" /></td>
				<td>
					<label><input type="checkbox" name="onErrorNotify" value="2"><kmss:message key="FlowChartObject.Lang.Node.subprocessExceptionNotifyPrivilege" bundle="sys-lbpmservice-node-subprocess" /></label>
					<label><input type="checkbox" name="onErrorNotify" value="1"><kmss:message key="FlowChartObject.Lang.Node.subprocessExceptionNotifyDraftsman" bundle="sys-lbpmservice-node-subprocess" /></label>
				</td>
			</tr>
			<tr>
				<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.description" bundle="sys-lbpm-engine" /></td>
				<td>
					<%-- <textarea name="wf_description" style="width:100%"></textarea>
					<xlang:lbpmlangArea property="_wf_description" style="width:100%" langs=""/>
					<br> --%>
					<c:if test="${!isLangSuportEnabled }">
						<textarea name="wf_description" style="width:100%"></textarea>
						<br>
					</c:if>
					<c:if test="${isLangSuportEnabled }">
						<xlang:lbpmlangAreaNew property="_wf_description" alias="wf_description" style="width:100%" langs=""/>
					</c:if>
					<kmss:message key="FlowChartObject.Lang.Node.imgLink" bundle="sys-lbpm-engine" />
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Advance" bundle="sys-lbpm-engine" />">
		<td>
			<table class="tb_normal" width="100%">
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.dayOfAbandonSub" bundle="sys-lbpmservice" /></td>
					<td colspan="2">
						<kmss:message key="FlowChartObject.Lang.Node.dayOfAbandonSub1" bundle="sys-lbpmservice" />
						<input name="wf_dayOfAbandonSub" class="inputsgl" value="0" size="3" style="text-align:center"><kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
						<input name="wf_hourOfAbandonSub" class="inputsgl" value="0" size="3" style="text-align:center"><kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
						<input name="wf_minuteOfAbandonSub" class="inputsgl" value="0" size="3" style="text-align:center"><kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
						<kmss:message key="FlowChartObject.Lang.Node.dayOfAbandonSub2" bundle="sys-lbpmservice" />
						<br><kmss:message key="FlowChartObject.Lang.Node.dayOfHandleHelp" bundle="sys-lbpmservice" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Event" bundle="sys-lbpm-engine" />">
		<td>
		<c:import url="/sys/lbpm/flowchart/page/node_event_attribute.jsp" charEncoding="UTF-8" />
		</td>
	</tr>
</table>


<script>
DocList_Info.push("paramTable");
DocList_Info.push("rowParamTable");
function formatJson(value) {
	// #103922 将回车换行去掉
	return value.replace(/"/ig,'\\"').replace(/[\r\n]/g,"");
}

AttributeObject.Init.AllModeFuns.push(initSubProcessInfo);
$(function(){
	if(AttributeObject.NodeData && typeof AttributeObject.NodeData.distributeParentNote !="undefined" && AttributeObject.NodeData.distributeParentNote ==="true"){
		$('#distributeParentNote').show();
	}
})

function nodeDataCheck(data){
	if (document.getElementsByName('modelName')[0].value == '') {
		alert('<kmss:message key="FlowChartObject.Lang.Node.subprocessAlertModelNameNotNull" bundle="sys-lbpmservice-node-subprocess" />');
		return false;
	}
	var startIdentityType = getRadioValue(document.getElementsByName('startIdentityType'));
	if (startIdentityType == '3' && (document.getElementsByName('addressValueId')[0].value == '')) {
		alert('<kmss:message key="FlowChartObject.Lang.Node.subprocessAlertAddressValueIdNotNull" bundle="sys-lbpmservice-node-subprocess" />');
		return false;
	}
	else if (startIdentityType == '4' && (document.getElementsByName('formulaValueId')[0].value == '')) {
		alert('<kmss:message key="FlowChartObject.Lang.Node.subprocessAlertFormulaValueIdNotNull" bundle="sys-lbpmservice-node-subprocess" />');
		return false;
	}
	else if(startIdentityType =="5"){
		var getDomValue=function (domId){
			return document.getElementById(domId).value
		};
		var address=getDomValue("selectAddress");
		if(address==""){
			alert('<kmss:message key="FlowChartObject.Lang.Node.pleaseSelectDetailsAndAddress" bundle="sys-lbpmservice-node-subprocess" />');
			return false;
		}
	}
	var expressionValues= document.getElementsByName("startParamenters.expression.value");
	for (var i = 0; i < expressionValues.length; i ++) {
		var value = expressionValues[i].value;
		if (value == '') {
			alert('<kmss:message key="FlowChartObject.Lang.Node.subprocessAlertExpressionValueNotNull" bundle="sys-lbpmservice-node-subprocess" />');
			return false;
		}
	}
	var startCountType = getRadioValue(document.getElementsByName('startCountType'));
	if(startCountType=="3"){
		var selectDetails=$("#selectDetailsId").val();
		if(selectDetails==""){
			alert('<kmss:message key="FlowChartObject.Lang.Node.pleaseSelectDetails" bundle="sys-lbpmservice-node-subprocess" />');
			return false;
		}
	}
	var distributeParentDisplayName = $("[name='wf_distributeParentDisplayName']");
	var distributeNoteNodeNames = $("[name='wf_distributeNoteNodeNames']");
	var distributeChecked= $('input[name="wf_distributeParentNote"]').prop('checked');
	if(distributeChecked && (distributeParentDisplayName.val() === '' || distributeNoteNodeNames.val() === '')){
		alert("<kmss:message key="FlowChartObject.Lang.Node.subprocessDistributeNoteNodeValueNotNull" bundle="sys-lbpmservice-node-subprocess" />");
		return false;
	}
	
	if (AttributeObject.NodeData['configContent'] != null) {
		var json = eval("(" + AttributeObject.NodeData['configContent'] + ")");
		var startCountType = getRadioValue(document.getElementsByName('startCountType'));
		var templateId = document.getElementsByName('templateId')[0].value;
		if (json.startCountType != startCountType || json.subProcess.templateId != templateId) {
			var nodes = getRelationRecoverNode();
			if (nodes != null && nodes.length > 0) {
				var alertNames = [];
				for (var i = 0; i < nodes.length; i ++) {
					var node = nodes[i];
					alertNames.push(node.Data.id + "." + node.Data.name);
				}
				node.NeedReConfig = true;
				alert(FlowChartObject.Lang.GetMessage(FlowChartObject.Lang.Node.subprocessAlertConfigRelationNode, alertNames.join("; ")));
			}
		}
	}
	return true;
}
AttributeObject.CheckDataFuns.push(nodeDataCheck);


function getRelationRecoverNode() {
	var rtn = [];
	var nodes = FlowChartObject.Nodes.all;
	for (var i = 0; i < nodes.length; i ++) {
		var node = nodes[i];
		if (node.Type == "recoverSubProcessNode" && node.Data['configContent'] != null) {
			var json = (new Function("return ("+ node.Data['configContent'] + ")"))();
			if (json.subProcessNode == AttributeObject.NodeData.id) {
				rtn.push(node);
			}
		}
	}
	return rtn;
}

function writeJSON() {
	var getNameValue = function(name) {
		return (formatJson(document.getElementsByName(name)[0].value));
	}
	var config = [];
	config.push("{ \"subProcess\":{\"modelName\":");
	config.push("\"" + getNameValue('modelName') + "\"");
	config.push(",\"dictBean\":");
	config.push("\"" + getNameValue('dictBean') + "\"");
	config.push(",\"templateId\":");
	config.push("\"" + getNameValue('templateId') + "\"");
	config.push(",\"templateName\":");
	config.push("\"" + getNameValue('templateName') + "\"");
	config.push(",\"createParam\":");
	config.push("\"" + getNameValue('createParam') + "\"");
	config.push("}");
	//启动人身份
	var startIdentityType = getRadioValue(document.getElementsByName('startIdentityType'));
	//启动选项
	var startCountType = getRadioValue(document.getElementsByName('startCountType'));
	
	var startIdentityNames = ["", ""];
	if (startIdentityType == "3") {
		startIdentityNames = [getNameValue("addressValueId"), getNameValue("addressValueName")];
	} else if (startIdentityType == "4") {
		startIdentityNames = [getNameValue("formulaValueId"), getNameValue("formulaValueName")];
	}
	config.push(",\"startIdentity\":{\"type\":");
	config.push(startIdentityType);
	if(startIdentityType=="5"){
		config.push(",\"addressIdValue\":");
		config.push("\"" + $("#selectAddress").val() + "\"");
		
		config.push(",\"addressIdText\":");
		config.push("\"" +$("#selectAddress").find("option:selected").text() + "\"");
	}
	else{
		config.push(",\"names\":");
		config.push("\"" + startIdentityNames[1] + "\"");
		config.push(",\"values\":");
		config.push("\"" + startIdentityNames[0] + "\"");
	}	
	//根据明细表行启动子流程
	if(startCountType=='3'){
		config.push(",\"detailsIdValue\":");
		config.push("\"" + $("#selectDetailsId").val() + "\"");

		config.push(",\"detailsIdText\":");
		config.push("\"" + $("#selectDetailsId").find("option:selected").text() + "\"");
	}
	config.push("}");
	
	config.push(",\"startCountType\":");
	config.push(getRadioValue(document.getElementsByName('startCountType')));

	config.push(",\"skipDraftNode\":");
	config.push("" + document.getElementsByName('skipDraftNode')[0].checked);

	var notifies = getCheckboxValue(document.getElementsByName('onErrorNotify'));
	var onErrorNotify = [];
	for (var i = 0; i < notifies.length; i ++) {
		onErrorNotify.push("" + notifies[i]);
	}
	config.push(",\"onErrorNotify\":");
	config.push("[" + onErrorNotify.join(",") + "]");

	config.push(",\"startParamenters\":");
	config.push(getParamentersJson());
	if(startCountType=='3'){
		//增加明细表启动行参数
		config.push(",\"rowParamTableParamenters\":");
		config.push(getParamentersJson("rowParamTable"));
	}
	
	config.push("}");
	AttributeObject.NodeData['configContent'] = config.join("");
}

AttributeObject.AppendDataFuns.push(writeJSON);

function getParamentersJson(tableId) {
	
	var prefix=(tableId=='paramTable'||!tableId) ?"startParamenters":tableId+"Paramenters";
	var rtn = [];
	var values = document.getElementsByName(prefix+".name.value");
	var texts = document.getElementsByName(prefix+".name.text");
	var types = document.getElementsByName(prefix+".name.type");
	var notNulls = document.getElementsByName(prefix+".name.notNull");
	var expressionValues= document.getElementsByName(prefix+".expression.value");
	var expressionTexts= document.getElementsByName(prefix+".expression.text");
	for (var i = 0; i < values.length; i ++) {
		rtn.push("{\"name\":{\"text\":\"" + formatJson(texts[i].value) 
				+ "\",\"value\":\"" + values[i].value
				+ "\",\"type\":\"" + types[i].value 
			    + "\",\"notNull\":\"" + notNulls[i].value + "\"}"
			    + ",\"expression\":{\"text\":\"" + formatJson(expressionTexts[i].value) 
				+ "\",\"value\":\"" + formatJson(expressionValues[i].value) + "\"}}");
	}
	return "[" + rtn.join(",") + "]";
}

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

function initSubProcessInfo() {
	var config = AttributeObject.NodeData['configContent'];
	if (config != null && config != "") {
		//去掉回车换行        
		config = config.replace(/[\r\n]/g,"");
			
		var json = (new Function("return ("+ config + ")"))();
		initModelName(json);
		initParams(json);
		initParamsRowDetial(json)
	} else {
		SetStartCountTypeState('1');
	}
}
function initModelName(json) {
	document.getElementsByName('modelName')[0].value = json.subProcess.modelName;
	document.getElementsByName('dictBean')[0].value = json.subProcess.dictBean;
	document.getElementsByName('templateId')[0].value = json.subProcess.templateId;
	document.getElementsByName('templateName')[0].value = json.subProcess.templateName;
	document.getElementsByName('createParam')[0].value = json.subProcess.createParam;

	setRadio(document.getElementsByName('startIdentityType'), json.startIdentity.type);
	setRadio(document.getElementsByName('startCountType'), json.startCountType);
	var formFieldList =FlowChartObject.FormFieldList;
	if(json.startCountType=="3"){
		$("#div_startCountType_selectDetails").show();
		if(formFieldList.length==0){
			if(json.startIdentity.detailsIdValue!="" && json.startIdentity.detailsIdText!=""){
				var o = [{name:json.startIdentity.detailsIdValue,label: json.startIdentity.detailsIdText}];
				buildingSelectOptionById("selectDetailsId",o);
			}
		}else{
			loadSelectDetailsId();
		}
		document.getElementById('selectDetailsId').value=json.startIdentity.detailsIdValue;
	}
	setRadio(document.getElementsByName('skipDraftNode'), json.skipDraftNode);
	setCheckbox(document.getElementsByName('onErrorNotify'), json.onErrorNotify);

	if (json.startIdentity.type == 3) {
		document.getElementsByName('addressValueId')[0].value = json.startIdentity.values;
		document.getElementsByName('addressValueName')[0].value = json.startIdentity.names;
	} else if (json.startIdentity.type == 4) {
		document.getElementsByName('formulaValueId')[0].value = json.startIdentity.values;
		document.getElementsByName('formulaValueName')[0].value = json.startIdentity.names;
	}
	else if(json.startIdentity.type=="5"){
		/* addressIdText: "明细表1.普通地址本"
		addressIdValue: "fd_36b4e619d38cf8.fd_36b58cff38be1c"
		detailsIdText: "明细表1"
		detailsIdValue: "fd_36b4e619d38cf8" */
		if(formFieldList.length==0){
			if(json.startIdentity.addressIdValue!="" && json.startIdentity.addressIdText!=""){
				var o = [{name:json.startIdentity.addressIdValue,label: json.startIdentity.addressIdText}];
				buildingSelectOptionById("selectAddress",o);
			}
		}else{
			loadDetailSelectAddress();
		}
		document.getElementById('selectAddress').value=json.startIdentity.addressIdValue;
		
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
function initParamsRowDetial(json) {
	var params = json.rowParamTableParamenters;
	if(!params){
		return;
	}
	for (var i = 0; i < params.length; i ++) {
		var row = params[i];
		var p = [];//(row.name.notNull == 'true' || row.name.notNull == true) ? [null, null, null, ""] : [];
		DocList_AddRow("rowParamTable", p,
				{
					"rowParamTableParamenters.name.type" : row.name.type, 
					"rowParamTableParamenters.name.value" : row.name.value,
					"rowParamTableParamenters.name.text" : row.name.text,
					"rowParamTableParamenters.name.notNull" : row.name.notNull,
					"rowParamTableParamenters.expression.value" : row.expression.value,
					"rowParamTableParamenters.expression.text" : row.expression.text
				});
	}
}
function addSubProcessParamenterCheck(table){
	var selectDetailsId=$("#selectDetailsId").val();
	if(selectDetailsId){
		addSubProcessParamenter(table);
		return;
	}
	
	alert('<kmss:message key="FlowChartObject.Lang.Node.subprocess.detailstart.check" bundle="sys-lbpmservice-node-subprocess" />');
	return false;
}
//---- 子流程选择对话框
function addSubProcessParamenter(table) {
	while (table.tagName != 'TABLE') {
		table = table.parentNode;
	}
	var modelName = document.getElementsByName('modelName')[0];
	var dictBean = document.getElementsByName('dictBean')[0];
	var templateId = document.getElementsByName('templateId')[0];
	var url = "subProcessDictService&modelName=" + modelName.value + "&templateId=" + templateId.value;
	if (dictBean != null && dictBean.value != '') {
		url = dictBean.value;
	}
	//根据不同的明细表前缀来设置不同的值由于 startParamenters 是固有值，为兼容历史数据，故作此判断
	var prefix=(table.id=='paramTable') ?"startParamenters":table.id+"Paramenters";
	Dialog_List(false, null, null, ";", url, function(data) {
		if (data != null) {
			var tr = DocList_AddRow(table);
			var inputs = tr.getElementsByTagName("input");
			var value = getElementsByName(inputs, prefix+".name.value");
			var text = getElementsByName(inputs, prefix+".name.text");
			var type = getElementsByName(inputs, prefix+".name.type");
			var notNull = getElementsByName(inputs, prefix+".name.notNull");
			if (value != null && text != null) {
				var rows = data.GetHashMapArray();
				value.value = rows[0].id;
				text.value = rows[0].name;
				type.value = rows[0].type;
				notNull.value = rows[0].notNull;
			}
		}
	},null,null,true);
}

// 参数公式对话框
function showParamenterFormulaDialog(tr,onlyDetail) {
	while (tr.tagName != 'TR') {
		tr = tr.parentNode;
	}
	//获取表格
	var table=tr;
	while (table.tagName != 'TABLE') {
		table = table.parentNode;
	}
	//根据不同的明细表前缀来设置不同的值由于 startParamenters 是固有值，为兼容历史数据，故作此判断
	var prefix=(table.id=='paramTable') ?"startParamenters":table.id+"Paramenters";
	
	var inputs = tr.getElementsByTagName("input");
	var value = getElementsByName(inputs, prefix+".expression.value");
	var text = getElementsByName(inputs, prefix+".expression.text");
	var type = getElementsByName(inputs, prefix+".name.type");
	var nameValue = getElementsByName(inputs, prefix+".name.value");
	if(type.value == "Attachment" || type.value == "Attachment[]") {
		// 表单附件
		var data = new KMSSData();
		for(var i=0; i<FlowChartObject.FormFieldList.length; i++){
			var field = FlowChartObject.FormFieldList[i];
			if(!(field.type == "Attachment"  || field.type == "Attachment[]")){
			//if(!(field.type == "Attachment"))
				continue;
			}
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
		var formFields=FlowChartObject.FormFieldList;
		//只显示指定明细表的列
		if(onlyDetail){
			formFields=[];
			var selectDetailsId=$("#selectDetailsId").val();
			for(var i=0;i<FlowChartObject.FormFieldList.length;i++){
				var fieldName=FlowChartObject.FormFieldList[i].name;
				if(fieldName.indexOf(selectDetailsId+".")>=0){
					formFields.push(FlowChartObject.FormFieldList[i]);
				}
			}
		}
		//明细表行赋值给非明细表字段，取Object。避免明细表行类型为数组类型和非明细表字段不匹配
		var typeTemp="Object";
		if(nameValue.value.indexOf(".")>0){
			typeTemp= type.value;
		}
		Formula_Dialog(value, text,
				formFields, typeTemp, function(data) {
			
		});
	}
}

function loadNotNullProperties(modelName, templateId, dictBean) {
	var url = "subProcessDictService&modelName=" + modelName + "&templateId=" + templateId;
	if (dictBean != null && dictBean != "") {
		url = dictBean;
	}

	var kmssData = new KMSSData();
	kmssData.SendToBean(url , function(data) {
		var rows = data.GetHashMapArray();
		for (var i = 0; i < rows.length; i ++) {
			var row = rows[i];
			if (row.id != "docSubject"&&row.id != "fdName") {
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
function showDistributeParent(note){
	// 开关打开，则显示分发意见页签
	if($(note).prop('checked')){
		$('#distributeParentNote').show();
	}else{
		// 清空值再隐藏
		clearDistributeNote();
		$('#distributeParentNote').hide();
	}
}
function clearDistributeNote(){
	$("[name='wf_distributeParentDisplayName']").val('');
	$("[name='wf_distributeParentDisplayId']").val('');
	$("[name='wf_distributeNoteNodeNames']").val('');
	$("[name='wf_distributeNoteNodeIds']").val('');
}
function distributeCallback(arg){
	if(arg && arg.data && (arg.data).length>0){
		if(arg.data[0].id != ''){
			$("[name='wf_distributeParentDisplayName']").attr("readonly",true);
		}else{
			$("[name='wf_distributeParentDisplayName']").attr("readonly",false);
		}
	}
}

// 分发父流程意见节点选择
function selectNodes(idField, nameField){
	var data = new KMSSData(), NodeData = AttributeObject.NodeData;
	var preNodes = [];
	getPreNode(AttributeObject.NodeObject,preNodes);
	preNodes.reverse();
	for(var i=0; i<preNodes.length; i++){
		var node = preNodes[i];
		if(node.Data.id == NodeData.id || (node.Data.groupNodeId != null && node.Data.groupNodeId!=NodeData.groupNodeId))
			continue;
		var nodDesc = AttributeObject.Utils.nodeDesc(node);
		if ((nodDesc.isHandler && !nodDesc.isDraftNode) || node.Type == "embeddedSubFlowNode" || node.Type == "splitNode") {
			data.AddHashMap({id:node.Data.id, name:node.Data.id+"."+node.Data.name});
			if(node.Type == "embeddedSubFlowNode"){
				var fdContent = getContentByRefId(node.Data.embeddedRefId);
				if(fdContent){
					//嵌入的流程图对象
					var embeddedFlow = WorkFlow_LoadXMLData(fdContent);
					for(var j = 0;j<embeddedFlow.nodes.length;j++){
						var eNode = embeddedFlow.nodes[j];
						if((_isHandler(eNode) && !_isDraftNode(eNode))){
							data.AddHashMap({id:node.Data.id+"-"+eNode.id, name:node.Data.id+"."+node.Data.name+"("+eNode.id+"."+eNode.name+")"});
						}
					}
				}
			}
		}
	}
	var dialog1 = new KMSSDialog(true, true);
	dialog1.winTitle = FlowChartObject.Lang.dialogTitle;
	dialog1.AddDefaultOption(data);
	dialog1.BindingField(idField, nameField, ";");
	dialog1.Show();
}

// 获取当前节点之前的节点
function getPreNode(nodeObject,preNodes){
	for(var i=0;i<nodeObject.LineIn.length;i++){
	  var node = nodeObject.LineIn[i].StartNode;
	  if($.inArray(node,preNodes)>-1){
	  	continue;
	  }
	  preNodes.push(node);
	  // 非起草节点循环递归
	  if(node && node.Type && node.Type !="draftNode"){
		 getPreNode(node,preNodes);
	  }
	}
}
//嵌入子流程根据redId获得流程图xml
function getContentByRefId(fdRefId){
	var fdContent = "";
	var ajaxurl = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpmEmbeddedSubFlow.do?method=getContentByRefId&fdRefId='+fdRefId;
	var kmssData = new KMSSData();
	kmssData.SendToUrl(ajaxurl, function(http_request) {
		var responseText = http_request.responseText;
		var json = eval("("+responseText+")");
		if (json.fdContent){
			fdContent = json.fdContent;
		}
	},false);
	return fdContent;
}

function _isHandler(node) {
	var sNodDesc = FlowChartObject.NodeTypeDescs[FlowChartObject.NodeDescMap[node.XMLNODENAME]];
	return sNodDesc ? (
			sNodDesc.isHandler() &&
			!sNodDesc.isAutomaticRun() &&
			!sNodDesc.isBranch() &&
			!sNodDesc.isSubProcess() &&
			!sNodDesc.isConcurrent() &&
			sNodDesc.uniqueMark() == null
	) ||  sNodDesc.uniqueMark() == 'signNodeDesc' ||  sNodDesc.uniqueMark() == 'voteNodeDesc' : false;
};
function _isDraftNode(node) {
	var sNodDesc = FlowChartObject.NodeTypeDescs[FlowChartObject.NodeDescMap[node.XMLNODENAME]];
	return sNodDesc ? (sNodDesc.uniqueMark() == 'draftNodeDesc') : false;
};
function _isSendNode(node) {
	var sNodDesc = FlowChartObject.NodeTypeDescs[FlowChartObject.NodeDescMap[node.XMLNODENAME]];
	return sNodDesc ? (
			sNodDesc.isHandler() &&
			sNodDesc.isAutomaticRun() &&
			!sNodDesc.isBranch() &&
			!sNodDesc.isSubProcess() &&
			!sNodDesc.isConcurrent() &&
			sNodDesc.uniqueMark() == null
	) : false;
};

// 子流程选择框
function selectSubFlow() {
	var dialog = new KMSSDialog(false, false);
	var treeTitle = '<kmss:message key="FlowChartObject.Lang.Node.subprocessTitle" bundle="sys-lbpmservice-node-subprocess" />';
	var node = dialog.CreateTree(treeTitle);
	dialog.winTitle = treeTitle;
	var fdId = null;
	try {
		if (FlowChartObject.IsTemplate) { // just for template
			var dialogObject=window.dialogArguments?window.dialogArguments:opener.Com_Parameter.Dialog;
			var url = dialogObject.Window.parent.parent.location.href;
			fdId = Com_GetUrlParameter(url, 'fdId');
		}
	} catch (e) {}
	node.AppendBeanData("subProcessDialogService", null, null, false, fdId);
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
			clearDistributeNote();
			loadNotNullProperties(modelName.value, node.value, dictBean.value);
		}
	});
	dialog.Show();
	//showConfigPanel(true);
	
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
	$("#div_startCountType_selectDetails").hide();
	SetStartCountTypeState(value,true);
}

function _disabledStartIdentityType(disabled){
	var startIdentityTypes = document.getElementsByName("startIdentityType");
	for(var i=0;i<startIdentityTypes.length;i++){
		if(startIdentityTypes[i].value=="3"  || startIdentityTypes[i].value=="4"){
			startIdentityTypes[i].disabled=disabled;
		}
	}
}

function startCountTypeChange(startType){
	if(startType=='3'){
		
		_disabledStartIdentityType(true);

		$('#div_startCountType_selectDetails').show();
		if(!document.getElementById('selectDetailsId').value){
			loadSelectDetailsId();
			loadDetailSelectAddress()
		}
	}else{
		_disabledStartIdentityType(false);
		$('#div_startCountType_selectDetails').hide();
	}
}
//加载根据明细表行启动子流程明细表
function loadSelectDetailsId(){
	var formFieldList =FlowChartObject.FormFieldList;

	var selectDetails=[];
	for(var i=0;i<formFieldList.length;i++){
		//过滤掉非明细表字段
		if(!formFieldList[i].controlType){
			continue;
		}
		if(formFieldList[i].controlType && formFieldList[i].controlType=="detailsTable"){
			selectDetails.push(formFieldList[i]);
		}
	}
	//document.getElementById('selectAddress').value="";
	//document.getElementById('selectDetailsId').value="";
	buildingSelectOptionById("selectDetailsId",selectDetails);
}
//加载启动人身份->明细表地址本
function loadDetailSelectAddress(){
	var formFieldList =FlowChartObject.FormFieldList;
	var addressSelect=[];
	//启动项中指定的明细表
	var selectDetailsId = document.getElementById('selectDetailsId').value
	for(var i=0;i<formFieldList.length;i++){
		//过滤掉非明细表字段
		if(!formFieldList[i].controlType || formFieldList[i].name.indexOf(".")<0){
			continue;
		}
		//没有选择明细表，不加载启动人身份 明细表地址本
		if(!selectDetailsId){
			loadSelectDetailsId();
			continue;
		}
		//启动项中指定了明细表,只取指定明细表中的地址本
		if(formFieldList[i].name.indexOf(selectDetailsId+".")>=0 && (formFieldList[i].orgType=="ORG_TYPE_PERSON" || formFieldList[i].type.indexOf("SysOrgPerson") != -1)){
			addressSelect.push(formFieldList[i]);
		}
	}
	buildingSelectOptionById("selectAddress",addressSelect);
}

//填充选择项
function buildingSelectOptionById(domId,data){	
	var dom=$("#"+domId);
	dom.empty();
	dom.append('<option selected="selected" value=""><kmss:message key="FlowChartObject.Lang.Node.pleaseSelect" bundle="sys-lbpmservice-node-subprocess" /></option>');
	for(var i=0;i<data.length;i++){
		dom.append("<option value='"+data[i].name+"'>"+data[i].label+"</option>");
	}
}

function SetStartCountTypeState(identityType,isChangeIdentityType) {
	//启动身份类型与启动项的映射关系
	var checkRelationMapping=[
		["1","3"],
		[],
		["1","2","3"],
		["1","2","3"],
		["3"],
	];
	var curStartCountType=$("input[name='startCountType']:checked").val();
	var intIdentityType=parseInt(identityType);
	var checkRelation=checkRelationMapping[intIdentityType-1];
	var startCountTypes = document.getElementsByName('startCountType');
	for (var i = 0; i < startCountTypes.length; i ++) {
		var startCountType = startCountTypes[i];
		//判断是否来自“启动人身份”切换事件
		if(isChangeIdentityType){
			//“启动人身份” 为 起草人时，启动选项不能为根据启动人启动多条子流程
			if (identityType == '1'&& startCountType.value=="1" && curStartCountType=="2") {
				startCountType.checked = true;
			}
			else if (identityType == '5'&& startCountType.value=="3") {
				startCountType.checked = true;
				
			}
		}	
		startCountType.disabled = true;
		for(var j=0;j<checkRelation.length;j++){
			if(startCountType.value==checkRelation[j]){
				startCountType.disabled=false;
			}
		}
	}
	
	if(curStartCountType=="3"){	
		$("#div_startCountType_selectDetails").show();
		//启动人身份选择明细表地址本时 ，启动项的明细表需要加载
		if(!document.getElementById('selectDetailsId').value){
			loadSelectDetailsId();
			//loadDetailSelectAddress()
		}
		_disabledStartIdentityType(true);
	}else{
		_disabledStartIdentityType(false);
	}

	if(identityType=="5"){
		if(!document.getElementById('selectAddress').value){
			loadDetailSelectAddress()
		}
		$("#div_startCountType_selectDetails").show();
		$("#startCountType_selectDetails").attr({"disabled":"false"});
		_disabledStartIdentityType(true);
	}
	else{
		$("#startCountType_selectDetails").removeAttr("disabled");
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
function clearRowDetailParamTable() {
	var table = document.getElementById("rowParamTable");
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

AttributeObject.Init.EditModeFuns.push(function(nodeData) {
	//多语言
	_initPropLang4Edit("nodeDesc",nodeData,"description","_");
});
AttributeObject.Init.ViewModeFuns.push(function(nodeData) {
	//多语言
	_initPropLang4View("nodeDesc",nodeData,"description","_");
});

AttributeObject.AppendDataFuns.push(function(nodeData){
/**
	"nodeDesc":[//描述
		{"lang":"zh-CN","value":"主管审批意见"},{"lang":"en-US","value":"Manager Auditing Note"}
	],
**/
	_propLang4AppendData("nodeDesc",nodeData,"description","_");
});

</script>