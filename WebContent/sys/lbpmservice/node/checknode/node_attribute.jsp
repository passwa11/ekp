<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting" %>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextareaGroupTagNew"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<table width="590px" id="Label_Tabel">
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Base" bundle="sys-lbpm-engine" />">
		<td>
			<table width="100%" class="tb_normal">
				<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8" />
				<tr>
					<td width="100px"><kmss:message key="lbpm.nodeType.checkNode.template.attr.handler" bundle="sys-lbpmservice-node-checknode" /></td>
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
						
						<input type="checkbox" name="wf_ignoreOnHandlerEmpty" value="true" style="display:none"/>
						
					</td>
				</tr>
				<tr>
					<td width="100px"><kmss:message key="lbpm.nodeType.checkNode.template.attr.condition" bundle="sys-lbpmservice-node-checknode" /></td>
					<td>
						<input name="wf_execConditionName" class="inputsgl" style="width:400px" readonly>
						<input name="wf_execConditionId" type="hidden">
						<a href="#" onclick="selectConditionByFormula('wf_execConditionId', 'wf_execConditionName');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						<span class="com_help"><kmss:message bundle="sys-lbpmservice-node-checknode" key="lbpm.nodeType.checkNode.template.attr.conditionTitle" /></span>
					</td>
				</tr>
				<tr>
					<td width="100px"><kmss:message key="lbpm.nodeType.checkNode.template.attr.tipInfo" bundle="sys-lbpmservice-node-checknode" /></td>
					<td>
						<input name="wf_tipInfoName" class="inputsgl" style="width:400px" readonly>
						<input name="wf_tipInfoId" type="hidden">
						<a href="#" onclick="selectTipByFormula('wf_tipInfoId', 'wf_tipInfoName');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
					</td>
				</tr>
				<tr>
					<td width="100px"><kmss:message key="lbpmOperations.fdOperType.processor.handlerJump" bundle="sys-lbpmservice-node-checknode" /><kmss:message key="lbpm.nodeType.checkNode.template.attr.scope" bundle="sys-lbpmservice-node-checknode" /></td>
					<td>
						<input type="hidden" name="wf_canHandlerJumpNodeIds">
						<textarea name="wf_canHandlerJumpNodeNames" style="width:100%;height:50px" readonly></textarea>
						<a href="#" onclick="selectCanHandlerJumpNodes('wf_canHandlerJumpNodeIds', 'wf_canHandlerJumpNodeNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						<span class="com_help"><kmss:message bundle="sys-lbpmservice-node-checknode" key="lbpm.nodeType.checkNode.template.attr.scopeTitle" /></span>
					</td>
				</tr>
				<!-- 默认并审模式 -->
				<input name="wf_processType" type="hidden" value="1"/>
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.nodeOptions" bundle="sys-lbpmservice" /></td>
					<td colspan="2">
						<label>					
						<input name="wf_recalculateHandler" type="checkbox" value="true" >
						<kmss:message key="lbpm.nodeType.checkNode.template.attr.recalcHandlerDesc" bundle="sys-lbpmservice-node-checknode" />
						</label>
					</td>
				</tr>
				<c:import url="/sys/lbpmservice/node/common/node_handler_common_operation.jsp" charEncoding="UTF-8" />
				
				<c:import url="/sys/lbpmservice/node/common/node_notify_attribute.jsp" charEncoding="UTF-8" />
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.popedom" bundle="sys-lbpmservice" /></td>
					<td>
						<label>
							<input name="wf_canModifyMainDoc" type="checkbox" value="true">
							<kmss:message key="FlowChartObject.Lang.Node.canModifyMainDoc" bundle="sys-lbpmservice" />
						</label>
						&nbsp;&nbsp;
						<label>
							<input name="wf_canAddAuditNoteAtt" type="checkbox" checked value="true">
							<kmss:message key="FlowChartObject.Lang.Node.canAddAuditNoteAtt" bundle="sys-lbpmservice" />
						</label>
					</td>
				</tr>
				<tr>
		<td width="100px"><kmss:message key="FlowChartObject.Lang.Right.canModifyFlow" bundle="sys-lbpm-engine" /></td>
		<td>
			<label>
				<input name="wf_canModifyFlow" type="radio" value="true">
				<kmss:message key="FlowChartObject.Lang.Yes" bundle="sys-lbpm-engine" />
			</label><label>
				<input name="wf_canModifyFlow" type="radio" value="false" checked>
				<kmss:message key="FlowChartObject.Lang.No" bundle="sys-lbpm-engine" />
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
				<c:param name="passOperationType"><kmss:message key="lbpmOperations.fdOperType.processor.handlerGiveup" bundle="sys-lbpmservice-node-checknode" /></c:param>
			</c:import>
		</td>
	</tr>
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
	<c:import url="/sys/lbpm/flowchart/page/node_variant_attribute.jsp" charEncoding="UTF-8">
		<c:param name="nodeType" value="${param.nodeType}" />
		<c:param name="modelName" value="${param.modelName }" />
	</c:import>

</table>

<script>
//选择可跳转节点范围
function selectCanHandlerJumpNodes(idField, nameField){
	var data = new KMSSData(), NodeData = AttributeObject.NodeData;
	for(var i=0; i<FlowChartObject.Nodes.all.length; i++){
		var node = FlowChartObject.Nodes.all[i];
		if(node.Data.id == NodeData.id || node.Data.groupNodeId != null)
			continue;
		var nodDesc = AttributeObject.Utils.nodeDesc(node);
		if (nodDesc.isHandler || node.Type	== 'robotNode' || node.Type=='draftNode') {
			data.AddHashMap({id:node.Data.id, name:node.Data.id+"."+node.Data.name});
		}
	}
	var dialog = new KMSSDialog(true, true);
	dialog.winTitle = FlowChartObject.Lang.dialogTitle;
	dialog.AddDefaultOption(data);
	dialog.BindingField(idField, nameField, ";");
	dialog.Show();
}

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
AttributeObject.Init.AllModeFuns.push(function() {

	if(!handlerSelectType || handlerSelectType!="formula"){
		document.getElementById('SPAN_SelectType1').style.display='';
		document.getElementById('SPAN_SelectType2').style.display='none';
	}else{
		document.getElementById('SPAN_SelectType1').style.display='none';
		document.getElementById('SPAN_SelectType2').style.display='';
	}

	initHandlerSameSelect(ignoreOnHandlerSame,onAdjoinHandlerSame);
	
	AttributeObject.Utils.switchOrgAttributes(document.getElementsByName("wf_handlerIds")[0], handlerSelectType);
	
	var settingInfo = getSettingInfo();
	//审批意见附件开关开始
	if (!AttributeObject.NodeData["canAddAuditNoteAtt"]){
		var _isCanAddAuditNoteAtt = settingInfo["isCanAddAuditNoteAtt"];
		if (_isCanAddAuditNoteAtt === "false"){
			$("input[name='wf_canAddAuditNoteAtt']").prop("checked",false);
		}
	}
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


//判断是否非负整数
function isInt(i){
	var re = /^[0-9]+$/;
	return re.test(i);
}
function controlNumber(obj){
	obj.value=(parseInt((obj.value=obj.value.replace(/\D/g,''))==''||parseInt((obj.value=obj.value.replace(/\D/g,''))==0)?'0':obj.value,10));
}

AttributeObject.CheckDataFuns.push(function(data) {
	
	if(data.useOptHandlerOnly=="true" && data.optHandlerIds==""){
		alert('<kmss:message key="FlowChartObject.Lang.Node.checkOptHandlerEmpty" bundle="sys-lbpmservice" />');
		return false;
	}
	return true;
});
function initHandlerSameSelect(ignoreHandlerSame,adjoinHandlerSame){
	if(ignoreHandlerSame==null){
		ignoreHandlerSame = "true";
	}
	if(adjoinHandlerSame==null){
		adjoinHandlerSame = "true";
	}
	var selected = "1";
	if(ignoreHandlerSame == "true"){//兼容老数据配置
		if(adjoinHandlerSame=="true"){
			selected = "1";//相邻跳过
		}else{
			selected = "2";//跨节点跳过
		}
	}else{
		selected = "0";//不跳过
	}
	$("select[name='handlerSameSelect']").val(selected);
}
function switchHandlerSameSelect(thisObj){
	var selected = $(thisObj).val();
	var ignoreHandlerSameObj = $("input[name='wf_ignoreOnHandlerSame']");
	var adjoinHandlerSameObj = $("input[name='wf_onAdjoinHandlerSame']");
	if(selected=="1"){//相邻处理人重复跳过
		ignoreHandlerSameObj.val("true");
		adjoinHandlerSameObj.val("true");
	}else if(selected=="2"){//跨节点处理人重复跳过
		ignoreHandlerSameObj.val("true");
		adjoinHandlerSameObj.val("false");
	}else{//不跳过
		ignoreHandlerSameObj.val("false");
		adjoinHandlerSameObj.val("false");
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
function selectConditionByFormula(idField, nameField){
	Formula_Dialog(idField,
			nameField,
			FlowChartObject.FormFieldList, 
			"Boolean",
			null,
			"com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction",
			FlowChartObject.ModelName);
}
function selectTipByFormula(idField, nameField){
	Formula_Dialog(idField,
			nameField,
			FlowChartObject.FormFieldList, 
			"String",
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