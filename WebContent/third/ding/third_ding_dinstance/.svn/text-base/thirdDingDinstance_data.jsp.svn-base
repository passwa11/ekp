<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingDinstance" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding:thirdDingDinstance.fdName')}" />
        <list:data-column col="fdTemplate.name" title="${lfn:message('third-ding:thirdDingDinstance.fdTemplate')}" escape="false">
            <c:out value="${thirdDingDinstance.fdTemplate.fdName}" />
        </list:data-column>
        <list:data-column col="fdTemplate.id" escape="false">
            <c:out value="${thirdDingDinstance.fdTemplate.fdId}" />
        </list:data-column>
        <list:data-column property="fdInstanceId" title="${lfn:message('third-ding:thirdDingDinstance.fdInstanceId')}" />
        <list:data-column property="fdEkpInstanceId" title="${lfn:message('third-ding:thirdDingDinstance.fdEkpInstanceId')}" />
        <list:data-column col="fdCreator.name" title="${lfn:message('third-ding:thirdDingDinstance.fdCreator')}" escape="false">
            <c:out value="${thirdDingDinstance.fdCreator.fdName}" />
        </list:data-column>
        <list:data-column col="fdCreator.id" escape="false">
            <c:out value="${thirdDingDinstance.fdCreator.fdId}" />
        </list:data-column>
        <list:data-column property="fdUrl" title="${lfn:message('third-ding:thirdDingDinstance.fdUrl')}" />
        <list:data-column property="fdDingUserId" title="${lfn:message('third-ding:thirdDingDinstance.fdDingUserId')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding:thirdDingDinstance.docCreateTime')}">
            <kmss:showDate value="${thirdDingDinstance.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
