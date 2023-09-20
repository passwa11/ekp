<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>

<list:data>
    <list:data-columns var="item" list="${queryPage.list}" varIndex="status" custom="false">
    	<list:data-column col="fdId">
            <c:out value="${item.fdKnowledgeBaseDoc.fdId}"/>
        </list:data-column>
        <list:data-column col="fdBorrowId">
            <c:out value="${item.fdId}"/>
        </list:data-column>
        <list:data-column property="docSubject" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.docSubject')}" />
        <list:data-column col="fdBorrowEffectiveTime" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.fdBorrowEffectiveTime')}">
            <kmss:showDate value="${item.fdBorrowEffectiveTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdDuration" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.fdDuration')}">
        	<c:choose>
				<c:when test="${'0' eq item.fdDuration}">
					<c:out
						value="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.fdStatus.forever') }" />
				</c:when>
					<c:otherwise>
				    <c:out
						value="${item.fdDuration }" />
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdStatusName" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.fdStatus')}">
			<c:choose>
				<c:when test="${('0' eq item.fdStatus) or ((nowDate - item.fdBorrowEffectiveTime.getTime()) < 0 )}">
					<c:out
						value="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.fdStatus.undo') }" />
				</c:when>
				<c:when test="${'1' eq item.fdStatus}">
					<c:out
						value="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.fdStatus.doing') }" />
				</c:when>
				<c:when test="${'2' eq item.fdStatus}">
					<c:out
						value="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.fdStatus.done') }" />
				</c:when>
				<c:otherwise>
				    <c:out
						value="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.fdStatus.close') }" />
				</c:otherwise>
			</c:choose>
		</list:data-column>
    	<list:data-column property="docCreator.fdName" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.docCreator')}">
		</list:data-column>
			<list:data-column property="docCreateTime" title="${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.docCreateTime') }">
		</list:data-column>
		<list:data-column col="docAuthor" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.docAuthor')}">
			<c:out value="${item.fdKnowledgeBaseDoc.docCreator.fdName }" />
		</list:data-column>
		<list:data-column col="docTemplate" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.docTemplate')}">
			<c:out value="${item.fdKnowledgeBaseDoc.docCategory.fdName }" />
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>