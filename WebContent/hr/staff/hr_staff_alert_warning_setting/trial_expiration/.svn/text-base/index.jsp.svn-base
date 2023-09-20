<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message key="hr.staff.tree.info.setting" bundle="hr-staff"/></template:replace>
	<template:replace name="content">
	
		<h2 align="center" style="margin:10px 0">
			<span class="profile_config_title"><bean:message key="hr.staff.tree.trial.expiration.reminder" bundle="hr-staff"/></span>
		</h2>
			<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do" onsubmit="return validateAppConfigForm(this);">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" colspan="2">
							<b><label>${lfn:message('hr-staff:hr.staff.tree.trial.expiration.reminder')}</label></b>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">${lfn:message('hr-staff:hr.staff.enable')}</td>
						<td >
						   	<ui:switch property="value(staffReminder)"  enabledText="${lfn:message('hr-staff:hr.staff.enable.open')}" disabledText="${lfn:message('hr-staff:hr.staff.enable.close')}"  onValueChange="change();" ></ui:switch>
						</td>
					</tr>
					<tr id="tr1">
					   <td class="td_normal_title" width="15%">${lfn:message('hr-staff:hr.staff.cycle.reminder')}</td>
					   <td >   
						   <xform:select property="value(cycleReminder)"  style="width: 100px" showStatus="edit" showPleaseSelect="false">
								<xform:simpleDataSource value="week"><bean:message bundle="hr-staff" key="hr.staff.week" /></xform:simpleDataSource>
								<xform:simpleDataSource value="month"><bean:message bundle="hr-staff" key="hr.staff.month" /></xform:simpleDataSource>
								<xform:simpleDataSource value="twoMonth"><bean:message bundle="hr-staff" key="hr.staff.twoMonth" /></xform:simpleDataSource>
								<xform:simpleDataSource value="quarter"><bean:message bundle="hr-staff" key="hr.staff.quarter" /></xform:simpleDataSource>
						   </xform:select>
						   <div>
							   <bean:message bundle="hr-staff" key="hr.staff.setting.typeInfo0"/><br>
							   <bean:message bundle="hr-staff" key="hr.staff.setting.typeInfo1"/><br>
							   <bean:message bundle="hr-staff" key="hr.staff.setting.typeInfo2"/><br>
							   <bean:message bundle="hr-staff" key="hr.staff.setting.typeInfo3"/><br>
							   <bean:message bundle="hr-staff" key="hr.staff.setting.typeInfo4"/>
						   </div>
						</td>
					</tr>
					<tr id="tr2">
					   		<td class="td_normal_title" width="15%">${lfn:message('hr-staff:hr.staff.person.reminder')}</td>
						   	<td >
						   	<xform:address style="width:100%;height:80px" textarea="true" showStatus="edit"  propertyId="value(personReminderId)" propertyName="value(personReminderName)" orgType="ORG_TYPE_PERSON|ORG_TYPE_DEPT|ORG_TYPE_POST" mulSelect="true"></xform:address>
							</td>
					</tr>

					<tr>
						<td class="td_normal_title" width="15%">${lfn:message('hr-staff:hr.staff.person.right')}</td>
						<td >
							<ui:switch  property="value(cerifyAuthorization)"  enabledText="${lfn:message('hr-staff:hr.staff.enable.open')}" disabledText="${lfn:message('hr-staff:hr.staff.enable.close')}"   ></ui:switch>
						</td>
					</tr>
				</table>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.hr.staff.model.HrStaffAlertWarningTrial" />
			
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			</center>
			
			</html:form>
		<script type="text/javascript">
		function validateAppConfigForm(thisObj){
				return true;
			}

		LUI.ready(function() {
			var x=$("input[name='value(staffReminder)']").val();
			if(x=="true"){
				$("#tr1").show();
				$("#tr2").show();
			}else{
				$("#tr1").hide();
				$("#tr2").hide();
			}
			});
		function change(){
			$("#tr1").toggle();
			$("#tr2").toggle();
		}
		</script>
	</template:replace>
</template:include>
