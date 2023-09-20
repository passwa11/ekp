<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWelinkNotifyLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('third-welink:thirdWelinkNotifyLog.docSubject')}" />
        <list:data-column col="fdUser.name" title="${lfn:message('third-welink:thirdWelinkNotifyLog.fdUser')}" escape="false">
            <c:out value="${thirdWelinkNotifyLog.fdUser.fdName}" />
        </list:data-column>
        <list:data-column col="fdUser.id" escape="false">
            <c:out value="${thirdWelinkNotifyLog.fdUser.fdId}" />
        </list:data-column>
        <list:data-column property="fdNotifyId" title="${lfn:message('third-welink:thirdWelinkNotifyLog.fdNotifyId')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-welink:thirdWelinkNotifyLog.docCreateTime')}">
            <kmss:showDate value="${thirdWelinkNotifyLog.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdExpireTime" title="${lfn:message('third-welink:thirdWelinkNotifyLog.fdExpireTime')}" />
        <list:data-column col="fdResult.name" title="${lfn:message('third-welink:thirdWelinkNotifyLog.fdResult')}">
            <sunbor:enumsShow value="${thirdWelinkNotifyLog.fdResult}" enumsType="third_welink_result" />
        </list:data-column>
        <list:data-column col="fdMethod.name" title="${lfn:message('third-welink:thirdWelinkNotifyLog.fdMethod')}">
            <sunbor:enumsShow value="${thirdWelinkNotifyLog.fdMethod}" enumsType="third_welink_notify_method" />
        </list:data-column>
        <list:data-column col="fdResult">
            <c:out value="${thirdWelinkNotifyLog.fdResult}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
