
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="net.sf.json.JSONObject"%><%@ page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String pageNum = request.getParameter("pageNum");
	request.setAttribute("currentPage", StringUtil.isNotNull(pageNum) ? pageNum : "1");
	request.setAttribute("toolPosition", request.getParameter("toolPosition"));
	request.setAttribute("showAllPage",
			request.getParameter("showAllPage") == null ? "false" : request.getParameter("showAllPage"));
	request.setAttribute("newOpen", request.getParameter("newOpen"));
	String fullScreen = request.getParameter("fullScreen");
	request.setAttribute("fullScreen", StringUtil.isNull(fullScreen) ? "no" : fullScreen);
	String viewerParam = request.getAttribute("viewerParam").toString();
	String viewerStyle = request.getAttribute("viewerStyle").toString();
	String attIconName = "";
	if (viewerStyle.toLowerCase().contains("pdf")) {
		attIconName = "pdf.png";
	}
	if (viewerStyle.toLowerCase().contains("excel")) {
		attIconName = "excel.png";
	}
	if (viewerStyle.toLowerCase().contains("excelet")) {
		attIconName = "et.png";
	}
	if (viewerStyle.toLowerCase().contains("word")) {
		attIconName = "word.png";
	}
	if (viewerStyle.toLowerCase().contains("ppt")) {
		attIconName = "ppt.png";
	}
	request.setAttribute("attIconName", attIconName);
	if (viewerStyle.toLowerCase().contains("aspose")) {
		request.getRequestDispatcher("/sys/anonym/dataview/attachment/viewer/aspose/aspose_htmlviewer.jsp").forward(request,
				response);
	}
	if (viewerStyle.toLowerCase().contains("yozo")) {
		request.getRequestDispatcher("/sys/anonym/dataview/attachment/viewer/yozo/yozo_htmlviewer.jsp").forward(request,
				response);
	}
%>