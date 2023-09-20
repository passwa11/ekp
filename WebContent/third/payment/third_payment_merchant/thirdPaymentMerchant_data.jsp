<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdPaymentMerchant" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdMerchId" title="${lfn:message('third-payment:thirdPaymentMerchant.fdMerchId')}" />
        <list:data-column col="fdMerchType.name" title="${lfn:message('third-payment:thirdPaymentMerchant.fdMerchType')}">
            <sunbor:enumsShow value="${thirdPaymentMerchant.fdMerchType}" enumsType="third_payment_merch_type" />
        </list:data-column>
        <list:data-column col="fdMerchType">
            <c:out value="${thirdPaymentMerchant.fdMerchType}" />
        </list:data-column>
        <list:data-column property="fdMerchName" title="${lfn:message('third-payment:thirdPaymentMerchant.fdMerchName')}" />
        <list:data-column property="fdCorpName" title="${lfn:message('third-payment:thirdPaymentMerchant.fdCorpName')}" />
        <list:data-column col="docCreator.name" title="${lfn:message('third-payment:thirdPaymentMerchant.docCreator')}" escape="false">
            <c:out value="${thirdPaymentMerchant.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${thirdPaymentMerchant.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('third-payment:thirdPaymentMerchant.docCreateTime')}">
            <kmss:showDate value="${thirdPaymentMerchant.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
