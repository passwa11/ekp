<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page
	import="com.landray.kmss.util.SpringBeanUtil,
                java.util.List,
                com.landray.kmss.sys.authentication.identity.plugin.IdentityPlugin,
                com.landray.kmss.sys.authentication.identity.plugin.IdentityValidatePluginData,
                java.util.Map,
                java.util.TreeMap,
                com.landray.kmss.sys.authentication.identity.plugin.IdentityValidatePlugin,
                com.landray.kmss.sys.authentication.identity.plugin.IdentityValidateData,
                com.landray.kmss.sys.authentication.identity.plugin.AuthConfigUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
       List<IdentityValidatePluginData> identityList = IdentityPlugin.getExtensionList();
		Map<String, String> returnMap = new TreeMap<String, String>();
		for(IdentityValidatePluginData data : identityList) {
			String key = String.valueOf(data.getKey());
			String value =data.getName();
			returnMap.put(key, value);
		}
		request.setAttribute("validateTypeMap",returnMap);
		
		List<IdentityValidateData> identityListByAuth = IdentityValidatePlugin.getExtensionListByAuthModule();
		request.setAttribute("identityList", identityListByAuth);
		
		request.setAttribute("isAgent", AuthConfigUtil.isAgent());//是否代理人
		request.setAttribute("islegalPerson", AuthConfigUtil.islegalPerson());//是否法人
		request.setAttribute("isOtherPerson", AuthConfigUtil.isOtherPerson());//是否有其他配置

%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.PasswordSecurityConfiguration')}</template:replace>
	<template:replace name="head">
		<script>
			var DesEncryptUtil = null;
			LUI.ready(function() {
				var security_config_desEncrypt = 'sys/profile/maintenance/js/security_config_desEncrypt.js';
				seajs.use([ security_config_desEncrypt ], function(desEncrypt) {
					desEncrypt.init({});
					DesEncryptUtil = desEncrypt;
				})
			})

		</script>
	</template:replace>
	<template:replace name="content">
	<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/org.css"/>
		<h2 align="center" style="margin:10px 0">
			<span class="profile_config_title">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.PasswordSecurityConfiguration')}</span>
		</h2>
		
		<html:form action="/sys/profile/passwordSecurityConfig.do" onsubmit="return validateAppConfigForm(this);">
			<ui:tabpanel layout="sys.ui.tabpanel.security">
				<!-- 账户安全 -->
				<ui:content id="tag1" title="${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.tag1')}">
					<table class="tb_normal" width=95%>
						<!-- 登录设置 -->
						<tr>
							<td class="td_normal_title" colspan=2>
								<b><label>${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.loginSetting')}</label></b>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.loginCode')}</td>
							<td>
								<ui:switch property="value(kmssVerifycodeEnabled)" checked="${passwordSecurityConfig.kmssVerifycodeEnabled}" onValueChange="config_verifytimes_chgEnabled();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<br>
								<div id='lab_verifytimes' style="display:none;">
								 <span class="message">&nbsp;${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.loginCode.descripton1')}
								 <xform:text property="value(kmssVerifycodeTimes)" value="${passwordSecurityConfig.kmssVerifycodeTimes}" style="width:50px;" showStatus="edit" required="true" subject="${lfn:message('sys-ui:sys.profile.org.passwordSecurityConfig.numberOfVerification')}" validators="digits min(0)"/>
								 ${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.loginCode.descripton2')} <br>
								  &nbsp;${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.loginCode.descripton3')} 
								 <xform:text property="value(kmssVerifycodeForce)" value="${passwordSecurityConfig.kmssVerifycodeForce}" style="width:50px;" showStatus="edit" required="true" subject="${lfn:message('sys-ui:sys.profile.org.passwordSecurityConfig.numberOfWrongUsers')}" validators="digits min(1)"/>
								${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.loginCode.descripton4')} </span>
							    </div>
							</td>
						</tr>
						<c:import url="/sys/profile/maintenance/third_defaultlogin_config.jsp" charEncoding="utf-8"></c:import>
						<c:import url="/sys/profile/maintenance/third_login_config.jsp" charEncoding="utf-8"></c:import>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.numberLock')} </td>
							<td>
								<ui:switch property="value(kmssAuthlockEnabled)" checked="${passwordSecurityConfig.kmssAuthlockEnabled}" onValueChange="config_authlock_chgEnabled();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<br>
								<div id='lab_authlock' style="display:none;">
								 <span class="message">&nbsp;${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.numberLock.descripton1')}
								 <xform:text property="value(kmssAuthlockErrorinterval)" value="${passwordSecurityConfig.kmssAuthlockErrorinterval}" style="width:50px;" showStatus="edit" required="true" subject="验证时长" validators="digits min(1)"/>
								 ${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.numberLock.descripton2')}
								 <xform:text property="value(kmssAuthlockTimes)" value="${passwordSecurityConfig.kmssAuthlockTimes}" style="width:50px;" showStatus="edit" required="true" subject="验证次数" validators="digits min(1)"/>
								 ${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.numberLock.descripton3')}
								 <xform:text property="value(kmssAuthlockLockinterval)" value="${passwordSecurityConfig.kmssAuthlockLockinterval}" style="width:50px;" showStatus="edit" required="true" subject="锁定时长" validators="digits min(1)"/>
								${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.numberLock.descripton4')}<br>
								&nbsp;${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.numberLock.descripton5')}
								 </span>
							    </div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title">
								<b><label><bean:message  bundle="sys-profile" key="sys.profile.browserCheck.config" /></label></b>
								<br><font color="red"><bean:message  bundle="sys-profile" key="sys.profile.browserCheck.config.tips" /></font>
							</td>
							<td>
								<ui:switch property="value(kmssBrowserCompatibleEnable)" checked="${passwordSecurityConfig.kmssBrowserCompatibleEnable}" onValueChange="config_BrowserCompatible_chgEnabled();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<b><font id="kmssBrowserCompatible_warning" color="red"></font></b>
							</td>
						</tr>
						<tr name="lab_BrowserCompatible" style="display: none;">
							<td class="td_normal_title" width="35%"><bean:message  bundle="sys-profile" key="sys.profile.browserCheck.config.compatible" /></td>
							<td>
								 <xform:checkbox property="value(kmssBrowserCompatible)" value="${passwordSecurityConfig.kmssBrowserCompatible}" showStatus="edit">
								 	<xform:simpleDataSource value="Chrome">Chrome</xform:simpleDataSource>
								 	<xform:simpleDataSource value="Opera">Opera</xform:simpleDataSource>
								 	<xform:simpleDataSource value="IE">IE 8+</xform:simpleDataSource>
								 	<xform:simpleDataSource value="IE10">IE 10+</xform:simpleDataSource>
								 	<xform:simpleDataSource value="Edge">Edge</xform:simpleDataSource>
								 	<xform:simpleDataSource value="Firefox">Firefox</xform:simpleDataSource>
								 	<xform:simpleDataSource value="Safari">Safari</xform:simpleDataSource>
								 </xform:checkbox>
							</td>
						</tr>
						<tr name="lab_BrowserCompatible" style="display: none;">
							<td class="td_normal_title" width="35%"><bean:message  bundle="sys-profile" key="sys.profile.browserCheck.config.custom" /></td>
							<td>
								<ui:button text="${lfn:message('button.edit')}" onclick="addCustomBrowser();"></ui:button>
								<input type="hidden" name="value(kmssCustomBrowser)" value="${passwordSecurityConfig.kmssCustomBrowser}">
								<br>
								<span id="customBrowser_span"></span>
							</td>
						</tr>
						
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.newUserGuidePageEnabled')}</td>
							<td>
								<ui:switch property="value(newUserGuidePageEnabled)" checked="${passwordSecurityConfig.newUserGuidePageEnabled}" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							</td>
						</tr>
						<!-- 加密设置 -->
						<tr>
							<td class="td_normal_title" colspan=2>
								<b><label>${lfn:message('sys-profile:sys.profile.request.encryp.setting')}</label></b>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%">SM2</td>
							<td>
								<xform:text property="value(security.sm2.pubKey)" showStatus="readOnly"></xform:text><input type="button" onclick="DesEncryptUtil.generateSm2Key();" value="${lfn:message('sys-profile:sys.profile.reset.pubKey.priKey')}">
							</td>
						</tr>
						<!-- 密码策略 -->
						<tr>
							<td class="td_normal_title" colspan=2>
								<b><label>${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.passwordSafety')}</label></b>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.passwordLength')}</td>
							<td>
								<div style="float: left;"><xform:text property="value(kmssOrgPasswordlength)" value="${passwordSecurityConfig.kmssOrgPasswordlength}" required="true" validators="required digits min(1) max(20)" showStatus="edit"></xform:text></div>
									<div class="pwdTip PwdTip" style="float: left;">
											<p class="icon"><span></span></p>
											<p class="textTip" style="color: #A9A9A9"></p>
									</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.passwordStrength')}</td>
							<td>
								<label>
								<xform:radio property="value(kmssOrgPasswordstrength)" value="${passwordSecurityConfig.kmssOrgPasswordstrength}" showStatus="edit">
								 	<xform:simpleDataSource value="1">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.passwordStrength.type1')}</xform:simpleDataSource>
								 	<xform:simpleDataSource value="2">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.passwordStrength.type2')}</xform:simpleDataSource>
								 	<xform:simpleDataSource value="3">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.passwordStrength.type3')}</xform:simpleDataSource>
								 	<xform:simpleDataSource value="4">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.passwordStrength.type4')}</xform:simpleDataSource>
								 </xform:radio>
								</label>
								<br>
								<span class="message">&nbsp;${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.passwordStrength.descripton')}</span>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%" rowspan="2">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.forcesChangePassword')}</td>
							<td>
								<ui:switch property="value(kmssOrgPasswordchange)" checked="${passwordSecurityConfig.kmssOrgPasswordchange}" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<br>
								<span class="message">
									 &nbsp;${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.forcesChangePassword.descripton')}
								</span>
							</td>
						</tr>
						<tr>
							<td>
								<label>
									<span class="message">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.forcesChangePassword.descripton1')}
									<xform:text property="value(kmssOrgPasswordchangeday)" value="${passwordSecurityConfig.kmssOrgPasswordchangeday}" validators="digits min(0)" showStatus="edit" style="width:50px;"></xform:text>
									${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.forcesChangePassword.descripton2')}</span>
								</label>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.regularlyRemindUser')}</td>
							<td>
								<label>
								<span class="message">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.regularlyRemindUser.descripton1')}
									<xform:text property="value(kmssOrgPasswordremindday)" value="${passwordSecurityConfig.kmssOrgPasswordremindday}" validators="digits min(0)" showStatus="edit" style="width:50px;"></xform:text>
									${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.regularlyRemindUser.descripton2')}</span>
								</label>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.newUserForceChangePwdEnabled')}</td>
							<td>
								<ui:switch property="value(newUserForceChangePwdEnabled)" checked="${passwordSecurityConfig.newUserForceChangePwdEnabled}" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.passwordCleaTextSubmit')}</td>
							<td id="login_plaintext_disabled">
								<ui:switch property="value(loginPlaintextDisabled)" checked="${passwordSecurityConfig.loginPlaintextDisabled}" text="${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.passwordCleaTextSubmit.ForbiddenCleaTextSubmit')}"></ui:switch>
								<br>
								<span class="message">&nbsp;${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.passwordCleaTextSubmit.descripton')}</span>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.usernameCleaTextSubmit')}</td>
							<td id="login_Account_plaintext_disabled">
								<ui:switch property="value(loginAccountPlaintextDisabled)" checked="${passwordSecurityConfig.loginAccountPlaintextDisabled}" text="${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.usernameCleaTextSubmit.ForbiddenCleaTextSubmit')}"></ui:switch>
								<br>
								<span class="message">&nbsp;${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.usernameCleaTextSubmit.descripton')}</span>
							</td>
						</tr>	
						
						<!-- 个人设置 -->
						<tr>
							<td class="td_normal_title" colspan=2>
								<b><label>${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.personalSettings')}</label></b>
							</td>
						</tr>
						<tr>
						  <td class="td_normal_title" width=35%>
							 ${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.modifyMobile')}
						  </td>
						  <td colspan="3">
							<label>
								<xform:radio property="value(mobileNoUpdateCheckEnable)" value="${passwordSecurityConfig.mobileNoUpdateCheckEnable}" showStatus="edit">
								 	<xform:simpleDataSource value="false">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.directlyModify')}</xform:simpleDataSource>
								 	<xform:simpleDataSource value="true">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.verifyPasswordModify')}</xform:simpleDataSource>
								 </xform:radio>
							</label>
						  </td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.alterEmailEnabled')}</td>
							<td id="alter_email_enabled">
								<ui:switch property="value(alterEmailEnabled)" checked="${passwordSecurityConfig.alterEmailEnabled}" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<br>
								<span class="message">&nbsp;${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.alterEmailEnabled.descripton')}</span>
							</td>
						</tr>
						<kmss:ifModuleExist  path = "/elec/authentication/">
						<!-- 身份认证-->
						<tr>
							<td class="td_normal_title" colspan=2>
								<b><label>${lfn:message('sys-authentication-identity:identity.auth')}</label></b>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title"  width="35%">${lfn:message('sys-profile:sys.profile.authentication.personal.realname.auth')}</td>
							</td>
							<td>
								<table class="tb_normal" width="95%" 
									id="personId" style="margin:0">
									<tr>
										<td colspan="2"><ui:switch property="value(elecAuth.person.enabled)"
												onValueChange="elecAuthPersonalEnabledChange()"
												enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
												disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch><br>
										</td>
									</tr>
									<tr>
										<td class="td_normal_title" width="150px">
												${lfn:message('sys-profile:sys.profile.authentication.elements.configuration')}<span class="txtstrong">*</span>
										</td>
										<td>
											<xform:radio property="value(person.auth.Element)"
												alignment="V" required="true" subject="${lfn:message('sys-profile:sys.profile.authentication.personal.elements.configuration')}" showStatus="edit"
												style="height: 22px;vertical-align: middle;" >
												<xform:customizeDataSource
							                      className="com.landray.kmss.sys.authentication.identity.plugin.IdentifyPersonConfigBean">
						                        </xform:customizeDataSource>
											</xform:radio>
											<c:if test="${isOtherPerson }">
												<table class="tb_normal" width="95%" style="margin: 0">
													<tr>
														<td class="td_normal_title" width="150px">${lfn:message('sys-profile:sys.profile.authentication.ohter.configuration')}</td>
														<td><xform:radio property="value(person.will.certMethod)"
																required="true"
																alignment="V" subject="${lfn:message('sys-profile:sys.profile.authentication.ohter.configuration')}" showStatus="edit">
																<xform:customizeDataSource
											                      className="com.landray.kmss.sys.authentication.identity.plugin.OtherPersonConfigBean">
										                        </xform:customizeDataSource>
															</xform:radio>
															</td>
													</tr>
												</table>
											</c:if>
										</td>
									</tr>
									<tr>
						              	<td class="td_normal_title" width="150px">${lfn:message('sys-profile:sys.profile.authentication.apply.objects.configuration')}</td>
						                <td>
						                	<div id="_xform_fdCaManagerId" _xform_type="address">
												<xform:address propertyId="value(elec.fdAuthManagerIds)" 
													propertyName="value(fdCaManagerNames)" mulSelect="true" orgType="ORG_TYPE_PERSON"
													showStatus="edit" textarea="true" style="width:95%;" subject="${lfn:message('sys-profile:sys.profile.authentication.admin')}" />
											</div>
										</td>
					              </tr>
								</table>
							</td>
					    </tr>
					    <tr>
							<td class="td_normal_title"  width="35%">${lfn:message('sys-profile:sys.profile.authentication.enterprise.realname.auth')}</td>
						<td>
							<table class="tb_normal" width="95%" id="priseId" style="margin:0">
								<tr>
									<td colspan="2">
										<ui:switch property="value(elecAuth.prise.enabled)"
											onValueChange="elecAuthPriseEnabledChange()"
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
											disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
										</ui:switch><br>
									</td>
							   </tr>
							   <tr class="tr_add_inner_prise">
							   		<td colspan="2">
							   			<ui:button style="float:left" text="${lfn:message('sys-profile:sys.profile.authentication.button.quick.add.enterprise.auth')}" onclick="openPriseDialog()"></ui:button>
											<br>
							   		</td>
							   </tr>
							   <tr>
									<td class="td_normal_title" width="150px">${lfn:message('sys-profile:sys.profile.authentication.elements.configuration')}</td>
									<td>
										<xform:radio property="value(prise.auth.Element)"
											alignment="V" subject="${lfn:message('sys-profile:sys.profile.authentication.elements.configuration')}" showStatus="edit" required="true"
											onValueChange="elementValueChange">
											<xform:simpleDataSource value="2">${lfn:message('sys-profile:sys.profile.authentication.elements.configuration.four')}</xform:simpleDataSource>
										</xform:radio>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="150px">${lfn:message('sys-profile:sys.profile.authentication.auth.type.configuration')}</td>
									<td>
										<div>
											<xform:radio subject="${lfn:message('sys-profile:sys.profile.authentication.enterprtise.authhenType.configuration')}" required="true"
												property="value(enterprtise.authhenType)" showStatus="edit"
												 alignment="V">
												<c:if test="${isAgent==true}">
										   			<xform:simpleDataSource value="1">${lfn:message('sys-profile:sys.profile.authentication.enterprtise.authhenType.1')}<br>
														<div id="sendTypeId2" style="left:100px;display:inline-block;">
															<table>
																<tr>
																	<td style="min-width:140px; vertical-align: top;">
																		<span style="float:left">${lfn:message('sys-profile:sys.profile.msg.attorney.submit.type')}</span>
																	</td>
																	<td>
																		<xform:radio property="value(enterprise.priseSendType)"
																			alignment="V" showStatus="edit">
																			<xform:customizeDataSource
														                      	className="com.landray.kmss.sys.authentication.identity.plugin.PriseSendTypeConfigBean">
													                        </xform:customizeDataSource>
																		</xform:radio>
																	</td>
																</tr>
															</table>
														</div>
														<br>
													</xform:simpleDataSource>
													<br>
												 </c:if>
												 <c:if test="${islegalPerson==true}">
												    <xform:simpleDataSource value="2">${lfn:message('sys-profile:sys.profile.authentication.enterprtise.authhenType.2')}<br>
													</xform:simpleDataSource>
												 </c:if>
											</xform:radio>
										</div>
										<div>${lfn:message('sys-profile:sys.profile.msg.auth.type.configuration')}</div>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="150px">${lfn:message('sys-profile:sys.profile.authentication.small.money')}</td>
									<td>
										<ui:switch property="value(payment.enabled)"
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
											disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
										</ui:switch>
										<br>
										<div>${lfn:message('sys-profile:sys.profile.msg.small.money.line.1')}</div>
										<div>${lfn:message('sys-profile:sys.profile.msg.small.money.line.2')}</div>
										<div>${lfn:message('sys-profile:sys.profile.msg.small.money.line.3')}</div>
									</td>
								</tr>
							</table>
							<div>${lfn:message('sys-profile:sys.profile.msg.enterprise.realname.auth')}</div>
						</td>
					  	</tr>
						</kmss:ifModuleExist>
					    <!-- 身份校验-->
						<tr>
							<td class="td_normal_title" colspan=2>
								<b><label>${lfn:message('sys-authentication-identity:identity.authenVerify')}</label></b>
							</td>
						</tr>
						<tr>
						<td class="td_normal_title"  width="35%">${lfn:message('sys-profile:sys.profile.authentication.identity.type.setting')}</td>
						</td>
						<td><xform:checkbox subject="${lfn:message('sys-profile:sys.profile.authentication.identity.type.setting')}"
								property="value(Identity.verifi.type)" alignment="V"
								showStatus="edit">
								<xform:customizeDataSource
									className="com.landray.kmss.sys.authentication.identity.taglib.ValidateTypeBean">
								</xform:customizeDataSource>
							</xform:checkbox></td>
					    </tr>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.authentication.identity.exception.setting')}</td>
							<td><table class="tb_normal" width="100%"
									id="exceptionConfigId2">
									<c:forEach items="${validateTypeMap}" var="mymap">
										<c:if test="${mymap.key ne 'faceAuthenVerify' }">
											<tr>
												<td class="td_normal_title" width="15%">${mymap.value}</td>
												<td width="85%">${lfn:message('sys-profile:sys.profile.msg.dancijiaoyanzai')}<xform:text showStatus="edit"
														property="value(Identity.Exception.num_${mymap.key})"
														style="width:30px" subject="${mymap.value}${lfn:message('sys-profile:sys.profile.msg.yichangcishu')}"
														validators="required digits min(1) max(9)" />${lfn:message('sys-profile:sys.profile.msg.cishiweiyichang')}</td>
											</tr>
										</c:if>
									</c:forEach>
								</table></td>
						</tr>
						<tr>
						<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.authentication.identity.account.freeze')}</td>
						<td width="85%"><ui:switch
								property="value(Identity.Is.DongJie)"
								onValueChange="enableChangedDongJie(this.checked)"
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
								disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch><br>
							<div id="dongJieId">
								<span>${lfn:message('sys-profile:sys.profile.authentication.identity.freeze.time')}</span>
								<xform:text showStatus="edit"
									property="value(Identity.dongJie.time)" validators="required digits min(1)"  style="width:70px"
									subject="${lfn:message('sys-profile:sys.profile.authentication.identity.freeze.time')}" />
								<xform:select property="value(Identity.dongJie.Unit)"
									subject="${lfn:message('sys-profile:sys.profile.authentication.identity.freeze.unit')}" showStatus="edit" required="true">
									<xform:simpleDataSource value="1">${lfn:message('sys-profile:sys.profile.msg.unit.hour')}</xform:simpleDataSource>
									<xform:simpleDataSource value="2">${lfn:message('sys-profile:sys.profile.msg.unit.day')}</xform:simpleDataSource>
								</xform:select>
							</div></td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">${lfn:message('sys-profile:sys.profile.authentication.identity.priority.serial')}</td>
						<td><table class="tb_normal" width="100%" id="priority">
								<tr>
									<td colspan="2"><ui:switch
											property="value(Identity.priority.enabled)"
											onValueChange="enablePriorityChanged(this.checked)"
											enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
											disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									</td>
								</tr>
								
								<c:forEach items="${validateTypeMap}" var="mymap" > 
                                  <tr>
									<td class="td_normal_title" width="15%">${mymap.value}</td>
									<td width="85%"><xform:text showStatus="edit"
											property="value(Identity.order_${mymap.key})" style="width:150px"
											subject="${mymap.value}" validators="digits min(1) max(9)" /><br>
									</td>
								</tr>
                                </c:forEach> 
							</table></td>
					</tr>	
					<c:if test="${!empty identityList}">
		                 <!-- 实名校验方式-->
						<tr>
							<td class="td_normal_title" colspan=2>
								<b><label>${lfn:message('sys-profile:sys.profile.authentication.realName.check')}</label></b>
							</td>
						</tr>
						<tr>
						<td class="td_normal_title"  width="35%">${lfn:message('sys-profile:sys.profile.authentication.realName.check.type')}</td>
						</td>
						<td>
						<ui:switch property="value(Identity.verify.auth.Enabled)" checked="${passwordSecurityConfig.identityVerifyAuthEnabled}" onValueChange="identityEnabledChange();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						<br>
						<div id="indentifyAuthDiv3">
						   <xform:checkbox subject="${lfn:message('sys-profile:sys.profile.authentication.identity.type.setting')}"
								property="value(Identity.verify.auth.type)" alignment="V"
								showStatus="edit">
								<xform:customizeDataSource
									className="com.landray.kmss.elec.core.authentication.plugin.IdentifyValidateTypeByAuthBean">
								</xform:customizeDataSource>
							</xform:checkbox>
						</div>
					    <div id="indentifyAuthDiv1">${lfn:message('sys-profile:sys.profile.msg.indentifyAuthDiv1')}</div>
					    <div id="indentifyAuthDiv2">${lfn:message('sys-profile:sys.profile.msg.indentifyAuthDiv2')}</div>
					  </td>
					    </tr>
		           </c:if>
						<!-- 其他 -->
						<%-- <tr>
							<td class="td_normal_title" colspan=2>
								<b><label>${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.other')}</label></b>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=35%>
								${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.other.loginType')} 
						  	</td>
							<td colspan="3">
								<c:import url="/sys/profile/maintenance/third_login_config.jsp"></c:import>
							</td>
						</tr> --%>
					</table>
					<center style="margin:10px 0;">
						<!-- 保存 -->
						<ui:button text="${lfn:message('button.save')}" suspend="bottom" height="35" width="120" onclick="formSubmit();"></ui:button>
					</center>
				</ui:content>
				<!-- 系统安全 -->
				<ui:content id="tag2" title="${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.tag2')}" toggle="alert(5);">
					<table class="tb_normal" width=95%>
						<!-- 系统安全 -->
						<tr>
							<td class="td_normal_title" colspan=2>
								<b><label>${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.systemSafety')}</label></b>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.SessionSecurityProtection')}</td>
							<td>
								<ui:switch property="value(kmssSessionCheck)" checked="${passwordSecurityConfig.kmssSessionCheck}" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<br>
								<span class="message">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.SessionSecurityProtection.descripton')}</span>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.TrustTheSiteCheck')}</td>
							<td>
								<ui:switch property="value(kmssRedirecttoCheck)" checked="${passwordSecurityConfig.kmssRedirecttoCheck}" onValueChange="config_redirectto_chgEnabled();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<br>
								<span class="message">
									${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.TrustTheSiteCheck.descripton1')}
								</span>
								<div id="redirectto_div" style="display: none;">
									${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.TrustTheSiteCheck.TrustedSites')}
									<xform:text property="value(kmssRedirecttoAllowdomainnames)" value="${passwordSecurityConfig.kmssRedirecttoAllowdomainnames}" showStatus="edit" style="width:90%"></xform:text><br>
									<span class="message">
									${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.TrustTheSiteCheck.descripton2')}
									</span>
								</div>
								<div id="requestCheckIfUnknownReferer" style="display: none;margin: 20px 0">
									<ui:switch property="value(kmssRequestCheckIfUnknownReferer)" checked="${passwordSecurityConfig.kmssRequestCheckIfUnknownReferer}" onValueChange="config_kmssRequestCheckIfUnknownReferer_chgEnabled();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
									<span class="message">
										${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.TrustTheSiteCheck.UnknownReferer.descripton1')}
										<br/>
										${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.TrustTheSiteCheck.UnknownReferer.descripton2')}
									</span>
								</div>
								<div id="whiteListIfUnknownReferer" style="display: none">
									<xform:textarea property="value(whiteListIfUnknownReferer)" style="width:95%" value="${passwordSecurityConfig.whiteListIfUnknownReferer}"></xform:textarea>
									<br/><span class="message">
										${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.TrustTheSiteCheck.UnknownReferer.descripton3')}
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.HardMode')}</td>
							<td>
								<ui:switch property="value(kmssHardMode)" checked="${passwordSecurityConfig.kmssHardMode}" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<br>
								<span class="message">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.HardMode.descripton')}</span>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.NoCacheMode')}</td>
							<td>
								<ui:switch property="value(kmssNoCacheMode)" checked="${passwordSecurityConfig.kmssNoCacheMode}" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<br>
								<span class="message">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.NoCacheMode.descripton')}</span>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.kmssHttpsSign')}</td>
							<td>
								<ui:switch property="value(kmssHttpsSign)" checked="${passwordSecurityConfig.kmssHttpsSign}" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<br>
								<span class="message">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.kmssHttpsSign.descripton')}</span>
							</td>
						</tr>																			
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.ErrorStackOutput')}</td>
							<td>
								<ui:switch property="value(kmssErrorStackDisabled)" checked="${passwordSecurityConfig.kmssErrorStackDisabled}" text="${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.ErrorStackOutput.descripton')}"></ui:switch>
							</td>
						</tr>
						
						<!-- 安全加固 -->
						<tr>
							<td class="td_normal_title" colspan=2>
								<b><label>${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.safetyReinforcement')}</label></b>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.whetherRetrievePasswordFeature')}<br><font color="red">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.whetherRetrievePasswordFeature.note')}</font></td>
							<td>
								<ui:switch property="value(retrievePasswordEnable)" checked="${passwordSecurityConfig.retrievePasswordEnable}" onValueChange="config_retrievePassword_chgEnabled();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.doubleAuthEnable')}<br><font color="red">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.doubleAuthEnable.note')}</font></td>
							<td>
								<div style="display:inline-block;width:100px;">
									<ui:switch property="value(doubleAuthEnable)" checked="${passwordSecurityConfig.doubleAuthEnable}" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								</div>
								<ui:button text="${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.quickFactorSetting')}" onclick="quickFactorSetting();"></ui:button>
							</td>
						</tr>
						<input type="hidden" name="value(policyType)" value="${passwordSecurityConfig.policyType}"/>
						<%-- <tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.policyType')}</td>
							<td>
								<label>
									<xform:radio property="value(policyType)" value="${passwordSecurityConfig.policyType}" showStatus="edit">
									 	<xform:simpleDataSource value="allEnable">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.policyType.allEnable')}</xform:simpleDataSource>
									 	<xform:simpleDataSource value="allDisable">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.policyType.allDisable')}</xform:simpleDataSource>
									 	<xform:simpleDataSource value="network">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.policyType.network')}</xform:simpleDataSource>
									 </xform:radio>
								</label>
							</td>
						</tr> --%>
						<tr>
							<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.SMSReceive')}<br><font color="red">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.SMSReceive.note')}</font></td>
							<td>
								<ui:switch property="value(smsReceiveEnable)" checked="${passwordSecurityConfig.smsReceiveEnable}" onValueChange="config_smsReceive_chgEnabled();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
								<br>
							    <table id="lab_smsReceive"  align="center" style="width: 95%;line-height:50px;">
									<tr>
										<td class="td_normal_title" style="text-align:left;">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.whetherRetrievePasswordFeature.timeIntervalForResending')}</td>
										<td>
											<xform:text property="value(reSentIntervalTime)" showStatus="edit" style="width:50px;" value="${passwordSecurityConfig.reSentIntervalTime }"></xform:text>${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.whetherRetrievePasswordFeature.timeIntervalForResendin.second')}
										</td>
									</tr>
									<tr>
										<td class="td_normal_title" style="text-align:left;">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.whetherRetrievePasswordFeature.verificationCodeValid')}</td>
										<td>
											<xform:text property="value(codeEffectiveTime)" showStatus="edit" style="width:50px;" value="${passwordSecurityConfig.codeEffectiveTime }"></xform:text>${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.whetherRetrievePasswordFeature.verificationCodeValid.minute')}
										</td>
									</tr>
									<tr>
										<td class="td_normal_title" style="text-align:left;">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.maximumNumberOfTimesADayToSend')}</td>
										<td>
											<xform:text property="value(maxTimesOneDay)" showStatus="edit" style="width:50px;" value="${passwordSecurityConfig.maxTimesOneDay }"></xform:text>${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.maximumNumberOfTimesADayToSend.times')}
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<center style="margin:10px 0;">
						<!-- 保存 -->
						<ui:button text="${lfn:message('button.save')}" suspend="bottom" height="35" width="120" onclick="formSubmit();"></ui:button>
					</center>
				</ui:content>
				<!-- 网段策略 -->
				<ui:content id="tag3" title="${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.tag3')}">
					<c:import url="/sys/profile/sys_profile_network_strategy/index.jsp" charEncoding="utf-8"></c:import>
				</ui:content>
				<!-- 敏感词配置 -->
				<ui:content id="tag4" title="${lfn:message('sys-profile:sys.profile.sensitive.word.setting')}">
					<ui:event event="show">
						document.getElementById('senWordConfig').src = '<c:url value="/sys/profile/sysCommonSensitiveConfig.do" />?method=edit&modelName=com.landray.kmss.sys.profile.model.SysCommonSensitiveConfig';
					</ui:event>
					<iframe id="senWordConfig" width="100%" height="1000" frameborder=0 scrolling=no></iframe>
				</ui:content>
				<ui:content id="tag5" title="${lfn:message('sys-profile:sys.profile.passwordSecurityConfig.self.check')}">
					<c:import url="/sys/profile/securityScan.jsp" charEncoding="utf-8"></c:import>
				</ui:content>
			</ui:tabpanel>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.profile.model.PasswordSecurityConfig" />
			<input type="hidden" name="autoclose" value="false" />
		</html:form>
		
	 	<script type="text/javascript">
			$KMSSValidation();
			var validation = null;
			$(function() {
				  $(":radio").click(function(){
					  var strength=$(this);
				      var length=$("input[name='value\\\(kmssOrgPasswordlength\\\)']");
				      checkPass(length,strength);
				  });
				  $("input[name='value\\\(kmssOrgPasswordlength\\\)']").blur(function(){
					  var length=$(this);
					  var strength= $(":radio:checked");
	                         checkPass(length,strength);
					 });
	
				  $("input[name='value\\\(kmssOrgPasswordlength\\\)']").click(function(){
					  var length=$(this);
					  var strength= $(":radio:checked");
					  promptPass(length,strength);
					 });
				  
				  // 初始化自定义兼容浏览器
				  <c:if test="${!empty passwordSecurityConfig.kmssCustomBrowser}">
				  initCustomBrowser();
				  </c:if>
			});
			//快捷设置双因子策略
			function quickFactorSetting() {
				seajs.use( [ 'lui/dialog','lui/jquery' ], function(dialog,$) {
					var url = "/sys/profile/maintenance/quick_factor_setting.jsp";
            		dialog.iframe(url,"${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.quickFactorSetting')}", function(value) {
    				}, {
    					"width" : 800,
    					"height" : 450
    				});
				});
			}
			
			function formSubmit() {
				var customBrowsers = "";
				$.each($("#customBrowser_span input:checked"), function(i, n) {
					customBrowsers += $(n).attr("data-val") + ";";
				});
				$("input[name='value\\\(kmssCustomBrowser\\\)']").val(customBrowsers);
				
				var _isCheck = false;
				// 检测是否有开启浏览检验，并且是否有选择相应的浏览器
				if("true" == $("input[name='value\\\(kmssBrowserCompatibleEnable\\\)']").val()) {
					_isCheck = true;
					// 开启了浏览器检测，但是又没有选择可访问的浏览器，需要出现警告，否则任何浏览器都无法访问系统
					if($("tr[name=lab_BrowserCompatible] input:checked").length < 1) {
						var msg = '<bean:message bundle="sys-profile" key="sys.profile.browserCheck.config.warning" />';
						$("#kmssBrowserCompatible_warning").html(msg);
						seajs.use( [ 'lui/dialog' ], function(dialog) {
							dialog.confirm(msg, function(val) {
								if(val) {
									Com_Submit(document.sysAppConfigForm, 'update');
								}
							});
						});
					} else {
						_isCheck = false;
					}
				}
				
				// 提交数据
				if(!_isCheck)
					Com_Submit(document.sysAppConfigForm, 'update');
			}
			
			function initCustomBrowser() {
				var datas = [];
				var _customBrowsers = "${passwordSecurityConfig.kmssCustomBrowser}".split(";");
			  	$.each(_customBrowsers, function(i, n) {
			  		if(n.length < 5) // 过滤不合法的字符
			  			return true;
			  		var customBrowser = JSON.parse(unescape(n));
			  		datas.push(customBrowser);
			  	});
			  	showCustomBrowser(datas);
			}
			
			function showCustomBrowser(datas) {
				var _browsers = [];
				$.each(datas, function(i, n) {
					_browsers.push('<label><input type="checkbox" name="_value(kmssCustomBrowser)" value="'+n.name+'" data-val="'+escape(JSON.stringify(n))+'">'+n.name+'</label>');
				});
				$("#customBrowser_span").empty().append(_browsers.join("&nbsp;"));
				$("#customBrowser_span input").attr("checked", true);
			}
			
			function addCustomBrowser() {
				seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
					dialog.iframe('/sys/profile/sys_profile_main/sysCfgProfileConfig.do?method=customBrowser',
							'<bean:message  bundle="sys-profile" key="sys.profile.browserCheck.config.custom" />', function(datas) {
						if(datas) {
							showCustomBrowser(datas);
						}
					}, {
						width : 1024,
						height : 500
					});
				});
			}
			
			function promptPass(length,strength){
				setPwdTip('PwdTip','blueIcon',"${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.passwordLengthCanNotBeLessThan')}"+" "+strength.val()+" "+"${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.character')}");
			}
				  
			 function checkPass(length,strength){
				  if(parseInt(length.val())<parseInt(strength.val())){
						   setPwdTip('PwdTip','redIcon',"${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.passwordLengthCanNotBeLessThan')}"+" "+strength.val()+" "+"${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.character')}");
						   length.val(strength.val());
					}else{
						removePwdTip('PwdTip','redIcon');
					}

			 }

			 function setPwdTip(className,icon,text){
					$('.' + className + ' .icon span').removeClass().addClass(icon);
					$('.' + className + ' .textTip').html(text);
				};

				function removePwdTip(className,icon){
					$('.' + className + ' .icon span').removeClass().removeClass(icon);
					$('.' + className + ' .textTip').html("");
				}
			
			function validateAppConfigForm(thisObj) {
				return true;
			}
		
			function config_redirectto_chgEnabled() {
				var tbObj = $("#redirectto_div");
				var isChecked = "true" == $("input[name='value\\\(kmssRedirecttoCheck\\\)']").val();
				if (isChecked) {
					tbObj.show();
					$("#requestCheckIfUnknownReferer").show();
					config_kmssRequestCheckIfUnknownReferer_chgEnabled()
				} else {
					tbObj.hide();
					$("#requestCheckIfUnknownReferer").hide();
					$("#whiteListIfUnknownReferer").hide();
				}
			}

			function config_kmssRequestCheckIfUnknownReferer_chgEnabled(){
				var enabled = $("[name='value(kmssRequestCheckIfUnknownReferer)']").val();
				if("true" == enabled){
					$("#whiteListIfUnknownReferer").show();
					var whiteList = $("[name='value(whiteListIfUnknownReferer)']").val();
					var defaultWhiteList = '${requestScope['defaultWhiteListIfUnknownReferer']}';
					if(!whiteList && defaultWhiteList){
						$("[name='value(whiteListIfUnknownReferer)']").val(defaultWhiteList);
					}
				}else{
					$("#whiteListIfUnknownReferer").hide();
				}
			}
			
			function enableChangedDongJie(){
				var value= ("true" == $("input[name='value\\\(Identity.Is.DongJie\\\)']").val());
				var tbObj =$("#dongJieId");
			    if(value){
			    	$("input[name='value\\\(Identity.dongJie.time\\\)']").removeAttr("disabled");
			    	$("select[name='value\\\(Identity.dongJie.Unit\\\)']").removeAttr("disabled");
			    	tbObj.show("fast");
			    }else{
			    	$("input[name='value\\\(Identity.dongJie.time\\\)']").attr("disabled","disabled");
			    	$("select[name='value\\\(Identity.dongJie.Unit\\\)']").attr("disabled","disabled");
			    	tbObj.hide("fast");
			    }
			
			}
			
			function changeIdenExpNum(obj){
				if($(obj).size()==1){
					if($(obj).val()==""){
						$(obj).val("5");
					}
				}
			}
			
			function enablePriorityChanged(){
				var value= ("true" == $("input[name='value\\\(Identity.priority.enabled\\\)']").val());
				var tbObj =document.getElementById("priority");
				for (var i = 1; i < tbObj.rows.length; i++) {
					tbObj.rows[i].style.display = value ? "table-row"
							: "none";
					var cfgFields = tbObj.rows[i].getElementsByTagName("INPUT");
					for (var j = 0; j < cfgFields.length; j++) {
						cfgFields[j].disabled = !value;
					}
				}
			
			}

			function config_verifytimes_chgEnabled() {
				var cfgVerifyTime = $("#lab_verifytimes");
				var isChecked = "true" == $("input[name='value\\\(kmssVerifycodeEnabled\\\)']").val();
				if (isChecked) {
					cfgVerifyTime.show();
				} else {
					cfgVerifyTime.hide();
				}

				cfgVerifyTime.find("input").each(function() {
					$(this).attr("disabled", !isChecked);
				});
			}
			
			function identityEnabledChange() {
				var isChecked = "true" == $("input[name='value\\\(Identity.verify.auth.Enabled\\\)']").val();
				if (isChecked) {//显示
				    $("#indentifyAuthDiv1").show();
				    $("#indentifyAuthDiv2").show();	
					$("#indentifyAuthDiv3").show();
				} else {//隐藏
				    $("#indentifyAuthDiv1").hide();
				    $("#indentifyAuthDiv2").hide();	
					$("#indentifyAuthDiv3").hide();
				}
			}


			function config_smsReceive_chgEnabled() {
				var cfgetrievePassword = $("#lab_smsReceive");
				var isChecked = "true" == $("input[name='value\\\(smsReceiveEnable\\\)']").val();
				if (isChecked) {
					cfgetrievePassword.show();
				} else {
					cfgetrievePassword.hide();
				}

				cfgetrievePassword.find("input").each(function() {
					$(this).attr("disabled", !isChecked);
				});
			}
			
			function config_retrievePassword_chgEnabled() {
				var isSmsReceiveEnable = "true" == $("input[name='value\\\(smsReceiveEnable\\\)']").val();
				var isChecked = "true" == $("input[name='value\\\(retrievePasswordEnable\\\)']").val();
				if(isChecked && !isSmsReceiveEnable) {
					seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
						dialog.alert("${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.whetherRetrievePasswordFeature.warning')}");
					});
				}
			}
			
			function config_BrowserCompatible_chgEnabled() {
				var browserCompatible = $("tr[name=lab_BrowserCompatible]");
				var isChecked = "true" == $("input[name='value\\\(kmssBrowserCompatibleEnable\\\)']").val();
				if (isChecked) {
					browserCompatible.show();
				} else {
					browserCompatible.hide();
				}

				browserCompatible.find("input").each(function() {
					$(this).attr("disabled", !isChecked);
				});
			}
			
			function config_authlock_chgEnabled() {
				var labelObj = $("#lab_authlock");
				var isChecked = "true" == $("input[name='value\\\(kmssAuthlockEnabled\\\)']").val();
				if (isChecked) {
					labelObj.show();
				} else {
					labelObj.hide();
				}

				labelObj.find("input").each(function() {
					$(this).attr("disabled", !isChecked);
				});
			}
			
			//打开快捷添加企业认证弹窗
			function openPriseDialog(){
				seajs.use(['lui/dialog'],
				    function(dialog) {
					dialog.iframe("/elec/authentication/prise/elecAuthenPrise.do?method=addInnerPriseInfo",
		                    '新增企业认证',null,{width:600,height:500});
				});
			}
			
			function __init() {
				config_verifytimes_chgEnabled();
				config_authlock_chgEnabled();
				config_redirectto_chgEnabled();
				config_smsReceive_chgEnabled();
				config_BrowserCompatible_chgEnabled();
				enableChangedDongJie();
				enablePriorityChanged();
				identityEnabledChange();
				$('#exceptionConfigId2 tr').each(function(){                 
				       $(this).children('td').each(function(j){ 
				                if(j==1){
				                	if($(this).find("input").size()>0){
				                		changeIdenExpNum($(this).find("input")[0]);
				                	}
				                }
				      });
				});
				
			}
			
			
			
			LUI.ready(function() {	
				validation = $KMSSValidation();
				LUI("tag1").on("show", function(){
					__init();
				});
				LUI("tag2").on("show", function(){
					__init();
				});
			});
			
			function tabClick(i){
				if(i!=2){
					$(".lui_list_operation").hide();
				}else{
					$(".lui_list_operation").show();
				}
			}
			
			/**没勾选父复选框时，子选项不出现Start**/
			function hiddenCheckBox(){
            	$("#sendTypeId2").hide();
				var value = $("[name='value(enterprtise.authhenType)']:checked").val();
				if(value){
		            if(value=='1'){
		            	$("#sendTypeId2").show();
		            }
					if(value=='2'){
		            }
				}
			}
			hiddenCheckBox();
			$("[name='value(enterprtise.authhenType)']").change(function(){
				hiddenCheckBox();
			})
			/**没勾选父复选框时，子选项不出现End**/
			
			/**企业认证开关触发事件Start**/
			function elecAuthPriseEnabledChange(){
				$(".tr_add_inner_prise").hide();
				var personAuthOpen = $("[name='value(elecAuth.person.enabled)']").val();
				var value = $("[name='value(elecAuth.prise.enabled)']").val();
				/*********start--移除企业认证配置必填校验--start******/
				$("[name='value(prise.auth.Element)']").each(function(index,dom){
					validation.removeElements(dom,"required",true);
				});
				$("[name='value(enterprtise.authhenType)']").each(function(index,dom){
					validation.removeElements(dom,"required",true);
				});
				/**********end--移除企业认证配置必填校验--end***********/
				if(value == 'true'){
					//显示企业快捷认证入口
					$(".tr_add_inner_prise").show();
					if(personAuthOpen == 'false'){
						//提示开启个人实名认证开关
						seajs.use( ['lui/dialog'], function(dialog) {
							dialog.alert("请开启个人认证并配置认证方式");
						});
					}
					/*********start--添加企业认证配置必填校验--start******/
					$("[name='value(prise.auth.Element)']").each(function(index,dom){
						validation.addElements(dom,"required");
					});
					$("[name='value(enterprtise.authhenType)']").each(function(index,dom){
						validation.addElements(dom,"required");
					});
					/**********end--添加企业认证配置必填校验--end***********/
				}
			}
			//页面第一次加载先触发一次此事件
			setTimeout("elecAuthPriseEnabledChange()",500);
			/**企业认证开关触发事件End**/
			
			/**个人实名认证开关触发事件Start**/
			function elecAuthPersonalEnabledChange(){
				var value = $("[name='value(elecAuth.person.enabled)']").val();
				/*********start--移除个人实名认证配置必填校验--start******/
				$("[name='value(person.auth.Element)']").each(function(index,dom){
					validation.removeElements(dom,"required",true);
				});
				$("[name='value(person.will.certMethod)']").each(function(index,dom){
					validation.removeElements(dom,"required",true);
				});
				/**********end--移除个人实名认证配置必填校验--end***********/
				if(value == 'true'){
					/*********start--添加个人实名认证配置必填校验--start******/
					$("[name='value(person.auth.Element)']").each(function(index,dom){
						validation.addElements(dom,"required");
					});
					$("[name='value(person.will.certMethod)']").each(function(index,dom){
						validation.addElements(dom,"required");
					});
					/**********end--添加个人实名认证配置必填校验--end***********/
				}
			}
			//页面第一次加载先触发一次此事件
			setTimeout("elecAuthPersonalEnabledChange()",500);
			/**个人实名认证开关触发事件End**/
		</script>
	</template:replace>
</template:include>
