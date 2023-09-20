<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title">${lfn:message('third-idm:idm.setting')}</template:replace>
	<template:replace name="head">
		<script type="text/javascript"
			src="${KMSS_Parameter_ResPath}js/common.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
	Com_IncludeFile(
			"validator.jsp|validation.js|plugin.js|validation.jsp|xform.js",
			null, "js");
</script>
		<style type="text/css">
.tb_normal td {
	//padding: 5px; //
	border: 1px #d2d2d2 solid; //
	word-break: break-all;
}
</style>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0"><span
			style="color: #35a1d0;">${lfn:message('third-idm:idm.setting')}</span></h2>

		<html:form action="/third/idm/oms/in/config.do">
			<center>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>${lfn:message('third-idm:idm.integrate.enable')}</td>
					<td><ui:switch property="value(kmss.oms.in.idm.enabled)"
						onValueChange="config_chgEnabled();"
						enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
						disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					<span class="message">${lfn:message('third-idm:idm.integrate.tip')}</span></td>
				</tr>
			</table>
			<table class="tb_normal" id='lab_detail' width=95% cellpadding="20"
				cellspacing="20"
				style="display: none; width: 95%; line-height: 25px;">
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-idm:idm.key')}</td>
					<td><xform:text property="value(kmss.idm.key)"
						subject="${lfn:message('third-idm:idm.key')}" required="true" style="width:85%"
						showStatus="edit" htmlElementProperties="disabled='true'" /><br>
					<span class="message">${lfn:message('third-idm:idm.key.tip')}</span></td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-idm:idm.webservice.org.url')}</td>
					<td><xform:text
						property="value(kmss.idm.webservice.url.organization)"
						subject="${lfn:message('third-idm:idm.webservice.org.url')}" required="true" style="width:85%"
						showStatus="edit" htmlElementProperties="disabled='true'" /><br>
					<span class="message">${lfn:message('third-idm:idm.webservice.org.url.tip')}</span></td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-idm:idm.webservice.person.url')}</td>
					<td><xform:text property="value(kmss.idm.webservice.url.user)"
						subject="${lfn:message('third-idm:idm.webservice.person.url')}" required="true" style="width:85%"
						showStatus="edit" htmlElementProperties="disabled='true'" /><br>
					<span class="message">${lfn:message('third-idm:idm.webservice.person.url.tip')}</span></td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-idm:idm.webservice.userName')}</td>
					<td><xform:text property="value(kmss.idm.webservice.userName)"
						subject="${lfn:message('third-idm:idm.webservice.userName')}" required="false" style="width:85%"
						showStatus="edit" htmlElementProperties="disabled='true'" /></td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-idm:idm.webservice.passsword')}</td>
					<td>
					<xform:text property="value(kmss.idm.webservice.password)"
						subject="${lfn:message('third-idm:idm.webservice.passsword')}" required="false"
						 style="width:85%"
						showStatus="edit" htmlElementProperties="disabled='true' type='password' class='inputsgl'" />
								
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-idm:idm.webservice.org.baseDN')}</td>
					<td><xform:text
						property="value(kmss.idm.ldap.base.organization)" subject="${lfn:message('third-idm:idm.webservice.org.baseDN')}"
						style="width:85%" showStatus="edit"
						htmlElementProperties="disabled='true'" /><br>
					<span class="message">${lfn:message('third-idm:idm.webservice.org.baseDN.tip')}</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-idm:idm.webservice.org.filter')}</td>
					<td><xform:text
						property="value(kmss.idm.ldap.filter.organization)"
						subject="${lfn:message('third-idm:idm.webservice.org.filter')}" style="width:85%" showStatus="edit"
						htmlElementProperties="disabled='true'" /><br>
					<span class="message">${lfn:message('third-idm:idm.webservice.org.filter.tip')}</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-idm:idm.webservice.person.baseDN')}</td>
					<td><xform:text property="value(kmss.idm.ldap.base.user)"
						subject="${lfn:message('third-idm:idm.webservice.person.baseDN')}" style="width:85%" showStatus="edit"
						htmlElementProperties="disabled='true'" /><br>
					<span class="message">${lfn:message('third-idm:idm.webservice.person.baseDN.tip')}</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-idm:idm.webservice.person.filter')}</td>
					<td><xform:text property="value(kmss.idm.ldap.filter.user)"
						subject="${lfn:message('third-idm:idm.webservice.person.filter')}" style="width:85%" showStatus="edit"
						htmlElementProperties="disabled='true'" /><br>
					<span class="message">${lfn:message('third-idm:idm.webservice.person.filter.tip')}</span>
					</td>
				</tr>
			</table>

			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName"
				value="com.landray.kmss.third.idm.IDMConfig" />
			<input type="hidden" name="autoclose" value="false" />
			<center style="margin-top: 10px;"><!-- 保存 --> <ui:button
				text="${lfn:message('button.save')}" height="35" width="120"
				onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			</center>
		</html:form>

		<script type="text/javascript">
	$KMSSValidation();
	function validateAppConfigForm(thisObj) {
		return true;
	}

	function config_chgEnabled() {
		var cfgDetail = $("#lab_detail");
		var isChecked = "true" == $(
				"input[name='value\\\(kmss.oms.in.idm.enabled\\\)']")
				.val();
		if (isChecked) {
			cfgDetail.show();
		} else {
			cfgDetail.hide();
		}

		cfgDetail.find("input").each( function() {
			$(this).attr("disabled", !isChecked);
		});
	}

	LUI.ready( function() {
		config_chgEnabled();
	});
</script>
	</template:replace>
</template:include>
