<%@ page language="java" pageEncoding="UTF-8"%>
<%
	request.getRequestDispatcher("/sys/attachment/sys_att_main/sysAttMain.do?method=downloadFile")
        .forward(request, response);
%>