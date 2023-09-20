<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscBudgetExecute" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdModelName" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdModelName')}" />
        <list:data-column col="fdModel.fdName" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdModelName')}">
        	${valMap[fsscBudgetExecute.fdId]["fdModelName"]}
        </list:data-column>
        <list:data-column col="fdModel.docSubject" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdModel.docSubject')}">
        	${valMap[fsscBudgetExecute.fdId]["docSubject"]}
        </list:data-column>
        <list:data-column col="fdModel.docNumber" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdModel.docNumber')}">
        	${valMap[fsscBudgetExecute.fdId]["docNumber"]}
        </list:data-column>
        <list:data-column col="fdUrl" escape="false">
        	${valMap[fsscBudgetExecute.fdId]["fdUrl"]}
        </list:data-column>
        <list:data-column col="fdMoney" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdMoney')}">
        	<kmss:showNumber value="${fsscBudgetExecute.fdMoney}" pattern="##0.00"></kmss:showNumber>
        </list:data-column>
        <list:data-column col="docCreateName" title="提单人">
            ${valMap[fsscBudgetExecute.fdId]["docCreateName"]}
        </list:data-column>
        <list:data-column col="realUser" title="报销人">
            ${valMap[fsscBudgetExecute.fdId]["realUser"]}
        </list:data-column>realDept
        <list:data-column col="realDept" title="报销部门">
            ${valMap[fsscBudgetExecute.fdId]["realDept"]}
        </list:data-column>
        <list:data-column col="fdContent" title="事由">
            ${valMap[fsscBudgetExecute.fdId]["fdContent"]}
        </list:data-column>
        <list:data-column property="fdCompanyId" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdCompanyId')}" />
        <list:data-column property="fdCostCenterId" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdCostCenterId')}" />
        <list:data-column property="fdBudgetItemId" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdBudgetItemId')}" />
        <list:data-column property="fdModelId" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdModelId')}" />
        <list:data-column property="fdType" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdType')}" />
        <list:data-column property="fdBudgetId" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdBudgetId')}" />
        <list:data-column property="fdDetailId" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdDetailId')}" />
        <list:data-column property="fdCompanyGroupId" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdCompanyGroupId')}" />
        <list:data-column property="fdProjectId" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdProjectId')}" />
        <list:data-column property="fdInnerOrderId" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdInnerOrderId')}" />
        <list:data-column property="fdWbsId" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdWbsId')}" />
        <list:data-column property="fdPersonId" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdPersonId')}" />
        <list:data-column property="fdCompanyGroupCode" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdCompanyGroupCode')}" />
        <list:data-column property="fdCompanyCode" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdCompanyCode')}" />
        <list:data-column property="fdCostCenterGroupId" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdCostCenterGroupId')}" />
        <list:data-column property="fdCostCenterCode" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdCostCenterCode')}" />
        <list:data-column property="fdCostCenterGroupCode" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdCostCenterGroupCode')}" />
        <list:data-column property="fdBudgetItemCode" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdBudgetItemCode')}" />
        <list:data-column property="fdProjectCode" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdProjectCode')}" />
        <list:data-column property="fdInnerOrderCode" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdInnerOrderCode')}" />
        <list:data-column property="fdWbsCode" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdWbsCode')}" />
        <list:data-column property="fdPersonCode" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdPersonCode')}" />
        <list:data-column property="fdCurrency" title="${lfn:message('fssc-budget:fsscBudgetExecute.fdCurrency')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
