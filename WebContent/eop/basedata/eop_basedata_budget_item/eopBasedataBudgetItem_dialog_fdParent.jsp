<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataBudgetItem" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataBudgetItem.fdName')}" escape="false" />
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataBudgetItem.fdCode')}" escape="false" />
        <list:data-column col="fdParent.name" title="${lfn:message('eop-basedata:eopBasedataBudgetItem.fdParent')}" escape="false">
            <c:out value="${eopBasedataBudgetItem.fdParent.fdName}" />
        </list:data-column>
        <list:data-column col="fdParent.id" escape="false">
            <c:out value="${eopBasedataBudgetItem.fdParent.fdId}" />
        </list:data-column>
        <list:data-column col="fdCompanyName" title="${lfn:message('eop-basedata:eopBasedataBudgetItem.fdCompanyList')}" >
            <c:forEach items="${eopBasedataBudgetItem.fdCompanyList}" var="fdCompany" varStatus="status">
                <c:if test="${status.index!=0}">;</c:if>
                ${fdCompany.fdName}
            </c:forEach>
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
