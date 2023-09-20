<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="java.util.Date"%>
<%

	JSONArray data = new JSONArray();
	JSONObject o = null;
	JSONArray times = null;
	JSONArray timesItem = null;
	
	o = new JSONObject();
	o.put("name", "通用A班次");
	o.put("color", "#3CCC50");
	
	times = new JSONArray();
	timesItem = new JSONArray();
	timesItem.add("08:00");
	timesItem.add("12:00");
	times.add(timesItem);
	timesItem = new JSONArray();
	timesItem.add("14:00");
	timesItem.add("18:00");
	times.add(timesItem);
	o.put("times", times);
	
	data.add(o);
	
	o = new JSONObject();
	o.put("name", "通用B班次");
	o.put("color", "#9514D1");
	
	times = new JSONArray();
	timesItem = new JSONArray();
	timesItem.add("08:00");
	timesItem.add("12:00");
	times.add(timesItem);
	timesItem = new JSONArray();
	timesItem.add("14:00");
	timesItem.add("18:00");
	times.add(timesItem);
	o.put("times", times);
	
	data.add(o);
	
	request.setAttribute("lui-source", data);
	request.getRequestDispatcher("/sys/ui/jsp/json.jsp").forward(request, response);
%>