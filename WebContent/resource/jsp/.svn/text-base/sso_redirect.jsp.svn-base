<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.landray.kmss.util.*"%>
<%@page import="com.landray.kmss.constant.*"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%
	String url = request.getParameter("url");
	url = SecureUtil.BASE64Decoder(url);
	int queryIndex = url.indexOf("?");
	String url_pre = queryIndex > -1 ? url.substring(0, queryIndex)
			: url;
	String queryString = queryIndex > -1 ? url.substring(queryIndex+1)
			: "";
	String ssoRedirectUrl = ResourceUtil
			.getKmssConfigString("kmss.ssoclient.redirect.loginURL");
	if (StringUtil.isNotNull(ssoRedirectUrl)) {
		ssoRedirectUrl = ssoRedirectUrl
				.replace("${url}", java.net.URLEncoder.encode(url))
				.replace("${URL}", java.net.URLEncoder.encode(url));
		ssoRedirectUrl = ssoRedirectUrl + "&outopen=true";
		url = ssoRedirectUrl;
	}else{
		try{
			String openType = com.landray.kmss.third.ding.model.DingConfig.newInstance().getDingTodoPcOpenType();
			if("out".equals(openType)){
				queryString = StringUtil.setQueryParameter(queryString, "dingOut", "true");
			}else{
				queryString = StringUtil.setQueryParameter(queryString, "dingOut", "false");
			}
			url = url_pre+"?"+queryString;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	response.sendRedirect(url);
%>

