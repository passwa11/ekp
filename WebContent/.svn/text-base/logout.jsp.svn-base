<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices" %>
<%@ page import="org.springframework.security.authentication.UsernamePasswordAuthenticationToken"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="org.springframework.security.core.Authentication"%>

<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>

<%@ page import="com.landray.kmss.web.Globals"%>

<%@ page import="com.landray.kmss.web.filter.security.TrustSiteChecker"%>
<%@ page import="com.landray.kmss.framework.plugin.core.config.IExtension" %>
<%@ page import="com.landray.kmss.framework.service.plugin.Plugin" %>
<%@ page import="com.landray.kmss.sys.log.service.ISysLogLogoutService" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page import="com.landray.kmss.sys.authentication.CredentialCache"%>
<%@ page import="com.landray.kmss.sys.authentication.user.KMSSUserAuthInfoCache"%>
<%@ page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%@ page import="com.landray.kmss.sys.log.util.UserOperHelper"%>
<%@ page import="com.landray.kmss.framework.plugin.core.config.IExtensionPoint" %>
<%@ page import="com.landray.kmss.sys.attachment.interfaces.ISysAttachmentLogoutProvider" %>
<%@ page import="com.landray.kmss.util.*" %>

<%
	String value = ResourceUtil.getKmssConfigString("kmss.isLogLoginEnabled");
    KMSSUser user = UserUtil.getKMSSUser(request);
    if(!user.isAnonymous() && UserOperHelper.isLogEnabled()) {
	    // 记录登录日志
	    UserOperHelper.logoutInfo(request, user.getPerson());
	    UserOperHelper.setOperSuccess(true);
	    UserOperHelper.saveLog();
    }
	if (StringUtil.isNotNull(value) 
	        && value.equals("true") 
	        && !user.isAnonymous()) {
		ISysLogLogoutService sysLogLogoutService = (ISysLogLogoutService) WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext()).getBean("sysLogLogoutService");
		sysLogLogoutService.saveLogoutInfo(request.getRemoteAddr(),user.getPerson());
	}
	Object lang = session.getAttribute(Globals.LOCALE_KEY);
	session.setAttribute("manualLogout","true");
	Authentication authed = SecurityContextHolder.getContext().getAuthentication();
	if(authed!=null 
	        && !(authed instanceof UsernamePasswordAuthenticationToken)
	        && authed.getCredentials()!=null){
        CredentialCache.getInstance().set(authed.getCredentials(),authed.getName());
    }

	try {
		IExtensionPoint point = Plugin.getExtensionPoint("com.landray.kmss.sys.attachment.integrate.logout");
		IExtension[] extensions = point.getExtensions();
		if (extensions != null && extensions.length > 0) {
			for (IExtension extension : extensions) {
				ISysAttachmentLogoutProvider provider = (ISysAttachmentLogoutProvider) com.landray.kmss.util.SpringBeanUtil.getBean(Plugin.getParamValueString(extension, "provider"));
				provider.logout(user);
			}
		}
	} catch (Exception e) {

	}

	if(user!=null){
		KMSSUserAuthInfoCache.getInstance().remove(user.getUserId());
	}
	session.invalidate();
	
	request.getSession().setAttribute(Globals.LOCALE_KEY,lang);
	Cookie terminate = new Cookie(Globals.REMEMBER_ME_COOKIE_KEY, null);
	terminate.setMaxAge(0);
	response.addCookie(terminate);
	String logoutUrl = request.getParameter("logoutUrl");
	if(!TrustSiteChecker.check(logoutUrl, request, false)){
		logoutUrl = null;
	}
	if(StringUtil.isNull(logoutUrl)){
		IExtension extensioin = Plugin.getExtension(
				"com.landray.kmss.sys.authentication", "*", "redirectURL");
		if (extensioin != null) {
			logoutUrl = Plugin.getParamValueString(extensioin,
					"logoutUrl");
		}
		if(StringUtil.isNull(logoutUrl)){
			logoutUrl = request.getContextPath() + "/";
		}else{
			String homeUrl = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
			logoutUrl = StringUtil.replace(logoutUrl, "${url}", homeUrl);
		}
	}
	SecurityContextHolder.getContext().setAuthentication(null);
	response.sendRedirect(logoutUrl);
%>