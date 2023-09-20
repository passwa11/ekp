<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataWbs" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataWbs.fdName')}"  escape="false"/>
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataWbs.fdCode')}"  escape="false"/>
        <list:data-column col="fdParent.name" title="${lfn:message('eop-basedata:eopBasedataWbs.fdParent')}" escape="false">
            <c:out value="${eopBasedataWbs.fdParent.fdName}" />
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataWbs.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataWbs.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataWbs.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataWbs.docCreator')}" escape="false">
            <c:out value="${eopBasedataWbs.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataWbs.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataWbs.docCreateTime')}">
            <kmss:showDate value="${eopBasedataWbs.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
