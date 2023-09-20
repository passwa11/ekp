<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:replace name="content">
	<!-- 软删除配置 -->
	<c:import url="/sys/recycle/import/redirect.jsp">
		<c:param name="formBeanName" value="kmArchivesBorrowForm"></c:param>
	</c:import>
	<div class='lui_form_title_frame'>
		<div class='lui_form_subject'>${lfn:message('km-archives:table.kmArchivesBorrow')}</div>
		<div class='lui_form_baseinfo'></div>
	</div>
	<c:if test="${param.approveModel ne 'right'}">
		<form name="kmArchivesBorrowForm" method="post" action="${KMSS_Parameter_ContextPath}km/archives/km_archives_borrow/kmArchivesBorrow.do">
	</c:if>
	<html:hidden property="fdId" />
	<html:hidden property="docStatus" />
	<html:hidden property="method_GET" />
	<c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<c:import url="/km/archives/km_archives_borrow/kmArchivesBorrow_viewContent.jsp" charEncoding="UTF-8">
 		 		<c:param name="contentType" value="xform" />
  			</c:import>
			<br>
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'
				var-supportExpand="true" var-expand="true">
				<c:choose>
					<c:when test="${kmArchivesBorrowForm.docStatus>='30' || kmArchivesBorrowForm.docStatus=='00'}">
						<!-- 流程 -->
						<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmArchivesBorrowForm" />
							<c:param name="fdKey" value="kmArchivesBorrow" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
							<c:param name="needInitLbpm" value="true" />
						</c:import>
					</c:when>
					<c:otherwise>
						<c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmArchivesBorrowForm" />
							<c:param name="fdKey" value="kmArchivesBorrow" />
							<c:param name="approveType" value="right" />
							<c:param name="isExpand" value="true" />
						</c:import>
					</c:otherwise>
				</c:choose>
	  			<c:import url="/km/archives/km_archives_borrow/kmArchivesBorrow_viewContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="right"></c:param>
	  			</c:import>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false" var-navwidth="90%">
				<ui:content title="${ lfn:message('km-archives:py.JiBenXinXi') }" toggle="false">
					<c:import url="/km/archives/km_archives_borrow/kmArchivesBorrow_viewContent.jsp" charEncoding="UTF-8">
		 		 		<c:param name="contentType" value="xform" />
		  			</c:import>
	  			</ui:content>
	  			<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmArchivesBorrowForm" />
					<c:param name="fdKey" value="kmArchivesBorrow" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="onClickSubmitButton" value="Com_Submit(document.kmArchivesBorrowForm, 'approve');" />
					<c:param name="isExpand" value="true" />
				</c:import>
	  			<c:import url="/km/archives/km_archives_borrow/kmArchivesBorrow_viewContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="right"></c:param>
	  			</c:import>
			</ui:tabpage>
			</form>
		</c:otherwise>
	</c:choose>
</template:replace>
<c:if test="${param.approveModel eq 'right'}">
	<template:replace name="barRight">
		<c:choose>
			<c:when test="${kmArchivesBorrowForm.docStatus>='30' || kmArchivesBorrowForm.docStatus=='00'}">
				<ui:accordionpanel>
					<!-- 基本信息-->
					<c:import url="/km/archives/km_archives_borrow/kmArchivesBorrow_viewBaseInfoContent.jsp" charEncoding="UTF-8">
					</c:import>
				</ui:accordionpanel>
			</c:when>
			<c:otherwise>
				<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel" >
               		<%-- 流程 --%>
					<c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
	                    <c:param name="formName" value="kmArchivesBorrowForm" />
	                    <c:param name="fdKey" value="kmArchivesBorrow" />
	                    <c:param name="isExpand" value="true" />
	                    <c:param name="approveType" value="right" />
	                    <c:param name="onClickSubmitButton" value="Com_Submit(document.kmArchivesBorrowForm, 'approve');" />
						<c:param name="approvePosition" value="right" />
               		</c:import>
					<!-- 审批记录 -->
					<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmArchivesBorrowForm" />
						<c:param name="fdModelId" value="${kmArchivesBorrowForm.fdId}" />
						<c:param name="fdModelName" value="com.landray.kmss.km.archives.model.KmArchivesBorrow" />
					</c:import>
					<!-- 基本信息-->
					<c:import url="/km/archives/km_archives_borrow/kmArchivesBorrow_viewBaseInfoContent.jsp" charEncoding="UTF-8">
					</c:import>
				</ui:tabpanel>
			</c:otherwise>
		</c:choose>
	</template:replace>
</c:if>
