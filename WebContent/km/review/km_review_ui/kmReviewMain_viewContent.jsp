<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:if test="${param.approveModel ne 'right'}">
	<ui:content title="${lfn:message('km-review:kmReviewDocumentLableName.baseInfo') }">
		<table class="tb_normal" width=100%>
			<!--主题-->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="km-review" key="kmReviewMain.docSubject" />
				</td>
				<td colspan=3>
					<c:out value="${ kmReviewMainForm.docSubject}"></c:out>
					<div style="display: inline-block;float:right;">
					  <ui:switch property="fdCanCircularize" showType="show" enabledText="${ lfn:message('km-review:kmReviewMain.fdCanCircularize.yes') }" disabledText="${ lfn:message('km-review:kmReviewMain.fdCanCircularize.no') }" checked="${kmReviewMainForm.fdCanCircularize}"></ui:switch>
					</div>
				</td>
			</tr>
			<!--关键字-->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="km-review" key="kmReviewKeyword.fdKeyword" />
				</td>
				<td colspan=3>
					<c:out value="${ kmReviewMainForm.fdKeywordNames}"></c:out>
				</td>
			</tr>
			<!--模板名称-->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="km-review" key="kmReviewTemplate.fdName" />
				</td>
				<td colspan=3>
					<c:out value="${ kmReviewMainForm.fdTemplateName}"></c:out>
				</td>
			</tr>
			<!--申请人-->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="km-review" key="kmReviewMain.docCreatorName" />
				</td>
				<td width=35%>
					<xform:text property="docCreatorId" showStatus="noShow"/> 
					<c:out value="${ kmReviewMainForm.docCreatorName}"></c:out>
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="km-review" key="kmReviewMain.fdNumber" />
				</td>
				<td width=35%>
					<c:out value="${ kmReviewMainForm.fdNumber}"></c:out>
				</td>
			</tr>
			<!--部门-->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="km-review" key="kmReviewMain.department" />
				</td>
				<td>
					<c:out value="${ kmReviewMainForm.fdDepartmentName}"></c:out>
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="km-review" key="kmReviewMain.docCreateTime" />
				</td>
				<td width=35%>
					<c:out value="${ kmReviewMainForm.docCreateTime}"></c:out>
				</td>
			</tr>
			<!--状态-->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="km-review" key="kmReviewMain.docStatus" />
				</td>
				<td>
					<c:if test="${kmReviewMainForm.docStatus=='00'}">
						<bean:message bundle="km-review" key="status.discard"/>
					</c:if>
					<c:if test="${kmReviewMainForm.docStatus=='10'}">
						<bean:message bundle="km-review" key="status.draft"/>
					</c:if>
					<c:if test="${kmReviewMainForm.docStatus=='11'}">
						<bean:message bundle="km-review" key="status.refuse"/>
					</c:if>
					<c:if test="${kmReviewMainForm.docStatus=='20'}">
						<bean:message bundle="km-review" key="status.append"/>
					</c:if>
					<c:if test="${kmReviewMainForm.docStatus=='30'}">
						<bean:message bundle="km-review" key="status.publish"/>
					</c:if>
					<c:if test="${kmReviewMainForm.docStatus=='31'}">
						<bean:message bundle="km-review" key="status.feedback" />
					</c:if>
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="km-review" key="kmReviewMain.docPublishTime" />
				</td>
				<td width=35%>
					<c:out value="${ kmReviewMainForm.docPublishTime}"></c:out>
				</td>
			</tr>
			<!--实施反馈人-->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="km-review" key="table.kmReviewFeedback" />
				</td>
				<td colspan=3>
					<c:out value="${ kmReviewMainForm.fdFeedbackNames}"></c:out>
				</td>

			</tr>
			<%-- 所属场所 --%>
			<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
                <c:param name="id" value="${kmReviewMainForm.authAreaId}"/>
            </c:import> 
			<!--其他属性-->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="km-review" key="table.sysCategoryProperty" />
				</td>
				<td colspan=3>
					<c:out value="${ kmReviewMainForm.docPropertyNames}"></c:out>
				</td>
			</tr>
			<%-- <xform:isExistRelationProcesses relationType="parent">
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="km-review" key="kmReviewMain.process.parent" />
				</td>
				<td colspan=3>
					<xform:showParentProcesse />
				</td>
			</tr>
			</xform:isExistRelationProcesses>
			<xform:isExistRelationProcesses relationType="subs">
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="km-review" key="kmReviewMain.process.subs" />
				</td>
				<td colspan=3>
					<xform:showSubProcesses />
				</td>
			</tr>
			</xform:isExistRelationProcesses> --%>
		</table>
	</ui:content>
</c:if>	
<%-- 收藏 --%>
<c:import url="/sys/bookmark/import/bookmark_bar.jsp" charEncoding="UTF-8">
	<c:param name="fdSubject" value="${kmReviewMainForm.docSubject}" />
	<c:param name="fdModelId" value="${kmReviewMainForm.fdId}" />
	<c:param name="fdModelName"	value="com.landray.kmss.km.review.model.KmReviewMain" />
	<c:param name="enable" value="${enableModule.enableSysBookmark eq 'false' ? 'false' : 'true'}"></c:param>
</c:import>
<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<c:choose>
			<c:when test="${kmReviewMainForm.docStatus>='30' || kmReviewMainForm.docStatus=='00'}">
				<%-- 流程 --%>
				<c:choose>
					<c:when test="${kmReviewMainForm.fdUseForm == 'true' || empty kmReviewMainForm.fdUseForm}">
						<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmReviewMainForm" />
							<c:param name="fdKey" value="reviewMainDoc" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="onClickSubmitButton" value="Com_Submit(document.kmReviewMainForm, 'publishDraft');" />
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
							<c:param name="needInitLbpm" value="true" />
							<c:param name="order" value="1" />
						</c:import>
					</c:when>
					<c:otherwise>
						<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmReviewMainForm" />
							<c:param name="fdKey" value="reviewMainDoc" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
							<c:param name="needInitLbpm" value="true" />
							<c:param name="order" value="1" />
						</c:import>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<%-- 流程 --%>
				<c:choose>
					<c:when test="${kmReviewMainForm.fdUseForm == 'true' || empty kmReviewMainForm.fdUseForm}">
						<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmReviewMainForm" />
							<c:param name="fdKey" value="reviewMainDoc" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="onClickSubmitButton" value="Com_Submit(document.kmReviewMainForm, 'publishDraft');" />
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
							<c:param name="order" value="1" />
						</c:import>
					</c:when>
					<c:otherwise>
						<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmReviewMainForm" />
							<c:param name="fdKey" value="reviewMainDoc" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
							<c:param name="order" value="1" />
						</c:import>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<%-- 流程 --%>
		<c:choose>
			<c:when test="${kmReviewMainForm.fdUseForm == 'true' || empty kmReviewMainForm.fdUseForm}">
				<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmReviewMainForm" />
					<c:param name="fdKey" value="reviewMainDoc" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="onClickSubmitButton" value="Com_Submit(document.kmReviewMainForm, 'publishDraft');" />
					<c:param name="isExpand" value="true" />
				</c:import>
			</c:when>
			<c:otherwise>
				<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmReviewMainForm" />
					<c:param name="fdKey" value="reviewMainDoc" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
				</c:import>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
<input type="hidden" name="fdSignEnable" value="${kmReviewMainForm.fdSignEnable}"/>
<c:if test="${kmReviewMainForm.fdSignEnable=='true'}">
<table class="tb_normal" width=100%>
 	<tr>
 		<td class="td_normal_title" width=15%>
 			${ lfn:message('km-review:kmReviewMain.yqqSignFile') }
 		</td>
 		<td width="85%" colspan="3" >
 			<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp"
				charEncoding="UTF-8">
				<c:param name="formBeanName" value="kmReviewMainForm" />
				<c:param name="fdKey" value="yqqSigned" />
				<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
			</c:import>
		</td>
 	</tr>
 	<tr>
 		<td class="td_normal_title" width=15%>
 			${ lfn:message('km-review:kmReviewMain.fdSignFile') }
 		</td>
 		<td width="85%" colspan="3" >
		<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp"
			charEncoding="UTF-8">
			<c:param name="formBeanName" value="kmReviewMainForm" />
			<c:param name="fdKey" value="fdSignFile" />
			<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
		</c:import>
		</td>
 	</tr>
</table>
<br>
<br>
</c:if>
<!-- 版本锁机制 -->
<c:import url="/component/locker/import/componentLockerVersion_import.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="kmReviewMainForm" />
</c:import>
<%-- 权限 --%>
<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="kmReviewMainForm" />
	<c:param name="moduleModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
	<c:param name="order" value="${kmReviewMainForm.docStatus=='30'||kmReviewMainForm.docStatus=='00'||kmReviewMainForm.docStatus=='31' ? 55 : 30}" />
</c:import>

<%-- 阅读记录 (访问统计)--%>
<c:import url="/sys/readlog/import/sysReadLog_view.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="kmReviewMainForm" />
	<c:param name="order" value="${kmReviewMainForm.docStatus=='30'||kmReviewMainForm.docStatus=='00'||kmReviewMainForm.docStatus=='31' ? 65 : 40}" />
	<c:param name="enable" value="${enableModule.enableSysReadlog eq 'false' ? 'false' : 'true'}"></c:param>
</c:import>		
<%--传阅机制(传阅记录)--%>
<c:if test="${kmReviewMainForm.fdCanCircularize eq 'true' }">
	<c:import url="/sys/circulation/import/sysCirculationMain_view.jsp"	charEncoding="UTF-8">
		<c:param name="formName" value="kmReviewMainForm" />
		<c:param name="order" value="${kmReviewMainForm.docStatus=='30'||kmReviewMainForm.docStatus=='00'||kmReviewMainForm.docStatus=='31' ? 25 : 5}" />
		<c:param name="isNew" value="true" />
		<c:param name="enable" value="${enableModule.enableSysCirculation eq 'false' ? 'false' : 'true'}"></c:param>
	</c:import>
</c:if>
<%-- 发起沟通(相关沟通) --%>
<c:if test="${kmReviewMainForm.docStatus!='10'}">
	<kmss:ifModuleExist path = "/km/collaborate/">
		<%request.setAttribute("communicateTitle",ResourceUtil.getString("kmReviewMain.communicateTitle","km-review"));%>
			<c:import url="/km/collaborate/import/kmCollaborateMain_view.jsp" charEncoding="UTF-8">
				<c:param name="commuTitle" value="${communicateTitle}" />
				<c:param name="formName" value="kmReviewMainForm" />
				<c:param name="docSubject" value="${kmReviewMainForm.docSubject}" />
				<c:param name="order" value="${kmReviewMainForm.docStatus=='30'||kmReviewMainForm.docStatus=='00'||kmReviewMainForm.docStatus=='31' ? 5 : 10}" />
				<c:param name="enable" value="${enableModule.enableKmCollaborate eq 'false' ? 'false' : 'true'}"></c:param>
			</c:import>
	</kmss:ifModuleExist>   
    <!-- 发起督办(相关督办) -->
	<kmss:ifModuleExist path="/km/supervise/">
		<c:import url="/km/supervise/import/kmSuperviseMain_edit.jsp"
			charEncoding="UTF-8">
			<c:param name="fdModelId" value="${kmReviewMainForm.fdId}" />
			<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
			<c:param name="order" value="${kmReviewMainForm.docStatus=='30'||kmReviewMainForm.docStatus=='00'||kmReviewMainForm.docStatus=='31' ? 15 : 20}" />
			<c:param name="enable" value="${enableModule.enableKmSupervise eq 'false' ? 'false' : 'true'}"></c:param>
		</c:import>
	</kmss:ifModuleExist>
</c:if>
   
<%-- 反馈记录 --%>
<c:if test="${kmReviewMainForm.docStatus=='30'||kmReviewMainForm.docStatus=='31'}">
	<ui:content title="${ lfn:message('km-review:kmReviewDocumentLableName.feedbackInfo') }${kmReviewMainForm.fdReviewFeedbackInfoCount}" cfg-order="${kmReviewMainForm.docStatus!='30' ? 35 : 55}" cfg-disable="false">
	<kmss:auth
		requestURL="/km/review/km_review_feedback_info/kmReviewFeedbackInfo.do?method=deleteall&fdModelId=${param.fdModelId}"
		requestMethod="GET">
	<c:set var="validateAuthfeedback" value="true" />
	</kmss:auth>
		<list:listview channel="feedbackch1">
			<ui:source type="AjaxJson">
				{"url":"/km/review/km_review_feedback_info/kmReviewFeedbackInfo.do?method=listdata&fdModelName=com.landray.kmss.km.review.model.KmReviewMain&fdModelId=${kmReviewMainForm.fdId}&currentVer=${sysEditionForm.mainVersion}.${sysEditionForm.auxiVersion}&orderby=docCreateTime&ordertype=down"}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.listtable"  cfg-norecodeLayout="simple" 
			  rowHref="/km/review/km_review_feedback_info/kmReviewFeedbackInfo.do?method=view&fdId=!{fdId}">
				<list:col-auto props="fdSerial;fdHasAttachment;fdSummary;fdCreator.fdName;docCreateTime"></list:col-auto>
				<c:if test="${validateAuthfeedback=='true'}"> 
					<list:col-html style="width:60px;" title="">		
							{$<a href="#" onclick="deleteFeedbackInfo('{%row.fdId%}')" class="com_btn_link"><bean:message key="button.delete" /></a>$}
					</list:col-html>
				</c:if>
			</list:colTable>						
		</list:listview>
		<div style="height: 15px;"></div>
		<list:paging channel="feedbackch1" layout="sys.ui.paging.simple"></list:paging>
	</ui:content>
</c:if>
<%-- 数据迁移 --%>
<kmss:ifModuleExist path="/tools/datatransfer/">
	<c:import url="/tools/datatransfer/import/toolsDatatransfer_old_data.jsp" charEncoding="UTF-8">
		<c:param name="fdModelId" value="${kmReviewMainForm.fdId}" />
		<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />	
	</c:import>
</kmss:ifModuleExist>

<%-- 沉淀记录 --%>
<kmss:ifModuleExist path="/kms/multidoc/">
	<kmss:authShow roles="ROLE_KMSMULTIDOC_DEFAULT">
		<c:import url="/kms/multidoc/kms_multidoc_subside/subsideRecord.jsp" charEncoding="UTF-8">
			<c:param name="fdId" value="${kmReviewMainForm.fdId }" />
			<c:param name="order" value="${kmReviewMainForm.docStatus=='30'||kmReviewMainForm.docStatus=='00'||kmReviewMainForm.docStatus=='31' ? 50 : 65}" />
			<c:param name="enable" value="${enableModule.enableKmsMultidoc eq 'false' ? 'false' : 'true'}"></c:param>
		</c:import>
	</kmss:authShow>
</kmss:ifModuleExist>
<c:if test="${enableModule.enableSysAgenda ne 'false' && (kmReviewMainForm.syncDataToCalendarTime=='flowSubmitAfter' || kmReviewMainForm.syncDataToCalendarTime=='flowPublishAfter')}">
<ui:content title="${ lfn:message('sys-agenda:module.sys.agenda.syn') }" >
	<!--日程机制(表单模块) 开始-->
	<table class="tb_normal" width=100%>
		<tr>
			<td class="td_normal_title" width="15%">
		 		<bean:message bundle="sys-agenda" key="module.sys.agenda.syn.time" />
		 	</td>
		 	<td colspan="3">
		 		<xform:radio property="syncDataToCalendarTime">
	       			<xform:enumsDataSource enumsType="kmReviewMain_syncDataToCalendarTime" />
				</xform:radio>
			</td>
		</tr>
		<tr>
			<td colspan="4" style="padding: 0px;">
				 <c:import url="/sys/agenda/import/sysAgendaMain_formula_view.jsp"	charEncoding="UTF-8">
				    <c:param name="formName" value="kmReviewMainForm" />
				    <c:param name="fdKey" value="reviewMainDoc" />
				    <c:param name="fdPrefix" value="sysAgendaMain_formula_view" />
				 </c:import>
		 	</td>
		 </tr>
    </table>
	<!--日程机制(表单模块) 结束-->
</ui:content>
</c:if>

<!-- 分享机制  -->
<kmss:ifModuleExist path="/third/ywork/">
	<c:import url="/third/ywork/ywork_share/yworkDoc_share.jsp"
		charEncoding="UTF-8">
		<c:param name="modelId" value="${kmReviewMainForm.fdId}" />
		<c:param name="modelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
		<c:param name="templateId" value="${ kmReviewMainForm.fdTemplateId}" />
		<c:param name="allPath" value="${ kmReviewMainForm.fdTemplateName}" />
		<c:param name="readRecord" value="true" />
		<c:param name="shareRecord" value="true" />
		<c:param name="synergyRecord" value="true" />
	</c:import>
</kmss:ifModuleExist>
