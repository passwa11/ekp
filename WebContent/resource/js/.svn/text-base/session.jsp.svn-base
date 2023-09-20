<%@ page language="java" contentType="application/x-javascript; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.landray.kmss.sys.profile.model.PasswordSecurityConfig"%>

<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", -1);
%>

window.getSessionId = function(){
	return "<%= request.getSession().getId() %>";
}
window.getSM2PubKey = function(){
	return  "<%= PasswordSecurityConfig.getSM2PubKey()%>";
}