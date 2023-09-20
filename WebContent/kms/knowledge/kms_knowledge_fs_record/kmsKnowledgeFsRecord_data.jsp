<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="kmsKnowledgeFsRecord" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column property="fdName" title="${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdName')}" />
        <list:data-column property="fdTotalSize" title="${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdTotalSize')}" />
        <list:data-column property="fdSuccessSize" title="${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdSuccessSize')}" />
        <list:data-column property="fdErrorSize" title="${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdErrorSize')}" />
        <list:data-column col="fdStatus" title=" ${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdStatusText')}" >
            <c:if test="${kmsKnowledgeFsRecord.fdStatus == '0'}">
                ${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdStatus.0')}
            </c:if>
            <c:if test="${kmsKnowledgeFsRecord.fdStatus == '1'}">
                ${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdStatus.1')}
            </c:if>
            <c:if test="${kmsKnowledgeFsRecord.fdStatus == '2'}">
                ${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdStatus.2')}
            </c:if>
            <c:if test="${kmsKnowledgeFsRecord.fdStatus == '3'}">
                ${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdStatus.3')}
            </c:if>
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.docCreator')}" escape="false">
            <c:out value="${kmsKnowledgeFsRecord.docCreator.fdName}" />
        </list:data-column>
        <list:data-column property="docCreateTime" title="${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.docCreateTime')}" />
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${kmsKnowledgeFsRecord.docCreator.fdId}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
