<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="kmReviewMainForm" value="${requestScope[param.formName]}" />
<c:if test="${JsParam.enable ne 'false' && kmReviewMainForm.circulationForm.fdIsShow=='true'}">
	<c:choose>
		<c:when test="${JsParam.isNewVersion eq 'true'}">
			<c:choose>
				<c:when test="${JsParam.existOpinion eq 'true'}">
					<div data-dojo-type="sys/circulation/mobile/js/CirculationReplyButton"
						data-dojo-props='icon1:"${_cir_icon}",align:"${param.align}",label:"${ lfn:message('sys-circulation:sysCirculationMain.replyOpinion') }",
							modelName:"${kmReviewMainForm.modelClass.name}",modelId:"${kmReviewMainForm.fdId}",fdKey:"${param.fdKey }",addCirculate:"${addCirculate}"'>
					</div>
				</c:when>
				<c:otherwise>
					<c:set var="sysCirculationForm" value="${requestScope[JsParam.formName]}" />
					<c:set var="circulationUrl"
						value="/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=add&fdModelName=${sysCirculationForm.circulationForm.fdModelName}&fdModelId=${sysCirculationForm.circulationForm.fdModelId}&fdIsNewVersion=${sysCirculationForm.circulationForm.fdIsNewVersion}" />
					<c:set var="showButton" value="false"></c:set>
					<c:set var="addCirculate" value="false"></c:set>
					<c:set var="cir_label" value="${empty param.label ? lfn:message('sys-circulation:sysCirculationMain.button.circulation') : param.label}" />
					<kmss:auth requestURL="${circulationUrl}" requestMethod="GET">
						<c:set var="showButton" value="true"></c:set>
					</kmss:auth>
					<c:if test="${JsParam.isNewVersion !=null && JsParam.isNewVersion == 'true'}">
						<c:set var="isNewVersion" value="true" />
					</c:if>
					<c:if test="${showButton eq 'false' and  isNewVersion}">
						<kmss:auth requestURL="/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=addCirculate&fdModelName=${sysCirculationForm.circulationForm.fdModelName}&fdModelId=${sysCirculationForm.circulationForm.fdModelId}" requestMethod="GET">
							<c:set var="showButton" value="true"></c:set>
							<c:set var="addCirculate" value="true"></c:set>
						</kmss:auth>
					</c:if>
					<c:if test="${showButton eq 'true'}">
						<div data-dojo-type="sys/circulation/mobile/js/CirculationNewButton"
							data-dojo-props='icon1:"${_cir_icon}",align:"${param.align}",label:"${cir_label}",
								modelName:"${kmReviewMainForm.modelClass.name}",modelId:"${kmReviewMainForm.fdId}",fdKey:"${param.fdKey }",addCirculate:"${addCirculate}"'>
						</div>
					</c:if>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<c:set var="_cir_count" value="" />
			<c:set var="showOption" value="${param.showOption}" />
		    <c:set var="_cir_icon" value="" />
		   <c:set var="_cir_label" value="" />
		   
		  <c:if test="${showOption == 'icon' }">
		    <c:set var="_cir_icon" value="mui mui-intr" />
		</c:if>
		<c:if test="${showOption == 'label'}">
			<c:set var="_cir_label" value="${lfn:message('sys-circulation:sysCirculationMain.button.circulation')}" />
		</c:if>
		<c:if test="${showOption == 'all' || empty showOption }">
		    <c:set var="_cir_icon" value="mui mui-intr" />
		    <c:if test="${ empty showOption}">
				<c:set var="_cir_label" value="${param.label}" />
			</c:if>
			<c:if test="${ showOption == 'all'}">
				<c:set var="_cir_label" value="${lfn:message('sys-circulation:sysCirculationMain.button.circulation')}" />
			</c:if>
		</c:if>
		   
			<c:if test="${kmReviewMainForm.circulationForm.fdCirculationCount!=null && kmReviewMainForm.circulationForm.fdCirculationCount!='' }">
					<c:set var="_cir_count" value="${kmReviewMainForm.circulationForm.fdCirculationCount}" />
					<c:set var="_cir_count" value="${fn:replace(_cir_count,'(','')}" />
					<c:set var="_cir_count" value="${fn:replace(_cir_count,')','')}" />
			</c:if>
			<c:if test="${empty _cir_label}">
			<div data-dojo-type="mui/tabbar/TabBarButton"
				data-dojo-props='icon1:"${_cir_icon}",align:"${param.align}",
					badge:"${_cir_count}",href:"/sys/circulation/mobile/index.jsp?modelName=${kmReviewMainForm.modelClass.name}&modelId=${kmReviewMainForm.fdId}&fdKey=${param.fdKey }"'>
			</div>
			</c:if>
			<c:if test="${not empty _cir_label}">
			<c:if test="${not empty _cir_count}"><c:set var="_cir_label" value="${_cir_label}(${_cir_count})" /></c:if>
			<div data-dojo-type="mui/tabbar/TabBarButton"
				data-dojo-props='icon1:"",align:"${param.align}",label:"${_cir_label}",
					href:"/sys/circulation/mobile/index.jsp?modelName=${kmReviewMainForm.modelClass.name}&modelId=${kmReviewMainForm.fdId}&fdKey=${param.fdKey}&isNew=${param.isNew}"'>
			</div>
			</c:if>
		</c:otherwise>
	</c:choose>
</c:if>
