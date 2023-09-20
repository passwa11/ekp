<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinPayLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdBody" title="${lfn:message('third-weixin:thirdWeixinPayLog.fdBody')}" />
        <list:data-column col="fdReqDate" title="${lfn:message('third-weixin:thirdWeixinPayLog.fdReqDate')}">
            <kmss:showDate value="${thirdWeixinPayLog.fdReqDate}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdResult.name" title="${lfn:message('third-weixin:thirdWeixinPayLog.fdResult')}">
            <sunbor:enumsShow value="${thirdWeixinPayLog.fdResult}" enumsType="third_weixin_req_result" />
        </list:data-column>
        <list:data-column col="fdResult">
            <c:out value="${thirdWeixinPayLog.fdResult}" />
        </list:data-column>
        <list:data-column property="fdExpireTime" title="${lfn:message('third-weixin:thirdWeixinPayLog.fdExpireTime')}" />
        <list:data-column property="fdOutTradeNo" title="${lfn:message('third-weixin:thirdWeixinPayLog.fdOutTradeNo')}" />
        <list:data-column property="fdPrepayId" title="${lfn:message('third-weixin:thirdWeixinPayLog.fdPrepayId')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
