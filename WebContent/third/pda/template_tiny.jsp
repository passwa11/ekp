<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.ui.util.SysUiConfigUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<c:set var="lang" value="<%=ResourceUtil.getLocaleByUser().getCountry()%>" />
<!DOCTYPE HTML>
<html class="mobile mui-${lang.toLowerCase()}-html  <%="true".equals(SysUiConfigUtil.getFdIsSysMourning()) ? "mourning" : ""%>">
	<head>
		<meta name="viewport" 
			content="width=device-width<c:if test="${param.scale == 'true'}">,target-densitydpi=device-dpi</c:if><c:if test="${param.scale != 'true'}">,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no</c:if>" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta names="apple-mobile-web-app-status-bar-style" content="black-translucent" />
		<meta content="telephone=no" name="format-detection"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><template:block name="title" /></title>
		<mui:cache-file name="common-tiny.css" cacheType="md5"/>
		<c:if test="${param.compatibleMode eq 'true' or param.compatibleMode eq '1'}">
			<c:import url="/sys/mobile/template/com_head.jsp"></c:import>
		</c:if>
		<template:block name="head" />
	</head>
	<body class="muiLoginPage">
		
		<div id="content">
			<template:block name="content" />
		</div>
		
		<script>
			$(function(){
				setTimeout(function(){
					preLoading('<mui:min-cachepath name="dojo.js"/>');
					preLoading('<mui:min-cachepath name="mui-common.js"/>');	
				},3000);
			})
		</script>
		
	</body>
</html>
