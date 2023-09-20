<%@page
	import="com.landray.kmss.sys.filestore.service.ISysFileConvertLogService"%>
<%@page import="com.landray.kmss.sys.filestore.model.SysFileConvertLog"%>
<%@page
	import="com.landray.kmss.sys.filestore.dao.ISysFileConvertLogDao"%>
<%@page import="org.springframework.transaction.TransactionStatus"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>

<%
	String logId = request.getParameter("logId");
	ISysFileConvertLogService logService = (ISysFileConvertLogService) SpringBeanUtil
			.getBean("sysFileConvertLogService");
	SysFileConvertLog logInfo = (SysFileConvertLog) logService.findByPrimaryKey(logId);
	request.setAttribute("statusInfo", logInfo.getFdStatusInfo());
%>

<template:include ref="default.simple">
	<template:replace name="body">
		<center>
			<div style="width: 90%">
				<span>${statusInfo }</span>
			</div>
		</center>
	</template:replace>
</template:include>