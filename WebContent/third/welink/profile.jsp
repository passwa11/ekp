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
					"pinYin" : "JCZJPZ",
					"order" : "0",
					"icon" : "welink_config",
					"messageKey" : "<bean:message key="third.welink.profile.config.messageKey" bundle="third-welink"/>",
					"description" : "<bean:message key="third.welink.profile.config.description" bundle="third-welink"/>",
					"url" : "/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.third.welink.model.ThirdWelinkConfig"
				},{
					"key" : "omswelinkinit",
					"pinYin" : "OMSWELINKINIT",
					"order":"1",
					"icon" : "welink_config",
					"messageKey" : "<bean:message key="third.welink.profile.oms.messageKey" bundle="third-welink"/>",
					"description" : "<bean:message key="third.welink.profile.oms.description" bundle="third-welink"/>",
					"url" : "/moduleindex_notopic.jsp?nav=/third/welink/tree_omsinit.jsp"
				},{
					"key" : "welinknotify",
					"pinYin" : "WELINKNOTIFY",
					"order" : "3",
					"icon" : "welink_config",
					"messageKey" : "<bean:message key="module.third.welink.notify.profile" bundle="third-welink"/>",
					"description" : "<bean:message key="module.third.welink.notify.description" bundle="third-welink"/>",
					"url" : "/moduleindex_notopic.jsp?nav=/third/welink/tree_notify.jsp"
				},{
					"key" : "welinkadmin",
					"pinYin" : "WELINKADMIN",
					"icon" : "welink_admin",
					"order" : "6",
					"messageKey" : "<bean:message key="third.welink.profile.welinkadmin.messageKey" bundle="third-welink"/>",
					"description" : "<bean:message key="third.welink.profile.welinkadmin.description" bundle="third-welink"/>",
					"url" : "https://welink.huaweicloud.com",
					"target" : "_blank"
				}]
			</ui:source>		
			<ui:render type="Javascript" ref="sys.profile.listview.default"></ui:render>
		</profile:listview>
	<template:block name="body"></template:block>
</body>
</html>

