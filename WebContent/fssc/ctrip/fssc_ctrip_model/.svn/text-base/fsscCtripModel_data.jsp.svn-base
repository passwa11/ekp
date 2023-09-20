<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscCtripModel" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('fssc-ctrip:fsscCtripModel.fdName')}" />
        <list:data-column property="fdModelName" title="${lfn:message('fssc-ctrip:fsscCtripModel.fdModelName')}" />
        <list:data-column property="fdCategoryName" title="${lfn:message('fssc-ctrip:fsscCtripModel.fdCategoryName')}" />
        <list:data-column property="fdKey" title="${lfn:message('fssc-ctrip:fsscCtripModel.fdKey')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
