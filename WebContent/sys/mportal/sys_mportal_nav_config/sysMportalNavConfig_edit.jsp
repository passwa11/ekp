<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page
	import="com.landray.kmss.sys.language.utils.SysLangUtil,java.lang.String"%>
<template:include ref="config.profile.edit">
	<template:replace name="content">

		<link charset="utf-8" rel="stylesheet"
			href="${ LUI_ContextPath}/sys/mportal/sys_mportal_nav_config/style/edit.css">

		<script type="text/javascript"
			src="${ LUI_ContextPath}/sys/mportal/sys_mportal_nav_config/js/edit.js"></script>

		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title">
				${lfn:message('sys-mportal:sysMportal.profile.nav.config')} </span>
		</h2>

		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
			<div style="margin: 0 auto">
				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width="25%">${lfn:message("sys-mportal:sysMportalNavConfig.type")  }</td>
						<td width="75%">
							<table style="width: 100%; text-align: center;">
								<tr>
									<td>
										<div class="type" data-type="top">

											<span></span>
											<%	
												String currentLocaleCountry = SysLangUtil.getCurrentLocaleCountry();
												if(currentLocaleCountry != null && currentLocaleCountry.equals("US")){
											%>
											<img alt=""
												src="${ LUI_ContextPath}/sys/mportal/sys_mportal_nav_config/style/img/top_English.png">
											<% 
											}
												if(currentLocaleCountry != null && currentLocaleCountry.equals("CN")){
											%>
											<img alt=""
												src="${ LUI_ContextPath}/sys/mportal/sys_mportal_nav_config/style/img/top.png">
											<% 
												}
											%>
											<div>
												<strong>${lfn:message("sys-mportal:sysMportalNavConfig.top")  }</strong>
											</div>
										</div>
									</td>
									<td>
										<div class="type" data-type="bottom">
											<span></span>
											<%	
												if(currentLocaleCountry != null && currentLocaleCountry.equals("US")){
											%>
											<img alt=""
												src="${ LUI_ContextPath}/sys/mportal/sys_mportal_nav_config/style/img/bottom_English.png">
											<% 
											}
												if(currentLocaleCountry != null && currentLocaleCountry.equals("CN")){
											%>
											<img alt=""
												src="${ LUI_ContextPath}/sys/mportal/sys_mportal_nav_config/style/img/bottom.png">
											<% 
												}
											%>
											<div>
												<strong>${lfn:message("sys-mportal:sysMportalNavConfig.bottom")  }</strong><br>${lfn:message("sys-mportal:sysMportalNavConfig.bottom.tip")  }
											</div>
										</div>
									</td>
								</tr>
							</table>


						</td>
					</tr>

					<tr>
						<td class="td_normal_title" width="25%">${lfn:message("sys-mportal:sysMportalNavConfig.help")  }
							<div class="tip">${lfn:message("sys-mportal:sysMportalNavConfig.tip") }</div>
						</td>
						<td width="75%">
							<div class="input-group" id="url"></div>
						</td>
				</table>
				<center style="margin: 10px 0;">
				    <ui:button text="${lfn:message('button.save')}" suspend="bottom" height="35" width="120" onclick="Com_Submit(document.forms[0], 'update');"></ui:button>
				</center>
			</div>

			<html:hidden property="value(fdType)" />

			<html:hidden property="method_GET" />

			<input type="hidden" name="modelName"
				value="com.landray.kmss.sys.mportal.model.SysMportalNavConfig" />
		</html:form>

		<script type="text/javascript">
			$KMSSValidation();
		</script>
	</template:replace>
</template:include>
