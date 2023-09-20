<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataTaxRate" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="fdCompany.name" title="${lfn:message('eop-basedata:eopBasedataTaxRate.fdCompany')}" escape="false">
            <c:out value="${eopBasedataTaxRate.fdCompany.fdName}" />
        </list:data-column>
        
        <list:data-column col="fdAccount.name" title="${lfn:message('eop-basedata:eopBasedataTaxRate.fdAccount')}" escape="false">
            <c:out value="${eopBasedataTaxRate.fdAccount.fdName}" />
        </list:data-column>
        <list:data-column col="fdRate" title="${lfn:message('eop-basedata:eopBasedataTaxRate.fdRate')}" >
        	${eopBasedataTaxRate.fdRate}%
        </list:data-column>
        <list:data-column col="fdAccount.id" escape="false">
            <c:out value="${eopBasedataTaxRate.fdAccount.fdId}" />
        </list:data-column>
        <list:data-column col="fdCompany.id" escape="false">
            <c:out value="${eopBasedataTaxRate.fdCompany.fdId}" />
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
