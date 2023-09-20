<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple" spa="true">
	<template:replace name="body">
	<div style="width:100%">
	  <ui:tabpanel id="hrStaffAttendance"  layout="sys.ui.tabpanel.list">
		 <ui:content title="${lfn:message('hr-staff:hr.staff.nav.attendance.management') }">
		 	 <ui:iframe id="hrStaffAttendanceManage" src="${LUI_ContextPath}/hr/staff/hr_staff_attendance_manage/"></ui:iframe>
		  </ui:content>
		</ui:tabpanel>
	  </div> 
	</template:replace>
</template:include>
