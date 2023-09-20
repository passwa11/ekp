<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="net.sf.json.JSONObject,com.landray.kmss.util.StringUtil"%>
<%
		String url = "/third/im/kk/kk_app/mobile/index.jsp";
		String data = request.getParameter("data");
		String params = "";
		if(StringUtil.isNotNull(data)){
			JSONObject json = JSONObject.fromObject(data);
			Object sessionId = json.get("sessionId");
			Object sessionType = json.get("sessionType");
			Object typeId = json.get("typeId");
			Object sessionName = json.get("sessionName");
			params = "?sessionId="+sessionId+"&sessionType="+sessionType+"&typeId="+typeId+"&sessionName="+sessionName;
		}
		url = url + params;
		response.sendRedirect(StringUtil.formatUrl(url));
		//request.getRequestDispatcher(url).forward(request,response);
%>
