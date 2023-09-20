<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingNotifyQueueError" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdSubject" title="${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdSubject')}" />
        <list:data-column property="fdErrorMsg" title="${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdErrorMsg')}" />
        <list:data-column col="fdSendTime" title="${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdSendTime')}">
            <kmss:showDate value="${thirdDingNotifyQueueError.fdSendTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdRepeatHandle" title="${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdRepeatHandle')}" />
        <list:data-column property="fdTodoId" title="${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdTodoId')}" />
        <list:data-column col="fdUser.name" title="${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdUser')}" escape="false">
            <c:out value="${thirdDingNotifyQueueError.fdUser.fdName}" />
        </list:data-column>
        <list:data-column col="fdUser.id" escape="false">
            <c:out value="${thirdDingNotifyQueueError.fdUser.fdId}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
