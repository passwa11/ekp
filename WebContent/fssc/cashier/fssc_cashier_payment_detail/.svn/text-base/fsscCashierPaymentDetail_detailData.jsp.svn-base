<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscCashierPaymentDetail" list="${detailList}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column property="docMain.fdId"  />
        <list:data-column property="fdPlanPaymentDate"  title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPlanPaymentDate')}" />
        <list:data-column property="docNumber" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.docNumber')}" />
        <list:data-column property="fdModelNumber" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdModelNumber')}" />
        <list:data-column property="fdPayeeName" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPayeeName')}" />
        <list:data-column property="fdPayeeAccount" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPayeeAccount')}" />
        <list:data-column property="fdPayeeBankName" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPayeeBankName')}" />
        <list:data-column col="fdStatus" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdStatus')}">
        	<sunbor:enumsShow enumsType="fssc_cashier_fd_status" value="${fsscCashierPaymentDetail.fdStatus}"></sunbor:enumsShow>
        </list:data-column>
        <list:data-column col="fdPaymentMoney" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPaymentMoney')}" >
        <kmss:showNumber value="${fsscCashierPaymentDetail.fdPaymentMoney}" pattern="0.00"></kmss:showNumber>
        </list:data-column>
        <list:data-column col="fdStatus.name" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdStatus')}" escape="false" >
            <sunbor:enumsShow value="${fsscCashierPaymentDetail.fdStatus}" enumsType="fssc_cashier_fd_status" />
            <input type="hidden" name="fdExportNumber" value="${fsscCashierPaymentDetail.fdExportNumber}"  />
                <input type="hidden" name="serialNumber" value="${status+1}"  />
        </list:data-column>
        <list:data-column col="fdIsExport.name" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdIsExport')}" escape="false" >
            <sunbor:enumsShow value="${fsscCashierPaymentDetail.fdIsExport}" enumsType="fssc_cashier_fd_is_export" />
        </list:data-column>
        <list:data-column col="fdBaseCurrency.name" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdBaseCurrency')}" >
            <c:out value="${fsscCashierPaymentDetail.fdBaseCurrency.fdName}" />
        </list:data-column>
        <list:data-column col="fdCompany.name" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdCompany')}" >
            <c:out value="${fsscCashierPaymentDetail.fdCompany.fdName}" />
        </list:data-column>
        <list:data-column col="fdBasePayBank.name" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdBasePayBank')}" >
            <c:out value="${fsscCashierPaymentDetail.fdBasePayBank.fdBankName}" />
        </list:data-column>
        <list:data-column col="fdRate" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdRate')}" >
            <c:out value="${fsscCashierPaymentDetail.fdRate}" />
        </list:data-column>
        <list:data-column col="fdBasePayWay.name" title="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdBasePayWay')}" >
            <c:out value="${fsscCashierPaymentDetail.fdBasePayWay.fdName}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
