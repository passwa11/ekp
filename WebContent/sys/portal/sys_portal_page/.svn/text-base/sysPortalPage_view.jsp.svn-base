<%@page import="com.landray.kmss.sys.portal.util.SysPortalInfo"%>
<%@page import="com.landray.kmss.sys.portal.util.PortalUtil"%>
<%@page import="com.landray.kmss.sys.portal.forms.SysPortalPageForm"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page errorPage="/resource/jsp/jsperror.jsp" %>
<%
	SysPortalPageForm pageForm = (SysPortalPageForm)request.getAttribute("sysPortalPageForm");	
	SysPortalInfo info = new SysPortalInfo();
	PortalUtil.getSysPortalPageInfo(info,pageForm.getFdId());
	info.setUsePortal("false");
	info.setPortalPageName(ResourceUtil.getString("portlet.theader.item.page", "sys-portal"));
	request.setAttribute("sys_portal_page_preview",info);
	if (info.getPageType().equals("2")) {
		//#90049 页面类型为url时的跳转问题
		String url = info.getPageUrl();
		//为空直接跳转
		if(StringUtil.isNull(url)){
			response.sendRedirect(url);
		}
		//包含根路径/ekp,直接跳转
		if(url.startsWith(request.getContextPath())){
			response.sendRedirect(url);
		}
		//否则需要处理，判断是否加上根路径
		response.sendRedirect(StringUtil.formatUrl(url,false));
	}else{
		String path = PortalUtil.getPortalPageJspPath(info);
		request.getRequestDispatcher(path).forward(request, response);
	}
%>