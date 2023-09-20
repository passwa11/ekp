<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	/* // 开启三员后不能访问
	if(TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) {
		String url = request.getContextPath() + "/resource/jsp/e404.jsp";
		request.getRequestDispatcher(url).forward(request, response);
	} */
%>
<template:include ref="profile.listview" type="transfer">
</template:include>