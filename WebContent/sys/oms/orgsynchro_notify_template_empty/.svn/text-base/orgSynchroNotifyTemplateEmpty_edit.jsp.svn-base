<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="sys-organization" key="sysOrgConfig" /></template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message  bundle="sys-oms-notify" key="table.orgSynchroNotifyTemplateEmpty"/></span>
		</h2>

		<html:form action="/sys/oms/orgsynchro_notify_template_empty/orgSynchroNotifyTemplateEmpty.do">
			<center>
			<html:hidden property="fdId"/>
			<html:hidden property="method_GET"/>
			<table class="tb_normal" width=95% style="border: none;">
			  <tr style="border: none;"><td style="border: none;"><table class="tb_normal"  width=100%>
				<tr class="tr_normal_title">
					<td colspan="4">
						<bean:message  bundle="sys-oms-notify" key="orgSynchroNotifyTemplateEmpty.setting.message"/>
					</td>
				</tr>
				<c:import url="/sys/notify/include/sysNotifySetting_msg_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="orgSynchroNotifyTemplateEmptyForm" />
					<c:param name="fdKey" value="orgSynchroMessageSetting" />
				</c:import>
				</table>
				<div align="center">
				${lfn:message('sys-oms-notify:orgSynchroNotifyTemplateEmpty.setting.message')}：${lfn:message('sys-oms-notify:orgSynchro.OMSError')}<br>
				<br>
				</div>
				</td></tr>
				
				<kmss:ifModuleExist path="/hr/organization/">
				<tr style="border: none;"><td style="border: none;"><table class="tb_normal"  width=100%>
					<tr class="tr_normal_title">
						<td colspan="4">
							<bean:message  bundle="sys-oms-notify" key="createAccountNotifyTemplateEmpty.setting.message"/>
						</td>
					</tr>
					<c:import url="/sys/notify/include/sysNotifySetting_msg_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="orgSynchroNotifyTemplateEmptyForm" />
						<c:param name="fdKey" value="createAccountMessageSetting" />
						<c:param name="replaceText" value="sys-oms-notify:orgSynchroNotifyTemplateEmpty.fdNotifyPersonName" />
					</c:import>
				</table>
				<div align="center">
				${lfn:message('sys-oms-notify:createAccountNotifyTemplateEmpty.setting.message')}：${lfn:message('sys-oms-notify:orgSynchro.HrSynchro.OpenAccount')}<br>
				<br>	
				</div>	
						</td>
						</tr>
				
				<tr style="border: none;"><td style="border: none;"><table class="tb_normal"  width=100%>
					<tr class="tr_normal_title">
						<td colspan="4">
							<bean:message  bundle="sys-oms-notify" key="deleteAccountNotifyTemplateEmpty.setting.message"/>
						</td>
					</tr>
					<c:import url="/sys/notify/include/sysNotifySetting_msg_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="orgSynchroNotifyTemplateEmptyForm" />
						<c:param name="fdKey" value="deleteAccountMessageSetting" />
						<c:param name="replaceText" value="sys-oms-notify:orgSynchroNotifyTemplateEmpty.fdNotifyPersonName" />
					</c:import>
				</table>
				<div align="center">
				${lfn:message('sys-oms-notify:deleteAccountNotifyTemplateEmpty.setting.message')}：${lfn:message('sys-oms-notify:orgSynchro.HrSynchro.DeleteAccount')}
				</div>		</td></tr>
				</kmss:ifModuleExist>
			</table>
			</center>
			
			<center style="margin-top: 10px;">
				<!-- 保存 -->
				<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.orgSynchroNotifyTemplateEmptyForm, 'update');"></ui:button>
			</center>
		</html:form>
	</template:replace>
</template:include>
