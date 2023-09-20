<%@ page language="java" contentType="text/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="
	com.landray.kmss.sys.config.util.enums.JsonErrorCode,
	com.landray.kmss.sys.config.util.JsonOutput" %>
<%
	out.print(JsonOutput.returnMsg(JsonErrorCode.SUCCESS, null));
%>