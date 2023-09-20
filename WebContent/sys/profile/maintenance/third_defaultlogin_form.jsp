<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.landray.kmss.framework.service.plugin.Plugin"%>
<%@ page import="com.landray.kmss.sys.profile.interfaces.IThirdLogin"%>
<%@ page import="com.landray.kmss.sys.profile.model.PasswordSecurityConfig"%>
<%@ page import="com.landray.kmss.sys.profile.util.ThirdLoginPluginUtil"%>
<%@ page import="com.landray.kmss.framework.plugin.core.config.IExtension"%>
<%
	IExtension[] extensions = ThirdLoginPluginUtil.getExtensions();
	PasswordSecurityConfig passwordSecurityConfig = PasswordSecurityConfig.newInstance();
	String thirdLoginType = passwordSecurityConfig.getThirdLoginType();
	for(IExtension extension : extensions){
		IThirdLogin bean = (IThirdLogin)Plugin.getParamValue(extension, "bean");
		boolean checked =  thirdLoginType.indexOf(bean.key()) > -1 ? true : false;
		if(bean.cfgEnable() && bean.isDefault() && checked){
			Map<String,String> map = new HashMap<String,String>();	
			map.put("key", bean.key());
			map.put("name", bean.name());
			map.put("url", bean.loginUrl());
			request.setAttribute("defaultLoginConfig", map);
			break;
		}
	}
%>
