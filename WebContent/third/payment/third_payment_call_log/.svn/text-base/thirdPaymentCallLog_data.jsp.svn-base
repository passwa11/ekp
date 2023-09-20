<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdPaymentCallLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdModelName" title="${lfn:message('third-payment:thirdPaymentCallLog.fdModelName')}" />
        <list:data-column property="fdModelId" title="${lfn:message('third-payment:thirdPaymentCallLog.fdModelId')}" />
        <list:data-column property="fdOrderNo" title="${lfn:message('third-payment:thirdPaymentCallLog.fdOrderNo')}" />
        <list:data-column property="fdOrderDesc" title="${lfn:message('third-payment:thirdPaymentCallLog.fdOrderDesc')}" />
        <list:data-column property="fdCallMethod" title="${lfn:message('third-payment:thirdPaymentCallLog.fdCallMethod')}" />
        <list:data-column col="fdCallResult.name" title="${lfn:message('third-payment:thirdPaymentCallLog.fdCallResult')}">
            <sunbor:enumsShow value="${thirdPaymentCallLog.fdCallResult}" enumsType="third_payment_call_result" />
        </list:data-column>
        <list:data-column col="fdCallResult">
            <c:out value="${thirdPaymentCallLog.fdCallResult}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('third-payment:thirdPaymentCallLog.docCreateTime')}">
            <kmss:showDate value="${thirdPaymentCallLog.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
