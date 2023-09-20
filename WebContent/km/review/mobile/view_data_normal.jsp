<%@ page import="com.landray.kmss.km.review.util.KmReviewUtil" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/component/locker/import/componentLockerVersion_show.jsp"%>
<ui:ajaxtext>
	<%
		JSONObject enableModule = KmReviewUtil.getEnableModule();
		request.setAttribute("enableModule", enableModule);
	%>
	<script type="text/javascript">
		var enableModule = <%=enableModule%>;
	</script>
	<%-- 此处为浏览器窗口标题 --%>
	<div data-dojo-block="title">
		<c:out value="${requestScope.templateName}"/>
	</div>
	<%--此处为内容 --%>
	<div data-dojo-block="content">
		<html:form action="/km/review/km_review_main/kmReviewMain.do">
			<div id="scrollView"
				data-dojo-type="mui/view/DocScrollableView"
				data-dojo-mixins="mui/form/_ValidateMixin,mui/form/_AlignMixin" class="muiFlowBack">
				<html:hidden property="fdId" />
				<c:import url="/km/review/mobile/view_banner.jsp" charEncoding="UTF-8">
					<c:param name="formBeanName" value="kmReviewMainForm"></c:param>
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
				<!-- 版本锁机制 -->
				<c:import url="/component/locker/import/componentLockerVersion_import.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmReviewMainForm" />
				</c:import>
				<%-- 支持移动端查阅 --%>
				<c:if test="${'true' eq kmReviewMainForm.fdIsMobileView}">
					    <div data-dojo-type="mui/panel/NavPanel" data-dojo-mixins="mui/panel/extend/_PromptPanelMixin,km/review/mobile/resource/js/view/ReviewNavPanelMixin" data-dojo-props="modelId:'${kmReviewMainForm.fdId }',modelName:'com.landray.kmss.km.review.model.KmReviewMain'">
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
									<c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
										<c:param name="fdModelId" value="${kmReviewMainForm.fdId }"/>
										<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain"/>
										<c:param name="formBeanName" value="kmReviewMainForm"/>
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
					<template:replace name="flowArea">
						<c:if test="${kmReviewMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.eqbSign =='true'}">
							<c:if test="${eqbSignButtonFlag == 'true'}">
								<c:import url="/km/review/km_review_main/kmReviewMain.do?method=getEqbSignPage" charEncoding="UTF-8">
									<c:param name="signId" value="${kmReviewMainForm.fdId}"></c:param>
							 	</c:import>
							</c:if>
						</c:if>
						<c:if test="${isTempAvailable && 'true' eq kmReviewMainForm.fdIsCopyDoc}">
							<kmss:auth requestURL="/km/review/km_review_main/kmReviewMain.do?method=add&fdReviewId=${param.fdId}&fdId=${param.fdId}" requestMethod="GET">
							<!-- 复制流程 -->
							<c:import url="/sys/lbpmservice/mobile/import/copy_btn_view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmReviewMainForm"></c:param>
								<c:param name="showOption" value="label"></c:param>
								<c:param name="url" value="/km/review/km_review_main/kmReviewMain.do?method=add&fdReviewId=${kmReviewMainForm.fdId}&fdTemplateId=${kmReviewMainForm.fdTemplateId}"></c:param>
								<c:param name="checkUrl" value="/km/review/km_review_main/kmReviewMain.do?method=checkTemplate&fdReviewId=${kmReviewMainForm.fdId}&fdTemplateId=${kmReviewMainForm.fdTemplateId}"></c:param>
								<c:param name="_referer" value="/km/review/mobile"></c:param>
							</c:import>
					 		</kmss:auth>
					 	</c:if>
						<kmss:auth requestURL="/km/review/km_review_main/kmReviewMain.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
							<!-- 删除文档 -->
							<div data-dojo-type="km/review/mobile/resource/js/button/DeleteButton" >
							</div>
						</kmss:auth>
						<c:if test="${existOpinion}">
							<c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
								 <c:param name="formName" value="kmReviewMainForm"></c:param>
								 <c:param name="isNewVersion" value="true"></c:param>
								 <c:param name="existOpinion" value="true"></c:param>
								 <c:param name="enable" value="${enableModule.enableSysCirculation eq 'false' ? 'false' : 'true'}"></c:param>
						 	</c:import>
						</c:if>
						<c:if test="${'true' eq kmReviewMainForm.fdIsMobileView}">
							<c:import url="/sys/bookmark/mobile/import/view.jsp" charEncoding="UTF-8">
							  <c:param name="fdModelName" value="${kmReviewMainForm.modelClass.name}"/>
							  <c:param name="fdModelId" value="${kmReviewMainForm.fdId}"/>
							  <c:param name="fdSubject" value="${kmReviewMainForm.docSubject}"/>
							  <c:param name="showOption" value="label"></c:param>
							  <c:param name="enable" value="${enableModule.enableSysBookmark eq 'false' ? 'false' : 'true'}"></c:param>
							</c:import>
						</c:if>
						<%--传阅 --%>
						<c:if test="${'true' eq kmReviewMainForm.fdCanCircularize }">
						 	<c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
								 <c:param name="formName" value="kmReviewMainForm"></c:param>
								 <c:param name="showOption" value="label"></c:param>
								 <c:param name="isNewVersion" value="true"></c:param>
								 <c:param name="enable" value="${enableModule.enableSysCirculation eq 'false' ? 'false' : 'true'}"></c:param>
						 	</c:import>
						</c:if>
						<c:if test="${'true' eq kmReviewMainForm.fdIsMobileView}">
							<c:import url="/sys/relation/mobile/import/view.jsp" charEncoding="UTF-8">
						  		<c:param name="formName" value="kmReviewMainForm"/>
						  		 <c:param name="showOption" value="label"></c:param>
						  	</c:import>
						</c:if>
					</template:replace>
					<template:replace name="publishArea">
						<c:if test="${isTempAvailable && 'true' eq kmReviewMainForm.fdIsCopyDoc}">
							<kmss:auth requestURL="/km/review/km_review_main/kmReviewMain.do?method=add&fdReviewId=${param.fdId}&fdId=${param.fdId}" requestMethod="GET">
							<c:import url="/sys/lbpmservice/mobile/import/copy_btn_view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmReviewMainForm"></c:param>
								<c:param name="showOption" value="label"></c:param>
								<c:param name="url" value="/km/review/km_review_main/kmReviewMain.do?method=add&fdReviewId=${kmReviewMainForm.fdId}&fdTemplateId=${kmReviewMainForm.fdTemplateId}"></c:param>
								<c:param name="checkUrl" value="/km/review/km_review_main/kmReviewMain.do?method=checkTemplate&fdReviewId=${kmReviewMainForm.fdId}&fdTemplateId=${kmReviewMainForm.fdTemplateId}"></c:param>
								<c:param name="_referer" value="/km/review/mobile"></c:param>
						 	</c:import>
						 	</kmss:auth>
						 </c:if>
						 <c:if test="${existOpinion}">
							<c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
								 <c:param name="formName" value="kmReviewMainForm"></c:param>
								 <c:param name="isNewVersion" value="true"></c:param>
								 <c:param name="existOpinion" value="true"></c:param>
								 <c:param name="enable" value="${enableModule.enableSysCirculation eq 'false' ? 'false' : 'true'}"></c:param>
						 	</c:import>
						</c:if>
						<c:if test="${'true' eq kmReviewMainForm.fdIsMobileView}">
							<c:import url="/sys/bookmark/mobile/import/view.jsp" charEncoding="UTF-8">
							  <c:param name="fdModelName" value="${kmReviewMainForm.modelClass.name}"/>
							  <c:param name="fdModelId" value="${kmReviewMainForm.fdId}"/>
							  <c:param name="fdSubject" value="${kmReviewMainForm.docSubject}"/>
							  <c:param name="showOption" value="label"></c:param>
							  <c:param name="enable" value="${enableModule.enableSysBookmark eq 'false' ? 'false' : 'true'}"></c:param>
							</c:import>
						</c:if>
						<%--传阅 --%>
						<c:if test="${'true' eq kmReviewMainForm.fdCanCircularize }">
						 	<c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
								 <c:param name="formName" value="kmReviewMainForm"></c:param>
								 <c:param name="showOption" value="label"></c:param>
								 <c:param name="isNewVersion" value="true"></c:param>
								 <c:param name="enable" value="${enableModule.enableSysCirculation eq 'false' ? 'false' : 'true'}"></c:param>
						 	</c:import>
						</c:if>
						<c:if test="${'true' eq kmReviewMainForm.fdIsMobileView}">
							<c:import url="/sys/relation/mobile/import/view.jsp" charEncoding="UTF-8">
						  		<c:param name="formName" value="kmReviewMainForm"/>
						  		 <c:param name="showOption" value="label"></c:param>
						  	</c:import>
						</c:if>
						<%--功能反馈--%>
						<c:import url="/km/review/mobile/km_review_feedback_info/import/view.jsp" charEncoding="UTF-8">
							<c:param name="fdModelId" value="${kmReviewMainForm.fdId}"/>
							<c:param name="formName" value="kmReviewMainForm"/>
							<c:param name="showOption" value="label"></c:param>
						</c:import>
					</template:replace>
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
							<c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmReviewMainForm" />
								<c:param name="fdKey" value="reviewMainDoc" />
								<c:param name="lbpmViewName" value="lbpmView" />
								<c:param name="onClickSubmitButton" value="Com_Submit(document.kmReviewMainForm, 'publishDraft');" />
							</c:import>
						</c:otherwise>
					</c:choose>
				</c:if>
				<script type="text/javascript">
					require(["mui/form/ajax-form!kmReviewMainForm"]);
				</script>
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
	</div>
</ui:ajaxtext>
