<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="com.landray.kmss.sys.auditlog.service.ISysAuditlogService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	ISysAuditlogService logService = (ISysAuditlogService) SpringBeanUtil
			.getBean("sysAuditlogService");
	logService.clearData();
	out.print("数据已清理。");
%>