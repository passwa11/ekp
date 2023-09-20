<%@ page language="java" contentType="text/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="
	com.sunbor.web.tag.Page,
	com.alibaba.fastjson.JSONObject,
	com.landray.kmss.sys.config.util.enums.JsonErrorCode,
	com.landray.kmss.sys.config.util.JsonOutput,
	com.landray.kmss.sys.config.util.ConvertFormDictToJson" %>
<%
	String type = (String)request.getAttribute("type");
	if("e403".equals(type)){
		out.print(JsonOutput.returnMsg(JsonErrorCode.NO_AUTHORITY, null));
	}else if("e404".equals(type)){
		out.print(JsonOutput.returnMsg(JsonErrorCode.PAGE_NOT_FOUND, null));
	}else if("e500".equals(type)){
		out.print(JsonOutput.returnMsg(JsonErrorCode.SERVER_ERROR, null));
	}else if("e503".equals(type)){
		out.print(JsonOutput.returnMsg(JsonErrorCode.SERVICE_UNAVAILABLE, null));
	}else if("failure".equals(type)){
		ConvertFormDictToJson convert = new ConvertFormDictToJson();
		out.print(convert.getFailureJson(request));
	}
%>