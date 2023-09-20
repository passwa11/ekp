<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<lbpm:lbpmApproveModel modelName="com.landray.kmss.fssc.expense.model.FsscExpenseMain"/>
<c:if test="${lbpmApproveModel eq 'right' }">
<template:include ref="lbpm.right" showQrcode="true" isEdit="true" fdUseForm="${fsscExpenseMainForm.docUseXform}" formName="fsscExpenseMainForm" formUrl="${KMSS_Parameter_ContextPath}fssc/expense/fssc_expense_main/fsscExpenseMain.do">
	<c:import url="/fssc/expense/fssc_expense_main/fsscExpenseMain_edit_content.jsp">
		<c:param name="approveType" value="${lbpmApproveModel }"/>
	</c:import>
</template:include>
</c:if>
<c:if test="${lbpmApproveModel ne 'right' }">
<template:include ref="default.edit">
	<c:import url="/fssc/expense/fssc_expense_main/fsscExpenseMain_edit_content.jsp">
		<c:param name="approveType" value="${lbpmApproveModel }"/>
	</c:import>
</template:include>
</c:if>
<script>
	Com_IncludeFile("quickSelect.js", Com_Parameter.ContextPath+"eop/basedata/resource/js/", 'js', true);
</script>
