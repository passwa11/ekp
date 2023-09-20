<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingTodoTemplate" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column property="fdIsdefault" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
       <list:data-column property="fdType" title="${lfn:message('third-ding:thirdDingTodoTemplate.fdType')}" />
        <list:data-column property="fdName" title="${lfn:message('third-ding:thirdDingTodoTemplate.fdName')}" />
        <list:data-column property="fdModelNameText" title="${lfn:message('third-ding:thirdDingTodoTemplate.fdModelName')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding:thirdDingTodoTemplate.docCreateTime')}">
            <kmss:showDate value="${thirdDingTodoTemplate.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('third-ding:thirdDingTodoTemplate.docAlterTime')}">
            <kmss:showDate value="${thirdDingTodoTemplate.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        
        <list:data-column col="docCreator.name" title="${lfn:message('third-ding:thirdDingTodoTemplate.docCreator')}" escape="false">
            <c:out value="${thirdDingTodoTemplate.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${thirdDingTodoTemplate.docCreator.fdId}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
