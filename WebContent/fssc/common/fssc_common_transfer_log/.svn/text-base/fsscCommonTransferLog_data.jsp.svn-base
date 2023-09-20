<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscCommonTransferLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdTarget" title="${lfn:message('fssc-common:fsscCommonTransferLog.fdTarget')}" />
        <list:data-column col="fdStartTime" title="${lfn:message('fssc-common:fsscCommonTransferLog.fdStartTime')}">
            <kmss:showDate value="${fsscCommonTransferLog.fdStartTime}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdEndTime" title="${lfn:message('fssc-common:fsscCommonTransferLog.fdEndTime')}">
            <kmss:showDate value="${fsscCommonTransferLog.fdEndTime}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdCount" title="${lfn:message('fssc-common:fsscCommonTransferLog.fdCount')}" />
        <list:data-column property="fdResult" title="${lfn:message('fssc-common:fsscCommonTransferLog.fdResult')}" />
        <list:data-column property="fdOperator" title="${lfn:message('fssc-common:fsscCommonTransferLog.fdOperator')}" />
        <list:data-column property="fdMessage" title="${lfn:message('fssc-common:fsscCommonTransferLog.fdMessage')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
