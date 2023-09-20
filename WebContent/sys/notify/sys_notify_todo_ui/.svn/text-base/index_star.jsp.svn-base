<%@ page language="java" pageEncoding="UTF-8"
	import="com.landray.kmss.sys.notify.service.ISysNotifyCategoryService,com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="net.sf.json.JSONArray,net.sf.json.JSONObject,java.util.List" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<% 
	//提前获取业务聚合分类
	ISysNotifyCategoryService sysNotifyCategoryService = (ISysNotifyCategoryService) SpringBeanUtil
		.getBean("sysNotifyCategoryService");
	List cate = sysNotifyCategoryService.getCategorys();
	request.setAttribute("cateList",cate);
	JSONArray array = new JSONArray();
	for (int i = 0; i < cate.size(); i++) {
		Object[] obj = (Object[]) cate.get(i);
		JSONObject json = new JSONObject();
		json.put("text", obj[1]);
		json.put("value", obj[0]);
		array.add(json);
	}
	request.setAttribute("cateJsonArr",array.toString());
%>
<template:include ref="default.simple4list" spa="true">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-notify:table.sysNotifyTodo') }"></c:out>
	</template:replace>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/notify/resource/css/notify.css"/>
	</template:replace>
	<template:replace name="content">
		<div style="width:100%">
		  <c:set var="starCon" value="&q.star=1"></c:set>
		  <ui:tabpanel id="notifyTabpanel" layout="sys.ui.tabpanel.list">
		  	 <%@ include file="/sys/notify/sys_notify_todo_ui/index_todo.jsp"%>
		  	 <%@ include file="/sys/notify/sys_notify_todo_ui/index_toview.jsp"%>
		  	 <%@ include file="/sys/notify/sys_notify_todo_ui/index_todo_done.jsp"%>
		  	 <%@ include file="/sys/notify/sys_notify_todo_ui/index_toview_done.jsp"%>
		  	 
		  	 <%@ include file="/sys/notify/sys_notify_todo_ui/index.js.jsp"%>
		  </ui:tabpanel>
	  </div> 
	</template:replace>
</template:include>
