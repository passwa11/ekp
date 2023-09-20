<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingCategoryLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdSynType.name" title="${lfn:message('third-ding:thirdDingCategoryLog.fdSynType')}">
            <sunbor:enumsShow value="${thirdDingCategoryLog.fdSynType}" enumsType="third_ding_category_type" />
        </list:data-column>
        <list:data-column col="fdSynType">
            <c:out value="${thirdDingCategoryLog.fdSynType}" />
        </list:data-column>
        <list:data-column col="fdSynTime" title="${lfn:message('third-ding:thirdDingCategoryLog.fdSynTime')}">
            <kmss:showDate value="${thirdDingCategoryLog.fdSynTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdSynStatus.name" title="${lfn:message('third-ding:thirdDingCategoryLog.fdSynStatus')}">
            <sunbor:enumsShow value="${thirdDingCategoryLog.fdSynStatus}" enumsType="third_ding_category_status" />
        </list:data-column>
        <list:data-column col="fdSynStatus">
            <c:out value="${thirdDingCategoryLog.fdSynStatus}" />
        </list:data-column>
        <list:data-column property="fdContent" title="${lfn:message('third-ding:thirdDingCategoryLog.fdContent')}" />
        <list:data-column property="fdCorpId" title="${lfn:message('third-ding:thirdDingCategoryLog.fdCorpId')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
