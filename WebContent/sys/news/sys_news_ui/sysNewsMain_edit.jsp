<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.landray.kmss.util.ResourceUtil,java.io.File"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.news.model.SysNewsConfig"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<%
	 pageContext.setAttribute("_isJGEnabled", new Boolean(
			com.landray.kmss.sys.attachment.util.JgWebOffice
					.isJGEnabled()));
   	 SysNewsConfig sysNewsConfig = new SysNewsConfig();
     pageContext.setAttribute("ImageW",sysNewsConfig.getfdImageW());
     pageContext.setAttribute("ImageH",sysNewsConfig.getfdImageH());

%>

<lbpm:lbpmApproveModel modelName="com.landray.kmss.sys.news.model.SysNewsMain"></lbpm:lbpmApproveModel>

<c:choose>
	<c:when test="${lbpmApproveModel eq 'right'}">
		<template:include ref="lbpm.right" showQrcode="true" isEdit="true" formName="sysNewsMainForm" formUrl="${KMSS_Parameter_ContextPath}sys/news/sys_news_main/sysNewsMain.do">
			<c:import url="/sys/news/sys_news_ui/sysNewsMain_editHead.jsp" charEncoding="UTF-8">
				<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
		 	</c:import>
		 	<c:import url="/sys/news/sys_news_ui/sysNewsMain_editTab.jsp" charEncoding="UTF-8">
				<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
		 	</c:import>
		</template:include>
	</c:when>
	<c:otherwise>
		<template:include ref="default.edit" sidebar="auto">
			<c:import url="/sys/news/sys_news_ui/sysNewsMain_editHead.jsp" charEncoding="UTF-8"></c:import>
		 	<c:import url="/sys/news/sys_news_ui/sysNewsMain_editTab.jsp" charEncoding="UTF-8"></c:import>
		</template:include>
	</c:otherwise>
</c:choose>