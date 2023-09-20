<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String viewerStyle = request.getAttribute("viewerStyle").toString();
	if (viewerStyle.toLowerCase().contains("excel")) {
		request.getRequestDispatcher(
				"/sys/anonym/dataview/attachment/viewer/yozo/yozo_htmlexcelviewer.jsp")
				.forward(request, response);
	} else {
		request.getRequestDispatcher(
				"/sys/anonym/dataview/attachment/viewer/yozo/yozo_htmlpageviewer.jsp")
				.forward(request, response);

	}
%>