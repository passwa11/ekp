<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmsKnowledgeWikiTemlpate" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<%-- 模板名称 --%>
		<list:data-column style="width:35%;text-align:center" property="fdName" title="${ lfn:message('kms-knowledge:kmsKnowledgeWikiTemplate.fdName') }">
		</list:data-column>
		<%-- 排序号 --%>
		<list:data-column col="fdOrder" title="${ lfn:message('kms-knowledge:kmsKnowledgeWikiTemplate.fdOrder') }">
			<c:out value="${kmsKnowledgeWikiTemlpate.fdOrder}" />
		</list:data-column>
		<%-- 创建者 --%>
		<list:data-column property="docCreator.fdName" title="${ lfn:message('kms-knowledge:kmsKnowledgeWikiTemplate.docCreator') }">
			<c:out value="${kmsKnowledgeWikiTemlpate.docCreator.fdName}" />
		</list:data-column>
		<%-- 创建时间 --%>
		<list:data-column col="docCreateTime"  title="${ lfn:message('kms-knowledge:kmsKnowledgeWikiTemplate.docCreateTime') }">
			<kmss:showDate value="${kmsKnowledgeWikiTemlpate.docCreateTime}" type="date"></kmss:showDate>
		</list:data-column>
		<%-- 修改者 --%>
		<list:data-column property="docAlteror.fdName" title="${ lfn:message('kms-knowledge:kmsKnowledgeWikiTemplate.docAlteror') }">
			<c:out value="${kmsKnowledgeWikiTemlpate.docAlteror.fdName}" />
		</list:data-column> 
		<%-- 最后修改时间 --%>
		<list:data-column col="docAlterTime" title="${ lfn:message('kms-knowledge:kmsKnowledgeWikiTemplate.docAlterTime') }">
			<kmss:showDate value="${kmsKnowledgeWikiTemlpate.docAlterTime}" type="date"></kmss:showDate>
		</list:data-column>
		
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>