<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:choose>
	<c:when test="${HtmlParam.categoryType eq 'custom'}">
		<%@ include file="/sys/attend/sys_attend_main/index_custom.jsp" %>
	</c:when>
	<c:otherwise>
		<%@ include file="/sys/attend/sys_attend_main/index_attend.jsp" %>
	</c:otherwise>
</c:choose>