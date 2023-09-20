<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	<template:replace name="title">
		<c:choose>
			<c:when test="${sysAttendMainExcForm.method_GET == 'add' || sysAttendMainExcForm.method_GET == 'addExc' }">
				<c:out value="${ lfn:message('operation.create') } - ${ lfn:message('sys-attend:module.sys.attend') }"></c:out>	
			</c:when>
			<c:otherwise>
				<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3"> 
			<c:choose>
				<c:when test="${ sysAttendMainExcForm.method_GET == 'edit' && param.approveModel eq 'right'}">
					<c:if test="${ sysAttendMainExcForm.docStatus == '10'}">
						<ui:button text="${ lfn:message('button.savedraft') }" onclick="commitMethod('update', 'true');" ></ui:button>
					</c:if>
					<c:if test="${sysAttendMainExcForm.docStatus=='10'||sysAttendMainExcForm.docStatus=='11'||sysAttendMainExcForm.docStatus=='20' }">
						<ui:button text="${ lfn:message('button.update') }" onclick="commitMethod('update');" styleClass="lui_widget_btn_primary" isForcedAddClass="true"></ui:button>
					</c:if>
				</c:when>
				<c:when test="${ sysAttendMainExcForm.method_GET == 'edit'}">
					<c:if test="${ sysAttendMainExcForm.docStatus == '10'}">
						<ui:button text="${ lfn:message('button.savedraft') }" onclick="commitMethod('update', 'true');" ></ui:button>
					</c:if>
					<c:if test="${sysAttendMainExcForm.docStatus=='10'||sysAttendMainExcForm.docStatus=='11'||sysAttendMainExcForm.docStatus=='20' }">
						<ui:button text="${ lfn:message('button.update') }" onclick="commitMethod('update');"></ui:button>
					</c:if>
				</c:when>
			</c:choose>
			
			<c:choose>
				<c:when test="${ (sysAttendMainExcForm.method_GET == 'add' || sysAttendMainExcForm.method_GET == 'addExc') && param.approveModel eq 'right'}">
					<ui:button text="${ lfn:message('button.savedraft') }" onclick="commitMethod('save', 'true');"></ui:button>
					<ui:button text="${ lfn:message('button.submit') }" onclick="commitMethod('save');" styleClass="lui_widget_btn_primary" isForcedAddClass="true"></ui:button>
				</c:when>
				<c:when test="${ (sysAttendMainExcForm.method_GET == 'add' || sysAttendMainExcForm.method_GET == 'addExc')}">
					<ui:button text="${ lfn:message('button.savedraft') }" onclick="commitMethod('save', 'true');"></ui:button>
					<ui:button text="${ lfn:message('button.submit') }" onclick="commitMethod('save');"></ui:button>
				</c:when>
			</c:choose>
				
				
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
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