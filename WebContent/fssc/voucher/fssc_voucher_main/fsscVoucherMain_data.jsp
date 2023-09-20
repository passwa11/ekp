<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscVoucherMain" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdCompany.name" title="${lfn:message('fssc-voucher:fsscVoucherMain.fdCompany')}" escape="false">
            <c:out value="${fsscVoucherMain.fdCompany.fdName}" />
        </list:data-column>
        <list:data-column col="fdCompany.id" escape="false">
            <c:out value="${fsscVoucherMain.fdCompany.fdId}" />
        </list:data-column>
        <list:data-column property="docFinanceNumber" title="${lfn:message('fssc-voucher:fsscVoucherMain.docFinanceNumber')}" />
        <list:data-column property="docNumber" title="${lfn:message('fssc-voucher:fsscVoucherMain.docNumber')}" />
        <list:data-column property="fdModelNumber" title="${lfn:message('fssc-voucher:fsscVoucherMain.fdModelNumber')}" />
        <list:data-column col="fdBaseVoucherType.name" title="${lfn:message('fssc-voucher:fsscVoucherMain.fdBaseVoucherType')}">
            <c:out value="${fsscVoucherMain.fdBaseVoucherType.fdName}" />
        </list:data-column>

        <list:data-column property="fdAccountingYear" title="${lfn:message('fssc-voucher:fsscVoucherMain.fdAccountingYear')}" />
        <list:data-column property="fdPeriod" title="${lfn:message('fssc-voucher:fsscVoucherMain.fdPeriod')}" />
        <list:data-column col="fdVoucherDate" title="${lfn:message('fssc-voucher:fsscVoucherMain.fdVoucherDate')}">
            <kmss:showDate value="${fsscVoucherMain.fdVoucherDate}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdBookkeepingDate" title="${lfn:message('fssc-voucher:fsscVoucherMain.fdBookkeepingDate')}">
            <kmss:showDate value="${fsscVoucherMain.fdBookkeepingDate}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-voucher:fsscVoucherMain.docCreateTime')}">
            <kmss:showDate value="${fsscVoucherMain.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('fssc-voucher:fsscVoucherMain.docAlterTime')}">
            <kmss:showDate value="${fsscVoucherMain.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdBookkeepingStatus" title="${lfn:message('fssc-voucher:fsscVoucherMain.fdBookkeepingStatus')}">
            <sunbor:enumsShow value="${fsscVoucherMain.fdBookkeepingStatus}" enumsType="fssc_voucher_fd_bookkeeping_status" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
