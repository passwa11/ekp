<%@page import="com.landray.kmss.third.weixin.mutil.forms.ThirdWxWorkConfigForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message key="third.weixin.work.config.setting" bundle="third-weixin-mutil" /></template:replace>
	<template:block name="path" >
		<span class=txtlistpath><bean:message key="third.weixin.work.config.currurl" bundle="third-weixin-mutil" />：<bean:message key="third.weixin.work.config.setting" bundle="third-weixin-mutil" /></span>
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
			<span class="profile_config_title"><bean:message key="third.weixin.work.config.setting" bundle="third-weixin-mutil" /></span>
		</h2>
		<html:form action="/third/weixin/mutil/thirdWxWorkConfig.do?autoclose=false" onsubmit="return validateAppConfigForm(this);">
			<center>
				<table id="wxBaseTable" class="tb_normal" width=100%>
					<tr id="wenxinEnableTR">
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxEnabledTitle" bundle="third-weixin-mutil" /></td>
						<td>
							<ui:switch property="value(wxEnabled)" onValueChange="wx_display_change();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div class="message"><bean:message key="third.weixin.work.config.wxEnabledMsg" bundle="third-weixin-mutil" /></div>
							<div class="txtstrong"><bean:message key="third.weixin.work.config.wxEnabledMsg.tip" bundle="third-weixin-mutil" /></div>
						</td>
					</tr>
					<!-- 企业微信名称 -->
					<tr>
						<td class="td_normal_title" width="15%">名称</td>
						<td>
							<xform:text property="value(wxName)" style="width:85%;" showStatus="edit" required="false" subject="名称" validators="wxRequire" />
							<span class="txtstrong">*</span>
							<div class="message">当前企业微信配置名称</div>
						</td>
					</tr>
					<!-- 标识 -->
					<tr>
						<td class="td_normal_title" width="15%">标识</td>
						<td>
							<xform:text property="value(wxKey)" style="width:85%;" showStatus="edit" required="false" subject="标识" validators="wxRequire"/>
							<span class="txtstrong">*</span>
							<div class="message">当前企业微信配置标识（例如：wx_1,此标识主要用区分多企业微信配置）</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.config.api.url.title" bundle="third-weixin-mutil" /></td>
						<td>
							<xform:text property="value(wx.api.url)" style="width:85%;" showStatus="edit" required="false" subject="${lfn:message('third-weixin-mutil:third.weixin.config.api.url.tip') }"/>
							<div class="message"><bean:message key="third.weixin.config.api.url.tip" bundle="third-weixin-mutil" /></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxCorpidTitle" bundle="third-weixin-mutil" /></td>
						<td>
							<xform:text property="value(wxCorpid)" style="width:85%;" showStatus="edit" required="false" subject="${lfn:message('third-weixin-mutil:third.weixin.work.config.wxCorpidTitle') }" validators="wxRequire"/>
							<span class="txtstrong">*</span>
							<div class="message"><bean:message key="third.weixin.work.config.wxCorpidMsg" bundle="third-weixin-mutil" /></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxDomainTitle" bundle="third-weixin-mutil" /></td>
						<td>
							<xform:text property="value(wxDomain)" subject="${lfn:message('third-weixin-mutil:third.weixin.work.config.wxDomainSubject') }" 
								required="false" style="width:85%" showStatus="edit" onValueChange="config_wx_callbackurl();"/>&nbsp;&nbsp;
							<ui:button text="${lfn:message('third-weixin-mutil:third.weixin.work.config.wxDomainBtn') }" onclick="config_wx_dns_getUrl();config_wx_callbackurl();" style="vertical-align: top;"></ui:button>	
							<div class="message"><bean:message key="third.weixin.work.config.wxDomainMsg" bundle="third-weixin-mutil" /></div>
							<div class="txtstrong"><bean:message key="third.weixin.work.config.wxDomainMsg.tip" bundle="third-weixin-mutil" /></div>
						</td>
					</tr>
					<%-- <tr>
						<td class="td_normal_title" width="15%">代理服务器设置</td>
						<td>
							<xform:text property="value(wxProxy)" subject="代理服务器地址" required="false" style="width:85%" showStatus="edit" validators="wxProxy"/>
							<div class="message">适用于EKP服务器无法直接访问微信服务器的系统,地址格式为:host:port</div>
						</td>
					</tr> --%>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxOauth2EnabledTitle" bundle="third-weixin-mutil" /></td>
						<td>
							<ui:switch property="value(wxOauth2Enabled)" onValueChange="" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div class="message"><bean:message key="third.weixin.work.config.wxOauth2EnableMsg" bundle="third-weixin-mutil" /></div>
						</td>
					</tr>
					<%-- <tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxPcScanLoginEnabledTitle" bundle="third-weixin-mutil" /></td>
						<td>
							<ui:switch property="value(wxPcScanLoginEnabled)" onValueChange="" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div class="message"><bean:message key="third.weixin.work.config.wxPcScanLoginEnabledMsg" bundle="third-weixin-mutil" /></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxSSOAgentIdTitle" bundle="third-weixin-mutil" /></td>
						<td>
							<xform:text property="value(wxSSOAgentId)" style="width:85%;" showStatus="edit" required="false"/>
							<div class="message"><bean:message key="third.weixin.work.config.wxSSOAgentIdMsg" bundle="third-weixin-mutil" /></div>
						</td>
					</tr> --%>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxCorpsecretTitle" bundle="third-weixin-mutil"/></td>
						<td>
							<xform:text property="value(wxCorpsecret)" subject="${lfn:message('third-weixin-mutil:third.weixin.work.config.wxCorpsecretSuject') }" required="false" style="width:85%" showStatus="edit" validators="wxRequire"/>
							<br/>
							<div class="message"><bean:message key="third.weixin.work.config.wxCorpsecretMsg" bundle="third-weixin-mutil"/></div>
						</td>
					</tr>
				</table>
				<br/>
				<table id="wxTodoTable" class="tb_normal" width=100%>
					<!-- 待办推送 -->
					<tr id="wxTodoTR">
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxTodoEnabledTitle" bundle="third-weixin-mutil" /></td>
						<td>
							<ui:switch property="value(wxTodoEnabled)" onValueChange="wxTodo_display_change()" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxTodoNotifyTypeTitle" bundle="third-weixin-mutil" /></td>
						<td>
							<xform:radio property="value(wxNotifyType)" subject="${lfn:message('third-weixin-mutil:third.weixin.work.config.wxNotifyTypeSubject') }" showStatus="edit" >
								<xform:simpleDataSource value="news"><bean:message key="third.weixin.work.config.wxNotifyTypeNews" bundle="third-weixin-mutil"/></xform:simpleDataSource>
								<%-- <xform:simpleDataSource value="text"><bean:message key="third.weixin.work.config.wxNotifyTypeText" bundle="third-weixin-mutil"/></xform:simpleDataSource> --%>
							</xform:radio>
							<%-- <div class="message"><bean:message key="third.weixin.work.config.wxNotifyTypeNewsMsg" bundle="third-weixin-mutil"/></div>
							<div class="message"><bean:message key="third.weixin.work.config.wxNotifyTypeTextMsg" bundle="third-weixin-mutil"/></div> --%>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxTodoAgentidTitle" bundle="third-weixin-mutil"/></td>
						<td>
							<xform:text property="value(wxAgentid)" subject="${lfn:message('third-weixin-mutil:third.weixin.work.config.wxAgentidSubject') }" required="false" 
								style="width:85%" showStatus="edit" validators="wxTodoRequire"/>
							<span class="txtstrong">*</span>
							<div class="message"><bean:message key="third.weixin.work.config.wxAgentidMsg" bundle="third-weixin-mutil"/><a class="weixinLink" href="https://work.weixin.qq.com" target="_blank"><bean:message key="third.weixin.work.config.wxAgentidMsg.weixinLink" bundle="third-weixin-mutil"/></a><bean:message key="third.weixin.work.config.wxAgentidMsg.tip" bundle="third-weixin-mutil"/></div>
						</td>
					</tr>
					<!-- 待阅推送 -->
						<tr>
							<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxTodoType2EnabledTitle" bundle="third-weixin-mutil" /></td>
							<td>
								<ui:switch property="value(wxTodoType2Enabled)" onValueChange="wxToRead_display_change()" 
									enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<div class="message"><bean:message key="third.weixin.work.config.wxTodoType2EnabledMsg" bundle="third-weixin-mutil" /></div>
							</td>
						</tr>
						<tr id="wxToReadNotifyTR">
							<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxToReadNotifyTypeTitle" bundle="third-weixin-mutil" /></td>
							<td>
								<xform:radio property="value(wxToReadNotifyType)" subject="${lfn:message('third-weixin-mutil:third.weixin.work.config.wxNotifyTypeSubject') }" showStatus="edit" >
									<xform:simpleDataSource value="news"><bean:message key="third.weixin.work.config.wxNotifyTypeNews" bundle="third-weixin-mutil"/></xform:simpleDataSource>
									<%-- <xform:simpleDataSource value="text"><bean:message key="third.weixin.work.config.wxNotifyTypeText" bundle="third-weixin-mutil"/></xform:simpleDataSource> --%>
								</xform:radio>
								<%-- <div class="message"><bean:message key="third.weixin.work.config.wxToReadNotifyTypeNewsMsg" bundle="third-weixin-mutil"/></div>
								<div class="message"><bean:message key="third.weixin.work.config.wxToReadNotifyTypeTextMsg" bundle="third-weixin-mutil"/></div> --%>
							</td>
						</tr>
						<tr id="wxToReadAgentidTR">
							<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxToReadAgentidTitle" bundle="third-weixin-mutil"/></td>
							<td>
								<xform:text property="value(wxToReadAgentid)" subject="${lfn:message('third-weixin-mutil:third.weixin.work.config.wxAgentidSubject') }" required="false" 
									style="width:50%" showStatus="edit" validators="wxToReadRequire"/>
								<span class="txtstrong">*</span>
								<span>&nbsp;&nbsp;&nbsp;&nbsp;
									<xform:checkbox property="value(wxToReadPre)">
										<xform:simpleDataSource value="true"><bean:message key="third.weixin.work.config.wxToRead.model" bundle="third-weixin-mutil"/></xform:simpleDataSource>
									</xform:checkbox>
								</span>
								<div class="message"><bean:message key="third.weixin.work.config.wxToReadAgentidMsg" bundle="third-weixin-mutil"/><a class="weixinLink" href="https://work.weixin.qq.com" target="_blank"><bean:message key="third.weixin.work.config.wxAgentidMsg.weixinLink" bundle="third-weixin-mutil"/></a><bean:message key="third.weixin.work.config.wxAgentidMsg.tip" bundle="third-weixin-mutil"/><bean:message key="third.weixin.work.config.wxToReadTip" bundle="third-weixin-mutil"/></div>
							</td>
						</tr>
				</table>
				<br/>
				<table id="wxOmsTable" class="tb_normal" width=100%>
					<tr id="wxOmsTR">
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxOmsOutEnabledTitle" bundle="third-weixin-mutil"/></td>
						<td>
							<ui:switch property="value(wxOmsOutEnabled)" onValueChange="wxOms_display_change()" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						</td>
					</tr>
					<%-- <tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxCorpsecretTitle" bundle="third-weixin-mutil"/></td>
						<td>
							<xform:text property="value(wxCorpsecret)" subject="${lfn:message('third-weixin-mutil:third.weixin.work.config.wxCorpsecretSuject') }" required="false" style="width:85%" showStatus="edit" validators="wxRequire1"/>
							<br/>
							<div class="message"><bean:message key="third.weixin.work.config.wxCorpsecretMsg" bundle="third-weixin-mutil"/></div>
						</td>
					</tr> --%>
					
					<%-- <tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxCallbackurlTitle" bundle="third-weixin-mutil"/></td>
						<td>
							<input type="hidden" name="wxUrlSuffix" value="/resource/third/wxwork/mutil/cpEndpoint.do?method=service&key=">
							<xform:text property="value(wxCallbackurl)" subject="URL" required="false" style="width:85%" showStatus="readOnly" />
							<div class="message"><bean:message key="third.weixin.work.config.wxCallbackurlMsg" bundle="third-weixin-mutil"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxTokenTitle" bundle="third-weixin-mutil"/></td>
						<td>
							<xform:text property="value(wxToken)" subject="Token" required="false" style="width:85%" showStatus="edit" validators="wxRequire1"/>
							<ui:button text="${lfn:message('third-weixin-mutil:third.weixin.work.config.wxTokenBtn') }" onclick="window.random_token();validation.validateElement(document.getElementsByName('value(wxToken)')[0]);" style="vertical-align: top;"></ui:button>
							<div class="message"><bean:message key="third.weixin.work.config.wxTokenMsg" bundle="third-weixin-mutil"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxAeskeyTitle" bundle="third-weixin-mutil"/></td>
						<td>
							<xform:text property="value(wxAeskey)" subject="${lfn:message('third-weixin-mutil:third.weixin.work.config.wxAeskeySubject') }" required="false" style="width:85%" showStatus="edit" validators="wxRequire1"/>
							<ui:button text="${lfn:message('third-weixin-mutil:third.weixin.work.config.wxAeskeyBtn') }" onclick="window.random_AESKey();validation.validateElement(document.getElementsByName('value(wxAeskey)')[0]);" style="vertical-align: top;"></ui:button>
							<div class="message"><bean:message key="third.weixin.work.config.wxAeskeyMsg" bundle="third-weixin-mutil"/></div>
							<div class="txtstrong"><bean:message key="third.weixin.work.config.wxAeskeyMsg.tip" bundle="third-weixin-mutil"/></div>
						</td>
					</tr> --%>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxOrgIdTitle" bundle="third-weixin-mutil"/></td>
						<td>
							<xform:address propertyId="value(wxOrgId)" propertyName="value(wxOrgName)" 
								mulSelect="true" orgType="ORG_TYPE_ORGORDEPT" showStatus="edit" onValueChange="wxOrgId_display_change()" 
								style="width:75%" subject="${lfn:message('third-weixin-mutil:third.weixin.work.config.wxOrgIdSubject') }" validators="wxOmsRequire">
							</xform:address>
							<span class="txtstrong">*</span>
							<ui:button text="${lfn:message('third-weixin-mutil:third.weixin.work.config.wxOrgIdBtn') }" onclick="cleanTime();" style="vertical-align: top;"></ui:button>
							<div class="message"><bean:message key="third.weixin.work.config.wxOrgIdMsg" bundle="third-weixin-mutil"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxOmsRootFlagTitle" bundle="third-weixin-mutil"/></td>
						<td>
							<ui:switch property="value(wxOmsRootFlag)" onValueChange="" 
								enabledText="${lfn:message('third-weixin-mutil:third.weixin.work.config.wxOmsRootFlag.sync') }" disabledText="${lfn:message('third-weixin-mutil:third.weixin.work.config.wxOmsRootFlag.asyn') }"></ui:switch>
							<div class="message"><bean:message key="third.weixin.work.config.wxOmsRootFlagMsg" bundle="third-weixin-mutil"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxOmsOrgPersonHandleTitle" bundle="third-weixin-mutil"/></td>
						<td>
							<xform:radio property="value(wxOmsOrgPersonHandle)">
								<xform:enumsDataSource enumsType="wx_oms_sync_handle" />
							</xform:radio>
							<div class="message"><bean:message key="third.weixin.work.config.wxOmsOrgPersonHandleMsg" bundle="third-weixin-mutil"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxRootIdTitle" bundle="third-weixin-mutil"/></td>
						<td>
							<xform:text property="value(wxRootId)" showStatus="edit" style="width:65%"/>
							<div class="message"><bean:message key="third.weixin.work.config.wxRootIdMsg" bundle="third-weixin-mutil"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxLoginNameTitle" bundle="third-weixin-mutil"/></td>
						<td>
							<xform:text property="value(wxLoginName)" showStatus="noShow"/>
							<div id="ln"><bean:message key="third.weixin.work.config.wxLoginNameLn" bundle="third-weixin-mutil"/></div>
							<div class="message"><bean:message key="third.weixin.work.config.wxLoginNameMsg" bundle="third-weixin-mutil"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxPersonOrderTitle" bundle="third-weixin-mutil"/></td>
						<td>
							<div id="ln">
								<bean:message key="third.weixin.work.config.wxPersonOrder" bundle="third-weixin-mutil"/>:
								<xform:radio property="value(wxPersonOrder)" showStatus="edit">
									<xform:simpleDataSource value="0"><bean:message key="third.weixin.work.config.wxPersonOrder.asc" bundle="third-weixin-mutil"/></xform:simpleDataSource>
									<xform:simpleDataSource value="1"><bean:message key="third.weixin.work.config.wxPersonOrder.desc" bundle="third-weixin-mutil"/></xform:simpleDataSource>
								</xform:radio>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<bean:message key="third.weixin.work.config.wxDeptOrder" bundle="third-weixin-mutil"/>:
								<xform:radio property="value(wxDeptOrder)" showStatus="edit">
									<xform:simpleDataSource value="0"><bean:message key="third.weixin.work.config.wxPersonOrder.asc" bundle="third-weixin-mutil"/></xform:simpleDataSource>
									<xform:simpleDataSource value="1"><bean:message key="third.weixin.work.config.wxPersonOrder.desc" bundle="third-weixin-mutil"/></xform:simpleDataSource>
								</xform:radio>
							</div>
							<div class="message"><bean:message key="third.weixin.work.config.wxPersonOrderMsg" bundle="third-weixin-mutil"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.extendfiled" bundle="third-weixin-mutil"/></td>
						<td>
							<table style="width: 100%">
								<tr>
									<td width="150px"><ui:switch property="value(wxOfficePhone)" enabledText="${lfn:message('third-weixin-mutil:third.weixin.work.config.officephone') }" disabledText="${lfn:message('third-weixin-mutil:third.weixin.work.config.officephone') }"></ui:switch></td>
									<td><ui:switch property="value(wxPostEnabled)" enabledText="${lfn:message('third-weixin-mutil:third.weixin.config.dingPostEnabled') }" disabledText="${lfn:message('third-weixin-mutil:third.weixin.config.dingPostEnabled') }"></ui:switch></td>
									
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="fdKey" value="" />
			<center style="margin:20px 0;">
				<!-- 保存 -->
				<ui:button text="${lfn:message('button.save')}" onclick="window.wxSubmit();" width="120" height="35"></ui:button>
			</center>
		</html:form>
		<script type="text/javascript">
			var validation = $KMSSValidation();
			<%-- var woi = "<%=WeixinWorkConfig.newInstance().getWxOrgId()   %>"; --%>
			function setLoginName(){
				var value = $('[name="value(wxLoginName)"]').val();
				if(value==""){
					$('[name="value(wxLoginName)"]').val("loginname");
				}else{
					if(value=="id"){
						$("#ln").empty();
						$("#ln").text("<bean:message key='third.weixin.work.config.ekpPrimary' bundle='third-weixin-mutil'/>");
					}else{
						$("#ln").empty();
						$("#ln").text("<bean:message key='third.weixin.work.config.wxLoginNameLn' bundle='third-weixin-mutil'/>");
						$('[name="value(wxLoginName)"]').val("loginname");
					}
				}
			}
			function cleanTime(){
				if(confirm("<bean:message key='third.weixin.work.config.clearMsg' bundle='third-weixin-mutil'/>")){
					var url = '<c:url value="/third/wxwork/mutil/weixinSynchroOrgCheck.do?method=cleanTime" />';
					$.ajax({
					   type: "POST",
					   url: url,
					   data:{"key":$('[name="value(wxKey)"]').val()},
					   async:false,
					   dataType: "json",
					   success: function(data){
							if(data.status=="1"){
								alert("<bean:message key='third.weixin.work.config.clearSuccess' bundle='third-weixin-mutil'/>");
							}else{
								alert(data.msg);
							}
					   }
					});
				}
			}
			function wxOrgId_display_change(){
				var value = $('[name="value(wxOrgId)"]').val();
				/* if(value!=""&&woi!=""&&woi!=value){ */
					var tip = "<bean:message key='third.weixin.work.config.org.tip' bundle='third-weixin-mutil'/>";
					var tip1 = "<bean:message key='third.weixin.work.config.org.tip1' bundle='third-weixin-mutil'/>";
					var tip2 = "<bean:message key='third.weixin.work.config.org.tip2' bundle='third-weixin-mutil'/>";
					var tip3 = "<bean:message key='third.weixin.work.config.org.tip3' bundle='third-weixin-mutil'/>";
					alert("同步根机构发生更改，下次同步将会执行全量同步。请确认！"+
							"注意："+
							"ekp根机构默认同步到企业微信的根机构下；若要ekp同步到某个子部门下，可在“企业微信的根机构ID”填上该子部门的ID；");
				/* } */
			}
			seajs.use(['lui/jquery'], function($) {
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
							$('[name="value(wxTodoType2Enabled)"]').val(false);
						}
						wxToRead_display_change();
					}
					
					function wxToRead_display_change(){
						var value = $('[name="value(wxTodoType2Enabled)"]').val();
						if(value == 'true'){
							$('#wxTodoTable tr[id="wxToReadNotifyTR"]').show();
							$('#wxTodoTable tr[id="wxToReadAgentidTR"]').show();
						}else{
							$('#wxTodoTable tr[id="wxToReadNotifyTR"]').hide();
							$('#wxTodoTable tr[id="wxToReadAgentidTR"]').hide();
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
						var wxKey = $("[name='value(wxKey)']").val();
						$('[name="value(wxCallbackurl)"]').val(wxDomain + wxUrlSuffix + wxKey);
					}
					function config_wx_dns_getUrl(){
						var protocol = location.protocol,
							host = location.host,
							contextPath = seajs.data.env.contextPath;
						$('[name="value(wxDomain)"]').val(protocol + '//' + host + contextPath );
					}
					//设置待办消息类型
					function config_wx_notify_type(wxNotifyType){
						var type = wxNotifyType,
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
					
					validation.addValidator('wxRequire',"<bean:message key='third.weixin.work.config.notnull' bundle='third-weixin-mutil'/>",function(v, e, o){
						var wxEnabled = $('[name="value(wxEnabled)"]').val();
						if(wxEnabled == 'true' && !v){
							var validator = this.getValidator('wxRequire'),
								error = "<bean:message key='third.weixin.work.config.notnull' bundle='third-weixin-mutil'/>";
							if($(e).attr('subject')){
								error = $(e).attr('subject') + error;
							}	
							validator.error = error;
							return false;
						}
						return true;
					});
					validation.addValidator('wxRequire1',"<bean:message key='third.weixin.work.config.notnull' bundle='third-weixin-mutil'/>",function(v, e, o){
						var wxEnabled = $('[name="value(wxOmsOutEnabled)"]').val();
						if(wxEnabled == 'true' && !v){
							var validator = this.getValidator('wxRequire1'),
								error = "<bean:message key='third.weixin.work.config.notnull' bundle='third-weixin-mutil'/>";
							if($(e).attr('subject')){
								error = $(e).attr('subject') + error;
							}	
							validator.error = error;
							return false;
						}
						return true;
					});

					validation.addValidator('wxTodoRequire','{name}'+"<bean:message key='third.weixin.work.config.notnull' bundle='third-weixin-mutil'/>",function(v, e, o){
						var wxEnabled = $('[name="value(wxEnabled)"]').val();
						if(wxEnabled != 'true'){
							return true;
						}
						var todoEnabled = $('[name="value(wxTodoEnabled)"]').val();
						return (todoEnabled == 'true' && !v) ? false : true;
					});
					validation.addValidator('wxToReadRequire','{name}'+"<bean:message key='third.weixin.work.config.notnull' bundle='third-weixin-mutil'/>",function(v, e, o){
						var wxEnabled = $('[name="value(wxEnabled)"]').val();
						if(wxEnabled != 'true'){
							return true;
						}
						var todoEnabled = $('[name="value(wxTodoType2Enabled)"]').val();
						return (todoEnabled == 'true' && !v) ? false : true;
					});

					validation.addValidator('wxOmsRequire','{name}'+"<bean:message key='third.weixin.work.config.notnull' bundle='third-weixin-mutil'/>",function(v, e, o){
						var wxEnabled = $('[name="value(wxEnabled)"]').val();
						if(wxEnabled != 'true'){
							return true;
						}
						var omsEnabled = $('[name="value(wxOmsOutEnabled)"]').val();
						return (omsEnabled == 'true' && !v) ? false : true;
					});
					
					function set_wxName_disabled(){
						var wxName = $('[name="value(wxName)"]');
						var wxKey = $('[name="value(wxKey)"]');
						if(wxName.val() != "" ){
							wxName.attr("readonly","readonly");
						}
						if(wxKey.val() != "" ){
							wxKey.attr("readonly","readonly");
						}
					}

					LUI.ready(function(){
						wx_display_change();
						wxTodo_display_change();
						wxToRead_display_change();
						wxOms_display_change();
						config_wx_callbackurl();
						config_wx_notify_type(document.getElementsByName("value(wxNotifyType)"));
						config_wx_notify_type(document.getElementsByName("value(wxToReadNotifyType)"));
						setLoginName(); 
						set_wxName_disabled();
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
						$('[name="value(wxToReadAgentid)"]').val($.trim($('[name="value(wxToReadAgentid)"]').val()));
						$('[name="value(wxCorpsecret)"]').val($.trim($('[name="value(wxCorpsecret)"]').val()));
						$('[name="value(wxDomain)"]').val($.trim($('[name="value(wxDomain)"]').val()));
						$('[name="value(wxToken)"]').val($.trim($('[name="value(wxToken)"]').val()));
						$('[name="value(wxAeskey)"]').val($.trim($('[name="value(wxAeskey)"]').val()));
						$('[name="value(wxRootId)"]').val($.trim($('[name="value(wxRootId)"]').val()));
						$('[name="value(wxSSOAgentId)"]').val($.trim($('[name="value(wxSSOAgentId)"]').val()));
						$('[name="fdKey"]').val($.trim($('[name="value(wxKey)"]').val()));
						
						Com_Submit(document.thirdWxWorkConfigForm, 'update');
					};
					
					window.wx_display_change = wx_display_change;
					window.wxTodo_display_change = wxTodo_display_change;
					window.wxToRead_display_change = wxToRead_display_change;
					window.wxOms_display_change = wxOms_display_change;
					window.random_token = random_token;
					window.random_AESKey = random_AESKey;
					window.config_wx_callbackurl = config_wx_callbackurl;
					window.config_wx_dns_getUrl = config_wx_dns_getUrl;
				});
		</script>
	</template:replace>	
</template:include>
