<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.hr.ratify.util.HrRatifyUtil" %>
    
        <%
            pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
<template:replace name="content">

	<c:import url="/sys/recycle/import/redirect.jsp">
		<c:param name="formBeanName" value="hrRatifyChangeForm"></c:param>
	</c:import>
	<!-- 流程状态标识 -->
	<c:import url="/hr/ratify/hr_ratify_main/hrRatifyMain_banner.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="hrRatifyChangeForm" />
		<c:param name="approveType" value="${param.approveModel}" />
	</c:import>
	<c:if test="${param.approveModel ne 'right'}">
		<form name="hrRatifyChangeForm" method="post" action="<c:url value="/hr/ratify/hr_ratify_change/hrRatifyChange.do"/>">
	</c:if>
	<p class="lui_form_subject">
		<c:out value="${hrRatifyChangeForm.docSubject}" />
	</p>
	<c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<ui:tabpage collapsed="true" id="reviewTabPage">
				<script>
					LUI.ready(function(){
						setTimeout(function(){
							var reviewTabPage = LUI("reviewTabPage");
							if(reviewTabPage!=null){
								reviewTabPage.element.find(".lui_tabpage_float_collapse").hide();
								reviewTabPage.element.find(".lui_tabpage_float_navs_mark").hide();
							}
						},100)
					})
				</script>
				<c:import url="/hr/ratify/hr_ratify_change/hrRatifyChange_viewContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
			</ui:tabpage>
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-supportExpand="true" var-extend="true" var-average='false' var-useMaxWidth='true'>
				<%--流程--%>
				<c:choose>
					<c:when test="${hrRatifyChangeForm.docStatus>='30' || hrRatifyChangeForm.docStatus=='00'}">
						<%-- 流程 --%>
						<c:choose>
							<c:when test="${hrRatifyChangeForm.docUseXform == 'true' || empty hrRatifyChangeForm.docUseXform}">
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifyChangeForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="onClickSubmitButton" value="Com_Submit(document.hrRatifyChangeForm, 'update');" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
									<c:param name="needInitLbpm" value="true" />
								</c:import>
							</c:when>
							<c:otherwise>
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifyChangeForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
									<c:param name="needInitLbpm" value="true" />
								</c:import>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<%-- 流程 --%>
						<c:choose>
							<c:when test="${hrRatifyChangeForm.docUseXform == 'true' || empty hrRatifyChangeForm.docUseXform}">
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifyChangeForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="onClickSubmitButton" value="Com_Submit(document.hrRatifyChangeForm, 'update');" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
								</c:import>
							</c:when>
							<c:otherwise>
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifyChangeForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
								</c:import>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<c:import url="/hr/ratify/hr_ratify_change/hrRatifyChange_viewContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
	  			</c:import>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false" var-navwidth="90%" >
				<c:import url="/hr/ratify/hr_ratify_change/hrRatifyChange_viewContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_change/hrRatifyChange_viewContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	  			</c:import>
			</ui:tabpage>
		</c:otherwise>
	</c:choose>
	<c:if test="${param.approveModel ne 'right'}">
		</form>
	</c:if>
</template:replace>
<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<template:replace name="barRight">
			<c:choose>
				<c:when test="${hrRatifyChangeForm.docStatus>='30' || hrRatifyChangeForm.docStatus=='00'}">
					<ui:accordionpanel>
						<!-- 基本信息-->
						<c:import url="/hr/ratify/hr_ratify_change/hrRatifyChange_viewBaseInfoContent.jsp" charEncoding="UTF-8">
						</c:import>
						<!-- 关联机制(与原有机制有差异) -->
						<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="hrRatifyChangeForm" /> 
						</c:import> 
					</ui:accordionpanel>
				</c:when>
				<c:otherwise>
					<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel" >
						<%-- 流程 --%>
						<c:choose>
							<c:when test="${hrRatifyChangeForm.docUseXform == 'true' || empty hrRatifyChangeForm.docUseXform}">
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifyChangeForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="onClickSubmitButton" value="Com_Submit(document.hrRatifyChangeForm, 'update');" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
									<c:param name="approvePosition" value="right" />
								</c:import>
							</c:when>
							<c:otherwise>
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifyChangeForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
									<c:param name="approvePosition" value="right" />
								</c:import>
							</c:otherwise>
						</c:choose>
						<!-- 审批记录 -->
						<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="hrRatifyChangeForm" />
							<c:param name="fdModelId" value="${hrRatifyChangeForm.fdId}" />
							<c:param name="fdModelName" value="com.landray.kmss.hr.ratify.model.HrRatifyChange" />
						</c:import>
						<!-- 关联机制(与原有机制有差异) -->
						<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="hrRatifyChangeForm" />
							<c:param name="approveType" value="right" />
							<c:param name="needTitle" value="true" />
						</c:import> 
						<!-- 基本信息-->
						<c:import url="/hr/ratify/hr_ratify_change/hrRatifyChange_viewBaseInfoContent.jsp" charEncoding="UTF-8">
						</c:import>
					</ui:tabpanel>
				</c:otherwise>
			</c:choose>
		</template:replace>
	</c:when>
	<c:otherwise>
		<template:replace name="nav">
			<%--关联机制(与原有机制有差异)--%>
			<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="hrRatifyChangeForm" />
			</c:import>
		</template:replace>
	</c:otherwise>
</c:choose>