<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscAlitripMessage" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('fssc-alitrip:fsscAlitripMessage.fdName')}" />
        <list:data-column property="fdAppKey" title="${lfn:message('fssc-alitrip:fsscAlitripMessage.fdAppKey')}" />
        <list:data-column property="fdAppSecret" title="${lfn:message('fssc-alitrip:fsscAlitripMessage.fdAppSecret')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
