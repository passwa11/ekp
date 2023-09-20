<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdPaymentOrder" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdOrderDesc" title="${lfn:message('third-payment:thirdPaymentOrder.fdOrderDesc')}" />
        <list:data-column property="fdModelId" title="${lfn:message('third-payment:thirdPaymentOrder.fdModelId')}" />
        <list:data-column property="fdTotalMoney" title="${lfn:message('third-payment:thirdPaymentOrder.fdTotalMoney')}" />
        <list:data-column property="fdOrderNo" title="${lfn:message('third-payment:thirdPaymentOrder.fdOrderNo')}" />
        <list:data-column col="fdPayType.name" title="${lfn:message('third-payment:thirdPaymentOrder.fdPayType')}">
            <sunbor:enumsShow value="${thirdPaymentOrder.fdPayType}" enumsType="third_payment_type" />
        </list:data-column>
        <list:data-column col="fdPayType">
            <c:out value="${thirdPaymentOrder.fdPayType}" />
        </list:data-column>
        <list:data-column col="fdPaymentStatus.name" title="${lfn:message('third-payment:thirdPaymentOrder.fdPaymentStatus')}">
            <sunbor:enumsShow value="${thirdPaymentOrder.fdPaymentStatus}" enumsType="third_payment_status" />
        </list:data-column>
        <list:data-column col="fdPaymentStatus">
            <c:out value="${thirdPaymentOrder.fdPaymentStatus}" />
        </list:data-column>
        <list:data-column col="fdPayTime" title="${lfn:message('third-payment:thirdPaymentOrder.fdPayTime')}">
            <kmss:showDate value="${thirdPaymentOrder.fdPayTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
