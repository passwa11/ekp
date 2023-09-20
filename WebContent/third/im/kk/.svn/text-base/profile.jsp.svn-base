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
				[{
					"key" : "notify",
					"pinYin" : "kkdaibanjichengrizhi",
					"icon" : "kk_notify",
					"messageKey" : "<bean:message key="table.kkNotifyLog" bundle="third-im-kk"/>",
					"description" : "<bean:message key="kmNotifyLog.description" bundle="third-im-kk"/>",
					"url" : "/third/im/kk/kk_notify/indexLog.jsp"
				},{
					"key" : "kkconfig",
					"pinYin" : "kkjichengpeizhi",
					"icon" : "kk_kkconfig",
					"messageKey" : "<bean:message key="kk5.setting" bundle="third-im-kk"/>",
					"description" : "<bean:message key="kk5.setting.description" bundle="third-im-kk"/>",
					"url" : "/third/im/kk/kk_config/kkConfig_choose.jsp"
				}]
			</ui:source>		
			<ui:render type="Javascript" ref="sys.profile.listview.default"></ui:render>
		</profile:listview>
	<template:block name="body"></template:block>
</body>
</html>

