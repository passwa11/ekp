<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataSupType" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataSupType.fdCode')}" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataSupType.fdName')}" />
        <list:data-column col="fdParent.name" title="${lfn:message('eop-basedata:eopBasedataSupType.fdParent')}" escape="false">
            <c:out value="${eopBasedataSupType.fdParent.fdName}" />
        </list:data-column>
        <list:data-column col="fdParent.id" escape="false">
            <c:out value="${eopBasedataSupType.fdParent.fdId}" />
        </list:data-column>
        <list:data-column col="fdStatus.name" title="${lfn:message('eop-basedata:eopBasedataSupType.fdStatus')}">
            <sunbor:enumsShow value="${eopBasedataSupType.fdStatus}" enumsType="eop_basedata_mate_status" />
        </list:data-column>
        <list:data-column col="fdStatus">
            <c:out value="${eopBasedataSupType.fdStatus}" />
        </list:data-column>
        <list:data-column col="docAlteror.name" title="${lfn:message('eop-basedata:eopBasedataSupType.docAlteror')}" escape="false">
            <c:out value="${eopBasedataSupType.docAlteror.fdName}" />
        </list:data-column>
        <list:data-column col="docAlteror.id" escape="false">
            <c:out value="${eopBasedataSupType.docAlteror.fdId}" />
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('eop-basedata:eopBasedataSupType.docAlterTime')}">
            <kmss:showDate value="${eopBasedataSupType.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
