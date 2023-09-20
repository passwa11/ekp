<%@page import="com.landray.kmss.kms.lservice.util.UrlsUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>


<template:include ref="mobile.list">
	<template:replace name="title">

		<c:if test="${param.moduleName!=null && param.moduleName!=''}">
			<c:out value="${param.moduleName}"></c:out>
		</c:if>

		<c:if test="${param.moduleName==null || param.moduleName==''}">
			${lfn:message('kms-lservice:lservice.index') }-${lfn:message('kms-lservice:lservice.teacher') }
		</c:if>

	</template:replace>

	<template:replace name="head">
	
		<link rel="stylesheet" type="text/css"
			href="${ LUI_ContextPath}/kms/lservice/mobile/style/mobile.css" />

	</template:replace>

	<template:replace name="content">

		<%
			request.setAttribute("jsondatas", UrlsUtil.getMobileUrls(UrlsUtil.getTeacherUrls()));
		%>

		<script>
			var jsondatas = ${jsondatas};
		</script>

		<div data-dojo-type="kms/lservice/mobile/js/Menu"
			data-dojo-props="jsondatas:jsondatas"></div>
			
		<div data-dojo-type="kms/lservice/mobile/js/Role"
			data-dojo-props="role:'teacher'"></div>

	</template:replace>
</template:include>
