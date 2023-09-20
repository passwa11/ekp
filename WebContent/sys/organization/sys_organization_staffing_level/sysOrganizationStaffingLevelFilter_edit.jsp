<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="head">
		<script>
			Com_IncludeFile("doclist.js|dialog.js", null, "js");
			
			function validate(){
				var table_obj = document.getElementById("TABLE_DocList");
				var input_objs = table_obj.getElementsByTagName("input");
				for(var i=0;i<input_objs.length;i++){
					input_obj = input_objs[i];
					//debugger;
					if(input_obj.value==''){
						alert(i + "不能为空");
						return false;
					}
				}
				return true;
			}
			
			function changeOrgStaffingLevelFilterEnable(){
				var table_obj = document.getElementById("tr_staffingLevelFilterSetting"),
					help_link = document.getElementById('helpLink');
				if($("input[name='isOrgStaffingLevelFilterEnable']").val() == 'true'){
					table_obj.style.display = "";
					help_link.style.display = '';
				}else{
					table_obj.style.display = "none";
					help_link.style.display = 'none';
				}
			}
			
			LUI.ready(function() {
				changeOrgStaffingLevelFilterEnable();
			});
			
			function checkNum(){
				if((parseInt($("input[name='orgStaffingLevelFilterSub']").val())>2147483647)){
					alert("级别不能大于2147483647");
					return false;
				}else{
					Com_Submit(document.forms['sysOrganizationStaffingLevelFilterForm'], 'update');
					}
				
				
				
			}
			

		</script>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-organization" key="sysOrganizationStaffingLevelFilter.setting" /></span>
		</h2>
		<html:form action="/sys/organization/sys_organization_staffing_level_filter/sysOrganizationStaffingLevelFilter.do">
			<div width="95%" style="width: 95%" align="center">
				<table align="left" style="margin-left: 20px;">
					<tr>
						<td>
							<ui:switch property="isOrgStaffingLevelFilterEnable" checked="${sysOrganizationStaffingLevelFilterForm.isOrgStaffingLevelFilterEnable}" onValueChange="changeOrgStaffingLevelFilterEnable();" enabledText="${lfn:message('sys-organization:sysOrganizationStaffingLevelFilter.enable')}" disabledText="${lfn:message('sys-organization:sysOrganizationStaffingLevelFilter.enable')}"></ui:switch>
						</td>
						<td>
							<a id='helpLink' href="${KMSS_Parameter_ContextPath}sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevelFilter_help.jsp" target="_blank" ><font color="red">${lfn:message('sys-organization:sysOrganization.help')}</font></a>
						</td>
					</tr>
				</table>
				<table id="tr_staffingLevelFilterSetting"  align="center" style="width: 95%;line-height:30px;">
					<tr>
						<td>
							<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevelFilter.orgStaffingLevelFilterSub.prefix"/>
							<xform:text property="orgStaffingLevelFilterSub" showStatus="edit" style="width:30px;" validators="digits min(0)" value="${sysOrganizationStaffingLevelFilterForm.orgStaffingLevelFilterSub }"></xform:text>
							<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevelFilter.orgStaffingLevelFilterSub.suffix"/>
						</td>
					</tr>
					<tr>
						<td>
							<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevelFilter.privilege.setting"/><sunbor:enums property="orgStaffingLevelFilterDirection" enumsType="sys_org_staffing_level_filter_direction" elementType="radio" />
						</td>
					</tr>
					<tr>
						<td>
							<br>
							<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevelFilter.setting.description"/>
						</td>
					</tr>
				</table>
			</div>	
			
			<table width="100%">
				<tr>
					<td>
						<center style="margin-top: 10px;">
						<kmss:auth requestURL="/sys/organization/sys_organization_staffing_level_filter/sysOrganizationStaffingLevelFilter.do?method=update">
							<!-- 保存 -->
							<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="checkNum()"></ui:button>
						</kmss:auth>
						</center>
					</td>
				</tr>
			</table>
		</html:form>
		<script language="JavaScript">
			$KMSSValidation(document.forms['sysOrganizationStaffingLevelFilterForm']);
		</script>
	</template:replace>
</template:include>
