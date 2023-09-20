<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%
	String viewerStyle = request.getAttribute("viewerStyle").toString();
	String fileKeySufix = "";
	String scaleStr = "true";
	String highFidelity = request.getAttribute("highFidelity").toString();
	if (viewerStyle.toLowerCase().contains("pdf")) {
		if (highFidelity.contains("html")) {
			fileKeySufix = "-svg";
		} else {
			fileKeySufix = "-img";
		}
	}
	if (viewerStyle.toLowerCase().contains("word")
			|| viewerStyle.toLowerCase().contains("ppt")) {
		if (SysAttViewerUtil.isLowerThanIE8(request)) {
			fileKeySufix = "-img";
		} else {
			if (highFidelity.contains("html")) {
				fileKeySufix = "-svg";
			} else if (highFidelity.contains("pic")) {
				fileKeySufix = "-img";
			} else {
				fileKeySufix = "-svg";
			}
		}
	}
	if (viewerStyle.toLowerCase().contains("excel")) {
		scaleStr = "false";
	}
	request.setAttribute("fileKeySufix", fileKeySufix);
	request.setAttribute("scaleStr", scaleStr);
	request.setAttribute("highFidelity", StringUtil.isNotNull(highFidelity)
			&& (highFidelity.contains("html") || highFidelity.contains("pic")) ? "true" : "false");
	request
			.getRequestDispatcher(
					"/sys/attachment/mobile/viewer/pageviewer/mobilehtmlpageviewer.jsp")
			.forward(request, response);
%>