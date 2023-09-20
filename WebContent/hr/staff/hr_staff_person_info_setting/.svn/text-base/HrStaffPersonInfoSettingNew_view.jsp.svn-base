<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
<div id="optBarDiv">
			<input type="button"
				value="<bean:message key="button.edit"/>"
				onclick="Com_OpenWindow('hrStaffInfoSetNew.do?method=edit&fdId=${JsParam.fdId}&type=${fdType}','_self');">
		
			<input type="button"
				value="<bean:message key="button.delete"/>"
				onclick="if(!confirmDelete())return;Com_OpenWindow('hrStaffInfoSetNew.do?method=delete&fdId=${JsParam.fdId}','_self');">
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
	<c:if test="${ 'fdStaffType' eq hrStaffPersonInfoSettingNewForm.fdType }">
	<p class="txttitle"><bean:message  bundle="hr-staff" key="table.hrStaffCategory"/></p>
</c:if>
<c:if test="${ 'fdHealth'  eq hrStaffPersonInfoSettingNewForm.fdType }">
	<p class="txttitle"><bean:message bundle="hr-staff" key="table.hrStaffHealthyStatus"/></p>
</c:if>
<c:if test="${ 'fdNation' eq hrStaffPersonInfoSettingNewForm.fdType }">
	<p class="txttitle"><bean:message bundle="hr-staff" key="table.hrStaffNation"/></p>
</c:if>
<c:if test="${ 'fdPoliticalLandscape' eq hrStaffPersonInfoSettingNewForm.fdType }">
	<p class="txttitle"><bean:message bundle="hr-staff" key="table.hrStaffPoliticalStatus"/></p>
</c:if>
<c:if test="${ 'fdHighestEducation' eq hrStaffPersonInfoSettingNewForm.fdType }">
	<p class="txttitle"><bean:message bundle="hr-staff" key="table.hrStaffEducation"/></p>
</c:if>
<c:if test="${ 'fdHighestDegree' eq hrStaffPersonInfoSettingNewForm.fdType }">
	<p class="txttitle"><bean:message bundle="hr-staff" key="table.hrStaffDegree"/></p>
</c:if>
<c:if test="${ 'fdMaritalStatus' eq hrStaffPersonInfoSettingNewForm.fdType }">
	<p class="txttitle"><bean:message bundle="hr-staff" key="table.hrStaffMaritalStatus"/></p>
</c:if>
<c:if test="${ 'fdAttendance' eq hrStaffPersonInfoSettingNewForm.fdType }">
	<p class="txttitle"><bean:message bundle="hr-staff" key="table.hrStaffAttendance"/></p>
</c:if>
<c:if test="${ 'fdNatureWork' eq hrStaffPersonInfoSettingNewForm.fdType }">
	<p class="txttitle"><bean:message bundle="hr-staff" key="table.hrStaffNatureWork"/></p>
</c:if>
<c:if test="${ 'fdWorkAddress' eq hrStaffPersonInfoSettingNewForm.fdType }">
	<p class="txttitle"><bean:message bundle="hr-staff" key="table.hrStaffWorkAddress"/></p>
</c:if>
<c:if test="${ 'fdBonusMalusType' eq hrStaffPersonInfoSettingNewForm.fdType }">
	<p class="txttitle"><bean:message bundle="hr-staff" key="table.hrStaffBonusMalusType"/></p>
</c:if>
<c:if test="${ 'fdLeaveReason' eq hrStaffPersonInfoSettingNewForm.fdType }">
	<p class="txttitle"><bean:message bundle="hr-staff" key="table.hrStaffLeaveReason"/></p>
</c:if>
<center>
<table class="tb_normal" width=95%>
		<html:hidden name="hrStaffPersonInfoSettingNewForm" property="fdId"/>
		<html:hidden name="hrStaffPersonInfoSettingNewForm" property="fdType"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="hr-staff" key="hrStaff.fdOrder"/>
		</td><td width=35%>
			<c:out value="${hrStaffPersonInfoSettingNewForm.fdOrder}" />
		</td>
			<td class="td_normal_title" width=15%>
			<bean:message  bundle="hr-staff" key="hrStaff.fdName"/>
		</td><td width=35%>
			<c:out value="${hrStaffPersonInfoSettingNewForm.fdName}" />
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>