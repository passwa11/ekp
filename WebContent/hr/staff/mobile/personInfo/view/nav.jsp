<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
[ 
    {
		url : "/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=list&vaIndex=1&personId=${ param.personInfoId}",
		text: "<bean:message bundle='hr-staff' key='table.hrStaffAttendanceManageDetailed'/>",
		selected:true
	},{
		url : "/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=list&vaIndex=2&personId=${ param.personInfoId}",
		text: "<bean:message bundle='hr-staff' key='hrStaffAttendanceManage.paidHoliday'/>",
	},
	{ 
		url : "/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=list&vaIndex=3&personId=${ param.personInfoId}&fdType=2", 
		text: "<bean:message bundle='hr-staff' key='table.hrStaffAttendanceManageDetailed.overtime'/>",
	}
]
