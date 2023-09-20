<%@page import="com.landray.kmss.third.ding.util.DingUtils"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	if(UserUtil.getKMSSUser().isAdmin()){
		String token = DingUtils.getDingApiService().getAccessToken();
		out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;token：<br>"+token);
	}else {
		out.println("只有超级管理员才能获取token信息");
	}
%>