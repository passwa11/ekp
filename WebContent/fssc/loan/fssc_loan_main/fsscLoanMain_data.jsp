<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscLoanMain" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('fssc-loan:fsscLoanMain.docSubject')}" />
        <list:data-column property="docNumber" title="${lfn:message('fssc-loan:fsscLoanMain.docNumber')}" />
        <list:data-column col="fdLoanMoney" title="${lfn:message('fssc-loan:fsscLoanMain.fdLoanMoney')}" >
            <fmt:formatNumber value="${fsscLoanMain.fdLoanMoney}" pattern="#,###.##"/>
        </list:data-column>
        <list:data-column col="fdLoanPerson.name" title="${lfn:message('fssc-loan:fsscLoanMain.fdLoanPerson')}" escape="false">
            <c:out value="${fsscLoanMain.fdLoanPerson.fdName}" />
        </list:data-column>
        <list:data-column col="fdLoanPerson.id" escape="false">
            <c:out value="${fsscLoanMain.fdLoanPerson.fdId}" />
        </list:data-column>
        <list:data-column col="fdCompany.fdName" title="${lfn:message('fssc-loan:fsscLoanMain.fdCompany')}" escape="false">
            <c:out value="${fsscLoanMain.fdCompany.fdName}" />
        </list:data-column>
        <list:data-column col="fdCompany.id" escape="false">
            <c:out value="${fsscLoanMain.fdCompany.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-loan:fsscLoanMain.docCreateTime')}">
            <kmss:showDate value="${fsscLoanMain.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdExpectedDate" title="${lfn:message('fssc-loan:fsscLoanMain.fdExpectedDate')}">
            <kmss:showDate value="${fsscLoanMain.fdExpectedDate}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docStatus" title="${lfn:message('fssc-loan:fsscLoanMain.docStatus')}">
            <sunbor:enumsShow value="${fsscLoanMain.docStatus}" enumsType="fssc_loan_doc_status" />
        </list:data-column>
        <list:data-column col="docStatus">
            <c:out value="${fsscLoanMain.docStatus}" />
        </list:data-column>
        <list:data-column col="fdPaymentStatus" title="${lfn:message('fssc-loan:fsscLoanMain.fdPaymentStatus')}">
            <sunbor:enumsShow value="${fsscLoanMain.fdPaymentStatus}" enumsType="eop_basedata_payment_status" />
        </list:data-column>
        <list:data-column col="fdPaymentStatus">
            <c:out value="${fsscLoanMain.fdPaymentStatus}" />
        </list:data-column>
        <list:data-column col="fdVoucherStatus" title="${lfn:message('fssc-loan:fsscLoanMain.fdVoucherStatus')}">
             <sunbor:enumsShow value="${fsscLoanMain.fdVoucherStatus}" enumsType="eop_basedata_fd_voucher_status" />
        </list:data-column>
        <list:data-column col="fdVoucherStatus">
             <c:out value="${fsscLoanMain.fdVoucherStatus}" />
        </list:data-column>
        <list:data-column col="fdBookkeepingStatus"  title="${lfn:message('fssc-loan:fsscLoanMain.fdBookkeepingStatus')}">
            <sunbor:enumsShow value="${fsscLoanMain.fdBookkeepingStatus}" enumsType="eop_basedata_fd_bookkeeping_status" />
        </list:data-column>
        <list:data-column col="fdBookkeepingStatus">
            <c:out value="${fsscLoanMain.fdBookkeepingStatus}" />
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_summary" title="${lfn:message('fssc-loan:lbpm.currentSummary') }" escape="false">
            <kmss:showWfPropertyValues var="nodevalue" idValue="${fsscLoanMain.fdId}" propertyName="nodeName" />
            <div title="${nodevalue}">
                <c:out value="${nodevalue}"></c:out>
            </div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_handler" title="${lfn:message('fssc-loan:lbpm.currentHandler') }" escape="false">
            <kmss:showWfPropertyValues var="handlerValue" idValue="${fsscLoanMain.fdId}" propertyName="handlerName" />
            <div style="font-weight:bold;" title="${handlerValue}">
                <c:out value="${handlerValue}"></c:out>
            </div>
        </list:data-column>
          <%--累计未还款金额--%>
        <list:data-column col="fdTotalNotRepaymentMoney" title="${lfn:message('fssc-loan:fsscLoanMain.fdTotalNotRepaymentMoney')}" >
            <kmss:showNumber value="${fsscLoanMain.fdTotalNotRepaymentMoney}" pattern="###,##0.00"/>
        </list:data-column>
        <%--累计已还款金额--%>
        <list:data-column col="fdTotalRepaymentMoney" title="${lfn:message('fssc-loan:fsscLoanMain.fdTotalRepaymentMoney')}" >
            <kmss:showNumber value="${fsscLoanMain.fdTotalRepaymentMoney}" pattern="###,##0.00"/>
        </list:data-column>
        <%--累计借款金额--%>
        <list:data-column col="fdTotalLoanMoney" title="${lfn:message('fssc-loan:fsscLoanMain.fdTotalLoanMoney')}" >
            <kmss:showNumber value="${fsscLoanMain.fdTotalLoanMoney}" pattern="###,##0.00"/>
        </list:data-column>
        <%--累计待批金额，累计剩余金额--%>
        <list:data-column col="fdTotalPendingMoney" title="${lfn:message('fssc-loan:fsscLoanMain.fdTotalPendingMoney')}" >
            <fmt:formatNumber value="${fsscLoanMain.fdTotalPendingMoney}" pattern="#,###.##"/>
        </list:data-column>
        <list:data-column col="fdTotalRemainingMoney" title="${lfn:message('fssc-loan:fsscLoanMain.fdTotalRemainingMoney')}" >
            <fmt:formatNumber value="${fsscLoanMain.fdTotalRemainingMoney}" pattern="#,###.##"/>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
