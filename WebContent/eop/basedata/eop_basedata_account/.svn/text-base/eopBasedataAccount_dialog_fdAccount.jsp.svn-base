<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataAccount" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="fdBankNo" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataAccount.fdName')}" escape="false"/>
        <list:data-column property="fdBankName" title="${lfn:message('eop-basedata:eopBasedataAccount.fdBankName')}" escape="false" />
        <list:data-column property="fdBankAccount" title="${lfn:message('eop-basedata:eopBasedataAccount.fdBankAccount')}" />
        <list:data-column col="fdPerson.fdName" title="${lfn:message('eop-basedata:eopBasedataAccount.fdPerson')}" >
        	${eopBasedataAccount.fdPerson.fdName}
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
      <list:data-column property="fdAccountArea" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
