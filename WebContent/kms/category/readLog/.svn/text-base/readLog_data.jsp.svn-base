<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="kmsCategoryKnowledgeRel" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdMainId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
		<list:data-column property="docSubject" title="${lfn:message('kms-category:kmsCategoryKnowledgeRel.docSubject')}" />
        <list:data-column property="docTemplate" title="${lfn:message('kms-category:kmsCategoryKnowledgeRel.docTemplate')}" />
        <list:data-column property="fdSourceType" title="${lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType')}" />
        <list:data-column property="docCreateTime" title="${lfn:message('kms-category:kmsCategoryKnowledge.docReadTime')}" />
        
        <list:data-column col="linkStr" escape="false">
			${kmsCategoryKnowledgeRel.linkStr}
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
