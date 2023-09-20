<%@page
	import="com.landray.kmss.sys.property.mobile.builder.MobileOuterFilterBuilder"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/mobile/js/mui/property/template.jsp">

	<template:replace name="filter">
		<%
			MobileOuterFilterBuilder builder = (MobileOuterFilterBuilder) SpringBeanUtil
							.getBean("mobileOuterFilterBuilder");
					String fdCategoryId = request.getParameter("fdCategoryId");
					String modelName = request.getParameter("modelName");
					out.print(builder.build(fdCategoryId, modelName));
		%>
	</template:replace>

</template:include>

