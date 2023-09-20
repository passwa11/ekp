<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" />
<c:set var="lbpmProcessForm" value="${sysWfBusinessForm.sysWfBusinessForm.internalForm}" />
<link type="text/css" rel="stylesheet" href="<c:url value="/sys/lbpmservice/common/css/process_tab_main.css?s_cache=${LUI_Cache}"/>"/> 
<%@ include file="/sys/lbpmservice/include/sysLbpmProcess_script.jsp"%>

<input type="hidden" name="sysWfBusinessForm.fdParameterJson" value="" id="sysWfBusinessForm.fdParameterJson">
<input type="hidden" name="sysWfBusinessForm.fdAdditionsParameterJson" value="" id="sysWfBusinessForm.fdAdditionsParameterJson">
<input type="hidden" name="sysWfBusinessForm.fdIsModify" value='' id="sysWfBusinessForm.fdIsModify">
<html:hidden property="sysWfBusinessForm.fdCurHanderId"  styleId="sysWfBusinessForm.fdCurHanderId" />
<html:hidden property="sysWfBusinessForm.fdCurNodeSavedXML"  styleId="sysWfBusinessForm.fdCurNodeSavedXML" />
<html:hidden property="sysWfBusinessForm.fdFlowContent"  styleId="sysWfBusinessForm.fdFlowContent"  />
<html:hidden property="sysWfBusinessForm.fdProcessId"  styleId="sysWfBusinessForm.fdProcessId"  />
<html:hidden property="sysWfBusinessForm.fdKey"  styleId="sysWfBusinessForm.fdKey" />
<input type="hidden" name="sysWfBusinessForm.fdModelId" id="sysWfBusinessForm.fdModelId" value='<c:out value="${sysWfBusinessForm.fdId}" />' >
<input type="hidden" name="sysWfBusinessForm.fdModelName" id="sysWfBusinessForm.fdModelName" value='<c:out value="${sysWfBusinessForm.modelClass.name}" />' >
<html:hidden property="sysWfBusinessForm.fdCurNodeXML"  styleId="sysWfBusinessForm.fdCurNodeXML" />
<html:hidden property="sysWfBusinessForm.fdTemplateModelName"  styleId="sysWfBusinessForm.fdTemplateModelName"/>
<html:hidden property="sysWfBusinessForm.fdTemplateKey" styleId="sysWfBusinessForm.fdTemplateKey"/>
<input type="hidden" name="sysWfBusinessForm.canStartProcess" id="sysWfBusinessForm.canStartProcess" value='' >
<html:hidden property="sysWfBusinessForm.fdTranProcessXML" styleId="sysWfBusinessForm.fdTranProcessXML"/>
<html:hidden property="sysWfBusinessForm.fdDraftorId" styleId="sysWfBusinessForm.fdDraftorId"/>
<html:hidden property="sysWfBusinessForm.fdDraftorName" styleId="sysWfBusinessForm.fdDraftorName"/>
<html:hidden property="sysWfBusinessForm.fdHandlerRoleInfoIds"  styleId="sysWfBusinessForm.fdHandlerRoleInfoIds"/>
<html:hidden property="sysWfBusinessForm.fdHandlerRoleInfoNames"  styleId="sysWfBusinessForm.fdHandlerRoleInfoNames" />
<html:hidden property="sysWfBusinessForm.fdAuditNoteFdId"  styleId="sysWfBusinessForm.fdAuditNoteFdId" />
<html:hidden property="sysWfBusinessForm.fdIdentityId"  styleId="sysWfBusinessForm.fdIdentityId" />
<html:hidden property="sysWfBusinessForm.fdProcessStatus" styleId="sysWfBusinessForm.fdProcessStatus" />
<html:hidden property="sysWfBusinessForm.fdDefaultIdentity" styleId="sysWfBusinessForm.fdDefaultIdentity" />
<input type="hidden" id="sysWfBusinessForm.fdSubFormXML" name="sysWfBusinessForm.fdSubFormXML" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdSubFormXML}" />'>
<input type="hidden" id="__docStatus"   value='<c:out value="${sysWfBusinessForm.docStatus}" />' >
<input type="hidden" id="__method"   value='<c:out value="${param.method}" />' >
<%-- 动态加载操作--%>
<c:forEach items="${lbpmProcessForm.curHandlerOperationsReviewJs}" var="reviewJs" varStatus="vstatus">
	<c:import url="${reviewJs}" charEncoding="UTF-8" />
</c:forEach>
<c:if test="${sysWfBusinessForm.docStatus == null || sysWfBusinessForm.docStatus<'20'}">
	<!-- 起草人选择人工分支节点 -->
	<div id="manualBranchNodeRow" style="display:none">
		<div class="lui-lbpm-titleNode">
			<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.manualNodeSelect" />
		</div>
		<div class="lui-lbpm-detailNode" id="manualNodeSelectTD">
			
		</div>
	</div>
	<div id="operationMethodsRow">
		<div class="lui-lbpm-titleNode">
			<span class="txtstrong">*</span><bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationMethods" />
		</div>
		<div class="lui-lbpm-detailNode" id="operationMethodsGroup">
			
		</div>
	</div>
	<div id="handlerIdentityRow" style="display:none">
		<div class="lui-lbpm-titleNode">
			<bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.submitRole" />
		</div>
		<div class="lui-lbpm-detailNode">
			<!-- 删除onchange事件中的WorkFlow_GetFlowContent(this); 2009-09-01 by fuyx -->
			<select name="rolesSelectObj" key="handlerId" style="max-width:90%">
			</select>
			<span class="specialIcon"></span>
			<!--[if lte IE 9]>   
		        <style>
					#handlerIdentityRow .lui-lbpm-detailNode select{
					    padding-right: 0;
					}
				
					#handlerIdentityRow .lui-lbpm-detailNode .specialIcon {
					    display: inline;
					    background: none;
					}
		        </style>
			<![endif]-->
		</div>
	</div>
	<div id="nextNodeRow">
		<div class="lui-lbpm-titleNode" id="nextNodeTDTitle">
			<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.nextNode" />
		</div>
		<div class="lui-lbpm-detailNode" id="nextNodeTD">
			&nbsp;
		</div>
	</div>									
	<div id="freeSubFlowNodeRow" style="display:none">
		<div class="lui-lbpm-titleNode">
			<bean:message bundle="sys-lbpmservice" key="lbpm.group.freeSubFlow" />
		</div>
		<div class="lui-lbpm-detailNode" id="freeSubFlowNodeDIV">
			<ul id="freeSubFlowNodeUL">
			</ul>
		</div>
	</div>
</c:if>	
<c:if test="${sysWfBusinessForm.docStatus>='20' && sysWfBusinessForm.docStatus!='21' && sysWfBusinessForm.docStatus<'30'}">
	<div id="operationItemsRow">
		<div class="lui-lbpm-titleNode">
			<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationItems" />
		</div>
		<div class="lui-lbpm-detailNode">
			<select name="operationItemsSelect" onchange="lbpm.globals.operationItemsChanged(this);">
			</select>
		</div>
	</div>
	<div id="operationMethodsRow">
		<div class="lui-lbpm-titleNode">
			<span class="txtstrong">*</span><bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationMethods" />
		</div>
		<div class="lui-lbpm-detailNode">
			<div id="operationMethodsGroup"></div>
		</div>
	</div>
	<div id="operationsRow" style="display:none;" lbpmMark="operation">
		<div class="lui-lbpm-titleNode"  id="operationsTDTitle">
			&nbsp;
		</div>
		<div class="lui-lbpm-detailNode" id="operationsTDContent">
			&nbsp;
		</div>
	</div>
	<div id="operationsRow_Scope" style="display:none;" lbpmMark="operation">
		<div class="lui-lbpm-titleNode"  id="operationsTDTitle_Scope">
			&nbsp;
		</div>
		<div class="lui-lbpm-detailNode" id="operationsTDContent_Scope">
			&nbsp;
		</div>
	</div>
</c:if>
<c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != null && sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != ''}">
	<c:if test="${sysWfBusinessForm.docStatus == null || sysWfBusinessForm.docStatus<'30'}">
		<div id="checkChangeFlowTR">
			<div class="lui-lbpm-titleNode">
				<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.changeProcessor" />
			</div>
			<div class="lui-lbpm-detailNode">
				<label id="changeProcessorDIV" style="display:none;">
					<a href="javascript:;" class="com_btn_link" onclick="Com_EventPreventDefault();lbpm.globals.changeProcessorClick();">
						<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.changeProcessor.button" />
					</a>&nbsp;&nbsp;
				</label>
				
				<label id="modifyFlowDIV" style="display:none;">
					<a class="com_btn_link" href="javascript:lbpm.globals.modifyProcess('sysWfBusinessForm.fdFlowContent', 'sysWfBusinessForm.fdTranProcessXML');">
						<bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.modifyFlow" />
					</a>
				</label>
				
				<label id="modifyEmbeddedSubFlowDIV" style="display:none;">
					
				</label>
			</div>
		</div>
	</c:if>
</c:if>
<div class="lui-lbpm-fold-add">
<div id="descriptionRow">
	<div class="lui-lbpm-titleNode">
		<span id="mustSignStar" class="txtstrong" style="display:none">*</span><bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.opinion" />
	</div>
	<div class="lui-lbpm-detailNode">
		<table width=100% border=0 class="tb_noborder">
			<tr>
				<td id="fdUsageContentTd" width="85%">
					<span style="display: block;" class="lui-lbpm-opinion-outerBox">	
						<span id="fdUsageContentSpan" style="display: block;border: none;">
							<textarea name="fdUsageContent" class="process_review_content" placeholder="${ lfn:message('sys-lbpmservice:lbpmRight.opinion') }" style="height:100px;width:100%;resize:none;border-bottom: 0" key="auditNode" subject="${lfn:message('sys-lbpmservice:lbpmNode.createDraft.opinion')}" validate="fdUsageContentMaxLength(4000)"></textarea>
						</span>
						<div class="lui-lbpm-opinion-action">
	                    	<div class="lui-lbpm-opinion-action-box">
								<div class="commonUsedOpinion">
									<div class="lui-lbpm-advice">
		                          		<span><bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.commonUsages" /></span>
		                        		<i class="lui-lbpm-downIcon"></i>
				                        		<div class="commonUsedOpinionList">
				                            <ul></ul>
				                        	<div class="lui-lbpm-opinion-custormBtn" onclick="Com_EventPreventDefault();lbpm.globals.openDefiniateUsageWindow();">
				                        		<i class="lui-lbpm-opinion-custormBtn-add"></i>
				                        		<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.commonUsages.definite" />
				                        	</div>
				                        </div>
		                        	</div>
		                     	</div>
		                      <div class="lui-lbpm-opinion-otherFnc">
		                          <div title="<bean:message key="button.saveDraft" bundle="sys-lbpmservice" />" class="saveOpinion lui-lbpm-opinion-btn" id="saveDraftButton" onclick="lbpm.globals.saveDraftAction(this);">
		                            	<c:choose>
				                            <c:when test='<%="true".equals(SysFormDingUtil.getEnableDing()) || "true".equals(request.getParameter("ddpage"))%>'>
				                            	<i><div style="display: none" class="lui-lbpm-option-btn-text">暂存审批意见</div></i>
				                            </c:when>
				                            <c:otherwise>
				                            	<i></i>
				                            </c:otherwise>
				                         </c:choose>
		                          </div>
		                          <div class="lui-lbpm-opinion-more lui-lbpm-opinion-btn" style="display:none">
		                            <i></i>
		                            <div class="lui-lbpm-opinion-moreList">
		                              <ul></ul>
		                            </div>
		                          </div>
		                      </div>
	                      </div>
	                  </div>
					</span>
				</td>
			</tr>
			<tr>
				<td>
				<div id="nodeDescriptionDiv"  class="lui-lbpm-nodeHelp">
					<div class="lui-lbpm-titleNode">
						<bean:message key="lbpmNode.processingNode.changeProcessor.nodeHelpInfo" bundle="sys-lbpmservice" />
					</div>
					<div class="nodeDescription">
						<p>
							<label id="currentNodeDescription"></label>
						</p>
						<div id="extNodeDescriptionDiv">
							<c:import url="/sys/lbpm/flowchart/page/nodeDescription_ext_show.jsp" charEncoding="UTF-8">
								<c:param name="modelId" value="${sysWfBusinessForm.fdId}" />
								<c:param name="modelName" value="${param.modelName}" />
								<c:param name="formName" value="${param.formName}" />
								<c:param name="provideFor" value="pc" />
							</c:import>
						</div>
					</div>
					<div style="text-align: right;" id="lbpmNodeDescBtn">
						<span class="lui-lbpm-more lookMore"><bean:message bundle="sys-lbpmservice" key="lbpmRight.lookmore" /></span>
             			<span class="lui-lbpm-moreFold"><bean:message bundle="sys-lbpmservice" key="lbpmRight.close" /></span>
					</div>
				</div>
				</td>
			</tr>
		</table>
	</div>
</div>
<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_ext.jsp" charEncoding="UTF-8">
	<c:param name="auditNoteFdId" value="${sysWfBusinessForm.sysWfBusinessForm.fdAuditNoteFdId}" />
	<c:param name="modelName" value="${sysWfBusinessForm.modelClass.name}" />
	<c:param name="modelId" value="${sysWfBusinessForm.fdId}" />
	<c:param name="formName" value="${param.formName}" />
	<c:param name="curHanderId" value="${sysWfBusinessForm.sysWfBusinessForm.fdCurHanderId}"/>
	<c:param name="provideFor" value="pc" />
	<c:param name="approveModel" value="right" />
</c:import>
<div id="assignmentRow" style="display:none;">
	<div class="lui-lbpm-titleNode">
		<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.assignment" />
	</div>
	<div class="lui-lbpm-detailNode">
		<!-- Attachments -->
		<c:forEach items="${sysWfBusinessForm.sysWfBusinessForm.fdAttachmentsKeyList}" var="fdAttachmentsKey" varStatus="statusKey">
			<table class="tb_noborder" width="100%" id="${fdAttachmentsKey}" style="display:none;" name="attachmentTable">
				<tr>
					<td>
						<c:import
							url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp"
							charEncoding="UTF-8">
							<c:param
								name="fdKey"
								value="${fdAttachmentsKey}" />
							<c:param
								name="formBeanName"
								value="${param.formName}" />
							<c:param
								name="fdModelName"
								value="${sysWfBusinessForm.modelClass.name}"/>
							<c:param
								name="fdModelId"
								value="${sysWfBusinessForm.fdId}"/>
							 <c:param name="uploadAfterSelect" value="true" />
							 <c:param name="fdViewType" value="/sys/lbpmservice/support/lbpm_right/js/render_right.js" />
						</c:import>
					</td>
				</tr>
			</table>
		</c:forEach>
	</div>
</div>
<div class="lui-lbpm-fold">
<!--通知紧急程度 -->
<div id="notifyLevelRow">
	<div class="lui-lbpm-titleNode">
		<bean:message bundle="sys-notify" key="sysNotifyTodo.level.title" />
	</div>
	<div class="lui-lbpm-detailNode" id="notifyLevelTD">
		<c:if test="${sysWfBusinessForm.docStatus=='11'}">
		<nobr></nobr> 
		</c:if>
		<kmss:editNotifyLevel property="sysWfBusinessForm.fdNotifyLevel" value=""/>
	</div>
</div>
<!-- 通知选项 -->
<c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != null && sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != ''}">
	<input type="hidden" name="sysWfBusinessForm.fdSystemNotifyType" value="" id="sysWfBusinessForm.fdSystemNotifyType">
	<c:if test="${sysWfBusinessForm.docStatus == null || sysWfBusinessForm.docStatus<'20'}">
		<div id="notifyOptionTR">
			<div class="lui-lbpm-titleNode">
				<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.notifyOption" />
			</div>
			<div class="lui-lbpm-detailNode">
				<bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.notifyToDraft.message1" />
				&nbsp;<input type="text" class="inputSgl" style="width:25px;" id="dayOfNotifyDrafter" key="dayOfNotifyDrafter" validate="digits,min(0)" maxlength="3"><bean:message bundle="sys-lbpmservice" key="FlowChartObject.Lang.Node.day" />
				&nbsp;<input type="text" class="inputSgl" style="width:25px;" id="hourOfNotifyDrafter" key="hourOfNotifyDrafter" validate="digits,min(0)" maxlength="4"><bean:message bundle="sys-lbpmservice" key="FlowChartObject.Lang.Node.hour" />
				&nbsp;<input type="text" class="inputSgl" style="width:25px;" id="minuteOfNotifyDrafter" key="minuteOfNotifyDrafter" validate="digits,min(0)" maxlength="5"><bean:message bundle="sys-lbpmservice" key="FlowChartObject.Lang.Node.minute" />
				<bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.notifyToDraft.message2" /><br>
				<label class='lui-lbpm-checkbox' style="display:inline-block;margin-top:8px;"><input type="checkbox" id="notifyDraftOnFinish" value="true" alertText="" key="notifyOnFinish">
				<span class='checkbox-label'><bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.notifyToDraft.message3" /></span>
				</label>
				&nbsp;&nbsp;
				<label style="display:none;" class='lui-lbpm-checkbox'>
				<input type="checkbox" id="notifyForFollow" value="true" alertText="" key="notifyForFollow">
				<span class='checkbox-label'><bean:message key="lbpmFollow.button.follow" bundle="sys-lbpmservice-support" /></span>
				</label>
			</div>
		</div>
	</c:if>
	<c:if test="${sysWfBusinessForm.docStatus>='20' && sysWfBusinessForm.docStatus<'30'}">
		<div id="notifyOptionTR">
			<div class="lui-lbpm-titleNode">
				<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.notifyOption" />
			</div>
			<div class="lui-lbpm-detailNode">
				<label class='lui-lbpm-checkbox'>
				<input type="checkbox" id="notifyOnFinish" value="true" alertText="" key="notifyOnFinish">
				<span class='checkbox-label'><bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.notifyOption.message" /></span>
				</label>
				&nbsp;&nbsp;
				<label style="display:none;" class='lui-lbpm-checkbox'>
				<input type="checkbox" id="notifyForFollow" value="true" alertText="" key="notifyForFollow">
				<span class='checkbox-label'><bean:message key="lbpmFollow.button.follow" bundle="sys-lbpmservice-support" /></span>
				</label>
			</div>
		</div>
	</c:if>
</c:if>
<div id="fdFlowDescriptionRow">
	<div class="lui-lbpm-titleNode">
		<bean:message bundle="sys-lbpmservice" key="lbpmProcess.history.description" />
	</div>
	<div class="lui-lbpm-detailNode">
		<div id="fdFlowDescription"></div>
		<div style="text-align: right;">
			<span class="lui-lbpm-more lookMore"><bean:message bundle="sys-lbpmservice" key="lbpmRight.lookmore" /></span>
          	<span class="lui-lbpm-moreFold"><bean:message bundle="sys-lbpmservice" key="lbpmRight.close" /></span>
		</div>
	</div>
</div>
<c:if test="${sysWfBusinessForm.docStatus>='20' && sysWfBusinessForm.docStatus<'30'}">
	<!-- 当前流程状态 -->
	<div id="processStatusRow" style="display:none">
		<div class="lui-lbpm-titleNode">
			<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.processStatus" />
		</div>
		<div class="lui-lbpm-detailNode">
			<label id="processStatusLabel"></label>
		</div>
	</div>
	<div id="currentHandlersRow">
		<div class="lui-lbpm-titleNode">
			<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.currentProcessor" />
		</div>
		<div class="lui-lbpm-detailNode">
			<div id="currentHandlersLabel" style="word-break:break-all">
				<kmss:showWfPropertyValues idValue="${sysWfBusinessForm.fdId}" propertyName="handerNameDetail" />
			</div>
		</div>
	</div>			
	<div id="historyHandlersRow">
		<div class="lui-lbpm-titleNode">
			<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.finishProcessor" />
		</div>
		<div class="lui-lbpm-detailNode">
			<div id="historyHandlersLabel" style="word-break:break-all">
				<kmss:showWfPropertyValues idValue="${sysWfBusinessForm.fdId}" propertyName="historyHanderName" />
			</div>
		</div>
	</div>
</c:if>
</div>
</div>
<!-- 展开收起按钮 -->
<div class="lui-lbpm-foldOrUnfold">
    <div class="lui-lbpm-foldOrUnfold-box">
      <div>
        <span><bean:message bundle="sys-lbpmservice" key="lbpmRight.fold" /></span>
        <i></i>
      </div>
    </div>
</div>
<script>
if(lbpm){
	lbpm.approveType = "right";
}
seajs.use(['lui/util/onload'], function(onloadUtil) {
	LUI.ready(function(){ 
		onloadUtil.resetOnLoad(
				function(){
					$(".divselect").css({"margin":0});
				});
	});
}); 
</script>