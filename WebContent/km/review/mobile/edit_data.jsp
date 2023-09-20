<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:choose>
	<%--钉钉端--%>
	<c:when test='<%="true".equals(SysFormDingUtil.getEnableDing()) || "true".equals(request.getParameter("ddpage"))%>'>
		<c:import url="/km/review/mobile/dingSuit/ding_edit_data.jsp" charEncoding="UTF-8"/>
	</c:when>
	<c:otherwise>
		<c:import url="/km/review/mobile/edit_data_normal.jsp" charEncoding="UTF-8"/>
	</c:otherwise>
</c:choose>