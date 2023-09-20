<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWelinkPersonNoMapp" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdWelinkName" title="${lfn:message('third-welink:thirdWelinkPersonNoMapp.fdWelinkName')}" />
        <list:data-column property="fdWelinkId" title="${lfn:message('third-welink:thirdWelinkPersonNoMapp.fdWelinkId')}" />
        <list:data-column property="fdWelinkMobileNo" title="${lfn:message('third-welink:thirdWelinkPersonNoMapp.fdWelinkMobileNo')}" />
        <list:data-column property="fdWelinkEmail" title="${lfn:message('third-welink:thirdWelinkPersonNoMapp.fdWelinkEmail')}" />
        <list:data-column col="docAlterTime" title="${lfn:message('third-welink:thirdWelinkPersonNoMapp.docAlterTime')}">
            <kmss:showDate value="${thirdWelinkPersonNoMapp.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
