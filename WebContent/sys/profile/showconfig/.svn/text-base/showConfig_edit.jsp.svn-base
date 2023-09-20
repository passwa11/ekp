<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">	
	<template:replace name="content">
	<style type="text/css">
		.paging_setting label { 
		    padding-right: 50px;
	    }
	    
	    .count_switch label{
	    	padding-right: 40px;
	    }
	    
	    .default_load_data_volume label{
	    	padding-right: 20px;
	    }
	</style>
		<html:form action="/sys/profile/showConfig.do">
			<h2 align="center" style="margin: 10px 0"><span
				class="profile_config_title"><bean:message bundle="sys-profile" key="sys.showConfig"/></span></h2>
			<center>
             	<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width=15% style="height:200px;text-align: center">
							<bean:message bundle="sys-profile" key="sys.showConfig.pagingSetting"/>
						</td>
						<td colspan="3" class="paging_setting" style="height:200px">
							<xform:radio property="value(pagingSetting)" value="${showConfig.pagingSetting}" onValueChange="pagingSettingChange">
								<xform:enumsDataSource enumsType="sys_show_config_paging_setting" />
							</xform:radio>
							<div>
								<img id="paging_setting_img">
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15% style="height:160px;text-align: center">
							<bean:message bundle="sys-profile" key="sys.showConfig.countSwitch"/>
						</td>
						<td colspan="3" class="count_switch" style="height:160px">
							<xform:radio property="value(countSwitch)" value="${showConfig.countSwitch}" onValueChange="countSwitchChange" >
								<xform:enumsDataSource enumsType="sys_show_config_count_switch" />
							</xform:radio>
							<br>
							<div>
								<img id="count_switch_img">
							</div>
						</td>
						
					</tr>
					<tr>
						<td class="td_normal_title" width=15% style="height:40px;text-align: center">
							<bean:message bundle="sys-profile" key="sys.showConfig.loadDataVolume"/>
						</td>
						<td colspan="3" class="default_load_data_volume">
							<xform:radio property="value(loadDataVolume)" value="${showConfig.loadDataVolume}">
								<xform:enumsDataSource enumsType="sys_show_config_load_data_volume" />
							</xform:radio>
						</td>
					</tr>
				</table>
				<div style="margin-bottom: 10px;margin-top:25px">
	   				<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="submint();" order="1" ></ui:button>
				</div>
			</center>
		<html:hidden property="method_GET" />
		<input type="hidden" name="modelName" value="${showConfig.modelName }" />
		</html:form>
		<script>
			$KMSSValidation();
			function submint() {
				Com_Submit(document.sysAppConfigForm, 'update');
			}
			
			window.onload = function(){
				var pagingSetting = "${showConfig.pagingSetting}";
				var countSwitch = "${showConfig.countSwitch}"
				pagingSettingChange(pagingSetting);
				countSwitchChange(countSwitch);
			}
			
			function pagingSettingChange(value){
				var src;
				if(value == "1"){
					src = Com_Parameter.ContextPath + "sys/profile/showconfig/images/paging_setting_default.png";
				}else if(value == "2"){
					src = Com_Parameter.ContextPath + "sys/profile/showconfig/images/paging_setting_simple.png";
				}else if(value == "3"){
					src = Com_Parameter.ContextPath + "sys/profile/showconfig/images/paging_setting_can_change.png";
				}
				$("#paging_setting_img").attr("src",src);
			}
			
			function countSwitchChange(value){
				var src ;
				if(value == "1"){
					src = Com_Parameter.ContextPath + "sys/profile/showconfig/images/count_switch_on.png";
					$("#count_switch_img").removeAttr("style");
				}else if(value == "0"){
					src = Com_Parameter.ContextPath + "sys/profile/showconfig/images/count_switch_off.png";
					$("#count_switch_img").attr("style","padding-left:86px;");
				}
				$("#count_switch_img").attr("src",src);
			}
		</script>
	</template:replace>
</template:include>