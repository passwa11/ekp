<%@page import="com.landray.kmss.util.StringUtil,org.apache.commons.lang.StringEscapeUtils"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="application/json; charset=UTF-8"%>
<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", -1);
String jsonpcallback = request.getParameter("jsonpcallback");
jsonpcallback = StringEscapeUtils.escapeHtml(jsonpcallback);
Object json = request.getAttribute("lui-source");
if(json != null){
	if(StringUtil.isNotNull(jsonpcallback)){
		out.print(jsonpcallback+"("+json.toString()+")");
	}else{
		out.print(json.toString());
	}
}else{
	response.setHeader("error",java.net.URLEncoder.encode("请求数据为空,lui-source=null"));
}
%>