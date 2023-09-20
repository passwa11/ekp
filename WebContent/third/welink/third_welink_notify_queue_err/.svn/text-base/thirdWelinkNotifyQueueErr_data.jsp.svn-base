<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWelinkNotifyQueueErr" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdSubject" title="${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdSubject')}" />
        <list:data-column property="fdNotifyId" title="${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdNotifyId')}" />
        <list:data-column property="fdMethod" title="${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdMethod')}" />
        <list:data-column property="fdRepeatHandle" title="${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdRepeatHandle')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-welink:thirdWelinkNotifyQueueErr.docCreateTime')}">
            <kmss:showDate value="${thirdWelinkNotifyQueueErr.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdSendType.name" title="${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdSendType')}">
            <sunbor:enumsShow value="${thirdWelinkNotifyQueueErr.fdSendType}" enumsType="third_welink_notify_target" />
        </list:data-column>
        <list:data-column col="fdSendType">
            <c:out value="${thirdWelinkNotifyQueueErr.fdSendType}" />
        </list:data-column>
        <list:data-column col="fdMethod.name" title="${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdMethod')}">
            <sunbor:enumsShow value="${thirdWelinkNotifyQueueErr.fdMethod}" enumsType="third_welink_notify_method" />
        </list:data-column>
        <list:data-column col="fdFlag.name" title="${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdFlag')}">
            <sunbor:enumsShow value="${thirdWelinkNotifyQueueErr.fdFlag}" enumsType="third_welink_handle_flag" />
        </list:data-column>
        <list:data-column col="fdToUser.name" title="${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdToUser')}" escape="false">
            <c:out value="${thirdWelinkNotifyQueueErr.fdToUser.fdName}" />
        </list:data-column>
        <list:data-column col="fdToUser.id" escape="false">
            <c:out value="${thirdWelinkNotifyQueueErr.fdToUser.fdId}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
