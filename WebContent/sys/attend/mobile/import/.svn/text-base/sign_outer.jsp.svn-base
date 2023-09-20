<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/mobile/import/css/view.css?s_cache=${MUI_Cache}"></link>
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="sys/attend/mobile/import/js/SysAttendSignView"
			data-dojo-props="categoryId:'${JsParam.categoryId}',userId:'${JsParam.userId}',outer:true">
		</div>
	</template:replace>
</template:include>
