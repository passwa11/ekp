<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataCity" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdProvince.name" title="${lfn:message('eop-basedata:eopBasedataCity.fdProvince')}" escape="false">
            <c:out value="${eopBasedataCity.fdProvince.fdName}" />
        </list:data-column>
        <list:data-column col="fdProvince.id" escape="false">
            <c:out value="${eopBasedataCity.fdProvince.fdId}" />
        </list:data-column>
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataCity.fdName')}"  escape="false"/>
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataCity.fdCode')}"  escape="false"/>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataCity.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataCity.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataCity.fdIsAvailable}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
