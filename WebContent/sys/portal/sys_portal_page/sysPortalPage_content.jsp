<%@page import="java.net.URLEncoder"%>
<%@page import="com.landray.kmss.sys.portal.util.SysPortalInfo"%>
<%@page import="com.landray.kmss.sys.portal.util.PortalUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page errorPage="/resource/jsp/jsperror.jsp" %>
<%
	String fdId = request.getParameter("fdId");
	SysPortalInfo info = new SysPortalInfo();	
	PortalUtil.getSysPortalPageInfo(info,fdId);
	String path = PortalUtil.getPortalPageJspPath(info);
	if("true".equals(request.getParameter("j_content"))){
		path += (path.indexOf("?") > -1 ? "&" : "?") + "j_content=true";
	}else{
		SysPortalInfo defaultInfo = PortalUtil.viewDefaultPortalInfo(request);
		if(defaultInfo.getPortalIsQuick()){
			path = "/sys/portal/template/quick/index.jsp?j_start=" + URLEncoder.encode(path);
		}
	}
	request.getRequestDispatcher(path).forward(request, response);
%>