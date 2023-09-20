<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	
	<template:replace name="title">
		WPS WebOffice集成配置
	</template:replace>

	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span style="color: #35a1d0;">WPS WebOffice集成配置</span>
		</h2>
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td colspan="2">	
						说明：WPS-WebOffice是公网对接方式，文档不上云，可在浏览器中执行去插件化的预览与编辑
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>$WPS WebOffice集成配置</td>
						<td>
							<ui:switch property="value(thirdWpsWebOfficeEnabled)" onValueChange="autoChange();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}" >
							</ui:switch>
						</td>
					</tr>
					<tr class="thirdWps" style="display: none">
						<td class="td_normal_title" width=15%>wps服务器地址</td>
						<td>
							<html:text property="value(thirdWpsWebOfficeUrl)" style="width:90%"/>
						</td>
					</tr>
					<tr class="thirdWps" style="display: none">
						<td class="td_normal_title" width=15%>应用Appid</td>
						<td>
							<html:text property="value(thirdWpsWebOfficeAppId)" style="width:90%"/>
						</td>
					</tr>
					<tr class="thirdWps" style="display: none">
						<td class="td_normal_title" width=15%>应用Appkey</td>
						<td>
							<html:text property="value(thirdWpsWebOfficeAppKey)" style="width:90%"/>
						</td>
					</tr>
				</table>
			</center>
						
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.third.wps.model.ThirdWpsWebOfficeConfig" />
			<center style="margin-top: 10px;">
				<kmss:authShow roles="ROLE_THIRDWPS_SETTING">
						<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="configChange();"></ui:button>
				</kmss:authShow>
			</center>
		</html:form>

		<script type="text/javascript">
		function configChange() {
			Com_Submit(document.sysAppConfigForm, 'update');
		}
		
		function autoChange() {
			var autoCategoryTag = "true" == $("input[name='value\\\(thirdWpsWebOfficeEnabled\\\)']").val();

			if (autoCategoryTag) {
				$('.thirdWps').css('display', 'table-row');
			}else{
				$('.thirdWps').hide();
			}
		}
		
		LUI.ready(function() {
			autoChange();
		});
		</script>
	</template:replace>
</template:include>
