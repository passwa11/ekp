<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title">${lfn:message('third-aliMeeting:aliMeeting.setting')}</template:replace>
		<template:replace name="head">
		<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/common.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
		Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js|dialog.js", null, "js");
		</script>
		<style type="text/css"> 
			.message{
			  color:red;
			}
		</style> 
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title">${lfn:message('third-aliMeeting:aliMeeting.setting')}</span>
		</h2>
		
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
			<center>
				<div>
					<br>
					<div>
						<table  class="tb_normal" width=95%  cellpadding="20" cellspacing="20" style="width: 95%;">
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-alimeeting:aliMeeting.config.integrate.enable')}</td>
								<td> 
									<label> 
										<ui:switch property="value(aliMeeting.config.integrate.enable)" onValueChange="config_chgEnabled();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</label>
								</td>
							</tr>	
						</table>
						
						<table id='lab_detail' class="tb_normal" width=95%  cellpadding="20" cellspacing="20" style="width: 95%;">
							<tr>
								<td class="td_normal_title" width="15%">阿里云视频会议调用来源</td>
								<td colspan="3">
									<xform:radio property="value(aliMeeting.config.service.type)" required="true" onValueChange="changServiceType(this);">
										<xform:enumsDataSource enumsType="aliMeeting_config_service_type"></xform:enumsDataSource>
									</xform:radio>
									<br>
									<span id="kkTip" class="message">${lfn:message('third-alimeeting:aliMeeting.config.service.type.kk.tip')}</span>
									<span id="imeetingTip" class="message">${lfn:message('third-alimeeting:aliMeeting.config.service.type.imeeting.tip')}</span>
								</td>
							</tr>
						</table>
						
						<!-- KK配置项 -->
						<table id='typeKK' class="tb_normal" width=95%  cellpadding="20" cellspacing="20" style="width: 95%;display: none;">
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-alimeeting:aliMeeting.config.kk.inner.domain')}</td>
								<td>
									<xform:text showStatus="edit" property="value(kmss.alimeeting.kk.innerDomain)"  style="width:80%" subject="${lfn:message('third-alimeeting:aliMeeting.config.kk.inner.domain')}" />
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-alimeeting:aliMeeting.config.kk.serverjAuthId')}</td>
								<td>
									<xform:text showStatus="edit" property="value(kmss.alimeeting.kk.serverjAuthId)"  style="width:80%" subject="${lfn:message('third-alimeeting:aliMeeting.config.kk.serverjAuthId')}" />
								</td>
							</tr>
							
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-alimeeting:aliMeeting.config.kk.sererrjAuthKey')}</td>
								<td>
									<xform:text showStatus="edit" property="value(kmss.alimeeting.kk.sererrjAuthKey)"  style="width:80%" subject="${lfn:message('third-alimeeting:aliMeeting.config.kk.sererrjAuthKey')}" />
								</td>
							</tr>
						</table>
						
						<!-- 阿里云配置项 -->
						<table id='typeAlimeeting' class="tb_normal" width=95%  cellpadding="20" cellspacing="20" style="width: 95%;display: none;">
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-alimeeting:aliMeeting.config.regionID')}</td>
								<td>
									<xform:text showStatus="edit" property="value(kmss.alimeeting.regionID)"  style="width:350px" subject="${lfn:message('third-alimeeting:aliMeeting.config.regionID')}" required="true"/><br>
									<span class="message">${lfn:message('third-alimeeting:aliMeeting.config.regionID.tip')}</span>
								</td>
							</tr>
							
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-alimeeting:aliMeeting.config.address')}</td>
								<td>
									<xform:text showStatus="edit" property="value(kmss.alimeeting.address)"  style="width:350px" subject="${lfn:message('third-alimeeting:aliMeeting.config.address')}" required="true"/><br>
									<span class="message">${lfn:message('third-alimeeting:aliMeeting.config.address.tip')}</span>
								</td>
							</tr>
							
							<tr>
							   <td class="td_normal_title" width="15%">${lfn:message('third-alimeeting:aliMeeting.config.appKey')}</td>
								<td>
								    <xform:text showStatus="edit" property="value(kmss.alimeeting.accessKeyId)"  style="width:350px" subject="${lfn:message('third-alimeeting:aliMeeting.config.appKey')}"  required="true"/><br/>
									<%-- <span class="message">${lfn:message('third-alimeeting:aliMeeting.config.appKey')}</span> --%>
								</td>
							</tr>
							
							<tr>
							   <td class="td_normal_title" width="15%">${lfn:message('third-alimeeting:aliMeeting.config.accessKey')}</td>
								<td>
								    <xform:text showStatus="edit" property="value(kmss.alimeeting.accessKeySecret)"  style="width:350px" subject="${lfn:message('third-alimeeting:aliMeeting.config.accessKey')}"  required="true"/><br/>
									<%-- <span class="message">${lfn:message('third-alimeeting:aliMeeting.config.accessKey')}</span> --%>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</center>
			
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.third.alimeeting.model.AliMeetingConfig" />
			<input type="hidden" name="autoclose" value="false" />
			<center style="margin-top: 10px;">
				<!-- 保存 -->
				<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="doSubmit();"></ui:button>
			</center>
		
		</html:form>
		
	 	<script type="text/javascript">
	 		
		 	seajs.use(['lui/dialog'], function (dialog) {
				window.dialog = dialog;
			});
		 	
		 	var validation = $KMSSValidation(document.forms['sysAppConfigForm']);
		 	
		 	$KMSSValidation();
			function validateAppConfigForm(thisObj) {
				return true;
			}
		 	
		 	function changServiceType(domElement) {
		 		var aliTalbe = $("#typeAlimeeting");
		 		var serviceType = $(domElement).val();
		 		var kkTip = $("#kkTip");
		 		var imeetingTip = $("#imeetingTip");
		 		if ("1" == serviceType) {
		 			aliTalbe.show();
		 			imeetingTip.show();
		 			kkTip.hide();
		 		} else if ("0" == serviceType) {
		 			aliTalbe.hide();
		 			imeetingTip.hide();
		 			kkTip.show();
		 		}
		 	}
		 	
		 	// 保存KK配置信息
		 	function saveKKInfos(kkInfos) {
		 		var failStr = "";
		 		if (kkInfos instanceof Array && kkInfos.length > 0) {
		 			kkInfos.forEach(function (item, index) {
		 				switch (item) {
		 				case "innerDomain":
		 					if (!getKkInfo(item)) {
		 						failStr += "${lfn:message('third-alimeeting:aliMeeting.config.kk.inner.domain')}" + "、";
		 					}
		 					break;
		 				case "serverjAuthId": 
		 					if (!getKkInfo(item)) {
		 						failStr += "${lfn:message('third-alimeeting:aliMeeting.config.kk.serverjAuthId')}" + "、";
		 					}
		 					break;
		 				case "sererrjAuthKey":
		 					if (!getKkInfo(item)) {
		 						failStr += "${lfn:message('third-alimeeting:aliMeeting.config.kk.sererrjAuthKey')}" + "、";
		 					}
		 					break;
		 				}
			 		})
		 		}
		 		if (failStr) {
		 			dialog.alert(failStr.substring(0, failStr.length - 1) + " 等配置信息未获取成功，请检查KK一体化是否配置正确，再重新保存");
		 		} else {
		 			Com_Submit(document.sysAppConfigForm, 'update');
		 		}
		 	}
		 	
		 	function getKkInfo(infoType) {
		 		var res = false;
		 		$.ajax({
		   			url : "${LUI_ContextPath}/third/im/kk/config.do?method=getKkConfigInfo",
					type : "POST",
					async : false,    //用同步方式
					data:{
						infoType : infoType
					},
					success: function(result) {
						 result = eval('(' + result + ')');
						 if (result.value) {
							 var HTMLStr = "[name='value(kmss.alimeeting.kk." + infoType + ")']";
							 $(HTMLStr).val(result.value);
							 res = true;
						 }
					 }
		   		})
		   		return res;
		 	};
	 	
			function config_chgEnabled() {
				var kkTip = $("#kkTip");
		 		var imeetingTip = $("#imeetingTip");
				var cfgDetail = $("#lab_detail");
				var kkTalbe = $("#typeKK");
		 		var aliTalbe = $("#typeAlimeeting");
		 		var typeValue = $("[name='value(aliMeeting.config.service.type)']:checked").val();
				var isChecked = "true" == $("input[name='value\\\(aliMeeting.config.integrate.enable\\\)']").val();
				if (isChecked) {
					cfgDetail.show();
					if ("0" == typeValue) {
						kkTip.show();
						imeetingTip.hide();
						aliTalbe.hide();
					} else if ("1" == typeValue) {
						kkTip.hide();
						imeetingTip.show();
						aliTalbe.show();
					}
				} else {
					cfgDetail.hide();
					aliTalbe.hide();
				}

				cfgDetail.find("input").each(function() {
					if(isChecked){
						$(this).attr("disabled", false);
					}else{
						if($(this).val()==''){
							$(this).attr("disabled", true);
						}
					}
				});
				
				kkTalbe.find("input").each(function() {
					if(isChecked){
						$(this).attr("disabled", false);
					}else{
						if($(this).val()==''){
							$(this).attr("disabled", true);
						}
					}
				});
				
				aliTalbe.find("input").each(function() {
					if(isChecked){
						$(this).attr("disabled", false);
					}else{
						if($(this).val()==''){
							$(this).attr("disabled", true);
						}
					}
				});
			}

			function doSubmit() {
				var isCheckedValue = $("input[name='value\\\(aliMeeting.config.integrate.enable\\\)']").val();
				
				var fdTypeValue = $("[name='value(aliMeeting.config.service.type)']:checked").val();
				if ("1" == fdTypeValue) {
					if ("true" == isCheckedValue) {
						dialog.confirm("生成相关会议数据之后，更换阿里云地域Region ID会导致旧数据丢失的问题，请确认地域Region ID是否填写正确!若填写无误，请点确定。", function(result) {
							if (result) {
								_removeRequireValidate(fdTypeValue);
								Com_Submit(document.sysAppConfigForm, 'update');
							}
						});
					} else {
						Com_Submit(document.sysAppConfigForm, 'update');
					}
				} else if ("0" == fdTypeValue) {
					_removeRequireValidate(fdTypeValue);
					var propertyArray = ['innerDomain', 'serverjAuthId', 'sererrjAuthKey'];
					saveKKInfos(propertyArray);
				}
			}
			
			//移除必填校验
			function _removeRequireValidate(type) {
				var domObjArray = new Array();
				
				if ("0" == type) {
					domObjArray.push($("[name='value(kmss.alimeeting.regionID)']")[0]);
					domObjArray.push($("[name='value(kmss.alimeeting.address)']")[0]);
					domObjArray.push($("[name='value(kmss.alimeeting.accessKeyId)']")[0]);
					domObjArray.push($("[name='value(kmss.alimeeting.accessKeySecret)']")[0]);
				}
				
				domObjArray.forEach(function(item, index) {
					validation.removeElements(item,'required');
				});
			}
			
			function clearCache(){
				Com_OpenWindow('${LUI_ContextPath }/sys/common/configcacheclear.jsp?s_css=default','_blank','max');
			}
			
			LUI.ready(function() {
				config_chgEnabled();
			});
		</script>
	</template:replace>
</template:include>
