<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.sunbor.web.tag.Page"%>
<%@page import="java.util.List"%>

<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.common.dao.IBaseDao"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="com.landray.kmss.util.NumberUtil"%>
<%@page	import="com.landray.kmss.kms.knowledge.util.KmsKnowledgeUtil"%>
<%@page	import="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"%>

<list:data>
	<list:data-columns var="item" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		
		<list:data-column property="docSubject"
			title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docSubjects')}">
		</list:data-column>
		<list:data-column col="label" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docSubjects')}">
			${item.docSubject}
		</list:data-column>
	
		<list:data-column col="created"
			title="${lfn:message('kms-knowledge:kmsKnowledge.docPublishTime') }">
			<kmss:showDate value="${item.docPublishTime}" type="date" />
		</list:data-column>

		<list:data-column col="count"
			title="${lfn:message('kms-knowledge:kmsKnowledge.read') }" escape="false">
			${item.docReadCount}
		</list:data-column>

		<list:data-column
			title="${lfn:message('kms-knowledge:kmsKnowledge.score') }"
			col="docScore" escape="false">
			<span class="com_number">${not empty scoreJson[item.fdId]? scoreJson[item.fdId]: 0}</span>
		</list:data-column>

		<list:data-column col="docCategory.fdName"
			title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docCategory.categoryTrue') }">
			${item.docCategoryName}
		</list:data-column>
		
		
		<list:data-column col="icon" title="imageLink" escape="false">
			<c:if test="${item.fdKnowledgeType eq '1'}">
				/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=docThumb&modelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc&fdId=${item.fdId}&knowledgeType=1
			</c:if>
			<c:if test="${item.fdKnowledgeType eq '2'}">
				/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=docThumb&modelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc&fdId=${item.fdId}&knowledgeType=2
			</c:if>
		</list:data-column>

		<list:data-column col="href" escape="false">
			<c:if test="${item.fdKnowledgeType eq '1' }">
				/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=view&fdId=${item.fdId}
			</c:if>
			<c:if test="${item.fdKnowledgeType eq '2' }">
				/kms/wiki/kms_wiki_main/kmsWikiMain.do?method=view&fdId=${item.fdId}
			</c:if>
			<c:if test="${item.fdKnowledgeType eq '3' }">
				/kms/kem/kms_kem_main/kmsKemMain.do?method=view&fdId=${item.fdId}
			</c:if>
		</list:data-column>
	</list:data-columns>

	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>