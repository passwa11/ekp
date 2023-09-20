<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple" spa="true">
	<template:replace name="body">
	<div style="width:100%">
	  <ui:tabpanel id="hrStaffPersonInfo"  layout="sys.ui.tabpanel.list">
	   <c:if test="${'in'==JsParam.personKey}">
		 <ui:content title="${lfn:message('hr-staff:hr.staff.nav.employee.information.in') }">
		 	 <ui:iframe id="hrStaffPersonIn" src="${LUI_ContextPath}/hr/staff/hr_staff_person_info/"></ui:iframe>
		  </ui:content>
		 </c:if>
		  <c:if test="${'quit'==JsParam.personKey}">
		  <ui:content title="${lfn:message('hr-staff:hr.staff.nav.employee.information.quit') }">
		 	 <ui:iframe id="hrStaffPersonQuit" src="${LUI_ContextPath}/hr/staff/hr_staff_person_info/index_quit.jsp"></ui:iframe>
		  </ui:content>
		  </c:if> 
		</ui:tabpanel>
	  </div> 
	</template:replace>
</template:include>
