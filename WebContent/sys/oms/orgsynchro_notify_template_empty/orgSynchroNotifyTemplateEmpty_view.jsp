<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/oms/orgsynchro_notify_template_empty/orgSynchroNotifyTemplateEmpty.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('orgSynchroNotifyTemplateEmpty.do?method=edit&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message  bundle="sys-oms-notify" key="table.orgSynchroNotifyTemplateEmpty"/></p>
<center>
<table class="tb_normal" width=95%>
    			  <tr class="tr_normal_title">
			        <td>
			                        ${lfn:message('sys-oms-notify:button.save')}
			        </td>
					<td colspan="3">
					    <div style="text-align: left">
						1）${lfn:message('sys-oms-notify:orgSynchroNotifyTemplateEmpty.setting.message')}：${lfn:message('sys-oms-notify:orgSynchro.OMSError')}<br>
						<kmss:ifModuleExist path="/hr/">
						2）${lfn:message('sys-oms-notify:createAccountNotifyTemplateEmpty.setting.message')}：${lfn:message('sys-oms-notify:orgSynchro.HrSynchro.OpenAccount')}<br>
						3）${lfn:message('sys-oms-notify:deleteAccountNotifyTemplateEmpty.setting.message')}：${lfn:message('sys-oms-notify:orgSynchro.HrSynchro.DeleteAccount')}
						</kmss:ifModuleExist>
						</div>
					</td>
				</tr>
	<tr class="tr_normal_title">
		<td colspan="4">
			<bean:message  bundle="sys-oms-notify" key="orgSynchroNotifyTemplateEmpty.setting.message"/>
		</td>
	</tr>
	<c:import url="/sys/notify/include/sysNotifySetting_msg_view.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="orgSynchroNotifyTemplateEmptyForm" />
		<c:param name="fdKey" value="orgSynchroMessageSetting" />
	</c:import>
<kmss:ifModuleExist path="/hr/">
	<tr class="tr_normal_title">
		<td colspan="4">
			<bean:message  bundle="sys-oms-notify" key="createAccountNotifyTemplateEmpty.setting.message"/>
		</td>
	</tr>
	<c:import url="/sys/notify/include/sysNotifySetting_msg_view.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="orgSynchroNotifyTemplateEmptyForm" />
		<c:param name="fdKey" value="createAccountMessageSetting" />
	</c:import>

	<tr class="tr_normal_title">
		<td colspan="4">
			<bean:message  bundle="sys-oms-notify" key="deleteAccountNotifyTemplateEmpty.setting.message"/>
		</td>
	</tr>
	<c:import url="/sys/notify/include/sysNotifySetting_msg_view.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="orgSynchroNotifyTemplateEmptyForm" />
		<c:param name="fdKey" value="deleteAccountMessageSetting" />
	</c:import>
</kmss:ifModuleExist>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>