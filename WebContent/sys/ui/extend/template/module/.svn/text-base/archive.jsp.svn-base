<%@page import="com.landray.kmss.util.HtmlToMht"%>
<%@page import="org.apache.commons.logging.LogFactory"%>
<%@page import="java.util.Date"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.Iterator"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
	//来源校验 
	Cookie[] cookies = request.getCookies();
	String _fromSource = null;
	Cookie cookie = null;
	for(int i=0; i < cookies.length; i++){
		cookie = cookies[i];
		if(cookie.getName().equals("_fromSource")){
			_fromSource = cookie.getValue();
		}
	}
	if(!"ArChIvE".equals(_fromSource)){
		LogFactory.getLog(HtmlToMht.class).error("archive.jsp -> 权限错误");
		response.sendError(404);
		return;
	}
%>

<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<% 
	//引入css样式
	StringBuffer sb = new StringBuffer();
	String _contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+ "/";
	request.setAttribute("_contextPath", _contextPath);
	request.setAttribute("_cssCache", new Date().getTime()); 

	try{
		String jsonStr = SysUiPluginUtil.getThemes(request);
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Iterator iterator = json.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next().toString();
			if(json.get(key) instanceof JSONArray){
				JSONArray arr = (JSONArray)json.get(key);
				for(int i=0; i<arr.size(); i++){
					String href = arr.get(i).toString();
					if(!href.startsWith("http")) {
						href = _contextPath + href;
					}
					sb.append("<link charset=\"utf-8\" rel=\"stylesheet\" href=\"").append(href).append("\">\n");
				}
			}
		}
		request.setAttribute("____content____", "true");
	}catch(Exception e){
		e.printStackTrace();
	}
%>

<link charset="utf-8" rel="stylesheet" href="${_contextPath}sys/archives/resource/css/archive.css?cache=${_cssCache}" />
<%=sb.toString() %>

<title>
	<template:block name="title" />
</title>
<template:block name="head" />
</head>
<body class="lui_print_body">
<div id="optBtnBarDiv" class="btnprint">
<template:block name="toolbar"/>
</div>
<table class="tempTB" style="width:1000px; margin: 0px auto;">
	<tr>
		<td valign="top" class="lui_form_content_td">
			<div class="lui_form_content">
				<template:block name="content" />
			</div>
		</td>
	</tr>
</table>
<div style="height:20px;"></div>
<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
</body>
</html>

