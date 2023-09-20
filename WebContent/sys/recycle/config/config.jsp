<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.web.taglib.xform.ICustomizeDataSource"%>
<%@page import="com.landray.kmss.sys.recycle.forms.SoftDeleteConfigForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title">${lfn:message('sys-recycle:config.setting')}</template:replace>
	<template:replace name="head">
		<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/common.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
			Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js", null, "js");
		</script>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0"><span
			style="color: #35a1d0;">${lfn:message('sys-recycle:config.setting')}</span></h2>

		<html:form action="/sys/recycle/softDeleteConfig.do?autoclose=false">
			<center>
					<table class="tb_normal" width=95%>
						<tr>
							<td class="td_normal_title" colspan=2>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-recycle:config.setting.enabled')}</td>
							<td>
								<ui:switch property="value(softDeleteConfigEnable)" checked="${softDeleteConfigEnable}" onValueChange="config_softDelete_chgEnabled();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<br>
								<div id='lab_softDelete' >
									<xform:checkbox  property="value(softDeleteConfigEnableModules)" showStatus="edit">
										<xform:customizeDataSource className="com.landray.kmss.sys.recycle.service.spring.SoftDeleteDataSource"></xform:customizeDataSource>
									</xform:checkbox >
									
									<br><br>
									<ui:button text="${lfn:message('sys-recycle:config.setting.transfer.check')}" onclick="_check();"></ui:button>
							    </div>
							</td>
						</tr>
						<tr id="tr_softDelete">
							<td class="td_normal_title" width="35%">${lfn:message('sys-recycle:config.setting.clear')}</td>
							<td>
								<label>
									<span class="message">${lfn:message('sys-recycle:config.setting.clear1')}
									<xform:text property="value(clearRecycleByDay)" validators="digits min(0)" showStatus="edit" style="width:50px;"></xform:text>
									${lfn:message('sys-recycle:config.setting.clear2')}</span>
									<br>
									<span class="message">${lfn:message('sys-recycle:config.setting.clear.desc')}</span>
								</label>
							</td>
						</tr>
					</table>
					<center style="margin:10px 0;">
						<!-- 保存 -->
						<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="_save();"></ui:button>
					</center>
				
				
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.recycle.model.SoftDeleteConfig" />
			<input type="hidden" name="autoclose" value="false" />
			<input type="hidden" name="allOpertType" />
			<input type="hidden" name="addModules" />
			<input type="hidden" name="delModules" />
		</html:form>
	 	<script type="text/javascript">
	 		$KMSSValidation();
	 		
	 		// 所有已部署的模块
	 		var models = {};
	 		<%
	 			// 修改前已经开启的模块
	 			SoftDeleteConfigForm configForm = (SoftDeleteConfigForm)request.getAttribute("softDeleteConfigForm");
	 			List<String> oriEnableModules = configForm.getEnableModules();
	 			
				Map<String, String> map = ((ICustomizeDataSource)SpringBeanUtil.getBean("softDeleteDataSource")).getOptions();
				if(map != null) {
					for(String key: map.keySet()) {
			%>
				models['<%=key%>'] = '<%=map.get(key)%>';
			<%
					}
				}
			%>
			
			// 原始开启的模块
			var oriEnableModules = {};
			<%
				for(String val : oriEnableModules) {
			%>
				oriEnableModules['<%=val%>'] = '<%=val%>';
			<%
				}
			%>
	 		
			var $lang = {
					delModulesInfo: '<bean:message bundle="sys-recycle" key="config.setting.delModules"/>',
					addModulesInfo: '<bean:message bundle="sys-recycle" key="config.setting.addModules"/>',
					confirmInfo1: '<bean:message bundle="sys-recycle" key="config.setting.confirm.info1"/>',
					confirmInfo2: '<bean:message bundle="sys-recycle" key="config.setting.confirm.info2"/>',
					confirmInfo3: '<bean:message bundle="sys-recycle" key="config.setting.confirm.info3"/>',
					confirmInfo4: '<bean:message bundle="sys-recycle" key="config.setting.confirm.info4"/>',
					allRecover: '<bean:message bundle="sys-recycle" key="config.setting.allRecover"/>',
					allDelete: '<bean:message bundle="sys-recycle" key="config.setting.allDelete"/>',
					ok: '<bean:message key="button.ok"/>',
					cancel: '<bean:message key="button.cancel"/>',
					operationTitle: '<bean:message bundle="sys-ui" key="ui.dialog.operation.title"/>',
					checkNoTransfer: '<bean:message bundle="sys-recycle" key="config.setting.transfer.checkNoTransfer"/>',
					checkTransfer: '<bean:message bundle="sys-recycle" key="config.setting.transfer.check"/>'
			};
		</script>
		<script type="text/javascript" src="${LUI_ContextPath}/sys/recycle/resource/js/config_script.js"></script>
	</template:replace>
</template:include>
