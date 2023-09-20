<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="mainModelForm" value="${requestScope[param.formName]}" scope="request"/>
<c:set var="sysRelationMainForm" value="${mainModelForm.sysRelationMainForm}" scope="request"/>
<c:set var="currModelId" value="${mainModelForm.fdId}" scope="request"/>
<c:set var="currModelName" value="${mainModelForm.modelClass.name}" scope="request"/>
<c:set var="showOption" value="${param.showOption}" />
<c:set var="_rela_icon" value="" />
<c:set var="_rela_label" value="" />

<c:if test="${showOption == 'icon' }">
    <c:set var="_rela_icon" value="mui mui-rela" />
</c:if>
<c:if test="${showOption == 'label'}">
	<c:set var="_rela_label" value="${lfn:message('sys-relation:sysRelationMain.fdOtherUrl')}" />
</c:if>
<c:if test="${showOption == 'all' || empty showOption }">
    <c:set var="_rela_icon" value="mui mui-rela" />
    <c:if test="${ empty showOption}">
		<c:set var="_rela_label" value="${param.label}" />
	</c:if>
	<c:if test="${ showOption == 'all'}">
		<c:set var="_rela_label" value="${lfn:message('sys-relation:sysRelationMain.fdOtherUrl')}" />
	</c:if>
</c:if>

<c:if test="${not empty sysRelationMainForm.sysRelationEntryFormList}">
		<div data-dojo-type="mui/tabbar/TabBarButton"
		data-dojo-props='icon1:"${_rela_icon}",label:"${_rela_label}",href:"/sys/relation/mobile/index.jsp?modelName=${mainModelForm.modelClass.name}&modelId=${mainModelForm.fdId}"'></div>
</c:if>
 