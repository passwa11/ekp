<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataCashFlow" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataCashFlow.fdName')}" escape="false" />
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataCashFlow.fdCode')}" escape="false" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataCashFlow.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataCashFlow.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataCashFlow.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataCashFlow.docCreator')}" escape="false">
            <c:out value="${eopBasedataCashFlow.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataCashFlow.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataCashFlow.docCreateTime')}">
            <kmss:showDate value="${eopBasedataCashFlow.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
