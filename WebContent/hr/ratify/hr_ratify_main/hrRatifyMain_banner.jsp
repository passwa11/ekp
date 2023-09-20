<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="__hrRatifyMainForm" value="${requestScope[param.formName]}" />
<div class="lbpm_flowInfoW">
	<c:if test="${__hrRatifyMainForm.docStatus eq '30'}">
   		<!-- <div class="lbpm_processStatus"  id="processStatusDiv">
      	</div> -->
      	<img src="${KMSS_Parameter_ContextPath}sys/ui/extend/theme/default/images/lbpm-processStatus.png" height="78" width="100" />
    </c:if>
   	<c:if test="${__hrRatifyMainForm.docStatus eq '00'}">
    	<!-- <div class="lbpm_discardStatus" id="discardStatusDiv">
      	</div> -->
      	<img src="${KMSS_Parameter_ContextPath}sys/ui/extend/theme/default/images/lbpm-discardStatus.png" height="78" width="100" />
   	</c:if>
</div>
<c:if test="${param.approveType eq 'right'}">
 	<script>
 		$(function(){
 			$(".lbpm_flowInfoW").css("top","2px")
 		});
 	</script>
</c:if>