<%@ page language="java" contentType="text/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="
	com.alibaba.fastjson.JSONObject,
	com.landray.kmss.sys.config.util.enums.JsonErrorCode,
	com.landray.kmss.sys.config.util.JsonOutput" %>
<%
	JSONObject redirect = new JSONObject(true); 
	redirect.put("modelId", request.getAttribute("modelId"));
	redirect.put("modelName", request.getAttribute("modelName"));
	redirect.put("redirectUrl", request.getAttribute("redirectto"));
	
	out.print(JsonOutput.returnMsg(JsonErrorCode.REDIRECT, redirect));
%>