<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdCtripHotel" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdCityEname" title="${lfn:message('third-ctrip:thirdCtripHotel.fdCityEname')}" />
        <list:data-column property="fdCityCname" title="${lfn:message('third-ctrip:thirdCtripHotel.fdCityCname')}" />
        <list:data-column property="fdCitySname" title="${lfn:message('third-ctrip:thirdCtripHotel.fdCitySname')}" />
        <list:data-column property="fdProvinceEname" title="${lfn:message('third-ctrip:thirdCtripHotel.fdProvinceEname')}" />
        <list:data-column property="fdProvinceCname" title="${lfn:message('third-ctrip:thirdCtripHotel.fdProvinceCname')}" />
        <list:data-column property="fdCountryEname" title="${lfn:message('third-ctrip:thirdCtripHotel.fdCountryEname')}" />
        <list:data-column property="fdCountryCname" title="${lfn:message('third-ctrip:thirdCtripHotel.fdCountryCname')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
