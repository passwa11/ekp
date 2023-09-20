<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdCtripConfig" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ctrip:thirdCtripConfig.fdName')}" />
        <list:data-column property="fdAppKey" title="${lfn:message('third-ctrip:thirdCtripConfig.fdAppKey')}" />
        <list:data-column property="fdAppSecurity" title="${lfn:message('third-ctrip:thirdCtripConfig.fdAppSecurity')}" />
        <list:data-column property="fdCorpId" title="${lfn:message('third-ctrip:thirdCtripConfig.fdCorpId')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('third-ctrip:thirdCtripConfig.fdIsAvailable')}">
            <sunbor:enumsShow value="${thirdCtripConfig.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${thirdCtripConfig.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('third-ctrip:thirdCtripConfig.docCreator')}" escape="false">
            <c:out value="${thirdCtripConfig.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${thirdCtripConfig.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('third-ctrip:thirdCtripConfig.docCreateTime')}">
            <kmss:showDate value="${thirdCtripConfig.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
