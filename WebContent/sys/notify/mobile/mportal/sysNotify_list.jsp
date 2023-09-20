<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/notify/mobile/mportal/css/notify.css">
<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/notify/mobile/resource/css/notify.css">
<div class="muiPortalNotifySimple"	
	data-dojo-type="sys/mportal/mobile/card/JsonStoreCardList"
	data-dojo-mixins="sys/notify/mobile/mportal/js/NotifyListMixin"
	data-dojo-props="url:'/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&rowsize=${param.rowsize}',lazy:false,type:'${param.type}'">
</div>