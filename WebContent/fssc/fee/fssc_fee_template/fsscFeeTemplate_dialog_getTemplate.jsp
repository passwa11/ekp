<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscFeeTemplate" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="fdName" title="${lfn:message('fssc-fee:fsscFeeTemplate.fdName')}" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdOrder" title="${lfn:message('fssc-fee:fsscFeeTemplate.fdOrder')}" />
        <list:data-column col="docCreator.name" title="${lfn:message('fssc-fee:fsscFeeTemplate.docCreator')}" escape="false">
            <c:out value="${fsscFeeTemplate.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${fsscFeeTemplate.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-fee:fsscFeeTemplate.docCreateTime')}">
            <kmss:showDate value="${fsscFeeTemplate.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docCategory.name" title="${lfn:message('fssc-fee:fsscFeeTemplate.docCategory')}" escape="false">
            <c:out value="${fsscFeeTemplate.docCategory.fdName}" />
        </list:data-column>
        <list:data-column col="docCategory.id" escape="false">
            <c:out value="${fsscFeeTemplate.docCategory.fdId}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
