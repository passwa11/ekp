<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWelinkTodoTaskMapp" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('third-welink:thirdWelinkTodoTaskMapp.docSubject')}" />
        <list:data-column property="fdTodoId" title="${lfn:message('third-welink:thirdWelinkTodoTaskMapp.fdTodoId')}" />
        <list:data-column property="fdPersonId" title="${lfn:message('third-welink:thirdWelinkTodoTaskMapp.fdPersonId')}" />
        <list:data-column property="fdWelinkUserId" title="${lfn:message('third-welink:thirdWelinkTodoTaskMapp.fdWelinkUserId')}" />
        <list:data-column property="fdTaskId" title="${lfn:message('third-welink:thirdWelinkTodoTaskMapp.fdTaskId')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-welink:thirdWelinkTodoTaskMapp.docCreateTime')}">
            <kmss:showDate value="${thirdWelinkTodoTaskMapp.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
