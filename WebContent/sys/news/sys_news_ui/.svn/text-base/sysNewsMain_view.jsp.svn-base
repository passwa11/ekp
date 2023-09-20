<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>

<lbpm:lbpmApproveModel modelName="com.landray.kmss.km.news.model.SysNewsMain"></lbpm:lbpmApproveModel>

<c:import url="/sys/recycle/import/redirect.jsp">
	<c:param name="formBeanName" value="sysNewsMainForm"></c:param>
</c:import>

<c:choose>
	<c:when test="${lbpmApproveModel eq 'right'}">
		<template:include ref="lbpm.right" showQrcode="true" formName="sysNewsMainForm" formUrl="${KMSS_Parameter_ContextPath}sys/news/sys_news_main/sysNewsMain.do">
			<c:import url="/sys/news/sys_news_ui/sysNewsMain_viewHead.jsp" charEncoding="UTF-8">
				<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
		 	</c:import>
		 	<c:import url="/sys/news/sys_news_ui/sysNewsMain_viewTab.jsp" charEncoding="UTF-8">
				<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
		 	</c:import>
		</template:include>
	</c:when>
	<c:otherwise>
		<template:include ref="default.view" sidebar="auto">
			<c:import url="/sys/news/sys_news_ui/sysNewsMain_viewHead.jsp" charEncoding="UTF-8"></c:import>
		 	<c:import url="/sys/news/sys_news_ui/sysNewsMain_viewTab.jsp" charEncoding="UTF-8"></c:import>
		</template:include>
	</c:otherwise>
</c:choose>