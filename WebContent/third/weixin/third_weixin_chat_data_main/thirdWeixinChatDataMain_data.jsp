<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinChatDataMain" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdSeq" title="${lfn:message('third-weixin:thirdWeixinChatDataMain.fdSeq')}" />
        <list:data-column property="fdMsgId" title="${lfn:message('third-weixin:thirdWeixinChatDataMain.fdMsgId')}" />
        <list:data-column property="fdMsgType" title="${lfn:message('third-weixin:thirdWeixinChatDataMain.fdMsgType')}" />
        <list:data-column property="fdFrom" title="${lfn:message('third-weixin:thirdWeixinChatDataMain.fdFrom')}" />
        <list:data-column property="fdRoomId" title="${lfn:message('third-weixin:thirdWeixinChatDataMain.fdRoomId')}" />
        <list:data-column property="fdMsgTime" title="${lfn:message('third-weixin:thirdWeixinChatDataMain.fdMsgTime')}" />
        <list:data-column property="fdTitle" title="${lfn:message('third-weixin:thirdWeixinChatDataMain.fdTitle')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-weixin:thirdWeixinChatDataMain.docCreateTime')}">
            <kmss:showDate value="${thirdWeixinChatDataMain.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
