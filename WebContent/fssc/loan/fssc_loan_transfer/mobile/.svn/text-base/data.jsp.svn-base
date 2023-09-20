<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>

<list:data>
    <list:data-columns var="fsscLoanTransfer" list="${queryPage.list }" varIndex="status" mobile="true">
        <list:data-column property="fdId">
        </list:data-column>
        <list:data-column col="href" escape="false">
            /fssc/loan/fssc_loan_transfer/fsscLoanTransfer.do?method=view&fdId=${fsscLoanTransfer.fdId}
        </list:data-column>


        <list:data-column col="label" title="${lfn:message('fssc-loan:fsscLoanTransfer.docSubject')}" property="docSubject" />

        <c:if test="${fsscLoanTransfer.docCreator.fdName!=undefined}">
            <list:data-column col="creator" title="${lfn:message('fssc-loan:fsscLoanTransfer.docCreator')}" property="docCreator.fdName" />
        </c:if>

        <list:data-column col="created" title="${lfn:message('fssc-loan:fsscLoanTransfer.docCreateTime')}">
            <kmss:showDate value="${fsscLoanTransfer.docCreateTime}" type="date"></kmss:showDate>
        </list:data-column>

        <list:data-column col="icon" escape="false">
            <person:headimageUrl personId="${fsscLoanTransfer.docCreator.fdId}" size="m" />
        </list:data-column>

    </list:data-columns>

    <list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
    </list:data-paging>
</list:data>
