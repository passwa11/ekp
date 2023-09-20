<%@page import="com.landray.kmss.sys.archives.service.spring.SysArchivesModuleSelectService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	SysArchivesModuleSelectService moduleSelectService = new SysArchivesModuleSelectService();
%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title">${lfn:message('third-gcalendar:gcalendar.setting')}</template:replace>
		<template:replace name="head">
		<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/common.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
		     Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js", null, "js");
		</script>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span style="color: #35a1d0;">归档设置</span>
		</h2>
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
					  <td class="td_normal_title" width=15%>
						归档开关
					  </td>
					  <td>
							<ui:switch  property="value(sysArchEnabled)" onValueChange="config_chgEnabled();"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					  </td>
					</tr>
					<tr id="lab_detail">
		               <td class="td_normal_title" width="15%">档案归属</td>
		               <td>
							<kmss:ifModuleExist path="/km/archives/">
								<label class="lui-lbpm-radio">
									<input type="radio" name="value(kmOrEop)" value="km_arch" validate="required" subject="档案归属">
									<span class="radio-label"><bean:message bundle="sys-archives" key="enums.sys.kmArch"/></span>
								</label>
							</kmss:ifModuleExist>
							<kmss:ifModuleExist path="/eop/arch/">
								<label class="lui-lbpm-radio">
									<input type="radio" name="value(kmOrEop)" checked="" value="eop_arch" validate="required" subject="档案归属">
									<span class="radio-label"><bean:message bundle="sys-archives" key="enums.sys.eopArch"/></span>
								</label>
							</kmss:ifModuleExist>

							<span class="txtstrong">*</span>
					   </td>
	                </tr>
					<tr id="modulesTr">
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-archives" key="kmArchivesFileConfig.fdFileModels"/>
						</td><td>
						<html:hidden property="value(fdFileModels)"/>
						<kmss:ifModuleExist path="/km/review/">
							<label>
								<input name="fdFileModels" type="checkbox" value="km/review"/>
								<%=moduleSelectService.getModuleNames("km/review") %>
							</label>
						</kmss:ifModuleExist>
						<kmss:ifModuleExist path="/km/contract/">
							<label>
								<input name="fdFileModels" type="checkbox" value="km/contract"/>
								<%=moduleSelectService.getModuleNames("km/contract") %>
							</label>
						</kmss:ifModuleExist>
						<kmss:ifModuleExist path="/km/imissive/">
							<label>
								<input name="fdFileModels" type="checkbox" value="km/imissive"/>
								<%=moduleSelectService.getModuleNames("km/imissive") %>
							</label>
						</kmss:ifModuleExist>
						<kmss:ifModuleExist path="/km/agreement/">
							<label>
								<input name="fdFileModels" type="checkbox" value="km/agreement"/>
								<%=moduleSelectService.getModuleNames("km/agreement") %>
							</label>
						</kmss:ifModuleExist>
						<kmss:ifModuleExist path="/hr/ratify/">
							<label>
								<input name="fdFileModels" type="checkbox" value="hr/ratify"/>
								<%=moduleSelectService.getModuleNames("hr/ratify") %>
							</label>
						</kmss:ifModuleExist>
						<kmss:ifModuleExist path="/bmp/review/">
							<label>
								<input name="fdFileModels" type="checkbox" value="bmp/review"/>
								<%=moduleSelectService.getModuleNames("bmp/review") %>
							</label>
						</kmss:ifModuleExist>
					</td>
					</tr>
				</table>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.archives.config.SysArchivesConfig" />
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			</center>
		</html:form>
		
	 	<script type="text/javascript">
			$KMSSValidation();
			function validateAppConfigForm(thisObj) {
				return true;
			}

			function config_chgEnabled() {
				var cfgDetail = $("#lab_detail");
				var modulesTr = $("#modulesTr");
				var isChecked = "true" == $("input[name='value\\\(sysArchEnabled\\\)']").val();
				if (isChecked) {
					cfgDetail.show();
					modulesTr.show();
				} else {
					cfgDetail.hide();
					modulesTr.hide();
				}

				cfgDetail.find("input").each(function() {
					$(this).attr("disabled", !isChecked);
				});
			}
			LUI.ready(function() {
				config_chgEnabled();
			});

			seajs.use(['lui/jquery'],function($) {
				$(document).ready(function() {
					$("[name='fdFileModels']").click(function() {
						var fdFileModels = [];
						$("[name='fdFileModels']").each(function() {
							var item = this;
							if(item.checked) {
								fdFileModels.push(item.value);
							}
						});
						$("[name='value(fdFileModels)']").val(fdFileModels.join(';'));
					});
					var fdFileModels = $("[name='value(fdFileModels)']").val();
					fdFileModels = fdFileModels.split(';');
					for (var i = 0; i < fdFileModels.length; i++) {
						$("[value='"+fdFileModels[i]+"']").prop('checked',true);
					}
				});
			});
			//选择模块
			function selectModule(){
				Dialog_List(true, "value(fdFileModels)", "fdFileModelNames", null, "sysArchivesModuleSelectService",afterModuleSelect,null,null,null,
						"<bean:message bundle='sys-archives' key='kmArchivesFileConfig.moduleSelectDilog'/>");
			}
			function afterModuleSelect() {

			}
		</script>
	</template:replace>
</template:include>
