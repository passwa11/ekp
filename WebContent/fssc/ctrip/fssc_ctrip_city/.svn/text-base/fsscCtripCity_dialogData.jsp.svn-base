<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscCtripCity" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdCityId" title="${lfn:message('fssc-ctrip:fsscCtripCity.fdCityId')}" />
        <list:data-column property="fdName" title="${lfn:message('fssc-ctrip:fsscCtripCity.fdName')}" />
        <list:data-column property="fdNameEn" title="${lfn:message('fssc-ctrip:fsscCtripCity.fdNameEn')}" />
        <list:data-column property="fdCnShortSpelling" title="${lfn:message('fssc-ctrip:fsscCtripCity.fdCnShortSpelling')}" />
        <list:data-column property="fdCountryId" title="${lfn:message('fssc-ctrip:fsscCtripCity.fdCountryId')}" />
        <list:data-column property="fdCountryName" title="${lfn:message('fssc-ctrip:fsscCtripCity.fdCountryName')}" />
        <list:data-column property="fdCountryNameEn" title="${lfn:message('fssc-ctrip:fsscCtripCity.fdCountryName')}" />
        <list:data-column col="fdOwer.name" title="${lfn:message('fssc-ctrip:fsscCtripCity.fdOwer')}">
            <sunbor:enumsShow value="${fsscCtripCity.fdOwer}" enumsType="fssc_ctrip_ower" />
        </list:data-column>
        <list:data-column col="fdOwer">
            <c:out value="${fsscCtripCity.fdOwer}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
