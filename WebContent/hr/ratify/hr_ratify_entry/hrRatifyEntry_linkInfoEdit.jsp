<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<table class="tb_normal" width=100%>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdEmail"/>
		</td>
		<td width="35%">
			<xform:text property="fdEmail"></xform:text>
		</td>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdMobileNo"/>
		</td>
		<td width="35%">
			<xform:text property="fdMobileNo" validators="phoneNumber uniqueMobileNo" required="true"></xform:text>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdEmergencyContact"/>
		</td>
		<td width="35%">
			<xform:text property="fdEmergencyContact"></xform:text>
		</td>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdEmergencyContactPhone"/>
		</td>
		<td width="35%">
			<xform:text property="fdEmergencyContactPhone" validators="phoneNumber"></xform:text>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdOtherContact"/>
		</td>
		<td colspan="3">
			<xform:text property="fdOtherContact"></xform:text>
		</td>
	</tr>
</table>