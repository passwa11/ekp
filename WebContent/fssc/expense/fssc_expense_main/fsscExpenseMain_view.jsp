<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<lbpm:lbpmApproveModel modelName="com.landray.kmss.fssc.expense.model.FsscExpenseMain"/>
<c:if test="${lbpmApproveModel eq 'right' }">
<template:include ref="lbpm.right" showQrcode="true" fdUseForm="true" formName="fsscExpenseMainForm" formUrl="${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMain.do">
	<c:import url="/fssc/expense/fssc_expense_main/fsscExpenseMain_view_content.jsp">
		<c:param name="approveType" value="${lbpmApproveModel }"/>
	</c:import>
	
</template:include>
</c:if>
<c:if test="${lbpmApproveModel ne 'right' }">
<template:include ref="default.view">
	<c:import url="/fssc/expense/fssc_expense_main/fsscExpenseMain_view_content.jsp">
		<c:param name="approveType" value="${lbpmApproveModel }"/>
	</c:import>
</template:include>
</c:if>
