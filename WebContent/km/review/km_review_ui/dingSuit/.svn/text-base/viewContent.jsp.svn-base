<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
	if(Com_Parameter.dingXForm === "true") {
		Com_IncludeFile("lbpmProcess.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
	}
</script>
<%-- 流程 --%>
<c:choose>
	<c:when test="${kmReviewMainForm.docStatus>='30' || kmReviewMainForm.docStatus=='00'}">
		<!-- 流程状态+流程图 -->
		<c:import url="/sys/workflow/import/sysWfProcess_view_flow_ding.jsp" charEncoding="UTF-8">
			<c:param name="needInitLbpm" value="true" />
			<c:param name="formName" value="kmReviewMainForm" />
		</c:import>
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
					<c:param name="approvePosition" value="right" />
				</c:import>
			</c:when>
			<c:otherwise>
				<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmReviewMainForm" />
					<c:param name="fdKey" value="reviewMainDoc" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
				</c:import>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
<!-- 审批记录 -->
<c:import url="/sys/lbpmservice/support/lbpm_audit_note/dingSuit/lbpmAuditNote_list_content.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="kmReviewMainForm" />
	<c:param name="fdModelId" value="${kmReviewMainForm.fdId}" />
	<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
	<c:param name="docStatus" value="${kmReviewMainForm.docStatus}" />
</c:import>
<!-- 流程状态+流程图 -->
<c:import url="/sys/workflow/import/sysWfProcess_view_flow_ding.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="kmReviewMainForm" />
	<c:param name="fdKey" value="reviewMainDoc" />
	<c:param name="showHistoryOpers" value="true" />
	<c:param name="isExpand" value="true" />
</c:import>
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
