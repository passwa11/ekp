<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.third.pda.util.PdaFlagUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils" %>
<%
	/*
		解决在移动端点开二级页面链接还是在pc端的问题
		使用方式：在二级页面引入模板的地方写上移动端url,url中的!{xxx}会替换成pc端url的参数xxx(包括hash带的参数)的值
		例如：
		pc端二级页面链接为：/km/doc/?docCategory=123 或 /km/doc/?xxx=1#docCategory=123
		则可在pc端二级页面这样写:<template:include mobileUrl="/km/doc/mobile/?categoryId=!{docCategory}"/>
	*/
	if(PdaFlagUtil.checkClientIsPda(request)) {
		String mobileUrl = request.getParameter("mobileUrl");
		mobileUrl = StringEscapeUtils.escapeJavaScript(mobileUrl);
		if(StringUtil.isNotNull(mobileUrl)) {
		%>
			<script type="text/javascript" src="<%=request.getContextPath()%>/sys/ui/js/mobile_redirect.js?s_cache=${HtmlParam.LUI_Cache}"></script>
			<script>
				window.luiMobileRedirect("<%=mobileUrl%>", "<%=request.getContextPath()%>");
			</script>
		<%
		} else {
			String redirectUrl = PdaFlagUtil.moduleHomeRedirectWhenMobile(request);
			if(redirectUrl != null) {
			%>
			<script>
				location.replace('<%=redirectUrl%>');
			</script>
			<%
			}
		}
	}
%>
	
