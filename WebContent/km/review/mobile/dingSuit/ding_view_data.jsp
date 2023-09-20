<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<ui:ajaxtext>
	<%-- 此处为浏览器窗口标题 --%>
	<div data-dojo-block="title">
	   ${requestScope.templateName}
	</div>
	<%--此处为内容 --%>
	<div data-dojo-block="content">
		<html:form action="/km/review/km_review_main/kmReviewMain.do">		
			<div id="scrollView"
				data-dojo-type="mui/view/DocScrollableView"
				data-dojo-mixins="mui/form/_ValidateMixin,mui/form/_AlignMixin" class="muiFlowBack">
				<html:hidden property="fdId" />
				<c:import url="/km/review/mobile/dingSuit/ding_view_banner.jsp" charEncoding="UTF-8">
					<c:param name="formBeanName" value="kmReviewMainForm"></c:param>
					<c:param name="serviceSource" value="ding"></c:param>
				</c:import>
				
				<c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmReviewMainForm" />
					<c:param name="moduleModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
				</c:import>
				
				<c:import url="/sys/relation/mobile/edit_hidden.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmReviewMainForm" />
				</c:import>
				
				<c:import url="/sys/agenda/mobile/edit_hidden.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmReviewMainForm" />
				</c:import>
				
				<c:import url="/sys/authorization/mobile/edit_hidden.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmReviewMainForm" />
				</c:import>
				<%-- 支持移动端查阅 --%>
				<c:if test="${'true' eq kmReviewMainForm.fdIsMobileView}">
					    <div data-dojo-type="mui/panel/NavPanel">
					        <%--  审批内容    --%>
							<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="km-review" key="mui.kmReviewMain.mobile.info" />'">
								<c:if test="${kmReviewMainForm.fdUseForm == 'false'}">
									<c:choose>
										<c:when test="${kmReviewMainForm.fdUseWord == 'true'}">
											<c:import url="/sys/attachment/mobile/import/viewContent.jsp" charEncoding="UTF-8">
												<c:param name="formName" value="kmReviewMainForm"></c:param>
												<c:param name="fdKey" value="mainContent"></c:param>
											</c:import> 
										</c:when>
										<c:otherwise>
											<div class="muiFormContent">
												<br/>
												<xform:rtf property="docContent" mobile="true"></xform:rtf>
												<br/>
												<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
													<c:param name="formName" value="kmReviewMainForm"></c:param>
													<c:param name="fdKey" value="fdAttachment"></c:param>
												</c:import> 
												<br/>
											</div>
										</c:otherwise>
									</c:choose>
								</c:if>
								
								<c:if test="${kmReviewMainForm.fdUseForm == 'true' || empty kmReviewMainForm.fdUseForm}">
									<div data-dojo-type="mui/table/ScrollableHContainer">
											<c:import url="/sys/xform/mobile/import/sysForm_mobile.jsp"
												charEncoding="UTF-8">
												<c:param name="formName" value="kmReviewMainForm" />
												<c:param name="fdKey" value="reviewMainDoc" />
												<c:param name="backTo" value="scrollView" />
											</c:import>
									</div>
								</c:if>
							</div>
							<%--  流程记录    --%>
							<div data-dojo-type="mui/panel/DelayContent" data-dojo-props="title:'<bean:message bundle="km-review" key="mui.kmReviewMain.mobile.note" />'">
								<div class="muiFormContent">
									<c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/dingSuit/view.jsp" charEncoding="UTF-8">
										<c:param name="fdModelId" value="${kmReviewMainForm.fdId }"/>
										<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain"/>
										<c:param name="formBeanName" value="kmReviewMainForm"/>
										<c:param name="docStatus" value="${kmReviewMainForm.docStatus}"/>
									</c:import>
									<xform:isExistRelationProcesses relationType="parent">
										<xform:showParentProcesse mobile="true" />
									</xform:isExistRelationProcesses>
										
									<xform:isExistRelationProcesses relationType="subs">
										<xform:showSubProcesses mobile="true"/>
									</xform:isExistRelationProcesses>
								</div>
							</div>
						</div>
				</c:if>

				<template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp"
								editUrl="/km/review/km_review_main/kmReviewMain.do?method=edit&fdId=${param.fdId }&_referer=${LUI_ContextPath }/km/review/mobile"
								formName="kmReviewMainForm"
								viewName="lbpmView"
								allowReview="${kmReviewMainForm.fdIsMobileApprove!='false' && kmReviewMainForm.fdIsMobileView!='false'}">
				</template:include>
			</div>
			<%-- 支持移动端查阅 --%>
			<c:if test="${'true' eq kmReviewMainForm.fdIsMobileView}">
				<%--钉钉图标 --%>
				<kmss:ifModuleExist path="/third/ding">
					<c:import url="/third/ding/import/ding_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmReviewMainForm" />
					</c:import>
				</kmss:ifModuleExist>
				<kmss:ifModuleExist path="/third/lding">
					<c:import url="/third/lding/import/ding_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmReviewMainForm" />
					</c:import>
				</kmss:ifModuleExist>
				<%--钉钉图标 end--%>
				<c:if test="${kmReviewMainForm.docStatus < '30' }">
					<c:choose>
						<c:when test="${'false' eq kmReviewMainForm.fdIsMobileApprove}">
							<script type="text/javascript">
								require(["mui/dialog/BarTip", "dojo/ready"], function(BarTip, ready) {
									ready(function() {
										BarTip.tip({text: "<bean:message key='km-review:kmReviewTemplate.tipmessage.approve'/>"});
									});
								});
							</script>
						</c:when>
						<c:otherwise>
							<c:import url="/sys/lbpmservice/mobile/import/dingSuit/view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmReviewMainForm" />
								<c:param name="fdKey" value="reviewMainDoc" />
								<c:param name="lbpmViewName" value="lbpmView" />
								<c:param name="onClickSubmitButton" value="Com_Submit(document.kmReviewMainForm, 'publishDraft');" />
							</c:import>
							<script type="text/javascript">
								require(["mui/form/ajax-form!kmReviewMainForm"]);
							</script>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:if>
		</html:form>
		<%-- 支持移动端查阅 --%>
		<c:if test="${'true' eq kmReviewMainForm.fdIsMobileView}">
			<!-- 分享机制  -->
			<kmss:ifModuleExist path="/third/ywork/">
				 <c:import url="/third/ywork/ywork_share/yworkDoc_mobile_share.jsp"
					charEncoding="UTF-8">
					<c:param name="modelId" value="${kmReviewMainForm.fdId}" />
					<c:param name="modelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
					<c:param name="templateId" value="${ kmReviewMainForm.fdTemplateId}" />
					<c:param name="allPath" value="${ kmReviewMainForm.fdTemplateName}" />
				</c:import>
			</kmss:ifModuleExist>
		</c:if>
		<%-- 不支持移动端查阅 --%>
		<c:if test="${'false' eq kmReviewMainForm.fdIsMobileView}">
			<script type="text/javascript">
				require(["mui/dialog/BarTip", "dojo/ready"], function(BarTip, ready) {
					ready(function() {
						BarTip.tip({text: "<bean:message key='km-review:kmReviewTemplate.tipmessage.view'/>"});
					});
				});
			</script>
		</c:if>
		<script>
			Com_IncludeFile("ding_mobile.css","${LUI_ContextPath}/third/ding/third_ding_xform/resource/css/","css",true);
		</script>
	</div>
</ui:ajaxtext>
