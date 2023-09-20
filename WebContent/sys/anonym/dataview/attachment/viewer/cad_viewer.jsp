<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String url = request.getContextPath()
			+ "/sys/attachment/sys_att_main/sysAttMain.do?method=view&viewer=cadviewer&fdId="
			+ request.getParameter("fdId")
			+ "&filekey=cadToImg-Aspose_png";

	response.sendRedirect(url);
%>
