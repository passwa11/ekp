<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">

	<template:replace name="title">
		${lfn:message('kms-lservice:module.kms.lservice.config')}
	</template:replace>

	<template:replace name="content">

		<h2 align="center" style="margin: 10px 0">

			<span style="color: #35a1d0;">
				${lfn:message('kms-lservice:module.kms.lservice.config')} </span>

		</h2>

		<html:form action="/kms/lservice/lserviceConfig.do">

			<center>

				<table class="tb_normal" width=95%>

					<tr>
						<td class="td_normal_title" width=15%>${lfn:message('kms-lservice:lservice.config.refs')}</td>
						<td><ui:switch property="value(kmss.lservice.refs.enabled)"
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
								disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>

							<span class="message"><font color="red">${lfn:message('kms-lservice:lservice.config.refs.tip')}</font></span></td>
					</tr>

					<tr>
						<td class="td_normal_title" width=15%>${lfn:message('kms-lservice:lservice.config.auth')}</td>
						<td><ui:switch property="value(kmss.lservice.auth.enabled)"
								onValueChange="configChange();"
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
								disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>

							<span class="message"><font color="red">${lfn:message('kms-lservice:lservice.config.auth.tip')}</font></span></td>
					</tr>

					<tr style="display: none;" id="readers">
						<td class="td_normal_title" width=15%>${lfn:message('kms-lservice:lservice.config.readers')}</td>
						<td><ui:switch
								property="value(kmss.lservice.readers.enabled)"
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
								disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<span class="message"><font color="red">${lfn:message('kms-lservice:lservice.config.readers.tip')}</font></span>
						</td>
					</tr>
					
					<tr>
						<td class="td_normal_title" width=15%>${lfn:message('kms-lservice:lservice.config.agenda')}</td>
						<td><ui:switch property="value(kmss.lservice.refs.agenda)"
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
								disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>

							<span class="message"><font color="red">${lfn:message('kms-lservice:lservice.config.agenda.tip')}</font></span></td>
					</tr>

				</table>

			</center>


			<html:hidden property="method_GET" />

			<input type="hidden" name="modelName"
				value="com.landray.kmss.kms.lservice.config.LserviceConfig" />
			<input type="hidden" name="autoclose" value="false" />


			<center style="margin-top: 10px;">
				<ui:button text="${lfn:message('button.save')}" height="35"
					width="120"
					onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			</center>

		</html:form>

		<script type="text/javascript">
			function configChange() {

				var isOn = "true" == $(
						"input[name='value\\\(kmss.lservice.auth.enabled\\\)']")
						.val();

				// 显示隐藏授权情况选项
				if (isOn) {
					$('#readers').css('display', 'table-row');
				} else {//
					$('#readers').hide();
				}

			}

			LUI.ready(function() {

				configChange();
			});
		</script>
	</template:replace>
</template:include>
