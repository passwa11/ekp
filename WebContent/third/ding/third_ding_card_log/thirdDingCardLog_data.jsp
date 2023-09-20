<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingCardLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding:thirdDingCardLog.fdName')}" />
        <list:data-column property="fdCardId" title="${lfn:message('third-ding:thirdDingCardLog.fdCardId')}" />
        <list:data-column col="fdRequestTime" title="${lfn:message('third-ding:thirdDingCardLog.fdRequestTime')}">
            <kmss:showDate value="${thirdDingCardLog.fdRequestTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdRequestUrl" title="${lfn:message('third-ding:thirdDingCardLog.fdRequestUrl')}" />
        <list:data-column col="fdStatus.name" title="${lfn:message('third-ding:thirdDingCardLog.fdStatus')}">
            <sunbor:enumsShow value="${thirdDingCardLog.fdStatus}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdStatus">
            <c:out value="${thirdDingCardLog.fdStatus}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
