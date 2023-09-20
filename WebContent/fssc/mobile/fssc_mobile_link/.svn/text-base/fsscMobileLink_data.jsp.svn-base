<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscMobileLink" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column property="fdType" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('fssc-mobile:fsscMobileLink.fdName')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('fssc-mobile:fsscMobileLink.fdIsAvailable')}">
            <sunbor:enumsShow value="${fsscMobileLink.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${fsscMobileLink.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-mobile:fsscMobileLink.docCreateTime')}">
            <kmss:showDate value="${fsscMobileLink.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('fssc-mobile:fsscMobileLink.docCreator')}" escape="false">
            <c:out value="${fsscMobileLink.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${fsscMobileLink.docCreator.fdId}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
