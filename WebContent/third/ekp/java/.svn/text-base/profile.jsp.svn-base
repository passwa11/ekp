<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/profile/profile.tld" prefix="profile"%>
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
	<c:set var="type" scope="page" value="${empty param.type ? 'ekp' : param.type}"/>  
	<profile:listview>
			<ui:source type="Static">
				[
				{
					"key" : "config",
					"pinYin" : "EJPZ",
					"order" : "0",
					"icon" : "EKPJ_config",
					"messageKey" : "<bean:message key="ekp.java.setting" bundle="third-ekp-java"/>",
					"description" : "<bean:message key="ekp.java.setting" bundle="third-ekp-java"/>",
					"url" : "/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.third.ekp.java.EkpJavaConfig"
				},{
					"key" : "ekpjnotify",
					"pinYin" : "EKPJNOTIFY",
					"order":"1",
					"icon" : "EKPJ_notify_config",
					"messageKey" : "<bean:message key="ekpj.notify" bundle="third-ekp-java-notify"/>",
					"description" : "<bean:message key="ekpj.notify" bundle="third-ekp-java-notify"/>",
					"url" : "/moduleindex_notopic.jsp?nav=/third/ekp/java/notify/tree_notify.jsp"
				}
				]
			</ui:source>		
			<ui:render type="Javascript" ref="sys.profile.listview.default"></ui:render>
		</profile:listview>
	<template:block name="body"></template:block>
</body>
</html>