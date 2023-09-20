<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinPayBl" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdBody" title="${lfn:message('third-weixin:thirdWeixinPayBl.fdBody')}" />
        <list:data-column property="fdTotalFee" title="${lfn:message('third-weixin:thirdWeixinPayBl.fdTotalFee')}" />
        <list:data-column property="fdTransactionId" title="${lfn:message('third-weixin:thirdWeixinPayBl.fdTransactionId')}" />
        <list:data-column col="fdPayPerson.name" title="${lfn:message('third-weixin:thirdWeixinPayBl.fdPayPerson')}" escape="false">
            <c:out value="${thirdWeixinPayBl.fdPayPerson.fdName}" />
        </list:data-column>
        <list:data-column col="fdPayPerson.id" escape="false">
            <c:out value="${thirdWeixinPayBl.fdPayPerson.fdId}" />
        </list:data-column>
        <list:data-column property="fdModelName" title="${lfn:message('third-weixin:thirdWeixinPayBl.fdModelName')}" />
        <list:data-column property="fdModelId" title="${lfn:message('third-weixin:thirdWeixinPayBl.fdModelId')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
