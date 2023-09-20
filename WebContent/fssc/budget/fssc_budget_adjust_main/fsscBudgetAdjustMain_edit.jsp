<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<lbpm:lbpmApproveModel modelName="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain"></lbpm:lbpmApproveModel>
<c:if test="${lbpmApproveModel eq 'right'}">
	<template:include ref="lbpm.right" showQrcode="true" isEdit="true"  formName="fsscBudgetAdjustMainForm" formUrl="${LUI_ContextPath}/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do">
		 <c:import url="/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain_edit_import.jsp" charEncoding="UTF-8">
		 	<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
 		 </c:import>
	</template:include>
</c:if>
<c:if test="${lbpmApproveModel ne 'right'}">
	<template:include ref="default.edit" sidebar="auto">
	 	<c:import url="/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain_edit_import.jsp" charEncoding="UTF-8"></c:import>
	</template:include>
</c:if>
