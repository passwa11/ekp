<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.dbcenter.echarts.util.RequestParameterMap"%>
<%@page import="com.landray.kmss.dbcenter.echarts.execute.InputConverter"%>
<%@page import="com.landray.kmss.dbcenter.echarts.execute.DsExecutor"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String code = request.getParameter("code");
	JSONObject json = JSONObject.fromObject(code);
	InputConverter converter = new InputConverter(json, new RequestParameterMap(request.getParameterMap()));
	Map<String, Object> params = converter.execute();
	if(!params.containsKey("startIndex"))
		params.put("startIndex", 0);
	if(!params.containsKey("endIndex"))
		params.put("endIndex", 15);
	if(!params.containsKey("rowSize"))
		params.put("rowSize", 15);
	DsExecutor executor = new DsExecutor(json, params);
	executor.test();
	out.write(executor.getMessage());
%>