<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/resource/jsp/common.jsp"%>

<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" />

<div data-dojo-type="mui/tabbar/TabBar" fixed="bottom" class="muiTabBarBottomAdaptive">

	<c:choose>
		<c:when test="${pageScope.sysWfBusinessForm.sysWfBusinessForm.fdTemplateType == '4'}">
			<%-- “流程图”（自由流）按钮  --%>
			<li data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowChartButton" class="lbpmSwitchButton muiSplitterButton" data-dojo-props='icon1:"fontmuis muis-flow-chart"'>
				<bean:message bundle="sys-lbpmservice" key="lbpm.tab.graphic" />
			</li>
		</c:when>
		<c:otherwise>
			<%-- “流程图”按钮  --%>
			<li data-dojo-type="mui/tabbar/TabBarButton" class="lbpmSwitchButton muiSplitterButton" data-dojo-props='icon1:"fontmuis muis-flow-chart",onClick:showFlowChart'>
				<bean:message bundle="sys-lbpmservice" key="lbpm.tab.graphic" />
			</li>
		</c:otherwise>
	</c:choose>

	<%-- 智能检查助手  --%>
	<kmss:ifModuleExist path="/sys/iassister">
		<c:import url="/sys/iassister/mobile/tabbarButton.jsp" charEncoding="UTF-8">
			<c:param name="panelId" value="barRightPanel"></c:param>
			<c:param name="idx" value="2"></c:param>
			<c:param name="templateModelName" value="com.landray.kmss.km.review.model.KmReviewTemplate"></c:param>
			<c:param name="mainModelId" value="${kmReviewMainForm.fdId }"></c:param>
			<c:param name="templateId" value="${kmReviewMainForm.fdTemplateId }"></c:param>
			<c:param name="mainModelName" value="com.landray.kmss.km.review.model.KmReviewMain"></c:param>
			<c:param name="fdKey" value="reviewMainDoc"></c:param>
		</c:import>
	</kmss:ifModuleExist>

	<c:if test="${not empty param.supportSaveDraft && param.supportSaveDraft eq 'true' && pageScope.sysWfBusinessForm.docStatus == '10'}">
		<c:set var="validateDomId" value="${not empty param.saveDraftValidateDomId?param.saveDraftValidateDomId:''}" />
		<c:set var="validateElementId" value="${not empty param.saveDraftValidateElementId?param.saveDraftValidateElementId:''}" />
		<%-- 暂存  --%>
		<li id='saveDraftButton' data-dojo-type="mui/tabbar/SaveDraftButton" data-dojo-props='_resized:false,validateDomId:"${pageScope.validateDomId}",validateElementId:"${pageScope.validateElementId}",docStatus:"${pageScope.sysWfBusinessForm.docStatus}"' class="normalTabBarButton">
			<span><bean:message  bundle="sys-lbpmservice"  key="mui.button.savedraft" /></span>
		</li>
	</c:if>

	<c:if test="${not empty param.onClickSubmitButton && param.onClickSubmitButton ne '' }">
		<%-- “提交”按钮  --%>
		<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props='id:"process_review_button"' class="mainTabBarButton" onclick="${param.onClickSubmitButton};">
			<span><bean:message  key="button.submit" /></span>
		</li>
	</c:if>

</div>
<div data-dojo-type="mui/tabbar/TabBarAdapter" style="display: none"></div>