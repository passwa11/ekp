<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>

<list:data>
    <list:data-columns var="fsscBudgetMain" list="${queryPage.list }" varIndex="status" mobile="true">
        <list:data-column property="fdId">
        </list:data-column>
        <list:data-column col="href" escape="false">
            /fssc/budget/fssc_budget_main/fsscBudgetMain.do?method=view&fdId=${fsscBudgetMain.fdId}
        </list:data-column>



        <list:data-column col="creator" title="${lfn:message('fssc-budget:fsscBudgetMain.docCreator')}" property="docCreator.fdName" />

        <list:data-column col="created" title="${lfn:message('fssc-budget:fsscBudgetMain.docCreateTime')}">
            <kmss:showDate value="${fsscBudgetMain.docCreateTime}" type="date"></kmss:showDate>
        </list:data-column>

        <list:data-column col="icon" escape="false">
            <person:headimageUrl personId="${fsscBudgetMain.docCreator.fdId}" size="m" />
        </list:data-column>


        <list:data-column col="summary" title="${lfn:message('fssc-budget:fsscBudgetMain.fdDesc')}" property="fdDesc" />
    </list:data-columns>

    <list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
    </list:data-paging>
</list:data>
