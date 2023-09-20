<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataCompany" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="fdDutyParagraph" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataCompany.fdName')}" escape="false" />
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataCompany.fdCode')}"  escape="false"/>
        <list:data-column col="fdBudgetCurrency.name" title="${lfn:message('eop-basedata:eopBasedataCompany.fdBudgetCurrency')}" escape="false">
            <c:out value="${eopBasedataCompany.fdBudgetCurrency.fdName}" />
        </list:data-column>
        <list:data-column col="fdBudgetCurrency.id" escape="false">
            <c:out value="${eopBasedataCompany.fdBudgetCurrency.fdId}" />
        </list:data-column>
        <list:data-column col="fdAccountCurrency.name" title="${lfn:message('eop-basedata:eopBasedataCompany.fdAccountCurrency')}" escape="false">
            <c:out value="${eopBasedataCompany.fdAccountCurrency.fdName}" />
        </list:data-column>
        <list:data-column col="fdAccountCurrency.id" escape="false">
            <c:out value="${eopBasedataCompany.fdAccountCurrency.fdId}" />
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataCompany.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataCompany.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataCompany.fdIsAvailable}" />
        </list:data-column>
        <list:data-column property="fdJoinSystem" title="${lfn:message('eop-basedata:eopBasedataCompany.fdJoinSystem')}"  escape="false"/>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <%--税号--%>
        <list:data-column col="fdDutyParagraph">
            ${eopBasedataCompany.fdDutyParagraph}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
