<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWelinkDeptNoMapping" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdWelinkName" title="${lfn:message('third-welink:thirdWelinkDeptNoMapping.fdWelinkName')}" />
        <list:data-column property="fdWelinkId" title="${lfn:message('third-welink:thirdWelinkDeptNoMapping.fdWelinkId')}" />
        <list:data-column property="fdWelinkPath" title="${lfn:message('third-welink:thirdWelinkDeptNoMapping.fdWelinkPath')}" />
        <list:data-column col="docAlterTime" title="${lfn:message('third-welink:thirdWelinkDeptNoMapping.docAlterTime')}">
            <kmss:showDate value="${thirdWelinkDeptNoMapping.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
