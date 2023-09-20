<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
<list:data>
    <list:data-columns var="fsscBudgetMain" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdCompany.name" title="${lfn:message('fssc-budget:fsscBudgetMain.fdCompany')}" escape="false">
            <c:out value="${fsscBudgetMain.fdCompany.fdName}" />
        </list:data-column>
        <list:data-column col="fdCompany.id" escape="false">
            <c:out value="${fsscBudgetMain.fdCompany.fdId}" />
        </list:data-column>
        <list:data-column col="fdYear" title="${lfn:message('fssc-budget:fsscBudgetMain.fdYear')}">
        	<kmss:showPeriod value="${fsscBudgetMain.fdYear}"></kmss:showPeriod>
		</list:data-column>
        <list:data-column col="fdBudgetScheme.name" title="${lfn:message('fssc-budget:fsscBudgetMain.fdBudgetScheme')}" escape="false">
            <c:out value="${fsscBudgetMain.fdBudgetScheme.fdName}" />
        </list:data-column>
        <list:data-column col="fdBudgetScheme.id" escape="false">
            <c:out value="${fsscBudgetMain.fdBudgetScheme.fdId}" />
        </list:data-column>
        <fssc:switchOn property="fdCompanyGroup">
        <list:data-column col="fdCompanyGroup.name" title="${lfn:message('fssc-budget:fsscBudgetMain.fdCompanyGroup')}" escape="false">
            <c:out value="${fsscBudgetMain.fdCompanyGroup.fdName}" />
        </list:data-column>
        <list:data-column col="fdCompanyGroup.id" escape="false">
            <c:out value="${fsscBudgetMain.fdCompanyGroup.fdId}" />
        </list:data-column>
        </fssc:switchOn>
        <list:data-column col="fdEnableDate" title="${lfn:message('fssc-budget:fsscBudgetMain.fdEnableDate')}">
            <kmss:showDate value="${fsscBudgetMain.fdEnableDate}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-budget:fsscBudgetMain.docCreateTime')}">
            <kmss:showDate value="${fsscBudgetMain.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('fssc-budget:fsscBudgetMain.docCreator')}" escape="false">
            <c:out value="${fsscBudgetMain.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${fsscBudgetMain.docCreator.fdId}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
