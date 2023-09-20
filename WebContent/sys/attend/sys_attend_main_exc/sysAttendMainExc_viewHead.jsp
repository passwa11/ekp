<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3" >
			<c:if test="${sysAttendMainExcForm.docStatus=='10' || sysAttendMainExcForm.docStatus=='11' || sysAttendMainExcForm.docStatus=='20'}">
				<kmss:auth
					requestURL="/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=edit&fdId=${param.fdId}"
					requestMethod="GET">
			<c:choose>
				<c:when test="${param.approveModel eq 'right'}">
					<ui:button text="${lfn:message('button.edit')}" 
						onclick="Com_OpenWindow('sysAttendMainExc.do?method=edit&fdId=${JsParam.fdId}','_self');" order="1" styleClass="lui_widget_btn_primary" isForcedAddClass="true">
				    </ui:button>
				</c:when>
				<c:otherwise>
					<ui:button text="${lfn:message('button.edit')}" 
						onclick="Com_OpenWindow('sysAttendMainExc.do?method=edit&fdId=${JsParam.fdId}','_self');" order="1">
				    </ui:button>
				</c:otherwise>
			</c:choose>
					
				</kmss:auth>
			</c:if>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home">
			</ui:menu-item>	
			<ui:menu-item text="${ lfn:message('sys-attend:module.sys.attend') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-attend:table.sysAttendMainExc') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>