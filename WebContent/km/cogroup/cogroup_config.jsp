<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.km.cogroup.util.CogroupUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message key="cogroup.cogroupConfig" bundle="km-cogroup"/></template:replace>
	<template:block name="path" >
		<span class=txtlistpath><bean:message key="cogroupConfig.currurl" bundle="km-cogroup"/>：<bean:message key="cogroup.config.setting" bundle="km-cogroup"/></span>
	</template:block>
	<template:replace name="content">
		<h2 align="center" style="margin:10px 0">
			<span class="profile_config_title"><bean:message key="cogroup.cogroupConfig" bundle="km-cogroup"/></span>
		</h2>
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do?autoclose=false" onsubmit="return validateAppConfigForm(this);">
			<center>
				<table id="cogroupBaseTable" class="tb_normal" width="95%">
					<tr id="cogroupEnableTR">
						<td class="td_normal_title" width="15%"><bean:message key="cogroup.cogroupEnabled" bundle="km-cogroup"/></td>
						<td>
							<ui:switch property="value(cogroupEnabled)" onValueChange="window.wx_display_change();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<br/>
							<font><bean:message key="group.tips" bundle="km-cogroup"/></font>
						</td>
					</tr>
					<%
					boolean dingExist = CogroupUtil.moduleExist("/third/ding/");
					boolean lDingExist = CogroupUtil.moduleExist("/third/lding/");
					if(dingExist || lDingExist){
					%>
						<%-- <kmss:ifModuleExist  path = "/third/ding/"> --%>
					<tr id="dingCogroupEnableTR">
						<td class="td_normal_title" width="15%"><bean:message key="cogroup.dingCogroupEnabled" bundle="km-cogroup"/></td>
						<td>
							<ui:switch property="value(dingCogroupEnable)" onValueChange="window.ding_display_change();" enabledText="${lfn:message('km-cogroup:group.tips.ding.open')}" disabledText="${lfn:message('km-cogroup:group.tips.ding.close')}"></ui:switch>
							<font><bean:message key="group.tips.ding" bundle="km-cogroup"/></font>

							<div name="dingContainDept">
								<br/>
								<ui:switch property="value(dingCogroupContainDept)" enabledText="${lfn:message('km-cogroup:group.tips.ding.containDept.open')}" disabledText="${lfn:message('km-cogroup:group.tips.ding.containDept.close')}"></ui:switch>
								<font><bean:message key="group.tips.ding.containDept" bundle="km-cogroup"/></font>
							</div>

							<div name="dingContainDept">
								<br/>
								<ui:switch property="value(autoEnterGroup)" enabledText="${lfn:message('km-cogroup:group.tips.ding.autoEnterGroup.open')}" disabledText="${lfn:message('km-cogroup:group.tips.ding.autoEnterGroup.close')}"></ui:switch>
								<font><bean:message key="group.tips.ding.autoEnterGroup" bundle="km-cogroup"/></font>
							</div>

						</td>
					</tr>
					<tr>
						<td colspan="2">
							<img src="${KMSS_Parameter_ContextPath}km/cogroup/resource/images/demo.png" style="width: 100%;">
						</td>
					</tr>
					<% } %>
					<!-- 企业微信群聊 -->
					<%
						boolean weixinExist = CogroupUtil.moduleExist("/third/weixin/");
						if(weixinExist){
					%>
					<tr id="wxWorkCogroupEnableTR">
						<td class="td_normal_title" width="15%"><bean:message key="cogroup.wxWorkCogroupEnable" bundle="km-cogroup"/></td>
						<td>
							<ui:switch property="value(wxWorkCogroupEnable)" onValueChange="window.wx_display_change();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<br/>
							<font><bean:message key="group.tips.weixinWork" bundle="km-cogroup"/></font>
						</td>
					</tr>
					<% } %>
						<%-- </kmss:ifModuleExist> --%>

				</table>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.km.cogroup.model.GroupConfig" />
			<center style="margin:20px 0;">
				<!-- 保存 -->
				<ui:button text="${lfn:message('button.save')}" suspend="bottom" onclick="window.itSubmit();" width="120" height="35"></ui:button>
			</center>
		</html:form>
		<script type="text/javascript">
			var validation = $KMSSValidation();
			seajs.use(['lui/jquery'],function($){
					function ding_display_change(){
						var value = $('[name="value(dingCogroupEnable)"]').val();
						if(value == 'true'){
							$("[name='dingContainDept']").show();
						}else{
							$("[name='dingContainDept']").hide();
						}
					}

					window.validateAppConfigForm = function(){
						return true;
					};

					window.itSubmit = function(){
						Com_Submit(document.sysAppConfigForm, 'update');
					};

					LUI.ready(function(){
						ding_display_change();
					});

					window.ding_display_change = ding_display_change;
				});
		</script>
	</template:replace>
</template:include>