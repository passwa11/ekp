<%@ page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.landray.kmss.util.*,com.landray.kmss.third.ding.constant.DingConstant" %>
<%
String fdId = request.getParameter("id");
String type = request.getParameter("type");
String url = request.getParameter("url");
String redirctUrl=request.getParameter("pg");
String domain = DingConfig.newInstance().getDingDomain();
if(StringUtil.isNotNull(url)&&!"null".equalsIgnoreCase(url)){
	redirctUrl = SecureUtil.BASE64Decoder(url);
}
if("todo".equals(type)&&StringUtil.isNotNull(fdId)){
	redirctUrl = "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="+fdId;
}
if(StringUtil.isNotNull(domain)){
	redirctUrl=StringUtil.formatUrl(redirctUrl,domain);
}else{
	redirctUrl= StringUtil.formatUrl(redirctUrl);
}
redirctUrl = org.apache.commons.lang.StringEscapeUtils.escapeHtml(redirctUrl);
redirctUrl = redirctUrl.replaceAll("&amp;", "&");
%>
<script>

	window.onload=function(){
		window.location.href="<%=redirctUrl%>";
	} 

</script>
