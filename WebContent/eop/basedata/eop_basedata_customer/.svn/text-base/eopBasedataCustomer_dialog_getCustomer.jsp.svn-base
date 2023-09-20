<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataCustomer" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataCustomer.fdName')}"  escape="false"/>
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataCustomer.fdCode')}"  escape="false"/>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataCustomer.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataCustomer.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataCustomer.fdIsAvailable}" />
        </list:data-column>
        <list:data-column property="fdTaxNo" title="${lfn:message('eop-basedata:eopBasedataCustomer.fdTaxNo')}"  escape="false"/>
        <list:data-column property="fdErpNo" title="${lfn:message('eop-basedata:eopBasedataCustomer.fdErpNo')}"  escape="false"/>
        <list:data-column col="fdCustomerChargeName" escape="false">
            <c:out value="${eopBasedataCustomer.fdCharge.fdName}" />
        </list:data-column>
        <list:data-column col="fdCustomerChargeId" escape="false">
            <c:out value="${eopBasedataCustomer.fdCharge.fdId}" />
        </list:data-column>
        <list:data-column col="fdTel" escape="false">
            <c:out value="${eopBasedataCustomer.fdTel}" />
        </list:data-column>
        <list:data-column col="fdAddress" escape="false">
            <c:out value="${eopBasedataCustomer.fdAddress}" />
        </list:data-column>
        <list:data-column col="fdPayerBank" escape="false">
            <c:out value="${eopBasedataCustomer.fdBankName}" />
        </list:data-column>
        <list:data-column col="fdBankAccount" escape="false">
            <c:out value="${eopBasedataCustomer.fdBankAccount}" />
        </list:data-column>
        <list:data-column col="fdEmail" escape="false">
            <c:out value="${eopBasedataCustomer.fdEmail}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataCustomer.docCreateTime')}">
            <kmss:showDate value="${eopBasedataCustomer.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
