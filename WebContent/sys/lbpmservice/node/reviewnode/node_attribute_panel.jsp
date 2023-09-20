<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.lbpmext.businessauth.service.ILbpmExtBusinessSettingInfoService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextareaGroupTagNew"%>
<%
	ILbpmExtBusinessSettingInfoService lbpmExtBusinessSettingInfoService = (ILbpmExtBusinessSettingInfoService) SpringBeanUtil
			.getBean("lbpmExtBusinessSettingInfoService");
	String isOpinionTypeEnabled = lbpmExtBusinessSettingInfoService.getIsOpinionTypeEnabled("imissiveLbpmSwitch");
	request.setAttribute("isOpinionTypeEnabled", isOpinionTypeEnabled);
%>
<style>
.inputread {border:0px; color:#868686;background-color:#f6f6f6;}
.tdTitle {color:#666;width:75px;}
</style>
<!-- 自由流的审批节点属性面板 -->
<c:choose>
	<c:when test="${JsParam.isFixedNode eq 'true'}">
		<table width="590px" id="Label_Tabel">
			<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Base" bundle="sys-lbpm-engine" />">
				<td>
					<table width="100%" class="tb_normal">
						<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8">
							<c:param name="flowType" value="1" />
						</c:import>
						<tr>
							<td class="tdTitle"><kmss:message key="FlowChartObject.Lang.Node.handlerNames" bundle="sys-lbpmservice" /></td>
							<td>
								<span id="handlerSelectTypeSpan">
									<label><input type="radio" name="wf_handlerSelectType" value="org" onclick="switchHandlerSelectType(value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
									<label><input type="radio" name="wf_handlerSelectType" value="matrix" onclick="switchHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectOrgMatrix" bundle="sys-lbpmservice" /></label>
									<label><input type="radio" name="wf_handlerSelectType" value="formula" onclick="switchHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
									<br>
								</span>
								<input name="wf_handlerNames" class="inputsgl" style="width:300px" readonly>
								<input name="wf_handlerIds" type="hidden" orgattr="handlerIds:handlerNames">
								<input name="wf_titleImissive" type="hidden" />
								<input name="wf_showText" type="hidden" />
								<span id="SPAN_SelectType1">
									<a href="#" onclick="selectByOrg('wf_handlerIds', 'wf_handlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
								</span>
								<span id="SPAN_SelectType2" style="display:none ">
									<a href="#" onclick="selectByFormula('wf_handlerIds', 'wf_handlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
								</span>
								<span id="SPAN_SelectType3" style="display:none ">
									<a href="#" onclick="selectByMatrix('wf_handlerIds', 'wf_handlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
								</span>
								<span class="txtstrong">*</span>
								<br>
								<label>
									<input type="checkbox" name="wf_ignoreOnHandlerEmpty" value="true">
									<kmss:message key="FlowChartObject.Lang.Node.ignoreOnHandlerCalculateEmpty" bundle="sys-lbpmservice" />
								</label>
							</td>
						</tr>
						<tr>
							<td class="tdTitle"><kmss:message key="FlowChartObject.Lang.Node.processType" bundle="sys-lbpmservice" /></td>
							<td>
								<label>
									<input name="wf_processType" type="radio" value="0" checked>
									<kmss:message key="FlowChartObject.Lang.Node.processType_0" bundle="sys-lbpmservice" />
								</label><label>
									<input name="wf_processType" type="radio" value="1">
									<kmss:message key="FlowChartObject.Lang.Node.processType_1" bundle="sys-lbpmservice" />
								</label><label>
									<input name="wf_processType" type="radio" value="2">
									<kmss:message key="FlowChartObject.Lang.Node.processType_20" bundle="sys-lbpmservice" />
								</label>
								<c:import url="/sys/lbpmservice/node/common/node_handler_closeDataCoverageWarn_attribute.jsp" charEncoding="UTF-8" />
							</td>
						</tr>
						
						<c:if test="${isOpinionTypeEnabled eq 'true'}">
							<tr>
								<td class="tdTitle">意见配置参数</td>
								<td>
									<label><input name="wf_opinionConfig" type="checkbox" value="true">意见是否可配置</input></label>
								</td>
							</tr>
							<tr>
								<td class="tdTitle">默认意见类型</td>
								<td>
									<label>
										<xform:select property="wf_opinionType" showStatus="edit" subject="默认意见类型"  onValueChange="saveNodeData" value="">
											<xform:customizeDataSource className="com.landray.kmss.sys.lbpmservice.support.service.spring.LbpmAuditNoteTypeDataSource"></xform:customizeDataSource>
										</xform:select>
									</label>
								</td>
							</tr>
						</c:if>
						
						<tr>
							<td class="tdTitle" style="word-break:break-all;"><kmss:message key="FlowChartObject.Lang.Node.onHandlerSame" bundle="sys-lbpmservice"/></td>
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
							</td>
						</tr>
						<c:import url="/sys/lbpmservice/node/common/node_jump_attribute.jsp" charEncoding="UTF-8">
						</c:import>
						<tr>
							<td class="tdTitle"><bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.notify.type" /></td>
							<td id="NODE_TD_notifyType">
								<kmss:editNotifyType property="node_notifyType" value="no" /><br />
							</td>
						</tr>
						<tr>
							<td class="tdTitle"><kmss:message key="FlowChartObject.Lang.Node.popedom" bundle="sys-lbpmservice" /></td>
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
							<td class="tdTitle"><kmss:message key="FlowChartObject.Lang.Node.flowPopedom" bundle="sys-lbpmservice" /></td>
							<td>
								<label>
									<input name="wf_flowPopedom" type="radio" value="0" checked>
									<kmss:message key="FlowChartObject.Lang.Node.flowPopedom_0" bundle="sys-lbpmservice" />
								</label><label>
									<input name="wf_flowPopedom" type="radio" value="1">
									<kmss:message key="FlowChartObject.Lang.Node.flowPopedom_1" bundle="sys-lbpmservice" />
								</label><label id="flowPopedom_modify">
									<input name="wf_flowPopedom" type="radio" value="2">
									<kmss:message key="FlowChartObject.Lang.Node.flowPopedom_2" bundle="sys-lbpmservice" />
								</label>
							</td>
						</tr>
						<c:import url="/sys/lbpmservice/node/common/node_handler_common_operation.jsp" charEncoding="UTF-8">
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
						
						<c:import url="/sys/lbpm/flowchart/page/node_ext_attribute.jsp" charEncoding="UTF-8">
							<c:param name="position" value="base" />
							<c:param name="nodeType" value="${param.nodeType}" />
							<c:param name="modelName" value="${param.modelName}" />
						</c:import>
						
						<tr style="display:none">
							   <td>
									<table class="tb_normal" width="100%">
										<tr>
											<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.optHandlerNames" bundle="sys-lbpmservice" /></td>
											<td colspan="2">
												<label><input type="radio" name="wf_optHandlerSelectType" value="org" onclick="switchOptHandlerSelectType(value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
												<label><input type="radio" name="wf_optHandlerSelectType" value="formula" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
												<label><input type="radio" name="wf_optHandlerSelectType" value="mechanism" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectMechanism" bundle="sys-lbpmservice" /></label>
												<label><input type="radio" name="wf_optHandlerSelectType" value="dept" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectDept" bundle="sys-lbpmservice" /></label>
												<input name="wf_optHandlerIds" type="hidden" orgattr="optHandlerIds:optHandlerNames">
												<input name="wf_optHandlerNames" class="inputsgl" style="width:400px" readonly>
												<span id="SPAN_OptSelectType1">
												<a href="#" onclick="Dialog_Address(true, 'wf_optHandlerIds', 'wf_optHandlerNames', null, ORG_TYPE_POSTORPERSON | ORG_TYPE_DEPT | ORG_TYPE_ROLE | ORG_TYPE_GROUP);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
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
											<td width="100px" rowspan="4"><kmss:message key="FlowChartObject.Lang.Node.dayOfHandle" bundle="sys-lbpmservice" /></td>
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
												<%-- <br><kmss:message key="FlowChartObject.Lang.Node.dayOfHandleHelp" bundle="sys-lbpmservice" /> --%>
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
										<tr>
											<c:import url="/sys/lbpmservice/node/common/node_operationScope_attribute.jsp" charEncoding="UTF-8">
											</c:import>
										</tr>
										<c:import url="/sys/lbpm/flowchart/page/node_ext_attribute.jsp" charEncoding="UTF-8">
											<c:param name="position" value="advance" />
											<c:param name="nodeType" value="${param.nodeType}" />
											<c:param name="modelName" value="${param.modelName }" />
										</c:import>
									</table>
								</td>
								<td>
								<c:import url="/sys/lbpmservice/node/common/node_right_attribute.jsp" charEncoding="UTF-8" />
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
			<c:import url="/sys/lbpm/flowchart/page/node_variant_attribute.jsp" charEncoding="UTF-8">
				<c:param name="nodeType" value="${param.nodeType}" />
				<c:param name="modelName" value="${param.modelName }" />
			</c:import>
			<c:import url="/sys/lbpmservice/node/common/node_custom_notify_attribute.jsp" charEncoding="UTF-8">
				<c:param name="nodeType" value="${param.nodeType}" />
				<c:param name="modelName" value="${param.modelName }" />
			</c:import>
			<c:import url="/sys/lbpm/flowchart/page/node_variant_attribute_panel.jsp" charEncoding="UTF-8">
				<c:param name="nodeType" value="${param.nodeType}" />
				<c:param name="modelName" value="${param.modelName }" />
			</c:import>
		</table>
	</c:when>
	<c:otherwise>
		<style>
			.inputsgl {width:150px;border:0px !important;margin:3px 0 3px 0;}
		</style>
		<table width="420px" id="Label_Tabel">
			<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Base" bundle="sys-lbpm-engine" />">
				<td style="background-color:#f6f6f6 !important;">
					<table width="95%" style="background-color:#f6f6f6;" class="tb_normal">
						<c:import url="/sys/lbpmservice/node/common/node_fixed_attribute.jsp" charEncoding="UTF-8">
						</c:import>
						<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8">
							<c:param name="flowType" value="1" />
						</c:import>
						<tr>
							<td class="tdTitle"><kmss:message key="FlowChartObject.Lang.Node.handlerNames" bundle="sys-lbpmservice" /></td>
							<td>
								<span id="handlerSelectTypeSpan">
									<label><input type="radio" name="wf_handlerSelectType" value="org" onclick="switchHandlerSelectType(value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
									<label><input type="radio" name="wf_handlerSelectType" value="matrix" onclick="switchHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectOrgMatrix" bundle="sys-lbpmservice" /></label>
									<label><input type="radio" name="wf_handlerSelectType" value="formula" onclick="switchHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
									<br>
								</span>
								<input name="wf_handlerNames" class="inputsgl" style="width:200px" readonly>
								<input name="wf_handlerIds" type="hidden" orgattr="handlerIds:handlerNames">
								<input name="wf_titleImissive" type="hidden" />
								<input name="wf_showText" type="hidden" />
								<span id="SPAN_SelectType1">
									<a href="#" onclick="selectByOrg('wf_handlerIds', 'wf_handlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
								</span>
								<span id="SPAN_SelectType2" style="display:none ">
									<a href="#" onclick="selectByFormula('wf_handlerIds', 'wf_handlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
								</span>
								<span id="SPAN_SelectType3" style="display:none ">
									<a href="#" onclick="selectByMatrix('wf_handlerIds', 'wf_handlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
								</span>
								<span class="txtstrong">*</span>
								<br>
								<label>
									<input type="checkbox" name="wf_ignoreOnHandlerEmpty" value="true">
									<kmss:message key="FlowChartObject.Lang.Node.ignoreOnHandlerCalculateEmpty" bundle="sys-lbpmservice" />
								</label>
							</td>
						</tr>
						<tr>
							<td class="tdTitle"><kmss:message key="FlowChartObject.Lang.Node.processType" bundle="sys-lbpmservice" /></td>
							<td>
								<label>
									<input name="wf_processType" type="radio" value="0" checked>
									<kmss:message key="FlowChartObject.Lang.Node.processType_0" bundle="sys-lbpmservice" />
								</label><label>
									<input name="wf_processType" type="radio" value="1">
									<kmss:message key="FlowChartObject.Lang.Node.processType_1" bundle="sys-lbpmservice" />
								</label><label>
									<input name="wf_processType" type="radio" value="2">
									<kmss:message key="FlowChartObject.Lang.Node.processType_20" bundle="sys-lbpmservice" />
								</label><br>
								<c:import url="/sys/lbpmservice/node/common/node_handler_closeDataCoverageWarn_attribute.jsp" charEncoding="UTF-8" />
							</td>
						</tr>
						
						<c:if test="${isOpinionTypeEnabled eq 'true'}">
							<tr>
								<td class="tdTitle">意见配置参数</td>
								<td>
									<label><input name="wf_opinionConfig" type="checkbox" value="true" checked ='checked'>意见是否可配置</input></label>
								</td>
							</tr>
							<tr>
								<td class="tdTitle">默认意见类型</td>
								<td>
									<label>
											<xform:select property="wf_opinionType" showStatus="edit" subject="默认意见类型"  onValueChange="saveNodeData" value="">
												<xform:customizeDataSource className="com.landray.kmss.sys.lbpmservice.support.service.spring.LbpmAuditNoteTypeDataSource"></xform:customizeDataSource>
											</xform:select>
									</label>
								</td>
							</tr>
						</c:if>
						
						<tr>
							<td class="tdTitle" style="word-break:break-all;"><kmss:message key="FlowChartObject.Lang.Node.onHandlerSame" bundle="sys-lbpmservice"/></td>
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
							</td>
						</tr>
						<c:import url="/sys/lbpmservice/node/common/node_jump_attribute.jsp" charEncoding="UTF-8">
						</c:import>
						<tr>
							<td class="tdTitle"><bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.notify.type" /></td>
							<td id="NODE_TD_notifyType">
								<kmss:editNotifyType property="node_notifyType" value="no" /><br />
							</td>
						</tr>
						<tr>
							<td class="tdTitle"><kmss:message key="FlowChartObject.Lang.Node.popedom" bundle="sys-lbpmservice" /></td>
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
							<td class="tdTitle"><kmss:message key="FlowChartObject.Lang.Node.flowPopedom" bundle="sys-lbpmservice" /></td>
							<td>
								<label>
									<input name="wf_flowPopedom" type="radio" value="0" checked>
									<kmss:message key="FlowChartObject.Lang.Node.flowPopedom_0" bundle="sys-lbpmservice" />
								</label><label>
									<input name="wf_flowPopedom" type="radio" value="1">
									<kmss:message key="FlowChartObject.Lang.Node.flowPopedom_1" bundle="sys-lbpmservice" />
								</label><label id="flowPopedom_modify">
									<input name="wf_flowPopedom" type="radio" value="2">
									<kmss:message key="FlowChartObject.Lang.Node.flowPopedom_2" bundle="sys-lbpmservice" />
								</label>
							</td>
						</tr>
						<c:import url="/sys/lbpmservice/node/common/node_handler_common_operation.jsp" charEncoding="UTF-8">
							<c:param name="isFreeFlow" value="true" />
						</c:import>
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
						
						<c:import url="/sys/lbpm/flowchart/page/node_ext_attribute.jsp" charEncoding="UTF-8">
							<c:param name="position" value="base" />
							<c:param name="nodeType" value="${param.nodeType}" />
							<c:param name="modelName" value="${param.modelName}" />
						</c:import>
						
						<tr style="display:none">
							   <td>
									<table class="tb_normal" width="100%">
										<tr>
											<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.optHandlerNames" bundle="sys-lbpmservice" /></td>
											<td colspan="2">
												<label><input type="radio" name="wf_optHandlerSelectType" value="org" onclick="switchOptHandlerSelectType(value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
												<label><input type="radio" name="wf_optHandlerSelectType" value="formula" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
												<label><input type="radio" name="wf_optHandlerSelectType" value="mechanism" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectMechanism" bundle="sys-lbpmservice" /></label>
												<label><input type="radio" name="wf_optHandlerSelectType" value="dept" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectDept" bundle="sys-lbpmservice" /></label>
												<input name="wf_optHandlerIds" type="hidden" orgattr="optHandlerIds:optHandlerNames">
												<input name="wf_optHandlerNames" class="inputsgl" style="width:400px" readonly>
												<span id="SPAN_OptSelectType1">
												<a href="#" onclick="Dialog_Address(true, 'wf_optHandlerIds', 'wf_optHandlerNames', null, ORG_TYPE_POSTORPERSON | ORG_TYPE_DEPT | ORG_TYPE_ROLE | ORG_TYPE_GROUP);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
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
											<td width="100px" rowspan="4"><kmss:message key="FlowChartObject.Lang.Node.dayOfHandle" bundle="sys-lbpmservice" /></td>
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
												<%-- <br><kmss:message key="FlowChartObject.Lang.Node.dayOfHandleHelp" bundle="sys-lbpmservice" /> --%>
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
										<tr>
											<c:import url="/sys/lbpmservice/node/common/node_operationScope_attribute.jsp" charEncoding="UTF-8">
											</c:import>
										</tr>
										<c:import url="/sys/lbpm/flowchart/page/node_ext_attribute.jsp" charEncoding="UTF-8">
											<c:param name="position" value="advance" />
											<c:param name="nodeType" value="${param.nodeType}" />
											<c:param name="modelName" value="${param.modelName }" />
										</c:import>
									</table>
								</td>
								<td>
								<c:import url="/sys/lbpmservice/node/common/node_right_attribute.jsp" charEncoding="UTF-8" />
								</td>
								<td>
								<c:import url="/sys/lbpm/flowchart/page/node_event_attribute.jsp" charEncoding="UTF-8" />
								</td>
						</tr>
					</table>
				</td>
			</tr>
			<%-- <tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Popedom" bundle="sys-lbpmservice" />">
				<td></td>
			</tr> --%>
			<c:import url="/sys/lbpm/flowchart/page/node_variant_attribute_panel.jsp" charEncoding="UTF-8">
				<c:param name="nodeType" value="${param.nodeType}" />
				<c:param name="modelName" value="${param.modelName }" />
			</c:import>
		</table>
	</c:otherwise>
</c:choose>


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
var ignoreOnFutureHandlerSame = AttributeObject.NodeData["ignoreOnFutureHandlerSame"];
var processType = AttributeObject.NodeData["processType"];
var repeatDayOfNotify = AttributeObject.NodeData["repeatDayOfNotify"];
var titleImissive = AttributeObject.NodeData["titleImissive"];
AttributeObject.Init.AllModeFuns.push(function() {

	if(!handlerSelectType || (handlerSelectType!="formula" && handlerSelectType!="matrix")){
		document.getElementById('SPAN_SelectType1').style.display='';
		document.getElementById('SPAN_SelectType2').style.display='none';
		document.getElementById('SPAN_SelectType3').style.display='none';
	}else{
		document.getElementById('SPAN_SelectType1').style.display='none';
		if (handlerSelectType=="formula") {
			document.getElementById('SPAN_SelectType2').style.display='';
		} else if (handlerSelectType=="matrix") {
			document.getElementById('SPAN_SelectType3').style.display='';
		}
	}

	if (optHandlerSelectType=="formula"){
		document.getElementById('SPAN_OptSelectType1').style.display='none';
		document.getElementById('SPAN_OptSelectType2').style.display='';
		document.getElementById('DIV_OptHandlerCalType').style.display='none';
		document.getElementsByName("wf_optHandlerNames")[0].style.display="";
	} else if (!optHandlerSelectType || optHandlerSelectType=="org"){
		document.getElementById('SPAN_OptSelectType1').style.display='';
		document.getElementById('SPAN_OptSelectType2').style.display='none';
		document.getElementById('DIV_OptHandlerCalType').style.display='';
		document.getElementsByName("wf_optHandlerNames")[0].style.display="";
	} else {
		document.getElementById('SPAN_OptSelectType1').style.display='none';
		document.getElementById('SPAN_OptSelectType2').style.display='none';
		document.getElementById('DIV_OptHandlerCalType').style.display='none';
		document.getElementsByName("wf_optHandlerNames")[0].style.display="none";
	}

	initHandlerSameSelect(ignoreOnHandlerSame,onAdjoinHandlerSame,ignoreOnFutureHandlerSame);
	
	AttributeObject.Utils.switchOrgAttributes(document.getElementsByName("wf_handlerIds")[0], handlerSelectType);
	AttributeObject.Utils.switchOrgAttributes(document.getElementsByName("wf_optHandlerIds")[0], optHandlerSelectType);

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
	
	/** # 63881 start **/
	  if (typeof AttributeObject.NodeData["canModifyMainDoc"] === "undefined"){
	    var _isEditMainDocument = settingInfo["isEditMainDocument"];
	    if(_isEditMainDocument === "true"){
	      $("input[name='wf_canModifyMainDoc']").prop("checked",true);
	    }else{
	      $("input[name='wf_canModifyMainDoc']").prop("checked",false);
	    }
	  }
	  /** # 63881 end **/
	
	if (typeof AttributeObject.NodeData["canAddAuditNoteAtt"] === "undefined"){
		var _isCanAddAuditNoteAtt = settingInfo["isCanAddAuditNoteAtt"];
		if (_isCanAddAuditNoteAtt === "false"){
			$("input[name='wf_canAddAuditNoteAtt']").prop("checked",false);
		}
	}
	if (!AttributeObject.NodeData["flowPopedom"]) {
		if(settingInfo["defaultFlowPopedom"]){
			var flowPopedom = (settingInfo["defaultFlowPopedom"] > AttributeObject.FLOWTYPE_POPEDOM) ? AttributeObject.FLOWTYPE_POPEDOM : settingInfo["defaultFlowPopedom"];
			$('input[type=radio][name=wf_flowPopedom]').each(function(i,val){
				 if (this.value == flowPopedom) {
			        	this.checked = true;
			        }
			});
		}
	}
	var isOpenNewWin = "${JsParam.isOpenNewWin eq 'true'}";
	if(isOpenNewWin!="true"){
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
	}
	
	//公文定制权限名称
	if(titleImissive){
		document.getElementsByName("wf_titleImissive").value = titleImissive;
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
	document.getElementsByName("wf_handlerIds")[0].value = "";
	document.getElementsByName("wf_handlerNames")[0].value = "";
	
	AttributeObject.Utils.switchOrgAttributes(document.getElementsByName("wf_handlerIds")[0], handlerSelectType);
}
// 备选审批人选择方式
function switchOptHandlerSelectType(value) {
	if(optHandlerSelectType==value)
		return;
	optHandlerSelectType = value;
	document.getElementById('SPAN_OptSelectType1').style.display=optHandlerSelectType=="org"?"":"none";
	document.getElementById('SPAN_OptSelectType2').style.display=optHandlerSelectType=="formula"?"":"none";
	document.getElementById('DIV_OptHandlerCalType').style.display=optHandlerSelectType=="org"?"":"none";
	document.getElementsByName("wf_optHandlerNames")[0].style.display=(optHandlerSelectType == "formula" || optHandlerSelectType == "org")?"":"none";
	document.getElementsByName("wf_optHandlerIds")[0].value = "";
	document.getElementsByName("wf_optHandlerNames")[0].value = "";

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
		$("input[name=wf_repeatTimesDayOfNotify]")[0].value = "1";
		$("input[name=wf_intervalDayOfNotify]")[0].value = "0";
		$("input[name=wf_intervalHourOfNotify]")[0].value = "0";
		$("input[name=wf_intervalMinuteOfNotify]")[0].value = "0";
	}
}

AttributeObject.CheckDataFuns.push(function(data) {
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
	/*if (!data.handlerIds || data.handlerIds == "") {
		alert('<kmss:message key="FlowChartObject.Lang.Node.checkHandlerEmpty" bundle="sys-lbpmservice" />');
		return false;
	}*/
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
	saveNodeData();
}
function selectByOrg(idField, nameField){
	var orgType = ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE;
	Dialog_Address(true, idField, nameField, null, orgType,function(a,b,c){
		var hName = $("[name='"+nameField+"']").val();
		var wf_titleImissive = $("[name='wf_titleImissive']").val();
		if(hName){
			hName = hName.replace(/;/g,",");
		}
		if(wf_titleImissive){
			hName = wf_titleImissive + hName;
			$("[name='wf_showText']").val(hName);
			$("[name='wf_showText']").trigger($.Event("change"));
		}
	});
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
//使用矩阵组织选择
function selectByMatrix(idField, nameField){
	// 弹出矩阵组织设置窗口
	var dialog = new KMSSDialog();
	dialog.FormFieldList = FlowChartObject.FormFieldList;
	dialog.ModelName = FlowChartObject.ModelName;
	dialog.BindingField(idField, nameField);
	dialog.URL = Com_Parameter.ContextPath + "sys/lbpmservice/node/common/node_handler_matrix_config.jsp";
	var size = getSizeForAddress();
	dialog.Show(size.width, size.height);
}
AttributeObject.Init.EditModeFuns.push(function(nodeData) {
	//多语言
	_initPropLang4Edit("nodeDesc",nodeData,"description","_");
	
	if (AttributeObject.FLOWTYPE_POPEDOM != AttributeObject.FLOWTYPE_POPEDOM_MODIFY) {
		$("#flowPopedom_modify").hide();
	}
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

	nodeData.operations = new Array();
	var data = new KMSSData();
	//edge浏览器设置缓存报RTC服务器不可用?
	data.UseCache = false;
	data.AddBeanData("getOperTypesByNodeService&nodeType=freeFlowReviewNode");
	data = data.GetHashMapArray();
	var refId = null;
	for(var j=0;j<data.length;j++){
		if(data[j].isDefault=="true"){
			refId = data[j].value;
			break;
		}
	}
	if(refId==null){
		alert(FlowChartObject.Lang.configDefaultOperationType_ApprovalNode);
		return;
	}
	nodeData.operations.refId = refId;
	var flowPopedom=$('input:radio[name="wf_flowPopedom"]:checked').val();
	if (flowPopedom && flowPopedom != "0") {
		nodeData["canModifyFlow"]="true";
	}
});
</script>