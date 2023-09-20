<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>

<list:data>
    <list:data-columns var="fsscLoanRepayment" list="${queryPage.list }" varIndex="status" mobile="true">
        <list:data-column property="fdId">
        </list:data-column>
        <list:data-column col="href" escape="false">
            /fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=view&fdId=${fsscLoanRepayment.fdId}
        </list:data-column>


        <list:data-column col="label" title="${lfn:message('fssc-loan:fsscLoanRepayment.docSubject')}" property="docSubject" />

        <c:if test="${fsscLoanRepayment.docCreator.fdName!=undefined}">
            <list:data-column col="creator" title="${lfn:message('fssc-loan:fsscLoanRepayment.docCreator')}" property="docCreator.fdName" />
        </c:if>

        <list:data-column col="created" title="${lfn:message('fssc-loan:fsscLoanRepayment.docCreateTime')}">
            <kmss:showDate value="${fsscLoanRepayment.docCreateTime}" type="date"></kmss:showDate>
        </list:data-column>

        <list:data-column col="icon" escape="false">
            <person:headimageUrl personId="${fsscLoanRepayment.docCreator.fdId}" size="m" />
        </list:data-column>

        <list:data-column col="status" title="${lfn:message('fssc-loan:fsscLoanRepayment.docStatus')}" escape="false">
            <sunbor:enumsShow value="${fsscLoanRepayment.docStatus}" enumsType="fssc_loan_doc_status" />
        </list:data-column>
        <list:data-column col="statusIdx">
            <c:out value="${fsscLoanRepayment.docStatus}" />
        </list:data-column>

    </list:data-columns>

    <list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
    </list:data-paging>
</list:data>
