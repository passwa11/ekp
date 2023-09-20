<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-lbpmservice" key="module.node.paramsSetup.defaultSetting"/></span>
		</h2>
		
		<html:form action="/sys/lbpmservice/support/lbpmConfigAction.do">
		<center>
		<div style="margin:auto auto 60px;">
		<%-- <table id="Label_Tabel" width=95%>
			<tr LKS_LabelName="<bean:message bundle='sys-lbpmservice-support' key='lbpmSetting.templateDefine.defaultValues'/>">
				<td> --%>
			<ui:tabpanel>
				<ui:content  title="${lfn:message('sys-lbpmservice-support:lbpmSetting.templateDefine.defaultValues')}">
					<table class="tb_normal" width=100%>
						<!-- 默认通知方式 -->
						<tr>
							<td class="td_normal_title" width=20%>
								<bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.notify.type.default"/>
							</td>
							<td width=80%>
								<kmss:editNotifyType property="value(defaultNotifyType)" value="${sysAppConfigForm.map.defaultNotifyType}"/>
							</td>
						</tr>
						
						<!-- 审批操作相关设置项 -->
						<tr>
							<td class="td_normal_title" width="20%">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.operations"/>
							</td>
							<td width="80%">
								<table class="tb_normal" width="100%">
									<tr>
										<td>
											<c:if test="${sysAppConfigForm.map.isMultiCommunicateEnabledDefault != null}">
												<xform:checkbox property="value(isMultiCommunicateEnabledDefault)" showStatus="edit">
													<xform:simpleDataSource value="true">
														<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isMultiCommunicateEnabledDefault" />
													</xform:simpleDataSource>
												</xform:checkbox>
											</c:if>
											<c:if test="${sysAppConfigForm.map.isMultiCommunicateEnabledDefault == null}">
												<xform:checkbox property="value(isMultiCommunicateEnabledDefault)" showStatus="edit" value="true">
													<xform:simpleDataSource value="true">
														<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isMultiCommunicateEnabledDefault" />
													</xform:simpleDataSource>
												</xform:checkbox>
											</c:if>
											<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isMultiCommunicateEnabledDefault.desc"/>
											<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isMultiCommunicateEnabledDefault');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
										</td>
									</tr>
									
									<tr>
										<td>
											<c:if test="${sysAppConfigForm.map.isHiddenCommunicateNoteEnabledDefault != null}">
												<xform:checkbox property="value(isHiddenCommunicateNoteEnabledDefault)" showStatus="edit">
													<xform:simpleDataSource value="true">
														<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenCommunicateNoteEnabledDefault" />
													</xform:simpleDataSource>
												</xform:checkbox>
											</c:if>
											<c:if test="${sysAppConfigForm.map.isHiddenCommunicateNoteEnabledDefault == null}">
												<xform:checkbox property="value(isHiddenCommunicateNoteEnabledDefault)" showStatus="edit" value="true">
													<xform:simpleDataSource value="true">
														<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenCommunicateNoteEnabledDefault" />
													</xform:simpleDataSource>
												</xform:checkbox>
											</c:if>
											<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isHiddenCommunicateNoteEnabledDefault.desc"/>
											<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isHiddenCommunicateNoteEnabledDefault');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
										</td>
									</tr>
									
									<!-- 指纹审批，建议可以设置全局流程的开启/关闭;默认开启  -->
									<tr>
										<td>
											<c:if test="${sysAppConfigForm.map.isCanFingerPrintReview != null}">
													<xform:checkbox property="value(isCanFingerPrintReview)" showStatus="edit">
														<xform:simpleDataSource value="true">
															<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isCanFingerPrintReview" />
														</xform:simpleDataSource>
													</xform:checkbox>
											</c:if>
											<c:if test="${sysAppConfigForm.map.isCanFingerPrintReview == null}">
												<xform:checkbox property="value(isCanFingerPrintReview)" showStatus="edit" value="false">
													<xform:simpleDataSource value="true">
														<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isCanFingerPrintReview" />
													</xform:simpleDataSource>
												</xform:checkbox>
											</c:if>
											<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isCanFingerPrintReview.desc"/>
											<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isCanFingerPrintReview');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
										</td>
									</tr>
									<!-- 移动端签字节点和审批节点是否需要手写签字  -->
									<tr>
										<td>
											<c:if test="${sysAppConfigForm.map.needMobileHandWrittenSignatureReviewNode != null}">
												<xform:checkbox property="value(needMobileHandWrittenSignatureReviewNode)" showStatus="edit">
													<xform:simpleDataSource value="true">
														<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.needMobileReviewSignature.reviewNode" />
													</xform:simpleDataSource>
												</xform:checkbox>
											</c:if>
											<c:if test="${sysAppConfigForm.map.needMobileHandWrittenSignatureReviewNode == null}">
												<xform:checkbox property="value(needMobileHandWrittenSignatureReviewNode)" showStatus="edit" value="false">
													<xform:simpleDataSource value="true">
														<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.needMobileReviewSignature.reviewNode" />
													</xform:simpleDataSource>
												</xform:checkbox>
											</c:if></br>
											<c:if test="${sysAppConfigForm.map.needMobileHandWrittenSignatureSignNode != null}">
												<xform:checkbox property="value(needMobileHandWrittenSignatureSignNode)" showStatus="edit">
													<xform:simpleDataSource value="true">
														<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.needMobileReviewSignature.signNode" />
													</xform:simpleDataSource>
												</xform:checkbox>
											</c:if>
											<c:if test="${sysAppConfigForm.map.needMobileHandWrittenSignatureSignNode == null}">
												<xform:checkbox property="value(needMobileHandWrittenSignatureSignNode)" showStatus="edit" value="false">
													<xform:simpleDataSource value="true">
														<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.needMobileReviewSignature.signNode" />
													</xform:simpleDataSource>
												</xform:checkbox>
											</c:if>
											<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.needMobileReviewSignature.desc"/>
											<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('needMobileHandWrittenSignatureReviewNode');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
										</td>
									</tr>
								</table>
								
							</td>
						</tr>
						
						<!-- 操作人员限定范围 -->
						<tr>
							<td class="td_normal_title" width=20% rowspan="4"><kmss:message key="FlowChartObject.Lang.Node.operationScope" bundle="sys-lbpmservice" /></td>
							<td><kmss:message key="lbmp.operation.handler_commission" bundle="sys-lbpmservice" />&nbsp;&nbsp;&nbsp;&nbsp;
							<span>
								<c:if test="${sysAppConfigForm.map.handlerCommissionDefaultScope == null}">
									<label>
										<input type="radio" name="value(handlerCommissionDefaultScope)" value="all" checked = "checked">
										<kmss:message key="FlowChartObject.Lang.Node.operationScope.all" bundle="sys-lbpmservice" />
									</label>
									&nbsp;&nbsp;
									<label>
										<input type="radio" name="value(handlerCommissionDefaultScope)" value="org">
										<kmss:message key="FlowChartObject.Lang.Node.operationScope.org" bundle="sys-lbpmservice" />
									</label>
									&nbsp;&nbsp;
									<label>
										<input type="radio" name="value(handlerCommissionDefaultScope)" value="dept">
										<kmss:message key="FlowChartObject.Lang.Node.operationScope.dept" bundle="sys-lbpmservice" />
									</label>
									&nbsp;&nbsp;
									<label>
										<input type="radio" name="value(handlerCommissionDefaultScope)" value="custom">
										<kmss:message key="FlowChartObject.Lang.Node.operationScope.custom" bundle="sys-lbpmservice" />
									</label>
								</c:if>
								<c:if test="${sysAppConfigForm.map.handlerCommissionDefaultScope != null}">
									<xform:radio property="value(handlerCommissionDefaultScope)" showStatus="edit">
										<xform:simpleDataSource value="all"><kmss:message key="FlowChartObject.Lang.Node.operationScope.all" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
										<xform:simpleDataSource value="org"><kmss:message key="FlowChartObject.Lang.Node.operationScope.org" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
										<xform:simpleDataSource value="dept"><kmss:message key="FlowChartObject.Lang.Node.operationScope.dept" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
										<xform:simpleDataSource value="custom"><kmss:message key="FlowChartObject.Lang.Node.operationScope.custom" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
									</xform:radio>
								</c:if>
							</span>
							</td>
						</tr>
						<tr>
							<td><kmss:message key="lbmp.operation.handler_communicate" bundle="sys-lbpmservice" />&nbsp;&nbsp;&nbsp;&nbsp;
							<span>
								<c:if test="${sysAppConfigForm.map.handlerCommunicateDefaultScope == null}">
									<label>
										<input type="radio" name="value(handlerCommunicateDefaultScope)" value="all" checked = "checked">
										<kmss:message key="FlowChartObject.Lang.Node.operationScope.all" bundle="sys-lbpmservice" />
									</label>
									&nbsp;&nbsp;
									<label>
										<input type="radio" name="value(handlerCommunicateDefaultScope)" value="org">
										<kmss:message key="FlowChartObject.Lang.Node.operationScope.org" bundle="sys-lbpmservice" />
									</label>
									&nbsp;&nbsp;
									<label>
										<input type="radio" name="value(handlerCommunicateDefaultScope)" value="dept">
										<kmss:message key="FlowChartObject.Lang.Node.operationScope.dept" bundle="sys-lbpmservice" />
									</label>
									&nbsp;&nbsp;
									<label>
										<input type="radio" name="value(handlerCommunicateDefaultScope)"  value="custom">
										<kmss:message key="FlowChartObject.Lang.Node.operationScope.custom" bundle="sys-lbpmservice" />
									</label>
								</c:if>
								<c:if test="${sysAppConfigForm.map.handlerCommunicateDefaultScope != null}">
									<xform:radio property="value(handlerCommunicateDefaultScope)" showStatus="edit">
										<xform:simpleDataSource value="all"><kmss:message key="FlowChartObject.Lang.Node.operationScope.all" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
										<xform:simpleDataSource value="org"><kmss:message key="FlowChartObject.Lang.Node.operationScope.org" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
										<xform:simpleDataSource value="dept"><kmss:message key="FlowChartObject.Lang.Node.operationScope.dept" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
										<xform:simpleDataSource value="custom"><kmss:message key="FlowChartObject.Lang.Node.operationScope.custom" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
									</xform:radio>
								</c:if>
							</span>
							</td>
						</tr>
						<tr>
							<td><kmss:message key="lbpmOperations.fdOperType.processor.additionSign" bundle="sys-lbpmservice-operation-handler" />&nbsp;&nbsp;&nbsp;&nbsp;
							<span>
								<c:if test="${sysAppConfigForm.map.handlerAdditionSignDefaultScope == null}">
									<label>
										<input type="radio" name="value(handlerAdditionSignDefaultScope)" value="all" checked = "checked">
										<kmss:message key="FlowChartObject.Lang.Node.operationScope.all" bundle="sys-lbpmservice" />
									</label>
									&nbsp;&nbsp;
									<label>
										<input type="radio" name="value(handlerAdditionSignDefaultScope)" value="org">
										<kmss:message key="FlowChartObject.Lang.Node.operationScope.org" bundle="sys-lbpmservice" />
									</label>
									&nbsp;&nbsp;
									<label>
										<input type="radio" name="value(handlerAdditionSignDefaultScope)" value="dept">
										<kmss:message key="FlowChartObject.Lang.Node.operationScope.dept" bundle="sys-lbpmservice" />
									</label>
									&nbsp;&nbsp;
									<label>
										<input type="radio" name="value(handlerAdditionSignDefaultScope)" value="custom">
										<kmss:message key="FlowChartObject.Lang.Node.operationScope.custom" bundle="sys-lbpmservice" />
									</label>
								</c:if>
								<c:if test="${sysAppConfigForm.map.handlerAdditionSignDefaultScope != null}">
									<xform:radio property="value(handlerAdditionSignDefaultScope)" showStatus="edit">
										<xform:simpleDataSource value="all"><kmss:message key="FlowChartObject.Lang.Node.operationScope.all" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
										<xform:simpleDataSource value="org"><kmss:message key="FlowChartObject.Lang.Node.operationScope.org" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
										<xform:simpleDataSource value="dept"><kmss:message key="FlowChartObject.Lang.Node.operationScope.dept" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
										<xform:simpleDataSource value="custom"><kmss:message key="FlowChartObject.Lang.Node.operationScope.custom" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
									</xform:radio>
								</c:if>
							</span>
							</td>
						</tr>
						<tr>
							<td><kmss:message key="lbpmOperations.fdOperType.processor.assign" bundle="sys-lbpmservice-operation-assignment" />&nbsp;&nbsp;&nbsp;&nbsp;
							<span>
								<c:if test="${sysAppConfigForm.map.handlerAssignDefaultScope == null}">
									<label>
										<input type="radio" name="value(handlerAssignDefaultScope)" value="all" checked = "checked">
										<kmss:message key="FlowChartObject.Lang.Node.operationScope.all" bundle="sys-lbpmservice" />
									</label>
									&nbsp;&nbsp;
									<label>
										<input type="radio" name="value(handlerAssignDefaultScope)" value="org">
										<kmss:message key="FlowChartObject.Lang.Node.operationScope.org" bundle="sys-lbpmservice" />
									</label>
									&nbsp;&nbsp;
									<label>
										<input type="radio" name="value(handlerAssignDefaultScope)" value="dept">
										<kmss:message key="FlowChartObject.Lang.Node.operationScope.dept" bundle="sys-lbpmservice" />
									</label>
									&nbsp;&nbsp;
									<label>
										<input type="radio" name="value(handlerAssignDefaultScope)"  value="custom">
										<kmss:message key="FlowChartObject.Lang.Node.operationScope.custom" bundle="sys-lbpmservice" />
									</label>
								</c:if>
								<c:if test="${sysAppConfigForm.map.handlerAssignDefaultScope != null}">
									<xform:radio property="value(handlerAssignDefaultScope)" showStatus="edit">
										<xform:simpleDataSource value="all"><kmss:message key="FlowChartObject.Lang.Node.operationScope.all" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
										<xform:simpleDataSource value="org"><kmss:message key="FlowChartObject.Lang.Node.operationScope.org" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
										<xform:simpleDataSource value="dept"><kmss:message key="FlowChartObject.Lang.Node.operationScope.dept" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
										<xform:simpleDataSource value="custom"><kmss:message key="FlowChartObject.Lang.Node.operationScope.custom" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
									</xform:radio>
								</c:if>
							</span>
							</td>
						</tr>
						<!-- 流程引擎默认值设置新增“审批意见中上传附件”参数，默认选中  -->
						<tr>
							<td class="td_normal_title" width=20%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isCanAddAuditNoteAtt" />
							</td>
							<td width="80%">
								<c:if test="${sysAppConfigForm.map.isCanAddAuditNoteAtt != null}">
										<xform:checkbox property="value(isCanAddAuditNoteAtt)" showStatus="edit">
											<xform:simpleDataSource value="true">
												<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isCanAddAuditNoteAtt" />
											</xform:simpleDataSource>
										</xform:checkbox>
								</c:if>
								<c:if test="${sysAppConfigForm.map.isCanAddAuditNoteAtt == null}">
									<xform:checkbox property="value(isCanAddAuditNoteAtt)" showStatus="edit" value="true">
										<xform:simpleDataSource value="true">
											<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isCanAddAuditNoteAtt" />
										</xform:simpleDataSource>
									</xform:checkbox>
								</c:if>
								<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isCanAddAuditNoteAtt.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isCanAddAuditNoteAtt');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
						
						<tr>
							<td class="td_normal_title" rowspan="6" width="20%">
								<kmss:message key="FlowChartObject.Lang.Node.Overtime.Notify" bundle="sys-lbpmservice" />
							</td>
							<td width="80%">
								<kmss:message key="FlowChartObject.Lang.Node.dayOfNotify1" bundle="sys-lbpmservice" />
								<c:if test="${sysAppConfigForm.map.dayOfNotify != null}">
									<xform:text property="value(dayOfNotify)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.dayOfNotify == null}">
									<xform:text property="value(dayOfNotify)" className="inputsgl" value="0" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
									<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
								<c:if test="${sysAppConfigForm.map.hourOfNotify != null}">
									<xform:text property="value(hourOfNotify)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.hourOfNotify == null}">
									<xform:text property="value(hourOfNotify)" className="inputsgl" value="0" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
									<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
								<c:if test="${sysAppConfigForm.map.minuteOfNotify != null}">
									<xform:text property="value(minuteOfNotify)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.minuteOfNotify == null}">
									<xform:text property="value(minuteOfNotify)" className="inputsgl" value="0" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
									<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.tranNotifyHandler"/><br/>
								<c:if test="${sysAppConfigForm.map.repeatDayOfNotify != null}">
									<xform:checkbox property="value(repeatDayOfNotify)" showStatus="edit" validators="checkRepeat checkRepeatInterval" onValueChange="showRepeatConfig(this.checked)">
										<xform:simpleDataSource value="true">
											<kmss:message key="FlowChartObject.Lang.Node.repeat" bundle="sys-lbpmservice" />
										</xform:simpleDataSource>
									</xform:checkbox>
								</c:if>
								<c:if test="${sysAppConfigForm.map.repeatDayOfNotify == null}">
									<xform:checkbox property="value(repeatDayOfNotify)" showStatus="edit" validators="checkRepeat checkRepeatInterval" value="false" onValueChange="showRepeatConfig(this.checked)">
										<xform:simpleDataSource value="true">
											<kmss:message key="FlowChartObject.Lang.Node.repeat" bundle="sys-lbpmservice" />
										</xform:simpleDataSource>
									</xform:checkbox>
								</c:if>
								<c:if test="${sysAppConfigForm.map.repeatDayOfNotify == 'true'}">
									<span id="repeatConfigDiv">
								</c:if>
								<c:if test="${sysAppConfigForm.map.repeatDayOfNotify == 'false' || sysAppConfigForm.map.repeatDayOfNotify == null}">
									<span id="repeatConfigDiv" style="display:none">
								</c:if>
									<c:if test="${sysAppConfigForm.map.repeatTimesDayOfNotify != null}">
										<xform:text property="value(repeatTimesDayOfNotify)" className="inputsgl" style="text-align:center" validators="checkRepeatTimes" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
									</c:if>
									<c:if test="${sysAppConfigForm.map.repeatTimesDayOfNotify == null}">
										<xform:text property="value(repeatTimesDayOfNotify)" className="inputsgl" value="1" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
									</c:if>
									<kmss:message key="FlowChartObject.Lang.Node.times" bundle="sys-lbpmservice" />&nbsp;&nbsp;
									<kmss:message key="FlowChartObject.Lang.Node.interval" bundle="sys-lbpmservice" />
									<c:if test="${sysAppConfigForm.map.intervalDayOfNotify != null}">
										<xform:text property="value(intervalDayOfNotify)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
									</c:if>
									<c:if test="${sysAppConfigForm.map.intervalDayOfNotify == null}">
										<xform:text property="value(intervalDayOfNotify)" className="inputsgl" value="0" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
									</c:if>
										<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
									<c:if test="${sysAppConfigForm.map.intervalHourOfNotify != null}">
										<xform:text property="value(intervalHourOfNotify)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
									</c:if>
									<c:if test="${sysAppConfigForm.map.intervalHourOfNotify == null}">
										<xform:text property="value(intervalHourOfNotify)" className="inputsgl" value="0" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
									</c:if>
										<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
									<c:if test="${sysAppConfigForm.map.intervalMinuteOfNotify != null}">
										<xform:text property="value(intervalMinuteOfNotify)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
									</c:if>
									<c:if test="${sysAppConfigForm.map.intervalMinuteOfNotify == null}">
										<xform:text property="value(intervalMinuteOfNotify)" className="inputsgl" value="0" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
									</c:if>
										<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
								</span>
							</td>
						</tr>
						<tr>
							<td width="80%">	
								<kmss:message key="FlowChartObject.Lang.Node.dayOfNotify1" bundle="sys-lbpmservice" />
							  	<c:if test="${sysAppConfigForm.map.tranNotifyDraft != null}">
									<xform:text property="value(tranNotifyDraft)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.tranNotifyDraft == null}">
									<xform:text property="value(tranNotifyDraft)" className="inputsgl" value="0" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
							  		<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
							  	<c:if test="${sysAppConfigForm.map.hourOfTranNotifyDraft != null}">
									<xform:text property="value(hourOfTranNotifyDraft)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.hourOfTranNotifyDraft == null}">
									<xform:text property="value(hourOfTranNotifyDraft)" className="inputsgl" value="0" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
							  		<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
							  	<c:if test="${sysAppConfigForm.map.minuteOfTranNotifyDraft != null}">
									<xform:text property="value(minuteOfTranNotifyDraft)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.minuteOfTranNotifyDraft == null}">
									<xform:text property="value(minuteOfTranNotifyDraft)" className="inputsgl" value="0" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
							  		<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
							  	<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.tranNotifyDraft"/><br>
							</td>
						</tr>
						<tr>
							<td width="80%">		
								<kmss:message key="FlowChartObject.Lang.Node.dayOfNotify1" bundle="sys-lbpmservice" />
								<c:if test="${sysAppConfigForm.map.tranNotifyPrivate != null}">
									<xform:text property="value(tranNotifyPrivate)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.tranNotifyPrivate == null}">
									<xform:text property="value(tranNotifyPrivate)" className="inputsgl" value="0" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
									<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
								<c:if test="${sysAppConfigForm.map.hourOfTranNotifyPrivate != null}">
									<xform:text property="value(hourOfTranNotifyPrivate)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.hourOfTranNotifyPrivate == null}">
									<xform:text property="value(hourOfTranNotifyPrivate)" className="inputsgl" value="0" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
									<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
								<c:if test="${sysAppConfigForm.map.minuteOfTranNotifyPrivate != null}">
									<xform:text property="value(minuteOfTranNotifyPrivate)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.minuteOfTranNotifyPrivate == null}">
									<xform:text property="value(minuteOfTranNotifyPrivate)" className="inputsgl" value="0" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
									<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.tranNotifyPrivileger"/><br>
							</td>
						</tr>
						<tr>
							<td width="80%">		
								<kmss:message key="lbpmSetting.rejectNotifyDraft" bundle="sys-lbpmservice-support" />
								<c:if test="${sysAppConfigForm.map.rejectNotifyDraft != null}">
									<xform:text property="value(rejectNotifyDraft)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.rejectNotifyDraft == null}">
									<xform:text property="value(rejectNotifyDraft)" className="inputsgl" value="0" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
									<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
								<c:if test="${sysAppConfigForm.map.hourOfRejectNotifyDraft != null}">
									<xform:text property="value(hourOfRejectNotifyDraft)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.hourOfRejectNotifyDraft == null}">
									<xform:text property="value(hourOfRejectNotifyDraft)" className="inputsgl" value="0" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
									<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
								<c:if test="${sysAppConfigForm.map.minuteOfRejectNotifyDraft != null}">
									<xform:text property="value(minuteOfRejectNotifyDraft)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.minuteOfRejectNotifyDraft == null}">
									<xform:text property="value(minuteOfRejectNotifyDraft)" className="inputsgl" value="0" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
									<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.tranNotifyDraft"/><br>
							</td>
						</tr>
						<tr>
							<td width="80%">		
								<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.dayOfNotifyPrivileger1"/>
								<c:if test="${sysAppConfigForm.map.dayOfNotifyDrafter != null}">
									<xform:text property="value(dayOfNotifyDrafter)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.dayOfNotifyDrafter == null}">
									<xform:text property="value(dayOfNotifyDrafter)" className="inputsgl" style="text-align:center" value="0" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
									<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
								<c:if test="${sysAppConfigForm.map.hourOfNotifyDrafter != null}">
									<xform:text property="value(hourOfNotifyDrafter)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.hourOfNotifyDrafter == null}">
									<xform:text property="value(hourOfNotifyDrafter)" className="inputsgl" style="text-align:center" value="0" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
									<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
								<c:if test="${sysAppConfigForm.map.minuteOfNotifyDrafter != null}">
									<xform:text property="value(minuteOfNotifyDrafter)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.minuteOfNotifyDrafter == null}">
									<xform:text property="value(minuteOfNotifyDrafter)" className="inputsgl" style="text-align:center" value="0" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
									<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.tranNotifyDraft"/>
							</td>
						</tr>
						<tr>
							<td width="80%">		
								<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.dayOfNotifyPrivileger1"/>
								<c:if test="${sysAppConfigForm.map.dayOfNotifyPrivileger != null}">
									<xform:text property="value(dayOfNotifyPrivileger)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.dayOfNotifyPrivileger == null}">
									<xform:text property="value(dayOfNotifyPrivileger)" className="inputsgl" style="text-align:center" value="15" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
									<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
								<c:if test="${sysAppConfigForm.map.hourOfNotifyPrivileger != null}">
									<xform:text property="value(hourOfNotifyPrivileger)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.hourOfNotifyPrivileger == null}">
									<xform:text property="value(hourOfNotifyPrivileger)" className="inputsgl" style="text-align:center" value="0" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
									<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
								<c:if test="${sysAppConfigForm.map.minuteOfNotifyPrivileger != null}">
									<xform:text property="value(minuteOfNotifyPrivileger)" className="inputsgl" style="text-align:center" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.minuteOfNotifyPrivileger == null}">
									<xform:text property="value(minuteOfNotifyPrivileger)" className="inputsgl" style="text-align:center" value="0" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
								</c:if>
									<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.tranNotifyPrivileger"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" rowspan="2" width="20%">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.notifyTypeOnFinish" />
							</td>
							<td width="80%">
								<c:if test="${sysAppConfigForm.map.notifyOnFinish != null}">
									<xform:checkbox property="value(notifyOnFinish)" showStatus="edit">
										<xform:simpleDataSource value="true">
											<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.notifyOnFinish" />
										</xform:simpleDataSource>
									</xform:checkbox>
								</c:if>
								<c:if test="${sysAppConfigForm.map.notifyOnFinish == null}">
									<xform:checkbox property="value(notifyOnFinish)" showStatus="edit" value="true">
										<xform:simpleDataSource value="true">
											<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.notifyOnFinish" />
										</xform:simpleDataSource>
									</xform:checkbox>
								</c:if>
								<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.notifyOnFinish.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('notifyOnFinish');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
						<tr>
							<td width="80%">	
								<c:if test="${sysAppConfigForm.map.notifyDraftOnFinish != null}">
									<xform:checkbox property="value(notifyDraftOnFinish)" showStatus="edit">
										<xform:simpleDataSource value="true">
											<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.notifyDraftOnFinish" />
										</xform:simpleDataSource>
									</xform:checkbox>
								</c:if>
								<c:if test="${sysAppConfigForm.map.notifyDraftOnFinish == null}">
									<xform:checkbox property="value(notifyDraftOnFinish)" showStatus="edit" value="true">
										<xform:simpleDataSource value="true">
											<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.notifyDraftOnFinish" />
										</xform:simpleDataSource>
									</xform:checkbox>
								</c:if>
								<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.notifyDraftOnFinish.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('notifyDraftOnFinish');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
					</table>
				<%-- </td>
			</tr>
			<tr LKS_LabelName="<bean:message bundle='sys-lbpmservice-support' key='lbpmSetting.processRun.defaultValues'/>">
				<td> --%>
				</ui:content>
				<ui:content title="${lfn:message('sys-lbpmservice-support:lbpmSetting.processRun.defaultValues')}" >
					<table class="tb_normal" width=100%>
						<!-- 审批操作相关设置项 -->
						<tr>
							<td class="td_normal_title" width="20%">
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.operations"/>
							</td>
							<td width="80%">
								<table class="tb_normal" width="100%">
									<tr>
										<td>
											<c:if test="${sysAppConfigForm.map.isRefuseToPrevNodeDefault != null}">
												<xform:checkbox property="value(isRefuseToPrevNodeDefault)" showStatus="edit">
													<xform:simpleDataSource value="true">
														<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isRefuseToPrevNodeDefault" />
													</xform:simpleDataSource>
												</xform:checkbox>
											</c:if>
											<c:if test="${sysAppConfigForm.map.isRefuseToPrevNodeDefault == null}">
												<xform:checkbox property="value(isRefuseToPrevNodeDefault)" showStatus="edit" value="false">
													<xform:simpleDataSource value="true">
														<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isRefuseToPrevNodeDefault" />
													</xform:simpleDataSource>
												</xform:checkbox>
											</c:if>
											<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isRefuseToPrevNodeDefault.desc"/>
											<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isRefuseToPrevNodeDefault');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
										</td>
									</tr>
									
									<tr>
										<td>
											<c:if test="${sysAppConfigForm.map.isMultiCommunicateDefault != null}">
												<xform:checkbox property="value(isMultiCommunicateDefault)" showStatus="edit">
													<xform:simpleDataSource value="true">
														<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isMultiCommunicateDefault" />
													</xform:simpleDataSource>
												</xform:checkbox>
											</c:if>
											<c:if test="${sysAppConfigForm.map.isMultiCommunicateDefault == null}">
												<xform:checkbox property="value(isMultiCommunicateDefault)" showStatus="edit" value="true">
													<xform:simpleDataSource value="true">
														<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isMultiCommunicateDefault" />
													</xform:simpleDataSource>
												</xform:checkbox>
											</c:if>
											<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isMultiCommunicateDefault.desc"/>
											<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isMultiCommunicateDefault');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
										</td>
									</tr>
									
									
									<tr>
										<td>
											<c:if test="${sysAppConfigForm.map.isMultiAssignDefault != null}">
												<xform:checkbox property="value(isMultiAssignDefault)" showStatus="edit">
													<xform:simpleDataSource value="true">
														<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isMultiAssignDefault" />
													</xform:simpleDataSource>
												</xform:checkbox>
											</c:if>
											<c:if test="${sysAppConfigForm.map.isMultiAssignDefault == null}">
												<xform:checkbox property="value(isMultiAssignDefault)" showStatus="edit" value="true">
													<xform:simpleDataSource value="true">
														<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isMultiAssignDefault" />
													</xform:simpleDataSource>
												</xform:checkbox>
											</c:if>
											<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isMultiAssignDefault.desc"/>
											<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isMultiAssignDefault');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
										</td>
									</tr>
									
									
								</table>
							</td>
						</tr>
						<!-- 加签/人员通过后自动跳过限定默认勾选的用户范围-->
						<tr>
							<td class="td_normal_title" width=20%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.assignPassSkip"/>
							</td>
							<td width="80%">
								<xform:address textarea="true" propertyId="value(assignPassSkipIds)" propertyName="value(assignPassSkipNames)" mulSelect="true" orgType="ORG_TYPE_PERSON | ORG_TYPE_POST | ORG_TYPE_DEPT" style="width:85%">
								</xform:address>
								<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.assignPassSkip.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('assignPassSkip',true);"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
						<!-- 已处理人撤回设置 -->
						<tr>
							<td class="td_normal_title" width=20%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.handerBackSetting"/>
							</td>
							<td width=80% colspan="2">
								<c:if test="${sysAppConfigForm.map.handerBackSetting != null}">
									<xform:radio property="value(handerBackSetting)" showStatus="edit">
										<xform:simpleDataSource value="0"><kmss:message key="lbpmSetting.handerBackSetting_Item1" bundle="sys-lbpmservice-support" /></xform:simpleDataSource>
										<xform:simpleDataSource value="1"><kmss:message key="lbpmSetting.handerBackSetting_Item2" bundle="sys-lbpmservice-support" /></xform:simpleDataSource>
									</xform:radio>
								</c:if>
								<c:if test="${sysAppConfigForm.map.handerBackSetting == null}">
									<label><input name="value(handerBackSetting)" value="0" type="radio"><kmss:message key="lbpmSetting.handerBackSetting_Item1" bundle="sys-lbpmservice-support" /></label>&nbsp;
									<label><input name="value(handerBackSetting)" value="1" type="radio" checked><kmss:message key="lbpmSetting.handerBackSetting_Item2" bundle="sys-lbpmservice-support" /></label>&nbsp;
								</c:if>
								<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.handerBackSetting.desc"/>
							</td>
						</tr>
						
						<!-- 节点默认权限（自由流） -->
						<tr>
							<td class="td_normal_title" width=20%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.defaultFlowPopedom"/>
							</td>
							<td width=80% colspan="2">
								<c:if test="${sysAppConfigForm.map.defaultFlowPopedom != null}">
									<xform:radio property="value(defaultFlowPopedom)" showStatus="edit">
										<xform:simpleDataSource value="0"><kmss:message key="FlowChartObject.Lang.Node.flowPopedom_0" bundle="sys-lbpmservice" /></xform:simpleDataSource>
										<xform:simpleDataSource value="1"><kmss:message key="FlowChartObject.Lang.Node.flowPopedom_1" bundle="sys-lbpmservice" /></xform:simpleDataSource>
										<xform:simpleDataSource value="2"><kmss:message key="FlowChartObject.Lang.Node.flowPopedom_2" bundle="sys-lbpmservice" /></xform:simpleDataSource>
									</xform:radio>
								</c:if>
								<c:if test="${sysAppConfigForm.map.defaultFlowPopedom == null}">
									<label><input name="value(defaultFlowPopedom)" value="0" type="radio"><kmss:message key="FlowChartObject.Lang.Node.flowPopedom_0" bundle="sys-lbpmservice" /></label>&nbsp;
									<label><input name="value(defaultFlowPopedom)" value="1" type="radio" checked><kmss:message key="FlowChartObject.Lang.Node.flowPopedom_1" bundle="sys-lbpmservice" /></label>&nbsp;
									<label><input name="value(defaultFlowPopedom)" value="2" type="radio"><kmss:message key="FlowChartObject.Lang.Node.flowPopedom_2" bundle="sys-lbpmservice" /></label>&nbsp;
								</c:if>
								<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.defaultFlowPopedom.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showRadioItemDetail('defaultFlowPopedom');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
						
						<!-- #63881 编辑主文档（自由流） 2019年7月9日10:59:04 start -->
			            <tr>
			              <td class="td_normal_title" width=20%>
			                <bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isEditMainDocument"/>
			              </td>
			              <td width="80%">
			                <c:if test="${sysAppConfigForm.map.isEditMainDocument != null}">
			                  <xform:checkbox property="value(isEditMainDocument)" showStatus="edit">
			                    <xform:simpleDataSource value="true">
			                      <bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isEditMainDocument.item" />
			                    </xform:simpleDataSource>
			                  </xform:checkbox>
			                </c:if>
			                <c:if test="${sysAppConfigForm.map.isEditMainDocument == null}">
			                  <xform:checkbox property="value(isEditMainDocument)" showStatus="edit">
			                    <xform:simpleDataSource value="true">
			                      <bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isEditMainDocument.item" />
			                    </xform:simpleDataSource>
			                  </xform:checkbox>
			                </c:if>
			                <br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.isEditMainDocument.desc"/>
			                <br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('isEditMainDocument');"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
			              </td>
			            </tr>
			            <!-- #63881 2019年7月9日10:59:04 end -->
						
						<!-- 快速审批入口权限 -->
						<tr>
							<td class="td_normal_title" width=20%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.fastTodo" />
							</td>
							<td width="80%">
								<xform:address textarea="true" propertyId="value(fastTodoIds)" propertyName="value(fastTodoNames)" mulSelect="true" orgType="ORG_TYPE_PERSON | ORG_TYPE_POST | ORG_TYPE_DEPT" style="width:85%">
								</xform:address>
								<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.fastTodo.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('fastTodo',true);"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
						<!-- 汇总审批入口限定用户范围-->
						<tr>
							<td class="td_normal_title" width=20%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.summaryApproval"/>
							</td>
							<td width="80%">
								<xform:address textarea="true" propertyId="value(summaryApprovalIds)" propertyName="value(summaryApprovalNames)" mulSelect="true" orgType="ORG_TYPE_PERSON | ORG_TYPE_POST | ORG_TYPE_DEPT" style="width:85%">
								</xform:address>
								<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.summaryApproval.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('summaryApproval',true);"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
						<!-- 投票特权人 -->
						<tr>
							<td class="td_normal_title" width=20%>
								<bean:message bundle="sys-lbpmservice-node-votenode" key="lbpmSetting.voteAdmin"/>
							</td>
							<td width="80%">
								<xform:address textarea="true" propertyId="value(voteAdminIds)" propertyName="value(voteAdminNames)" mulSelect="true" orgType="ORG_TYPE_PERSON | ORG_TYPE_POST" style="width:85%">
								</xform:address>
								<br/><bean:message bundle="sys-lbpmservice-node-votenode" key="lbpmSetting.voteAdmin.desc"/>
							</td>
						</tr>
						<!-- #60550 个人流程中心模块范围设置 -->
						<tr>
							<td class="td_normal_title" width=20%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.modularSelect"/>
							</td>
							<td width="80%">
								<xform:dialog style="width:85%;" propertyName="value(moduleScopeNames)" propertyId="value(moduleScopeIds)" dialogJs="selectShowModules()"></xform:dialog>
								<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.modularSelect.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('modularSelect',true);"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
						<!-- #164460 个人流程中心模块搜索层级定义 -->
						<tr>
							<td class="td_normal_title" width=20%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.search.level"/>
							</td>
							<td width="80%">
								<c:if test="${sysAppConfigForm.map.searchLevel == null || sysAppConfigForm.map.searchLevel == ''}">
									<xform:text property="value(searchLevel)" value="4" className="inputsgl"/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.search.levels"/>
									<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.search.level.desc"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.searchLevel != null && sysAppConfigForm.map.searchLevel != ''}">
									<xform:text property="value(searchLevel)" className="inputsgl"/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.search.levels"/>
									<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.search.level.desc"/>
								</c:if>
							</td>
						</tr>
						<!-- 流程授权模块范围设置 -->
						<tr>
							<td class="td_normal_title" width=20%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.lbpmAuthorize.modularSelect"/>
							</td>
							<td width="80%">
								<xform:dialog style="width:85%;" propertyName="value(lbpmAuthorizeModuleScopeNames)" propertyId="value(lbpmAuthorizeModuleScopeIds)" dialogJs="selectProcessAuthModules()"></xform:dialog>
								<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.lbpmAuthorize.modularSelect.desc"/>
								<br/><a class="com_btn_link" href="javascript:void(0)" onclick="showItemDetail('authorizeModule',true);"><bean:message bundle="sys-lbpmservice-support" key="lbpmSetting.showItemDetail"/></a>
							</td>
						</tr>
						<!-- 审批模式 -->
						<tr>
							<td class="td_normal_title" width=20% ><kmss:message key="lbpmSetting.approve.model" bundle="sys-lbpmservice" /></td>
							<td>
							<span>
								<c:if test="${sysAppConfigForm.map.approveModel == null}">
									<label>
										<input type="radio" name="value(approveModel)" value="defalut" checked = "checked">
										<kmss:message key="lbpmSetting.approve.model.default" bundle="sys-lbpmservice" />
									</label>
									&nbsp;&nbsp;
									<label>
										<input type="radio" name="value(approveModel)" value="dialog">
										<kmss:message key="lbpmSetting.approve.model.dialog" bundle="sys-lbpmservice" />
									</label>
									&nbsp;&nbsp;
									<label>
										<input type="radio" name="value(approveModel)" value="tiled">
										<kmss:message key="lbpmSetting.approve.model.tiled" bundle="sys-lbpmservice" />
									</label>
									&nbsp;&nbsp;
									<label>
										<input type="radio" name="value(approveModel)" value="right">
										<kmss:message key="lbpmSetting.approve.model.right" bundle="sys-lbpmservice" />
									</label>
								</c:if>
								<c:if test="${sysAppConfigForm.map.approveModel != null}">
									<xform:radio property="value(approveModel)" showStatus="edit">
										<xform:simpleDataSource value="defalut"><kmss:message key="lbpmSetting.approve.model.default" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
										<xform:simpleDataSource value="dialog"><kmss:message key="lbpmSetting.approve.model.dialog" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
										<xform:simpleDataSource value="tiled"><kmss:message key="lbpmSetting.approve.model.tiled" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
										<xform:simpleDataSource value="right"><kmss:message key="lbpmSetting.approve.model.right" bundle="sys-lbpmservice" />&nbsp;&nbsp;</xform:simpleDataSource>
									</xform:radio>
								</c:if>
								<br/><bean:message bundle="sys-lbpmservice" key="lbpmSetting.approve.model.desc"/>
							</span>
							</td>
						</tr>
						<!-- 催办冷却时间 -->
						<tr>
							<td class="td_normal_title" width=20%>
								<bean:message bundle="sys-lbpmservice" key="lbpmSetting.approve.ReminderCooldown"/>
							</td>
							<td width="80%">
								<xform:text property="value(reminderCooldownTime)" className="inputsgl"/><bean:message bundle="sys-lbpmservice" key="lbpmSetting.approve.ReminderCooldown.hours"/>
								<br/><bean:message bundle="sys-lbpmservice" key="lbpmSetting.approve.ReminderCooldown.desc"/>
							</td>
						</tr>
						<!-- 自由流编辑冻结时间 -->
						<tr>
							<td class="td_normal_title" width=20%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmFreeflowVersion.freezeFreeFlowTime"/>
							</td>
							<td width="80%">
								<c:if test="${sysAppConfigForm.map.isFreezeFreeFlow != null}">
									<xform:checkbox property="value(isFreezeFreeFlow)" showStatus="edit" onValueChange="isFreezeFreeFlowChange(this.checked)">
										<xform:simpleDataSource value="true">
											<bean:message bundle="sys-lbpmservice-support" key="lbpmFreeflowVersion.isFreezeFreeFlow"/>
										</xform:simpleDataSource>
									</xform:checkbox>
								</c:if>
								<c:if test="${sysAppConfigForm.map.isFreezeFreeFlow == null}">
									<xform:checkbox property="value(isFreezeFreeFlow)" showStatus="edit" value="true" onValueChange="isFreezeFreeFlowChange(this.checked)">
										<xform:simpleDataSource value="true">
											<bean:message bundle="sys-lbpmservice-support" key="lbpmFreeflowVersion.isFreezeFreeFlow"/>
										</xform:simpleDataSource>
									</xform:checkbox>
								</c:if>
								<c:if test="${sysAppConfigForm.map.freezeFreeFlowTime != null}">
									<xform:text property="value(freezeFreeFlowTime)" className="inputsgl" style="width:50px;" htmlElementProperties="size='3' onkeyup='controlNumber(this,10)' onafterpaste='controlNumber(this,10)'"/>
								</c:if>
								<c:if test="${sysAppConfigForm.map.freezeFreeFlowTime == null}">
									<xform:text property="value(freezeFreeFlowTime)" className="inputsgl" style="width:50px;" value="10" />
								</c:if>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmFreeflowVersion.isFreezeFreeFlow.minutes"/>
								<br/><bean:message bundle="sys-lbpmservice-support" key="lbpmFreeflowVersion.freezeFreeFlowTime.desc"/>
							</td>
						</tr>
						<!--#154142子任务结束（如沟通）时检查更新待办状态任务执行时间间隔-->
						<tr>
							<td class="td_normal_title" width=20%>
								<bean:message bundle="sys-lbpmservice" key="lbpmSetting.checkTodoTimeInterval.title"/>
							</td>
							<td width="80%">
								<xform:text  property="value(checkTodoTimeInterval)" className="inputsgl" validators=" digits min(60)"/><bean:message bundle="sys-lbpmservice" key="lbpmSetting.checkTodoTimeInterval.second"/>
								<br/><bean:message bundle="sys-lbpmservice" key="lbpmSetting.checkTodoTimeInterval.desc"/>
							</td>
						</tr>
					</table>
				</ui:content>
			</ui:tabpanel>
				<!-- </td>
			</tr>
		</table> -->
		</div>
		</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.lbpmservice.support.model.LbpmSettingDefault" />
			
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" style="position: fixed;bottom:0px;left: 15px;width:95%;background: #fff;" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');" order="1" ></ui:button>
			</center>
		</html:form>
		<script type="text/javascript">
		Com_IncludeFile("doclist.js");
	 		var defauleValidation = $KMSSValidation();
	 		defauleValidation.addValidator('checkRepeat',
 				"<bean:message bundle='sys-lbpmservice' key='FlowChartObject.Lang.Node.checkDayOfNotifyForRepeat'/>",
 				function(v,e,o){
	 				if(v == 'true'){
	 					var day = $("input[name='value(dayOfNotify)']").val();
	 					var hour = $("input[name='value(hourOfNotify)']").val();
	 					var minute = $("input[name='value(minuteOfNotify)']").val();
	 					if(day=="0" && hour=="0" && minute=="0"){
	 						return false;
	 					}
	 				}
	 				return true;
	 			}
 			);
	 		
	 		defauleValidation.addValidator('checkRepeatTimes',
 				"<bean:message bundle='sys-lbpmservice' key='FlowChartObject.Lang.Node.checkRepeatTimes'/>",
 				function(v,e,o){
 					if($("input[name='_value(repeatDayOfNotify)']").is(':checked')){
 						try{
 							if(parseInt(v)<1){
 	 							return false;
 	 						}
 						}catch (ex) {
 							return false;
 						}
 					}
	 				return true;
	 			}
 			);
	 		
	 		defauleValidation.addValidator('checkRepeatInterval',
 				"<bean:message bundle='sys-lbpmservice' key='FlowChartObject.Lang.Node.checkRepeatInterval'/>",
 				function(v,e,o){
	 				if(v == 'true'){
	 					var day = $("input[name='value(intervalDayOfNotify)']").val();
	 					var hour = $("input[name='value(intervalHourOfNotify)']").val();
	 					var minute = $("input[name='value(intervalMinuteOfNotify)']").val();
	 					if(day=="0" && hour=="0" && minute=="0"){
	 						return false;
	 					}
	 				}
	 				return true;
	 			}
 			);
	 		
	 		var userLang = "<%=MultiLangTextGroupTag.getUserLangKey()%>";
	 		
	 		window.showItemDetail=function(key,bool){
	 			var url = '/sys/lbpmservice/support/lbpm_setting/lbpmSetting_diagram.jsp?key=' + key;
	 			var resourceKey = 'sys-lbpmservice-support:lbpmSetting.' + key;
	 			var title  = Data_GetResourceString(resourceKey);
	 			if(!bool){
	 				var checked = "false";
		 			checked = $("input[name*='"+ key +"']")[0].checked;
		 			if (checked==true) {
		 				title += "(" + "${lfn:message('sys-ui:ui.switch.enabled')}" + ")";
		 				url += "1";
		 			} else {
		 				title += "(" + "${lfn:message('sys-ui:ui.switch.disabled')}" + ")";
		 				url += "0";
		 			}
	 			}
	 			if(userLang=="en-US"){
	 				url +="_"+userLang;
	 			}
	 			seajs.use([ 'lui/dialog'], function(dialog) {
					dialog.iframe(url,
							title,
							function(value) {
							}, {
								"width" : 616,
								"height" : 480,
								topWin : window
							});
				});
	 		};
	 		window.showRadioItemDetail=function(key,bool){
	 			var url = '/sys/lbpmservice/support/lbpm_setting/lbpmSetting_diagram.jsp?key=' + key;
	 			var resourceKey = 'sys-lbpmservice-support:lbpmSetting.' + key;
	 			var title  = Data_GetResourceString(resourceKey);
	 			var $checkedField = $("input[name*='" + key + "']:checked");
	 			if($checkedField.size() == 1){
	 				title += "(" + $checkedField.closest("label").text() + ")";
	 				url += $checkedField.val();
	 			}
	 			if(userLang=="en-US"){
	 				url +="_"+userLang;
	 			}
	 			seajs.use([ 'lui/dialog'], function(dialog) {
					dialog.iframe(url,
							title,
							function(value) {
							}, {
								"width" : 616,
								"height" : 480,
								topWin : window
							});
				});
	 		};
	 		function writeFormField(){
	 			var form = document.sysAppConfigForm;
	 			for(var i=0;i<form.length;i++){
	 				if(form[i].type == "checkbox"){
	 					var name = form[i].name;
	 					if (name){
	 						var name = name.substring(1);
	 						var dom = $("[name='"+name+"']");
	 						if (dom.val() == null || dom.val() === ""){
	 							dom.val("false");
	 						}
	 					}
	 				}
	 			}
	 			return true;
	 		}
	 		Com_Parameter.event["submit"].push(writeFormField);
	 		
	 		function showRepeatConfig(checked){
	 			if (checked == true) {
	 				$('#repeatConfigDiv').show();
	 			} else {
	 				$('#repeatConfigDiv').hide();
	 				document.getElementsByName("value(repeatTimesDayOfNotify)")[0].value="1";
	 				document.getElementsByName("value(intervalDayOfNotify)")[0].value="0";
	 				document.getElementsByName("value(intervalHourOfNotify)")[0].value="0";
	 				document.getElementsByName("value(intervalMinuteOfNotify)")[0].value="0";
	 			}
	 		}
	 		
	 		function controlNumber(obj){
	 			obj.value=(parseInt((obj.value=obj.value.replace(/\D/g,''))==''||parseInt((obj.value=obj.value.replace(/\D/g,''))==0)?'0':obj.value,10));
	 		}
	 		//#60550 个人流程中心模块范围设置-选择点击函数
	 		window.selectShowModules = function(){
	 			Dialog_Tree(true, 'value(moduleScopeIds)','value(moduleScopeNames)', ';','sysLbpmSelectModuleInfoService&fdValue=!{value}&filter=true', "<bean:message bundle='sys-lbpmservice-support' key='lbpmSetting.modularSelect.scope'/>");
	 		}
	 		
	 		//选择流程授权模块范围
	 		window.selectProcessAuthModules = function(){
	 			Dialog_Tree(true, 'value(lbpmAuthorizeModuleScopeIds)','value(lbpmAuthorizeModuleScopeNames)', ';','sysLbpmSelectModuleInfoService&fdValue=!{value}', "<bean:message bundle='sys-lbpmservice-support' key='lbpmSetting.lbpmAuthorize.modularSelect.scope'/>");
		 	}

		 	function isFreezeFreeFlowChange(checked){
	 			if(checked){
					$("input[name='value(freezeFreeFlowTime)']").prop("readOnly",false);
				}else{
					$("input[name='value(freezeFreeFlowTime)']").prop("readOnly","false");
				}
			}

	 		$(function(){
				$("input[name='value(moduleScopeNames)']").css("color","#4285f4");
				$("input[name='value(lbpmAuthorizeModuleScopeNames)']").css("color","#4285f4");
				isFreezeFreeFlowChange($("input[name='value(isFreezeFreeFlow)']").val()=="true")
			 });
	 	</script>
	</template:replace>
</template:include>
