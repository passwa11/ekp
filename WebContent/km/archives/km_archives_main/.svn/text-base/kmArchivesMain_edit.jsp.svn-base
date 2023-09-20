<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.Calendar"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>
<lbpm:lbpmApproveModel modelName="com.landray.kmss.km.archives.model.KmArchivesMain"></lbpm:lbpmApproveModel>
<c:choose>
	<c:when test="${lbpmApproveModel eq 'right'}">
		<template:include ref="lbpm.right" showQrcode="true" isEdit="true" formName="kmArchivesMainForm" formUrl="${KMSS_Parameter_ContextPath}km/archives/km_archives_main/kmArchivesMain.do">
			<c:import url="/km/archives/km_archives_main/kmArchivesMain_editHead.jsp" charEncoding="UTF-8">
				<c:param name="approveModel" value="${lbpmApproveModel}" />
		 	</c:import>
		 	<c:import url="/km/archives/km_archives_main/kmArchivesMain_editTab.jsp" charEncoding="UTF-8">
				<c:param name="approveModel" value="${lbpmApproveModel}" />
		 	</c:import>
		</template:include>
	</c:when>
	<c:otherwise>
		<template:include ref="default.edit">
			<c:import url="/km/archives/km_archives_main/kmArchivesMain_editHead.jsp" charEncoding="UTF-8"></c:import>
			 <c:import url="/km/archives/km_archives_main/kmArchivesMain_editTab.jsp" charEncoding="UTF-8"></c:import>
		</template:include>
	</c:otherwise>
</c:choose>