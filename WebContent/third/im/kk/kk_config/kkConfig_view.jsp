﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title">${lfn:message('third-im-kk:table.kkImConfig')}</template:replace>
		<template:replace name="head">
		<script type="text/javascript">
		Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js", null, "js");
		</script>
		<style type="text/css"> 
			
		</style> 
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title">${lfn:message('third-im-kk:table.kkImConfig')}</span>
		</h2>
		
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
			<center>
					  <div>
					  <br>
						<div>
							<table  class="tb_normal" width=95%  cellpadding="20" cellspacing="20" style="width: 95%;">
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.view.start')}</td>
								<td> 
									<label> 
										<ui:switch property="kkConfigStatus" checked="${map.kkConfigStatus }" onValueChange="config_chgEnabled();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</label>
								</td>
							</tr>	
							</table>
	
							<table id='lab_detail' class="tb_normal" width=95%  cellpadding="20" cellspacing="20" style="width: 95%;">
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.view.changeKKqrcodeEnabled')}</td>
								<td colspan="2"> 
									<label> 
										<ui:switch property="changeKKqrcodeEnabled"  checked="${map.changeKKqrcodeEnabled }"  enabledText="${lfn:message('third-im-kk:kk.config.view.newQrcode')}" disabledText="${lfn:message('third-im-kk:kk.config.view.oldQrcode')}"></ui:switch>
										<span class="message">${lfn:message('third-im-kk:kk.config.view.KKqrcodeEnabled')}</span>
									</label>
								</td>
							</tr>	
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.view.address')}</td>
								<td colspan="2"> 
									<label> 
										<xform:text showStatus="view" property="" value="${map.kkConAddress }" style="width:300px"/><br>
									</label>
								</td>
							</tr>	
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.view.connect.key')}</td>
								<td colspan="2">
									<xform:text showStatus="view" property="" value="${map.secretkey }"  style="width:300px" /><br>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.view.kk.outerDomain')}</td>
								<td colspan="2"> 
									<xform:text showStatus="view" property="" value="${map.outerDomain }" style="width:300px" /><br>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.view.kk.innerDomain')}</td>
								<td colspan="2">
									<xform:text showStatus="view" property="" value="${map.innerDomain }" style="width:300px" /><br>
									<input type="hidden" name="innerDomain" value="${map.innerDomain }"/>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.view.org')}</td>
								<td colspan="2">
									<label>
										<ui:switch id="orgId" property="org" checked="${map.org }" onValueChange="org_change();"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</label>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.view.orgVisible')}</td>
								<td colspan="2">
									<label> 
										<ui:switch id="orgVisibleId" property="org_visible" checked="${map.org_visible }" onValueChange="orgVisible_change();"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</label>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.view.sso')}</td>
								<td colspan="2">
									<label> 
										<ui:switch property="sso" checked="${map.sso }"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</label>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.view.mobileApp')}</td>
								<td colspan="2">
									<label> 
										<ui:switch property="mobile_app" checked="${map.mobile_app }"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</label>
								</td>
							</tr>	
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.view.pcApp')}</td>
								<td colspan="2">
									<label> 
										<ui:switch property="pc_app" checked="${map.pc_app }"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</label>
								</td>
							</tr>	
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.view.sms')}</td>
								<td colspan="2">
									<label> 
										<ui:switch property="sms" checked="${map.sms }"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</label>
								</td>
							</tr>
							<!-- 智能助手 -->
                   			<kmss:ifModuleExist path="/third/intell/">
								<tr>
									<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.syn.robot')}</td>
									<td colspan="2">
										<label> 
											<ui:switch property="robot" checked="${map.robot }"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
										</label>
									</td>
								</tr>
							</kmss:ifModuleExist>
							<!-- 其它应用配置 -->
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.syn.extend')}</td>
								<td colspan="2">
									<label> 
										<ui:switch property="extend_app" checked="${map.extend_app }"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</label>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.view.todo.config')}</td>
								<td colspan="2">
									<label> 
										<ui:switch property="notify" checked="${map.notify }" onValueChange="todoConfig();"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</label>
								</td>
							</tr>	
							<tr id="todoPush">
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.view.todo.push')}</td>
								<td>
									<xform:radio property="notify_type"
										showStatus="edit"
										onValueChange="config_notify_type(this.value);"
										subject="${lfn:message('third-im-kk:kk5.notify.synchro.type')}" required="true" value="${map.notify_type }">
										<xform:simpleDataSource value="corpNotify">${lfn:message('third-im-kk:kk5.notify.corpNotify')}</xform:simpleDataSource>
										<xform:simpleDataSource value="gzhNotify">${lfn:message('third-im-kk:kk5.notify.gzhNotify')}</xform:simpleDataSource>
									</xform:radio>
									<input type="hidden" id="notify_type" value="${map.notify_type }">
								</td>
								<td>
									<span id="corpNotifyMessage" class="message">${lfn:message('third-im-kk:kk5.notify.corpNotify.tip')}</span>
									<span id="gzhNotifyMessage" class="message">${lfn:message('third-im-kk:kk5.notify.gzhNotify.tip')}</span>
								</td>
							</tr>
							<tr id="tr_kk5_gzh_Notify">
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk5.notify.gzhNotify')}</td>
								
							</tr>
							<tr id="toSource">
								<td class="td_normal_title" width="15%">推送待办来源信息（针对其他系统）</td>
								<td colspan="2">
									<label> 
										<ui:switch property="todoSource" checked="${map.todoSource }"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</label>
								</td>
							</tr>
							<tr id="toAudit">
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.view.toAudit')}</td>
								<td colspan="2">
									<label> 
										<ui:switch property="notify_todo" checked="${map.notify_todo }"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</label>
								</td>
							</tr>
							<tr id="toview">
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.view.toview')}</td>
								<td colspan="2">
									<label>
										<ui:switch property="notify_toread" checked="${map.notify_toread }"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</label>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-im-kk:kk.config.view.log.saveTime')}</td>
								<td colspan="2">
									<xform:text showStatus="edit" property="logBak_days" value="${map.logBak_days }" style="width:3%" subject="${lfn:message('third-mail-coremail:coremail.mail.list.size')}"/>
									<span class="message">${lfn:message('third-im-kk:kk.config.view.logTime')}</span>
								</td>
							</tr>
							<tr id="toKKIM">
								<td class="td_normal_title" width="15%">IM是否启用转任务/日程</td>
								<td colspan="2">
									<label>
										<ui:switch property="imTransferEnable" checked="${not empty map.imTransferEnable ? map.imTransferEnable:'true' }"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</label>
								</td>
							</tr>
			</table>
			</div>
		
		</td>
	</tr>	
					
				</table>
				</div>
				</div>
				
			</center>
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="saveKKConfig();"></ui:button>
			<!-- 进入KK控制台 -->
			<ui:button id="loginKK" text="${lfn:message('third-im-kk:kk.config.view.comeKKaddress')}" height="35" width="120" onclick="loginKK();"></ui:button>
			<!-- 配置向导 -->
			<ui:button text="${lfn:message('third-im-kk:kk.config.view.configuration')}" height="35" width="120" onclick="intiConfig()"></ui:button>
			</center>
		</html:form>
		
	 	<script type="text/javascript">
			$KMSSValidation();
			function validateAppConfigForm(thisObj) {
				return true;
			}
			
			function config_chgEnabled() {
				var cfgDetail = $("#lab_detail");
				var isChecked = "true" == $("input[name='kkConfigStatus']").val();
				if (isChecked) {
					cfgDetail.show();
					$("#loginKK").show();
				} else {
					cfgDetail.hide();
					$("#loginKK").hide();
				}
			}
			
			
			
			function org_change(){
				 //$("#orgVisibleId input[type='checkbox']").click();
			}
			
			 function orgVisible_change(){
				 var org = "true" == $("input[name='org']").val();
				 var org_visible = "true" == $("input[name='org_visible']").val();
				 if(org != org_visible){
					 $("#orgId input[type='checkbox']").click();
				 }
			} 
			
			function todoConfig(){
				var isChecked = "true" == $("input[name='notify']").val();
				var notifyType = $("#notify_type").val();					
				var todoPush=document.getElementById("todoPush");
				var toAudit=document.getElementById("toAudit");
				var toview=document.getElementById("toview");
				var toSource=document.getElementById("toSource");
				var tr_kk5_gzh_Notify=document.getElementById("tr_kk5_gzh_Notify");
				var corpNotifyMessage=document.getElementById("corpNotifyMessage");
				var gzhNotifyMessage=document.getElementById("gzhNotifyMessage");
				if(isChecked){
					todoPush.style.display = 'table-row';
					toAudit.style.display = 'table-row';
					toview.style.display = 'table-row';
					toSource.style.display = 'table-row';
					if("corpNotify"==notifyType){
						tr_kk5_gzh_Notify.style.display = 'none';
						toAudit.style.display = 'table-row';
						toview.style.display = 'table-row';
						corpNotifyMessage.style.display = '';
						gzhNotifyMessage.style.display = 'none';
						//initCommonAccountList(false);
					}
					if("gzhNotify"==notifyType){
						tr_kk5_gzh_Notify.style.display = 'table-row';
						toAudit.style.display = 'none';
						toview.style.display = 'none';
						gzhNotifyMessage.style.display = '';
						corpNotifyMessage.style.display = 'none';
						initialGzhInfo();
					}
				}else{
					tr_kk5_gzh_Notify.style.display = 'none';
					todoPush.style.display = 'none';
					toAudit.style.display = 'none';
					toview.style.display = 'none';
					toSource.style.display = 'none';
				}
			}
			
			//保存
			function saveKKConfig(){
				var kkConfigStatus = "true" == $("input[name='kkConfigStatus']").val();
				var changeKKqrcodeEnabled = "true" == $("input[name='changeKKqrcodeEnabled']").val();
				var data = {};
				if(kkConfigStatus){					
					data.org = $("input[name='org']").val();
					data.org_visible = $("input[name='org_visible']").val();
					data.sso = $("input[name='sso']").val();
					data.mobile_app = $("input[name='mobile_app']").val();
					data.pc_app = $("input[name='pc_app']").val();
					data.sms = $("input[name='sms']").val();
					data.robot = $("input[name='robot']").val();
					data.extend_app = $("input[name='extend_app']").val();
					data.todoSource = $("input[name='todoSource']").val();
						
					var notify = $("input[name='notify']").val();
					var notifyType = $("#notify_type").val();
					var notifyTodo = $("input[name='notify_todo']").val();
					var notifyToread = $("input[name='notify_toread']").val();
					var logBakDays = $("input[name='logBak_days']").val();
					var imTransferEnable = $('input[name="imTransferEnable"]').val();
					
					var selectService = null;
				    var g = /^[1-9]*[1-9][0-9]*$/;  
					if(!g.test(logBakDays)){
						alert("<bean:message bundle='third-im-kk' key='kk.config.view.logBakDays.alert'/>");
						return;
					}
					if("false" == notify){
						notifyTodo = false;
						notifyToread = false;
					}
					if(notifyType == "gzhNotify"){
						selectService = $("#commonAccountList").val();
						notifyTodo = false;
						notifyToread = false;
					}
					data.notify = notify;
					data.notify_type = notifyType;
					data.notify_todo = notifyTodo;
					data.notify_toread = notifyToread;
					data.logBak_days = logBakDays;
					data.kkConfigStatus = kkConfigStatus;
					data.changeKKqrcodeEnabled = changeKKqrcodeEnabled;
					data.selectService = selectService;
					data.imTransferEnable=imTransferEnable;
					
					
					/* data = {"org":org, "org_visible":orgVisible, "sso":sso, "mobile_app":mobileApp, "pc_app":pcApp, "sms":sms, "robot":robot, "extend_app":extend_app, "notify":notify, "notify_type":notifyType, "notify_todo":notifyTodo
			        		, "notify_toread":notifyToread, "logBak_days":logBakDays, "kkConfigStatus":kkConfigStatus, "selectService":selectService}; */
				}else{
					data = {"org":false, "org_visible":false, "sso":false, "mobile_app":false, "pc_app":false, "sms":false,"robot":false, "extend_app":false, "notify":false,"todoSource":false, "notify_todo":false
			        		, "notify_toread":false, "kkConfigStatus":kkConfigStatus,"changeKKqrcodeEnabled":changeKKqrcodeEnabled,"selectService":selectService,"imTransferEnable":false};
				}
				
				
				$.ajax({
					type: 'POST',
					dataType: 'json',
					url: "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=saveKKConfig",
					data: {"data":JSON.stringify(data)},
					success: showResult,
					error: showResult
				});
			}
			
			
			seajs.use(['lui/dialog'], function(dialog){
				//重置配置
				window.intiConfig = function(){
					dialog.confirm('<bean:message key="third-im-kk:kk.config.view.reset.config"/>',function(ok){
						if(ok==true){
							window.location.href = "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=resetConfig";
						}
					});
				};
				window.showResult = function(data){
					if(true == data.result){
						dialog.alert("保存成功！", function(){
		            		window.location.href = "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=configView";
		            	});
		            }else{
		            	dialog.alert(data.msg, function(){
		            		window.location.href = "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=configView";
		            	});
		                return false;
		            }
				}
			});
			
			//登录kk控制台
			function loginKK(){
				$.ajax({
			        type: "post",
			        url: "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=loginKK",
			        async : false,
			        dataType: "json",
			        success: function (data ,textStatus, jqXHR)
			        {
			            if('0' == data.result ){
			            	window.open(data.conAddress + "/kk/kk/console/ekp_login.jsp?sign=" + data.sign);
			            }else{
			             	alert(data.error_msg);
			                return false;
			            }
			        }
		    	 });
			}
			
			//公众号推送
			function config_notify_type(notifyType){
				$("#notify_type").val(notifyType);
				var toAudit=document.getElementById("toAudit");
				var toview=document.getElementById("toview");
				var tr_kk5_gzh_Notify=document.getElementById("tr_kk5_gzh_Notify");
				var corpNotifyMessage=document.getElementById("corpNotifyMessage");
				var gzhNotifyMessage=document.getElementById("gzhNotifyMessage");
				
				if("corpNotify"==notifyType){
					tr_kk5_gzh_Notify.style.display = 'none';
					toAudit.style.display = 'table-row';
					toview.style.display = 'table-row';
					gzhNotifyMessage.style.display = 'none';
					corpNotifyMessage.style.display = '';
					//initCommonAccountList(false);
				}
				if("gzhNotify"==notifyType){
					tr_kk5_gzh_Notify.style.display = '';
					toAudit.style.display = 'none';
					toview.style.display = 'none';
					corpNotifyMessage.style.display = 'none';
					gzhNotifyMessage.style.display = '';
					initialGzhInfo();
				}
			}
			function initialGzhInfo(){
				var kk5ServerUrl = $("input[name='innerDomain']").val();
				var getGzhUrl=kk5ServerUrl+"serverj/info/listService.ajax";
				$.getJSON(Com_Parameter.ContextPath+"third/im/kk/webparts/getGzhInfo.jsp?getGzhUrl="+getGzhUrl,function (data){handle(data);})
			}
			function handle(data){
				gzhInfo=data;
				if(gzhInfo.length==0){
					alert("<bean:message bundle='third-im-kk' key='kk.config.view.handle.alert'/>");
				}else{
					initCommonAccountList(true);
					//设置下拉默认选中
					$("select[name='selectService']").find("option[value='${map.selectService}']").prop("selected",true);
					initchangeDescription('${map.selectService}');
				}
			}
			function initchangeDescription(value) {
				if (value == "choose") {
					document.getElementById("commonAccountDescription").innerHTML = "";
				} else {
					var values = getCommonAccountValues();
					for (var i = 0, length = values.length; i < length; i++) {
						if (values[i].value == value) {
							document.getElementById("commonAccountDescription").innerHTML = values[i].description;
							break;
						}
					}
				}
			};

			
			LUI.ready(function() {
				config_chgEnabled();
				todoConfig();
			});
		</script>
		<script type="text/javascript">
				var gzhInfo=new Array();
				var gzhSelected = null;
				var commonAccountListTdParam = {
					"border" : false,
					"width" : "60px",
					"type" : "select",
					"id" : "commonAccountList",
					"defaultInputText" : "",
					"checkInputText" : "",
					"defaultSelectValue" : "",
					"checkSelectValue" : "",
					"innerText" : ""
				};
	
				var commonAccountDescriptionTdParam = {
					"border" : false,
					"width" : "150px",
					"type" : "",
					"id" : "",
					"defaultInputText" : "",
					"checkInputText" : "",
					"defaultSelectValue" : "",
					"checkSelectValue" : "",
					"innerText" : ""
				};
	
				var commonAccountValues;

				function getCommonAccountValues() {
					if (commonAccountValues === undefined) {
						return new Array();
					}
					return commonAccountValues;
				};
				function createCommonAccountList(trElement, options) {
					if (trElement != null && options != null) {
						var tdElement = createTdElement(trElement,
								commonAccountListTdParam, options);
						 tdElement = createTdElement(trElement,
								commonAccountDescriptionTdParam, null);
						tdElement.id = "commonAccountDescription";
					}
				};
				function createTdElement(trElement, tdParam, options) {
					var tdElement = trElement.insertCell(trElement.cells.length);
					if (tdParam.border == true) {
						tdElement.style.borderTop = "0px";
					}
					if (tdParam.width != "") {
						tdElement.style.width = tdParam.width;
					}
					if (tdParam.type == "select") {
						tdElement.innerHTML = getSelectElementHTML(tdParam.id, options,
								tdParam.checkSelectValue, tdParam.defaultSelectValue)
								+ tdParam.innerText;
						return tdElement;
					}
					tdElement.innerHTML = tdParam.innerText;
					return tdElement;
				};
				function getSelectElementHTML(id, options, checkSelectValue,
						defaultSelectValue) {
					var rtnResult = new Array();
					rtnResult.push('<option value=\'choose\'>${ lfn:message("third-im-kk:robotnode_hint_3") }</option>');
					if (options == null || options.length == 0) {
						var value = checkSelectValue || defaultSelectValue;
						if (value != '')
							rtnResult.push('<option value=\'' + value + '\' selected>'
									+ value + '</option>');
					} else {
						var value = checkSelectValue || defaultSelectValue;
						for (var i = 0, length = options.length; i < length; i++) {
							var option = options[i], optionValue = option.value;
							rtnResult.push('<option value=\'' + optionValue + '\'');
							if (optionValue == value) {
								rtnResult.push(' selected');
							}
							rtnResult.push('>' + option.name + '</option>');
						}
					}
					if (id == "commonAccountList") {
						return '<select name="selectService" onchange="changeDescription(this);" id="' + id
								+ '"' + ' style=\'width: 160px;\'>' + rtnResult.join('')
								+ '</select>';
					}
					return '<select id="' + id + '"' + ' style=\'width: 160px;\'>'
							+ rtnResult.join('') + '</select>';
				};
				function changeDescription(select) {
					if (select.value == "choose") {
						document.getElementById("commonAccountDescription").innerHTML = "";
					} else {
						var values = getCommonAccountValues();
						for (var i = 0, length = values.length; i < length; i++) {
							if (values[i].value == select.value) {
								document.getElementById("commonAccountDescription").innerHTML = values[i].description;
								break;
							}
						}
					}
				};
				function transCommonAccountList(jsonArray) {
					var rtnResult = new Array();
					if (!jsonArray)
						return rtnResult;
					for (var i = 0, length = jsonArray.length; i < length; i++) {
						if(jsonArray[i].serviceState==0){
							continue;
						}
						rtnResult.push({
							value : jsonArray[i].corp+","+jsonArray[i].service,
							name : jsonArray[i].serviceName,
							description : jsonArray[i].serviceDesc
						});
					}
					return rtnResult;
				};
				function initCommonAccountList(enable) {
					var tdCommonAccount=document.getElementById("commonAccountList");
					var tdCommonAccountDescription=document.getElementById("commonAccountDescription");
					if(enable){
						if(tdCommonAccount==null){
							commonAccountListTdParam.checkSelectValue = gzhSelected;
							commonAccountValues=transCommonAccountList(gzhInfo);
							var values = getCommonAccountValues();
							for (var i = 0, length = values.length; i < length; i++) {
								if (values[i].value == commonAccountListTdParam.checkSelectValue) {
									commonAccountDescriptionTdParam.innerText = values[i].description;
									break;
								}
							}
							createCommonAccountList(document
									.getElementById("tr_kk5_gzh_Notify"), commonAccountValues);
						}else{
							document.getElementById("commonAccountList").value=gzhSelected;
							var values = getCommonAccountValues();
							for (var i = 0, length = values.length; i < length; i++) {
								if (values[i].value == gzhSelected) {
									document.getElementById("commonAccountDescription").innerHTML = values[i].description;
									break;
								}
							}
							tdCommonAccount.style.display="";
							tdCommonAccountDescription.style.display="";
						}
					}else{
						if(tdCommonAccount!=null){
							tdCommonAccount.style.display="none";
							tdCommonAccountDescription.style.display="none";
						}
					}
				};
		</script>
	</template:replace>
</template:include>