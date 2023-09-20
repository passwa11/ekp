<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscBudgetAdjustMain" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('fssc-budget:fsscBudgetAdjustMain.docSubject')}" />
        <list:data-column property="docNumber" title="${lfn:message('fssc-budget:fsscBudgetAdjustMain.docNumber')}" />
        <list:data-column col="fdCompany.name" title="${lfn:message('fssc-budget:fsscBudgetAdjustMain.fdCompany')}" escape="false">
            <c:out value="${fsscBudgetAdjustMain.fdCompany.fdName}" />
        </list:data-column>
        <list:data-column col="fdCompany.id" escape="false">
            <c:out value="${fsscBudgetAdjustMain.fdCompany.fdId}" />
        </list:data-column>
        <list:data-column col="docStatus.name" title="${lfn:message('fssc-budget:fsscBudgetAdjustMain.docStatus')}">
            <sunbor:enumsShow value="${fsscBudgetAdjustMain.docStatus}" enumsType="fssc_budget_doc_status" />
        </list:data-column>
        <list:data-column col="docStatus">
            <c:out value="${fsscBudgetAdjustMain.docStatus}" />
        </list:data-column>
        <list:data-column col="docTemplate.name" title="${lfn:message('fssc-budget:fsscBudgetAdjustMain.docTemplate')}" escape="false">
            <c:out value="${fsscBudgetAdjustMain.docTemplate.fdName}" />
        </list:data-column>
        <list:data-column col="docTemplate.id" escape="false">
            <c:out value="${fsscBudgetAdjustMain.docTemplate.fdId}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('fssc-budget:fsscBudgetAdjustMain.docCreator')}" escape="false">
            <c:out value="${fsscBudgetAdjustMain.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${fsscBudgetAdjustMain.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-budget:fsscBudgetAdjustMain.docCreateTime')}">
            <kmss:showDate value="${fsscBudgetAdjustMain.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_node" title="${lfn:message('fssc-budget:lbpm.currentNode') }" escape="false">
            <kmss:showWfPropertyValues var="nodevalue" idValue="${fsscBudgetAdjustMain.fdId}" propertyName="nodeName" />
            <div title="${nodevalue}">
                <c:out value="${nodevalue}"></c:out>
            </div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_handler" title="${lfn:message('fssc-budget:lbpm.currentHandler') }" escape="false">
            <kmss:showWfPropertyValues var="handlerValue" idValue="${fsscBudgetAdjustMain.fdId}" propertyName="handlerName" />
            <div style="font-weight:bold;" title="${handlerValue}">
                <c:out value="${handlerValue}"></c:out>
            </div>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
