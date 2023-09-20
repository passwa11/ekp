<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscExpenseBalance" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('fssc-expense:fsscExpenseBalance.docSubject')}" />
        <list:data-column col="fdCompany.name" title="${lfn:message('fssc-expense:fsscExpenseBalance.fdCompany')}" escape="false">
            <c:out value="${fsscExpenseBalance.fdCompany.fdName}" />
        </list:data-column>
        <list:data-column col="fdCompany.id" escape="false">
            <c:out value="${fsscExpenseBalance.fdCompany.fdId}" />
        </list:data-column>
        <list:data-column col="fdCostCenter.name" title="${lfn:message('fssc-expense:fsscExpenseBalance.fdCostCenter')}" escape="false">
            <c:out value="${fsscExpenseBalance.fdCostCenter.fdName}" />
        </list:data-column>
        <list:data-column col="fdCostCenter.id" escape="false">
            <c:out value="${fsscExpenseBalance.fdCostCenter.fdId}" />
        </list:data-column>
        <list:data-column col="fdVoucherType.name" title="${lfn:message('fssc-expense:fsscExpenseBalance.fdVoucherType')}" escape="false">
            <c:out value="${fsscExpenseBalance.fdVoucherType.fdName}" />
        </list:data-column>
        <list:data-column col="fdVoucherType.id" escape="false">
            <c:out value="${fsscExpenseBalance.fdVoucherType.fdId}" />
        </list:data-column>
        <list:data-column property="fdMonth" title="${lfn:message('fssc-expense:fsscExpenseBalance.fdMonth')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-expense:fsscExpenseBalance.docCreateTime')}">
            <kmss:showDate value="${fsscExpenseBalance.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('fssc-expense:fsscExpenseBalance.docCreator')}" escape="false">
            <c:out value="${fsscExpenseBalance.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${fsscExpenseBalance.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docStatus" title="${lfn:message('fssc-expense:fsscExpenseBalance.docStatus')}">
            <sunbor:enumsShow value="${fsscExpenseBalance.docStatus}" enumsType="fssc_expense_doc_status" />
        </list:data-column>
        <list:data-column col="docStatus">
            <c:out value="${fsscExpenseBalance.docStatus}" />
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_summary" title="${lfn:message('fssc-expense:lbpm.currentSummary') }" escape="false">
            <kmss:showWfPropertyValues var="nodevalue" idValue="${fsscExpenseBalance.fdId}" propertyName="nodeName" />
            <div title="${nodevalue}">
                <c:out value="${nodevalue}"></c:out>
            </div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_handler" title="${lfn:message('fssc-expense:lbpm.currentHandler') }" escape="false">
            <kmss:showWfPropertyValues var="handlerValue" idValue="${fsscExpenseBalance.fdId}" propertyName="handlerName" />
            <div style="font-weight:bold;" title="${handlerValue}">
                <c:out value="${handlerValue}"></c:out>
            </div>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
