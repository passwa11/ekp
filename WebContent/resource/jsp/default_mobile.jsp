<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.ResourceUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.edit">
	<template:replace name="title">${lfn:message('return.systemInfo') }</template:replace>
	<template:replace name="content">
		<center>
			<div style="margin-top:30%">
				<img src="${LUI_ContextPath }/resource/images/defaultMobile.png"/> <br>
				<h3>${lfn:message('sys-mobile:mui.btn.building') }</h3>
			</div>
		</center>
	</template:replace>
</template:include>