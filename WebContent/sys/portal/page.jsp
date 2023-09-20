<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.portal.util.SysPortalInfo"%>
<%@page import="com.landray.kmss.sys.portal.util.PortalUtil"%>
<%@page errorPage="/resource/jsp/jsperror.jsp"%>
<%	
	SysPortalInfo xpage = PortalUtil.viewDefaultPortalInfo(request);
	if ("2".equals(xpage.getPageType())) { //外部页面，直接跳转
		response.sendRedirect(PortalUtil.formatUrl(request, StringUtil.formatUrl(xpage.getPageUrl(),false)));
	} else {
		String path = PortalUtil.getPortalPageJspPath(xpage);
		String mainPageId = request.getParameter("mainPageId");
		String portalId = request.getParameter("portalId");
		if(StringUtil.isNotNull(mainPageId)){
			SysPortalInfo info = new SysPortalInfo();	
			PortalUtil.getSysPortalPageInfo(info,mainPageId);
			path = PortalUtil.getPortalPageJspPath(info);
		}
		if(xpage.getPortalIsQuick() != null && xpage.getPortalIsQuick()){
			if("true".equals(request.getParameter("j_content"))){
				// 当j_content=true时返回HTML片段而不进入极速门户
				path += (path.indexOf("?") > -1 ? "&" : "?") +  "j_content=true";
			}else{
				//进入极速门户
				path = "/sys/portal/template/quick/index.jsp?j_start=" + URLEncoder.encode(path);
			}
		}
		if(StringUtil.isNotNull(portalId)){
			PortalUtil.setSysPortalPageLang(request,portalId);
		}
		String portalPageName = xpage.getPortalPageName();
		portalPageName = StringUtil.isNotNull(portalPageName) ? portalPageName.trim() : portalPageName;
		request.setAttribute("headerPortalPageName", portalPageName);
		request.getRequestDispatcher(path).forward(request, response);
	}
%>
