<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="elecCaYinzhang" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('elec-core-certification:elecCaCertBind.fdName')}" />
        <list:data-column property="fdDn" title="${lfn:message('elec-core-certification:elecCaCertBind.fdDn')}" />
        <list:data-column col="fdCertId.name" title="${lfn:message('elec-core-certification:elecCaCertBind.fdCertId')}" escape="false">
            <c:out value="${elecCaYinzhang.fdCertId.fdCertType}" />
        </list:data-column>
        <list:data-column col="fdCertId.id" escape="false">
            <c:out value="${elecCaYinzhang.fdCertId.fdId}" />
        </list:data-column>
        <list:data-column property="fdCertName" title="${lfn:message('elec-core-certification:elecCaCertBind..fdCertName')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
