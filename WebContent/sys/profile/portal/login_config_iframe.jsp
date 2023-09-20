<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<template:include ref="config.view">
	<template:replace name="head">
		<style>
		body {
			overflow:hidden;
		}
		.kk_is_show{
			display:none;
		}
		</style>
	</template:replace>
	<template:replace name="content">
	<html:form action="/sys/profile/LoginConfig.do?autoclose=false" method="POST">
		<table class="tb_normal" width="95%">
			<tr>
				<td class="td_normal_title" width="30%">
					<bean:message bundle="sys-profile" key="sys.profile.loginconfig.fdDefaultLoginType" />
				</td>
				<td colspan="2">
					<xform:radio property="value(fdDefaultLoginType)">
							<xform:customizeDataSource
								className="com.landray.kmss.sys.profile.service.spring.SysLoginDefaultTypeDataSource">
							</xform:customizeDataSource>
						</xform:radio>
				</td>
			</tr>
			<tr class="kk_download_enable">
				<td class="td_normal_title" width="30%"><bean:message bundle="sys-profile" key="sys.profile.loginconfig.KKClientDownload" /></td>
				<td colspan="2">
						<label>
							<ui:switch property="value(showDownLoad)" onValueChange="changeKKShow()" checked="${loginConfig.isShowDownLoadKK}"  enabledText="${lfn:message('sys-profile:sys.profile.loginconfig.enabled')}" disabledText="${lfn:message('sys-profile:sys.profile.loginconfig.closed')}"></ui:switch>
						</label>
				</td>
			</tr>
			<%if("true".equals(ResourceUtil.getKmssConfigString("kmss.lang.suportEnabled"))){%>
			<tr>
				<td class="td_normal_title" width="30%"><bean:message bundle="sys-profile" key="sys.profile.loginconfig.multiLanguageSwitching" /></td>
				<td colspan="2">
						<label>
							<ui:switch property="value(hiddenLang)" checked="${loginConfig.isShowDownLoadKK}"  enabledText="${lfn:message('sys-profile:sys.profile.loginconfig.enabled')}" disabledText="${lfn:message('sys-profile:sys.profile.loginconfig.closed')}"></ui:switch>
						</label>
				</td>
			</tr>
			<%}%>
			<tr class="kk_is_show kk_download_url">
				<td class="td_normal_title" width="30%"><bean:message bundle="sys-profile" key="sys.profile.loginconfig.KKDownloadLink" /></td>
				<td colspan="2">
					<xform:text style="width:400px;" property="value(downloadUrl)" value="${loginConfig.KkDownloadUrl}" showStatus="edit"/>
				</td>
			</tr>
			<tr class="kk_is_show">
				<td class="td_normal_title" width="30%"><bean:message bundle="sys-profile" key="sys.profile.loginconfig.KKDownloadLinkName" /></td>
				<td colspan="2">
					<xform:text style="width:400px;" property="value(downLoadName)" showStatus="edit"></xform:text>
				</td>
			</tr>
			<tr class="login_template_enable">
				<td class="td_normal_title" width="30%"><bean:message bundle="sys-profile" key="sys.profile.loginconfig.customLoginTemplate" /></td>
				<td colspan="2">
					<ui:switch property="value(customLoginTemplateEnable)"  checked="${customLoginTemplateEnable.xformRelevanceEnable}" enabledText="${lfn:message('sys-profile:sys.profile.loginconfig.enabled')}" disabledText="${lfn:message('sys-profile:sys.profile.loginconfig.closed')}"></ui:switch>
				</td>
			</tr>
			<%-- <c:import url="/sys/profile/maintenance/third_defaultlogin_config.jsp" charEncoding="utf-8"></c:import>
			<c:import url="/sys/profile/maintenance/third_login_config.jsp" charEncoding="utf-8"></c:import> --%>
		</table>
		<%if("true".equals(ResourceUtil.getKmssConfigString("kmss.lang.suportEnabled"))){%>
			<p style="font-size:12px;padding-left:8px;margin-left:22px;"><bean:message bundle="sys-profile" key="sys.profile.loginconfig.ps" /></p>
		<%}%>
		<center style="margin:10px 0;">
			<ui:button text="${lfn:message('button.save')}" suspend="bottom" height="35" width="120" onclick="formSubmit()"></ui:button>
		</center>
	</html:form>
	<script type="text/javascript">
	
 		//window.onload=function(){
 			var timerCount = 0;
 			var sTimer = setInterval(function(){
 				changeKKShow();
 			},100);

 		//}
		function changeKKShow(){
				try{
 					if($(".kk_download_enable input[type=hidden]").val()||timerCount==20){
 						clearInterval(sTimer);
 					}
 					timerCount++;
 					if($(".kk_download_enable input[type=hidden]").val()==="true"){
 						$(".kk_is_show").show();
 					}else{
 						$(".kk_is_show").hide();
 					}
 				}catch(e){
 					if(window.console){
 						console.log(e);
 					}
 				}
		}
		function formSubmit(){
			var newData = $(".login_template_enable input[type=hidden]").val();
			window.parent.templateValue = newData;
			Com_Submit(document.sysAppConfigForm, 'update');
		}
	</script>
	</template:replace>
</template:include>