<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%@page import="java.util.Map"%>
<%
	String modelName = "com.landray.kmss.km.imeeting.model.KmImeetingConfig";
	ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
	Map map = sysAppConfigService.findByKey(modelName);
	request.setAttribute("kmImeetingConfig", map);
	request.setAttribute("userId", UserUtil.getUser().getFdId());
%>
<template:replace name="head">
	<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/km/imeeting/resource/css/book.css"></link>
	<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/km/imeeting/resource/css/view.css"></link>
	<script>
		Com_IncludeFile("upload.css",Com_Parameter.ContextPath	+ "sys/attachment/view/img/","css",true);
		Com_IncludeFile("dnd.css",Com_Parameter.ContextPath	+ "sys/attachment/view/img/","css",true);
	</script>
</template:replace>
<%--页签名--%>
<template:replace name="title">
	<c:choose>
		<c:when test="${ kmImeetingMainForm.method_GET == 'add' }">
			<c:out value="${ lfn:message('km-imeeting:kmImeetingMain.opt.create') } - ${ lfn:message('km-imeeting:module.km.imeeting') }"></c:out>	
		</c:when>
		<c:otherwise>
			<c:out value="${kmImeetingMainForm.fdName} - ${ lfn:message('km-imeeting:module.km.imeeting') }"></c:out>
		</c:otherwise>
	</c:choose>
</template:replace>
<%--操作栏--%>
<template:replace name="toolbar">
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5"> 
		<c:choose>
			<c:when test="${param.approveModel eq 'right'}">
				<%--暂存--%>
			    <ui:button text="${lfn:message('button.savedraft') }" order="2" onclick="commitMethod('save', 'true');"></ui:button>
			    <%--保存--%>
				<ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethod('save', 'false');" styleClass="lui_widget_btn_primary" isForcedAddClass="true"></ui:button>
			</c:when>
			<c:otherwise>
				<%--暂存--%>
			    <ui:button text="${lfn:message('button.savedraft') }" order="2" onclick="commitMethod('save', 'true');"></ui:button>
			    <%--保存--%>
				<ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethod('save', 'false');"></ui:button>
			</c:otherwise>
		</c:choose>
		<%-- 流程右侧审批三级页面模板 --%>
		<ui:button id="sys_ui_step_pre" text="${lfn:message('sys-ui:ui.step.pre')}" 
			order="2" onclick="clickSysUiStep('pre')" style="display:none">
		</ui:button>
		<ui:button id="sys_ui_step_next" text="${lfn:message('sys-ui:ui.step.next')}" order="2" onclick="clickSysUiStep('next')">
		</ui:button>
		<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()"></ui:button>
	</ui:toolbar>
</template:replace>
<%--导航路径--%>
<template:replace name="path">
	<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
		<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home"></ui:menu-item>
		<ui:menu-item text="${ lfn:message('km-imeeting:module.km.imeeting') }"  ></ui:menu-item>
		<ui:menu-item text="${ lfn:message('km-imeeting:table.kmImeetingMain') }"></ui:menu-item>
		<ui:menu-source autoFetch="false">
			<ui:source type="AjaxJson">
				{"url":"/sys/category/criteria/sysCategoryCriteria.do?method=path&modelName=com.landray.kmss.km.imeeting.model.KmImeetingTemplate&categoryId=${kmImeetingMainForm.fdTemplateId}"} 
			</ui:source>
		</ui:menu-source>
	</ui:menu>
</template:replace>
	
