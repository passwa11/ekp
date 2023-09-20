<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingNotifyLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('third-ding-notify:thirdDingNotifyLog.docSubject')}" />
        <list:data-column property="fdNotifyId" title="${lfn:message('third-ding-notify:thirdDingNotifyLog.fdNotifyId')}" />
        <list:data-column col="fdSendTime" title="${lfn:message('third-ding-notify:thirdDingNotifyLog.fdSendTime')}">
            <kmss:showDate value="${thirdDingNotifyLog.fdSendTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdResult.name" title="${lfn:message('third-ding-notify:thirdDingNotifyLog.fdResult')}">
            <sunbor:enumsShow value="${thirdDingNotifyLog.fdResult}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdResult">
            <c:out value="${thirdDingNotifyLog.fdResult}" />
        </list:data-column>
        <list:data-column property="fdUrl" title="${lfn:message('third-ding-notify:thirdDingNotifyLog.fdUrl')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
