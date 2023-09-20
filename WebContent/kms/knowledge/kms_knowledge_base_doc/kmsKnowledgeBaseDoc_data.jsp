<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<c:set var="docStatus" value="${docStatus}" scope="request" />
<c:set var="kmsCategoryEnabled" value="false"></c:set>	
<c:set var="kmsKnowledgeBaseDocDocCategory" value="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCategory') }"></c:set>	
<%
	KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
	String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
	if ("true".equals(kmsCategoryEnabled)) {
%>
	<c:set var="kmsCategoryEnabled" value="true"></c:set>	
	<c:set var="kmsKnowledgeBaseDocDocCategory" value="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCategory.categoryTrue') }"></c:set>
<%
	}
%>
<list:data>
	<list:data-columns var="kmsKnowledgeBaseDoc" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<%-- 标题 --%>
		<list:data-column style="width:35%;text-align:center" property="docSubject" title="${ lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docSubjects') }">
		</list:data-column>
		<%-- 类型 --%>
		<list:data-column style="width:35%;text-align:center" property="fdKnowledgeType">
		</list:data-column>
		<%-- 创建者 --%>
		<list:data-column property="docCreator.fdName" title="${ lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docAuthorId') }">
			<c:out value="${kmsKnowledgeBaseDoc.docCreator.fdName}" />
		</list:data-column>
		<%-- 创建时间 --%>
		<list:data-column col="docCreateTime"  title="${ lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCreateTime') }">
			<kmss:showDate value="${kmsKnowledgeBaseDoc.docCreateTime}" ></kmss:showDate>
		</list:data-column>
		<c:if test="${docStatus == '40'}">
			<list:data-column col="docExpireTime"  title="${ lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docExpireTime') }">
				<kmss:showDate value="${kmsKnowledgeBaseDoc.docExpireTime}" ></kmss:showDate>
			</list:data-column>
		</c:if>
		<c:if test="${docStatus == '60'}">
			<list:data-column col="docFailureTime"  title="${ lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docFailureTime') }">
				<kmss:showDate value="${kmsKnowledgeBaseDoc.docFailureTime}" ></kmss:showDate>
			</list:data-column>
		</c:if>
		<%-- 所属分类 --%>
		<list:data-column property="docCategory.fdName" title="${kmsKnowledgeBaseDocDocCategory}">
			<c:out value="${kmsKnowledgeBaseDoc.docCategory.fdName}" />
		</list:data-column>
		<list:data-column col="icon" escape="false" title="${lfn:message('kms-knowledge:kmsKnowledge.introduced')}">
			 <c:if test="${kmsKnowledgeBaseDoc.docStatus=='60'}">
			 	<span class="lui_icon_s lui_icon_s_icon_invalid" title="${ lfn:message('kms-common:kmsRemindObject.invalid') }"></span>
			 </c:if>
		</list:data-column>
		
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>