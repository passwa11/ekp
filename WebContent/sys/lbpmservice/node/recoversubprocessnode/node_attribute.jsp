<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextareaGroupTagNew"%>
<table width="590px" id="Label_Tabel">
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Base" bundle="sys-lbpm-engine" />">
		<td>
			<table width="100%" class="tb_normal">
				<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8" />
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverTitle" bundle="sys-lbpmservice-node-subprocess" /></td>
					<td>
						<select id="subProcessNode" name="subProcessNode" onchange="clearParamTable();clearRecoverNote();">
						</select>
						<label><input type="checkbox" name="wf_recoverSubProcessNote" value="true" onclick="showRecoverSubNodes(this)"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverNote" bundle="sys-lbpmservice-node-subprocess" /></label>
					</td>
				</tr>
				<tr id="recoverSubProcessNote" style="display: none">
					<td width="100px">
						<kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverNoteNode" bundle="sys-lbpmservice-node-subprocess" />
						<span class="txtstrong">*</span>
					</td>
					<td>
						<input name="wf_recoverSubDisplayName" class="inputsgl" style="width:400px" placeholder="<kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverNodeName" bundle="sys-lbpmservice-node-subprocess" />">
						<input type="hidden" name="wf_recoverSubDisplayId">
						<span id="SPAN_OptSelectType1">
						<a href="#" onclick="Formula_Dialog('wf_recoverSubDisplayId','wf_recoverSubDisplayName',
								startNodeFormFieldList, 'Object'
								, recoverCallback, 'com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction;com.landray.kmss.sys.lbpmservice.formula.LbpmSubProcessFunction', FlowChartObject.ModelName);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
					</span> <br> <br>
						<input type="hidden" name="wf_recoverSubNoteNodeIds">
						<textarea name="wf_recoverSubNoteNodeNames" style="width:100%;height:50px" readonly></textarea>
						<a href="#" onclick="selectSubNodes('wf_recoverSubNoteNodeIds', 'wf_recoverSubNoteNodeNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
					</td>
				</tr>
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverVariableScope" bundle="sys-lbpmservice-node-subprocess" /></td>
					<td>
						<label><input type="radio" name="variableScope" value="2" checked><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverVariableScopePassed" bundle="sys-lbpmservice-node-subprocess" /></label>
						<label><input type="radio" name="variableScope" value="3"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverVariableScopeAbandon" bundle="sys-lbpmservice-node-subprocess" /></label>
						<label><input type="radio" name="variableScope" value="1"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverVariableScopeEnd" bundle="sys-lbpmservice-node-subprocess" /></label>
					</td>
				</tr>
				<tr id="recoverRuleTR">
					<td width="100px" ><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverRule" bundle="sys-lbpmservice-node-subprocess" /></td>
					<td >
						<label><input type="radio" name="recoverRuleType" onclick="changeRecoverRuleType(this);" value="1" checked><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverRuleAllEnd" bundle="sys-lbpmservice-node-subprocess" /></label>
						<label style="position: relative;" onmouseover='$("#recoverRuleType_1").show();' onmouseout='$("#recoverRuleType_1").hide();'>
							<img src="${KMSS_Parameter_ContextPath}sys/lbpmservice/resource/images/icon_help.png" style="margin-bottom:-2px;"></img>&nbsp;&nbsp;
							
							<div id="recoverRuleType_1" style="display:none;position: absolute; z-index:9999;left: 0px;top:0px;min-width: 130px; min-height: 24px;border: 1px solid #EAEDF3;background: #FFFFFF;">
								<div><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverRuleAllEndInfo" bundle="sys-lbpmservice-node-subprocess" /></div>
							</div>
						</label>
						
						<label><input type="radio" name="recoverRuleType" onclick="changeRecoverRuleType(this);" value="2"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverRuleAnyEnd" bundle="sys-lbpmservice-node-subprocess" /></label>
						<label style="position: relative;" onmouseover='$("#recoverRuleType_2").show();' onmouseout='$("#recoverRuleType_2").hide();'>
							<img src="${KMSS_Parameter_ContextPath}sys/lbpmservice/resource/images/icon_help.png" style="margin-bottom:-2px;"></img>&nbsp;&nbsp;
							<div id="recoverRuleType_2" style="display:none;position: absolute; z-index:9999;left: 0px;top:0px;min-width: 130px; min-height: 24px;border: 1px solid #EAEDF3;background: #FFFFFF;">
								<div><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverRuleAnyEndInfo" bundle="sys-lbpmservice-node-subprocess" /></div>
							</div>
						</label>
					
						
						<label><input type="radio" name="recoverRuleType"  onclick="changeRecoverRuleType(this);" value="3"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverRuleFormula" bundle="sys-lbpmservice-node-subprocess" /></label>
						<label style="position: relative;" onmouseover='$("#recoverRuleType_3").show();' onmouseout='$("#recoverRuleType_3").hide();'>
							<img src="${KMSS_Parameter_ContextPath}sys/lbpmservice/resource/images/icon_help.png" style="margin-bottom:-2px;"></img>&nbsp;&nbsp;
							<div id="recoverRuleType_3" style="display:none;position: absolute; z-index:9999;left: 0px;top:0px;min-width: 130px; min-height: 24px;border: 1px solid #EAEDF3;background: #FFFFFF;"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverRuleFormulaInfo" bundle="sys-lbpmservice-node-subprocess" /></div>
						</label>
						
						
						<div id="recoverRuleTypeDiv_1" style="display:none;">
						</div>
						
						<div id="recoverRuleTypeDiv_2" style="display:none;">
							<select name="anyoneAbandoned" style="width: 170px;">
 								<option value="1"><kmss:message key="FlowChartObject.Lang.Node.processMainAbandoned" bundle="sys-lbpmservice-node-subprocess" /> </option>
 								<option value="2"><kmss:message key="FlowChartObject.Lang.Node.processMainNoAbandoned" bundle="sys-lbpmservice-node-subprocess" /> </option>
 								<option value="3"><kmss:message key="FlowChartObject.Lang.Node.processMainNodeAbandoned" bundle="sys-lbpmservice-node-subprocess" /> </option>
 							</select>
						</div>

						<div id="recoverRuleTypeDiv_3" style="display:none;">
							<input type="hidden" name="formulaValueId">
							<input type="text" name="formulaValueName" style="width:85%" class="inputSgl">
							<a href="javascript:void(0);" 
								onclick="showRecoverRuleFormula();">
									<kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
							<span class="txtstrong">*</span>
							<br><br>
							<select name="formulaAbandoned" style="width: 170px;">
 								<option value="1"><kmss:message key="FlowChartObject.Lang.Node.processMainAbandoned" bundle="sys-lbpmservice-node-subprocess" /></option>
 								<option value="2"><kmss:message key="FlowChartObject.Lang.Node.processMainNoAbandoned" bundle="sys-lbpmservice-node-subprocess" /></option>
 								<option value="3"><kmss:message key="FlowChartObject.Lang.Node.processMainNodeAbandoned" bundle="sys-lbpmservice-node-subprocess" /></option>
 							</select>
						</div>
						
						
					</td>
				</tr>
				
				
				
				
				<tr>
					<td colspan="2">
					<!--
						<div class="txtstrong">
							<kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParameterIntroduce" bundle="sys-lbpmservice-node-subprocess" />
						</div>
					-->
						<table class="tb_normal" id="paramTable" style="width:100%">
							<tr>
								<td style="width:10%"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamNo" bundle="sys-lbpmservice-node-subprocess" /></td>
								<td style="width:30%"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamName" bundle="sys-lbpmservice-node-subprocess" /></td>
								<td style="width:40%"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamExpression" bundle="sys-lbpmservice-node-subprocess" /></td>
								<td style="width:30%"><a href="javascript:void(0);" onclick="addSubProcessParamenter(this);"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamAdd" bundle="sys-lbpmservice-node-subprocess" /></a></td>
							</tr>
							<tr KMSS_IsReferRow="1" style="display:none">
								<td KMSS_IsRowIndex="1"></td>
								<td>
									<input type="hidden" name="recoverParamenters.name.value">
									<input type="hidden" name="recoverParamenters.name.type">
									<input type="text" name="recoverParamenters.name.text" readonly="readonly" style="width:90%" class="inputSgl">
								</td>
								<td>
									<input type="hidden" name="recoverParamenters.expression.value">
									<input type="text" name="recoverParamenters.expression.text" readonly="readonly" style="width:80%" class="inputSgl">
									<a href="javascript:void(0);"  onclick="showParamenterFormulaDialog(this);"><kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
									<span class="txtstrong">*</span>
								</td>
								<td>
									<a href="javascript:void(0);" onclick="DocList_DeleteRow();"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamDel" bundle="sys-lbpmservice-node-subprocess" /></a>
									<a href="javascript:void(0);" onclick="DocList_MoveRow(-1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveUp" bundle="sys-lbpmservice" /></a>
									<a href="javascript:void(0);" onclick="DocList_MoveRow(1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveDown" bundle="sys-lbpmservice" /></a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td width="108px"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamSetValueRule" bundle="sys-lbpmservice-node-subprocess" /></td>
					<td>
						<label><input type="radio" name="recoverParamSetValueRule" value="1" onclick="changeRecoverParamSetValueRuleType(this);" checked><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamSetValueRule.update" bundle="sys-lbpmservice-node-subprocess" /></label>
						<label><input type="radio" name="recoverParamSetValueRule" value="2" onclick="changeRecoverParamSetValueRuleType(this);"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamSetValueRule.replace" bundle="sys-lbpmservice-node-subprocess" /></label>
						<div id="recoverParamSetValueRule_1" style="display:none;"><span class="com_help"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamSetValueRule.updateHelp" bundle="sys-lbpmservice-node-subprocess" /></span></div>
						<div id="recoverParamSetValueRule_2" style="display:none;"><span class="com_help"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamSetValueRule.replaceHelp" bundle="sys-lbpmservice-node-subprocess" /></span></div>
						<div id="recoverParamSetValueRule_3" style="display:none;"><span class="com_help"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamSetValueRule.appendHelp" bundle="sys-lbpmservice-node-subprocess" /></span></div>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table id="paramTableList" class="tb_normal" style="width:100%">
							
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="color: #47b5ea; text-align: center;">
						<a href="javascript:void(0);" onclick="addFieldMapping();"><kmss:message key="FlowChartObject.Lang.Node.detailedTableMapping" bundle="sys-lbpmservice-node-subprocess" /></a>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						
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
						<kmss:message key="FlowChartObject.Lang.Node.imgLink" bundle="sys-lbpm-engine" />111
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
//初始化参数
DocList_Info.push("paramTable");
DocList_Info.push("paramTable1");
//DocList_Info.push("paramTableList");
//初始化回收意见框的展示或隐藏
$(function(){
	if(AttributeObject.NodeData && typeof AttributeObject.NodeData.recoverSubProcessNote !="undefined" && AttributeObject.NodeData.recoverSubProcessNote ==="true"){
		$('#recoverSubProcessNote').show();
	}
})
var fieldMappingIndex=0;
/**
 * 添加映射关系行
 */
function addFieldMapping(){
	var table=document.getElementById("paramTableList");
	fieldMappingIndex++;
	var htmlCode=rowHtml(fieldMappingIndex);	
	//console.log(htmlCode);
	$("#paramTableList").append(htmlCode);
	DocList_Info.push("paramTable"+fieldMappingIndex);
	DocListFunc_Init();
}
/*
 * 选择映射列
 */
function selectMapping(index,type){
	var selectType="";
	// TODO 自定义表单附件
	var properties;
	switch (type) {
	case 0:
		selectType = "master";
		properties = FlowChartObject.FormFieldList;
		break;
	case 1:
		selectType = "child";
		properties = startNodeFormFieldList;
		break;
	default:
		break;
	}
	
	var kmssdata = new KMSSData();

	for (var i = 0; i < properties.length; i ++) {
		var p = properties[i];
		if(p.name.indexOf(".")<0){
			continue;
		}
		kmssdata.AddHashMap({id: p.name, name: p.label, type: p.type});
	}
	
	var dialog = new KMSSDialog(false, true);
	dialog.winTitle = '<kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamTile" bundle="sys-lbpmservice-node-subprocess" />';
	dialog.AddDefaultOption(kmssdata);
	dialog.BindingField(null, null, ";", false);
	dialog.SetAfterShow(function(data) {
		if (data != null) {
			var rows = data.GetHashMapArray();
			for (var i = 0; i < rows.length; i ++) {
				var row = rows[i];
				
				$("#mapping_"+selectType+"_value"+index).val(row.id);
				$("#mapping_"+selectType+"_type"+index).val(row.type);
				$("#mapping_"+selectType+"_text"+index).val(row.name);
			}
		}
	});
	dialog.notNull = true;
	dialog.Show();
}
/**
 * 删除行
 */
function deleteFieldMapping(tr){
	while (tr.tagName != 'TR') {
		tr = tr.parentNode;
	}
	$(tr).remove();
}
function rowHtml(index){
	var mapping_master_value="mapping_master_value"+index;
	var mapping_master_type="mapping_master_type"+index;
	var mapping_master_text="mapping_master_text"+index;
	var mapping_child_value="mapping_child_value"+index;
	var mapping_child_type="mapping_child_type"+index;
	var mapping_child_text="mapping_child_text"+index;
	var htmlCode='<tr><td>';
	htmlCode+='<kmss:message key="FlowChartObject.Lang.Node.masterDetailedTableMapping" bundle="sys-lbpmservice-node-subprocess" />';
	htmlCode+='<input type="hidden" name="'+mapping_master_value+'" id="'+mapping_master_value+'">';
	htmlCode+='<input type="hidden" name="'+mapping_master_type+'" id="'+mapping_master_type+'">';
	htmlCode+='<input type="text" style="width: 100px;" name="'+mapping_master_text+'" id="'+mapping_master_text+'" readonly="readonly" class="inputSgl">';
	htmlCode+='<a href="javascript:void(0);" style="color: #47b5ea;" onclick="selectMapping('+index+',0);"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamAdd" bundle="sys-lbpmservice-node-subprocess" /></a>';
	htmlCode+=',';
	htmlCode+='<kmss:message key="FlowChartObject.Lang.Node.childDetailedTableMapping" bundle="sys-lbpmservice-node-subprocess" />';
	htmlCode+='<input type="hidden" name="'+mapping_child_value+'" id="'+mapping_child_value+'">';
	htmlCode+='<input type="hidden" name="'+mapping_child_type+'" id="'+mapping_child_type+'">';
	htmlCode+='<input type="text" style="width: 100px;" name="'+mapping_child_text+'" id="'+mapping_child_text+'" readonly="readonly" class="inputSgl">';
	htmlCode+='<a href="javascript:void(0);" style="color: #47b5ea;" onclick="selectMapping('+index+',1);"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamAdd" bundle="sys-lbpmservice-node-subprocess" /></a>';
	htmlCode+='<a style="float: right;" href="javascript:void(0);" onclick="deleteFieldMapping(this)"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamDel" bundle="sys-lbpmservice-node-subprocess" /></a>';
	htmlCode+='<br>';
	htmlCode+='<br>';
	htmlCode+='<table class="tb_normal" id="paramTable'+index+'" style="width:100%">';
	htmlCode+='<tbody><tr>';
	htmlCode+='<td style="width:10%"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamNo" bundle="sys-lbpmservice-node-subprocess" /></td>';
	htmlCode+='<td style="width:30%"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamName" bundle="sys-lbpmservice-node-subprocess" /></td>';
	htmlCode+='<td style="width:40%"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamExpression" bundle="sys-lbpmservice-node-subprocess" /></td>';
	htmlCode+='<td style="width:30%"><a href="javascript:void(0);" onclick="addSubProcessParamenter(this,'+index+');"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamAdd" bundle="sys-lbpmservice-node-subprocess" /></a></td>';
	htmlCode+='</tr>';	
	htmlCode+='<tr KMSS_IsReferRow="1" style="display:none">';
	htmlCode+='<td KMSS_IsRowIndex="1"></td>';
	htmlCode+='<td>';
	htmlCode+='<input type="hidden" name="recoverParamenters.name.value'+index+'">';
	htmlCode+='<input type="hidden" name="recoverParamenters.name.type'+index+'">';
	htmlCode+='<input type="text" name="recoverParamenters.name.text'+index+'" readonly="readonly" style="width:90%" class="inputSgl">';
	htmlCode+='</td>';
	htmlCode+='<td>';
	htmlCode+='<input type="hidden" name="recoverParamenters.expression.value'+index+'">';
	htmlCode+='<input type="text" name="recoverParamenters.expression.text'+index+'" readonly="readonly" style="width:80%" class="inputSgl">';
	htmlCode+='<a href="javascript:void(0);"  onclick="showParamenterFormulaDialog(this,'+index+');"><kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>';
	htmlCode+='<span class="txtstrong">*</span>';
	htmlCode+='</td>';
	htmlCode+='<td>';
	htmlCode+='<a href="javascript:void(0);" onclick="DocList_DeleteRow();"><kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamDel" bundle="sys-lbpmservice-node-subprocess" /></a>';
	htmlCode+='<a href="javascript:void(0);" onclick="DocList_MoveRow(-1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveUp" bundle="sys-lbpmservice" /></a>';
	htmlCode+='<a href="javascript:void(0);" onclick="DocList_MoveRow(1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveDown" bundle="sys-lbpmservice" /></a>';	
	htmlCode+='</td>';
	htmlCode+='</tr>';
	htmlCode+='</tbody>';		
	htmlCode+='</table>';
	htmlCode+='</tr></td>';
	return htmlCode;
}
function formatJson(value) {
	return value.replace(/"/ig,'\\"').replace(/\r\n/ig,'\\r\\n');
}

AttributeObject.Init.AllModeFuns.push(initStartNodes,initRecoverConfig);

function nodeDataCheck(data){
	if (document.getElementsByName('subProcessNode')[0].value == '') {
		alert('<kmss:message key="FlowChartObject.Lang.Node.subprocessAlertSelectProcessNode" bundle="sys-lbpmservice-node-subprocess" />');
		return false;
	}
	var expressionValues= document.getElementsByName("recoverParamenters.expression.value");
	for (var i = 0; i < expressionValues.length; i ++) {
		var value = expressionValues[i].value;
		if (value == '') {
			alert('<kmss:message key="FlowChartObject.Lang.Node.subprocessAlertExpressionValueNotNull" bundle="sys-lbpmservice-node-subprocess" />');
			return false;
		}
	}
	var recoverRuleType = getRadioValue(document.getElementsByName('recoverRuleType'));
	if (recoverRuleType == '3') {
		if (document.getElementsByName('formulaValueName')[0].value == "") {
			alert('<kmss:message key="FlowChartObject.Lang.Node.subprocessAlertRecoverFormulaValueIdNotNull" bundle="sys-lbpmservice-node-subprocess" />');
			return false;
		}
	}
	var recoverSubDisplayName = $("[name='wf_recoverSubDisplayName']");
	var recoverSubNoteNodeNames = $("[name='wf_recoverSubNoteNodeNames']");
	var recoverChecked= $('input[name="wf_recoverSubProcessNote"]').prop('checked');
	if(recoverChecked && (recoverSubDisplayName.val() === '' || recoverSubNoteNodeNames.val() === '')){
		alert("<kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverNoteNodeValueNotNull" bundle="sys-lbpmservice-node-subprocess" />");
		return false;
	}

	//验证所有明细表映射关系
	for(var i=0;i<fieldMappingIndex;i++){
		var index=i+1;
		var table=document.getElementById("paramTable"+index);
		if(!table){
			continue;
		}
		var mapping_child_values = checkValueByName("mapping_child_value"+index);
		if(!mapping_child_values){
			return false;
		}
		var mapping_master_values = checkValueByName("mapping_child_value"+index); 
		if(!mapping_master_values){
			return false;
		}
		var mappingExpressions = checkValueByName("recoverParamenters.expression.value"+index);
		if(!mappingExpressions){
			return false;
		}
	}
	return true;
}


function checkValueByName(name){
	var objs=document.getElementsByName(name);
	for (var j = 0; j < objs.length; j ++) {			
		var value = objs[j].value;
		if (value == '') {
			alert('<kmss:message key="FlowChartObject.Lang.Node.subprocessAlertMappingValueNotNull" bundle="sys-lbpmservice-node-subprocess" />');
			return false;
		}
	}
	return true;
}

AttributeObject.CheckDataFuns.push(nodeDataCheck);

function writeJSON(data) {
	var getNameValue = function(name) {
		return (formatJson(document.getElementsByName(name)[0].value));
	};
	var config = [];
	config.push("{ \"subProcessNode\":");
	config.push("\"" + getNameValue('subProcessNode') + "\"");
	
	config.push(",\"variableScope\":");
	config.push(getRadioValue(document.getElementsByName('variableScope')));
	
	config.push(",\"recoverParamSetValueRule\":");
	config.push(getRadioValue(document.getElementsByName('recoverParamSetValueRule')));

	config.push(",\"recoverRule\":{\"type\":");
	config.push(getRadioValue(document.getElementsByName('recoverRuleType')));
	
	config.push(",\"anyoneAbandoned\":");
	config.push("\"" +getSelectValue(document.getElementsByName('anyoneAbandoned')[0])+ "\"");
	config.push(",\"formulaAbandoned\":");
	config.push("\"" +getSelectValue(document.getElementsByName('formulaAbandoned')[0])+ "\"");
	
	
	config.push(",\"expression\":{\"text\":");
	config.push("\"" + formatJson(document.getElementsByName('formulaValueName')[0].value) + "\"");
	config.push(",\"value\":");
	config.push("\"" + formatJson(document.getElementsByName('formulaValueId')[0].value) + "\"");
	config.push("}}");

	config.push(",\"recoverParamenters\":");
	config.push(getParamentersJson());
	config.push(",\"fieldMapping\":");
	config.push("[");
	config.push(getfieldMapping(1));
	config.push("]");
	config.push("}");
	//console.log(config.join(""));
	//console.log(JSON.parse(config.join("")));
	
	data['configContent'] = config.join("");

	AttributeObject.NodeObject.NeedReConfig = false;
}
AttributeObject.AppendDataFuns.push(writeJSON);

function getfieldMapping(index){
	var config=[];
	for(var i=0;i<fieldMappingIndex;i++){
		var index=i+1;
		var table=document.getElementById("paramTable"+index);
		if(!table){
			continue;
		}
		if(config.length>0){
			config.push(",");
		}
		config.push("{\"mapping_master_value\":");
		config.push("\""+document.getElementById("mapping_master_value"+index).value+"\",");
		config.push("\"mapping_master_type\":");
		config.push("\""+document.getElementById("mapping_master_type"+index).value+"\",");
		config.push("\"mapping_master_text\":");
		config.push("\""+document.getElementById("mapping_master_text"+index).value+"\",");
		config.push("\"mapping_child_value\":");
		config.push("\""+document.getElementById("mapping_child_value"+index).value+"\",");
		config.push("\"mapping_child_type\":");
		config.push("\""+document.getElementById("mapping_child_type"+index).value+"\",");
		config.push("\"mapping_child_text\":");
		config.push("\""+document.getElementById("mapping_child_text"+index).value+"\",");
		config.push("\"mappingParamenters\":");
		config.push(getParamentersJsonByIndex(index));	
		config.push("}");


		
	}	
	return config.join("");
}

function getParamentersJson() {
	var rtn = [];
	var recovervalues = document.getElementsByName("recoverParamenters.name.value");
	var texts = document.getElementsByName("recoverParamenters.name.text");
	var types = document.getElementsByName("recoverParamenters.name.type");
	//var notNulls = document.getElementsByName("startParamenters.name.notNull");
	var expressionValues= document.getElementsByName("recoverParamenters.expression.value");
	var expressionTexts= document.getElementsByName("recoverParamenters.expression.text");
	for (var i = 0; i < recovervalues.length; i ++) {
		rtn.push("{\"name\":{\"text\":\"" + formatJson(texts[i].value) 
				+ "\",\"value\":\"" + formatJson(recovervalues[i].value)
				+ "\",\"type\":\"" + formatJson(types[i].value)  + "\"}"
			    + ",\"expression\":{\"text\":\"" + formatJson(expressionTexts[i].value) 
				+ "\",\"value\":\"" + formatJson(expressionValues[i].value) + "\"}}");
	}
	return "[" + rtn.join(",") + "]";
}
function getParamentersJsonByIndex(index) {			
	var rtn = [];
	var recovervalues = document.getElementsByName("recoverParamenters.name.value"+index);
	var texts = document.getElementsByName("recoverParamenters.name.text"+index);
	var types = document.getElementsByName("recoverParamenters.name.type"+index);	
	var expressionValues= document.getElementsByName("recoverParamenters.expression.value"+index);
	var expressionTexts= document.getElementsByName("recoverParamenters.expression.text"+index);
	for (var i = 0; i < recovervalues.length; i ++) {
		rtn.push("{\"name\":{\"text\":\"" + formatJson(texts[i].value) 
				+ "\",\"value\":\"" + formatJson(recovervalues[i].value)
				+ "\",\"type\":\"" + formatJson(types[i].value)  + "\"}"
			    + ",\"expression\":{\"text\":\"" + formatJson(expressionTexts[i].value) 
				+ "\",\"value\":\"" + formatJson(expressionValues[i].value) + "\"}}");
	}
	return "[" + rtn.join(",") + "]";
}

function initStartNodes() {
	var nodesObject = GetAllPreNodes(AttributeObject.NodeObject, AttributeObject.NodeObject, function(node){return node.Type == "startSubProcessNode"});//FlowChartObject.Nodes.all;
	var select = document.getElementById("subProcessNode");
	select.options.length = 0;
	var l = nodesObject.length;
	//select.options.add(new Option("=== 请选择 ===", "false"));
	for(var i = 0; i < l; i++){
		var node = nodesObject[i];
		//if(node.Data.id == NodeData.id)
		//	continue;
		//if (node.Type == "startSubProcessNode") {
			select.options.add(new Option(node.Data.id + ":" + node.Data.name, node.Data.id));
		//}
	}
}
function UtilContains(array, obj) {
	for (var i = 0; i < array.length; i ++) {
		if (obj == array[i]) {
			return true;
		}
	}
	return false;
}
function GetAllPreNodes(now, begin, condition, result, tmp) {
	now = now || NodeObject;
	begin = begin || NodeObject;
	result = result != null ? result : [];
	tmp = tmp != null ? tmp : [];
	if(now.LineIn) {
		if(now.LineIn.length > 1) {
			tmp.push(now); // 避免死循环
		}
		for(var i = 0; i < now.LineIn.length; i++){
			var line = now.LineIn[i];
			var startNode = line.StartNode;
			if (startNode != null && startNode.Data.id != begin.Data.id && !UtilContains(tmp, startNode)) {
				if (condition == null || condition(startNode)) {
					result.push(startNode);
				}
				GetAllPreNodes(startNode, begin, condition, result, tmp);
			}
		}
	}
	return result;
}
function initRecoverConfig() {
	var config = AttributeObject.NodeData['configContent'];
	if (config != null && config != "") {
		//去掉回车换行        
		config = config.replace(/[\r\n]/g,"");
		
		var json = eval("("+config+")");
		
		initModelName(json);
		initParams(json);
		initFieldMapping(json);
	} else {
		initFormulaKMSSDataFormFieldList();
		resetStartCountRelation();
	}
	changeRecoverRuleType();
	changeRecoverParamSetValueRuleType();
}
/**
 * 加载明细表映射关系
 */
function initFieldMapping(json){
	var fieldMappings=json.fieldMapping;
	if(fieldMappings&&fieldMappings.length>0){
		for(var i=0;i<fieldMappings.length;i++){
			var fieldMapping=fieldMappings[i];
			var index=i+1;
			var htmlCode=rowHtml(index);	
			$("#paramTableList").append(htmlCode);
			DocList_Info.push("paramTable"+index);
			DocListFunc_Init();
			fieldMappingIndex=index;
			
			document.getElementById("mapping_master_value"+index).value=fieldMapping.mapping_master_value;
			document.getElementById("mapping_master_type"+index).value=fieldMapping.mapping_master_type;
			document.getElementById("mapping_master_text"+index).value=fieldMapping.mapping_master_text;
			document.getElementById("mapping_child_value"+index).value=fieldMapping.mapping_child_value;
			document.getElementById("mapping_child_type"+index).value=fieldMapping.mapping_child_type;
			document.getElementById("mapping_child_text"+index).value=fieldMapping.mapping_child_text;
			initMappingParams(fieldMapping,index);//映射关系明细
		}
	}
	
}
/**
 * 加载映射关系明细
 */
function initMappingParams(json,index) {
	var params = json.mappingParamenters;
	for (var i = 0; i < params.length; i ++) {
		var row = params[i];
		//支持动态生成的明细表
		var jsonstr='{';
		jsonstr+='"recoverParamenters.name.type'+index+'":"'+row.name.type+'",';
		jsonstr+='"recoverParamenters.name.value'+index+'":"'+row.name.value+'",';
		jsonstr+='"recoverParamenters.name.text'+index+'":"'+row.name.text+'",';
		jsonstr+='"recoverParamenters.expression.value'+index+'":"'+ row.expression.value+'",';
		jsonstr+='"recoverParamenters.expression.text'+index+'":"' +row.expression.text+'"';
		jsonstr+='}';
		//console.log(jsonstr);
		var jsonData=JSON.parse(jsonstr);
		DocList_AddRow("paramTable"+index, [],jsonData);//paramTable
	}
}
function initModelName(json) {
	
	setRadio(document.getElementsByName('variableScope'), json.variableScope);
	setRadio(document.getElementsByName('recoverParamSetValueRule'), json.recoverParamSetValueRule ? json.recoverParamSetValueRule : 1);
	setRadio(document.getElementsByName('recoverRuleType'), json.recoverRule.type);
	
	//初始化回收规则是公式或者任意一个子流程结束是否废弃其他子流程选项
	var formulaAbandonedSel=document.getElementsByName('formulaAbandoned')[0];
	var formulaAbandonedValue=json.recoverRule.formulaAbandoned? json.recoverRule.formulaAbandoned : "1";
	for (var i = 0; i < formulaAbandonedSel.options.length; i ++) {
		var formulaAbandonedSelVal = formulaAbandonedSel.options[i];
		if (formulaAbandonedSelVal.value == formulaAbandonedValue) {
			formulaAbandonedSelVal.selected = true;
			break;
		} 
	}
	
	
	
	
	var anyoneAbandonedSel=document.getElementsByName('anyoneAbandoned')[0];
	var anyoneAbandonedValue=(json.recoverRule.anyoneAbandoned? json.recoverRule.anyoneAbandoned : "1");
	for (var i = 0; i < anyoneAbandonedSel.options.length; i ++) {
		var anyoneAbandonedSelVal = anyoneAbandonedSel.options[i];
		if (anyoneAbandonedSelVal.value == anyoneAbandonedValue) {
			anyoneAbandonedSelVal.selected = true;
			break;
		} 
	}
	
	
	
	var select = document.getElementById("subProcessNode");
	var subProcessNodeIsOk = false;
	for (var i = 0; i < select.options.length; i ++) {
		var opt = select.options[i];
		if (opt.value == json.subProcessNode) {
			opt.selected = true;
			subProcessNodeIsOk = true;
			break;
		}
	}
	if (!subProcessNodeIsOk) {
		json.recoverParamenters = [];
		alert('<kmss:message key="FlowChartObject.Lang.Node.subprocessAlertReSelectProcessNode" bundle="sys-lbpmservice-node-subprocess" />');
	} else {
		initFormulaKMSSDataFormFieldList();
		resetStartCountRelation();
	}
	if (json.recoverRule.expression.value != null && json.recoverRule.expression.text != null) {
		document.getElementsByName('formulaValueId')[0].value = json.recoverRule.expression.value;
		document.getElementsByName('formulaValueName')[0].value = json.recoverRule.expression.text;
	}
	if (json.recoverRule.type == 3) {
		//document.getElementById("recoverRuleType_3").style.display = "";
	}
}
function initParams(json) {
	var params = json.recoverParamenters;
	for (var i = 0; i < params.length; i ++) {
		var row = params[i];
		DocList_AddRow("paramTable", [],
				{
					"recoverParamenters.name.type" : row.name.type, 
					"recoverParamenters.name.value" : row.name.value,
					"recoverParamenters.name.text" : row.name.text,
					"recoverParamenters.expression.value" : row.expression.value,
					"recoverParamenters.expression.text" : row.expression.text
				});
	}
}

function addSubProcessParamenter(table,index) {
	while (table.tagName != 'TABLE') {
		table = table.parentNode;
	}

	// TODO 自定义表单附件
	var properties = FlowChartObject.FormFieldList;
	var kmssdata = new KMSSData();

	for (var i = 0; i < properties.length; i ++) {
		var p = properties[i];
		if(index){ 
			if(p.name.indexOf(".")!=-1){
				//只添加明细表属性
				kmssdata.AddHashMap({id: p.name, name: p.label, type: p.type});
			}
		}else{
			kmssdata.AddHashMap({id: p.name, name: p.label, type: p.type});
		}
	}
	
	var dialog = new KMSSDialog(false, true);
	dialog.winTitle = '<kmss:message key="FlowChartObject.Lang.Node.subprocessRecoverParamTile" bundle="sys-lbpmservice-node-subprocess" />';
	dialog.AddDefaultOption(kmssdata);
	dialog.BindingField(null, null, ";", false);
	dialog.SetAfterShow(function(data) {
		if (data != null) {
			var rows = data.GetHashMapArray();
			for (var i = 0; i < rows.length; i ++) {
				var row = rows[i];
				if(index){
					//支持动态生成的明细表
					var jsonstr='{';
					jsonstr+='"recoverParamenters.name.type'+index+'":"'+row.type+'",';
					jsonstr+='"recoverParamenters.name.value'+index+'":"'+row.id+'",';
					jsonstr+='"recoverParamenters.name.text'+index+'":"'+row.name+'"';
					jsonstr+='}';
					//console.log(jsonstr);
					var jsonData=JSON.parse(jsonstr);
					DocList_AddRow(table, [],jsonData);
				}
				else{
					DocList_AddRow(table, [],
							{
								"recoverParamenters.name.type" : row.type, 
								"recoverParamenters.name.value" : row.id,
								"recoverParamenters.name.text" : row.name
							});
				}
				
			}
		}
	});
	dialog.notNull = true;
	dialog.Show();
}

// 设置
function resetStartCountRelation() {
	var configContent = getStartNodeConfigContent();
	if (configContent == null) {
		return;
	}
	var json = eval("(" + configContent + ")");
	var recoverRuleDisplay = document.getElementById("recoverRuleTR");
	if (json.startCountType == '1' || json.startCountType == 1) {
		recoverRuleDisplay.style.display = 'none';
	} else {
		recoverRuleDisplay.style.display = '';
	}
}
function showRecoverSubNodes(note){
	// 开关打开，则显示回收意见页签
	if($(note).prop('checked')){
		$('#recoverSubProcessNote').show();
	}else{
		// 清空值再隐藏
		clearRecoverNote();
		$('#recoverSubProcessNote').hide();
	}
}
function clearRecoverNote(){
	$("[name='wf_recoverSubDisplayName']").val('');
	$("[name='wf_recoverSubDisplayId']").val('');
	$("[name='wf_recoverSubNoteNodeNames']").val('');
	$("[name='wf_recoverSubNoteNodeIds']").val('');
}
function recoverCallback(arg){
	if(arg && arg.data && (arg.data).length>0){
		if(arg.data[0].id != ''){
			$("[name='wf_recoverSubDisplayName']").attr("readonly",true);
		}else{
			$("[name='wf_recoverSubDisplayName']").attr("readonly",false);
		}
	}
}
// 回收子流程意见节点选择
function selectSubNodes(idField, nameField){
	var configContent = getStartNodeConfigContent();
	if (configContent == null) {
		return ;
	}
	var url = getFormulaKMSSDataUrl(configContent);
	// 添加类型以便于识别是回收子流程的节点获取
	url += "&recoverType=1";
	var dataSend = new KMSSData();
	var detailXml;
	dataSend.SendToBean(url, function(http_request) {
		var responseText = http_request.responseText;

		var json = http_request.GetHashMapArray();
		if (json[0].detailXml){
			detailXml = json[0].detailXml;
		}
		var data = new KMSSData();
		if(detailXml){
			// 子流程图对象
			var lbpmFlow = WorkFlow_LoadXMLData(detailXml);
			for(var i=0; i<lbpmFlow.nodes.length; i++){
				var node = lbpmFlow.nodes[i];
				if ((_isHandler(node) && !_isDraftNode(node))|| node.XMLNODENAME == "embeddedSubFlowNode" || node.XMLNODENAME == "splitNode") {
					
					data.AddHashMap({id:node.id, name:node.id+"."+node.name});
					if(node.XMLNODENAME == "embeddedSubFlowNode"){
						var fdContent = getContentByRefId(node.embeddedRefId);
						if(fdContent){
							//嵌入的流程图对象
							var embeddedFlow = WorkFlow_LoadXMLData(fdContent);
							for(var j = 0;j<embeddedFlow.nodes.length;j++){
								var eNode = embeddedFlow.nodes[j];
								if((_isHandler(eNode) && !_isDraftNode(eNode))){
									data.AddHashMap({id:node.id+"-"+eNode.id, name:node.id+"."+node.name+"("+eNode.id+"."+eNode.name+")"});
								}
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
	});

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


function getStartNodeConfigContent() {
	var subProcessNode = document.getElementById("subProcessNode");
	if(!subProcessNode.value) {
		return null;
	}
	var node = FlowChartObject.Nodes.GetNodeById(subProcessNode.value);
	if (node == null)
		return null;
	var configContent = node.Data.configContent;
	return configContent;
}

function getFormulaKMSSDataUrl(configContent) {
	var json = eval("(" + configContent + ")");
	var url = "subProcessDictService&modelName=" + json.subProcess.modelName + "&templateId=" + json.subProcess.templateId;
	if (json.subProcess.dictBean != null && json.subProcess.dictBean != "") {
		url = json.subProcess.dictBean;
	}
	return url;
}

// 启动节点所选流程属性
var startNodeFormFieldList = [];

function initFormulaKMSSDataFormFieldList() {
	startNodeFormFieldList = [];
	var configContent = getStartNodeConfigContent();
	if (configContent == null) {
		return ;
	}
	var kmssData = new KMSSData();
	kmssData.SendToBean(getFormulaKMSSDataUrl(configContent) , function(data) {
		var rows = data.GetHashMapArray();
		var formFieldList = [];
		for (var i = 0; i < rows.length; i ++) {
			var row = rows[i];
			formFieldList.push({
				name: row.id,
				label: row.name,
				type: row.type
			});
		}
		startNodeFormFieldList = formFieldList;
	});
}

// 检查启动节点配置
function checkStartNodeConfig() {
	var configContent = getStartNodeConfigContent();
	if (configContent == null) {
		alert('<kmss:message key="FlowChartObject.Lang.Node.subprocessAlertConfigStartNode" bundle="sys-lbpmservice-node-subprocess" />');
		return false;
	}
	return true;
}

//参数公式对话框
function showParamenterFormulaDialog(tr,index) {
	while (tr.tagName != 'TR') {
		tr = tr.parentNode;
	}
	if (!checkStartNodeConfig()) {
		return ;
	}
	var inputs = tr.getElementsByTagName("input");
	var value = getElementsByName(inputs, "recoverParamenters.expression.value");
	var text = getElementsByName(inputs, "recoverParamenters.expression.text");
	var type = getElementsByName(inputs, "recoverParamenters.name.type");
	if(index){
		value = getElementsByName(inputs, "recoverParamenters.expression.value"+index);
		text = getElementsByName(inputs, "recoverParamenters.expression.text"+index);
		type = getElementsByName(inputs, "recoverParamenters.name.type"+index);
	}
	
	if(type.value == "Attachment"  || type.value == "Attachment[]") {
		var attachmentdata = new KMSSData();
		for(var i = 0; i < startNodeFormFieldList.length; i++) {
			var p = startNodeFormFieldList[i];
			if(p.type == "Attachment" || p.type == "Attachment[]") {
			//if(p.type == "Attachment") {
				var pp={name:p.name,label:p.label};
				if(p .type == "Attachment[]"){
					pp.name="$"+p.name+"$";
					pp.label="$"+p.label+"$";
				}
				attachmentdata.AddHashMap({id: pp.name, name: pp.label, type: p.type});
			}
		}
		var dialog = new KMSSDialog(false, true);
		dialog.AddDefaultOption(attachmentdata);
		dialog.BindingField(value, text, ";", false);
		dialog.notNull = true;
		dialog.Show();
	} else {
		var tv = type.value;
		if(tv == "BigDecimal_Money[]"){
			tv = "BigDecimal[]";
		}
		if(tv == "BigDecimal_Money"){
			tv = "BigDecimal";
		}
		Formula_Dialog(value, text, startNodeFormFieldList, tv);
	}
}

function showRecoverRuleFormula() {
	if (!checkStartNodeConfig()) {
		return ;
	}
	Formula_Dialog('formulaValueId','formulaValueName', startNodeFormFieldList, 'Boolean'
			, null, "com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction;com.landray.kmss.sys.lbpmservice.formula.LbpmSubProcessFunction");
}

function changeRecoverRuleType() {
	var ruleTypes = document.getElementsByName("recoverRuleType");
	for (var i = 0; i < ruleTypes.length; i ++) {
		var ruleType = ruleTypes[i];
		var div = document.getElementById("recoverRuleTypeDiv_" + ruleType.value);
		if (ruleType.checked) {
			div.style.display = "";
		} else {
			div.style.display = "none";
		}
	}
}

function changeRecoverParamSetValueRuleType() {
	var ruleTypes = document.getElementsByName("recoverParamSetValueRule");
	for (var i = 0; i < ruleTypes.length; i ++) {
		var ruleType = ruleTypes[i];
		var div = document.getElementById("recoverParamSetValueRule_" + ruleType.value);
		if (ruleType.checked) {
			div.style.display = "";
		} else {
			div.style.display = "none";
		}
	}
}

function clearParamTable() {
	// 重新计算数据字典
	initFormulaKMSSDataFormFieldList();
	resetStartCountRelation();
	
	var table = document.getElementById("paramTable");
	var rows = table.rows;
	if (rows.length < 2) {
		return;
	}
	var l = rows.length - 1;
	for (var i = l; i > 0; i --) {
		DocList_DeleteRow(rows[i]);
	}
}
function getRadioValue(radios) {
	for (var i = 0; i < radios.length; i ++) {
		var radio = radios[i];
		if (radio.checked) {
			return radio.value;
		}
	}
}

function getSelectValue(selectes) {
	for (var i = 0; i < selectes.options.length; i ++) {
		
		var selectTemp = selectes.options[i];
		if (selectTemp.selected) {
			return selectTemp.value;
		}
	}
	return "";
}


function setRadio(radios, value) {
	value = value.toString();
	for (var i = 0; i < radios.length; i ++) {
		var radio = radios[i];
		if (radio.value == value) {
			radio.checked = true;
		} else {
			radio.checked = false;
		}
	}
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
