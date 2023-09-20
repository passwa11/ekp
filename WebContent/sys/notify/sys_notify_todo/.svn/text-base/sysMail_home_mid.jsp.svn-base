<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="org.apache.commons.lang.StringUtils,com.landray.kmss.sys.notify.service.spring.SysNotifyEmailServiceImp"%>
<%
	SysNotifyEmailServiceImp service = new SysNotifyEmailServiceImp();
	String importMailNumJsp = service.getEmailNumsJspFromPlugin();
	if(StringUtils.isNotBlank(importMailNumJsp)){
		request.setAttribute("importMailNumJsp",importMailNumJsp);
	}
%>
<c:if test="${not empty importMailNumJsp}">
	<c:import url="${importMailNumJsp }" charEncoding="UTF-8"></c:import>
</c:if>
