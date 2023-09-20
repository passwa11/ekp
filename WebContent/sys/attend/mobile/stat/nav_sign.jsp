<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

[ 
	{ 
		url : "/sys/attend/sys_attend_stat_month/sysAttendStatMonth.do?method=list&fdType=2", 
		text : "${ lfn:message('sys-attend:mui.signed') }",
		statType:2
	},
	{
		url : "/sys/attend/sys_attend_stat_month/sysAttendStatMonth.do?method=list&fdType=3",
		text: "${ lfn:message('sys-attend:mui.unsign') }",
		statType:2
	}
]
