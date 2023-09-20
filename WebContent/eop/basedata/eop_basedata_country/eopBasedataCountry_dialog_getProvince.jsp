<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataCountry" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataCountry.fdName')}"  escape="false"/>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataCountry.fdCode')}"  escape="false"/>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataCountry.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataCountry.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataCountry.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataCountry.docCreator')}" escape="false">
            <c:out value="${eopBasedataCountry.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataCountry.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataCountry.docCreateTime')}">
            <kmss:showDate value="${eopBasedataCountry.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
