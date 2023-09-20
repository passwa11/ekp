<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:replace name="content">
	<div class='lui_form_title_frame'>
        <div class='lui_form_subject'>
            ${lfn:message('km-archives:table.kmArchivesAppraise')}
        </div>
    </div>
    <c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<c:import url="/km/archives/km_archives_appraise/kmArchivesAppraise_viewContent.jsp" charEncoding="UTF-8">
 		 		<c:param name="contentType" value="xform"></c:param>
  			</c:import>
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'
				var-supportExpand="true" var-expand="true">
				<c:import url="/km/archives/km_archives_appraise/kmArchivesAppraise_viewContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
	  			</c:import>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false" var-navwidth="90%">
	 		 	<ui:content title="${ lfn:message('km-archives:py.JiBenXinXi') }" expand="true">
					<c:import url="/km/archives/km_archives_appraise/kmArchivesAppraise_viewContent.jsp" charEncoding="UTF-8">
	 		 			<c:param name="contentType" value="xform"></c:param>
	  				</c:import>
  				</ui:content>
  				
  				<c:import url="/km/archives/km_archives_appraise/kmArchivesAppraise_viewContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
	  			</c:import>
			</ui:tabpage>
		</c:otherwise>
	</c:choose>
</template:replace>
<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<template:replace name="barRight">
			<c:choose>
				<c:when test="${kmArchivesAppraiseForm.docStatus>='30' || kmArchivesAppraiseForm.docStatus=='00'}">
					<ui:accordionpanel>
						<!-- 基本信息-->
						<c:import url="/km/archives/km_archives_appraise/kmArchivesAppraise_viewBaseInfoContent.jsp" charEncoding="UTF-8">
						</c:import>
					</ui:accordionpanel>
				</c:when>
				<c:otherwise>
					<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel" >
						<%-- 流程 --%>
						<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmArchivesAppraiseForm" />
							<c:param name="fdKey" value="kmArchivesAppraise" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
							<c:param name="approvePosition" value="right" />
						</c:import>
						<!-- 审批记录 -->
						<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmArchivesAppraiseForm" />
							<c:param name="fdModelId" value="${kmArchivesAppraiseForm.fdId}" />
							<c:param name="fdModelName" value="com.landray.kmss.km.archives.model.KmArchivesAppraise" />
						</c:import>
						<!-- 基本信息-->
						<c:import url="/km/archives/km_archives_appraise/kmArchivesAppraise_viewBaseInfoContent.jsp" charEncoding="UTF-8">
						</c:import>
					</ui:tabpanel>
				</c:otherwise>
			</c:choose>
		</template:replace>
	</c:when>
</c:choose>

