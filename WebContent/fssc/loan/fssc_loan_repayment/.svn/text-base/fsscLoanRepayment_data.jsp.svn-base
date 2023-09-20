<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscLoanRepayment" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('fssc-loan:fsscLoanRepayment.docSubject')}" />
        <list:data-column property="docNumber" title="${lfn:message('fssc-loan:fsscLoanRepayment.docNumber')}" />
        <list:data-column col="docCreator.name" title="${lfn:message('fssc-loan:fsscLoanRepayment.docCreator')}" escape="false">
            <c:out value="${fsscLoanRepayment.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${fsscLoanRepayment.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="fdRepaymentPerson.name" title="${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentPerson')}" escape="false">
            <c:out value="${fsscLoanRepayment.fdRepaymentPerson.fdName}" />
        </list:data-column>
        <list:data-column col="fdRepaymentPerson.id" escape="false">
            <c:out value="${fsscLoanRepayment.fdRepaymentPerson.fdId}" />
        </list:data-column>
        <list:data-column col="fdRepaymentDept.fdName" title="${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentDept')}" escape="false">
            <c:out value="${fsscLoanRepayment.fdRepaymentDept.fdName}" />
        </list:data-column>
        <list:data-column col="fdRepaymentDept.id" escape="false">
            <c:out value="${fsscLoanRepayment.fdRepaymentDept.fdId}" />
        </list:data-column>
        <list:data-column col="fdCanOffsetMoney" title="${lfn:message('fssc-loan:fsscLoanRepayment.fdCanOffsetMoney')}">
            <kmss:showNumber value="${fsscLoanRepayment.fdCanOffsetMoney}" pattern="###,##0.00"></kmss:showNumber>
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-loan:fsscLoanRepayment.docCreateTime')}">
            <kmss:showDate value="${fsscLoanRepayment.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdRepaymentMoney" title="${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentMoney')}" >
            <fmt:formatNumber value="${fsscLoanRepayment.fdRepaymentMoney}" pattern="#,###.##"/>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_summary" title="${lfn:message('fssc-loan:lbpm.currentSummary') }" escape="false">
            <kmss:showWfPropertyValues var="nodevalue" idValue="${fsscLoanRepayment.fdId}" propertyName="nodeName" />
            <div title="${nodevalue}">
                <c:out value="${nodevalue}"></c:out>
            </div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_handler" title="${lfn:message('fssc-loan:lbpm.currentHandler') }" escape="false">
            <kmss:showWfPropertyValues var="handlerValue" idValue="${fsscLoanRepayment.fdId}" propertyName="handlerName" />
            <div style="font-weight:bold;" title="${handlerValue}">
                <c:out value="${handlerValue}"></c:out>
            </div>
        </list:data-column>
        <list:data-column col="docStatus" title="${lfn:message('fssc-loan:fsscLoanRepayment.docStatus')}">
            <sunbor:enumsShow value="${fsscLoanRepayment.docStatus}" enumsType="fssc_loan_doc_status" />
        </list:data-column>
        <list:data-column col="docStatus">
            <c:out value="${fsscLoanRepayment.docStatus}" />
        </list:data-column>
        <list:data-column col="fdVoucherStatus" title="${lfn:message('fssc-loan:fsscLoanRepayment.fdVoucherStatus')}">
            <sunbor:enumsShow value="${fsscLoanRepayment.fdVoucherStatus}" enumsType="eop_basedata_fd_voucher_status" />
        </list:data-column>
        <list:data-column col="fdVoucherStatus">
            <c:out value="${fsscLoanRepayment.fdVoucherStatus}" />
        </list:data-column>
        <list:data-column col="fdBookkeepingStatus" title="${lfn:message('fssc-loan:fsscLoanRepayment.fdBookkeepingStatus')}">
            <sunbor:enumsShow value="${fsscLoanRepayment.fdBookkeepingStatus}" enumsType="eop_basedata_fd_bookkeeping_status" />
        </list:data-column>
        <list:data-column col="fdBookkeepingStatus">
            <c:out value="${fsscLoanRepayment.fdBookkeepingStatus}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
