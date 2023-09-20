<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinCgUserMapp" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdUserName" title="${lfn:message('third-weixin-work:thirdWeixinCgUserMapp.fdUserName')}" />
        <list:data-column property="fdEkpId" title="${lfn:message('third-weixin-work:thirdWeixinCgUserMapp.fdEkpId')}" />
        <list:data-column property="fdUserId" title="${lfn:message('third-weixin-work:thirdWeixinCgUserMapp.fdUserId')}" />
        <list:data-column property="fdOpenUserId" title="${lfn:message('third-weixin-work:thirdWeixinCgUserMapp.fdOpenUserId')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('third-weixin-work:thirdWeixinCgUserMapp.fdIsAvailable')}">
            <sunbor:enumsShow value="${thirdWeixinCgUserMapp.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${thirdWeixinCgUserMapp.fdIsAvailable}" />
        </list:data-column>
        <list:data-column property="fdCorpId" title="${lfn:message('third-weixin-work:thirdWeixinCgUserMapp.fdCorpId')}" />
        <list:data-column col="docAlterTime" title="${lfn:message('third-weixin-work:thirdWeixinCgUserMapp.docAlterTime')}">
            <kmss:showDate value="${thirdWeixinCgUserMapp.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
