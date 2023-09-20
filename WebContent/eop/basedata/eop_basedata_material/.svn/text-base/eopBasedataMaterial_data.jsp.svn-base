<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataMaterial" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdName')}" />
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdCode')}" />
        <list:data-column property="fdSpecs" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdSpecs')}" />
        <list:data-column col="fdType.name" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdType')}" escape="false">
            <c:out value="${eopBasedataMaterial.fdType.fdName}" />
        </list:data-column>
        <list:data-column col="fdType.id" escape="false">
            <c:out value="${eopBasedataMaterial.fdType.fdId}" />
        </list:data-column>
        <list:data-column col="fdUnit.name" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdUnit')}" escape="false">
            <c:out value="${eopBasedataMaterial.fdUnit.fdName}" />
        </list:data-column>
        <list:data-column col="fdUnit.id" escape="false">
            <c:out value="${eopBasedataMaterial.fdUnit.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataMaterial.docCreateTime')}">
            <kmss:showDate value="${eopBasedataMaterial.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdStatus.name" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdStatus')}">
            <sunbor:enumsShow value="${eopBasedataMaterial.fdStatus}" enumsType="eop_basedata_mate_status" />
        </list:data-column>
        <list:data-column col="fdStatus">
            <c:out value="${eopBasedataMaterial.fdStatus}" />
        </list:data-column>
        <list:data-column col="fdPrice">
            <c:out value="${eopBasedataMaterial.fdPrice}" />
        </list:data-column>
        <list:data-column col="fdErpCode">
            <c:out value="${eopBasedataMaterial.fdErpCode}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
