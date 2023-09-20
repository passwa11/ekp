<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="kmsKnowledgeFsReDetail" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column property="fdName" title="${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.docSubject')}" />
        <list:data-column col="fdType" title="${lfn:message('kms-knowledge:kmsKnowledgeFsReDetail.fdType')}" >
            <c:if test="${kmsKnowledgeFsReDetail.fdType == '0'}">
                ${lfn:message('kms-knowledge:kmsKnowledgeFsReDetail.fdType.0')}
            </c:if>
            <c:if test="${kmsKnowledgeFsReDetail.fdType == '1'}">
                ${lfn:message('kms-knowledge:kmsKnowledgeFsReDetail.fdType.1')}
            </c:if>
        </list:data-column>
        <list:data-column property="fdRowNumber" title="${lfn:message('kms-knowledge:kmsKnowledgeFsReDetail.fdRowNumber')}" />
        <list:data-column col="fdMsg" title="${lfn:message('kms-knowledge:kmsKnowledgeFsReDetail.fdMsg')}" escape="false">
            <c:if test="${kmsKnowledgeFsReDetail.fdType == '1'}">
                <a style="color:blue;" href="${LUI_ContextPath}/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=view&amp;fdId=${kmsKnowledgeFsReDetail.fdModelId}">
                        ${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.viewDoc')}
                </a>
            </c:if>
            <c:if test="${kmsKnowledgeFsReDetail.fdType == '0'}">
                    <span style="color:red;">
                    <c:out value="${kmsKnowledgeFsReDetail.fdErrorMsg}"/>
                </span>
            </c:if>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
