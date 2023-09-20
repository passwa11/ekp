<%@page import="com.landray.kmss.util.StringUtil"%><%@ page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	response.setHeader("status","true");
	String access = request.getParameter("access");
	if (StringUtil.isNotNull(access))
		request.getSession().setAttribute("S_PADFlag", access);
%>