<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple" spa="true">
	<template:replace name="body">
	<div style="width:100%">
	  <ui:tabpanel id="hrStaffPayroll"  layout="sys.ui.tabpanel.list">
		 <ui:content title="${lfn:message('hr-staff:hr.staff.nav.payroll') }">
		 	 <ui:iframe id="hrStaffPayrollIssuance" src="${LUI_ContextPath}/hr/staff/hr_staff_payroll_issuance/index.jsp?type=payrollIssuance"></ui:iframe>
		  </ui:content>
		</ui:tabpanel>
	  </div> 
	</template:replace>
</template:include>
