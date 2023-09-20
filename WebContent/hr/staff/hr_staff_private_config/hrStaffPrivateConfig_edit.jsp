<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit">
	<template:replace name="content">
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
			<div style="margin-top:25px">
			<p class="txttitle">${lfn:message('hr-staff:hr.staff.tree.privacy.settings') }</p>
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td width=35% class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.brief" />
						</td>
						<td width="65%">
							<html:hidden property="value(isBriefPrivate)"/>
							<label>
							<input name="isBriefPrivate" type="checkbox" onclick="changeVal(this);"/>
							<bean:message bundle="hr-staff" key="hr.staff.btn.hide" />
							</label>
						</td>
					</tr>
					<tr>
						<td width=35% class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.project" />
						</td>
						<td width="65%">
							<html:hidden property="value(isProjectPrivate)"/>
							<label>
							<input name="isProjectPrivate" type="checkbox" onclick="changeVal(this);"/>
							<bean:message bundle="hr-staff" key="hr.staff.btn.hide" />
							</label>
						</td>
					</tr>
					<tr>
						<td width=35% class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.work" />
						</td>
						<td width="65%">
							<html:hidden property="value(isWorkPrivate)"/>
							<label>
							<input name="isWorkPrivate" type="checkbox" onclick="changeVal(this);"/>
							<bean:message bundle="hr-staff" key="hr.staff.btn.hide" />
							</label>
						</td>
					</tr>
					<tr>
						<td width=35% class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.education" />
						</td>
						<td width="65%">
							<html:hidden property="value(isEducationPrivate)"/>
							<label>
							<input name="isEducationPrivate" type="checkbox" onclick="changeVal(this);"/>
							<bean:message bundle="hr-staff" key="hr.staff.btn.hide" />
							</label>
						</td>
					</tr>
					<tr>
						<td width=35% class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.training" />
						</td>
						<td width="65%">
							<html:hidden property="value(isTrainingPrivate)"/>
							<label>
							<input name="isTrainingPrivate" type="checkbox" onclick="changeVal(this);"/>
							<bean:message bundle="hr-staff" key="hr.staff.btn.hide" />
							</label>
						</td>
					</tr>
					<tr>
						<td width=35% class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.bonusMalus" />
						</td>
						<td width="65%">
							<html:hidden property="value(isBonusPrivate)"/>
							<label>
							<input name="isBonusPrivate" type="checkbox" onclick="changeVal(this);"/>
							<bean:message bundle="hr-staff" key="hr.staff.btn.hide" />
							</label>
						</td>
					</tr>
				</table>
				<div style="margin-bottom: 10px;margin-top:25px">
					   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick=" Com_Submit(document.sysAppConfigForm, 'update');" order="1" ></ui:button>
				</div>
			</center>
			</div>
		    <html:hidden property="method_GET"/>
		</html:form>
		<script>
		$(document).ready(function (){
			init();
		});
		function init(){
			setVal("isBriefPrivate");
			setVal("isProjectPrivate");
			setVal("isWorkPrivate");
			setVal("isEducationPrivate");
			setVal("isTrainingPrivate");
			setVal("isBonusPrivate");
		}
		function setVal(name){
			var checkBoxEle=document.getElementsByName(name)[0];
			var eleVal=document.getElementsByName("value("+name+")")[0];
			if(eleVal.value=="1"){
				checkBoxEle.checked=true;
			}else {
				checkBoxEle.checked=false;
			}
		}
		
		function changeVal(obj){
			var checkBoxEle = document.getElementsByName(obj.name)[0];
			var eleVal = document.getElementsByName("value("+obj.name+")")[0];
			if(checkBoxEle.checked){
				eleVal.value="1";
			}else{
				eleVal.value="0";
			}
		}
		</script>
	</template:replace>
</template:include>
