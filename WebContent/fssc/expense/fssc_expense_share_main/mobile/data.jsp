<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>

<list:data>
    <list:data-columns var="fsscExpenseShareMain" list="${queryPage.list }" varIndex="status" mobile="true">
        <list:data-column property="fdId">
        </list:data-column>
        <list:data-column col="href" escape="false">
            /fssc/expense/fssc_expense_share_main/fsscExpenseShareMain.do?method=view&fdId=${fsscExpenseShareMain.fdId}
        </list:data-column>


        <list:data-column col="label" title="${lfn:message('fssc-expense:fsscExpenseShareMain.docSubject')}" property="docSubject" />

        <c:if test="${fsscExpenseShareMain.docCreator.fdName!=undefined}">
            <list:data-column col="creator" title="${lfn:message('fssc-expense:fsscExpenseShareMain.docCreator')}" property="docCreator.fdName" />
        </c:if>

        <list:data-column col="created" title="${lfn:message('fssc-expense:fsscExpenseShareMain.docCreateTime')}">
            <kmss:showDate value="${fsscExpenseShareMain.docCreateTime}" type="date"></kmss:showDate>
        </list:data-column>

        <list:data-column col="icon" escape="false">
            <person:headimageUrl personId="${fsscExpenseShareMain.docCreator.fdId}" size="m" />
        </list:data-column>

        <list:data-column col="status" title="${lfn:message('fssc-expense:fsscExpenseShareMain.docStatus')}" escape="false">
            <sunbor:enumsShow value="${fsscExpenseShareMain.docStatus}" enumsType="fssc_expense_doc_status" />
        </list:data-column>
        <list:data-column col="statusIdx">
            <c:out value="${fsscExpenseShareMain.docStatus}" />
        </list:data-column>

    </list:data-columns>

    <list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
    </list:data-paging>
</list:data>
