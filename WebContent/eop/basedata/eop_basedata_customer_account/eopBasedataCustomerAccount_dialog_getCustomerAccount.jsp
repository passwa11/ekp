<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataCustomerAccount" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="fdBankNo" />
        <list:data-column property="fdAccountName" title="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdAccountName')}"  escape="false"/>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdBankName" title="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdBankName')}"  escape="false"/>
        <list:data-column property="fdBankAccount" title="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdBankAccount')}" />
        <list:data-column property="fdAccountAreaCode" />
        <list:data-column property="fdAccountAreaName" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
