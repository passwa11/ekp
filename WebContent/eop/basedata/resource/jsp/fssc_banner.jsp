<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/eop/basedata/resource/css/banner.css"/>
<c:set var="fsscLbpmMainForm" value="${requestScope[param.formName]}" />
<div class="lbpm_flowInfoW">
	<c:if test="${fsscLbpmMainForm.docStatus eq '30'}">
   		<div class="lbpmProcessStatus">
   			<div class="lbpmProcessText">${lfn:message('eop-basedata:fsscLbpmMainForm.processStatus.end')}</div>
      	</div>
    </c:if>
   	<c:if test="${fsscLbpmMainForm.docStatus eq '00'}">
    	<div class="lbpmProcessStatus lbpmDiscardStatus">
      		<div class="lbpmProcessText">${lfn:message('eop-basedata:fsscLbpmMainForm.processStatus.discard')}</div>
      	</div>
   	</c:if>
</div>
<c:if test="${param.approveType eq 'right'}">
 	<script>
 		$(function(){
 			$(".lbpm_flowInfoW").css("top","2px")
 		});
 	</script>
</c:if>
