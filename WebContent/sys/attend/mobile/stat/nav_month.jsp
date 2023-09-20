<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

[ 
	{ 
		url : "/sys/attend/sys_attend_stat_month/sysAttendStatMonth.do?method=list&fdType=2", 
		text : "${ lfn:message('sys-attend:sysAttendMain.fdStatus.late') }",
		statType:2,
		isNavCount:true
	},
	{
		url : "/sys/attend/sys_attend_stat_month/sysAttendStatMonth.do?method=list&fdType=3",
		text: "${ lfn:message('sys-attend:sysAttendMain.fdStatus.left') }",
		statType:2,
		isNavCount:true
	},
	{
		url : "/sys/attend/sys_attend_stat_month/sysAttendStatMonth.do?method=list&fdType=0",
		text: "${ lfn:message('sys-attend:sysAttendMain.fdStatus.unSign') }",
		statType:2,
		isNavCount:true
	},
	{ 
		url : "/sys/attend/sys_attend_stat_month/sysAttendStatMonth.do?method=list&fdType=5", 
		text : "${ lfn:message('sys-attend:sysAttendMain.fdStatus.missed') }",
		statType:2,
		isNavCount:true
	},{ 
		url : "/sys/attend/sys_attend_stat_month/sysAttendStatMonth.do?method=list&fdType=4", 
		text : "${ lfn:message('sys-attend:sysAttendMain.outside') }",
		statType:2,
		isNavCount:true
	},
	{
		url : "/sys/attend/sys_attend_stat_month/sysAttendStatMonth.do?method=list&fdType=1",
		text: "${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok') }",
		statType:2,
		isNavCount:true
	},{
		url : "/sys/attend/sys_attend_stat_month/sysAttendStatMonth.do?method=list&fdType=7",
		text: "${ lfn:message('sys-attend:sysAttendMain.fdStatus.askforleave') }",
		statType:2,
		isNavCount:true
	},{
		url : "/sys/attend/sys_attend_stat_month/sysAttendStatMonth.do?method=list&fdType=6",
		text: "${ lfn:message('sys-attend:sysAttendMain.fdStatus.business') }",
		statType:2,
		isNavCount:true
	},{
		url : "/sys/attend/sys_attend_stat_month/sysAttendStatMonth.do?method=list&fdType=8",
		text: "${ lfn:message('sys-attend:sysAttendMain.fdStatus.overtime') }",
		statType:2,
		isNavCount:true
	},
	{
		url : "/sys/attend/sys_attend_stat_month/sysAttendStatMonth.do?method=list&fdType=9",
		text: "${ lfn:message('sys-attend:sysAttendMain.fdStatus.outgoing') }",
		statType:2,
		isNavCount:true
	},
]
