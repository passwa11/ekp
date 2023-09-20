<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<c:set var="kmsCategoryEnabled" value="false"></c:set>	
<c:set var="kmsKnowledgeCategoryFdName" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.fdName') }"></c:set>	
<%
	KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
	String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
	if ("true".equals(kmsCategoryEnabled)) {
%>
	<c:set var="kmsCategoryEnabled" value="true"></c:set>	
	<c:set var="kmsKnowledgeCategoryFdName" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.fdName.categoryTrue') }"></c:set>
<%
	}
%>
<list:data>
	<list:data-columns var="kmsKnowledgeCategory" list="${queryPage.list}"
		varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="fdName"
			title="${kmsKnowledgeCategoryFdName}">
		</list:data-column>
	</list:data-columns>
	<list:data-paging page="${queryPage}" />
</list:data>