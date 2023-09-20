<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscCtripCountry" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdCountryId" title="${lfn:message('fssc-ctrip:fsscCtripCountry.fdCountryId')}" />
        <list:data-column property="fdName" title="${lfn:message('fssc-ctrip:fsscCtripCountry.fdName')}" />
        <list:data-column property="fdNameEn" title="${lfn:message('fssc-ctrip:fsscCtripCountry.fdNameEn')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
