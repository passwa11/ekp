<%@page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@page import="com.landray.kmss.sys.appconfig.forms.SysAppConfigForm"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%@page import="java.util.*"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.sys.organization.util.SysOrgEcoUtil"%>
<%@page import="java.lang.String"%>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	request.setAttribute("fd_constom_List", DingConfig.getCustomData());
	DingConfig oldconfig = DingConfig.newInstance(); 
	request.setAttribute("oldDingOrgId",oldconfig.getDingOrgId());
	request.setAttribute("oldSyncSelection",oldconfig.getSyncSelection());
	request.setAttribute("dingOmsOutEnabled",oldconfig.getDingOmsOutEnabled());
	request.setAttribute("dingOmsInEnabled",oldconfig.getDingOmsInEnabled());
	request.setAttribute("ldingEnabled",oldconfig.getLdingEnabled());
	
	//记录组织编号开关
	String noEnable = "false";
	ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil
			.getBean("sysAppConfigService");
	Map orgMap = sysAppConfigService.findByKey(
			"com.landray.kmss.sys.organization.model.SysOrganizationConfig");
	if (orgMap != null && orgMap.containsKey("isNoRequired")) {
		Object noObject = orgMap.get("isNoRequired");
		if (noObject != null && "true".equals(noObject.toString())) {
			noEnable = "true";
		} 
	}
	request.setAttribute("noEnable",noEnable);
	SysAppConfigForm sysAppConfigForm = (SysAppConfigForm)request.getAttribute("sysAppConfigForm");
	
	String noRequired = (String)sysAppConfigForm.getValue("isNoRequired");
	
	//旧数据兼容
	Object oldData = sysAppConfigForm.getValue("dingOmsCreateDeptGroup"); //企业群
	if(oldData != null && "true".equals(oldData.toString())){
		sysAppConfigForm.setValue("org2ding.dept.group.all", "true"); 
		sysAppConfigForm.setValue("org2ding.dept.group.synWay", "syn"); 
	}
	oldData = sysAppConfigForm.getValue("dingWorkPhoneEnabled"); //办公电话
	if(oldData != null && "true".equals(oldData.toString())){
		sysAppConfigForm.setValue("org2ding.tel.synWay", "syn"); 
		sysAppConfigForm.setValue("org2ding.tel", "fdWorkPhone"); 
	}
	
	//工号 dingNoEnabled  org2ding.jobnumber
	oldData = sysAppConfigForm.getValue("dingNoEnabled"); 
	if(oldData != null && "true".equals(oldData.toString())){
		sysAppConfigForm.setValue("org2ding.jobnumber.synWay", "syn"); 
		sysAppConfigForm.setValue("org2ding.jobnumber", "fdNo"); 
	}
	
	//职位  dingPostEnabled -> org2ding.position :hbmPosts
	oldData = sysAppConfigForm.getValue("dingPostEnabled"); 
	if(oldData != null && "true".equals(oldData.toString())){
		sysAppConfigForm.setValue("org2ding.position.synWay", "syn"); 
		sysAppConfigForm.setValue("org2ding.position", "hbmPosts"); 
	}
	
	//人员排序  dingPersonOrder （1默认倒序，0默认升序） ->  org2ding.orderInDepts(asc desc)
	oldData = sysAppConfigForm.getValue("dingPersonOrder"); 
	if(oldData != null && oldData != ""){
		if(oldData.toString().equals("0")){
			sysAppConfigForm.setValue("org2ding.orderInDepts.synWay", "syn"); 
			sysAppConfigForm.setValue("org2ding.orderInDepts", "asc"); 
		}else if(oldData.toString().equals("1")){
			sysAppConfigForm.setValue("org2ding.orderInDepts.synWay", "syn"); 
			sysAppConfigForm.setValue("org2ding.orderInDepts", "desc"); 
		}
		
	}
	
	//一人多部门  dingPostMulDeptEnabled -> org2ding.department  fdMuilDept
	oldData = sysAppConfigForm.getValue("dingPostMulDeptEnabled"); 
	if(oldData != null && "true".equals(oldData.toString())){
		
		sysAppConfigForm.setValue("org2ding.department.synWay", "syn"); 
		sysAppConfigForm.setValue("org2ding.department", "fdMuilDept"); 
	}
	
	//部门领导同步 dingDeptLeaderEnabled -> org2ding.dept.deptManager : hbmThisLeader
	oldData = sysAppConfigForm.getValue("dingDeptLeaderEnabled"); 
	if(oldData != null && "true".equals(oldData.toString())){
		
		sysAppConfigForm.setValue("org2ding.dept.deptManager.synWay", "syn"); 
		sysAppConfigForm.setValue("org2ding.dept.deptManager", "hbmThisLeader"); 
	}
	
	//userId
	oldData = sysAppConfigForm.getValue("wxLoginName"); 
	if(oldData != null && oldData != ""){
		
		if(oldData.toString().equalsIgnoreCase("id")){
			sysAppConfigForm.setValue("org2ding.userid", "fdId"); 
		}
		if(oldData.toString().equalsIgnoreCase("loginname")){
			sysAppConfigForm.setValue("org2ding.userid", "fdLoginName"); 
		}
		
	}
	//手机号码隐藏和部门创建初始化
	oldData = sysAppConfigForm.getValue("org2ding.isHide.all"); 
	if(oldData == "" || oldData == null){
		sysAppConfigForm.setValue("org2ding.isHide.all", "false"); 
	}
	
	oldData = sysAppConfigForm.getValue("org2ding.dept.group.all"); 
	if(oldData == "" || oldData == null){
		sysAppConfigForm.setValue("org2ding.dept.group.all", "false"); 
	}
	
	if(sysAppConfigForm.getValue("devModel")==null ||sysAppConfigForm.getValue("devModel")==""){
		sysAppConfigForm.setValue("devModel", "2"); 
	}

	//专有账号数据初始化
	oldData = sysAppConfigForm.getValue("org2ding.isExclusiveAccount.all");
	if(oldData == "" || oldData == null){
		sysAppConfigForm.setValue("org2ding.isExclusiveAccount.all", "true");
	}
	oldData = sysAppConfigForm.getValue("org2ding.exclusiveAccount.type");
	if(oldData == "" || oldData == null){
		sysAppConfigForm.setValue("org2ding.exclusiveAccount.type", "dingtalk");
	}
	oldData = sysAppConfigForm.getValue("org2ding.exclusiveAccount.loginName");
	if(oldData == "" || oldData == null){
		sysAppConfigForm.setValue("org2ding.exclusiveAccount.loginName", "fdLoginName");
	}

	//钉钉到ekp的旧数据兼容
	//登录名选择
	Object dingOldData = sysAppConfigForm.getValue("ding2ekp.loginName.synWay"); 
	if(dingOldData == null || dingOldData == ""){
		sysAppConfigForm.setValue("ding2ekp.loginName.synWay", "addSyn"); 
		sysAppConfigForm.setValue("ding2ekp.loginName", "mobile"); 
	}
	//姓名
	dingOldData = sysAppConfigForm.getValue("ding2ekp.name.synWay"); 
	if(dingOldData == null || dingOldData == ""){
		sysAppConfigForm.setValue("ding2ekp.name.synWay", "syn"); 
		sysAppConfigForm.setValue("ding2ekp.name", "name"); 
	}
	
	//一人多部门dingOmsInMoreDeptEnabled
	dingOldData = sysAppConfigForm.getValue("dingOmsInMoreDeptEnabled"); 
	if(dingOldData != null && "true".equals(dingOldData.toString())){
		sysAppConfigForm.setValue("ding2ekp.department.synWay", "syn"); 
		sysAppConfigForm.setValue("ding2ekp.department", "multDept"); 
	}
	
	//手机号码
	dingOldData = sysAppConfigForm.getValue("ding2ekp.mobile.synWay"); 
	if(dingOldData == null || dingOldData == ""){
		sysAppConfigForm.setValue("ding2ekp.mobile.synWay", "syn"); 
	}
	//编号 ding2ekp.fdNo.synWay
	dingOldData = sysAppConfigForm.getValue("ding2ekp.fdNo.synWay"); 
	if(dingOldData == null || dingOldData == ""){
		sysAppConfigForm.setValue("ding2ekp.fdNo.synWay", "syn"); 
		sysAppConfigForm.setValue("ding2ekp.fdNo", "jobnumber"); 
	}
	//邮箱 ding2ekp.email.synWay
	dingOldData = sysAppConfigForm.getValue("ding2ekp.email.synWay"); 
	if(dingOldData == null || dingOldData == ""){
		sysAppConfigForm.setValue("ding2ekp.email.synWay", "syn"); 
		sysAppConfigForm.setValue("ding2ekp.email", "email"); 
	}
	//办公电话ding2ekp.tel.synWay
	dingOldData = sysAppConfigForm.getValue("ding2ekp.tel.synWay"); 
	if(dingOldData == null || dingOldData == ""){
		sysAppConfigForm.setValue("ding2ekp.tel.synWay", "syn"); 
		sysAppConfigForm.setValue("ding2ekp.tel", "tel"); 
	}
	
	//部门主管 dingOmsInDeptManagerEnabled
	dingOldData = sysAppConfigForm.getValue("dingOmsInDeptManagerEnabled"); 
	if(dingOldData != null && "true".equals(dingOldData.toString())){
		sysAppConfigForm.setValue("ding2ekp.dept.leader.synWay", "syn"); 
		sysAppConfigForm.setValue("ding2ekp.dept.leader", "leader"); 
	}
	//是否开启生态组织
	String ISENABLEDECO = "false";
	if (SysOrgEcoUtil.IS_ENABLED_ECO) {
	    // 开启生态组织
		ISENABLEDECO = "true";
	}
	request.setAttribute("ISENABLEDECO",ISENABLEDECO);
	request.setAttribute("sysAppConfigForm",sysAppConfigForm);
	
	com.landray.kmss.third.ding.util.ThirdDingDing2ekpCustomData.getDingCustomInfo();
	com.landray.kmss.third.ding.util.ThirdDingDing2ekpRoleCustomData.getDingCustomInfo();
%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message key="third.ding.config.setting" bundle="third-ding"/></template:replace>
	<template:block name="path" >
		<span class=txtlistpath><bean:message key="third.ding.config.currurl" bundle="third-ding"/>：<bean:message key="third.ding.config.setting" bundle="third-ding"/></span>
	</template:block>
	<template:replace name="head">
		<style>
		body{padding:20px;}.message{margin-top:10px;}
		a.dingLink{color:#15a4fa; text-decoration: underline;}
		a.dingLink:hover{color:red; }

		.custom_width_15{
			width: 15%;
		}
		.custom_width_25{
			width: 25%;
		}
		.custom_width_55{
			width: 55%;
		}
		</style>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin:10px 0">
			<span class="profile_config_title"><bean:message key="third.ding.config.setting" bundle="third-ding"/></span>
		</h2>
		<html:form action="/third/ding/sysAppConfig.do?autoclose=false" onsubmit="return validateAppConfigForm(this);" >
			<center>
				<ui:tabpanel>
					<!-- 基础配置 -->
					<ui:content id="tag1" title="${ lfn:message('third-ding:thirdDing.tab.base.setting') }">
						<br/>
						<table id="dingBaseTable" class="tb_normal" width=100%>
							<tr id="dingEnableTR">
								<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.turnoff" bundle="third-ding"/></td>
								<td>
									<ui:switch property="value(dingEnabled)" onValueChange="ding_display_change();" 
										enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%">CorpID</td>
								<td>
									<xform:text property="value(dingCorpid)" style="width:85%;" showStatus="edit" required="false" subject="CorpID" validators="dingRequire"/>
									<span class="txtstrong">*</span>
									<div class="message">
										<bean:message key="third.ding.config.dingCorpId" bundle="third-ding"/></div>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="thirdDing.devModel" bundle="third-ding"/></td>
								<td>
									<xform:radio property="value(devModel)" showStatus="edit" required="true" onValueChange="devModel_display_change();">
										<xform:enumsDataSource enumsType="third_ding_devmodel"></xform:enumsDataSource>
									</xform:radio>
								</td>
							</tr>
							<tr name="dev1">
								<td class="td_normal_title" width="15%">CorpSecret <bean:message key="third.ding.config.old" bundle="third-ding"/></td>
								<td>
									<xform:text property="value(dingCorpSecret)" subject="CorpSecret" required="false" style="width:85%" showStatus="edit"/><span class="txtstrong">*</span>
									<div class="message"><bean:message key="third.ding.config.dingCorpSecret" bundle="third-ding"/></div>
										
								</td>
							</tr>
							<tr name="dev2">
								<td class="td_normal_title" width="15%">AppKey <bean:message key="third.ding.config.new" bundle="third-ding"/></td>
								<td>
									<xform:text property="value(appKey)" subject="AppKey" required="false" style="width:85%" showStatus="edit"/><span class="txtstrong">*</span>
									<div class="message"><bean:message key="third.ding.config.appkey" bundle="third-ding"/></div>
								</td>
							</tr>
							<tr name="dev2">
								<td class="td_normal_title" width="15%">AppSecret <bean:message key="third.ding.config.new" bundle="third-ding"/></td>
								<td>
									<xform:text property="value(appSecret)" subject="AppSecret" required="false" style="width:85%" showStatus="edit"/><span class="txtstrong">*</span>
									<div class="message"><bean:message key="third.ding.config.appsecret" bundle="third-ding"/></div>									
								</td>
							</tr>
							<tr name="dev3">
								<td class="td_normal_title" width="15%">CustomKey <bean:message key="third.ding.config.new" bundle="third-ding"/></td>
								<td>
									<xform:text property="value(customKey)" subject="CustomKey" required="false" style="width:85%" showStatus="edit"/><span class="txtstrong">*</span>
									<div class="message"><bean:message key="thirdDing.devModel.3.note" bundle="third-ding"/></div>
								</td>
							</tr>
							<tr name="dev3">
								<td class="td_normal_title" width="15%">CustomSecret <bean:message key="third.ding.config.new" bundle="third-ding"/></td>
								<td>
									<xform:text property="value(customSecret)" subject="CustomSecret" required="false" style="width:85%" showStatus="edit"/><span class="txtstrong">*</span>
									<div class="message"><bean:message key="thirdDing.devModel.3.note" bundle="third-ding"/></div>									
								</td>
							</tr>
							
							<!-- 默认应用aggenId -->
							<tr>
								<td class="td_normal_title" width="15%">${lfn:message('third-ding:third.ding.config.app.agentid')}</td>
								<td>
									<xform:text property="value(dingAgentid)" subject="${lfn:message('third-ding:third.ding.config.app.agentid')}" required="false" style="width:85%" showStatus="edit" validators="dingRequire" htmlElementProperties="onblur='changeTodoAgentId(this)'"/>
									<span class="txtstrong">*</span>
									<div class="message">
									<bean:message key="third.ding.config.appsecret" bundle="third-ding"/>;
									<bean:message key="third.ding.config.dingAgentidMsg" bundle="third-ding"/><a class="dingLink" href="https://oa.dingtalk.com" target="_blank"><bean:message key="third.ding.config.dingAgentidMsg.dingLink" bundle="third-ding"/></a><bean:message key="third.ding.config.dingAgentidMsg.tip" bundle="third-ding"/></div>
								</td>
							</tr>
								
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingDomainTitle" bundle="third-ding"/></td>
								<td>
									<xform:text property="value(dingDomain)" subject="${lfn:message('third-ding:third.ding.config.dingDomainSubject') }" 
										required="false" style="width:85%" showStatus="edit" onValueChange="config_ding_callbackurl();"/>&nbsp;&nbsp;
									<ui:button text="${lfn:message('third-ding:third.ding.config.dingDomainBtn') }" onclick="config_ding_dns_getUrl();config_ding_callbackurl();" style="vertical-align: top;"></ui:button>	
									<div class="message"><bean:message key="third.ding.config.dingDomainBtnMsg" bundle="third-ding"/></div>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingCallbackurlTitle" bundle="third-ding"/></td>
								<td>
									<input type="hidden" name="dingUrlSuffix" value="/resource/third/ding/endpoint.do?method=service">
									<xform:text property="value(dingCallbackurl)" subject="${lfn:message('third-ding:third.ding.config.dingCallbackurlSubject') }" 
										required="false" style="width:85%" showStatus="readOnly" />
									<div class="message"><bean:message key="third.ding.config.dingCallbackurlMsg" bundle="third-ding"/></div>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingTokenTitle" bundle="third-ding"/></td>
								<td>
									<xform:text property="value(dingToken)" subject="${lfn:message('third-ding:third.ding.config.dingTokenSubject') }" required="false" style="width:85%" showStatus="edit" validators="dingRequire"/>
									<span class="txtstrong">*</span>
									<ui:button text="${lfn:message('third-ding:third.ding.config.dingTokenBtn') }" onclick="window.random_token();validation.validateElement(document.getElementsByName('value(dingToken)')[0]);" style="vertical-align: top;"></ui:button>
									<div class="message"><bean:message key="third.ding.config.dingTokenBtnMsg" bundle="third-ding"/></div>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingAeskeyTitle" bundle="third-ding"/></td>
								<td>
									<xform:text property="value(dingAeskey)" subject="${lfn:message('third-ding:third.ding.config.dingAeskeySubject') }" required="false" style="width:85%" showStatus="edit" validators="dingRequire"/>
									<span class="txtstrong">*</span>
									<ui:button text="${lfn:message('third-ding:third.ding.config.dingAeskeyBtn') }" onclick="window.random_AESKey();validation.validateElement(document.getElementsByName('value(dingAeskey)')[0]);" style="vertical-align: top;"></ui:button>
									<div class="message"><bean:message key="third.ding.config.dingAeskeyBtnMsg" bundle="third-ding"/></div>
									<div class="txtstrong"><bean:message key="third.ding.config.dingAeskeyBtnMsg.tip" bundle="third-ding"/></div>
								</td>
							</tr>
							<c:if test="${sysAppConfigForm.getValue('syncSelection') == 2 || sysAppConfigForm.getValue('syncSelection') == 1}">
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="thirdDingCallback.info" bundle="third-ding"/></td>
								<input type="hidden" name="value(dingCallback)"> 
								<td>
								   <c:if test="${sysAppConfigForm.getValue('dingCallback') == 1}">
								     <bean:message key="thirdDingCallback.success" bundle="third-ding"/>
								       <br/><bean:message key="third.ding.config.callback.address" bundle="third-ding"/>：${sysAppConfigForm.getValue('dingCallbackurl')}
								     
								   </c:if>
								   <c:if test="${sysAppConfigForm.getValue('dingCallback') == 2}">
								     <span class="txtstrong"><bean:message key="thirdDingCallback.fail" bundle="third-ding"/></span>
								     <br/><bean:message key="third.ding.config.callback.fail.reason" bundle="third-ding"/>：${sysAppConfigForm.getValue('dingCallbackErro')}
								      <br/> <ui:button text="${lfn:message('third-ding:third.ding.config.callback.register')}" onclick="repeat_register(false);" style="vertical-align: top;"></ui:button>
								   </c:if>
								   <c:if test="${sysAppConfigForm.getValue('dingCallback') == -1}">
								     <span class="txtstrong"><bean:message key="thirdDingCallback.delet.fail" bundle="third-ding"/></span>
								     <br/><bean:message key="third.ding.config.callback.fail.reason" bundle="third-ding"/>：${sysAppConfigForm.getValue('dingCallbackErro')}
								     <br/>
								                （<bean:message key="third.ding.config.callback.exist.tip" bundle="third-ding"/>：${sysAppConfigForm.getValue('oldDingCallbackurl')}）
								        <ui:button text="删除并注册新回调" onclick="repeat_register(true);" style="vertical-align: top;"></ui:button>
								   </c:if>
								   <c:if test="${sysAppConfigForm.getValue('dingCallback') == -11}">
								     <span class="txtstrong"><bean:message key="thirdDingCallback.delet.success" bundle="third-ding"/></span>
								     <br/> <ui:button text="注册回调" onclick="repeat_register(false);" style="vertical-align: top;"></ui:button>
								   </c:if>
								   <c:if test="${empty sysAppConfigForm.getValue('dingCallback')}">
								       <c:if test="${ empty sysAppConfigForm.getValue('dingCallbackErro')}">
									      <span class="txtstrong"><bean:message key="thirdDingCallback.not" bundle="third-ding"/></span>
									   </c:if>
								       <c:if test="${not empty sysAppConfigForm.getValue('dingCallbackErro')}">
									      <span class="txtstrong"><bean:message key="thirdDingCallback.fail" bundle="third-ding"/></span>
									       <br/>失败原因：${sysAppConfigForm.getValue('dingCallbackErro')}
									   </c:if>
								   </c:if>
								    <c:if test="${sysAppConfigForm.getValue('dingCallback') == -2}">
								     <span style="width: 82%;" class="txtstrong"><bean:message key="thirdDingCallback.fail.repeat" bundle="third-ding"/><br>（钉钉端已注册了其他域名的回调事件。回调地址：${sysAppConfigForm.getValue('oldDingCallbackurl')}）</span>
								     <ui:button text="删除并注册新回调" onclick="repeat_register(true);" style="vertical-align: top;"></ui:button>
								     
								   </c:if>
									
								</td>
							</tr>
							</c:if>

							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingEnableRateLimit" bundle="third-ding"/></td>
								<td>
									<ui:switch property="value(dingEnableRateLimit)"
											   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									<span style="width: 82%;" class="txtstrong"><bean:message key="third.ding.config.dingEnableRateLimit.tip" bundle="third-ding"/></span>
									<br><br>
									<bean:message key="third.ding.config.dingEnableRateLimit.count" bundle="third-ding"/>：
									<xform:text property="value(dingEnableRateLimitCount)" required="false" style="width:15%" showStatus="edit" />
									<span style="width: 82%;" class="txtstrong"><bean:message key="third.ding.config.dingEnableRateLimit.count.tip" bundle="third-ding"/></span>

								</td>
							</tr>

						</table>
					</ui:content>
					
					
					<!-- 登录配置 -->
					<ui:content id="tag2" title="${ lfn:message('third-ding:thirdDing.tab.login.setting') }">
						<br/>
						<table class="tb_normal" width=100%>
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingOauth2EnabledTitle" bundle="third-ding"/></td>
								<td>
									<ui:switch property="value(dingOauth2Enabled)" onValueChange="" 
										enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									<div class="message"><bean:message key="third.ding.config.dingOauth2EnabledMsg" bundle="third-ding"/></div>
								</td>
							</tr>
							<!--SSOSecret配置项-->
							<c:if test='<%=!"true".equals(ResourceUtil.getKmssConfigString("kmss.ding.addAppKey"))%>'>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingmngSSOSecretTitle" bundle="third-ding"/></td>
									<td>
										<xform:text property="value(dingmngSSOSecret)" required="false" style="width:85%" showStatus="edit"/>
										<div class="message"><bean:message key="third.ding.config.dingmngSSOSecretMsg" bundle="third-ding"/>
											<br/><bean:message key="third.ding.config.dingmngSSOSecretInfoMsg" bundle="third-ding"/></div>
									</td>
								</tr>
							</c:if>
						</table>
						<br/>
						<table id="dingPcScanTable" class="tb_normal" width=100%>
							<tr id="dingPcScanEnableTR">
								<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingPcScanLoginEnabledTitle" bundle="third-ding"/></td>
								<td>
									<ui:switch property="value(dingPcScanLoginEnabled)" onValueChange="dingPcScan_display_change()" 
										enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingPcScanappIdTitle" bundle="third-ding"/></td>
								<td>
									<xform:text property="value(dingPcScanappId)" style="width:85%;" showStatus="edit" required="false" subject="${lfn:message('third-ding:third.ding.config.dingPcScanappIdSubject') }" validators="dingPcScanRequire"/>
									<span class="txtstrong">*</span>
									<div class="message"><bean:message key="third.ding.config.dingPcScanappIdMsg" bundle="third-ding"/></div>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingPcScanappSecretTitle" bundle="third-ding"/></td>
								<td>
									<xform:text property="value(dingPcScanappSecret)" style="width:85%;" showStatus="edit" required="false" subject="${lfn:message('third-ding:third.ding.config.dingPcScanappSecretSubject') }" validators="dingPcScanRequire"/>
									<span class="txtstrong">*</span>
									<div class="message"><bean:message key="third.ding.config.dingPcScanappSecretMsg" bundle="third-ding"/></div>
								</td>
							</tr>
						</table>
						
					</ui:content>
					
					
					<!-- 待办待阅推送配置 -->
					<ui:content id="tag3" title="${ lfn:message('third-ding:thirdDing.tab.notify.setting') }">
						<br/>
						<table id="dingTodoTable" class="tb_normal" width=100%>
								<%-- <tr id="dingTodoTR">
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingTodoEnabledTitle" bundle="third-ding"/></td>
									<td>
										<ui:switch property="value(dingTodoEnabled)" onValueChange="dingTodo_display_change();" 
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</td>
								</tr> --%>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingTodoTitle" bundle="third-ding"/></td>
									<td>
										<table style="width: 90%">
											<tr>
												<td width="350px">
													<!-- 推送到钉钉的待办 -->
													<div style="float: left">
														<ui:switch property="value(dingWorkRecordEnabled)" onValueChange=""
														enabledText="${lfn:message('third-ding:third.ding.config.dingWorkRecordEnabledTitle')}" disabledText="${lfn:message('third-ding:third.ding.config.dingWorkRecordEnabledTitle') }"></ui:switch>
													</div>
													<div style="float: left; padding-left: 50px;">
														<xform:checkbox property="value(notify.isOnlyShowExecutor)"  showStatus="edit" >
															<xform:simpleDataSource value="true"><bean:message key="third.ding.config.todo.isOnlyShowExecutor" bundle="third-ding" /></xform:simpleDataSource>
														</xform:checkbox>
													</div>
													<br>
													<div class="message" style="float: none;"><bean:message key="third.ding.config.dingWorkRecordEnabledTitle.tip" bundle="third-ding"/></div>
													<br>
												</td>
											</tr>
											<tr>
												<td width="150px">
													<!-- 推送到钉钉的通知 -->
													<div style="float: left">
															<ui:switch property="value(dingTodotype1Enabled)" onValueChange=""
													enabledText="${lfn:message('third-ding:third.ding.config.dingTodotype1EnabledTitle')}" disabledText="${lfn:message('third-ding:third.ding.config.dingTodotype1EnabledTitle') }"></ui:switch>
													</div>
													<div style="float: left; padding-left: 50px;">
														<xform:checkbox property="value(notify.updateMessageStatus)"  showStatus="edit" >
															<xform:simpleDataSource value="true"><bean:message key="third.ding.config.dingTodotype1EnabledTitle.showStatus" bundle="third-ding" /></xform:simpleDataSource>
														</xform:checkbox>
													</div>
													<br>
													<div style="float: none;" class="message"><bean:message key="third.ding.config.dingTodotype1EnabledTitle.tip" bundle="third-ding"/></div>
												</td>
											</tr>
										</table>
										</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingTodotype2EnabledTitle" bundle="third-ding"/></td>
									<td>
										<ui:switch property="value(dingTodotype2Enabled)" onValueChange="" 
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
										<div class="message"><bean:message key="third.ding.config.dingTodotype2EnabledMsg" bundle="third-ding"/></div>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingTodoPcOpenType" bundle="third-ding"/></td>
									<td>
										<xform:radio property="value(dingTodoPcOpenType)" showStatus="edit">
											<xform:simpleDataSource value="in" textKey="third.ding.config.dingTodoPcOpenType.in" bundle="third-ding"></xform:simpleDataSource>
											<xform:simpleDataSource value="out" textKey="third.ding.config.dingTodoPcOpenType.out" bundle="third-ding"></xform:simpleDataSource>
										</xform:radio>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.sso.address.url" bundle="third-ding"/></td>
									<td>
										<ui:switch property="value(dingTodoSsoEnabled)"
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
										<div class="message"><bean:message key="third.ding.sso.address.url.tip" bundle="third-ding"/></div>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="thirdDingWork.app.notify" bundle="third-ding"/></td>
									<td>
									    <span name="defaultAgentId">
									    <c:if test="${sysAppConfigForm.getValue('dingAgentid') == ''}">
									         <bean:message key="thirdDingWork.app.agentId" bundle="third-ding"/>
									    </c:if>
									    <c:if test="${sysAppConfigForm.getValue('dingAgentid') != ''}">
									       ${sysAppConfigForm.getValue('dingAgentid')}
									    </c:if>
									   </span>(<bean:message key="thirdDingWork.app.default" bundle="third-ding"/>)
									    <span style="margin-left: 30%">
									     <xform:checkbox subject="" property="value(sendAsModel)" dataType="boolean">
											<xform:simpleDataSource value="true"><bean:message key="thirdDingWork.app.send.way" bundle="third-ding"/></xform:simpleDataSource>
										</xform:checkbox>
									    </span>
									   
										<div class="message">
										 <bean:message key="thirdDingWork.app.send.tip" bundle="third-ding"/>
										</div>
										<%-- <xform:text property="value(dingAgentid)" subject="${lfn:message('third-ding:third.ding.config.dingAgentidSubject') }" required="false" style="width:85%" showStatus="edit" validators="dingTodoRequire" />
										<span class="txtstrong">*</span>
										<div class="message"><bean:message key="third.ding.config.dingAgentidMsg" bundle="third-ding"/><a class="dingLink" href="https://oa.dingtalk.com" target="_blank"><bean:message key="third.ding.config.dingAgentidMsg.dingLink" bundle="third-ding"/></a><bean:message key="third.ding.config.dingAgentidMsg.tip" bundle="third-ding"/></div> --%>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingTitleColorTitle" bundle="third-ding"/></td>
									<td>
										<xform:text property="value(dingTitleColor)" subject="${lfn:message('third-ding:third.ding.config.dingTitleColorSubject') }" 
											required="false" style="width:85%" showStatus="edit" />
										<div class="message"><bean:message key="third.ding.config.dingTitleColorMsg" bundle="third-ding"/></div>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.notifyLog.dingLogDays" bundle="third-ding"/></td>
									<td>
										<xform:text property="value(dingLogDays)" subject="dingLogDays" required="false" style="width:85%" showStatus="edit"/>
										<div class="message"><bean:message key="third.ding.notifyLog.dingLogDays.msg" bundle="third-ding"/></div>									
									</td>
								</tr>
								<%-- <tr>
									<td class="td_normal_title" width="15%"><bean:message key="dingconfig.notifyClearDay" bundle="third-ding"/></td>
									<td>
										<xform:datetime property="value(notifyClearDay)" showStatus="edit" dateTimeType="date" style="width:95%;" />
										<div class="message"><bean:message key="dingconfig.notifyClearDay.msg" bundle="third-ding"/></div>									
									</td>
								</tr> --%>
									<input id="ori_task" type="hidden" value="${sysAppConfigForm.getValue('notifyApiType')}"/>
									<tr style="">
									<td class="td_normal_title" width="15%">钉钉待办接口类型</td>
									<td>
										<xform:radio  property="value(notifyApiType)">
											<xform:simpleDataSource value="WF">工作流接口 >>> </xform:simpleDataSource>
											<xform:simpleDataSource value="WR"><span style="text-decoration: line-through">待办任务接口V1.0 </span>(<span style="color: red">不推荐</span>)  >>> </xform:simpleDataSource>
											<xform:simpleDataSource value="TODO">待办任务接口V2.0 (<strong style="color: red;">推荐</strong>)</xform:simpleDataSource>
										</xform:radio>
										<br><br>
										<span color="red" style="color:red">工作流接口:受钉钉工作流的限制,一个实例只能发100个待办,存在待办不消失问题;</span><br>
										<span color="red" style="color:red">待办接口V1.0:无数量限制,但存在待办撤销或删除后无法删除待办问题;(之前在钉钉申请过对应权限的应用可以正常使用，不受影响。现在钉钉应用后台已不能申请该权限了)</span><br>
										<span color="red" style="color:red">待办接口V2.0:解决其他两个接口的问题,功能更齐全推荐使用(<strong>不可随意切换钉钉应用，否则会存在旧应用推送的待办无法更新的情况</strong>);</span><br><br>
										<span color="red" style="color:red">工作流接口升级到待办接口V1.0:待办接口V1.0推送的待办是独立的待办列表展现,将不在钉钉OA审批列表中和特办事项的已发起中展现;</span><br>
										<span color="red" style="color:red">待办接口V1.0升级到待办接口V2.0：历史待办数据继续用旧接口推送,新待办数据通过新接口推送;</span>
										</span>
									</td>
								</tr>
								<tr style="display:none;">
									<td class="td_normal_title" width="15%" style="display:none;">钉钉待办清理时兼容旧接口</td>
									<td style="display:none;">
										<ui:switch id="notifySynchroErrorWF" property="value(notifySynchroErrorWF)"
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
											<span color="red" style="color:red">请确认钉钉上的待办都是从本系统同步过去的（不存在从其他系统同步过去的待办），否则如果开启了本开关，会导致错删钉钉上的待办</span>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.dealWithAllErrNotify" bundle="third-ding"/></td>
									<td>
										<ui:switch id="dealWithAllErrNotify" property="value(dealWithAllErrNotify)"
											enabledText="${lfn:message('third-ding:third.ding.dealWithAllErrNotify.yes')}" disabledText="${lfn:message('third-ding:third.ding.dealWithAllErrNotify.no')}"></ui:switch>
											<span color="red" style="color:red"><bean:message key="third.ding.dealWithAllErrNotify.tip" bundle="third-ding"/></span>
									</td>
								</tr>

							</table>
					</ui:content>
					
					<!-- 通讯录配置 -->
					<ui:content id="tag4" title="${ lfn:message('third-ding:thirdDing.tab.org.setting') }">
						<br/>
						<table id="syncSelection" class="tb_normal" width=100%>
							<tr>
								<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.syncSelection.title" bundle="third-ding"/></td>
								<td>
									<xform:radio  property="value(syncSelection)" alignment="V" onValueChange="syncSelection_display_change(this);">
											<xform:simpleDataSource value="1"><bean:message key="third.ding.config.syncSelection.ekptodd" bundle="third-ding"/></xform:simpleDataSource>
											<xform:simpleDataSource value="2"><bean:message key="third.ding.config.syncSelection.ddtoekp" bundle="third-ding"/></xform:simpleDataSource>
											<xform:simpleDataSource value="3"><bean:message key="third.ding.config.syncSelection.noSync.getPersonrelationFromDD" bundle="third-ding"/></xform:simpleDataSource>
											<xform:simpleDataSource value="4"><bean:message key="third.ding.config.syncSelection.noSync.getPersonrelationFromLD" bundle="third-ding"/></xform:simpleDataSource>
											<xform:simpleDataSource value="0"><bean:message key="third.ding.config.syncSelection.noSync" bundle="third-ding"/></xform:simpleDataSource>
									</xform:radio>
									<div class="message" style="color: red;"><bean:message key="third.ding.config.syncSelection.tip" bundle="third-ding"/></div>
								</td>
							</tr>
						</table>
						<table id="dingOmsOutTable" class="tb_normal" width=100%>
								<%-- <tr id="dingOmsOutTR"  title="${lfn:message('third-ding:third.ding.config.dingOmsOutTRTitle') }">
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingOmsOutEnabledTitle" bundle="third-ding"/></td>
									<td>
										<ui:switch id="dingOmsOutSwitch" property="value(dingOmsOutEnabled)" onValueChange="dingOmsOut_display_change();" 
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</td>
								</tr> --%>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingOrgIdTitle" bundle="third-ding"/></td>
									<td>
										<xform:address propertyId="value(dingOrgId)" propertyName="value(dingOrgName)" 
										mulSelect="true" orgType="ORG_TYPE_ORGORDEPT" showStatus="edit" style="width:85%" onValueChange="wxOrgId_display_change()" 
										subject="${lfn:message('third-ding:third.ding.config.dingOrgIdSubject') }" textarea="true"></xform:address>
										<ui:button text="${lfn:message('third-ding:third.ding.config.dingOrgIdBtn') }" onclick="cleanTime();" style="vertical-align: top;"></ui:button>
										<!-- <span class="txtstrong">*</span> -->
										<div class="message"><bean:message key="third.ding.config.dingOrgIdMsg" bundle="third-ding"/></div>
									</td>
								</tr>
								<tr id="tr_dingOmsRootFlag" >
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingOmsRootFlagTitle" bundle="third-ding"/></td>
									<td>
										<ui:switch property="value(dingOmsRootFlag)" onValueChange="" 
											enabledText="${lfn:message('third-ding:third.ding.config.dingOmsRootFlag.sync') }" disabledText="${lfn:message('third-ding:third.ding.config.dingOmsRootFlag.asyn') }"></ui:switch>
										<div class="message"><bean:message key="third.ding.config.dingOmsRootFlagMsg" bundle="third-ding"/></div>
									</td>
								</tr>
								<c:if test="${ISENABLEDECO=='true'}">
									<tr id="tr_dingOmsExternal" style="display: none;">
										<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingOmsExternal" bundle="third-ding"/></td>
										<td>
											<ui:switch property="value(dingOmsExternal)" onValueChange="" 
												enabledText="${lfn:message('third-ding:third.ding.config.dingOmsRootFlag.sync') }" disabledText="${lfn:message('third-ding:third.ding.config.dingOmsRootFlag.asyn') }"></ui:switch>
											<div class="message" style="color: red;"><bean:message key="third.ding.config.dingOmsExternal.tip" bundle="third-ding"/></div>
										</td>
									</tr>
								</c:if>
								<!-- 专属账号开关 -->
								<tr id="tr_dingDeptid" style="display: none;">
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.exclusiveAccount" bundle="third-ding"/></td>
									<td>
										<ui:switch property="value(exclusiveAccountEnable)" onValueChange="exclusiveAccountEnable_switch()"
												   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
										<div class="message"  id="dingDeptidMsg_omsOut" style="color: red;"><bean:message key="third.ding.config.exclusiveAccount.tip" bundle="third-ding"/></div>
										<div class="message"  id="dingDeptidMsg_mapping" style="color: red;">专属账号开关开启，则优先匹配专属账号，关闭则专属账号当作普通账号匹配</div>
									</td>
								</tr>

								<tr id="tr_dingDeptid">
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingDeptidTitle" bundle="third-ding"/></td>
									<td>
										<xform:text property="value(dingDeptid)" subject="${lfn:message('third-ding:third.ding.config.dingDeptidSubject') }" 
											required="false" style="width:85%" showStatus="edit" />
										<div id="dingDeptidMsg_omsOut" class="message"><bean:message key="third.ding.config.dingDeptidMsg" bundle="third-ding"/></div>
										<div id="dingDeptidMsg_mapping" class="message" style="display:none;">${lfn:message('third-ding:third.ding.config.ding.dept') }</div>
									</td>
								</tr>
								<!-- ekp到钉钉同步 -->
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.person" bundle="third-ding"/></td>
									<td>
										<div>
										<span style="color:red"><bean:message key="third.ding.org.ekp2ding.person.tip" bundle="third-ding"/></span>	
										   
										   <table style="text-align: center" class="tb_normal" width=100% >
										       <tr>
										           <td class="td_normal_title"><bean:message key="third.ding.org.ekp2ding.ding" bundle="third-ding"/></td>
										           <td class="td_normal_title"><bean:message key="third.ding.org.ekp2ding.synWay" bundle="third-ding"/></td>
										           <td class="td_normal_title"><bean:message key="third.ding.org.ekp2ding.from.ekp" bundle="third-ding"/></td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.name" bundle="third-ding"/> <span class="txtstrong">*</span></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.name.synWay)"  className="selectsgl" subject="姓名" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										               <xform:select className="selectsgl" showStatus="view" value="fdName" property="value(org2ding.name)" subject="${lfn:message('third-ding:third.ding.org.ekp2ding.name')}" showPleaseSelect="false" htmlElementProperties="disabled=disabled" style="width:45%;">
														   <xform:simpleDataSource value="fdName"><bean:message key="third.ding.org.ekp2ding.name" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.mobile" bundle="third-ding"/> <span class="txtstrong">*</span></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.mobile.synWay)" className="selectsgl" subject="手机号" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%"  align="left">
										               <xform:select property="value(org2ding.mobile)" showStatus="view" value="fdMobileNo" className="selectsgl" subject="手机号" showPleaseSelect="false" style="width:45%">
														   <xform:simpleDataSource value="fdMobileNo"><bean:message key="third.ding.org.ekp2ding.mobile" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
													   <!-- <span style="color:red">企业内必须唯一</span>	 -->
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.userId" bundle="third-ding"/> <span class="txtstrong">*</span></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.userid.synWay)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										               <xform:select property="value(org2ding.userid)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
														   <xform:simpleDataSource value="fdLoginName"><bean:message key="third.ding.org.ekp2ding.loginName" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="fdId">fdId</xform:simpleDataSource>
													   </xform:select>
													   <span style="color:red"><bean:message key="third.ding.org.ekp2ding.userId.tip" bundle="third-ding"/></span>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.dept" bundle="third-ding"/> <span class="txtstrong">*</span></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.department.synWay)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										               <xform:select property="value(org2ding.department)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
														   <xform:simpleDataSource value="fdDept"><bean:message key="third.ding.org.ekp2ding.dept.sigle" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="fdMuilDept"><bean:message key="third.ding.org.ekp2ding.dept.mutil" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										       </tr>
										       
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.order" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.orderInDepts.synWay)"  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject=""  showStatus="edit" showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										            <div class="sys_property" style="display: block">
										               <xform:select property="value(org2ding.orderInDepts)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
														   <xform:simpleDataSource value="asc"><bean:message key="third.ding.org.ekp2ding.order.asc" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="desc"><bean:message key="third.ding.org.ekp2ding.order.desc" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
													   <span style="color:red"><bean:message key="third.ding.org.ekp2ding.order.tip" bundle="third-ding"/></span>
													</div>   
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.email" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.email.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										            <div class="sys_property" style="display: block">
										               <xform:select property="value(org2ding.email)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
														   <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingPersonCustomData" />
													   </xform:select>
													   </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.post" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.position.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										             <div class="sys_property" style="display: block">
										               <xform:select property="value(org2ding.position)"  onValueChange="checkPositionSyn(this)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
														   <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingPersonCustomData" />
													   </xform:select>
													   <xform:select property="value(org2ding.position.order)"  htmlElementProperties=" id='org2ding_position_order'" className="selectsgl" subject=""  showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value=""><bean:message key="org2ding.position.order.no" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="postOrder"><bean:message key="org2ding.position.order.postOrder" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
													   
													  </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.tel" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.tel.synWay)" className="selectsgl" subject=""  showStatus="edit"  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										            <div class="sys_property" style="display: block">
										               <xform:select property="value(org2ding.tel)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
														   <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingPersonCustomData" />
													   </xform:select>
													   <!-- <span style="color:red">企业内必须唯一</span>	 -->
													   </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.jobNum" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.jobnumber.synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										            <div class="sys_property" style="display: block">
										               <xform:select property="value(org2ding.jobnumber)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
														   <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingPersonCustomData" />
													   </xform:select>
													   </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.workplace" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.workPlace.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"  className="selectsgl" subject=""  showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										            <div class="sys_property" style="display: block">
										               <xform:select property="value(org2ding.workPlace)" subject="" className="selectsgl" showStatus="edit" htmlElementProperties="iscustom='true'"   showPleaseSelect="false" style="width:45%">
													   		<xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingPersonCustomData" />
													   </xform:select>
													   </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.hiredate" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.hiredDate.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject=""  showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										            <div class="sys_property" style="display: block">
										               <xform:select property="value(org2ding.hiredDate)" subject="" className="selectsgl" htmlElementProperties="iscustom='true'" showStatus="edit"  showPleaseSelect="false" style="width:45%">
													 	 <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingPersonCustomData" />
													   </xform:select>
													   </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.remark" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.remark.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject=""  showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										            <div class="sys_property" style="display: block">
										           <xform:select property="value(org2ding.remark)" subject="" className="selectsgl" showStatus="edit" htmlElementProperties="iscustom='true'"  showPleaseSelect="false" style="width:45%">
										               <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingPersonCustomData" />
													</xform:select>
										               </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.orgMail" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.orgEmail.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject=""  showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										            <div class="sys_property" style="display: block">
										               <xform:select property="value(org2ding.orgEmail)" subject="" className="selectsgl" htmlElementProperties="iscustom='true'"  showStatus="edit"  showPleaseSelect="false" style="width:45%">
													   		<xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingPersonCustomData" />
													   </xform:select>
													   <br/>
													   <span style="color:red"><bean:message key="third.ding.org.ekp2ding.orgMail.tip" bundle="third-ding"/></span>	
													</div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.mobile.hide" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.isHide.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject=""  showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										             <div class="sys_property" style="display: block">
										               <%-- <ui:switch property="value(org2ding.isHide.all)" 
																enabledText="全部隐藏" disabledText="全部隐藏"></ui:switch><br/>
													    <xform:select property="value(org2ding.isHide)" subject="" className="selectsgl" showStatus="view" value="isContactPrivate" htmlElementProperties="iscustom='true'"  showPleaseSelect="false" style="width:45%">
										                  <xform:simpleDataSource value="isContactPrivate">隐藏联系方式（员工黄页隐私设置）</xform:simpleDataSource>
													   </xform:select> --%>
													   <xform:radio property="value(org2ding.isHide.all)" showStatus="edit" htmlElementProperties="onclick='switchMobileHide(this.value)'">
															<xform:simpleDataSource value="true" textKey="third.ding.config.mobile.isHide" bundle="third-ding"></xform:simpleDataSource>
															<xform:simpleDataSource value="false" textKey="third.ding.config.ekp2ding.from" bundle="third-ding"></xform:simpleDataSource>
														</xform:radio>
														<div style="display: block" name="org2dingMobileIsHide">
														   <xform:select property="value(org2ding.isHide)" subject="" className="selectsgl" htmlElementProperties="iscustom='true'"  showPleaseSelect="false" style="width:45%">
											                  <xform:simpleDataSource value="isContactPrivate"><bean:message key="third.ding.org.ekp2ding.mobile.from.zone" bundle="third-ding"/></xform:simpleDataSource>
											                  <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingPersonCustomData" />
														   </xform:select> 
														    <p style="color:red"><bean:message key="third.ding.org.ekp2ding.mobile.hide.tip" bundle="third-ding"/>
														    <a href="${LUI_ContextPath }/sys/zone/sys_zone_private_config/sysZonePrivateConfig.do?method=edit&modelName=com.landray.kmss.sys.zone.model.SysZonePrivateConfig&s_path=应用配置%E3%80%80>%E3%80%80员工黄页%E3%80%80>%E3%80%80隐私设置&s_css=default"
														       target="_blank"
											                   style="color: blue"
														    ><bean:message key="third.ding.org.ekp2ding.mobile.zone" bundle="third-ding"/></a></p>	
														</div>
														
													   
													  </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.isSenior" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.isSenior.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										            <div class="sys_property" style="display: block">
										               <xform:select property="value(org2ding.isSenior)" subject="" className="selectsgl" showStatus="edit" htmlElementProperties="iscustom='true'"  showPleaseSelect="false" style="width:45%">
										                  <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingPersonCustomData" />
													   </xform:select>
													   <span style="color:red"><bean:message key="third.ding.org.ekp2ding.isSenior.tip" bundle="third-ding"/></span>	
													  </div> 
										           </td>
										       </tr>
											   <!-- 专属账号配置 -->
											   <tr name="exclusiveAccountEnable_item" style="display: none">
												   <td class="td_normal_title" width="15%"><bean:message key="third.ding.config.isExclusiveAccount" bundle="third-ding"/></td>
												   <td width="25%">
													   <xform:select property="value(org2ding.isExclusiveAccount.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
												   </td>
												   <td width="55%" align="left">
													   <div class="sys_property" style="display: block">
														   <xform:radio property="value(org2ding.isExclusiveAccount.all)" showStatus="edit" htmlElementProperties="onclick='swithExclusiveAccount(this.value)'">
															   <xform:simpleDataSource value="true" textKey="third.ding.config.isExclusiveAccount.all" bundle="third-ding"></xform:simpleDataSource>
															   <xform:simpleDataSource value="false" textKey="third.ding.config.ekp2ding.from" bundle="third-ding"></xform:simpleDataSource>
														   </xform:radio>
														   <div style="display: block" name="org2dingIsExclusiveAccount">
															   <xform:select property="value(org2ding.isExclusiveAccount)" subject="" className="selectsgl" htmlElementProperties="iscustom='true'"  showPleaseSelect="false" style="width:45%">
																   <xform:simpleDataSource value="isContactPrivate"><bean:message key="third.ding.org.ekp2ding.mobile.from.zone" bundle="third-ding"/></xform:simpleDataSource>
																   <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingPersonCustomData" />
															   </xform:select>
															   <p style="color:red"><bean:message key="third.ding.config.isExclusiveAccount.syn.tip" bundle="third-ding"/></p>
														   </div>
													   </div>
												   </td>
											   </tr>
											   <!--专属账号类型-->
											   <tr name="exclusiveAccountEnable_item" style="display: none">
												   <td class="td_normal_title" width="15%"><bean:message key="third.ding.config.exclusiveAccount.type" bundle="third-ding"/></td>
												   <td width="25%">
													   <xform:select property="value(org2ding.exclusiveAccount.type.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
												   </td>
												   <td width="55%" align="left">
													   <div class="sys_property" style="display: block">
														   <xform:radio property="value(org2ding.exclusiveAccount.type)" showStatus="edit" htmlElementProperties="onclick='switchexclusiveAccountType(this.value)'">
															   <xform:simpleDataSource value="sso" textKey="third.ding.config.exclusiveAccount.type.sso" bundle="third-ding"></xform:simpleDataSource>
															   <xform:simpleDataSource value="dingtalk" textKey="third.ding.config.exclusiveAccount.type.dingtalk" bundle="third-ding"></xform:simpleDataSource>
														   </xform:radio>
														   <p style="color:red"><bean:message key="third.ding.config.exclusiveAccount.type.tip" bundle="third-ding"/></p>
													   </div>
												   </td>
											   </tr>

											   <!--专属账号登录名-->
											   <tr name="exclusiveAccountEnable_item_dingtalk" style="display: none">
												   <td class="td_normal_title" width="15%"><bean:message key="third.ding.config.exclusiveAccount.loginName" bundle="third-ding"/></td>
												   <td width="25%">
													   <xform:select property="value(org2ding.exclusiveAccount.loginName.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
												   </td>
												   <td width="55%" align="left">
													   <div class="sys_property" style="display: block">
														   <xform:select property="value(org2ding.exclusiveAccount.loginName)" subject="" className="selectsgl" showStatus="edit" htmlElementProperties="iscustom='true'"  showPleaseSelect="false" style="width:45%">
															   <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ExclusiveAccountCustomData" />
														   </xform:select>
														   <p style="color:red"><bean:message key="third.ding.config.exclusiveAccount.loginName.tip" bundle="third-ding"/></p>
													   </div>
												   </td>
											   </tr>

											   <!--专属账号初始密码-->
											   <tr name="exclusiveAccountEnable_item_dingtalk" style="display: none">
												   <td class="td_normal_title" width="15%"><bean:message key="third.ding.config.exclusiveAccount.password" bundle="third-ding"/></td>
												   <td width="25%">
													   <xform:select property="value(org2ding.exclusiveAccount.password.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
												   </td>
												   <td width="55%" align="left">
													   <div class="sys_property">
														   <xform:text property="value(org2ding.exclusiveAccount.password)" subject="${lfn:message('third-ding:third.ding.config.exclusiveAccount.password') }"
																	   required="true" style="width:35%" showStatus="edit" />
														   <p style="color:red"><bean:message key="third.ding.config.exclusiveAccount.password.tip" bundle="third-ding"/></p>
													   </div>
												   </td>
											   </tr>

											   <!-- 钉钉自定义字段 -->
											   <tr >
												   <td colspan="3">
													   <div width=100%>
														   <table id="org2ding_custom" style="text-align: center; margin: 0px 0px;" class="tb_normal" width=100% >
															   <tr KMSS_IsReferRow="1" style="display:none">
																   <td width="15%" align="center" class="td_normal_title custom_width_15">
																	   <xform:select property="value(org2ding.custom.[!{index}].title)" subject="" className="selectsgl" showStatus="edit" required="true" htmlElementProperties="att='customField'" showPleaseSelect="true" style="width:45%">
																		   <xform:customizeDataSource className="com.landray.kmss.third.ding.util.DingCustomData" />
																	   </xform:select>
																   </td>
																   <td class="custom_width_25">
																	   <xform:select property="value(org2ding.custom.[!{index}].synWay)" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
																		   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
																		   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
																	   </xform:select>
																   </td>

																   <td class="custom_width_55" align="left">
																	   <div class="sys_property" style="display: block">
																		   <xform:select property="value(org2ding.custom.[!{index}].target)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
																			   <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingPersonCustomData" />
																		   </xform:select>

																		   <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
																			   <span style="margin-top: 2px;padding-left: 30px"><img src="${KMSS_Parameter_StylePath}icons/icon_del.png" style="margin-top: 5px;" border="0" /> </span>${lfn:message('third-ding:third.ding.delete')}
																		   </a>
																	   </div>
																   </td>
															   </tr>
															   <c:forEach items="${fd_constom_List}" var="fdDetail_FormItem" varStatus="vstatus">
																   <tr KMSS_IsContentRow="1">
																	   <td  align="center" class="td_normal_title" width="100px" style="width: 14.6%">
																		   <xform:select property="value(org2ding.custom.[${vstatus.index}].title)" value="${fdDetail_FormItem.title}" subject="" htmlElementProperties="att='customField'" className="selectsgl" showStatus="edit"  required="true"  showPleaseSelect="true" style="width:45%">
																			   <xform:customizeDataSource className="com.landray.kmss.third.ding.util.DingCustomData" />
																		   </xform:select>
																	   </td>
																	   <td width="25%">
																		   <xform:select property="value(org2ding.custom.[${vstatus.index}].synWay)" value="${fdDetail_FormItem.synWay}" className="selectsgl" subject=""  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit"  showPleaseSelect="false">
																			   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
																			   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
																		   </xform:select>
																	   </td>

																	   <td width="55%" align="left">
																		   <span class="sys_property" style="display: block">
																			   <xform:select property="value(org2ding.custom.[${vstatus.index}].target)"  value="${fdDetail_FormItem.target}" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
																				   <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingPersonCustomData" />
																			   </xform:select>
																			    <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
																				   <span style="margin-top: 2px;padding-left: 30px"><img src="${KMSS_Parameter_StylePath}icons/icon_del.png" style="margin-top: 5px;" border="0" /> </span> ${lfn:message('third-ding:third.ding.delete')}
																			    </a>
																		   </span>

																	   </td>
																   </tr>

															   </c:forEach>
															   <tr>
																   <td colspan="3" >
																	   <div style="text-align: left ">
																		   <ui:button text="+${lfn:message('third-ding:third.ding.config.field.customize.add')}" onclick="DocList_AddRow();" style="vertical-align: top;"></ui:button>
																	   </div>
																   </td>
															   </tr>
														   </table>
														   <input type="hidden" name="fdDetail_Flag" value="1">
														   <script>
															   Com_IncludeFile("doclist.js");
														   </script>
														   <script>
															   DocList_Info.push('org2ding_custom');
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
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.dept.source" bundle="third-ding"/></td>
									<td>
										<div>
										<span style="color:red"><bean:message key="third.ding.org.ekp2ding.dept.source.tip" bundle="third-ding"/></span>	
										   
										   <table style="text-align: center" class="tb_normal" width=100% >
										       <tr>
										           <td class="td_normal_title"><bean:message key="third.ding.org.ekp2ding.ding" bundle="third-ding"/></td>
										           <td class="td_normal_title"><bean:message key="third.ding.org.ekp2ding.synWay" bundle="third-ding"/></td>
										           <td class="td_normal_title"><bean:message key="third.ding.org.ekp2ding.from.ekp" bundle="third-ding"/></td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.dept.name" bundle="third-ding"/> <span class="txtstrong">*</span></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.dept.name.synWay)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										               <xform:select className="selectsgl" property="value(org2ding.dept.name)" subject=""  value="fdName" showStatus="view"   showPleaseSelect="false" htmlElementProperties="disabled=disabled" style="width:45%;">
														   <xform:simpleDataSource value="fdName"><bean:message key="third.ding.org.ekp2ding.dept.name" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.dept.parent" bundle="third-ding"/><span class="txtstrong">*</span></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.dept.parentDept.synWay)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										               <xform:select className="selectsgl" property="value(org2ding.dept.parentDept)" subject="" value="fdParentDept" showStatus="view"  showPleaseSelect="false" htmlElementProperties="disabled=disabled" style="width:45%;">
														   <xform:simpleDataSource value="fdParentDept"><bean:message key="third.ding.org.ekp2ding.dept.parent" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										       </tr>
										       <%-- <tr>
										           <td class="td_normal_title" width="15%">部门Id <span class="txtstrong">*</span></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.dept.id.synWay)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="addSyn">仅新增时同步</xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										               <xform:select className="selectsgl" property="value(org2ding.dept.id)" subject="" value="fdId" showStatus="view" showPleaseSelect="false" htmlElementProperties="disabled=disabled" style="width:45%;">
														   <xform:simpleDataSource value="fdId">fdId</xform:simpleDataSource>
													   </xform:select>
										           </td>
										       </tr> --%>
										       
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.dept.order" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.dept.order.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
                                                           <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													 </xform:select>
										           </td>
										           <td width="55%" align="left">
										            <div class="sys_property" style="display: block">
										               <xform:select className="selectsgl" property="value(org2ding.dept.order)" subject="" showPleaseSelect="false"  style="width:45%;">
														  <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDeptCustomData" />
													   </xform:select>
													   <span style="color:red"><bean:message key="third.ding.org.ekp2ding.dept.order.tip" bundle="third-ding"/></span>	
													   </div>
										           </td>
										       </tr>
										        <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.dept.deptManager" bundle="third-ding"/> </td>
										           <td width="25%">
										             <xform:select property="value(org2ding.dept.deptManager.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
                                                           <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													 </xform:select>
										           </td>
										           <td width="55%" align="left">
										            <div class="sys_property" style="display: block">
										               <xform:select className="selectsgl" property="value(org2ding.dept.deptManager)" subject="" value="hbmThisLeader" showStatus="view"  showPleaseSelect="false" style="width:45%;">
														  <xform:simpleDataSource value="hbmThisLeader"><bean:message key="third.ding.org.ekp2ding.dept.leader" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
													   </div>
										           </td>
										       </tr>
										        <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.dept.group" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.dept.group.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
                                                           <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													 </xform:select>
										           </td>
										           <td width="55%" align="left">
										            <div class="sys_property" style="display: block">
														   <xform:radio property="value(org2ding.dept.group.all)" showStatus="edit" htmlElementProperties="onclick='switchGroupHide(this.value)'">
															<xform:simpleDataSource value="true" textKey="third.ding.config.group.all" bundle="third-ding"></xform:simpleDataSource>
															<xform:simpleDataSource value="false" textKey="third.ding.config.ekp2ding.from" bundle="third-ding"></xform:simpleDataSource>
														</xform:radio>
														<div style="display: block" name="org2dingGroupCreate">
														   <xform:select property="value(org2ding.dept.group)" subject="" className="selectsgl" htmlElementProperties="iscustom='true'"  showPleaseSelect="false" style="width:45%">
											                  <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDeptCustomData" />
														   </xform:select> 
														</div>
													 </div>
										           </td>
										       </tr>
										       
										       <!-- (部门群)包含子部门成员 -->
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.dept.groupContainSubDept" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(org2ding.dept.groupContainSubDept.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
                                                           <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ekp2ding.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													 </xform:select>
										           </td>
										           <td width="55%" align="left">
										            <div class="sys_property" style="display: block">
														 <xform:radio property="value(org2ding.dept.groupContainSubDept.all)" showStatus="edit" htmlElementProperties="onclick='switchGroupContainSubDeptHide(this.value)'">
															<xform:simpleDataSource value="true" textKey="third.ding.config.group.contains.all" bundle="third-ding"></xform:simpleDataSource>
															<xform:simpleDataSource value="false" textKey="third.ding.config.ekp2ding.from" bundle="third-ding"></xform:simpleDataSource>
														</xform:radio>
														<div style="display: block" name="groupContainSubDeptProps">
														   <xform:select property="value(org2ding.dept.groupContainSubDept)" subject="" className="selectsgl" htmlElementProperties="iscustom='true'"  showPleaseSelect="false" style="width:45%">
											                  <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDeptCustomData" />
														   </xform:select> 
														</div>
													 </div>
										           </td>
										       </tr>
											   <!-- 部门人员查看通讯录范围 -->
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.dept.dingRange" bundle="third-ding"/> </td>
										           <td width="25%">
										             <xform:select property="value(dingRangeEnabled)" htmlElementProperties="type='synWay'" onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false" style="width:36%;">
                                                           <xform:simpleDataSource value="false"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="true"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
													 </xform:select>
										           </td>
										           <td width="55%" align="left">
										           	   <div class="sys_property" style="display: block">
										           	   		<bean:message key="third.ding.org.ekp2ding.dept.dingRange.desc" bundle="third-ding"/>
													   </div>
										           </td>
										       </tr>
											<!--部门隐藏同步设置-->
											   <tr>
												   <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ekp2ding.dept.dingHideRange" bundle="third-ding"/> </td>
												   <td width="25%">
													   <xform:select property="value(dingHideRangeEnabled)" htmlElementProperties="type='synWay'" onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false" style="width:36%;">
														   <xform:simpleDataSource value="false"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="true"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
												   </td>
												   <td width="55%" align="left">
													   <div class="sys_property" style="display: block">
														   <bean:message key="third.ding.org.ekp2ding.dept.dingHideRange.desc" bundle="third-ding"/>
													   </div>
												   </td>
											   </tr>
										   </table>
										</div>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.oms.person1" bundle="third-ding"/></td>
									<td>
										<xform:text property="value(dingSize)" showStatus="edit" validators="number"/>
										<div class="message"><bean:message key="third.ding.oms.person.tip" bundle="third-ding"/></div>
									</td>
								</tr>
								<!-- 钉钉人员手机号变更监听 -->
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingMobileEnabled" bundle="third-ding"/></td>
									<td>
										<ui:switch property="value(dingMobileEnabled)"
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
										<div class="txtstrong"><bean:message key="third.ding.config.dingMobileEnabled.tip" bundle="third-ding"/></div>
									</td>
								</tr>

								<!-- 钉钉人员退出企业监听 -->
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.person.leave.listener" bundle="third-ding"/></td>
									<td>
										<ui:switch property="value(userLeaveListenerEnable)"
												   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
										<div class="txtstrong"><bean:message key="third.ding.config.person.leave.listener.tip" bundle="third-ding"/></div>
									</td>
								</tr>
							</table>
							<!-- <br/> -->
							<div>
							<table id="dingOmsInTable" class="tb_normal" width=100% >
								<%-- <tr id="dingOmsInTR"  title="${lfn:message('third-ding:third.ding.config.dingOmsInTRTitle') }">
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingOmsInEnabledTitle" bundle="third-ding"/></td>
									<td>
										<ui:switch id="dingOmsInSwitch" property="value(dingOmsInEnabled)" onValueChange="dingOmsIn_display_change();" 
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</td>
								</tr> --%>
								<%-- <tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingOmsInDeptEnabledTitle" bundle="third-ding"/></td>
									<td>
										<ui:switch property="value(dingOmsInDeptEnabled)" onValueChange="" 
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</td>
								</tr> --%>
								<!-- 钉钉根机构 -->
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingOrgIdToEkpTitle" bundle="third-ding"/></td>
									<td>
										<xform:text property="value(dingOrgId2ekp)" subject="${lfn:message('third-ding:third.ding.config.dingOrgIdToEkpTitle') }" 
											required="false" style="width:85%" showStatus="edit" />
										<!-- <span class="txtstrong">*</span> -->
										<div class="message"><bean:message key="third.ding.config.dingOrgIdToEkpTitle.tip" bundle="third-ding"/></div>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingInOrgIdTitle" bundle="third-ding"/></td>
									<td>
										<xform:text property="value(dingInOrgId)" subject="${lfn:message('third-ding:third.ding.config.dingInOrgIdSubject') }" 
											required="false" style="width:85%" showStatus="edit" />
										<!-- <span class="txtstrong">*</span> -->
										<div class="message"><bean:message key="third.ding.config.dingInOrgIdMsg" bundle="third-ding"/></div>
									</td>
								</tr>
								<!-- 钉钉组织接入ekp，ekp多余的架构信息处理 -->
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.ding2ekpOrgHandle" bundle="third-ding"/></td>
									<td>
										 <xform:radio  property="value(ding.ekpOrgHandle)">
											<xform:simpleDataSource value="noHandle"><bean:message key="third.ding.config.ding2ekpOrgHandle.no" bundle="third-ding"/></xform:simpleDataSource>
											<xform:simpleDataSource value="autoDisable"><bean:message key="third.ding.config.ding2ekpOrgHandle.autoDisable" bundle="third-ding"/></xform:simpleDataSource>
									     </xform:radio>
										<div class="message"><bean:message key="third.ding.config.ding2ekpOrgHandle.tip" bundle="third-ding"/></div>
									</td>
								</tr>
								
								<!-- 是否同步关联外部组织通讯录：默认同步 -->
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingOmsInAssociatedExternalEnabledTitle" bundle="third-ding"/></td>
									<td>
										<ui:switch property="value(dingOmsInAssociatedExternalEnabled)" onValueChange="" checked="true"
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
											<bean:message key="third.ding.config.dingOmsInAssociatedExternalEnabled.tip" bundle="third-ding"/>
									</td>
								</tr>
								
								<!-- 是否同步管理员 -->
								<tr  style="display:none;">
									<td class="td_normal_title" width="15%">是否同步管理员</td>
									<td >
										<ui:switch property="value(dingAdminSynEnabled)" onValueChange="ding_adminSynEnabled_change();"   checked="false"
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
											开启此开关，则同步完组织架构之后，会同步钉钉管理员到系统“系统级_子管理员”的群组里。
									</td>
								</tr>
								<tr id="ding_admin_groupId"  style="display:none;">
									<td class="td_normal_title" width="15%">管理员群组fdId</td>
									<td>
										<xform:text property="value(dingAdminGroupId)" subject="高级审批管理门户地址" 
											required="false" style="width:85%" showStatus="edit" />
										<div class="message">选填；为空则默认以名称为“系统级_子管理员”的群组作为钉钉管理员的群组；不为空时，钉钉管理员的群组则以该配置fdId为准。</div>
									</td>
								</tr>
								<%-- <!-- 是否同步部门主管 -->
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingOmsInDeptManagerEnabledTitle" bundle="third-ding"/></td>
									<td>
										<ui:switch property="value(dingOmsInDeptManagerEnabled)" onValueChange="" 
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
											<bean:message key="third.ding.config.dingOmsInDeptManagerEnabled.tip" bundle="third-ding"/>
									</td>
								</tr>
								<!-- 是否同步人员多部门 -->
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.config.dingOmsInMoreDeptEnabledTitle" bundle="third-ding"/></td>
									<td>
										<ui:switch property="value(dingOmsInMoreDeptEnabled)" onValueChange="" 
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
											<bean:message key="third.ding.config.dingOmsInMoreDeptEnabled.tip" bundle="third-ding"/>
									</td>
								</tr> --%>
								<!-- 钉钉到ekp同步配置 -->
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.person" bundle="third-ding"/></td>
									<td>
										<div>
										<span style="color:red"><bean:message key="third.ding.org.ding2ekp.person.tip.1" bundle="third-ding"/></span><br/>	
										 <span style="color:red"><bean:message key="third.ding.org.ding2ekp.person.tip.2" bundle="third-ding"/></span>	  
										   <table style="text-align: center" class="tb_normal" width=100% >
										       <tr>
										           <td class="td_normal_title"><bean:message key="third.ding.org.ding2ekp.ekp" bundle="third-ding"/></td>
										           <td class="td_normal_title"><bean:message key="third.ding.org.ding2ekp.synWay" bundle="third-ding"/></td>
										           <td class="td_normal_title"><bean:message key="third.ding.org.ding2ekp.from.ding" bundle="third-ding"/></td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.person.name" bundle="third-ding"/> <span class="txtstrong">*</span></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.name.synWay)"  className="selectsgl" subject="姓名" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										               <xform:select property="value(ding2ekp.name)" className="selectsgl" showStatus="edit" style="width:45%" showPleaseSelect="false">
							                                <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDing2ekpCustomData" />
							                           </xform:select>
										           </td>
										       </tr>
										      <%--  <tr>
										           <td class="td_normal_title" width="15%">员工Id <span class="txtstrong">*</span></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.userid.synWay)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="addSyn">仅新增时同步</xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										               <xform:select property="value(ding2ekp.userid)" subject="" className="selectsgl" value="userId"  showStatus="view"  showPleaseSelect="false" style="width:45%">
														   <xform:simpleDataSource value="userId">员工UserId</xform:simpleDataSource>
													   </xform:select>
													   <!-- <span style="color:red">企业内必须唯一。仅新建时同步</span> -->
										           </td>
										       </tr> --%>
										      
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.person.loginName" bundle="third-ding"/> <span class="txtstrong">*</span></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.loginName.synWay)" className="selectsgl" subject=""  showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										                 <xform:select property="value(ding2ekp.loginName)"  className="selectsgl" subject="" style="width:45%"  showStatus="edit"  showPleaseSelect="false">
							                                <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDing2ekpCustomData" />
							                             </xform:select>
							                             <br/>
													   <span style="color:red"><bean:message key="third.ding.org.ding2ekp.person.loginName.tip" bundle="third-ding"/></span>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.person.dept" bundle="third-ding"/><span class="txtstrong">*</span></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.department.synWay)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										               <xform:select property="value(ding2ekp.department)" subject="" className="selectsgl" showStatus="edit"  showPleaseSelect="false" style="width:45%">
														   <xform:simpleDataSource value="singleDept"><bean:message key="third.ding.org.ding2ekp.person.sigleDept" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="multDept"><bean:message key="third.ding.org.ding2ekp.person.multDept" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										       </tr>
										        <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.person.mobile" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.mobile.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="手机号" showStatus="edit"  showPleaseSelect="false">
										                   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ding2ekp.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%"  align="left">
										           <div class="sys_property" style="display: block">
										               <xform:select property="value(ding2ekp.mobile)" showStatus="view" value="fdMobileNo" className="selectsgl" subject="手机号" showPleaseSelect="false" style="width:45%">
														   <xform:simpleDataSource value="fdMobileNo"><bean:message key="third.ding.org.ding2ekp.person.mobile" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
													   <span style="color:red;padding-left: 20%"><bean:message key="third.ding.org.ding2ekp.person.mobile.tip" bundle="third-ding"/></span>
													   </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.person.nickName" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.fdNickName.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject=""  showStatus="edit" showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ding2ekp.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										           <div class="sys_property" style="display: block">
													   <xform:select property="value(ding2ekp.fdNickName)"  className="selectsgl" subject="" style="width:45%"  showStatus="edit"  showPleaseSelect="false">
							                                 <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDing2ekpCustomData" />
							                             </xform:select>
							                             </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.person.fdNo" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.fdNo.synWay)" className="selectsgl" subject="" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" showStatus="edit" showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ding2ekp.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										              <div class="sys_property" style="display: block">
											                <xform:select property="value(ding2ekp.fdNo)"  className="selectsgl" subject="" style="width:45%"  showStatus="edit"  showPleaseSelect="false">
								                                <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDing2ekpCustomData" />
								                             </xform:select>
								                             <br>
														   <span style="color:red"><bean:message key="third.ding.org.ding2ekp.person.fdNo.tip1" bundle="third-ding"/>
												             <a href="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.organization.model.SysOrganizationConfig&s_path=组织权限管理%E3%80%80>%E3%80%80基础设置%E3%80%80>%E3%80%80参数配置%E3%80%80>%E3%80%80组织开关配置&s_css=default"
												                target="_blank"
												                style="color:blue"
												             > <bean:message key="third.ding.org.ding2ekp.person.fdNo.tip2" bundle="third-ding"/></a><bean:message key="third.ding.org.ding2ekp.person.fdNo.tip3" bundle="third-ding"/>
														   </span>
													   </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.person.email" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.email.synWay)" className="selectsgl" subject="" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"  showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ding2ekp.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										              <div class="sys_property" style="display: block">
											              <xform:select property="value(ding2ekp.email)"  className="selectsgl" subject="" style="width:45%"  showStatus="edit"  showPleaseSelect="false">
								                                 <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDing2ekpCustomData" />
								                             </xform:select>
								                     </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.person.tel" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.tel.synWay)" className="selectsgl" subject="" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"  showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ding2ekp.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										             <div class="sys_property" style="display: block">
											              <xform:select property="value(ding2ekp.tel)"  className="selectsgl" subject="" style="width:45%"  showStatus="edit"  showPleaseSelect="false">
								                                 <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDing2ekpCustomData" />
								                          </xform:select>
								                      </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.person.lang" bundle="third-ding"/></td>
										           <td width="25%">
										               <xform:select property="value(ding2ekp.defLang.synWay)" className="selectsgl" subject="" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"   showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ding2ekp.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										           <div class="sys_property" style="display: block">
										              <xform:select property="value(ding2ekp.defLang)"  className="selectsgl" subject="" style="width:45%"  showStatus="edit"  showPleaseSelect="false">
							                                 <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDing2ekpCustomData" />
							                          </xform:select>
							                          <span style="color:red;"><bean:message key="third.ding.org.ding2ekp.person.lang.tip" bundle="third-ding"/></span>
							                          </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.person.keyword" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.keyword.synWay)" className="selectsgl" subject="" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"   showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ding2ekp.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										           <div class="sys_property" style="display: block">
										               <xform:select property="value(ding2ekp.keyword)"  className="selectsgl" subject="" style="width:45%"  showStatus="edit"  showPleaseSelect="false">
							                                 <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDing2ekpCustomData" />
							                          </xform:select>
							                          </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.person.order" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.order.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject=""  showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ding2ekp.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										           <div class="sys_property" style="display: block">
										               <xform:select property="value(ding2ekp.order)"  className="selectsgl" subject="" style="width:45%"  showStatus="edit"  showPleaseSelect="false">
							                                 <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDing2ekpCustomData" />
							                          </xform:select>
							                          </div>
										           </td>
										       </tr>
										       <%-- <tr>
										           <td class="td_normal_title" width="15%">职务</td>
										           <td width="25%">
										             <xform:select property="value(org2ding.hiredDate.synWay)" className="selectsgl" subject=""  showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn">不同步</xform:simpleDataSource>
														   <xform:simpleDataSource value="syn">同步</xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn">仅新增时同步</xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										               <xform:select property="value(org2ding.hiredDate)" subject="" className="selectsgl" htmlElementProperties="iscustom='true'" showStatus="edit"  showPleaseSelect="false" style="width:45%">
													 	 <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingPersonCustomData" />
													   </xform:select>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%">岗位</td>
										           <td width="25%">
										             <xform:select property="value(org2ding.remark.synWay)" className="selectsgl" subject=""  showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn">不同步</xform:simpleDataSource>
														   <xform:simpleDataSource value="syn">同步</xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn">仅新增时同步</xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										           <xform:select property="value(org2ding.remark)" subject="" className="selectsgl" showStatus="edit" htmlElementProperties="iscustom='true'"  showPleaseSelect="false" style="width:45%">
										               <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingPersonCustomData" />
													</xform:select>
										               
										           </td>
										       </tr> --%>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.person.sex" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.sex.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"  className="selectsgl" subject=""  showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ding2ekp.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										           <div class="sys_property" style="display: block">
										                <xform:select property="value(ding2ekp.sex)"  className="selectsgl" subject="" style="width:45%"  showStatus="edit"  showPleaseSelect="false">
							                                 <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDing2ekpCustomData" />
							                          </xform:select>
							                          
							                           <span style="color:red;"><bean:message key="third.ding.org.ding2ekp.person.sex.tip" bundle="third-ding"/></span>
							                          </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.person.shortNo" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.fdShortNo.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"  className="selectsgl" subject=""  showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ding2ekp.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										           <div class="sys_property" style="display: block">
										               <xform:select property="value(ding2ekp.fdShortNo)"  className="selectsgl" subject="" style="width:45%"  showStatus="edit"  showPleaseSelect="false">
							                                 <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDing2ekpCustomData" />
							                          </xform:select>
							                          </div>
													   
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.person.isBusiness" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.fdIsBusiness.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"  className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ding2ekp.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										           <div class="sys_property" style="display: block">
										               <xform:select property="value(ding2ekp.fdIsBusiness)"  className="selectsgl" subject="" style="width:45%"  showStatus="edit"  showPleaseSelect="false">
							                                 <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDing2ekpCustomData" />
							                          </xform:select>
							                          </div>
													   
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.person.fdMemo" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.fdMemo.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"  className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ding2ekp.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										           <div class="sys_property" style="display: block">
										                <xform:select property="value(ding2ekp.fdMemo)"  className="selectsgl" subject="" style="width:45%"  showStatus="edit"  showPleaseSelect="false">
							                                 <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDing2ekpCustomData" />
							                          </xform:select>
							                          </div>
													   
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.person.password" bundle="third-ding"/></td>
										           <td colspan="2" align="left">
										             <span><bean:message key="third.ding.org.ding2ekp.person.password.tip1" bundle="third-ding"/>
											             <a href="${LUI_ContextPath }/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.organization.model.SysOrgDefaultConfig&s_path=组织权限管理%E3%80%80>%E3%80%80基础设置%E3%80%80>%E3%80%80参数配置%E3%80%80>%E3%80%80默认值设置&s_css=default"
											                target="_blank"
											                style="color: blue"
											             ><bean:message key="third.ding.org.ding2ekp.person.password.tip2" bundle="third-ding"/></a></span>
										           </td>
										           
										       </tr>
										       
										   </table>
										</div>
									</td>
								</tr>
								
								<!-- 部门同步字段 -->
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.dept" bundle="third-ding"/></td>
									<td>
										<div>
										<span style="color:red"><bean:message key="third.ding.org.ding2ekp.dept.tip" bundle="third-ding"/></span>	
										   
										   <table style="text-align: center" class="tb_normal" width=100% >
										       <tr>
										           <td class="td_normal_title"><bean:message key="third.ding.org.ding2ekp.ekp" bundle="third-ding"/></td>
										           <td class="td_normal_title"><bean:message key="third.ding.org.ding2ekp.synWay" bundle="third-ding"/></td>
										           <td class="td_normal_title"><bean:message key="third.ding.org.ding2ekp.from.ding" bundle="third-ding"/></td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.dept.name" bundle="third-ding"/> <span class="txtstrong">*</span></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.dept.name.synWay)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										               <xform:select className="selectsgl" property="value(ding2ekp.dept.name" subject=""  value="name" showStatus="view"   showPleaseSelect="false" htmlElementProperties="disabled=disabled" style="width:45%;">
														  <xform:simpleDataSource value="name"><bean:message key="third.ding.org.ding2ekp.dept.name" bundle="third-ding"/> </xform:simpleDataSource>
													   </xform:select>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.dept.parent" bundle="third-ding"/><span class="txtstrong">*</span></td>
										           <td width="25%">
										            <xform:select property="value(ding2ekp.dept.parentDept.synWay)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													 </xform:select>
										           </td>
										           <td width="55%" align="left">
										               <xform:select className="selectsgl" property="value(ding2ekp.dept.parentDept)" subject="" value="parenteDept" showStatus="view"  showPleaseSelect="false" htmlElementProperties="disabled=disabled" style="width:45%;">
														   <xform:simpleDataSource value="parenteDept"><bean:message key="third.ding.org.ding2ekp.dept.parent" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										       </tr>
										      <%--  <tr>
										           <td class="td_normal_title" width="15%">部门Id <span class="txtstrong">*</span></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.dept.id.synWay)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="addSyn">仅新增时同步</xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										               <xform:select className="selectsgl" property="value(ding2ekp.dept.id)" subject="" value="fdId" showStatus="view" showPleaseSelect="false" htmlElementProperties="disabled=disabled" style="width:45%;">
														   <xform:simpleDataSource value="fdId">fdId</xform:simpleDataSource>
													   </xform:select>
										           </td>
										       </tr>  --%>
										       
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.dept.order" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.dept.order.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"  className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
                                                           <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ding2ekp.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													 </xform:select>
										           </td>
										           <td width="55%" align="left">
										           <div class="sys_property" style="display: block">
										              <xform:select className="selectsgl" property="value(ding2ekp.dept.order)" subject="" showStatus="view" value="order"  showPleaseSelect="false" style="width:45%;">
														  <xform:simpleDataSource value="order"><bean:message key="third.ding.org.ding2ekp.dept.order" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										              <%-- <xform:select className="selectsgl" property="value(ding2ekp.dept.order)" subject=""  showPleaseSelect="false" style="width:45%;">
														  <xform:enumsDataSource enumsType="third_ding_ding2ekp_dept"></xform:enumsDataSource>
													   </xform:select> --%>
												   </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%"><bean:message key="third.ding.org.ding2ekp.dept.manger" bundle="third-ding"/></td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.dept.leader.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"  className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
                                                           <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ding2ekp.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ding2ekp.syn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn"><bean:message key="third.ding.org.ding2ekp.addSyn" bundle="third-ding"/></xform:simpleDataSource>
													 </xform:select>
										           </td>
										           <td width="55%" align="left">
										           <div class="sys_property" style="display: block">
										               <xform:select className="selectsgl" property="value(ding2ekp.dept.leader)" subject="" showStatus="view" value="leader"  showPleaseSelect="false"  style="width:45%;">
														  <xform:simpleDataSource value="leader"><bean:message key="third.ding.org.ding2ekp.dept.leader" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
													   </div>
										           </td>
										       </tr>
										       <%--  <tr>
										           <td class="td_normal_title" width="15%">编号 </td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.dept.fdNo.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"  className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
                                                           <xform:simpleDataSource value="noSyn">不同步</xform:simpleDataSource>
														   <xform:simpleDataSource value="syn">同步</xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn">仅新增时同步</xform:simpleDataSource>
													 </xform:select>
										           </td>
										           <td width="55%" align="left">
										           <div class="sys_property" style="display: block">
										               <xform:select className="selectsgl" property="value(ding2ekp.dept.fdNo)" subject=""  showPleaseSelect="false" style="width:45%;">
														  <xform:enumsDataSource enumsType="third_ding_ding2ekp_dept"></xform:enumsDataSource>
													   </xform:select>
													   </div>
										           </td>
										       </tr>
										        <tr>
										           <td class="td_normal_title" width="15%">关键字</td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.dept.keyword.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"  className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
                                                           <xform:simpleDataSource value="noSyn">不同步</xform:simpleDataSource>
														   <xform:simpleDataSource value="syn">同步</xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn">仅新增时同步</xform:simpleDataSource>
													 </xform:select>
										           </td>
										           <td width="55%" align="left">
										           <div class="sys_property" style="display: block">
										               <xform:select property="value(ding2ekp.dept.keyword)" className="selectsgl" subject="" showPleaseSelect="false" style="width:45%;">
														 <xform:enumsDataSource enumsType="third_ding_ding2ekp_dept"></xform:enumsDataSource>
													   </xform:select>
													   </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%">是否与业务有关</td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.dept.fdIsBusiness.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"  className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
                                                           <xform:simpleDataSource value="noSyn">不同步</xform:simpleDataSource>
														   <xform:simpleDataSource value="syn">同步</xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn">仅新增时同步</xform:simpleDataSource>
													 </xform:select>
										           </td>
										           <td width="55%" align="left">
										           <div class="sys_property" style="display: block">
										               <xform:select  property="value(ding2ekp.dept.fdIsBusiness)" subject="" className="selectsgl" showPleaseSelect="false" style="width:45%;">
														   <xform:enumsDataSource enumsType="third_ding_ding2ekp_dept"></xform:enumsDataSource>
													   </xform:select>
													   </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%">备注</td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.dept.fdMemo.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)"  className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
                                                           <xform:simpleDataSource value="noSyn">不同步</xform:simpleDataSource>
														   <xform:simpleDataSource value="syn">同步</xform:simpleDataSource>
														   <xform:simpleDataSource value="addSyn">仅新增时同步</xform:simpleDataSource>
													 </xform:select>
										           </td>
										           <td width="55%" align="left">
										           <div class="sys_property" style="display: block">
										                <xform:select  property="value(ding2ekp.dept.fdMemo)" subject="" className="selectsgl" showPleaseSelect="false" style="width:45%;">
														   <xform:enumsDataSource enumsType="third_ding_ding2ekp_dept"></xform:enumsDataSource>
													   </xform:select>	
													   </div>
										           </td>
										       </tr> --%>
										       
										       
										       
										   </table>
										</div>
									</td>
								</tr>
								
								<!-- 角色同步 -->
								<tr>
								<td class="td_normal_title" width="15%">角色同步</td>
									<td>
										<div>
										<span style="color:red"><bean:message key="third.ding.org.ding2ekp.dept.tip" bundle="third-ding"/></span>	
										   
										   <table style="text-align: center" class="tb_normal" width=100% >
										       <tr>
										           <td class="td_normal_title"><bean:message key="third.ding.org.ding2ekp.ekp" bundle="third-ding"/></td>
										           <td class="td_normal_title"><bean:message key="third.ding.org.ding2ekp.synWay" bundle="third-ding"/></td>
										           <td class="td_normal_title"><bean:message key="third.ding.org.ding2ekp.from.ding" bundle="third-ding"/></td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%">岗位</td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.role.post.synWay)"  htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
													  
										           </td>
										           <td width="55%" align="left">
										             <div class="sys_property" style="display: block">
										               <xform:select property="value(ding2ekp.role.post)"  className="selectsgl" subject="" style="width:45%"  showStatus="edit"  showPleaseSelect="false">
							                                 <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDing2ekpRoleCustomData" />
							                          </xform:select>
							                          
													    <div>
													     说明：由于钉钉端的岗位是通用角色，同步到EKP岗位中是没有上级部门的
													   </div>
												    </div>
										           </td>
										       </tr>
										       <tr>
										           <td class="td_normal_title" width="15%">职务</td>
										           <td width="25%">
										             <xform:select property="value(ding2ekp.role.staffing.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										           <div class="sys_property" style="display: block">
										               <xform:select property="value(ding2ekp.role.staffing)"  className="selectsgl" subject="" style="width:45%"  showStatus="edit"  showPleaseSelect="false">
							                                 <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDing2ekpRoleCustomData" />
							                          </xform:select>
							                          </div>
													    <div>
													     说明：由于EKP的职务是一人一职务，而钉钉是可以一人多角色，所以为了数据的一致性，需要保证同步的的钉钉角色也是一人一角色；
													   </div>
										           </td>
										       </tr>
										       
										       <tr>
										           <td class="td_normal_title" width="15%">群组</td>
										           <td width="25%">
										              <xform:select property="value(ding2ekp.role.group.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>
										           </td>
										           <td width="55%" align="left">
										           <div class="sys_property" style="display: block">
													   <xform:checkbox property="value(ding2ekp.role.group)"  className="selectsgl" subject="" style="width:45%"  showStatus="edit">
														   <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDing2ekpRoleCustomData" />
													   </xform:checkbox>
							                          </div>
													   
										           </td>
										       </tr>
										       
										       <tr>
										           <td class="td_normal_title" width="15%">角色线</td>
										           <td width="25%">
										              <xform:select property="value(ding2ekp.role.roleline.synWay)" htmlElementProperties="type='synWay'"  onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"  showPleaseSelect="false">
														   <xform:simpleDataSource value="noSyn"><bean:message key="third.ding.org.ekp2ding.noSyn" bundle="third-ding"/></xform:simpleDataSource>
														   <xform:simpleDataSource value="syn"><bean:message key="third.ding.org.ekp2ding.syn" bundle="third-ding"/></xform:simpleDataSource>
													   </xform:select>  
										           </td>
										           <td width="55%" align="left">
										           <div class="sys_property" style="display: block">
										               <xform:checkbox property="value(ding2ekp.role.roleline)"  className="selectsgl" subject="" style="width:45%"  showStatus="edit">
							                                 <xform:customizeDataSource className="com.landray.kmss.third.ding.util.ThirdDingDing2ekpRoleCustomData" />
							                          </xform:checkbox>
							                          </div>
													   
										           </td>
										       </tr>
							       
										   </table>
										</div>
									</td>
								</tr>
								
								<%-- <tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.ding.omstoekp.tip" bundle="third-ding"/></td>
									<td>
										<div class="message"><bean:message key="third.ding.omstoekp" bundle="third-ding"/></div>
									</td>
								</tr> --%>
							</table>
							</div>
							
							<!-- <br/> -->
							<table id="ldingOmsInTable" class="tb_normal" width=100% >
								<%-- <tr id="ldingOmsInTR"  title="${lfn:message('third-ding:third.lding.enabled') }">
									<td class="td_normal_title" width="15%"><bean:message key="third.lding.enabled" bundle="third-ding"/></td>
									<td>
										<ui:switch id="ldingOmsInSwitch" property="value(ldingEnabled)" onValueChange="ldingOmsIn_display_change();" 
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</td>
								</tr> --%>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.lding.enabled.address" bundle="third-ding"/></td>
									<td>
										<xform:text property="value(ldingSynAddress)" subject="${lfn:message('third-ding:third.lding.enabled.address') }" 
											required="false" style="width:85%" showStatus="edit" validators="url"/>
										<!-- <span class="txtstrong">*</span> -->
										<div class="message"><bean:message key="third.lding.enabled.address.desc" bundle="third-ding"/></div>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.lding.enabled.user" bundle="third-ding"/></td>
									<td>
										<xform:text property="value(ldingUser)" subject="${lfn:message('third-ding:third.lding.enabled.user') }" 
											required="false" style="width:85%" showStatus="edit" htmlElementProperties="autocomplete='off'"/>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.lding.enabled.pwd" bundle="third-ding"/></td>
									<td>
										<xform:text property="value(ldingPwd)" subject="${lfn:message('third-ding:third.lding.enabled.pwd') }" 
											required="false" style="width:85%" showStatus="edit" htmlElementProperties="type='password' autocomplete='new-password'"/>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message key="third.lding.enabled.time" bundle="third-ding"/></td>
									<td>
										<div id="ldingSynTime">
											<xform:text property="value(ldingSynTime)" subject="${lfn:message('third-ding:third.lding.enabled.time') }" 
											required="false" style="width:85%" showStatus="view" />
										</div>
										<ui:button text="${lfn:message('third-ding:third.lding.time.clear') }" onclick="cleanLdingTime();" style="vertical-align: top;"></ui:button>
										<div class="message"><bean:message key="third.lding.enabled.time.desc" bundle="third-ding"/></div>
									</td>
								</tr>
							</table>
					</ui:content>
					
					
					<!-- 业务数据配置 -->
					<ui:content id="tag5" title="${ lfn:message('third-ding:thirdDing.tab.business.setting') }">
						<%-- <br/>
						<table  id="dingAppTable" class="tb_normal" width=100%>
							<tr>
								<td class="td_normal_title" width="15%">应用同步配置</td>
								<td>
									<ui:switch property="value(dingAppEnabled)"
										enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</td>
							</tr>
						</table> --%>
						<br/>
						<table id="dingBusTable" class="tb_normal" width=100% >
							<%-- <tr>
								<td class="td_normal_title" width="15%">
									<bean:message key="third.ding.config.review" bundle="third-ding"/>
								</td>
								<td>
									<table id="flowTable"  style="width: 100%">
										<tr>
											<td width="15%">
												<ui:switch property="value(dingFlowEnabled)" onValueChange="dingFlow_display_change();"
													enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
											</td>
											<td id="flowSynType" width="70%">
												<xform:select property="value(dingFlowSynType)">
					                                 <xform:enumsDataSource enumsType="third_ding_flow_synType" />
					                             </xform:select>
											</td>
										</tr>
									</table>
								</td>
							</tr> --%>
							<tr>
								<td class="td_normal_title" width="15%">
									<bean:message key="third.ding.config.schedule" bundle="third-ding"/>
								</td>
								<td>
									<ui:switch property="value(dingScheduleEnabled)"  onValueChange="ding_schedule_change();"
										enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									<div class="message"><font color="red"><bean:message key="module.third.ding.calendar.sync.desc" bundle="third-ding"/></font></div>
								</td>
							</tr>

							<%--隐藏按钮 显示当前选中的日程版本--%>
							<input id="ori_calId" type="hidden" value="${sysAppConfigForm.getValue('calendarApiVersion')}"/>
							<%--隐藏按钮 显示日程旧id是否被替换成了新id  true 代表替换成了新ID--%>
							<input id="calendarInitFlag" type="hidden" value="${sysAppConfigForm.getValue('calendarInitFlag')}"/>
							<%--是否初始化过人员对照表--%>
							<input id="personInitFlag" type="hidden" value="${sysAppConfigForm.getValue('personInitFlag')}"/>
							<tr hidden>
									<td colspan="1" class="td_normal_title" width="20%">
									<td><ui:switch property="value(personInitFlag)"
												   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
												   disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</td>
							</tr>
							<tr hidden>
								<td colspan="1" class="td_normal_title" width="20%">
								<td><ui:switch property="value(calendarInitFlag)"
											   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
											   disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</td>
							</tr>
							<tr id="tr_calendar">
								<td class="td_normal_title" width="15%">
									<bean:message key="third.ding.calendar.api.version" bundle="third-ding"/>
								</td>
								<td>
									<xform:radio property="value(calendarApiVersion)" showStatus="edit">
												<xform:simpleDataSource value="V1" textKey="third.ding.calendar.api.version.V1" bundle="third-ding"></xform:simpleDataSource>
												<xform:simpleDataSource value="V2" textKey="third.ding.calendar.api.version.V2" bundle="third-ding"></xform:simpleDataSource>
										        <xform:simpleDataSource value="V3" textKey="third.ding.calendar.api.version.V3" bundle="third-ding"></xform:simpleDataSource>
									</xform:radio>
									<div class="message">
										<font color="red"><bean:message key="third.ding.calendar.api.version.tip" bundle="third-ding"/> </font><br/>
										<font color="red"><bean:message key="third.ding.calendar.api.version.attention.tip" bundle="third-ding"/> </font>
									</div>
								</td>
							</tr>
							</table>
							<!-- 高级审批配置 -->
							<table id="attendance.lbpm" class="tb_normal" width=100% style="margin-top: 5px" >
							  <!-- 高级审批 -->
							<tr style="display:none;">
								<td class="td_normal_title" width="15%">
									<bean:message key="thirdDing.attendance.lbpm" bundle="third-ding"/>
								</td>
								<td>
								    <ui:switch property="value(attendanceEnabled)"
										enabledText="${lfn:message('third-ding:thirdDing.attendance.open')}" disabledText="${lfn:message('third-ding:thirdDing.attendance.close')}" onValueChange="chooseAttendance()"></ui:switch>
								    <div class="message"><font color="red">${lfn:message('third-ding:thirdDing.attendance.tip')}</font></div>
								    <div id="attendance_error">${lfn:message('third-ding:thirdDing.attendance.notify.target')}：
								    
									<xform:address propertyId="value(attendance.error.notify.OrgId)" propertyName="value(attendance.error.notify.OrgName)" 
										mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" style="width:25%"
										subject="${lfn:message('third-ding:thirdDing.attendance.notify.target')}"></xform:address>
										<span>${lfn:message('third-ding:thirdDing.attendance.notify.target.tip')}</span>
									</div>
									
								</td>
							</tr>
							<!--钉钉套件开关：-->
							<tr>
								<td class="td_normal_title" width="15%">钉钉套件</td>
								<td>
									<ui:switch property="value(dingSuitEnabled)"
									enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							        <div class="message"><font color="red">开启"钉钉套件",则流程管理可以选择钉钉套件，套件流程的模板、实例、待办都会同步到钉钉审批应用中。</font></div>
								</td>
							</tr>
							<!-- 代码调试模式 -->
							<tr style="display:none;">
									<td class="td_normal_title" width="15%" style="display:none;">代码调试模式</td>
									<td style="display:none;">
										<xform:radio  property="value(attendanceDebug)">
											<xform:simpleDataSource value="true">阿里郎调试模式</xform:simpleDataSource>
											<xform:simpleDataSource value="false">正常模式</xform:simpleDataSource>
										</xform:radio>
									</td>
							</tr>
							<!-- 高级审批管理门户地址 -->
							<tr style="display:none;">
								<td class="td_normal_title" width="15%">高级审批管理门户地址</td>
								<td>
									<xform:text property="value(dingPortalUrl)" subject="高级审批管理门户地址" 
										required="false" style="width:85%" showStatus="edit" />
								</td>
							</tr>
							
							
						</table>
						
						<!-- 钉盘配置 -->
						<table id="cspace_tab" style="margin-top: 5px;" class="tb_normal" width=100% >
						     <tr id="tr_cspace_tab">
								<td class="td_normal_title" width="15%">
								        <bean:message key="third.ding.config.attachment" bundle="third-ding"/> 
								</td>
								<td>
									<ui:switch property="value(cspaceEnable)" onValueChange="ding_cspace_change();" 
										enabledText="${lfn:message('third-ding:third.ding.config.attachment.open')}" disabledText="${lfn:message('third-ding:third.ding.config.attachment.close')}"></ui:switch>
									<div class="message"><font color="red"><bean:message key="third.ding.config.attachment.tip" bundle="third-ding"/> </font></div>
								</td>
							</tr>
							<tr id="tr_cspace_time">
								<td class="td_normal_title" width="15%">
									<bean:message key="third.ding.config.media.time" bundle="third-ding"/> 
								</td>
								<td>
									<xform:text property="value(cspaceTime)" subject="有效时间" 
											required="false" validators="" style="width:5%" showStatus="edit" htmlElementProperties="placeholder='30'"/> <bean:message key="third.ding.config.media.unit" bundle="third-ding"/> 
											<div class="message"><font color="red"><bean:message key="third.ding.config.media.time.tip" bundle="third-ding"/> </font></div>
								</td>
							</tr>
						</table>

						<!-- 互动卡片配置 -->
						<table id="card_tab" style="margin-top: 5px;" class="tb_normal" width=100% >
							<tr id="tr_card_tab">
								<td class="td_normal_title" width="15%">
									互动卡片推送
								</td>
								<td>
									<ui:switch property="value(interactiveCardEnable)" onValueChange="interactiveCardChange();"
											   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									<div class="message"><font color="red">启用后支持互动卡片功能</font></div>
								</td>
							</tr>
							<tr id="tr_robot_tab">
								<td class="td_normal_title" width="15%">
									应用机器人
								</td>
								<td>
									<div>
										AgentId: &nbsp;&nbsp;&nbsp;&nbsp;
										<xform:text property="value(robotAgentId)" subject="AgentId"
													required="true" validators="" style="width:65%" showStatus="edit" htmlElementProperties="placeholder='AgentId'"/>
										<br/><br/>
										AppKey:&nbsp;&nbsp;&nbsp;&nbsp;
										<xform:text property="value(robotAppKey)" subject="AppKey"
													required="true" validators="" style="width:65%" showStatus="edit" htmlElementProperties="placeholder='AppKey'"/>
										<br/><br/>
										AppSecret:
										<xform:text property="value(robotAppSecret)" subject="AppSecret"
													required="true" validators="" style="width:65%" showStatus="edit" htmlElementProperties="placeholder='AppSecret'"/>

									</div>
									<div class="message"><font color="red">启用后支持互动卡片功能。</font>钉钉后台获取机器人信息：<a href="https://open-dev.dingtalk.com/fe/app#/corp/robot" target="_blank">https://open-dev.dingtalk.com/fe/app#/corp/robot</a></div>
								</td>
							</tr>

						</table>
					</ui:content>
				</ui:tabpanel>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.third.ding.model.DingConfig" />
			<center style="margin:20px 0;">
				<!-- 保存 -->
				<ui:button text="${lfn:message('button.save')}" onclick="window.dingSubmit();"  width="120" height="35"></ui:button>
			</center>
		</html:form>
		<script type="text/javascript">
			var validation = $KMSSValidation();
			var woi = "<%=DingConfig.newInstance().getDingOrgId()   %>";
			var ldingSynTime = "<%=DingConfig.newInstance().getLdingSynTime()   %>";
			function setLoginName(){
				var value = $('[name="value(wxLoginName)"]').val();
				if(value==""){
					$('[name="value(wxLoginName)"]').val("loginname");
				}else{
					if(value=="id"){
						$("#ln").empty();
						$("#ln").text("<bean:message key='third.ding.config.ekpPrimary' bundle='third-ding' />");
					}else{
						$("#ln").empty();
						$("#ln").text("<bean:message key='third.ding.config.wxLoginNameLn' bundle='third-ding' />");
						$('[name="value(wxLoginName)"]').val("loginname");
					}
				}
				if(ldingSynTime>0){
					var d = new Date(parseFloat(ldingSynTime));
					var dt = d.getFullYear();
					if(d.getMonth()+1<10){
						dt += "-0"+ (d.getMonth()+1);
					}else{
						dt += "-"+ (d.getMonth()+1);
					}
					if(d.getDate()<10){
						dt += "-0"+ d.getDate();
					}else{
						dt += "-"+ d.getDate();
					}
					if(d.getHours()<10){
						dt += " 0"+ d.getHours();
					}else{
						dt += " "+ d.getHours();
					}
					if(d.getMinutes()<10){
						dt += ":0"+ d.getMinutes();
					}else{
						dt += ":"+ d.getMinutes();
					}
					if(d.getSeconds()<10){
						dt += ":0"+ d.getSeconds();
					}else{
						dt += ":"+ d.getSeconds();
					}
					$('#ldingSynTime').text(dt);
				}else{
					$('#ldingSynTime').text("");
				}
			}
			function cleanTime(){
				if(confirm("<bean:message key='third.ding.config.clearMsg' bundle='third-ding' />")){
					var url = '<c:url value="/resource/third/ding/attachment.do?method=cleanTime" />';
					$.ajax({
					   type: "POST",
					   url: url,
					   async:false,
					   dataType: "json",
					   success: function(data){
							if(data.status=="1"){
								alert("<bean:message key='third.ding.config.clearSuccess' bundle='third-ding' />");
							}else{
								alert(data.msg);
							}
					   }
					});
				}
			}
			function cleanLdingTime(){
				if(confirm("<bean:message key='third.lding.time.clearMsg' bundle='third-ding' />")){
					var url = '<c:url value="/resource/third/ding/attachment.do?method=cleanLdingTime" />';
					$.ajax({
					   type: "POST",
					   url: url,
					   async:false,
					   dataType: "json",
					   success: function(data){
							if(data.status=="1"){
								alert("<bean:message key='third.lding.time.clear.success' bundle='third-ding' />");
								$('#ldingSynTime').text("");
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
					alert('<bean:message key='third.ding.config.org.tip' bundle='third-ding'/>');
				}
				external_display_change();
			}
			//生态组织相关配置显示调整
			function external_display_change(){
				//ekp根机构
				var value = $('[name="value(dingOrgId)"]').val();
				if(!value){
					$('#dingOmsOutTable tr[id="tr_dingOmsRootFlag"]').hide();
					var ISENABLEDECO = '${ISENABLEDECO}';
					if(ISENABLEDECO=='true'){
						$('#dingOmsOutTable tr[id="tr_dingOmsExternal"]').show();
					}
				}else{
					$('#dingOmsOutTable tr[id="tr_dingOmsRootFlag"]').show();
				}
				//判断生态组织实时同步配置
				checkExternal(value);
			}
			function checkExternal(value){
				var ISENABLEDECO = '${ISENABLEDECO}';
				if(ISENABLEDECO==='false'){
					return;
				}
				if(!value){
					return;
				}
				var url = '<c:url value="/resource/third/ding/attachment.do?method=checkExternal" />';
				$.ajax({
				   type: "POST",
				   url: url,
				   data:{dingOrgId:value},
				   dataType: "json",
				   success: function(data){
						if(data.status=="1"){
							$('#dingOmsOutTable tr[id="tr_dingOmsExternal"]').show();
						}else{
							$('#dingOmsOutTable tr[id="tr_dingOmsExternal"]').hide();
						}
				   },
				   error:function(err){
					   $('#dingOmsOutTable tr[id="tr_dingOmsExternal"]').hide();
					   if(window.console){
						   window.console.log('isExistExternal request fail,err:'+err);
					   }
				   }
				});
			}
			function tip(type){
				var dle = $("input[name='value(dingDeptLeaderEnabled)']").val();
				var pme = $("input[name='value(dingPostMulDeptEnabled)']").val();
				if("dingPostMulDeptEnabled"==type&&"true"==pme){
					alert("<bean:message key='third.ding.config.dingPost.tip' bundle='third-ding'/>");
				}else if("dingDeptLeaderEnabled"==type&&"true"==dle){
					alert("<bean:message key='third.ding.config.dingPostLeader.tip' bundle='third-ding'/>");
				}
			}

			//专属账号相关js
			function exclusiveAccountEnable_switch(val){
				if(val == undefined){
					val = $('[name="value(exclusiveAccountEnable)"]').val();
				}

				//同步方式
				var syncSelection;
				var _type = document.getElementsByName("value(syncSelection)");
				for(var i = 0; i < _type.length; i++) {
					if(_type[i].checked) {
						syncSelection = _type[i].value;
						break;
					}
				}

				if(val == 'true'){
					$('[name="exclusiveAccountEnable_item"]').show();
					if(syncSelection == "3"){
						$("input[name='value(org2ding.exclusiveAccount.password)']").attr("disabled",true);
					}else{
						$("input[name='value(org2ding.exclusiveAccount.password)']").attr("disabled",false);
					}
				}else{
					$('[name="exclusiveAccountEnable_item"]').hide();
                    //密码非空校验
					$("input[name='value(org2ding.exclusiveAccount.password)']").attr("disabled",true);
				}

				switchexclusiveAccountType();

			}

			seajs.use(['lui/jquery'],function($){

				//判断当前待办和日程的版本 切换高版本后禁用掉低版本
				var calendarApiVersion = $('#ori_calId').val();
				if(calendarApiVersion == 'V3'){
					$("input[name='value(calendarApiVersion)']").each(function(){
						if(!$(this).is(":checked")){
							$(this).attr("disabled",true);
						}
					});
				}

				if ($('#ori_task').val() =='TODO') {
					$("input[name='value(notifyApiType)']").each(function(){
						if(!$(this).is(":checked")){
							$(this).attr("disabled",true);
						}
					});
				}

				$("input[name='value(notifyApiType)']").change(function(){
					//获取原本保存的待办版本
					var  taskApiVersion = $('#ori_task').val();
					var i=1;
					$("input[name='value(notifyApiType)']").each(function(key){
						if(taskApiVersion ==this.value){
							i=key;
						}
					});
					//获取当前选中待办的版本
					var newTaskApiVersion = $('input[name="value(notifyApiType)"]:checked ').val();

					if (taskApiVersion != 'WR' && newTaskApiVersion == 'WR'){
						seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
							dialog.confirm("钉钉待办1.0接口的相关权限已不能申请，可能会导致待办推送失败，是否确认升级?", function(flag, d) {
								if (flag) {
									$("input[name='value(notifyApiType)']").each(function(){
										if(!$(this).is(":checked")){
											$(this).attr("disabled",true);
										}
									});
								} else {
									$('input[name="value(notifyApiType)"]').get(i).checked=true; ;
								}
							});
						});
					}else if (taskApiVersion == 'WF' && newTaskApiVersion == 'TODO'
							||taskApiVersion == 'WR' && newTaskApiVersion == 'TODO'
					) {
						seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
							dialog.confirm("升级待办接口操作不可逆，已生成的旧代办数据不受影响，是否确认升级?", function(flag, d) {
								if (flag) {
									$("input[name='value(notifyApiType)']").each(function(){
										if(!$(this).is(":checked")){
											$(this).attr("disabled",true);
										}
									});
								} else {
									$('input[name="value(notifyApiType)"]').get(i).checked=true; ;
								}
							});
						});
					}
				});



				//是否显示钉钉配置项
				function ding_display_change(){
					var value = $('[name="value(dingEnabled)"]').val();
					if(value == 'true'){
						$('#dingBaseTable tr[id!="dingEnableTR"]').show();
						$('#dingTodoTable,#dingPcScanTable,#dingBusTable').show();
						__setVisible(true);
					}else{
						$('#dingBaseTable tr[id!="dingEnableTR"]').hide();
						$('#dingTodoTable,#dingPcScanTable,#dingBusTable').hide();
						__setVisible(false);
					}
					devModel_display_change();
				}
				
				function chooseAttendance(){
					var value = $('[name="value(attendanceEnabled)"]').val();
					if(value == 'true'){
						$('#attendance_error').show();
					}else{
						$('#attendance_error').hide();
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
				//是否显示钉钉PC扫码相关配置项
				function dingPcScan_display_change(){
					var value = $('[name="value(dingPcScanLoginEnabled)"]').val();
					if(value == 'true'){
						$('#dingPcScanTable tr[id!="dingPcScanEnableTR"]').show();
					}else{
						$('#dingPcScanTable tr[id!="dingPcScanEnableTR"]').hide();
					}
				}
				
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

					//专属账号密码,切换同步方式时
					$("input[name='value(org2ding.exclusiveAccount.password)']").attr("disabled",true);

					if(syncSelection =='0'){
						$('#dingOmsOutTable').hide();
						$('#dingOmsInTable').hide();
						$('#ldingOmsInTable').hide();
					}else if(syncSelection =='1'){
						$('#dingOmsOutTable').show();
						$('#dingDeptidMsg_omsOut').show();
						$('#dingDeptidMsg_mapping').hide();
						$('#dingOmsOutTable').find("tr").each(
								function(){
								   $(this).show();
								}
							);
						
						$('#dingOmsInTable').hide();
						$('#ldingOmsInTable').hide();

						//专属账号判断
						exclusiveAccountEnable_switch();
						
					}else if(syncSelection =='2'){
						$('#dingOmsOutTable').hide();
						$('#dingOmsInTable').show();
						$('#ldingOmsInTable').hide();
					}else if(syncSelection =='3'){
						
						$('#dingOmsInTable').hide();
						$('#ldingOmsInTable').hide();
						
						$('#dingDeptidMsg_omsOut').hide();
						$('#dingDeptidMsg_mapping').show();
						$('#dingOmsOutTable').find("tr").each(
							function(){
							    var id = $(this).attr("id");
							    if(id && id=="tr_dingDeptid"){
							    	$(this).show();
							    }else{
							    	$(this).hide();
							    }
							}
						);
						$('#dingOmsOutTable').show();
					}else if(syncSelection =='4'){
						$('#dingOmsOutTable').hide();
						$('#dingOmsInTable').hide();
						$('#ldingOmsInTable').show();
					}
				}
				
				
				
				/* //是否显示组织架构接出配置项
				function dingOmsOut_display_change(){
					var value = $('[name="value(dingOmsOutEnabled)"]').val();
					if(value == 'true'){
						$('#dingOmsOutTable tr[id!="dingOmsOutTR"]').show();
						setSwitchStatus(LUI('dingOmsInSwitch'),false);
						try{
							setSwitchStatus(LUI('ldingOmsInSwitch'),false);
						}catch(err){}
					}else{
						$('#dingOmsOutTable tr[id!="dingOmsOutTR"]').hide();
					}
				}
				//是否显示组织架构接入配置项
				function dingOmsIn_display_change(){
					var value = $('[name="value(dingOmsInEnabled)"]').val();
					if(value == 'true'){
						$('#dingOmsInTable tr[id!="dingOmsInTR"]').show();
						setSwitchStatus(LUI('dingOmsOutSwitch'),false);
						try{
							setSwitchStatus(LUI('ldingOmsInSwitch'),false);
						}catch(err){}
					}else{
						$('#dingOmsInTable tr[id!="dingOmsInTR"]').hide();
					}
				}
				//是否显示蓝钉组织架构接入配置项
				function ldingOmsIn_display_change(){
					var value = $('[name="value(ldingEnabled)"]').val();
					if(value == 'true'){
						$('#ldingOmsInTable tr[id!="ldingOmsInTR"]').show();
						setSwitchStatus(LUI('dingOmsOutSwitch'),false);
						setSwitchStatus(LUI('dingOmsInSwitch'),false);
					}else{
						$('#ldingOmsInTable tr[id!="ldingOmsInTR"]').hide();
					}
				} */
				
				
			/* 	//开关设置
				function setSwitchStatus(widget,status){
					// 处理需要转义的字符
					var _property = widget.config.property.replace(/\(/g, "\\\(").replace(/\)/g, "\\\)");
					$("input[name=" + _property + "]").val(status);
					widget.checkbox.prop('checked',status);
					widget.setText(status);
					// 内容修改事件
					if(widget.config.onValueChange) {
						eval(widget.config.onValueChange);
					}
				} */
				
				function config_randomAlphanumeric(charsLength,chars){
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
				
				//生成随机Token
				function random_token(){
					$("[name='value(dingToken)']").val( config_randomAlphanumeric(16) ); 
				}
				//生成随机AESKey
				function random_AESKey(){
					$("[name='value(dingAeskey)']").val( config_randomAlphanumeric(43) ); 
				}
				
				//设置微信回调URL
				function config_ding_callbackurl(){
					var dingDomain = $("[name='value(dingDomain)']") && $("[name='value(dingDomain)']").val() || '',
						dingUrlSuffix = $("[name='dingUrlSuffix']").val();
					$('[name="value(dingCallbackurl)"]').val(dingDomain + dingUrlSuffix);
				}
				function config_ding_dns_getUrl(){
					var protocol = location.protocol,
						host = location.host,
						contextPath = seajs.data.env.contextPath;
					$('[name="value(dingDomain)"]').val(protocol + '//' + host + contextPath );
				}
				//流程同步开关
				function dingFlow_display_change(){
					var value = $('[name="value(dingFlowEnabled)"]').val();
					if(value == 'true'){
						$('#flowTable td[id="flowSynType"]').show();
					}else{
						$('#flowTable td[id="flowSynType"]').hide();
					}
				}

				//自定义校验器
				validation.addValidator('dingRequire','{name}'+"<bean:message key='third.ding.config.notnull' bundle='third-ding'/>",function(v, e, o){
					var wxEnabled = $('[name="value(dingEnabled)"]').val();
					if(wxEnabled == 'true' && !v){
						return false;
					}
					return true;
				});

				validation.addValidator('dingOmsRequire','{name}'+"<bean:message key='third.ding.config.notnull' bundle='third-ding'/>",function(v, e, o){
					var wxEnabled = $('[name="value(dingEnabled)"]').val();
					var syncSelection;
					var _type = document.getElementsByName("value(syncSelection)");
					for(var i = 0; i < _type.length; i++) {
    		 			if(_type[i].checked) {
    		 				syncSelection = _type[i].value;
    		 				break;
    		 			}
    		 		}
					if(wxEnabled != 'true'){
						return true;
					}
					return (syncSelection == '1' && !v) ? false : true;
				});

				/* validation.addValidator('dingTodoRequire','{name}'+"<bean:message key='third.ding.config.notnull' bundle='third-ding'/>",function(v, e, o){
					var wxEnabled = $('[name="value(dingEnabled)"]').val(),
						toDoEnabled = $('input[name="value(dingTodoEnabled)"]').val();
					if(wxEnabled != 'true'){
						return true;
					}
					return (toDoEnabled == 'true' && !v) ? false : true;
				}); */
				
				validation.addValidator('dingProxy','{name}不合法',function(v,e,o){
					var wxProxy = $('[name="value(dingProxy)"]').val();
					if(wxProxy && ! /(^:)*:\d+/.test(v)  ){
						return false;
					}
					return true;
				});
				
				validation.addValidator('dingPcScanRequire','{name}'+"<bean:message key='third.ding.config.notnull' bundle='third-ding'/>",function(v,e,o){
					var wxEnabled = $('[name="value(dingEnabled)"]').val(),
						pcScanEnabled = $('input[name="value(dingPcScanLoginEnabled)"]').val();
					if(wxEnabled != 'true'){
						return true;
					}
					return (pcScanEnabled == 'true' && !v) ? false : true;
				});
				
				function devModel_display_change(){
					var value = $('[name="value(dingEnabled)"]').val();
					if(value=="true"){
						var devModel = $('[name="value(devModel)"]:checked').val();
						if("2" == devModel){
							$('tr[name="dev1"]').hide();
							$('tr[name="dev2"]').show();
							$('tr[name="dev3"]').hide();
						}else if("3" == devModel){
							$('tr[name="dev1"]').hide();
							$('tr[name="dev2"]').hide();
							$('tr[name="dev3"]').show();
						}else{
							$('tr[name="dev1"]').show();
							$('tr[name="dev2"]').hide();
							$('tr[name="dev3"]').hide();
						}
					}
				}
				
				function __init(){
					dingPcScan_display_change();
					
					syncSelection_display_change();
					/* dingOmsOut_display_change();
					dingOmsIn_display_change();
					ldingOmsIn_display_change(); */
					ding_display_change();
					dingFlow_display_change();
					
					config_ding_callbackurl();
					setLoginName();
					
					devModel_display_change();
					external_display_change();
					
					ding_schedule_change();
					chooseAttendance();
					ding_cspace_change();
					//ding_adminSynEnabled_change();
					interactiveCardChange();
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
					LUI("tag5").on("show", function(){
						__init();
					});
				});
				
				window.validateAppConfigForm = function(){
					return true;
				};
				
				window.dingSubmit = function(){

					var dingEnabled = $('[name="value(dingEnabled)"]').val();
					if(!dingEnabled || dingEnabled == 'false'){
						$('[name="value(dingOauth2Enabled)"]').val('false');
						//$('[name="value(dingTodoEnabled)"]').val('false');
						$('[name="value(dingTodotype2Enabled)"]').val('false');
						/* $('[name="value(dingOmsOutEnabled)"]').val('false');
						$('[name="value(dingOmsInEnabled)"]').val('false'); */
						$('[name="value(dingMobileEnabled)"]').val('false');
						/* $('[name="value(ldingEnabled)"]').val('false'); */
						//设置同步选择为"0"
						 $('[name="value(syncSelection)"]').val('0');
					}
					var syncSelection;
					var _type = document.getElementsByName("value(syncSelection)");
					for(var i = 0; i < _type.length; i++) {
    		 			if(_type[i].checked) {
    		 				syncSelection = _type[i].value;
    		 				break;
    		 			}
    		 		}
					
					if(syncSelection !="1"){
						$('[name="value(dingMobileEnabled)"]').val('false');
					}
					/* var dingOmsOutEnabled = $('[name="value(dingOmsOutEnabled)"]').val();
					if(!dingOmsOutEnabled || dingOmsOutEnabled == 'false'){
						$('[name="value(dingMobileEnabled)"]').val('false');
					} */
					var flag = true;
					if(syncSelection == 2){
						if("${noEnable}" == "true"){
							var fdnoSyn = $('[name="value(ding2ekp.fdNo.synWay)"]').val();
							if("noSyn" == fdnoSyn){
								alert("组织架构编号必填开关已开启，钉钉同步到ekp的编号字段必须配置！");
								flag = false;
							}
							
						}
						
					}
					var dingSize = $('[name="value(dingSize)"]').val();
					if(dingSize!=""&&parseInt(dingSize)<2000){
						alert("<bean:message key='third.ding.oms.person.error.tip' bundle='third-ding'/>");
						flag = false;
					}
					
					// 开发方式调整
					if(dingEnabled == 'true'){
						var devModel = $('[name="value(devModel)"]:checked').val();
						var dingCorpSecret = $('[name="value(dingCorpSecret)"]').val();
						var appKey = $('[name="value(appKey)"]').val();
						var appSecret = $('[name="value(appSecret)"]').val();
						var customKey = $('[name="value(customKey)"]').val();
						var customSecret = $('[name="value(customSecret)"]').val();
						if("1"== devModel && !dingCorpSecret){
							alert("CorpSecret<bean:message key='third.ding.config.app.yanzhen' bundle='third-ding'/>");
							flag = false;
						}else if("2"== devModel && (!appKey || !appSecret)){
							alert("appKey/appSecret<bean:message key='third.ding.config.app.yanzhen' bundle='third-ding'/>");
							flag = false;
						}else if("3"== devModel && (!customKey || !customSecret)){
							alert("customKey/customSecret<bean:message key='third.ding.config.app.yanzhen' bundle='third-ding'/>");
							flag = false;
						}
					}
					var dingFlowEnabled = $('[name="value(dingFlowEnabled)"]').val();
					if(dingFlowEnabled == 'true'){
						var dingFlowSynType = $('[name="value(dingFlowSynType)"] option:selected').val();
						if(dingFlowSynType == null || dingFlowSynType == ""){
							alert("<bean:message key='thirdDing.select.flow.synch.type' bundle='third-ding'/>");
							flag = false;
						}
					}
					
					/* var ldingEnabled = $('[name="value(ldingEnabled)"]').val(); */
					if(syncSelection =="4"){
						var ldingSynAddress = $('[name="value(ldingSynAddress)"]').val();
						if(ldingSynAddress==null||ldingSynAddress==""){
							alert("<bean:message key='third.lding.enabled.validate' bundle='third-ding'/>");
							flag = false;
						}
					}
					
					//剔除前后空格
					$('[name="value(dingCorpid)"]').val($.trim($('[name="value(dingCorpid)"]').val()));
					$('[name="value(dingCorpSecret)"]').val($.trim($('[name="value(dingCorpSecret)"]').val()));
					$('[name="value(appKey)"]').val($.trim($('[name="value(appKey)"]').val()));
					$('[name="value(appSecret)"]').val($.trim($('[name="value(appSecret)"]').val()));
					$('[name="value(customKey)"]').val($.trim($('[name="value(customKey)"]').val()));
					$('[name="value(customSecret)"]').val($.trim($('[name="value(customSecret)"]').val()));
					$('[name="value(dingDomain)"]').val($.trim($('[name="value(dingDomain)"]').val()));
					$('[name="value(dingToken)"]').val($.trim($('[name="value(dingToken)"]').val()));
					$('[name="value(dingAeskey)"]').val($.trim($('[name="value(dingAeskey)"]').val()));
					$('[name="value(dingPcScanappId)"]').val($.trim($('[name="value(dingPcScanappId)"]').val()));
					$('[name="value(dingPcScanappSecret)"]').val($.trim($('[name="value(dingPcScanappSecret)"]').val()));
					$('[name="value(dingAgentid)"]').val($.trim($('[name="value(dingAgentid)"]').val()));
					$('[name="value(dingDeptid)"]').val($.trim($('[name="value(dingDeptid)"]').val()));
					var oldSyncSelection = "${oldSyncSelection}";
					var oldDingOrgId = "${oldDingOrgId}";
					var dingOrgId = $('[name="value(dingOrgId)"]').val();
					if(oldSyncSelection != syncSelection  && syncSelection =="1"){
						alert("您已修改同步方式为：同步，从本系统同步到钉钉，提交后，下次同步将执行全量同步，为了保证数据准确性，请手动重新初始化一次部门和人员对照表。");
					}
					if(oldSyncSelection != syncSelection && syncSelection =="2"){
						alert("您已修改同步方式为：同步，从钉钉同步到本系统，提交后，下次同步将执行全量同步，为了保证数据准确性，请手动重新初始化一次部门和人员对照表。");
					}
					if(oldSyncSelection != syncSelection && syncSelection =="3"){
						alert("您已修改同步方式为：不同步，仅从钉钉获取人员对应关系，提交后，为了保证数据准确性，请重新初始化一次部门和人员对照表。");
					}
					if(oldSyncSelection != syncSelection && syncSelection =="4"){
						alert("您已修改同步方式为：不同步，从蓝钉管理台获取人员对应关系，提交后，为了保证数据准确性，请重新初始化一次部门和人员对照表。");
					}
					if(syncSelection == '1' && oldSyncSelection == syncSelection && oldDingOrgId !=dingOrgId){
						alert("您已修改同步根机构，提交后，下次同步将执行全量同步，为了保证数据准确性，请手动重新初始化一次部门和人员对照表。");
					}

					//不是同步到钉钉的方向
					if(syncSelection !="1"){
						//移掉没有选拓展字段的明细行
						var $custom = $("[att='customField']");
						$custom.each(function(index,element){
							if(this.value==""){
								$(this.closest("tr")).find("a[onclick='DocList_DeleteRow();']").click();
							}
						});
					}

					//获取原本保存的日程版本
					var oldCalendarApiVersion = $('#ori_calId').val();
					//获取当前选中日程的版本
					var newCalendarApiVersion = $('input[name="value(calendarApiVersion)"]:checked ').val();
					if (oldCalendarApiVersion == 'V2' && newCalendarApiVersion == 'V3') {
						//如果当前是V2版本 切换V3 需要给出提示（1、更新UnionId 2、把V2的旧日程Id替换成新日程Id）
						//人员还没初始化执行过

						/*if ($('#personInitFlag').val() == "false") {
							//初始化人员更新UnioId
							initOms();
							flag = false;
						}*/

						if ($('#calendarInitFlag').val() == "false") {
							//旧id替换成新id
							calendarIdConvert("执行此操作会把V2旧日程的ID替换成新日程ID，此操作不可逆，请谨慎操作！");
							console.log(flag)
						}

					}

					//获取专有属性开关旧值
					var exclusiveAccountEnable_old = "${sysAppConfigForm.getValue('exclusiveAccountEnable')}";
					var exclusiveAccountEnable_cur = ""+$('[name="value(exclusiveAccountEnable)"]').val();
					if(flag && exclusiveAccountEnable_cur != undefined){
						if(exclusiveAccountEnable_old=="false" && exclusiveAccountEnable_cur=="true"){
							alert("您启用了专属账号同步!为了保证数据的准确性，需要您清理钉钉人员对照表信息，并进行 人员初始化 操作，人员优先匹配专属账号。")
						}
					}
					if (flag && $KMSSValidation().validate()) {
						//先后端删除拓展数据
						$.ajaxSettings.async = false;
						var url = '<c:url value="/third/ding/sysAppConfig.do?method=deleteCustom" />';
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
					}
				};

				function initOms() {
					alert('执行此操作需要手动去通讯录人员对照表中执行一次人员初始化');
					/*seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
						dialog.confirm(function(flag, d) {
							if (flag) {
								dialog.alert('执行此操作需要手动去通讯录人员对照表中执行一次人员初始化');
							} else {
								dialog.alert('取消成功...');
							}
						});
					});*/
				}

				function calendarIdConvert(content){
					seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
						dialog.confirm(content, function(flag, d) {
							if (flag) {
								//旧id替换成新id
								$.ajax({
									url: '<c:url value="/third/ding/third_ding_calendar/thirdDingCalendar.do?method=calendarIdConvert" />',
									type: "GET",
									dataType: "json",
									success:function(res){
										debugger
										console.log(res)
										if(res.resultCode == 200){
											//替换成功
											console.log(res.data)
											return true;
										}else{
											return false;
										}
									},error:function() {
										return false;
									}
								});
							} else {
								return false;
							}
						});
					});
				}

				//是否显示钉钉配置项
				function ding_schedule_change(){
					var value = $('[name="value(dingScheduleEnabled)"]').val();
					if(value == 'true'){
						$('#tr_calendar').show();
					}else{
						$('#tr_calendar').hide();
					}
				}
				function ding_cspace_change(){
					var value = $('[name="value(cspaceEnable)"]').val();
					if(value == 'true'){
						$('#tr_cspace_time').show();
					}else{
						$('#tr_cspace_time').hide();
					}
				}

				function interactiveCardChange(){
					var value = $('[name="value(interactiveCardEnable)"]').val();
					if(value == 'true'){
						$('#tr_robot_tab').show();
						$("input[name='value(robotAgentId)']").attr("disabled",false);
						$("input[name='value(robotAppKey)']").attr("disabled",false);
						$("input[name='value(robotAppSecret)']").attr("disabled",false);
					}else{
						$('#tr_robot_tab').hide();
						$("input[name='value(robotAgentId)']").attr("disabled",true);
						$("input[name='value(robotAppKey)']").attr("disabled",true);
						$("input[name='value(robotAppSecret)']").attr("disabled",true);
					}
				}
				
				function ding_adminSynEnabled_change(){
					var value = $('[name="value(dingAdminSynEnabled)"]').val();
					 if(value == 'true'){
						$('#ding_admin_groupId').show();
					}else{
						$('#ding_admin_groupId').hide();
					} 
				}
				window.ding_adminSynEnabled_change = ding_adminSynEnabled_change;
				
				window.ding_display_change = ding_display_change;
				
				window.chooseAttendance = chooseAttendance;
				window.dingPcScan_display_change = dingPcScan_display_change;
				
				window.syncSelection_display_change = syncSelection_display_change;
				
				/* window.dingOmsOut_display_change = dingOmsOut_display_change; */
				/* window.dingOmsIn_display_change = dingOmsIn_display_change; */
				window.random_token = random_token;
				window.random_AESKey = random_AESKey;
				window.config_ding_callbackurl = config_ding_callbackurl;
				window.config_ding_dns_getUrl = config_ding_dns_getUrl;
				window.dingFlow_display_change = dingFlow_display_change;
				window.devModel_display_change = devModel_display_change;
				/* window.ldingOmsIn_display_change = ldingOmsIn_display_change; */
				window.external_display_change= external_display_change;
				window.ding_schedule_change= ding_schedule_change;
				window.ding_cspace_change=ding_cspace_change;
				window.interactiveCardChange=interactiveCardChange;
			});
			
			seajs.use(['lui/jquery','lui/dialog'],function($, dialog){
				function repeat_register(idDelete){
					var callBackUrl = $('[name="value(dingCallbackurl)"]').val();
					var dingDomain = $('[name="value(dingDomain)"]').val();
					var dismissTips ="";
					if(idDelete){
						dismissTips = '是否确认删除钉钉端已注册回调并且注册新回调吗？';
					}else{
						dismissTips = '确认注册回调:<br/>'+callBackUrl+'吗？';
					}
                    dialog.confirm(dismissTips, function(ok) {
                        if(ok == true) {
                        	var del_load = dialog.loading();
                			var url ="${LUI_ContextPath}/resource/third/ding/endpoint.do?method=repeatRegisterBackUrl&callBackUrl="+callBackUrl+"&idDelete="+idDelete+"&dingDomain="+dingDomain;         			
                			$.ajax({
								url : url,
								type : 'post',
								async : false,
								dataType : "json",
								success : function(data) {
									if(data.success){
										dialog.alert(data.message);
										location.reload();
									}else{
										dialog.alert(data.message);
										location.reload();
									}
								} ,
								error : function(req) {
									
								}
						});
                 	 }
                	});
				}
				window.repeat_register = repeat_register;
			});
			window.onload = function(){
				
				var oldSyncSelection = "${oldSyncSelection}";
				var dingOmsOutEnabled="${dingOmsOutEnabled}";
				var dingOmsInEnabled="${dingOmsInEnabled}";
				var ldingEnabled="${ldingEnabled}";
				if(oldSyncSelection == null || oldSyncSelection==''){
					if(dingOmsOutEnabled=='true'){
						$("input:radio[name='value(syncSelection)'][value='1']").attr("checked",true);
					}else if(dingOmsInEnabled=='true'){
						$("input:radio[name='value(syncSelection)'][value='2']").attr("checked",true);
					}else if(ldingEnabled== 'true'){
						$("input:radio[name='value(syncSelection)'][value='4']").attr("checked",true);
					}else{
						$("input:radio[name='value(syncSelection)'][value='0']").attr("checked",true);
					}
				}
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
			
			window.checkPositionSyn=function(target){
				var val = $('[name="value(org2ding.position)"]').val();
				if(val == "hbmPosts"){
					$("#org2ding_position_order").css("display","");
				}else{
					$("#org2ding_position_order").css("display","none");
				}
			}
			
			
			//查询所有select
			var $allSyn = $('table').find("select[type='synWay']");
            $allSyn.each(function(index,element){
            	checkSyn(this);
    		 });
            
            function changeTodoAgentId(target){
            	var val=target.value;
            	if(val == ""){
            		return;
            	}
            	var $defaultAgentId = $("span[name='defaultAgentId']");
            	$defaultAgentId.text(val);
            }
            
            function switchMobileHide(val){
            	if("true" == val){
            		$("div[name='org2dingMobileIsHide']").css("display","none");
            	}else{
            		$("div[name='org2dingMobileIsHide']").css("display","block");
            	}
            }

			function swithExclusiveAccount(val){
				if("true" == val){
					$("div[name='org2dingIsExclusiveAccount']").css("display","none");
				}else{
					$("div[name='org2dingIsExclusiveAccount']").css("display","block");
				}
			}

			function switchexclusiveAccountType(v){
				//同步方式
				var syncSelection;
				var _type = document.getElementsByName("value(syncSelection)");
				for(var i = 0; i < _type.length; i++) {
					if(_type[i].checked) {
						syncSelection = _type[i].value;
						break;
					}
				}

				var exclusiveAccountEnable = $('[name="value(exclusiveAccountEnable)"]').val()

				if(syncSelection == "1" && exclusiveAccountEnable == "true"){
					var _val;
					var _type = document.getElementsByName("value(org2ding.exclusiveAccount.type)");
					for(var i = 0; i < _type.length; i++) {
						if(_type[i].checked) {
							_val = _type[i].value;
							break;
						}
					}
					if("sso" == _val){
						$("[name='exclusiveAccountEnable_item_dingtalk']").hide();
						$("input[name='value(org2ding.exclusiveAccount.password)']").attr("disabled",true);
					}else if("dingtalk" == _val){
						$("[name='exclusiveAccountEnable_item_dingtalk']").show();
						$("input[name='value(org2ding.exclusiveAccount.password)']").attr("disabled",false);
					}
				}else{
					$("[name='exclusiveAccountEnable_item_dingtalk']").hide();
					$("input[name='value(org2ding.exclusiveAccount.password)']").attr("disabled",true);
				}

			}

            function switchGroupHide(val){
            	if("true" == val){
            		$("div[name='org2dingGroupCreate']").css("display","none");
            	}else{
            		$("div[name='org2dingGroupCreate']").css("display","block");
            	}
            }
            
            function switchGroupContainSubDeptHide(val){
            	if("true" == val){
            		$("div[name='groupContainSubDeptProps']").css("display","none");
            	}else{
            		$("div[name='groupContainSubDeptProps']").css("display","block");
            	}
            }

			exclusiveAccountEnable_switch("${sysAppConfigForm.getValue('exclusiveAccountEnable')}");
			switchexclusiveAccountType("${sysAppConfigForm.getValue('org2ding.exclusiveAccount.type')}");
			swithExclusiveAccount("${sysAppConfigForm.getValue('org2ding.isExclusiveAccount.all')}");
            switchMobileHide("${sysAppConfigForm.getValue('org2ding.isHide.all')}");
            switchGroupHide("${sysAppConfigForm.getValue('org2ding.dept.group.all')}");
            switchGroupContainSubDeptHide("${sysAppConfigForm.getValue('org2ding.dept.groupContainSubDept.all')}");
            checkPositionSyn();

		</script>
	</template:replace>	
</template:include>