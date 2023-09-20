<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinGroupChat" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdRoomName" title="${lfn:message('third-weixin:thirdWeixinGroupChat.fdRoomName')}" />
        <list:data-column property="fdRoomCreator" title="${lfn:message('third-weixin:thirdWeixinGroupChat.fdRoomCreator')}" />
        <list:data-column property="fdRoomCreateTime" title="${lfn:message('third-weixin:thirdWeixinGroupChat.fdRoomCreateTime')}" />
        <list:data-column col="docAlterTime" title="${lfn:message('third-weixin:thirdWeixinGroupChat.docAlterTime')}">
            <kmss:showDate value="${thirdWeixinGroupChat.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdIsDissolve.name" title="${lfn:message('third-weixin:thirdWeixinGroupChat.fdIsDissolve')}">
            <sunbor:enumsShow value="${thirdWeixinGroupChat.fdIsDissolve}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsDissolve">
            <c:out value="${thirdWeixinGroupChat.fdIsDissolve}" />
        </list:data-column>
        <list:data-column property="fdOwnerFdid" title="${lfn:message('third-weixin:thirdWeixinGroupChat.fdOwnerFdid')}" />
        <list:data-column property="fdRoomId" title="${lfn:message('third-weixin:thirdWeixinGroupChat.fdRoomId')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
