<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="userId" value="${(empty param.userId) ? KMSS_Parameter_CurrentUserId : (param.userId)}" scope="request"/>
<template:include ref="zone.navlink">
	<template:replace name="content">
		<c:import 
			charEncoding="UTF-8"
			url="/sys/zone/sys_zone_personInfo/sysZonePersonInfo_personData_edit_import.jsp">
			<c:param name="userId"  value="${userId}"/>
			<c:param name="method" value="${KMSS_Parameter_CurrentUserId eq userId ? 'edit' : 'view'}"/>
		</c:import>
	</template:replace> 
</template:include>
	   
