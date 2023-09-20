<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<lbpm:lbpmApproveModel modelName="com.landray.kmss.hr.ratify.model.HrRatifyFire"></lbpm:lbpmApproveModel>
<c:choose>
	<c:when test="${lbpmApproveModel eq 'right'}">
		<template:include ref="lbpm.right" showQrcode="true" isEdit="true" fdUseForm="${hrRatifyFireForm.docUseXform == 'true' || empty hrRatifyFireForm.docUseXform}" formName="hrRatifyFireForm" formUrl="${KMSS_Parameter_ContextPath}hr/ratify/hr_ratify_fire/hrRatifyFire.do">
			<c:import url="/hr/ratify/hr_ratify_fire/hrRatifyFire_editHead.jsp" charEncoding="UTF-8">
				<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
 		 	</c:import>
 		 	<c:import url="/hr/ratify/hr_ratify_fire/hrRatifyFire_editTab.jsp" charEncoding="UTF-8">
 		 		<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
  			</c:import>
		</template:include>
	</c:when>
	<c:otherwise>
		<template:include ref="default.edit" sidebar="auto">
			<c:import url="/hr/ratify/hr_ratify_fire/hrRatifyFire_editHead.jsp" charEncoding="UTF-8">
 		 	</c:import>
 		 	<c:import url="/hr/ratify/hr_ratify_fire/hrRatifyFire_editTab.jsp" charEncoding="UTF-8">
  			</c:import>
		</template:include>
	</c:otherwise>
</c:choose>
