<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

[ 
	{ 
		moveTo: "usualCateView", 
		text: "${ lfn:message('sys-mobile:mui.mobile.address.usual') }",
		key: "usual",
		selected : true
	},
	{ 
		moveTo: "allCateView", 
		text: "${ lfn:message('sys-mobile:mui.mobile.address.allCate') }",
		key: "all"
	},
	{
		moveTo: "groupView",
		text: "${ lfn:message('sys-mobile:mui.mobile.address.group') }",
		key: "group"
	}
]