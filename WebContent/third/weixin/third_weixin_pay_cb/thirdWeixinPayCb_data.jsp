<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinPayCb" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdTransactionId" title="${lfn:message('third-weixin:thirdWeixinPayCb.fdTransactionId')}" />
        <list:data-column property="fdOpenid" title="${lfn:message('third-weixin:thirdWeixinPayCb.fdOpenid')}" />
        <list:data-column property="fdTotalFee" title="${lfn:message('third-weixin:thirdWeixinPayCb.fdTotalFee')}" />
        <list:data-column property="fdResultCode" title="${lfn:message('third-weixin:thirdWeixinPayCb.fdResultCode')}" />
        <list:data-column property="fdReturnCode" title="${lfn:message('third-weixin:thirdWeixinPayCb.fdReturnCode')}" />
        <list:data-column property="fdTimeEnd" title="${lfn:message('third-weixin:thirdWeixinPayCb.fdTimeEnd')}" />
        <list:data-column property="fdBankType" title="${lfn:message('third-weixin:thirdWeixinPayCb.fdBankType')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
