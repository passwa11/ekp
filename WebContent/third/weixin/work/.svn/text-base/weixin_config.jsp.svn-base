<%@page import="com.landray.kmss.third.weixin.work.model.WeixinWorkConfig"%>
<%@page import="com.landray.kmss.sys.appconfig.forms.SysAppConfigForm"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%@page import="java.util.*"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.third.weixin.work.service.IThirdWeixinWorkContactService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<% 

    request.setAttribute("fd_constom_List", WeixinWorkConfig.getCustomData());

	//ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
	//Map orgMap = sysAppConfigService.findByKey("com.landray.kmss.third.weixin.work.model.WeixinWorkConfig");
	SysAppConfigForm sysAppConfigForm = (SysAppConfigForm)request.getAttribute("sysAppConfigForm");
	//旧数据兼容   人员排序
	Object oldData = sysAppConfigForm.getValue("wxPersonOrder"); //人员排序
	if(oldData != null && "1".equals(oldData.toString())){
		sysAppConfigForm.setValue("org2wxWork.orderInDepts", "desc"); 
		sysAppConfigForm.setValue("org2wxWork.orderInDepts.synWay", "syn"); 
	}else if(oldData != null && "0".equals(oldData.toString())){
		sysAppConfigForm.setValue("org2wxWork.orderInDepts", "asc"); 
		sysAppConfigForm.setValue("org2wxWork.orderInDepts.synWay", "syn"); 
	}
	
	oldData = sysAppConfigForm.getValue("wxDeptOrder"); //部门排序
	if(oldData != null && "1".equals(oldData.toString())){
		sysAppConfigForm.setValue("org2wxWork.dept.order", "desc"); 
		sysAppConfigForm.setValue("org2wxWork.dept.order.synWay", "syn"); 
	}else if(oldData != null && "0".equals(oldData.toString())){
		sysAppConfigForm.setValue("org2wxWork.dept.order", "asc"); 
		sysAppConfigForm.setValue("org2wxWork.dept.order.synWay", "syn"); 
	}
	
	oldData = sysAppConfigForm.getValue("wxOfficePhone"); //办公电话
	if(oldData != null && "true".equals(oldData.toString())){
		sysAppConfigForm.setValue("org2wxWork.tel", "fdWorkPhone"); 
		sysAppConfigForm.setValue("org2wxWork.tel.synWay", "syn"); 
	}

	oldData = sysAppConfigForm.getValue("wxPostEnabled"); //职位
	if(oldData != null && "true".equals(oldData.toString())){
		sysAppConfigForm.setValue("org2wxWork.position", "hbmPosts"); 
		sysAppConfigForm.setValue("org2wxWork.position.synWay", "syn"); 
	}

	String ecoEnable = ResourceUtil.getKmssConfigString("kmss.org.eco.enabled");
	request.setAttribute("ecoEnable",ecoEnable);
	if("true".equals(ecoEnable)){
		IThirdWeixinWorkContactService thirdWeixinWorkContactService = (IThirdWeixinWorkContactService)SpringBeanUtil.getBean("thirdWeixinWorkContactService");
		String orgTypeSettingNew = thirdWeixinWorkContactService.updateOrgTypeSetting();
		if(StringUtil.isNotNull(orgTypeSettingNew)){
			sysAppConfigForm.setValue("syncContact.orgType.setting",orgTypeSettingNew);
		}
	}

%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message key="third.weixin.work.config.setting" bundle="third-weixin-work" /></template:replace>
	<template:block name="path" >
		<span class=txtlistpath><bean:message key="third.weixin.work.config.currurl" bundle="third-weixin-work" />：<bean:message key="third.weixin.work.config.setting" bundle="third-weixin-work" /></span>
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
			<span class="profile_config_title"><bean:message key="third.weixin.work.config.setting" bundle="third-weixin-work" /></span>
		</h2>
		<html:form action="/third/weixin/work/sysAppConfig.do?autoclose=false" onsubmit="return validateAppConfigForm(this);">
			<center>
				<ui:tabpanel>
					<!-- 基础配置 -->
					<ui:content id="tag1" title="${ lfn:message('third-weixin-work:thirdWeixin.tab.base.setting') }">
						
				<table id="wxBaseTable" class="tb_normal" width=100%>
					<tr id="wenxinEnableTR">
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxEnabledTitle" bundle="third-weixin-work" /></td>
						<td>
							<ui:switch property="value(wxEnabled)" onValueChange="wx_display_change();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.config.api.url.title" bundle="third-weixin-work" /></td>
						<td>
							<xform:text property="value(wx.api.url)" style="width:85%;" showStatus="edit" required="false" subject="${lfn:message('third-weixin-work:third.weixin.config.api.url.tip') }"/>
							<div class="message"><bean:message key="third.weixin.config.api.url.tip" bundle="third-weixin-work" /></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxCorpidTitle" bundle="third-weixin-work" /></td>
						<td>
							<xform:text property="value(wxCorpid)" style="width:85%;" showStatus="edit" required="false" subject="${lfn:message('third-weixin-work:third.weixin.work.config.wxCorpidTitle') }" validators="wxRequire"/>
							<span class="txtstrong">*</span>
							<div class="message"><bean:message key="third.weixin.work.config.wxCorpidMsg" bundle="third-weixin-work" /></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">${lfn:message('third-weixin-work:thirdWeixin.agentId.default') }</td>
						<td>
							<xform:text property="value(wxAgentid)" subject="${lfn:message('third-weixin-work:third.weixin.work.config.wxAgentidSubject') }" required="false" 
								style="width:85%" showStatus="edit" validators="wxRequire"/>
							<span class="txtstrong">*</span>
							<div class="message">
								<bean:message key="thirdWeixin.agentId.default.tip.1" bundle="third-weixin-work" />
								<a class="weixinLink" href="https://work.weixin.qq.com" target="_blank">
									<bean:message key="third.weixin.work.config.wxAgentidMsg.weixinLink" bundle="third-weixin-work"/>
								</a><bean:message key="third.weixin.work.config.wxAgentidMsg.tip" bundle="third-weixin-work"/>
								同时也需要在 <a class="weixinLink" href="${LUI_ContextPath }/third/weixin/work/third_weixin_work/thirdWeixinWork.do?method=list" target="_blank">应用配置</a> 中配置对应的应用信息
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxDomainTitle" bundle="third-weixin-work" /></td>
						<td>
							<xform:text property="value(wxDomain)" subject="${lfn:message('third-weixin-work:third.weixin.work.config.wxDomainSubject') }" 
								required="false" style="width:85%" showStatus="edit" onValueChange="config_wx_callbackurl();"/>&nbsp;&nbsp;
							<ui:button text="${lfn:message('third-weixin-work:third.weixin.work.config.wxDomainBtn') }" onclick="config_wx_dns_getUrl();config_wx_callbackurl();" style="vertical-align: top;"></ui:button>	
							<div class="message"><bean:message key="third.weixin.work.config.wxDomainMsg" bundle="third-weixin-work" /></div>
							<div class="txtstrong"><bean:message key="third.weixin.work.config.wxDomainMsg.tip" bundle="third-weixin-work" /></div>
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
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxCorpsecretTitle" bundle="third-weixin-work"/></td>
						<td>
							<xform:text property="value(wxCorpsecret)" subject="${lfn:message('third-weixin-work:third.weixin.work.config.wxCorpsecretSuject') }" style="width:85%" showStatus="edit" validators="wxRequire"/>
							<span class="txtstrong">*</span>
							<br/>
							<div class="message"><bean:message key="third.weixin.work.config.wxCorpsecretMsg" bundle="third-weixin-work"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxSSOAttendSecretTitle" bundle="third-weixin-work" /></td>
						<td>
							<xform:text property="value(wxSSOAttendSecret)" style="width:85%;" showStatus="edit" required="false"/>
							<div class="message"><bean:message key="third.weixin.work.config.wxSSOAttendSecretMsg" bundle="third-weixin-work" /></div>
						</td>
					</tr>
					
					<!-- 回调信息 -->
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxCallbackurlTitle" bundle="third-weixin-work"/></td>
						<td>
							<input type="hidden" name="wxUrlSuffix" value="/resource/third/wxwork/cpEndpoint.do?method=service">
							<xform:text property="value(wxCallbackurl)" subject="URL" required="false" style="width:85%" showStatus="readOnly" />
							<div class="message"><bean:message key="third.weixin.work.config.wxCallbackurlMsg" bundle="third-weixin-work"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxTokenTitle" bundle="third-weixin-work"/></td>
						<td>
							<xform:text property="value(wxToken)" subject="Token" required="false" style="width:85%" showStatus="edit" validators="wxRequire1"/>
							<ui:button text="${lfn:message('third-weixin-work:third.weixin.work.config.wxTokenBtn') }" onclick="window.random_token();validation.validateElement(document.getElementsByName('value(wxToken)')[0]);" style="vertical-align: top;"></ui:button>
							<div class="message"><bean:message key="third.weixin.work.config.wxTokenMsg" bundle="third-weixin-work"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxAeskeyTitle" bundle="third-weixin-work"/></td>
						<td>
							<xform:text property="value(wxAeskey)" subject="${lfn:message('third-weixin-work:third.weixin.work.config.wxAeskeySubject') }" required="false" style="width:85%" showStatus="edit" validators="wxRequire1"/>
							<ui:button text="${lfn:message('third-weixin-work:third.weixin.work.config.wxAeskeyBtn') }" onclick="window.random_AESKey();validation.validateElement(document.getElementsByName('value(wxAeskey)')[0]);" style="vertical-align: top;"></ui:button>
							<div class="message"><bean:message key="third.weixin.work.config.wxAeskeyMsg" bundle="third-weixin-work"/></div>
							<div class="txtstrong"><bean:message key="third.weixin.work.config.wxAeskeyMsg.tip" bundle="third-weixin-work"/></div>
						</td>
					</tr>
					
				</table>
				</ui:content>
				
				<ui:content id="tag2" title="${ lfn:message('third-weixin-work:thirdWeixin.tab.sso.setting') }">
				<table id="wxSsoTable" class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxOauth2EnabledTitle" bundle="third-weixin-work" /></td>
						<td>
							<ui:switch property="value(wxOauth2Enabled)" onValueChange="" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div class="message"><bean:message key="third.weixin.work.config.wxOauth2EnableMsg" bundle="third-weixin-work" /></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxPcScanLoginEnabledTitle" bundle="third-weixin-work" /></td>
						<td>
							<ui:switch property="value(wxPcScanLoginEnabled)" onValueChange="" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div class="message"><bean:message key="third.weixin.work.config.wxPcScanLoginEnabledMsg" bundle="third-weixin-work" /></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxSSOAgentIdTitle" bundle="third-weixin-work" /></td>
						<td>
							<xform:text property="value(wxSSOAgentId)" style="width:85%;" showStatus="edit" required="false"/>
							<div class="message"><bean:message key="third.weixin.work.config.wxSSOAgentIdMsg" bundle="third-weixin-work" /></div>
						</td>
					</tr>
					
					<tr>
						<td class="td_normal_title" width="15%">${lfn:message('third-weixin-work:third.weixin.work.config.internalLogin') }</td>
						<td>
							<ui:switch property="value(wxAuthCheckEnabled)" onValueChange="wxAuth_display_change()" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div id="authCheck_div">
								<table width=95% >
									<tr >
										<td>
											${lfn:message('third-weixin-work:third.weixin.work.config.loginToken') }:<xform:text property="value(wxAuchCheckToken)" style="width:60%;" showStatus="edit" required="false" validators="wxAuthCheckRequire"/>
										</td>
										<td><span class="txtstrong">*</span></td>
									</tr>
									<tr >
										<td>
											${lfn:message('third-weixin-work:third.weixin.work.config.loginAESKey') }:<xform:text property="value(wxAuchCheckAESKey)" style="width:60%;" showStatus="edit" required="false" validators="wxAuthCheckRequire"/>
										</td>
										<td><span class="txtstrong">*</span></td>
									</tr>
									<tr>
										<td colspan="2">
											<span style="color:red">${lfn:message('third-weixin-work:third.weixin.work.config.attest') }</span>
										</td>
									</tr>
									<tr>
										<td>
											${lfn:message('third-weixin-work:third.weixin.work.config.acceptToken')}:<xform:text property="value(wxUpdatePassToken)" style="width:60%;" showStatus="edit" required="false" validators="wxAuthCheckRequire"/>
										</td>
										<td><span class="txtstrong">*</span></td>
									</tr>
									<tr>
										<td>
											${lfn:message('third-weixin-work:third.weixin.work.config.acceptAESKey')}:<xform:text property="value(wxUpdatePassAESKey)" style="width:60%;" showStatus="edit" required="false" validators="wxAuthCheckRequire"/>
										</td>
										<td><span class="txtstrong">*</span></td>
									</tr>
									<tr>
										<td colspan="2">
											<span style="color:red">${lfn:message('third-weixin-work:third.weixin.work.config.receiver')}</span>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>

					<tr>
						<td class="td_normal_title" width="15%">单点时哈希("#"号)处理</td>
						<td>
							<xform:checkbox property="value(wxSSOHashHandle)"  className="selectsgl" subject="" style="width:45%"  showStatus="edit">
								<xform:simpleDataSource value="pc">PC端</xform:simpleDataSource>
								<xform:simpleDataSource value="mobile">移动端</xform:simpleDataSource>
							</xform:checkbox>

							<div class="message" style="color:red">注意：该功能采用前端跳转(中间页面)方式实现，可能存在首次"返回"上一页时，回到中间页面，页面会稍微停顿后关闭的情况。请谨慎选择</div>
						</td>
					</tr>
				</table>
				</ui:content>
				
				
				<ui:content id="tag3" title="${ lfn:message('third-weixin-work:thirdWeixin.tab.notify.setting') }">
				<table id="wxTodoTable" class="tb_normal" width=100%>
					<!-- 待办推送 -->
					<tr id="wxTodoTR">
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxTodoEnabledTitle" bundle="third-weixin-work" /></td>
						<td>
							<ui:switch property="value(wxTodoEnabled)" onValueChange="wxTodo_display_change()" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						</td>
					</tr>
					<%-- <tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxTodoNotifyTypeTitle" bundle="third-weixin-work" /></td>
						<td>
							<xform:radio property="value(wxNotifyType)" subject="${lfn:message('third-weixin-work:third.weixin.work.config.wxNotifyTypeSubject') }" showStatus="edit" >
								<xform:simpleDataSource value="news"><bean:message key="third.weixin.work.config.wxNotifyTypeNews" bundle="third-weixin-work"/></xform:simpleDataSource>
								<xform:simpleDataSource value="text"><bean:message key="third.weixin.work.config.wxNotifyTypeText" bundle="third-weixin-work"/></xform:simpleDataSource>
							</xform:radio>
							<div class="message"><bean:message key="third.weixin.work.config.wxNotifyTypeNewsMsg" bundle="third-weixin-work"/></div>
							<div class="message"><bean:message key="third.weixin.work.config.wxNotifyTypeTextMsg" bundle="third-weixin-work"/></div>
						</td>
					</tr> --%>
					<tr>
					<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxTodoNotifyTypeTitle" bundle="third-weixin-work" /></td>
						<td>
							<xform:radio property="value(wxNotifySendType)" subject="${lfn:message('third-weixin-work:third.weixin.work.config.wxNotifyTypeSubject') }" showStatus="edit" >
								<xform:simpleDataSource value="news1"><bean:message key="third.weixin.work.config.wxNotifyTypeNews" bundle="third-weixin-work"/></xform:simpleDataSource>
								<xform:simpleDataSource value="taskcard"><bean:message key="third.weixin.work.config.wxNotifyTypeTaskcard" bundle="third-weixin-work"/></xform:simpleDataSource>
							</xform:radio>
							<%-- 
							<div class="message"><bean:message key="third.weixin.work.config.wxNotifyTypeNewsMsg" bundle="third-weixin-work"/></div>
							<div class="message"><bean:message key="third.weixin.work.config.wxNotifyTypeTextMsg" bundle="third-weixin-work"/></div>
							--%>
						</td>
					</tr>

					<!-- 待办打开方式 -->
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.dingTodoPcOpenType" bundle="third-weixin-work"/></td>
						<td>
							<xform:radio property="value(wxWorkTodoPcOpenType)" showStatus="edit">
								<xform:simpleDataSource value="in" textKey="third.weixin.work.config.dingTodoPcOpenType.in" bundle="third-weixin-work"></xform:simpleDataSource>
								<xform:simpleDataSource value="out" textKey="third.weixin.work..config.dingTodoPcOpenType.out" bundle="third-weixin-work"></xform:simpleDataSource>
							</xform:radio>
							<div class="message"> <bean:message key="third.weixin.work.config.dingTodoPcOpenType.tip" bundle="third-weixin-work"/></div>
						</td>
					</tr>

					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxTodoAgentidTitle" bundle="third-weixin-work"/></td>
						<td><xform:text property="value(wxAgentid)" 
								style="width:85%" showStatus="view" />(${lfn:message('third-weixin-work:thirdWeixin.default') })
							</td>
					</tr>
					<!-- 待阅推送 -->
						<tr>
							<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxTodoType2EnabledTitle" bundle="third-weixin-work" /></td>
							<td>
								<ui:switch property="value(wxTodoType2Enabled)" onValueChange="wxToRead_display_change()" 
									enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<div class="message"><bean:message key="third.weixin.work.config.wxTodoType2EnabledMsg" bundle="third-weixin-work" /></div>
							</td>
						</tr>
						<%-- <tr id="wxToReadNotifyTR">
							<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxToReadNotifyTypeTitle" bundle="third-weixin-work" /></td>
							<td>
								<xform:radio property="value(wxToReadNotifyType)" subject="${lfn:message('third-weixin-work:third.weixin.work.config.wxNotifyTypeSubject') }" showStatus="edit" >
									<xform:simpleDataSource value="news"><bean:message key="third.weixin.work.config.wxNotifyTypeNews" bundle="third-weixin-work"/></xform:simpleDataSource>
									<xform:simpleDataSource value="text"><bean:message key="third.weixin.work.config.wxNotifyTypeText" bundle="third-weixin-work"/></xform:simpleDataSource>
								</xform:radio>
								<div class="message"><bean:message key="third.weixin.work.config.wxToReadNotifyTypeNewsMsg" bundle="third-weixin-work"/></div>
								<div class="message"><bean:message key="third.weixin.work.config.wxToReadNotifyTypeTextMsg" bundle="third-weixin-work"/></div>
							</td>
						</tr> --%>
						<tr id="wxToReadAgentidTR">
							<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxToReadAgentidTitle" bundle="third-weixin-work"/></td>
							<td>
								<xform:text property="value(wxToReadAgentid)" subject="${lfn:message('third-weixin-work:third.weixin.work.config.wxAgentidSubject') }" required="false" 
									style="width:50%" showStatus="edit" validators="wxToReadRequire"/>
								<span class="txtstrong">*</span>
								<span>&nbsp;&nbsp;&nbsp;&nbsp;
									<xform:checkbox property="value(wxToReadPre)">
										<xform:simpleDataSource value="true"><bean:message key="third.weixin.work.config.wxToRead.model" bundle="third-weixin-work"/></xform:simpleDataSource>
									</xform:checkbox>
								</span>
								<div class="message"><bean:message key="third.weixin.work.config.wxToReadAgentidMsg" bundle="third-weixin-work"/><a class="weixinLink" href="https://work.weixin.qq.com" target="_blank"><bean:message key="third.weixin.work.config.wxAgentidMsg.weixinLink" bundle="third-weixin-work"/></a><bean:message key="third.weixin.work.config.wxAgentidMsg.tip" bundle="third-weixin-work"/><bean:message key="third.weixin.work.config.wxToReadTip" bundle="third-weixin-work"/></div>
							</td>
						</tr>
				</table>
				</ui:content>
				
			   <ui:content id="tag4" title="${ lfn:message('third-weixin-work:thirdWeixin.tab.org.setting') }">
				 <br/>
				 <table id="syncSelection" class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.syncSelection.title" bundle="third-weixin-work"/></td>
						<td>
							<xform:radio  property="value(syncSelection)" alignment="V" onValueChange="syncSelection_display_change(this);">
									<xform:simpleDataSource value="toWx"><bean:message key="third.weixin.work.config.syncSelection.toWx" bundle="third-weixin-work"/></xform:simpleDataSource>
									<xform:simpleDataSource value="fromWx"><bean:message key="third.weixin.work.config.syncSelection.fromWx" bundle="third-weixin-work"/></xform:simpleDataSource>
									<xform:simpleDataSource value="notSyn"><bean:message key="third.weixin.work.config.syncSelection.notSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
							</xform:radio>
							<div class="message" style="color: red;"><bean:message key="third.weixin.work.config.syncSelection.tip" bundle="third-weixin-work"/></div>
						</td>
					</tr>
				 </table>
				
				<table id="wxOmsTable" class="tb_normal" width=100%>
				
					<%-- <tr id="wxOmsTR">
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxOmsOutEnabledTitle" bundle="third-weixin-work"/></td>
						<td>
							<ui:switch property="value(wxOmsOutEnabled)" onValueChange="wxOms_display_change()" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						</td>
					</tr> --%>
					
					<%-- <tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxCorpsecretTitle" bundle="third-weixin-work"/></td>
						<td>
							<xform:text property="value(wxCorpsecret)" subject="${lfn:message('third-weixin-work:third.weixin.work.config.wxCorpsecretSuject') }" required="false" style="width:85%" showStatus="edit" validators="wxRequire1"/>
							<br/>
							<div class="message"><bean:message key="third.weixin.work.config.wxCorpsecretMsg" bundle="third-weixin-work"/></div>
						</td>
					</tr> --%>
					<!-- 回调信息 -->
				
					<%--  <tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxCallbackurlTitle" bundle="third-weixin-work"/></td>
						<td>
							<input type="hidden" name="wxUrlSuffix" value="/resource/third/wxwork/cpEndpoint.do?method=service">
							<xform:text property="value(wxCallbackurl)" subject="URL" required="false" style="width:85%" showStatus="readOnly" />
							<div class="message"><bean:message key="third.weixin.work.config.wxCallbackurlMsg" bundle="third-weixin-work"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxTokenTitle" bundle="third-weixin-work"/></td>
						<td>
							<xform:text property="value(wxToken)" subject="Token" required="false" style="width:85%" showStatus="edit" validators="wxRequire1"/>
							<ui:button text="${lfn:message('third-weixin-work:third.weixin.work.config.wxTokenBtn') }" onclick="window.random_token();validation.validateElement(document.getElementsByName('value(wxToken)')[0]);" style="vertical-align: top;"></ui:button>
							<div class="message"><bean:message key="third.weixin.work.config.wxTokenMsg" bundle="third-weixin-work"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxAeskeyTitle" bundle="third-weixin-work"/></td>
						<td>
							<xform:text property="value(wxAeskey)" subject="${lfn:message('third-weixin-work:third.weixin.work.config.wxAeskeySubject') }" required="false" style="width:85%" showStatus="edit" validators="wxRequire1"/>
							<ui:button text="${lfn:message('third-weixin-work:third.weixin.work.config.wxAeskeyBtn') }" onclick="window.random_AESKey();validation.validateElement(document.getElementsByName('value(wxAeskey)')[0]);" style="vertical-align: top;"></ui:button>
							<div class="message"><bean:message key="third.weixin.work.config.wxAeskeyMsg" bundle="third-weixin-work"/></div>
							<div class="txtstrong"><bean:message key="third.weixin.work.config.wxAeskeyMsg.tip" bundle="third-weixin-work"/></div>
						</td>
					</tr> --%>
					 
					
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxOrgIdTitle" bundle="third-weixin-work"/></td>
						<td>
							<xform:address propertyId="value(wxOrgId)" propertyName="value(wxOrgName)" 
								mulSelect="true" orgType="ORG_TYPE_ORGORDEPT" showStatus="edit" onValueChange="wxOrgId_display_change()" 
								style="width:75%" subject="${lfn:message('third-weixin-work:third.weixin.work.config.wxOrgIdSubject') }" validators="wxOmsRequire">
							</xform:address>
							<ui:button text="${lfn:message('third-weixin-work:third.weixin.work.config.wxOrgIdBtn') }" onclick="cleanTime();" style="vertical-align: top;"></ui:button>
							<div class="message"><bean:message key="third.weixin.work.config.wxOrgIdMsg" bundle="third-weixin-work"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxOmsRootFlagTitle" bundle="third-weixin-work"/></td>
						<td>
							<ui:switch property="value(wxOmsRootFlag)" onValueChange="" 
								enabledText="${lfn:message('third-weixin-work:third.weixin.work.config.wxOmsRootFlag.sync') }" disabledText="${lfn:message('third-weixin-work:third.weixin.work.config.wxOmsRootFlag.asyn') }"></ui:switch>
							<div class="message"><bean:message key="third.weixin.work.config.wxOmsRootFlagMsg" bundle="third-weixin-work"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxOmsOrgPersonHandleTitle" bundle="third-weixin-work"/></td>
						<td>
							<xform:radio property="value(wxOmsOrgPersonHandle)">
								<xform:enumsDataSource enumsType="wx_oms_sync_handle" />
							</xform:radio>
							<div class="message"><bean:message key="third.weixin.work.config.wxOmsOrgPersonHandleMsg" bundle="third-weixin-work"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.oms.disabale.way" bundle="third-weixin-work"/></td>
						<td>
							<xform:radio property="value(wxOmsPersonDisableHandle)">
								<xform:enumsDataSource enumsType="wx_oms_sync_disable_handle" />
							</xform:radio>
							<div class="message"><bean:message key="third.weixin.work.config.oms.disabale.way.tip" bundle="third-weixin-work"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxRootIdTitle" bundle="third-weixin-work"/></td>
						<td>
							<xform:text property="value(wxRootId)" showStatus="edit" style="width:65%"/>
							<div class="message"><bean:message key="third.weixin.work.config.wxRootIdMsg" bundle="third-weixin-work"/></div>
						</td>
					</tr>
					
					<!-- 通讯录字段配置 -->
					<!-- 人员信息 -->
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.title" bundle="third-weixin-work"/></td>
						<td>
							<div>
							<span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.tip" bundle="third-weixin-work"/></span>	
							   
							   <table style="text-align: center" class="tb_normal" width=100% >
							       <tr>
							           <td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.weixin" bundle="third-weixin-work"/></td>
							           <td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.way" bundle="third-weixin-work"/></td>
							           <td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.ekp" bundle="third-weixin-work"/></td>
							       </tr>
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.name" bundle="third-weixin-work"/> <span class="txtstrong">*</span></td>
							           <td width="25%">
							             <xform:select property="value(org2wxWork.name.synWay)"  className="selectsgl" subject="姓名" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							               <xform:select className="selectsgl" showStatus="view" value="fdName" property="value(org2wxWork.name)" subject="${lfn:message('third-weixin-work:thirdWeixinConfig.syn.person.name')}" showPleaseSelect="false" htmlElementProperties="disabled=disabled" style="width:45%;">
											   <xform:simpleDataSource value="fdName"><bean:message key="thirdWeixinConfig.syn.person.name" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							       </tr>
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.mobile" bundle="third-weixin-work"/> <span class="txtstrong">*</span></td>
							           <td width="25%">
							             <xform:select property="value(org2wxWork.mobile.synWay)" className="selectsgl" subject="${lfn:message('third-weixin-work:thirdWeixinConfig.syn.person.mobile')}" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%"  align="left">
							               <xform:select property="value(org2wxWork.mobile)" showStatus="view" value="fdMobileNo" className="selectsgl" subject="${lfn:message('third-weixin-work:thirdWeixinConfig.syn.person.mobile')}" showPleaseSelect="false" style="width:45%">
											   <xform:simpleDataSource value="fdMobileNo"><bean:message key="thirdWeixinConfig.syn.person.mobile" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
										   <span style="color:red">${lfn:message('third-weixin-work:third.weixin.work.config.firm.only')}</span>
							           </td>
							       </tr>
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.userId" bundle="third-weixin-work"/> <span class="txtstrong">*</span></td>
							           <td width="25%">
							             <xform:select property="value(org2wxWork.userid.synWay)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							               <xform:select property="value(org2wxWork.userid)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
											   <xform:simpleDataSource value="fdLoginName"><bean:message key="thirdWeixinConfig.syn.person.loginName" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="fdId">fdId</xform:simpleDataSource>
										   </xform:select>
										   <span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.userId.tip" bundle="third-weixin-work"/></span>
							           </td>
							       </tr>
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.dept" bundle="third-weixin-work"/> <span class="txtstrong">*</span></td>
							           <td width="25%">
							             <xform:select property="value(org2wxWork.department.synWay)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							               <xform:select property="value(org2wxWork.department)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
											   <xform:simpleDataSource value="fdDept"><bean:message key="thirdWeixinConfig.syn.person.dept.sigle" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="fdMuilDept"><bean:message key="thirdWeixinConfig.syn.person.dept.mutil" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							       </tr>
							       
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.order" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(org2wxWork.orderInDepts.synWay)"  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject=""  showStatus="edit" showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							               <xform:select property="value(org2wxWork.orderInDepts)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
											   <xform:simpleDataSource value="asc"><bean:message key="thirdWeixinConfig.syn.person.order.asc" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="desc"><bean:message key="thirdWeixinConfig.syn.person.order.desc" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
										   <span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.order.tip" bundle="third-weixin-work"/></span>
										</div>   
							           </td>
							       </tr>
							       <!-- 成员别名 -->
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.alias" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(org2wxWork.alias.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							               <xform:select property="value(org2wxWork.alias)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
											   <xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.WxworkPersonCustomData" />
										   </xform:select>
										    <span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.alias.tip" bundle="third-weixin-work"/></span>
										   </div>
							           </td>
							       </tr>
							       
							       <!-- 性别 -->
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.sex" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(org2wxWork.sex.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							               <xform:select property="value(org2wxWork.sex)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
											   <xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.WxworkPersonCustomData" />
										   </xform:select>
										    <!-- <span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.alias.tip" bundle="third-weixin-work"/></span> -->
										   </div>
							           </td>
							       </tr>
							       
							       <!-- 座机 -->
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.tel" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(org2wxWork.tel.synWay)" className="selectsgl" subject=""  showStatus="edit"  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							               <xform:select property="value(org2wxWork.tel)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
											   <xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.WxworkPersonCustomData" />
										   </xform:select>
										   <!-- <span style="color:red">企业内必须唯一</span>	 -->
										   </div>
							           </td>
							       </tr>

								   <!--邮箱 -->
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.email" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(org2wxWork.email.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							               <xform:select property="value(org2wxWork.email)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
											   <xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.WxworkPersonCustomData" />
										   </xform:select>
										   </div>
							           </td>
							       </tr>

								   <!--企业邮箱 -->
								   <tr>
									   <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.bizEmail" bundle="third-weixin-work"/></td>
									   <td width="25%">
										   <xform:select property="value(org2wxWork.bizEmail.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
									   </td>
									   <td width="55%" align="left">
										   <div class="sys_property" style="display: block">
											   <xform:select property="value(org2wxWork.bizEmail)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
												   <xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.WxworkPersonCustomData" />
											   </xform:select>
											   <br>
											   <span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.bizEmail.tip" bundle="third-weixin-work"/></span>
										   </div>
									   </td>
								   </tr>
							       
							       <!-- 地址 -->
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.workplace" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(org2wxWork.workPlace.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"  className="selectsgl" subject=""  showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							               <xform:select property="value(org2wxWork.workPlace)" subject="" className="selectsgl" showStatus="edit" htmlElementProperties="iscustom='true'"   showPleaseSelect="false" style="width:45%">
										   		<xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.WxworkPersonCustomData" />
										   </xform:select>
										   </div>
							           </td>
							       </tr>
							       
							       <!-- 英文名 -->
							      <%--  <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.us_name" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(org2wxWork.usName.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"  className="selectsgl" subject=""  showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							               <xform:select property="value(org2wxWork.usName)" subject="" className="selectsgl" showStatus="edit" htmlElementProperties="iscustom='true'"   showPleaseSelect="false" style="width:45%">
										   		<xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.WxworkPersonCustomData" />
										   </xform:select>
										   </div>
							           </td>
							       </tr> --%>
							       
							       <!-- 职务 -->
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.position" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(org2wxWork.position.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							               <xform:select property="value(org2wxWork.position)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
											   <xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.WxworkPersonCustomData" />
										   </xform:select>
										   </div>
							           </td>
							       </tr>
							       
							       <!-- 身份 -->
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.is_leader_in_dept" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(org2wxWork.isLeaderInDept.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							                                                                ${lfn:message('third-weixin-work:third.weixin.work.config.branch.ifparent')}
										 </div>
							           </td>
							       </tr>
							       
							        <!-- 拓展字段 -->
							       <tr >
							          <td colspan="3"> 
								          <div width=100%>
								             <table id="org2wxWork_custom" style="text-align: center; margin: 0px 0px;" class="tb_normal" width=100% >
										       <tr KMSS_IsReferRow="1" >                                           
	                                                <td align="center" class="td_normal_title" width="15%" style="width: 14.6%">
	                                                    <xform:text property="value(org2wxWork.custom.[!{index}].title)" style="width:85%;" showStatus="edit" required="true" subject="${lfn:message('third-weixin-work:third.weixin.work.config.field.customize')}"/>
	                                                </td>
	                                                <td width="25%">
											             <xform:select property="value(org2wxWork.custom.[!{index}].synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
															   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
															   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
														   </xform:select>
											         </td>
											          
	                                                <td width="55%" align="left">
											            <div class="sys_property" style="display: block">
											               <xform:select property="value(org2wxWork.custom.[!{index}].target)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
															   <xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.WxworkPersonCustomData" />
														   </xform:select>
														   
														    <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
	                                                           <span style="margin-top: 2px;padding-left: 30px"><img src="${KMSS_Parameter_StylePath}icons/icon_del.png" style="margin-top: 5px;" border="0" /> </span>${lfn:message('third-weixin-work:wxOms.sync.disable.handle0')}
	                                                        </a>
														 </div>
											         </td>
	                                           </tr>
	                                           <c:forEach items="${fd_constom_List}" var="fdDetail_FormItem" varStatus="vstatus">
	                                             <tr KMSS_IsContentRow="1">                                           
	                                                <td  align="center" class="td_normal_title" width="100px" style="width: 14.6%">
	                                                    <xform:text property="value(org2wxWork.custom.[${vstatus.index}].title)" value="${fdDetail_FormItem.title}" style="width:85%;" showStatus="edit" required="true" subject="${lfn:message('third-weixin-work:third.weixin.work.config.field.customize')}"/>
	                                                </td>
	                                                <td width="25%">
											             <xform:select property="value(org2wxWork.custom.[${vstatus.index}].synWay)" value="${fdDetail_FormItem.synWay}" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
															   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
															   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
														   </xform:select>
											         </td>
											          
	                                                <td width="55%" align="left">
											            <div class="sys_property" style="display: block">
											               <xform:select property="value(org2wxWork.custom.[${vstatus.index}].target)"  value="${fdDetail_FormItem.target}" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
															   <xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.WxworkPersonCustomData" />
														   </xform:select>
														   
														    <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
	                                                           <span style="margin-top: 2px;padding-left: 30px"><img src="${KMSS_Parameter_StylePath}icons/icon_del.png" style="margin-top: 5px;" border="0" /> </span> ${lfn:message('third-weixin-work:wxOms.sync.disable.handle0')}
	                                                        </a>
														 </div>
											         </td>
	                                              
	                                               <%--  <td class="docList" align="center">						
	                                                    <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
	                                                       <span style="padding-top:5px;> 
	                                                           <img src="${KMSS_Parameter_StylePath}icons/icon_del.png"  border="0" /> 
	                                                       </span>
	                                                       
	                                                    </a>
	                                                </td> --%>
	                                           </tr>
	                                           
	                                           </c:forEach>
	                                           <tr>
										          <td colspan="3" >
										             <div style="text-align: left ">
										                <span style="color:red;text-align: left;padding-right: 300px ">${lfn:message('third-weixin-work:third.weixin.work.config.field')}</span>
										                <ui:button text="+${lfn:message('third-weixin-work:third.weixin.work.config.field.ream')}" onclick="DocList_AddRow();" style="vertical-align: top;"></ui:button>
										             </div>
										          </td> 
										       </tr>
								             </table>
								             <input type="hidden" name="fdDetail_Flag" value="1">
		                                        <script>
		                                            Com_IncludeFile("doclist.js");
		                                        </script>
		                                        <script>
		                                            DocList_Info.push('org2wxWork_custom');
		                                        </script>
								         </div>
							          </td>
							       </tr>
							   </table>
							</div>
						</td>
					</tr>
					<!-- 部门同步字段 -->
						<tr>
							<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.dept.source" bundle="third-weixin-work"/></td>
							<td>
								<div>
								<span style="color:red"><bean:message key="thirdWeixinConfig.syn.dept.source.tip" bundle="third-weixin-work"/></span>	
								   
								   <table style="text-align: center" class="tb_normal" width=100% >
								       <tr>
								           <td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.weixin" bundle="third-weixin-work"/></td>
							               <td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.way" bundle="third-weixin-work"/></td>
							               <td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.ekp" bundle="third-weixin-work"/></td>
								       </tr>
								       <tr>
								           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.dept.name" bundle="third-weixin-work"/> <span class="txtstrong">*</span></td>
								           <td width="25%">
								             <xform:select property="value(org2wxWork.dept.name.synWay)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
												   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
												   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   </xform:select>
								           </td>
								           <td width="55%" align="left">
								               <xform:select className="selectsgl" property="value(org2wxWork.dept.name)" subject=""  value="fdName" showStatus="view"   showPleaseSelect="false" htmlElementProperties="disabled=disabled" style="width:45%;">
												   <xform:simpleDataSource value="fdName"><bean:message key="thirdWeixinConfig.syn.dept.name" bundle="third-weixin-work"/></xform:simpleDataSource>
											   </xform:select>
								           </td>
								       </tr>
								       <tr>
								           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.dept.parent" bundle="third-weixin-work"/><span class="txtstrong">*</span></td>
								           <td width="25%">
								             <xform:select property="value(org2wxWork.dept.parentDept.synWay)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
												   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.dept.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
												   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.dept.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   </xform:select>
								           </td>
								           <td width="55%" align="left">
								               <xform:select className="selectsgl" property="value(org2wxWork.dept.parentDept)" subject="" value="fdParentDept" showStatus="view"  showPleaseSelect="false" htmlElementProperties="disabled=disabled" style="width:45%;">
												   <xform:simpleDataSource value="fdParentDept"><bean:message key="thirdWeixinConfig.syn.dept.parent" bundle="third-weixin-work"/></xform:simpleDataSource>
											   </xform:select>
								           </td>
								       </tr>
								       
								       <tr>
								           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.dept.order" bundle="third-weixin-work"/></td>
								           <td width="25%">
								             <xform:select property="value(org2wxWork.dept.order.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
                                                         <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
												   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
												   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											 </xform:select>
								           </td>
								           <td width="55%" align="left">
								            <div class="sys_property" style="display: block">
								               <xform:select className="selectsgl" property="value(org2wxWork.dept.order)" subject="" showPleaseSelect="false"  style="width:45%;">
												   <xform:simpleDataSource value="asc"><bean:message key="thirdWeixinConfig.syn.person.order.asc" bundle="third-weixin-work"/></xform:simpleDataSource>
											       <xform:simpleDataSource value="desc"><bean:message key="thirdWeixinConfig.syn.person.order.desc" bundle="third-weixin-work"/></xform:simpleDataSource>
											   </xform:select>
											    <span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.order.tip" bundle="third-weixin-work"/></span>
											 
											   </div>
								           </td>
								       </tr>
								       <tr>
								           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.dept.USName" bundle="third-weixin-work"/></td>
								           <td width="25%">
								             <xform:select property="value(org2wxWork.dept.USName.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
                                                         <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
												   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
												   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											 </xform:select>
								           </td>
								           <td width="55%" align="left">
								            <div class="sys_property" style="display: block">
								               <xform:select className="selectsgl" property="value(org2wxWork.dept.USName)" subject="" showPleaseSelect="false"  style="width:45%;">
												  <xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.WxworkDeptCustomData" />
											   </xform:select>
											   </div>
								           </td>
								       </tr>
								   </table>
								</div>
							</td>
						</tr>
						<!-- 不同步人员 -->
						<tr>
							<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.notSyn.person.title" bundle="third-weixin-work"/></td>
							<td>
							    <div>
							        <xform:address propertyId="value(ekp2wx.noSyn.person.ids)" propertyName="value(ekp2wx.noSyn.person.names)" 
										mulSelect="true" orgType="ORG_TYPE_PERSON" showStatus="edit"  textarea="true" 
										style="width:95%" subject="${lfn:message('third-weixin-work:thirdWeixinConfig.notSyn.person.title') }"  validators="notSynPersonMaxCheck">
									</xform:address>
							    </div>
								<div style="padding-top: 10px;padding-bottom: 5px;">
								  <ui:button text="${lfn:message('third-weixin-work:thirdWeixinConfig.notSyn.person.clean')}" onclick="cleanWxworkPerson()" width="170" height="35"></ui:button>
								  <span style="padding-left: 700px;"><bean:message key="thirdWeixinConfig.notSyn.person.max" bundle="third-weixin-work"/></span>
								</div>
								<p style="color:red"><bean:message key="thirdWeixinConfig.notSyn.person.tip" bundle="third-weixin-work"/></p>	
							</td>
						</tr>
						
					<%-- <tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxLoginNameTitle" bundle="third-weixin-work"/></td>
						<td>
							<xform:text property="value(wxLoginName)" showStatus="noShow"/>
							<div id="ln"><bean:message key="third.weixin.work.config.wxLoginNameLn" bundle="third-weixin-work"/></div>
							<div class="message"><bean:message key="third.weixin.work.config.wxLoginNameMsg" bundle="third-weixin-work"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.wxPersonOrderTitle" bundle="third-weixin-work"/></td>
						<td>
							<div id="ln">
								<bean:message key="third.weixin.work.config.wxPersonOrder" bundle="third-weixin-work"/>:
								<xform:radio property="value(wxPersonOrder)" showStatus="edit">
									<xform:simpleDataSource value="0"><bean:message key="third.weixin.work.config.wxPersonOrder.asc" bundle="third-weixin-work"/></xform:simpleDataSource>
									<xform:simpleDataSource value="1"><bean:message key="third.weixin.work.config.wxPersonOrder.desc" bundle="third-weixin-work"/></xform:simpleDataSource>
								</xform:radio>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<bean:message key="third.weixin.work.config.wxDeptOrder" bundle="third-weixin-work"/>:
								<xform:radio property="value(wxDeptOrder)" showStatus="edit">
									<xform:simpleDataSource value="0"><bean:message key="third.weixin.work.config.wxPersonOrder.asc" bundle="third-weixin-work"/></xform:simpleDataSource>
									<xform:simpleDataSource value="1"><bean:message key="third.weixin.work.config.wxPersonOrder.desc" bundle="third-weixin-work"/></xform:simpleDataSource>
								</xform:radio>
							</div>
							<div class="message"><bean:message key="third.weixin.work.config.wxPersonOrderMsg" bundle="third-weixin-work"/></div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="third.weixin.work.config.extendfiled" bundle="third-weixin-work"/></td>
						<td>
							<table style="width: 100%">
								<tr>
									<td width="150px"><ui:switch property="value(wxOfficePhone)" enabledText="${lfn:message('third-weixin-work:third.weixin.work.config.officephone') }" disabledText="${lfn:message('third-weixin-work:third.weixin.work.config.officephone') }"></ui:switch></td>
									<td><ui:switch property="value(wxPostEnabled)" enabledText="${lfn:message('third-weixin-work:third.weixin.config.dingPostEnabled') }" disabledText="${lfn:message('third-weixin-work:third.weixin.config.dingPostEnabled') }"></ui:switch></td>
									
								</tr>
							</table>
						</td>
					</tr> --%>
				</table>
				
				<!-- 组织架构从企业微信同步到ekp -->
				<table id="omsFromWxTable" class="tb_normal" width=100%>
				     <tr>
						<td class="td_normal_title" width="15%">
								${lfn:message('third-weixin-work:third.weixin.work.config.org2ekp.root.id') }
						</td>
						<td>
							<xform:text property="value(wx2ekp.wxRootId)" showStatus="edit" style="width:65%"/>
							<div class="message">${lfn:message('third-weixin-work:third.weixin.work.config.org2ekp.root.id.tip') }</div>
						</td>
					</tr>
					 <tr>
						<td class="td_normal_title" width="15%">${lfn:message('third-weixin-work:third.weixin.work.config.org2ekp.ekpOrg.root.id') }</td>
						<td>
							<xform:address propertyId="value(wx2ekp.ekpOrgId)" propertyName="value(wx2ekp.ekpOrgName)" 
								mulSelect="false" orgType="ORG_TYPE_ORGORDEPT" showStatus="edit" onValueChange="wxOrgId_display_change()" 
								style="width:75%" subject="${lfn:message('third-weixin-work:third.weixin.work.config.wxOrgIdSubject') }">
							</xform:address>
							<div class="message">${lfn:message('third-weixin-work:third.weixin.work.config.org2ekp.ekpOrg.root.id') }</div>
						</td>
					</tr>
					
					<tr>
						<td class="td_normal_title" width="15%">${lfn:message('third-weixin-work:third.weixin.work.config.org2ekp.redundant.relation') }</td>
						<td>
							 <xform:radio  property="value(wx2ekp.ekpOrgHandle)">
								<xform:simpleDataSource value="noHandle">
									${lfn:message('third-weixin-work:third.weixin.work.config.org2ekp.redundant.relation.handle.no') }
								</xform:simpleDataSource>
								<xform:simpleDataSource value="autoDisable">
									${lfn:message('third-weixin-work:third.weixin.work.config.org2ekp.redundant.relation.handle.autoDelete') }
								</xform:simpleDataSource>
						     </xform:radio>
							<div class="message">${lfn:message('third-weixin-work:third.weixin.work.config.org2ekp.redundant.relation.tip') }</div>
						</td>
					</tr>
					<!-- 企业微信到ekp,通讯录字段配置 -->
					<!-- 人员信息 -->
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.title" bundle="third-weixin-work"/></td>
						<td>
							<div>
							<span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.tip" bundle="third-weixin-work"/></span>	
							   
							   <table style="text-align: center" class="tb_normal" width=100% >
							       <tr>
							           <td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.ekp" bundle="third-weixin-work"/></td>
							           <td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.way" bundle="third-weixin-work"/></td>
							           <td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.weixin" bundle="third-weixin-work"/></td>
							       </tr>
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.name" bundle="third-weixin-work"/> <span class="txtstrong">*</span></td>
							           <td width="25%">
							               <xform:select property="value(wx2ekp.name.synWay)"  className="selectsgl" subject="姓名" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							               <xform:select className="selectsgl" showStatus="view" value="fdName" property="value(wx2ekp.name)" subject="${lfn:message('third-weixin-work:thirdWeixinConfig.syn.person.name')}" showPleaseSelect="false" htmlElementProperties="disabled=disabled" style="width:45%;">
											   <xform:simpleDataSource value="fdName"><bean:message key="thirdWeixinConfig.syn.person.name" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							       </tr>
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.loginName" bundle="third-weixin-work"/><span class="txtstrong">*</span></td>
							           <td width="25%">
							             <xform:select property="value(wx2ekp.loginName.synWay)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
											    <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							               <xform:select property="value(wx2ekp.loginName)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
											   <xform:simpleDataSource value="mobile"><bean:message key="thirdWeixinConfig.syn.person.mobile" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="userid">userid</xform:simpleDataSource>
										   </xform:select>
										   <%-- <span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.userId.tip" bundle="third-weixin-work"/></span> --%>
							           </td>
							       </tr>
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.mobile" bundle="third-weixin-work"/> <span class="txtstrong">*</span></td>
							           <td width="25%">
							             <xform:select property="value(wx2ekp.mobile.synWay)" className="selectsgl" subject="手机号" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										  </xform:select>
							           </td>
							           <td width="55%"  align="left">
							               <xform:select property="value(wx2ekp.mobile)" showStatus="view" value="fdMobileNo" className="selectsgl" subject="手机号" showPleaseSelect="false" style="width:45%">
											   <xform:simpleDataSource value="fdMobileNo"><bean:message key="thirdWeixinConfig.syn.person.mobile" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							       </tr>
							       <!-- 部门 -->
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.dept" bundle="third-weixin-work"/> <span class="txtstrong">*</span></td>
							           <td width="25%">
							             <xform:select property="value(wx2ekp.department.synWay)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							               <xform:select property="value(wx2ekp.department)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
											   <xform:simpleDataSource value="mainDept"><bean:message key="thirdWeixinConfig.syn.person.dept.select.main" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="muilDept"><bean:message key="thirdWeixinConfig.syn.person.dept.select.mutil" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
										   <br/>
										   <span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.dept.tip" bundle="third-weixin-work"/></span>
							           </td>
							       </tr>
							       
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.order" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(wx2ekp.orderInMainDept.synWay)"  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject=""  showStatus="edit" showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							            
							                <xform:select className="selectsgl" property="value(wx2ekp.orderInMainDept)" subject="" showPleaseSelect="false"  style="width:45%;">
												   <xform:simpleDataSource value="asc"><bean:message key="thirdWeixinConfig.syn.person.order.asc" bundle="third-weixin-work"/></xform:simpleDataSource>
											       <xform:simpleDataSource value="desc"><bean:message key="thirdWeixinConfig.syn.person.order.desc" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
										    <span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.order.tip" bundle="third-weixin-work"/></span>
										</div>   
							           </td>
							       </tr>
							       <!-- 成员别名 -->
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.alias" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(wx2ekp.alias.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							               <xform:select property="value(wx2ekp.alias)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
											   <xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.Wx2ekpPersonCustomData" />
										   </xform:select>
										    <span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.alias.tip" bundle="third-weixin-work"/></span>
										   </div>
							           </td>
							       </tr>
							       
							       <!-- 性别 -->
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.sex" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(wx2ekp.sex.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							               <xform:select property="value(wx2ekp.sex)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
											   <xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.Wx2ekpPersonCustomData" />
										   </xform:select>
										    <!-- <span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.alias.tip" bundle="third-weixin-work"/></span> -->
										   </div>
							           </td>
							       </tr>
							       
							       <!-- 座机 -->
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.tel" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(wx2ekp.tel.synWay)" className="selectsgl" subject=""  showStatus="edit"  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							               <xform:select property="value(wx2ekp.tel)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
											   <xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.Wx2ekpPersonCustomData" />
										   </xform:select>
										   <!-- <span style="color:red">企业内必须唯一</span>	 -->
										   </div>
							           </td>
							       </tr>
							       
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.email" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(wx2ekp.email.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							               <xform:select property="value(wx2ekp.email)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
											   <xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.Wx2ekpPersonCustomData" />
										   </xform:select>
										   </div>
							           </td>
							       </tr>
							       
							       <!-- 地址 -->
							       <%-- <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.workplace" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(wx2ekp.workPlace.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"  className="selectsgl" subject=""  showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							               <xform:select property="value(wx2ekp.workPlace)" subject="" className="selectsgl" showStatus="edit" htmlElementProperties="iscustom='true'"   showPleaseSelect="false" style="width:45%">
										   		<xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.Wx2ekpPersonCustomData" />
										   </xform:select>
										   </div>
							           </td>
							       </tr> --%>
	       
							       <!-- 默认语言 -->
							      <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.defaultLang" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(wx2ekp.defaultLang.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							              <div class="sys_property" style="display: block">
							                <xform:text property="value(wx2ekp.defaultLang)" subject="${lfn:message('third-weixin-work:thirdWeixinConfig.syn.person.defaultLang')}"
											required="false" style="width:85%" htmlElementProperties="placeholder ='${lfn:message('third-weixin-work:thirdWeixinConfig.syn.weixin.expand.field')}'"/>
										   </div>
							           </td>
							           
							           </td>
							       </tr>
							       
							       <!-- 关键字 -->
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.keyword" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(wx2ekp.keyword.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							                <xform:text property="value(wx2ekp.keyword)" subject="${lfn:message('third-weixin-work:thirdWeixinConfig.syn.person.keyword')}"
											required="false" style="width:85%" htmlElementProperties="placeholder ='${lfn:message('third-weixin-work:thirdWeixinConfig.syn.weixin.expand.field')}'"/>
										 </div>
							           </td>
							       </tr>
							      
							       <!-- 短号 -->
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.short.num" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(wx2ekp.short.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							                <xform:text property="value(wx2ekp.short)" subject="${lfn:message('third-weixin-work:thirdWeixinConfig.syn.person.short.num')}"
											required="false" style="width:85%" htmlElementProperties="placeholder ='${lfn:message('third-weixin-work:thirdWeixinConfig.syn.weixin.expand.field')}'"/>
										   </div>
							           </td>
							       </tr>
							       
							        <!-- 是否业务相关 -->
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.isBussiness" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(wx2ekp.isBussiness.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							           <div class="sys_property" style="display: block">
							                <xform:text property="value(wx2ekp.isBussiness)" subject="${lfn:message('third-weixin-work:thirdWeixinConfig.syn.person.isBussiness')}"
											required="false" style="width:85%" htmlElementProperties="placeholder ='${lfn:message('third-weixin-work:thirdWeixinConfig.syn.weixin.expand.field')}'"/>
										   </div>
							           </td>
							       </tr> 
							        <!-- 备注 -->
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.remark" bundle="third-weixin-work"/></td>
							           <td width="25%">
							             <xform:select property="value(wx2ekp.remark.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
											   <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
										   </xform:select>
							           </td>
							           <td width="55%" align="left">
							            <div class="sys_property" style="display: block">
							                <xform:text property="value(wx2ekp.remark)" subject="${lfn:message('third-weixin-work:thirdWeixinConfig.syn.person.remark')}"
											    required="false" style="width:85%" htmlElementProperties="placeholder ='${lfn:message('third-weixin-work:thirdWeixinConfig.syn.weixin.expand.field')}'"  />
										   </div>
							           </td>
							       </tr>
							       <tr>
							           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.password" bundle="third-weixin-work"/></td>
							           <td colspan="2" align="left">
							             <span><bean:message key="wx2ekp.org.config.person.password.tip1" bundle="third-weixin-work"/>
								             <a href="${LUI_ContextPath }/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.organization.model.SysOrgDefaultConfig&s_path=组织权限管理%E3%80%80>%E3%80%80基础设置%E3%80%80>%E3%80%80参数配置%E3%80%80>%E3%80%80默认值设置&s_css=default"
								                target="_blank"
								                style="color: blue"
								             ><bean:message key="wx2ekp.org.config.person.password.tip2" bundle="third-weixin-work"/></a></span>
							           </td>
							           
							       </tr>
							      						       
							   </table>
							</div>
						</td>
					</tr>
					<!-- 部门同步字段 -->
						<tr>
							<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.dept.source" bundle="third-weixin-work"/></td>
							<td>
								<div>
								<span style="color:red"><bean:message key="thirdWeixinConfig.syn.dept.source.tip" bundle="third-weixin-work"/></span>	
								   
								   <table style="text-align: center" class="tb_normal" width=100% >
								       <tr>
								           <td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.ekp" bundle="third-weixin-work"/></td>
							               <td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.way" bundle="third-weixin-work"/></td>
							               <td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.weixin" bundle="third-weixin-work"/></td>
								       </tr>
								       <tr>
								           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.dept.name" bundle="third-weixin-work"/> <span class="txtstrong">*</span></td>
								           <td width="25%">
								             <xform:select property="value(wx2ekp.dept.name.synWay)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
												   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
												   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   </xform:select>
								           </td>
								           <td width="55%" align="left">
								               <xform:select className="selectsgl" property="value(wx2ekp.dept.name)" subject=""  value="fdName" showStatus="view"   showPleaseSelect="false" htmlElementProperties="disabled=disabled" style="width:45%;">
												   <xform:simpleDataSource value="fdName"><bean:message key="thirdWeixinConfig.syn.dept.name" bundle="third-weixin-work"/></xform:simpleDataSource>
											   </xform:select>
								           </td>
								       </tr>
								       <tr>
								           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.dept.parent" bundle="third-weixin-work"/><span class="txtstrong">*</span></td>
								           <td width="25%">
								             <xform:select property="value(wx2ekp.dept.parentDept.synWay)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
												   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.dept.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
												   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.dept.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											   </xform:select>
								           </td>
								           <td width="55%" align="left">
								               <xform:select className="selectsgl" property="value(wx2ekp.dept.parentDept)" subject="" value="fdParentDept" showStatus="view"  showPleaseSelect="false" htmlElementProperties="disabled=disabled" style="width:45%;">
												   <xform:simpleDataSource value="fdParentDept"><bean:message key="thirdWeixinConfig.syn.dept.parent" bundle="third-weixin-work"/></xform:simpleDataSource>
											   </xform:select>
								           </td>
								       </tr>
								       
								       <tr>
								           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.dept.order" bundle="third-weixin-work"/></td>
								           <td width="25%">
								             <xform:select property="value(wx2ekp.dept.order.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
                                                     <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
												   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
												   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											 </xform:select>
								           </td>
								           <td width="55%" align="left">
								            <div class="sys_property" style="display: block">
								               <xform:select className="selectsgl" property="value(wx2ekp.dept.order)" subject="" showPleaseSelect="false"  style="width:45%;">
												   <xform:simpleDataSource value="asc"><bean:message key="thirdWeixinConfig.syn.person.order.asc" bundle="third-weixin-work"/></xform:simpleDataSource>
											       <xform:simpleDataSource value="desc"><bean:message key="thirdWeixinConfig.syn.person.order.desc" bundle="third-weixin-work"/></xform:simpleDataSource>
											   </xform:select>
											    <span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.order.tip" bundle="third-weixin-work"/></span>
											 
											   </div>
								           </td>
								       </tr>
								       <tr>
								           <td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.dept.leader" bundle="third-weixin-work"/></td>
								           <td width="25%">
								             <xform:select property="value(wx2ekp.dept.leader.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
                                                    <xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
												   <xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
												   <xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											 </xform:select>
								           </td>
								           <td width="55%" align="left">
								            <div class="sys_property" style="display: block">
												<bean:message key="thirdWeixinConfig.syn.dept.superior.identity" bundle="third-weixin-work"/>
											 </div>
								           </td>
								       </tr>
								   </table>
								</div>
							</td>
						</tr>
				</table>
				</ui:content>
				<ui:content id="tag5" title="${ lfn:message('third-weixin-work:thirdWeixin.tab.business.setting') }">
				<table id="wxBusinessTable" class="tb_normal" width=100%>
					<tr id="wxBusinessTR">
						<td class="td_normal_title" width="15%">${ lfn:message('third-weixin-work:thirdWeixin.calendar') }</td>
						<td>
							<ui:switch property="value(wxCalendarEnabled)"
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						
							<span class="txtstrong">
									${ lfn:message('third-weixin-work:thirdWeixin.calendar.tip') }
							</span>
						</td>
					</tr>
				</table>

				<br>
					<table id="wxPayTable" class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" width="15%">${ lfn:message('third-weixin:thirdWeixin.payment') }</td>
							<td>
								<ui:switch property="value(wxPayEnable)" onValueChange="wxPay_display_change()"
										   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							</td>
						</tr>
						<tr name="wxPayTR">
							<td class="td_normal_title" width="15%">${ lfn:message('third-weixin-work:third.weixin.work.config.secrect') }</td>
							<td>
								<xform:text property="value(wxPayKey)" validators="wxPayCheckRequire" showStatus="edit" style="width:65%"/><span class="txtstrong">*</span>
								<div class="message">${ lfn:message('third-weixin-work:third.weixin.work.config.secrect.tip') }</div>

							</td>
						</tr>
						<%--
						<tr name="wxPayTR">
							<td class="td_normal_title" width="15%">默认商户ID</td>
							<td>
								<xform:text property="value(wxPayMchID)" showStatus="edit" style="width:65%"/>
								<div class="message"></div>
							</td>
						</tr>
						<tr name="wxPayTR">
							<td class="td_normal_title" width="15%">证书路径</td>
							<td>
								<xform:text property="value(wxPayCertFilePath)" showStatus="edit" style="width:65%"/>
								<div class="message"></div>
							</td>
						</tr>
						--%>
					</table>

					<br/>
					<table id="wxLivingTable" class="tb_normal" width=100%>
						<tr id="wxLivingTR">
							<td class="td_normal_title" width="15%">${ lfn:message('third-weixin-work:third.weixin.work.config.living') }</td>
							<td>
								<ui:switch property="value(wxLivingEnabled)"
										   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>

								<span class="txtstrong">
									${ lfn:message('third-weixin-work:third.weixin.work.config.living.tip') }
							</span>
							</td>
						</tr>
					</table>

					<br/>
					<table id="wxChatdataTable" class="tb_normal" width=100%>
						<tr id="wxChatdataTR">
							<td class="td_normal_title" width="15%">企业微信会话内容存档</td>
							<td>
								<ui:switch property="value(chatdataSyncEnable)"  onValueChange="wxChatdata_display_change()"
										   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>

								<span class="txtstrong">
								启用，则可以对员工使用企业微信进行内外沟通的内容存档。该功能需要配置企业微信通讯录/外部联系人配置使用。
								</span>
								<br/>
								<span class="txtstrong">
									注意，使用该功能需要先部署SDK，部署方式请参考 “<a target="_blank" href='<c:url value="/third/weixin/chatdata/help/sdk_help.jsp"/>' >SDK部署帮助</a>”
								</span>
								<br/>
								<br/>
								<div id="wxChatdataDiv">
									"会话内容存档"应用secret:<xform:text property="value(chatdataAppSecret)" validators="wxChatdataCheckRequire" showStatus="edit" style="width:65%"/><span class="txtstrong">*</span>
									<br/>
									<br/>
									<%--
									"会话内容存档"解密secret:<xform:text property="value(chatdataArchiveSecret)" validators="wxChatdataCheckRequire" showStatus="edit" style="width:65%"/><span class="txtstrong">*</span>
									<br/>
									--%>
									"会话内容存档"传输公钥:<xform:textarea property="value(chatdataSyncPublicKey)" validators="wxChatdataCheckRequire" showStatus="edit" style="width:65%"/><span class="txtstrong">*</span>
									<ui:button text="生成秘钥对" onclick="genRsaKey();" style="vertical-align: top;"></ui:button>

									<br/>
									<br/>
									"会话内容存档"传输私钥:<xform:textarea property="value(chatdataSyncPrivateKey)" validators="wxChatdataCheckRequire" showStatus="edit" style="width:65%"/><span class="txtstrong">*</span>
									<br/>
									<br/>
									<ui:switch property="value(chatdataEncryEnable)" text="加密存储"
												   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									<span class="txtstrong">
										启用，则会话内容进行加密存储，数据安全；但是，会话内容不支持搜索。
									</span>
									<br/>
									<br/>
									会话内容同步历史表周期：<xform:text property="value(chatdataBakMonth)" validators="digits min(0)" showStatus="edit" style="width:3%"/>个月前的数据同步到历史表，为空则默认为3个月
								</div>
							</td>
						</tr>
					</table>
				</ui:content>

					<c:if test="${ecoEnable eq 'true'}">
				<ui:content id="tag6" title="${ lfn:message('third-weixin-work:thirdWeixin.tab.synergia.setting') }">
						<table id="wxContactTable" class="tb_normal" width=100%>
							<tr>
								<td class="td_normal_title" width="15%">启用客户集成</td>
								<td>
									<ui:switch property="value(contactIntegrateEnable)"  onValueChange="wxContact_display_change()"
											   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</td>
							</tr>
							<tr id="wxContactTR" name="wxContactDetail">
								<td class="td_normal_title" width="15%">${ lfn:message('third-weixin-work:thirdWeixin.tab.contact.sync.toEkp') }</td>
								<td>
									<ui:switch property="value(syncContact.toEkp)"
											   enabledText="${lfn:message('third-weixin-work:thirdWeixin.tab.contact.sync.auto')}" disabledText="${lfn:message('third-weixin-work:thirdWeixin.tab.contact.sync.auto.no')}"></ui:switch>
									<br>

									<div>
										<div style="float:left">
											<span>
												手动将企业微信已有客户同步到EKP生态组织：
											</span>
										</div>
										<div style="float:left">
											<ui:button text="${lfn:message('third-weixin-work:thirdWeixin.tab.contact.sync.manual')}" onclick="syncContact2ekp()" width="100" height="25"></ui:button>
										</div>
									</div>
									<br>
									<br>
									<div class="txtstrong">
										<span>${lfn:message('third-weixin-work:third.weixin.work.config.org.inint.tip')}</span>
									</div>
								</td>
							</tr>
							<tr name="wxContactDetail">
								<td class="td_normal_title" width="15%">${ lfn:message('third-weixin-work:thirdWeixin.tab.contact.sync.secret') }</td>
								<td>
									<xform:text property="value(syncContact.secret)"
												 style="width:85%"/>
								</td>
							</tr>
							<tr name="wxContactDetail">
								<td class="td_normal_title" width="15%">${ lfn:message('third-weixin-work:thirdWeixin.tab.contact.sync.org.type.setting') }</td>
								<td>
									<xform:text property="value(syncContact.orgType.setting)"
												required="false" style="width:85%" htmlElementProperties="style='display:none'"/>

									<ui:button text="${lfn:message('third-weixin-work:thirdWeixin.tab.contact.sync.add')}" onclick="addContactTagSetting(true);" width="100" height="25"></ui:button>

									<table id="TABLE_DocList" style="text-align: center" class="tb_normal" width=100% >
										<tr>
											<th class="td_normal_title"><bean:message key="thirdWeixin.tab.contact.ekp.orgType" bundle="third-weixin-work"/></th>
											<th class="td_normal_title"><bean:message key="thirdWeixin.tab.contact.tag" bundle="third-weixin-work"/></th>
											<th class="td_normal_title"><bean:message key="thirdWeixin.tab.contact.sync.oper" bundle="third-weixin-work"/></th>
										</tr>
										<tr KMSS_IsReferRow="1" style="display: none">
											<td width="">
												<xform:select property="contactTag[!{index}]OrgType" showPleaseSelect="false">
													<xform:customizeDataSource className="com.landray.kmss.third.weixin.service.spring.ThirdWeixinContactOrgTypeDataSource"></xform:customizeDataSource>
												</xform:select>
											</td>
											<td width="">
												<div class="inputselectsgl"
													 onclick="updateContactTagSetting('contactTag[!{index}]Id','contactTag[!{index}]Name');"
													 style="width:90%;float:left">
													<input name="contactTag[!{index}]Id" value="" type="hidden">
													<div class="input"><input name="contactTag[!{index}]Name" value="" type="text" readonly="">
													</div><div class="selectitem">
												</div></div>
												<span class="txtstrong">*</span></td>
											</td>
											<td width="" align="center">
												<div onclick='DocList_DeleteRow(this);' style='cursor:pointer;display:inline-block;width: 16px;height: 16px;' class='lui_icon_s_icon_close_red' ></div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr name="wxContactDetail">
								<td class="td_normal_title" width="15%">${ lfn:message('third-weixin-work:thirdWeixin.tab.contact.sync.external.field') }</td>
								<td>
									<table style="text-align: center" class="tb_normal" width=100% >
										<tr>
											<td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.ekp" bundle="third-weixin-work"/></td>
											<td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.way" bundle="third-weixin-work"/></td>
											<td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.weixin" bundle="third-weixin-work"/></td>
										</tr>

										<tr>
											<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.name" bundle="third-weixin-work"/> <span class="txtstrong">*</span></td>
											<td width="25%">
												<xform:select property="value(contact2ekp.name.synWay)"  className="selectsgl" subject="姓名" showStatus="edit"  showPleaseSelect="false">
													<xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
													<xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
												</xform:select>
											</td>
											<td width="55%" align="left">
												<input type="hidden" name="value(contact2ekp.name)" value="name" />
												<xform:select className="selectsgl" showStatus="view" value="fdName" property="value(contact2ekp.name)" subject="${lfn:message('third-weixin-work:thirdWeixinConfig.syn.person.name')}" showPleaseSelect="false" htmlElementProperties="disabled=disabled" style="width:45%;">
													<xform:simpleDataSource value="name"><bean:message key="thirdWeixinConfig.syn.person.name" bundle="third-weixin-work"/></xform:simpleDataSource>
												</xform:select>
											</td>
										</tr>
										<tr>
											<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.userId" bundle="third-weixin-work"/> <span class="txtstrong">*</span></td>
											<td width="25%">
												<xform:select property="value(contact2ekp.userid.synWay)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
													<xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
												</xform:select>
											</td>
											<td width="55%" align="left">
												<xform:select property="value(contact2ekp.userid)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
													<xform:simpleDataSource value="userid">userid</xform:simpleDataSource>
												</xform:select>
												<span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.userId.tip" bundle="third-weixin-work"/></span>
											</td>
										</tr>
										<!-- 成员别名 -->
										<tr>
											<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.alias" bundle="third-weixin-work"/></td>
											<td width="25%">
												<xform:select property="value(contact2ekp.alias.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  showStatus="edit"  showPleaseSelect="false">
													<xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
													<xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
													<xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
												</xform:select>
											</td>
											<td width="55%" align="left">
												<div style="display: block">
													<xform:select property="value(contact2ekp.alias)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
														<xform:simpleDataSource value="external_userid">userid</xform:simpleDataSource>
														<xform:simpleDataSource value="name">${lfn:message('third-weixin-work:thirdWeixinWorkLiving.fdName')}</xform:simpleDataSource>
														<xform:simpleDataSource value="position">${lfn:message('third-weixin-work:thirdWeixinConfig.syn.person.position')}</xform:simpleDataSource>
														<xform:simpleDataSource value="corp_name">${lfn:message('third-weixin-work:third.weixin.work.config.firm.name')}</xform:simpleDataSource>
														<xform:simpleDataSource value="corp_full_name">${lfn:message('third-weixin-work:third.weixin.work.config.firm.fullName')}</xform:simpleDataSource>
														<xform:simpleDataSource value="unionid">unionid</xform:simpleDataSource>
													</xform:select>
													<span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.alias.tip" bundle="third-weixin-work"/></span>
												</div>
											</td>
										</tr>

										<!-- 性别 -->
										<tr>
											<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.sex" bundle="third-weixin-work"/></td>
											<td width="25%">
												<xform:select property="value(contact2ekp.sex.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  showStatus="edit"  showPleaseSelect="false">
													<xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
													<xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
													<xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
												</xform:select>
											</td>
											<td width="55%" align="left">
												<div style="display: block">
													<xform:select property="value(contact2ekp.sex)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
														<xform:simpleDataSource value="gender">${lfn:message('third-weixin-work:thirdWeixinConfig.syn.person.sex')}</xform:simpleDataSource>
													</xform:select>
												</div>
											</td>
										</tr>

										<%--
										<tr>
											<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.tel" bundle="third-weixin-work"/></td>
											<td width="25%">
												<xform:select property="value(contact2ekp.tel.synWay)" className="selectsgl" subject=""  showStatus="edit"  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showPleaseSelect="false">
													<xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
													<xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
													<xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
												</xform:select>
											</td>
											<td width="55%" align="left">
												<div class="sys_property" style="display: block">
													<xform:select property="value(contact2ekp.tel)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
														<xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.WxworkPersonCustomData" />
													</xform:select>
													<!-- <span style="color:red">企业内必须唯一</span>	 -->
												</div>
											</td>
										</tr>

										<tr>
											<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.email" bundle="third-weixin-work"/></td>
											<td width="25%">
												<xform:select property="value(contact2ekp.email.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
													<xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
													<xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
													<xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
												</xform:select>
											</td>
											<td width="55%" align="left">
												<div class="sys_property" style="display: block">
													<xform:select property="value(contact2ekp.email)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
														<xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.WxworkPersonCustomData" />
													</xform:select>
												</div>
											</td>
										</tr>

										<!-- 地址 -->
										<tr>
											<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.workplace" bundle="third-weixin-work"/></td>
											<td width="25%">
												<xform:select property="value(contact2ekp.workPlace.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"  className="selectsgl" subject=""  showStatus="edit"  showPleaseSelect="false">
													<xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
													<xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
													<xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
												</xform:select>
											</td>
											<td width="55%" align="left">
												<div class="sys_property" style="display: block">
													<xform:select property="value(contact2ekp.workPlace)" subject="" className="selectsgl" showStatus="edit" htmlElementProperties="iscustom='true'"   showPleaseSelect="false" style="width:45%">
														<xform:customizeDataSource className="com.landray.kmss.third.weixin.work.util.WxworkPersonCustomData" />
													</xform:select>
												</div>
											</td>
										</tr>
										--%>
										<!-- 职务
										<tr>
											<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.position" bundle="third-weixin-work"/></td>
											<td width="25%">
												<xform:select property="value(contact2ekp.position.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  showStatus="edit"  showPleaseSelect="false">
													<xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
													<xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
													<xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
												</xform:select>
											</td>
											<td width="55%" align="left">
												<div style="display: block">
													<xform:select property="value(contact2ekp.position)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
														<xform:simpleDataSource value="position">${lfn:message('third-weixin-work:thirdWeixinConfig.syn.person.position')}</xform:simpleDataSource>
													</xform:select>
												</div>
											</td>
										</tr>
										-->
									</table>
								</td>
							</tr>
						</table>
					<br/>

					<table id="wxCorpGroupTable" class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" width="15%">启用上下游集成</td>
							<td>
								<ui:switch property="value(corpGroupIntegrateEnable)"  onValueChange="wxCorpGroup_display_change()"
										   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							</td>
						</tr>
						<tr name="wxCorpGroupDetail">
							<td class="td_normal_title" width="15%">自动同步上下游企业到EKP生态组织</td>
							<td>
								<ui:switch property="value(syncCorpGroupOrgEnable)"
										   enabledText="同步" disabledText="不同步"></ui:switch>
								<br>
								<div>
								<div style="float:left">
								<span>
										手动将企业微信已有上下游企业同步到EKP生态组织：
								</span>
								</div>
								<div style="float:left">
								<ui:button text="开始同步" onclick="syncCorpGtoup2ekp()" width="100" height="25"></ui:button>
								</div>
								</div>
								<br>
								<br>
								<div class="txtstrong">
								<span>注意：更改配置，生态组织需进行一次全量同步</span>
								</div>
							</td>
						</tr>
						<tr name="wxCorpGroupDetail">
							<td class="td_normal_title" width="15%">同步应用ID</td>
							<td>
								<xform:text property="value(corpGroupAgentIds)"
											style="width:85%"/>
								<span>
									<br/>
									提示：<br/>
									1、所有共享给下游企业的自建应用均需要填写，否则无法获取对应下游企业。<br/>
									2、多个应用ID之间用分号分隔；配置的应用中，不能存在多个应用分别给同个下游企业的情况。<br/>
									3、所有的应用都需要在 企业微信入口 -> 应用配置 中配置秘钥。<br/>
								</span>
							</td>
						</tr>
						<tr name="wxCorpGroupDetail">
							<td class="td_normal_title" width="15%">${ lfn:message('third-weixin-work:thirdWeixin.tab.contact.sync.org.type.setting') }</td>
							<td>
								<xform:select property="value(syncCorpGroupOrgType)" showPleaseSelect="false">
									<xform:customizeDataSource className="com.landray.kmss.third.weixin.service.spring.ThirdWeixinContactOrgTypeDataSource"></xform:customizeDataSource>
								</xform:select>
							</td>
						</tr>
						<tr name="wxCorpGroupDetail">
							<td class="td_normal_title" width="15%">企业员工字段映射</td>
							<td>
								<table style="text-align: center" class="tb_normal" width=100% >
									<tr>
										<td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.ekp" bundle="third-weixin-work"/></td>
										<td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.way" bundle="third-weixin-work"/></td>
										<td class="td_normal_title"><bean:message key="thirdWeixinConfig.syn.person.weixin" bundle="third-weixin-work"/></td>
									</tr>

									<tr>
										<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.name" bundle="third-weixin-work"/> <span class="txtstrong">*</span></td>
										<td width="25%">
											<xform:select property="value(corpGroup2ekp.name.synWay)"  className="selectsgl" subject="姓名" showStatus="edit"  showPleaseSelect="false">
												<xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
												<xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											</xform:select>
										</td>
										<td width="55%" align="left">
											<input type="hidden" name="value(corpGroup2ekp.name)" value="name" />
											<xform:select className="selectsgl" showStatus="view" value="fdName" property="value(corpGroup2ekp.name)" subject="${lfn:message('third-weixin-work:thirdWeixinConfig.syn.person.name')}" showPleaseSelect="false" htmlElementProperties="disabled=disabled" style="width:45%;">
												<xform:simpleDataSource value="name">名称<bean:message key="thirdWeixinConfig.syn.person.name" bundle="third-weixin-work"/></xform:simpleDataSource>
											</xform:select>
										</td>
									</tr>
									<tr>
										<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.userId" bundle="third-weixin-work"/> <span class="txtstrong">*</span></td>
										<td width="25%">
											<xform:select property="value(corpGroup2ekp.userid.synWay)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
												<xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											</xform:select>
										</td>
										<td width="55%" align="left">
											<xform:select property="value(corpGroup2ekp.userid)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
												<xform:simpleDataSource value="userid">userid</xform:simpleDataSource>
											</xform:select>
											<span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.userId.tip" bundle="third-weixin-work"/></span>
										</td>
									</tr>
									<!-- 排序号 -->
									<tr>
										<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.order" bundle="third-weixin-work"/></td>
										<td width="25%">
											<xform:select property="value(corpGroup2ekp.order.synWay)"  onValueChange="checkSyn(this)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  showStatus="edit"  showPleaseSelect="false">
												<xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
												<xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
												<xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											</xform:select>
										</td>
										<td width="55%" align="left">
											<div style="display: block">

												<xform:select property="value(corpGroup2ekp.orderInDepts)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
													<xform:simpleDataSource value="asc"><bean:message key="thirdWeixinConfig.syn.person.order.asc" bundle="third-weixin-work"/></xform:simpleDataSource>
													<xform:simpleDataSource value="desc"><bean:message key="thirdWeixinConfig.syn.person.order.desc" bundle="third-weixin-work"/></xform:simpleDataSource>
												</xform:select>
											</div>
										</td>
									</tr>

									<!-- 性别 -->
									<tr>
										<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.sex" bundle="third-weixin-work"/></td>
										<td width="25%">
											<xform:select property="value(corpGroup2ekp.sex.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  showStatus="edit"  showPleaseSelect="false">
												<xform:simpleDataSource value="noSyn"><bean:message key="thirdWeixinConfig.syn.person.noSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
												<xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
												<xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											</xform:select>
										</td>
										<td width="55%" align="left">
											<div style="display: block">
												<xform:select property="value(corpGroup2ekp.sex)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
													<xform:simpleDataSource value="gender">性别</xform:simpleDataSource>
												</xform:select>
											</div>
										</td>
									</tr>

									<!-- 部门 -->
									<tr>
										<td class="td_normal_title" width="15%"><bean:message key="thirdWeixinConfig.syn.person.dept" bundle="third-weixin-work"/> <span class="txtstrong">*</span></td>
										<td width="25%">
											<xform:select property="value(corpGroup2ekp.department.synWay)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false">
												<xform:simpleDataSource value="syn"><bean:message key="thirdWeixinConfig.syn.person.syn" bundle="third-weixin-work"/></xform:simpleDataSource>
												<xform:simpleDataSource value="addSyn"><bean:message key="thirdWeixinConfig.syn.person.addSyn" bundle="third-weixin-work"/></xform:simpleDataSource>
											</xform:select>
										</td>
										<td width="55%" align="left">
											<xform:select property="value(corpGroup2ekp.department)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
												<xform:simpleDataSource value="mainDept"><bean:message key="thirdWeixinConfig.syn.person.dept.select.main" bundle="third-weixin-work"/></xform:simpleDataSource>
												<xform:simpleDataSource value="muilDept"><bean:message key="thirdWeixinConfig.syn.person.dept.select.mutil" bundle="third-weixin-work"/></xform:simpleDataSource>
											</xform:select>
											<br/>
											<span style="color:red"><bean:message key="thirdWeixinConfig.syn.person.dept.tip" bundle="third-weixin-work"/></span>
										</td>
									</tr>

								</table>
							</td>
						</tr>
					</table>
					</ui:content>
					</c:if>
				</ui:tabpanel>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.third.weixin.work.model.WeixinWorkConfig" />
			<center style="margin:20px 0;">
				<!-- 保存 -->
				<ui:button text="${lfn:message('button.save')}" onclick="window.wxSubmit();" width="120" height="35"></ui:button>
			</center>
		</html:form>
		<script type="text/javascript">
			var validation = $KMSSValidation();
			var woi = "<%=WeixinWorkConfig.newInstance().getWxOrgId()   %>";
			function setLoginName(){
				var value = $('[name="value(wxLoginName)"]').val();
				if(value==""){
					$('[name="value(wxLoginName)"]').val("loginname");
				}else{
					if(value=="id"){
						$("#ln").empty();
						$("#ln").text("<bean:message key='third.weixin.work.config.ekpPrimary' bundle='third-weixin-work'/>");
					}else{
						$("#ln").empty();
						$("#ln").text("<bean:message key='third.weixin.work.config.wxLoginNameLn' bundle='third-weixin-work'/>");
						$('[name="value(wxLoginName)"]').val("loginname");
					}
				}
			}
			function cleanTime(){
				if(confirm("<bean:message key='third.weixin.work.config.clearMsg' bundle='third-weixin-work'/>")){
					var url = '<c:url value="/third/wxwork/weixinSynchroOrgCheck.do?method=cleanTime" />';
					$.ajax({
					   type: "POST",
					   url: url,
					   async:false,
					   dataType: "json",
					   success: function(data){
							if(data.status=="1"){
								alert("<bean:message key='third.weixin.work.config.clearSuccess' bundle='third-weixin-work'/>");
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
					var tip = "<bean:message key='third.weixin.work.config.org.tip' bundle='third-weixin-work'/>";
					var tip1 = "<bean:message key='third.weixin.work.config.org.tip1' bundle='third-weixin-work'/>";
					var tip2 = "<bean:message key='third.weixin.work.config.org.tip2' bundle='third-weixin-work'/>";
					var tip3 = "<bean:message key='third.weixin.work.config.org.tip3' bundle='third-weixin-work'/>";
					alert("${lfn:message('third-weixin-work:third.weiixn.work.config.org.tip')}");
				}
			}
			seajs.use(['lui/jquery'], function($) {
					function wx_display_change(){
						var value = $('[name="value(wxEnabled)"]').val();
						if(value == 'true'){
							$('#wxBaseTable tr[id!="wenxinEnableTR"]').show();
							$('#wxTodoTable,#wxOmsTable,#wxSsoTable,#wxBusinessTable').show();
						}else{
							$('#wxBaseTable tr[id!="wenxinEnableTR"]').hide();
							$('#wxTodoTable,#wxOmsTable,#wxSsoTable,#wxBusinessTable').hide();
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
							//$('#wxTodoTable tr[id="wxToReadNotifyTR"]').show();
							$('#wxTodoTable tr[id="wxToReadAgentidTR"]').show();
						}else{
							//$('#wxTodoTable tr[id="wxToReadNotifyTR"]').hide();
							$('#wxTodoTable tr[id="wxToReadAgentidTR"]').hide();
						}
					}

					/* function wxOms_display_change(){
						var value = $('[name="value(wxOmsOutEnabled)"]').val();
						if(value == 'true'){
							$('#wxOmsTable tr[id!="wxOmsTR"]').show();
						}else{
							$('#wxOmsTable tr[id!="wxOmsTR"]').hide();
						}
					} */
					
					function wxAuth_display_change(){
						var value = $('[name="value(wxAuthCheckEnabled)"]').val();
						if(value == 'true'){
							$('#authCheck_div').show();
						}else{
							$('#authCheck_div').hide();
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
					validation.addValidator('wxRequire1',"<bean:message key='third.weixin.work.config.notnull' bundle='third-weixin-work'/>",function(v, e, o){
						var wxEnabled = $('[name="value(wxOmsOutEnabled)"]').val();
						if(wxEnabled == 'true' && !v){
							var validator = this.getValidator('wxRequire1'),
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
					validation.addValidator('wxToReadRequire','{name}'+"<bean:message key='third.weixin.work.config.notnull' bundle='third-weixin-work'/>",function(v, e, o){
						var wxEnabled = $('[name="value(wxEnabled)"]').val();
						if(wxEnabled != 'true'){
							return true;
						}
						var todoEnabled = $('[name="value(wxTodoType2Enabled)"]').val();
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
					validation.addValidator('wxAuthCheckRequire','{name}'+"<bean:message key='third.weixin.work.config.notnull' bundle='third-weixin-work'/>",function(v, e, o){
						var wxEnabled = $('[name="value(wxEnabled)"]').val();
						if(wxEnabled != 'true'){
							return true;
						}
						var wxAuthCheckEnabled = $('[name="value(wxAuthCheckEnabled)"]').val();
						return (wxAuthCheckEnabled == 'true' && !v) ? false : true;
					});
					
					validation.addValidator('notSynPersonMaxCheck','{name}'+"<bean:message key='thirdWeixinConfig.notSyn.person.max' bundle='third-weixin-work'/>",function(v, e, o){
						var ids = $('[name="value(ekp2wx.noSyn.person.ids)"]').val();
						var _ids= new Array(); //定义一数组
						_ids = ids.split(";");
						if(_ids.length>100){
							return false;
						}else{
							return true;
						}
					});

					validation.addValidator('wxPayCheckRequire','{name}'+"<bean:message key='third.weixin.work.config.notnull' bundle='third-weixin-work'/>",function(v, e, o){
						var wxEnabled = $('[name="value(wxEnabled)"]').val();
						if(wxEnabled != 'true'){
							return true;
						}
						var wxPayEnable = $('[name="value(wxPayEnable)"]').val();
						return (wxPayEnable == 'true' && !v) ? false : true;
					});

				validation.addValidator('wxChatdataCheckRequire','{name}'+"<bean:message key='third.weixin.work.config.notnull' bundle='third-weixin-work'/>",function(v, e, o){
					var wxEnabled = $('[name="value(wxEnabled)"]').val();
					if(wxEnabled != 'true'){
						return true;
					}
					var chatdataSyncEnable = $('[name="value(chatdataSyncEnable)"]').val();
					return (chatdataSyncEnable == 'true' && !v) ? false : true;
				});

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
						LUI("tag5").on("show", function(){
							__init();
						});
						if(LUI("tag6")){
							LUI("tag6").on("show", function(){
								initContactSetting();
								__init();
							});
						}
					});
					
					window.__init = function(){
						wx_display_change();
						wxTodo_display_change();
						wxToRead_display_change();
						//wxOms_display_change();
						config_wx_callbackurl();
						//config_wx_notify_type(document.getElementsByName("value(wxNotifyType)"));
						//config_wx_notify_type(document.getElementsByName("value(wxToReadNotifyType)"));
						setLoginName(); 
						wxAuth_display_change();
						syncSelection_display_change();
						wxPay_display_change();
						wxChatdata_display_change();
						wxContact_display_change();
						wxCorpGroup_display_change();
					}

					window.initContactSetting = function(){
						var orgTypeSetting = $('[name="value(syncContact.orgType.setting)"]').val();
						if(orgTypeSetting==null || orgTypeSetting==''){
							return;
						}
						var array = JSON.parse(orgTypeSetting);
						for(var i=0;i<array.length;i++){
							var setting = array[i];
							var row = DocList_AddRow("TABLE_DocList");
							$(row).find('input').get(0).value = setting.tagId;
							$(row).find('input').get(1).value = setting.tagName;
							$(row).find('select').get(0).value = setting.tagOrgType;
						}
					};
					
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
						$('[name="value(wxSSOAttendSecret)"]').val($.trim($('[name="value(wxSSOAttendSecret)"]').val()));

						if(validateOrgTypeSetting()==false){
							return false;
						}

						//先后端删除拓展数据
						$.ajaxSettings.async = false;
						var url = '<c:url value="/third/weixin/work/sysAppConfig.do?method=deleteCustom" />';
						$.ajax({
						   type: "POST",
						   url: url,
						   async:false,
						   dataType: "json",
						   success: function(data){
								if(data.status=="1"){
									Com_Submit(document.sysAppConfigForm, 'update');
								}else{
									alert(data.msg);
								}
						   }
						});
						$.ajaxSettings.async = true;

					};
					
					window.wx_display_change = wx_display_change;
					window.wxTodo_display_change = wxTodo_display_change;
					window.wxToRead_display_change = wxToRead_display_change;
					//window.wxOms_display_change = wxOms_display_change;
					window.random_token = random_token;
					window.random_AESKey = random_AESKey;
					window.config_wx_callbackurl = config_wx_callbackurl;
					window.config_wx_dns_getUrl = config_wx_dns_getUrl;
					window.wxAuth_display_change = wxAuth_display_change;
					window.validateOrgTypeSetting = validateOrgTypeSetting;
				});

			function containTagId(settings, tagId){
				for(var i=0;i<settings.length;i++){
					var setting = settings[i];
					var ss = setting.tagId.split(";");
					var ss2 = tagId.split(";");
					for(var j=0;j<ss.length;j++){
						if(ss[j]==''){
							continue;
						}
						for(var k=0;k<ss2.length;k++){
							if(ss2[k]==''){
								continue;
							}
							if(ss2[k]==ss[j]){
								return true;
							}
						}
					}
				}
				return false;
			}

			function containTagOrgType(settings, tagOrgType){
				for(var i=0;i<settings.length;i++){
					var setting = settings[i];
					if(tagOrgType==setting.tagOrgType){
						return true;
					}
				}
				return false;
			}

			function validateOrgTypeSetting() {
				var settings = new Array();
				var trs = $("#TABLE_DocList").find("tr");
				for (var j = 1; j < trs.length; j++) {
						var tagId = $(trs[j]).find("input").get(0).value;
						var tagName = $(trs[j]).find("input").get(1).value;
						var tagOrgType = $(trs[j]).find("select").get(0).value;
						if (tagId == '' || tagOrgType == '') {
							alert("${lfn:message('third-weixin-work:thied.weixin.work.config.NotBeEmpty')}");
							return false;
						}
						if (containTagId(settings, tagId)) {
							alert("${lfn:message('third-weixin-work:thied.weixin.work.config.notRepeating')}");
							return false;
						}
						if (containTagOrgType(settings, tagOrgType)) {
							alert("${lfn:message('third-weixin-work:thied.weixin.work.config.EKPnotRepeating')}");
							return false;
						}
						settings.push({"tagId": tagId, "tagName": tagName, "tagOrgType": tagOrgType});

				}
				console.log(JSON.stringify(settings));

				var contactOrgTypeSetting = $('[name="value(syncContact.orgType.setting)"]');
				if(contactOrgTypeSetting){
					contactOrgTypeSetting.val(JSON.stringify(settings));
				}
				return true;
			}
			
				function checkSyn(target){
					var val=target.value;
					var $tr = target.closest("tr");
					if(val == "noSyn" || val == "false"){
						$($tr).find(".sys_property").css("display","none");
					}else{
						$($tr).find(".sys_property").css("display","block");
					}
				}
				
				//查询所有select
				var $allSyn = $('table').find("select[type='synWay']");
	            $allSyn.each(function(index,element){
	            	checkSyn(this);
	    		 });
	            
	          //通讯录 同步选择
				function syncSelection_display_change(){
					var syncSelection;
					var _type = document.getElementsByName("value(syncSelection)");
					for(var i = 0; i < _type.length; i++) {
    		 			if(_type[i].checked) {
    		 				syncSelection = _type[i].value;
    		 				break;
    		 			}
    		 		}
					//alert(syncSelection);
					if(syncSelection =='notSyn'){
						$('#wxOmsTable').hide();
						$('#omsFromWxTable').hide();
					}else if(syncSelection =='fromWx'){
						
						$('#wxOmsTable').hide();
						$('#omsFromWxTable').show();
						
					}else if(syncSelection =='toWx'){
						$('#wxOmsTable').show();
						$('#omsFromWxTable').hide();
					}
					
				}
	            
	          //清理企业微信人员
	          function cleanWxworkPerson(){
	        	  var ids = $('[name="value(ekp2wx.noSyn.person.ids)"]').val();
	        	  if(ids==""){
	        		  alert("<bean:message key='third.weixin.work.config.cleanWxworkPerson.null' bundle='third-weixin-work'/>");
	        	  }else{
	        		  var names = $('[name="value(ekp2wx.noSyn.person.names)"]').val();
		        	  if(confirm("<bean:message key='third.weixin.work.config.cleanWxworkPerson' bundle='third-weixin-work'/>")){
							var url = '<c:url value="/third/wxwork/weixinSynchroOrgCheck.do?method=cleanWxworkPerson" />';
							$.ajax({
							   type: "POST",
							   url: url,
							   async:false,
							   dataType: "json",
							   data:{
								   ids:ids,
								   names:names
							   },
							   success: function(data){
									if(data.status=="1"){
										alert("<bean:message key='third.weixin.work.config.cleanWxworkPerson.success' bundle='third-weixin-work'/>");
									}else{
										alert(data.msg);
									}
							   }
							});
						} 
	        	  }
	          }

			function addContactTagSetting(){
				var url = 'thirdWeixinWorkContactTagTreeService' + '&categoryId=!{value}';
				Dialog_Tree(true, null, null, ";", url, '${lfn:message('third-weixin-work:thirdWeixin.tab.contact.tag')}',true, dialog_ContactTag_add, null, null, null, '${lfn:message('third-weixin-work:thirdWeixin.tab.contact.sync.selectContactTag')}',null);

				}

				var idField_;
				var nameField_;
			function updateContactTagSetting(idField,nameField){
				var url = 'thirdWeixinWorkContactTagTreeService' + '&categoryId=!{value}';
				idField_ = idField;
				nameField_ = nameField;
				Dialog_Tree(true, idField, nameField, ";", url, '${lfn:message('third-weixin-work:thirdWeixin.tab.contact.tag')}',true, dialog_ContactTag_update, null, null, null, '${lfn:message('third-weixin-work:thirdWeixin.tab.contact.sync.selectContactTag')}',null);
			}

			function dialog_ContactTag_add(val){
				var result = buildTagIdAndName(val);
				var row = DocList_AddRow("TABLE_DocList");
				$(row).find('input').get(0).value = result.tagIds;
				$(row).find('input').get(1).value = result.tagNames;
			}

			function dialog_ContactTag_update(val){
				var result = buildTagIdAndName(val);
				document.getElementsByName(idField_)[0].value = result.tagIds;
				document.getElementsByName(nameField_)[0].value = result.tagNames;
			}

			function buildTagIdAndName(val){
				var datas = val.data;
				var datas_new = new Array();
				var datas_group = new Array();
				for(var i=0;i<datas.length;i++){
					var data = datas[i];
					var id = data.id;
					if(id.indexOf("group_")==0){
						//datas.splice(i, 1);
						//getTagsByGroup(id,datas_new);
						datas_group.push(data);
					}else{
						datas_new.push(data);
					}
				}
				if(datas_group.length>0){
					getTagsByGroup(datas_group,datas_new);
				}
				console.log("datas_new:"+datas_new);
				var tagIds = "";
				var tagNames = "";
				for(var i=0;i<datas_new.length;i++) {
					tagIds += datas_new[i].id+";";
					tagNames += datas_new[i].name+";";
				}
				return {"tagIds":tagIds,"tagNames":tagNames};
			}

			function getTagsByGroup(datas_group, datas){
				var groupids = "";
				for(var i=0;i<datas_group.length;i++){
					groupids += datas_group[i].id+";";
				}
				var url = '<c:url value="/third/wxwork/thirdWeixinWorkContact.do?method=getGroupTags" />'+'&group_id='+groupids;
				$.ajax({
					type: "GET",
					url: url,
					async:false,
					dataType: "json",
					success: function(data){
						console.log("getTagsByGroup:"+data);
						if(data.status=="1"){
							var tags = data.tags;
							addTags(datas,tags);
							console.log("datas2:"+datas);
						}else{
							alert(data.errmsg);
						}
					}
				});
			}

			function isContains(tags_all, tag){
				for(var j=0;j<tags_all.length;j++){
					var id_ = tags_all[j].id;
					if(tag.id==id_){
						return true;
					}
				}
				return false;
			}

			function addTags(tags_all,tags_add){
				var to_add = new Array();
				for(var i=0;i<tags_add.length;i++){
					if(!isContains(tags_all,tags_add[i])){
						tags_all.push(tags_add[i]);
					}
				}
			}

			function syncContact2ekp(){
				var secret = $('input[name="value(syncContact.secret)"]').val();
				if(secret == null || secret == ''){
					alert("<bean:message key='third.weixin.work.form.secret.empty' bundle='third-weixin-work'/>");
					return;
				}
				var url = '<c:url value="/third/wxwork/thirdWeixinWorkContact.do?method=synchro2ekp" />';
				window.open(url,'_blank');
			}

			function wxPay_display_change(){
				var value = $('[name="value(wxPayEnable)"]').val();
				if(value == 'true'){
					$('tr[name="wxPayTR"]').show();
				}else{
					$('tr[name="wxPayTR"]').hide();
				}
			}

			function wxChatdata_display_change(){
				var value = $('[name="value(chatdataSyncEnable)"]').val();
				if(value == 'true'){
					$('#wxChatdataDiv').show();
				}else{
					$('#wxChatdataDiv').hide();
				}
			}

			function genRsaKey() {
				var url = '<c:url value="/third/weixin/third_weixin_chat_data/thirdWeixinChatData.do?method=genRsaKey" />';
				$.ajax({
					type: "POST",
					url: url,
					async: false,
					dataType: "json",
					success: function (data) {
						if (data.status == "1") {
							var publicKey = data.publicKey;
							var privateKey = data.privateKey;
							$('[name="value(chatdataSyncPublicKey)"]').val(publicKey);
							$('[name="value(chatdataSyncPrivateKey)"]').val(privateKey);
						} else {
							alert("生成秘钥对失败，失败信息：" + data.msg);
						}
					}
				});
			}

			function wxContact_display_change(){
				var value = $('[name="value(contactIntegrateEnable)"]').val();
				if(value == 'true'){
					$('tr[name="wxContactDetail"]').show();
				}else{
					$('tr[name="wxContactDetail"]').hide();
				}
			}

			function wxCorpGroup_display_change(){
				var value = $('[name="value(corpGroupIntegrateEnable)"]').val();
				if(value == 'true'){
					$('tr[name="wxCorpGroupDetail"]').show();
				}else{
					$('tr[name="wxCorpGroupDetail"]').hide();
				}
			}

			function syncCorpGtoup2ekp(){
				window.open(Com_Parameter.ContextPath+"third/weixin/work/sysAppConfig.do?method=syncCorpGtoup2ekp",'_blank');
			}

		</script>
	</template:replace>	
</template:include>
