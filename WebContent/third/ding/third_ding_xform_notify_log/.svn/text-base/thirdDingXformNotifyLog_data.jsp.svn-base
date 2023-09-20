<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingXformNotifyLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('third-ding:thirdDingXformNotifyLog.docSubject')}" />
        <list:data-column col="fdSendTime" title="${lfn:message('third-ding:thirdDingXformNotifyLog.fdSendTime')}">
            <kmss:showDate value="${thirdDingXformNotifyLog.fdSendTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdRtnTime" title="${lfn:message('third-ding:thirdDingXformNotifyLog.fdRtnTime')}">
            <kmss:showDate value="${thirdDingXformNotifyLog.fdRtnTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
