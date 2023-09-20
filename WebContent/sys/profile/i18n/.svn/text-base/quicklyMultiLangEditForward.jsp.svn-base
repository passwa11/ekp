<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.alibaba.fastjson.JSONArray"%>
<%@ page import="com.alibaba.fastjson.JSONObject"%>

<%
	String viewName = (String)request.getAttribute("realViewName");
	Boolean isAjaxRequest = (Boolean) request.getAttribute("isAjaxRequest");
	if(StringUtil.isNull(viewName)){
		viewName = java.net.URLDecoder.decode(request.getParameter("realViewName"), "UTF-8");	
		isAjaxRequest = Boolean.valueOf(request.getParameter("isAjaxRequest"));
	}
%>
<c:import url="<%=viewName%>"></c:import>
<%
if(isAjaxRequest){  
	JSONArray quicklyEditKeys = (JSONArray) request.getAttribute("quicklyEditKeys");
	if(quicklyEditKeys != null && quicklyEditKeys.size() > 0){
		pageContext.getOut().append("----^o^----");
		pageContext.getOut().append("<messageKeys>");
		pageContext.getOut().append(quicklyEditKeys.toJSONString());
		pageContext.getOut().append("</messageKeys>");
	}
} 
%>
<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>