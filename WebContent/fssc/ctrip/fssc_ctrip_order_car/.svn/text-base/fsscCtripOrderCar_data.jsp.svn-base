<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscCtripOrderCar" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdOrderId" title="${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdOrderId')}" />
        <list:data-column property="fdTripId" title="${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdTripId')}" />
        <list:data-column property="fdOrderAmount" title="${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdOrderAmount')}" />
        <list:data-column property="fdDealAmount" title="${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdDealAmount')}" />
        <list:data-column property="fdServiceFee" title="${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdServiceFee')}" />
        <list:data-column property="fdOrderDetailStatus" title="${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdOrderDetailStatus')}" />
        <list:data-column property="fdPaymentStatus" title="${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdPaymentStatus')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
