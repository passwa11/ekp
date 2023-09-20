<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@page import="com.landray.kmss.sys.language.utils.SysLangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	response.addHeader("isloginpage","true");
%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<template:block name="head">
</template:block>
<script type="text/javascript">
	function checkLocation(){
		if(top==window){
			return;
		}
		domain.call(window.top,"login");
	}
	try{
		checkLocation();
	}catch(e){window.console(e);}
</script>
<title><%
	String title = LoginTemplateUtil.getConfigLangValue(request, "title", ResourceUtil.getString(request.getSession(),"login.title"));
	out.print(title);
%></title>
</head>
<body>
  <template:block name="body">
  </template:block>
</body>
</html>
