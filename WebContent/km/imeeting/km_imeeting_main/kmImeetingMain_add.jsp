<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%request.setAttribute("dateTimeFormatter",ResourceUtil.getString("date.format.datetime"));%>
<c:choose>
	<%-- 新建无模板会议（极简形式） --%>
	<c:when test="${noTemplate == 'true'}">
		<%@ include file="/km/imeeting/km_imeeting_main/kmImeetingMain_add_simple.jsp"%>
	</c:when>
	<%-- 新建有模板会议（完整形式） --%>
	<c:otherwise>
		<%@ include file="/km/imeeting/km_imeeting_main/kmImeetingMain_add_complete.jsp"%>
	</c:otherwise>
</c:choose>
