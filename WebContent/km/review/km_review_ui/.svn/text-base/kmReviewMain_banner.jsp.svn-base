<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/km/review/resource/style/doc/banner.css"/>
<c:set var="__kmReviewMainForm" value="${requestScope[param.formName]}" />
<c:if test="${__kmReviewMainForm.docStatus eq '30' || __kmReviewMainForm.docStatus eq '00' || __kmReviewMainForm.docStatus eq '31'}">
	<div class="lbpm_flowInfoW">
		<c:if test="${__kmReviewMainForm.docStatus eq '30'}">
	   		<div class="lbpmProcessStatus">
	   			<div class="lbpmProcessText"><bean:message bundle="km-review" key="kmReviewMain.processStatus.end" /></div>
	      	</div>
	    </c:if>
	   	<c:if test="${__kmReviewMainForm.docStatus eq '00'}">
	    	<div class="lbpmProcessStatus lbpmDiscardStatus">
	      		<div class="lbpmProcessText"><bean:message bundle="km-review" key="kmReviewMain.processStatus.discard" /></div>
	      	</div>
	   	</c:if>
		<%--#145079-新增已反馈文档的状态标记-开始--%>
		<c:if test="${__kmReviewMainForm.docStatus eq '31'}">
			<div class="lbpmProcessStatus">
				<div class="lbpmProcessText"><bean:message bundle="km-review" key="kmReviewMain.processStatus.end" /></div>
			</div>
		</c:if>
		<%--#145079-新增已反馈文档的状态标记-结束--%>
	</div>
	<c:if test="${param.approveType eq 'right'}">
	 	<script>
	 		$(function(){
	 			$(".lbpm_flowInfoW").css("top","2px")
	 		});
	 	</script>
	</c:if>
</c:if>