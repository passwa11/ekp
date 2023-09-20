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
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.handlerNames" bundle="sys-lbpmservice" /></td>
					<td>
						<label><input type="radio" name="wf_handlerSelectType" value="org" onclick="switchHandlerSelectType(value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_handlerSelectType" value="matrix" onclick="switchHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectOrgMatrix" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_handlerSelectType" value="formula" onclick="switchHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_handlerSelectType" value="rule" onclick="switchHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectRule" bundle="sys-lbpmservice" /></label>
						<input name="wf_handlerNames" class="inputsgl" style="width:400px" readonly>
						<input name="wf_handlerIds" type="hidden" orgattr="handlerIds:handlerNames">
						<span id="SPAN_SelectType1">
						<a href="#" onclick="Dialog_Address(true, 'wf_handlerIds', 'wf_handlerNames', null, ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<span id="SPAN_SelectType2" style="display:none ">
						<a href="#" onclick="selectByFormula('wf_handlerIds', 'wf_handlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<span id="SPAN_SelectType3" style="display:none "><br/>
						<div style="padding-top:6px">
						<kmss:message key="FlowChartObject.Lang.Node.orgMatrix" bundle="sys-lbpmservice" /> &nbsp;&nbsp;
						<input name="orgMatrixId" type="hidden">
						<input name="orgMatrixName" class="inputsgl" style="width:75%" readonly>
						<a href="#" onclick="selectOrgMatrix();"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a><br/>
						<input name="matrixVersion" type="hidden">
						<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.results" bundle="sys-lbpmservice" /> &nbsp;&nbsp;
						<input name="matrixResultId" type="hidden">
						<input name="matrixResultName" class="inputsgl" style="width:75%" readonly>
						<a href="#" onclick="selectMatrixResultField();"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a><br/>
						<div style="margin-top:5px;">
							<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.resultsMoreThanOne" bundle="sys-lbpmservice" />
							<label style="margin-right:10px;"><input type="radio" name="matrixResultOption" value="1" checked>
								<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.resultOptionOfFirst" bundle="sys-lbpmservice" /></label>
							<label style="margin-right:10px;"><input type="radio" name="matrixResultOption" value="2">
								<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.resultOptionOfAll" bundle="sys-lbpmservice" /></label>
							<label style="margin-right:10px;"><input type="radio" name="matrixResultOption" value="3">
								<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.resultOptionOfError" bundle="sys-lbpmservice" /></label>
						</div>
						<div style="margin-top:6px;padding:2px;width:100%;height:80%;">
							<table id="conditionParamList" class="tb_normal" width="97%">
								<tr>
									<td width="45%"><kmss:message key="FlowChartObject.Lang.Node.orgMatrix.conditionParam" bundle="sys-lbpmservice" /></td>
									<td width="45%"><kmss:message key="FlowChartObject.Lang.Node.orgMatrix.conditionValue" bundle="sys-lbpmservice" /></td>
									<td width="10%" align="center">
										<a href="javascript:void(0)" onclick="addMatrixOrgConditionParamRow();"><img src="${KMSS_Parameter_StylePath}icons/add.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationAdd" bundle="sys-lbpmservice" />"></a>
									</td>
								</tr>
								<tr KMSS_IsReferRow="1" style="display:none">
									<td>
										<select name="conditionParam" onchange="conditionParamChange(this);" style="width:100%">
										</select>
										<select name="conditionType" style="width:35%;display:none">
											<option value="fdId">ID</option>
											<option value="fdName">Name</option>
										</select>
									</td>
									<td>
										<input type="hidden" name="conditionParam.expression.value">
										<input type="text" name="conditionParam.expression.text" readonly="readonly" style="width:80%" class="inputSgl">
										<a href="javascript:void(0);"  onclick="assignConditionParamValue(this);"><kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
									</td>
									<td align="center">
										<a href="javascript:void(0)" onclick="DocList_DeleteRow();"><img src="${KMSS_Parameter_StylePath}icons/delete.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationDelete" bundle="sys-lbpmservice" />"></a>
									</td>
								</tr>
							</table>
						</div>
						</div>
						</span>
						<span id="SPAN_SelectType4" style="display:none">
							<c:import url="/sys/rule/sys_ruleset_quote/sysRuleQuote.jsp" charEncoding="UTF-8">
								<c:param name="type" value="reviewnode"></c:param>
								<c:param name="returnType" value="ORG_TYPE_POST|ORG_TYPE_PERSON"></c:param>
								<c:param name="mode" value="all"></c:param>
								<c:param name="key" value="handler"></c:param>
							</c:import>
						</span>
						<br>
						<label>
							<input type="checkbox" name="wf_ignoreOnHandlerEmpty" value="true">
							<kmss:message key="FlowChartObject.Lang.Node.ignoreOnHandlerEmpty" bundle="sys-lbpmservice" />
						</label>&nbsp;
						<label>
							<!-- 审批通过时必须含有手写签字（限移动端） -->
							<input type="checkbox" name="wf_needMobileHandWrittenSignatureSignNode" value="true">
							<kmss:message key="FlowChartObject.Lang.Node.needMobileHandWrittenSignatureSignNode" bundle="sys-lbpmservice" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.processType" bundle="sys-lbpmservice" /></td>
					<td>
						<label>
							<input name="wf_processType" type="radio" value="0" checked>
							<kmss:message key="FlowChartObject.Lang.Node.processType_0" bundle="sys-lbpmservice" />
						</label><label>
							<input name="wf_processType" type="radio" value="1">
							<kmss:message key="FlowChartObject.Lang.Node.processType_1" bundle="sys-lbpmservice" />
						</label><label>
							<input name="wf_processType" type="radio" value="2">
							<kmss:message key="FlowChartObject.Lang.Node.processType_21" bundle="sys-lbpmservice" />
						</label>
						<c:import url="/sys/lbpmservice/node/common/node_handler_closeDataCoverageWarn_attribute.jsp" charEncoding="UTF-8" />
					</td>
				</tr>
				<c:import url="/sys/lbpmservice/node/common/node_handler_advance_approval.jsp" charEncoding="UTF-8" />
				<c:import url="/sys/lbpmservice/node/common/node_handler_common_operation.jsp" charEncoding="UTF-8" />
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.onHandlerSame" bundle="sys-lbpmservice"/></td>
					<td>
						<input name="wf_ignoreOnHandlerSame" type="hidden" value="true" />
						<input name="wf_onAdjoinHandlerSame" type="hidden" value="true"/>
						<input name="wf_ignoreOnFutureHandlerSame" type="hidden" value="false"/>
						<select name="handlerSameSelect" onchange="switchHandlerSameSelect(this);">
							<option value="1" selected="selected">
								<kmss:message key="FlowChartObject.Lang.Node.onAdjoinHandlerSame" bundle="sys-lbpmservice" /></option>
							<option value="2">
								<kmss:message key="FlowChartObject.Lang.Node.onSkipHandlerSame" bundle="sys-lbpmservice" /></option>
							<option value="0">
								<kmss:message key="FlowChartObject.Lang.Node.ignoreOnHandlerSame" bundle="sys-lbpmservice" /></option>
							<option value="3">
								<kmss:message key="FlowChartObject.Lang.Node.ignoreOnFutureHandlerSame" bundle="sys-lbpmservice" /></option>
						</select>
						<br/>
						<span class="com_help"><kmss:message key="FlowChartObject.Lang.Node.onHandlerSame.desc" bundle="sys-lbpmservice"/></span>
					</td>
				</tr>
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
				<c:param name="passOperationType"><kmss:message key="lbpm.operation.handler_sign" bundle="sys-lbpmservice" /></c:param>
			</c:import>
		</td>
	</tr>
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Advance" bundle="sys-lbpm-engine" />">
		<td>
			<table class="tb_normal" width="100%">
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.optHandlerNames" bundle="sys-lbpmservice" /></td>
					<td colspan="2">
						<label><input type="radio" name="wf_optHandlerSelectType" value="org" onclick="switchOptHandlerSelectType(value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_optHandlerSelectType" value="formula" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_optHandlerSelectType" value="mechanism" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectMechanism" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_optHandlerSelectType" value="dept" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectDept" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_optHandlerSelectType" value="rule" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectRule" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_optHandlerSelectType" value="otherOrgDept" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectOtherOrgDept" bundle="sys-lbpmservice" /></label><br/>
						<input name="wf_optHandlerIds" type="hidden" orgattr="optHandlerIds:optHandlerNames">
						<input name="wf_optHandlerNames" class="inputsgl" style="width:400px" readonly>
						<span id="SPAN_OptSelectType1">
						<a href="#" onclick="Dialog_Address(true, 'wf_optHandlerIds', 'wf_optHandlerNames', null, ORG_TYPE_POSTORPERSON | ORG_TYPE_DEPT | ORG_TYPE_ROLE);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<span id="SPAN_OptSelectType2" style="display:none ">
						<a href="#" onclick="selectByFormula('wf_optHandlerIds', 'wf_optHandlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<span id="SPAN_OptSelectType3"  style="display:none">
							<c:import url="/sys/rule/sys_ruleset_quote/sysRuleQuote.jsp" charEncoding="UTF-8">
								<c:param name="type" value="reviewnode"></c:param>
								<c:param name="returnType" value="ORG_TYPE_POST|ORG_TYPE_PERSON|ORG_TYPE_DEPT"></c:param>
								<c:param name="mode" value="all"></c:param>
								<c:param name="key" value="optHandler"></c:param>
							</c:import> 
						</span>
						<span id="SPAN_OptSelectType4" style="display:none">
							<a href="#" onclick="Dialog_Address(true, 'wf_optHandlerIds', 'wf_optHandlerNames', null, ORG_TYPE_ORGORDEPT);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
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
				<!-- 办理限时 starts -->
				<tr>
					<td width="100px" rowspan="2" class="lbpmLimittimeTd"><kmss:message key="FlowChartObject.Lang.Node.Limitedtime.Handle" bundle="sys-lbpmservice" /></td>
					<td width="12%"><kmss:message key="FlowChartObject.Lang.Node.Limitedtime.rule" bundle="sys-lbpmservice" /></td>
					<td>
						<select name="wf_dayOfPassRule" style="width: 120px">
							<option value="0"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass1" bundle="sys-lbpmservice" /></option>
							<option value="1"><kmss:message key="FlowChartObject.Lang.Node.dayOfNotify1" bundle="sys-lbpmservice" /></option>
						</select>
						<div class="passRuleLimitDiv" style="margin-top: 8px">
							<label>
								<input name="wf_ruleTimeType" type="radio" value="0" checked>
								按天
							</label>
							<label>
								<input name="wf_ruleTimeType" type="radio" value="1">
								 按公式定义
							</label>
						</div>
					</td>
				</tr>
				<tr>
					<td width="12%"><kmss:message key="FlowChartObject.Lang.Node.Limitedtime.time" bundle="sys-lbpmservice" /></td>
					<td>
						<div class="lbpmLimitDiv">
							<input name="wf_dayOfLimit" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
								<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
							<input name="wf_hourOfLimit" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
								<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
							<input name="wf_minuteOfLimit" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
								<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
						</div>
						<div class="lbpmRuleLimitDiv">
							<input name="wf_limitFormulaDefinition" type="hidden">
							<input name="wf_limitFormulaDefinitionNames" class="inputsgl" style="width:300px" readonly>
							<a href="#" onclick="selectRuleByFormula('wf_limitFormulaDefinition', 'wf_limitFormulaDefinitionNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</div>
					</td>
				</tr>
				<!-- 办理限时 ends -->
				<tr>
					<td width="100px" rowspan="3"><kmss:message key="FlowChartObject.Lang.Node.Overtime.Notify.1" bundle="sys-lbpmservice" /></td>
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
					<td width="100px" rowspan="1" class="lbpmOvertimeTd"><kmss:message key="FlowChartObject.Lang.Node.Overtime.Handle" bundle="sys-lbpmservice" /></td>
					<%-- <td width="12%"><kmss:message key="FlowChartObject.Lang.Node.dayOfPassRule" bundle="sys-lbpmservice" /></td>
					<td>
						<select name="wf_dayOfPassRule" style="width: 120px">
							<option value="0"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass1" bundle="sys-lbpmservice" /></option>
							<option value="1"><kmss:message key="FlowChartObject.Lang.Node.dayOfNotify1" bundle="sys-lbpmservice" /></option>
						</select>
					</td> --%>
					<td width="12%"><kmss:message key="FlowChartObject.Lang.Node.dayOfPassType" bundle="sys-lbpmservice" /></td>
					<td>
						<select name="wf_dayOfPassType" style="width: 120px">
							<option value="0"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass.NonExecution" bundle="sys-lbpmservice" />
							<option value="1"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass" bundle="sys-lbpmservice" /></option>
							<option value="2"><kmss:message key="lbpmOperations.fdOperType.system.auto.Commission" bundle="sys-lbpmservice" /></option>
							<option value="3"><kmss:message key="FlowChartObject.Lang.Node.rollbackPreviousNode" bundle="sys-lbpmservice" /></option>
							<option value="4"><kmss:message key="FlowChartObject.Lang.Node.rollbackDraftNode" bundle="sys-lbpmservice" /></option>
						</select>
					</td>
				</tr>
				<%-- <tr>
					<td width="12%"><kmss:message key="FlowChartObject.Lang.Node.dayOfPassType" bundle="sys-lbpmservice" /></td>
					<td>
						<select name="wf_dayOfPassType" style="width: 120px">
							<option value="0"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass.NonExecution" bundle="sys-lbpmservice" />
							<option value="1"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass" bundle="sys-lbpmservice" /></option>
							<option value="2"><kmss:message key="lbpmOperations.fdOperType.system.auto.Commission" bundle="sys-lbpmservice" /></option>
							<option value="3"><kmss:message key="FlowChartObject.Lang.Node.rollbackPreviousNode" bundle="sys-lbpmservice" /></option>
							<option value="4"><kmss:message key="FlowChartObject.Lang.Node.rollbackDraftNode" bundle="sys-lbpmservice" /></option>
						</select>
					</td>
				</tr> --%>
				<tr style="display: none" class="lbpmPassTimeTr">
					<td width="12%"><kmss:message key="FlowChartObject.Lang.Node.dayOfPassTime" bundle="sys-lbpmservice" /></td>
					<td>
						<div class="lbpmPassDiv">
							<input name="wf_dayOfPass" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
								<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
							<input name="wf_hourOfPass" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
								<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
							<input name="wf_minuteOfPass" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
								<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
						</div>
						<div class="lbpmRulePassDiv">
							<input name="wf_passFormulaDefinition" type="hidden" >
							<input name="wf_passFormulaDefinitionNames" class="inputsgl" style="width:300px" readonly>
							<a href="#" onclick="selectRuleByFormula('wf_passFormulaDefinition', 'wf_passFormulaDefinitionNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</div>
						<div style="display: none" class="lbpmCommissionDiv">
							<input name="wf_dayOfCommission" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
								<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
							<input name="wf_hourOfCommission" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
								<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
							<input name="wf_minuteOfCommission" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
								<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="lbpmCommissionTr">
					<td width="12%"><kmss:message key="FlowChartObject.Lang.Node.commissionHandler" bundle="sys-lbpmservice" /></td>
					<td colspan="2">
						<input name="wf_commissionHandlerId" type="hidden" orgattr="commissionHandlerId:commissionHandlerName">
						<input name="wf_commissionHandlerName" class="inputsgl" readonly>
						<span id="SPAN_OptSelectType1">
						<a href="#" onclick="Dialog_Address(false, 'wf_commissionHandlerId', 'wf_commissionHandlerName', null, ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						</br>
						</br>
						<div id="DIV_OptHandlerCalType"><kmss:message key="FlowChartObject.Lang.Node.optHandlerCalType" bundle="sys-lbpmservice" />: 
						<label>
							<input name="wf_commissionRule" type="radio" value="0" checked>
							<kmss:message key="FlowChartObject.Lang.Node.handler" bundle="sys-lbpm-engine" />
						</label><label>
							<input name="wf_commissionRule" type="radio" value="1">
							<kmss:message key="FlowChartObject.Lang.Node.creator" bundle="sys-lbpm-engine" />
						</label><br></div>
					</td>
				</tr>
				<%-- <tr>
					<td width="12%"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass" bundle="sys-lbpmservice" /></td>
					<td colspan="2">
						<label>
							<input name="wf_dayOfPassRule" type="radio" value="0" checked>
							<kmss:message key="FlowChartObject.Lang.Node.dayOfPass1" bundle="sys-lbpmservice" />
						</label>
						&nbsp;&nbsp;
						<label>
							<input name="wf_dayOfPassRule" type="radio" value="1">
							<kmss:message key="FlowChartObject.Lang.Node.dayOfNotify1" bundle="sys-lbpmservice" />
						</label><br>
						<input name="wf_dayOfPass" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
							<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
						<input name="wf_hourOfPass" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
							<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
						<input name="wf_minuteOfPass" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
							<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
						<kmss:message key="FlowChartObject.Lang.Node.uncompleted" bundle="sys-lbpmservice" />
						<br><kmss:message key="FlowChartObject.Lang.Node.dayOfHandleHelp" bundle="sys-lbpmservice" /> 
					</td>
				</tr> --%>
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
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.fastReview" bundle="sys-lbpmservice"/></td>
					<td colspan="3">
						<input name="wf_canFastReview" type="checkbox" value="true"><kmss:message key="FlowChartObject.Lang.Node.fastApprove" bundle="sys-lbpmservice"/></input><br/>
						<span class="com_help"><kmss:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.fastReview.info" /></span>
					</td>
				</tr>
				
				<!-- 配置节点自定义审批审批意见 -->
				<c:import url="/sys/lbpmservice/node/common/node_handler_lbpmCustomizeContent.jsp" charEncoding="UTF-8" />
			</table>
		</td>
	</tr>
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Popedom" bundle="sys-lbpmservice" />" LKS_LabelId="node_right_tr">
		<td>
			<c:import url="/sys/lbpmservice/node/common/node_right_attribute.jsp" charEncoding="UTF-8" >
				<c:param name="nodePrivilegeSetting" value="true"/>
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
	<c:import url="/sys/lbpmservice/node/common/node_custom_notify_attribute.jsp" charEncoding="UTF-8">
		<c:param name="nodeType" value="${param.nodeType}" />
		<c:param name="modelName" value="${param.modelName }" />
	</c:import>
</table>

<script>
//提交进行超时时间和限时时间的比较
AttributeObject.SubmitFuns.push(function(){
	//获取超时时间
	var dayOfPass = $("input[name='wf_dayOfPass']").val();
	var hourOfPass = $("input[name='wf_hourOfPass']").val();
	var minuteOfPass = $("input[name='wf_minuteOfPass']").val();
	
	//获取转办时间
	var dayOfCommission =  $("input[name='wf_dayOfCommission']").val();
	var hourOfCommission = $("input[name='wf_hourOfCommission']").val();
	var minuteOfCommission = $("input[name='wf_minuteOfCommission']").val();

	//获取限时时间
	var dayOfLimit = $("input[name='wf_dayOfLimit']").val();
	var hourOfLimit = $("input[name='wf_hourOfLimit']").val();
	var minuteOfLimit = $("input[name='wf_minuteOfLimit']").val();

	//获取催办处理人时间
	var dayOfNotify = $("input[name='wf_dayOfNotify']").val();
	var hourOfNotify = $("input[name='wf_hourOfNotify']").val();
	var minuteOfNotify = $("input[name='wf_minuteOfNotify']").val();

	//获取催办通知起草人时间
	var tranNotifyDraft = $("input[name='wf_tranNotifyDraft']").val();
	var hourOfTranNotifyDraft = $("input[name='wf_hourOfTranNotifyDraft']").val();
	var minuteOfTranNotifyDraft = $("input[name='wf_minuteOfTranNotifyDraft']").val();

	//获取催办特权人时间
	var tranNotifyPrivate = $("input[name='wf_tranNotifyPrivate']").val();
	var hourOfTranNotifyPrivate = $("input[name='wf_hourOfTranNotifyPrivate']").val();
	var minuteOfTranNotifyPrivate = $("input[name='wf_minuteOfTranNotifyPrivate']").val();
	
	//获取秒数
	var passTime = (parseInt(dayOfPass) * 24 + parseInt(hourOfPass)) * 3600 + parseInt(minuteOfPass) * 60;
	var limitTime = (parseInt(dayOfLimit) * 24 + parseInt(hourOfLimit)) * 3600 + parseInt(minuteOfLimit) * 60;
	var commissionTime = (parseInt(dayOfCommission) * 24 + parseInt(hourOfCommission)) * 3600 + parseInt(minuteOfCommission) * 60;
	var notifyTime = (parseInt(dayOfNotify) * 24 + parseInt(hourOfNotify)) * 3600 + parseInt(minuteOfNotify) * 60;
	var notifyDraftTime = (parseInt(tranNotifyDraft) * 24 + parseInt(hourOfTranNotifyDraft)) * 3600 + parseInt(minuteOfTranNotifyDraft) * 60;
	var notifyPrivateTime = (parseInt(tranNotifyPrivate) * 24 + parseInt(hourOfTranNotifyPrivate)) * 3600 + parseInt(minuteOfTranNotifyPrivate) * 60;
		
	//比较,超时时间必须大于等于限时时间
	if((limitTime > 0 && passTime > 0 && passTime < limitTime) || 
			(limitTime > 0 && commissionTime > 0 && commissionTime < limitTime)){
		alert("${lfn:message('sys-lbpmservice:lbpmNode.attribute.passTime.compare.limitTime.tip')}");
		return false;
	}

	//比较，超时时间必须大于等于催办时间
	if((notifyTime > 0 && passTime > 0 && passTime < notifyTime) || (notifyDraftTime > 0 && passTime > 0  && passTime < notifyDraftTime) ||
			(notifyPrivateTime > 0  && passTime > 0 && passTime < notifyPrivateTime) ||
			(notifyTime > 0 && commissionTime > 0 && commissionTime < notifyTime) || (notifyDraftTime > 0 && commissionTime > 0  && commissionTime < notifyDraftTime) || 
			(notifyPrivateTime > 0  && commissionTime > 0 && commissionTime < notifyPrivateTime)){
		alert("${lfn:message('sys-lbpmservice:lbpmNode.attribute.passTime.compare.commissionTime.tip')}");
		return false;
	}
	return true;
});
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
var ignoreOnFutureHandlerSame = AttributeObject.NodeData["ignoreOnFutureHandlerSame"];
var processType = AttributeObject.NodeData["processType"];
var repeatDayOfNotify = AttributeObject.NodeData["repeatDayOfNotify"];
AttributeObject.Init.AllModeFuns.push(function() {

	if(!handlerSelectType || (handlerSelectType!="formula" && handlerSelectType!="matrix" && handlerSelectType!="rule")){
		document.getElementById('SPAN_SelectType1').style.display='';
		document.getElementById('SPAN_SelectType2').style.display='none';
		document.getElementById('SPAN_SelectType3').style.display='none';
		document.getElementById('SPAN_SelectType4').style.display='none';
	}else{
		document.getElementById('SPAN_SelectType1').style.display='none';
		if (handlerSelectType=="formula") {
			document.getElementById('SPAN_SelectType2').style.display='';
		} else if (handlerSelectType=="matrix") {
			document.getElementsByName("wf_handlerNames")[0].style.display = "none";
			document.getElementById('SPAN_SelectType3').style.display='';
		}else if (handlerSelectType=="rule") {
			document.getElementsByName("wf_handlerNames")[0].style.display = "none";
			document.getElementById('SPAN_SelectType4').style.display='';
			$(".rule.handler").eq(0).show();
		}
	}

	if (optHandlerSelectType=="formula"){
		document.getElementById('SPAN_OptSelectType1').style.display='none';
		document.getElementById('SPAN_OptSelectType2').style.display='';
		document.getElementById('DIV_OptHandlerCalType').style.display='none';
		document.getElementById('SPAN_OptSelectType4').style.display='none';
		document.getElementById('SPAN_OptSelectType3').style.display='none';
		document.getElementsByName("wf_optHandlerNames")[0].style.display="";
	} else if (!optHandlerSelectType || optHandlerSelectType=="org"){
		document.getElementById('SPAN_OptSelectType1').style.display='';
		document.getElementById('SPAN_OptSelectType2').style.display='none';
		document.getElementById('SPAN_OptSelectType3').style.display='none';
		document.getElementById('SPAN_OptSelectType4').style.display='none';
		document.getElementById('DIV_OptHandlerCalType').style.display='';
		document.getElementsByName("wf_optHandlerNames")[0].style.display="";
	}  else if(optHandlerSelectType=="rule"){
		document.getElementById('SPAN_OptSelectType1').style.display='none';
		document.getElementById('SPAN_OptSelectType2').style.display='none';
		document.getElementById('SPAN_OptSelectType3').style.display='';
		document.getElementById('SPAN_OptSelectType4').style.display='none';
		document.getElementById('DIV_OptHandlerCalType').style.display='none';
		document.getElementsByName("wf_optHandlerNames")[0].style.display="none";
		$(".rule.optHandler").eq(0).show();
	} else if(optHandlerSelectType=="otherOrgDept"){
		document.getElementById('SPAN_OptSelectType1').style.display='none';
		document.getElementById('SPAN_OptSelectType2').style.display='none';
		document.getElementById('SPAN_OptSelectType3').style.display='none';
		document.getElementById('SPAN_OptSelectType4').style.display='';
		document.getElementById('DIV_OptHandlerCalType').style.display='none';
		document.getElementsByName("wf_optHandlerNames")[0].style.display="";
	} else {
		document.getElementById('SPAN_OptSelectType1').style.display='none';
		document.getElementById('SPAN_OptSelectType2').style.display='none';
		document.getElementById('SPAN_OptSelectType3').style.display='none';
		document.getElementById('DIV_OptHandlerCalType').style.display='none';
		document.getElementsByName("wf_optHandlerNames")[0].style.display="none";
	}

	initHandlerSameSelect(ignoreOnHandlerSame,onAdjoinHandlerSame,ignoreOnFutureHandlerSame);
	
	AttributeObject.Utils.switchOrgAttributes(document.getElementsByName("wf_handlerIds")[0], handlerSelectType);
	AttributeObject.Utils.switchOrgAttributes(document.getElementsByName("wf_optHandlerIds")[0], optHandlerSelectType);
	
	// 关联流转方式与超时跳过规则配置项，只有串行时才允许配置事务启动的超时跳过
	if (processType && processType != '0') {
		//$('input[type=radio][name=wf_dayOfPassRule][value=1]').removeAttr("checked");
    	//$('input[type=radio][name=wf_dayOfPassRule][value=0]')[0].checked = true;
    	//$('input[type=radio][name=wf_dayOfPassRule][value=1]').attr("disabled",'disabled');
		var $dayOfPassRule = $("select[name='wf_dayOfPassRule']");
		var $dayOfPassType = $("select[name='wf_dayOfPassType']");
		var op1 = $dayOfPassType.find("option[value='1']");
		var op0 = $dayOfPassType.find("option[value='0']");
		if($dayOfPassRule.val()=="0"){
			if(op1.length==0){
				op0.after('<option value="1"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass" bundle="sys-lbpmservice" /></option>');
				//$dayOfPassType.prepend('<option value="1"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass" bundle="sys-lbpmservice" /></option>');
			}
		}else{
			if(isSeriesProcess()){
				if(op1.length==0){
					op0.after('<option value="1"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass" bundle="sys-lbpmservice" /></option>');
					//$dayOfPassType.prepend('<option value="1"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass" bundle="sys-lbpmservice" /></option>');
				}
			}else{
				if(op1.length>0){
					op1.remove();
				}
			}
		}
	}
	$('input[type=radio][name=wf_processType]').change(function() {
        /* if (this.value == '0') {
        	$('input[type=radio][name=wf_dayOfPassRule][value=1]').removeAttr("disabled");
        } else {
        	$('input[type=radio][name=wf_dayOfPassRule][value=1]').removeAttr("checked");
        	$('input[type=radio][name=wf_dayOfPassRule][value=0]')[0].checked = true;
        	$('input[type=radio][name=wf_dayOfPassRule][value=1]').attr("disabled",'disabled');
        	
        } */
		var $dayOfPassRule = $("select[name='wf_dayOfPassRule']");
		var $dayOfPassType = $("select[name='wf_dayOfPassType']");
		var op1 = $dayOfPassType.find("option[value='1']");
		//修改
		var op0 = $dayOfPassType.find("option[value='0']");
		//
		if($dayOfPassRule.val()=="0"){
			if(op1.length==0){
				op0.after('<option value="1"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass" bundle="sys-lbpmservice" /></option>');
				//$dayOfPassType.prepend('<option value="1"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass" bundle="sys-lbpmservice" /></option>');
			}
		}else{
			if(isSeriesProcess()){
				if(op1.length==0){
					op0.after('<option value="1"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass" bundle="sys-lbpmservice" /></option>');
					//$dayOfPassType.prepend('<option value="1"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass" bundle="sys-lbpmservice" /></option>');
				}
			}else{
				if(op1.length>0){
					op1.remove();
				}
			}
		}
		$dayOfPassType.find("option:first").prop("selected", 'selected');
		$dayOfPassType.change();
    });
    
	var dayOfPassRule = AttributeObject.NodeData["dayOfPassRule"];
	var dayOfPassType = AttributeObject.NodeData["dayOfPassType"];
	if(dayOfPassRule=="1"){
		var $dayOfPassType = $("select[name='wf_dayOfPassType']");
		$dayOfPassType.find("option[value='3']").remove();
		$dayOfPassType.find("option[value='4']").remove();
	}
	if(dayOfPassType && dayOfPassType != "0"){
		//办理限时修改
		$(".lbpmPassTimeTr").show();
		$(".lbpmOvertimeTd").attr("rowspan","2");
	}
	if(dayOfPassType=="2"){
		$("select[name='wf_dayOfPassType']").val(dayOfPassType);
		$(".lbpmOvertimeTd").attr("rowspan","3");
		$(".lbpmCommissionTr").show();
		$(".lbpmPassDiv").hide();
		$(".lbpmCommissionDiv").show();
		//办理限时修改
		$(".lbpmPassTimeTr").show();
	}
	
	if(!dayOfPassType){
		//兼容自动转办历史数据
		var dayOfPass = AttributeObject.NodeData["dayOfPass"];
		var hourOfPass = AttributeObject.NodeData["hourOfPass"];
		var minuteOfPass = AttributeObject.NodeData["minuteOfPass"];
		var dayOfCommission = AttributeObject.NodeData["dayOfCommission"];
		var hourOfCommission = AttributeObject.NodeData["hourOfCommission"];
		var minuteOfCommission = AttributeObject.NodeData["minuteOfCommission"];
		if(dayOfPass=='0' && hourOfPass=='0' && minuteOfPass=='0' && dayOfCommission &&
				(dayOfCommission!='0'||hourOfCommission!='0'||minuteOfCommission!='0')){
			var $dayOfPassType = $("select[name='wf_dayOfPassType']");
			//选中自动转办
			$dayOfPassType.val("2");
			$(".lbpmOvertimeTd").attr("rowspan","3");
			$(".lbpmCommissionTr").show();
			$(".lbpmPassDiv").hide();
			$(".lbpmCommissionDiv").show();
			//办理限时修改
			$(".lbpmPassTimeTr").show();
		}
	}
    
	// 兼容升级问题的自动跳过数据
	if(typeof(dayOfPassType) === "undefined"){
		if(!(dayOfPass=='0' && hourOfPass=='0' && minuteOfPass=='0')){
			var $dayOfPassType = $("select[name='wf_dayOfPassType']");
			// 显示自动跳过
			$dayOfPassType.val("1");
			$(".lbpmOvertimeTd").attr("rowspan","2");
			$(".lbpmPassTimeTr").show();
		}
	}
	var settingInfo = getSettingInfo();
	//审批意见附件开关开始
	if (!AttributeObject.NodeData["canAddAuditNoteAtt"]){
		var _isCanAddAuditNoteAtt = settingInfo["isCanAddAuditNoteAtt"];
		if (_isCanAddAuditNoteAtt === "false"){
			$("input[name='wf_canAddAuditNoteAtt']").prop("checked",false);
		}
	}
	//移动端审批是否必须包含签名
	if (!AttributeObject.NodeData["needMobileHandWrittenSignatureSignNode"]){
		var _needMobileHandWrittenSignatureSignNode = settingInfo["needMobileHandWrittenSignatureSignNode"];
		if (_needMobileHandWrittenSignatureSignNode === "true"){
			$("input[name='wf_needMobileHandWrittenSignatureSignNode']").prop("checked",true);
		} else {
			$("input[name='wf_needMobileHandWrittenSignatureSignNode']").prop("checked",false);
		}
	}
	//超时通知
	if (!AttributeObject.NodeData["dayOfNotify"]){
		var _dayOfNotify = settingInfo["dayOfNotify"];
		if (_dayOfNotify){
			$("input[name='wf_dayOfNotify']").val(_dayOfNotify);
		}
	}
	if (!AttributeObject.NodeData["hourOfNotify"]){
		var _hourOfNotify = settingInfo["hourOfNotify"];
		if (_hourOfNotify){
			$("input[name='wf_hourOfNotify']").val(_hourOfNotify);
		}
	}
	if (!AttributeObject.NodeData["minuteOfNotify"]){
		var _minuteOfNotify = settingInfo["minuteOfNotify"];
		if (_minuteOfNotify){
			$("input[name='wf_minuteOfNotify']").val(_minuteOfNotify);
		}
	}
	if (repeatDayOfNotify=="true") {
		showRepeatConfig(true);
	}
	if (!repeatDayOfNotify){
		var _repeatDayOfNotify = settingInfo["repeatDayOfNotify"];
		if (_repeatDayOfNotify === "true"){
			$("input[name='wf_repeatDayOfNotify']").prop("checked",true);
			$('#repeatConfigDiv').show();
			$("input[name=wf_repeatTimesDayOfNotify]").val(settingInfo["repeatTimesDayOfNotify"]);
			$("input[name=wf_intervalDayOfNotify]").val(settingInfo["intervalDayOfNotify"]);
			$("input[name=wf_intervalHourOfNotify]").val(settingInfo["intervalHourOfNotify"]);
			$("input[name=wf_intervalMinuteOfNotify]").val(settingInfo["intervalMinuteOfNotify"]);
		}
	}
	if (!AttributeObject.NodeData["tranNotifyDraft"]){
		var _tranNotifyDraft = settingInfo["tranNotifyDraft"];
		if (_tranNotifyDraft){
			$("input[name='wf_tranNotifyDraft']").val(_tranNotifyDraft);
		}
	}
	if (!AttributeObject.NodeData["hourOfTranNotifyDraft"]){
		var _hourOfTranNotifyDraft = settingInfo["hourOfTranNotifyDraft"];
		if (_hourOfTranNotifyDraft){
			$("input[name='wf_hourOfTranNotifyDraft']").val(_hourOfTranNotifyDraft);
		}
	}
	if (!AttributeObject.NodeData["minuteOfTranNotifyDraft"]){
		var _minuteOfTranNotifyDraft = settingInfo["minuteOfTranNotifyDraft"];
		if (_minuteOfTranNotifyDraft){
			$("input[name='wf_minuteOfTranNotifyDraft']").val(_minuteOfTranNotifyDraft);
		}
	}
	if (!AttributeObject.NodeData["tranNotifyPrivate"]){
		var _tranNotifyPrivate = settingInfo["tranNotifyPrivate"];
		if (_tranNotifyPrivate){
			$("input[name='wf_tranNotifyPrivate']").val(_tranNotifyPrivate);
		}
	}
	if (!AttributeObject.NodeData["hourOfTranNotifyPrivate"]){
		var _hourOfTranNotifyPrivate = settingInfo["hourOfTranNotifyPrivate"];
		if (_hourOfTranNotifyPrivate){
			$("input[name='wf_hourOfTranNotifyPrivate']").val(_hourOfTranNotifyPrivate);
		}
	}
	if (!AttributeObject.NodeData["minuteOfTranNotifyPrivate"]){
		var _minuteOfTranNotifyPrivate = settingInfo["minuteOfTranNotifyPrivate"];
		if (_minuteOfTranNotifyPrivate){
			$("input[name='wf_minuteOfTranNotifyPrivate']").val(_minuteOfTranNotifyPrivate);
		}
	}
});

//审批人选择方式
function switchHandlerSelectType(value){
	if(handlerSelectType==value)
		return;
	handlerSelectType = value;
	SPAN_SelectType1.style.display=handlerSelectType=="org"?"":"none";
	SPAN_SelectType2.style.display=handlerSelectType=="formula"?"":"none";
	SPAN_SelectType3.style.display=handlerSelectType=="matrix"?"":"none";
	SPAN_SelectType4.style.display=handlerSelectType=="rule"?"":"none";
	document.getElementsByName("wf_handlerIds")[0].value = "";
	document.getElementsByName("wf_handlerNames")[0].value = "";
	if (handlerSelectType == "matrix" || handlerSelectType == "rule") {
		document.getElementsByName("wf_handlerNames")[0].style.display = "none";
	} else {
		document.getElementsByName("wf_handlerNames")[0].style.display = "";
	}
	if(handlerSelectType=="rule"){
		$(".rule.handler").eq(0).show();
		$(".rule.handler").eq(0).find(".alreadyMapType").eq(0).hide();
		$(".rule.handler").eq(0).find(".mapArea").eq(0).hide();
	}else{
		$(".rule.handler").eq(0).hide();
		//记录需要该引用id
		window.sysRuleQuoteToHandler.recordDelMapContentIds(0);
		//清空规则信息
		$(".rule.handler").eq(0).find("[name='ruleId']").eq(0).val("");
		$(".rule.handler").eq(0).find("[name='ruleName']").eq(0).val("");
		
		$(".rule.handler").eq(0).find("[name='mapContent']").eq(0).val("");
		$(".rule.handler").eq(0).find("[name='alreadyMapId']").eq(0).val("");
		$(".rule.handler").eq(0).find("[name='alreadyMapName']").eq(0).val("");
	}

	AttributeObject.Utils.switchOrgAttributes(document.getElementsByName("wf_handlerIds")[0], handlerSelectType);
}
// 备选审批人选择方式
function switchOptHandlerSelectType(value) {
	if(optHandlerSelectType==value)
		return;
	optHandlerSelectType = value;
	document.getElementById('SPAN_OptSelectType1').style.display=optHandlerSelectType=="org"?"":"none";
	document.getElementById('SPAN_OptSelectType2').style.display=optHandlerSelectType=="formula"?"":"none";
	document.getElementById('SPAN_OptSelectType3').style.display=optHandlerSelectType=="rule"?"":"none";
	document.getElementById('SPAN_OptSelectType4').style.display=optHandlerSelectType=="otherOrgDept"?"":"none";
	document.getElementById('DIV_OptHandlerCalType').style.display=optHandlerSelectType=="org"?"":"none";
	document.getElementsByName("wf_optHandlerNames")[0].style.display=(optHandlerSelectType == "formula" || optHandlerSelectType == "org")?"":"none";
	document.getElementsByName("wf_optHandlerIds")[0].value = "";
	document.getElementsByName("wf_optHandlerNames")[0].value = "";
	if (optHandlerSelectType=="rule") {
		document.getElementsByName("wf_optHandlerNames")[0].style.display = "none";
		$(".rule.optHandler").eq(0).show();
		$(".rule.optHandler").eq(0).find(".alreadyMapType").eq(0).hide();
		$(".rule.optHandler").eq(0).find(".mapArea").eq(0).hide();
	} else {
		document.getElementsByName("wf_optHandlerNames")[0].style.display = "";
		$(".rule.optHandler").eq(0).hide();
		//记录需要该引用id
		window.sysRuleQuoteToOptHandler.recordDelMapContentIds(0);
		//清空规则信息
		$(".rule.optHandler").eq(0).find("[name='ruleId']").eq(0).val("");
		$(".rule.optHandler").eq(0).find("[name='ruleName']").eq(0).val("");
		
		$(".rule.optHandler").eq(0).find("[name='mapContent']").eq(0).val("");
		$(".rule.optHandler").eq(0).find("[name='alreadyMapId']").eq(0).val("");
		$(".rule.optHandler").eq(0).find("[name='alreadyMapName']").eq(0).val("");
	}

	AttributeObject.Utils.switchOrgAttributes(document.getElementsByName("wf_optHandlerIds")[0], optHandlerSelectType);
}

//判断是否非负整数
function isInt(i){
	var re = /^[0-9]+$/;
	return re.test(i);
}
function controlNumber(obj){
	obj.value=(parseInt((obj.value=obj.value.replace(/\D/g,''))==''||parseInt((obj.value=obj.value.replace(/\D/g,''))==0)?'0':obj.value,10));
}

function showRepeatConfig(checked){
	if (checked == true) {
		$('#repeatConfigDiv').show();
	} else {
		$('#repeatConfigDiv').hide();
		var settingInfo = getSettingInfo();
		$("input[name=wf_repeatTimesDayOfNotify]").val(settingInfo["repeatTimesDayOfNotify"]?settingInfo["repeatTimesDayOfNotify"]:"1");
		$("input[name=wf_intervalDayOfNotify]").val(settingInfo["intervalDayOfNotify"]?settingInfo["intervalDayOfNotify"]:"0");
		$("input[name=wf_intervalHourOfNotify]").val(settingInfo["intervalHourOfNotify"]?settingInfo["intervalHourOfNotify"]:"0");
		$("input[name=wf_intervalMinuteOfNotify]").val(settingInfo["intervalMinuteOfNotify"]?settingInfo["intervalMinuteOfNotify"]:"0");
	}
}

AttributeObject.CheckDataFuns.push(function(data) {
	//设置快速审批校验
	if(data.canFastReview=='true'){
		//存在修改其他节点处理人的审批节点，不允许快速通过
		if(data.mustModifyHandlerNodeIds){
			alert(FlowChartObject.Lang.GetMessage(FlowChartObject.Lang.checkFastReviewMustModify,data.id + "." + data.name));
			return false;
		}
		//审批节点的包含审批要点，并且必须是重要的，不允许校验
		if(data.extAttributes){
			for(var i=0;i<data.extAttributes.length;i++){
				if(data.extAttributes[i].name=="lbpmExtAuditPointCfg" && data.extAttributes[i].value){
					var jsonAuditPoint=JSON.parse(data.extAttributes[i].value);
					for(var point in jsonAuditPoint){
						if(jsonAuditPoint[point].fdIsImportant=='true'){
							alert(FlowChartObject.Lang.GetMessage(FlowChartObject.Lang.checkFastReviewAuditNote,data.id + "." + data.name));
							return false;
						}
					}
				}
			}
		}
	}
	if(data.useOptHandlerOnly=="true" && data.optHandlerIds==""){
		//如果选择的是本部门或者本机构，不进行备选列表为空判断，否则要进行判断
		if(data.optHandlerSelectType && data.optHandlerSelectType != "dept" && data.optHandlerSelectType != "mechanism"){
			alert('<kmss:message key="FlowChartObject.Lang.Node.checkOptHandlerEmpty" bundle="sys-lbpmservice" />');
			return false;
		}
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
	if(!isInt(data.dayOfPass) || !isInt(data.hourOfPass) || !isInt(data.minuteOfPass)){
		alert('<kmss:message key="FlowChartObject.Lang.Node.checkDayOfPass" bundle="sys-lbpmservice" />');
		return false;
	}
	if (data.repeatDayOfNotify=="true") {
		if(!isInt(data.repeatTimesDayOfNotify) || !isInt(data.intervalDayOfNotify) || !isInt(data.intervalHourOfNotify) || !isInt(data.intervalMinuteOfNotify)){
			alert('<kmss:message key="FlowChartObject.Lang.Node.checkRepeatTimesAndInterval" bundle="sys-lbpmservice" />');
			return false;
		}
		if (parseInt(data.dayOfNotify,10) == 0 && parseInt(data.hourOfNotify,10) == 0 && parseInt(data.minuteOfNotify,10) == 0){
			alert('<kmss:message key="FlowChartObject.Lang.Node.checkDayOfNotifyForRepeat" bundle="sys-lbpmservice" />');
			return false;
		}
		if (parseInt(data.repeatTimesDayOfNotify) < 1){
			alert('<kmss:message key="FlowChartObject.Lang.Node.checkRepeatTimes" bundle="sys-lbpmservice" />');
			return false;
		}
		if (parseInt(data.intervalDayOfNotify,10) == 0 && parseInt(data.intervalHourOfNotify,10) == 0 && parseInt(data.intervalMinuteOfNotify,10) == 0){
			alert('<kmss:message key="FlowChartObject.Lang.Node.checkRepeatInterval" bundle="sys-lbpmservice" />');
			return false;
		}
	}
	return true;
});
function initHandlerSameSelect(ignoreHandlerSame,adjoinHandlerSame,ignoreOnFutureHandlerSame){
	if(ignoreHandlerSame==null){
		ignoreHandlerSame = "true";
	}
	if(adjoinHandlerSame==null){
		adjoinHandlerSame = "true";
	}
	if(ignoreOnFutureHandlerSame==null){
		ignoreOnFutureHandlerSame = "false";
	}
	var selected = "1";
	if(ignoreHandlerSame == "true"){//兼容老数据配置
		if(adjoinHandlerSame=="true"){
			selected = "1";//相邻跳过
		}else{
			selected = "2";//跨节点跳过
		}
	}else if(ignoreOnFutureHandlerSame=="true"){
		selected = "3";//后续处理人身份重复跳过当前
	}else{
		selected = "0";//不跳过
	}
	$("select[name='handlerSameSelect']").val(selected);
}
function switchHandlerSameSelect(thisObj){
	var selected = $(thisObj).val();
	var ignoreHandlerSameObj = $("input[name='wf_ignoreOnHandlerSame']");
	var adjoinHandlerSameObj = $("input[name='wf_onAdjoinHandlerSame']");
	var ignoreOnFutureHandlerSameObj = $("input[name='wf_ignoreOnFutureHandlerSame']");
	if(selected=="1"){//相邻处理人重复跳过
		ignoreHandlerSameObj.val("true");
		adjoinHandlerSameObj.val("true");
		ignoreOnFutureHandlerSameObj.val("false");
	}else if(selected=="2"){//跨节点处理人重复跳过
		ignoreHandlerSameObj.val("true");
		adjoinHandlerSameObj.val("false");
		ignoreOnFutureHandlerSameObj.val("false");
	}else if(selected=="3"){//后续处理人身份重复跳过当前
		ignoreHandlerSameObj.val("false");
		adjoinHandlerSameObj.val("false");
		ignoreOnFutureHandlerSameObj.val("true");
	}else{//不跳过
		ignoreHandlerSameObj.val("false");
		adjoinHandlerSameObj.val("false");
		ignoreOnFutureHandlerSameObj.val("false");
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

function selectRuleByFormula(idField, nameField){
	Formula_Dialog(idField,
			nameField,
			FlowChartObject.FormFieldList, 
			"datetime",
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

	function isSeriesProcess(){
		return $('input[type=radio][name=wf_processType]:checked').val()=='0';
	}

	//计时方案值改变事件
	$("select[name='wf_dayOfPassRule']").change(function(){
		var $dayOfPassType = $("select[name='wf_dayOfPassType']");
		if(this.value=="0"){
			var op1 = $dayOfPassType.find("option[value='1']");
			//修改
			var op0 = $dayOfPassType.find("option[value='0']");
			//
			if(op1.length==0){
				//$dayOfPassType.prepend('<option value="1"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass" bundle="sys-lbpmservice" /></option>');
				op0.after('<option value="1"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass" bundle="sys-lbpmservice" /></option>');
			}
			var op3 = $dayOfPassType.find("option[value='3']");
			if(op3.length==0){
				$dayOfPassType.append('<option value="3"><kmss:message key="FlowChartObject.Lang.Node.rollbackPreviousNode" bundle="sys-lbpmservice" /></option>');
				$dayOfPassType.append('<option value="4"><kmss:message key="FlowChartObject.Lang.Node.rollbackDraftNode" bundle="sys-lbpmservice" /></option>');
			}
			
		}else{
			
			var op1 = $dayOfPassType.find("option[value='1']");
			var op0 = $dayOfPassType.find("option[value='0']");
			if(isSeriesProcess()){
				if(op1.length==0){
					op0.after('<option value="1"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass" bundle="sys-lbpmservice" /></option>');
					//$dayOfPassType.prepend('<option value="1"><kmss:message key="FlowChartObject.Lang.Node.dayOfPass" bundle="sys-lbpmservice" /></option>');
				}
			}else{
				if(op1.length>0){
					op1.remove();
				}
			}
			$dayOfPassType.find("option[value='3']").remove();
			$dayOfPassType.find("option[value='4']").remove();
		}
		$dayOfPassType.find("option:first").prop("selected", 'selected');
		$dayOfPassType.change();
	});

	//执行动作值改变事件
	$("select[name='wf_dayOfPassType']").change(function(){
		if(this.value=="2"){
			$(".lbpmOvertimeTd").attr("rowspan","3");
			$(".lbpmCommissionTr").show();
			$(".lbpmPassDiv").hide();
			
			var radioRuleTimeType = $("input:radio[name='wf_ruleTimeType']:checked").val();//判断是否被选中，选择是true，否则为false
		     if(radioRuleTimeType=="0"){
		    	 $(".lbpmCommissionDiv").show();//转办另外设置的时分秒
		 		 $(".lbpmRulePassDiv").hide();//显示公式
		     }else{
		    	 $(".lbpmCommissionDiv").hide();//转办另外设置的时分秒
			 	 $(".lbpmRulePassDiv").show();//显示公式
		     }
			//办理限时添加
			$(".lbpmPassTimeTr").show();
		}else if(this.value == "0"){
			$(".lbpmOvertimeTd").attr("rowspan","1");
			$(".lbpmCommissionTr").hide();
			
			 var radioRuleTimeType = $("input:radio[name='wf_ruleTimeType']:checked").val();//判断是否被选中，选择是true，否则为false
		 		
		     if(radioRuleTimeType=="0"){
		    	 $(".lbpmPassDiv").show();
		 		 $(".lbpmRulePassDiv").hide();//显示公式
		     }else{
		    	 $(".lbpmPassDiv").hide();
			 	 $(".lbpmRulePassDiv").show();//显示公式
		     }
			
			
			$(".lbpmCommissionDiv").hide();
			//办理限时添加
			$(".lbpmPassTimeTr").hide();
		}else{
			$(".lbpmOvertimeTd").attr("rowspan","2");
			$(".lbpmCommissionTr").hide();
			
			var radioRuleTimeType = $("input:radio[name='wf_ruleTimeType']:checked").val();//判断是否被选中，选择是true，否则为false
			
		     if(radioRuleTimeType=="0"){
		    	 $(".lbpmPassDiv").show();
		 		 $(".lbpmRulePassDiv").hide();//显示公式
		     }else{
		    	 $(".lbpmPassDiv").hide();
			 	 $(".lbpmRulePassDiv").show();//显示公式
		     }
			
			$(".lbpmCommissionDiv").hide();
			//办理限时添加
			$(".lbpmPassTimeTr").show();
		}
		$("input[name='wf_dayOfPass']").val("0");
		$("input[name='wf_hourOfPass']").val("0");
		$("input[name='wf_minuteOfPass']").val("0");
		$("input[name='wf_dayOfCommission']").val("0");
		$("input[name='wf_hourOfCommission']").val("0");
		$("input[name='wf_minuteOfCommission']").val("0");
		$("input[name='wf_commissionHandlerId']").val("");
		$("input[name='wf_commissionHandlerName']").val("");
	});
	
//<---------------- 矩阵组织相关 begin------------------->
DocList_Info.push("conditionParamList");
MatrixConditions = null; //矩阵组织条件字段集
ConditionInfo = new Object(); //矩阵组织条件字段信息集

// 选择矩阵组织
function selectOrgMatrix() {
	var dialog = new KMSSDialog(false, false);
	dialog.winTitle = '<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.select" bundle="sys-lbpmservice" />';
	var node = dialog.CreateTree('<kmss:message key="FlowChartObject.Lang.Node.orgMatrix" bundle="sys-lbpmservice" />');
	node.AppendBeanData("sysOrgMatrixService&parent=!{value}", null, null, true, null);
	dialog.notNull = true;
	dialog.BindingField('orgMatrixId', 'orgMatrixName');
	dialog.SetAfterShow(function(rtnData){
		if(rtnData!=null){
			initMatrixConditionInfo(this.rtnData.GetHashMapArray()[0].id);
			clearOrgMatrixParamInfo();
		}
	});
	dialog.Show();
}
// 选择结果字段
function selectMatrixResultField() {
	var sysOrgMatrixFdId = $("input[name=orgMatrixId]")[0].value;
	if (!sysOrgMatrixFdId) {
		alert('<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.checkIfEmpty" bundle="sys-lbpmservice" />');
		return;
	}
	var treeBean = 'sysOrgMatrixService&id={sysOrgMatrixFdId}&rtnType=2';
	Dialog_Tree(true, "matrixResultId", "matrixResultName", null, treeBean.replace("{sysOrgMatrixFdId}",sysOrgMatrixFdId), '选择矩阵结果');
}

// 添加条件字段赋值设置行
function addMatrixOrgConditionParamRow(){
	if (MatrixConditions == null || MatrixConditions.length == 0) {
		alert('<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.checkIfEmpty" bundle="sys-lbpmservice" />');
		return;
	}
	var fieldValues = new Object();
	var row = DocList_AddRow("conditionParamList",null,fieldValues);
	var conditionParamSelect = $(row).find("select[name='conditionParam']");
	MatrixConditions.forEach(function(condition, index){
		conditionParamSelect.append("<option value='"+condition.value+"'>"+condition.text+"</option>");
		if (index == 0) {
			conditionParamChange(conditionParamSelect[0]);
		}
	});
	return row;
}

//条件字段赋值设置
function assignConditionParamValue(target) {
	var tr = $(target).closest("tr");
	var idField = $(tr).find("input[name='conditionParam.expression.value']")[0];
	var nameField = $(tr).find("input[name='conditionParam.expression.text']")[0];
	Formula_Dialog(idField, nameField, FlowChartObject.FormFieldList, "String", null, "com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction", FlowChartObject.ModelName);
}

//条件字段切换
function conditionParamChange(self,type){
	if($(self).length == 0)
		return;
	var conditionTypeSelect = $(self).next("select");
	if (type) {
		conditionTypeSelect.val(type);
	} else {
		//切换条件字段时自动还原ID/Name下拉框的默认值
		conditionTypeSelect.val("fdId");
	}
	if (ConditionInfo[self.value]["type"] == "constant") {
		conditionTypeSelect.val("fdName");
		//常量类型不需要出现ID/Name的下拉选择
		conditionTypeSelect[0].style.display = "none";
		self.style.width = "100%";
	} else {
		//对象类型显示出ID/Name下拉框供选择
		conditionTypeSelect[0].style.display = "";
		self.style.width = "60%";
	}
	//悬浮可查看该条件字段的具体类型
	self.title = ConditionInfo[self.value]["type"];
}

// 清除矩阵组织相关参数的设置
function clearOrgMatrixParamInfo() {
	$("input[name=matrixResultId]").val("");
	$("input[name=matrixResultName]").val("");
	var rows = $("#conditionParamList")[0].rows;
	for (var i = rows.length - 1; i > 0; i --) {
		DocList_DeleteRow(rows[i]);
	}
}

// 矩阵设置保存校验
AttributeObject.CheckDataFuns.push(function(data) {
	if (data.handlerSelectType == "matrix") {
		if ($("input[name=orgMatrixId]")[0].value != "") {
			if (!$("input[name=matrixResultId]")[0].value) {
				alert('<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.checkIfResultEmpty" bundle="sys-lbpmservice" />');
				return false;
			}
			if ($("select[name='conditionParam']").length == 0) {
				alert('<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.checkIfConditionEmpty" bundle="sys-lbpmservice" />');
				return false;
			} else {
				var conditionValues = $("input[name='conditionParam.expression.value']");
				for (var i=0;i<conditionValues.length;i++) {
					if (conditionValues[i].value == "" || conditionValues[i].value == null) {
						alert('<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.checkIfConditionValueEmpty" bundle="sys-lbpmservice" />');
						return false;
					}
				}
			}
		}
	}
});
// 加载矩阵组织条件字段信息
function initMatrixConditionInfo(sysOrgMatrixFdId){
	if (!sysOrgMatrixFdId) {
		sysOrgMatrixFdId = $("input[name=orgMatrixId]")[0].value;
	}
	var dataBean = 'sysOrgMatrixService&id={sysOrgMatrixFdId}&rtnType=1';
	MatrixConditions = new KMSSData().AddBeanData(dataBean.replace("{sysOrgMatrixFdId}",sysOrgMatrixFdId)).GetHashMapArray();
	ConditionInfo = new Object();
	MatrixConditions.forEach(function(condition, index){
		ConditionInfo[condition.value] = condition;
	});
	// 获取版本号
	$.post('<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=getVersions" />', {'fdId': sysOrgMatrixFdId}, function(res) {
		if(res && res.length > 0) {
			for(var i = res.length-1; i>=0; i--){
				var marxVersionInfo = res[i];
				//防止未和sys-organization集成构建时项目报错
				var isEnable = marxVersionInfo.fdIsEnable;
				if(isEnable == true || isEnable == undefined){
					$("input[name='matrixVersion']").val(res[i].fdName);
					break;
				}
			}
		}
	}, 'json');
}

// 填充矩阵设置信息
function initOrgMatrixInfo() {
	if (AttributeObject.NodeData.handlerSelectType != "matrix") {
		return;
	}
	var config = AttributeObject.NodeData['handlerIds'];
	if (config != null && config != "") {
		var json = (new Function("return ("+ config + ")"))();
		//矩阵组织
		$("input[name=orgMatrixId]")[0].value = json.id;
		$("input[name=orgMatrixName]")[0].value = json.idText;
		$("input[name='matrixVersion']").val(json.version);
		initMatrixConditionInfo(json.id);
		//结果字段
		$("input[name=matrixResultId]")[0].value = json.results;
		$("input[name=matrixResultName]")[0].value = json.resultsText;
		$("input:radio[name=matrixResultOption][value="+json.option+"]").attr('checked','true');
		//条件字段参数表
		json.conditionals.forEach(function(obj,index){
			var row = addMatrixOrgConditionParamRow();
			var conditionParamSelect = $(row).find("select[name='conditionParam']");
			conditionParamSelect.val(obj.id);
			conditionParamChange(conditionParamSelect[0],obj.type);
			var expressionValue = $(row).find("input[name='conditionParam.expression.value']");
			expressionValue.val(obj.value);
			var expressionText = $(row).find("input[name='conditionParam.expression.text']");
			expressionText.val(obj.text);
		});
	}
}

AttributeObject.Init.AllModeFuns.push(initOrgMatrixInfo);

// 保存矩阵组织相关配置信息到节点定义里
function writeMatrixOrgJSON(data) {
	if (data.handlerSelectType != "matrix" || $("input[name=orgMatrixId]")[0].value == "") {
		return;
	}
	var config = [];
	config.push("{\"id\":");
	config.push("\"" + $("input[name=orgMatrixId]")[0].value + "\"");
	config.push(",\"idText\":");
	config.push("\"" + $("input[name=orgMatrixName]")[0].value + "\"");
	config.push(",\"version\":");
	config.push("\"" + $("input[name=matrixVersion]")[0].value + "\"");
	config.push(",\"results\":");
	config.push("\"" + $("input[name=matrixResultId]")[0].value + "\"");
	config.push(",\"resultsText\":");
	config.push("\"" + $("input[name=matrixResultName]")[0].value + "\"");
	config.push(",\"option\":");
	config.push("\"" + $('input[type=radio][name=matrixResultOption]:checked').val() + "\"");
	
	config.push(",\"conditionals\":");
	config.push(getConditionsParamJson());
	config.push("}");
	
	data['handlerIds'] = config.join("");
	data['handlerNames'] = $("input[name=orgMatrixName]")[0].value + "{" + $("input[name=matrixResultName]")[0].value + "}";
}

function getConditionsParamJson() {
	var rtn = [];
	
	var conditionParams = $("select[name='conditionParam']");
	var conditionTypes = $("select[name='conditionType']");
	var expressionValues = $("input[name='conditionParam.expression.value']");
	var expressionTexts = $("input[name='conditionParam.expression.text']");
	
	conditionParams.each(function(index,obj){
		rtn.push("{\"id\":\"" + conditionParams[index].value + "\""
				+ ",\"type\":\"" + conditionTypes[index].value + "\""
				+ ",\"value\":\"" + formatJson(expressionValues[index].value) + "\""
			    + ",\"text\":\"" + formatJson(expressionTexts[index].value) + "\"}");
	});
	
	return "[" + rtn.join(",") + "]";
}

function formatJson(value) {
	return value.replace(/"/ig,'\\"').replace(/\r\n/ig,'\\r\\n');
}

AttributeObject.AppendDataFuns.push(writeMatrixOrgJSON);
//<---------------- 矩阵组织相关 end------------------->
//<-----规则引擎----->
//审批人start
function initRuleQuoteToHandler(){
	var isShow = isShowRule('handler');
	if(isShow){
		if(!window.sysRuleQuoteToHandler){
			window.sysRuleQuoteToHandler = window.SysRuleQuote("wf_handlerIds","wf_handlerNames","handler",FlowChartObject.SysRuleTemplate,FlowChartObject.LbpmTemplateKey);
		}
		var config = AttributeObject.NodeData['handlerIds'];
		if (config != null && config != "") {
			var isEdit = AttributeObject.isEdit ? AttributeObject.isEdit() : FlowChartObject.IsEdit;
			var index;
			if(isEdit){
				index = window.sysRuleQuoteToHandler.initRuleQuote(config, 0, 'wf_handlerIds', 'wf_handlerNames', 'edit');
			}else{
				index = window.sysRuleQuoteToHandler.initRuleQuote(config, 0, 'wf_handlerIds', 'wf_handlerNames', 'view');
			}
			//进行了规则初始化就是规则
			$(".rule.handler").eq(0).show();
		}
	}else{
		//不存在机制内容，隐藏入口
		$("[name='wf_handlerSelectType'][value='rule']").parent().hide();
	}
}
AttributeObject.Init.AllModeFuns.push(initRuleQuoteToHandler);

function isShowRule(type){
	var isShow = false;
	if(FlowChartObject.SysRuleTemplate || (type=='handler' && AttributeObject.NodeData.handlerSelectType === "rule") || (type=='optHandler' && AttributeObject.NodeData.optHandlerSelectType === "rule")){
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
	return isShow;
}
//校验
function checkRuleDataToHandler(data){
	if (data.handlerSelectType != "rule") {
		return true;
	}
	return window.sysRuleQuoteToHandler.checkData();
}
AttributeObject.CheckDataFuns.push(checkRuleDataToHandler);
//写回数据
function writeRuleMapDataToHandler(data){
	if(!window.sysRuleQuoteToHandler){
		return;
	}
	window.sysRuleQuoteToHandler.writeData("wf_handlerSelectType",data);
}
AttributeObject.AppendDataFuns.push(writeRuleMapDataToHandler);
//审批人end
//备选人start
function initRuleQuoteToOptHandler(){
	var isShow = isShowRule('optHandler');
	if(isShow){
		if(!window.sysRuleQuoteToOptHandler){
			window.sysRuleQuoteToOptHandler = window.SysRuleQuote("wf_optHandlerIds","wf_optHandlerNames","optHandler",FlowChartObject.SysRuleTemplate,FlowChartObject.LbpmTemplateKey);
		}
		var config = AttributeObject.NodeData['optHandlerIds'];
		if (config != null && config != "") {
			var isEdit = AttributeObject.isEdit ? AttributeObject.isEdit() : FlowChartObject.IsEdit;
			var index;
			if(isEdit){
				index = window.sysRuleQuoteToOptHandler.initRuleQuote(config, 0, 'wf_optHandlerIds', 'wf_optHandlerNames', 'edit');
			}else{
				index = window.sysRuleQuoteToOptHandler.initRuleQuote(config, 0, 'wf_optHandlerIds', 'wf_optHandlerNames', 'view');
			}
			//进行了规则初始化就是规则
			$(".rule.optHandler").eq(0).show();
		}
	}else{
		//不存在机制内容，隐藏入口
		$("[name='wf_optHandlerSelectType'][value='rule']").parent().hide();
	}
}
AttributeObject.Init.AllModeFuns.push(initRuleQuoteToOptHandler);
//校验
function checkRuleDataToOptHandler(data){
	if (data.handlerSelectType != "rule") {
		return true;
	}
	return window.sysRuleQuoteToOptHandler.checkData();
}
AttributeObject.CheckDataFuns.push(checkRuleDataToOptHandler);
//写回数据
function writeRuleMapDataToOptHandler(data){
	if(!window.sysRuleQuoteToOptHandler){
		return;
	}
	window.sysRuleQuoteToOptHandler.writeData("wf_optHandlerSelectType",data);
}
AttributeObject.AppendDataFuns.push(writeRuleMapDataToOptHandler);
//备选人end
//选择
function selectRule(returnType,mode,key){
	if(key == 'handler'){
		window.sysRuleQuoteToHandler.selectRule(returnType,mode);
	}else if(key == 'optHandler'){
		window.sysRuleQuoteToOptHandler.selectRule(returnType,mode);
	}
}
//更新映射
function updateMapContent(value, obj, key){
	if(key == 'handler'){
		window.sysRuleQuoteToHandler.updateMapContent(value, obj);
	}else if(key == 'optHandler'){
		window.sysRuleQuoteToOptHandler.updateMapContent(value, obj);
	}
}

AttributeObject.AppendDataFuns.push(writeMapContentJSON);

function writeMapContentJSON(data){
	if (data.handlerSelectType === "rule") {
		var mapContent = $("[name='mapContent']").val();
		if (mapContent) {
			mapContent = JSON.parse(mapContent);
			var maps = mapContent.maps;
			data['ruleMaps'] = JSON.stringify(maps);
		}
	}
}
//<-----规则引擎----->


//<-----限时类型增加公式定义说明 start----->
function initRuleLimitTimeToHandler(){
	
	//限时类型 是节点启动为0 ，事务启动为1
	$("select[name='wf_dayOfPassRule']").change(function(){
		if(this.value=="0"){
			$('.passRuleLimitDiv').show();//按天按公式单选按钮div层显示
			$('.lbpmRulePassDiv').show();//超时执行动作选择公式器div显示
			
			//只要改变下拉框值，都需要将按公式定义器设置属性设置为按天选中
			$("input[type='radio'][name='wf_ruleTimeType'][value='0']").prop("checked",true);
			$("input[type='radio'][name='wf_ruleTimeType'][value='1']").prop("checked",false);
			
			$('.lbpmLimitDiv').show();//限时时间选择按天显示
			$('.lbpmRuleLimitDiv').hide();//限时时间选择公式方式隐藏
		}else{
			$('.passRuleLimitDiv').hide();//按天按公式单选按钮div层显示
			
			$('.lbpmRuleLimitDiv').hide();//限时时间选择公式方式隐藏
			$('.lbpmLimitDiv').show(); //限时时间选择按天显示
			
			$('.lbpmPassDiv').show(); //超时执行动作按天显示div 显示
			$('.lbpmRulePassDiv').hide();//超时执行动作选择公式器div隐藏
			//选择事务启动，需要将按公式定义器设置属性设置为按天
			$("input[type='radio'][name='wf_ruleTimeType'][value='0']").prop("checked",true);
			$("input[type='radio'][name='wf_ruleTimeType'][value='1']").prop("checked",false);

		}
	});
	
	
	var dayOfPassRule=$("select[name='wf_dayOfPassRule']").val();
	
	if(dayOfPassRule=="0"){
		$('.passRuleLimitDiv').show();
	}else{
		$('.passRuleLimitDiv').hide();
	}
	
	var dayOfPassTypeS=$("select[name='wf_dayOfPassType']").val();//超时处理执行动作下拉框
	
	$('input[type=radio][name=wf_ruleTimeType]').change(function() {
		if(this.value==0){
			$(".lbpmLimitDiv").show();
			$(".lbpmRuleLimitDiv").hide();
			
			//超时执行动作设置
			if(dayOfPassTypeS=="2"){
				$(".lbpmCommissionDiv").show();
				$(".lbpmPassDiv").hide();
			}else{
				$(".lbpmPassDiv").show();
				$(".lbpmCommissionDiv").hide();
			}
			
			$(".lbpmRulePassDiv").hide();
		}else{
			$(".lbpmLimitDiv").hide();
			$(".lbpmRuleLimitDiv").show();
			
			//超时执行动作设置
			$(".lbpmPassDiv").hide();
			$(".lbpmCommissionDiv").hide();
			
			$(".lbpmRulePassDiv").show();
		}
    });

	 var radioRuleTimeType = $("input:radio[name='wf_ruleTimeType']:checked").val();//判断是否被选中，选择是true，否则为false
	
	 if(radioRuleTimeType=="0"){
    	 $(".lbpmLimitDiv").show();
		 $(".lbpmRuleLimitDiv").hide();
		 
		//超时执行动作设置
		if(dayOfPassTypeS=="2"){
			$(".lbpmCommissionDiv").show();
			$(".lbpmPassDiv").hide();
		}else{
			$(".lbpmPassDiv").show();
			$(".lbpmCommissionDiv").hide();
		}

		$(".lbpmRulePassDiv").hide();
     }else{
    	 $(".lbpmLimitDiv").hide();
		 $(".lbpmRuleLimitDiv").show();
		 
		//超时执行动作设置
		$(".lbpmPassDiv").hide();
		$(".lbpmRulePassDiv").show();
		$(".lbpmCommissionDiv").hide();
     }
}
AttributeObject.Init.AllModeFuns.push(initRuleLimitTimeToHandler);
//<-----限时类型增加公式定义说明 end----->


</script>
<script type="text/javascript" src='<c:url value="/sys/rule/resources/js/common.js"/>'></script>
<script type="text/javascript" src='<c:url value="/sys/rule/resources/js/rule_quote.js"/>'></script>
