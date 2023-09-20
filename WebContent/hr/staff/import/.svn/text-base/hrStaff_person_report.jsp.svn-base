<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple" spa="true">
	<template:replace name="body">
	<div style="width:100%">
	  <ui:tabpanel id="hrStaffPersonReport"  layout="sys.ui.tabpanel.list">
	   <c:if test="${'reportStaffNum'==JsParam.type}">
		 <ui:content title="${lfn:message('hr-staff:hrStaffPersonReport.type.reportStaffNum') }">
		 	 <ui:iframe id="reportStaffNum" src="${LUI_ContextPath}/hr/staff/hr_staff_person_report/index.jsp?type=reportStaffNum"></ui:iframe>
		  </ui:content>
		 </c:if>
	   <c:if test="${'reportPersonnelMonthlyReportStaffEntryAndExit'==JsParam.type}">
		 <ui:content title="${lfn:message('hr-staff:hrStaffPersonReport.type.reportPersonnelMonthlyReportStaffEntryAndExit') }">
		 	 <ui:iframe id="reportPersonnelMonthlyReportStaffEntryAndExit" src="${LUI_ContextPath}/hr/staff/hr_staff_person_report/index.jsp?type=reportPersonnelMonthlyReportStaffEntryAndExit"></ui:iframe>
		  </ui:content>
		 </c:if>
		  <c:if test="${'reportAge'==JsParam.type}">
		  <ui:content title="${lfn:message('hr-staff:hrStaffPersonReport.type.reportAge') }">
		 	 <ui:iframe id="reportAge" src="${LUI_ContextPath}/hr/staff/hr_staff_person_report/index.jsp?type=reportAge"></ui:iframe>
		  </ui:content>
		  </c:if>
		  <c:if test="${'reportWorkTime'==JsParam.type}">
		 <ui:content title="${lfn:message('hr-staff:hrStaffPersonReport.type.reportWorkTime') }">
		 	 <ui:iframe id="reportWorkTime" src="${LUI_ContextPath}/hr/staff/hr_staff_person_report/index.jsp?type=reportWorkTime"></ui:iframe>
		  </ui:content>
		 </c:if>
		 <c:if test="${'reportEducation'==JsParam.type}">
		 <ui:content title="${lfn:message('hr-staff:hrStaffPersonReport.type.reportEducation') }">
		 	 <ui:iframe id="reportEducation" src="${LUI_ContextPath}/hr/staff/hr_staff_person_report/index.jsp?type=reportEducation"></ui:iframe>
		  </ui:content>
		 </c:if>
		 <c:if test="${'reportStaffingLevel'==JsParam.type}">
		 <ui:content title="${lfn:message('hr-staff:hrStaffPersonReport.type.reportStaffingLevel') }">
		 	 <ui:iframe id="reportStaffingLevel" src="${LUI_ContextPath}/hr/staff/hr_staff_person_report/index.jsp?type=reportStaffingLevel"></ui:iframe>
		  </ui:content>
		 </c:if>
		 <c:if test="${'reportStatus'==JsParam.type}">
		 <ui:content title="${lfn:message('hr-staff:hrStaffPersonReport.type.reportStatus') }">
		 	 <ui:iframe id="reportStatus" src="${LUI_ContextPath}/hr/staff/hr_staff_person_report/index.jsp?type=reportStatus"></ui:iframe>
		  </ui:content>
		 </c:if>
		  <c:if test="${'reportMarital'==JsParam.type}">
		 <ui:content title="${lfn:message('hr-staff:hrStaffPersonReport.type.reportMarital') }">
		 	 <ui:iframe id="reportMarital" src="${LUI_ContextPath}/hr/staff/hr_staff_person_report/index.jsp?type=reportMarital"></ui:iframe>
		  </ui:content>
		 </c:if> 
		</ui:tabpanel>
	  </div> 
	</template:replace>
</template:include>
