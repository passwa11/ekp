<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple" spa="true">
	<template:replace name="body">
	<div style="width:100%">
	  <ui:tabpanel id="hrStaffAlertWarning"  layout="sys.ui.tabpanel.list">
	   <c:if test="${'lastBirthdayShow'==JsParam.alert}">
		 <ui:content title="${lfn:message('hr-staff:hr.staff.nav.last.birthday') }">
		 	 <ui:iframe id="lastBirthdayShow" src="${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=lastBirthdayShow"></ui:iframe>
		  </ui:content>
		 </c:if>
		  <c:if test="${'contractExpirationShow'==JsParam.alert}">
		  <ui:content title="${lfn:message('hr-staff:hr.staff.nav.contract.expiration') }">
		 	 <ui:iframe id="contractExpirationShow" src="${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=contractExpirationShow"></ui:iframe>
		  </ui:content>
		  </c:if>
		  <c:if test="${'trialExpirationShow'==JsParam.alert}">
		  <ui:content title="${lfn:message('hr-staff:hr.staff.nav.trial.expiration') }">
		 	 <ui:iframe id="trialExpirationShow" src="${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=trialExpirationShow"></ui:iframe>
		  </ui:content>
		  </c:if> 
		</ui:tabpanel>
	  </div> 
	</template:replace>
</template:include>
