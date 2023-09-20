<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-auditlog" key="sysAuditlog.setting"/></span>
		</h2>
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-auditlog" key="sysAuditlog.setting.auditLogSwitch"/>
						</td><td width=85%>
							<c:if test="${sysAppConfigForm.map.auditLogSwitch == null}">
								<ui:switch property="value(auditLogSwitch)" checked="true"></ui:switch>
							</c:if>
							<c:if test="${sysAppConfigForm.map.auditLogSwitch != null}">
								<ui:switch property="value(auditLogSwitch)"></ui:switch>
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-auditlog" key="sysAuditlog.setting.buckup"/>
						</td><td width="85%">
								<bean:message bundle="sys-auditlog" key="sysAuditlog.setting.buckup0"/>
								<c:if test="${sysAppConfigForm.map.backupInterval == null}">
									<xform:text property="value(backupInterval)" style="width:20px;" value="1" validators="number">
									</xform:text>
								</c:if>
								<c:if test="${sysAppConfigForm.map.backupInterval != null}">
									<xform:text property="value(backupInterval)" style="width:20px;" validators="number">
									</xform:text>
								</c:if>
								<xform:select property="value(backupUnit)" showPleaseSelect="false">
									<xform:enumsDataSource enumsType="sysAuditlog_backupUnit"></xform:enumsDataSource>
								</xform:select>
								<bean:message bundle="sys-auditlog" key="sysAuditlog.setting.buckup1"/>
						</td>
					</tr>
				</table>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.auditlog.model.SysAuditlogConfig" />
			
			<center style="margin-top: 10px;">
				<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');" order="1" >
			</ui:button>
			</center>
		</html:form>
		<script type="text/javascript">
	 		$KMSSValidation();
	 	</script>
	</template:replace>
</template:include>
