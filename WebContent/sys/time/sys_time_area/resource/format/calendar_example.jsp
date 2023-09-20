<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="java.util.Date"%>
<%

	JSONArray data = new JSONArray();

	JSONObject o = new JSONObject();
	o.put("fromDate", "12/01/2017");
	o.put("toDate", null);
	o.put("fromWeek", 1);
	o.put("toWeek", 4);
	o.put("name", "");
	o.put("type", 1);
	o.put("color", "#A5BFDA");
	data.add(o);
	
	o = new JSONObject();
	o.put("fromDate", "12/01/2017");
	o.put("toDate", null);
	o.put("fromWeek", 5);
	o.put("toWeek", null);
	o.put("name", "");
	o.put("type", 1);
	o.put("color", "#7AE7BF");
	data.add(o);
	
	o = new JSONObject();
	o.put("fromDate", "12/25/2017");
	o.put("toDate", null);
	o.put("fromWeek", null);
	o.put("toWeek", null);
	o.put("name", "圣诞节");
	o.put("type", 2);
	o.put("color", "#51B749");
	data.add(o);
	
	o = new JSONObject();
	o.put("fromDate", "12/24/2017");
	o.put("toDate", null);
	o.put("fromWeek", null);
	o.put("toWeek", null);
	o.put("name", "");
	o.put("type", 3);
	o.put("color", "#FFB878");
	data.add(o);
	
	request.setAttribute("lui-source", data);
	request.getRequestDispatcher("/sys/ui/jsp/json.jsp").forward(request, response);
%>