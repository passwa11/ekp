<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.ServerTypeUtil"%>
<%@ page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%
	JgWebOffice ws = new JgWebOffice();
	String isAddition = request.getParameter("_addition");
	if ("1".equals(isAddition)) {
		ws.addition(request, response);
	} else {
		ws.execute(request, response);
	}
	if(ServerTypeUtil.getServerType()!=ServerTypeUtil.WEBLOGIC){
		out.clear();
		//保存当前的out对象
		out = pageContext.pushBody();
	}
%>