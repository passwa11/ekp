<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.attend.util.DateTimeFormatUtil,com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="java.util.List,com.landray.kmss.sys.attend.actions.SysAttendMainAction,com.landray.kmss.sys.attend.service.ISysAttendCategoryService,com.alibaba.fastjson.JSONArray,com.alibaba.fastjson.JSONObject" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
//	ISysAttendCategoryService cateService = (ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
//	JSONArray cateList = cateService.getAttendCategorys(request);
//	if(!cateList.isEmpty()){
//		JSONObject json = (JSONObject)cateList.get(0);
//		String fdId = (String)json.get("fdId");
//		String fdName = (String)json.get("fdName");
//		List list = new SysAttendMainAction().test(request, response,fdId);
//		if(!list.isEmpty()){
//			request.setAttribute("_list", list);
//			request.setAttribute("fdCategoryId", fdId);
//			request.setAttribute("fdCategoryName", fdName);
//		}
//	}
%>
<template:include ref="default.edit">
	<template:replace name="title">
		<c:choose>
			<c:when test="${sysAttendMainForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('operation.create') } - ${ lfn:message('sys-attend:module.sys.attend') }"></c:out>	
			</c:when>
			<c:otherwise>
				<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3"> 
			
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">			
		<ui:menu layout="sys.ui.menu.nav"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home">
			</ui:menu-item>	
			<ui:menu-item text="${ lfn:message('sys-attend:module.sys.attend') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>	
	<template:replace name="content">
		<h1>不支持pc端打卡操作，请使用移动端设备打卡！</h1>
	</template:replace>
</template:include>