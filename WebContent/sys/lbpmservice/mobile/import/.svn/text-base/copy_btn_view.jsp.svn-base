<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="kmReviewMainForm" value="${requestScope[param.formName]}" />
<c:set var="showOption" value="${param.showOption}" />
<c:set var="_copy_icon" value="" />
<c:set var="_copy_label" value="" />
   
<c:if test="${showOption == 'icon' }">
    <c:set var="_copy_icon" value="mui mui-intr" />
</c:if>
<c:if test="${showOption == 'label'}">
	<c:set var="_copy_label" value="${lfn:message('sys-lbpmservice:lbpm.operation.copyProcess')}" />
</c:if>
<c:if test="${showOption == 'all' || empty showOption }">
    <c:set var="_copy_icon" value="mui mui-intr" />
    <c:if test="${ empty showOption}">
		<c:set var="_copy_label" value="${param.label}" />
	</c:if>
	<c:if test="${ showOption == 'all'}">
		<c:set var="_copy_label" value="${lfn:message('sys-lbpmservice:lbpm.operation.copyProcess')}" />
	</c:if>
</c:if>
<c:if test="${empty _copy_label}">
<div data-dojo-type="sys/lbpmservice/mobile/workitem/CopyButton"
	data-dojo-props='icon1:"${_copy_icon}",align:"${param.align}"
	,href:"${param.url}"
	,checkUrl:"${param.checkUrl}"
	,_referer:"${param._referer}"'>
</div>
</c:if>
<c:if test="${not empty _copy_label}">
<div data-dojo-type="sys/lbpmservice/mobile/workitem/CopyButton"
	data-dojo-props='icon1:"",align:"${param.align}",label:"${_copy_label}"
	,href:"${param.url}"
	,checkUrl:"${param.checkUrl}"
	,_referer:"${param._referer}"'>
</div>
</c:if>
