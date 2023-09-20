<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextareaGroupTagNew"%>
<style>
.inputread {border:0px; color:#868686;background-color:#f6f6f6;}
</style>
<!-- 自由流的起草节点属性面板 -->
<c:choose>
	<c:when test="${JsParam.isFixedNode eq 'true'}">
		<table width="590px" id="Label_Tabel">
			<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Base" bundle="sys-lbpm-engine" />">
				<td>
					<table width="100%" class="tb_normal">
						<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8">
							<c:param name="flowType" value="1" />
						</c:import>
						<c:import url="/sys/lbpmservice/node/common/node_handler_common_operation.jsp" charEncoding="UTF-8">
						</c:import>
						<tr>
							<td width="100px"><bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.notify.type" /></td>
							<td id="NODE_TD_notifyType">
								<kmss:editNotifyType property="node_notifyType" value="no" /><br />
							</td>
						</tr>
						<tr>
							<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.popedom" bundle="sys-lbpmservice" /></td>
							<td>
								<label>
									<input name="wf_canAddAuditNoteAtt" type="checkbox" checked value="true">
									<kmss:message key="FlowChartObject.Lang.Node.canAddAuditNoteAtt" bundle="sys-lbpmservice" />
								</label>
							</td>
						</tr>
						<tr style="display:none">
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
						<tr style="display:none">
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
						<tr style="display:none">
							<td width="100px"><kmss:message key="FlowChartObject.Lang.Right.canModifyFlow" bundle="sys-lbpm-engine" /></td>
							<td colspan="2">
								<label>
									<input name="wf_canModifyFlow" type="radio" value="true" checked>
									<kmss:message key="FlowChartObject.Lang.Yes" bundle="sys-lbpm-engine" />
								</label><label>
									<input name="wf_canModifyFlow" type="radio" value="false">
									<kmss:message key="FlowChartObject.Lang.No" bundle="sys-lbpm-engine" />
								</label>
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
	</c:when>
	<c:otherwise>
		<table width="420px" id="Label_Tabel">
			<tr>
				<td style="background-color:#f6f6f6 !important;">
					<table width="95%" style="background-color:#f6f6f6 !important;" class="tb_normal">
						<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8">
							<c:param name="flowType" value="1" />
						</c:import>
						<c:import url="/sys/lbpmservice/node/common/node_handler_common_operation.jsp" charEncoding="UTF-8">
							<c:param name="isFreeFlow" value="true" />
						</c:import>
						<tr>
							<td width="100px"><bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.notify.type" /></td>
							<td id="NODE_TD_notifyType">
								<kmss:editNotifyType property="node_notifyType" value="no" /><br />
							</td>
						</tr>
						<tr>
							<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.popedom" bundle="sys-lbpmservice" /></td>
							<td>
								<label>
									<input name="wf_canAddAuditNoteAtt" type="checkbox" checked value="true">
									<kmss:message key="FlowChartObject.Lang.Node.canAddAuditNoteAtt" bundle="sys-lbpmservice" />
								</label>
							</td>
						</tr>
						<c:import url="/sys/lbpmservice/node/common/node_freeflow_more_attribute.jsp" charEncoding="UTF-8">
						</c:import>
						<tr style="display:none">
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
						<tr style="display:none">
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
						<tr style="display:none">
							<td width="100px"><kmss:message key="FlowChartObject.Lang.Right.canModifyFlow" bundle="sys-lbpm-engine" /></td>
							<td colspan="2">
								<label>
									<input name="wf_canModifyFlow" type="radio" value="true" checked>
									<kmss:message key="FlowChartObject.Lang.Yes" bundle="sys-lbpm-engine" />
								</label><label>
									<input name="wf_canModifyFlow" type="radio" value="false">
									<kmss:message key="FlowChartObject.Lang.No" bundle="sys-lbpm-engine" />
								</label>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</c:otherwise>
</c:choose>


	<script>
	AttributeObject.Init.AllModeFuns.push(function() {
		var settingInfo = getSettingInfo();
		var notifyType = AttributeObject.NodeData["notifyType"];
		if (!notifyType) {
			notifyType = settingInfo["defaultNotifyType"];
		}
		$("input[name^='__notify_type_']:checkbox").each(function(index,element){
			if(notifyType && notifyType.indexOf($(element).val())>-1){
				$(element).attr("checked","true");
			}else{
				$(element).removeAttr("checked");
			}
		});
		if (!AttributeObject.NodeData["canAddAuditNoteAtt"]){
			var _isCanAddAuditNoteAtt = settingInfo["isCanAddAuditNoteAtt"];
			if (_isCanAddAuditNoteAtt === "false"){
				$("input[name='wf_canAddAuditNoteAtt']").prop("checked",false);
			}
		}
		
		// 自由流实时保存节点数据
		$("input[name^='__notify_type_']:checkbox").change(function(){
			saveNodeData();
		});
		$("input[name^='_wf_name_']").change(function(){
			saveNodeData();
		});
		$("input[name^='wf_']").change(function(){
			saveNodeData();
		});
		$("input[name^='_wf_name_']").change(function(){
			saveNodeData();
		});
	});
	AttributeObject.Init.EditModeFuns.push(function(nodeData) {
		//多语言
		_initPropLang4Edit("nodeDesc",nodeData,"description","_");
	});
	AttributeObject.Init.ViewModeFuns.push(function(nodeData) {
		//多语言
		_initPropLang4View("nodeDesc",nodeData,"description","_");
	});

	AttributeObject.AppendDataFuns.push(function(nodeData){
		//var langs=[{"lang":"zh-CN","value":"经理审批"},{"lang":"en-US","value":"Manager Auditing"}];
		_propLang4AppendData("nodeDesc",nodeData,"description","_");
		
		var notifyType = "";
		$("input[name^='__notify_type_']:checkbox:checked").each(function(index,element){
			notifyType+=";"+$(element).val();
		});
		if(notifyType){
			notifyType = notifyType.substring(1);
			nodeData["notifyType"]=notifyType;
		}else{
			nodeData["notifyType"]=null;
		}	
	});

	</script>