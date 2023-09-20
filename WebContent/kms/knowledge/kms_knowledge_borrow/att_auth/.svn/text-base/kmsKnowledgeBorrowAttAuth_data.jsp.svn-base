<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="item" list="${queryPage.list}" varIndex="status" custom="false">
    	<list:data-column col="fdId">
            <c:out value="${item.fdId}"/>
        </list:data-column>
        <list:data-column col="fdDocId">
            <c:out value="${item.fdKnowledgeBaseDoc.fdId}"/>
        </list:data-column>
		<list:data-column col="index">
			${status+1}
		</list:data-column>
		<list:data-column col="lbmpDocStatus">
			<c:if test="${item.docStatus=='30'}">
				${lfn:message('kms-:kmsKnowledge.lbmp.docStatus_30')}
			</c:if>
			<c:if test="${item.docStatus=='00'}">
				${lfn:message('kms-knowledge:enums.doc_status.00')}
			</c:if>
			<c:if test="${item.docStatus=='11'}">
				${lfn:message('kms-knowledge:enums.doc_status.11')}
			</c:if>
		</list:data-column>
        <list:data-column property="docSubject" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.docSubject')}" />
        <list:data-column col="fdEffectiveTime" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdEffectiveTime')}">
            <kmss:showDate value="${item.fdEffectiveTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdDuration" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdDuration')}">
        	<c:choose>
				<c:when test="${'0' eq item.fdDuration}">
					<c:out
						value="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdStatus.forever') }" />
				</c:when>
					<c:otherwise>
				    <c:out
						value="${item.fdDuration }" />
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdStatusName" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdStatus')}">
			<c:choose>
				<c:when test="${'0' eq item.fdStatus}">
					<c:out
						value="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdStatus.undo') }" />
				</c:when>
				<c:when test="${'1' eq item.fdStatus}">
					<c:out
						value="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdStatus.doing') }" />
				</c:when>
				<c:when test="${'2' eq item.fdStatus}">
					<c:out
						value="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdStatus.done') }" />
				</c:when>
				<c:otherwise>
				    <c:out
						value="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdStatus.close') }" />
				</c:otherwise>
			</c:choose>
		</list:data-column>
    	<list:data-column property="docCreator.fdName" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.docCreator')}">
		</list:data-column>
			<list:data-column property="docCreateTime" title="${ lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.docCreateTime') }">
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>