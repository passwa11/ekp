<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>


[ 

    {
		url : "/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=data&q.mydoc=create&orderby=docCreateTime&ordertype=down&mobile=contract",
		text: "${ lfn:message('hr-ratify:kmReviewMain.mobile.create.mine') }"
	},
	{ 
		url : "/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=data&q.mydoc=approval&orderby=docCreateTime&ordertype=down&mobile=contract", 
		text : "${ lfn:message('hr-ratify:kmReviewMain.mobile.approval.mine') }",
		selected : true 
	},
	{ 
		url : "/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=data&q.mydoc=approved&orderby=docCreateTime&ordertype=down&mobile=contract", 
		text : "${ lfn:message('hr-ratify:kmReviewMain.mobile.approved.mine') }"
	},
	{
		url : "/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=data&orderby=docCreateTime&ordertype=down&mobile=contract", 
		text : "${ lfn:message('hr-ratify:kmReviewMain.mobile.all.mine') }"
	}
]
