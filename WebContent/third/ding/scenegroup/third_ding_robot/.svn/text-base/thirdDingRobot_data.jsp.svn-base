<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingRobot" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding-scenegroup:thirdDingRobot.fdName')}" />
        <list:data-column property="fdKey" title="${lfn:message('third-ding-scenegroup:thirdDingRobot.fdKey')}" />
        <list:data-column property="fdDesc" title="${lfn:message('third-ding-scenegroup:thirdDingRobot.fdDesc')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('third-ding-scenegroup:thirdDingRobot.fdIsAvailable')}">
            <sunbor:enumsShow value="${thirdDingRobot.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${thirdDingRobot.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding-scenegroup:thirdDingRobot.docCreateTime')}">
            <kmss:showDate value="${thirdDingRobot.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
