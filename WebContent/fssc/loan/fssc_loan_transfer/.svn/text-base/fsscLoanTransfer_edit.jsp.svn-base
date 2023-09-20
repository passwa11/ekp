<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<lbpm:lbpmApproveModel modelName="com.landray.kmss.fssc.loan.model.FsscLoanTransfer"></lbpm:lbpmApproveModel>
<c:if test="${lbpmApproveModel eq 'right'}">
	<template:include ref="lbpm.right" showQrcode="true" isEdit="true"  formName="fsscLoanTransferForm" formUrl="${LUI_ContextPath}/fssc/loan/fssc_loan_transfer/fsscLoanTransfer.do">
		 <c:import url="/fssc/loan/fssc_loan_transfer/fsscLoanTransfer_edit_import.jsp" charEncoding="UTF-8">
		 	<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
 		 </c:import>
	</template:include>
</c:if>
<c:if test="${lbpmApproveModel ne 'right'}">
	<template:include ref="default.edit" sidebar="auto">
	 	<c:import url="/fssc/loan/fssc_loan_transfer/fsscLoanTransfer_edit_import.jsp" charEncoding="UTF-8"></c:import>
	</template:include>
</c:if>
