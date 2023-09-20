<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.third.intell.model.IntellConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message key="thirdIntellConfig.itConfig" bundle="third-intell"/></template:replace>
	<template:block name="path" >
		<span class=txtlistpath><bean:message key="thirdIntellConfig.currurl" bundle="third-intell"/>：<bean:message key="thirdIntellConfig.config.setting" bundle="third-intell"/></span>
	</template:block>
	<template:replace name="content">
		<h2 align="center" style="margin:10px 0">
			<span class="profile_config_title"><bean:message key="thirdIntellConfig.config.setting" bundle="third-intell"/></span>
		</h2>
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do?autoclose=false" onsubmit="return validateAppConfigForm(this);">
			<center>
				<table id="itBaseTable" class="tb_normal" width="95%">
					<tr>
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.itEnabled" bundle="third-intell"/></td>
						<td>
							<ui:switch property="value(itEnabled)" onValueChange="window.display_change();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						</td>
					</tr>
					<tr name="one" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.itDomain" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(itDomain)" subject="${lfn:message('third-intell:thirdIntellConfig.itDomain') }" required="false" style="width:85%;vertical-align: bottom;" showStatus="edit" validators="itRequire"/>
							<span class="txtstrong">*</span>
							<ui:button text="${lfn:message('third-intell:thirdIntellConfig.itUrl.config') }" onclick="window.urlConfig();" style="vertical-align: top;"></ui:button>	
						</td>
					</tr>
					<tr name="one" style="display: none;">
                    	<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.innerItDomain" bundle="third-intell"/></td>
                    	<td>
                    		<xform:text property="value(innerItDomain)" subject="${lfn:message('third-intell:thirdIntellConfig.innerItDomain') }" required="false" style="width:85%;vertical-align: bottom;" showStatus="edit"/>
                    	</td>
                    </tr>
					<tr name="one" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.itSecret" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(itSecret)" subject="${lfn:message('third-intell:thirdIntellConfig.itSecret') }" required="false" style="width:85%" showStatus="edit" validators="itRequire"/>
							<span class="txtstrong">*</span>
							<ui:button text="${lfn:message('third-intell:thirdIntellConfig.itSecret.name') }" onclick="window.randomSecret();" style="vertical-align: top;" id="isb"></ui:button>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.itSecret.desc" bundle="third-intell"/></div>
						</td>
					</tr>
					<tr style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.itUrl" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(itUrl)" style="width:65%;" showStatus="edit" required="false" subject="${lfn:message('third-intell:thirdIntellConfig.itUrl') }"/>
							<span class="txtstrong">*</span>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.itUrl.help" bundle="third-intell"/></div>
						</td>
					</tr>
					<tr style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.itConfigUrl" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(itConfigUrl)" style="width:65%;" showStatus="edit" required="false" subject="${lfn:message('third-intell:thirdIntellConfig.itConfigUrl') }"/>
							<span class="txtstrong">*</span>
							<ui:button text="${lfn:message('third-intell:thirdIntellConfig.itUrl.config') }" style="vertical-align: top;"></ui:button>	
							<div style="color: gray;"><bean:message key="thirdIntellConfig.itConfigUrl.help" bundle="third-intell"/></div>
							<xform:text property="value(itAppAll)" showStatus="edit"/>
							<xform:text property="value(itSSOSecret)" showStatus="edit"/>
							<xform:text property="value(itSSOPSecret)" showStatus="edit"/>
							<xform:text property="value(itSSOUserKey)" showStatus="edit"/>
							<xform:text property="value(itSessionExpireTime)" showStatus="edit"/>
							<xform:text property="value(itCompanyName)" showStatus="edit"/>
							<xform:text property="value(itCompanyCode)" showStatus="edit"/>
							<xform:text property="value(itSsoEnabled)" showStatus="edit"/>
						</td>
					</tr>
			          <tr name="one" style="display: none;">
			            <td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.search" bundle="third-intell"/></td>
			            <td>
			              <ui:switch id="ice" property="value(searchEnabled)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
			              <div style="color: gray;"><bean:message key="thirdIntellConfig.search.desc" bundle="third-intell"/></div>
			            </td>
			          </tr>
			          <tr name="one" style="display: none;">
			            <td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.smartTag" bundle="third-intell"/></td>
			            <td>
			              <ui:switch id="ice" property="value(smartTag)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
			              <div style="color: gray;"><bean:message key="thirdIntellConfig.smartTag.desc" bundle="third-intell"/></div>
			            </td>
			          </tr>
					<%-- <tr style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.nlpUrl" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(nlpUrl)" style="width:65%;" showStatus="edit" required="false" subject="${lfn:message('third-intell:thirdIntellConfig.nlpUrl') }"/>
							<span class="txtstrong">*</span>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.nlpUrl.help" bundle="third-intell"/></div>
						</td>
					</tr> --%>
					
					
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.itApp" bundle="third-intell"/></td>
						<td id="app">
							
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.itConfig" bundle="third-intell"/></td>
						<td>
							<ui:switch id="ice" property="value(itConfigEnabled)" onValueChange="window.display_sso_change();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.itConfigEnabled.desc" bundle="third-intell"/></div>
							<div id="ssoTable" style="border: 1px #d2d2d2 solid;padding: 8px !important;text-align: left;word-break: keep-all;margin: 5 auto; background-color:#f6f6f6; " align="left">
								<table style="width: 100%;">
									<tr height="28px">
										<td style="width: 70%;white-space: nowrap;overflow: hidden;">
											<bean:message key="thirdIntellConfig.itEKPDomain" bundle="third-intell"/>
											<xform:text property="value(itEKPDomain)" style="width:100%;background-color:#f6f6f6;" showStatus="edit" required="true"/>
										</td>
										<td style="width: 30%"><ui:button text="${lfn:message('third-intell:thirdIntellConfig.ssoConfig') }" onclick="window.open('${LUI_ContextPath}/admin.do?method=config','_blank');" style="vertical-align: top;"></ui:button>	</td>
									</tr>
									<tr height="28px">
										<td style="width: 70%;white-space: nowrap;overflow: hidden;">
											<bean:message key="thirdIntellConfig.itKKDomain" bundle="third-intell"/>
											<xform:text property="value(itKKDomain)" style="width:100%;background-color:#f6f6f6;" showStatus="edit"/>
										</td>
										<td></td>
									</tr>
									<tr height="28px">
										<td style="width: 70%;white-space: nowrap;overflow: hidden;">
										<bean:message key="thirdIntellConfig.itSSOToken" bundle="third-intell"/>
										<xform:text property="value(itSSOToken)" style="width:100%;background-color:#f6f6f6;" showStatus="readOnly"/>
										</td>
										<td><ui:button text="${lfn:message('third-intell:thirdIntellConfig.ssoConfig') }" onclick="window.open('${LUI_ContextPath}/admin.do?method=config','_blank');" style="vertical-align: top;"></ui:button></td>
									</tr>
									<tr height="28px">
										<td>
											<bean:message key="thirdIntellConfig.itSSOSecret" bundle="third-intell"/>
											<span class="txtstrong" id="iss">******</span>
										</td>
										<td></td>
									</tr>
									<tr height="28px" style="display: none;">
										<td>
											<bean:message key="thirdIntellConfig.itSSOPSecret" bundle="third-intell"/>
											<span class="txtstrong" id="isps">******</span>
										</td>
										<td></td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.itPersonNameEnabled" bundle="third-intell"/></td>
						<td>
							<ui:switch property="value(itPersonNameEnabled)" onValueChange="window.display_person_change();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.itPersonNameEnabled.desc" bundle="third-intell"/></div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.itPersonCardType" bundle="third-intell"/></td>
						<td>
							<xform:radio property="value(personCardType)" alignment="H">
								<xform:enumsDataSource enumsType="intell_person_cardtype" />
							</xform:radio>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.itPersonCardType.desc" bundle="third-intell"/></div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.itMainDataEnabled" bundle="third-intell"/></td>
						<td>
							<ui:switch property="value(itMainDataEnabled)" onValueChange="window.display_nlp_change();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.itMainDataEnabled.desc" bundle="third-intell"/></div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.itKgDataEnabled" bundle="third-intell"/></td>
						<td>
							<ui:switch property="value(itKgDataEnabled)" onValueChange="window.display_kg_change();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.itKgDataEnabled.desc" bundle="third-intell"/></div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.mainDataIndexThreadCount" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(mainDataIndexThreadCount)" style="width:65%;vertical-align: bottom;" showStatus="edit" required="false" subject="${lfn:message('third-intell:thirdIntellConfig.mainDataIndexThreadCount') }"/>
							<ui:button text="${lfn:message('third-intell:thirdIntellConfig.indexMainData') }" onclick="window.startIndexMainData();" style="vertical-align: top;"></ui:button>
							<ui:button text="${lfn:message('third-intell:thirdIntellConfig.reIndexMainData') }" onclick="window.reStartIndexMainData();" style="vertical-align: top;"></ui:button>		
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.kgDataIndex" bundle="third-intell"/></td>
						<td>
							<ui:button text="${lfn:message('third-intell:thirdIntellConfig.indexKgData') }" onclick="window.startIndexKgData();" style="vertical-align: top;"></ui:button>
							<ui:button text="${lfn:message('third-intell:thirdIntellConfig.reIndexKgData') }" onclick="window.reStartIndexKgData();" style="vertical-align: top;"></ui:button>		
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.itModelInfoEnabled" bundle="third-intell"/></td>
						<td>
							<ui:switch property="value(itModelInfoEnabled)" onValueChange="window.display_modelInfo_change();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.itModelInfoEnabled.desc" bundle="third-intell"/></div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.itCategoryInfoEnabled" bundle="third-intell"/></td>
						<td>
							<ui:switch property="value(itCategoryInfoEnabled)" onValueChange="window.display_categoryInfo_change();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.itCategoryInfoEnabled.desc" bundle="third-intell"/></div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.kms.itKmsCategory" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(itKmsCategory)" subject="${lfn:message('third-intell:thirdIntellConfig.kms.itKmsCategory') }" required="false" style="width:85%" showStatus="edit"/>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.kms.tip" bundle="third-intell"/>/data/labc-biz-qa/api/category/sync?type=2</div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.kms.itKmsDelCategory" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(itKmsDelCategory)" subject="${lfn:message('third-intell:thirdIntellConfig.kms.itKmsDelCategory') }" required="false" style="width:85%" showStatus="edit"/>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.kms.tip" bundle="third-intell"/>/data/labc-biz-qa/api/category/delete?</div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.kms.itKmsQuestion" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(itKmsQuestion)" subject="${lfn:message('third-intell:thirdIntellConfig.kms.itKmsQuestion') }" required="false" style="width:85%" showStatus="edit"/>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.kms.tip" bundle="third-intell"/>/data/labc-biz-qa/api/question/sync</div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.kms.itKmsDelQuestion" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(itKmsDelQuestion)" subject="${lfn:message('third-intell:thirdIntellConfig.kms.itKmsDelQuestion') }" required="false" style="width:85%" showStatus="edit"/>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.kms.tip" bundle="third-intell"/>/data/labc-biz-qa/api/question/delete</div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.kms.mainDataModulesClearPath" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(mainDataModulesClearPath)" subject="${lfn:message('third-intell:thirdIntellConfig.kms.mainDataModulesClearPath') }" required="false" style="width:85%" showStatus="edit"/>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.kms.tip" bundle="third-intell"/>/data/labc-biz-qa/api/question/clearNotInModules</div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.kms.modelInfoAipUrl" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(modelInfoAipUrl)" subject="${lfn:message('third-intell:thirdIntellConfig.kms.modelInfoAipUrl') }" required="false" style="width:85%" showStatus="edit"/>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.kms.tip" bundle="third-intell"/>/data/labc-biz-sysmodelconfig/modelInfo/addlist</div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.kms.categoryInfoAipUrl" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(categoryInfoAipUrl)" subject="${lfn:message('third-intell:thirdIntellConfig.kms.categoryInfoAipUrl') }" required="false" style="width:85%" showStatus="edit"/>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.kms.tip" bundle="third-intell"/>/data/labc-biz-sysmodelconfig/categoryInfo/addlist</div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.kms.kgEntityApiUrl" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(kgEntityApiUrl)" subject="${lfn:message('third-intell:thirdIntellConfig.kms.kgEntityApiUrl') }" required="false" style="width:85%" showStatus="edit"/>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.kms.tip" bundle="third-intell"/>/data/labc-biz-sysmodelconfig/categoryInfo/addlist</div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.kms.searchUrl" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(searchUrl)" subject="${lfn:message('third-intell:thirdIntellConfig.kms.searchUrl') }" required="false" style="width:85%" showStatus="edit"/>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.kms.tip" bundle="third-intell"/>/web/labc-search/#/?q=</div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.dingRelationEnable" bundle="third-intell"/></td>
						<td>
							<ui:switch property="value(intellDingRelationEnable)" onValueChange="window.display_kg_change();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.dingRelationEnable.desc" bundle="third-intell"/></div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.intellDingRelationUrl" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(intellDingRelationUrl)" subject="${lfn:message('third-intell:thirdIntellConfig.intellDingRelationUrl') }" required="false" style="width:85%" showStatus="edit"/>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.intellDingRelationUrl.tip" bundle="third-intell"/></div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.syncUserBehavior" bundle="third-intell"/></td>
						<td>
							<ui:switch property="value(syncUserBehavior)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.syncUserBehavior.desc" bundle="third-intell"/></div>
							<ui:button text="${lfn:message('third-intell:thirdIntellConfig.reSyncBehaviorData') }" onclick="window.reSyncBehaviorData();" style="vertical-align: top;"></ui:button>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.syncUserBehaviorByCloud" bundle="third-intell"/></td>
						<td>
							<ui:switch property="value(syncUserBehaviorByCloud)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.syncUserBehaviorByCloud.tip" bundle="third-intell"/></div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.syncUserBehaviorUrl" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(syncUserBehaviorUrl)" subject="${lfn:message('third-intell:thirdIntellConfig.syncUserBehaviorUrl') }" required="false" style="width:85%" showStatus="edit"/>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.syncUserBehaviorUrl.tip" bundle="third-intell"/></div>
						</td>
					</tr>
					<tr name="two" style="display: none;">
						<td class="td_normal_title" width="15%"><bean:message key="thirdIntellConfig.serverKey" bundle="third-intell"/></td>
						<td>
							<xform:text property="value(serverKey)" subject="${lfn:message('third-intell:thirdIntellConfig.serverKey') }" required="false" style="width:85%" showStatus="edit"/>
							<div style="color: gray;"><bean:message key="thirdIntellConfig.serverKey.tip" bundle="third-intell"/></div>
						</td>
					</tr>
				</table>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.third.intell.model.IntellConfig" />
			<center style="margin:20px 0;">
				<!-- 保存 -->
				<ui:button id="so" text="${lfn:message('third-intell:thirdIntellConfig.link')}" onclick="window.showtwo();" width="120" height="35"></ui:button>
				<ui:button id="st1" text="${lfn:message('button.save')}" onclick="window.itSubmit();" width="120" height="35"></ui:button>
				<ui:button id="st2" text="${lfn:message('third-intell:thirdIntellConfig.cl.config')}" onclick="window.hidetwo();" width="120" height="35"></ui:button>
			</center>
		</html:form>
		<script type="text/javascript">
			var validation = $KMSSValidation();
			var appid = '<%=IntellConfig.newInstance().getItApp()%>';
			var appname = '<%=IntellConfig.newInstance().getItAppName()%>';
			var appall = '<%=IntellConfig.newInstance().getItAppAll()%>';
			seajs.use(['lui/jquery'],function($){
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
				//生成16的秘钥
				function randomSecret(){
					var sct = $("input[name='value(itSecret)']").val();
					if(sct!=null||sct!=""){
						if(confirm('<bean:message key="thirdIntellConfig.itSecret.tip" bundle="third-intell"/>')){
							$("input[name='value(itSecret)']").val( config_randomAlphanumeric(32) ); 
						}
					}else{
						$("input[name='value(itSecret)']").val( config_randomAlphanumeric(32) ); 
					}
				}
				
				function display_change(){
					var value = $('input[name="value(itEnabled)"]').val();
					if(value == 'true'){
						$('#itBaseTable tr[name="one"]').show();
						var itSecret = $('input[name="value(itSecret)"]').val();
						var itUrl = $('input[name="value(itUrl)"]').val();
						var ce = $('input[name="value(itConfigEnabled)"]').val();
						if((ce == 'true'||ce == 'false')&&itSecret!=""&&itUrl!=""){
							$('#itBaseTable tr[name="two"]').show();
							$("#so").hide();
							$("#st1").show();
							$("#st2").show();
							$('input[name="value(itDomain)"]').attr("readonly","readonly");
							$('input[name="value(innerItDomain)"]').attr("readonly","readonly");
							$('input[name="value(itSecret)"]').attr("readonly","readonly");
							$("#isb").hide();
						}else{
							$('#itBaseTable tr[name="two"]').hide();
							$("#so").show();
							$("#st1").hide();
							$("#st2").hide();
							$('input[name="value(itDomain)"]').removeAttr("readonly");
							$('input[name="value(innerItDomain)"]').removeAttr("readonly");
							$('input[name="value(itSecret)"]').removeAttr("readonly");
							$("#isb").show();
						}
					}else{
						$("#st1").show();
						$("#so").hide();
						$("#st2").hide();
						$('#itBaseTable tr[name="one"]').hide();
						$('#itBaseTable tr[name="two"]').hide();
					}					
				}
				function checkDomain(){
					//检查能否访问外网智能平台
					/*var domainurl = $('input[name="value(itDomain)"]').val();
					var index = domainurl.lastIndexOf('/');
					var webUrl;
					if(index==domainurl.length-1){
						webUrl = domainurl + "data/labc-biz-ekp/ekp/test";
					}else{
						webUrl = domainurl + "/data/labc-biz-ekp/ekp/test";
					}
					var flag = true;
					$.ajax({
						   type: "GET",
						   url: webUrl,
						   async:false,
						   dataType: "json",
						   success: function(data){
						   },
						   error: function(){
							   flag = false;
							  alert('<bean:message key="thirdIntellConfig.link.tip" bundle="third-intell"/>('+webUrl+")");
			               }
					});
					return flag;*/
					return true;
				}
				function showtwo(){
					var flag = validation.validate();
					//校验admin.do是否配置域名
					var ekpdomain = $('input[name="value(itEKPDomain)"]').val();
					if(ekpdomain==""||ekpdomain==null||ekpdomain.indexOf("localhost")>-1||ekpdomain.indexOf("127.0.0.1")>-1){
						flag = false;
						alert('<bean:message key="thirdIntellConfig.ssoConfig.tip" bundle="third-intell"/>');
					}
					//检查能否访问外网智能平台
					if(flag){
						flag = checkDomain();
					}
					var domainurl = $('input[name="value(itDomain)"]').val();
					var innerDomain = $('input[name="value(innerItDomain)"]').val();
                    if(innerDomain == null){
                    	innerDoman = "";
                    }
					var itSecret = $('input[name="value(itSecret)"]').val();
					if(flag){
						//获取后台的加密密钥
						var url = '<c:url value="/third/intell/labcOrgnization.do?method=intellInfo" />'+"&type=token&secret="+itSecret+"&url="+encodeURIComponent(domainurl)+"&innerUrl="+encodeURIComponent(innerDomain);;
						$.ajax({
						   type: "GET",
						   url: url,
						   async:false,
						   dataType: "json",
						   success: function(data){
							   if(data.errcode==0){
							   }else{
								   var rtn = data.data;
								   if(rtn.status==500&&rtn.content==2){
									   var ce = $('input[name="value(itConfigEnabled)"]').val();
									   if(ce == 'true'||ce=="on"){
											if($('#ice :checkbox').is(':checked')) {
												$('#ice :checkbox').click();
											}
											alert(data.errmsg);
										}
								   }else{
									   flag = false;
									   alert(data.errmsg);
								   }
							   }
						   }
						});
					}
					if(flag){
						//后台获取智能平台的应用列表的签名
						var url = '<c:url value="/third/intell/labcOrgnization.do?method=intellInfo" />'+"&type=app&secret="+itSecret+"&url="+encodeURIComponent(domainurl)+"&innerUrl="+encodeURIComponent(innerDomain);
						$.ajax({
						   type: "GET",
						   url: url,
						   async:false,
						   dataType: "json",
						   success: function(data){
							   if(data.errcode==0){
								   $("#app").empty();
								   $('input[name="value(itAppAll)"]').val(JSON.stringify(data.data));
								   var rd;
								   var rtn = data.data.content;
								   for(var i=0;i<rtn.length;i++){
									   if(appid!=rtn[i].appId){
										    rd = '<input type="radio" name="value(itApp)" value="'+rtn[i].appId+'"><label>'+rtn[i].appName+'</label>';
										}else if(appid==rtn[i].appId){
											rd = '<input checked type="radio" name="value(itApp)" value="'+rtn[i].appId+'"><label>'+rtn[i].appName+'</label>';
										}
									   $("#app").append(rd);
								   }
								   if(appid==""||appid=='null'){
								   	$("input[name='value(itApp)']:eq(0)").attr("checked",'checked');
								   }
							   }else{
								   flag = false;
								   alert(data.errmsg);
							   }
						   }
						});
					}
					
					if(flag){
						$('input[name="value(itDomain)"]').attr("readonly","readonly");
						$('input[name="value(innerItDomain)"]').attr("readonly","readonly");
						$('input[name="value(itSecret)"]').attr("readonly","readonly");
						$('#itBaseTable tr[name="two"]').show();
						$("#so").hide();
						$("#isb").hide();
						$("#st1").show();
						$("#st2").show();
					}
				}
				
				function hidetwo(){
					$('input[name="value(itDomain)"]').removeAttr("readonly");
					$('input[name="value(innerItDomain)"]').removeAttr("readonly");
					$('input[name="value(itSecret)"]').removeAttr("readonly");
					$('#itBaseTable tr[name="two"]').hide();
					$("#so").show();
					$("#isb").show();
					$("#st1").hide();
					$("#st2").hide();
				}
				
				function display_sso_change(v){
					var val = $('input[name="value(itConfigEnabled)"]').val();
					if("true"==val){
						if(v==null||v=="")
							alert("<bean:message key='thirdIntellConfig.ssoConfig.open.desc' bundle='third-intell'/>\r<bean:message key='thirdIntellConfig.ssoConfig.open.desc1' bundle='third-intell'/>");
						$("#ssoTable").show();
					}else{
						$("#ssoTable").hide();
					}
				}
				function display_person_change(){
					var val = $('input[name="value(itPersonNameEnabled)"]').val();
					if("true"==val){
						alert("<bean:message key='thirdIntellConfig.ssoConfig.person.main' bundle='third-intell'/>");
						
					}
				}
				
				function display_nlp_change(){
					var val = $('input[name="value(itMainDataEnabled)"]').val();
					if("true"==val){
						//$('input[name="value(itKgDataEnabled)"]').val("false");
						alert("<bean:message key='thirdIntellConfig.ssoConfig.maindata.main' bundle='third-intell'/>");
					}
				}
				function display_kg_change(){
					var val = $('input[name="value(itKgDataEnabled)"]').val();
					if("true"==val){
						//$('input[name="value(itMainDataEnabled)"]').val("false");
						alert("<bean:message key='thirdIntellConfig.ssoConfig.kgdata.main' bundle='third-intell'/>");
					}
				}
				function display_modelInfo_change(){
					var val = $('input[name="value(itModelInfoEnabled)"]').val();
					if("true"==val){
						alert("<bean:message key='thirdIntellConfig.itModelInfoEnabled.desc' bundle='third-intell'/>");
					}
				}
				function display_categoryInfo_change(){
					var val = $('input[name="value(itCategoryInfoEnabled)"]').val();
					if("true"==val){
						alert("<bean:message key='thirdIntellConfig.itCategoryInfoEnabled.desc' bundle='third-intell'/>");
					}
				}
				function urlConfig(){
					var value = $('input[name="value(itDomain)"]').val();
					if(value==null||$.trim(value)==""){
						alert('<bean:message key="thirdIntellConfig.itDomain" bundle="third-intell"/>'+'<bean:message key="thirdIntellConfig.itUrl.noNull" bundle="third-intell"/>');
					}else{
						//智能助手后台URL=智能平台地址+/labc-platform/
						var index = value.lastIndexOf('/');
						if(index>0&&index==value.length-1){
							value = value.substring(0,index);
						}
						$('input[name="value(itConfigUrl)"]').val(value+"/web/labc-platform/");
						window.open($('input[name="value(itConfigUrl)"]').val(),"_blank");
					}
				}
				
				function reStartIndexMainData(){
					var url = '<c:url value="/third/intell/labcOrgnization.do?method=clearMainDataIndexTime" />';
					$.ajax({
					   type: "GET",
					   url: url,
					   async:false,
					   dataType: "json",
					   success: function(data){
							if(data.errcode==0){
								startIndexMainData();
							}else{
								alert(data.errmsg);
							}
						}
					});
				}

				function startIndexMainData(){
					window.open("${LUI_ContextPath}/sys/quartz/sys_quartz_job/sysQuartzJob.do?method=run&fdJobService=mainDataIndexTaskRunner&fdJobMethod=index","_blank");
				}
				
				function reStartIndexKgData(){
					var url = '<c:url value="/third/intell/labcOrgnization.do?method=clearKgDataIndexTime" />';
					$.ajax({
					   type: "GET",
					   url: url,
					   async:false,
					   dataType: "json",
					   success: function(data){
							if(data.errcode==0){
								startIndexKgData();
							}else{
								alert(data.errmsg);
							}
						}
					});
				}
				
				function startIndexKgData(){
					window.open("${LUI_ContextPath}/sys/quartz/sys_quartz_job/sysQuartzJob.do?method=run&fdJobService=kgDataTaskRunner&fdJobMethod=run","_blank");
				}
				
				function startSyncBehavior(){
					window.open("${LUI_ContextPath}/sys/quartz/sys_quartz_job/sysQuartzJob.do?method=run&fdJobService=syncUserBehaviorDataTask&fdJobMethod=sync","_blank");
				}
				
				function reSyncBehaviorData(){
					var url = '<c:url value="/third/intell/labcOrgnization.do?method=clearSyncBehaviorTime" />';
					$.ajax({
					   type: "GET",
					   url: url,
					   async:false,
					   dataType: "json",
					   success: function(data){
							if(data.errcode==0){
								startSyncBehavior();
							}else{
								alert(data.errmsg);
							}
						}
					});
				}
				
				validation.addValidator('itRequire','<bean:message key="thirdIntellConfig.itUrl.noNull" bundle="third-intell"/>',function(v, e, o){
					var wxEnabled = $('input[name="value(itEnabled)"]').val();
					if(wxEnabled == 'true' && !$.trim(v)){
						var validator = this.getValidator('itRequire'),
							error = '<bean:message key="thirdIntellConfig.itUrl.noNull" bundle="third-intell"/>';
						if($(e).attr('subject')){
							error = $(e).attr('subject') + error;
						}	
						validator.error = error;
						return false;
					}
					return true;
				});
				
				LUI.ready(function(){
					display_change();
					display_sso_change("init");
					checkMain();
					//app应用赋值
					if(appid!=null&&appid!='null'&&appid!=""&&appall!=""&&appall!="null"){
						var domainurl = $('input[name="value(itDomain)"]').val();
						var itSecret = $('input[name="value(itSecret)"]').val();
						var innerDomain = $('input[name="value(innerItDomain)"]').val();
                        if(innerDomain == null){
                        	innerDoman = "";
                        }
						//后台获取智能平台的应用列表的签名
						var url = '<c:url value="/third/intell/labcOrgnization.do?method=intellInfo" />'+"&type=app&secret="+itSecret+"&url="+encodeURIComponent(domainurl)+"&innerUrl="+encodeURIComponent(innerDomain);
						$.ajax({
						   type: "GET",
						   url: url,
						   async:false,
						   dataType: "json",
						   success: function(data){
							   if(data.errcode==0){
								   $("#app").empty();
								   $('input[name="value(itAppAll)"]').val(JSON.stringify(data.data));
								   var rd;
								   var rtn = data.data.content;
								   for(var i=0;i<rtn.length;i++){
									   if(appid!=rtn[i].appId){
										    rd = '<input type="radio" name="value(itApp)" value="'+rtn[i].appId+'"><label>'+rtn[i].appName+'</label>';
										}else if(appid==rtn[i].appId){
											rd = '<input checked type="radio" name="value(itApp)" value="'+rtn[i].appId+'"><label>'+rtn[i].appName+'</label>';
										}
									   $("#app").append(rd);
								   }
								   if(appid==""||appid=='null'){
								   	$("input[name='value(itApp)']:eq(0)").attr("checked",'checked');
								   }
							   }else{
								   var da = JSON.parse(appall).content;
									$("#app").empty();
								   var rd;
								   for(var i=0;i<da.length;i++){
									    if(appid!=da[i].appId){
										   rd = '<input type="radio" name="value(itApp)" value="'+da[i].appId+'"><label>'+da[i].appName+'</label>&nbsp;&nbsp;';
										}else if(appid==da[i].appId){
											rd = '<input checked type="radio" name="value(itApp)" value="'+da[i].appId+'"><label>'+da[i].appName+'</label>&nbsp;&nbsp;';
										}
									   $("#app").append(rd);
								   }
							   }
						   }
						});
					}
				});
				
				window.validateAppConfigForm = function(){
					return true;
				};
				
				window.itSubmit = function(){
					if($('input[name="value(itMainDataEnabled)"]').val() == 'true' 
							&& $('input[name="value(itKgDataEnabled)"]').val() == 'true' ){
						alert('<bean:message key="thirdIntellConfig.configError1" bundle="third-intell"/>');
						return false;
					}
					var value = $('input[name="value(itEnabled)"]').val();
					if(value == 'true'){	
						var flag = true;
						//检查能否访问外网智能平台
						flag = checkDomain();
						//sso配置检查
						var domainurl = $('input[name="value(itDomain)"]').val();
						var itSecret = $('input[name="value(itSecret)"]').val();
						var innerDomain = $('input[name="value(innerItDomain)"]').val();
                        if(innerDomain == null){
                        	innerDoman = "";
                        }
						var kkHost = $('input[name="value(itKKDomain)"]').val();
						var s = $('input[name="value(itConfigEnabled)"]').val();
						var s4 = $('input[name="value(itKKDomain)"]').val();
						if(s=="true"&&flag){
							if(flag){
								var url = '<c:url value="/third/intell/labcOrgnization.do?method=intellInfo" />'+"&type=config&secret="+itSecret+"&url="+encodeURIComponent(domainurl)+"&kkHost="+kkHost+"&innerUrl="+encodeURIComponent(innerDomain);
								$.ajax({
								   type: "GET",
								   url: url,
								   async:false,
								   dataType: "json",
								   success: function(data){
									   if(data.errcode==0){
										   var rtn = data.config.data;
										    $('input[name="value(itSSOToken)"]').val(rtn.ssoType);
											$('input[name="value(itSessionExpireTime)"]').val(rtn.sessionExpireTime);
											//$('input[name="value(itCompanyCode)"]').val(rtn.companyCode);
											$('input[name="value(itCompanyName)"]').val(rtn.companyName);
											if (rtn.type=="LtpaTokenGenerator") {
												$('input[name="value(itSSOUserKey)"]').val(rtn.dominoSecurityKey);
												$('input[name="value(itSSOSecret)"]').val(rtn.dominoUserKey);
											}else{
												$('input[name="value(itSSOPSecret)"]').val(rtn.securityKeyPrivate);
												$('input[name="value(itSSOSecret)"]').val(rtn.securityKeyPublic);
											}
									   }else{
										   flag = false;
										   alert(data.errmsg);
									   }
								   }
								});
							}
						}
						if(flag&&domainurl!=""){
							var index = domainurl.lastIndexOf('/');
							if(index==domainurl.length-1){
								domainurl = domainurl.substring(0,index);
							}
							//智能助手后台URL=智能平台地址+/labc-platform/
							$('input[name="value(itConfigUrl)"]').val(domainurl+"/web/labc-platform/");
							//智能助手地址=智能平台地址+/labc-robot/#/?appId=XXXX
							var aid = $('input:radio[name="value(itApp)"]:checked').val();
							if(aid){
								$('input[name="value(itUrl)"]').val(domainurl+"/web/labc-robot/#/?appId="+aid);
							}else{
								alert('<bean:message key="thirdIntellConfig.itApp" bundle="third-intell"/>'+'<bean:message key="thirdIntellConfig.itUrl.noNull" bundle="third-intell"/>');
								return false;
							}
							Com_Submit(document.sysAppConfigForm, 'update');
						}
					}else{
					    $("#so").remove();
                        $("#st2").remove();
						Com_Submit(document.sysAppConfigForm, 'update');
					}
				};
				function checkMain(){
					var domainurl = $('input[name="value(itDomain)"]').val();
					var innerDomain = $('input[name="value(innerItDomain)"]').val();
					if(innerDomain == null){
						innerDoman = "";
					}
					var itSecret = $('input[name="value(itSecret)"]').val();
					var ite = $('input[name="value(itEnabled)"]').val();
                    var ce = $('input[name="value(itConfigEnabled)"]').val();
					if(domainurl&&itSecret&&ite == 'true'){
						//获取后台的加密密钥
						var url = '<c:url value="/third/intell/labcOrgnization.do?method=intellInfo" />'+"&type=check&secret="+itSecret+"&url="+encodeURIComponent(domainurl)+"&innerUrl="+encodeURIComponent(innerDomain);
						$.ajax({
						   type: "GET",
						   url: url,
						   async:false,
						   dataType: "json",
						   success: function(data){
							   if(data.errcode==0){
								   var rtn = data.data;
								   if(rtn.status==200&&rtn.content==2){
									   if(ce == 'true'||ce=="on"){
											if($('#ice :checkbox').is(':checked')) {
												$('#ice :checkbox').click();
											}
										}
								   }else if(rtn.status==200&&(rtn.content==0||rtn.content==1)){									  
								   }else{
									   flag = false;
									   alert(rtn.message);
								   }
							   }else{
                                	alert(data.errmsg);
                               }
						   }
						});
					}
				}
				window.display_change = display_change;
				window.urlConfig = urlConfig;
				window.randomSecret = randomSecret;
				window.display_sso_change = display_sso_change;
				window.showtwo = showtwo;
				window.hidetwo = hidetwo;
				window.startIndexMainData = startIndexMainData;
				window.reStartIndexMainData = reStartIndexMainData;
				window.startIndexKgData = startIndexKgData;
				window.reStartIndexKgData = reStartIndexKgData;
				window.display_person_change = display_person_change;
				window.display_nlp_change = display_nlp_change;
				window.display_modelInfo_change = display_modelInfo_change;
				window.display_categoryInfo_change = display_categoryInfo_change;
				window.display_kg_change = display_kg_change;
				window.startSyncBehavior = startSyncBehavior;
				window.reSyncBehaviorData = reSyncBehaviorData;
			});
		</script>
	</template:replace>	
</template:include>