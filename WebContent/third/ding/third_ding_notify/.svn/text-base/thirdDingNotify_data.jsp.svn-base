<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingNotify" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdEkpUserId" title="${lfn:message('third-ding:thirdDingNotify.fdEkpUserId')}" />
        <list:data-column property="fdModelId" title="${lfn:message('third-ding:thirdDingNotify.fdModelId')}" />
        <list:data-column property="fdModelName" title="${lfn:message('third-ding:thirdDingNotify.fdModelName')}" />
        <list:data-column property="fdRecordId" title="${lfn:message('third-ding:thirdDingNotify.fdRecordId')}" />
        <list:data-column property="fdDingUserId" title="${lfn:message('third-ding:thirdDingNotify.fdDingUserId')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
