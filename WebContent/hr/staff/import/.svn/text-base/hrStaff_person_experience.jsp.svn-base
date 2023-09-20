<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple" spa="true">
	<template:replace name="body">
	<div style="width:100%">
	  <ui:tabpanel id="hrStaffExperience"  layout="sys.ui.tabpanel.list">
	   <c:if test="${'contract'==JsParam.experirnceKey}">
		 <ui:content title="${lfn:message('hr-staff:hrStaffPersonExperience.type.contract') }">
		 	 <ui:iframe id="hrStaffContract" src="${LUI_ContextPath}/hr/staff/hr_staff_person_experience/contract/"></ui:iframe>
		  </ui:content>
		 </c:if>
		  <c:if test="${'work'==JsParam.experirnceKey}">
		  <ui:content title="${lfn:message('hr-staff:hrStaffPersonExperience.type.work') }">
		 	 <ui:iframe id="hrStaffWork" src="${LUI_ContextPath}/hr/staff/hr_staff_person_experience/work/"></ui:iframe>
		  </ui:content>
		  </c:if>
		  <c:if test="${'education'==JsParam.experirnceKey}">
		 <ui:content title="${lfn:message('hr-staff:hrStaffPersonExperience.type.education') }">
		 	 <ui:iframe id="hrStaffEducation" src="${LUI_ContextPath}/hr/staff/hr_staff_person_experience/education/"></ui:iframe>
		  </ui:content>
		 </c:if>
		 <c:if test="${'training'==JsParam.experirnceKey}">
		 <ui:content title="${lfn:message('hr-staff:hrStaffPersonExperience.type.training') }">
		 	 <ui:iframe id="hrStaffTraining" src="${LUI_ContextPath}/hr/staff/hr_staff_person_experience/training/"></ui:iframe>
		  </ui:content>
		 </c:if>
		 <c:if test="${'qualification'==JsParam.experirnceKey}">
		 <ui:content title="${lfn:message('hr-staff:hrStaffPersonExperience.type.qualification') }">
		 	 <ui:iframe id="hrStaffQualification" src="${LUI_ContextPath}/hr/staff/hr_staff_person_experience/qualification/"></ui:iframe>
		  </ui:content>
		 </c:if>
		 <c:if test="${'bonusMalus'==JsParam.experirnceKey}">
		 <ui:content title="${lfn:message('hr-staff:hrStaffPersonExperience.type.bonusMalus') }">
		 	 <ui:iframe id="hrStaffBonusMalus" src="${LUI_ContextPath}/hr/staff/hr_staff_person_experience/bonusMalus/"></ui:iframe>
		  </ui:content>
		 </c:if> 
		</ui:tabpanel>
	  </div> 
	</template:replace>
</template:include>
