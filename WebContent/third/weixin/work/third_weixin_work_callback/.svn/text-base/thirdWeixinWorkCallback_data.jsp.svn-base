<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinWorkCallback" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdEventType" title="${lfn:message('third-weixin-work:thirdWeixinWorkCallback.fdEventType')}" />
        <list:data-column property="fdEventTypeTip" title="${lfn:message('third-weixin-work:thirdWeixinWorkCallback.fdEventTypeTip')}" />
         
        <list:data-column col="docCreateTime" title="${lfn:message('third-weixin-work:thirdWeixinWorkCallback.docCreateTime')}">
            <kmss:showDate value="${thirdWeixinWorkCallback.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdIsSuccess.name" title="${lfn:message('third-weixin-work:thirdWeixinWorkCallback.fdIsSuccess')}">
            <sunbor:enumsShow value="${thirdWeixinWorkCallback.fdIsSuccess}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsSuccess">
            <c:out value="${thirdWeixinWorkCallback.fdIsSuccess}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
