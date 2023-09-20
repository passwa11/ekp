<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.StringUtil,java.util.*"%>
<template:include ref="mobile.list">
	<template:replace name="title">
		<c:out value="${lfn:message('sys-notify:module.sys.notify')}"></c:out>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/notify/mobile/mportal/css/notify.css">
		<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/notify/mobile/resource/css/notify.css">
	</template:replace>
	<template:replace name="content">
		<div class="muiPortalNotifySimple"	
			data-dojo-type="sys/mportal/mobile/card/JsonStoreCardList"
			data-dojo-mixins="sys/notify/mobile/mportal/js/NotifyListMixin"
			data-dojo-props="url:'/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&rowsize=${rowsize}',lazy:false,type:'${type}'">
		</div>
	</template:replace>
</template:include>
