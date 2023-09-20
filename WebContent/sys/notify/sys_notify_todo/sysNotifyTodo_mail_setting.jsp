<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="com.landray.kmss.util.StringUtil"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title">邮箱设置</template:replace>
	<template:replace name="head">
		<script type="text/javascript"
			src="${KMSS_Parameter_ResPath}js/common.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
	Com_IncludeFile(
			"validator.jsp|validation.js|plugin.js|validation.jsp|xform.js|doclist.js|dialog.js",
			null, "js");
</script>
		<style type="text/css">
.tb_normal td {
	border: 1px #d2d2d2 solid;
	word-break: break-all;
}
</style>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0"><span
			style="color: #35a1d0;"><bean:message key="sysNotifyTodo.mail.config" bundle="sys-notify" /></span></h2>

		<html:form action="/sys/notify/sysNotifyMailSetting.do">
			<center>
			
			<table class="tb_normal" id='lab_detail' width=95% cellpadding="20"
				cellspacing="20"
				style="width: 95%;">
				<tr style="display:none" id="kmss.notify.mailFrom">
		<td class="td_normal_title" width="15%"><bean:message bundle="sys-notify" key="sysNotifyTodo.mail.config.default"/></td>
		<td>
			<xform:text property="value(kmss.notify.mailFrom)" style="width:300px"
				required="true" showStatus="edit" htmlElementProperties="disabled='true'" subject="${lfn:message('sys-notify:sysNotifyTodo.mail.config.default') }"/>
			<br>
			<span class="message"><bean:message bundle="sys-notify" key="sysNotifyTodo.mail.config.default.tip" arg0="${fn:escapeXml('<admin@demo.landray.com.cn>')}"/></span>
		</td>
	</tr>
	<tr id="kmss.notify.mailHost">
		<td class="td_normal_title" width="15%"><bean:message bundle="sys-notify" key="sysNotifyTodo.mail.config.host"/></td>
		<td>
			<xform:text property="value(kmss.notify.mailHost)" style="width:300px"
				required="true" showStatus="edit" subject="${lfn:message('sys-notify:sysNotifyTodo.mail.config.host') }" onValueChange="config_notify_mail_host_agreement"/>
			&nbsp;&nbsp;&nbsp;
			<bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.host.agreement"/>：
			<xform:select property="value(kmss.notify.mailHost.agreement)" showStatus="edit" onValueChange="config_notify_mail_host_agreement();" showPleaseSelect="false">
				<xform:simpleDataSource value=""><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.host.agreement.no"/></xform:simpleDataSource>
				<xform:simpleDataSource value="SSL">SSL</xform:simpleDataSource>
				<xform:simpleDataSource value="TLS">TLS</xform:simpleDataSource>
				<xform:simpleDataSource value="TLSv1.2">TLSv1.2</xform:simpleDataSource>
			</xform:select>
			&nbsp;&nbsp;&nbsp;
			<span id="sslTip" style="color: red;display: none;"><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.host.agreement.ssltip"/></span>
			<br>
			<span class="message"><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.host.agreement.ssltip.example"/></span>
		</td>
	</tr>
	<tr id="kmss.notify.mailFromDefault">
		<td class="td_normal_title" width="15%"><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.mailFromDefault"/></td>
		<%-- 
		<td>
			<xform:checkbox property="value(kmss.notify.mailFromDefault)" subject="始终以系统发送邮件"  htmlElementProperties="id=\"value(kmss.notify.mailFromDefault)\"" showStatus="edit">
				<xform:simpleDataSource value="true">启用</xform:simpleDataSource>
			</xform:checkbox>&nbsp;		
			<span class="message">(说明：勾中则以系统缺省邮件发送邮件，否则以个人邮件发送邮件)</span>
		</td>
		--%>
		<td><ui:switch property="value(kmss.notify.mailFromDefault)"
						enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
						disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"
						onValueChange="mailFromDefaultChange();">
			</ui:switch>
			<span class="message"><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.mailFromDefault.tip"/></span>
			<div id="mailFromDefault_div" style="color:red;display: none;"><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.mailFromDefault.tip2"/></div>
		</td>
	</tr>
	<tr id="kmss.notify.mailDefaultEncoding">
		<td class="td_normal_title" width="15%"><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.mailDefaultEncoding"/></td>
		<td>
			<xform:text property="value(kmss.notify.mailDefaultEncoding)" style="width:150px"
				required="true" showStatus="edit" subject="${lfn:message('sys-notify:sysNotityTodo.mail.config.mailDefaultEncoding') }"/>
			<br>
			<span class="message"><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.mailDefaultEncoding.example"/></span>
		</td>
	</tr>
	<tr id="kmss.notify.mailSmtpAuth">
		<td class="td_normal_title" width="15%"><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.mailSmtpAuth"/></td>
		<%--
		<td>
			<label>
				<xform:checkbox property="value(kmss.notify.mailSmtpAuth)" subject="是否需要认证" onValueChange="config_notify_configMail()"  htmlElementProperties="id=\"value(kmss.notify.mailSmtpAuth)\"" showStatus="edit">
					<xform:simpleDataSource value="true">启用</xform:simpleDataSource>
				</xform:checkbox>				
			</label>
		</td>
		 --%>
			<td><ui:switch property="value(kmss.notify.mailSmtpAuth)"
						onValueChange="config_notify_configMail();"
						enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
						disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					<span class="message"></span></td>
		
	</tr>
	<tr style="display:none" id="kmss.notify.mailUsername">
		<td class="td_normal_title" width="15%"><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.mailUsername"/></td>
		<td>
			<xform:text property="value(kmss.notify.mailUsername)" style="width:150px"
				required="true" showStatus="edit" htmlElementProperties="disabled='true'" subject="${lfn:message('sys-notify:sysNotityTodo.mail.config.mailUsername') }"/>
			<br>
		</td>
	</tr>
	<tr style="display:none" id="kmss.notify.mailPassword">
		<td class="td_normal_title" width="15%"><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.mailPassword"/></td>
		<td>
			<c:set var="_pass" value="${sysAppConfigForm.map['kmss.notify.mailPassword'] }"/>
			<%
				String pass = (String)pageContext.getAttribute("_pass");

				if(StringUtil.isNotNull(pass)){
					pass = new String(Base64.encodeBase64(pass.getBytes("UTF-8")),"UTF-8");
					pageContext.setAttribute("_pass", "\u4649\u5820\u4d45\u4241\u5345\u3634{" + pass +"}");	
				}
			%>		
			<xform:text property="value(kmss.notify.mailPassword)" style="width:150px" 
				required="true" showStatus="edit" htmlElementProperties="type='password' disabled='true'" value="${_pass}" subject="${lfn:message('sys-notify:sysNotityTodo.mail.config.mailPassword') }"/>
			<br>
		</td>
	</tr>
	<tr id="kmss.notify.mailTimeout">
		<td class="td_normal_title" width="15%"><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.mailTimeout"/></td>
		<td>
			<xform:text property="value(kmss.notify.mailTimeout)" style="width:150px"
				required="true" showStatus="edit"
				validators="required number scaleLength(0)" subject="${lfn:message('sys-notify:sysNotityTodo.mail.config.mailTimeout') }"/><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.mailTimeout.millisecond"/>
				<div>
				<span class="message"><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.mailTimeout.tip"/></span>
				</div>
			<br>
		</td>
	</tr>
	<tr id="kmss.notify.mailSendtoCount">
		<td class="td_normal_title" width="15%"><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.mailTimeout.mailSendtoCount"/></td>
		<td>
			<xform:text property="value(kmss.notify.mailSendtoCount)" style="width:150px"
				required="true" showStatus="edit"
				validators="required number scaleLength(0)" subject="${lfn:message('sys-notify:sysNotityTodo.mail.config.mailTimeout.mailSendtoCount') }"/><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.mailSendtoCount.one"/>
				<div>
				<span class="message"><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.mailSendtoCount.tip"/></span>
				</div>
			<br>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="35%">${lfn:message('sys-notify:kmss.notify.mailSendAddressRegular')}</td>
		<td>
			<label>
			<xform:radio property="value(kmss.notify.mailSendAddressRegular)" showStatus="edit">
			 	<xform:simpleDataSource value="1">${lfn:message('sys-notify:kmss.notify.mailSendAddressRegular.yes')}</xform:simpleDataSource>
			 	<xform:simpleDataSource value="0">${lfn:message('sys-notify:kmss.notify.mailSendAddressRegular.no')}</xform:simpleDataSource>
			 </xform:radio>
			</label>
			<br>
			<span class="message">&nbsp;${lfn:message('sys-notify:kmss.notify.mailSendAddressRegular.tip')}</span>
		</td>
	</tr>	

			</table>
			<br />
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.notify.model.SysNotifyMailSetting" />
			<input type="hidden" name="autoclose" value="false" />
			<center style="margin-top: 10px;">
			<!-- 保存 --> 
			<ui:button text="${lfn:message('button.save')}" suspend="bottom" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			</center>
		</html:form>

		<script type="text/javascript">
	$KMSSValidation();
	function validateAppConfigForm(thisObj) {
		return true;
	}
	
	function config_notify_configMail(){
		//var smtpAuth = document.getElementById("value(kmss.notify.mailSmtpAuth)");
		var isChecked = "true" == $(
				"input[name='value\\\(kmss.notify.mailSmtpAuth\\\)']")
				.val();
		
		var mailUsername = document.getElementsByName("value(kmss.notify.mailUsername)")[0];
		var mailPassword = document.getElementsByName("value(kmss.notify.mailPassword)")[0];
		var mailUsernameId = document.getElementById("kmss.notify.mailUsername");
		var mailPasswordId = document.getElementById("kmss.notify.mailPassword");
		if(isChecked){
			mailUsername.disabled = false;
			mailPassword.disabled = false;
			mailUsernameId.style.display = 'table-row';
			mailPasswordId.style.display = 'table-row';
		}else{
			mailUsername.disabled = true;
			mailPassword.disabled = true;
			mailUsernameId.style.display = 'none';
			mailPasswordId.style.display = 'none';
		}
	}

	function config_notify_mail_host_agreement() {
		var mailHost = document.getElementsByName("value(kmss.notify.mailHost)")[0].value;
		var agreement = document.getElementsByName("value(kmss.notify.mailHost.agreement)")[0].value;
		var sslTip = document.getElementById("sslTip");
		if (agreement == "SSL" || agreement == "TLS") {
			if (mailHost.indexOf(":") != -1) {
				sslTip.style.display = 'none';
			} else {
				sslTip.style.display = 'inline';
			}
		} else {
			sslTip.style.display = 'none';
		}
	}

	function mailFromDefaultChange() {
		var mailFromDefault = document.getElementsByName("value(kmss.notify.mailFromDefault)")[0].value;
		var mailFromDefault_div = document.getElementById("mailFromDefault_div");
		console.log("mailFromDefaultChange:", mailFromDefault);
		if(mailFromDefault == "false") {
			mailFromDefault_div.style.display = "";
		} else {
			mailFromDefault_div.style.display = "none";
		}
	}
	
	function config_notify_checkMailConfig(){
		var mailFrom = document.getElementsByName("value(kmss.notify.mailFrom)")[0];
		var mailFromId = document.getElementById("kmss.notify.mailFrom");
		var mailFromDefault = document.getElementsByName("value(kmss.notify.mailFromDefault)")[0];
		var mailFromDefaultId = document.getElementById("kmss.notify.mailFromDefault");
		var mailHostId = document.getElementById("kmss.notify.mailHost");
		var mailHost = document.getElementsByName("value(kmss.notify.mailHost)")[0];
		var defaultEncoding = document.getElementsByName("value(kmss.notify.mailDefaultEncoding)")[0];
		var defaultEncodingId = document.getElementById("kmss.notify.mailDefaultEncoding");
		//var smtpAuth = document.getElementById("value(kmss.notify.mailSmtpAuth)");

		var smtpAuthChecked = "true" == $(
		"input[name='value\\\(kmss.notify.mailSmtpAuth\\\)']")
		.val();

		
		var smtpAuthId = document.getElementById("kmss.notify.mailSmtpAuth");
		var mailUsername = document.getElementsByName("value(kmss.notify.mailUsername)")[0];
		var mailUsernameId = document.getElementById("kmss.notify.mailUsername");
		var mailPassword = document.getElementsByName("value(kmss.notify.mailPassword)")[0];
		var mailPasswordId = document.getElementById("kmss.notify.mailPassword");
		var mailTimeout = document.getElementsByName("value(kmss.notify.mailTimeout)")[0];
		var mailTimeoutId = document.getElementById("kmss.notify.mailTimeout");
		var mailSendtoCount =document.getElementsByName("value(kmss.notify.mailSendtoCount)")[0];
		var mailSendtoCountId = document.getElementById("kmss.notify.mailSendtoCount"); 
			mailFrom.disabled = false;
			mailFromId.style.display = 'table-row';
			mailHostId.style.display = 'table-row';
			mailFromDefaultId.style.display = 'table-row';
			mailHost.disabled = false;
			smtpAuthId.style.display = 'table-row';
			if(smtpAuthChecked){
				mailUsername.disabled = false;
				mailPassword.disabled = false;
				mailUsernameId.style.display = 'table-row';
				mailPasswordId.style.display = 'table-row';
			}else{
				mailUsername.disabled = true;
				mailPassword.disabled = true;
				mailUsernameId.style.display = 'none';
				mailPasswordId.style.display = 'none';
			}
			defaultEncoding.disabled = false;
			defaultEncodingId.style.display = 'table-row';
			if (defaultEncoding.value == '') {
				defaultEncoding.value = 'GB2312';
			}
			mailTimeout.disabled = false;
			mailTimeoutId.style.display = 'table-row';
			if (mailTimeout.value == '') {
				mailTimeout.value = '30000';
			}
			mailSendtoCount.disabled = false;
			mailSendtoCountId.style.display = 'table-row';
			if (mailSendtoCount.value == '') {
				mailSendtoCount.value = '100';
			}
	}

	LUI.ready( function() {
		config_notify_checkMailConfig();
		mailFromDefaultChange();
	});
</script>
	</template:replace>
</template:include>
