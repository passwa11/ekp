<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingOrmTemp" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding:thirdDingOrmTemp.fdName')}" />
        <list:data-column property="fdTemplateId" title="${lfn:message('third-ding:thirdDingOrmTemp.fdTemplateId')}" />
        <list:data-column property="fdProcessCode" title="${lfn:message('third-ding:thirdDingOrmTemp.fdProcessCode')}" />
       <%--  <list:data-column col="fdStartFlow.name" title="${lfn:message('third-ding:thirdDingOrmTemp.fdStartFlow')}">
            <sunbor:enumsShow value="${thirdDingOrmTemp.fdStartFlow}" enumsType="third_ding_start_flow" />
        </list:data-column>
        <list:data-column col="fdStartFlow">
            <c:out value="${thirdDingOrmTemp.fdStartFlow}" />
        </list:data-column> --%>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('third-ding:thirdDingOrmTemp.fdIsAvailable')}">
            <sunbor:enumsShow value="${thirdDingOrmTemp.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${thirdDingOrmTemp.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('third-ding:thirdDingOrmTemp.docCreator')}" escape="false">
            <c:out value="${thirdDingOrmTemp.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${thirdDingOrmTemp.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding:thirdDingOrmTemp.docCreateTime')}">
            <kmss:showDate value="${thirdDingOrmTemp.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
