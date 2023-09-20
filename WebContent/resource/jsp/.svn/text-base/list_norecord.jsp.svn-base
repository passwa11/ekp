<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
	JSONArray prompt = (JSONObject.fromObject(SysUiPluginUtil
			.getThemes(request))).getJSONArray("listview");
	for (int i = 0; i < prompt.size(); i++){
%>
<link type="text/css" rel="stylesheet"
	href="${LUI_ContextPath }/<%=prompt.get(i)%>" />
<%
	}
%>
<%@ include file="/resource/jsp/listview_norecord.jsp"%>
