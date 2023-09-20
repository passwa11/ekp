<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataSupGrade" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataSupGrade.fdCode')}" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataSupGrade.fdName')}" />
        <list:data-column col="fdStatus.name" title="${lfn:message('eop-basedata:eopBasedataSupGrade.fdStatus')}">
            <sunbor:enumsShow value="${eopBasedataSupGrade.fdStatus}" enumsType="eop_basedata_mate_status" />
        </list:data-column>
        <list:data-column col="fdStatus">
            <c:out value="${eopBasedataSupGrade.fdStatus}" />
        </list:data-column>
        <list:data-column col="docAlteror.name" title="${lfn:message('eop-basedata:eopBasedataSupGrade.docAlteror')}" escape="false">
            <c:out value="${eopBasedataSupGrade.docAlteror.fdName}" />
        </list:data-column>
        <list:data-column col="docAlteror.id" escape="false">
            <c:out value="${eopBasedataSupGrade.docAlteror.fdId}" />
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('eop-basedata:eopBasedataSupGrade.docAlterTime')}">
            <kmss:showDate value="${eopBasedataSupGrade.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
