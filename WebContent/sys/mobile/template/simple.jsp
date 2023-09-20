<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.ui.util.SysUiConfigUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ include file="./tripartiteAdmin.jsp" %>
<!DOCTYPE HTML>
<c:set var="ui" value="oldMui"/>
<c:set var="lang" value="<%=ResourceUtil.getLocaleByUser().getCountry()%>" />
<c:if test="${param.newMui eq 'true'}">
	<c:set var="ui" value="newMui"/>
</c:if>
<html class="mobile ${ui} mui-${lang.toLowerCase()}-html <%="true".equals(SysUiConfigUtil.getFdIsSysMourning()) ? "mourning" : ""%>">
	<head>
		<meta name="viewport" 
			content="viewport-fit=cover,width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no"/>
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
		<meta name="format-detection" content="telephone=no" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><template:block name="title" /></title>
		<c:if test="${param.s_console == true }">
			<mui:cache-file path="/sys/mobile/js/lib/vconsole/vconsole.js" cacheType="md5"/>
			<script type="text/javascript">new VConsole()</script>
		</c:if>
		
		<c:choose>
			<c:when test="${param.tiny eq 'true' || tiny eq 'true' }">
				<mui:cache-file name="common-tiny.css" cacheType="md5"/>
			</c:when>
			<c:otherwise>
				<mui:cache-file name="common.css" cacheType="md5"/>
			</c:otherwise>
		</c:choose>
		
		<%@ include file="./dojoConfig.jsp" %>
		<mui:cache-file name="dojo.js" cacheType="md5"/>
		<mui:cache-file name="mui-common.js" cacheType="md5"/>
		<template:block name="head" />
	</head>
	<body>
		<div id="pageLoading">
			<template:block name="loading">
				<ui:combin ref="${not empty param.loadRef ? param.loadRef : 'loading.default' }">
					<c:if test="${not empty param.loadCfg }">
						<ui:varParam name="config" value="${param.loadCfg }"></ui:varParam>
					</c:if>
				</ui:combin>
			</template:block>
		</div>
		<div id="content">
			<template:block name="content" />
		</div>
		<script type="text/javascript">
		
			<c:if test="${param.newMui == 'true'}">
				dojoConfig.newMui = true;
			</c:if>
			if('${param.isNative}' == 'true'){
				dojoConfig._native = true;
			}
			if('${param.tiny}' == 'true'){
				dojoConfig.tiny = true;
			}
		
		require(["dojo/parser", "mui/main", "mui/pageLoading", "dojo/_base/window", "dojo/topic", "dojo/domReady!"], 
				function(parser, main, pageLoading, win, topic){
			try {
				setTimeout(function(){
					parser.parse().then(function() {
						win.doc.dojoClick = false;
						pageLoading.hide();
						topic.publish('parser/done');
						setTimeout(function(){
							try{
								util.preLoading('<mui:min-cachepath name="sys-lbpm.js"/>');
								util.preLoading('<mui:min-cachepath name="sys-lbpm-note.js"/>');
							}catch(e){}
						},5000);
					});					
				},1)
				
			} catch (e) {
				alert(e);
			}
		});
		</script>
		<%@ include file="/resource/jsp/watermarkMobile.jsp" %>
	</body>
</html>
