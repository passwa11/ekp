<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.landray.kmss.sys.mportal.xml.SysMportalMportlet"%>
<%@page import="com.landray.kmss.sys.mportal.plugin.MportalMportletUtil"%>

<%@ page language="java" pageEncoding="UTF-8"%>
<%
	SysMportalMportlet mportlet = MportalMportletUtil.getPortletById(request
			.getParameter("fdId"));
	if(mportlet == null){
		out.print("<div class='validation-advice'><span class='validation-advice-msg'><span class='validation-advice-title'>部件："+request.getParameter("fdId")+"不存在</span></span></div>");
	}else{
		request.setAttribute("vars",
				JSONArray.fromObject(mportlet.getFdVars()));
		request.getRequestDispatcher(
				"/sys/ui/jsp/vars/variable.jsp").forward(
				request, response);
	}
%>