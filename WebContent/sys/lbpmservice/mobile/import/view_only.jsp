<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder,com.landray.kmss.util.StringUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>

<%
	//第三方系统集成时的表单参数
	String sformList = request.getParameter("sformList");

	if (StringUtil.isNull(sformList)){
		sformList = (String)request.getAttribute("sformList");
	}

	if (StringUtil.isNotNull(sformList)){
		request.setAttribute("_sformList",sformList);
		sformList = URLDecoder.decode(sformList,"UTF-8");
	}
%>

<c:set var="sysWfBusinessForm" value="${requestScope[formName]}" />
<c:set var="lbpmProcessForm" value="${sysWfBusinessForm.sysWfBusinessForm.internalForm}" />

<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		<c:out value="流程审批"></c:out>
	</template:replace>
	<template:replace name="head">
		<mui:min-file name="mui-review-view.css"/>
	</template:replace>
	<template:replace name="content">
		<div id="scrollView"
			 data-dojo-type="mui/view/DocScrollableView"
			 data-dojo-mixins="mui/form/_ValidateMixin">
			<div data-dojo-type="mui/panel/AccordionPanel">

				<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="sys-lbpmservice" key="mui.process.mobile.note" />',icon:'mui-ul'">
					<c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
						<c:param name="fdModelId" value="${sysWfBusinessForm.fdId}"/>
						<c:param name="fdModelName" value="${sysWfBusinessForm.modelClass.name}"/>
						<c:param name="formBeanName" value="${formName}"/>
					</c:import>
				</div>

			</div>

			<c:if test="${sysWfBusinessForm.docStatus < '30' }">
				<template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp"
								  editUrl="/sys/lbpmservice/support/lbpm_process/lbpmProcess.do?method=editProcess&type=mobile&fdId=${param.fdId}&sformList=1"
								  formName="${formName}" tabbaId="_pageBtn" allowReview="true" showType="normal">
				</template:include>
			</c:if>

		</div>

		<%
			//第三方系统集成时的表单参数
			if (StringUtil.isNotNull(sformList)){
		%>
		<script type="text/javascript">
			var _thirdSysFormList = <%=sformList%>;
		</script>
		<%}%>

		<!--直接使用移动端view.jsp--->
		<c:if test="${sysWfBusinessForm.sysWfBusinessForm!=null && sysWfBusinessForm.docStatus < '30'}">
			<%@ include file="./script.jsp" %>
			<jsp:include page="/sys/lbpmservice/mobile/lbpm_audit_note/import/view_include.jsp">
				<jsp:param name="processId" value="${sysWfBusinessForm.fdId}" />
			</jsp:include>
			<c:choose>
				<c:when test="${(sysWfBusinessForm.sysWfBusinessForm.fdIsHander == 'true') &&(sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus == null
		|| sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus=='' || fn:contains(sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus,'N'))}">
					<c:set var="lbpmViewName" value="${(empty param.viewName) ? 'lbpmView' : (param.viewName)}"/>
					<div class="lbpmView"
							<c:if test="${param.viewName != 'none'}">
								data-dojo-type="sys/lbpmservice/mobile/common/LbpmView" id="${lbpmViewName}"
							</c:if>>

						<div id='lbpmOperContent'>

							<c:if test="${empty param.onClickSubmitButton}">
							<form name="sysWfProcessForm" method="POST"
								  action="<c:url value="/sys/lbpmservice/support/lbpm_process/lbpmProcess.do" />" autocomplete="off">
								</c:if>
								<%@ include file="./form_hidden.jsp" %>
								<c:if test="${(sysWfBusinessForm.docStatus>='20' && sysWfBusinessForm.docStatus<'30') || sysWfBusinessForm.docStatus == '11'}">
									<c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdIsHander == 'true'}">
										<div class="actionView">
											<div id="lbpmOperationMethodTable" class="operationMethodArea">
												<div id="operationMethodRow">
													<div class="titleNode">
														<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.handMethods" />
													</div>
													<div class="detailNode">
													</div>
												</div>
											</div>
										</div>
										<div class="optionsSplitLine"></div>
										<div class="actionView">
											<div id="lbpmOperationTable">
												<div id="operationItemsRow">
													<div class="titleNode">
														<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationItems" />
														<input type="hidden" id="operationMethodsGroup" view-type="input"/>
														<input type="hidden" id="operationItemsSelect" value="0"/>
													</div>
													<div class="detailNode">
													</div>
												</div>
												<!-- <div id="operationMethodsRow" style="display: none;">
								<div class="titleNode" >
									<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.handMethods" />
								</div>
								<div class="detailNode">
								<select id="operationMethodsGroup" view-type="select" alertText='' key='operationType' name='oprGroup'></select>
								<select id="operationItemsSelect" name="operationItemsSelect"></select>
								</div>
							</div> -->
													<%-- 用于起草节点 ,显示即将流向--%>
												<c:if test="${sysWfBusinessForm.docStatus == null || sysWfBusinessForm.docStatus<'20'}">
													<div style="display:none;" class="splitLine">
														<div class="titleNode" >
															<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.manualNodeSelect" />
														</div>
														<div class="detailNode" id="manualNodeSelectTD">
															&nbsp;
														</div>
													</div>
													<div style="display:none;">
														<div class="titleNode" >
															<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.nextNode" />
														</div>
														<div class="detailNode" id="nextNodeTD">
															&nbsp;
														</div>
													</div>
												</c:if>
													<%-- 自由流节点行 --%>
												<div id="freeflowRow" style="display:none;">
													<div class="titleNode" id="freeflowRowTitle">
														<bean:message bundle="sys-lbpmservice" key="lbpm.tab.graphic" />
													</div>
													<div class="detailNode" id="freeFlowNodeDIV" style="display:none;">
													</div>
												</div>
												<div id="operationsRow" style="display:none;" lbpmMark="operation" class="splitLine">
													<div id="operationsTDTitle" class="titleNode" lbpmDetail="operation" >
														&nbsp;
													</div>
													<div id="operationsTDContent" class="detailNode" lbpmDetail="operation">
														&nbsp;
													</div>
												</div>
												<div id="operationsRow_Scope" style="display:none;" lbpmMark="operation" class="splitLine">
													<div id="operationsTDTitle_Scope" class="titleNode" lbpmDetail="operation" >
														&nbsp;
													</div>
													<div id="operationsTDContent_Scope" class="detailNode" lbpmDetail="operation">
														&nbsp;
													</div>
												</div>
												<%@ include file="/sys/lbpmservice/mobile/change_process/sysLbpmProcess_changeProcess.jsp" %>
													<%--通知紧急程度 --%>
												<div id="notifyLevelRow" class="notifyLevelRow">
													<div class="titleNode"  width="15%">
														<bean:message bundle="sys-notify" key="sysNotifyTodo.level.title" />
													</div>
													<div class="detailNode" id="notifyLevelTD">
														<%
															//获取紧急度级别
															com.landray.kmss.sys.lbpmservice.support.service.spring.InternalLbpmProcessForm lbpmForm =
																	(com.landray.kmss.sys.lbpmservice.support.service.spring.InternalLbpmProcessForm)pageContext.getAttribute("lbpmProcessForm");
															String notifyLevel = lbpmForm.getProcessInstanceInfo().getProcessParameters().getInstanceParamValue(lbpmForm.getProcessInstanceInfo().getProcessInstance(),"notifyLevel");
														%>
														<kmss:editNotifyLevel property='sysWfBusinessForm.fdNotifyLevel' mobile='true' value='<%= notifyLevel==null?"":notifyLevel %>'/>
														<script type="text/javascript">
															Com_Parameter.event["submit"].push(function() {
																//设置选择的级别（仿PC实现）
																if (lbpm.globals.setOperationParameterJson) {
																	var _notifyLevel = document.getElementsByName("sysWfBusinessForm.fdNotifyLevel")[0].value;
																	lbpm.globals.setOperationParameterJson(_notifyLevel,"notifyLevel","param");
																}
																return true;
															});
														</script>
													</div>
												</div>
											</div>
										</div>
										<div class="optionsSplitLine"></div>
										<div class="actionView">
											<div class="lbpmAuditNoteTable">
													<%-- 审批意见扩展使用 --%>
												<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_ext.jsp" charEncoding="UTF-8">
													<c:param name="auditNoteFdId" value="${sysWfBusinessForm.sysWfBusinessForm.fdAuditNoteFdId}" />
													<c:param name="modelName" value="${sysWfBusinessForm.modelClass.name}" />
													<c:param name="modelId" value="${sysWfBusinessForm.fdId}" />
													<c:param name="formName" value="${param.formName}" />
													<c:param name="lbpmViewName" value="${lbpmViewName}" />
													<c:param name="provideFor" value="mobile" />
												</c:import>

												<div id="commonUsagesRow">
													<div id="commonUsagesDiv">
														<div class="handingWay" id="commonUsages"><div class="iconArea"><i class="mui mui-create"></i></div><span class="iconTitle"><bean:message bundle="sys-lbpmservice" key="mui.lbpmNode.usage"/></span></div>
													</div>
													<div id="descriptionDiv">
														<div id="fdUsageContent" data-dojo-type='mui/form/Textarea'
															 data-dojo-props="'subject':'<bean:message bundle="sys-lbpmservice" key="mui.lbpmNode.usage" />','placeholder':'<bean:message bundle="sys-lbpmservice" key="lbpmNode.mustSignYourSuggestion"/>','name':'fdUsageContent',opt:false" alertText="" key="auditNode">
														</div>
													</div>
												</div>
													<%-- <div id="assignmentRow" style="display:none;" class="splitLine">
                                                        <c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
                                                            <c:param name="fdKey" value="${sysWfBusinessForm.sysWfBusinessForm.fdAuditNoteFdId}" />
                                                            <c:param name="formName" value="${param.formName}"/>
                                                            <c:param name="fdModelName" value="${sysWfBusinessForm.modelClass.name}"/>
                                                            <c:param name="fdModelId" value="${sysWfBusinessForm.fdId}"/>
                                                        </c:import>
                                                    </div> --%>

													<%-- 删除附件入口<div id="assignmentRow" style="display:none;" class="splitLine">
                                                    <table class="muiSimple" cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td colspan="2">
                                                                <c:forEach items="${sysWfBusinessForm.sysWfBusinessForm.fdAttachmentsKeyList}" var="fdAttachmentsKey" varStatus="statusKey">
                                                                    <div width="100%" id="${fdAttachmentsKey}" style="display:none;" name="attachmentDiv">
                                                                        <c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
                                                                            <c:param name="fdKey" value="${fdAttachmentsKey}" />
                                                                            <c:param name="formName" value="${param.formName}"/>
                                                                            <c:param name="fdModelName" value="${sysWfBusinessForm.modelClass.name}"/>
                                                                            <c:param name="fdModelId" value="${sysWfBusinessForm.fdId}"/>
                                                                        </c:import>
                                                                    </div>
                                                                </c:forEach>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    </div>--%>

													<%-- 节点帮助信息 --%>
												<div id="nodeDescriptionRow">
													<div class="lastTitleNode"><kmss:message key="FlowChartObject.Lang.Node.description" bundle="sys-lbpm-engine" />
													</div>
													<div class="lastDetailNode">
														<div class="mui_kmCard_wrap">
															<p class="mui_kmCard_title">
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
										</div>
									</c:if><%-- end action --%>
								</c:if>
								<div class="optionsSplitLine"></div>
								<div class="actionView">
									<div class="lbpmInfoTable">
										<div class="titleNode" >
											<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.currentProcessor" />
										</div>
										<div class="detailNode">
											<div id="currentHandlersLabel">
												<kmss:showWfPropertyValues idValue="${sysWfBusinessForm.fdId}" propertyName="handerNameDetail" mobile="true" />
											</div>
										</div>
									</div>
								</div>
								<c:if test="${empty param.onClickSubmitButton}">
								<script type="text/javascript">
									require(["mui/form/ajax-form!sysWfProcessForm"]);
								</script>
							</form>
							</c:if>

							<c:if test="${param.viewName != ''}">
								<ul id='lbpmViewOperationTabBar' data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
									<li data-dojo-type="mui/back/BackButton" data-dojo-props="icon1:''">
										<bean:message  bundle="sys-mobile"  key="mui.button.back" />
									</li>
									<c:if test="${sysWfBusinessForm.docStatus<'30'}">
										<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props='colSize:2'
											onclick="doFlowOK();">
											<bean:message  key="button.ok" />
										</li>
									</c:if>
									<c:if test="${sysWfBusinessForm.docStatus>='30'}">
										<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props='colSize:2'></li>
									</c:if>
									<c:choose>
										<c:when test="${sysWfBusinessForm.sysWfBusinessForm.fdTemplateType == '4'}">
											<li data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowChartButton" class="lbpmSwitchButton muiSplitterButton"
												data-dojo-props='icon1:"mui mui-flowchart"'>
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
								</ul>
							</c:if>

						</div>

					</div>


					<!--自由流辅助用流程图-->
					<div data-dojo-type="dojox/mobile/View" id="freeflowchart" style="display:none;">
						<div id="workflowInfoDiv" style="width:100%; -webkit-overflow-scrolling:touch; overflow:scroll;display:none;">
							<iframe scrolling="no" width="100%" id="WF_IFrame"></iframe>
						</div>
					</div>


				</c:when>
				<c:otherwise>
					<c:if test="${(sysWfBusinessForm.sysWfBusinessForm.fdIsDrafter == 'true' || sysWfBusinessForm.sysWfBusinessForm.fdIsHistoryHandler == 'true')
			&&(sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus == null || sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus=='' || fn:contains(sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus,'N'))}">
						<%@ include file="./form_hidden.jsp" %>
					</c:if>
				</c:otherwise>
			</c:choose>
		</c:if>
		<!--直接使用移动端view.jsp end--->

	</template:replace>

</template:include>

<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/domain.js"></script>

<script>
	require(["dojo/ready","dijit/registry"],function(ready,registry){
		ready(99999,function(){
			registry.byId("_pageBtn").domNode.children[0].onclick = function(){
				doFlowBack();
			};
		});
	});

	function doFlowBack(){
		domain.call(parent, "buz_backFlowZone", [], function(data) {
		});
	}

	function doFlowOK(){
		if (lbpm_validateProcess()) {
			hideFlowZone();
		}
	}

	function hideFlowZone(){
		domain.call(parent, "buz_hideFlowZone", [], function(data) {
		});
	}

	//流程校验后获取表单相关数据，若校验没通过，则返回空。（此方法把lbpm_getApprovalInfo和lbpm_validateProcess合并，以备异构系统调用方便。）
	var lbpm_getApprovalData = function() {
		if (lbpm_validateProcess() && lbpm_validateForm()) {
			return lbpm_getApprovalInfo();
		}
		return "";
	};

	//获取表单相关数据
	var lbpm_getApprovalInfo = function() {
		var obj = {};

		$("input[name^='sysWfBusinessForm.']:enabled").each(function() {
			var field = $(this);
			obj[field.attr("name")] = field.val();
		});

		$("input[name^='attachmentForms.']:enabled").each(function() {// 附件
			var attachment = $(this);
			obj[attachment.attr("name")] = attachment.val();
		});

		return JSON.stringify(obj);
	};

	//校验流程函数
	var lbpm_validateProcess = function() {
		//提交表单校验
		for (var i=0; i<Com_Parameter.event["submit"].length; i++) {
			var func = Com_Parameter.event["submit"][i];
			if(func.toString()!="function (){return _4a8.apply(_4a7,arguments||[]);}"){
				//手写在提交数据时才处理
				if(!Com_Parameter.event["submit"][i]()) {
					return false;
				}
			}
		}
		return lbpm.globals.submitFormEvent();
	};

	var lbpm_validateForm = function() {
		for (var i=0; i<Com_Parameter.event["submit"].length; i++) {
			var func = Com_Parameter.event["submit"][i];
			if(func.toString()=="function (){return _4a8.apply(_4a7,arguments||[]);}"){
				//手写在提交数据时才处理
				if(!Com_Parameter.event["submit"][i]()) {
					return false;
				}
			}
		}
		//提交表单消息确认
		for (var i=0; i<Com_Parameter.event["confirm"].length; i++) {
			if(!Com_Parameter.event["confirm"][i]()) {
				//附件在提交数据时才处理
				return false;
			}
		}
		return true;
	};

	//注册跨域调用方法
	domain.register("lbpm_getApprovalInfo", lbpm_getApprovalInfo);
	domain.register("lbpm_validateProcess", lbpm_validateProcess);
	domain.register("lbpm_validateForm", lbpm_validateForm);
	domain.register("lbpm_getApprovalData", lbpm_getApprovalData);

</script>