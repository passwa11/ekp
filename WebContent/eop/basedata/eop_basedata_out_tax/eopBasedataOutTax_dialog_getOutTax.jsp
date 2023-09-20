<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataOutTax" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="fdRate" title="${lfn:message('eop-basedata:eopBasedataOutTax.fdRate')}" >
            ${eopBasedataOutTax.fdRate}%
        </list:data-column>
        <list:data-column col="fdAccount.name" title="${lfn:message('eop-basedata:eopBasedataOutTax.fdAccount')}" escape="false">
            <c:out value="${eopBasedataOutTax.fdAccount.fdName}" />
        </list:data-column>
        <list:data-column col="fdAccount.id" escape="false">
            <c:out value="${eopBasedataOutTax.fdAccount.fdId}" />
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
