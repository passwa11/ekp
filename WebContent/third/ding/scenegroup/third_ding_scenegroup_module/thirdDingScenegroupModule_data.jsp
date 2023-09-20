<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingScenegroupModule" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding-scenegroup:thirdDingScenegroupModule.fdName')}" />
        <list:data-column property="fdKey" title="${lfn:message('third-ding-scenegroup:thirdDingScenegroupModule.fdKey')}" />
        <list:data-column property="fdModuleId" title="${lfn:message('third-ding-scenegroup:thirdDingScenegroupModule.fdModuleId')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding-scenegroup:thirdDingScenegroupModule.docCreateTime')}">
            <kmss:showDate value="${thirdDingScenegroupModule.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
