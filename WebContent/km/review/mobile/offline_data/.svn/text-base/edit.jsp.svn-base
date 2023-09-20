<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.web.taglib.xform.TagUtils"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	request.setAttribute(TagUtils.DOC_STATUS, "edit");
%>
<c:if test="${empty  kmReviewMainForm.docSubject}">
	<kmss:windowTitle subject="${lfn:message('km-review:kmReviewMain.opt.create') }"></kmss:windowTitle>
</c:if>
<c:if test="${not empty kmReviewMainForm.docSubject}">
	<kmss:windowTitle subject="${kmReviewMainForm.docSubject }"></kmss:windowTitle>
</c:if>
<html:form action="/km/review/km_review_main/kmReviewMain.do?method=save">
	<div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="scrollView">
		<div data-dojo-type="mui/panel/AccordionPanel">
			<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message  bundle="km-review" key="kmReview.config.base"/>',icon:'mui-ul'">
				<div class="muiFormContent">
					<html:hidden property="fdId"/>
					<html:hidden property="fdModelId" />
					<html:hidden property="fdModelName" />
					<table class="muiSimple" cellpadding="0" cellspacing="0">
						<tr>
							<td class="muiTitle">
								<bean:message bundle="km-review" key="kmReviewMain.docSubject" />
							</td><td>
								<c:if test="${kmReviewMainForm.titleRegulation==null || kmReviewMainForm.titleRegulation=='' }">
									<xform:text property="docSubject" mobile="true"/>
								</c:if>
								<c:if test="${kmReviewMainForm.titleRegulation!=null && kmReviewMainForm.titleRegulation!='' }">
									<xform:text property="docSubject" mobile="true" showStatus="readOnly" value="${lfn:message('km-review:kmReviewMain.docSubject.info') }" />
								</c:if>
							</td>
						</tr><tr>
							<td class="muiTitle">
								<bean:message bundle="km-review" key="kmReviewTemplate.fdName" />
							</td><td>
								<html:hidden property="fdTemplateId" /> 
								<xform:text property="fdTemplateName" mobile="true" showStatus="view"/>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="km-review" key="kmReviewMain.docCreatorName" />
							</td><td>
								<xform:text property="docCreatorName" mobile="true" showStatus="view"/>
							</td>
						</tr>
					</table>
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
				</div>
			</div>
			<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message  bundle="km-review" key="kmReview.config.info"/>',icon:'mui-ul'">
					<c:if test="${kmReviewMainForm.fdUseForm == 'false'}">
						<div class="muiFormContent">
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<tr>
								<td colspan="2">
									<c:set property="docContent" target="${kmReviewMainForm}" value=""/>
									<xform:textarea property="docContent" mobile="true"/>
								</td>
							</tr><tr>
								<td colspan="2">
									<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
										<c:param name="formName" value="kmReviewMainForm"></c:param>
										<c:param name="fdKey" value="fdAttachment"></c:param>
									</c:import> 
								</td>
							</tr>
						</table>
						</div>
					</c:if>
					<c:if test="${kmReviewMainForm.fdUseForm == 'true' || empty kmReviewMainForm.fdUseForm}">
						<div data-dojo-type="mui/table/ScrollableHContainer">
							<div data-dojo-type="mui/table/ScrollableHView" class="muiFormContent">
							<c:import url="/sys/xform/mobile/import/sysForm_mobile.jsp"
								charEncoding="UTF-8">
								<c:param name="formName" value="kmReviewMainForm" />
								<c:param name="fdKey" value="reviewMainDoc" />
								<c:param name="backTo" value="scrollView" />
							</c:import>
							<br/>
						</div>
						</div>
					</c:if>
			</div>
		</div>
		<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" <c:if test="${'false' ne kmReviewMainForm.fdIsMobileCreate}">data-dojo-props='fill:"grid"'</c:if>>
		  	<li data-dojo-type="mui/back/BackButton" edit="true"></li>
		  	<c:if test="${'false' ne kmReviewMainForm.fdIsMobileCreate}">
			  	<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext " 
			  		data-dojo-props='colSize:2,moveTo:"lbpmView",icon1:"mui mui-nextStep",transition:"slide"'><bean:message  bundle="km-review"  key="button.next" /></li>
		  	</c:if>
		   	<li data-dojo-type="mui/tabbar/TabBarButtonGroup" 
		   		data-dojo-props="icon1:'mui mui-more',align:'right'">
		    	<div data-dojo-type="mui/back/HomeButton"></div>
		   	</li>
		</ul>
	</div>
	<c:choose>
		<c:when test="${'false' eq kmReviewMainForm.fdIsMobileCreate}">
			<script type="text/javascript">
				require(["mui/dialog/BarTip", "dojo/ready"], function(BarTip, ready) {
					ready(function() {
						BarTip.tip({text: "<bean:message key='km-review:kmReviewTemplate.tipmessage.create'/>"});
					});
				});
			</script>
		</c:when>
		<c:otherwise>
			<c:import url="/sys/lbpmservice/mobile/import/edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmReviewMainForm" />
				<c:param name="fdKey" value="reviewMainDoc" />
				<c:param name="viewName" value="lbpmView" />
				<c:param name="backTo" value="scrollView" />
				<c:param name="onClickSubmitButton" value="review_submit();" />
			</c:import>
			<script type="text/javascript">
			require(["mui/form/ajax-form!kmReviewMainForm"]);
			function review_submit(){
				var status = document.getElementsByName("docStatus")[0];
				var method = Com_GetUrlParameter(location.href,'method');
				if(method=='add'){
					Com_Submit(document.forms[0],'save');
				}else{
					if(status.value=='10'||status.value=='11'){
						Com_Submit(document.forms[0],'publishDraft');
					}else{
						Com_Submit(document.forms[0],'update');
					}
				}
			}
			</script>
		</c:otherwise>
	</c:choose>
</html:form>