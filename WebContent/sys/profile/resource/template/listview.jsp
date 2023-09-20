<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/profile/profile.tld" prefix="profile"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="renderer" content="webkit" />
	<template:block name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	</template:block>
	<title>
		<template:block name="title"></template:block>
	</title>
	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/profile/resource/css/homepage.css" />
	<script type="text/javascript">seajs.use(['theme!profile'])</script>
</head>
<body class="lui_profile_listview_body">
	<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
	<c:set var="type" scope="page" value="${empty param.type ? 'ekp' : param.type}"/>  
	<profile:listview id="sysProfileBlock">
		<ui:source type="AjaxJson">
			{"url":"/sys/profile/sys_profile_main/sysCfgProfileConfig.do?method=data&type=${type}"}
		</ui:source>
		<ui:render type="Javascript" ref="sys.profile.listview.default">
			<%--TODO 配置项完善--%>
		</ui:render>
	</profile:listview>
	<template:block name="body"></template:block>
</body>
</html>
