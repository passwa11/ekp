<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingCallbackLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdEventType" title="${lfn:message('third-ding:thirdDingCallbackLog.fdEventType')}" />
        <list:data-column property="fdEventTypeTip" title="事件说明" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding:thirdDingCallbackLog.docCreateTime')}">
            <kmss:showDate value="${thirdDingCallbackLog.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdIsSuccess.name" title="${lfn:message('third-ding:thirdDingCallbackLog.fdIsSuccess')}">
            <sunbor:enumsShow value="${thirdDingCallbackLog.fdIsSuccess}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsSuccess">
            <c:out value="${thirdDingCallbackLog.fdIsSuccess}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
