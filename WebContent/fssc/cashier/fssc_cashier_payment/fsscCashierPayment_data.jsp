<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscCashierPayment" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('fssc-cashier:fsscCashierPayment.docSubject')}" />
        <list:data-column col="fdPaymentMoney" title="${lfn:message('fssc-cashier:fsscCashierPayment.fdPaymentMoney')}" >
            <fmt:formatNumber value="${fsscCashierPayment.fdPaymentMoney}" pattern="#,###.##"/>
        </list:data-column>

        <list:data-column property="fdDesc" style="width:30%;" title="${lfn:message('fssc-cashier:fsscCashierPayment.fdDesc')}" />
        <list:data-column property="fdModelNumber" title="${lfn:message('fssc-cashier:fsscCashierPayment.fdModelNumber')}" />
        <list:data-column col="fdCompany.name" title="${lfn:message('fssc-cashier:fsscCashierPayment.fdCompany')}" >
            <c:out value="${fsscCashierPayment.fdCompany.fdName}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
