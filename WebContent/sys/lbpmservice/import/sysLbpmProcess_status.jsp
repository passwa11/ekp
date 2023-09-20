<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="__kmReviewMainForm" value="${requestScope[param.formName]}" />
<div class="flowInfoW">
	<c:if test="${__kmReviewMainForm.docStatus eq '30'}">
   		<div class="processStatus"  id="processStatusDiv">
      	</div>
    </c:if>
   	<c:if test="${__kmReviewMainForm.docStatus eq '00'}">
    	<div class="discardStatus" id="discardStatusDiv">
      	</div>
   	</c:if>
</div>
