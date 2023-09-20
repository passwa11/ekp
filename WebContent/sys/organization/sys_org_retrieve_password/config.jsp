<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script>
Com_IncludeFile("doclist.js|dialog.js");

function changeRetrievePasswordEnable(obj){
	var table_obj = document.getElementById("table_Setting");
	if(obj.checked){
		table_obj.style.display = "";
	}else{
		table_obj.style.display = "none";
	}
}


window.onload = function(){
	changeRetrievePasswordEnable(document.getElementById("isEnable"));
}
</script>
<html:form action="/sys/profile/passwordSecurityConfig.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/sys/profile/passwordSecurityConfig.do?method=update">
			<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.sysAppConfigForm, 'update');">
		</kmss:auth>
	</div>
	<div width="95%" style="width: 95%" align="center">
	
	<table  align="center" style="width: 95%;line-height:50px;">
	<tr>
	<td>
		<xform:checkbox htmlElementProperties="id='isEnable'" showStatus="edit" property="value(smsReceiveEnable)" value="${passwordSecurityConfig.smsReceiveEnable}" onValueChange="changeRetrievePasswordEnable(this)">
			<xform:simpleDataSource value="true" ><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.isEnable" /></xform:simpleDataSource>
		</xform:checkbox>
	</td>
	</tr>
	</table>
	<table id="table_Setting"  align="center" style="width: 95%;line-height:50px;">
	<tr>
	<td class="td_normal_title" width="15%"><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.reSentIntervalTime" /></td>
	<td>
		<xform:text property="value(reSentIntervalTime)" showStatus="edit" style="width:50px;" value="${passwordSecurityConfig.reSentIntervalTime }"></xform:text>${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.whetherRetrievePasswordFeature.timeIntervalForResendin.second')}
	</td>
	</tr>
	

	<tr>
	<td class="td_normal_title" width="15%"><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.codeEffectiveTime" /></td>
	<td>
		<xform:text property="value(codeEffectiveTime)" showStatus="edit" style="width:50px;" value="${passwordSecurityConfig.codeEffectiveTime }"></xform:text>${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.whetherRetrievePasswordFeature.verificationCodeValid.minute')}
	</td>
	</tr>
	
	<tr>
	<td class="td_normal_title" width="15%"><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.maxTimesOneDay" /></td>
	<td>
		<xform:text property="value(maxTimesOneDay)" showStatus="edit" style="width:50px;" value="${passwordSecurityConfig.maxTimesOneDay }"></xform:text>${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.maximumNumberOfTimesADayToSend.times')}
	</td>
	</tr>
	</table>
	
	<table id="table_mobileNo_Setting"  align="center" style="width: 95%;line-height:50px;">
	<tr>
	<td>
		<xform:radio property="value(mobileNoUpdateCheckEnable)" value="${passwordSecurityConfig.mobileNoUpdateCheckEnable}" showStatus="edit">
	 		<xform:simpleDataSource value="false">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.directlyModify')}</xform:simpleDataSource>
	 		<xform:simpleDataSource value="true">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.verifyPasswordModify')}</xform:simpleDataSource>
	 	</xform:radio>
	</td>
	</tr>
	
	</table>
	
	</div>	
		
		
	<input type="hidden" name="modelName" value="com.landray.kmss.sys.profile.model.PasswordSecurityConfig" />
</html:form>
<script language="JavaScript">
			$KMSSValidation();
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>