<%@page
	import="com.landray.kmss.kms.multidoc.service.IKmsMultidocKnowledgeService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>


<%
	IKmsMultidocKnowledgeService service =
			(IKmsMultidocKnowledgeService) SpringBeanUtil
					.getBean("kmsMultidocKnowledgeService");

	service.addImportFile("/Users/hongzq/Downloads/test", null);
%>