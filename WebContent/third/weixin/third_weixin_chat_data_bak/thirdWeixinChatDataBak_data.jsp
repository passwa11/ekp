<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinChatDataBak" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdSeq" title="${lfn:message('third-weixin:thirdWeixinChatDataBak.fdSeq')}" />
        <list:data-column property="fdMsgId" title="${lfn:message('third-weixin:thirdWeixinChatDataBak.fdMsgId')}" />
        <list:data-column property="fdMsgType" title="${lfn:message('third-weixin:thirdWeixinChatDataBak.fdMsgType')}" />
        <list:data-column property="fdFrom" title="${lfn:message('third-weixin:thirdWeixinChatDataBak.fdFrom')}" />
        <list:data-column property="fdRoomId" title="${lfn:message('third-weixin:thirdWeixinChatDataBak.fdRoomId')}" />
        <list:data-column property="fdMsgTime" title="${lfn:message('third-weixin:thirdWeixinChatDataBak.fdMsgTime')}" />
        <list:data-column property="fdTitle" title="${lfn:message('third-weixin:thirdWeixinChatDataBak.fdTitle')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
