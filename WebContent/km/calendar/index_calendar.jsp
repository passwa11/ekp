<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@ page import="com.landray.kmss.common.actions.RequestContext"%>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%
	String id = request.getParameter("fdId");
	String r = request.getParameter("r");
	if(StringUtil.isNotNull(id)){
		//UA=mobile跳转到移动端的主页(临时解决方案)
		if(MobileUtil.getClientType(new RequestContext(request)) > -1){
			response.sendRedirect("km_calendar_main/kmCalendarMain.do?method=view&fdId="+id);
		}
		else{
			response.sendRedirect("index.jsp?fdId="+id+"&r="+(StringUtil.isNotNull(r)?r:""));
		}
	}
	else{
		response.sendRedirect("index.jsp");
	}
%>