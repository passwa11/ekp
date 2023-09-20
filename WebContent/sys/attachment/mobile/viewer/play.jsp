<%@ page language="java" pageEncoding="UTF-8"%>
<%

	request.getRequestDispatcher(
			"/sys/attachment/sys_att_main/sysAttMain.do?method=play&token="
					+ request.getParameter("token")).forward(request,
			response);
	out.clear();
	out = pageContext.pushBody();
%>