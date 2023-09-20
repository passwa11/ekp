<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:set var="hrRatifyForm" value="${requestScope[param.formName]}" />
<%-- 反馈记录 --%>
<c:if test="${hrRatifyForm.docStatus=='30'||hrRatifyForm.docStatus=='31'}">
	<ui:content title="反馈记录${hrRatifyForm.fdReviewFeedbackInfoCount}">
	<kmss:auth
		requestURL="/hr/ratify/hr_ratify_feedback/hrRatifyFeedback.do?method=deleteall&fdDocId=${hrRatifyForm.fdId}"
		requestMethod="GET">
	<c:set var="validateAuthfeedback" value="true" />
	</kmss:auth>
		<list:listview channel="feedbackch1">
			<ui:source type="AjaxJson">
				{"url":"/hr/ratify/hr_ratify_feedback/hrRatifyFeedback.do?method=listdata&fdDoc.fdId=${hrRatifyForm.fdId}"}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.listtable"  cfg-norecodeLayout="simple" 
			  rowHref="/hr/ratify/hr_ratify_feedback/hrRatifyFeedback.do?method=view&fdId=!{fdId}">
				<list:col-auto props="fdSerial;fdHasAttachment;fdSummary;docCreator.fdName;docCreateTime"></list:col-auto>
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

	<script language="JavaScript">
		seajs.use(['lui/dialog','lui/topic'],function(dialog,topic){
			window.deleteFeedbackInfo = function(fdId) {
				dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(isOk) {
					if (isOk) {
						var loading = dialog.loading();
						var url = '<c:url value="/hr/ratify/hr_ratify_feedback/hrRatifyFeedback.do" />?method=delete&fdId=' + fdId;
						$.getJSON(url, function(json) {
							loading.hide();
							if (json.status) {
								dialog.success('<bean:message key="return.optSuccess" />');
								topic.channel('feedbackch1').publish('list.refresh');
							} else {
								dialog.failure('<bean:message key="return.optFailure" />');
							}
						});
					}
				});
				return;
			};
		});
	</script>