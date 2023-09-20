<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataMateCate" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataMateCate.fdCode')}" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataMateCate.fdName')}" />
        <list:data-column col="fdParent.name" title="${lfn:message('eop-basedata:eopBasedataMateCate.fdParent')}" escape="false">
            <c:out value="${eopBasedataMateCate.fdParent.fdName}" />
        </list:data-column>
        <list:data-column col="fdParent.id" escape="false">
            <c:out value="${eopBasedataMateCate.fdParent.fdId}" />
        </list:data-column>
        <list:data-column col="fdStatus.name" title="${lfn:message('eop-basedata:eopBasedataMateCate.fdStatus')}">
            <sunbor:enumsShow value="${eopBasedataMateCate.fdStatus}" enumsType="eop_basedata_mate_status" />
        </list:data-column>
        <list:data-column col="fdStatus">
            <c:out value="${eopBasedataMateCate.fdStatus}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataMateCate.docCreator')}" escape="false">
            <c:out value="${eopBasedataMateCate.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataMateCate.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataMateCate.docCreateTime')}">
            <kmss:showDate value="${eopBasedataMateCate.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
