<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataPassport" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataPassport.fdName')}" />
        <list:data-column col="fdPerson.name" title="${lfn:message('eop-basedata:eopBasedataPassport.fdPerson')}" escape="false">
            <c:out value="${eopBasedataPassport.fdPerson.fdName}" />
        </list:data-column>
        <list:data-column col="fdPerson.id" escape="false">
            <c:out value="${eopBasedataPassport.fdPerson.fdId}" />
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataPassport.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataPassport.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataPassport.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docAlteror.name" title="${lfn:message('eop-basedata:eopBasedataPassport.docAlteror')}" escape="false">
            <c:out value="${eopBasedataPassport.docAlteror.fdName}" />
        </list:data-column>
        <list:data-column col="docAlteror.id" escape="false">
            <c:out value="${eopBasedataPassport.docAlteror.fdId}" />
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('eop-basedata:eopBasedataPassport.docAlterTime')}">
            <kmss:showDate value="${eopBasedataPassport.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
