<%@page import="com.landray.kmss.sys.lbpmext.businessauth.service.ILbpmExtBusinessSettingInfoService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
	ILbpmExtBusinessSettingInfoService lbpmExtBusinessSettingInfoService = (ILbpmExtBusinessSettingInfoService) SpringBeanUtil
			.getBean("lbpmExtBusinessSettingInfoService");
	String isOpinionTypeEnabled = lbpmExtBusinessSettingInfoService.getIsOpinionTypeEnabled("imissiveLbpmSwitch");
	request.setAttribute("isOpinionTypeEnabled", isOpinionTypeEnabled);
%>
<link type="text/css" rel="stylesheet" href="<c:url value="/sys/lbpmservice/common/css/process_tab_main.css?s_cache=${LUI_Cache}"/>"/> 
<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" />
<c:set var="lbpmProcessForm" value="${sysWfBusinessForm.sysWfBusinessForm.internalForm}" />

<%@ include file="/sys/lbpmservice/include/sysLbpmProcess_script.jsp"%> 
<!--引入暂存按钮-->
<c:import url="/sys/lbpmservice/import/sysLbpmProcess_saveDraft.jsp" charEncoding="UTF-8"> 
	<c:param name="formName" value="${param.formName}">
	</c:param>
</c:import>
<!--引入切换表单按钮-->
<%@ include file="/sys/lbpmservice/import/sysLbpmProcess_subform.jsp"%>
		<c:if test="${requestScope.sysWfProcess_useActionView != 'true'}">
		<c:if test="${empty param.onClickSubmitButton}">
		<form name="sysWfProcessForm" method="POST"
				action="<c:url value="/sys/lbpmservice/support/lbpm_process/lbpmProcess.do" />">
		</c:if>
		</c:if>
		<input type="hidden" id="sysWfBusinessForm.fdParameterJson" name="sysWfBusinessForm.fdParameterJson" value='' >
		<input type="hidden" id="sysWfBusinessForm.fdAdditionsParameterJson" name="sysWfBusinessForm.fdAdditionsParameterJson" value='' >
		<input type="hidden" id="sysWfBusinessForm.fdIsModify" name="sysWfBusinessForm.fdIsModify" value='' >
		<input type="hidden" id="sysWfBusinessForm.fdCurHanderId" name="sysWfBusinessForm.fdCurHanderId" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdCurHanderId}" />' >
		<input type="hidden" id="sysWfBusinessForm.fdCurNodeSavedXML" name="sysWfBusinessForm.fdCurNodeSavedXML" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdCurNodeSavedXML}" />' >
		<input type="hidden" id="sysWfBusinessForm.fdFlowContent" name="sysWfBusinessForm.fdFlowContent" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdFlowContent}" />' >
		<input type="hidden" id="sysWfBusinessForm.fdProcessId" name="sysWfBusinessForm.fdProcessId" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdProcessId}" />'>
		<input type="hidden" id="sysWfBusinessForm.fdKey" name="sysWfBusinessForm.fdKey" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdKey}" />' >
		<input type="hidden" id="sysWfBusinessForm.fdModelId" name="sysWfBusinessForm.fdModelId" value='<c:out value="${sysWfBusinessForm.fdId}" />' >
		<input type="hidden" id="sysWfBusinessForm.fdModelName" name="sysWfBusinessForm.fdModelName" value='<c:out value="${sysWfBusinessForm.modelClass.name}" />' >
		<input type="hidden" id="sysWfBusinessForm.fdCurNodeXML" name="sysWfBusinessForm.fdCurNodeXML" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdCurNodeXML}" />' >
		<input type="hidden" id="sysWfBusinessForm.fdTemplateModelName" name="sysWfBusinessForm.fdTemplateModelName" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdTemplateModelName}" />' >
		<input type="hidden" id="sysWfBusinessForm.fdTemplateKey" name="sysWfBusinessForm.fdTemplateKey" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdTemplateKey}" />' >
		<input type="hidden" id="sysWfBusinessForm.canStartProcess" name="sysWfBusinessForm.canStartProcess" value='' >
		<input type="hidden" id="sysWfBusinessForm.fdTranProcessXML" name="sysWfBusinessForm.fdTranProcessXML" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdTranProcessXML}" />' >
		<input type="hidden" id="sysWfBusinessForm.fdDraftorId" name="sysWfBusinessForm.fdDraftorId" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdDraftorId}" />' >
		<input type="hidden" id="sysWfBusinessForm.fdDraftorName" name="sysWfBusinessForm.fdDraftorName" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdDraftorName}" />' >
		<input type="hidden" id="sysWfBusinessForm.fdHandlerRoleInfoIds" name="sysWfBusinessForm.fdHandlerRoleInfoIds" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdHandlerRoleInfoIds}" />' >
		<input type="hidden" id="sysWfBusinessForm.fdHandlerRoleInfoNames" name="sysWfBusinessForm.fdHandlerRoleInfoNames" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdHandlerRoleInfoNames}" />' >
		<input type="hidden" id="sysWfBusinessForm.fdAuditNoteFdId" name="sysWfBusinessForm.fdAuditNoteFdId" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdAuditNoteFdId}" />' >
		<input type="hidden" id="docStatus" name="docStatus" value='<c:out value="${sysWfBusinessForm.docStatus}" />' >
		<input type="hidden" id="sysWfBusinessForm.fdIdentityId" name="sysWfBusinessForm.fdIdentityId" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdIdentityId}" />' >
		<input type="hidden" id="sysWfBusinessForm.fdProcessStatus" name="sysWfBusinessForm.fdProcessStatus" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdProcessStatus}" />' >
		<input type="hidden" id="sysWfBusinessFormPrefix" name="sysWfBusinessFormPrefix" value='<c:out value="${sysWfBusinessFormPrefix}" />' >
		<input type="hidden" id="sysWfBusinessForm.fdSubFormXML" name="sysWfBusinessForm.fdSubFormXML" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdSubFormXML}" />'>
		<!-- 留下其他信息处理的域，为了兼容特权人修改流程图信息时保存其他设计的信息 -->
		<input type="hidden" id="sysWfBusinessForm.fdOtherContentInfo" name="sysWfBusinessForm.fdOtherContentInfo" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdOtherContentInfo}" />'>
		<script>
		lbpm.onLoadEvents.once.push(function(){
			$("li[name='process_head_tab']").click(function(){
				$("li[name='process_head_tab']").attr("class","");
				$(this).attr("class","active");	
				
				//兼容有些模块下无法触发onresize事件的问题
				var isClick=$(this).attr("data-isClick");
				var dataLoad=$(this).attr("data-load");
				if(!isClick){
					$(this).attr("data-isClick","true");
					if(dataLoad){
						lbpm[dataLoad]();
					}
				}
				$("div[name='process_body']").attr("class","process_body_checked_false");
				var lis=$(this).parent().children();
				for(var i=0;i<lis.length;i++){
					var classValue=$(lis[i]).attr("class");
					if(classValue=="active"){
						var process_bodys=$("div[name='process_body']");
						$(process_bodys[i]).attr("class","process_body_checked_true");												
					}
				}
			});
		});	
		</script>
		
		<!--begin 选项卡头部 -->
	    <div class="lui_flowstate_tab_heading">
	        <ul class="lui_flowstate_tabhead">
	          <li name="process_head_tab" class="active"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processHandle') }</a></li>
	          <li name="process_head_tab" data-load="process_status_load_Frame"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processStatus') }</a></li>
	          <li name="process_head_tab" data-load="flow_chart_load_Frame"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processChart') }</a></li>
			  <li name="process_head_tab" data-load="flow_table_load_Frame"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processTable') }</a></li>
			  <li name="process_head_tab" data-load="flow_log_load_Frame"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processLog') }</a></li>
	        </ul>
	    </div>
	    <!--end 选项卡头部 -->
	    
	      <!--begin 流程处理  -->
	    <div name="process_body" class="process_body_checked_true">
	    	<table class="tb_normal process_review_panel" width="100%">
			<%--#152384-参数获取文件路径直接import输出导致安全问题-开始
			<c:if test="${not empty param.historyPrePage}">
				<c:import url="${param.historyPrePage}" charEncoding="UTF-8"/>
			</c:if>
			#152384-参数获取文件路径直接import输出导致安全问题-结束--%>
			<!--update by wubing date 2016-03-17,开放驳回到起草时，起草人可以进入处理-->
			<c:choose>
			<c:when test="${(sysWfBusinessForm.docStatus>='20' && sysWfBusinessForm.docStatus<'30') || sysWfBusinessForm.docStatus == '11' || sysWfBusinessForm.docStatus == '10'}">
				<c:if test="${requestScope.sysWfProcess_useActionView != 'true'}">
				<tr class="tr_normal_title" id="extendRoleOptRow">
					<td align="right" colspan="4">
					<!-- 流程跟踪 -->
					<a href="javascript:void(0);" id="followOptButton" class="com_btn_link" style="display:none; margin: 0 10px 0 0;">
						<bean:message key="lbpmFollow.button.follow" bundle="sys-lbpmservice-support" />
					</a>
					<!-- 取消跟踪 -->
					<a href="javascript:void(0);" id="cancelFollowOptButton" class="com_btn_link" style="display:none; margin: 0 10px 0 0;">
						<bean:message key="lbpmFollow.button.cancelFollow" bundle="sys-lbpmservice-support" />
					</a>
						<c:if test="${sysWfBusinessForm.docStatus!='21'}">
						
						<span id="historyhandlerOpt" style="display:none;"></span>
						<a href="javascript:void(0);" id="drafterOptButton" class="com_btn_link" style="display:none; margin: 0 10px 0 0;"
							onclick="lbpm.globals.openExtendRoleOptWindow('drafter');">
							<bean:message key="lbpmNode.processingNode.identifyRole.button.drafter" bundle="sys-lbpmservice" />
						</a>
						</c:if>
						
						<c:if test="${sysWfBusinessForm.docStatus!='10'}"><!--//fix #27954 起草人将流程撤回到起草节点后，特权人进入流程，不应该出现特权操作-->
						<a href="javascript:void(0);" id="authorityOptButton" class="com_btn_link" style="display:none; margin: 0 10px 0 0;"
							onclick="lbpm.globals.openExtendRoleOptWindow('authority');">
							<bean:message key="lbpmNode.processingNode.identifyRole.button.authority" bundle="sys-lbpmservice" />
						</a>
						</c:if>
						
						<c:if test="${sysWfBusinessForm.docStatus=='20'}">
						<a href="javascript:void(0);" id="branchAdminOptButton" class="com_btn_link" style="display:none; margin: 0 10px 0 0;"
							onclick="lbpm.globals.openExtendRoleOptWindow('branchadmin');">
							<bean:message key="lbpmNode.processingNode.identifyRole.button.branchadmin" bundle="sys-lbpmservice"/>
						</a>
						</c:if>
					</td>
				</tr>
				</c:if>
			</c:when>
			<c:otherwise>
			<tr class="tr_normal_title" id="followOptRow">
				<td align="right" colspan="4">
					<!-- 流程跟踪 -->
					<a href="javascript:void(0);" id="followOptButton" class="com_btn_link" style="display:none; margin: 0 10px 0 0;">
						<bean:message key="lbpmFollow.button.follow" bundle="sys-lbpmservice-support" />
					</a>
					<!-- 取消跟踪 -->
					<a href="javascript:void(0);" id="cancelFollowOptButton" class="com_btn_link" style="display:none; margin: 0 10px 0 0;">
						<bean:message key="lbpmFollow.button.cancelFollow" bundle="sys-lbpmservice-support" />
					</a>
				</td>
			</tr>
			</c:otherwise>
			</c:choose>
			<tr id="freeflowRow">
				<td class="td_normal_title" width="15%">
					<bean:message bundle="sys-lbpmservice" key="lbpm.freeFlow.flow" />
				</td>
				<td colspan=3>
				<c:if test="${sysWfBusinessForm.docStatus!='10'}">
				<div id="editFreeFlowDIV" style="display:none;">
					<input type="hidden" name="handlerIds_imissive">
					<input type="hidden" name="handlerNames_imissive">
					<div id="freeflow_appendNodeDIV">
						<div class="lbpm_freeflow_appendNode lbpm_freeflow_reviewNode" onclick="lbpm.globals.beforeAddNodeInFreeFlow('reviewNode');"><i></i><bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.review" /></div>
						<div class="lbpm_freeflow_appendNode lbpm_freeflow_signNode" onclick="lbpm.globals.beforeAddNodeInFreeFlow('signNode');"><i></i><bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.sign" /></div>
						<div class="lbpm_freeflow_appendNode lbpm_freeflow_sendNode" onclick="lbpm.globals.beforeAddNodeInFreeFlow('sendNode');"><i></i><bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.send" /></div>
					</div>
					<div class="lbpm_freeflow_modifyFlowContent">
						<a class="com_btn_link" href="javascript:lbpm.globals.modifyFreeFlow('sysWfBusinessForm.fdFlowContent', 'sysWfBusinessForm.fdTranProcessXML');">
							<bean:message bundle="sys-lbpmservice" key="lbpm.freeFlow.modifyFlowContent" />
						</a>
					</div>
				</div>
				<div id="viewFreeFlowDIV" style="display:none">
					<a class="com_btn_link" href="javascript:lbpm.globals.viewFreeFlow('sysWfBusinessForm.fdFlowContent', 'sysWfBusinessForm.fdTranProcessXML');">
						<bean:message bundle="sys-lbpmservice" key="lbpm.freeFlow.viewFlowContent" />
					</a>
				</div>
				<div id="flowNodeDIV" style="display:none">
					<ul id="flowNodeUL" class="lbpm_freeflow_draglist">
					</ul>
					<ul class="lbpm_freeflow_add_moreList">
						<li data-node-type="reviewNode"><bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.reviewNode" /></li>
						<li data-node-type="signNode"><bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.signNode" /></li>
						<li data-node-type="sendNode"><bean:message bundle="sys-lbpmservice" key="lbpmservice.freeflow.sendNode" /></li>
					</ul>
				</div>
				</c:if>
				<c:if test="${sysWfBusinessForm.docStatus=='10'}">
				<div id="editFreeFlowDIV" style="display:none;">
					<a class="com_btn_link" href="javascript:lbpm.globals.viewFreeFlow('sysWfBusinessForm.fdFlowContent', 'sysWfBusinessForm.fdTranProcessXML');">
						<bean:message bundle="sys-lbpmservice" key="lbpm.freeFlow.viewFlowContent" />
					</a>
				</div>
				<div id="viewFreeFlowDIV" style="display:none">
					<a class="com_btn_link" href="javascript:lbpm.globals.viewFreeFlow('sysWfBusinessForm.fdFlowContent', 'sysWfBusinessForm.fdTranProcessXML');">
						<bean:message bundle="sys-lbpmservice" key="lbpm.freeFlow.viewFlowContent" />
					</a>
				</div>
				</c:if>
				</td>
			</tr>
			<%--#148641-流程说明开关开启，若流程说明为空，隐藏当前行，如果流程说明不为空，显示当前行-开始--%>
			<tr id="tr_process_description">
				<td class="td_normal_title" width="15%">
					<bean:message bundle="sys-lbpmservice" key="lbpmProcess.history.description"/>
				</td>
				<td colspan="3">
					<span id="fdFlowDescription"></span>
				</td>
			</tr>
			<%--#148641-流程说明开关开启，若流程说明为空，隐藏当前行，如果流程说明不为空，显示当前行-结束--%>
			<c:if test="${not empty sysWfBusinessForm.docStatus}">
			<tr class="tr_normal_title">
				<td align="left" colspan="4">
					<label><input type="checkbox" value="true" checked onclick="lbpm.globals.showHistoryDisplay(this);">
					<bean:message bundle="sys-lbpmservice" key="lbpmProcess.history.description.show" /></label>
				</td>
			</tr>
			<tr id="historyTableTR">
				<td colspan="4" id="historyInfoTableTD" ${resize_prefix}onresize="lbpm.load_Frame();" style="padding: 0;">
					<iframe id="historyInfoTableIframe" width="100%" style="margin-bottom: -3px;border: none;" scrolling="no" FRAMEBORDER=0></iframe>
				</td>
			</tr>
			</c:if>
			<c:if test="${(sysWfBusinessForm.docStatus>='20' && sysWfBusinessForm.docStatus<'30') || sysWfBusinessForm.docStatus == '11'}">
				<c:if test="${requestScope.sysWfProcess_useActionView != 'true'}"><%-- start action --%>
				<tr id="operationItemsRow">
					<td class="td_normal_title" width="15%">
						<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationItems" />
					</td>
					<td colspan="3">
						<select name="operationItemsSelect" onchange="lbpm.globals.operationItemsChanged(this);">
						</select>
					</td>
				</tr>
				<%-- 起草人选择人工分支节点 --%>
				<c:if test="${sysWfBusinessForm.docStatus == null || sysWfBusinessForm.docStatus<'20'}">
					<tr id="manualBranchNodeRow" style="display:none">
						<td class="td_normal_title" width="15%">
							<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.manualNodeSelect" />
						</td>
						<td colspan="3" id="manualNodeSelectTD">
	
						</td>
					</tr>
				</c:if>
				<tr id="operationMethodsRow">
					<td class="td_normal_title" width="15%">
						<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationMethods" />
					</td>
					<td colspan="3">
					<div id="operationMethodsGroup" style="float: left; margin-right: 10px;"></div>
					</td>
				</tr>
				<%-- 动态加载操作--%>
				<c:forEach items="${lbpmProcessForm.curHandlerOperationsReviewJs}" var="reviewJs" varStatus="vstatus">
					<c:if test="${!(fn:contains(syslbpmOperation_jspArray, reviewJs)) || compressSwitch eq 'false'}">
						<c:import url="${reviewJs}" charEncoding="UTF-8" />
					</c:if>
				</c:forEach>

				<!--通知紧急程度 -->
				<tr id="notifyLevelRow">
					<td class="td_normal_title"  width="15%">
						<bean:message bundle="sys-notify" key="sysNotifyTodo.level.title" />
					</td>
					<td colspan="3" id="notifyLevelTD">
						<c:if test="${sysWfBusinessForm.docStatus=='11'}">
						<!--文档驳回后，起草人的重新提交页面 紧急度和提交选项 请左对齐-->
						<nobr></nobr> 
						</c:if>
						<kmss:editNotifyLevel property="sysWfBusinessForm.fdNotifyLevel" value=""/> 
					</td>
				</tr>									
				<%-- 用于起草节点 ,显示即将流向--%>
				<c:if test="${sysWfBusinessForm.docStatus == null || sysWfBusinessForm.docStatus<'20'}">
					<tr style="display:none;">
						<td id="nextNodeTDTitle" class="td_normal_title" width="15%">
							<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.nextNode" />
						</td>
						<td colspan="3" id="nextNodeTD">
							&nbsp;
						</td>
					</tr>
				</c:if>
				<div id="operationsRow_Type" style="display:none;" lbpmMark="operation">
					<div class="lui-lbpm-titleNode" id="operationsTDTitle_Type">
						&nbsp;
					</div>
					<div class="lui-lbpm-detailNode" id="operationsTDContent_Type">
						&nbsp;
					</div>
				</div>
				<tr id="operationsRow" style="display:none;" lbpmMark="operation">
					<td id="operationsTDTitle" class="td_normal_title" width="15%">
						&nbsp;
					</td>
					<td id="operationsTDContent" colspan="3">
						&nbsp;
					</td>
				</tr>
				<tr id="operationsRow_Scope" style="display:none;" lbpmMark="operation">
					<td id="operationsTDTitle_Scope" class="td_normal_title" width="15%">
						&nbsp;
					</td>
					<td id="operationsTDContent_Scope" colspan="3">
						&nbsp;
					</td>
				</tr>
				<c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != null && sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != ''}">
				<%-- <tr id="checkChangeFlowTR" style="display:none;">
					<td class="td_normal_title" width="15%">
						<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.changeProcessor" />
					</td>
					<td colspan="3">
						<label id="changeProcessorDIV" style="display:none;">
							<a href="javascript:;" class="com_btn_link" onclick="Com_EventPreventDefault();lbpm.globals.changeProcessorClick();">
								<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.changeProcessor.button" />
							</a>&nbsp;&nbsp;
						</label>
						<label id="modifyFlowDIV" style="display:none;">
							<a class="com_btn_link" href="javascript:lbpm.globals.modifyProcess('sysWfBusinessForm.fdFlowContent','sysWfBusinessForm.fdTranProcessXML');">
								<bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.modifyFlow" />
							</a>
						</label>
					</td>
				</tr> --%>
				</c:if>
				<tr id="descriptionRow">
					<td class="td_normal_title" width="15%">
						<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.opinion" />
					</td>
					<td colspan="3">
						<table width=100% border=0 class="tb_noborder" id="fdUsageContentTb">
							<tr>
								<td id="optionButtons" colspan="2">
									<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.commonUsages" />:&nbsp;&nbsp;
									<select name="commonUsages" onchange="lbpm.globals.setUsages(this);" style="width: 120px; overflow-x: hidden">
										<option value="">
											<bean:message key="page.firstOption" />
										</option>
									</select>
									<a href="javascript:;" class="com_btn_link" style="margin: 0 10px;" onclick="Com_EventPreventDefault();lbpm.globals.openDefiniateUsageWindow();">
										<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.commonUsages.definite" />
									</a>
									<c:if test="${sysWfBusinessForm.docStatus>='20'}">
										<a href="javascript:;" class="com_btn_link" id="saveDraftButton" onclick="lbpm.globals.saveDraftAction(this);">
											<bean:message key="button.saveDraft" bundle="sys-lbpmservice" />
										</a>
									</c:if>
									<input type="hidden" name="noticeHandlerIds" id="noticeHandlerIds">
									<a href="javascript:;" class="com_btn_link" style="margin-left:10px;" onclick="lbpm.globals.selectHistoryHandlers(this,'fdUsageContent');"><bean:message bundle="sys-lbpmservice" key="lbpm.process.noticeHandler.name" /></a><span style='display:none'>(<span id="noticeHandlerNum">0</span>/<span id="noticeHandlerTotal">0</span>)</span>
								</td>
							</tr>
							<tr>
								<td id="fdUsageContentTd" width="85%">
									<span id="fdUsageContentSpan" style="display: block;position: relative;">
										<textarea name="fdUsageContent" class="process_review_content" style="width:98%;padding: 0;" key="auditNode" subject="${lfn:message('sys-lbpmservice:lbpmNode.createDraft.opinion')}" validate="fdUsageContentMaxLength(4000)"></textarea>
										<span id="mustSignStar" class="txtstrong" style="margin-top:62px;display:none;position:absolute;">*</span>
									</span>
									<c:if test="${isOpinionTypeEnabled eq 'true'}">
										<span id="opinionConfig">
											<label>
												<input type="checkbox" id="notifyIsScript" checked="checked" name="sysWfBusinessForm.notifyIsScript">
												是否显示在稿纸
											</label>
											<label>
												<xform:select property="sysWfBusinessForm.approveOpinionType" showStatus="edit" subject="默认意见类型"  htmlElementProperties="id='approveOpinionType'">
													<xform:customizeDataSource className="com.landray.kmss.sys.lbpmservice.support.service.spring.LbpmAuditNoteTypeDataSource"></xform:customizeDataSource>
												</xform:select>
											</label>
										</span>
									</c:if>
							
								</td>
								<td width="15%">
									<input id="process_review_button" class="process_review_button" type=button value="<bean:message key="button.submit"/>"
										onclick="<%String onClickSubmitButton = org.apache.commons.lang.StringEscapeUtils.escapeHtml(request.getParameter("onClickSubmitButton"));
										if (onClickSubmitButton == null || onClickSubmitButton.length() == 0) {
											out.print("Com_Submit(document.sysWfProcessForm, 'update');");
										} else {
											out.print(onClickSubmitButton);
										}%>"/>
										
									<script>
									lbpm.onLoadEvents.once.push(function() {
										if (document.forms == null || document.forms.length < 1 || window.$GetFormValidation == null) {
											return;
										}
										var validation = $GetFormValidation(document.forms[0]);
										if (validation) {
											validation.options.beforeFormValidate= function() {
												return (lbpm.currentOperationType == null
														|| (lbpm.operations[lbpm.currentOperationType] && lbpm.operations[lbpm.currentOperationType].isPassType)
												);
											};
										}
									});
									</script>
								</td>
							</tr>
							<tr id="privateOpinionTr">
								<td colspan="2">
									<table style="width: 100%;margin-top: 10px;">
										<tr>
											<td width="80px" style="height: 26px;">
												<label><input type="checkbox" name="privateOpinion" onclick="lbpm.globals.setPrivateOpinion(this);"/><kmss:message key="sys-lbpmservice:lbpmservice.auditnote.privateOpinion" /></label>
											</td>
											<td>
												<div id="privateOpinionCanViewTr" style="display: none;">
													<input type="hidden" name="privateOpinionCanViewIds" />
													<input type="text" name="privateOpinionCanViewNames" subject="${ lfn:message('sys-lbpmservice:lbpmservice.auditnote.canViewPerson') }" placeholder="${ lfn:message('sys-lbpmservice:lbpmservice.auditnote.placeholder') }" style="height:25px;border:0px;border-bottom:1px solid #b4b4b4;width:85%" onclick="Dialog_Address(true,'privateOpinionCanViewIds','privateOpinionCanViewNames', ';',ORG_TYPE_ALL);" readonly />
													<a href="javascript:;" class="com_btn_link" onclick="Dialog_Address(true,'privateOpinionCanViewIds','privateOpinionCanViewNames', ';',ORG_TYPE_ALL);"><bean:message key="dialog.selectOther" /></a>
													<span class="txtstrong">*</span>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2"><label id="currentNodeDescription"></label></td>
							</tr>
						</table>
					</td>
				</tr>
				<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_ext.jsp" charEncoding="UTF-8">
					<c:param name="auditNoteFdId" value="${sysWfBusinessForm.sysWfBusinessForm.fdAuditNoteFdId}" />
					<c:param name="modelName" value="${sysWfBusinessForm.modelClass.name}" />
					<c:param name="modelId" value="${sysWfBusinessForm.fdId}" />
					<c:param name="formName" value="${param.formName}" />
					<c:param name="curHanderId" value="${sysWfBusinessForm.sysWfBusinessForm.fdCurHanderId}"/>
					<c:param name="provideFor" value="pc" />
				</c:import>
				<%-- 通知选项--%>
				<c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != null && sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != ''}">
				<input type="hidden" name="sysWfBusinessForm.fdSystemNotifyType" value="" id="sysWfBusinessForm.fdSystemNotifyType">
				<tr id="notifyOptionTR">
					<td class="td_normal_title" width="15%">
						<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.notifyOption" />
					</td>
					<td colspan="3">
						<label id="notifyOnFinishLabel">
						<input type="checkbox" id="notifyOnFinish" value="true" alertText="" key="notifyOnFinish">
						<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.notifyOption.message" />
						</label>
						&nbsp;&nbsp;
						<label id="notifyForFollowLabel">
						<input type="checkbox" id="notifyForFollow" value="true" alertText="" key="notifyForFollow">
						<bean:message key="lbpmFollow.button.follow" bundle="sys-lbpmservice-support" />
						</label>
					</td>
				</tr>
				</c:if>
				<c:import url="/sys/lbpmservice/common/process_ext.jsp" charEncoding="UTF-8">
					<c:param name="modelName" value="${sysWfBusinessForm.modelClass.name}" />
					<c:param name="modelId" value="${sysWfBusinessForm.fdId}" />
					<c:param name="formName" value="${param.formName}" />
				</c:import>
				<tr id="assignmentRow" style="display:none;">
					<td class="td_normal_title" width="15%">
						<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.assignment" />
					</td>
					<td colspan="3">
						<!-- Attachments -->
						<table class="tb_noborder" width="100%">
							<tr>
								<td>
									<c:import
										url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp"
										charEncoding="UTF-8">
										<c:param
											name="fdKey"
											value="${sysWfBusinessForm.sysWfBusinessForm.fdAuditNoteFdId}" />
										<c:param
											name="formBeanName"
											value="${param.formName}"/>
										<c:param
											name="fdModelName"
											value="${sysWfBusinessForm.modelClass.name}"/>
										<c:param
											name="fdModelId"
											value="${sysWfBusinessForm.fdId}"/>
									</c:import>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				</c:if><%-- end action --%>
				<%-- 当前流程状态 --%>
				<tr id="processStatusRow" style="display:none">
					<td class="td_normal_title" width="15%">
						<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.processStatus" />
					</td>
					<td colspan="3">
						<label id="processStatusLabel"></label>
					</td>
				</tr>
				<tr id="currentHandlersRow">
					<td class="td_normal_title" width="15%">
						<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.currentProcessor" />
					</td>
					<td colspan="3">
						<div id="currentHandlersLabel" style="word-break:break-all">
							<kmss:showWfPropertyValues idValue="${sysWfBusinessForm.fdId}" propertyName="handerNameDetail" />
						</div>
					</td>
				</tr>
				<tr id="historyHandlersRow">
					<td class="td_normal_title" width="15%">
						<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.finishProcessor" />
					</td>
					<td colspan="3">
						<div id="historyHandlersLabel" style="word-break:break-all">
							<kmss:showWfPropertyValues idValue="${sysWfBusinessForm.fdId}" propertyName="historyHanderName" />
						</div>
					</td>
				</tr>
			</c:if>
			<xform:isExistRelationProcesses relationType="all">
			<tr class="tr_normal_title">
				<td align="left" colspan="4">
					<label><input type="checkbox" id="relationProcessCheckBox" value="true">
					<bean:message bundle="sys-lbpmservice" key="lbpm.tab.relationProcesses" /></label>
				</td>
			</tr>
			<xform:isExistRelationProcesses relationType="parent">
					<tr id="parentFlowTR" style="display:none">
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-lbpmservice" key="label.parentFlow" />
						</td>
						<td colspan=3>
							<xform:showParentProcesse />
						</td>
					</tr>
			</xform:isExistRelationProcesses>
			<xform:isExistRelationProcesses relationType="subs">
					<tr id="subFlowTR" style="display:none">
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-lbpmservice" key="label.subFlow" />
						</td>
						<td colspan=3>
							<xform:showSubProcesses />
						</td>
					</tr>
			</xform:isExistRelationProcesses>
			</xform:isExistRelationProcesses>
				<%--#159742 自由流的流程表格与流程日志 收缩在更多中了，建议挪上去--%>
<%--			<tr class="tr_normal_title">--%>
<%--				<td align="left" colspan="4">--%>
<%--					<label><input type="checkbox" value="true" onclick="lbpm.globals.showDetails(checked);">--%>
<%--					<bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.details" /></label>--%>
<%--				</td>--%>
<%--			</tr>--%>
<%--			<tr id="showDetails">--%>
<%--				<td colspan="4">--%>
<%--					<table id="Label_Tabel_Workflow_Info" width=100%>--%>
<%--						<c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != null && sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != ''}">--%>
<%--						<tr id="lbpm_highLevelTab" LKS_LabelName="<bean:message bundle="sys-lbpmservice" key="label.highLevel" />" style="display:none">--%>
<%--							<td>--%>
<%--								<table class="tb_normal" width=100%>--%>
<%--									<tr id="nodeCanViewCurNodeTR" style="display:none;">--%>
<%--										<td class="td_normal_title" width="15%"><bean:message bundle="sys-lbpmservice" key="lbpmSupport.curNodeCanViewCurNode" /></td>--%>
<%--										<td>--%>
<%--											<input type="hidden" name="wf_nodeCanViewCurNodeIds">--%>
<%--											<textarea name="wf_nodeCanViewCurNodeNames" style="width:85%" readonly></textarea>--%>
<%--											<a href="javascript:;" class="com_btn_link" onclick="lbpm.globals.selectNotionNodes();"><bean:message key="dialog.selectOther" /></a>--%>
<%--										</td>--%>
<%--									</tr>--%>
<%--									<tr id="otherCanViewCurNodeTR" style="display:none;">--%>
<%--										<td class="td_normal_title" width="15%"><bean:message bundle="sys-lbpmservice" key="lbpmSupport.curNodeotherCanViewCurNode" /></td>--%>
<%--										<td>--%>
<%--											<input type="hidden" name="wf_otherCanViewCurNodeIds">--%>
<%--											<textarea name="wf_otherCanViewCurNodeNames" style="width:85%" readonly></textarea>--%>
<%--											<a href="javascript:;" class="com_btn_link" onclick="Dialog_Address(true,'wf_otherCanViewCurNodeIds','wf_otherCanViewCurNodeNames', ';',ORG_TYPE_ALL,function myFunc(rtv){lbpm.globals.updateXml(rtv,'otherCanViewCurNode');});"><bean:message key="dialog.selectOther" /></a>--%>
<%--										</td>--%>
<%--									</tr>--%>
<%--								</table>	--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						<tr LKS_LabelName="<bean:message bundle="sys-lbpmservice" key="lbpm.tab.table" />" style="display:none">--%>
<%--							<td id="workflowTableTD" ${resize_prefix}onresize="lbpm.flow_table_load_Frame();">--%>
<%--								<iframe width="100%" height="100%" scrolling="no" id="${sysWfBusinessFormPrefix}WF_TableIFrame" FRAMEBORDER=0></iframe>--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--						</c:if>--%>
<%--						<tr LKS_LabelName="<bean:message bundle="sys-lbpmservice" key="label.flowLog" />" style="display:none">--%>
<%--							<td  id="flowLogTableTD" ${resize_prefix}onresize="lbpm.flow_log_load_Frame();">--%>
<%--								<iframe width="100%" height="100%" scrolling="no" FRAMEBORDER=0></iframe>--%>
<%--							</td>--%>
<%--						</tr>--%>
<%--					</table>--%>
<%--				</td>--%>
<%--			</tr>--%>
		</table>
	    </div>
	    <!--end 流程处理  -->
		
		  <!-- begin流程状态 -->
	    <div name="process_body" class="process_body_checked_false">
	     <table width=100%>
	     	<tr>
				<td  id="processStatusTD" ${resize_prefix}onresize="lbpm.process_status_load_Frame();">
					<iframe width="100%" height="100%" scrolling="no" FRAMEBORDER=0></iframe>
				</td>
			</tr>
	     </table>
	    </div>
	    <!-- end流程状态 -->
	    
	    <!-- begin流程图 -->
	    <div name="process_body" >
	     <table class="tb_normal process_review_panel" width="100%">
	     		
				<c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != null && sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != ''}">
				<tr class="tr_normal_title" style="display:none">
					<td align="left" colspan="4">
						<label><input type="checkbox" id="flowGraphicShowCheckbox" value="true" onclick="this.checked ? $('#flowGraphic').show() : $('#flowGraphic').hide();">
						<bean:message bundle="sys-lbpmservice" key="lbpm.tab.graphic" /></label>
					</td>
				</tr>
				<tr id="flowGraphic">
					<td id="workflowInfoTD" ${resize_prefix}onresize="lbpm.flow_chart_load_Frame();" colspan="4">
					<iframe width="100%" height="100%" scrolling="no" id="${sysWfBusinessFormPrefix}WF_IFrame"></iframe>
					<script>
					$("#${sysWfBusinessFormPrefix}WF_IFrame").ready(function() {
						setTimeout(function(){
							lbpm.globals.updateFreeFlowNodeUL(true);
							lbpm.globals.initFreeFlowFormList();
						},50);
						var lbpm_panel_reload = function() {
							$("#${sysWfBusinessFormPrefix}WF_IFrame").attr('src', function (i, val) {return val;});
							lbpm.freeFlow.emptyHandlerNodes = [];
							lbpm.globals.updateFreeFlowNodeUL();
							$("#${sysWfBusinessFormPrefix}WF_IFrame")[0].onload = function() {    
								lbpm.globals.initFreeFlowFormList();
			            	};
						};
						lbpm.events.addListener(lbpm.constant.EVENT_MODIFYNODEATTRIBUTE, lbpm_panel_reload);
						lbpm.events.addListener(lbpm.constant.EVENT_MODIFYPROCESS, lbpm_panel_reload);
						lbpm.events.addListener(lbpm.constant.EVENT_SELECTEDMANUAL, lbpm_panel_reload);
					});
					$('#flowGraphicShowCheckbox').bind('click', function() {
						$('#workflowInfoTD').each(function() {
							var str = this.getAttribute('onresize');
							if (str) {
								(new Function(str))();
							}
						});
					});
					$('#relationProcessCheckBox').bind('click', function() {
						if (this.checked==true) {
							if ($('#parentFlowTR')) {
								$('#parentFlowTR').show();
							}
							if ($('#subFlowTR')) {
								$('#subFlowTR').show();
							}
						} else {
							if ($('#parentFlowTR')) {
								$('#parentFlowTR').hide();
							}
							if ($('#subFlowTR')) {
								$('#subFlowTR').hide();
							}
						}
					});
					</script>
				</td>
				</tr>
			</c:if>
	     </table>
	    </div>
	    <!-- end流程图 -->

		<!-- begin流程表格 -->
		<div name="process_body" class="process_body_checked_false">
			<table width=100%>
				<c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != null && sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != ''}">
					<tr LKS_LabelName="<bean:message bundle="sys-lbpmservice" key="lbpm.tab.table" />">
						<td id="workflowTableTD" ${resize_prefix}onresize="lbpm.flow_table_load_Frame();">
							<iframe width="100%" height="100%" scrolling="no" id="${sysWfBusinessFormPrefix}WF_TableIFrame" FRAMEBORDER=0></iframe>
						</td>
					</tr>
				</c:if>
			</table>
		</div>
		<!-- end流程表格 -->

		<!-- begin流程日志 -->
		<div name="process_body" class="process_body_checked_false">
			<table width=100%>
				<tr LKS_LabelName="<bean:message bundle="sys-lbpmservice" key="label.flowLog" />">
					<td  id="flowLogTableTD" ${resize_prefix}onresize="lbpm.flow_log_load_Frame();">
						<iframe width="100%" height="100%" scrolling="no" FRAMEBORDER=0></iframe>
					</td>
				</tr>
			</table>
		</div>
		<!-- end流程日志 -->


	<c:if test="${requestScope.sysWfProcess_useActionView != 'true'}">
	<c:if test="${empty param.onClickSubmitButton}">
	</form>
	</c:if>
	</c:if>

	<script>
	if(lbpm.adminOperationsReviewJs.length>0 || lbpm.branchAdminOperationsReviewJs.length > 0){
		lbpm.globals.includeFile("/sys/lbpmservice/node/node_common_review.js","<%=request.getContextPath()%>");	
	}
	lbpm.onLoadEvents.delay.push(function(){
		var isOpinionConfig="";
		var opinionTypeValue="";
		if(lbpm.nowNodeId){
			isOpinionConfig=lbpm.nodes[lbpm.nowNodeId].opinionConfig;
			opinionTypeValue=lbpm.nodes[lbpm.nowNodeId].opinionType;
		}
		if(isOpinionConfig=="true"){
			$("#opinionConfig").show();//显示div
		}else{
			$("#opinionConfig").hide();//隐藏div
		}
		//设置节点设置的默认值
		if(opinionTypeValue!=""){
			$("#approveOpinionType option[value='"+opinionTypeValue+"']").attr("selected", true);
		}
		//如果不是当前处理人隐藏opinionConfig，并且设置下拉框无效
		if(lbpm.allMyProcessorInfoObj&&lbpm.allMyProcessorInfoObj.length==0){
			$("#fdUsageContentTd #opinionConfig").hide();//隐藏div
			$('#fdUsageContentTd #approveOpinionType').remove();
		}
	});
	</script>