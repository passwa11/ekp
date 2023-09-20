<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>

<list:data>
    <list:data-columns var="fsscExpenseMain" list="${queryPage.list }" varIndex="status" mobile="true">
        <list:data-column property="fdId">
        </list:data-column>
        <list:data-column col="href" escape="false">
            /fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=view&fdId=${fsscExpenseMain.fdId}
        </list:data-column>


        <list:data-column col="label" title="${lfn:message('fssc-expense:fsscExpenseMain.docSubject')}" property="docSubject" />

        <c:if test="${fsscExpenseMain.docCreator.fdName!=undefined}">
            <list:data-column col="creator" title="${lfn:message('fssc-expense:fsscExpenseMain.docCreator')}" property="docCreator.fdName" />
        </c:if>

        <list:data-column col="created" title="${lfn:message('fssc-expense:fsscExpenseMain.docCreateTime')}">
            <kmss:showDate value="${fsscExpenseMain.docCreateTime}" type="date"></kmss:showDate>
        </list:data-column>

        <list:data-column col="icon" escape="false">
            <person:headimageUrl personId="${fsscExpenseMain.docCreator.fdId}" size="m" />
        </list:data-column>

        <list:data-column col="status" title="${lfn:message('fssc-expense:fsscExpenseMain.docStatus')}" escape="false">
            <sunbor:enumsShow value="${fsscExpenseMain.docStatus}" enumsType="fssc_expense_doc_status" />
        </list:data-column>
        <list:data-column col="statusIdx">
            <c:out value="${fsscExpenseMain.docStatus}" />
        </list:data-column>

    </list:data-columns>

    <list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
    </list:data-paging>
</list:data>
