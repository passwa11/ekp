<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/km/archives/km_archives_main/kmArchivesMain_view_include.jsp"%>
<template:replace name="content">
	<script type="text/javascript">
		Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js|calendar.js|dialog.js|jquery.js", null, "js");
	</script>
	<p class="txttitle">${lfn:message('km-archives:table.kmArchivesMain')} </p>
	<div class='lui_form_title_frame' id="barCodeImgBox">
		<div class='lui_form_subject' style="height: 70px; line-height: 70px; position: relative;">
			<img class="barCodeImg" id="imgcode" />
		</div>
		<div class='lui_form_baseinfo'></div>
	</div>
	<xform:text property="fdId" showStatus="noShow"/>	
	<xform:text property="docSubject" showStatus="noShow"/>
	<xform:text property="docStatus" showStatus="noShow"/>
	<xform:text property="fdNumber" showStatus="noShow"/>
	<xform:text property="method_GET" showStatus="noShow"/>
	<c:import url="/km/archives/km_archives_main/kmArchivesMain_viewContent.jsp" charEncoding="UTF-8">
 		 <c:param name="contentType" value="xform" />
  	</c:import>
	<c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="6" var-average='false' var-useMaxWidth='true'
				var-supportExpand="true" var-expand="true">
				<c:import url="/km/archives/km_archives_main/kmArchivesMain_viewContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
	  			</c:import>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false" var-navwidth="90%">
				<c:import url="/km/archives/km_archives_main/kmArchivesMain_viewContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
	  			</c:import>
			</ui:tabpage>
		</c:otherwise>
	</c:choose>
</template:replace>
<c:if test="${param.approveModel eq 'right'}">
	<template:replace name="barRight">
		<c:choose>
			<c:when
				test="${kmArchivesMainForm.docStatus>='30' || kmArchivesMainForm.docStatus=='00'}">
				<ui:accordionpanel>
					<!-- 基本信息-->
					<c:import
						url="/km/archives/km_archives_main/kmArchivesMain_viewBaseInfoContent.jsp"
						charEncoding="UTF-8">
					</c:import>
				</ui:accordionpanel>
			</c:when>
			<c:otherwise>
				<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
					<%-- 流程 --%>
					<c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmArchivesMainForm" />
						<c:param name="fdKey" value="kmArchivesMain" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
						<c:param name="approvePosition" value="right" />
					</c:import>
					<!-- 审批记录 -->
					<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmArchivesMainForm" />
						<c:param name="fdModelId" value="${kmArchivesMainForm.fdId}" />
						<c:param name="fdModelName" value="com.landray.kmss.km.archives.model.KmArchivesMain" />
					</c:import>
					<!-- 基本信息-->
					<c:import url="/km/archives/km_archives_main/kmArchivesMain_viewBaseInfoContent.jsp" charEncoding="UTF-8">
					</c:import>
				</ui:tabpanel>
			</c:otherwise>
		</c:choose>
	</template:replace>
</c:if>