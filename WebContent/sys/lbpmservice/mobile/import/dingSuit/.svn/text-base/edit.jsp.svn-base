<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>

<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" />
<c:set var="lbpmProcessForm" value="${sysWfBusinessForm.sysWfBusinessForm.internalForm}" />

<c:if test="${sysWfBusinessForm.sysWfBusinessForm!=null && lbpmProcessForm!=null}">
<%@include file="./../script.jsp"%>
<c:if test="${empty JsParam._data }">
	<mui:cache-file name="sys-lbpm-note.js" cacheType="md5"/>
</c:if>
<jsp:include page="/sys/lbpmservice/mobile/lbpm_audit_note/import/view_include.jsp">
	<jsp:param name="processId" value="${sysWfBusinessForm.fdId}" />
</jsp:include>

<c:choose>
    <c:when test="${ not empty param.viewRenderType && param.viewRenderType eq 'panel' }">
       <div class="lbpmView" data-dojo-type="sys/lbpmservice/mobile/common/LbpmPanel" data-dojo-mixins="sys/lbpmservice/mobile/common/_LbpmValidateMixin">
    </c:when>
	<c:otherwise>
	    <c:choose>
	      	<c:when test="${param.viewName != 'none'}">
		       <div class="lbpmView old" data-dojo-type="sys/lbpmservice/mobile/common/LbpmView" id="${(empty param.viewName) ? 'lbpmView' : (param.viewName)}" >
			</c:when>
			<c:otherwise>
			   <div class="lbpmView">
			</c:otherwise>
	   </c:choose>
	</c:otherwise>
</c:choose>
		<%@ include file="./../form_hidden.jsp" %>
		<div class="actionTop"></div>
		<div class="actionView actionCenter no_padding">
		<div id="lbpmOperationTable" <c:if test="${empty param['isNew'] }" >style='padding:0 1.2rem;'</c:if>>
			<table class="muiSimple" cellpadding="0" cellspacing="0">
				<tr id='lbpmOtherInfo' style="display: none">
					<td class="muiTitle">辅助信息</td>
					<td style="text-align: right">
						<div id='fdFlowDescriptionRow' style="display: none" onclick="showFlowDescriptionDialog()" class='flow_desc'>
							<span class='flow_desc_text'>查看流程说明</span>
							<%-- 节点帮助信息 --%>
							<div class='flow_desc_dialog' id='flowDescriptionDialog' style="display: none">
								<div class="lastTitleNode"><kmss:message key="FlowChartObject.Lang.Node.description" bundle="sys-lbpm-engine" />
								</div>
								<div class="lastDetailNode">
									<div>
										<p style="line-height: 20px;">
										<label id="currentflowDescription"></label>
										</p>
									</div>
								</div>
								<div class="muiDialogBtn" onclick="hideFlowDescriptionDialog(this);">确定</div>
							</div>
						</div>
						<script type="text/javascript">
							$(function(){
								 //A计划用于流程新建页，流程说明查看页的确定按钮，关闭流程说明弹窗
								window.hideFlowDescriptionDialog = function(obj){
									$("#flowDescriptionDialog").parents(".muiDialogElementContent").parent(".muiDialogElementContainer").parents(".muiDialogElement").css('display','none');
								}
							});
						</script>
						<div id='nodeDescriptionRow' style="display: none" onclick="showNodeHelpDialog()" class='node_help'>
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
				<tr>
					<td colspan="2" id="operationItemsRow" lbpmMark="operation" style="display: none">
						<%-- <div class="titleNode operation_title" >
							<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationItems" />
						</div> --%>
						<div class="detailNode">
							<div id="operationItemsSelect" data-dojo-type="mui/form/Select"
									data-dojo-props="name:'operationItemsSelect', value:'0', mul:false, subject: '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationItems" />',orient:'vertical'" ></div>
						</div>
					</td>
				</tr>
				<c:if test="${sysWfBusinessForm.docStatus == null || sysWfBusinessForm.docStatus<'20'}">
				<tr>
					<td class="muiTitle"><bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.submitRole" /></td>
					<td>
						<div id="handlerIdentityRow" lbpmMark="operation" style="display:none">
							<div class="detailNode">
								<div id="rolesSelectObj" data-dojo-type="mui/form/Select"  key="handlerId"
									data-dojo-props="name:'rolesSelectObj', value:'0', mul:false, subject: '<bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.submitRole" />'" ></div>
							</div>
						</div>
					</td>
				</tr>
				</c:if>
				<tr>
					<td id="operationMethodsRow" lbpmMark="operation" style="display: none;">
						<div class="titleNode">
							<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.handMethods" />
						</div>
						<div class="detailNode">
							<select id="operationMethodsGroup" view-type="select" alertText='' key='operationType' name='oprGroup' validate='optMethodGroup'></select>
						</div>
					</td>
				</tr>
				<%-- 用于起草节点 ,显示即将流向--%>
				<c:if test="${sysWfBusinessForm.docStatus == null || sysWfBusinessForm.docStatus<'20'}">
				<tr>
					<td colspan="2" style="padding:0;display:none"  id="manualBranchNodeRow" lbpmMark="operation">
						<div colspan="3" id="manualNodeSelectTD">
							&nbsp;
						</div>
					</td>
				</tr>
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
					<td colspan="2" lbpmMark="operation" style="display:none;">
							<div class="titleNode operation_title dingNodeTitle" id="nextNodeTDTitle">
								<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.nextNode" />
							</div>
							<div  class="detailNode nextNode dingNodeContent" id="nextNodeTD">
							</div>
					</td>
				</tr>
				</c:if>
				<c:if test="${sysWfBusinessForm.docStatus>='20' && sysWfBusinessForm.docStatus!='21' && sysWfBusinessForm.docStatus<'30'}">
				<tr>
					<td colspan="2" id="operationsRow" style="display:none;" lbpmMark="operation">
						<div id="operationsTDTitle" class="titleNode operation_title dingNodeTitle" lbpmDetail="operation">
							&nbsp;
						</div>
						<div id="operationsTDContent"  class="detailNode dingNodeContent" lbpmDetail="operation">
							&nbsp;
						</div>
					</td>
				</tr>
				</c:if>
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
					<td colspan="2" id="lbpmext_auditpoint_select_content" style="display:none;border-bottom: 1px solid rgba(231,231,231,0.3);">

					</td>
				</tr>
                <%--通知紧急程度 --%>
                <tr>
                    <td colspan="2" id="notifyLevelRow" class="notifyLevelRow">
                        <div class="titleNode operation_title dingNodeTitle"  width="15%">
                            <bean:message bundle="sys-notify" key="sysNotifyTodo.level.title" />
                        </div>
                        <div class="detailNode dingNodeContent" id="notifyLevelTD">
                            <%
                                //获取紧急度级别
                                com.landray.kmss.sys.lbpmservice.support.service.spring.InternalLbpmProcessForm lbpmForm =
                                        (com.landray.kmss.sys.lbpmservice.support.service.spring.InternalLbpmProcessForm)pageContext.getAttribute("lbpmProcessForm");
                                String notifyLevel = lbpmForm.getProcessInstanceInfo().getProcessParameters().getInstanceParamValue(lbpmForm.getProcessInstanceInfo().getProcessInstance(),"notifyLevel");
                            %>
                            <kmss:editNotifyLevel property='sysWfBusinessForm.fdNotifyLevel' mobile='true' value='<%= notifyLevel==null?"":notifyLevel%>'/>
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
                    </td>
                </tr>
                <tr class="ding_divide"></tr>
			</table>
		</div>
		</div>
		<!-- <div class="optionsSplitLine"></div> -->
		<div class="actionView no_padding" id='moreActionView' style="display: none;<c:if test="${empty param['isNew'] }">display:block</c:if>">
		<div class="lbpmAuditNoteTable lbpmDingAuditNoteTable" id="lbpmAuditNoteTable" <c:if test="${empty param['isNew'] }" >style='padding:0 1.2rem;'</c:if>>
			<%-- 审批意见扩展使用 --%>
			<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_ext.jsp" charEncoding="UTF-8">
				<c:param name="auditNoteFdId" value="${sysWfBusinessForm.sysWfBusinessForm.fdAuditNoteFdId}" />
				<c:param name="modelName" value="${sysWfBusinessForm.modelClass.name}" />
				<c:param name="modelId" value="${sysWfBusinessForm.fdId}" />
				<c:param name="formName" value="${param.formName}" />
				<c:param name="provideFor" value="mobile" />
				<c:param name="lbpmViewName" value="${ param.viewName eq 'none' && not empty param.backTo ? param.backTo : '' }" />
			</c:import>
			<table class="muiSimple" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="2"  id = "commonUsagesRow">
						<div class="titleNode operation_title">
							<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdUsageContent" />
						</div>
						<div id="descriptionDiv">
							<div id="fdUsageContent" data-dojo-type='mui/form/Textarea'
								data-dojo-props='validate:"fdUsageContent usageContentMaxLen","subject":"<bean:message bundle="sys-lbpmservice" key="mui.lbpmNode.usage" />","placeholder":"<bean:message bundle="sys-lbpmservice" key="lbpmNode.mustSignYourSuggestion"/>","name":"fdUsageContent",opt:false' alertText="" key="auditNode"></div>
							<div id="privateOpinionTr" class="muiFormEleWrap">
							    <div id="privateOpinion" data-dojo-type="mui/form/CheckBox" data-dojo-props="name:'isPrivateOpinion', value:'true', mul:false, text:'<bean:message bundle='sys-lbpmservice' key='lbpmservice.auditnote.setPrivateOpinion'/>'"></div>
								<div id="privateOpinionCanViewTr" style="display: none;margin-top: 1rem;margin-bottom: 1rem">
									<xform:address validators="privateOpinion" propertyName="privateOpinionCanViewNames" propertyId="privateOpinionCanViewIds" mobile="true" mulSelect="true" htmlElementProperties="id='privateOpinionPerson'"></xform:address>
								</div>
							</div>
						</div>
						<div id="commonUsagesDiv">
							<div class="handingWay" id="commonUsages"><div class="iconArea"><i class="mui mui-create"></i></div><span class="iconTitle"><bean:message bundle="sys-lbpmservice" key="mui.lbpmNode.usage"/></span></div>
						</div>
						<div id="signatureRow" class="dingSignatureRow" style="display: none">

						</div>
					</td>
				</tr>
                <tr class="ding_divide"></tr>
				<tr>
					<td id="assignmentRow" style="display:none;" class="" colspan="2">
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<c:forEach items="${sysWfBusinessForm.sysWfBusinessForm.fdAttachmentsKeyList}" var="fdAttachmentsKey" varStatus="statusKey">
								<tr>
									<td class='muiTitle' name='attachmentTtile' style="display: none"><bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.assignment" /></td>
									<td style="padding: 0">
										<div width="100%" id="${fdAttachmentsKey}" style="display:none;" name="attachmentDiv" class='attachmentDiv'>
											<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
												<c:param name="fdKey" value="${fdAttachmentsKey}" />
												<c:param name="formName" value="${param.formName}"/>
												<c:param name="fdModelName" value="${sysWfBusinessForm.modelClass.name}"/>
												<c:param name="fdModelId" value="${sysWfBusinessForm.fdId}"/>
												<c:param name="orient" value="horizontal"/>
												<c:param name="align" value="right"></c:param>
											</c:import>
										</div>
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				<c:if test="${sysWfBusinessForm.docStatus != null && sysWfBusinessForm.docStatus>='20'}">
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
				</c:if>
			</table>
		</div>
		</div>
		<div <c:if test="${empty param['isNew'] }" >style='display:none'</c:if> data-dojo-type="sys/lbpmservice/mobile/common/LbpmFolder"
		 data-dojo-props="expandDomId:'moreActionView'">
		</div>
		<c:if test="${not empty param['isNew'] }">
			<div style="height: 20px"></div>
		</c:if>
		<c:if test="${param.viewName != 'none'}">
		    <%-- 底部操作栏分割线  --%>
		    <div class="optionsSplitLine"></div>
   			<%-- 引入流程操作的底部操作栏（流程图、暂存、提交）  --%>
			<c:import url="/sys/lbpmservice/mobile/import/edit_bottom_tabbar.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="${param.formName}" />
				<c:param name="onClickSubmitButton" value="${not empty param.onClickSubmitButton ? param.onClickSubmitButton : ''}" />
			</c:import>
		</c:if>
</div>
<%--自由流辅助用流程图 --%>
<div data-dojo-type="dojox/mobile/View" id="freeflowchart" style="display:none;">
	<div id="workflowInfoDiv" style="width:100%; -webkit-overflow-scrolling:touch; overflow:scroll;display:none;">
		<iframe scrolling="no" width="100%" id="WF_IFrame"></iframe>
	</div>
</div>
</c:if>
