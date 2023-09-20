<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingCardMapping" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding:thirdDingCardMapping.fdName')}" />
        <list:data-column property="fdCardId" title="${lfn:message('third-ding:thirdDingCardMapping.fdCardId')}" />
        <list:data-column property="fdModelId" title="${lfn:message('third-ding:thirdDingCardMapping.fdModelId')}" />
        <list:data-column col="docAlterTime" title="${lfn:message('third-ding:thirdDingCardMapping.docAlterTime')}">
            <kmss:showDate value="${thirdDingCardMapping.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding:thirdDingCardMapping.docCreateTime')}">
            <kmss:showDate value="${thirdDingCardMapping.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
