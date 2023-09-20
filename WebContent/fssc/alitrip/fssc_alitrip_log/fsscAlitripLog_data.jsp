<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscAlitripLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdDesc" title="${lfn:message('fssc-alitrip:fsscAlitripLog.fdDesc')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-alitrip:fsscAlitripLog.docCreateTime')}">
            <kmss:showDate value="${fsscAlitripLog.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdModelId" title="${lfn:message('fssc-alitrip:fsscAlitripLog.fdModelId')}" />
        <list:data-column property="fdErrCode" title="${lfn:message('fssc-alitrip:fsscAlitripLog.fdErrCode')}" />
        <list:data-column property="fdType" title="${lfn:message('fssc-alitrip:fsscAlitripLog.fdType')}" />
        <list:data-column property="fdInterType" title="${lfn:message('fssc-alitrip:fsscAlitripLog.fdInterType')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
