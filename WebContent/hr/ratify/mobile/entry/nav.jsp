<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="/sys/ui/jsp/common.jsp"%>
[
	{ 
		url : "/hr/staff/hr_staff_entry/hrStaffEntry.do?method=list&q.fdStatus=1&q.j_path=%2FentryStatus1&orderby=fdLastModifiedTime&ordertype=up", 
		text : "<bean:message bundle='hr-ratify' key='mobile.hrStaffEntry.list'/>", 
		selected : true 
	},
]