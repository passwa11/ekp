<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingScenegroupMapp" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding-scenegroup:thirdDingScenegroupMapp.fdName')}" />
        <list:data-column property="fdSceneGroupId" title="${lfn:message('third-ding-scenegroup:thirdDingScenegroupMapp.fdSceneGroupId')}" />
        <list:data-column property="fdChatId" title="${lfn:message('third-ding-scenegroup:thirdDingScenegroupMapp.fdChatId')}" />
        <list:data-column col="fdModule.name" title="${lfn:message('third-ding-scenegroup:thirdDingScenegroupMapp.fdModule')}" escape="false">
            <c:out value="${thirdDingScenegroupMapp.fdModule.fdName}" />
        </list:data-column>
        <list:data-column col="fdModule.id" escape="false">
            <c:out value="${thirdDingScenegroupMapp.fdModule.fdId}" />
        </list:data-column>
        <list:data-column col="fdStatus.name" title="${lfn:message('third-ding-scenegroup:thirdDingScenegroupMapp.fdStatus')}">
            <sunbor:enumsShow value="${thirdDingScenegroupMapp.fdStatus}" enumsType="third_ding_group_status" />
        </list:data-column>
        <list:data-column col="fdStatus">
            <c:out value="${thirdDingScenegroupMapp.fdStatus}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding-scenegroup:thirdDingScenegroupMapp.docCreateTime')}">
            <kmss:showDate value="${thirdDingScenegroupMapp.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
