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
				<c:import url="/sys/lbpmservice/node/common/node_handler_common_operation.jsp" charEncoding="UTF-8" />
				<c:import url="/sys/lbpmservice/node/common/node_notify_attribute.jsp" charEncoding="UTF-8" />
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.popedom" bundle="sys-lbpmservice" /></td>
					<td>
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
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.dayOfHandle" bundle="sys-lbpmservice" /></td>
					<td>
					<kmss:message key="FlowChartObject.Lang.Node.rejectNotifyDraft1" bundle="sys-lbpmservice" />
					<input name="wf_rejectNotifyDraft" class="inputsgl" value="0" size="3" style="text-align:center"><kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
					<input name="wf_hourOfRejectNotifyDraft" class="inputsgl" value="0" size="3" style="text-align:center"><kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
					<input name="wf_minuteOfRejectNotifyDraft" class="inputsgl" value="0" size="3" style="text-align:center"><kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
					<kmss:message key="FlowChartObject.Lang.Node.rejectNotifyDraft2" bundle="sys-lbpmservice" /><br>
					<span class="com_help"><kmss:message key="FlowChartObject.Lang.Node.rejectNotifyDraft3" bundle="sys-lbpmservice" /></span>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<script>
	
	AttributeObject.Init.EditModeFuns.push(function(nodeData) {
		//多语言
		_initPropLang4Edit("nodeDesc",nodeData,"description","_");
		var settingInfo = getSettingInfo();
		//审批意见附件开关开始
		if (!AttributeObject.NodeData["canAddAuditNoteAtt"]){
			var _isCanAddAuditNoteAtt = settingInfo["isCanAddAuditNoteAtt"];
			if (_isCanAddAuditNoteAtt === "false"){
				$("input[name='wf_canAddAuditNoteAtt']").prop("checked",false);
			}
		}
		//过期处理默认值
		if (!AttributeObject.NodeData["rejectNotifyDraft"]){
			var _rejectNotifyDraft = settingInfo["rejectNotifyDraft"];
			if (_rejectNotifyDraft){
				$("input[name='wf_rejectNotifyDraft']").val(_rejectNotifyDraft);
			}
		}
		if (!AttributeObject.NodeData["hourOfRejectNotifyDraft"]){
			var _hourOfRejectNotifyDraft = settingInfo["hourOfRejectNotifyDraft"];
			if (_hourOfRejectNotifyDraft){
				$("input[name='wf_hourOfRejectNotifyDraft']").val(_hourOfRejectNotifyDraft);
			}
		}
		if (!AttributeObject.NodeData["minuteOfRejectNotifyDraft"]){
			var _minuteOfRejectNotifyDraft = settingInfo["minuteOfRejectNotifyDraft"];
			if (_minuteOfRejectNotifyDraft){
				$("input[name='wf_minuteOfRejectNotifyDraft']").val(_minuteOfRejectNotifyDraft);
			}
		}
	});
	AttributeObject.Init.ViewModeFuns.push(function(nodeData) {
		//多语言
		_initPropLang4View("nodeDesc",nodeData,"description","_");
		var settingInfo = getSettingInfo();
		//审批意见附件开关开始
		if (!AttributeObject.NodeData["canAddAuditNoteAtt"]){
			var _isCanAddAuditNoteAtt = settingInfo["isCanAddAuditNoteAtt"];
			if (_isCanAddAuditNoteAtt === "false"){
				$("input[name='wf_canAddAuditNoteAtt']").prop("checked",false);
			}
		}
		//过期处理默认值
		if (!AttributeObject.NodeData["rejectNotifyDraft"]){
			var _rejectNotifyDraft = settingInfo["rejectNotifyDraft"];
			if (_rejectNotifyDraft){
				$("input[name='wf_rejectNotifyDraft']").val(_rejectNotifyDraft);
			}
		}
		if (!AttributeObject.NodeData["hourOfRejectNotifyDraft"]){
			var _hourOfRejectNotifyDraft = settingInfo["hourOfRejectNotifyDraft"];
			if (_hourOfRejectNotifyDraft){
				$("input[name='wf_hourOfRejectNotifyDraft']").val(_hourOfRejectNotifyDraft);
			}
		}
		if (!AttributeObject.NodeData["minuteOfRejectNotifyDraft"]){
			var _minuteOfRejectNotifyDraft = settingInfo["minuteOfRejectNotifyDraft"];
			if (_minuteOfRejectNotifyDraft){
				$("input[name='wf_minuteOfRejectNotifyDraft']").val(_minuteOfRejectNotifyDraft);
			}
		}
	});

	AttributeObject.AppendDataFuns.push(function(nodeData){
		//var langs=[{"lang":"zh-CN","value":"经理审批"},{"lang":"en-US","value":"Manager Auditing"}];
		_propLang4AppendData("nodeDesc",nodeData,"description","_");
	});

	</script>

	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Popedom" bundle="sys-lbpmservice" />">
		<td>
		<c:import url="/sys/lbpmservice/node/common/node_right_attribute.jsp" charEncoding="UTF-8" />
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
	<c:import url="/sys/lbpmservice/node/common/node_custom_notify_attribute.jsp" charEncoding="UTF-8">
		<c:param name="nodeType" value="${param.nodeType}" />
		<c:param name="modelName" value="${param.modelName }" />
	</c:import>
</table>