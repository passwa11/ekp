<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:choose>
	<%-- 变更无模板会议（极简形式） --%>
	<c:when test="${kmImeetingMainForm.fdTemplateId == null || kmImeetingMainForm.fdTemplateId == ''}">
		<%@ include file="/km/imeeting/km_imeeting_main/kmImeetingMain_edit_simple.jsp"%>
	</c:when>
	<%-- 变更有模板会议（完整形式） --%>
	<c:otherwise>
		<%@ include file="/km/imeeting/km_imeeting_main/kmImeetingMain_edit_complete.jsp"%>
	</c:otherwise>
</c:choose>