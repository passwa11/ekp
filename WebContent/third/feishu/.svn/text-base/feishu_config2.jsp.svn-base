<%@page import="com.landray.kmss.third.weixin.model.WeixinConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message key="third.feishu.config.setting" bundle="third-feishu"/></template:replace>
	
	<template:replace name="head">
		<style>
			body{padding:20px;}
			.message{margin-top:10px;}
			a.feishuLink{color:#15a4fa; text-decoration: underline;}
			a.feishuLink:hover{color:red; }
		</style>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin:10px 0">
			<span class="profile_config_title"><bean:message key="third.feishu.config.setting" bundle="third-feishu"/></span>
		</h2>
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do?autoclose=false" onsubmit="return validateAppConfigForm(this);">
			<center>
				<table id="feishuBaseTable" class="tb_normal" width=100%>
					<tr id="feishuEnableTR">
						<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.feishuEnabledTitle" bundle="third-feishu"/></td>
						<td>
							<ui:switch property="value(feishuEnabled)" onValueChange="feishu_display_change();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.appid" bundle="third-feishu"/></td>
						<td>
							<xform:text property="value(appid)" style="width:85%;" showStatus="edit" required="false" subject="${lfn:message('third-feishu:third.feishu.config.appid') }" validators="feishuRequire"/>
							<span class="txtstrong">*</span>
							<div class="message"><bean:message key="third.feishu.config.appid.tip" bundle="third-feishu"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.appsecret" bundle="third-feishu"/></td>
						<td>
							<xform:text property="value(appsecret)" subject="${lfn:message('third-feishu:third.feishu.config.appsecret') }" required="false" style="width:85%" showStatus="edit" validators="feishuRequire"/>
							<span class="txtstrong">*</span><br/>
							<div class="message"><bean:message key="third.feishu.config.appsecret.tip" bundle="third-feishu"/></div>
						</td>
					</tr>
					
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.wxCallbackurlTitle" bundle="third-feishu"/></td>
						<td>
							<input type="hidden" name="wxUrlSuffix" value="/resource/third/wx/cpEndpoint.do?method=service">
							<xform:text property="value(wxCallbackurl)" subject="${lfn:message('third-feishu:third.feishu.config.wxCallbackurlSubject') }" required="false" style="width:85%" showStatus="readOnly" />
							<div class="message"><bean:message key="third.feishu.config.wxCallbackurlMsg" bundle="third-feishu"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.wxTokenTitle" bundle="third-feishu"/></td>
						<td>
							<xform:text property="value(wxToken)" subject="${lfn:message('third-feishu:third.feishu.config.wxTokenSubject') }" required="false" style="width:85%" showStatus="edit" validators="feishuRequire"/>
							<span class="txtstrong">*</span>
							<ui:button text="${lfn:message('third-feishu:third.feishu.config.wxTokenBtn') }" onclick="window.random_token();validation.validateElement(document.getElementsByName('value(wxToken)')[0]);" style="vertical-align: top;"></ui:button>
							<div class="message"><bean:message key="third.feishu.config.wxTokenMsg" bundle="third-feishu"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.wxAeskeyTitle" bundle="third-feishu"/></td>
						<td>
							<xform:text property="value(wxAeskey)" subject="${lfn:message('third-feishu:third.feishu.config.wxAeskeySubject') }" required="false" style="width:85%" showStatus="edit" validators="feishuRequire"/>
							<span class="txtstrong">*</span>
							<ui:button text="${lfn:message('third-feishu:third.feishu.config.wxAeskeyBtn') }" onclick="window.random_AESKey();validation.validateElement(document.getElementsByName('value(wxAeskey)')[0]);" style="vertical-align: top;"></ui:button>
							<div class="message"><bean:message key="third.feishu.config.wxAeskeyMsg" bundle="third-feishu"/></div>
							<div class="txtstrong"><bean:message key="third.feishu.config.wxAeskeyMsg.tip" bundle="third-feishu"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.wxOauth2EnabledTitle" bundle="third-feishu"/></td>
						<td>
							<ui:switch property="value(wxOauth2Enabled)" onValueChange="" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div class="message"><bean:message key="third.feishu.config.wxOauth2EnabledMsg" bundle="third-feishu"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.wxPcScanLoginEnabledTitle" bundle="third-feishu"/></td>
						<td>
							<ui:switch property="value(wxPcScanLoginEnabled)" onValueChange="" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div class="message"><bean:message key="third.feishu.config.wxPcScanLoginEnabledMsg" bundle="third-feishu"/></div>
						</td>
					</tr>
					<%-- 
					<tr>
						<td class="td_normal_title" width="15%">代理服务器设置</td>
						<td>
							<xform:text property="value(wxProxy)" subject="代理服务器地址" required="false" style="width:85%" showStatus="edit" validators="wxProxy"/>
							<div class="message">适用于EKP服务器无法直接访问微信服务器的系统,地址格式为:host:port</div>
						</td>
					</tr>
					--%>
				</table>
				<br/>
				<table id="wxTodoTable" class="tb_normal" width=100%>
					<tr id="wxTodoTR">
						<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.wxTodoEnabledTitle" bundle="third-feishu"/></td>
						<td>
							<ui:switch property="value(wxTodoEnabled)" onValueChange="wxTodo_display_change()" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.wxTodoType2EnabledTitle" bundle="third-feishu"/></td>
						<td>
							<ui:switch property="value(wxTodoType2Enabled)" onValueChange="" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div class="message"><bean:message key="third.feishu.config.wxTodoType2EnabledMsg" bundle="third-feishu"/></div>
						</td>
					</tr>
					
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.wxAgentidTitle" bundle="third-feishu"/></td>
						<td>
							<xform:text property="value(wxAgentid)" subject="${lfn:message('third-feishu:third.feishu.config.wxAgentidSubject') }" required="false" 
								style="width:85%" showStatus="edit" validators="wxTodoRequire"/>
							<span class="txtstrong">*</span>
							<div class="message"><bean:message key="third.feishu.config.wxAgentidMsg" bundle="third-feishu"/><a class="feishuLink" href="https://qy.weixin.qq.com/" target="_blank"><bean:message key="third.feishu.config.wxAgentidMsg.feishuLink" bundle="third-feishu"/></a><bean:message key="third.feishu.config.wxAgentidMsg.tip" bundle="third-feishu"/></div>
						</td>
					</tr>
				</table>
				<br/>
				<table id="wxOmsTable" class="tb_normal" width=100%>
					<tr id="wxOmsTR">
						<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.wxOmsOutEnabledTitle" bundle="third-feishu"/></td>
						<td>
							<ui:switch property="value(wxOmsOutEnabled)" onValueChange="wxOms_display_change()" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.wxOrgIdTitle" bundle="third-feishu"/></td>
						<td>
							<xform:address propertyId="value(wxOrgId)" propertyName="value(wxOrgName)" 
								mulSelect="true" orgType="ORG_TYPE_ORGORDEPT" showStatus="edit" onValueChange="wxOrgId_display_change()" 
								style="width:75%" subject="${lfn:message('third-feishu:third.feishu.config.wxOrgIdSubject') }" validators="wxOmsRequire">
							</xform:address>
							<span class="txtstrong">*</span>
							<ui:button text="${lfn:message('third-feishu:third.feishu.config.wxOrgIdBtn') }" onclick="cleanTime();" style="vertical-align: top;"></ui:button>
							<div class="message"><bean:message key="third.feishu.config.wxOrgIdMsg" bundle="third-feishu"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.wxOmsRootFlagTitle" bundle="third-feishu"/></td>
						<td>
							<ui:switch property="value(wxOmsRootFlag)" onValueChange="" 
								enabledText="${lfn:message('third-feishu:third.feishu.config.wxOmsRootFlag.sync') }" disabledText="${lfn:message('third-feishu:third.feishu.config.wxOmsRootFlag.asyn') }"></ui:switch>
							<div class="message"><bean:message key="third.feishu.config.wxOmsRootFlagMsg" bundle="third-feishu"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.wxOmsOrgPersonHandleTitle" bundle="third-feishu"/></td>
						<td>
							<xform:radio property="value(wxOmsOrgPersonHandle)">
								<xform:enumsDataSource enumsType="feishu_oms_sync_handle" />
							</xform:radio>
							<div class="message"><bean:message key="third.feishu.config.wxOmsOrgPersonHandleMsg" bundle="third-feishu"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.wxRootIdTitle" bundle="third-feishu"/></td>
						<td>
							<xform:text property="value(wxRootId)" showStatus="edit" style="width:65%"/>
							<div class="message"><bean:message key="third.feishu.config.wxRootIdMsg" bundle="third-feishu"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.wxLoginNameTitle" bundle="third-feishu"/></td>
						<td>
							<xform:text property="value(wxLoginName)" showStatus="noShow"/>
							<div id="ln"><bean:message key="third.feishu.config.wxLoginNameLn" bundle="third-feishu"/></div>
							<div class="message"><bean:message key="third.feishu.config.wxLoginNameMsg" bundle="third-feishu"/></div>
						</td>
					</tr>
				</table>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.third.weixin.model.WeixinConfig" />
			<center style="margin:20px 0;">
				<!-- 保存 -->
				<ui:button text="${lfn:message('button.save')}" onclick="window.wxSubmit();" width="120" height="35"></ui:button>
			</center>
		</html:form>
		<script type="text/javascript">
			var validation = $KMSSValidation();
			var woi = "<%=WeixinConfig.newInstance().getWxOrgId()   %>";
			function setLoginName(){
				var value = $('[name="value(wxLoginName)"]').val();
				if(value==""){
					$('[name="value(wxLoginName)"]').val("loginname");
				}else{
					if(value=="id"){
						$("#ln").empty();
						$("#ln").text("<bean:message key='third.feishu.config.ekpPrimary' bundle='third-feishu'/>");
					}else{
						$("#ln").empty();
						$("#ln").text("<bean:message key='third.feishu.config.wxLoginNameLn' bundle='third-feishu'/>");
						$('[name="value(wxLoginName)"]').val("loginname");
					}
				}
			}
			function cleanTime(){
				if(confirm("是否要清理同步时间戳")){
					var url = '<c:url value="/third/wx/weixinSynchroOrgCheck.do?method=cleanTime" />';
					$.ajax({
					   type: "POST",
					   url: url,
					   async:false,
					   dataType: "json",
					   success: function(data){
							if(data.status=="1"){
								alert("清理同步时间戳成功");
							}else{
								alert(data.msg);
							}
					   }
					});
				}
			}
			function wxOrgId_display_change(){
				var value = $('[name="value(wxOrgId)"]').val();
				if(value!=""&&woi!=""&&woi!=value){
					alert("<bean:message key='third.feishu.config.config.org.tip' bundle='third-feishu'/>");
				}
			}
			seajs.use(['lui/jquery'],function($){
					function feishu_display_change(){
						var value = $('[name="value(feishuEnabled)"]').val();
						if(value == 'true'){
							$('#feishuBaseTable tr[id!="feishuEnableTR"]').show();
							$('#wxTodoTable,#wxOmsTable').show();
						}else{
							$('#feishuBaseTable tr[id!="feishuEnableTR"]').hide();
							$('#wxTodoTable,#wxOmsTable').hide();
						}
					}

					function wxTodo_display_change(){
						var value = $('[name="value(wxTodoEnabled)"]').val();
						if(value == 'true'){
							$('#wxTodoTable tr[id!="wxTodoTR"]').show();
						}else{
							$('#wxTodoTable tr[id!="wxTodoTR"]').hide();
						}
					}

					function wxOms_display_change(){
						var value = $('[name="value(wxOmsOutEnabled)"]').val();
						if(value == 'true'){
							$('#wxOmsTable tr[id!="wxOmsTR"]').show();
						}else{
							$('#wxOmsTable tr[id!="wxOmsTR"]').hide();
						}
					}
					
					//生成随机码
					function config_randomAlphanumeric(charsLength,chars) { 
						var length = charsLength;
						if (!chars){
							var chars = "abcdefghijkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";
						}
						var randomChars = ""; 
						for(x=0; x<length; x++) {
							var i = Math.floor(Math.random() * chars.length); 
							randomChars += chars.charAt(i); 
						}
						return randomChars; 
					} 
					//设置微信回调URL
					function config_feishu_callbackurl(){
						var wxDomain = $("[name='value(wxDomain)']") && $("[name='value(wxDomain)']").val() || '',
							wxUrlSuffix = $("[name='wxUrlSuffix']").val();
						$('[name="value(wxCallbackurl)"]').val(wxDomain + wxUrlSuffix);
					}
					function config_feishu_dns_getUrl(){
						var protocol = location.protocol,
							host = location.host,
							contextPath = seajs.data.env.contextPath;
						$('[name="value(wxDomain)"]').val(protocol + '//' + host + contextPath );
					}
					//设置待办消息类型
					function config_feishu_notify_type(){
						var type = document.getElementsByName("value(wxNotifyType)"),
							checked = false;
						for(var i = 0; i < type.length; i++) {
							if(type[i].checked) {
								checked = true;
								break;
							}
						}
						if(!checked){
							type[0].checked = true;
						}
					}
					//生成随机Token
					function random_token(){
						$("[name='value(wxToken)']").val( config_randomAlphanumeric(16) ); 
					}
					//生成随机AESKey
					function random_AESKey(){
						$("[name='value(wxAeskey)']").val( config_randomAlphanumeric(43) ); 
					}
					
					validation.addValidator('feishuRequire',"<bean:message key='third.weixin.work.config.notnull' bundle='third-feishu-work'/>",function(v, e, o){
						var feishuEnabled = $('[name="value(feishuEnabled)"]').val();
						if(feishuEnabled == 'true' && !v){
							var validator = this.getValidator('feishuRequire'),
								error = "<bean:message key='third.weixin.work.config.notnull' bundle='third-feishu-work'/>";
							if($(e).attr('subject')){
								error = $(e).attr('subject') + error;
							}	
							validator.error = error;
							return false;
						}
						return true;
					});

					validation.addValidator('wxTodoRequire','{name}'+"<bean:message key='third.weixin.work.config.notnull' bundle='third-feishu-work'/>",function(v, e, o){
						var feishuEnabled = $('[name="value(feishuEnabled)"]').val();
						if(feishuEnabled != 'true'){
							return true;
						}
						var todoEnabled = $('[name="value(wxTodoEnabled)"]').val();
						return (todoEnabled == 'true' && !v) ? false : true;
					});

					validation.addValidator('wxOmsRequire','{name}'+"<bean:message key='third.weixin.work.config.notnull' bundle='third-feishu-work'/>",function(v, e, o){
						var feishuEnabled = $('[name="value(feishuEnabled)"]').val();
						if(feishuEnabled != 'true'){
							return true;
						}
						var omsEnabled = $('[name="value(wxOmsOutEnabled)"]').val();
						return (omsEnabled == 'true' && !v) ? false : true;
					});
					
					/* validation.addValidator('wxProxy','{name}不合法',function(v,e,o){
						var wxProxy = $('[name="value(wxProxy)"]').val();
						if(wxProxy && ! /(^:)*:\d+/.test(v)  ){
							return false;
						}
						return true;
					}); */

					LUI.ready(function(){
						feishu_display_change();
						wxTodo_display_change();
						wxOms_display_change();
						config_feishu_callbackurl();
						//config_feishu_notify_type();
						setLoginName();
					});
					
					window.validateAppConfigForm = function(){
						return true;
					};
					
					window.wxSubmit = function(){
						var feishuEnabled = $('[name="value(feishuEnabled)"]').val();
						if(!feishuEnabled || feishuEnabled == 'false'){
							$('[name="value(wxOauth2Enabled)"]').val('false');
							$('[name="value(wxTodoEnabled)"]').val('false');
							$('[name="value(wxTodoType2Enabled)"]').val('false');
							$('[name="value(wxOmsOutEnabled)"]').val('false');
						}
						//剔除前后空格
						$('[name="value(wxCorpid)"]').val($.trim($('[name="value(wxCorpid)"]').val()));
						$('[name="value(wxAgentid)"]').val($.trim($('[name="value(wxAgentid)"]').val()));
						$('[name="value(wxCorpsecret)"]').val($.trim($('[name="value(wxCorpsecret)"]').val()));
						$('[name="value(wxDomain)"]').val($.trim($('[name="value(wxDomain)"]').val()));
						$('[name="value(wxToken)"]').val($.trim($('[name="value(wxToken)"]').val()));
						$('[name="value(wxAeskey)"]').val($.trim($('[name="value(wxAeskey)"]').val()));
						$('[name="value(wxRootId)"]').val($.trim($('[name="value(wxRootId)"]').val()));
						
						Com_Submit(document.sysAppConfigForm, 'update');
					};
					
					window.feishu_display_change = feishu_display_change;
					window.wxTodo_display_change = wxTodo_display_change;
					window.wxOms_display_change = wxOms_display_change;
					window.random_token = random_token;
					window.random_AESKey = random_AESKey;
					window.config_feishu_callbackurl = config_feishu_callbackurl;
					window.config_feishu_dns_getUrl = config_feishu_dns_getUrl;
				});
		</script>
	</template:replace>	
</template:include>