<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataGood" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataGood.fdName')}"  escape="false"/>
        <list:data-column col="fdCode" title="${lfn:message('eop-basedata:eopBasedataGood.fdCode')}" escape="false">
            <c:out value="${eopBasedataGood.fdCode}" />
        </list:data-column>
        <list:data-column col="fdTaxRateName" title="${lfn:message('eop-basedata:eopBasedataGood.fdTaxRate')}" escape="false">
            <c:if test="${not empty eopBasedataGood.fdTaxRate}">
                ${eopBasedataGood.fdTaxRate}%
            </c:if>
        </list:data-column>
        <list:data-column col="fdTaxRate" escape="false">
            <c:out value="${eopBasedataGood.fdTaxRate}" />
        </list:data-column>
        <list:data-column col="fdWithTaxFlagName" title="${lfn:message('eop-basedata:eopBasedataGood.fdWithTaxFlag')}" escape="false">
            <sunbor:enumsShow enumsType="fssc_invoice_is_tax" value="${eopBasedataGood.fdWithTaxFlag }"/>
        </list:data-column>
        <list:data-column col="fdWithTaxFlag" escape="false">
            ${eopBasedataGood.fdWithTaxFlag}
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
