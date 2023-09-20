<%@page import="com.landray.kmss.sys.lbpmext.businessauth.service.ILbpmExtBusinessSettingInfoService"%>
<%@page import="com.alibaba.fastjson.JSONArray"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.service.ILbpmAuditNoteTypeService"%>
<%@page import="com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainFormAdapter"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessLimitTimeOperationLogService"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting"%>
<%@page import="java.util.*"%>
<%@page import="com.landray.kmss.util.StringUtil"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" />
<c:set var="lbpmProcessForm" value="${sysWfBusinessForm.sysWfBusinessForm.internalForm}" />
<c:if test="${sysWfBusinessForm.sysWfBusinessForm!=null && sysWfBusinessForm.docStatus < '30'}">
	<%@ include file="./script.jsp" %>
	<jsp:include page="/sys/lbpmservice/mobile/lbpm_audit_note/import/view_include.jsp">
		<jsp:param name="processId" value="${sysWfBusinessForm.fdId}" />
		<jsp:param name="docStatus" value="${sysWfBusinessForm.docStatus}" />
	</jsp:include>
	<%
		//限时办理，获取开关
		LbpmSetting lbpmSettingInfo = new LbpmSetting();
		String isShowLimitTimeOperation = lbpmSettingInfo.getIsShowLimitTimeOperation();
		request.setAttribute("showLimitTimeOperation", isShowLimitTimeOperation);

		if("true".equals(isShowLimitTimeOperation)){
			//限时办理，获取剩余时间
			ILbpmProcessLimitTimeOperationLogService operationLogService =
					(ILbpmProcessLimitTimeOperationLogService)SpringBeanUtil.getBean("lbpmProcessLimitTimeOperationLogService");
			String fdCurNodeXML = null;
			String fdProcessId = null;
			if(pageContext.getAttribute("sysWfBusinessForm") instanceof ISysLbpmMainFormAdapter){
				ISysLbpmMainFormAdapter adapter = (ISysLbpmMainFormAdapter)pageContext.getAttribute("sysWfBusinessForm");
				fdCurNodeXML = adapter.getSysWfBusinessForm().getFdCurNodeXML();
				fdProcessId = adapter.getSysWfBusinessForm().getFdProcessId();
			}
			if(StringUtil.isNotNull(fdCurNodeXML) && StringUtil.isNotNull(fdProcessId)){
			Map result = operationLogService.getRemainingTime(fdProcessId, fdCurNodeXML);
			Integer status = Integer.valueOf(result.get("status").toString());

				if(status == 0){
					//表示已经超时
					request.setAttribute("timeoutDay", result.get("day"));
					request.setAttribute("timeoutHour",result.get("hour"));
					request.setAttribute("timeoutMinute",result.get("minute"));
					request.setAttribute("timeoutSecond", result.get("second"));
					request.setAttribute("limitTotalTime", result.get("total"));
					request.setAttribute("isTimeout", "true");
					request.setAttribute("timeoutMinuteshowTimeType","timeout");//表示超时时间
				}else if(status == 1){
					//存在限时并未超过
					request.setAttribute("limitDay", result.get("day"));
					request.setAttribute("limitHour",result.get("hour"));
					request.setAttribute("limitMinute",result.get("minute"));
					request.setAttribute("limitSecond", result.get("second"));
					request.setAttribute("limitTotalTime", result.get("total"));
					request.setAttribute("isTimeout", "false");
					request.setAttribute("showTimeType", "limit");//表示限时时间
				}else if(status == -1){
					//不存在限时，不显示
					request.setAttribute("showLimitTimeOperation", "false");
				}
			}else{
				request.setAttribute("showLimitTimeOperation", "false");
			}
		}
	%>
	<%
		ILbpmExtBusinessSettingInfoService lbpmExtBusinessSettingInfoService = (ILbpmExtBusinessSettingInfoService) SpringBeanUtil
				.getBean("lbpmExtBusinessSettingInfoService");
		String isOpinionTypeEnabled = lbpmExtBusinessSettingInfoService.getIsOpinionTypeEnabled("imissiveLbpmSwitch");
		request.setAttribute("isOpinionTypeEnabled", isOpinionTypeEnabled);
		// 获取意见类型
		if ("true".equals(isOpinionTypeEnabled)) {
			ILbpmAuditNoteTypeService lbpmAuditNoteTypeService = (ILbpmAuditNoteTypeService) SpringBeanUtil
					.getBean("lbpmAuditNoteTypeService");
			JSONArray allAuditNodeTypes = lbpmAuditNoteTypeService.queryAllAuditNodeType("text", "value");
			if (allAuditNodeTypes == null) {
				allAuditNodeTypes = new JSONArray();
			}
			request.setAttribute("allAuditNodeTypes", allAuditNodeTypes);
		}
	%>
	<script>
		var limitTotalTime = '${requestScope.limitTotalTime}';//提供在流程初始化时使用
		var isTimeoutTotal = '${requestScope.isTimeout}';
		<c:if test="${isOpinionTypeEnabled eq 'true'}">
		window.allAuditNodeTypes = JSON.parse('${allAuditNodeTypes}');
		</c:if>
	</script>
	<c:choose>
		<c:when test="${(sysWfBusinessForm.sysWfBusinessForm.fdIsHander == 'true') &&(sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus == null
		|| sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus=='' || fn:contains(sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus,'N'))}">
			<c:set var="lbpmViewName" value="${(empty param.viewName) ? 'lbpmView' : (param.viewName)}"/>
			<div class="lbpmView" name='lbpmView'
					<c:if test="${param.viewName != 'none'}">
						data-dojo-type="sys/lbpmservice/mobile/common/LbpmView" data-dojo-props="showType:'dialog',backTo:'${param.backTo }'" id="${lbpmViewName}"
					</c:if>
				 data-dojo-mixins="sys/lbpmservice/mobile/common/_LbpmValidateMixin">
				<div id='lbpmOperContent'>
					<c:if test="${empty param.onClickSubmitButton}">
					<form name="sysWfProcessForm" method="POST"
						  action="<c:url value="/sys/lbpmservice/support/lbpm_process/lbpmProcess.do" />" autocomplete="off">
						</c:if>
						<%@ include file="./form_hidden.jsp" %>
						<div class="actionTop"></div>
						<c:if test="${(sysWfBusinessForm.docStatus>='20' && sysWfBusinessForm.docStatus<'30') || sysWfBusinessForm.docStatus == '11'}">
							<div class="actionView panel">
								<div id="lbpmOperationTable">
									<table class="muiSimple" cellpadding="0" cellspacing="0">
											<%-- 起草人，当前处理人和特权人能显示 --%>
										<c:if test="${lbpmProcessForm.fdIsDrafter eq 'true' || lbpmProcessForm.fdIsAdmin eq 'true' || lbpmProcessForm.fdIsHander eq 'true' || lbpmProcessForm.fdIsBranchAdmin eq 'true' }">
											<c:if test="${requestScope.showLimitTimeOperation eq 'true' }"><%-- 是否显示：开关或者是否有限时 --%>
												<tr>
													<td class="muiTitle">
														<div id="limitTimeMethodRowTitle">
															<div class="titleNode">
																<c:if test="${requestScope.showTimeType eq 'limit' }">
																	<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.limitTimeRow" />
																</c:if>
																<c:if test="${requestScope.showTimeType eq 'timeout' }">
																	<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.timeoutRow" />
																</c:if>
															</div>
														</div>
													</td>
													<td>
														<div id="lbpmLimitTimeMethodTable" class="limitTimeMethodArea">
															<div id="limitTimeMethodRow">
																<div class="detailNode" style="border:0;text-align: right;">
																	<c:if test="${requestScope.showTimeType eq 'limit' }">
																		<span class="limit_time_span">${requestScope.limitDay }</span> <bean:message bundle="sys-lbpmservice" key="FlowChartObject.Lang.Node.day" /> <span class="limit_time_span">${requestScope.limitHour }</span> : <span class="limit_time_span">${requestScope.limitMinute }</span> : <span class="limit_time_span">${requestScope.limitSecond }</span>
																	</c:if>
																	<c:if test="${requestScope.showTimeType eq 'timeout' }">
																		<span class="limit_time_span">${requestScope.timeoutDay }</span> <bean:message bundle="sys-lbpmservice" key="FlowChartObject.Lang.Node.day" /> <span class="limit_time_span">${requestScope.timeoutHour }</span> : <span class="limit_time_span">${requestScope.timeoutMinute }</span> : <span class="limit_time_span">${requestScope.timeoutSecond }</span>
																	</c:if>
																</div>
															</div>
														</div>
													</td>
													<script>
														$(document).ready(function() {
															$(".limit_time_span").css("border-radius","2px");
															$(".limit_time_span").css("padding","2px 7px");
														});
													</script>
												</tr>
											</c:if>
										</c:if>
											<%-- <c:if test="${(lbpmProcessForm.fdIsDrafter ne 'true' && lbpmProcessForm.fdIsAdmin ne 'true' && lbpmProcessForm.fdIsHander ne 'true') || requestScope.showLimitTimeOperation ne 'true' }">
                                                <tr id="lbpmLimitTimeMethodTable" style="display:none" class="limitTimeMethodArea">
                                                    <td class="muiTitle">
                                                        <div id="limitTimeMethodRowTitle">
                                                            <div class="titleNode">
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div id="limitTimeMethodRow">
                                                            <div class="detailNode" style="border:0;text-align: right;">
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:if> --%>
										<c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdIsHander == 'true'}">
											<tr style="display:none">
												<td id="lbpmOperationMethodTable" class="operationMethodArea">
													<div class="titleNode operation_title">
														<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.handMethods" />
													</div>
													<div id="operationMethodRow">
														<div class="detailNode">
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td colspan="2" id="operationItemsRow">
													<div class="titleNode operation_title">
														<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationItems" />
														<input type="hidden" id="operationMethodsGroup" view-type="input"/>
														<input type="hidden" id="operationItemsSelect" value="0"/>
													</div>
													<div class="detailNode"></div>
												</td>
											</tr>
											<%-- 用于起草节点 ,显示即将流向--%>
											<c:if test="${sysWfBusinessForm.docStatus == null || sysWfBusinessForm.docStatus<'20'}">
												<tr>
													<td colspan="2" style="display: none">
														<div class="titleNode operation_title">
															<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.manualNodeSelect" />
														</div>
														<div id="manualNodeSelectTD">
															&nbsp;
														</div>
													</td>
												</tr>
												<tr>
													<td colspan="2" style="display:none;">
														<div class="titleNode operation_title" id="nextNodeTDTitle">
															<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.nextNode" />
														</div>
														<div  class="detailNode" id="nextNodeTD">
															&nbsp;
														</div>
													</td>
												</tr>
											</c:if>
											<tr>
													<%-- 自由流节点行 --%>
												<td colspan="2" id="freeflowRow" style="display:none;">
													<div class="titleNode operation_title" id="freeflowRowTitle">
														<bean:message bundle="sys-lbpmservice" key="lbpm.tab.graphic" />
													</div>
													<div class="detailNode" id="freeFlowNodeDIV" style="display:none;">
													</div>
												</td>
											</tr>
											<tr>
												<td colspan="2" id="freeSubFlowNodeRow" style="display:none;">
													<div class="titleNode operation_title" id="freeSubFlowRowTitle">
														<bean:message bundle="sys-lbpmservice" key="lbpm.group.freeSubFlow" />
													</div>
													<div class="detailNode" id="freeSubFlowNodeDIV" style="display:none;">
													</div>
												</td>
											</tr>
											<tr>
												<td colspan="2" id="operationsRow" style="display:none;position: unset" lbpmMark="operation">
													<div id="operationsTDTitle" class="titleNode operation_title" lbpmDetail="operation">
														&nbsp;
													</div>
													<div id="operationsTDContent"  class="detailNode" lbpmDetail="operation">
														&nbsp;
													</div>
												</td>
											</tr>

											<tr id='operationsRow_ScopeTr'>
												<td colspan="2" id="operationsRow_Scope" style="display:none;" lbpmMark="operation">
													<div id="operationsTDTitle_Scope" class="titleNode operation_title" lbpmDetail="operation">
														&nbsp;
													</div>
													<div id="operationsTDContent_Scope"  class="detailNode" lbpmDetail="operation">
														&nbsp;
													</div>
												</td>
											</tr>
											<tr>
												<td colspan="2" id="modifyNodeAuthorizationTr" style="display:none">
													<div class="titleNode operation_title">
														<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.changeProcessor" />
													</div>
													<div id="modifyNodeAuthorizationDetail" class="detailNode">

													</div>
													<script type="text/javascript">
														lbpm.globals.includeFile("/sys/lbpmservice/mobile/change_process/sysLbpmProcess_changeProcess.js");
													</script>
												</td>
											</tr>
											<tr>
												<td colspan="2" id="lbpmext_auditpoint_select_content" style="display:none;border-bottom: 1px solid rgba(231,231,231,0.3);position: unset">

												</td>
											</tr>
											<tr>
												<td colspan="2">
														<%-- 审批意见扩展使用 --%>
													<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_ext.jsp" charEncoding="UTF-8">
														<c:param name="auditNoteFdId" value="${sysWfBusinessForm.sysWfBusinessForm.fdAuditNoteFdId}" />
														<c:param name="modelName" value="${sysWfBusinessForm.modelClass.name}" />
														<c:param name="modelId" value="${sysWfBusinessForm.fdId}" />
														<c:param name="formName" value="${param.formName}" />
														<c:param name="lbpmViewName" value="${lbpmViewName}" />
														<c:param name="showType" value="dialog"></c:param>
														<c:param name="backTo" value="${param.backTo }"></c:param>
														<c:param name="provideFor" value="mobile" />
													</c:import>
													<div  id="commonUsagesRow">
														<div class="titleNode operation_title">
															<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdUsageContent" />
														</div>
														<div id="descriptionDiv">
															<div id="fdUsageContent" data-dojo-type='mui/form/TextareaApproval'
																 data-dojo-mixins="sys/lbpmservice/mobile/common/_MaxApprovalMixin,sys/lbpmservice/mobile/usage/_LbpmUsageEventMixin"
												data-dojo-props="validate:'fdUsageContent usageContentMaxLen','subject':'<bean:message bundle="sys-lbpmservice" key="mui.lbpmNode.usage" />','placeholder':'<bean:message bundle="sys-lbpmservice" key="lbpmNode.mustSignYourSuggestion"/>','name':'fdUsageContent',opt:false" alertText="" key="auditNode">
															</div>
															<div class="muiDialogElementMask">

															</div>
															<div class="textarea_dropbox_content" >
																<div id="fdUsageMaxContent" data-dojo-type='mui/form/TextareaMaxApproval'
																	 data-dojo-mixins="sys/lbpmservice/mobile/common/_MaxApprovalMixin"
													data-dojo-props='validate:"fdUsageContent usageContentMaxLen","subject":"<bean:message bundle="sys-lbpmservice" key="mui.lbpmNode.usage" />","placeholder":"<bean:message bundle="sys-lbpmservice" key="lbpmNode.mustSignYourSuggestion"/>","name":"fdUsageContent",opt:false' alertText="" key="auditNode"></div>
															</div>
															<div id="noticeHandler" data-dojo-type='sys/lbpmservice/mobile/noticeHandler/NoticeHandler' style="margin: 10px 0;">
															</div>
															<div id="privateOpinionTr">
																<div id="privateOpinion" data-dojo-type="mui/form/CheckBox" data-dojo-props="name:'isPrivateOpinion', value:'true', mul:false, text:'<bean:message bundle='sys-lbpmservice' key='lbpmservice.auditnote.setPrivateOpinion'/>'"></div>
																<div id="privateOpinionCanViewTr" style="display: none;margin-top: 1rem;margin-bottom: 1rem">
																	<xform:address validators="privateOpinion" propertyName="privateOpinionCanViewNames" propertyId="privateOpinionCanViewIds" mobile="true" showStatus="edit" mulSelect="true" htmlElementProperties="id='privateOpinionPerson'"></xform:address>
																</div>
															</div>
														</div>
														<div id="signatureRow" style="display: none">

														</div>
														<div id="commonUsagesDiv">
															<div class="handingWay" id="commonUsages"><div class="iconArea"><i class="mui mui-create"></i></div><span class="iconTitle"><bean:message bundle="sys-lbpmservice" key="mui.lbpmNode.usage"/></span></div>
														</div>
													</div>
													<c:if test="${isOpinionTypeEnabled eq 'true'}">
														<div id="opinionConfig">
															<input type="checkbox" data-dojo-type="mui/form/CheckBox" data-dojo-props="id:'notifyIsScript',text:'是否显示在稿纸',checked:true,value:true">

															<div  data-dojo-type="mui/form/Select"
																  data-dojo-props="subject:'下拉框',mul:false,id:'approveOpinionType',name:'approveOpinionType',value:'1',store:allAuditNodeTypes">
															</div>

														</div>
													</c:if>
												</td>
											</tr>
											<tr>
												<td id="assignmentRow" style="display:none;" class="" colspan="2">
													<table class="muiSimple" cellpadding="0" cellspacing="0">
														<c:forEach items="${sysWfBusinessForm.sysWfBusinessForm.fdAttachmentsKeyList}" var="fdAttachmentsKey" varStatus="statusKey">
															<tr>
																<td class='muiTitle' name='attachmentTtile' style="display: none"><bean:message bundle="sys-lbpmservice" key="lbpmservice.attachment.upload" /></td>
																<td style="padding: 0">
																	<div width="100%" id="${fdAttachmentsKey}" style="display:none;" name="attachmentDiv">
																		<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
																			<c:param name="fdKey" value="${fdAttachmentsKey}" />
																			<c:param name="formName" value="${param.formName}"/>
																			<c:param name="fdModelName" value="${sysWfBusinessForm.modelClass.name}"/>
																			<c:param name="fdModelId" value="${sysWfBusinessForm.fdId}"/>
																			<c:param name="align" value="right"></c:param>
																		</c:import>
																	</div>
																</td>
															</tr>
														</c:forEach>
													</table>
												</td>
											</tr>
											<!-- #128662-com.landray.kmss.lbpm.engine.process扩展点支持在移动端的引入-开始 -->
											<tr>
												<td colspan="2">
													<c:import url="/sys/lbpmservice/mobile/import/process_ext.jsp" charEncoding="UTF-8">
														<c:param name="modelName" value="${sysWfBusinessForm.modelClass.name}" />
														<c:param name="modelId" value="${sysWfBusinessForm.fdId}" />
														<c:param name="formName" value="${param.formName}" />
													</c:import>
												</td>
											</tr>
											<!-- #128662-com.landray.kmss.lbpm.engine.process扩展点支持在移动端的引入-结束 -->
											<tr style="display: none">
												<td class="muiTitle">辅助信息</td>
												<td>
														<%-- <div id='fdFlowDescriptionRow' onclick="showFlowDescriptionDialog()" class='flow_desc'>
                                                            <span class='flow_desc_text'>查看流程说明</span>
                                                            节点帮助信息
                                                            <div class='flow_desc_dialog' id='lbpmDescriptionDialog' style="display: none">
                                                                <div class="lastTitleNode"><kmss:message key="FlowChartObject.Lang.Node.description" bundle="sys-lbpm-engine" />
                                                                <!-- </div> -->
                                                                <div class="lastDetailNode">
                                                                <div>
                                                                    <p style="line-height: 20px;">
                                                                    <label id="currentlbpmDescription"></label>
                                                                    </p>
                                                                </div>
                                                                </div>
                                                            </div>
                                                        </div> --%>
													<div id='nodeDescriptionRow' class='node_help'>
														<span class='node_help_text'>查看节点帮助</span>
															<%-- 节点帮助信息 --%>
														<div class='node_help_dialog' id='nodeDescriptionDialog' style="display: none">
																<%-- <div class="lastTitleNode"><kmss:message key="FlowChartObject.Lang.Node.description" bundle="sys-lbpm-engine" /> --%>
															<!-- </div> -->
															<div class="lastDetailNode">
																<div>
																	<p style="line-height: 20px;">
																		<label id="currentNodeDescription"></label>
																	</p>
																	<div id="extNodeDescriptionDiv">
																		<c:import url="/sys/lbpm/flowchart/page/nodeDescription_ext_show.jsp" charEncoding="UTF-8">
																			<c:param name="modelId" value="${sysWfBusinessForm.fdId}" />
																			<c:param name="modelName" value="${param.modelName}" />
																			<c:param name="formName" value="${param.formName}" />
																			<c:param name="provideFor" value="mobile" />
																		</c:import>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</td>
											</tr>
										</c:if><%-- end action --%>
									</table>
								</div>
							</div>
						</c:if>
						<div class="actionView panel" id='moreActionView' style="display: none;">
							<table class="muiSimple" cellpadding="0" cellspacing="0">
								<c:if test="${(sysWfBusinessForm.docStatus>='20' && sysWfBusinessForm.docStatus<'30') || sysWfBusinessForm.docStatus == '11'}">
									<c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdIsHander == 'true'}">
										<tr>
												<%--通知紧急程度 --%>
											<td colspan="2" id="notifyLevelRow" class="notifyLevelRow">
												<div class="titleNode operation_title">
													<bean:message bundle="sys-notify" key="sysNotifyTodo.level.title" />
												</div>
												<div class="detailNode" id="notifyLevelTD">
													<%
											//获取紧急度级别
											//获取紧急度级别
											com.landray.kmss.sys.lbpmservice.support.service.spring.InternalLbpmProcessForm lbpmForm =
													(com.landray.kmss.sys.lbpmservice.support.service.spring.InternalLbpmProcessForm)pageContext.getAttribute("lbpmProcessForm");
											String notifyLevel = lbpmForm.getProcessInstanceInfo().getProcessParameters().getInstanceParamValue(lbpmForm.getProcessInstanceInfo().getProcessInstance(),"notifyLevel");

											pageContext.setAttribute("notifyLevelTemp", notifyLevel);

													%>
										<kmss:editNotifyLevel property='sysWfBusinessForm.fdNotifyLevel' mobile='true' value='${notifyLevelTemp }'/>
													<script type="text/javascript">
														Com_Parameter.event["submit"].push(function() {
															<%--设置选择的级别（仿PC实现）--%>
															if (lbpm.globals.setOperationParameterJson) {
																var _notifyLevel = document.getElementsByName("sysWfBusinessForm.fdNotifyLevel")[0].value;
																lbpm.globals.setOperationParameterJson(_notifyLevel,"notifyLevel","param");
															}
															return true;
														});
													</script>
												</div>
											</td>
										</tr>
									</c:if>
								</c:if>
								<tr>
									<td class="muiTitle"><bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.currentProcessor" /></td>
									<td>
										<div id="currentHandlersRow">
											<div class="actionView no_padding">
												<div class="lbpmInfoTable">
													<div class="detailNode">
														<div id="currentHandlersLabel">
															<kmss:showWfPropertyValues idValue="${sysWfBusinessForm.fdId}" propertyName="handerNameDetail" mobile="true" />
														</div>
													</div>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</table>
						</div>
						<div data-dojo-type="sys/lbpmservice/mobile/common/LbpmFolder"
							 data-dojo-props="expandDomId:'moreActionView',showType:'dialog'">
						</div>
						<div style="height: 80px"></div>
						<c:if test="${empty param.onClickSubmitButton}">
						<script type="text/javascript">
							require(["mui/form/ajax-form!sysWfProcessForm"]);
						</script>
					</form>
					</c:if>
					<c:if test="${param.viewName != ''}">
						<!-- <div class="optionsSplitLine"></div> -->
					<ul id='lbpmViewOperationTabBar' data-dojo-type="mui/tabbar/TabBar" fixed="bottom" class="muiViewBottom muiTabBarBottomAdaptive">
							<c:choose>
								<c:when test="${sysWfBusinessForm.sysWfBusinessForm.fdTemplateType == '4'}">
									<li data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowChartButton" class="lbpmSwitchButton muiSplitterButton"
										data-dojo-props='icon1:"mui mui-flowchart",showType:"dialog",backTo:"${param.backTo }"'>
										<bean:message bundle="sys-lbpmservice" key="lbpm.tab.graphic"/>
									</li>
								</c:when>
								<c:otherwise>
									<li data-dojo-type="mui/tabbar/TabBarButton" class="lbpmSwitchButton muiSplitterButton"
										data-dojo-props='icon1:"mui mui-flowchart",onClick:showFlowChart'>
										<bean:message bundle="sys-lbpmservice" key="lbpm.tab.graphic"/>
									</li>
								</c:otherwise>
							</c:choose>
							<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props='id:"process_cancel_button",totalCopies:6,proportion:2' class="muiSplitterButton lbpmCancel"
								onclick="closeDialog()">
								<bean:message  key="button.cancel" />
							</li>
							<c:if test="${sysWfBusinessForm.docStatus<'30'}">
								<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props='id:"process_review_button",totalCopies:6,proportion:4' class="mainTabBarButton lbpmSubmit"
									onclick="if(review_submit()){${(empty param.onClickSubmitButton) ? 'Com_Submit(document.sysWfProcessForm, &quot;update&quot;);' : (param.onClickSubmitButton)}}">
									<bean:message  key="button.submit" />
								</li>
							</c:if>
							<c:if test="${sysWfBusinessForm.docStatus>='30'}">
								<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props='colSize:2'></li>
							</c:if>
						</ul>
					</c:if>
				</div>
				<script type="text/javascript">


					require(["dijit/registry","dojo/topic","dojox/mobile/sniff","mui/device/device","dojo/query","dojo/dom-style","dojo/Deferred","dojox/mobile/viewRegistry"], function(registry,topic,has,device,query,domStyle,Deferred,viewRegistry) {

						<c:if test="${(sysWfBusinessForm.docStatus>='20' && sysWfBusinessForm.docStatus<'30') || sysWfBusinessForm.docStatus == '11'}">
						topic.subscribe("initComplete",function(){
							//自由流和普通流程审批有点不一致，自由流是节点属性，普通流程用的是扩展属性
							var view_isOpinionTypeEnabled = "${isOpinionTypeEnabled}";
							var extAttributes=lbpm.globals.getNodeObj(lbpm.nowNodeId).extAttributes;
							var isOpinionConfig="";
							var opinionTypeValue="";
							if (view_isOpinionTypeEnabled == "true") {
								if(extAttributes){
									for(var i=0;i<extAttributes.length;i++){
										if(extAttributes[i].name=="opinionConfig"){
											isOpinionConfig=extAttributes[i].value;
										}
									}
									for(var i=0;i<extAttributes.length;i++){
										if(extAttributes[i].name=="opinionType"){
											opinionTypeValue=extAttributes[i].value;
										}
									}
								}
								//扩展属性没取到，从节点属性获取
								if(isOpinionConfig==""){
									isOpinionConfig=lbpm.globals.getNodeObj(lbpm.nowNodeId).opinionConfig;
									opinionTypeValue=lbpm.globals.getNodeObj(lbpm.nowNodeId).opinionType;
									if(isOpinionConfig=="true"){
										domStyle.set(query("#opinionConfig")[0],{
											"display":""
										});
										var selectWgt = registry.byId("approveOpinionType");
										if(opinionTypeValue&&opinionTypeValue!=""){
											selectWgt._setValueAttr(opinionTypeValue);
										}else{
											selectWgt._setValueAttr("1");
										}
									}else{
										domStyle.set(query("#opinionConfig")[0],{
											"display":"none"
										});
									}
								}else{
									if(isOpinionConfig=="true"){
										domStyle.set(query("#opinionConfig")[0],{
											"display":""
										});
										var selectWgt = registry.byId("approveOpinionType");
										if(opinionTypeValue&&opinionTypeValue!=""){
											selectWgt._setValueAttr(opinionTypeValue);
										}else{
											selectWgt._setValueAttr("1");
										}
									}else{
										domStyle.set(query("#opinionConfig")[0],{
											"display":"none"
										});
									}
								}
							}
						});
						</c:if>



						<%-- 提交流程(点击“提交”按钮的响应函数)  --%>
						window.review_submit = function(evt){
							if(device && device.getClientType() == 9){
								evt = evt || window.event || arguments[0];
								if (evt.preventDefault) {
									evt.preventDefault();
								}
								if (evt.stopPropagation) {
									evt.stopPropagation();
								}
								var nowTime = new Date().getTime();
								var clickTime = lbpm.cbtime;
								var areaTime = 500;
								if(lbpm.firstClickStatus == true){
									areaTime = 1200;
								}
								if (clickTime != "undefined" && nowTime - clickTime < areaTime) {
									areaTime = 500;
									lbpm.firstClickStatus = false;
									return false;
								}
								lbpm.firstClickStatus = false;
								lbpm.cbtime  = nowTime;
							}
							var lbpmwdt = registry.byId("${lbpmViewName}");
							if(lbpm && lbpm.saveFormData === true){
								lbpm.saveFormData = false;
								return true;
							}else if(lbpm.saveFormData === false){
								var canStartProcessId = query("[id='sysWfBusinessForm.canStartProcess']");
								if(typeof canStartProcessId !='undefined' && canStartProcessId.length>0) {
									canStartProcessId[0].value = "true";
								}
								var saveFormDataId= query("[id='saveFormData']");
								if(typeof saveFormDataId !='undefined' && saveFormDataId.length>0){
									var view = viewRegistry.getEnclosingView(saveFormDataId[0]);
									view = viewRegistry.getParentView(view) || view;
									var backTo = view.id;
									var element = registry.byId(this.backTo);
									if(element){
										element.notValRequired = false;
									}
								}

							}
							if(lbpmwdt.validate && !lbpmwdt.validate()){
								return false;
							}
							return true;
						}

						window.closeDialog = function(evt){
							if(device && device.getClientType() == 9){
								evt = evt || window.event || arguments[0];
								if (evt.preventDefault) {
									evt.preventDefault();
								}
								if (evt.stopPropagation) {
									evt.stopPropagation();
								}
								var nowTime = new Date().getTime();
								var clickTime = lbpm.cbtime;
								var areaTime = 500;
								if(lbpm.firstClickStatus == true){
									areaTime = 1200;
								}
								if (clickTime != "undefined" && nowTime - clickTime < areaTime) {
									areaTime = 500;
									lbpm.firstClickStatus = false;
									return false;
								}
								lbpm.firstClickStatus = false;
								lbpm.cbtime  = nowTime;
							}
							topic.publish("/lbpm/operation/destroyDialog");
						}
					});
				</script>
			</div>
			<%--自由流辅助用流程图--%>
			<div data-dojo-type="dojox/mobile/View" id="freeflowchart" style="display:none;">
				<div id="workflowInfoDiv" style="width:100%; -webkit-overflow-scrolling:touch; overflow:scroll;display:none;">
					<iframe scrolling="no" width="100%" id="WF_IFrame"></iframe>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<c:if test="${(sysWfBusinessForm.sysWfBusinessForm.fdIsDrafter == 'true' || sysWfBusinessForm.sysWfBusinessForm.fdIsHistoryHandler == 'true' || sysWfBusinessForm.sysWfBusinessForm.fdIsBranchAdmin == 'true')
			&&(sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus == null || sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus=='' || fn:contains(sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus,'N'))}">
				<%@ include file="./form_hidden.jsp" %>
			</c:if>
		</c:otherwise>
	</c:choose>
</c:if>
<div data-dojo-type="mui/tabbar/TabBarAdapter" style="display: none"></div>