<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<c:import url="/sys/modeling/auth/sys_opr_auth/index_body.jsp" charEncoding="UTF-8">
			<c:param name="fdAppModelId" value="${param.fdAppModelId}" />
		</c:import>
	</template:replace>
</template:include>
