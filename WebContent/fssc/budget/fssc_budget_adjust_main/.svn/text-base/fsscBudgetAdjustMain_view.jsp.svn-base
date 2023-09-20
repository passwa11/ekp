<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<lbpm:lbpmApproveModel modelName="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain"></lbpm:lbpmApproveModel>
<c:if test="${lbpmApproveModel eq 'right'}">
	<template:include ref="lbpm.right" showQrcode="true" fdUseForm="false"  formName="fsscBudgetAdjustMainForm" formUrl="${LUI_ContextPath}/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do">
		 	<c:import url="/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain_view_import.jsp" charEncoding="UTF-8">
		 		<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
 			</c:import>
	</template:include>
</c:if>
<c:if test="${lbpmApproveModel ne 'right'}">
	<template:include ref="default.view" sidebar="auto">
		 	<c:import url="/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain_view_import.jsp" charEncoding="UTF-8">
		 		<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
 			</c:import>
	</template:include>
</c:if>
