<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingCardConfig" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding:thirdDingCardConfig.fdName')}" />
        <list:data-column property="fdModelNameText" title="${lfn:message('third-ding:thirdDingCardConfig.fdModelNameText')}" />
        <list:data-column property="fdCardId" title="${lfn:message('third-ding:thirdDingCardConfig.fdCardId')}" />
        <list:data-column col="fdStatus.name" title="${lfn:message('third-ding:thirdDingCardConfig.fdStatus')}">
            <sunbor:enumsShow value="${thirdDingCardConfig.fdStatus}" enumsType="third_ding_card_status" />
        </list:data-column>
        <list:data-column col="fdStatus">
            <c:out value="${thirdDingCardConfig.fdStatus}" />
        </list:data-column>
        <list:data-column col="fdType.name" title="${lfn:message('third-ding:thirdDingCardConfig.fdType')}">
            <sunbor:enumsShow value="${thirdDingCardConfig.fdType}" enumsType="third_ding_card_type" />
        </list:data-column>
        <list:data-column col="fdType">
            <c:out value="${thirdDingCardConfig.fdType}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding:thirdDingCardConfig.docCreateTime')}">
            <kmss:showDate value="${thirdDingCardConfig.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
