<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title">${lfn:message('third-ecalendar:exchange.setting')}</template:replace>
		<template:replace name="head">
		<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/common.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
		Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js", null, "js");
		</script>
		<style type="text/css"> 
			.tb_normal td {
    //padding: 5px;
    //border: 1px #d2d2d2 solid;
    //word-break: break-all;
}
		</style> 
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title">${lfn:message('third-ecalendar:exchange.setting')}</span>
		</h2>
		
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
					  <td class="td_normal_title" width=15%>
						${lfn:message('third-ecalendar:exchange.integrate.enable')}
					  </td>
					  <td>
							<ui:switch property="value(calendar.integrate.ecalendar.enabled)" onValueChange="config_chgEnabled();"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					  </td>
					  </tr>
					  </table>
							<table class="tb_normal" id='lab_detail' width=95%  cellpadding="20" cellspacing="20" style="display:none; width: 95%;">
								<tr>
		<td class="td_normal_title" width="15%">${lfn:message('third-ecalendar:exchange.webservice.url')}</td>
		<td>
			<xform:text showStatus="edit" property="value(calendar.integrate.ecalendar.webService.url)" style="width:350px" subject="${lfn:message('third-ecalendar:exchange.webservice.url')}" required="true"/><br>
			
			${lfn:message('third-ecalendar:exchange.webservice.url.tip')}
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">${lfn:message('third-ecalendar:exchange.mail.domain')}</td>
		<td>
			<xform:text showStatus="edit" property="value(calendar.integrate.ecalendar.domain)" style="width:350px" subject="${lfn:message('third-ecalendar:exchange.mail.domain')}" required="true"/><br>
			
			${lfn:message('third-ecalendar:exchange.mail.domain.tip')}
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">${lfn:message('third-ecalendar:exchange.server.version')}</td>
		<td>
			<xform:select property="value(calendar.integrate.ecalendar.version)" showPleaseSelect="false" showStatus="edit">
				<xform:enumsDataSource enumsType="third_ecalendar_exchange_version" />
				
			</xform:select>
			<br/>
			<span class="txtstrong">
			${lfn:message('third-ecalendar:exchange.version.tip')}
			</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">${lfn:message('third-ecalendar:exchange.integrate.sso.style')}</td>
		<td>
			<xform:text showStatus="edit" property="value(calendar.integrate.sso.style)" style="width:50px" subject="${lfn:message('third-ecalendar:exchange.integrate.sso.style')}" /><br>
			<span class="txtstrong">
			${lfn:message('third-ecalendar:exchange.integrate.sso.style.tip')}
			</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">${lfn:message('third-ecalendar:exchange.auth.scheme')}</td>
		<td>
			<xform:select property="value(calendar.integrate.ecalendar.authScheme)" showPleaseSelect="true" showStatus="edit">
				<xform:enumsDataSource enumsType="third_ecalendar_exchange_authScheme" />
			</xform:select>
			<span class="txtstrong">
			${lfn:message('third-ecalendar:exchange.auth.scheme.tip2')}
			</span>
			<br/>
			<span>
			${lfn:message('third-ecalendar:exchange.auth.scheme.tip')}
			</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">${lfn:message('third-ecalendar:exchange.synchro.time.offset')}</td>
		<td>
			<xform:text showStatus="edit" property="value(calendar.integrate.ecalendar.offset)" style="width:50px" subject="${lfn:message('third-ecalendar:exchange.synchro.time.offset')}" validators="digits"/>${lfn:message('third-ecalendar:exchange.synchro.time.offset.hour')}<br>
			<span class="txtstrong">
			${lfn:message('third-ecalendar:exchange.synchro.time.offset.tip')}
			</span>
		</td>
	</tr>
	<tr>
			<td class="td_normal_title" width="15%">${lfn:message('third-ecalendar:exchange.mail.enable.schedule.synchronization')}</td>
			<td> 
				<label> 
					<ui:switch property="value(calendar.integrate.calendar.synchro)"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
				</label>
				<span class="txtstrong">
				${lfn:message('third-ecalendar:exchange.mail.enable.schedule.synchronization.tip')}
				</span>
			</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">${lfn:message('third-ecalendar:exchange.mail.enable')}</td>
		<td> 
			<label> 
				<ui:switch property="value(calendar.integrate.exchange.enabled)" onValueChange="config_chgEnabled();"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
			</label>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">${lfn:message('third-ecalendar:exchange.mail.addDomain')}</td>
		<td> 
			<label> 
				<ui:switch property="value(calendar.integrate.mail.addDomain)"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
			</label>
			<span class="txtstrong">
			${lfn:message('third-ecalendar:exchange.mail.addDomain.tip')}
			</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">${lfn:message('third-ecalendar:exchange.sso.addDomain')}</td>
		<td> 
			<label> 
				<ui:switch property="value(calendar.integrate.sso.addDomain)"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
			</label>
			
		</td>
	</tr>
	<kmss:ifModuleExist path="/third/emeeting/">
		<tr>
			<td class="td_normal_title" width="15%">${lfn:message('third-ecalendar:exchange.mail.enable.meeting.synchronization')}</td>
			<td> 
				<label> 
					<ui:switch property="value(calendar.integrate.meeting.synchro)"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
				</label>
				<span class="txtstrong">
				   ${lfn:message('third-ecalendar:exchange.mail.enable.meeting.synchronization.tip')}
				</span>
			</td>
		</tr>
	</kmss:ifModuleExist>
	
	<tr>
		<td class="td_normal_title" width="15%">${lfn:message('third-ecalendar:exchange.mail.udateMappingPassword')}</td>
			<td> 
				<label> 
					<ui:switch property="value(calendar.integrate.password.update)"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
				</label>
				<span class="txtstrong">
					${lfn:message('third-ecalendar:exchange.mail.udateMappingPassword.tip')}
				</span>
		</td>
	</tr>
	<%--
	<tr>
			<td class="td_normal_title" width="15%">${lfn:message('third-ecalendar:exchange.mail.circuitBreaker.enable')}</td>
			<td colspan="3">
			<label>
				<ui:switch checked="true" property="value(exchange.mail.CircuitBreaker.enable)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
			</label>
			
			<span class="txtstrong">${lfn:message('third-ecalendar:exchange.mail.circuitBreaker.tip')}</span>
			
			</td>
	</tr>
	
	<tr>
			<td class="td_normal_title" width="15%">${lfn:message('third-ecalendar:exchange.calendar.circuitBreaker.enable')}</td>
			<td colspan="3">
			<label>
				<ui:switch property="value(exchange.calendar.CircuitBreaker.enable)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
			</label>
			
			<span class="txtstrong">${lfn:message('third-ecalendar:exchange.calenadr.circuitBreaker.tip')}</span>
			
			</td>
	</tr>
	--%>		
	<tr>
		<td colspan="2" align="center">
			<input type="button" class="btnopt" style="width:120px;height:30px;" value="${lfn:message('third-ecalendar:exchange.calendar.test')}" onclick="testConnect('calendar');">&nbsp;&nbsp;
			<input type="button" class="btnopt" style="width:120px;height:30px;" value="${lfn:message('third-ecalendar:exchange.mail.test')}" onclick="testConnect('mailCount');">
		</td>
		
	</tr>
							</table>
							
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.third.ecalendar.EcalendarConfig" />
			<input type="hidden" name="autoclose" value="false" />
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
				var isChecked = "true" == $("input[name='value\\\(calendar.integrate.ecalendar.enabled\\\)']").val();
				if (isChecked) {
					cfgDetail.show();
				} else {
					cfgDetail.hide();
					$("input[name='value\\\(calendar.integrate.exchange.enabled\\\)']").val("false");
				}

				cfgDetail.find("input").each(function() {
					//$(this).attr("disabled", !isChecked);
					if(isChecked){
						$(this).attr("disabled", false);
					}else{
						if($(this).val()==''){
							$(this).attr("disabled", true);
						}
					}
				});
			}

			function myShowModalDialog(url, width, height, fn) {
			    if (navigator.userAgent.indexOf("Chrome") > 0) {
			        window.returnCallBackValue354865588 = fn;
			        var paramsChrome = 'height=' + height + ', width=' + width + ', top=' + (((window.screen.height - height) / 2) - 50) +
			            ',left=' + ((window.screen.width - width) / 2) + ',toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no';
			        window.open(url, "newwindow", paramsChrome);
			    }
			    else {
			        var params = 'dialogWidth:' + width + 'px;dialogHeight:' + height + 'px;sstatus:1; help:0; resizable:1';
			        var tempReturnValue = window.showModalDialog(url, "", params);
			        if(fn){
			        fn.call(window, tempReturnValue);
			        }
			    }
			}

			function testConnect(type){
				var webServiceUrl = document.getElementsByName("value(calendar.integrate.ecalendar.webService.url)")[0].value;
				var domain= document.getElementsByName("value(calendar.integrate.ecalendar.domain)")[0].value;
				var version= document.getElementsByName("value(calendar.integrate.ecalendar.version)")[0].value;
				//version = "Exchange2007_SP1";
				if(webServiceUrl!="" && domain!=""){
					var style = "dialogWidth:300; dialogHeight:300; status:1; help:0; resizable:1";
					var url = "<c:url value="/resource/jsp/frame.jsp"/>";
					var parameter = "webServiceUrl="+webServiceUrl+"&domain="+domain+"&version="+version+"&type="+type;
					url = Com_SetUrlParameter(url, "url","<c:url value='/third/ecalendar/testConnect.jsp?"+parameter+"'/>");
					var rtnVal = myShowModalDialog(url, 500,300);
				}else{
					alert("<bean:message key='ecalendarBindData.testConnect.tip' bundle='third-ecalendar'/>");
				}
			}		
			
			

			LUI.ready(function() {
				config_chgEnabled();
			});
		</script>
	</template:replace>
</template:include>
