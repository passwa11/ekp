<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	com.landray.kmss.third.ldap.LdapDetailConfig detailConfig = new com.landray.kmss.third.ldap.LdapDetailConfig();
	String managerDN = detailConfig.getValue("kmss.ldap.config.managerDN");
	if(managerDN==null){
		managerDN = "";
	}
	request.setAttribute("managerDN",managerDN);
%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title">${lfn:message('third-ldap:ldap.setting')}</template:replace>
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
				style="color: #35a1d0;">${lfn:message('third-ldap:ldap.setting')}</span></h2>

		<html:form action="/third/ldap/oms/in/config.do">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width=15%>${lfn:message('third-ldap:ldap.synchro.enable')}</td>
						<td><ui:switch id="oms_in_div" property="value(kmss.oms.in.ldap.enabled)"
									   onValueChange="config_oms_chgEnabled('in');"
									   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
									   disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<span class="message">${lfn:message('third-ldap:ldap.synchro.tip.pre')}<font color="red">
									${lfn:message('third-ldap:ldap.synchro.tip.mid')}</font>
									${lfn:message('third-ldap:ldap.synchro.tip.suf')}</span></td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>${lfn:message('third-ldap:ldap.synchro.out.enable')}</td>
						<td><ui:switch id="oms_out_div" property="value(kmss.oms.out.ldap.enabled)"
									   onValueChange="config_oms_chgEnabled('out');"
									   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
									   disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<span class="message">${lfn:message('third-ldap:ldap.synchro.out.tip1')}</span>
							<br>
							<span class="message"><font color="red">
					${lfn:message('third-ldap:ldap.synchro.out.notice')}:<br>
					1、${lfn:message('third-ldap:ldap.synchro.out.tip2')}<br>
					2、${lfn:message('third-ldap:ldap.synchro.out.tip3')}<br>
					3、${lfn:message('third-ldap:ldap.synchro.out.tip4')}</font>
					</span>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>${lfn:message('third-ldap:ldap.auth.enable')}</td>
						<td><ui:switch id="auth_div" property="value(kmss.authentication.ldap.enabled)"
									   onValueChange="config_auth_chgEnabled();"
									   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
									   disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<span class="message">${lfn:message('third-ldap:ldap.auth.tip.pre')}<font color="red">
									${lfn:message('third-ldap:ldap.auth.tip.mid')}</font>
									${lfn:message('third-ldap:ldap.auth.tip.suf')}</span></td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<a href="javascript:void(0)" onclick="openDetail()"><font color="blue" size="3">${lfn:message('third-ldap:ldap.setting.detail')}</font></a>
						</td>
					</tr>
				</table>

			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName"
				   value="com.landray.kmss.third.ldap.LdapConfig" />
			<input type="hidden" name="autoclose" value="false" />
			<center style="margin-top: 10px;"><!-- 保存 --> <ui:button
					text="${lfn:message('button.save')}" height="35" width="120"
					onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			</center>
		</html:form>

		<script type="text/javascript">
			$KMSSValidation();

			function disableOms(){
				//alert($("#oms_in_div input:checkbox"));
				$("#oms_in_div input:checkbox").click();
			}

			function disableOmsOut(){
				//alert($("#oms_in_div input:checkbox"));
				$("#oms_out_div input:checkbox").click();
			}

			function disableAuth(){
				//alert($("#oms_in_div input:checkbox"));
				$("#auth_div input:checkbox").click();
			}

			function config_oms_chgEnabled(direction) {
				//alert('${managerDN}');
				var managerDN = false;
				if('${managerDN}'!=''){
					managerDN = true;
				}
				var isChecked = "true" == $("input[name='value\\\(kmss.oms.in.ldap.enabled\\\)']").val();
				if (!managerDN && isChecked) {
					alert('${lfn:message('third-ldap:ldap.setting.detail.alert')}');
					//$("#oms_in input:checkbox").click();
					setTimeout(disableOms, 30 );
					return;
				}

				var isOutChecked = "true" == $("input[name='value\\\(kmss.oms.out.ldap.enabled\\\)']").val();
				if (!managerDN && isOutChecked) {
					alert('${lfn:message('third-ldap:ldap.setting.detail.alert')}');
					//$("#oms_in input:checkbox").click();
					setTimeout(disableOmsOut, 30 );
					return;
				}

				if(isChecked && isOutChecked){
					alert("${lfn:message('third-ldap:ldap.setting.oms.alert')}");
					//$("#oms_in input:checkbox").click();
					if(direction=="in"){
						setTimeout(disableOms, 30 );
					}else{
						setTimeout(disableOmsOut, 30 );
					}
				}
			}

			function config_auth_chgEnabled() {
				//alert('${managerDN}');
				if('${managerDN}'!=''){
					return;
				}
				var isChecked = "true" == $("input[name='value\\\(kmss.authentication.ldap.enabled\\\)']").val();
				if (isChecked) {
					alert('${lfn:message('third-ldap:ldap.setting.detail.alert')}');
					//$("#oms_in input:checkbox").click();
					setTimeout(disableAuth, 30 );

				}
			}

			function openDetail(){
				var href = "<c:url value='/third/ldap/setting.do'/>?method=edit&type=";
				window.open(href,'_blank');
			}
		</script>
	</template:replace>
</template:include>
