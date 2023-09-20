<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/km/imeeting/import/time.jsp"%>
<c:choose>
	<%-- 无模板会议（极简形式） --%>
	<c:when test="${kmImeetingMainForm.fdTemplateId == null || kmImeetingMainForm.fdTemplateId == ''}">
	<%@ include file="/km/imeeting/km_imeeting_main/kmImeetingMain_view_simple.jsp"%>
	</c:when>
	<%-- 有模板会议（完整形式） --%>
	<c:otherwise>
	<%@ include file="/km/imeeting/km_imeeting_main/kmImeetingMain_view_complete.jsp"%>
	</c:otherwise>
</c:choose>