<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:replace name="content">
	<c:import url="/sys/recycle/import/redirect.jsp">
		<c:param name="formBeanName" value="hrRatifyPositiveForm"></c:param>
	</c:import>
	<!-- 流程状态标识 -->
	<c:import url="/hr/ratify/hr_ratify_main/hrRatifyMain_banner.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="hrRatifyPositiveForm" />
		<c:param name="approveType" value="${param.approveModel}" />
	</c:import>
	<c:if test="${param.approveModel ne 'right'}">
		<form name="hrRatifyPositiveForm" method="post" action="<c:url value="/hr/ratify/hr_ratify_positive/hrRatifyPositive.do"/>">
	</c:if>
	<p class="lui_form_subject">
		<c:out value="${hrRatifyPositiveForm.docSubject}" />
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
				<c:import url="/hr/ratify/hr_ratify_positive/hrRatifyPositive_viewContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
			</ui:tabpage>
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-supportExpand="true" var-extend="true" var-average='false' var-useMaxWidth='true'>
				<%--流程--%>
				<c:choose>
					<c:when test="${hrRatifyPositiveForm.docStatus>='30' || hrRatifyPositiveForm.docStatus=='00'}">
						<%-- 流程 --%>
						<c:choose>
							<c:when test="${hrRatifyPositiveForm.docUseXform == 'true' || empty hrRatifyPositiveForm.docUseXform}">
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifyPositiveForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="onClickSubmitButton" value="Com_Submit(document.hrRatifyPositiveForm, 'update');" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
									<c:param name="needInitLbpm" value="true" />
								</c:import>
							</c:when>
							<c:otherwise>
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifyPositiveForm" />
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
							<c:when test="${hrRatifyPositiveForm.docUseXform == 'true' || empty hrRatifyPositiveForm.docUseXform}">
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifyPositiveForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="onClickSubmitButton" value="Com_Submit(document.hrRatifyPositiveForm, 'update');" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
								</c:import>
							</c:when>
							<c:otherwise>
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifyPositiveForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
								</c:import>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<c:import url="/hr/ratify/hr_ratify_positive/hrRatifyPositive_viewContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
	  			</c:import>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false" var-navwidth="90%" >
				<c:import url="/hr/ratify/hr_ratify_positive/hrRatifyPositive_viewContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_positive/hrRatifyPositive_viewContent.jsp" charEncoding="UTF-8">
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
				<c:when test="${hrRatifyPositiveForm.docStatus>='30' || hrRatifyPositiveForm.docStatus=='00'}">
					<ui:accordionpanel>
						<!-- 基本信息-->
						<c:import url="/hr/ratify/hr_ratify_positive/hrRatifyPositive_viewBaseInfoContent.jsp" charEncoding="UTF-8">
						</c:import>
						<!-- 关联配置 -->
						<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="hrRatifyPositiveForm" />  
						</c:import> 
					</ui:accordionpanel>
				</c:when>
				<c:otherwise>
					<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel" >
						<%-- 流程 --%>
						<c:choose>
							<c:when test="${hrRatifyPositiveForm.docUseXform == 'true' || empty hrRatifyPositiveForm.docUseXform}">
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifyPositiveForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="onClickSubmitButton" value="Com_Submit(document.hrRatifyPositiveForm, 'update');" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
									<c:param name="approvePosition" value="right" />
								</c:import>
							</c:when>
							<c:otherwise>
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifyPositiveForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
									<c:param name="approvePosition" value="right" />
								</c:import>
							</c:otherwise>
						</c:choose>
						<!-- 审批记录 -->
						<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="hrRatifyPositiveForm" />
							<c:param name="fdModelId" value="${hrRatifyPositiveForm.fdId}" />
							<c:param name="fdModelName" value="com.landray.kmss.hr.ratify.model.HrRatifyPositive" />
						</c:import>
						<!-- 基本信息-->
						<c:import url="/hr/ratify/hr_ratify_positive/hrRatifyPositive_viewBaseInfoContent.jsp" charEncoding="UTF-8">
						</c:import>
						<!-- 关联机制(与原有机制有差异) -->
						<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="hrRatifyPositiveForm" />
							<c:param name="approveType" value="right" />
							<c:param name="needTitle" value="true" />
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
				<c:param name="formName" value="hrRatifyPositiveForm" />
			</c:import>
		</template:replace>
	</c:otherwise>
</c:choose>