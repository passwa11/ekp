<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdFeishuNotifyQueueErr" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdSubject" title="${lfn:message('third-feishu:thirdFeishuNotifyQueueErr.fdSubject')}" />
        <list:data-column property="fdRepeatHandle" title="${lfn:message('third-feishu:thirdFeishuNotifyQueueErr.fdRepeatHandle')}" />
        <list:data-column col="docAlterTime" title="${lfn:message('third-feishu:thirdFeishuNotifyQueueErr.docAlterTime')}">
            <kmss:showDate value="${thirdFeishuNotifyQueueErr.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdPerson.name" title="${lfn:message('third-feishu:thirdFeishuNotifyQueueErr.fdPerson')}" escape="false">
            <c:out value="${thirdFeishuNotifyQueueErr.fdPerson.fdName}" />
        </list:data-column>
        <list:data-column col="fdPerson.id" escape="false">
            <c:out value="${thirdFeishuNotifyQueueErr.fdPerson.fdId}" />
        </list:data-column>
        <list:data-column property="fdNotifyId" title="${lfn:message('third-feishu:thirdFeishuNotifyQueueErr.fdNotifyId')}" />
    	<list:data-column property="fdMethod" title="${lfn:message('third-feishu:thirdFeishuNotifyQueueErr.fdMethod')}" />
     </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
