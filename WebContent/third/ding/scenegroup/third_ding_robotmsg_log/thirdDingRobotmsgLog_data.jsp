<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingRobotmsgLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdTitle" title="${lfn:message('third-ding-scenegroup:thirdDingRobotmsgLog.fdTitle')}" />
        <list:data-column col="fdReqTime" title="${lfn:message('third-ding-scenegroup:thirdDingRobotmsgLog.fdReqTime')}">
            <kmss:showDate value="${thirdDingRobotmsgLog.fdReqTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdRobot.name" title="${lfn:message('third-ding-scenegroup:thirdDingRobotmsgLog.fdRobot')}" escape="false">
            <c:out value="${thirdDingRobotmsgLog.fdRobot.fdName}" />
        </list:data-column>
        <list:data-column col="fdRobot.id" escape="false">
            <c:out value="${thirdDingRobotmsgLog.fdRobot.fdId}" />
        </list:data-column>
        <list:data-column col="fdResult.name" title="${lfn:message('third-ding-scenegroup:thirdDingRobotmsgLog.fdResult')}">
            <sunbor:enumsShow value="${thirdDingRobotmsgLog.fdResult}" enumsType="third_ding_req_result" />
        </list:data-column>
        <list:data-column col="fdResult">
            <c:out value="${thirdDingRobotmsgLog.fdResult}" />
        </list:data-column>
        <list:data-column property="fdExpireTime" title="${lfn:message('third-ding-scenegroup:thirdDingRobotmsgLog.fdExpireTime')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
