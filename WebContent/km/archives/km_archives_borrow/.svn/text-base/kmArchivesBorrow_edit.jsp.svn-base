<%@page import="com.landray.kmss.km.archives.model.KmArchivesConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>

    <% 
    	pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
    	KmArchivesConfig kmArchivesConfig = new KmArchivesConfig();
    	request.setAttribute("fdMaxRenewDate", kmArchivesConfig.getFdMaxRenewDate());
    %>
    
<lbpm:lbpmApproveModel modelName="com.landray.kmss.km.archives.model.KmArchivesBorrow"></lbpm:lbpmApproveModel>

<c:choose>
	<c:when test="${lbpmApproveModel eq 'right'}">
		<template:include ref="lbpm.right" showQrcode="true" isEdit="true" formName="kmArchivesBorrowForm" formUrl="${KMSS_Parameter_ContextPath}km/archives/km_archives_borrow/kmArchivesBorrow.do">
			<c:import url="/km/archives/km_archives_borrow/kmArchivesBorrow_editHead.jsp" charEncoding="UTF-8">
				<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
		 	</c:import>
		 	<c:import url="/km/archives/km_archives_borrow/kmArchivesBorrow_editTab.jsp" charEncoding="UTF-8">
				<c:param name="approveModel" value="${lbpmApproveModel}"></c:param>
		 	</c:import>
		</template:include>
	</c:when>
	<c:otherwise>
		<template:include ref="default.edit">
		    <c:import url="/km/archives/km_archives_borrow/kmArchivesBorrow_editHead.jsp" charEncoding="UTF-8"></c:import>
		 	<c:import url="/km/archives/km_archives_borrow/kmArchivesBorrow_editTab.jsp" charEncoding="UTF-8"></c:import>
		</template:include>
	</c:otherwise>
</c:choose>
