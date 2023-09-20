<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>

<list:data>
    <list:data-columns var="fsscBudgetAdjustMain" list="${queryPage.list }" varIndex="status" mobile="true">
        <list:data-column property="fdId">
        </list:data-column>
        <list:data-column col="href" escape="false">
            /fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=view&fdId=${fsscBudgetAdjustMain.fdId}
        </list:data-column>


        <list:data-column col="label" title="${lfn:message('fssc-budget:fsscBudgetAdjustMain.docSubject')}" property="docSubject" />

        <c:if test="${fsscBudgetAdjustMain.docCreator.fdName!=undefined}">
            <list:data-column col="creator" title="${lfn:message('fssc-budget:fsscBudgetAdjustMain.docCreator')}" property="docCreator.fdName" />
        </c:if>

        <list:data-column col="created" title="${lfn:message('fssc-budget:fsscBudgetAdjustMain.docCreateTime')}">
            <kmss:showDate value="${fsscBudgetAdjustMain.docCreateTime}" type="date"></kmss:showDate>
        </list:data-column>

        <list:data-column col="icon" escape="false">
            <person:headimageUrl personId="${fsscBudgetAdjustMain.docCreator.fdId}" size="m" />
        </list:data-column>

        <list:data-column col="status" title="${lfn:message('fssc-budget:fsscBudgetAdjustMain.docStatus')}" escape="false">
            <sunbor:enumsShow value="${fsscBudgetAdjustMain.docStatus}" enumsType="fssc_budget_doc_status" />
        </list:data-column>
        <list:data-column col="statusIdx">
            <c:out value="${fsscBudgetAdjustMain.docStatus}" />
        </list:data-column>

        <list:data-column col="summary" title="${lfn:message('fssc-budget:fsscBudgetAdjustMain.fdDesc')}" property="fdDesc" />
    </list:data-columns>

    <list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
    </list:data-paging>
</list:data>
