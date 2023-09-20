<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinChatGroup" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdNewestMsgId" title="${lfn:message('third-weixin:thirdWeixinChatGroup.fdNewestMsgId')}" />
        <list:data-column property="fdRelateUserId" title="${lfn:message('third-weixin:thirdWeixinChatGroup.fdRelateUserId')}" />
        <list:data-column property="fdRoomId" title="${lfn:message('third-weixin:thirdWeixinChatGroup.fdRoomId')}" />
        <list:data-column property="fdMd5" title="${lfn:message('third-weixin:thirdWeixinChatGroup.fdMd5')}" />
        <list:data-column col="fdIsOut.name" title="${lfn:message('third-weixin:thirdWeixinChatGroup.fdIsOut')}">
            <sunbor:enumsShow value="${thirdWeixinChatGroup.fdIsOut}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsOut">
            <c:out value="${thirdWeixinChatGroup.fdIsOut}" />
        </list:data-column>
        <list:data-column property="newestMsg" title="${lfn:message('third-weixin:thirdWeixinChatGroup.fdNewestMsgId')}" />
        <list:data-column property="newestMsgTime" title="${lfn:message('third-weixin:thirdWeixinChatGroup.fdNewestMsgTime')}" />
        <list:data-column property="msgSeq" />
        <list:data-column property="fdChatGroupName" title="${lfn:message('third-weixin:thirdWeixinChatGroup.fdChatGroupName')}" />
        <list:data-column property="groupNameImage" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
