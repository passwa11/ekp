<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<c:if test="${JsParam.enable ne 'false'}">
	<c:set var="showOption" value="${param.showOption}" />
	<c:set var="_book_icon" value="" />
	<c:set var="_book_label" value="" />
	
<c:if test="${showOption == 'icon'}">
    <c:set var="_book_icon" value="muiBookmarkTabButton mui mui-star-off" />
</c:if>
<c:if test="${showOption == 'label'}">
	<c:set var="_book_label" value="${lfn:message('sys-bookmark:button.bookmark')}" />
</c:if>
<c:if test="${showOption == 'all' || empty showOption }">
    <c:set var="_book_icon" value="muiBookmarkTabButton mui mui-star-off" />
    <c:if test="${ empty showOption}">
		<c:set var="_book_label" value="${param.label}" />
	</c:if>
	<c:if test="${ showOption == 'all'}">
		<c:set var="_book_label" value="${lfn:message('sys-bookmark:button.bookmark')}" />
	</c:if>
</c:if>
	
<%
	String escapeSubject = StringEscapeUtils.escapeJavaScript(request.getParameter("fdSubject"));
	request.setAttribute("escapeSubject", escapeSubject);
%>
<div data-dojo-type="mui/tabbar/TabBarButton"
	data-dojo-props='icon1:"${_book_icon}",label:"${_book_label}",align:"${param.align}",modelId:"${param.fdModelId}",subject:"${escapeSubject }",modelName:"${param.fdModelName }"'
	data-dojo-mixins="<%=request.getContextPath()%>/sys/bookmark/mobile/import/js/_BookTabBarButtonMixin.js,sys/bookmark/mobile/import/js/_BookMarkCategoryMixin">
</div>
</c:if>