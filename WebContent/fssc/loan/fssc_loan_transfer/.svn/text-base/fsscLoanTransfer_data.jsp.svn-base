<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscLoanTransfer" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('fssc-loan:fsscLoanTransfer.docSubject')}" />
        <list:data-column property="docNumber" title="${lfn:message('fssc-loan:fsscLoanTransfer.docNumber')}" />
        <list:data-column col="fdTurnOut.name" title="${lfn:message('fssc-loan:fsscLoanTransfer.fdTurnOut')}" escape="false">
            <c:out value="${fsscLoanTransfer.fdTurnOut.fdName}" />
        </list:data-column>
        <list:data-column col="fdTurnOut.id" escape="false">
            <c:out value="${fsscLoanTransfer.fdTurnOut.fdId}" />
        </list:data-column>
        <list:data-column col="fdReceive.name" title="${lfn:message('fssc-loan:fsscLoanTransfer.fdReceive')}" escape="false">
            <c:out value="${fsscLoanTransfer.fdReceive.fdName}" />
        </list:data-column>
        <list:data-column col="fdReceive.id" escape="false">
            <c:out value="${fsscLoanTransfer.fdReceive.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-loan:fsscLoanTransfer.docCreateTime')}">
             <kmss:showDate value="${fsscLoanTransfer.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdTransferMoney" title="${lfn:message('fssc-loan:fsscLoanTransfer.fdTransferMoney')}" >
            <fmt:formatNumber value="${fsscLoanTransfer.fdTransferMoney}" pattern="#,###.##"/>
        </list:data-column>
        <list:data-column col="docStatus" title="${lfn:message('fssc-loan:fsscLoanTransfer.docStatus')}">
            <sunbor:enumsShow value="${fsscLoanTransfer.docStatus}" enumsType="fssc_loan_doc_status" />
        </list:data-column>
        <list:data-column col="docStatus">
            <c:out value="${fsscLoanTransfer.docStatus}" />
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_summary" title="${lfn:message('fssc-loan:lbpm.currentSummary') }" escape="false">
            <kmss:showWfPropertyValues var="nodevalue" idValue="${fsscLoanTransfer.fdId}" propertyName="nodeName" />
            <div title="${nodevalue}">
                <c:out value="${nodevalue}"></c:out>
            </div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_handler" title="${lfn:message('fssc-loan:lbpm.currentHandler') }" escape="false">
            <kmss:showWfPropertyValues var="handlerValue" idValue="${fsscLoanTransfer.fdId}" propertyName="handlerName" />
                <div style="font-weight:bold;" title="${handlerValue}">
                <c:out value="${handlerValue}"></c:out>
            </div>
        </list:data-column>
        <list:data-column col="fdVoucherStatus" title="${lfn:message('fssc-loan:fsscLoanTransfer.fdVoucherStatus')}">
            <sunbor:enumsShow value="${fsscLoanTransfer.fdVoucherStatus}" enumsType="eop_basedata_fd_voucher_status" />
        </list:data-column>
        <list:data-column col="fdVoucherStatus">
            <c:out value="${fsscLoanTransfer.fdVoucherStatus}" />
        </list:data-column>
        <list:data-column col="fdBookkeepingStatus" title="${lfn:message('fssc-loan:fsscLoanTransfer.fdBookkeepingStatus')}">
            <sunbor:enumsShow value="${fsscLoanTransfer.fdBookkeepingStatus}" enumsType="eop_basedata_fd_bookkeeping_status" />
        </list:data-column>
        <list:data-column col="fdBookkeepingStatus">
            <c:out value="${fsscLoanTransfer.fdBookkeepingStatus}" />
        </list:data-column>
        <list:data-column col="fdCanOffsetMoney" title="${lfn:message('fssc-loan:fsscLoanTransfer.fdCanOffsetMoney')}">
            <kmss:showNumber value="${fsscLoanTransfer.fdCanOffsetMoney}" pattern="###,##0.00"></kmss:showNumber>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
