<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:replace name="title">
	<c:out value="${modelingAppModelMainForm.fdModelName}"></c:out>
</template:replace>
<template:replace name="toolbar">
	<ui:toolbar var-navwidth="90%" id="toolbar" layout="sys.ui.toolbar.float" count="6">  
		<c:if test="${modelingAppModelMainForm.method_GET=='add'}">
			<ui:button text="${ lfn:message('button.savedraft') }" order="1" onclick="submitForm('saveDraft','10');"></ui:button>
			<ui:button text="${saveButtonText}" order="1" onclick="submitForm('save','20');"></ui:button>
		</c:if>
		<c:if test="${modelingAppModelMainForm.method_GET=='edit'}">
			<c:if test="${modelingAppModelMainForm.method_GET=='edit' && (modelingAppModelMainForm.docStatus=='10' || modelingAppModelMainForm.docStatus=='11')}">
				<ui:button text="${ lfn:message('button.savedraft') }" order="1"
						onclick="submitForm('updateDraft','10');">
				</ui:button>
			</c:if>
			<c:choose>
				<c:when test="${modelingAppModelMainForm.docStatus == '10' || modelingAppModelMainForm.docStatus == '11' || modelingAppModelMainForm.docStatus == '20'}">
					<ui:button text="${updateButtonText}" order="1" onclick="submitForm('publishUpdate','20');"></ui:button>
				</c:when>
				<c:otherwise>
					<ui:button text="${updateButtonText}" order="1" onclick="submitForm('update');"></ui:button>
				</c:otherwise>
			</c:choose>
		</c:if>
		<ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow()"></ui:button>
	</ui:toolbar>
</template:replace>