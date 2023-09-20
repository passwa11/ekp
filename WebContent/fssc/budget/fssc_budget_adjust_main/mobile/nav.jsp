<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

    [{
            url: "/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=data&q.mydoc=create",

            text: "${ lfn:message('list.create') }",
            selected: true
        },

        {
            url: "/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=data&q.mydoc=approval",

            text: "${ lfn:message('list.approval') }"
        },

        {
            url: "/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=data&q.mydoc=approved",

            text: "${ lfn:message('list.approved') }"
        }
    ]

