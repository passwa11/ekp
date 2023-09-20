<%--移动端 实施反馈 按钮--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>


<kmss:auth requestMethod="GET"
		   requestURL="/km/review/km_review_feedback_info/kmReviewFeedbackInfo.do?method=add&fdMainId=${param.fdId}&fdCreatorId=${kmReviewMainForm.docCreatorId}">
		<div data-dojo-type="km/review/mobile/km_review_feedback_info/js/FeedbackInfoButton"
			 data-dojo-props='icon1:"${_cir_icon}",align:"${param.align}",label:"${ lfn:message('km-review:button.feedback.info') }",
				docCreatorName:"${kmReviewMainForm.docCreatorName}",	docCreatorId:"${kmReviewMainForm.docCreatorId}",	modelName:"${kmReviewMainForm.modelClass.name}",modelId:"${kmReviewMainForm.fdId}",fdKey:"${param.fdKey }",addCirculate:"${addCirculate}"'>
		</div>
</kmss:auth>