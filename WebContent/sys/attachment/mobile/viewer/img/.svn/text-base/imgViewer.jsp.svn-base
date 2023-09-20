<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.view" scale="true">
	<template:replace name="title">
		<c:out value="${param.title }"></c:out>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css"
		href="${LUI_ContextPath}/sys/attachment/mobile/viewer/base/css/attViewer.css?s_cache=${MUI_Cache}"></link>
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="sys/attachment/mobile/viewer/img/ImgViewer"
			data-dojo-props="fdId:'${param.fdId }',converterKey:'${converterKey}'"></div>
	</template:replace>
</template:include>
