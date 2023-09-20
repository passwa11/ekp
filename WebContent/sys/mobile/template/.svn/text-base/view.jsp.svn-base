<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.ui.util.SysUiConfigUtil"%>
<%@ page import="com.landray.kmss.sys.mobile.util.MobileFormUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/template.tld"
	prefix="template"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%
	boolean transformScale = MobileFormUtil.transformScale(request);
	request.setAttribute("transformScale",transformScale);
%>
<%@ include file="./tripartiteAdmin.jsp"%>
<c:set var="include" value="false"/>
<c:if test="${param.include eq 'true'}">
    <c:set var="include" value="true"/>
</c:if>
<c:if test="${include eq 'false'}">
    <!DOCTYPE HTML>
</c:if>
<c:set var="ui" value="oldMui" />
<c:set var="lang" value="<%=ResourceUtil.getLocaleByUser().getCountry()%>" />
<c:if test="${param.newMui eq 'true'}">
    <c:set var="ui" value="newMui" />
</c:if>
<c:if test="${include eq 'false'}">
<html class="mobile mui-${lang.toLowerCase()}-html <%="true".equals(SysUiConfigUtil.getFdIsSysMourning()) ? "mourning" : ""%>">
<head>
<meta name="viewport"
	content="viewport-fit=cover<c:if test="${param.scale != 'true'}">,width=device-width,initial-scale=1,minimum-scale=1<c:if test="${requestScope['transformScale'] == false}">,maximum-scale=1,user-scalable=no</c:if></c:if>" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style"
	content="black-translucent" />
<meta content="telephone=no" name="format-detection" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><template:block name="title" /></title>
<c:if test="${param.s_console == true }">
	<mui:cache-file path="/sys/mobile/js/lib/vconsole/vconsole.js"
		cacheType="md5" />
	<script type="text/javascript">
		new VConsole()
	</script>
</c:if>

<c:choose>
	<c:when test="${param.tiny eq 'true' || tiny eq 'true'}">
		<mui:cache-file name="common-tiny.css" cacheType="md5" />
		<mui:cache-file name="view-tiny.css" cacheType="md5" />
	</c:when>
	<c:otherwise>
		<mui:cache-file name="common.css" cacheType="md5" />
		<mui:cache-file name="view.css" cacheType="md5" />
	</c:otherwise>
</c:choose>

<template:block name="csshead" />
</head>
<body>
	<div id="pageLoading">
		<template:block name="loading">
			<ui:combin
				ref="${not empty param.loadRef ? param.loadRef : 'loading.default' }">
				<c:if test="${not empty param.loadCfg }">
					<ui:varParam name="config" value="${param.loadCfg }"></ui:varParam>
				</c:if>
			</ui:combin>
		</template:block>
	</div>

	<%@ include file="./dojoConfig.jsp"%>

	<mui:cache-file name="dojo.js" cacheType="md5" />

	<c:choose>
		<c:when test="${param.tiny eq 'true' || tiny eq 'true'}">
			<mui:cache-file name="mui-common.js" cacheType="md5" />
			<mui:cache-file name="mui-view.js" cacheType="md5" />
		</c:when>
		<c:otherwise>
			<mui:cache-file name="mui.js" cacheType="md5" />
		</c:otherwise>
	</c:choose>

	<c:if
		test="${param.compatibleMode eq 'true' or param.compatibleMode eq '1'}">
		<c:import url="/sys/mobile/template/com_head.jsp"></c:import>
	</c:if>
	<template:block name="head" />

	<div id="content"
		<xform:viewShow>class="muiTemplateView"</xform:viewShow>>
		<template:block name="content" />
	</div>

	<script type="text/javascript">

		if ('${param.newMui}' == 'true') {
			dojoConfig.newMui = true;
		}
		if('${param.isNative}' == 'true'){
			dojoConfig._native = true;
		}
		if('${param.tiny}' == 'true'){
			dojoConfig.tiny = true;
		}
		<c:choose>
			<c:when test="${param.tiny eq 'true' || tiny eq 'true'}">
				window.onload = function() {
					require([ "dojo/parser", "mui/main", "mui/pageLoading",
							"dojo/_base/window", "dojox/mobile/sniff", "dojo/topic" , "mui/form" ], function(
							parser, main, pageLoading, win, has, topic) {
						try {
							setTimeout(function() {
								parser.parse().then(function() {
									win.doc.dojoClick = 	!has('ios') || has('ios')>13;
									pageLoading.hide();
									topic.publish('parser/done');
								});
							}, 1);

						} catch (e) {
						}
					});
				}
			</c:when>
			<c:otherwise>
				require(["dojo/parser", "mui/main", "mui/pageLoading", "dojo/_base/window","dojox/mobile/sniff", "dojo/topic" ,"dojo/domReady!" , "mui/form"],
						function(parser, main, pageLoading, win, has, topic){
					try {
						parser.parse().then(function() {
							win.doc.dojoClick = 	!has('ios') || has('ios')>13;
							pageLoading.hide();
							topic.publish('parser/done');
						});
					} catch (e) {
					}
				});
			</c:otherwise>
		</c:choose>

	</script>
	<div data-dojo-type="mui/top/Top"
		data-dojo-mixins="mui/top/_TopViewMixin"
		data-dojo-props="bottom:'${param.sideTop}'"></div>
	<%@ include file="/resource/jsp/watermarkMobile.jsp" %>
</body>
</html>
</c:if>
<c:if test="${include eq 'true'}">
    <template:block name="head" />
    <div id="content">
        <template:block name="content" />
    </div>
</c:if>