<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.log.util.UserOperHelper"%>
<%@page import="com.landray.kmss.util.ClassUtils"%>
<%@page import="com.landray.kmss.util.ResourceUtil" %>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.net.URL"%>
<%@ page import="java.util.Enumeration" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body style="margin:20px;">
<form action="classtools.jsp" method="POST">
	<input type="hidden" name="_t" value="1">
	${ lfn:message('sys-admin:sysAdmin.classtools.className') }：<input name="fdClassName" value="${HtmlParam.fdClassName}" style="width:500px;">
	<input type="submit" value="${ lfn:message('sys-admin:sysAdmin.classtools.check') }">
</form>
<hr>
<div style="line-height:25px;">
<%
	String className = request.getParameter("fdClassName");
	if(className!=null && className.trim().length()>0){
		// 记录日志
		if(UserOperHelper.allowLogOper("check", null)) {
			UserOperHelper.setEventType(ResourceUtil.getString("sys-admin:sysAdmin.classtools"));
			UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil.getString("sys-admin:home.nav.sysAdmin"));
		}
		StringBuffer message = new StringBuffer();
		className = className.trim();
		out.write("<b>"+ResourceUtil.getString("sysAdmin.classtools.clz","sys-admin")+" "+StringEscapeUtils.escapeHtml(className)+" "+ResourceUtil.getString("sysAdmin.classtools.clzDefine","sys-admin")+"：</b><br>");
		message.append("\r\n").append(ResourceUtil.getString("sysAdmin.classtools.clz","sys-admin")+" "+className+" "+ResourceUtil.getString("sysAdmin.classtools.clzDefine","sys-admin")+"：\r\n\r\n");
		Enumeration<URL> en = Thread.currentThread().getContextClassLoader().getResources(className.replace('.','/')+".class");
		while(en.hasMoreElements()){
			URL url = en.nextElement();
			out.write(url+"<br>");
			message.append(url).append("\r\n");
		}
		out.write("<br><b>");
		message.append("\r\n");
		try{
			Class<?> clz = ClassUtils.forName(className);
			out.write(ResourceUtil.getString("sysAdmin.classtools.clzSysUse", "sys-admin")+"："+clz.getResource(clz.getSimpleName()+".class"));
			message.append(ResourceUtil.getString("sysAdmin.classtools.clzSysUse", "sys-admin")+"："+clz.getResource(clz.getSimpleName()+".class"));
		}catch(ClassNotFoundException e){
			out.write(ResourceUtil.getString("sysAdmin.classtools.clzNotFound", "sys-admin"));
			message.append(ResourceUtil.getString("sysAdmin.classtools.clzNotFound", "sys-admin"));
		}
		out.write("</b>");
		// 记录日志
		if(UserOperHelper.allowLogOper("check", null)) {
			UserOperHelper.logMessage(message.toString());
		}
	}
%>
</div>
</body>
</html>