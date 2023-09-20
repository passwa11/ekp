
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="net.sf.json.JSONObject"%><%@ page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
	<%
	String convertFileStatus = JgWebOffice.getConvertFileStatus(request);
	String promptInfo = "";
	if("-2".equals(convertFileStatus)){
		promptInfo = "文件转换服务未开启，暂无法在线查看文件，请联系管理员!";
	}else if("-1".equals(convertFileStatus)){
		promptInfo = "文件转换失败，请联系管理员!";
	}else if("0".equals(convertFileStatus)|| "1".equals(convertFileStatus)){
		promptInfo = "文件正在转换中，请稍后再打开!";
	}
	if(promptInfo != "")
	{
		request.setAttribute("promptInfo", promptInfo);
		request.getRequestDispatcher("/sys/attachment/sys_att_main/wps/sysAttMain_wps_prompt.jsp").forward(request,
				response);
		return;
	}

	String pageNum = request.getParameter("pageNum");
	request.setAttribute("currentPage", StringUtil.isNotNull(pageNum) ? pageNum : "1");
	String toolPosition = request.getParameter("toolPosition");
	if(StringUtil.isNotNull(toolPosition))
		toolPosition = org.apache.commons.lang.StringEscapeUtils.escapeHtml(toolPosition);
	request.setAttribute("toolPosition", toolPosition);
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
		request.getRequestDispatcher("/sys/attachment/viewer/aspose/aspose_htmlviewer.jsp").forward(request,
				response);
	}
	if (viewerStyle.toLowerCase().contains("yozo")) {
		request.getRequestDispatcher("/sys/attachment/viewer/yozo/yozo_htmlviewer.jsp").forward(request,
				response);
	}
%>