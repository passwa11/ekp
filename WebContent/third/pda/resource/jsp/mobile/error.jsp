<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
	if (request.getHeader("accept") != null) {
		if (request.getHeader("accept").indexOf("application/json") >= 0) {
%>
<ui:ajaxtext>
	<div data-dojo-block="title">
		<bean:message key="return.systemInfo" />
	</div>
	<div data-dojo-block="content">
		<c:import url="/third/pda/resource/jsp/mobile/error_data.jsp"
			charEncoding="utf-8"></c:import>
	</div>
</ui:ajaxtext>
<%
	return;
		}
	}
%>
<template:include file="/third/pda/template.jsp" compatibleMode="true">
	<template:replace name="title">
		<bean:message key="return.systemInfo" />
	</template:replace>
	<template:replace name="content">
		<c:import url="/third/pda/resource/jsp/mobile/error_data.jsp"
			charEncoding="utf-8"></c:import>
	</template:replace>
</template:include>