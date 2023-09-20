<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingNotifyMessage" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdNotifyId" title="${lfn:message('third-ding-notify:thirdDingNotifyMessage.fdNotifyId')}" />
        <list:data-column property="fdSubject" title="${lfn:message('third-ding-notify:thirdDingNotifyMessage.fdSubject')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding-notify:thirdDingNotifyMessage.docCreateTime')}">
            <kmss:showDate value="${thirdDingNotifyMessage.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdDingUserId" title="${lfn:message('third-ding-notify:thirdDingNotifyMessage.fdDingUserId')}" />
        <list:data-column property="fdDingTaskId" title="${lfn:message('third-ding-notify:thirdDingNotifyMessage.fdDingTaskId')}" />
        <list:data-column col="fdUser.name" title="${lfn:message('third-ding-notify:thirdDingNotifyMessage.fdUser')}" escape="false">
            <c:out value="${thirdDingNotifyMessage.fdUser.fdName}" />
        </list:data-column>
        <list:data-column col="fdUser.id" escape="false">
            <c:out value="${thirdDingNotifyMessage.fdUser.fdId}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
