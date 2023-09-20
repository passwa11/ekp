<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:replace name="content">
	<form name=${formName } method="post" action="${actionUrl }">
		<html:hidden property="fdId" />
		<html:hidden property="method_GET" />
		<html:hidden property="docCategoryId" />
		<c:import url="/${baseUrl }/edit_page/editContent.jsp"
			charEncoding="UTF-8">
			<c:param name="contentType" value="baseInfo"></c:param>
		</c:import>
		<c:import url="/${baseUrl }/edit_page/editContent.jsp"
			charEncoding="UTF-8">
			<c:param name="contentType" value="ruleInfo"></c:param>
			<c:param name="useVue" value="true"></c:param>
		</c:import>
		<c:import url="/${baseUrl }/edit_page/editContent.jsp"
			charEncoding="UTF-8">
			<c:param name="contentType" value="configInfo"></c:param>
			<c:param name="useVue" value="true"></c:param>
		</c:import>
	</form>
</template:replace>