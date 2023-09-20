<%@ page import="com.landray.kmss.third.feishu.model.ThirdFeishuConfig" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message key="third.feishu.config.setting" bundle="third-feishu"/></template:replace>
	
	<template:replace name="head">
		<style>
		body{padding:20px;}.message{margin-top:10px;}
		a.feishuLink{color:#15a4fa; text-decoration: underline;}
		a.feishuLink:hover{color:red; }
		</style>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin:10px 0">
			<span class="profile_config_title"><bean:message key="third.feishu.config.setting" bundle="third-feishu"/></span>
		</h2>
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do?autoclose=false" onsubmit="return validateAppConfigForm(this);" >
			<center>
				<ui:tabpanel>
					<!-- 基础配置 -->
					<ui:content id="tag1" title="${ lfn:message('third-feishu:thirdFeishu.tab.base.setting') }">
						<br/>
						<table id="feishuBaseTable" class="tb_normal" width=100%>
							<tr id="feishuEnableTR">
								<td class="td_normal_title" width="15%"><bean:message key="third.feishu.enable" bundle="third-feishu"/></td>
								<td>
									<ui:switch property="value(feishuEnabled)" onValueChange="feishu_display_change();" 
										enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.appid" bundle="third-feishu"/></td>
								<td>
									<xform:text property="value(feishuAppid)" style="width:85%;" showStatus="edit" subject="${lfn:message('third-feishu:third.feishu.config.appid') }" required="false"  validators="feishuRequire"/>
									<span class="txtstrong">*</span>
									<div class="message">
										<bean:message key="third.feishu.config.appid.tip" bundle="third-feishu"/></div>
								</td>
							</tr>
							
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.appsecret" bundle="third-feishu"/></td>
								<td>
									<xform:text property="value(feishuAppsecret)" subject="${lfn:message('third-feishu:third.feishu.config.appsecret') }" required="false"  validators="feishuRequire" style="width:85%" showStatus="edit"/><span class="txtstrong">*</span>
									<div class="message"><bean:message key="third.feishu.config.appsecret.tip" bundle="third-feishu"/></div>
								</td>
							</tr>
							
						</table>
					</ui:content>
					
					
					<!-- 登录配置 -->
					<ui:content id="tag2" title="${ lfn:message('third-feishu:thirdFeishu.tab.login.setting') }">
						<br/>
						<table class="tb_normal" width=100%>
								<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.sso" bundle="third-feishu"/></td>
								<td>
									<ui:switch property="value(feishuSsoEnabled)" onValueChange="" 
										enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									<div class="message"><bean:message key="third.feishu.config.sso.tip" bundle="third-feishu"/></div>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.sso.out" bundle="third-feishu"/></td>
								<td>
									<ui:switch property="value(feishuSsoOutEnabled)" onValueChange="" 
										enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									<div class="message"><bean:message key="third.feishu.config.sso.out.tip" bundle="third-feishu"/></div>
								</td>
							</tr>

							<tr id="feishuPcScanEnableTR">
								<td class="td_normal_title" width="15%">EKP PC端扫码登录</td>
								<td>
									<ui:switch property="value(pcScanLoginEnabled)"
											   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</td>
							</tr>
						</table>
						
					</ui:content>
					
					
					<!-- 待办待阅推送配置 -->
					<ui:content id="tag3" title="${ lfn:message('third-feishu:thirdFeishu.tab.notify.setting') }">
						<br/>
						<table id="feishuTodoTable" class="tb_normal" width=100%>
								<%-- 
								<tr id="feishuNotifyTR">
									<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.feishuNotifyEnabled" bundle="third-feishu"/></td>
									<td>
										<ui:switch property="value(feishuNotifyEnabled)" onValueChange="feishuNotify_display_change();" 
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</td>
								</tr>
								--%>
								<tr id="feishuTodoTR">
									<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.todo" bundle="third-feishu"/></td>
									<td>
										<table style="width: 40%">
											<tr>
												<td width="150px">
													<ui:switch property="value(feishuTodoMsgEnabled)" onValueChange="" 
													enabledText="${lfn:message('third-feishu:third.feishu.config.todo.msg')}" disabledText="${lfn:message('third-feishu:third.feishu.config.todo.msg') }"></ui:switch>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr id="feishuToreadTR">
									<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.toread" bundle="third-feishu"/></td>
									<td>
										<ui:switch property="value(feishuToreadMsgEnabled)" onValueChange="" 
											enabledText="${lfn:message('third-feishu:third.feishu.config.toread.msg')}" disabledText="${lfn:message('third-feishu:third.feishu.config.toread.msg')}"></ui:switch>
									</td>
								</tr>
								<tr id="feishuToreadTR" style="display: none">
									<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.approval" bundle="third-feishu"/></td>
									<td>
										<ui:switch property="value(feishuApprovalEnabled)" onValueChange="approvalEnable()"
												   enabledText="${lfn:message('third-feishu:third.feishu.config.toread.msg')}" disabledText="${lfn:message('third-feishu:third.feishu.config.toread.msg')}"></ui:switch>
										<div id="feishuApprovalUrlBlock" style="display: none">
											<xform:text property="value(feishuApprovalUrl)" style="width:85%;" showStatus="edit" subject="${lfn:message('third-feishu:third.feishu.config.approval.url') }" required="false"  validators="feishuApprovalValidator"/>
											<span class="txtstrong">*</span>
											<div class="message">
												<bean:message key="third.feishu.config.approval.url" bundle="third-feishu"/></div>
										</div>
									</td>
								</tr>
							</table>
					</ui:content>
					
					<!-- 通讯录配置 -->
					<ui:content id="tag4" title="${ lfn:message('third-feishu:third.feishu.tab.org.setting') }">
						<br/>
						<table class="tb_normal" width=100%>
							<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.feishu.org.synchro" bundle="third-feishu"/></td>
									<td>
										<ui:switch property="value(synchroOrg2Feishu)" onValueChange="feishuOrgSyn_display_change()"
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</td>
							</tr>

							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.feishu.org.ekp.root" bundle="third-feishu"/></td>
								<td>
									<xform:address propertyId="value(synchroOrg2FeishuEkpRootId)" propertyName="value(synchroOrg2FeishuEkpRootName)"
												   mulSelect="true" orgType="ORG_TYPE_ORGORDEPT" showStatus="edit" onValueChange="ekpRootChange()"
												   style="width:75%" subject="${lfn:message('third-feishu:third.feishu.org.wxOrgIdSubject') }" validators="wxOmsRequire">
									</xform:address>
									<ui:button text="${lfn:message('third-feishu:third.feishu.org.sync.cleanTime') }" onclick="cleanTime();" style="vertical-align: top;"></ui:button>
									<div class="message"><bean:message key="third.feishu.org.ekp.root.tip" bundle="third-feishu"/></div>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.feishu.org.root.isSync" bundle="third-feishu"/></td>
								<td>
									<ui:switch property="value(synchroOrg2FeishuEkpRootSync)" onValueChange=""
											   enabledText="${lfn:message('third-feishu:third.feishu.org.ekpRoot.sync') }" disabledText="${lfn:message('third-feishu:third.feishu.org.ekpRoot.asyn') }"></ui:switch>
									<div class="message"><bean:message key="third.feishu.org.root.isSync.tip" bundle="third-feishu"/></div>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.sync.out.handle" bundle="third-feishu"/></td>
								<td>
									<xform:radio property="value(synchroOrg2FeishuOutRangePersonHandle)">
										<xform:enumsDataSource enumsType="feishu_oms_sync_handle" />
									</xform:radio>
									<div class="message"><bean:message key="third.feishu.config.sync.out.handle.tip" bundle="third-feishu"/></div>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.sync.disabale.handle" bundle="third-feishu"/></td>
								<td>
									<xform:radio property="value(synchroOrg2FeishuDisablePersonHandle)">
										<xform:simpleDataSource value="1" bundle="third-feishu" textKey="feishu.sync.handle1" />
										<xform:simpleDataSource value="2" bundle="third-feishu" textKey="feishu.sync.handle2" />
									</xform:radio>
									<div class="message"><bean:message key="third.feishu.config.sync.disabale.handle.tip" bundle="third-feishu"/></div>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.feishu.config.sync.feishu.rootId" bundle="third-feishu"/></td>
								<td>
									<xform:text property="value(synchroOrg2FeishuFeishuRootId)" showStatus="edit" style="width:65%"/>
									<div class="message"><bean:message key="third.feishu.config.sync.feishu.rootId.tip" bundle="third-feishu"/></div>
									<input type="hidden" name="value(synchroOrg2FeishuFeishuRootOpenId)" value="" />
								</td>
							</tr>
							<!-- ekp到飞书同步 -->
							<tr id="feishuOrgSynTable">
								<td class="td_normal_title" width="15%"><bean:message key="third.feishu.org.person" bundle="third-feishu"/></td>
								<td>
									<%@ include file="/third/feishu/feishu_config_person_cols.jsp" %>
								</td>
							</tr>
							<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.feishu.updatePersonMapping.quartz" bundle="third-feishu"/></td>
									<td>
										<ui:switch property="value(updatePersonMapping)" onValueChange="" 
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</td>
							</tr>
						</table>
					</ui:content>
					
				</ui:tabpanel>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.third.feishu.model.ThirdFeishuConfig" />
			<center style="margin:20px 0;">
				<!-- 保存 -->
				<ui:button text="${lfn:message('button.save')}" onclick="feishuSubmit();"  width="120" height="35"></ui:button>
			</center>
		</html:form>
		<script type="text/javascript">
			var validation = $KMSSValidation();

			function approvalEnable(){
				var feishuApprovalEnabled = $('[name="value(feishuApprovalEnabled)"]').val();
				if("true" == feishuApprovalEnabled){
					document.getElementById("feishuApprovalUrlBlock").style.display = "block";
				}
				else{
					document.getElementById("feishuApprovalUrlBlock").style.display = "none";
				}
			}
			
			function cleanTime(){
				if(confirm("${lfn:message('third-feishu:third.feishu.org.sync.cleanTime.tip')}")){
					var url = '<c:url value="/third/feishu/thirdFeishu.do?method=cleanTime" />';
					$.ajax({
					   type: "POST",
					   url: url,
					   async:false,
					   dataType: "json",
					   success: function(data){
							if(data.status=="1"){
								alert("${lfn:message('third-feishu:third.feishu.config.config.clearSuccess')}");
							}else{
								alert(data.msg);
							}
					   }
					});
				}
			}
			
			seajs.use(['lui/jquery'],function($){
				//是否显示飞书配置项
				function feishu_display_change(){
					var value = $('[name="value(feishuEnabled)"]').val();
					if(value == 'true'){
						$('#feishuBaseTable tr[id!="feishuEnableTR"]').show();
						$('#feishuTodoTable,#feishuPcScanTable,#feishuBusTable').show();
						__setVisible(true);
					}else{
						$('#feishuBaseTable tr[id!="feishuEnableTR"]').hide();
						$('#feishuTodoTable,#feishuPcScanTable,#feishuBusTable').hide();
						__setVisible(false);
					}
				}
				
				function feishuNotify_display_change(){
					var value = $('[name="value(feishuNotifyEnabled)"]').val();
					var visible = value=="true"?true:false;
					//alert(value+","+visible);
					var trs = $('#feishuTodoTable').find("tr");
					for(var i=1;i<trs.length;i++){
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
				validation.addValidator('feishuRequire','{name}'+"${lfn:message('third-feishu:third.feishu.work.config.notnull')}",function(v, e, o){
					var feishuEnabled = $('[name="value(feishuEnabled)"]').val();
					if(feishuEnabled == 'true' && !v){
						return false;
					}
					/**
					var feishuApprovalEnabled = $('[name="value(feishuApprovalEnabled)"]').val();
					if(feishuApprovalEnabled == 'true' && !v){
						return false;
					}
					 */
					return true;
				});

				
				validation.addValidator('feishuApprovalValidator','{name}'+"${lfn:message('third-feishu:third.feishu.work.config.notnull')}",function(v, e, o){
					var feishuApprovalEnabled = $('[name="value(feishuApprovalEnabled)"]').val();
					var feishuApprovalUrl = $('[name="value(feishuApprovalUrl)"]').val();
					if(feishuApprovalEnabled == 'true' && (feishuApprovalUrl == null || feishuApprovalUrl == "")){
						return false;
					}
					return true;
				});

				function __init(){
					feishu_display_change();
					//feishuNotify_display_change();
					feishuOrgSyn_display_change();
					approvalEnable();
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
				
				window.feishuSubmit = function(){
					var feishuEnabled = $('[name="value(feishuEnabled)"]').val();
					if(!feishuEnabled || feishuEnabled == 'false'){
						$('[name="value(feishuSsoEnabled)"]').val('false');
						$('[name="value(feishuTodoMsgEnabled)"]').val('false');
						$('[name="value(feishuToreadMsgEnabled)"]').val('false');
						$('[name="value(feishuApprovalEnabled)"]').val('false');
					}
					
					
					Com_Submit(document.sysAppConfigForm, 'update');
					
				};

				function feishuOrgSyn_display_change(){
					var value = $('[name="value(synchroOrg2Feishu)"]').val();
					var visible = value=="true"?true:false;
					//alert(value+","+visible);
					if(visible){
						$('#feishuOrgSynTable').show();
					}else{
						$('#feishuOrgSynTable').hide();
					}
				};

				function ekpRootChange(){
					var woi = "<%=ThirdFeishuConfig.newInstance().getSynchroOrg2FeishuEkpRootId()   %>";
					var value = $('[name="value(synchroOrg2FeishuEkpRootId)"]').val();
					if(value!=""&&woi!=""&&woi!=value){
						alert("${lfn:message('third-feishu:third.feishu.config.config.org.tip')}");
					}
				};
				
				window.feishu_display_change = feishu_display_change;
				//window.feishuNotify_display_change = feishuNotify_display_change;
				window.feishuOrgSyn_display_change = feishuOrgSyn_display_change;
				window.ekpRootChange = ekpRootChange;
				
			});


		</script>
	</template:replace>	
</template:include>