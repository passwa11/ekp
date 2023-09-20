<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscFeeMain" list="${queryPage.list}" varIndex="status">
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="docSubject" title="${lfn:message('fssc-fee:fsscFeeMain.docSubject')}" >
        	${fsscFeeMain[0]}
        </list:data-column>
        <list:data-column col="docNumber" title="${lfn:message('fssc-fee:fsscFeeMain.docNumber')}" >
        	${fsscFeeMain[1]}
        </list:data-column>
        <list:data-column col="fdCompanyName" title="${lfn:message('fssc-fee:control.budgetRule.company')}">
            ${fsscFeeMain[2]}
        </list:data-column>
        <list:data-column col="fdCostCenterGroupName" title="${lfn:message('fssc-fee:fsscFeeMapp.fdCostGroup')}">
            ${fsscFeeMain[3]}
        </list:data-column>
        <list:data-column col="fdCostCenterName" title="${lfn:message('fssc-fee:control.budgetRule.costCenter')}">
            ${fsscFeeMain[4]}
        </list:data-column>
        <list:data-column col="fdExpenseItemName" title="${lfn:message('fssc-fee:control.budgetRule.expenseItem')}">
            ${fsscFeeMain[5]}
        </list:data-column>
        <list:data-column col="fdProjectName" title="${lfn:message('fssc-fee:control.budgetRule.project')}">
            ${fsscFeeMain[6]}
        </list:data-column>
        <list:data-column col="docMain.fdId">
            ${fsscFeeMain[7]}
        </list:data-column>
        <list:data-column col="fdLedgerId">
            ${fsscFeeMain[8]}
        </list:data-column>
        <c:set var="fdTotalMoney" value="${fsscFeeMain[8]}fdTotalMoney"/>
        <list:data-column col="fdTotalMoney" title="${lfn:message('fssc-fee:py.total')}">
            ${dataMap[fdTotalMoney]}
        </list:data-column>
        <c:set var="fdUsingMoney" value="${fsscFeeMain[8]}fdUsingMoney"/>
        <list:data-column col="fdUsingMoney" title="${lfn:message('fssc-fee:py.using')}">
            ${dataMap[fdUsingMoney]}
        </list:data-column>
        <c:set var="fdUsedMoney" value="${fsscFeeMain[8]}fdUsedMoney"/>
        <list:data-column col="fdUsedMoney" title="${lfn:message('fssc-fee:py.used')}">
            ${dataMap[fdUsedMoney]}
        </list:data-column>
        <c:set var="fdUsableMoney" value="${fsscFeeMain[8]}fdUsableMoney"/>
        <list:data-column col="fdUsableMoney" title="${lfn:message('fssc-fee:py.usable')}">
            ${dataMap[fdUsableMoney]}
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
