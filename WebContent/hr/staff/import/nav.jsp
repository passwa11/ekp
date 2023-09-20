<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%-- 人事档案  --%>
<ui:content title="${ lfn:message('hr-staff:hr.staff.nav.staff.manage')}" expand="true">
	<ul class='lui_list_nav_list'>
		<li class="${'overview' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a id="overview" href="javascript:void(0)" onclick="moduleAPI.hrStaff.switchMenuItem(this,'overview');">${ lfn:message('hr-staff:hr.staff.nav.overview')}</a></li>
		<kmss:authShow roles="ROLE_HRSTAFF_ATTENDANCE">
		<li class="${'attendance' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_attendance_manage.jsp');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hr.staff.nav.attendance.management')}</a></li>
		</kmss:authShow>
		<kmss:authShow roles="ROLE_HRSTAFF_EMOLUMENT">
		<li class="${'benefits' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_emolument_welfare.jsp');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hr.staff.nav.benefits')}</a></li>
		</kmss:authShow>
		<kmss:authShow roles="ROLE_HRSTAFF_PAYMENT">
		<li class="${'payrollIssuance' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_payroll_issuance.jsp');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hr.staff.nav.payroll')}</a></li>
		</kmss:authShow>
	</ul>
</ui:content>

<%-- 个人经历  --%>
<kmss:authShow roles="ROLE_HRSTAFF_EXPERIENCE">
<ui:content title="${ lfn:message('hr-staff:hr.staff.nav.staff.file.info')}" expand="false">
	<ul class='lui_list_nav_list'>
	<!--分成在职，离职  -->
		<kmss:authShow roles="ROLE_HRSTAFF_READALL">
		<li class="${'personInfo' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_person_info.jsp?personKey=in');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hr.staff.nav.employee.information.in')}</a></li>
		</kmss:authShow>
		<kmss:authShow roles="ROLE_HRSTAFF_READALL">
		<li class="${'personInfo_quit' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_person_info.jsp?personKey=quit');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hr.staff.nav.employee.information.quit')}</a></li>
		</kmss:authShow>
		<li class="${'contract' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_person_experience.jsp?experirnceKey=contract');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hrStaffPersonExperience.type.contract')}</a></li>
		<li class="${'work' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_person_experience.jsp?experirnceKey=work');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hrStaffPersonExperience.type.work')}</a></li>
		<li class="${'education' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_person_experience.jsp?experirnceKey=education');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hrStaffPersonExperience.type.education')}</a></li>
		<li class="${'training' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_person_experience.jsp?experirnceKey=training');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hrStaffPersonExperience.type.training')}</a></li>
		<li class="${'qualification' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_person_experience.jsp?experirnceKey=qualification');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hrStaffPersonExperience.type.qualification')}</a></li>
		<li class="${'bonusMalus' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_person_experience.jsp?experirnceKey=bonusMalus');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hrStaffPersonExperience.type.bonusMalus')}</a></li>
	</ul>
</ui:content>
</kmss:authShow>

<%-- 统计报表  --%>
<ui:content title="${ lfn:message('hr-staff:hr.staff.nav.statistical.report')}" expand="false">
	<ul class='lui_list_nav_list'>
		<li class="${'reportStaffNum' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_person_report.jsp?type=reportStaffNum');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hrStaffPersonReport.type.reportStaffNum')}</a></li>
		<li class="${'reportPersonnelMonthlyReportStaffEntryAndExit' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_person_report.jsp?type=reportPersonnelMonthlyReportStaffEntryAndExit');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hrStaffPersonReport.type.reportPersonnelMonthlyReportStaffEntryAndExit')}</a></li>
		<li class="${'reportAge' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_person_report.jsp?type=reportAge');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hrStaffPersonReport.type.reportAge')}</a></li>
		<li class="${'reportWorkTime' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_person_report.jsp?type=reportWorkTime');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hrStaffPersonReport.type.reportWorkTime')}</a></li>
		<li class="${'reportEducation' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_person_report.jsp?type=reportEducation');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hrStaffPersonReport.type.reportEducation')}</a></li>
		<li class="${'reportStaffingLevel' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_person_report.jsp?type=reportStaffingLevel');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hrStaffPersonReport.type.reportStaffingLevel')}</a></li>
		<li class="${'reportStatus' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_person_report.jsp?type=reportStatus');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hrStaffPersonReport.type.reportStatus')}</a></li>
		<li class="${'reportMarital' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"><a href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_person_report.jsp?type=reportMarital');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hrStaffPersonReport.type.reportMarital')}</a></li>
	</ul>
</ui:content>

<%-- 提醒预警  --%>
<kmss:authShow roles="ROLE_HRSTAFF_WARNING">
<ui:content title="${ lfn:message('hr-staff:hr.staff.nav.alert.warning')}" expand="false">
	<ul class='lui_list_nav_list'>
		<li class="${'warningBirthday' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}"  id="lastBirthdayShowParent"><a id="lastBirthdayShow" href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_alert_warning.jsp?alert=lastBirthdayShow');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hr.staff.nav.last.birthday')}</a></li>
		<li class="${'warningContract' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}" id="contractExpirationShowParent"><a id="contractExpirationShow" href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_alert_warning.jsp?alert=contractExpirationShow');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hr.staff.nav.contract.expiration')}</a></li>
		<li class="${'warningTrial' eq HtmlParam.type ? 'lui_list_nav_selected' : ''}" id="rialExpirationShowParent"><a id="rialExpirationShow" href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/hr/staff/import/hrStaff_alert_warning.jsp?alert=trialExpirationShow');resetMenuNavStyle(this);">${ lfn:message('hr-staff:hr.staff.nav.trial.expiration')}</a></li>
	</ul>
</ui:content>
</kmss:authShow>

<%-- 其他操作  --%>
<kmss:authShow roles="ROLE_HRSTAFF_BACKGROUND,ROLE_HRSTAFF_PAYMENT">
<ui:content title="${ lfn:message('hr-staff:hr.staff.nav.other.operations')}" expand="false">
	<ul class='lui_list_nav_list'>
		<kmss:authShow roles="ROLE_HRSTAFF_BACKGROUND">
		<li><a href="${LUI_ContextPath }/sys/profile/index.jsp#app/ekp/hr/staff" target="_blank">${ lfn:message('hr-staff:hr.staff.nav.bg.management')}</a></li>
		</kmss:authShow>
	</ul>
</ui:content>
</kmss:authShow>

<script type="text/javascript">
	function _openUrl(url) {
		window.open("${LUI_ContextPath}" + url, "_self");
	}
	window.openSearch=function(url){
		LUI.pageOpen(url,'_rIframe');
	}
	window.openHref=function(href){
		window.location.href=href;
	}
</script>
