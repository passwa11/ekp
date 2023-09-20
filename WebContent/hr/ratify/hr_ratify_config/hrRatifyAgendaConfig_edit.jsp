<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="sys-organization" key="sysOrgConfig" /></template:replace>
	<template:replace name="head">
		<script>
			Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|dialog.js", null, "js");
		</script>
		<script>$KMSSValidation();</script>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do" >
			<div style="margin-top:25px">
			<p class="configtitle">
				同步设置
			</p>
			<center>
			<table class="tb_normal" width=90%>
				<tr>
					<td class="td_normal_title" width=15%>
						合同签订/变更/解除
					</td><td colspan=3>
						<ui:switch property="value(fdHrStaffCont)" 
							enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
							disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						<br/>
						说明：流程通过后，自动写入（或更新）人事档案个人经历中的合同信息
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						转正申请
					</td><td colspan=3>
						<ui:switch property="value(fdHrStaffStatus)" 
							enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
							disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						<br/>
						说明：流程通过后，到了转正日期自动更新人事档案员工状态为正式
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						调薪申请
					</td><td colspan=3>
						<ui:switch property="value(fdHrStaffSalary)" 
							enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
							disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						<br/>
						说明：流程通过后，自动写入人事档案薪酬福利中的工资调整记录
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						调岗申请
					</td><td colspan=3>
						<ui:switch property="value(fdUpdateSysOrg)" 
							enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
							disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						<br/>
						说明：流程通过后，到了调岗日期自动更新系统组织中心调岗人员的部门、岗位和职级信息
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						离职/解聘/退休申请
					</td><td colspan=3>
						<table>
							<tr>
								<td>
									<ui:switch property="value(fdFalseSysOrg)" 
										enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
										disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									<br/>
									说明：流程通过后，到了离职日期将系统组织中心该员工置为无效
								</td>
							</tr>
							<tr>
								<td>
									<ui:switch property="value(fdFalseHrStaff)" 
										enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
										disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									<br/>
									说明：流程通过后，到了离职日期自动更新人事档案员工状态为离职、解聘或退休
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<%-- <tr>
					<td class="td_normal_title" width=15%>
						离职确认
					</td>
					<td>
						<ui:switch property="value(fdLeaveManage)"
							enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
							disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						<br/>
						说明：开启后离职确认将在员工关系中进行确认，离职流程审批完成后不会改变员工状态。
					</td>
				</tr> --%>
				<tr>
					<td class="td_normal_title" width=15%>
						返聘申请
					</td><td colspan=3>
						<ui:switch property="value(fdTrueSysOrg)" 
							enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
							disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						<br/>
						说明：流程通过后，自动在组织中心将返聘人员的系统帐号重新开通为有效并将人事档案信息员工状态置为正式
					</td>
				</tr>
			</table>
			<div style="margin-bottom: 10px;margin-top:25px">
				   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');" order="1"></ui:button>
			</div>
			</center>
			</div>
			<html:hidden property="method_GET"/>
			<html:hidden property="modelName" value="com.landray.kmss.hr.ratify.model.HrRatifyAgendaConfig"/>
		</html:form>
	</template:replace>
</template:include>