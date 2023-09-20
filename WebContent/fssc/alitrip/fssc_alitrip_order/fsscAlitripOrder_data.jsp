<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscAlitripOrder" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.fdName')}" />
        <list:data-column col="fdType" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.fdType')}">
            <sunbor:enumsShow value="${fsscAlitripOrder.fdType}" enumsType="fssc_alitrip_train_cate" />
        </list:data-column>
        <list:data-column property="id" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.id')}" />
        <list:data-column col="gmtCreate" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.gmtCreate')}">
            <kmss:showDate value="${fsscAlitripOrder.gmtCreate}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="gmtModified" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.gmtModified')}">
            <kmss:showDate value="${fsscAlitripOrder.gmtModified}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="corpId" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.corpId')}" />
        <list:data-column property="corpName" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.corpName')}" />
        <list:data-column property="userId" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.userId')}" />
        <list:data-column property="userName" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.userName')}" />
        <list:data-column property="departId" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.departId')}" />
        <list:data-column property="departName" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.departName')}" />
        <list:data-column property="applyId" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.applyId')}" />
        <list:data-column property="contactPhone" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.contactPhone')}" />
        <list:data-column property="contactName" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.contactName')}" />
        <list:data-column property="city" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.city')}" />
        <list:data-column property="hotelName" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.hotelName')}" />
        <list:data-column col="checkIn" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.checkIn')}">
            <kmss:showDate value="${fsscAlitripOrder.checkIn}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="checkOut" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.checkOut')}">
            <kmss:showDate value="${fsscAlitripOrder.checkOut}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="roomType" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.roomType')}" />
        <list:data-column property="roomNum" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.roomNum')}" />
        <list:data-column property="night" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.night')}" />
        <list:data-column property="costCenterNumber" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.costCenterNumber')}" />
        <list:data-column property="costCenterName" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.costCenterName')}" />
        <list:data-column property="invoiceTitle" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.invoiceTitle')}" />
        <list:data-column property="invoiceId" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.invoiceId')}" />
        <list:data-column property="orderStatusDesc" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.orderStatusDesc')}" />
        <list:data-column property="orderTypeDesc" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.orderTypeDesc')}" />
        <list:data-column property="guest" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.guest')}" />
        <list:data-column property="thirdpartItineraryId" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.thirdpartItineraryId')}" />
        <list:data-column property="orderStatus" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.orderStatus')}" />
        <list:data-column property="orderType" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.orderType')}" />
        <list:data-column property="depCity" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.depCity')}" />
        <list:data-column property="arrCity" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.arrCity')}" />
        <list:data-column col="depDate" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.depDate')}">
            <kmss:showDate value="${fsscAlitripOrder.depDate}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="retDate" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.retDate')}">
            <kmss:showDate value="${fsscAlitripOrder.retDate}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="tripType" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.tripType')}">
            <sunbor:enumsShow value="${fsscAlitripOrder.tripType}" enumsType="fssc_alitrip_trip_type" />
        </list:data-column>
        <list:data-column property="passengerCount" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.passengerCount')}" />
        <list:data-column property="cabinClass" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.cabinClass')}" />
        <list:data-column col="status" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.status')}">
            <sunbor:enumsShow value="${fsscAlitripOrder.status}" enumsType="fssc_alitrip_plan_status" />
        </list:data-column>
        <list:data-column property="arrAirport" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.arrAirport')}" />
        <list:data-column property="depAirport" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.depAirport')}" />
        <list:data-column property="passengerName" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.passengerName')}" />
        <list:data-column property="flightNo" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.flightNo')}" />
        <list:data-column property="discount" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.discount')}" />
        <list:data-column property="depStation" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.depStation')}" />
        <list:data-column property="arrStation" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.arrStation')}" />
        <list:data-column col="depTime" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.depTime')}">
            <kmss:showDate value="${fsscAlitripOrder.depTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="arrTime" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.arrTime')}">
            <kmss:showDate value="${fsscAlitripOrder.arrTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="trainNumber" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.trainNumber')}" />
        <list:data-column property="trainType" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.trainType')}" />
        <list:data-column property="seatType" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.seatType')}" />
        <list:data-column property="runTime" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.runTime')}" />
        <list:data-column property="ticketNo12306" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.ticketNo12306')}" />
        <list:data-column property="riderName" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.riderName')}" />
        <list:data-column property="ticketCount" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.ticketCount')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
