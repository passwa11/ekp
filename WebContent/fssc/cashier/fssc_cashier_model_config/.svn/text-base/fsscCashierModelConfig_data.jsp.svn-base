<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscCashierModelConfig" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('fssc-cashier:fsscCashierModelConfig.fdName')}" />
        <list:data-column property="fdModelName" title="${lfn:message('fssc-cashier:fsscCashierModelConfig.fdModelName')}" />
        <list:data-column property="fdCategoryName" title="${lfn:message('fssc-cashier:fsscCashierModelConfig.fdCategoryName')}" />
        <list:data-column property="fdCategoryPropertyName" title="${lfn:message('fssc-cashier:fsscCashierModelConfig.fdCategoryPropertyName')}" />
    	<list:data-column property="fdPath" title="${lfn:message('fssc-cashier:fsscCashierModelConfig.fdPath')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
