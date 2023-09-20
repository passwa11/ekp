<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscBudgetData" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdCompany.name" title="${lfn:message('fssc-budget:fsscBudgetData.fdCompany')}" escape="false">
            <c:out value="${fsscBudgetData.fdCompany.fdName}" />
        </list:data-column>
        <list:data-column col="fdCompany.id" escape="false">
            <c:out value="${fsscBudgetData.fdCompany.fdId}" />
        </list:data-column>
        <list:data-column col="fdCompanyGroup.name" title="${lfn:message('fssc-budget:fsscBudgetData.fdCompanyGroup')}" escape="false">
            <c:out value="${fsscBudgetData.fdCompanyGroup.fdName}" />
        </list:data-column>
        <list:data-column col="fdCompanyGroup.id" escape="false">
            <c:out value="${fsscBudgetData.fdCompanyGroup.fdId}" />
        </list:data-column>
        <list:data-column col="fdCostCenter.name" title="${lfn:message('fssc-budget:fsscBudgetData.fdCostCenter')}" escape="false">
            <c:out value="${fsscBudgetData.fdCostCenter.fdName}" />
        </list:data-column>
        <list:data-column col="fdCostCenter.fdParent.name" title="成本中心所属组" escape="false">
            <c:out value="${fsscBudgetData.fdCostCenter.fdParent.fdName}" />
        </list:data-column>
        <list:data-column col="fdCostCenter.id" escape="false">
            <c:out value="${fsscBudgetData.fdCostCenter.fdId}" />
        </list:data-column>
        <list:data-column col="fdDept.name" title="${lfn:message('fssc-budget:fsscBudgetData.fdDept')}" escape="false">
            <c:out value="${fsscBudgetData.fdDept.fdName}" />
        </list:data-column>
        <list:data-column col="fdDept.id" escape="false">
            <c:out value="${fsscBudgetData.fdDept.fdId}" />
        </list:data-column>
        <list:data-column col="fdBudgetItem.name" title="${lfn:message('fssc-budget:fsscBudgetData.fdBudgetItem')}" escape="false">
            <c:out value="${fsscBudgetData.fdBudgetItem.fdName}" />
        </list:data-column>
        <list:data-column col="fdBudgetItemParent.name" title="${lfn:message('fssc-budget:fsscBudgetData.fdBudgetItemParentName')}" escape="false">
            <c:out value="${fsscBudgetData.fdBudgetItemParent.fdName}" />
        </list:data-column>
        <list:data-column col="fdBudgetItem.id" escape="false">
            <c:out value="${fsscBudgetData.fdBudgetItem.fdId}" />
        </list:data-column>
        <list:data-column col="fdProject.name" title="${lfn:message('fssc-budget:fsscBudgetData.fdProject')}" escape="false">
            <c:out value="${fsscBudgetData.fdProject.fdName}" />
        </list:data-column>
        <list:data-column col="fdProject.id" escape="false">
            <c:out value="${fsscBudgetData.fdProject.fdId}" />
        </list:data-column>
        <list:data-column col="fdInnerOrder.name" title="${lfn:message('fssc-budget:fsscBudgetData.fdInnerOrder')}" escape="false">
            <c:out value="${fsscBudgetData.fdInnerOrder.fdName}" />
        </list:data-column>
        <list:data-column col="fdInnerOrder.id" escape="false">
            <c:out value="${fsscBudgetData.fdInnerOrder.fdId}" />
        </list:data-column>
        <list:data-column col="fdWbs.name" title="${lfn:message('fssc-budget:fsscBudgetData.fdWbs')}" escape="false">
            <c:out value="${fsscBudgetData.fdWbs.fdName}" />
        </list:data-column>
        <list:data-column col="fdWbs.id" escape="false">
            <c:out value="${fsscBudgetData.fdWbs.fdId}" />
        </list:data-column>
        <list:data-column col="fdPerson.name" title="${lfn:message('fssc-budget:fsscBudgetData.fdPerson')}" >
            <c:out value="${fsscBudgetData.fdPerson.fdName}" />
        </list:data-column>
        <list:data-column col="fdPerson.id" escape="false">
            <c:out value="${fsscBudgetData.fdPerson.fdId}" />
        </list:data-column>
        <list:data-column col="fdYear" title="${lfn:message('fssc-budget:fsscBudgetData.fdYear')}">
        	<kmss:showPeriod value="${fsscBudgetData.fdYear}"></kmss:showPeriod>
        </list:data-column>
        <list:data-column property="fdPeriodType" title="${lfn:message('fssc-budget:fsscBudgetData.fdPeriodType')}" />
        <list:data-column col="fdPeriod" title="${lfn:message('fssc-budget:fsscBudgetData.fdPeriod')}">
        	<c:if test="${not empty fsscBudgetData.fdPeriod}">
	        	<c:if test="${fsscBudgetData.fdPeriod<9}">
	        		<c:out value="${fn:substring(fsscBudgetData.fdPeriod,1,2)+1}"></c:out>
	        	</c:if>
	        	<c:if test="${fsscBudgetData.fdPeriod >8}">
	        		<c:out value="${fsscBudgetData.fdPeriod+1}"></c:out>
	        	</c:if>
        	</c:if>
        	<sunbor:enumsShow enumsType="fssc_budget_period_type_name" value="${fsscBudgetData.fdPeriodType}"></sunbor:enumsShow>
        </list:data-column>
        <list:data-column col="fdMoney" title="${lfn:message('fssc-budget:fsscBudgetData.fdMoney')}">
        	<kmss:showNumber value="${fsscBudgetData.fdMoney}" pattern="##0.00"></kmss:showNumber>
        </list:data-column>
        <list:data-column col="fdBudgetStatus" title="${lfn:message('fssc-budget:fsscBudgetData.fdBudgetStatus')}">
        	<sunbor:enumsShow enumsType="fssc_budget_status" value="${fsscBudgetData.fdBudgetStatus}"></sunbor:enumsShow>
        </list:data-column>
        <list:data-column property="fdRule" title="${lfn:message('fssc-budget:fsscBudgetData.fdRule')}" />
        <list:data-column col="fdElasticPercent" title="${lfn:message('fssc-budget:fsscBudgetData.fdElasticPercent')}">
        	<c:if test="${empty  fsscBudgetData.fdElasticPercent}">
        		0
        	</c:if>
        	<c:if test="${not empty  fsscBudgetData.fdElasticPercent}">
        		<c:out value="${fsscBudgetData.fdElasticPercent}" />
        	</c:if>
        	%
        </list:data-column>
        <list:data-column col="fdBudgetScheme.name" title="${lfn:message('fssc-budget:fsscBudgetData.fdBudgetScheme')}" escape="false">
            <c:out value="${fsscBudgetData.fdBudgetScheme.fdName}" />
        </list:data-column>
        <list:data-column col="fdBudgetScheme.id" escape="false">
            <c:out value="${fsscBudgetData.fdBudgetScheme.fdId}" />
        </list:data-column>
        <list:data-column col="fdCostCenterGroup.name" title="${lfn:message('fssc-budget:fsscBudgetData.fdCostCenterGroup')}" escape="false">
            <c:out value="${fsscBudgetData.fdCostCenterGroup.fdName}" />
        </list:data-column>
        <list:data-column col="fdCostCenterGroup.id" escape="false">
            <c:out value="${fsscBudgetData.fdCostCenterGroup.fdId}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('fssc-budget:fsscBudgetData.docCreator')}" escape="false">
            <c:out value="${fsscBudgetData.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${fsscBudgetData.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-budget:fsscBudgetData.docCreateTime')}">
        	<kmss:showDate value="${fsscBudgetData.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdCompanyGroupCode" title="${lfn:message('fssc-budget:fsscBudgetData.fdCompanyGroupCode')}" />
        <list:data-column property="fdCompanyCode" title="${lfn:message('fssc-budget:fsscBudgetData.fdCompanyCode')}" />
        <list:data-column property="fdCostCenterGroupCode" title="${lfn:message('fssc-budget:fsscBudgetData.fdCostCenterGroupCode')}" />
        <list:data-column property="fdCostCenterCode" title="${lfn:message('fssc-budget:fsscBudgetData.fdCostCenterCode')}" />
        <list:data-column property="fdBudgetItemCode" title="${lfn:message('fssc-budget:fsscBudgetData.fdBudgetItemCode')}" />
        <list:data-column property="fdProjectCode" title="${lfn:message('fssc-budget:fsscBudgetData.fdProjectCode')}" />
        <list:data-column property="fdInnerOrderCode" title="${lfn:message('fssc-budget:fsscBudgetData.fdInnerOrderCode')}" />
        <list:data-column property="fdWbsCode" title="${lfn:message('fssc-budget:fsscBudgetData.fdWbsCode')}" />
        <list:data-column property="fdPersonCode" title="${lfn:message('fssc-budget:fsscBudgetData.fdPersonCode')}" />
        <list:data-column property="fdBudgetSchemeCode" title="${lfn:message('fssc-budget:fsscBudgetData.fdBudgetSchemeCode')}" />
		<!--调整金额-->
        <list:data-column col="fdAdjustMoney" title="${lfn:message('fssc-budget:fsscBudgetData.fdAdjustMoney')}">
            ${executeDataList[fsscBudgetData.fdId]["adjustAmount"]}
        </list:data-column>
        <!--已使用-->
        <list:data-column col="fdAlreadyUsedMoney" title="${lfn:message('fssc-budget:fsscBudgetData.fdAlreadyUsedMoney')}">
            ${executeDataList[fsscBudgetData.fdId]["alreadyUsedAmount"]}
        </list:data-column>
        <!--占用-->
        <list:data-column col="fdOccupyMoney" title="${lfn:message('fssc-budget:fsscBudgetData.fdOccupyMoney')}">
            ${executeDataList[fsscBudgetData.fdId]["occupyAmount"]}
        </list:data-column>
        <!--总预算-->
        <list:data-column col="fdTotalMoney" title="${lfn:message('fssc-budget:fsscBudgetData.fdTotalMoney')}">
            ${executeDataList[fsscBudgetData.fdId]["fdTotalMoney"]}
        </list:data-column>
        <!--可使用-->
        <list:data-column col="fdCanUseMoney" title="${lfn:message('fssc-budget:fsscBudgetData.canUseAmount')}">
            ${executeDataList[fsscBudgetData.fdId]["canUseAmount"]}
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
