<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.FileMimeTypeUtil"%>
<%@ page import="com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="com.landray.kmss.sys.organization.service.ISysOrgElementService"%>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrgElement"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.util.ServerTypeUtil"%>
<%
	response.reset();
	if (StringUtil.isNotNull(request.getHeader("If-Modified-Since"))) {
		response.setStatus(304);
	} else {
		long expires = 7 * 24 * 60 * 60;
		long nowTime = System.currentTimeMillis();
		response.addDateHeader("Last-Modified", nowTime + expires);
		response.addDateHeader("Expires", nowTime + expires * 1000);
		response.addHeader("Cache-Control", "max-age=" + expires);
		String orgId = request.getParameter("orgId");
		if(StringUtil.isNotNull(orgId)){
			ISysOrgElementService sysOrgElementService = (ISysOrgElementService)SpringBeanUtil.getBean("sysOrgElementService");
			SysOrgElement elem = (SysOrgElement)sysOrgElementService.findByPrimaryKey(orgId);
			response.setContentType(FileMimeTypeUtil
					.getContentType(PersonInfoServiceGetter.DEFAULT_IMG));
			if(elem.getFdOrgType() == 8){
				String src = PersonInfoServiceGetter.getPersonHeadimageUrl(orgId);
				if(src.startsWith("/")){
					request.getRequestDispatcher(src).forward(request, response);
				}else{
					response.sendRedirect(src);
				}
			}else if(elem.getFdOrgType() == 4){
				String src = "/sys/organization/resource/image/post.png";
				request.getRequestDispatcher(src).forward(
						request, response);
			}else{
				String src = "/sys/organization/resource/image/orgdept.png";
				request.getRequestDispatcher(src).forward(
						request, response);
			}
		} else {
			String src = "/sys/lbpmservice/mobile/resource/image/handlers.png";
			request.getRequestDispatcher(src).forward(
					request, response);
		}
		if(ServerTypeUtil.getServerType()!=ServerTypeUtil.WEBLOGIC){
			out.clear();  
			out = pageContext.pushBody(); 
		}
	}
%>