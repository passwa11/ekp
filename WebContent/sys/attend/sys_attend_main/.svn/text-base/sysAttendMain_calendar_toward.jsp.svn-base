<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.mobile.util.MobileUtil" %>
<%@ page import="com.landray.kmss.sys.authentication.util.StringUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
	int clientType = MobileUtil.getClientType(request);
	if(clientType >-1){
		String categoryId = request.getParameter("categoryId");
		request.getRequestDispatcher("/sys/attend/mobile/index_stat.jsp?categoryId=" +categoryId).forward(request,response);
	}else{
		String fdId = request.getParameter("fdId");
		if(StringUtil.isNull(fdId)){
			request.getRequestDispatcher("/sys/attend/").forward(request,response);
		}else{
			request.getRequestDispatcher("/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId="+fdId).forward(request, response);
		}
	}
%>