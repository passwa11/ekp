<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscMobileInvoiceTitle" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdName')}" />
        <list:data-column property="fdTaxNo" title="${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdTaxNo')}" />
        <list:data-column property="fdAddress" title="${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdAddress')}" />
        <list:data-column property="fdPhone" title="${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdPhone')}" />
        <list:data-column property="fdBankName" title="${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdBankName')}" />
        <list:data-column property="fdBankAccount" title="${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdBankAccount')}" />
        <list:data-column col="docCreator.name" title="${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.docCreator')}" escape="false">
            <c:out value="${fsscMobileInvoiceTitle.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${fsscMobileInvoiceTitle.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.docCreateTime')}">
            <kmss:showDate value="${fsscMobileInvoiceTitle.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
