<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingFinstance" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdTemplateId" title="${lfn:message('third-ding:thirdDingFinstance.fdTemplateId')}" />
        <list:data-column property="fdModelId" title="${lfn:message('third-ding:thirdDingFinstance.fdModelId')}" />
        <list:data-column property="fdEkpStatus" title="${lfn:message('third-ding:thirdDingFinstance.fdEkpStatus')}" />
        <list:data-column col="fdStartFlow.name" title="${lfn:message('third-ding:thirdDingFinstance.fdStartFlow')}">
            <sunbor:enumsShow value="${thirdDingFinstance.fdStartFlow}" enumsType="third_ding_start_flow" />
        </list:data-column>
        <list:data-column col="fdStartFlow">
            <c:out value="${thirdDingFinstance.fdStartFlow}" />
        </list:data-column>
        <list:data-column property="fdProcessCode" title="${lfn:message('third-ding:thirdDingFinstance.fdProcessCode')}" />
        <list:data-column property="fdInstanceId" title="${lfn:message('third-ding:thirdDingFinstance.fdInstanceId')}" />
        <list:data-column property="fdDingStatus" title="${lfn:message('third-ding:thirdDingFinstance.fdDingStatus')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
