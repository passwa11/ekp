<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page	import="com.landray.kmss.kms.knowledge.util.KmsKnowledgeUtil"%>
<%@page	import="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"%>

<list:data>
    <list:data-columns var="item" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docSubjects')}"/>
		<list:data-column col="docPublishTime" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docPublishTime')}">
			<kmss:showDate value="${item.docPublishTime}" type="datetime"></kmss:showDate>
		</list:data-column>
		<list:data-column col="docCreateTime" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCreateTime')}">
			<kmss:showDate value="${item.docCreateTime}" type="datetime"></kmss:showDate>
		</list:data-column>
		<list:data-column col="docAlterTime" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docAlterTime')}">
			<kmss:showDate value="${item.docAlterTime}" type="datetime"></kmss:showDate>
		</list:data-column>
		<list:data-column col="docCreator.fdName" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCreator')}">
			<c:out value="${item.docCreator.fdName}" />
		</list:data-column>
		<list:data-column col="docCreator.fdId">
			<c:out value="${item.docCreator.fdId}" />
		</list:data-column>
		<list:data-column
				col="docAuthor.fdName"
				title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docAuthor') }"
				escape="false">
			<c:if test="${not empty item.fdDocAuthorList}">
				<c:forEach var="obj" items="${item.fdDocAuthorList}" varStatus="status">
					<c:choose>
						<c:when test="${status.count==item.fdDocAuthorList.size() }">
							<c:out value="${obj.fdSysOrgElement.fdName}"/>
						</c:when>
						<c:otherwise>
							<c:out value="${obj.fdSysOrgElement.fdName}"/>;
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</c:if>
			<c:if test="${empty item.fdDocAuthorList  }">
				<c:out value="${item.outerAuthor }"/>
			</c:if>
		</list:data-column>
        <list:data-column col="docAuthor.fdId" escape="false">
            <c:out value="${item.docAuthor.fdId}" />
        </list:data-column>
		<list:data-column col="docAuthor.name" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docAuthor')}" escape="false">
			<c:if test="${empty item.outerAuthor }">
				<c:out value="${item.docAuthor.fdName}" />
			</c:if>
			<c:if test="${not empty item.outerAuthor }">
				<c:out value="${item.outerAuthor}" />
			</c:if>
		</list:data-column>
		<list:data-column col="docAuthor.id" escape="false">
			<c:out value="${item.docAuthor.fdId}" />
		</list:data-column>
		<list:data-column col="docCategory.name" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCategory')}" escape="false">
            <c:out value="${item.docCategory.fdName}" />
        </list:data-column>
        <list:data-column col="docCategory.id" escape="false">
            <c:out value="${item.docCategory.fdId}" />
        </list:data-column>
		<list:data-column col="docStatus">
			<c:out value="${item.docStatus}" />
		</list:data-column>
		<list:data-column col="docBorrowFlagName" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.readStatus') }" escape="false">
			<c:if test="${item.docBorrowFlag == 0}">
				<c:set var="borrowFlag" value="kms-knowledge-borrow:kmsKnowledgeBorrow.canRead"></c:set>
				<c:out value="${lfn:message(borrowFlag) }" />
			</c:if>
			<c:if test="${item.docBorrowFlag != 0}">
				<c:out value="-"></c:out>
			</c:if>
		</list:data-column>
		<list:data-column col="authAttDownload" title="附件下载权限">
			<%
				Object basedocObj = pageContext.getAttribute("item");
				if(basedocObj != null) {
					KmsKnowledgeBaseDoc basedoc = (KmsKnowledgeBaseDoc)basedocObj;
					if(KmsKnowledgeUtil.checkAuthAttDownload(basedoc)){
						out.print("1");
					}else {
						out.print("0");
					}
				}
			%>
		</list:data-column>
		<list:data-column col="authAttCopy" title="附件复制权限">
			<%
				Object basedocObj = pageContext.getAttribute("item");
				if(basedocObj != null) {
					KmsKnowledgeBaseDoc basedoc = (KmsKnowledgeBaseDoc)basedocObj;
					if(KmsKnowledgeUtil.checkAuthAttCopy(basedoc)){
						out.print("1");
					}else {
						out.print("0");
					}
				}
			%>
		</list:data-column>
		<list:data-column col="authAttPrint" title="附件打印权限">
			<%
				Object basedocObj = pageContext.getAttribute("item");
				if(basedocObj != null) {
					KmsKnowledgeBaseDoc basedoc = (KmsKnowledgeBaseDoc)basedocObj;
					if(KmsKnowledgeUtil.checkAuthAttPrint(basedoc)){
						out.print("1");
					}else {
						out.print("0");
					}
				}
			%>
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
