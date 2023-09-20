<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

    [{
            url: "/fssc/loan/fssc_loan_main/fsscLoanMain.do?method=data&q.mydoc=create",

            text: "${ lfn:message('fssc-loan:lbpm.create.my') }",
            selected: true
        },

        {
            url: "/fssc/loan/fssc_loan_main/fsscLoanMain.do?method=data&q.mydoc=approval",

            text: "${ lfn:message('fssc-loan:lbpm.approval.my') }"
        },

        {
            url: "/fssc/loan/fssc_loan_main/fsscLoanMain.do?method=data&q.mydoc=approved",

            text: "${ lfn:message('fssc-loan:lbpm.approved.my') }"
        }
    ]

