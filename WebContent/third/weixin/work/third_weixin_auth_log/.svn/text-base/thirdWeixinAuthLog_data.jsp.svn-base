<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinAuthLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdUserId" title="${lfn:message('third-weixin-work:thirdWeixinAuthLog.fdUserId')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-weixin-work:thirdWeixinAuthLog.docCreateTime')}">
            <kmss:showDate value="${thirdWeixinAuthLog.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdResult.name" title="${lfn:message('third-weixin-work:thirdWeixinAuthLog.fdResult')}">
            <sunbor:enumsShow value="${thirdWeixinAuthLog.fdResult}" enumsType="third_weixin_req_result" />
        </list:data-column>
        <list:data-column col="fdResult">
            <c:out value="${thirdWeixinAuthLog.fdResult}" />
        </list:data-column>
        <list:data-column col="fdLogType.name" title="${lfn:message('third-weixin-work:thirdWeixinAuthLog.fdLogType')}">
            <sunbor:enumsShow value="${thirdWeixinAuthLog.fdLogType}" enumsType="third_weixin_log_type" />
        </list:data-column>
        <list:data-column col="fdLogType">
            <c:out value="${thirdWeixinAuthLog.fdLogType}" />
        </list:data-column>
        <list:data-column property="fdExpireTime" title="${lfn:message('third-weixin-work:thirdWeixinAuthLog.fdExpireTime')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
