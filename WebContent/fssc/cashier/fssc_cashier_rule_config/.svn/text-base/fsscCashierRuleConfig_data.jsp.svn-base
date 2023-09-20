<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscCashierRuleConfig" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdName')}" />
        <list:data-column col="fdCashierModelConfig.name" title="${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdCashierModelConfig')}" escape="false">
            <c:out value="${fsscCashierRuleConfig.fdCashierModelConfig.fdName}" />
        </list:data-column>
        <list:data-column col="fdCashierModelConfig.id" escape="false">
            <c:out value="${fsscCashierRuleConfig.fdCashierModelConfig.fdId}" />
        </list:data-column>
        <list:data-column property="fdCategoryName" title="${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdCategoryName')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('fssc-cashier:fsscCashierRuleConfig.fdIsAvailable')}">
            <sunbor:enumsShow value="${fsscCashierRuleConfig.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${fsscCashierRuleConfig.fdIsAvailable}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
