<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingCalendarLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding:thirdDingCalendarLog.fdName')}" />
        <list:data-column col="fdSynWay.name" title="${lfn:message('third-ding:thirdDingCalendarLog.fdSynWay')}">
            <sunbor:enumsShow value="${thirdDingCalendarLog.fdSynWay}" enumsType="third_ding_syn_way" />
        </list:data-column>
        <list:data-column col="fdSynWay">
            <c:out value="${thirdDingCalendarLog.fdSynWay}" />
        </list:data-column>
        <list:data-column col="fdOptType.name" title="${lfn:message('third-ding:thirdDingCalendarLog.fdOptType')}">
            <sunbor:enumsShow value="${thirdDingCalendarLog.fdOptType}" enumsType="third_ding_calendar_opt" />
        </list:data-column>
        <list:data-column col="fdOptType">
            <c:out value="${thirdDingCalendarLog.fdOptType}" />
        </list:data-column>
        <list:data-column col="fdStatus.name" title="${lfn:message('third-ding:thirdDingCalendarLog.fdStatus')}">
            <sunbor:enumsShow value="${thirdDingCalendarLog.fdStatus}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdStatus">
            <c:out value="${thirdDingCalendarLog.fdStatus}" />
        </list:data-column>
        <list:data-column col="fdReqStartTime" title="${lfn:message('third-ding:thirdDingCalendarLog.fdReqStartTime')}">
            <kmss:showDate value="${thirdDingCalendarLog.fdReqStartTime}" type="dateTime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
