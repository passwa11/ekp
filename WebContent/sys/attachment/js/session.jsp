<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="application/x-javascript; charset=UTF-8" pageEncoding="UTF-8" %>
<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", -1);

JSONObject json = new JSONObject();
json.put("JSESSIONID", request.getSession().getId());

String cookieHeader = request.getHeader("Cookie");
if (StringUtil.isNotNull(cookieHeader)) {
	String[] cookies = cookieHeader.split(";");
	if (cookies != null) {
		for (int i = 0; i < cookies.length; i++) {
			String cookie = cookies[i].trim();
			json.put(cookie.substring(0, cookie.indexOf("=")), cookie.substring(cookie.indexOf("=") + 1, cookie.length()));
		}
	}
}
%>

window.getJGSession = function() {
	return eval('(<%= json.toJSONString() %>)');
}