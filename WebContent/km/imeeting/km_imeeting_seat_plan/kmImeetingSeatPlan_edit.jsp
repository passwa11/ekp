<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:choose>
	<c:when test="${kmImeetingSeatPlanForm.fdHasTemplateDetail == 'true' }">
		<%@ include file="/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan_edit_template.jsp" %>
	</c:when>
	<c:otherwise>
		<%@ include file="/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan_edit_no_template.jsp" %>
	</c:otherwise>
</c:choose>