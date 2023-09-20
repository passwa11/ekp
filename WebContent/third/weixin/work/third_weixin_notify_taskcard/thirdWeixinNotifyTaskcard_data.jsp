<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinNotifyTaskcard" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdSubject" title="${lfn:message('third-weixin-work:thirdWeixinNotifyTaskcard.fdSubject')}" />
        <list:data-column property="fdNotifyId" title="${lfn:message('third-weixin-work:thirdWeixinNotifyTaskcard.fdNotifyId')}" />
        <list:data-column property="fdTaskcardId" title="${lfn:message('third-weixin-work:thirdWeixinNotifyTaskcard.fdTaskcardId')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-weixin-work:thirdWeixinNotifyTaskcard.docCreateTime')}">
            <kmss:showDate value="${thirdWeixinNotifyTaskcard.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdCorpId" title="${lfn:message('third-weixin-work:thirdWeixinNotifyTaskcard.fdCorpId')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
