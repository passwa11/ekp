<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
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
<p class="txttitle"><bean:message bundle="hr-ratify"
	key="table.hrRatifyFeedback" /></p>
<div class="lui_form_content_frame" style="padding-top:30px">
 <table class="tb_normal" width=70%>
	<!-- 反馈人 -->
	<tr>
		<td class="td_normal_title" width=10%><bean:message
			bundle="hr-ratify" key="hrRatifyFeedback.docCreator" /></td>
		<td><bean:write name="hrRatifyFeedbackForm"
			property="docCreatorName" /></td>
	</tr>
	<!-- 反馈时间 -->
	<tr>
		<td class="td_normal_title" width=10%><bean:message
			bundle="hr-ratify" key="hrRatifyFeedback.docCreateTime" /></td>
		<td><bean:write name="hrRatifyFeedbackForm"
			property="docCreateTime" /></td>
	</tr>
	<!-- 提要 -->
	<tr>
		<td class="td_normal_title" width=10%><bean:message
			bundle="hr-ratify" key="hrRatifyFeedback.fdSummary" /></td>
		<td><bean:write name="hrRatifyFeedbackForm"
			property="fdSummary" /></td>
	</tr>
	<!-- 反馈内容 -->
	<tr>
		<td class="td_normal_title" width=10%><bean:message
			bundle="hr-ratify" key="hrRatifyFeedback.docContent" /></td>
		<td><xform:textarea property="docContent">
			</xform:textarea></td>
	</tr>
	<!-- 通知人员 -->
	<tr>
		<td class="td_normal_title" width=10%><bean:message
			bundle="hr-ratify" key="hrRatifyFeedback.fdNotifyPeople" /></td>
		<td><bean:write name="hrRatifyFeedbackForm"
			property="fdNotifyPeople" /></td>
	</tr>
	<!-- 通知方式 -->
	<tr>
		<td class="td_normal_title" width=10%><bean:message
			bundle="hr-ratify" key="hrRatifyFeedback.fdNotifyType" /></td>
		<td>
			<kmss:showNotifyType value="${hrRatifyFeedbackForm.fdNotifyType}"/>
		</td>
	</tr>
</table>
	<!--附件-->
	<c:if test="${not empty hrRatifyFeedbackForm.attachmentForms['feedBackAttachment'].attachments}">    
	  	<div class="lui_form_spacing"> </div>
	    <div>
			<div class="lui_form_subhead"><img src="${KMSS_Parameter_ContextPath}sys/attachment/view/img/attachment.png"> ${ lfn:message('km-collaborate:kmCollaborate.jsp.attachment') }(${fn:length(hrRatifyFeedbackForm.attachmentForms['feedBackAttachment'].attachments)})</div>
			<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
				<c:param name="fdKey" value="feedBackAttachment" />
				<c:param name="fdModelId" value="${param.fdId}" />
				<c:param name="formBeanName" value="hrRatifyFeedbackForm" />
			</c:import>
    	</div>
	</c:if>
</div>
</template:replace>
</template:include>
