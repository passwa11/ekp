<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscVoucherRuleConfig" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdName')}" />
        <list:data-column col="fdVoucherModelConfig.name" title="${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdVoucherModelConfig')}" escape="false">
            <c:out value="${fsscVoucherRuleConfig.fdVoucherModelConfig.fdName}" />
        </list:data-column>
        <list:data-column col="fdVoucherModelConfig.id" escape="false">
            <c:out value="${fsscVoucherRuleConfig.fdVoucherModelConfig.fdId}" />
        </list:data-column>
        <list:data-column property="fdCategoryName" title="${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdCategoryName')}" />
        <list:data-column property="fdRuleText" title="${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdRuleFormula')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdIsAvailable')}">
            <sunbor:enumsShow value="${fsscVoucherRuleConfig.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${fsscVoucherRuleConfig.fdIsAvailable}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
