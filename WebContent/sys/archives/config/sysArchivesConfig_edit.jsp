<%@page import="com.landray.kmss.sys.archives.service.spring.SysArchivesModuleSelectService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	SysArchivesModuleSelectService moduleSelectService = new SysArchivesModuleSelectService();
%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title">${lfn:message('sys-archives:kmArchivesFileConfig.setting')}</template:replace>
		<template:replace name="head">
		<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/common.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
		     Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js", null, "js");
		</script>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<%--归档设置--%>
			<span style="color: #35a1d0;"><bean:message bundle="sys-archives" key="kmArchivesFileConfig.setting"/></span>
		</h2>
		<html:form action="/sys/archives/sys_archives_main/sysArchivesConfig.do">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
					  <td class="td_normal_title" width=15%>
						<%--归档开关--%>
						<bean:message bundle="sys-archives" key="kmArchivesFileConfig.opening"/>
					  </td>
					  <td>
							<ui:switch  property="value(sysArchEnabled)" onValueChange="config_chgEnabled();"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					  </td>
					</tr>
					<tr id="lab_detail">
		               <td class="td_normal_title" width="15%">
						   <%--档案归属--%>
						   <bean:message bundle="sys-archives" key="kmArchivesFileConfig.ascription"/>
						   <span id="sysArchKmOrEopValue" style="display: none">${sysAppConfigForm.dataMap.kmOrEop}</span>
					   </td>
						<td>
							<kmss:ifModuleExist path="/km/archives/">
								<label class="lui-lbpm-radio">
									<input type="radio" name="value(kmOrEop)" value="km_arch" validate="required" onchange="change_eop_km();" subject="档案归属">
									<span class="radio-label"><bean:message bundle="sys-archives" key="enums.sys.kmArch"/></span>
								</label>
							</kmss:ifModuleExist>
							<kmss:ifModuleExist path="/eop/arch/">
								<label class="lui-lbpm-radio">
									<input type="radio" name="value(kmOrEop)" value="eop_arch" validate="required" onchange="change_eop_km();" subject="档案归属">
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
						<kmss:ifModuleExist path="/km/supervise/">
							<label>
								<input name="fdFileModels" type="checkbox" value="km/supervise"/>
								<%=moduleSelectService.getModuleNames("km/supervise") %>
							</label>
						</kmss:ifModuleExist>
						<kmss:ifModuleExist path="/km/reception/">
							<label>
								<input name="fdFileModels" type="checkbox" value="km/reception"/>
								<%=moduleSelectService.getModuleNames("km/reception") %>
							</label>
						</kmss:ifModuleExist>

						<%--费控管理--%>
						<kmss:ifModuleExist path="/fssc/expense/">
							<label>
								<input name="fdFileModels" type="checkbox" value="fssc/expense"/>
								<%=moduleSelectService.getModuleNames("fssc/expense") %>
							</label>
						</kmss:ifModuleExist>
						<kmss:ifModuleExist path="/fssc/loan/">
							<label>
								<input name="fdFileModels" type="checkbox" value="fssc/loan"/>
								<%=moduleSelectService.getModuleNames("fssc/loan") %>
							</label>
						</kmss:ifModuleExist>
						<kmss:ifModuleExist path="/fssc/payment/">
							<label>
								<input name="fdFileModels" type="checkbox" value="fssc/payment"/>
								<%=moduleSelectService.getModuleNames("fssc/payment") %>
							</label>
						</kmss:ifModuleExist>
						<kmss:ifModuleExist path="/fssc/proapp/">
							<label>
								<input name="fdFileModels" type="checkbox" value="fssc/proapp"/>
								<%=moduleSelectService.getModuleNames("fssc/proapp") %>
							</label>
						</kmss:ifModuleExist>
						<kmss:ifModuleExist path="/fssc/ar/">
							<label>
								<input name="fdFileModels" type="checkbox" value="fssc/ar"/>
								<%=moduleSelectService.getModuleNames("fssc/ar") %>
							</label>
						</kmss:ifModuleExist>
						<kmss:ifModuleExist path="/fssc/invoice/">
							<label>
								<input name="fdFileModels" type="checkbox" value="fssc/invoice"/>
								<%=moduleSelectService.getModuleNames("fssc/invoice") %>
							</label>
						</kmss:ifModuleExist>
						<%--费控管理--%>
					</td>
					</tr>
					<tr id="eopArchCreatorTR">
						<td class="td_normal_title" width=15%>
							<%--默认档案采集人--%>
							<bean:message bundle="sys-archives" key="kmArchivesFileConfig.default.collector"/>
						</td>
						<td>
							<xform:address   propertyId="value(archCreatorId)" propertyName="value(archCreatorName)"   onValueChange="archCreateChange"  orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:50%;" />
							&nbsp;&nbsp;&nbsp;
							<span style="color:red;">${lfn:message('sys-archives:message.sysArchivesConfig.archCreator.tips')}</span>
						</td>
					</tr>
				</table>
			</center>

			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.archives.config.SysArchivesConfig" />
			<input type="hidden" name="value(archCreatorId)"   value="${sysAppConfigForm.dataMap.archCreatorId}" />
			<input type="hidden" name="value(archCreatorName)"   value="${sysAppConfigForm.dataMap.archCreatorName}" />
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

				change_eop_km();

				cfgDetail.find("input").each(function() {
					$(this).attr("disabled", !isChecked);
				});
				//根据模块选择让单选选中
				$("input[name='value(kmOrEop)']").each(function() {
					var fdValue = $("#sysArchKmOrEopValue").text();
					//console.log(fdValue);
					if ($(this).val() != fdValue) {
						$(this).removeAttr("checked");
					} else {
						$(this).prop("checked", "checked");
					}
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

			/**
			 * 根据档案归属控制；默认档案采集人
			 */
			function change_eop_km(){
               var archives = $("[name='value\\\(kmOrEop\\\)']:checked").val();
               var isChecked = "true" == $("input[name='value\\\(sysArchEnabled\\\)']").val();
               if(!archives){
				   setTimeout(function(){
					   change_eop_km();
				   },200);
			   }
               if(archives=='eop_arch'&&isChecked){
               	  $("#eopArchCreatorTR").show();
			   }else{
				  $("#eopArchCreatorTR").hide();
			   }
			}

			function archCreateChange(value){
				var name = value[1];
				var id = value[0];
				$("[name='value(archCreatorId)']").val(id);
				$("[name='value(archCreatorName)']").val(name);
			}
		</script>
	</template:replace>
</template:include>
