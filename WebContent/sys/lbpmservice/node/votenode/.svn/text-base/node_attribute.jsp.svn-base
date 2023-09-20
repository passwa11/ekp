<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextareaGroupTagNew"%>
<table width="590px" id="Label_Tabel">
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Base" bundle="sys-lbpm-engine" />">
		<td>
			<table width="100%" class="tb_normal">
				<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8" />
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.voter" bundle="sys-lbpmservice-node-votenode" /></td>
					<td>
						<label><input type="radio" name="wf_handlerSelectType" value="org" onclick="switchHandlerSelectType(value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_handlerSelectType" value="formula" onclick="switchHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
						<input name="wf_handlerNames" class="inputsgl" style="width:400px" readonly>
						<input name="wf_handlerIds" type="hidden" orgattr="handlerIds:handlerNames">
						<span id="SPAN_SelectType1">
						<a href="#" onclick="Dialog_Address(true, 'wf_handlerIds', 'wf_handlerNames', null, ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<span id="SPAN_SelectType2" style="display:none ">
						<a href="#" onclick="selectByFormula('wf_handlerIds', 'wf_handlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<br>
						<label>
							<input type="checkbox" name="wf_ignoreOnHandlerEmpty" value="true">
							<kmss:message key="FlowChartObject.Lang.Node.ignoreOnHandlerEmpty" bundle="sys-lbpmservice" />
						</label>
					</td>
				</tr>
				<tr style="display:none">
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.processType" bundle="sys-lbpmservice" /></td>
					<td>
						</label><label>
							<input name="wf_processType" type="radio" value="2" checked>
							<kmss:message key="FlowChartObject.Lang.Node.processType_21" bundle="sys-lbpmservice" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="100px" title="<kmss:message key='FlowChartObject.Lang.Node.voteRule.hint' bundle='sys-lbpmservice-node-votenode' />">
						<kmss:message key="FlowChartObject.Lang.Node.voteRule" bundle="sys-lbpmservice-node-votenode" />
					</td>
					<td>
						<label>
							<input name="wf_voteRule" type="radio" value="1" onclick="switchVoteRule(value);">
							<kmss:message key="FlowChartObject.Lang.Node.voteRule_1" bundle="sys-lbpmservice-node-votenode" />
							<span name="SPAN_voteRule_1" style="display:none"><input name="wf_votePassRate" class="inputsgl" style="width:15%;text-align:center;" type="text" onkeyup="controlPercent(this)" onafterpaste="controlPercent(this)"> 
							<span class="txtstrong">*</span> %</span>
						</label><br/>
						<label>
							<input name="wf_voteRule" type="radio" value="2" onclick="switchVoteRule(value);">
							<kmss:message key="FlowChartObject.Lang.Node.voteRule_2" bundle="sys-lbpmservice-node-votenode" />
							<span name="SPAN_voteRule_2" style="display:none"><input name="wf_votePassNum" class="inputsgl" style="width:15%;text-align:center;" type="text" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)"> 
							<span class="txtstrong">*</span> <kmss:message key="FlowChartObject.Lang.Node.voteRule_2.num" bundle="sys-lbpmservice-node-votenode" />
							<kmss:message key="FlowChartObject.Lang.Node.voteRule_2.hint" bundle="sys-lbpmservice-node-votenode" /></span>
						</label><br/>
						<label>
							<input name="wf_voteRule" type="radio" value="3" onclick="switchVoteRule(value);">
							<kmss:message key="FlowChartObject.Lang.Node.voteRule_3" bundle="sys-lbpmservice-node-votenode" />
							<span name="SPAN_voteRule_3" style="display:none"><input name="wf_voteVetoRate" class="inputsgl" style="width:15%;text-align:center;" type="text" onkeyup="controlPercent(this)" onafterpaste="controlPercent(this)"> 
							<span class="txtstrong">*</span> %</span>
						</label><br/>
						<label>
							<input name="wf_voteRule" type="radio" value="4" onclick="switchVoteRule(value);">
							<kmss:message key="FlowChartObject.Lang.Node.voteRule_4" bundle="sys-lbpmservice-node-votenode" />
						</label><br/>
						<label>
							<input name="wf_voteRule" type="radio" value="0" onclick="switchVoteRule(value);" checked>
							<kmss:message key="FlowChartObject.Lang.Node.voteRule_0" bundle="sys-lbpmservice-node-votenode" />
						</label>
					</td>
				</tr>
				<c:import url="/sys/lbpmservice/node/common/node_handler_common_operation.jsp" charEncoding="UTF-8" />
				<tr style="display:none">
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.onHandlerSame" bundle="sys-lbpmservice"/></td>
					<td>
						<input name="wf_ignoreOnHandlerSame" type="hidden" value="false" />
						<input name="wf_onAdjoinHandlerSame" type="hidden" value="false" />
					</td>
				</tr>
				<c:import url="/sys/lbpmservice/node/common/node_notify_attribute.jsp" charEncoding="UTF-8" />
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.popedom" bundle="sys-lbpmservice" /></td>
					<td>
						<label style="display:none">
							<input name="wf_canModifyMainDoc" type="checkbox" value="true">
							<kmss:message key="FlowChartObject.Lang.Node.canModifyMainDoc" bundle="sys-lbpmservice" />
						</label>
						<label>
							<input name="wf_canAddAuditNoteAtt" type="checkbox" checked value="true">
							<kmss:message key="FlowChartObject.Lang.Node.canAddAuditNoteAtt" bundle="sys-lbpmservice" />
						</label>
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
						<c:import url="/sys/lbpm/flowchart/page/nodeDescription_ext_attribute.jsp" charEncoding="UTF-8">
							<c:param name="nodeType" value="${param.nodeType}" />
							<c:param name="modelName" value="${param.modelName}" />
						</c:import>
					</td>
				</tr>
				
				<c:import url="/sys/lbpm/flowchart/page/node_ext_attribute.jsp" charEncoding="UTF-8">
					<c:param name="position" value="base" />
					<c:param name="nodeType" value="${param.nodeType}" />
					<c:param name="modelName" value="${param.modelName}" />
				</c:import>
			</table>
		</td>
	</tr>
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Operation" bundle="sys-lbpm-engine" />">
		<td>
			<c:import url="/sys/lbpmservice/node/common/node_operation_attribute.jsp" charEncoding="UTF-8">
				<c:param name="nodeType" value="${param.nodeType}" />
				<c:param name="modelName" value="${param.modelName}" />
				<c:param name="passOperationType"><kmss:message key="lbpm.operation.handler_agree" bundle="sys-lbpmservice-operation-vote" /></c:param>
			</c:import>
		</td>
	</tr>
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Advance" bundle="sys-lbpm-engine" />">
		<td>
			<table class="tb_normal" width="100%">
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.optVoterNames" bundle="sys-lbpmservice-node-votenode" /></td>
					<td colspan="2">
						<label><input type="radio" name="wf_optHandlerSelectType" value="org" onclick="switchOptHandlerSelectType(value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_optHandlerSelectType" value="formula" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
						<input name="wf_optHandlerIds" type="hidden" orgattr="optHandlerIds:optHandlerNames">
						<input name="wf_optHandlerNames" class="inputsgl" style="width:400px" readonly>
						<span id="SPAN_OptSelectType1">
						<a href="#" onclick="Dialog_Address(true, 'wf_optHandlerIds', 'wf_optHandlerNames', null, ORG_TYPE_POSTORPERSON | ORG_TYPE_DEPT | ORG_TYPE_ROLE);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<span id="SPAN_OptSelectType2" style="display:none ">
						<a href="#" onclick="selectByFormula('wf_optHandlerIds', 'wf_optHandlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<br><div id="DIV_OptHandlerCalType"><kmss:message key="FlowChartObject.Lang.Node.optHandlerCalType" bundle="sys-lbpmservice" />: 
						<label>
							<input name="wf_optHandlerCalType" type="radio" value="1">
							<kmss:message key="FlowChartObject.Lang.Node.handler" bundle="sys-lbpm-engine" />
						</label><label>
							<input name="wf_optHandlerCalType" type="radio" value="2" checked>
							<kmss:message key="FlowChartObject.Lang.Node.creator" bundle="sys-lbpm-engine" />
						</label><br></div><label>
							<input name="wf_useOptHandlerOnly" type="checkbox" value="true">
							<kmss:message key="FlowChartObject.Lang.Node.useOptHandlerOnly" bundle="sys-lbpmservice" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="100px" rowspan="3"><kmss:message key="FlowChartObject.Lang.Node.dayOfHandle" bundle="sys-lbpmservice" /></td>
					<td width="15%"><kmss:message key="FlowChartObject.Lang.Node.dayOfNotify" bundle="sys-lbpmservice" /></td>
					<td colspan="2">
						<kmss:message key="FlowChartObject.Lang.Node.dayOfNotify1" bundle="sys-lbpmservice" />
						<input name="wf_dayOfNotify" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
							<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
						<input name="wf_hourOfNotify" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
							<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
						<input name="wf_minuteOfNotify" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
							<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
						<kmss:message key="FlowChartObject.Lang.Node.uncompleted" bundle="sys-lbpmservice" /><br/>
						<label><input name="wf_repeatDayOfNotify" type="checkbox" value="true" onclick="showRepeatConfig(this.checked);"><kmss:message key="FlowChartObject.Lang.Node.repeat" bundle="sys-lbpmservice" /></label>&nbsp;&nbsp;
						<span id="repeatConfigDiv" style="display:none">
							<input name="wf_repeatTimesDayOfNotify" class="inputsgl" value="1" size="3" style="text-align:center" onkeyup="this.value = ((value=value.replace(/\D/g,''))==''? value : parseInt(this.value.replace(/\D/g,''),10))">
							<kmss:message key="FlowChartObject.Lang.Node.times" bundle="sys-lbpmservice" />&nbsp;&nbsp;
							<kmss:message key="FlowChartObject.Lang.Node.interval" bundle="sys-lbpmservice" />
							<input name="wf_intervalDayOfNotify" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
								<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
							<input name="wf_intervalHourOfNotify" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
								<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
							<input name="wf_intervalMinuteOfNotify" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
								<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
						</span>
					</td>
				</tr>
				<tr>
					<td width="12%"><kmss:message key="FlowChartObject.Lang.Node.tranNotifyDraft" bundle="sys-lbpmservice" /></td>
					<td colspan="2">
						<kmss:message key="FlowChartObject.Lang.Node.dayOfNotify1" bundle="sys-lbpmservice" />
					  	<input name="wf_tranNotifyDraft" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
					  		<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
					  	<input name="wf_hourOfTranNotifyDraft" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
					  		<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
					  	<input name="wf_minuteOfTranNotifyDraft" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
					  		<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
					  	<kmss:message key="FlowChartObject.Lang.Node.uncompleted" bundle="sys-lbpmservice" /><br>
					</td>
				</tr>
				<tr>
					<td width="12%"><kmss:message key="FlowChartObject.Lang.Node.tranNotifyPrivileger" bundle="sys-lbpmservice" /></td>
					<td colspan="2">
						<kmss:message key="FlowChartObject.Lang.Node.dayOfNotify1" bundle="sys-lbpmservice" />
						<input name="wf_tranNotifyPrivate" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
							<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
						<input name="wf_hourOfTranNotifyPrivate" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
							<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
						<input name="wf_minuteOfTranNotifyPrivate" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
							<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
						<kmss:message key="FlowChartObject.Lang.Node.uncompleted" bundle="sys-lbpmservice" /><br>
					</td>
				</tr>
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.Overtime.Handle" bundle="sys-lbpmservice" /></td>
					<td colspan="2">
						<input name="wf_dayOfVote" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
							<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
						<input name="wf_hourOfVote" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
							<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
						<input name="wf_minuteOfVote" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
							<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
						<kmss:message key="FlowChartObject.Lang.Node.uncompleted" bundle="sys-lbpmservice" /><br/>
						<div style="margin-top:5px">
						<label>
							<input name="wf_dayOfVoteType" type="radio" value="1" checked>
							<kmss:message key="FlowChartObject.Lang.Node.autoVoteAgree" bundle="sys-lbpmservice-node-votenode" />
						</label>
						&nbsp;&nbsp;
						<label>
							<input name="wf_dayOfVoteType" type="radio" value="2">
							<kmss:message key="FlowChartObject.Lang.Node.autoVoteDisagree" bundle="sys-lbpmservice-node-votenode" />
						</label>
						&nbsp;&nbsp;
						<label>
							<input name="wf_dayOfVoteType" type="radio" value="3">
							<kmss:message key="FlowChartObject.Lang.Node.autoVoteAbstain" bundle="sys-lbpmservice-node-votenode" />
						</label>
						</div>
					</td>
				</tr>
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.nodeOptions" bundle="sys-lbpmservice" /></td>
					<td colspan="2">
						<label>					
						<input name="wf_recalculateHandler" type="checkbox" value="true" >
						<kmss:message key="FlowChartObject.Lang.Node.isRecalculate" bundle="sys-lbpmservice" />
						</label>
					</td>
				</tr>
				<c:import url="/sys/lbpm/flowchart/page/node_ext_attribute.jsp" charEncoding="UTF-8">
					<c:param name="position" value="advance" />
					<c:param name="nodeType" value="${param.nodeType}" />
					<c:param name="modelName" value="${param.modelName }" />
				</c:import>
			</table>
		</td>
	</tr>
	<%-- <tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Popedom" bundle="sys-lbpmservice" />">
		<td>
		<c:import url="/sys/lbpmservice/node/common/node_right_attribute.jsp" charEncoding="UTF-8" />
		</td>
	</tr> --%>
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Event" bundle="sys-lbpm-engine" />">
		<td>
		<c:import url="/sys/lbpm/flowchart/page/node_event_attribute.jsp" charEncoding="UTF-8" />
		</td>
	</tr>
	<c:import url="/sys/lbpm/flowchart/page/node_ext_attribute.jsp" charEncoding="UTF-8">
		<c:param name="position" value="newtag" />
		<c:param name="nodeType" value="${param.nodeType}" />
		<c:param name="modelName" value="${param.modelName }" />
	</c:import>
	<%-- <c:import url="/sys/lbpm/flowchart/page/node_variant_attribute.jsp" charEncoding="UTF-8">
		<c:param name="nodeType" value="${param.nodeType}" />
		<c:param name="modelName" value="${param.modelName }" />
	</c:import> --%>
	<c:import url="/sys/lbpmservice/node/common/node_custom_notify_attribute.jsp" charEncoding="UTF-8">
		<c:param name="nodeType" value="${param.nodeType}" />
		<c:param name="modelName" value="${param.modelName }" />
	</c:import>
</table>

<script>

AttributeObject.Init.AllModeFuns.unshift(function() {
	var NodeData = AttributeObject.NodeData;
	if (FlowChartObject.ProcessData != null && FlowChartObject.ProcessData.recalculateHandler == null) {
		FlowChartObject.ProcessData.recalculateHandler = "true";
	}
	if (NodeData.recalculateHandler == null && FlowChartObject.ProcessData != null) {
		AttributeObject.NodeObject.Data.recalculateHandler = FlowChartObject.ProcessData.recalculateHandler;
	}
});

var handlerSelectType = AttributeObject.NodeData["handlerSelectType"];
var optHandlerSelectType = AttributeObject.NodeData["optHandlerSelectType"];
var ignoreOnHandlerSame = AttributeObject.NodeData["ignoreOnHandlerSame"];
var onAdjoinHandlerSame = AttributeObject.NodeData["onAdjoinHandlerSame"];
var processType = AttributeObject.NodeData["processType"];
var repeatDayOfNotify = AttributeObject.NodeData["repeatDayOfNotify"];
AttributeObject.Init.AllModeFuns.push(function() {

	if(!handlerSelectType || handlerSelectType!="formula"){
		document.getElementById('SPAN_SelectType1').style.display='';
		document.getElementById('SPAN_SelectType2').style.display='none';
	}else{
		document.getElementById('SPAN_SelectType1').style.display='none';
		document.getElementById('SPAN_SelectType2').style.display='';
	}

	if (!optHandlerSelectType || optHandlerSelectType!="formula"){
		document.getElementById('SPAN_OptSelectType1').style.display='';
		document.getElementById('SPAN_OptSelectType2').style.display='none';
		document.getElementById('DIV_OptHandlerCalType').style.display='';
	} else {
		document.getElementById('SPAN_OptSelectType1').style.display='none';
		document.getElementById('SPAN_OptSelectType2').style.display='';
		document.getElementById('DIV_OptHandlerCalType').style.display='none';
	}

	AttributeObject.Utils.switchOrgAttributes(document.getElementsByName("wf_handlerIds")[0], handlerSelectType);
	AttributeObject.Utils.switchOrgAttributes(document.getElementsByName("wf_optHandlerIds")[0], optHandlerSelectType);
	
	var settingInfo = getSettingInfo();
	//审批意见附件开关开始
	if (!AttributeObject.NodeData["canAddAuditNoteAtt"]){
		var _isCanAddAuditNoteAtt = settingInfo["isCanAddAuditNoteAtt"];
		if (_isCanAddAuditNoteAtt === "false"){
			$("input[name='wf_canAddAuditNoteAtt']").prop("checked",false);
		}
	}
	
	switchVoteRule(AttributeObject.NodeData["voteRule"]);
});

//审批人选择方式
function switchHandlerSelectType(value){
	if(handlerSelectType==value)
		return;
	handlerSelectType = value;
	SPAN_SelectType1.style.display=handlerSelectType!="formula"?"":"none";
	SPAN_SelectType2.style.display=handlerSelectType=="formula"?"":"none";
	document.getElementsByName("wf_handlerIds")[0].value = "";
	document.getElementsByName("wf_handlerNames")[0].value = "";

	AttributeObject.Utils.switchOrgAttributes(document.getElementsByName("wf_handlerIds")[0], handlerSelectType);
}
// 备选审批人选择方式
function switchOptHandlerSelectType(value) {
	if(optHandlerSelectType==value)
		return;
	optHandlerSelectType = value;
	document.getElementById('SPAN_OptSelectType1').style.display=optHandlerSelectType!="formula"?"":"none";
	document.getElementById('SPAN_OptSelectType2').style.display=optHandlerSelectType=="formula"?"":"none";
	document.getElementById('DIV_OptHandlerCalType').style.display=optHandlerSelectType!="formula"?"":"none";
	document.getElementsByName("wf_optHandlerIds")[0].value = "";
	document.getElementsByName("wf_optHandlerNames")[0].value = "";

	AttributeObject.Utils.switchOrgAttributes(document.getElementsByName("wf_optHandlerIds")[0], optHandlerSelectType);
}

//判断是否非负整数
function isInt(i){
	var re = /^[0-9]+$/;
	return re.test(i);
}
//控制只能输入数字
function controlNumber(obj){
	obj.value=(parseInt((obj.value=obj.value.replace(/\D/g,''))==''||parseInt((obj.value=obj.value.replace(/\D/g,''))==0)? '0' :obj.value,10));
}
//控制只能输入0-100的数字
function controlPercent(obj){
	var reg = /^((?!0)\d{1,2}|100)$/;
	if(!obj.value.match(reg)){
		obj.value = "";
	}
}

function showRepeatConfig(checked){
	if (checked == true) {
		$('#repeatConfigDiv').show();
	} else {
		$('#repeatConfigDiv').hide();
		$("input[name=wf_repeatTimesDayOfNotify]")[0].value = "1";
		$("input[name=wf_intervalDayOfNotify]")[0].value = "0";
		$("input[name=wf_intervalHourOfNotify]")[0].value = "0";
		$("input[name=wf_intervalMinuteOfNotify]")[0].value = "0";
	}
}

AttributeObject.CheckDataFuns.push(function(data) {
	if(data.useOptHandlerOnly=="true" && data.optHandlerIds==""){
		alert('<kmss:message key="FlowChartObject.Lang.Node.checkOptHandlerEmpty" bundle="sys-lbpmservice" />');
		return false;
	}
	if(!isInt(data.dayOfNotify) || !isInt(data.hourOfNotify) || !isInt(data.minuteOfNotify)){
		alert('<kmss:message key="FlowChartObject.Lang.Node.checkDayOfNotify" bundle="sys-lbpmservice" />');
		return false;
	}
	if(!isInt(data.tranNotifyDraft) || !isInt(data.hourOfTranNotifyDraft) || !isInt(data.minuteOfTranNotifyDraft)){
		alert('<kmss:message key="FlowChartObject.Lang.Node.checkTranNotifyDraft" bundle="sys-lbpmservice" />');
		return false;
	}
	if(!isInt(data.tranNotifyPrivate) || !isInt(data.hourOfTranNotifyPrivate) || !isInt(data.minuteOfTranNotifyPrivate)){
		alert('<kmss:message key="FlowChartObject.Lang.Node.checkTranNotifyPrivate" bundle="sys-lbpmservice" />');
		return false;
	}
	if (data.voteRule == "2" && parseInt(data.votePassNum,10) <= 0) {
		alert('<kmss:message key="FlowChartObject.Lang.Node.voteRule_2.checkVotePassNum" bundle="sys-lbpmservice-node-votenode" />');
		$("input[name='wf_votePassNum']").focus();
		return false;
	}
	if (data.voteRule == "1" && !data.votePassRate) {
		alert('<kmss:message key="FlowChartObject.Lang.Node.voteRule_1_notNull" bundle="sys-lbpmservice-node-votenode" />');
		return false;
	}
	if (data.voteRule == "2" && !data.votePassNum) {
		alert('<kmss:message key="FlowChartObject.Lang.Node.voteRule_2_notNull" bundle="sys-lbpmservice-node-votenode" />');
		return false;
	}
	if (data.voteRule == "3" && !data.voteVetoRate) {
		alert('<kmss:message key="FlowChartObject.Lang.Node.voteRule_3_notNull" bundle="sys-lbpmservice-node-votenode" />');
		return false;
	}
	return true;
});

/**
 * 变更投票规则
 */
function switchVoteRule(value){
	$("span[name*='SPAN_voteRule_']").each(function(){
		var self = $(this);
		if (self.attr("name") == ("SPAN_voteRule_" + value)) {
			self.show();
		} else {
			self.hide();
		}
	});
	if (value == "1") {
		$("input[name='wf_votePassRate']").focus();
		$("input[name='wf_votePassNum']").val("");
		$("input[name='wf_voteVetoRate']").val("");
	} else if (value == "2") {
		$("input[name='wf_votePassNum']").focus();
		$("input[name='wf_votePassRate']").val("");
		$("input[name='wf_voteVetoRate']").val("");
	} else if (value == "3") {
		$("input[name='wf_voteVetoRate']").focus();
		$("input[name='wf_votePassRate']").val("");
		$("input[name='wf_votePassNum']").val("");
	} else {
		$("input[name='wf_votePassRate']").val("");
		$("input[name='wf_votePassNum']").val("");
		$("input[name='wf_voteVetoRate']").val("");
	}
}

function selectByFormula(idField, nameField){
	Formula_Dialog(idField,
			nameField,
			FlowChartObject.FormFieldList, 
			"com.landray.kmss.sys.organization.model.SysOrgElement[]",
			null,
			"com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction",
			FlowChartObject.ModelName);
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