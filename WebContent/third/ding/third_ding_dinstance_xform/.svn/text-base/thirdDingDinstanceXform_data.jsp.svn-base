<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingDinstanceXform" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding:thirdDingDinstanceXform.fdName')}" />
        <list:data-column col="fdTemplate.name" title="${lfn:message('third-ding:thirdDingDinstanceXform.fdTemplate')}" escape="false">
            <c:out value="${thirdDingDinstanceXform.fdTemplate.fdName}" />
        </list:data-column>
        <list:data-column col="fdTemplate.id" escape="false">
            <c:out value="${thirdDingDinstanceXform.fdTemplate.fdId}" />
        </list:data-column>
        <list:data-column col="fdEkpUser.name" title="${lfn:message('third-ding:thirdDingDinstanceXform.fdEkpUser')}" escape="false">
            <c:out value="${thirdDingDinstanceXform.fdEkpUser.fdName}" />
        </list:data-column>
        <list:data-column col="fdEkpUser.id" escape="false">
            <c:out value="${thirdDingDinstanceXform.fdEkpUser.fdId}" />
        </list:data-column>
        <list:data-column property="fdInstanceId" title="${lfn:message('third-ding:thirdDingDinstanceXform.fdInstanceId')}" />
        <list:data-column property="fdEkpInstanceId" title="${lfn:message('third-ding:thirdDingDinstanceXform.fdEkpInstanceId')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding:thirdDingDinstanceXform.docCreateTime')}">
            <kmss:showDate value="${thirdDingDinstanceXform.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
