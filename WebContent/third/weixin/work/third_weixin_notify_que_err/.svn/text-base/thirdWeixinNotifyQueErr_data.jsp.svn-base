<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinNotifyQueErr" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdSubject" title="${lfn:message('third-weixin-work:thirdWeixinNotifyQueErr.fdSubject')}" />
        <list:data-column property="fdNotifyId" title="${lfn:message('third-weixin-work:thirdWeixinNotifyQueErr.fdNotifyId')}" />
        <list:data-column property="fdApiType" title="${lfn:message('third-weixin-work:thirdWeixinNotifyQueErr.fdApiType')}" />
        <list:data-column property="fdMethod" title="${lfn:message('third-weixin-work:thirdWeixinNotifyQueErr.fdMethod')}" />
        <list:data-column property="fdRepeatHandle" title="${lfn:message('third-weixin-work:thirdWeixinNotifyQueErr.fdRepeatHandle')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-weixin-work:thirdWeixinNotifyQueErr.docCreateTime')}">
            <kmss:showDate value="${thirdWeixinNotifyQueErr.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdCorpId" title="${lfn:message('third-weixin-work:thirdWeixinNotifyQueErr.fdCorpId')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
