<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscExpenseCategory" list="${queryPage.list}" varIndex="status">
        <list:data-column col="fdId" >
        	${fsscExpenseCategory['fdId']}
        </list:data-column>
        <list:data-column col="docSubject" title="${lfn:message('fssc-expense:fsscExpenseMain.docSubject')}" >
        	${fsscExpenseCategory['docSubject']}
        </list:data-column>
        <list:data-column col="docNumber" title="${lfn:message('fssc-expense:fsscExpenseMain.docNumber')}" >
        	${fsscExpenseCategory['docNumber']}
        </list:data-column>
        <list:data-column col="docCreator" title="${lfn:message('fssc-expense:fsscExpenseMain.docCreator')}" >
        	${fsscExpenseCategory['docCreator']}
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-expense:fsscExpenseMain.docCreateTime')}" >
        	${fsscExpenseCategory['docCreateTime']}
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
