<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>

<list:data>
    <list:data-columns var="fsscLoanMain" list="${queryPage.list }" varIndex="status" mobile="true">
        <list:data-column property="fdId">
        </list:data-column>
        <list:data-column col="href" escape="false">
            /fssc/loan/fssc_loan_main/fsscLoanMain.do?method=view&fdId=${fsscLoanMain.fdId}
        </list:data-column>


        <list:data-column col="label" title="${lfn:message('fssc-loan:fsscLoanMain.docSubject')}" property="docSubject" />

        <c:if test="${fsscLoanMain.docCreator.fdName!=undefined}">
            <list:data-column col="creator" title="${lfn:message('fssc-loan:fsscLoanMain.docCreator')}" property="docCreator.fdName" />
        </c:if>

        <list:data-column col="created" title="${lfn:message('fssc-loan:fsscLoanMain.docCreateTime')}">
            <kmss:showDate value="${fsscLoanMain.docCreateTime}" type="date"></kmss:showDate>
        </list:data-column>

        <list:data-column col="icon" escape="false">
            <person:headimageUrl personId="${fsscLoanMain.docCreator.fdId}" size="m" />
        </list:data-column>

        <list:data-column col="status" title="${lfn:message('fssc-loan:fsscLoanMain.docStatus')}" escape="false">
            <sunbor:enumsShow value="${fsscLoanMain.docStatus}" enumsType="fssc_loan_doc_status" />
        </list:data-column>
        <list:data-column col="statusIdx">
            <c:out value="${fsscLoanMain.docStatus}" />
        </list:data-column>

    </list:data-columns>

    <list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
    </list:data-paging>
</list:data>
