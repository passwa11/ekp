<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.person.service.plugin.*" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.web.filter.security.TrustSiteChecker" %>
<%@ page import="com.landray.kmss.sys.profile.model.PasswordSecurityConfig" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String[] domains = TrustSiteChecker.getTrustSite();

	String url = request.getParameter("url");

	Boolean pass = false;

	if (domains != null && domains.length > 0) {
		if((url.startsWith("/")||url.contains(request.getServerName()))&&!url.contains("file://"))
			pass = true;
		for (int i = 0; i < domains.length; i++) {
			if("*".equals(domains[i])){
				pass = true;
				break;
			}

			if (StringUtil.isNotNull(domains[i])) {
				if(url.contains(domains[i])){
					pass = true;
					break;
				}
			}
		}
	}
	if(pass==false && "true".equalsIgnoreCase(PasswordSecurityConfig.newInstance().getKmssRedirecttoCheck()))
		throw new Exception("该url为非法url，如需跳转请在后台 安全管控-信任站点 处添加可信域名");
%>
<template:include ref="person.home">
	<template:replace name="content">
		<iframe id="mainIframe" style="width: 100%;border: 0px;height:800px;" src="${HtmlParam.url }"></iframe>
		<script>
		domain.register('fireEvent', function(evt) {
			$('#mainIframe').height(evt.data.height);
		});
		</script>
	</template:replace>
</template:include>