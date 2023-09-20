<%@ page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
	JSONObject json = JSONObject.fromObject(request.getAttribute("kmCalendarMainForm"));
%>
<%=json.toString()%>
