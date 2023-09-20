<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<html:form action="/km/review/km_review_main/kmReviewMain.do">	
	<div id="scrollView"
		data-dojo-type="mui/view/DocScrollableView"
		data-dojo-mixins="mui/form/_ValidateMixin">
		<div data-dojo-type="mui/panel/AccordionPanel">
			<div class="muiFlowInfoW muiFormContent">
				 <header class="muiFlowHeader">
	                <div class="muiProcessIcon">
	                    <img class="muiProcessImg" src='<person:headimageUrl personId="${kmReviewMainForm.docCreatorId}" size="m" />'/>
	                </div>
	                <div class="muiProcessTitle">
	                    <xform:text property="docCreatorName" mobile="true"/>
	                </div>
	                <c:if test="${kmReviewMainForm.docStatus eq '30'}">
		                <div class="muiProcessStatus" id=processStatusDiv>
		                    <i class="mui mui-processPass"></i>
		                </div>
	                </c:if>
	                 <c:if test="${kmReviewMainForm.docStatus eq '00'}">
		                <div class="muiDiscardStatus" id="discardStatusDiv">
		                    <i class="mui mui-processDiscard"></i>
		                </div>
	                </c:if>
	            </header>
				<table class="muiSimple" cellpadding="0" cellspacing="0">
					<tr>
						<td class="muiTitle">
							<bean:message bundle="km-review" key="kmReviewMain.docSubject" />
						</td><td>
							<xform:text property="docSubject" mobile="true"></xform:text>
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="km-review" key="kmReviewTemplate.fdName" />
						</td><td>
							<xform:text property="fdTemplateName" mobile="true"/>
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="km-review" key="kmReviewMain.fdNumber" />
						</td><td>
							<xform:text property="fdNumber" mobile="true"/>
						</td>
					</tr>
				</table>
			</div>
			<c:if test="${'true' eq kmReviewMainForm.fdIsMobileView}">
					<xform:isExistRelationProcesses relationType="parent">
					<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="km-review" key="mui.kmReviewMain.mobile.parentprocesses" />',icon:'mui-ul'">
						<xform:showParentProcesse mobile="true" />
					</div>
					</xform:isExistRelationProcesses>
					
					<xform:isExistRelationProcesses relationType="subs">
					<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="km-review" key="mui.kmReviewMain.mobile.subprocesses" />',icon:'mui-ul'">
						<xform:showSubProcesses mobile="true"/>
					</div>
					</xform:isExistRelationProcesses>
			</c:if>
			
			<%-- 支持移动端查阅 --%>
			<c:if test="${'true' eq kmReviewMainForm.fdIsMobileView}">
			<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="km-review" key="mui.kmReviewMain.mobile.info" />',icon:'mui-ul'">
				<c:if test="${kmReviewMainForm.fdUseForm == 'false'}">
					<br/>
					<xform:rtf property="docContent" mobile="true"></xform:rtf>
					<br/>
					<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmReviewMainForm"></c:param>
						<c:param name="fdKey" value="fdAttachment"></c:param>
					</c:import> 
					<br/>
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
						</div>
					</div>
				</c:if>
			</div>
			<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="km-review" key="mui.kmReviewMain.mobile.note" />',icon:'mui-ul'">
				<c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
					<c:param name="fdModelId" value="${kmReviewMainForm.fdId }"/>
					<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain"/>
					<c:param name="formBeanName" value="kmReviewMainForm"/>
				</c:import>
			</div>
			</c:if>
		</div>
		<c:if test="${kmReviewMainForm.docStatus >= '30' }">
			<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" >
			  <li data-dojo-type="mui/back/BackButton"></li>
			  <%-- 支持移动端查阅 --%>
			  <c:if test="${'true' eq kmReviewMainForm.fdIsMobileView}">
			  <c:import url="/sys/bookmark/mobile/import/view.jsp" charEncoding="UTF-8">
				  <c:param name="fdModelName" value="${kmReviewMainForm.modelClass.name}"/>
				  <c:param name="fdModelId" value="${kmReviewMainForm.fdId}"/>
				  <c:param name="fdSubject" value="${kmReviewMainForm.docSubject}"/>
			  </c:import>
			  <%--传阅 --%>
			  <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmReviewMainForm"></c:param>
			 </c:import>
			 </c:if>
			   <li data-dojo-type="mui/tabbar/TabBarButtonGroup" data-dojo-props="icon1:'mui mui-more'">
			    	<div data-dojo-type="mui/back/HomeButton"></div>
			    	<c:import url="/sys/relation/mobile/import/view.jsp" charEncoding="UTF-8">
			    		<c:param name="formName" value="kmReviewMainForm"/>
			    	</c:import>
			    </li>
			</ul>
		</c:if>
		<c:if test="${kmReviewMainForm.docStatus < '30' }">
			<c:choose>
				<c:when test="${'false' eq kmReviewMainForm.fdIsMobileApprove || 'false' eq kmReviewMainForm.fdIsMobileView}">
					<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" >
					  <li data-dojo-type="mui/back/BackButton"></li>
					  <%-- 支持移动端查阅 --%>
					  <c:if test="${'true' eq kmReviewMainForm.fdIsMobileView}">
						  <c:import url="/sys/bookmark/mobile/import/view.jsp" charEncoding="UTF-8">
								<c:param name="fdModelName" value="${kmReviewMainForm.modelClass.name}"/>
								<c:param name="fdModelId" value="${kmReviewMainForm.fdId}"/>
								<c:param name="fdSubject" value="${kmReviewMainForm.docSubject}"/>
						  </c:import>
						  <%--传阅 --%>
						  <c:if test="${kmReviewMainForm.docStatus > '10' }">
							 <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmReviewMainForm"></c:param>								
							 </c:import>
						  </c:if>
					  </c:if>
					  <li data-dojo-type="mui/tabbar/TabBarButtonGroup" data-dojo-props="icon1:'mui mui-more'">
					    <div data-dojo-type="mui/back/HomeButton"></div>
					   </li>
					</ul>
				</c:when>
				<c:otherwise>
					<template:include file="/sys/lbpmservice/mobile/import/bar.jsp" 
						editUrl="/km/review/km_review_main/kmReviewMain.do?method=edit&fdId=${param.fdId }"
						formName="kmReviewMainForm">
						<template:replace name="group">
							<template:super/>
							 <c:import url="/sys/bookmark/mobile/import/view.jsp" charEncoding="UTF-8">
								  <c:param name="fdModelName" value="${kmReviewMainForm.modelClass.name}"/>
								  <c:param name="fdModelId" value="${kmReviewMainForm.fdId}"/>
								  <c:param name="fdSubject" value="${kmReviewMainForm.docSubject}"/>
								  <c:param name="label" value="${lfn:message('sys-bookmark:button.bookmark')}"></c:param>
							 </c:import>
							 <%--传阅 --%>
							 <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmReviewMainForm"></c:param>
								<c:param name="showNum" value="false"></c:param>
							 </c:import>
							<c:import url="/sys/relation/mobile/import/view.jsp" charEncoding="UTF-8">
					    		<c:param name="formName" value="kmReviewMainForm"/>
					    	</c:import>
						</template:replace>
					</template:include>
				</c:otherwise>
			</c:choose>
		</c:if>
	</div>
	
	<%-- 支持移动端查阅 --%>
	<c:if test="${'true' eq kmReviewMainForm.fdIsMobileView}">
		<!-- 钉钉图标 -->
		<kmss:ifModuleExist path="/third/ding">
			<c:import url="/third/ding/import/ding_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmReviewMainForm" />
			</c:import>
		</kmss:ifModuleExist>
		
		<!-- 钉钉图标 end -->

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
						<c:param name="backTo" value="scrollView" />
						<c:param name="onClickSubmitButton" value="Com_Submit(document.kmReviewMainForm, 'publishDraft');" />
					</c:import>
					<script type="text/javascript">
						require(["mui/form/ajax-form!kmReviewMainForm"]);
					</script>
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${kmReviewMainForm.docStatus eq '30'}">
			<script type="text/javascript">
				require(["dojo/ready"], function(ready) {
					ready(function() {
						document.getElementById("processStatusDiv").className="muiProcessStatus stamp";
					});
				});
			</script>
		</c:if>
		<c:if test="${kmReviewMainForm.docStatus eq '00'}">
			<script type="text/javascript">
				require(["dojo/ready"], function(ready) {
					ready(function() {
						document.getElementById("discardStatusDiv").className="muiDiscardStatus stamp";
					});
				});
			</script>
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