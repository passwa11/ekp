<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.Cookie,
	com.landray.kmss.util.StringUtil,
	java.net.*,
	com.landray.kmss.sys.authentication.sso.LoginStringUtil"
%>
<%
	String ltpaToken = null;
	String token = request.getParameter("sessionId");
	String target = request.getParameter("target");
	String path = request.getContextPath();
	path = path.equals("") ? "/" : path;
	if(target==null){
		target = path;
	}else{
		target = URLDecoder.decode(target);
	}

	String cookieName = null;
	String domain = null;

	if(StringUtil.isNotNull(token)) {
		ltpaToken = LoginStringUtil.decryptLoginSessionId(token);
		if(StringUtil.isNotNull(ltpaToken)){
			LoginStringUtil.setCookieAndRedirect(request, response, ltpaToken, target);
			return;
		}
		//domain = LoginStringUtil.getCookieDomain();
		//cookieName = LoginStringUtil.getCookieName();
	}

	if(StringUtil.isNull(token) || StringUtil.isNull(ltpaToken)) {
		response.sendRedirect(path+"/login.jsp");
		return;
	}
%>

