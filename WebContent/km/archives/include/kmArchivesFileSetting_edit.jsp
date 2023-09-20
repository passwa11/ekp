<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:choose>
	<c:when test="${param.mechanismMap == 'true'}">
		<%@ include file="/km/archives/include/machanism/kmArchivesFileSetting_edit.jsp"%>
	</c:when>
	<c:otherwise>
		<%@ include file="/km/archives/include/old/kmArchivesFileSetting_edit.jsp"%>
	</c:otherwise>
</c:choose>