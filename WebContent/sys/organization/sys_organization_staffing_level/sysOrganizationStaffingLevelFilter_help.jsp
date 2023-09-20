<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/watermarkPcCommon.jsp" %>
<%@ page import="com.landray.kmss.sys.language.utils.SysLangUtil,java.lang.String" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message bundle="sys-organization"
		key="sysOrganizationStaffingLevelFilter.usehelp" /></title>
<style type="text/css">
body {
	padding-left: 50px;
	font-size: 14px;
}

p {
	margin-top: 0px;
	line-height: 32px;
}

.number {
	margin: 0 5px;
	color: red;
}

.text {
	color: #4372C7;
}

.secondLine {
	margin-left: 42px;
}

.img {
	display: block;
	margin: 5px;
	width: 480px;
	height: 350px;
}

.warning {
	color: red;
}
</style>
</head>
<body>
	<h2>
		<bean:message bundle="sys-organization"
			key="sysOrganizationStaffingLevelFilter.usehelp.setting" />
	</h2>
	<p>
		<span class='warning'><bean:message bundle="sys-organization"
				key="sysOrganizationStaffingLevelFilter.usehelp.describe1" /></span>
		<bean:message bundle="sys-organization"
			key="sysOrganizationStaffingLevelFilter.usehelp.describe2"
			/><span class='number'>5</span>
			<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevelFilter.usehelp.describe3"/>
			<br/>
			<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevelFilter.usehelp.forexample"/>
			<span class='firstLine'><span class='text'><bean:message bundle="sys-organization" key="sysOrganizationStaffingLevelFilter.usehelp.describe4"/></span>
			<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevelFilter.usehelp.describe5"/>
			<span class='text'><bean:message bundle="sys-organization" key="sysOrganizationStaffingLevelFilter.usehelp.describe6"/></span>
			<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevelFilter.usehelp.describe7"/>
			</span>
			<br />
			<span class='secondLine'> <span class='text'><bean:message
						bundle="sys-organization"
						key="sysOrganizationStaffingLevelFilter.usehelp.describe8" /></span> <bean:message
					bundle="sys-organization"
					key="sysOrganizationStaffingLevelFilter.usehelp.describe5" /><span
				class='text'><bean:message bundle="sys-organization"
						key="sysOrganizationStaffingLevelFilter.usehelp.describe9" /></span> <bean:message
					bundle="sys-organization"
					key="sysOrganizationStaffingLevelFilter.usehelp.describe7" /></span>
			<br />
			<%
			String currentLocaleCountry = SysLangUtil.getCurrentLocaleCountry();
			if(currentLocaleCountry != null && currentLocaleCountry.equals("US")){
				%>
				<img class='img'
				src="images/sysOrganizationStaffingLevelFilter_help_English.png" />
				<% 
			}
			if(currentLocaleCountry != null && currentLocaleCountry.equals("CN")){
				%>
				<img class='img'
				src="images/sysOrganizationStaffingLevelFilter_help.png" />
				<% 
			}
			%>
			<span class='warning'><bean:message bundle="sys-organization"
					key="sysOrganizationStaffingLevelFilter.usehelp.describe10" /></span>
			<bean:message bundle="sys-organization"
				key="sysOrganizationStaffingLevelFilter.usehelp.describe2" />
			<span class='number'>5</span>
			<bean:message bundle="sys-organization"
				key="sysOrganizationStaffingLevelFilter.usehelp.describe3" />
			<br />
			<bean:message bundle="sys-organization"
				key="sysOrganizationStaffingLevelFilter.usehelp.forexample" />
			<span class='firstLine'><span class='text'><bean:message
						bundle="sys-organization"
						key="sysOrganizationStaffingLevelFilter.usehelp.describe11" /></span>
			<bean:message bundle="sys-organization"
					key="sysOrganizationStaffingLevelFilter.usehelp.describe5" /><span
				class='text'><bean:message bundle="sys-organization"
						key="sysOrganizationStaffingLevelFilter.usehelp.describe12" /></span>
			<bean:message bundle="sys-organization"
					key="sysOrganizationStaffingLevelFilter.usehelp.describe7" /></span>
			<br />
			<span class='secondLine'><span class='text'><bean:message
						bundle="sys-organization"
						key="sysOrganizationStaffingLevelFilter.usehelp.describe13" /></span>
			<bean:message bundle="sys-organization"
					key="sysOrganizationStaffingLevelFilter.usehelp.describe5" /><span
				class='text'><bean:message bundle="sys-organization"
						key="sysOrganizationStaffingLevelFilter.usehelp.describe14" /></span>
			<bean:message bundle="sys-organization"
					key="sysOrganizationStaffingLevelFilter.usehelp.describe7" /></span>
			<br />
			<%
			if(currentLocaleCountry != null && currentLocaleCountry.equals("US")){
				%>
				<img class='img'
				src="images/sysOrganizationStaffingLevelFilter_help2_English.png" />
				<% 
			}
			if(currentLocaleCountry != null && currentLocaleCountry.equals("CN")){
				%>
				<img class='img'
				src="images/sysOrganizationStaffingLevelFilter_help2.png" />
				<% 
			}
			%>
			
			<span class='warning'><bean:message bundle="sys-organization"
					key="sysOrganizationStaffingLevelFilter.usehelp.notice" /></span>
	</p>
	<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
</body>
</html>