<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinNotifyLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdSubject" title="${lfn:message('third-weixin-work:thirdWeixinNotifyLog.fdSubject')}" />
        <list:data-column property="fdNotifyId" title="${lfn:message('third-weixin-work:thirdWeixinNotifyLog.fdNotifyId')}" />
        <list:data-column property="fdApiType" title="${lfn:message('third-weixin-work:thirdWeixinNotifyLog.fdApiType')}" />
        <list:data-column property="fdMethod" title="${lfn:message('third-weixin-work:thirdWeixinNotifyLog.fdMethod')}" />
        <list:data-column col="fdResult.name" title="${lfn:message('third-weixin-work:thirdWeixinNotifyLog.fdResult')}">
            <sunbor:enumsShow value="${thirdWeixinNotifyLog.fdResult}" enumsType="third_weixin_req_result" />
        </list:data-column>
        <list:data-column col="fdResult">
            <c:out value="${thirdWeixinNotifyLog.fdResult}" />
        </list:data-column>
        <list:data-column col="fdReqDate" title="${lfn:message('third-weixin-work:thirdWeixinNotifyLog.fdReqDate')}">
            <kmss:showDate value="${thirdWeixinNotifyLog.fdReqDate}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdExpireTime" title="${lfn:message('third-weixin-work:thirdWeixinNotifyLog.fdExpireTime')}" />
        <list:data-column property="fdCorpId" title="${lfn:message('third-weixin-work:thirdWeixinNotifyLog.fdCorpId')}" />

    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
