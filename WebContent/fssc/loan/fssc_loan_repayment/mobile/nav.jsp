<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

    [{
            url: "/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=data&q.mydoc=create&o.docCreateTime=down",

            text: "${ lfn:message('fssc-loan:lbpm.create.my') }"
        },

        {
            url: "/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=data&q.mydoc=approval&o.docCreateTime=down",

            text: "${ lfn:message('fssc-loan:lbpm.approval.my') }"
        },

        {
            url: "/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=data&q.mydoc=approved&o.docCreateTime=down",

            text: "${ lfn:message('fssc-loan:lbpm.approved.my') }",
            selected: true
        }
    ]

