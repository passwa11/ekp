<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingNotifyWorkrecord" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdNotifyId" title="${lfn:message('third-ding-notify:thirdDingNotifyWorkrecord.fdNotifyId')}" />
        <list:data-column property="fdSubject" title="${lfn:message('third-ding-notify:thirdDingNotifyWorkrecord.fdSubject')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding-notify:thirdDingNotifyWorkrecord.docCreateTime')}">
            <kmss:showDate value="${thirdDingNotifyWorkrecord.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdDingUserId" title="${lfn:message('third-ding-notify:thirdDingNotifyWorkrecord.fdDingUserId')}" />
        <list:data-column property="fdRecordId" title="${lfn:message('third-ding-notify:thirdDingNotifyWorkrecord.fdRecordId')}" />
        <list:data-column col="fdUser.name" title="${lfn:message('third-ding-notify:thirdDingNotifyWorkrecord.fdUser')}" escape="false">
            <c:out value="${thirdDingNotifyWorkrecord.fdUser.fdName}" />
        </list:data-column>
        <list:data-column col="fdUser.id" escape="false">
            <c:out value="${thirdDingNotifyWorkrecord.fdUser.fdId}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
