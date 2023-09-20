<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld"
	prefix="portal"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script type="text/javascript">
	seajs.use([ 'theme!common', 'theme!portal', 'theme!zone' ]);
</script>
<title><template:block name="title" /></title>
<template:block name="head" />
</head>
<body class="<c:if test="${not empty param['j_iframe'] && param['j_iframe'] eq 'true' }">lui_zone_address_iframe_body</c:if>">
	<c:choose>
	<c:when test="${ not empty param['j_iframe'] && param['j_iframe'] eq 'true'}">
		<c:set var="frameWidth" scope="page" value="${(empty param.width) ? '90%' : fn:escapeXml(param.width)}"/>  
		<div class="lui_zone_address_iframe_wrap" style="width:${frameWidth}; min-width:980px;max-width:${fdPageMaxWidth}; margin:0px auto;">  
			<template:block name="path" />
			<template:block name="content" />
		</div>
	</c:when>
	<c:otherwise>
		<c:set var="frameWidth" scope="page" value="${(empty param.width) ? '90%' : (fn:escapeXml(param.width))}"/>  
		<portal:header var-width="${frameWidth}" />
		<div
			style="width:100%;">
			<template:block name="path" />
			<template:block name="content" />
		</div>
		<portal:footer />
		<ui:top id="top"></ui:top>
	</c:otherwise>
</c:choose>
	
	
</body>
</html>