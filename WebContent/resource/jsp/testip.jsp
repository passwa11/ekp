<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%
Enumeration e = request.getHeaderNames(); 
while (e.hasMoreElements()) { 
	String name = (String)e.nextElement(); 
	String value = request.getHeader(name); 
%>
<%=StringEscapeUtils.escapeHtml(name)%>=<%= StringEscapeUtils.escapeHtml(value)%><br>
<%}%>


