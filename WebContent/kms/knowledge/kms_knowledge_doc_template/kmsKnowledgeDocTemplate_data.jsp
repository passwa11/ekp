<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmsKnowledgeDocTemlpate" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<%-- 模板名称 --%>
		<list:data-column style="width:35%;text-align:center" property="fdName" title="${ lfn:message('kms-knowledge:kmsKnowledgeDocTemplate.fdName') }">
		</list:data-column>
		<%-- 排序号 --%>
		<list:data-column col="fdOrder" title="${ lfn:message('kms-knowledge:kmsKnowledgeDocTemplate.fdOrder') }">
			<c:out value="${kmsKnowledgeDocTemlpate.fdOrder}" />
		</list:data-column>
		<%-- 创建者 --%>
		<list:data-column property="docCreator.fdName" title="${ lfn:message('kms-knowledge:kmsKnowledgeDocTemplate.docCreator') }">
			<c:out value="${kmsKnowledgeDocTemlpate.docCreator.fdName}" />
		</list:data-column>
		<%-- 创建时间 --%>
		<list:data-column col="docCreateTime"  title="${ lfn:message('kms-knowledge:kmsKnowledgeDocTemplate.docCreateTime') }">
			<kmss:showDate value="${kmsKnowledgeDocTemlpate.docCreateTime}" type="date"></kmss:showDate>
		</list:data-column>
		
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>