<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%@page import="java.util.Map"%>
<%
	String modelName = "com.landray.kmss.km.imeeting.model.KmImeetingConfig";
	ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
	Map map = sysAppConfigService.findByKey(modelName);
	request.setAttribute("kmImeetingConfig", map);
	request.setAttribute("userId", UserUtil.getUser().getFdId());
	request.setAttribute("dateTimeFormatter",ResourceUtil.getString("date.format.datetime"));
%>
<template:replace name="head">
	<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/km/imeeting/resource/css/book.css"></link>
	<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/km/imeeting/resource/css/view.css"></link>
	<script>
		Com_IncludeFile("upload.css",Com_Parameter.ContextPath	+ "sys/attachment/view/img/","css",true);
		Com_IncludeFile("dnd.css",Com_Parameter.ContextPath	+ "sys/attachment/view/img/","css",true);
	</script>
</template:replace>
<template:replace name="title">
	<c:choose>
		<c:when test="${ kmImeetingMainForm.method_GET == 'add' }">
			<c:out value="${ lfn:message('km-imeeting:kmImeetingMain.opt.change') } - ${ lfn:message('km-imeeting:module.km.imeeting') }"></c:out>	
		</c:when>
		<c:otherwise>
			<c:out value="${kmImeetingMainForm.fdName} - ${ lfn:message('km-imeeting:module.km.imeeting') }"></c:out>
		</c:otherwise>
	</c:choose>
</template:replace>

<template:replace name="toolbar">
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
		<c:if test="${kmImeetingMainForm.fdChangeMeetingFlag=='true'}">
			<c:if test="${kmImeetingMainForm.docStatus=='10'}">
				 <ui:button text="${lfn:message('km-imeeting:kmImeeting.changeMeeting') }" order="2" onclick="commitMethodChange('updateChange', 'false','true');">
				</ui:button> 
			</c:if>
			<c:if test="${kmImeetingMainForm.docStatus !='10'}">
				<ui:button text="${lfn:message('km-imeeting:kmImeeting.changeMeeting') }" order="2" onclick="commitMethodChange('update', 'false','true');">
				</ui:button> 
			</c:if>
		</c:if>
		<c:if test="${kmImeetingMainForm.fdChangeMeetingFlag=='false'}">
			<c:if test="${kmImeetingMainForm.docStatus=='10'}">
				 <ui:button text="${lfn:message('button.savedraft') }" order="2" onclick="commitMethodChange('update', 'true');">
				 </ui:button>
				 <ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethodChange('update', 'false');">
				 </ui:button>
			</c:if>
			<c:if test="${kmImeetingMainForm.docStatus=='11'}">
				<ui:button text="${lfn:message('button.savedraft') }" order="2" onclick="commitMethodChange('update', 'true');">
				</ui:button>
			 	<ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethodChange('update', 'false');">
				 </ui:button>
			</c:if>
			<c:if test="${kmImeetingMainForm.docStatus=='20'}">
				<ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethodChange('update', 'false');">
			 	</ui:button>
			</c:if>
			<c:if test="${kmImeetingMainForm.docStatus>='30'}">
				<ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethodChange('update', 'false');">
			 	</ui:button>
			</c:if>				
		</c:if> 
		<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()">
		</ui:button>
	</ui:toolbar>
</template:replace>

<template:replace name="path">
	<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
		<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home"></ui:menu-item>
		<ui:menu-item text="${ lfn:message('km-imeeting:module.km.imeeting') }" ></ui:menu-item>
		<ui:menu-item text="${ lfn:message('km-imeeting:table.kmImeetingMain') }"></ui:menu-item>
		<ui:menu-source autoFetch="false">
			<ui:source type="AjaxJson">
				{"url":"/sys/category/criteria/sysCategoryCriteria.do?method=path&modelName=com.landray.kmss.km.imeeting.model.KmImeetingTemplate&categoryId=${kmImeetingMainForm.fdTemplateId}"} 
			</ui:source>
		</ui:menu-source>
	</ui:menu>
</template:replace>