<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message key="third.welink.config.setting" bundle="third-welink"/></template:replace>
	<template:block name="path" >
	</template:block>
	<template:replace name="head">
		<style>
		body{padding:20px;}.message{margin-top:10px;}
		a.welinkLink{color:#15a4fa; text-decoration: underline;}
		a.welinkLink:hover{color:red; }
		</style>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin:10px 0">
			<span class="profile_config_title"><bean:message key="third.welink.config.setting" bundle="third-welink"/></span>
		</h2>
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do?autoclose=false" onsubmit="return validateAppConfigForm(this);" >
			<center>
				<ui:tabpanel>
					<!-- 基础配置 -->
					<ui:content id="tag1" title="${ lfn:message('third-welink:thirdWelink.tab.base.setting') }">
						<br/>
						<table id="welinkBaseTable" class="tb_normal" width=100%>
							<tr id="welinkEnableTR">
								<td class="td_normal_title" width="15%"><bean:message key="third.welink.enable" bundle="third-welink"/></td>
								<td>
									<ui:switch property="value(welinkEnabled)" onValueChange="welink_display_change();" 
										enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.welink.config.clientid" bundle="third-welink"/></td>
								<td>
									<xform:text property="value(welinkClientid)" style="width:85%;" showStatus="edit" required="false"  validators="welinkRequire"/>
									<span class="txtstrong">*</span>
									<div class="message">
										<bean:message key="third.welink.config.clientid.tip" bundle="third-welink"/></div>
								</td>
							</tr>
							
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.welink.config.clientsecret" bundle="third-welink"/></td>
								<td>
									<xform:text property="value(welinkClientsecret)"  required="false" style="width:85%" showStatus="edit" validators="welinkRequire"/><span class="txtstrong">*</span>
									<div class="message"><bean:message key="third.welink.config.clientsecret.tip" bundle="third-welink"/></div>
								</td>
							</tr>
							
						</table>
					</ui:content>
					
					
					<!-- 登录配置 -->
					<ui:content id="tag2" title="${ lfn:message('third-welink:thirdWelink.tab.login.setting') }">
						<br/>
						<table class="tb_normal" width=100%>
								<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.welink.config.sso" bundle="third-welink"/></td>
								<td>
									<ui:switch property="value(welinkSsoEnabled)" onValueChange="" 
										enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									<div class="message"><bean:message key="third.welink.config.sso.tip" bundle="third-welink"/></div>
								</td>
							</tr>
						</table>
						
					</ui:content>
					
					
					<!-- 待办待阅推送配置 -->
					<ui:content id="tag3" title="${ lfn:message('third-welink:thirdWelink.tab.notify.setting') }">
						<br/>
						<table id="welinkTodoTable" class="tb_normal" width=100%>
								<tr id="welinkNotifyTR">
									<td class="td_normal_title" width="15%"><bean:message key="third.welink.config.welinkNotifyEnabled" bundle="third-welink"/></td>
									<td>
										<ui:switch property="value(welinkNotifyEnabled)" onValueChange="welinkNotify_display_change();" 
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</td>
								</tr>
								<tr id="welinkTodoTR">
									<td class="td_normal_title" width="15%"><bean:message key="third.welink.config.todo" bundle="third-welink"/></td>
									<td>
										<table style="width: 40%">
											<tr>
												<td width="150px">
													<ui:switch property="value(welinkTodoMsgEnabled)" onValueChange="" 
													enabledText="${lfn:message('third-welink:third.welink.config.todo.msg')}" disabledText="${lfn:message('third-welink:third.welink.config.todo.msg') }"></ui:switch>
												</td>
												
												<td width="150px">
													<ui:switch property="value(welinkTodoTaskEnabled)" onValueChange="updateTodoTask_enable()" 
													enabledText="${lfn:message('third-welink:third.welink.config.todo.task')}" disabledText="${lfn:message('third-welink:third.welink.config.todo.task') }"></ui:switch>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr id="welinkToreadTR">
									<td class="td_normal_title" width="15%"><bean:message key="third.welink.config.toread" bundle="third-welink"/></td>
									<td>
										<ui:switch property="value(welinkToreadMsgEnabled)" onValueChange="" 
											enabledText="${lfn:message('third-welink:third.welink.config.toread.msg')}" disabledText="${lfn:message('third-welink:third.welink.config.toread.msg')}"></ui:switch>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.welink.config.msg.mobile.only" bundle="third-welink"/></td>
									<td>
										<ui:switch property="value(welinkMsgMobileOnly)" onValueChange="" 
											enabledText="${lfn:message('third-welink:third.welink.config.msg.mobile.only')}" disabledText="${lfn:message('third-welink:third.welink.config.msg.mobile.only')}"></ui:switch>
									</td>
								</tr>
								<tr name="welinkTodoTaskTR">
									<td class="td_normal_title" width="15%"><bean:message key="third.welink.notify.task.applicantUserId" bundle="third-welink"/></td>
									<td>
										<xform:text property="value(welinkNotifyTaskApplicantUserId)" style="width:50%" showStatus="edit" validators="welinkTaskRequire"/><span class="txtstrong">*</span>
										<br>
										<span class="txtstrong">
										<bean:message key="third.welink.notify.task.applicantUserId.tip" bundle="third-welink"/>
										</span>
									</td>
								</tr>
								<tr name="welinkTodoTaskTR">
									<td class="td_normal_title" width="15%"><bean:message key="third.welink.notify.task.applicantUserNameCn" bundle="third-welink"/></td>
									<td>
										<xform:text property="value(welinkNotifyTaskApplicantUserNameCn)" style="width:50%" showStatus="edit" validators="welinkTaskRequire"/><span class="txtstrong">*</span>
										<br>
										<span class="txtstrong">
										<bean:message key="third.welink.notify.task.applicantUserId.tip" bundle="third-welink"/>
										</span>
									</td>
								</tr>
								<tr name="welinkTodoTaskTR">
									<td class="td_normal_title" width="15%"><bean:message key="third.welink.notify.task.applicantUserNameEn" bundle="third-welink"/></td>
									<td>
										<xform:text property="value(welinkNotifyTaskApplicantUserNameEn)" style="width:50%" showStatus="edit" validators="welinkTaskRequire"/><span class="txtstrong">*</span>
										<br>
										<span class="txtstrong">
										<bean:message key="third.welink.notify.task.applicantUserId.tip" bundle="third-welink"/>
										</span>
									</td>
								</tr>
								
								<!-- 
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.welink.notifyLog.saveDays" bundle="third-welink"/></td>
									<td>
										<xform:text property="value(welinkNotifyLogSaveDays)" required="false" style="width:85%" showStatus="edit"/><bean:message key="third.welink.notifyLog.saveDays.day" bundle="third-welink"/>
										<div class="message"><bean:message key="third.welink.notifyLog.saveDays.tip" bundle="third-welink"/></div>									
									</td>
								</tr>
								 -->
							</table>
					</ui:content>
					
					<!-- 通讯录配置 -->
					<ui:content id="tag4" title="${ lfn:message('third-welink:third.welink.tab.org.setting') }">
						<br/>
						<table class="tb_normal" width=100%>
							<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.welink.org.synchro" bundle="third-welink"/></td>
									<td>
										<ui:switch property="value(synchroOrg2Welink)" onValueChange="" 
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</td>
							</tr>
							<tr>
							<td colspan="3" align="center">
								<ui:button text="删除同步时间戳" onclick="window.welinkDelOmsTimestamp();"  width="120" height="35"></ui:button>
								<!--
								<ui:button text="查询同步结果" onclick="window.getSyncStatus();"  width="120" height="35"></ui:button>
								-->
							</td>
							
							</tr>
						</table>
					</ui:content>
					
				</ui:tabpanel>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.third.welink.model.ThirdWelinkConfig" />
			<center style="margin:20px 0;">
				<!-- 保存 -->
				<ui:button text="${lfn:message('button.save')}" onclick="window.welinkSubmit();"  width="120" height="35"></ui:button>
			</center>
		</html:form>
		<script type="text/javascript">
			var validation = $KMSSValidation();
			
			function welinkDelOmsTimestamp(){
				if(confirm("是否确定要删除同步时间戳？")){
					var url = '<c:url value="/third/welink/thirdWelink.do?method=delOmsTimestamp" />';
					$.ajax({
					   type: "POST",
					   url: url,
					   async:false,
					   dataType: "json",
					   success: function(data){
							if(data.status=="1"){
								alert("删除同步时间戳成功");
							}else{
								alert(data.msg);
							}
					   }
					});
				}
			}
			
			function getSyncStatus(){
					var url = '<c:url value="/third/welink/thirdWelink.do?method=getSyncStatus" />';
					window.open(url,"_blank");
				
			}
			
			seajs.use(['lui/jquery'],function($){
				//是否显示钉钉配置项
				function welink_display_change(){
					var value = $('[name="value(welinkEnabled)"]').val();
					if(value == 'true'){
						$('#welinkBaseTable tr[id!="welinkEnableTR"]').show();
						$('#welinkTodoTable,#welinkPcScanTable,#welinkBusTable').show();
						__setVisible(true);
					}else{
						$('#welinkBaseTable tr[id!="welinkEnableTR"]').hide();
						$('#welinkTodoTable,#welinkPcScanTable,#welinkBusTable').hide();
						__setVisible(false);
					}
				}
				
				function welinkNotify_display_change(){
					var value = $('[name="value(welinkNotifyEnabled)"]').val();
					var welinkTodoTaskEnabled = $('[name="value(welinkTodoTaskEnabled)"]').val();
					var visible = value=="true"?true:false;
					var trs = $('#welinkTodoTable').find("tr");
					for(var i=1;i<trs.length;i++){
						if("welinkTodoTaskTR" === $(trs[i]).attr("name")){
							if(welinkTodoTaskEnabled == "true"){
								$(trs[i]).show();
							}
							else{
								$(trs[i]).hide();
							}
						}
						else if(visible){
							$(trs[i]).show();
						}else{
							$(trs[i]).hide();
						}
					}
				}
				          
				function updateTodoTask_enable(){
					var value = $('[name="value(welinkTodoTaskEnabled)"]').val();
					var visible = value=="true"?true:false;
					//alert(value+","+visible);
					var trs = $("tr[name='welinkTodoTaskTR']");
					for(var i=0;i<trs.length;i++){
						if(visible){
							$(trs[i]).show();
						}else{
							$(trs[i]).hide();
						}
					}
				}
				
				function __setVisible(visible){
					var titleNodes = $('.lui_tabpanel_frame').find("[data-lui-mark='panel.nav.title']");
					for(var i=1;i<titleNodes.length;i++){
						if(visible){
							$(titleNodes[i]).show();
						}else{
							$(titleNodes[i]).hide();
						}
					}
				}
				

				//自定义校验器
				validation.addValidator('welinkRequire','{name}'+"不能为空",function(v, e, o){
					var welinkEnabled = $('[name="value(welinkEnabled)"]').val();
					if(welinkEnabled == 'true' && !v){
						return false;
					}
					return true;
				});

				validation.addValidator('welinkTaskRequire','{name}'+"不能为空",function(v, e, o){
					//debugger;
					var welinkEnabled = $('[name="value(welinkEnabled)"]').val();
					var welinkNotifyEnabled = $('[name="value(welinkNotifyEnabled)"]').val();
					var welinkTodoTaskEnabled = $('[name="value(welinkTodoTaskEnabled)"]').val();
					if(welinkEnabled == 'true' && welinkNotifyEnabled == 'true' && welinkTodoTaskEnabled == 'true' && !v){
						return false;
					}
					return true;
				});
				
				function __init(){
					welink_display_change();
					welinkNotify_display_change();
					updateTodoTask_enable();
				};
				
				LUI.ready(function(){
					LUI("tag1").on("show", function(){
						__init();
					});
					LUI("tag2").on("show", function(){
						__init();
					});
					LUI("tag3").on("show", function(){
						__init();
					});
					LUI("tag4").on("show", function(){
						__init();
					});
					
				});
				
				window.validateAppConfigForm = function(){
					return true;
				};
				
				window.welinkSubmit = function(){
					var welinkEnabled = $('[name="value(welinkEnabled)"]').val();
					if(!welinkEnabled || welinkEnabled == 'false'){
						$('[name="value(welinkSsoEnabled)"]').val('false');
						$('[name="value(welinkTodoMsgEnabled)"]').val('false');
						$('[name="value(welinkToreadMsgEnabled)"]').val('false');
					}
					
					
					Com_Submit(document.sysAppConfigForm, 'update');
					
				};
				
				window.welink_display_change = welink_display_change;
				window.welinkNotify_display_change = welinkNotify_display_change;
				window.updateTodoTask_enable = updateTodoTask_enable;
				
			});
			
			
		</script>
	</template:replace>	
</template:include>