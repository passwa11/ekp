<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String viewerStyle = request.getAttribute("viewerStyle").toString();
	if (viewerStyle.toLowerCase().contains("aspose")) {
		request.getRequestDispatcher(
				"/sys/attachment/mobile/viewer/aspose/aspose_mobilehtmlviewer.jsp")
				.forward(request, response);
	}
	if (viewerStyle.toLowerCase().contains("yozo")) {
		request.getRequestDispatcher(
				"/sys/attachment/mobile/viewer/yozo/yozo_mobilehtmlviewer.jsp")
				.forward(request, response);
	}
%>