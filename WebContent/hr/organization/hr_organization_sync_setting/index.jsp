<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message key="hr.organization.sync.rule" bundle="hr-organization"/></template:replace>
	<template:replace name="head">
		<script>
			Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|dialog.js", null, "js");
		</script>
		<script>$KMSSValidation();</script>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do" onsubmit="return validateAppConfigForm(this);">
		<div style="margin-top:25px">
			<h2 align="center" style="margin:10px 0">
				<span class="profile_config_title"><bean:message key="hr.organization.sync.rule" bundle="hr-organization"/></span>
			</h2>
			<center>
				<table class="tb_normal" width=90%>
					<tr>
						<td class="td_normal_title" width=15%>${lfn:message('hr-organization:hr.organization.hr.to.ekp')}</td>
						<td  colspan=3>
						   	<ui:switch id="hrToEkpEnableSwitch" property="value(hrToEkpEnable)"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
						   	disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"  onValueChange="hrToEkp_display_change();" ></ui:switch>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>${lfn:message('hr-organization:hr.organization.ekp.to.hr')}</td>
						<td  colspan=3>
						   	<ui:switch id="ekpToHrEnableSwitch" property="value(ekpToHrEnable)"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
						   	disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"  onValueChange="ekpToHr_display_change();" ></ui:switch>
						</td>
					</tr>
					<!-- 获取EKP组织架构新增数据 -->
					<%-- <tr>
						<td class="td_normal_title" width=15%>同步EKP组织架构数据</td>
						<td  colspan=3>
						   	<ui:button text="点击同步EKP组织架构" onclick="syncEKP();"></ui:button>
						</td>
					</tr> --%>
				</table>
				
				<div style="margin-bottom: 10px;margin-top:25px">
				   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
				</div>
			</center>
		</div>
		<html:hidden property="method_GET" />
		<input type="hidden" name="modelName" value="com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting" />
		</html:form>
		<script type="text/javascript">
		
		seajs.use(['lui/jquery', 'lui/dialog'],function($, dialog){
			
			LUI.ready(function(){
				hrToEkp_display_change();
			});
			
			function validateAppConfigForm(thisObj){
				return true;
			}

			function hrToEkp_display_change(){
				var hrToEkpEnable = $('[name="value(hrToEkpEnable)"]').val();
				if(hrToEkpEnable == 'true'){
					$('[name="value(ekpToHrEnable)"]').val('false');
					setSwitchStatus(LUI('ekpToHrEnableSwitch'),false);
					//$('#syncTR').show();
				}else{
					//$('#syncTR').hide();
				}
			}
			
			function ekpToHr_display_change(){
				var ekpToHrEnable = $('[name="value(ekpToHrEnable)"]').val();
				if(ekpToHrEnable == 'true'){
					$('[name="value(hrToEkpEnable)"]').val('false');
					setSwitchStatus(LUI('hrToEkpEnableSwitch'),false);
					//$('#syncTR').hide();
				}
			}
			
			//开关设置
			function setSwitchStatus(widget,status){
				// 处理需要转义的字符
				var _property = widget.config.property.replace(/\(/g, "\\\(").replace(/\)/g, "\\\)");
				$("input[name=" + _property + "]").val(status);
				widget.checkbox.prop('checked',status);
				widget.setText(status);
				// 内容修改事件
				if(widget.config.onValueChange) {
					eval(widget.config.onValueChange);
				}
			}
			
			function syncEKP(){
				 dialog.confirm("确认是否同步EKP组织架构数据到人事组织架构(此操作会将人事组织架构数据全部覆盖)？", function(ok) {
		            if(ok == true) {
		                var del_load = dialog.loading();
		                $.ajax({
		                    url: '${LUI_ContextPath}/hr/organization/hr_organization_element/hrOrganizationElement.do?method=syncEKP',
		                    dataType: 'json',
		                    type: 'POST',
		                    success: function(data) {
		                    	console.log(data)
		                    	console.log(data.fdId)
		                        if(del_load != null) {
		                            del_load.hide();
		                        }
		                    	if(data.fdId == null || data.fdId == "" || data.fdId == "undefined"){
		                    		dialog.failure("请导入定时任务!");
		                    	}else{
		                    		jobRun(data.fdId);
		                    	}
		                    },
		                    error: function(req) {
		                        if(req.responseJSON) {
		                            var data = req.responseJSON;
		                            dialog.failure(data.title);
		                        } else {
		                            dialog.failure('操作失败');
		                        }
		                        del_load.hide();
		                    }
		                });
		            }
		        });
			}
			
			function jobRun(fdId){
				Com_OpenWindow('${LUI_ContextPath}/sys/quartz/sys_quartz_job/sysQuartzJob.do?method=run&fdId='+fdId,'_bank');
			}
			
			window.syncEKP = syncEKP;
			window.hrToEkp_display_change = hrToEkp_display_change;
			window.ekpToHr_display_change = ekpToHr_display_change;
			window.validateAppConfigForm = validateAppConfigForm;
		});
		</script>
	</template:replace>
</template:include>
