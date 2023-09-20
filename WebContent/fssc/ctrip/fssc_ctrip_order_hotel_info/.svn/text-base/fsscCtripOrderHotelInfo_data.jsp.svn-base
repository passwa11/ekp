<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscCtripOrderHotelInfo" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdOrderId" title="${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdOrderId')}" />
        <list:data-column property="fdJourneyNo" title="${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdJourneyNo')}" />
        <list:data-column property="fdTripId" title="${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdTripId')}" />
        <list:data-column property="fdUid" title="${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdUid')}" />
        <list:data-column property="fdEmployeeId" title="${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdEmployeeId')}" />
        <list:data-column property="fdEmployeeName" title="${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdEmployeeName')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
