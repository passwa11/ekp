<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil"%>
<%
    request.setAttribute("fdIsBudget", EopBasedataFsscUtil.getSwitchValue("fdIsBudget"));
%>
<c:if test="${(empty fdIsBudget or fdIsBudget=='true') and (param.docStatus=='20' or param.docStatus=='30')}">
    <script src="${LUI_ContextPath }/fssc/budget/resource/jsp/fsscBudgetBill_view.js"></script>
    <kmss:auth requestURL="/fssc/budget/fssc_budget_execute/fsscBudgetExecute.do?method=billBudget&fdModelId=${HtmlParam.fdModelId}">
        <ui:button parentId="toolbar" text="${lfn:message('fssc-budget:button.view.bill.budget')}" order="4" onclick="viewBillBudget('${HtmlParam.fdModelId}');" />
    </kmss:auth>
</c:if>