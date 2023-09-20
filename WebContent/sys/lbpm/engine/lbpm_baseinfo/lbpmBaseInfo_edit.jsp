<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message key="module.node.paramsSetup.base" bundle="sys-lbpm-engine"/></span>
		</h2>
		
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do" onsubmit="return validateAppConfigForm(this);">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.nodeNameSelectItem"/>
						</td>
						<td width=85%>
							<xform:text property="value(nodeNameSelectItem)" style="width:85%" /><br />
							<bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.nodeNameSelectItem.text"/>
						</td>
					</tr> 
					<%-- <tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.notify.type.default"/>
						</td>
						<td width=85%>
							<kmss:editNotifyType property="value(defaultNotifyType)" value="${sysAppConfigForm.map.defaultNotifyType}"/>
						</td>
					</tr> --%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.notify.type.system"/>
						</td>
						<td width=85%>
							<kmss:editNotifyType property="value(systemNotifyType)" value="${sysAppConfigForm.map.systemNotifyType}"/><br>
							<bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.notify.type.system.description"/>
						</td>
				
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.notify.crash.target"/>
						</td>
						<td width=85%>
							<xform:address propertyId="value(notifyCrashTargetIds)" propertyName="value(notifyCrashTargetNames)" mulSelect="true" orgType="ORG_TYPE_PERSON | ORG_TYPE_POST" style="width:85%">
							</xform:address>
							<br>
							<xform:checkbox property="value(notifyTargetAuthor)" showStatus="edit">
								<xform:simpleDataSource value="true">
									<bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.notify.crash.target.type.author"/>
								</xform:simpleDataSource>
							</xform:checkbox>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<xform:checkbox property="value(notifyTargetSubmit)" showStatus="edit">
								<xform:simpleDataSource value="true">
									<bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.notify.crash.target.type.sumbit"/>
								</xform:simpleDataSource>
							</xform:checkbox>
						</td>
					</tr>
					
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.expire"/>
						</td>
						<td width=85%>
							<bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.expire.text1" />
							<xform:text property="value(notifyExpire)" style="width:25pt;" validators="digits,min(0)" /><bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.expire.day" />
							<!--<xform:text property="value(hourOfNotifyExpire)" style="width:25pt;" validators="digits,min(0)" /><bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.expire.hour" />
							<xform:text property="value(minuteOfNotifyExpire)" style="width:25pt;" validators="digits,min(0)" /><bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.expire.minute" />-->
							<bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.expire.text2" />
							&nbsp;&nbsp;<i><bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.notifyExpire.note" /></i><br/>
							<bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.notifyExpire.description" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.threadPoolSize"/>
						</td>
						<td width=85%>
							<xform:text property="value(threadPoolSize)" style="width:50pt;" validators="digits,min(1)" />
							<br><bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.threadPoolSize.note" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-lbpm-engine" key="lbpmBaseInfo.isQueueLogEnalbed"/>
						</td>
						<td width=85%>
							<ui:switch property="value(isQueueLogEnalbed)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						</td>
					</tr>
				</table>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmBaseInfo" />
			
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');" order="1" ></ui:button>
			</center>
		</html:form>
		
	 	<script type="text/javascript">
	 		$KMSSValidation();
		 	function validateAppConfigForm(thisObj){
		 		return true;
		 	}
	 	</script>
	</template:replace>
</template:include>
