<%@page import="com.landray.kmss.util.DateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.Date,java.util.Arrays,java.util.List,java.util.Calendar,net.sf.json.JSONObject,com.landray.kmss.third.pda.util.PdaFlagUtil,com.landray.kmss.util.DateUtil,com.landray.kmss.util.UserUtil,com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService,com.landray.kmss.sys.organization.model.SysOrgElement,com.landray.kmss.sys.attend.service.ISysAttendConfigService,com.landray.kmss.sys.attend.model.SysAttendConfig" %>
<%
	if (PdaFlagUtil.checkClientIsPda(request)) {//移动
		String fdCategoryId =(String) request.getParameter("fdCategoryId");
		if (fdCategoryId != null && fdCategoryId != "" && !"null".equals(fdCategoryId)) {
			response.sendRedirect(request.getContextPath() +
					"/sys/attend/mobile/index_stat.jsp?navIndex=2&categoryId="
					+ fdCategoryId);
		} else {
			response.sendRedirect(request.getContextPath()
					+ "/sys/attend/mobile/index_stat.jsp?navIndex=2");
		}
	}
%>

<template:include ref="default.view">

	<template:replace name="content">
		<center>请使用移动端查看</center>
	</template:replace>
</template:include>