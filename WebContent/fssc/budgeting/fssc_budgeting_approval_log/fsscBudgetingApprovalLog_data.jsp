<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscBudgetingApprovalLog" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('fssc-budgeting:fsscBudgetingApprovalLog.docSubject')}" />
        <list:data-column col="fdOperator.name" title="${lfn:message('fssc-budgeting:fsscBudgetingApprovalLog.fdOperator')}" escape="false">
            <c:out value="${fsscBudgetingApprovalLog.fdOperator.fdName}" />
        </list:data-column>
        <list:data-column col="fdOperator.id" escape="false">
            <c:out value="${fsscBudgetingApprovalLog.fdOperator.fdId}" />
        </list:data-column>
        <list:data-column col="fdApprovalTime" title="${lfn:message('fssc-budgeting:fsscBudgetingApprovalLog.fdApprovalTime')}">
            <kmss:showDate value="${fsscBudgetingApprovalLog.fdApprovalTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdApprovalType" title="${lfn:message('fssc-budgeting:fsscBudgetingApprovalLog.fdApprovalType')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
