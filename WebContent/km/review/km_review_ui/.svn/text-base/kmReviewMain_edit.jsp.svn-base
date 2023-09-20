<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@page import="com.landray.kmss.km.review.util.KmReviewUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<lbpm:lbpmApproveModel modelName="com.landray.kmss.km.review.model.KmReviewMain"></lbpm:lbpmApproveModel>
<% 
   request.setAttribute("enableModule", KmReviewUtil.getEnableModule());
%>
<c:choose>
	<%--钉钉端--%>
	<c:when test='<%="true".equals(SysFormDingUtil.getEnableDing()) || "true".equals(request.getParameter("ddpage"))%>'>
		<c:import url="/km/review/km_review_ui/dingSuit/edit.jsp" charEncoding="UTF-8"/>
	</c:when>
	<c:when test="${lbpmApproveModel eq 'right'}">
		<template:include ref="lbpm.right" showQrcode="true" isEdit="true" fdUseForm="${kmReviewMainForm.fdUseForm == 'true' || empty kmReviewMainForm.fdUseForm}" formName="kmReviewMainForm" formUrl="${KMSS_Parameter_ContextPath}km/review/km_review_main/kmReviewMain.do">
			<c:import url="/km/review/km_review_ui/kmReviewMain_editHead.jsp" charEncoding="UTF-8">
				<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
 		 	</c:import>
 		 	<c:import url="/km/review/km_review_ui/kmReviewMain_editTab.jsp" charEncoding="UTF-8">
 		 		<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
  			</c:import>
		</template:include>
	</c:when>
	<c:otherwise>
		<template:include ref="default.edit" sidebar="auto">
			<c:import url="/km/review/km_review_ui/kmReviewMain_editHead.jsp" charEncoding="UTF-8">
 		 	</c:import>
 		 	<c:import url="/km/review/km_review_ui/kmReviewMain_editTab.jsp" charEncoding="UTF-8">
  			</c:import>
		</template:include>
	</c:otherwise>
</c:choose>
