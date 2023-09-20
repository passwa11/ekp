<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<c:choose>
	<c:when test="${'true' eq kmArchivesMainForm.fdDestroyed }">
		<%@include file="./kmArchivesMain_view_destroyed.jsp" %>
	</c:when>
	<c:otherwise>
		<%@include file="./kmArchivesMain_view_onUse.jsp" %>
	</c:otherwise>
</c:choose>
