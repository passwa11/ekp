<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.DateUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.km.comminfo.forms.KmComminfoMainForm"%>
<%@ page import="java.util.Locale"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%
//移动端发布时间只显示日期，不显示时间
Locale locale = request.getLocale();
KmComminfoMainForm kmComminfoMainForm = (KmComminfoMainForm)request.getAttribute("kmComminfoMainForm");
kmComminfoMainForm.setDocCreateTime(DateUtil.convertDateToString(DateUtil.convertStringToDate(
		kmComminfoMainForm.getDocCreateTime(), "datetime",locale), "date",locale));
%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="head">
		<link rel="stylesheet" href="${LUI_ContextPath}/km/comminfo/mobile/resource/css/comminfo.css?s_cache=${MUI_Cache}" />
	</template:replace>
	<template:replace name="title">
		<c:out value="${kmComminfoMainForm.docSubject}"></c:out>
	</template:replace>
	<template:replace name="content">
		<div id="scrollView"  data-dojo-type="mui/view/DocScrollableView">
		<c:choose>
			<c:when test='${fn:contains(kmComminfoMainForm.docSubject,"\'")}'>
				<div id="_banner" data-dojo-type="km/comminfo/mobile/resource/js/ViewBanner" data-dojo-props='
					created:"${kmComminfoMainForm.docCreateTime}",
					icon:"<person:headimageUrl contextPath='true' personId='${kmComminfoMainForm.docCreatorId}' size='m' />",
					creator:"${kmComminfoMainForm.docCreatorName}",
					docSubject:"${fn:escapeXml(kmComminfoMainForm.docSubject)}"'></div>
			</c:when>
			<c:otherwise>
				<div id="_banner" data-dojo-type="km/comminfo/mobile/resource/js/ViewBanner" data-dojo-props="
					created:'${kmComminfoMainForm.docCreateTime}',
					icon:'<person:headimageUrl contextPath="true" personId="${kmComminfoMainForm.docCreatorId}" size="m" />',
					creator:'${kmComminfoMainForm.docCreatorName}',
					docSubject:'${fn:escapeXml(kmComminfoMainForm.docSubject)}'"></div>
			</c:otherwise>
		</c:choose>
			
			<div class="muiDocFrame">
				<%--文档内容--%>
				<div class="muiDocContent" id="contentDiv" style="z-index: -1;">
					<xform:rtf property="docContent" mobile="true"></xform:rtf>
				</div>
			</div>
			<div data-dojo-type="mui/panel/AccordionPanel">
				<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmComminfoMainForm" />
					<c:param name="fdKey" value="attachment" />
				</c:import>
			</div>
		</div>
	</template:replace>
</template:include>