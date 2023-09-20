<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<lbpm:lbpmApproveModel modelName="com.landray.kmss.km.review.model.KmReviewMain"></lbpm:lbpmApproveModel>
<%
	//固定为蓝天凌云主题
	request.setAttribute("sys.ui.theme", "sky_blue");
%>
<template:include ref="default.edit" sidebar="auto">
	<c:import url="/km/review/km_review_ui/dingSuit/editHead.jsp" charEncoding="UTF-8">
	</c:import>
	<c:import url="/km/review/km_review_ui/dingSuit/editTab.jsp" charEncoding="UTF-8">
	</c:import>
</template:include>