<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<lbpm:lbpmApproveModel modelName="com.landray.kmss.km.imeeting.model.KmImeetingSummary"></lbpm:lbpmApproveModel>
<c:choose>
	<c:when test="${lbpmApproveModel eq 'right'}">
		<template:include ref="lbpm.right" showQrcode="true" formName="kmImeetingSummaryForm" formUrl="${KMSS_Parameter_ContextPath}km/imeeting/km_imeeting_summary/kmImeetingSummary.do">
			<c:import url="/km/imeeting/km_imeeting_summary/kmImeetingSummary_viewHead.jsp" charEncoding="UTF-8">
				<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
 		 	</c:import>
 		 	<c:import url="/km/imeeting/km_imeeting_summary/kmImeetingSummary_viewTab.jsp" charEncoding="UTF-8">
 		 		<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
 		 		<c:param name="kmImeetingSummaryForm_fdId" value="${kmImeetingSummaryForm.fdId}" />
  			</c:import>
		</template:include>
	</c:when>
	<c:otherwise>
		<template:include ref="default.view" sidebar="auto">
			<c:import url="/km/imeeting/km_imeeting_summary/kmImeetingSummary_viewHead.jsp" charEncoding="UTF-8">
 		 	</c:import>
 		 	<c:import url="/km/imeeting/km_imeeting_summary/kmImeetingSummary_viewTab.jsp" charEncoding="UTF-8">
 		 	<c:param name="kmImeetingSummaryForm_fdId" value="${kmImeetingSummaryForm.fdId}" />
  			</c:import>
		</template:include>
	</c:otherwise>
</c:choose>