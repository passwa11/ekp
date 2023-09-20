<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinAccount" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdAccountId" title="${lfn:message('third-weixin:thirdWeixinAccount.fdAccountId')}" />
        <list:data-column col="fdAccountType.name" title="${lfn:message('third-weixin:thirdWeixinAccount.fdAccountType')}">
            <sunbor:enumsShow value="${thirdWeixinAccount.fdAccountType}" enumsType="third_weixin_account_type" />
        </list:data-column>
        <list:data-column col="fdAccountType">
            <c:out value="${thirdWeixinAccount.fdAccountType}" />
        </list:data-column>
        <list:data-column property="fdAccountName" title="${lfn:message('third-weixin:thirdWeixinAccount.fdAccountName')}" />
        <list:data-column property="fdEkpId" title="${lfn:message('third-weixin:thirdWeixinAccount.fdEkpId')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-weixin:thirdWeixinAccount.docCreateTime')}">
            <kmss:showDate value="${thirdWeixinAccount.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('third-weixin:thirdWeixinAccount.docAlterTime')}">
            <kmss:showDate value="${thirdWeixinAccount.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
