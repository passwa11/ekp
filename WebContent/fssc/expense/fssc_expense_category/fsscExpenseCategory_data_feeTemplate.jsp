<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscExpenseCategory" list="${queryPage.list}" varIndex="status">
        <list:data-column col="fdId" >
        	${fsscExpenseCategory['fdId']}
        </list:data-column>
        <list:data-column col="fdName" title="${lfn:message('fssc-expense:fsscExpenseCategory.fdFeeTemplateName')}" >
        	${fsscExpenseCategory['fdName']}
        </list:data-column>
        <list:data-column col="docCreator.fdName" title="${lfn:message('fssc-expense:fsscExpenseMain.docTemplate')}" >
        	${fsscExpenseCategory['docCategory']}
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
