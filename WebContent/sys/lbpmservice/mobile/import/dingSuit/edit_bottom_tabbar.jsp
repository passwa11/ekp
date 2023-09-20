<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/resource/jsp/common.jsp"%>

<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" />

<div data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
	<c:if test="${not empty param.supportSaveDraft && param.supportSaveDraft eq 'true' }">
	    <c:set var="validateDomId" value="${not empty param.saveDraftValidateDomId?param.saveDraftValidateDomId:''}" />
	    <c:set var="validateElementId" value="${not empty param.saveDraftValidateElementId?param.saveDraftValidateElementId:''}" />
	    <%-- 暂存  --%>
	  	<li id='saveDraftButton' data-dojo-type="mui/tabbar/SaveDraftButton" data-dojo-props='validateDomId:"${pageScope.validateDomId}",validateElementId:"${pageScope.validateElementId}",docStatus:"${pageScope.sysWfBusinessForm.docStatus}"' class="normalTabBarButton">
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