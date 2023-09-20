<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdCtripFlight" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdCityCode" title="${lfn:message('third-ctrip:thirdCtripFlight.fdCityCode')}" />
        <list:data-column property="fdCityCname" title="${lfn:message('third-ctrip:thirdCtripFlight.fdCityCname')}" />
        <list:data-column property="fdCityEname" title="${lfn:message('third-ctrip:thirdCtripFlight.fdCityEname')}" />
        <list:data-column property="fdCitySname" title="${lfn:message('third-ctrip:thirdCtripFlight.fdCitySname')}" />
        <list:data-column property="fdCityPinyin" title="${lfn:message('third-ctrip:thirdCtripFlight.fdCityPinyin')}" />
        <list:data-column col="fdPoiType.name" title="${lfn:message('third-ctrip:thirdCtripFlight.fdPoiType')}">
            <sunbor:enumsShow value="${thirdCtripFlight.fdPoiType}" enumsType="third_ctrip_poi" />
        </list:data-column>
        <list:data-column col="fdPoiType">
            <c:out value="${thirdCtripFlight.fdPoiType}" />
        </list:data-column>
        <list:data-column property="fdContryCode" title="${lfn:message('third-ctrip:thirdCtripFlight.fdContryCode')}" />
        <list:data-column property="fdContryCname" title="${lfn:message('third-ctrip:thirdCtripFlight.fdContryCname')}" />
        <list:data-column property="fdCountryEname" title="${lfn:message('third-ctrip:thirdCtripFlight.fdCountryEname')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
