<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscFeeMain" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="docSubject" title="${lfn:message('fssc-loan:fsscLoanMain.docSubject')}" />
        <list:data-column property="docNumber" title="${lfn:message('fssc-loan:fsscLoanMain.docNumber')}" />
        <list:data-column col="docCreator.name" title="${lfn:message('fssc-fee:fsscFeeMain.docCreator')}" escape="false">
            <c:out value="${fsscFeeMain.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${fsscFeeMain.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-fee:fsscFeeMain.docCreateTime')}">
            <kmss:showDate value="${fsscFeeMain.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docTemplate.name" title="${lfn:message('fssc-fee:fsscFeeMain.docTemplate')}" escape="false">
            <c:out value="${fsscFeeMain.docTemplate.fdName}" />
        </list:data-column>
        <list:data-column col="docTemplate.id" escape="false">
            <c:out value="${fsscFeeMain.docTemplate.fdId}" />
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
