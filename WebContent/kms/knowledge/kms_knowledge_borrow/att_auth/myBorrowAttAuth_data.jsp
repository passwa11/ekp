<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="item" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="fdDocId" >
            ${item.fdKnowledgeBaseDoc.fdId}
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
		<list:data-column property="docSubject" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docSubjects')}" />
        <list:data-column col="fdDocVersion" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docVersion')}" escape="false">
			 ${item.fdKnowledgeBaseDoc.docMainVersion}.${item.fdKnowledgeBaseDoc.docAuxiVersion}
        </list:data-column>
		<list:data-column
				col="docAuthor.fdName"
				title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docAuthor') }"
				escape="false">
			<c:if test="${not empty item.fdKnowledgeBaseDoc.fdDocAuthorList}">
				<c:forEach var="obj" items="${item.fdKnowledgeBaseDoc.fdDocAuthorList}" varStatus="status">
					<c:choose>
						<c:when test="${status.count==item.fdKnowledgeBaseDoc.fdDocAuthorList.size() }">
							<c:out value="${obj.fdSysOrgElement.fdName}"/>
						</c:when>
						<c:otherwise>
							<c:out value="${obj.fdSysOrgElement.fdName}"/>;
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</c:if>
			<c:if test="${empty item.fdKnowledgeBaseDoc.fdDocAuthorList  }">
				<c:out value="${item.fdKnowledgeBaseDoc.outerAuthor }"/>
			</c:if>
		</list:data-column>
       <list:data-column col="docAuthor.fdId" escape="false">
            <c:out value="${item.fdKnowledgeBaseDoc.docAuthor.fdId}" />
        </list:data-column>      
    	<list:data-column col="docPublishTime" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docPublishTime')}">
        	<kmss:showDate value="${item.fdKnowledgeBaseDoc.docPublishTime}" type="datetime"></kmss:showDate>
    	</list:data-column>
    	<list:data-column col="docCategory.name" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCategory')}" escape="false">
            <c:out value="${item.fdKnowledgeBaseDoc.docCategory.fdName}" />
        </list:data-column>
        <list:data-column col="fdStatus" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdStatus')}"/>
        <list:data-column col="fdStatusName" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdStatus')}">
			<c:choose>
				<c:when test="${'0' eq item.fdStatus}">
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
					<%--
				    <c:out
						value="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.fdStatus.close') }" />
					--%>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdEffectiveTime" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdEffectiveTime')}">
            <kmss:showDate value="${item.fdEffectiveTime}" type="datetime"></kmss:showDate>
        </list:data-column>  
        		<list:data-column col="docCreateTime" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdApplicationTime')}">
            <kmss:showDate value="${item.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>  
        <list:data-column col="fdDuration" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.fdDuration')}">
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
		</list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
