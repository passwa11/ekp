<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<!DOCTYPE HTML>
<html class="mobile">
	<head>
		<meta name="viewport" 
			content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no"/>
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
		<meta name="format-detection" content="telephone=no" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><template:block name="title" /></title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/ios7.css?s_cache=${MUI_Cache}"></link>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/icon/font-mui.css?s_cache=${MUI_Cache}"></link>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/common.css?s_cache=${MUI_Cache}"></link>
		<mui:dojo-file/>
		<mui:min-file name="mui.js"/>
		<template:block name="head" />
	</head>
	<body>
		<div id="pageLoading">
			<span>
				<i class="mui mui-loading mui-spin"></i>
				<div>${lfn:message('sys-mobile:mui.list.pull.loading') }</div>
			</span>
		</div>
		
		<div id="content">
			<template:block name="content" />
		</div>
		
		<script type="text/javascript">
		require(["mui/device/adapter"],function(adapter){
			adapter.ready(function(){
				// 绿色无公害可重复调用
				//adapter.setOrientation(1);
			});
		});
		
		require(["dojo/parser", "mui/main", "mui/pageLoading", "dojo/_base/window","dojox/mobile/sniff","dojo/domReady!"], 
				function(parser, main, pageLoading, win, has){
			try {
				parser.parse().then(function() {
					win.doc.dojoClick = false;
					pageLoading.hide();
				});
			} catch (e) {
				alert(e);
			}
		});
		</script>
	</body>
</html>
