<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinPayOrder" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdBody" title="${lfn:message('third-weixin:thirdWeixinPayOrder.fdBody')}" />
        <list:data-column property="fdFeeType" title="${lfn:message('third-weixin:thirdWeixinPayOrder.fdFeeType')}" />
        <list:data-column property="fdTotalFee" title="${lfn:message('third-weixin:thirdWeixinPayOrder.fdTotalFee')}" />
        <list:data-column col="fdTradeType.name" title="${lfn:message('third-weixin:thirdWeixinPayOrder.fdTradeType')}">
            <sunbor:enumsShow value="${thirdWeixinPayOrder.fdTradeType}" enumsType="third_weixin_trade_type" />
        </list:data-column>
        <list:data-column col="fdTradeType">
            <c:out value="${thirdWeixinPayOrder.fdTradeType}" />
        </list:data-column>
        <list:data-column property="fdProductId" title="${lfn:message('third-weixin:thirdWeixinPayOrder.fdProductId')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-weixin:thirdWeixinPayOrder.docCreateTime')}">
            <kmss:showDate value="${thirdWeixinPayOrder.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdModelName" title="${lfn:message('third-weixin:thirdWeixinPayOrder.fdModelName')}" />
        <list:data-column property="fdModelId" title="${lfn:message('third-weixin:thirdWeixinPayOrder.fdModelId')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
