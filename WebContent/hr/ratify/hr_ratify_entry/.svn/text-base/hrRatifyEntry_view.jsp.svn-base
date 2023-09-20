<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.hr.ratify.util.HrRatifyUtil" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<lbpm:lbpmApproveModel modelName="com.landray.kmss.hr.ratify.model.HrRatifyEntry"></lbpm:lbpmApproveModel>
    
<%
    pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
if(UserUtil.getUser().getFdParentOrg() != null) {
    pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
} else {
    pageContext.setAttribute("currentOrg", "");
} %>
<c:choose>
	<c:when test="${lbpmApproveModel eq 'right'}">
		<template:include ref="lbpm.right" showQrcode="true" fdUseForm="${hrRatifyEntryForm.docUseXform == 'true' || empty hrRatifyEntryForm.docUseXform}" formName="hrRatifyEntryForm" formUrl="${KMSS_Parameter_ContextPath}hr/ratify/hr_ratify_entry/hrRatifyEntry.do">
			<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_viewHead.jsp" charEncoding="UTF-8">
 		 	</c:import>
 		 	<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_viewTab.jsp" charEncoding="UTF-8">
 		 		<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
  			</c:import>
		</template:include>
	</c:when>
	<c:otherwise>
		<template:include ref="default.view" sidebar="auto">
			<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_viewHead.jsp" charEncoding="UTF-8">
 		 	</c:import>
 		 	<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_viewTab.jsp" charEncoding="UTF-8">
  			</c:import>
		</template:include>
	</c:otherwise>
</c:choose>
