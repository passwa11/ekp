
<%@page import="com.landray.kmss.third.im.kk.util.KK5Util"%><%@ page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	out.print(KK5Util.getGZHJson(request.getParameter("getGzhUrl")));
%>