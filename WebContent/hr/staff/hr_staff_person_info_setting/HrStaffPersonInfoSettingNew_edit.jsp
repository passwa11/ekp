<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>	
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<html:form action="/hr/staff/hr_staff_person_info_setting/hrStaffInfoSetNew.do" > 
<html:hidden property="method_GET"/> 
<div id="optBarDiv">
	<c:if test="${hrStaffPersonInfoSettingNewForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.hrStaffPersonInfoSettingNewForm, 'update');">
	</c:if>
	<c:if test="${hrStaffPersonInfoSettingNewForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.hrStaffPersonInfoSettingNewForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.hrStaffPersonInfoSettingNewForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
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
		<html:hidden property="fdId"/> 
		<html:hidden property="fdType" value="${fdType}"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="hr-staff" key="hrStaff.fdOrder"/>
		</td><td width=35%>
			<xform:text	property="fdOrder" style="width:90%;" />
		</td>
			<td class="td_normal_title" width=15%>
			<bean:message  bundle="hr-staff" key="hrStaff.fdName"/>
		</td><td width=35%>
			<xform:text	property="fdName" validators="uniqueFdName" style="width:90%;"  />
		</td>
	</tr>
</table>
</center>
</html:form>

<script>
var _validation = $KMSSValidation(document.forms['hrStaffPersonInfoSettingNewForm']);
var FdNameValidators = {
		'uniqueFdName': {
			error : "名称已使用,请重新输入!",
			test :  function(value){
					var fdId = document.getElementsByName("fdId")[0].value;
					var fdType = document.getElementsByName("fdType")[0].value
					var result = fdNameCheckUnique("hrStaffPersonInfoSetNewService",value,fdId,fdType);
					if (!result) 
						return false;
					return true;
			}
		}
};

_validation.addValidators(FdNameValidators);
	

function fdNameCheckUnique(bean,fdName,fdId,fdType) {
	var	url=encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=" 
			+ bean + "&fdName=" + fdName+"&fdId="+fdId+"&fdType="+fdType);
	var xmlHttpRequest;
	if (window.XMLHttpRequest) { // Non-IE browsers
		xmlHttpRequest = new XMLHttpRequest();
	} else if (window.ActiveXObject) { // IE
		try {
			xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (othermicrosoft) {
			try {
				xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (failed) {
				xmlHttpRequest = false;
			}
		}
	}
	if (xmlHttpRequest) {
		xmlHttpRequest.open("GET", url, false);
		xmlHttpRequest.send();
		var result = xmlHttpRequest.responseText.replace(/\s/g, "").replace(/;/g, "\n");
		if (result != "") {
			return false;
		}
	}
	return true;
}
</script>

<%@ include file="/resource/jsp/edit_down.jsp"%>