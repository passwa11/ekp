<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<!DOCTYPE HTML>
<html class="mobile">
	<head>
		<meta name="viewport" 
			content="<c:if test="${param.scale != 'true'}">width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no</c:if>" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
		<meta content="telephone=no" name="format-detection"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><template:block name="title" /></title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/ios7.css?s_cache=${MUI_Cache}"></link>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/icon/font-mui.css?s_cache=${MUI_Cache}"></link>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/common.css?s_cache=${MUI_Cache}"></link>
		<mui:min-file name="view.css"/>
		<mui:dojo-file/>
		<mui:min-file name="mui.js"/>
		<c:if test="${param.compatibleMode eq 'true' or param.compatibleMode eq '1'}">
			<c:import url="/sys/mobile/template/com_head.jsp"></c:import>
		</c:if>
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
		
		require(["dojo/parser","dojo/request","dojo/Deferred","mui/main","mui/util", "mui/pageLoading", "dojo/_base/window","dojox/mobile/sniff" ,"dojo/domReady!"], 
				function(parser, request, Deferred, main, util, pageLoading, win , has){
			try {
				var defer = new Deferred(),
					url = '${param.offlineData}';
				if(url){
					var searchJSON = util.urlParse(location.href).searchJSON,
						url = util.setUrlParameterMap(url,searchJSON);
					url = util.formatUrl(url);
					request.post(url).then(function(html){
						var content = document.getElementById('content');
						util.setInnerHTML(content,html);
						defer.resolve();
					});
				}else{
					defer.resolve();
				}
				defer.then(function(){
					parser.parse().then(function() {
						<%--
						//win.doc.dojoClick = false;影响 andriod的 on事件，导致部分on设置事件失效
						//win.doc.dojoClick = true; 导致 iphone中 file事件的onchange事件无法触发
						 --%>
						win.doc.dojoClick = !has('ios');
						pageLoading.hide();
					});
				});
			} catch (e) {
				//alert(e);
			}
		});
		</script>
		 <div data-dojo-type="mui/top/Top" 
		 	data-dojo-mixins="mui/top/_TopViewMixin" 
			data-dojo-props="bottom:'${param.sideTop}'"></div>
	</body>
</html>
