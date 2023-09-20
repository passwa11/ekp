<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="elecAuthenPrise" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <%--
        <list:data-column property="fdOrgType" title="${lfn:message('elec-core:elecAuthenPrise.fdOrgType')}" />
        --%>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column property="fdPrincipalName" title="${lfn:message('elec-core:elecAuthenPrise.fdPrincipalName')}" />
        <list:data-column property="fdCreditCode" title="${lfn:message('elec-core:elecAuthenPrise.fdCreditCode')}" />
        <list:data-column property="fdLegalPerson" title="${lfn:message('elec-core:elecAuthenPrise.fdLegalPerson')}" />
        <list:data-column property="fdIdentificationNo" title="${lfn:message('elec-core:elecAuthenPrise.fdIdentificationNo')}" />
        <list:data-column property="fdAuthorizer" title="${lfn:message('elec-core:elecAuthenPrise.fdAuthorizer')}" />
        <list:data-column property="fdAuthorizerIdentificationNo" title="${lfn:message('elec-core:elecAuthenPrise.fdAuthorizerIdentificationNo')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
