<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page language="java"
	import="com.landray.kmss.kms.knowledge.util.KmsKnowledgeUtil"%>
<%@ page language="java" import="java.util.Map"%>
<%
	Map<String, String> configMap = KmsKnowledgeUtil.getFilterConfig();
	pageContext.setAttribute("configMap", configMap);
%>
<template:include ref="person.home" spa="true">
	<template:replace name="title">
		<c:out value="${ lfn:message('kms-multidoc:module.kms.multidoc') }"></c:out>
	</template:replace>
	<template:replace name="content">
		<c:set var="navTitle" value="${ lfn:message('kms-multidoc:kmsMultidoc.kmsMultidocKnowledge.zone.my') }"></c:set>
		<c:if test="${not empty param.navTitle}">
			<c:set var="navTitle" value="${param.navTitle}"></c:set>
		</c:if>
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${navTitle}">
				<c:import url="/kms/multidoc/kms_multidoc_personal/kmsMultidocKnowledge_my.jsp" charEncoding="UTF-8"/>
			</ui:content>
		</ui:tabpanel>
	</template:replace>
</template:include>
