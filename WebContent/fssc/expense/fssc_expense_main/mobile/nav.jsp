<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

    [{
            url: "/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=data&q.mydoc=create&o.docCreateTime=down",

            text: "${ lfn:message('fssc-expense:py.WoChuangJianDe') }"
        },

        {
            url: "/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=data&q.mydoc=approval&o.docCreateTime=down",

            text: "${ lfn:message('fssc-expense:py.DaiWoShenDe') }"
        },

        {
            url: "/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=data&q.mydoc=approved&o.docCreateTime=down",

            text: "${ lfn:message('fssc-expense:py.WoYiShenDe') }",
            selected: true
        }
    ]

