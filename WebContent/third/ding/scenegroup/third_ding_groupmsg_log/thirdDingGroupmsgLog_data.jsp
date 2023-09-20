<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingGroupmsgLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdTitle" title="${lfn:message('third-ding-scenegroup:thirdDingGroupmsgLog.fdTitle')}" />
        <list:data-column col="fdGroup.name" title="${lfn:message('third-ding-scenegroup:thirdDingGroupmsgLog.fdGroup')}" escape="false">
            <c:out value="${thirdDingGroupmsgLog.fdGroup.fdName}" />
        </list:data-column>
        <list:data-column col="fdGroup.id" escape="false">
            <c:out value="${thirdDingGroupmsgLog.fdGroup.fdId}" />
        </list:data-column>
        <list:data-column col="fdResult.name" title="${lfn:message('third-ding-scenegroup:thirdDingGroupmsgLog.fdResult')}">
            <sunbor:enumsShow value="${thirdDingGroupmsgLog.fdResult}" enumsType="third_ding_req_result" />
        </list:data-column>
        <list:data-column col="fdResult">
            <c:out value="${thirdDingGroupmsgLog.fdResult}" />
        </list:data-column>
        <list:data-column col="fdReqTime" title="${lfn:message('third-ding-scenegroup:thirdDingGroupmsgLog.fdReqTime')}">
            <kmss:showDate value="${thirdDingGroupmsgLog.fdReqTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdExpireTime" title="${lfn:message('third-ding-scenegroup:thirdDingGroupmsgLog.fdExpireTime')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
