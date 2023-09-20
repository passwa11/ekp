<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscLoanControl" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdValidity.name" title="${lfn:message('fssc-loan:fsscLoanControl.fdValidity')}">
            <sunbor:enumsShow value="${fsscLoanControl.fdValidity}" enumsType="fssc_loan_fd_validity" />
        </list:data-column>
        <list:data-column col="fdValidity">
            <c:out value="${fsscLoanControl.fdValidity}" />
        </list:data-column>
        <list:data-column col="fdForbid.name" title="${lfn:message('fssc-loan:fsscLoanControl.fdForbid')}">
            <sunbor:enumsShow value="${fsscLoanControl.fdForbid}" enumsType="fssc_loan_fd_forbid" />
        </list:data-column>
        <list:data-column col="fdForbid">
            <c:out value="${fsscLoanControl.fdForbid}" />
        </list:data-column>
        <list:data-column col="docAlteror.name" title="${lfn:message('fssc-loan:fsscLoanControl.docAlteror')}" escape="false">
            <c:out value="${fsscLoanControl.docAlteror.fdName}" />
        </list:data-column>
        <list:data-column col="docAlteror.id" escape="false">
            <c:out value="${fsscLoanControl.docAlteror.fdId}" />
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('fssc-loan:fsscLoanControl.docAlterTime')}">
            <kmss:showDate value="${fsscLoanControl.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
