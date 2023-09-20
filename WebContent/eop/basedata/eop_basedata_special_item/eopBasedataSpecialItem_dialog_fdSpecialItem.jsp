<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataSpecialItem" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="fdDescription" title="${lfn:message('eop-basedata:eopBasedataSpecialItem.fdDescription')}" />
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataSpecialItem.fdCode')}" />
        <list:data-column col="fdCompany.name" title="${lfn:message('eop-basedata:eopBasedataSpecialItem.fdCompany')}" escape="false">
            <c:out value="${eopBasedataSpecialItem.fdCompany.fdName}" />
        </list:data-column>
        <list:data-column col="fdCompany.id" escape="false">
            <c:out value="${eopBasedataSpecialItem.fdCompany.fdId}" />
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
