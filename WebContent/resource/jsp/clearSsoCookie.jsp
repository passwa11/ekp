<%@page import="com.landray.kmss.sys.authentication.token.TokenGenerator"%>
<%@ page language="java" contentType="application/x-javascript; charset=UTF-8" pageEncoding="UTF-8" %>
<%


if(TokenGenerator.isInitialized() ){
	TokenGenerator generator = TokenGenerator.getInstance();
	if(generator==null){
		return;
	}
	String appurl = request.getRequestURL().toString();
	if (appurl.startsWith("http")) {
		appurl = appurl.substring(appurl.indexOf("//") + 2);
	}
	if (appurl.indexOf("/") > 0) {
		appurl = appurl.substring(0, appurl.indexOf("/"));
	}
	if (appurl.indexOf(":") > 0) {
		appurl = appurl.substring(0, appurl.indexOf(":"));
	}
	String domain = generator.getSuitedDomain(appurl);
	response.addHeader(
			"Set-Cookie",
			generator.getCookieName() + "="
					+ ""
					+ "; Version=0; Domain=" + domain
					+ "; Path=/");
	}
%>
