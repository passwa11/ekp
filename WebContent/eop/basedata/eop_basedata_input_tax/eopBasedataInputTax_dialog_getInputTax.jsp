<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataInputTax" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
         <list:data-column col="fdTaxRate" title="${lfn:message('eop-basedata:eopBasedataInputTax.fdTaxRate')}"  escape="false">
        	${eopBasedataInputTax.fdTaxRate}%
        </list:data-column> 
        <list:data-column col="fdAccount.name" title="${lfn:message('eop-basedata:eopBasedataInputTax.fdAccount')}" escape="false">
            <c:out value="${eopBasedataInputTax.fdAccount.fdName}" />
        </list:data-column>
       
        <list:data-column col="fdAccount.id" escape="false">
            <c:out value="${eopBasedataInputTax.fdAccount.fdId}" />
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
