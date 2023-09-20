<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataProject" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataProject.fdName')}"  escape="false"/>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataProject.fdCode')}"  escape="false"/>
        <list:data-column col="fdParent.name" title="${lfn:message('eop-basedata:eopBasedataProject.fdParent')}" escape="false">
            <c:out value="${eopBasedataProject.fdParent.fdName}" />
        </list:data-column>
        <list:data-column col="fdType.name" title="${lfn:message('eop-basedata:eopBasedataProject.fdType')}" escape="false">
            <sunbor:enumsShow value="${eopBasedataProject.fdType}" enumsType="eop_basedata_project_type" />
        </list:data-column>
        <list:data-column col="fdType">
            <c:out value="${eopBasedataProject.fdType}" />
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataProject.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataProject.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataProject.fdIsAvailable}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
