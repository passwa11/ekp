<%@page import="java.net.URLDecoder"%>
<%@page import="com.landray.kmss.web.Globals"%>
<%@page import="com.landray.kmss.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String pwdExpireDays = request.getParameter("pwdExpireDays");
String isNewUser = request.getParameter("isNewUser");
String targetUrl = request.getParameter(Globals.SPRING_SECURITY_TARGET_URL_KEY);
String compulsoryChangePassword = request.getParameter("compulsoryChangePassword");
String redirectTo = request.getParameter("redirectTo");

if(StringUtil.isNotNull(pwdExpireDays)){
	session.setAttribute("pwdExpireDays", Integer.parseInt(pwdExpireDays));
}
if(StringUtil.isNotNull(isNewUser)){
	session.setAttribute("isNewUser", Boolean.parseBoolean(isNewUser));
}
if(StringUtil.isNotNull(compulsoryChangePassword)){
	session.setAttribute("compulsoryChangePassword", Boolean.parseBoolean(compulsoryChangePassword));
}
redirectTo = URLDecoder.decode(redirectTo);
response.sendRedirect(redirectTo);
%>

