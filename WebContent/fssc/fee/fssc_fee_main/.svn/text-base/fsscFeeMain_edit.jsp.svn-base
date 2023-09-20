<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<lbpm:lbpmApproveModel modelName="com.landray.kmss.fssc.fee.model.FsscFeeMain"/>
<c:if test="${lbpmApproveModel eq 'right' }">
<template:include ref="lbpm.right" showQrcode="true" isEdit="true" fdUseForm="${fsscFeeMainForm.docUseXform}" formName="fsscFeeMainForm" formUrl="${KMSS_Parameter_ContextPath}fssc/fee/fssc_fee_main/fsscFeeMain.do">
	<c:import url="/fssc/fee/fssc_fee_main/fsscFeeMain_edit_content.jsp">
		<c:param name="approveType" value="${lbpmApproveModel }"/>
	</c:import>
</template:include>
</c:if>
<c:if test="${lbpmApproveModel ne 'right' }">
<template:include ref="default.edit">
	<c:import url="/fssc/fee/fssc_fee_main/fsscFeeMain_edit_content.jsp">
		<c:param name="approveType" value="${lbpmApproveModel }"/>
	</c:import>
</template:include>
</c:if>
