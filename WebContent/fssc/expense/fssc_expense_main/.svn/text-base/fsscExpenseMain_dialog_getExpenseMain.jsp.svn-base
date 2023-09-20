<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscExpenseMain" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="docSubject" title="${lfn:message('fssc-expense:fsscExpenseMain.docSubject')}" />
        <list:data-column property="docNumber" title="${lfn:message('fssc-expense:fsscExpenseMain.docNumber')}" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
