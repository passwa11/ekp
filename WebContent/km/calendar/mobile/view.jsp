<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:choose> 
	<c:when test="${ kmCalendarMainForm.fdType=='note' }">   
		<%@ include file="./view_note.jsp"  %>
	</c:when>
	<c:otherwise>   
		<%@ include file="./view_event.jsp"  %>
	</c:otherwise>
</c:choose>
