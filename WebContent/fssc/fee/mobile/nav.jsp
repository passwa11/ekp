<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

[ 
	{ 
		url : "/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=data&q.mydoc=approval&orderby=docCreateTime&ordertype=down", 
		text : "${ lfn:message('fssc-fee:lbpm.approval.my') }", 
		selected : true 
	},
	{ 
		url : "/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=data&q.mydoc=approved&orderby=docCreateTime&ordertype=down", 
		text : "${ lfn:message('fssc-fee:lbpm.approved.my') }"
	},
	{
		url : "/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=data&q.mydoc=create&orderby=docCreateTime&ordertype=down",
		text: "${ lfn:message('fssc-fee:lbpm.create.my') }"
	}
]
