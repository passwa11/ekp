<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinAppchat" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdChatId" title="${lfn:message('third-weixin:thirdWeixinAppchat.fdChatId')}" />
        <list:data-column property="fdChatName" title="${lfn:message('third-weixin:thirdWeixinAppchat.fdChatName')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-weixin:thirdWeixinAppchat.docCreateTime')}">
            <kmss:showDate value="${thirdWeixinAppchat.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdOwnerId" title="${lfn:message('third-weixin:thirdWeixinAppchat.fdOwnerId')}" />
        <list:data-column property="fdOwnerFdid" title="${lfn:message('third-weixin:thirdWeixinAppchat.fdOwnerFdid')}" />
        <list:data-column property="fdChatMsg" title="${lfn:message('third-weixin:thirdWeixinAppchat.fdChatMsg')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
