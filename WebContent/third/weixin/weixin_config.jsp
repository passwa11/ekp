<%@page import="com.landray.kmss.third.weixin.model.WeixinConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message key="third.wx.config.setting" bundle="third-weixin"/></template:replace>
	<template:block name="path" >
		<span class=txtlistpath><bean:message key="third.wx.config.currurl" bundle="third-weixin"/>：<bean:message key="third.wx.config.setting" bundle="third-weixin"/></span>
	</template:block>
	<template:replace name="head">
		<style>
			body{padding:20px;}
			.message{margin-top:10px;}
			a.weixinLink{color:#15a4fa; text-decoration: underline;}
			a.weixinLink:hover{color:red; }
		</style>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin:10px 0">
			<span class="profile_config_title"><bean:message key="third.wx.config.setting" bundle="third-weixin"/></span>
		</h2>
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do?autoclose=false" onsubmit="return validateAppConfigForm(this);">
			<center>
				<table id="wxBaseTable" class="tb_normal" width=100%>
					<tr id="wenxinEnableTR">
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxEnabledTitle" bundle="third-weixin"/></td>
						<td>
							<ui:switch property="value(wxEnabled)" onValueChange="wx_display_change();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div class="txtstrong"><bean:message key="third.wx.config.wxEnabledTitle.tip" bundle="third-weixin" /></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxCorpidTitle" bundle="third-weixin"/></td>
						<td>
							<xform:text property="value(wxCorpid)" style="width:85%;" showStatus="edit" required="false" subject="${lfn:message('third-weixin:third.wx.config.wxCorpidSubject') }" validators="wxRequire"/>
							<span class="txtstrong">*</span>
							<div class="message"><bean:message key="third.wx.config.wxCorpidMsg" bundle="third-weixin"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxCorpsecretTitle" bundle="third-weixin"/></td>
						<td>
							<xform:text property="value(wxCorpsecret)" subject="${lfn:message('third-weixin:third.wx.config.wxCorpsecretSubject') }" required="false" style="width:85%" showStatus="edit" validators="wxRequire"/>
							<span class="txtstrong">*</span><br/>
							<div class="message"><bean:message key="third.wx.config.wxCorpsecretMsg" bundle="third-weixin"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxDomainTitle" bundle="third-weixin"/></td>
						<td>
							<xform:text property="value(wxDomain)" subject="${lfn:message('third-weixin:third.wx.config.wxDomainSubject') }" 
								required="false" style="width:85%" showStatus="edit" onValueChange="config_wx_callbackurl();"/>&nbsp;&nbsp;
							<ui:button text="${lfn:message('third-weixin:third.wx.config.wxDomainBtn') }" onclick="config_wx_dns_getUrl();config_wx_callbackurl();" style="vertical-align: top;"></ui:button>	
							<div class="message"><bean:message key="third.wx.config.wxDomainMsg" bundle="third-weixin"/></div>
							<div class="txtstrong"><bean:message key="third.wx.config.wxDomainMsg.tip" bundle="third-weixin"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxCallbackurlTitle" bundle="third-weixin"/></td>
						<td>
							<input type="hidden" name="wxUrlSuffix" value="/resource/third/wx/cpEndpoint.do?method=service">
							<xform:text property="value(wxCallbackurl)" subject="${lfn:message('third-weixin:third.wx.config.wxCallbackurlSubject') }" required="false" style="width:85%" showStatus="readOnly" />
							<div class="message"><bean:message key="third.wx.config.wxCallbackurlMsg" bundle="third-weixin"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxTokenTitle" bundle="third-weixin"/></td>
						<td>
							<xform:text property="value(wxToken)" subject="${lfn:message('third-weixin:third.wx.config.wxTokenSubject') }" required="false" style="width:85%" showStatus="edit" validators="wxRequire"/>
							<span class="txtstrong">*</span>
							<ui:button text="${lfn:message('third-weixin:third.wx.config.wxTokenBtn') }" onclick="window.random_token();validation.validateElement(document.getElementsByName('value(wxToken)')[0]);" style="vertical-align: top;"></ui:button>
							<div class="message"><bean:message key="third.wx.config.wxTokenMsg" bundle="third-weixin"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxAeskeyTitle" bundle="third-weixin"/></td>
						<td>
							<xform:text property="value(wxAeskey)" subject="${lfn:message('third-weixin:third.wx.config.wxAeskeySubject') }" required="false" style="width:85%" showStatus="edit" validators="wxRequire"/>
							<span class="txtstrong">*</span>
							<ui:button text="${lfn:message('third-weixin:third.wx.config.wxAeskeyBtn') }" onclick="window.random_AESKey();validation.validateElement(document.getElementsByName('value(wxAeskey)')[0]);" style="vertical-align: top;"></ui:button>
							<div class="message"><bean:message key="third.wx.config.wxAeskeyMsg" bundle="third-weixin"/></div>
							<div class="txtstrong"><bean:message key="third.wx.config.wxAeskeyMsg.tip" bundle="third-weixin"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxOauth2EnabledTitle" bundle="third-weixin"/></td>
						<td>
							<ui:switch property="value(wxOauth2Enabled)" onValueChange="" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div class="message"><bean:message key="third.wx.config.wxOauth2EnabledMsg" bundle="third-weixin"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxPcScanLoginEnabledTitle" bundle="third-weixin"/></td>
						<td>
							<ui:switch property="value(wxPcScanLoginEnabled)" onValueChange="" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div class="message"><bean:message key="third.wx.config.wxPcScanLoginEnabledMsg" bundle="third-weixin"/></div>
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
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxTodoEnabledTitle" bundle="third-weixin"/></td>
						<td>
							<ui:switch property="value(wxTodoEnabled)" onValueChange="wxTodo_display_change()" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxTodoType2EnabledTitle" bundle="third-weixin"/></td>
						<td>
							<ui:switch property="value(wxTodoType2Enabled)" onValueChange="" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div class="message"><bean:message key="third.wx.config.wxTodoType2EnabledMsg" bundle="third-weixin"/></div>
						</td>
					</tr>
					<%-- <tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxNotifyTypeTitle" bundle="third-weixin"/></td>
						<td>
							<xform:radio property="value(wxNotifyType)" subject="${lfn:message('third-weixin:third.wx.config.wxNotifyTypeSubject') }" showStatus="edit" >
								<xform:simpleDataSource value="news"><bean:message key="third.wx.config.wxNotifyTypeNews" bundle="third-weixin"/></xform:simpleDataSource>
								<xform:simpleDataSource value="text"><bean:message key="third.wx.config.wxNotifyTypeText" bundle="third-weixin"/></xform:simpleDataSource>
							</xform:radio>
							<div class="message"><bean:message key="third.wx.config.wxNotifyTypeNewsMsg" bundle="third-weixin"/></div>
							<div class="message"><bean:message key="third.wx.config.wxNotifyTypeTextMsg" bundle="third-weixin"/></div>
						</td>
					</tr> --%>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxAgentidTitle" bundle="third-weixin"/></td>
						<td>
							<xform:text property="value(wxAgentid)" subject="${lfn:message('third-weixin:third.wx.config.wxAgentidSubject') }" required="false" 
								style="width:85%" showStatus="edit" validators="wxTodoRequire"/>
							<span class="txtstrong">*</span>
							<div class="message"><bean:message key="third.wx.config.wxAgentidMsg" bundle="third-weixin"/><a class="weixinLink" href="https://qy.weixin.qq.com/" target="_blank"><bean:message key="third.wx.config.wxAgentidMsg.weixinLink" bundle="third-weixin"/></a><bean:message key="third.wx.config.wxAgentidMsg.tip" bundle="third-weixin"/></div>
						</td>
					</tr>
				</table>
				<br/>
				<table id="wxOmsTable" class="tb_normal" width=100%>
					<tr id="wxOmsTR">
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxOmsOutEnabledTitle" bundle="third-weixin"/></td>
						<td>
							<ui:switch property="value(wxOmsOutEnabled)" onValueChange="wxOms_display_change()" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxOrgIdTitle" bundle="third-weixin"/></td>
						<td>
							<xform:address propertyId="value(wxOrgId)" propertyName="value(wxOrgName)" 
								mulSelect="true" orgType="ORG_TYPE_ORGORDEPT" showStatus="edit" onValueChange="wxOrgId_display_change()" 
								style="width:75%" subject="${lfn:message('third-weixin:third.wx.config.wxOrgIdSubject') }" validators="wxOmsRequire">
							</xform:address>
							<span class="txtstrong">*</span>
							<ui:button text="${lfn:message('third-weixin:third.wx.config.wxOrgIdBtn') }" onclick="cleanTime();" style="vertical-align: top;"></ui:button>
							<div class="message"><bean:message key="third.wx.config.wxOrgIdMsg" bundle="third-weixin"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxOmsRootFlagTitle" bundle="third-weixin"/></td>
						<td>
							<ui:switch property="value(wxOmsRootFlag)" onValueChange="" 
								enabledText="${lfn:message('third-weixin:third.wx.config.wxOmsRootFlag.sync') }" disabledText="${lfn:message('third-weixin:third.wx.config.wxOmsRootFlag.asyn') }"></ui:switch>
							<div class="message"><bean:message key="third.wx.config.wxOmsRootFlagMsg" bundle="third-weixin"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxOmsOrgPersonHandleTitle" bundle="third-weixin"/></td>
						<td>
							<xform:radio property="value(wxOmsOrgPersonHandle)">
								<xform:enumsDataSource enumsType="wx_oms_sync_handle" />
							</xform:radio>
							<div class="message"><bean:message key="third.wx.config.wxOmsOrgPersonHandleMsg" bundle="third-weixin"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxRootIdTitle" bundle="third-weixin"/></td>
						<td>
							<xform:text property="value(wxRootId)" showStatus="edit" style="width:65%"/>
							<div class="message"><bean:message key="third.wx.config.wxRootIdMsg" bundle="third-weixin"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.wx.config.wxLoginNameTitle" bundle="third-weixin"/></td>
						<td>
							<xform:text property="value(wxLoginName)" showStatus="noShow"/>
							<div id="ln"><bean:message key="third.wx.config.wxLoginNameLn" bundle="third-weixin"/></div>
							<div class="message"><bean:message key="third.wx.config.wxLoginNameMsg" bundle="third-weixin"/></div>
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
						$("#ln").text("<bean:message key='third.wx.config.ekpPrimary' bundle='third-weixin'/>");
					}else{
						$("#ln").empty();
						$("#ln").text("<bean:message key='third.wx.config.wxLoginNameLn' bundle='third-weixin'/>");
						$('[name="value(wxLoginName)"]').val("loginname");
					}
				}
			}
			function cleanTime(){
				if(confirm("<bean:message key='third.wx.config.clearMsg' bundle='third-weixin'/>")){
					var url = '<c:url value="/third/wx/weixinSynchroOrgCheck.do?method=cleanTime" />';
					$.ajax({
					   type: "POST",
					   url: url,
					   async:false,
					   dataType: "json",
					   success: function(data){
							if(data.status=="1"){
								alert("<bean:message key='third.wx.config.config.clearSuccess' bundle='third-weixin'/>");
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
					alert("<bean:message key='third.wx.config.config.org.tip' bundle='third-weixin'/>");
				}
			}
			seajs.use(['lui/jquery'],function($){
					function wx_display_change(){
						var value = $('[name="value(wxEnabled)"]').val();
						if(value == 'true'){
							$('#wxBaseTable tr[id!="wenxinEnableTR"]').show();
							$('#wxTodoTable,#wxOmsTable').show();
						}else{
							$('#wxBaseTable tr[id!="wenxinEnableTR"]').hide();
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
					function config_wx_callbackurl(){
						var wxDomain = $("[name='value(wxDomain)']") && $("[name='value(wxDomain)']").val() || '',
							wxUrlSuffix = $("[name='wxUrlSuffix']").val();
						$('[name="value(wxCallbackurl)"]').val(wxDomain + wxUrlSuffix);
					}
					function config_wx_dns_getUrl(){
						var protocol = location.protocol,
							host = location.host,
							contextPath = seajs.data.env.contextPath;
						$('[name="value(wxDomain)"]').val(protocol + '//' + host + contextPath );
					}
					//设置待办消息类型
					function config_wx_notify_type(){
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
					
					validation.addValidator('wxRequire',"<bean:message key='third.weixin.work.config.notnull' bundle='third-weixin-work'/>",function(v, e, o){
						var wxEnabled = $('[name="value(wxEnabled)"]').val();
						if(wxEnabled == 'true' && !v){
							var validator = this.getValidator('wxRequire'),
								error = "<bean:message key='third.weixin.work.config.notnull' bundle='third-weixin-work'/>";
							if($(e).attr('subject')){
								error = $(e).attr('subject') + error;
							}	
							validator.error = error;
							return false;
						}
						return true;
					});

					validation.addValidator('wxTodoRequire','{name}'+"<bean:message key='third.weixin.work.config.notnull' bundle='third-weixin-work'/>",function(v, e, o){
						var wxEnabled = $('[name="value(wxEnabled)"]').val();
						if(wxEnabled != 'true'){
							return true;
						}
						var todoEnabled = $('[name="value(wxTodoEnabled)"]').val();
						return (todoEnabled == 'true' && !v) ? false : true;
					});

					validation.addValidator('wxOmsRequire','{name}'+"<bean:message key='third.weixin.work.config.notnull' bundle='third-weixin-work'/>",function(v, e, o){
						var wxEnabled = $('[name="value(wxEnabled)"]').val();
						if(wxEnabled != 'true'){
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
						wx_display_change();
						wxTodo_display_change();
						wxOms_display_change();
						config_wx_callbackurl();
						//config_wx_notify_type();
						setLoginName();
					});
					
					window.validateAppConfigForm = function(){
						return true;
					};
					
					window.wxSubmit = function(){
						var wxEnabled = $('[name="value(wxEnabled)"]').val();
						if(!wxEnabled || wxEnabled == 'false'){
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
					
					window.wx_display_change = wx_display_change;
					window.wxTodo_display_change = wxTodo_display_change;
					window.wxOms_display_change = wxOms_display_change;
					window.random_token = random_token;
					window.random_AESKey = random_AESKey;
					window.config_wx_callbackurl = config_wx_callbackurl;
					window.config_wx_dns_getUrl = config_wx_dns_getUrl;
				});
		</script>
	</template:replace>	
</template:include>