<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="sysIntroduceForm" value="${requestScope[param.formName]}" />
<c:if test="${sysIntroduceForm.introduceForm.fdIsShow=='true'}">
	<c:set var="_intr_count" value="" />
	<c:set var="showOption" value="${param.showOption}" />
	<c:set var="_intr_icon" value="" />
	<c:set var="_intr_label" value="" />
	
<c:if test="${showOption == 'icon'}">
    <c:set var="_intr_icon" value="mui mui-intr" />
</c:if>
<c:if test="${showOption == 'label'}">
	<c:set var="_intr_label" value="${lfn:message('sys-introduce:sysIntroduceMain.button.introduce')}" />
</c:if>
<c:if test="${showOption == 'all' || empty showOption }">
    <c:set var="_intr_icon" value="mui mui-intr" />
    <c:if test="${ empty showOption}">
		<c:set var="_intr_label" value="${param.label}" />
	</c:if>
	<c:if test="${ showOption == 'all'}">
		<c:set var="_intr_label" value="${lfn:message('sys-introduce:sysIntroduceMain.button.introduce')}" />
	</c:if>
</c:if>
	
	<c:if
		test="${sysIntroduceForm.introduceForm.fdIntroduceCount!=null && sysIntroduceForm.introduceForm.fdIntroduceCount!=''}">
		<c:set var="_intr_count"
			value="${sysIntroduceForm.introduceForm.fdIntroduceCount}" />
		<c:set var="_intr_count" value="${fn:replace(_intr_count,'(','')}" />
		<c:set var="_intr_count" value="${fn:replace(_intr_count,')','')}" />
	</c:if>
	<c:if test="${empty _intr_label}">
		<c:choose>
			<c:when test="${param.docFlag}">
				<li data-dojo-type="sys/evaluation/mobile/js/TabBarButton"
					data-dojo-props='icon1:"${_intr_icon}",align:"${param.align}",
			badge:"${_intr_count}",
			href:"/sys/introduce/mobile/index.jsp?modelName=${sysIntroduceForm.modelClass.name}&modelId=${sysIntroduceForm.fdId}&fdKey=${param.fdKey }"'>
				</li>
			</c:when>
			<c:otherwise>
				<li data-dojo-type="mui/tabbar/TabBarButton"
					data-dojo-props='icon1:"${_intr_icon}",align:"${param.align}",
			badge:"${_intr_count}",
			href:"/sys/introduce/mobile/index.jsp?modelName=${sysIntroduceForm.modelClass.name}&modelId=${sysIntroduceForm.fdId}&fdKey=${param.fdKey }"'>
				</li>
			</c:otherwise>
		</c:choose>
	</c:if>
	<c:if test="${not empty _intr_label}">
	<c:if test="${not empty _intr_count}"><c:set var="_intr_label" value="${_intr_label}(${_intr_count})" /></c:if>
	<li data-dojo-type="mui/tabbar/TabBarButton"
		data-dojo-props='icon1:"",align:"${param.align}",label:"${_intr_label}",
			href:"/sys/introduce/mobile/index.jsp?modelName=${sysIntroduceForm.modelClass.name}&modelId=${sysIntroduceForm.fdId}&fdKey=${param.fdKey }"'>
	</li>
	</c:if>
</c:if>
