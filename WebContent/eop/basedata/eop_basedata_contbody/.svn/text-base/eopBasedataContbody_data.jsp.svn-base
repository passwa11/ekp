<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataContbody" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdOrder" title="${lfn:message('eop-basedata:eopBasedataContbody.fdOrder')}" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataContbody.fdName')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataContbody.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataContbody.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataContbody.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataContbody.docCreator')}" escape="false">
            <c:out value="${eopBasedataContbody.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataContbody.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataContbody.docCreateTime')}">
            <kmss:showDate value="${eopBasedataContbody.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdKey" title="${lfn:message('eop-basedata:eopBasedataContbody.fdKey')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
