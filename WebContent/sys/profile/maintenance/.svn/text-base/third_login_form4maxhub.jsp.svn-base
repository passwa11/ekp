<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.landray.kmss.sys.profile.model.PasswordSecurityConfig"%>
<%@ page import="com.landray.kmss.sys.profile.util.ThirdLoginPluginUtil"%>
<%@ page import="com.landray.kmss.framework.plugin.core.config.IExtension"%>
<%@ page import="com.landray.kmss.framework.service.plugin.Plugin"%>
<%@ page import="com.landray.kmss.sys.profile.interfaces.IThirdLogin"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%
	IExtension[] extensions = ThirdLoginPluginUtil.getExtensions();
	List<Map<String,String>> collection = new ArrayList<Map<String,String>>();
	PasswordSecurityConfig passwordSecurityConfig = PasswordSecurityConfig.newInstance();
	String thirdLoginType = passwordSecurityConfig.getThirdLoginType();
	for(IExtension extension : extensions){
		IThirdLogin bean = (IThirdLogin)Plugin.getParamValue(extension, "bean");
		if(thirdLoginType.indexOf(bean.key()) > -1 && bean.isDefault() == false){
			Map<String,String> map = new HashMap<String,String>();
			map.put("name", bean.name());
			map.put("icon", bean.iconUrl());
			map.put("url", bean.loginUrl());
			collection.add(map);
		}
	}
	request.setAttribute("thirdLoginCollection", collection);
%>
<c:if test="${fn:length(thirdLoginCollection) > 0 }">
	<div id="third_login_form" class="mhuiThirdLogin">
		<div class="mhuiThirdLoginHeader">
			<span>第三方账号登录</span>
		</div>
		<ul class='mhuiThirdLoginList'>
		<c:forEach items="${thirdLoginCollection}" var="thirdLogin">
			<li class="mhuiThirdLoginItem">
				<a  href="${ thirdLogin.url}" title="${thirdLogin.name }"><img class="mhuiThirdLoginIcon" src="${thirdLogin.icon}"></i></a>
			</li>
		</c:forEach>
		</ul>
	</div>
</c:if>