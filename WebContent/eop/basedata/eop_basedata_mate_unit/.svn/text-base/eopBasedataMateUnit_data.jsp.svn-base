<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataMateUnit" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataMateUnit.fdCode')}" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataMateUnit.fdName')}" />
        <list:data-column col="fdStatus.name" title="${lfn:message('eop-basedata:eopBasedataMateUnit.fdStatus')}">
            <sunbor:enumsShow value="${eopBasedataMateUnit.fdStatus}" enumsType="eop_basedata_mate_status" />
        </list:data-column>
        <list:data-column col="fdStatus">
            <c:out value="${eopBasedataMateUnit.fdStatus}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataMateUnit.docCreator')}" escape="false">
            <c:out value="${eopBasedataMateUnit.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataMateUnit.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataMateUnit.docCreateTime')}">
            <kmss:showDate value="${eopBasedataMateUnit.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
