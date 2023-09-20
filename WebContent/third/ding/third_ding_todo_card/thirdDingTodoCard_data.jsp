<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingTodoCard" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding:thirdDingTodoCard.fdName')}" />
        <list:data-column property="fdTemplateId" title="${lfn:message('third-ding:thirdDingTodoCard.fdTemplateId')}" />
        <list:data-column property="fdCardId" title="${lfn:message('third-ding:thirdDingTodoCard.fdCardId')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding:thirdDingTodoCard.docCreateTime')}">
            <kmss:showDate value="${thirdDingTodoCard.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('third-ding:thirdDingTodoCard.docAlterTime')}">
            <kmss:showDate value="${thirdDingTodoCard.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdLang" title="${lfn:message('third-ding:thirdDingTodoCard.fdLang')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
