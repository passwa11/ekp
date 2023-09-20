<%@ page language="java" contentType="text/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="
	com.landray.kmss.sys.config.util.ConvertFormDictToJson" %>
<%
	Object form = (Object)request.getAttribute("form");
	ConvertFormDictToJson convert = new ConvertFormDictToJson();
	
	out.print(convert.convertJsonToStr(convert.formDictToJsonObject(form,request)));
%>