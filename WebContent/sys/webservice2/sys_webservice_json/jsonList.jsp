<%@ page language="java" contentType="text/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.sunbor.web.tag.Page,
	com.landray.kmss.sys.config.util.ConvertFormDictToJson" %>
<%
	Page queryPage = (Page)request.getAttribute("queryPage");
	ConvertFormDictToJson convert = new ConvertFormDictToJson();
	out.print(convert.convertJsonToStr(convert.pageDictToJsonObject(queryPage,request)));
%>