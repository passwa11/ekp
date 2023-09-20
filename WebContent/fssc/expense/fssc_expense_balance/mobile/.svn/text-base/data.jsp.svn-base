<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>

<list:data>
    <list:data-columns var="fsscExpenseBalance" list="${queryPage.list }" varIndex="status" mobile="true">
        <list:data-column property="fdId">
        </list:data-column>
        <list:data-column col="href" escape="false">
            /fssc/expense/fssc_expense_balance/fsscExpenseBalance.do?method=view&fdId=${fsscExpenseBalance.fdId}
        </list:data-column>


        <list:data-column col="label" title="${lfn:message('fssc-expense:fsscExpenseBalance.docSubject')}" property="docSubject" />

        <c:if test="${fsscExpenseBalance.docCreator.fdName!=undefined}">
            <list:data-column col="creator" title="${lfn:message('fssc-expense:fsscExpenseBalance.docCreator')}" property="docCreator.fdName" />
        </c:if>

        <list:data-column col="created" title="${lfn:message('fssc-expense:fsscExpenseBalance.docCreateTime')}">
            <kmss:showDate value="${fsscExpenseBalance.docCreateTime}" type="date"></kmss:showDate>
        </list:data-column>

        <list:data-column col="icon" escape="false">
            <person:headimageUrl personId="${fsscExpenseBalance.docCreator.fdId}" size="m" />
        </list:data-column>

        <list:data-column col="status" title="${lfn:message('fssc-expense:fsscExpenseBalance.docStatus')}" escape="false">
            <sunbor:enumsShow value="${fsscExpenseBalance.docStatus}" enumsType="fssc_expense_doc_status" />
        </list:data-column>
        <list:data-column col="statusIdx">
            <c:out value="${fsscExpenseBalance.docStatus}" />
        </list:data-column>


        <list:data-column col="summary" title="${lfn:message('fssc-expense:lbpm.summary') }">
            <kmss:showWfPropertyValues idValue="${fsscExpenseBalance.fdId}" propertyName="summary" />
        </list:data-column>
    </list:data-columns>

    <list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
    </list:data-paging>
</list:data>
