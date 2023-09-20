<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscFeeLedger" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdModelName" title="${lfn:message('fssc-fee:fsscFeeLedger.fdModelName')}">
        	${valMap[fsscFeeLedger.fdId]["fdModelName"]}
        </list:data-column>
        <list:data-column col="fdType" title="${lfn:message('fssc-fee:fsscFeeLedger.fdType')}">
        	${valMap[fsscFeeLedger.fdId]["fdType"]}
        </list:data-column>
        <list:data-column col="docSubject" title="${lfn:message('fssc-fee:fsscFeeLedger.fdModel.docSubject')}">
        	${valMap[fsscFeeLedger.fdId]["docSubject"]}
        </list:data-column>
        <list:data-column col="docNumber" title="${lfn:message('fssc-fee:fsscFeeLedger.fdModel.docNumber')}">
        	${valMap[fsscFeeLedger.fdId]["docNumber"]}
        </list:data-column>
        <list:data-column col="fdUrl" escape="false">
        	${valMap[fsscFeeLedger.fdId]["fdUrl"]}
        </list:data-column>
        <list:data-column col="fdMoney" title="${lfn:message('fssc-fee:fsscFeeLedger.fdMoney')}">
        	<kmss:showNumber value="${fsscFeeLedger.fdBudgetMoney}" pattern="##0.00"></kmss:showNumber>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
