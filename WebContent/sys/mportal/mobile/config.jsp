<%@page import="net.sf.json.JSONObject"%>
<%@page
	import="com.landray.kmss.sys.mportal.compressor.CompressJsRunner"%>
<%@ page language="java" contentType="text/javascript; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	StringBuilder sb = new StringBuilder();
	sb.append("define([], function() {");
	sb.append("return " + CompressJsRunner.getConfig().toString());
	sb.append("});");
	out.print(sb.toString());
%>

