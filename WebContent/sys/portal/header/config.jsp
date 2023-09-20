<%@page import="com.landray.kmss.sys.ui.util.SysUiConfigUtil"%>
<%@page import="com.landray.kmss.sys.portal.util.PortalUtil"%>
<% 
	request.setAttribute("fdHeaderMaxWidth", 
		"quick".equals(PortalUtil.getPortalMode(request))|| PortalUtil.isTTemplate(request) ? 
				"100%" :  SysUiConfigUtil.getFdMaxWidth());
%>