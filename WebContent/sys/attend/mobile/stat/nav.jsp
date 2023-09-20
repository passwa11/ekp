<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

[ 
	{ 
		url : "/sys/attend/sys_attend_stat/sysAttendStat.do?method=list&fdType=2", 
		text : "${ lfn:message('sys-attend:sysAttendMain.fdStatus.late') }",
		isNavCount:true
	},
	{
		url : "/sys/attend/sys_attend_stat/sysAttendStat.do?method=list&fdType=3",
		text: "${ lfn:message('sys-attend:sysAttendMain.fdStatus.left') }",
		isNavCount:true
	},
	{
		url : "/sys/attend/sys_attend_stat/sysAttendStat.do?method=list&fdType=0",
		text: "${ lfn:message('sys-attend:sysAttendMain.fdStatus.unSign') }",
		isNavCount:true
	},
	{ 
		url : "/sys/attend/sys_attend_stat/sysAttendStat.do?method=list&fdType=5", 
		text : "${ lfn:message('sys-attend:sysAttendMain.fdStatus.missed') }",
		isNavCount:true
	},
	{ 
		url : "/sys/attend/sys_attend_stat/sysAttendStat.do?method=list&fdType=4", 
		text : "${ lfn:message('sys-attend:sysAttendMain.outside') }",
		isNavCount:true
	},
	{
		url : "/sys/attend/sys_attend_stat/sysAttendStat.do?method=list&fdType=1",
		text: "${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok') }",
		isNavCount:true
	},{
		url : "/sys/attend/sys_attend_stat/sysAttendStat.do?method=list&fdType=7",
		text: "${ lfn:message('sys-attend:sysAttendMain.fdStatus.askforleave') }",
		isNavCount:true
	},{
		url : "/sys/attend/sys_attend_stat/sysAttendStat.do?method=list&fdType=6",
		text: "${ lfn:message('sys-attend:sysAttendMain.fdStatus.business') }",
		isNavCount:true
	},{
		url : "/sys/attend/sys_attend_stat/sysAttendStat.do?method=list&fdType=8",
		text: "${ lfn:message('sys-attend:sysAttendMain.fdStatus.overtime') }",
		isNavCount:true
	},
	{
		url : "/sys/attend/sys_attend_stat/sysAttendStat.do?method=list&fdType=9",
		text: "${ lfn:message('sys-attend:sysAttendMain.fdStatus.outgoing') }",
		isNavCount:true
	},
]
