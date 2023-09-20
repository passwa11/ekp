<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<template:include ref="default.view" sidebar="auto">
<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="90%">
		    <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
</template:replace>
<template:replace name="content">
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
<p class="txttitle"><bean:message bundle="km-review"
	key="table.kmReviewFeedbackInfo" /></p>
<div class="lui_form_content_frame" style="padding-top:30px">
 <table class="tb_normal" width=70%>
	<!-- 反馈人 -->
	<tr>
		<td class="td_normal_title" width=10%><bean:message
			bundle="km-review" key="kmReviewMain.titleName" /></td>
		<td><a style="text-decoration:underline;color: #0a57cb" href="${LUI_ContextPath}/km/review/km_review_main/kmReviewMain.do?method=view&fdId=${kmReviewFdId}">${kmReviewDocSubject}</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=10%><bean:message
			bundle="km-review" key="kmReviewFeedbackInfo.docCreatorId" /></td>
		<td><bean:write name="kmReviewFeedbackInfoForm"
			property="docCreatorName" /></td>
	</tr>
	<!-- 反馈时间 -->
	<tr>
		<td class="td_normal_title" width=10%><bean:message
			bundle="km-review" key="kmReviewFeedbackInfo.docCreatorTime" /></td>
		<td><bean:write name="kmReviewFeedbackInfoForm"
			property="docCreatorTime" /></td>
	</tr>
	<!-- 提要 -->
	<tr>
		<td class="td_normal_title" width=10%><bean:message
			bundle="km-review" key="kmReviewFeedbackInfo.fdSummary" /></td>
		<td><bean:write name="kmReviewFeedbackInfoForm"
			property="fdSummary" /></td>
	</tr>
	<!-- 反馈内容 -->
	<tr>
		<td class="td_normal_title" width=10%><bean:message
			bundle="km-review" key="kmReviewFeedbackInfo.docContent" /></td>
		<td><xform:textarea property="docContent">
			</xform:textarea></td>
	</tr>
	<!-- 通知人员 -->
	<tr>
		<td class="td_normal_title" width=10%><bean:message
			bundle="km-review" key="notify.people" /></td>
		<td><bean:write name="kmReviewFeedbackInfoForm"
			property="fdNotifyPeople" /></td>
	</tr>
	<!-- 通知方式 -->
	<tr>
		<td class="td_normal_title" width=10%><bean:message
			bundle="km-review" key="notify.type" /></td>
		<td>
			<kmss:showNotifyType value="${kmReviewFeedbackInfoForm.fdNotifyType}"/>
		</td>
	</tr>
</table>
	<!--附件-->
	<c:if test="${not empty kmReviewFeedbackInfoForm.attachmentForms['feedBackAttachment'].attachments}">    
	  	<div class="lui_form_spacing"> </div>
	    <div>
			<div class="lui_form_subhead"><img src="${KMSS_Parameter_ContextPath}sys/attachment/view/img/attachment.png"> ${ lfn:message('km-collaborate:kmCollaborate.jsp.attachment') }(${fn:length(kmReviewFeedbackInfoForm.attachmentForms['feedBackAttachment'].attachments)})</div>
			<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
				<c:param name="fdKey" value="feedBackAttachment" />
				<c:param name="fdModelId" value="${param.fdId}" />
				<c:param name="formBeanName" value="kmReviewFeedbackInfoForm" />
			</c:import>
    	</div>
	</c:if>
</div>
</template:replace>
</template:include>
