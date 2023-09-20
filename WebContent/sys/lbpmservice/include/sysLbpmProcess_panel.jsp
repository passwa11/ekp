<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content">
<%@ include file="/sys/lbpmservice/include/sysLbpmProcess_script.jsp"%>
<%@ include file="/sys/lbpmservice/workitem/workitem_admin.jsp"%>
<%@ include file="/sys/lbpmservice/workitem/workitem_drafter.jsp"%>
<script>
Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
lbpm.globals.includeFile("/sys/lbpmservice/node/node_common_review.js");
</script>
<form name="sysWfProcessForm" method="POST" action="<c:url value="/sys/lbpmservice/support/lbpm_process/lbpmProcess.do" />">
	<input type="hidden" id="sysWfBusinessForm.fdIsModify" name="sysWfBusinessForm.fdIsModify" value='' >
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdParameterJson" name="sysWfBusinessForm.fdParameterJson" >
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdAdditionsParameterJson" name="sysWfBusinessForm.fdAdditionsParameterJson" >
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdCurHanderId" name="sysWfBusinessForm.fdCurHanderId" >
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdCurNodeSavedXML" name="sysWfBusinessForm.fdCurNodeSavedXML" >
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdFlowContent" name="sysWfBusinessForm.fdFlowContent" >
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdProcessId" name="sysWfBusinessForm.fdProcessId">
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdKey" name="sysWfBusinessForm.fdKey" >
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdModelId" name="sysWfBusinessForm.fdModelId" >
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdModelName" name="sysWfBusinessForm.fdModelName" >
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdCurNodeXML" name="sysWfBusinessForm.fdCurNodeXML" >
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdTemplateModelName" name="sysWfBusinessForm.fdTemplateModelName" >
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdTemplateKey" name="sysWfBusinessForm.fdTemplateKey" >
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdTranProcessXML" name="sysWfBusinessForm.fdTranProcessXML" >
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdDraftorId" name="sysWfBusinessForm.fdDraftorId" >
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdDraftorName" name="sysWfBusinessForm.fdDraftorName" >
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdHandlerRoleInfoIds" name="sysWfBusinessForm.fdHandlerRoleInfoIds" >
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdHandlerRoleInfoNames" name="sysWfBusinessForm.fdHandlerRoleInfoNames" >
	<input type="hidden" needcopy="true" id="docStatus" name="docStatus" >
	<!-- 留下其他信息处理的域，为了兼容特权人修改流程图信息时保存其他设计的信息 -->
	<input type="hidden" needcopy="true" id="sysWfBusinessForm.fdOtherContentInfo" name="sysWfBusinessForm.fdOtherContentInfo">
<script type="text/javascript">
lbpm.modelingModelId = "${JsParam.modelingModelId}";
lbpm.defaultOperationType = "${JsParam.operationType}";
lbpm.defaultSelectedTaskId = "${JsParam.selectedTaskId}";
var dialogObject=null;
if(window.dialogArguments){
	dialogObject=window.dialogArguments;
}else if(opener && opener.Com_Parameter.Dialog){
	dialogObject=opener.Com_Parameter.Dialog;
}else{
	dialogObject=parent.dialogArguments?parent.dialogArguments:(parent.opener?parent.opener.Com_Parameter.Dialog:parent.parent.Com_Parameter.Dialog);
}
var pWin = dialogObject.Window;
var pDom = pWin.document;
lbpm.isFreeFlow = pWin.lbpm.isFreeFlow;
var _thirdSysFormList =dialogObject._thirdSysFormList;//第三方系统集成表单参数
var otherContentInfo = dialogObject.otherContentInfo;//其他信息参数
Com_AddEventListener(window, 'load', function(){
	var isNotifyCurrentHandler = Lbpm_SettingInfo["isNotifyCurrentHandler"];
	if ((isNotifyCurrentHandler === "false" && dialogObject.roleType === "authority") || dialogObject.roleType === "branchadmin"){
		$("#notifyLevelRow").hide();
		$("#notifyTypeRow").hide();
	}	
}); 
</script>
<c:if test="${param.roleType eq 'drafter'}">
	<script type="text/javascript">
		var pDrafterJsArr=pWin.lbpm.drafterOperationsReviewJs;
		for(var i=0,size=pDrafterJsArr.length;i<size;i++){
			if(pDrafterJsArr[i]!="")
			lbpm.globals.includeFile(pDrafterJsArr[i],"<%=request.getContextPath()%>","js");
		}
	</script>
</c:if>
<c:if test="${param.roleType eq 'authority'}">
	<script type="text/javascript">
		var pAdminJsArr=pWin.lbpm.adminOperationsReviewJs;
		for(var i=0,size=pAdminJsArr.length;i<size;i++){
			if(pAdminJsArr[i]!="")
			lbpm.globals.includeFile(pAdminJsArr[i],"<%=request.getContextPath()%>","js");
		};
	</script>
</c:if>
<c:if test="${param.roleType eq 'historyhandler'}">
	<script type="text/javascript">
		var pHistoryhandlerJsArr=pWin.lbpm.historyhandlerOperationsReviewJs;
		for(var i=0,size=pHistoryhandlerJsArr.length;i<size;i++){
			if(pHistoryhandlerJsArr[i]!="")
			lbpm.globals.includeFile(pHistoryhandlerJsArr[i],"<%=request.getContextPath()%>","js");
		};
		if("${JsParam.isShowHisHandlerOpt}" != "true"){
			$(document).ready(function(){
				$("#operationMethodsRow").hide(); // 历史操作选项隐藏
			});
		}
	</script>
</c:if>
<c:if test="${param.roleType eq 'branchadmin'}">
	<script type="text/javascript">
		var pBranchAdminJsArr=pWin.lbpm.branchAdminOperationsReviewJs;
		for(var i=0,size=pBranchAdminJsArr.length;i<size;i++){
			if(pBranchAdminJsArr[i]!="")
			lbpm.globals.includeFile(pBranchAdminJsArr[i],"<%=request.getContextPath()%>","js");
		};
	</script>
</c:if>
<script type="text/javascript">
function WorkFlow_SetFormField(pName, tName) {
	tName = tName ? tName : pName;
	var pField = pDom.getElementsByName(pName);
	var tField = document.getElementsByName(tName);
	if (pField.length > 0 && tField.length > 0) {
		tField[0].value = pField[0].value;
	} else {
		//alert("error!!\n pName=" + pName + "\n tName=" + tName);
	}
}
function WorkFlow_PanelInit() {
	$("[needcopy='true']").each(function() {
		WorkFlow_SetFormField(this.name);
	});
	$(function() {
		if (!pWin.lbpm.globals.isError) {
			$("#rerunIfErrorRow").remove();
		}
	});
}
WorkFlow_PanelInit();
//Com_AddEventListener(window, 'load', WorkFlow_PanelInit);

lbpm.globals.setAdminNodeNotifyType=function(nodeId,operationName){
	var notifyTypeDivIdEl = document.getElementById("systemNotifyTypeTD");
	notifyTypeDivIdEl.innerHTML=lbpm.globals.getNotifyType4NodeHTML(nodeId);
	var text = $.trim(notifyTypeDivIdEl.innerText);
	//如果是特权人身份操作，并且开关是开的或者是起草人身份
	var isShow =(Lbpm_SettingInfo["isNotifyCurrentHandler"] === "true" && dialogObject.roleType === "authority") || (dialogObject.roleType === "drafter");
	if(text!="" && isShow){
		$("#notifyTypeRow").show();
	}
	//催办无论开关与否都要显示
	if (typeof operationName != "undefined" && operationName === "press"){
		$("#notifyTypeRow").show();
	}
	if (typeof operationName == "undefined" && Lbpm_SettingInfo["isNotifyCurrentHandler"] === "false" 
			&& dialogObject.roleType === "authority"){
		$("#notifyTypeRow").hide();
	}
		
	
}

</script>
<c:if test="${param.operationType eq 'history_handler_back'}">
	<script type="text/javascript">
	function setKeepAuditNoteParam(value){
		if ("true"==value) {
			lbpm.globals.setOperationParameterJson(true, "Back_keepAuditNote", "param");
		} else {
			lbpm.globals.setOperationParameterJson(false, "Back_keepAuditNote", "param");
		}
	};
	</script>
</c:if>
	<center>
			<ui:toolbar layout="sys.ui.toolbar.float">
				<%--input id="saveDraftButton" type=button value="<bean:message key="button.saveDraft" bundle="sys-workflow" />"
                    onclick="lbpm.globals.extendRoleOptWindowSubmit('saveDraftByPanel');"--%>
				<%-- <c:if test="${(param.docStatus >= '20' && param.docStatus < '30') || param.docStatus == '11' || param.docStatus == '10'}">
                    <c:if test="${param.roleType eq 'historyhandler'}"><!-- 某些历史处理人操作由于不需要显示意见行，所以需要保留顶部的提交按钮 -->
                        <ui:button id="updateButton" text="${ lfn:message('sys-lbpmservice:button.update') }" styleClass="lui_toolbar_btn_gray"
                            onclick="lbpm.globals.extendRoleOptWindowSubmit('updateByPanel');">
                        </ui:button>
                    </c:if>
                </c:if> --%>
				<ui:button text="${ lfn:message('button.close') }" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow()">
				</ui:button>
			</ui:toolbar>

	<c:if test="${(param.docStatus >= '20' && param.docStatus < '30') || param.docStatus == '11' || param.docStatus == '10'}">
	<table class="tb_normal" width=95% style="table-layout: fixed">
			<tr id="commonUsagesRow">
				<td class="td_normal_title" width="18%" >
					<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.commonUsages" />
				</td>
				<td colspan="3" >
					<select name="commonUsages" onchange="lbpm.globals.setUsages(this);" style="width: 98%; overflow-x: hidden">
						<option value=""><bean:message key="page.firstOption" /></option>
					</select>
					<a href="#" onclick="Com_EventPreventDefault();lbpm.globals.openDefiniateUsageWindow();">
						<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.commonUsages.definite" />
					</a>
				</td>
			</tr>
			<tr id="descriptionRow">
				<td class="td_normal_title" width="18%" >
					<bean:message bundle="sys-lbpmservice" key="lbpmNode.createDraft.opinion" />
				</td>
				<td colspan="3">
					<table width=100% border=0 class="tb_noborder">
						<tr>
							<td width="85%">
								<textarea name="fdUsageContent" class="inputMul" style="width:98%;" key="auditNode" subject="${lfn:message('sys-lbpmservice:lbpmNode.createDraft.opinion')}" validate="maxLength(4000)"></textarea>
								<span id="mustSignStar" class="txtstrong" style="margin-top:62px;position:absolute;display:none">*</span>
							</td>
							<td width="15%">
								<input id="update_button" class="update_button"
								style="margin-left: 8px;" type=button value="<bean:message key="button.submit"/>"
								onclick="lbpm.globals.extendRoleOptWindowSubmit('updateByPanel');" />
							</td>
						</tr>
						<tr>
							<td><label id="currentNodeDescription"></label></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr id="operationItemsRow">
				<td class="td_normal_title" width="18%" >
					<c:choose>
						<c:when test="${param.operationType ne 'history_handler_addOpinion' }">
							<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationItems" />
						</c:when>
						<c:otherwise><!-- #37399 -->
							<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.relatedItems" />
						</c:otherwise>
					</c:choose>
				</td>
				<td colspan="3">
					<c:choose>
						<c:when test="${!empty param.selectedTaskId}">
							<select name="operationItemsSelect" onchange="lbpm.globals.operationItemsChanged(this);" style="display:none;">
							</select>
							<span></span>
						</c:when>
						<c:otherwise>
							<select name="operationItemsSelect" onchange="lbpm.globals.operationItemsChanged(this);">
							</select>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr id="operationMethodsRow">
				<td class="td_normal_title" width="18%" >
					<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationMethods" />
				</td>
				<td colspan="3">
				<div id="operationMethodsGroup"></div>
				</td>
			</tr>
			<tr id="operationsRow_Type" style="display:none;" lbpmMark="operation">
				<td id="operationsTDTitle_Type" class="td_normal_title" width="18%">
					&nbsp;
				</td>
				<td id="operationsTDContent_Type" colspan="3">
					&nbsp;
				</td>
			</tr>
			<tr id="operationsRow" style="display:none;" lbpmMark="operation">
				<td id="operationsTDTitle" class="td_normal_title" width="18%">
					&nbsp;
				</td>
				<td id="operationsTDContent" colspan="3">
					&nbsp;
				</td>
			</tr>
			<tr id="operationsRow_Scope" style="display:none;" lbpmMark="operation">
				<td id="operationsTDTitle_Scope" class="td_normal_title" width="18%">
					&nbsp;
				</td>
				<td id="operationsTDContent_Scope" colspan="3">
					&nbsp;
				</td>
			</tr>
			<tr id="rerunIfErrorRow" style="display:none;" lbpmMark="hide">
				<td id="rerunIfErrorTDTitle" class="td_normal_title" width="18%" >
					<kmss:message key="sys-lbpmservice-operation-admin:lbpmOperations.fdOperType.addition.rerunEventTitle" />
				</td>
				<td id="rerunIfErrorTDContent" colspan="3">
					<label id="rerunIfErrorLabel">
					<input type="checkbox" id="rerunIfError" value="true">
					<kmss:message key="sys-lbpmservice-operation-admin:lbpmOperations.fdOperType.addition.rerunEventFlag" />
					</label>
				</td>
			</tr>
			
			<!--保留流程意见（撤回审批） -->
			<c:if test="${param.operationType eq 'history_handler_back' }">
			<tr id="keepAuditNoteRow" style="display:none;">
				<td class="td_normal_title"  width="18%" >
					<bean:message bundle="sys-lbpmservice-operation-historyhandler" key="lbpmOperations.fdOperType.historyhandler.back.keepAuditNote" />
				</td>
				<td colspan="3">
					<xform:radio value="false" showStatus="edit" property="keepAuditNote" onValueChange="setKeepAuditNoteParam(this.value);">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:radio>
				</td>
			</tr>
			</c:if>
			
			<!--通知紧急程度 -->
			<tr id="notifyLevelRow">
				<td class="td_normal_title"  width="18%" >
					<bean:message bundle="sys-notify" key="sysNotifyTodo.level.title" />
				</td>
				<td colspan="3" id="notifyLevelTD">
					<kmss:editNotifyLevel property="sysWfBusinessForm.fdNotifyLevel" value=""/>
				</td>
			</tr>
			
			<input type="hidden" name="sysWfBusinessForm.fdSystemNotifyType" value="" id="sysWfBusinessForm.fdSystemNotifyType">
			<tr id="notifyTypeRow" style="display:none">
				<td class="td_normal_title"  width="18%" >
					<bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.notify.type" />
				</td>
				<td colspan="3" id="systemNotifyTypeTD">
				</td>
			</tr>

			<!--
			<tr id="notifyTypeRow">
				<td class="td_normal_title"  width="18%">
					<bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.notify.type" />
				</td>
				<td colspan="3" id="systemNotifyTypeTD">
					<kmss:editNotifyType property="sysWfBusinessForm.fdSystemNotifyType" value="${param.notifyType}"/><br />
					<bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.notify.type.info1" /> 
				</td>
			</tr>
			-->
			<c:if test="${param.roleType  eq 'drafter'}">
			<tr id="notifyOptionTR">
				<td class="td_normal_title" width="18%" >
					<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.notifyOption" />
				</td>
				<td colspan="3">
					<label>
					<input type="checkbox" id="notifyOnFinish" value="true" alertText="" key="notifyOnFinish">
					<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.notifyOption.message" />
					</label>
				</td>
			</tr>
			</c:if>
			<tr id="checkChangeFlowTR" style="display:none;" lbpmMark="hide">
				<td class="td_normal_title" width="18%" >
					<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.changeProcessor" />
				</td>
				<td colspan="3">
					<label id="changeProcessorDIV" style="display:none;">
						<a class="com_btn_link" href="#" onclick="Com_EventPreventDefault();lbpm.globals.changeProcessorClick();">
							<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.changeProcessor.button" />
						</a>&nbsp;&nbsp;
					</label>
					<label id="modifyFlowDIV" style="display:none;">
						<a class="com_btn_link" href="javascript:lbpm.globals.modifyProcess('sysWfBusinessForm.fdFlowContent','sysWfBusinessForm.fdTranProcessXML');">
							<bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.modifyFlow" />
						</a>
					</label>
					<label id="modifyEmbeddedSubFlowDIV" style="display:none;margin-left:10px;">
					</label>
				</td>
			</tr>
			<tr id="currentHandlersRow">
				<td class="td_normal_title" width="18%" >
					<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.currentProcessor" />
				</td>
				<td colspan="3">
					<div id="currentHandlersLabel">
						<kmss:showWfPropertyValues idValue="${param.modelId}" propertyName="handerNameDetail" extend="hidePersonal" />
					</div>
				</td>
			</tr>
			<tr id="historyHandlersRow">
				<td class="td_normal_title" width="18%" >
					<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.finishProcessor" />
				</td>
				<td colspan="3">
					<div id="historyHandlersLabel">
						<kmss:showWfPropertyValues idValue="${param.modelId}" propertyName="historyHanderName" />
					</div>
				</td>
			</tr>
	</table>
	</c:if>
	</center>
</form>
<div id="subprocess_list_table_temp" style="display:none;">
<table id="_id_" class="tb_normal" style="width:100%;">
	<tr class="tr_normal_title" >
	<td><bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.adminrecover.subtitle" /></td>
	<td style="width:26px;">
		<a href="javascript:void(0);" onclick="lbpm.globals.adminOperationTypeRecover_AddNewSubprocessRows()" >
		<bean:message key="dialog.selectOther" />
		</a>
	</td>
	</tr>
</table>
<input type="hidden" _key_="recoverProcessIds" id="_recoverProcessIds_"
	alertText="<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.adminrecover.alertText" />" >
</div>
<table style="display:none;">
	<tr>
	<td id="subprocess_list_table_col_1">
		<input type="hidden" name="workflow_recover_subprocessid" value="">
		<span></span>
	</td>
	<td id="subprocess_list_table_col_2">
		<a href="javascript:void(0);" onclick="lbpm.globals.adminOperationTypeRecover_DeleteSubprocessRow(this);" >
		<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.adminrecover.delete" />
		</a>
	</td>
	</tr>
</table>

<style>
    .customDivCss {
		width: 500px;
		margin-top: 30px;
		margin-left: 40px;
		height:150px;
        border:1px solid #d2d6de;
		text-align:center;
		line-height:105px;   
		background-color: #dff6c6;
		color:red;
		font-size:20px;
    }
    .update_button{
		width: 78px;
	  	height: 78px;
	  	border: 1px solid #4285f4;
	  	background: #4285f4;
	  	color: #fff;
	  	font-size: 18px;
	  	font-weight: normal;
	  	transition: all .2s ease-in-out;
	  	cursor: pointer;
	}
	.update_button:hover{
		border-color: #346ac3;
		background-image: none;
		background-color: #346ac3;
		cursor:pointer;
	}
</style>
<div  id="_tipsDiv" style="display:none" class="customDivCss">
</div>

	</template:replace>
</template:include>