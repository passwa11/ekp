<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.Calendar"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<lbpm:lbpmApproveModel modelName="com.landray.kmss.km.archives.model.KmArchivesMain"></lbpm:lbpmApproveModel>
<c:choose>
	<c:when test="${'true' eq kmArchivesMainForm.fdDestroyed }">
		<%@include file="./kmArchivesMain_view_destroyed.jsp" %>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${lbpmApproveModel eq 'right'}">
				<template:include ref="lbpm.right" isEdit="true" formName="kmArchivesMainForm" formUrl="${KMSS_Parameter_ContextPath}km/archives/km_archives_main/kmArchivesMain.do">
					<c:import url="/km/archives/km_archives_main/kmArchivesMain_viewPreFileHead_onUse.jsp" >
						<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
					</c:import>
					<c:import url="/km/archives/km_archives_main/kmArchivesMain_viewTab_onUse.jsp" >
						<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
					</c:import>
				</template:include>
			</c:when>
			<c:otherwise>
				<template:include ref="default.view" sidebar="auto">
					<c:import url="/km/archives/km_archives_main/kmArchivesMain_viewPreFileHead_onUse.jsp" ></c:import>
					<c:import url="/km/archives/km_archives_main/kmArchivesMain_viewTab_onUse.jsp" ></c:import>
				</template:include>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
