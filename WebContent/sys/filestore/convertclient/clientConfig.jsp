<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" sidebar="auto">
	<template:replace name="head">
		<script type="text/javascript">
			function SaveConfig() {
				Com_Submit(document.sysFileConvertClientForm, "saveConfig");
			}
		</script>
	</template:replace>
	<template:replace name="content">
		<html:form
			action="/sys/filestore/sys_filestore/sysFileConvertClient.do">
			<html:hidden property="fdId" />
			<p class="lui_form_subject">
				<c:out
					value="${lfn:message('sys-filestore:clientconfig.config')}" />
			</p>
			<div class="lui_form_content_frame" style="padding-top: 20px">
				<table class="tb_normal" style="width: 95%;">
					<tr width="100%">
						<td class="td_normal_title" width="180px" align="center"><label> <bean:message
									key="clientconfig.taskCapacity"
									bundle="sys-filestore" />
						</label></td>
						<td><xform:text
								property="taskCapacity" style="width:60px;"
								subject="${lfn:message('sys-filestore:clientconfig.taskCapacity')}"
								showStatus="edit" validators="min(1) digits" /> <span class="message"><bean:message
									key='clientconfig.taskCapacity.desc' bundle='sys-filestore' /></span></td>
						<td rowspan="2" align="center"><kmss:authShow roles="SYSROLE_ADMIN"><ui:button text="${lfn:message('button.save') }" onclick="SaveConfig();"></ui:button></kmss:authShow></td>
					</tr>
					<tr width="100%">
						<td class="td_normal_title" width="180px" align="center"><label> <bean:message
									key="clientconfig.taskTimeout"
									bundle="sys-filestore" />
						</label></td>
						<td><xform:text property="taskTimeout"
								style="width:60px;"
								subject="${lfn:message('sys-filestore:clientconfig.taskTimeout')}"
								showStatus="edit" validators="min(1) digits" /> <span class="message"><bean:message
									key='clientconfig.taskTimeout.desc'
									bundle='sys-filestore' /></span></td>
					</tr>
					<tr width="100%">
						<td class="td_normal_title" width="180px" align="center"><label> <bean:message
									key="clientconfig.logLevel"
									bundle="sys-filestore" />
						</label></td>
						<td><xform:select property="logLevel" showPleaseSelect="false" showStatus="edit">
								<xform:simpleDataSource value="INFO"></xform:simpleDataSource>
								<xform:simpleDataSource value="DEBUG"></xform:simpleDataSource>
							</xform:select> <span class="message"><bean:message
									key='clientconfig.logLevel.desc' bundle='sys-filestore' /></span></td>
					</tr>
				</table>
			</div>
		</html:form>
		<script language="JavaScript">
			$KMSSValidation(document.forms['sysFileConvertClientForm']);
		</script>
	</template:replace>
</template:include>